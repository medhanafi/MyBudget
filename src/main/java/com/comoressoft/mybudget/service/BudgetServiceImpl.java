package com.comoressoft.mybudget.service;

import java.math.BigDecimal;
import java.text.DateFormatSymbols;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.mapstruct.factory.Mappers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.comoressoft.mybudget.dto.Category;
import com.comoressoft.mybudget.dto.Family;
import com.comoressoft.mybudget.dto.Item;
import com.comoressoft.mybudget.dto.ItemShoppingList;
import com.comoressoft.mybudget.dto.ShoppingList;
import com.comoressoft.mybudget.dto.SubCategory;
import com.comoressoft.mybudget.dto.Summary;
import com.comoressoft.mybudget.dto.TotalSummary;
import com.comoressoft.mybudget.entity.CategoryEntity;
import com.comoressoft.mybudget.entity.FamilyEntity;
import com.comoressoft.mybudget.entity.ItemEntity;
import com.comoressoft.mybudget.entity.ItemShoppingListEntity;
import com.comoressoft.mybudget.entity.ShoppingListEntity;
import com.comoressoft.mybudget.entity.SubCategoryEntity;
import com.comoressoft.mybudget.mapper.GlobalMapper;
import com.comoressoft.mybudget.repository.CategoryRepository;
import com.comoressoft.mybudget.repository.FamilyRepository;
import com.comoressoft.mybudget.repository.ItemRepository;
import com.comoressoft.mybudget.repository.ItemShoppingListRepository;
import com.comoressoft.mybudget.repository.ShoppingListRepository;
import com.comoressoft.mybudget.repository.SubCategoryRepository;

@Service
public class BudgetServiceImpl {

	private static final Logger LOGGER = LoggerFactory.getLogger(BudgetServiceImpl.class);
	@Autowired
	private CategoryRepository categoryRepository;
	@Autowired
	private ItemRepository itemRepository;
	@Autowired
	private SubCategoryRepository subCategoryRepository;

	@Autowired
	private ShoppingListRepository shoppingListRepository;
	@Autowired
	private ItemShoppingListRepository itemShoppingListRepository;

	@Autowired
	private FamilyRepository familyRepository;

	GlobalMapper mapper = Mappers.getMapper(GlobalMapper.class);

	public List<Category> getCategories(Integer month) {
		List<Category> categories = new ArrayList<>();

		List<CategoryEntity> listCat = categoryRepository.findAll();
		if (month != null && month != 0) {
			categories = this.getCategoriesByMonth(listCat, month);
		} else {
			this.categoryToCategoryDTO(listCat, categories);
		}

		return categories;
	}

	public ItemEntity addItem(ItemEntity item) {
		return itemRepository.save(item);
	}

	public Category addCategory(Category categoryDto) {
		CategoryEntity cat = this.categoryRepository.findById(categoryDto.getCatId()).get();
		cat.setCategoryLabel(categoryDto.getCatLabel());
		return mapper.categoryToCategoryDTO(categoryRepository.save(cat));
	}

	public SubCategory addSubCategory(SubCategory subCategoryDto) {
		SubCategoryEntity scat = this.subCategoryRepository.findById(subCategoryDto.getSubCatId()).get();
		scat.setSubCategoryLabel(subCategoryDto.getSubCatLabel());
		return mapper.subCategoryToSubCategoryDTO(subCategoryRepository.save(scat));
	}

	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public void deleteCategory(Long catId) {
		categoryRepository.deleteById(catId);
	}

	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public void deleteSubCategory(Long subCatId) {
		SubCategoryEntity sub = subCategoryRepository.findById(subCatId).get();
		CategoryEntity cat = categoryRepository.findById(sub.getCategory().getId()).get();
		cat.getSubCategory().remove(sub);
		LOGGER.debug(sub.getSubCategoryLabel() + " " + cat.getCategoryLabel());
		subCategoryRepository.deleteById(subCatId);
	}

	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public void deleteItem(Long itemId) {
		itemRepository.deleteById(itemId);
	}

	public List<ItemEntity> addManyItem(List<ItemEntity> itemsToSave) {
		List<ItemEntity> items = new LinkedList<>(itemRepository.saveAll(itemsToSave));
		items.sort(new Comparator<ItemEntity>() {
			@Override
			public int compare(ItemEntity o1, ItemEntity o2) {
				return (o1.getItemLabelle().compareTo(o2.getItemLabelle()));
			}
		});
		return items;
	}

	public List<ItemEntity> getItems() {

		List<ItemEntity> items = new LinkedList<>(itemRepository.findAll());

		items.sort(new Comparator<ItemEntity>() {
			@Override
			public int compare(ItemEntity o1, ItemEntity o2) {
				return (o1.getItemLabelle().compareTo(o2.getItemLabelle()));
			}
		});
		return items;
	}

	public List<SubCategory> getSubCategoryByCategory(Long catId, Integer month) {
		List<SubCategory> subCategories = new LinkedList<>();
		if (month != null && month != 0) {
			subCategories = this.getSubCategoriesByMonth(catId, month);
		} else {
			subCategories = this.getSubCategoryByCategory(catId);
		}
		return subCategories;
	}

	private List<SubCategory> getSubCategoriesByMonth(Long catId, Integer month) {
		List<SubCategoryEntity> listSubCat = getSubCategoryByCategry(catId);

		SubCategory subCatDto = null;
		List<SubCategory> subCategories = this.parepare(month, listSubCat, subCatDto);

		return subCategories;
	}

	private List<SubCategory> parepare(Integer month, List<SubCategoryEntity> listSubCat, SubCategory subCatDto) {

		List<SubCategory> subCategories = new LinkedList<>();
		BigDecimal catTotalCost = new BigDecimal(0);

		for (SubCategoryEntity scat : listSubCat) {

			subCatDto = new SubCategory();
			subCatDto.setSubCatId(scat.getId());
			subCatDto.setSubCatLabel(scat.getSubCategoryLabel());
			subCatDto.setSubCatState(scat.getSubCategoryState());

			if (!scat.getItem().isEmpty()) {
				BigDecimal subCatTotalCost = new BigDecimal(0);
				subCatTotalCost = subCatTotalCost.add(calculatTotalCostByMonth(scat.getItem(), month));
				subCatDto.setSubCatTotalCost(subCatTotalCost);
				catTotalCost = catTotalCost.add(subCatTotalCost);

			} else {
				subCatDto.setSubCatTotalCost(new BigDecimal(0));
			}
			subCategories.add(subCatDto);
		}
		return subCategories;

	}

	public List<SubCategory> getSubCategoryByCategory(Long idCat) {
		SubCategory scdto = null;
		List<SubCategory> subCategories = new LinkedList<>();

		for (SubCategoryEntity scat : getSubCategoryByCat(idCat)) {
			scdto = new SubCategory();
			scdto.setSubCatId(scat.getId());
			scdto.setSubCatLabel(scat.getSubCategoryLabel());
			scdto.setSubCatState(scat.getSubCategoryState());
			scdto.setSubCatTotalCost(calculatSubCatTotalCost(scat));

			subCategories.add(scdto);
		}
		return subCategories;
	}

	private Set<SubCategoryEntity> getSubCategoryByCat(Long idCat) {
		Optional<CategoryEntity> catData = categoryRepository.findById(idCat);
		if (catData.isPresent()) {
			return new LinkedHashSet<>(subCategoryRepository.findByCategory(catData.get()));
		}
		return null;

	}

	private List<SubCategoryEntity> getSubCategoryByCategry(Long idCat) {
		Optional<CategoryEntity> catData = categoryRepository.findById(idCat);
		if (catData.isPresent()) {
			return new LinkedList<>(subCategoryRepository.findByCategory(catData.get()));
		}
		return null;

	}

	private void categoryToCategoryDTO(List<CategoryEntity> listCat, List<Category> categories) {

		for (CategoryEntity cat : listCat) {
			categories.add(mapper.categoryToCategoryDTO(cat));
		}

	}

	private BigDecimal calculatTotalCostByMonth(List<ItemEntity> items, int month) {
		BigDecimal sum = new BigDecimal(0);

		for (ItemEntity item : items) {
			if (item.getDateItem().getMonthValue() == month) {
				BigDecimal param = getAsDecimal(String.valueOf(item.getExpectedQuantity()))
						.multiply(item.getExpectedAmount());
				sum = sum.add(param);
			}
		}
		return sum;
	}

	private BigDecimal getActualAmountBySHLItem(SubCategory scat, int month) {
		BigDecimal sum = new BigDecimal(0);

		for (ItemEntity item : itemRepository.findBySubCategory(mapper.subCategoryDTOToSubCategory(scat))) {
			if (item.getDateItem().getMonthValue() == month) {
				sum = sum.add(getActualAmountBySHL(item));
			}
		}
		return sum;
	}

	private BigDecimal getActualAmountBySHL(ItemEntity item) {
		List<ItemShoppingListEntity> itemShls = itemShoppingListRepository.findByItem(item);
		BigDecimal sum = new BigDecimal(0);
		for (ItemShoppingListEntity ishl : itemShls) {
			BigDecimal param = getAsDecimal(String.valueOf(ishl.getActualQuantity())).multiply(ishl.getActualAmount());
			sum = sum.add(param);
		}
		return sum;

	}

	private BigDecimal calculatSubCatTotalCost(SubCategoryEntity scat) {
		BigDecimal sum = new BigDecimal(0);

		for (ItemEntity item : scat.getItem()) {
			BigDecimal param = getAsDecimal(String.valueOf(item.getExpectedQuantity()))
					.multiply(item.getExpectedAmount());
			sum = sum.add(param);
		}
		return sum;
	}

	// private BigDecimal calculatItemsTotalCost(int month) {
	// List<Item> items = getItemsByMonth(month);
	// BigDecimal sum = new BigDecimal(0);
	// for (Item item : items) {
	// BigDecimal param = getAsDecimal(String.valueOf(item.getExpectedQuantity()))
	// .multiply(item.getExpectedAmount());
	// sum = sum.add(param);
	// }
	// return sum;
	// }

	private Pair<BigDecimal, BigDecimal> calculatTotalCost(int month, String revLabel) {
		BigDecimal sumRev = new BigDecimal(0);
		BigDecimal sumDep = new BigDecimal(0);
		List<Category> cats = getCategories(month);
		for (Category cat : cats) {
			if (cat != null) {
				if (cat.getCatLabel().equalsIgnoreCase(revLabel)) {
					if (cat.getCatTotalCost() != null) {
						sumRev = sumRev.add(cat.getCatTotalCost());
					}
				} else {
					if (cat.getCatTotalCost() != null) {
						for (SubCategory sCat : cat.getSubCategories()) {
							BigDecimal st = getActualAmountBySHLItem(sCat, month);
							sumDep = sumDep.add(st);
						}
					}
				}
			}
		}

		return Pair.of(sumRev, sumDep);
	}

	public Summary getSummary(int month) {
		for (Summary key : getSummary().values()) {
			if (key.getMonthPosition() == month) {
				return key;
			}
		}
		return null;
	}

	public Map<String, Summary> getSummary() {

		Map<String, Summary> mapSummary = new HashMap<>();
		for (int month = 1; month < 13; month++) {
			Pair<BigDecimal, BigDecimal> total = calculatTotalCost(month, "Revenus");
			BigDecimal dep = total.getSecond();
			BigDecimal rev = total.getFirst();
			Summary summary = new Summary();
			summary.setMonthPosition(month);
			TotalSummary tsummary = new TotalSummary();
			tsummary.setExpense(dep);
			tsummary.setIncome(rev);
			tsummary.setBalance(rev.subtract(dep));
			summary.setTotalSummary(tsummary);
			mapSummary.put(getMonthName(month), summary);
		}

		return mapSummary;
	}

	private String getMonthName(int month) {
		DateFormatSymbols french_dfs = new DateFormatSymbols(Locale.FRENCH);
		String[] frenchMonths = french_dfs.getMonths();
		return frenchMonths[month - 1];
	}

	private List<ItemEntity> getItemsByMonth(int month) {
		return itemRepository.findByDateItemLtAndGt(month);
	}

	public List<CategoryEntity> getCategoriesEntityByMonth(int month) {
		List<ItemEntity> items = getItemsByMonth(month);
		List<SubCategoryEntity> subCategories = new ArrayList<>();

		for (ItemEntity item : items) {
			Optional<SubCategoryEntity> subData = subCategoryRepository.findById(item.getSubCategory().getId());
			if (subData.isPresent()) {
				SubCategoryEntity subCategorie = subData.get();
				if (!subCategories.contains(subCategorie))
					subCategories.add(item.getSubCategory());
			}

		}

		List<CategoryEntity> categories = new LinkedList<>();
		for (SubCategoryEntity subCat : subCategories) {
			Optional<CategoryEntity> subData = categoryRepository.findById(subCat.getCategory().getId());
			if (subData.isPresent()) {
				CategoryEntity categorie = subData.get();
				if (!categories.contains(categorie))
					categories.add(categorie);
			}

		}
		List<CategoryEntity> resultCategories = new LinkedList<>();
		for (CategoryEntity cat : categories) {
			for (SubCategoryEntity scat : subCategories) {
				if (scat.getCategory().getId() == cat.getId()) {
					if (!cat.getSubCategory().contains(scat))
						cat.getSubCategory().add(scat);
				}
			}
			resultCategories.add(cat);
		}

		return resultCategories;
	}

	public List<SubCategory> getSubCategoriesByMonth(int month) {
		List<ItemEntity> items = getItemsByMonth(month);
		List<SubCategoryEntity> subCategories = new LinkedList<>();

		for (ItemEntity item : items) {
			Optional<SubCategoryEntity> subData = subCategoryRepository.findById(item.getSubCategory().getId());
			if (subData.isPresent()) {
				SubCategoryEntity subCategorie = subData.get();
				subCategories.add(subCategorie);
			}

		}
		SubCategory scdto = null;
		List<SubCategory> subCategoriesDTO = new LinkedList<>();
		for (SubCategoryEntity scat : subCategories) {
			scdto = new SubCategory();
			scdto.setSubCatId(scat.getId());
			scdto.setSubCatLabel(scat.getSubCategoryLabel());
			scdto.setSubCatState(scat.getSubCategoryState());
			scdto.setSubCatTotalCost(calculatSubCatTotalCost(scat));

			subCategoriesDTO.add(scdto);
		}
		return subCategoriesDTO;
	}

	public List<Category> getCategoriesByMonth(List<CategoryEntity> listCat, int month) {
		List<Category> categories = new LinkedList<>();

		Category catDto = null;
		SubCategory subCatDto = null;
		for (CategoryEntity cat : listCat) {
			catDto = new Category();
			catDto.setCatId(cat.getId());
			catDto.setCatLabel(cat.getCategoryLabel());
			catDto.setCatState(cat.getCategoryState());

			this.parepare(month, cat, catDto, subCatDto);
			// this.pareparCatDto(cat, catDto, subCatDto);
			categories.add(catDto);
		}

		return categories;
	}

	private void parepare(int month, CategoryEntity cat, Category catDto, SubCategory subCatDto) {
		List<SubCategory> subCategories = new LinkedList<>();
		BigDecimal catTotalCost = new BigDecimal(0);

		for (SubCategoryEntity scat : cat.getSubCategory()) {

			subCatDto = new SubCategory();
			subCatDto.setSubCatId(scat.getId());
			subCatDto.setSubCatLabel(scat.getSubCategoryLabel());
			subCatDto.setSubCatState(scat.getSubCategoryState());

			if (!scat.getItem().isEmpty()) {
				BigDecimal subCatTotalCost = new BigDecimal(0);
				subCatTotalCost = subCatTotalCost.add(calculatTotalCostByMonth(scat.getItem(), month));
				subCatDto.setSubCatTotalCost(subCatTotalCost);
				catTotalCost = catTotalCost.add(subCatTotalCost);

			} else {
				subCatDto.setSubCatTotalCost(new BigDecimal(0));
			}
			subCategories.add(subCatDto);
		}
		catDto.setCatTotalCost(catTotalCost);
		catDto.setSubCategories(subCategories);

	}

	public BigDecimal getAsDecimal(String val) {
		return new BigDecimal(val);
	}

	public List<Item> getItems(Integer month) {
		List<ItemEntity> items = new ArrayList<>();
		List<Item> itemsDto = null;
		if (month != null && month != 0) {
			items = this.getItemsByMonth(month);
		} else {
			items = this.getItems();
		}

		itemsDto = itemToItemDto(items);

		return itemsDto;
	}

	public List<Item> preloadItems(Integer month) {
		List<ItemEntity> itemsPrev = new ArrayList<>();
		List<ItemEntity> itemsAct = new ArrayList<>();
		List<Item> itemsDto = null;
		if (month != null && month != 0) {
			if (this.getItemsByMonth(month).isEmpty()) {
				itemsPrev = this.getItemsByMonth(month - 1);
				for (ItemEntity i : itemsPrev) {
					ItemEntity item = new ItemEntity();
					item.setDateItem(LocalDate.of(Calendar.getInstance().get(Calendar.YEAR), month,
							Calendar.getInstance().get(Calendar.DAY_OF_MONTH)));
					item.setExpectedAmount(i.getExpectedAmount());
					item.setExpectedQuantity(i.getExpectedQuantity());
					item.setItemLabelle(i.getItemLabelle());
					item.setSubCategory(i.getSubCategory());
					itemsAct.add(item);
				}
				itemsAct = this.itemRepository.saveAll(itemsAct);
			}

		}

		itemsDto = itemToItemDto(itemsAct);

		return itemsDto;
	}

	public List<Item> itemToItemDto(List<ItemEntity> items) {
		List<Item> itemsDto = new ArrayList<>();
		for (ItemEntity item : items) {
			if (!item.getSubCategory().getCategory().getCategoryLabel().equals("Revenus")) {
				itemsDto.add(mapper.itemToItemDTO(item));
			}
		}
		return itemsDto;
	}

	public List<Item> getItemsBySubCat(Long subCategorie, Integer month) {
		List<ItemEntity> items = itemRepository.findByMonthAndSubCat(month, subCategorie);
		return itemToItemDto(items);
	}

	public Item addItem(Item itemDto) {
		ItemEntity item = mapper.itemDTOToItem(itemDto);

//		if (item.getId() != null) {
//			Optional<Item> optItemOld = itemRepository.findById(item.getId());
//
//			if (optItemOld.isPresent()) {
//				Item itemOld = optItemOld.get();
//				BigDecimal amount = itemOld.getExpectedAmount().subtract(item.getExpectedAmount());
//
//				// repo add amount
//
//			}
//		}

		this.addItem(item);

		return mapper.itemToItemDTO(item);
	}

	public ItemShoppingList addItemToShoppingList(Long itemId, Long idSHL) {
		ItemShoppingListEntity iShopList = new ItemShoppingListEntity();
		ItemShoppingList dto = new ItemShoppingList();

		ItemEntity item = this.itemRepository.findById(itemId).get();
		ShoppingListEntity shl = this.shoppingListRepository.findById(idSHL).get();

		ItemShoppingListEntity ISHL = this.itemShoppingListRepository.findByShoppingListAndItem(shl, item);

		if (ISHL == null) {
			iShopList.setItem(item);
			iShopList.setShoppingList(shl);
			iShopList.setActualAmount(new BigDecimal(0));
			iShopList.setActualQuantity(0);
			iShopList = this.itemShoppingListRepository.save(iShopList);
			if (iShopList != null) {
				ItemEntity it = iShopList.getItem();
				it.setItemStatus("used");
				this.itemRepository.save(it);
				dto = mapper.itemShoppingListToITemShoppingListDTO(iShopList);
			}

		} else {
			ISHL.setActualQuantity(ISHL.getActualQuantity() + 1);
			ISHL = this.itemShoppingListRepository.save(ISHL);
			dto = mapper.itemShoppingListToITemShoppingListDTO(ISHL);
		}

		return dto;
	}

	public ItemShoppingList updateItemShoppingList(ItemShoppingList ishlToupdate) {
		return mapper.itemShoppingListToITemShoppingListDTO(
				this.itemShoppingListRepository.save(mapper.itemShoppingListDTOToITemShoppingList(ishlToupdate)));
	}

	@SuppressWarnings("unused")
	private ShoppingListEntity getCurrentShoppingList(int month) {
		List<ShoppingListEntity> lists = shoppingListRepository.findByCurrentDate(month);
		if (!lists.isEmpty()) {
			lists.sort(new Comparator<ShoppingListEntity>() {
				@Override
				public int compare(ShoppingListEntity o1, ShoppingListEntity o2) {
					return o2.getDateCreated().compareTo(o1.getDateCreated());
				}
			});
			return lists.get(0);
		} else {

			ShoppingListEntity shl = new ShoppingListEntity();
			shl.setAllocatedAmount(new BigDecimal(20));
			shl.setDateShopping(LocalDate.now());
			return shoppingListRepository.save(shl);
		}

	}

	public List<ShoppingList> getShoppingLists(Integer month) {
		List<ShoppingListEntity> lists = shoppingListRepository.findByCurrentDate(month);
		List<ShoppingList> listDto = new ArrayList<>();
		for (ShoppingListEntity shl : lists) {
			listDto.add(mapper.shoppingListToShoppingListDTO(shl));
		}
		return listDto;
	}

	public ShoppingList addShoppingLists(ShoppingListEntity shl) {
		shl.setDateCreated(LocalDate.now());
		ShoppingListEntity shlist = shoppingListRepository.save(shl);
		return mapper.shoppingListToShoppingListDTO(shlist);
	}

	public List<ItemShoppingList> getItemShoppingList(Long idSHL) {
		List<ItemShoppingListEntity> lists = itemShoppingListRepository.findByShoppingList(new ShoppingListEntity(idSHL));
		List<ItemShoppingList> listDto = new ArrayList<>();
		for (ItemShoppingListEntity shl : lists) {
			listDto.add(mapper.itemShoppingListToITemShoppingListDTO(shl));
		}
		return listDto;
	}

	public List<Item> getItems(Integer month, String codeFamily) {
		List<ItemEntity> items = new ArrayList<>();
		List<Item> itemsDto = null;
		if (month != null && month != 0) {
			items = this.getItemsByMonth(month);
		} else {
			items = this.getItems();
		}
		List<ItemEntity> itemsFamily = this.filterItemByFamily(items, codeFamily);
		itemsDto = itemToItemDto(itemsFamily);

		return itemsDto;
	}

	public List<Item> getItemsBySubCat(Long subCategorie, Integer month, String codeFamily) {
		List<ItemEntity> items = itemRepository.findByMonthAndSubCat(month, subCategorie);
		List<ItemEntity> itemsFamily = this.filterItemByFamily(items, codeFamily);
		return itemToItemDto(itemsFamily);
	}

	public Family addFamily(FamilyEntity family) {
		return this.mapper.familyToFamilyDTO(this.familyRepository.save(family));

	}

	public List<ItemEntity> filterItemByFamily(List<ItemEntity> listItem, String codeFamily) {
		return listItem.stream().filter(item -> item.getFamily().getCode().equals(codeFamily))
				.collect(Collectors.toList());
	}

	public List<ShoppingListEntity> filterShoppingListByFamily(List<ShoppingListEntity> listShoppingList, String codeFamily) {

		return listShoppingList.stream().filter(shl -> shl.getFamily().getCode().equals(codeFamily))
				.collect(Collectors.toList());
	}

	public List<ShoppingList> getShoppingLists(Integer month, String codeFamily) {
		List<ShoppingListEntity> lists = shoppingListRepository.findByCurrentDate(month);

		List<ShoppingListEntity> shoppingListFamily = this.filterShoppingListByFamily(lists, codeFamily);

		List<ShoppingList> listDto = new ArrayList<>();
		for (ShoppingListEntity shl : shoppingListFamily) {
			listDto.add(mapper.shoppingListToShoppingListDTO(shl));
		}
		return listDto;
	}

	public Family findFamily(String code, String pwd) {
		FamilyEntity family = this.familyRepository.findByCodeAndPwd(code, pwd);
		return this.mapper.familyToFamilyDTO(family);
	}

	public String getCodeFamily() {
		return "MHA14633";
	}

}

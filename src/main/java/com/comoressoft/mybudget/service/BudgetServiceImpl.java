package com.comoressoft.mybudget.service;

import java.math.BigDecimal;
import java.text.DateFormatSymbols;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;

import com.comoressoft.mybudget.dto.CategoryDTO;
import com.comoressoft.mybudget.dto.ItemDTO;
import com.comoressoft.mybudget.dto.ItemShoppingListDTO;
import com.comoressoft.mybudget.dto.ShoppingListDTO;
import com.comoressoft.mybudget.dto.SubCategoryDTO;
import com.comoressoft.mybudget.dto.SummaryDTO;
import com.comoressoft.mybudget.dto.TotalSummaryDTO;
import com.comoressoft.mybudget.model.Category;
import com.comoressoft.mybudget.model.Item;
import com.comoressoft.mybudget.model.ItemShoppingList;
import com.comoressoft.mybudget.model.ShoppingList;
import com.comoressoft.mybudget.model.SubCategory;
import com.comoressoft.mybudget.repository.CategoryRepository;
import com.comoressoft.mybudget.repository.ItemRepository;
import com.comoressoft.mybudget.repository.ItemShoppingListRepository;
import com.comoressoft.mybudget.repository.ShoppingListRepository;
import com.comoressoft.mybudget.repository.SubCategoryRepository;

@Service
public class BudgetServiceImpl {
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

//	summaryList.sort(new Comparator<Summary>() {
//        @Override
//        public int compare(Summary o1, Summary o2) {
//            return (Integer.compare(o1.getMonthPosition(), o2.getMonthPosition()));
//        }
//    });
	public List<CategoryDTO> getCategories(Integer month) {
		List<CategoryDTO> categories = new ArrayList<>();

		List<Category> listCat = categoryRepository.findAll();
		if (month != null && month != 0) {
			categories = this.getCategoriesByMonth(listCat, month);
		} else {
			this.categoryToCategoryDTO(listCat, categories);
		}

		return categories;
	}

	public Item addItem(Item item) {
		return itemRepository.save(item);
	}

	public void addCategory(Category cat) {
		categoryRepository.save(cat);
	}

	public void addSubCategory(SubCategory subCat) {
		subCategoryRepository.save(subCat);
	}

	public List<Item> addManyItem(List<Item> itemsToSave) {
		return new LinkedList<>(itemRepository.saveAll(itemsToSave));
	}

	public List<Item> getItems() {
		return new LinkedList<>(itemRepository.findAll());
	}

	public List<SubCategoryDTO> getSubCategoryByCategory(Long catId, Integer month) {
		List<SubCategoryDTO> subCategories = new LinkedList<>();
		if (month != null && month != 0) {
			subCategories = this.getSubCategoriesByMonth(catId, month);
		} else {
			subCategories = this.getSubCategoryByCategory(catId);
		}
		return subCategories;
	}

	private List<SubCategoryDTO> getSubCategoriesByMonth(Long catId, Integer month) {
		List<SubCategory> listSubCat = getSubCategoryByCategry(catId);

		SubCategoryDTO subCatDto = null;
		List<SubCategoryDTO> subCategories = this.parepare(month, listSubCat, subCatDto);

		return subCategories;
	}

	private List<SubCategoryDTO> parepare(Integer month, List<SubCategory> listSubCat, SubCategoryDTO subCatDto) {

		List<SubCategoryDTO> subCategories = new LinkedList<>();
		BigDecimal catTotalCost = new BigDecimal(0);

		for (SubCategory scat : listSubCat) {

			subCatDto = new SubCategoryDTO();
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

	public List<SubCategoryDTO> getSubCategoryByCategory(Long idCat) {
		SubCategoryDTO scdto = null;
		List<SubCategoryDTO> subCategories = new LinkedList<>();

		for (SubCategory scat : getSubCategoryByCat(idCat)) {
			scdto = new SubCategoryDTO();
			scdto.setSubCatId(scat.getId());
			scdto.setSubCatLabel(scat.getSubCategoryLabel());
			scdto.setSubCatState(scat.getSubCategoryState());
			scdto.setSubCatTotalCost(calculatSubCatTotalCost(scat));

			subCategories.add(scdto);
		}
		return subCategories;
	}

	private Set<SubCategory> getSubCategoryByCat(Long idCat) {
		Optional<Category> catData = categoryRepository.findById(idCat);
		if (catData.isPresent()) {
			return new LinkedHashSet<>(subCategoryRepository.findByCategory(catData.get()));
		}
		return null;

	}

	private List<SubCategory> getSubCategoryByCategry(Long idCat) {
		Optional<Category> catData = categoryRepository.findById(idCat);
		if (catData.isPresent()) {
			return new LinkedList<>(subCategoryRepository.findByCategory(catData.get()));
		}
		return null;

	}

	private void categoryToCategoryDTO(List<Category> listCat, List<CategoryDTO> categories) {

		for (Category cat : listCat) {
			categories.add(catToDto(cat));
		}

	}

	private CategoryDTO catToDto(Category cat) {
		CategoryDTO catDto = new CategoryDTO();
		catDto.setCatId(cat.getId());
		catDto.setCatLabel(cat.getCategoryLabel());
		catDto.setCatState(cat.getCategoryState());

		this.pareparCatDto(cat, catDto);
		return catDto;
	}

	private BigDecimal calculatTotalCostByMonth(List<Item> items, int month) {
		BigDecimal sum = new BigDecimal(0);

		for (Item item : items) {
			if (item.getDateItem().getMonthValue() == month) {
				BigDecimal param = getAsDecimal(String.valueOf(item.getExpectedQuantity()))
						.multiply(item.getExpectedAmount());
				sum = sum.add(param);
			}
		}
		return sum;
	}

	private BigDecimal calculatSubCatTotalCost(SubCategory scat) {
		BigDecimal sum = new BigDecimal(0);

		for (Item item : scat.getItem()) {
			BigDecimal param = getAsDecimal(String.valueOf(item.getExpectedQuantity()))
					.multiply(item.getExpectedAmount());
			sum = sum.add(param);
		}
		return sum;
	}

//	private BigDecimal calculatItemsTotalCost(int month) {
//		List<Item> items = getItemsByMonth(month);
//		BigDecimal sum = new BigDecimal(0);
//		for (Item item : items) {
//			BigDecimal param = getAsDecimal(String.valueOf(item.getExpectedQuantity()))
//					.multiply(item.getExpectedAmount());
//			sum = sum.add(param);
//		}
//		return sum;
//	}

	private Pair<BigDecimal, BigDecimal> calculatTotalCost(int month, String revLabel) {
		BigDecimal sumRev = new BigDecimal(0);
		BigDecimal sumDep = new BigDecimal(0);
		List<CategoryDTO> cats = getCategories(month);
		for (CategoryDTO cat : cats) {
			if (cat != null) {
				if (cat.getCatLabel().equalsIgnoreCase(revLabel)) {
					if (cat.getCatTotalCost() != null) {
						sumRev = sumRev.add(cat.getCatTotalCost());
					}
				} else {
					if (cat.getCatTotalCost() != null) {
						sumDep = sumDep.add(cat.getCatTotalCost());
					}
				}
			}
		}

		return Pair.of(sumRev, sumDep);
	}

	public SummaryDTO getSummary(int month) {
		for (SummaryDTO key : getSummary().values()) {
			if (key.getMonthPosition() == month) {
				return key;
			}
		}
		return null;
	}

	public Map<String, SummaryDTO> getSummary() {

		Map<String, SummaryDTO> mapSummary = new HashMap<>();
		for (int month = 1; month < 13; month++) {
			Pair<BigDecimal, BigDecimal> total = calculatTotalCost(month, "Revenus");
			BigDecimal dep = total.getSecond();
			BigDecimal rev = total.getFirst();
			SummaryDTO summary = new SummaryDTO();
			summary.setMonthPosition(month);
			TotalSummaryDTO tsummary = new TotalSummaryDTO();
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

	private List<Item> getItemsByMonth(int month) {
		// LocalDate start = LocalDate.of(Calendar.getInstance().get(Calendar.YEAR),
		// month, 1);
		// LocalDate end = LocalDate.of(Calendar.getInstance().get(Calendar.YEAR),
		// month, start.lengthOfMonth());
		return itemRepository.findByDateItemLtAndGt(month);
	}

	public List<Category> getCategoriesEntityByMonth(int month) {
		List<Item> items = getItemsByMonth(month);
		List<SubCategory> subCategories = new ArrayList<>();

		for (Item item : items) {
			Optional<SubCategory> subData = subCategoryRepository.findById(item.getSubCategory().getId());
			if (subData.isPresent()) {
				SubCategory subCategorie = subData.get();
				if (!subCategories.contains(subCategorie))
					subCategories.add(item.getSubCategory());
			}

		}

		List<Category> categories = new LinkedList<>();
		for (SubCategory subCat : subCategories) {
			Optional<Category> subData = categoryRepository.findById(subCat.getCategory().getId());
			if (subData.isPresent()) {
				Category categorie = subData.get();
				if (!categories.contains(categorie))
					categories.add(categorie);
			}

		}
		List<Category> resultCategories = new LinkedList<>();
		for (Category cat : categories) {
			for (SubCategory scat : subCategories) {
				if (scat.getCategory().getId() == cat.getId()) {
					if (!cat.getSubCategory().contains(scat))
						cat.getSubCategory().add(scat);
				}
			}
			resultCategories.add(cat);
		}

		return resultCategories;
	}

	public List<SubCategoryDTO> getSubCategoriesByMonth(int month) {
		List<Item> items = getItemsByMonth(month);
		List<SubCategory> subCategories = new LinkedList<>();

		for (Item item : items) {
			Optional<SubCategory> subData = subCategoryRepository.findById(item.getSubCategory().getId());
			if (subData.isPresent()) {
				SubCategory subCategorie = subData.get();
				subCategories.add(subCategorie);
			}

		}
		SubCategoryDTO scdto = null;
		List<SubCategoryDTO> subCategoriesDTO = new LinkedList<>();
		for (SubCategory scat : subCategories) {
			scdto = new SubCategoryDTO();
			scdto.setSubCatId(scat.getId());
			scdto.setSubCatLabel(scat.getSubCategoryLabel());
			scdto.setSubCatState(scat.getSubCategoryState());
			scdto.setSubCatTotalCost(calculatSubCatTotalCost(scat));

			subCategoriesDTO.add(scdto);
		}
		return subCategoriesDTO;
	}

	public List<CategoryDTO> getCategoriesByMonth(List<Category> listCat, int month) {
		List<CategoryDTO> categories = new LinkedList<>();

		CategoryDTO catDto = null;
		SubCategoryDTO subCatDto = null;
		for (Category cat : listCat) {
			catDto = new CategoryDTO();
			catDto.setCatId(cat.getId());
			catDto.setCatLabel(cat.getCategoryLabel());
			catDto.setCatState(cat.getCategoryState());

			this.parepare(month, cat, catDto, subCatDto);
			// this.pareparCatDto(cat, catDto, subCatDto);
			categories.add(catDto);
		}

		return categories;
	}

	private void pareparCatDto(Category cat, CategoryDTO catDto) {
		List<SubCategoryDTO> subCategories = new LinkedList<>();
		BigDecimal catTotalCost = new BigDecimal(0);
		for (SubCategory scat : cat.getSubCategory()) {
			SubCategoryDTO subCatDto = subCatToDto(scat);
			subCategories.add(subCatDto);
			catTotalCost = catTotalCost.add(subCatDto.getSubCatTotalCost());
		}
		catDto.setCatTotalCost(catTotalCost);
		catDto.setSubCategories(subCategories);

	}

	private SubCategoryDTO subCatToDto(SubCategory scat) {
		SubCategoryDTO subCatDto = new SubCategoryDTO();
		subCatDto = new SubCategoryDTO();
		subCatDto.setSubCatId(scat.getId());
		subCatDto.setSubCatLabel(scat.getSubCategoryLabel());
		subCatDto.setSubCatState(scat.getSubCategoryState());

		BigDecimal subCatTotalCost = new BigDecimal(0);
		subCatTotalCost = subCatTotalCost.add(calculatSubCatTotalCost(scat));
		subCatDto.setSubCatTotalCost(subCatTotalCost);

		return subCatDto;
	}

	private void parepare(int month, Category cat, CategoryDTO catDto, SubCategoryDTO subCatDto) {
		List<SubCategoryDTO> subCategories = new LinkedList<>();
		BigDecimal catTotalCost = new BigDecimal(0);

		for (SubCategory scat : cat.getSubCategory()) {

			subCatDto = new SubCategoryDTO();
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

	public List<ItemDTO> getItems(Integer month) {
		List<Item> items = new ArrayList<>();
		List<ItemDTO> itemsDto = null;
		if (month != null && month != 0) {
			items = this.getItemsByMonth(month);
		} else {
			items = this.getItems();
		}

		itemsDto = itemToItemDto(items);

		return itemsDto;
	}

	public List<ItemDTO> itemToItemDto(List<Item> items) {
		List<ItemDTO> itemsDto = new ArrayList<>();
		for (Item item : items) {
			if (!item.getSubCategory().getCategory().getCategoryLabel().equals("Revenus")) {
				itemsDto.add(itemToitemDto(item));
			}
		}
		return itemsDto;
	}

	private ItemDTO itemToitemDto(Item item) {
		ItemDTO itemDto = new ItemDTO();
		if (item != null) {
			itemDto.setItemId(item.getId());
			itemDto.setItemLabelle(item.getItemLabelle());
			itemDto.setExpectedAmount(item.getExpectedAmount());
			itemDto.setExpectedQuantity(item.getExpectedQuantity());
			itemDto.setSubCategorie(subCatToDto(item.getSubCategory()));
			itemDto.setItemStatus(item.getItemStatus());
			itemDto.setDateItem(item.getDateItem());
		}
		return itemDto;
	}

	private Item itemDtoToItem(ItemDTO itemDto) {
		Item item = new Item();
		if (item != null) {
			item.setId(itemDto.getItemId());
			item.setItemLabelle(itemDto.getItemLabelle());
			item.setExpectedAmount(itemDto.getExpectedAmount());
			item.setExpectedQuantity(itemDto.getExpectedQuantity());
			item.setSubCategory(subCatDtoToSubCat(itemDto.getSubCategorie()));
			item.setItemStatus(itemDto.getItemStatus());
			item.setDateItem(itemDto.getDateItem());
		}
		return item;
	}

	private SubCategory subCatDtoToSubCat(SubCategoryDTO subCateDto) {
		SubCategory subCat = new SubCategory();
		if (subCateDto != null) {
			subCat.setId(subCateDto.getSubCatId());
			subCat.setSubCategoryLabel(subCateDto.getSubCatLabel());
			subCat.setSubCategoryState(subCateDto.getSubCatState());
			subCat.setSubCategoryTotalCost(subCateDto.getSubCatTotalCost());
			subCat.setItem(this.getItemsBySubCat(subCateDto.getSubCatId()));
		}
		return subCat;
	}

	private List<Item> getItemsBySubCat(Long subCatId) {
		return itemRepository.findBySubCategory(new SubCategory(subCatId));
	}

	public List<ItemDTO> getItemsBySubCat(Long subCategorie, Integer month) {
		List<Item> items = itemRepository.findByMonthAndSubCat(month, subCategorie);
		return itemToItemDto(items);
	}

	public ItemDTO addItem(ItemDTO itemDto) {
		Item item = this.addItem(itemDtoToItem(itemDto));
		return itemToitemDto(item);
	}

	public ItemShoppingListDTO addItemToShoppingList(Long itemId, Long idSHL) {
		ItemShoppingList iShopList = new ItemShoppingList();
		ItemShoppingListDTO dto = new ItemShoppingListDTO();

		Item item = this.itemRepository.findById(itemId).get();
		ShoppingList shl = this.shoppingListRepository.findById(idSHL).get();

		ItemShoppingList ISHL = this.itemShoppingListRepository.findByShoppingListAndItem(shl, item);

		if (ISHL == null) {
			iShopList.setItem(item);
			iShopList.setShoppingList(shl);
			iShopList = this.itemShoppingListRepository.save(iShopList);
			if (iShopList != null) {
				Item it = iShopList.getItem();
				it.setItemStatus("used");
				this.itemRepository.save(it);
				dto = itemShopToDto(iShopList);
			}

		} else {
			ISHL.setActualQuantity(ISHL.getActualQuantity() + 1);
			ISHL = this.itemShoppingListRepository.save(ISHL);
			dto = itemShopToDto(ISHL);
		}

		return dto;
	}

	public ItemShoppingListDTO updateItemShoppingList(ItemShoppingListDTO ishlToupdate) {
		return itemShopToDto(this.itemShoppingListRepository.save(ishlToDtoToItemShoppingList(ishlToupdate)));
	}

	@SuppressWarnings("unused")
	private ShoppingList getCurrentShoppingList(int month) {
		List<ShoppingList> lists = shoppingListRepository.findByCurrentDate(month);
		if (!lists.isEmpty()) {
			lists.sort(new Comparator<ShoppingList>() {
				@Override
				public int compare(ShoppingList o1, ShoppingList o2) {
					return o2.getDateCreated().compareTo(o1.getDateCreated());
				}
			});
			return lists.get(0);
		} else {

			ShoppingList shl = new ShoppingList();
			shl.setAllocatedAmount(new BigDecimal(20));
			shl.setDateShopping(LocalDate.now());
			return shoppingListRepository.save(shl);
		}

	}

	private ItemShoppingListDTO itemShopToDto(ItemShoppingList ishopResult) {
		ItemShoppingListDTO itemDto = new ItemShoppingListDTO();
		itemDto.setId(ishopResult.getId());
		itemDto.setActualAmount(ishopResult.getActualAmount());
		itemDto.setActualQuantity(ishopResult.getActualQuantity());
		itemDto.setPurchasedDate(ishopResult.getPurchasedDate());
		itemDto.setItem(itemToitemDto(ishopResult.getItem()));
		itemDto.setShoppingList(shoppingListToDto(ishopResult.getShoppingList()));
		return itemDto;
	}

	private ShoppingListDTO shoppingListToDto(ShoppingList shoppingList) {
		ShoppingListDTO shopDto = new ShoppingListDTO();
		shopDto.setId(shoppingList.getId());
		shopDto.setAllocatedAmount(shoppingList.getAllocatedAmount());
		shopDto.setDateCreated(shoppingList.getDateCreated());
		shopDto.setShoppingListName(shoppingList.getShoppingListName());
		return shopDto;
	}

	private ShoppingList shoppingListDtoToSh(ShoppingListDTO shoppingList) {
		ShoppingList shop = new ShoppingList();
		shop.setId(shoppingList.getId());
		shop.setAllocatedAmount(shoppingList.getAllocatedAmount());
		shop.setDateCreated(shoppingList.getDateCreated());
		shop.setShoppingListName(shoppingList.getShoppingListName());
		return shop;
	}

	public List<ShoppingListDTO> getShoppingLists(Integer month) {
		List<ShoppingList> lists = shoppingListRepository.findByCurrentDate(month);
		List<ShoppingListDTO> listDto = new ArrayList<>();
		for (ShoppingList shl : lists) {
			listDto.add(shoppingListToDto(shl));
		}
		return listDto;
	}

	public ShoppingListDTO addShoppingLists(ShoppingList shl) {
		shl.setDateCreated(LocalDate.now());
		ShoppingList shlist = shoppingListRepository.save(shl);
		return shoppingListToDto(shlist);
	}

	public List<ItemShoppingListDTO> getItemShoppingList(Long idSHL) {
		List<ItemShoppingList> lists = itemShoppingListRepository.findByShoppingList(new ShoppingList(idSHL));
		List<ItemShoppingListDTO> listDto = new ArrayList<>();
		for (ItemShoppingList shl : lists) {
			listDto.add(itemShoppingListToDto(shl));
		}
		return listDto;
	}

	private ItemShoppingListDTO itemShoppingListToDto(ItemShoppingList shl) {
		ItemShoppingListDTO iShlDto = new ItemShoppingListDTO();
		iShlDto.setId(shl.getId());
		iShlDto.setActualAmount(shl.getActualAmount());
		iShlDto.setActualQuantity(shl.getActualQuantity());
		iShlDto.setPurchasedDate(shl.getPurchasedDate());
		iShlDto.setItem(itemToitemDto(shl.getItem()));
		iShlDto.setShoppingList(shoppingListToDto(shl.getShoppingList()));
		return iShlDto;
	}

	private ItemShoppingList ishlToDtoToItemShoppingList(ItemShoppingListDTO shl) {
		ItemShoppingList iShl = new ItemShoppingList();
		iShl.setId(shl.getId());
		iShl.setActualAmount(shl.getActualAmount());
		iShl.setActualQuantity(shl.getActualQuantity());
		iShl.setPurchasedDate(shl.getPurchasedDate());
		iShl.setItem(itemDtoToItem(shl.getItem()));
		iShl.setShoppingList(shoppingListDtoToSh(shl.getShoppingList()));
		return iShl;
	}

}

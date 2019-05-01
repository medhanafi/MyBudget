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

import com.comoressoft.mybudget.dto.CategoryDTO;
import com.comoressoft.mybudget.dto.FamilyDTO;
import com.comoressoft.mybudget.dto.ItemDTO;
import com.comoressoft.mybudget.dto.ItemShoppingListDTO;
import com.comoressoft.mybudget.dto.ShoppingListDTO;
import com.comoressoft.mybudget.dto.SubCategoryDTO;
import com.comoressoft.mybudget.dto.SummaryDTO;
import com.comoressoft.mybudget.dto.TotalSummaryDTO;
import com.comoressoft.mybudget.entity.Category;
import com.comoressoft.mybudget.entity.Family;
import com.comoressoft.mybudget.entity.Item;
import com.comoressoft.mybudget.entity.ItemShoppingList;
import com.comoressoft.mybudget.entity.ShoppingList;
import com.comoressoft.mybudget.entity.SubCategory;
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

	public CategoryDTO addCategory(CategoryDTO categoryDto) {
		Category cat = this.categoryRepository.findById(categoryDto.getCatId()).get();
		cat.setCategoryLabel(categoryDto.getCatLabel());
		return mapper.categoryToCategoryDTO(categoryRepository.save(cat));
	}

	public SubCategoryDTO addSubCategory(SubCategoryDTO subCategoryDto) {
		SubCategory scat = this.subCategoryRepository.findById(subCategoryDto.getSubCatId()).get();
		scat.setSubCategoryLabel(subCategoryDto.getSubCatLabel());
		return mapper.subCategoryToSubCategoryDTO(subCategoryRepository.save(scat));
	}

	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public void deleteCategory(Long catId) {
		categoryRepository.deleteById(catId);
	}

	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public void deleteSubCategory(Long subCatId) {
		SubCategory sub = subCategoryRepository.findById(subCatId).get();
		Category cat = categoryRepository.findById(sub.getCategory().getId()).get();
		cat.getSubCategory().remove(sub);
		LOGGER.debug(sub.getSubCategoryLabel()+" "+cat.getCategoryLabel());
		subCategoryRepository.deleteById(subCatId);
	}

	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public void deleteItem(Long itemId) {
		itemRepository.deleteById(itemId);
	}

	public List<Item> addManyItem(List<Item> itemsToSave) {
		List<Item> items=new LinkedList<>(itemRepository.saveAll(itemsToSave));
		items.sort(new Comparator<Item>() {
	        @Override
	        public int compare(Item o1, Item o2) {
	        	return (o1.getItemLabelle().compareTo(o2.getItemLabelle()));
	        }
	    });
		return items;
	}

	public List<Item> getItems() {
		
		List<Item> items=new LinkedList<>(itemRepository.findAll());
		
		items.sort(new Comparator<Item>() {
	        @Override
	        public int compare(Item o1, Item o2) {
	            return (o1.getItemLabelle().compareTo(o2.getItemLabelle()));
	        }
	    });
		return items;
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
			categories.add(mapper.categoryToCategoryDTO(cat));
		}

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
	
	private BigDecimal getActualAmountBySHLItem(SubCategoryDTO scat, int month) {
		BigDecimal sum = new BigDecimal(0);

		for (Item item : itemRepository.findBySubCategory(mapper.subCategoryDTOToSubCategory(scat))) {
			if (item.getDateItem().getMonthValue() == month) {
				sum=sum.add(getActualAmountBySHL(item));
			}
		}
		return sum;
	}
	private BigDecimal getActualAmountBySHL(Item item) {
		List<ItemShoppingList> itemShls =itemShoppingListRepository.findByItem(item);
			BigDecimal sum = new BigDecimal(0);
		for(ItemShoppingList ishl:itemShls) {
			BigDecimal param = getAsDecimal(String.valueOf(ishl.getActualQuantity()))
					.multiply(ishl.getActualAmount());
			sum = sum.add(param);
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
		List<CategoryDTO> cats = getCategories(month);
		for (CategoryDTO cat : cats) {
			if (cat != null) {
				if (cat.getCatLabel().equalsIgnoreCase(revLabel)) {
					if (cat.getCatTotalCost() != null) {
						sumRev = sumRev.add(cat.getCatTotalCost());
					}
				} else {
					if (cat.getCatTotalCost() != null) {
						for (SubCategoryDTO sCat : cat.getSubCategories()) {
						BigDecimal st= getActualAmountBySHLItem(sCat, month);
						sumDep = sumDep.add(st);
						}
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
	
	public List<ItemDTO> preloadItems(Integer month) {
		List<Item> itemsPrev = new ArrayList<>();
		List<Item> itemsAct = new ArrayList<>();
		List<ItemDTO> itemsDto = null;
		if (month != null && month != 0) {
			if(this.getItemsByMonth(month).isEmpty()) {
				itemsPrev = this.getItemsByMonth(month-1);
				for(Item i:itemsPrev) {
					Item item=new Item();
					item.setDateItem(LocalDate.of(Calendar.getInstance().get(Calendar.YEAR), month, Calendar.getInstance().get(Calendar.DAY_OF_MONTH)));
					item.setExpectedAmount(i.getExpectedAmount());
					item.setExpectedQuantity(i.getExpectedQuantity());
					item.setItemLabelle(i.getItemLabelle());
					item.setSubCategory(i.getSubCategory());
					itemsAct.add(item);
				}
				itemsAct=this.itemRepository.saveAll(itemsAct);
			}
		
		}
		
		itemsDto = itemToItemDto(itemsAct);

		return itemsDto;
	}

	public List<ItemDTO> itemToItemDto(List<Item> items) {
		List<ItemDTO> itemsDto = new ArrayList<>();
		for (Item item : items) {
			if (!item.getSubCategory().getCategory().getCategoryLabel().equals("Revenus")) {
				itemsDto.add(mapper.itemToItemDTO(item));
			}
		}
		return itemsDto;
	}

	public List<ItemDTO> getItemsBySubCat(Long subCategorie, Integer month) {
		List<Item> items = itemRepository.findByMonthAndSubCat(month, subCategorie);
		return itemToItemDto(items);
	}

	public ItemDTO addItem(ItemDTO itemDto) {
		Item item = mapper.itemDTOToItem(itemDto);

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

	public ItemShoppingListDTO addItemToShoppingList(Long itemId, Long idSHL) {
		ItemShoppingList iShopList = new ItemShoppingList();
		ItemShoppingListDTO dto = new ItemShoppingListDTO();

		Item item = this.itemRepository.findById(itemId).get();
		ShoppingList shl = this.shoppingListRepository.findById(idSHL).get();

		ItemShoppingList ISHL = this.itemShoppingListRepository.findByShoppingListAndItem(shl, item);

		if (ISHL == null) {
			iShopList.setItem(item);
			iShopList.setShoppingList(shl);
			iShopList.setActualAmount(new BigDecimal(0));
			iShopList.setActualQuantity(0);
			iShopList = this.itemShoppingListRepository.save(iShopList);
			if (iShopList != null) {
				Item it = iShopList.getItem();
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

	public ItemShoppingListDTO updateItemShoppingList(ItemShoppingListDTO ishlToupdate) {
		return mapper.itemShoppingListToITemShoppingListDTO(
				this.itemShoppingListRepository.save(mapper.itemShoppingListDTOToITemShoppingList(ishlToupdate)));
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

	public List<ShoppingListDTO> getShoppingLists(Integer month) {
		List<ShoppingList> lists = shoppingListRepository.findByCurrentDate(month);
		List<ShoppingListDTO> listDto = new ArrayList<>();
		for (ShoppingList shl : lists) {
			listDto.add(mapper.shoppingListToShoppingListDTO(shl));
		}
		return listDto;
	}

	public ShoppingListDTO addShoppingLists(ShoppingList shl) {
		shl.setDateCreated(LocalDate.now());
		ShoppingList shlist = shoppingListRepository.save(shl);
		return mapper.shoppingListToShoppingListDTO(shlist);
	}

	public List<ItemShoppingListDTO> getItemShoppingList(Long idSHL) {
		List<ItemShoppingList> lists = itemShoppingListRepository.findByShoppingList(new ShoppingList(idSHL));
		List<ItemShoppingListDTO> listDto = new ArrayList<>();
		for (ItemShoppingList shl : lists) {
			listDto.add(mapper.itemShoppingListToITemShoppingListDTO(shl));
		}
		return listDto;
	}

	public List<ItemDTO> getItems(Integer month, String codeFamily) {
		List<Item> items = new ArrayList<>();
		List<ItemDTO> itemsDto = null;
		if (month != null && month != 0) {
			items = this.getItemsByMonth(month);
		} else {
			items = this.getItems();
		}
		List<Item> itemsFamily =this.filterItemByFamily(items,codeFamily);
		itemsDto = itemToItemDto(itemsFamily);

		return itemsDto;
	}

	public List<ItemDTO> getItemsBySubCat(Long subCategorie, Integer month, String codeFamily) {
		List<Item> items = itemRepository.findByMonthAndSubCat(month, subCategorie);
		List<Item> itemsFamily =this.filterItemByFamily(items,codeFamily);
		return itemToItemDto(itemsFamily);
	}

	public FamilyDTO addFamily(Family family) {
		return this.mapper.familyToFamilyDTO(this.familyRepository.save(family));
		
	}

	public List<Item> filterItemByFamily(List<Item> listItem, String codeFamily){
	return	listItem.stream().filter(item -> item.getFamily().getCode().equals(codeFamily))     
        .collect(Collectors.toList()); 
	}
	
	public List<ShoppingList> filterShoppingListByFamily(List<ShoppingList> listShoppingList, String codeFamily){
		return	listShoppingList.stream().filter(shl -> shl.getFamily().getCode().equals(codeFamily))     
	        .collect(Collectors.toList()); 
		}

	public List<ShoppingListDTO> getShoppingLists(Integer month, String codeFamily) {
		List<ShoppingList> lists = shoppingListRepository.findByCurrentDate(month);
		
		List<ShoppingList> shoppingListFamily =this.filterShoppingListByFamily(lists,codeFamily);
		
		List<ShoppingListDTO> listDto = new ArrayList<>();
		for (ShoppingList shl : shoppingListFamily) {
			listDto.add(mapper.shoppingListToShoppingListDTO(shl));
		}
		return listDto;
	}

	public FamilyDTO findFamily(String code, String pwd) {
		Family family=this.familyRepository.findByCodeAndPwd(code,pwd);
		return this.mapper.familyToFamilyDTO(family);
	}
}

package com.comoressoft.mybudget.service;

import java.math.BigDecimal;
import java.text.DateFormatSymbols;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.comoressoft.mybudget.dto.CategoryDTO;
import com.comoressoft.mybudget.dto.SubCategoryDTO;
import com.comoressoft.mybudget.dto.SummaryDTO;
import com.comoressoft.mybudget.dto.TotalSummaryDTO;
import com.comoressoft.mybudget.model.Category;
import com.comoressoft.mybudget.model.Item;
import com.comoressoft.mybudget.model.SubCategory;
import com.comoressoft.mybudget.repository.CategoryRepository;
import com.comoressoft.mybudget.repository.ItemRepository;
import com.comoressoft.mybudget.repository.SubCategoryRepository;

@Service
public class BudgetServiceImpl {
	@Autowired
	private CategoryRepository categoryRepository;
	@Autowired
	private ItemRepository itemRepository;
	@Autowired
	private SubCategoryRepository subCategoryRepository;

	public List<CategoryDTO> getCategories(Integer month) {
		List<CategoryDTO> categories = new ArrayList<>();

		List<Category> listCat = categoryRepository.findAll();
		if (month != null && month != 0) {
			categories = this.getCategoriesByMonth(month);
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

	private void categoryToCategoryDTO(List<Category> listCat, List<CategoryDTO> categories) {
		CategoryDTO catDto = null;
		SubCategoryDTO subCatDto = null;
		for (Category cat : listCat) {
			catDto = new CategoryDTO();
			catDto.setCatId(cat.getId());
			catDto.setCatLabel(cat.getCategoryLabel());
			catDto.setCatState(cat.getCategoryState());

			this.pareparCatDto(cat, catDto, subCatDto);
			categories.add(catDto);
		}

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

	private BigDecimal calculatItemsTotalCost(int month) {
		List<Item> items = getItemsByMonth(month);
		BigDecimal sum = new BigDecimal(0);
		for (Item item : items) {
			BigDecimal param = getAsDecimal(String.valueOf(item.getExpectedQuantity()))
					.multiply(item.getExpectedAmount());
			sum = sum.add(param);
		}
		return sum;
	}

	private BigDecimal calculatRevTotalCost(int month, String revLabel) {
		BigDecimal sum = new BigDecimal(0);
		List<CategoryDTO> cats = getCategoriesByMonth(month);
		for (CategoryDTO cat : cats) {
			if (cat != null && cat.getCatLabel().equalsIgnoreCase(revLabel)) {
				if (cat.getCatTotalCost() != null) {
					sum = sum.add(cat.getCatTotalCost());
				}
			}
		}

		return sum;
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
			BigDecimal dep = calculatItemsTotalCost(month);
			BigDecimal rev = calculatRevTotalCost(month, "Revenus");
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
		LocalDate start = LocalDate.of(Calendar.getInstance().get(Calendar.YEAR), month, 1);
		LocalDate end = LocalDate.of(Calendar.getInstance().get(Calendar.YEAR), month, start.lengthOfMonth());
		return itemRepository.findByDateItemLtAndGt(start, end);
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

	public List<CategoryDTO> getCategoriesByMonth(int month) {
		List<CategoryDTO> categories = new LinkedList<>();
		List<Category> listCat = getCategoriesEntityByMonth(month);

		CategoryDTO catDto = null;
		SubCategoryDTO subCatDto = null;
		for (Category cat : listCat) {
			catDto = new CategoryDTO();
			catDto.setCatId(cat.getId());
			catDto.setCatLabel(cat.getCategoryLabel());
			catDto.setCatState(cat.getCategoryState());

			this.parepare(month, cat, catDto, subCatDto);
			//this.pareparCatDto(cat, catDto, subCatDto);
			categories.add(catDto);
		}

		return categories;
	}

	private void pareparCatDto(Category cat, CategoryDTO catDto, SubCategoryDTO subCatDto) {
		List<SubCategoryDTO> subCategories = new LinkedList<>();

		BigDecimal catTotalCost = new BigDecimal(0);
		for (SubCategory scat : cat.getSubCategory()) {
			subCatDto = new SubCategoryDTO();
			subCatDto.setSubCatId(scat.getId());
			subCatDto.setSubCatLabel(scat.getSubCategoryLabel());
			subCatDto.setSubCatState(scat.getSubCategoryState());

			BigDecimal subCatTotalCost = new BigDecimal(0);
			subCatTotalCost = subCatTotalCost.add(calculatSubCatTotalCost(scat));
			subCatDto.setSubCatTotalCost(subCatTotalCost);
			catTotalCost = catTotalCost.add(subCatTotalCost);
			subCategories.add(subCatDto);
		}
		catDto.setCatTotalCost(catTotalCost);
		catDto.setSubCategories(subCategories);

	}

	private void parepare(int month, Category cat, CategoryDTO catDto, SubCategoryDTO subCatDto) {
		List<SubCategoryDTO> subCategories = new LinkedList<>();
		BigDecimal catTotalCost = new BigDecimal(0);

		for (SubCategory scat : cat.getSubCategory()) {
		List<Item> items=new ArrayList<>();
		items.addAll(scat.getItem());
			scat.getItem().clear();
			for (Item item : items) {
				if (item.getDateItem().getMonthValue() == month) {
					scat.getItem().add(item);

				}
			}
			if (!scat.getItem().isEmpty()) {

				subCatDto = new SubCategoryDTO();
				subCatDto.setSubCatId(scat.getId());
				subCatDto.setSubCatLabel(scat.getSubCategoryLabel());
				subCatDto.setSubCatState(scat.getSubCategoryState());

				BigDecimal subCatTotalCost = new BigDecimal(0);
				subCatTotalCost = subCatTotalCost.add(calculatSubCatTotalCost(scat));
				subCatDto.setSubCatTotalCost(subCatTotalCost);
				catTotalCost = catTotalCost.add(subCatTotalCost);
				subCategories.add(subCatDto);
			}
		}
		catDto.setCatTotalCost(catTotalCost);
		catDto.setSubCategories(subCategories);

	}

	public BigDecimal getAsDecimal(String val) {
		return new BigDecimal(val);
	}
}

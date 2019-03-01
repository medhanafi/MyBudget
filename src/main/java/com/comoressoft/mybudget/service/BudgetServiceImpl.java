package com.comoressoft.mybudget.service;

import java.text.DateFormatSymbols;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

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

	public Set<CategoryDTO> getCategories() {
		Set<CategoryDTO> categories = new HashSet<>();

		List<Category> listCat = categoryRepository.findAll();
		this.categoryToCategoryDTO(listCat, categories);
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

	public Set<Item> addManyItem(List<Item> itemsToSave) {
		return new LinkedHashSet<>(itemRepository.saveAll(itemsToSave));
	}

	public Set<Item> getItems() {
		return new LinkedHashSet<>(itemRepository.findAll());
	}

	public Set<SubCategoryDTO> getSubCategoryByCategory(Long idCat) {
		SubCategoryDTO scdto = null;
		Set<SubCategoryDTO> subCategories = new HashSet<>();

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

	private void categoryToCategoryDTO(List<Category> listCat, Set<CategoryDTO> categories) {
		CategoryDTO cdto = null;
		SubCategoryDTO scdto = null;
		for (Category cat : listCat) {
			cdto = new CategoryDTO();
			cdto.setCatId(cat.getId());
			cdto.setCatLabel(cat.getCategoryLabel());
			cdto.setCatState(cat.getCategoryState());

			Set<SubCategoryDTO> subCategories = new HashSet<>();

			for (SubCategory scat : cat.getSubCategory()) {
				scdto = new SubCategoryDTO();
				scdto.setSubCatId(scat.getId());
				scdto.setSubCatLabel(scat.getSubCategoryLabel());
				scdto.setSubCatState(scat.getSubCategoryState());
				scdto.setSubCatTotalCost(calculatSubCatTotalCost(scat));

				subCategories.add(scdto);
			}
			cdto.setCatTotalCost(calculatCatTotalCost(cat));
			cdto.setSubCategories(subCategories);
			categories.add(cdto);
		}

	}

	private Float calculatCatTotalCost(Category cat) {

		Float sum = 0F;
		for (SubCategory scat : cat.getSubCategory()) {
			sum = sum + calculatSubCatTotalCost(scat);
		}
		return sum;

	}

	private Float calculatSubCatTotalCost(SubCategory scat) {
		Float sum = 0F;
		for (Item item : scat.getItem()) {
			sum = sum + (item.getExpectedQuantity() * item.getExpectedAmount());
		}
		return sum;
	}

	private Float calculatItemsTotalCost(int month) {
		Set<Item> items = getItemsByMonth(month);
		Float sum = 0F;
		for (Item item : items) {
			sum = sum + (item.getExpectedQuantity() * item.getExpectedAmount());
		}
		return sum;
	}

	private Float calculatRevTotalCost(int month, String revLabel) {
		Float sum = 0F;
		Set<CategoryDTO> cats = getCategoriesByMonth(month);
		for (CategoryDTO cat : cats) {
			if (cat != null && cat.getCatLabel().equalsIgnoreCase(revLabel)) {
				sum = sum + cat.getCatTotalCost();
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
			Float dep = calculatItemsTotalCost(month);
			Float rev = calculatRevTotalCost(month, "Revenus");
			SummaryDTO summary = new SummaryDTO();
			summary.setMonthPosition(month);
			TotalSummaryDTO tsummary = new TotalSummaryDTO();
			tsummary.setExpense(dep);
			tsummary.setIncome(rev);
			tsummary.setBalance(rev - dep);
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

	private Set<Item> getItemsByMonth(int month) {
		LocalDate localDate = LocalDate.of(Calendar.getInstance().get(Calendar.YEAR), month,
				Calendar.getInstance().get(Calendar.DATE));
		return itemRepository.findByDateItem(localDate);
	}

	public Set<Category> getCategoriesEntityByMonth(int month) {
		Set<Item> items = getItemsByMonth(month);
		Set<SubCategory> subCategories = new HashSet<>();

		for (Item item : items) {
			Optional<SubCategory> subData = subCategoryRepository.findById(item.getSubCategory().getId());
			if (subData.isPresent()) {
				SubCategory subCategorie = subData.get();
				subCategories.add(subCategorie);
			}

		}

		Set<Category> categories = new HashSet<>();
		for (SubCategory subCat : subCategories) {
			Optional<Category> subData = categoryRepository.findById(subCat.getCategory().getId());
			if (subData.isPresent()) {
				Category categorie = subData.get();
				categories.add(categorie);
			}

		}
		Set<Category> resultCategories = new HashSet<>();
		for (Category cat : categories) {
			for (SubCategory scat : subCategories) {
				if (scat.getCategory().getId() == cat.getId()) {
					cat.getSubCategory().add(scat);
				}
			}
			resultCategories.add(cat);
		}

		return resultCategories;
	}

	public Set<SubCategoryDTO> getSubCategoriesByMonth(int month) {
		Set<Item> items = getItemsByMonth(month);
		Set<SubCategory> subCategories = new HashSet<>();

		for (Item item : items) {
			Optional<SubCategory> subData = subCategoryRepository.findById(item.getSubCategory().getId());
			if (subData.isPresent()) {
				SubCategory subCategorie = subData.get();
				subCategories.add(subCategorie);
			}

		}
		SubCategoryDTO scdto = null;
		Set<SubCategoryDTO> subCategoriesDTO = new HashSet<>();
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

	public Set<CategoryDTO> getCategoriesByMonth(int month) {
		Set<CategoryDTO> categories = new HashSet<>();
		Set<Category> listCat = getCategoriesEntityByMonth(month);

		CategoryDTO cdto = null;
		SubCategoryDTO scdto = null;
		for (Category cat : listCat) {
			cdto = new CategoryDTO();
			cdto.setCatId(cat.getId());
			cdto.setCatLabel(cat.getCategoryLabel());
			cdto.setCatState(cat.getCategoryState());
			Set<SubCategoryDTO> subCategories = new HashSet<>();

			for (SubCategory scat : cat.getSubCategory()) {
				scdto = new SubCategoryDTO();
				scdto.setSubCatId(scat.getId());
				scdto.setSubCatLabel(scat.getSubCategoryLabel());
				scdto.setSubCatState(scat.getSubCategoryState());
				scdto.setSubCatTotalCost(calculatSubCatTotalCost(scat));

				subCategories.add(scdto);
			}
			cat.setCategoryTotalCost(calculatCatTotalCost(cat));
			cdto.setSubCategories(subCategories);
			categories.add(cdto);
		}

		return categories;
	}

}

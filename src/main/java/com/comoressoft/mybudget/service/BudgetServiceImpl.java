package com.comoressoft.mybudget.service;

import java.time.LocalDate;
import java.util.Calendar;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.comoressoft.mybudget.dto.CategoryDTO;
import com.comoressoft.mybudget.dto.SubCategoryDTO;
import com.comoressoft.mybudget.dto.SummaryDTO;
import com.comoressoft.mybudget.model.Category;
import com.comoressoft.mybudget.model.Item;
import com.comoressoft.mybudget.model.SubCategory;
import com.comoressoft.mybudget.repository.CategoryRepository;
import com.comoressoft.mybudget.repository.ItemRepository;

@Service
public class BudgetServiceImpl {
	@Autowired
	private CategoryRepository categoryRepository;
	@Autowired
	private ItemRepository itemRepository;

	public Set<CategoryDTO> getCategories() {
		Set<CategoryDTO> categories = new HashSet<>();

		List<Category> listCat = categoryRepository.findAll();
		this.categoryToCategoryDTO(listCat, categories);
		return categories;
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
		// TODO Auto-generated method stub
		return null;
	}

	private Float calculatSubCatTotalCost(SubCategory scat) {
		Float sum = 0F;
		for (Item item : scat.getItem()) {
			sum = sum + (item.getExpectedQuantity() * item.getExpectedAmount());
		}
		return sum;
	}

	public Set<SummaryDTO> getSummary(int month) {
		SummaryDTO summary = new SummaryDTO();
		Set<Item> items = getItemsByMonth(month);

		return null;
	}

	private Set<Item> getItemsByMonth(int month) {
		Calendar cal = Calendar.getInstance();
		LocalDate localDate = LocalDate.of(cal.YEAR, month, cal.DATE);
		return itemRepository.findByDateItem(localDate);
	}

	public Set<CategoryDTO> getCategoriesByMonth(int month) {
		Set<CategoryDTO> categories = new HashSet<>();
		Set<Item> items = getItemsByMonth(month);
		for (Item item : items) {

		}
		List<Category> listCat = categoryRepository.findAll();

		CategoryDTO cdto = null;
		SubCategoryDTO scdto = null;
		for (Category cat : listCat) {
			cdto = new CategoryDTO();
			cdto.setCatId(cat.getId());
			cdto.setCatLabel(cat.getCategoryLabel());
			cdto.setCatState(cat.getCategoryState());
			cdto.setCatTotalCost(cat.getCategoryTotalCost());
			Set<SubCategoryDTO> subCategories = new HashSet<>();

			for (SubCategory scat : cat.getSubCategory()) {
				scdto = new SubCategoryDTO();
				scdto.setSubCatId(scat.getId());
				scdto.setSubCatLabel(scat.getSubCategoryLabel());
				scdto.setSubCatState(scat.getSubCategoryState());
				scdto.setSubCatTotalCost(scat.getSubCategoryTotalCost());

				subCategories.add(scdto);
			}
			cdto.setSubCategories(subCategories);
			categories.add(cdto);
		}

		return categories;
	}
}

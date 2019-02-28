package com.comoressoft.mybudget.service;

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

	}

	public Set<SummaryDTO> getSummary(int month) {
		SummaryDTO summary = new SummaryDTO();
		Calendar cal = Calendar.getInstance();
		cal.set(cal.YEAR, month, cal.DATE);
		Set<Item> items = itemRepository.findByDateItem(cal.getTime());

		return null;
	}

}

package com.comoressoft.mybudget.service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.comoressoft.mybudget.dto.CategoryDTO;
import com.comoressoft.mybudget.dto.SubCategoryDTO;
import com.comoressoft.mybudget.model.Category;
import com.comoressoft.mybudget.model.SubCategory;
import com.comoressoft.mybudget.repository.CategoryRepository;
import com.comoressoft.mybudget.repository.SubCategoryRepository;

@Service
public class BudgetServiceImpl {
	@Autowired
	private CategoryRepository categoryRepository;
	@Autowired
	private SubCategoryRepository subCategoryRepository;

	public Set<CategoryDTO> getCategories() {
		Set<CategoryDTO> categories = new HashSet<>();
		CategoryDTO cdto = null;
		SubCategoryDTO scdto = null;
		List<Category> listCat = categoryRepository.findAll();
		for (Category cat : listCat) {
			cdto = new CategoryDTO();
			cdto.setCatId(cat.getId());
			cdto.setCatLabel(cat.getCategoryLabel());
			cdto.setCatState(cat.getCategoryState());
			cdto.setCatTotalCost(cat.getCategoryTotalCost());
			Set<SubCategoryDTO> subCategories = new HashSet<>();
			for (SubCategory scat : cat.getSubCategory()) {
				scdto=new SubCategoryDTO();
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

package com.comoressoft.mybudget.dto;

import java.util.Set;

public class CategoryDTO {
	private Long catId;
	private String catLabel;
	private String catState;
	private Float catTotalCost;
	private Set<SubCategoryDTO> subCategories;

	public Long getCatId() {
		return catId;
	}

	public void setCatId(Long catId) {
		this.catId = catId;
	}

	public String getCatLabel() {
		return catLabel;
	}

	public void setCatLabel(String catLabel) {
		this.catLabel = catLabel;
	}

	public String getCatState() {
		return catState;
	}

	public void setCatState(String catState) {
		this.catState = catState;
	}

	public Float getCatTotalCost() {
		return catTotalCost;
	}

	public void setCatTotalCost(Float catTotalCost) {
		this.catTotalCost = catTotalCost;
	}

	public Set<SubCategoryDTO> getSubCategories() {
		return subCategories;
	}

	public void setSubCategories(Set<SubCategoryDTO> subCategories) {
		this.subCategories = subCategories;
	}

}

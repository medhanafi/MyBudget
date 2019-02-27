package com.comoressoft.mybudget.dto;

public class SubCategoryDTO {

	private int subCatId;
	private String subCatLabel;
	private String subCatState;
	private Float subCatTotalCost;
	private CategoryDTO category;

	public int getSubCatId() {
		return subCatId;
	}

	public void setSubCatId(int subCatId) {
		this.subCatId = subCatId;
	}

	public String getSubCatLabel() {
		return subCatLabel;
	}

	public void setSubCatLabel(String subCatLabel) {
		this.subCatLabel = subCatLabel;
	}

	public String getSubCatState() {
		return subCatState;
	}

	public void setSubCatState(String subCatState) {
		this.subCatState = subCatState;
	}

	public Float getSubCatTotalCost() {
		return subCatTotalCost;
	}

	public void setSubCatTotalCost(Float subCatTotalCost) {
		this.subCatTotalCost = subCatTotalCost;
	}

	public CategoryDTO getCategory() {
		return category;
	}

	public void setCategory(CategoryDTO category) {
		this.category = category;
	}

}

package com.comoressoft.mybudget.dto;

import java.math.BigDecimal;
import java.util.List;

public class CategoryDTO {
	private Long catId;
	private String catLabel;
	private String catState;
	private String catType;
	private BigDecimal catTotalCost;

	private List<SubCategoryDTO> subCategories;

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

	public BigDecimal getCatTotalCost() {
		return catTotalCost;
	}

	public void setCatTotalCost(BigDecimal catTotalCost) {
		this.catTotalCost = catTotalCost;
	}

	public List<SubCategoryDTO> getSubCategories() {
		return subCategories;
	}

	public void setSubCategories(List<SubCategoryDTO> subCategories) {
		this.subCategories = subCategories;
	}

	public String getCatType() {
		return catType;
	}

	public void setCatType(String catType) {
		this.catType = catType;
	}

}

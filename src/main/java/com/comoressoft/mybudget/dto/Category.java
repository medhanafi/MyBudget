package com.comoressoft.mybudget.dto;

import java.math.BigDecimal;
import java.util.List;

public class Category {
	private Long catId;
	private String catLabel;
	private String catState;
	private BigDecimal catTotalCost;

	private List<SubCategory> subCategories;

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

	public List<SubCategory> getSubCategories() {
		return subCategories;
	}

	public void setSubCategories(List<SubCategory> subCategories) {
		this.subCategories = subCategories;
	}

}

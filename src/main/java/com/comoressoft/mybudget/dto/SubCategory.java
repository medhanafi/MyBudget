package com.comoressoft.mybudget.dto;

import java.math.BigDecimal;

public class SubCategory {

	private Long subCatId;
	private String subCatLabel;
	private String subCatState;
	private BigDecimal subCatTotalCost;

	public Long getSubCatId() {
		return subCatId;
	}

	public void setSubCatId(Long subCatId) {
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

	public BigDecimal getSubCatTotalCost() {
		return subCatTotalCost;
	}

	public void setSubCatTotalCost(BigDecimal subCatTotalCost) {
		this.subCatTotalCost = subCatTotalCost;
	}

	


}

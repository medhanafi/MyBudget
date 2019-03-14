package com.comoressoft.mybudget.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

public class ItemDTO {
	private Long itemId;
	private String itemLabelle;
	private BigDecimal expectedAmount;
	private int expectedQuantity;
	private LocalDate dateItem;
	private String itemStatus;
	private SubCategoryDTO subCategory;

	public Long getItemId() {
		return itemId;
	}

	public void setItemId(Long itemId) {
		this.itemId = itemId;
	}

	public String getItemLabelle() {
		return itemLabelle;
	}

	public void setItemLabelle(String itemLabelle) {
		this.itemLabelle = itemLabelle;
	}

	public BigDecimal getExpectedAmount() {
		return expectedAmount;
	}

	public void setExpectedAmount(BigDecimal expectedAmount) {
		this.expectedAmount = expectedAmount;
	}

	public int getExpectedQuantity() {
		return expectedQuantity;
	}

	public void setExpectedQuantity(int expectedQuantity) {
		this.expectedQuantity = expectedQuantity;
	}

	public LocalDate getDateItem() {
		return dateItem;
	}

	public void setDateItem(LocalDate dateItem) {
		this.dateItem = dateItem;
	}

	public SubCategoryDTO getSubCategory() {
		return subCategory;
	}

	public void setSubCategory(SubCategoryDTO subCategory) {
		this.subCategory = subCategory;
	}

	public String getItemStatus() {
		return itemStatus;
	}

	public void setItemStatus(String itemStatus) {
		this.itemStatus = itemStatus;
	}

}

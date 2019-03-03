package com.comoressoft.mybudget.dto;

import java.math.BigDecimal;

public class ItemDTO {
	private int itemId;
	private String itemLabelle;
	private BigDecimal expectedAmount;
	private int expectedQuantity;
	private SubCategoryDTO subCategorie;

	public int getItemId() {
		return itemId;
	}

	public void setItemId(int itemId) {
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

	public SubCategoryDTO getSubCategorie() {
		return subCategorie;
	}

	public void setSubCategorie(SubCategoryDTO subCategorie) {
		this.subCategorie = subCategorie;
	}

}

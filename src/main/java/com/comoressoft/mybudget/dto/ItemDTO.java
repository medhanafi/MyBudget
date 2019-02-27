package com.comoressoft.mybudget.dto;

public class ItemDTO {
	private int itemId;
	private String itemLabelle;
	private String expectedAmount;
	private Float expectedQuantity;
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

	public String getExpectedAmount() {
		return expectedAmount;
	}

	public void setExpectedAmount(String expectedAmount) {
		this.expectedAmount = expectedAmount;
	}

	public Float getExpectedQuantity() {
		return expectedQuantity;
	}

	public void setExpectedQuantity(Float expectedQuantity) {
		this.expectedQuantity = expectedQuantity;
	}

	public SubCategoryDTO getSubCategorie() {
		return subCategorie;
	}

	public void setSubCategorie(SubCategoryDTO subCategorie) {
		this.subCategorie = subCategorie;
	}

}

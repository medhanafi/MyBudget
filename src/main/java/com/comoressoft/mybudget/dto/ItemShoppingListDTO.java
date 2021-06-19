package com.comoressoft.mybudget.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

public class ItemShoppingListDTO {

	private Long id;
	private BigDecimal actualAmount;
	private int actualQuantity;
	private LocalDate purchasedDate;
	private ItemDTO item;
	private ShoppingListDTO shoppingList;

	public BigDecimal getActualAmount() {
		return actualAmount;
	}

	public void setActualAmount(BigDecimal actualAmount) {
		this.actualAmount = actualAmount;
	}

	public int getActualQuantity() {
		return actualQuantity;
	}

	public void setActualQuantity(int actualQuantity) {
		this.actualQuantity = actualQuantity;
	}

	public LocalDate getPurchasedDate() {
		return purchasedDate;
	}

	public void setPurchasedDate(LocalDate purchasedDate) {
		this.purchasedDate = purchasedDate;
	}

	public ItemDTO getItem() {
		return item;
	}

	public void setItem(ItemDTO item) {
		this.item = item;
	}

	public ShoppingListDTO getShoppingList() {
		return shoppingList;
	}

	public void setShoppingList(ShoppingListDTO shoppingList) {
		this.shoppingList = shoppingList;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

}
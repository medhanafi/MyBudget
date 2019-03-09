package com.comoressoft.mybudget.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

public class ShoppingListDTO {

	private String shoppingListName;
	
	private LocalDate dateCreated;
	private BigDecimal allocatedAmount;

	public String getShoppingListName() {
		return shoppingListName;
	}

	public void setShoppingListName(String shoppingListName) {
		this.shoppingListName = shoppingListName;
	}

	public LocalDate getDateCreated() {
		return dateCreated;
	}

	public void setDateCreated(LocalDate dateCreated) {
		this.dateCreated = dateCreated;
	}

	public BigDecimal getAllocatedAmount() {
		return allocatedAmount;
	}

	public void setAllocatedAmount(BigDecimal allocatedAmount) {
		this.allocatedAmount = allocatedAmount;
	}

}

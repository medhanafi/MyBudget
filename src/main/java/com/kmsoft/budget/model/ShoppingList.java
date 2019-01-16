package com.kmsoft.budget.model;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity(name = "shoppinglist")
public class ShoppingList extends AbstractEntity<Long> {
	/**
	 * 
	 */
	private static final long serialVersionUID = 233005012846819952L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private Long id;

	@ManyToOne
	private Item item;

	@Column(nullable = false, length = 4)
	private int quantity;

	@Column(nullable = false, length = 11)
	private Float unitPrice;

	@Column(nullable = false, length = 11)
	private boolean purchased;

	@Column(nullable = false)
	private LocalDate purchasedDate;

	public Item getItem() {
		return this.item;
	}

	public void setItem(Item item) {
		this.item = item;
	}

	public int getQuantity() {
		return this.quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public Float getUnitPrice() {
		return this.unitPrice;
	}

	public void setUnitPrice(Float unitPrice) {
		this.unitPrice = unitPrice;
	}

	public boolean isPurchased() {
		return this.purchased;
	}

	public void setPurchased(boolean purchased) {
		this.purchased = purchased;
	}

	public LocalDate getPurchasedDate() {
		return this.purchasedDate;
	}

	public void setPurchasedDate(LocalDate purchasedDate) {
		this.purchasedDate = purchasedDate;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Override
	public Long primaryKey() {
		return this.id;
	}

}

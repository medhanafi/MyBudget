package com.comoressoft.mybudget.entity;

import java.math.BigDecimal;
import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;
import org.hibernate.annotations.Type;

@Entity
@Table(name = "item_shopping_list")
public class ItemShoppingList {

	@Id
	@GeneratedValue(generator = "seq_item_shopping_list", strategy = GenerationType.SEQUENCE)
	@GenericGenerator(name = "seq_item_shopping_list", strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator", parameters = {
			@Parameter(name = "sequence_name", value = "seq_item_shopping_list"), @Parameter(name = "increment_size", value = "1") })
	@Column(name = "id", nullable = false)
	private Long id;

	@Column(precision = 10, scale = 2)
	@Type(type = "big_decimal")
	private BigDecimal actualAmount;

	private int actualQuantity;

	private LocalDate purchasedDate;

	@ManyToOne
	@JoinColumn(name = "item")
	private Item item;

	@ManyToOne
	@JoinColumn(name = "shoppingList")
	private ShoppingList shoppingList;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

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

	public Item getItem() {
		return item;
	}

	public void setItem(Item item) {
		this.item = item;
	}

	public ShoppingList getShoppingList() {
		return shoppingList;
	}

	public void setShoppingList(ShoppingList shoppingList) {
		this.shoppingList = shoppingList;
	}

}

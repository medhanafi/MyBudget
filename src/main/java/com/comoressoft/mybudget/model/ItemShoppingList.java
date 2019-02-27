package com.comoressoft.mybudget.model;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "item_shopping_list")
public class ItemShoppingList {

	@Id
	@GeneratedValue(generator = "seq_item_shopping_list", strategy = GenerationType.SEQUENCE)
	@Column(name = "id", nullable = false)
	private Long id;

	@Column(nullable = true)
	private Float actualAmount;

	@Column(nullable = false)
	private int actualQuantity;

	@Column(nullable = false)
	private LocalDate purchasedDate;

	@ManyToOne
	private Item item;

	@ManyToOne
	private ShoppingList shoppingList;

}

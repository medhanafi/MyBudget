package com.comoressoft.mybudget.model;

import java.time.LocalDate;
import java.time.LocalDateTime;

//@Entity(name = "item_shopping_list")
public class ItemShoppingList {

	// @Id
	// @GeneratedValue(generator = "seq_item_shopping_list", strategy =
	// GenerationType.SEQUENCE)
	private Long id;

	private LocalDateTime dateShopping;

	private Item item;
	private int quantity;
	private Float unitPrice;
	private boolean purchased;
	private LocalDate purchasedDate;

}

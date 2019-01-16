package com.kmsoft.budget.model;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity(name = "shoppinglist")
public class ShoppingList {
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

}

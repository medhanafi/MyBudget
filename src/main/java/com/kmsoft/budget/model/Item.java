package com.kmsoft.budget.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Entity(name = "item")
public class Item {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private Long id;

	@Column(nullable = false, length = 255)
	private String labelle;

	@Column(nullable = false, length = 11)
	private Float expectedCost;

	@Column(nullable = false, length = 11)
	private Float actualCost;

	@ManyToOne
	private UsePeriod periode;

	@ManyToOne
	private SubCategory subCategory;

	@OneToMany(mappedBy = "item")
	private Set<ShoppingList> shoppinglist = new HashSet<>();
}

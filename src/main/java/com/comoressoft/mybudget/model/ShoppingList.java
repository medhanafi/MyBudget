package com.comoressoft.mybudget.model;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "shopping_list")
public class ShoppingList {

	@Id
	@GeneratedValue(generator = "seq_shopping_list", strategy = GenerationType.SEQUENCE)
	@Column(name = "id", nullable = false)
	private Long id;

	@Column(nullable = false)
	private LocalDate dateShopping;

	@Column(nullable = false)
	private Float allocatedAmount;

	// @ManyToMany(fetch = FetchType.LAZY, mappedBy = "shoppingList")
	// @JsonIgnore
	// private List<Item> items = new ArrayList<>();

	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "shoppingList", orphanRemoval = true)
	private Set<ItemShoppingList> itemShoppingList = new HashSet<>();
}

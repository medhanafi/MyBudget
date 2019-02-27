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
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "item")
public class Item {

	@Id
	@GeneratedValue(generator = "seq_item", strategy = GenerationType.SEQUENCE)
	@Column(name = "id", nullable = false)
	private Long id;

	@Column(nullable = false, length = 128)
	private String itemLabelle;

	@Column(nullable = true)
	private Float expectedAmount;

	@Column(nullable = false)
	private int expectedQuantity;

	@Column(nullable = false)
	private LocalDate dateItem;

	@ManyToOne
	private SubCategory subCategory;

	// @ManyToMany(fetch = FetchType.EAGER, cascade = { CascadeType.PERSIST,
	// CascadeType.MERGE })
	// @JoinTable(name = "item_shopping_list", joinColumns = @JoinColumn(name =
	// "item_id", foreignKey = @ForeignKey(name = "FK_item_shopping_list")),
	// inverseJoinColumns = @JoinColumn(name = "shopping_list_id", foreignKey =
	// @ForeignKey(name = "FK_unit_item")))
	// private Set<ShoppingList> shoppingList = new HashSet<>();

	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "item", orphanRemoval = true)
	private Set<ItemShoppingList> itemShoppingList = new HashSet<>();

}

package com.comoressoft.mybudget.model;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

@Entity(name = "item")
public class Item {

	@Id
	@GeneratedValue(generator = "seq_item", strategy = GenerationType.SEQUENCE)
	private Long id;

	@Column(nullable = false, length = 128)
	private String itemLabelle;

	@Column(nullable = true)
	private Float expectedAmount;

	@Column(nullable = false)
	private int expectedQuantity;

	@Column(nullable = false)
	private LocalDateTime dateItem;

	@ManyToOne
	private SubCategory subCategory;

	@ManyToMany(fetch = FetchType.EAGER, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	@JoinTable(name = "item_shoppingList", joinColumns = @JoinColumn(name = "item_id", foreignKey = @ForeignKey(name = "FK_item_shopping_list")), inverseJoinColumns = @JoinColumn(name = "shopping_list_id", foreignKey = @ForeignKey(name = "FK_unit_item")))
	private Set<ShoppingList> shoppingLists = new HashSet<>();

}

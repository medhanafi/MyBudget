package com.comoressoft.mybudget.model;

import java.math.BigDecimal;
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

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;
import org.hibernate.annotations.Type;

@Entity
@Table(name = "item")
public class Item {

	@Id
	@GeneratedValue(generator = "seq_item", strategy = GenerationType.SEQUENCE)
	@GenericGenerator(name = "seq_item", strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator", parameters = {
			@Parameter(name = "sequence_name", value = "seq_item"), @Parameter(name = "increment_size", value = "1") })
	@Column(name = "id", nullable = false)
	private Long id;

	@Column(nullable = false, length = 128)
	private String itemLabelle;

	@Column(nullable = false, precision = 8, scale = 2)
	@Type(type = "big_decimal")
	private BigDecimal expectedAmount;

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

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getItemLabelle() {
		return itemLabelle;
	}

	public void setItemLabelle(String itemLabelle) {
		this.itemLabelle = itemLabelle;
	}

	public BigDecimal getExpectedAmount() {
		return expectedAmount;
	}

	public void setExpectedAmount(BigDecimal expectedAmount) {
		this.expectedAmount = expectedAmount;
	}

	public int getExpectedQuantity() {
		return expectedQuantity;
	}

	public void setExpectedQuantity(int expectedQuantity) {
		this.expectedQuantity = expectedQuantity;
	}

	public LocalDate getDateItem() {
		return dateItem;
	}

	public void setDateItem(LocalDate dateItem) {
		this.dateItem = dateItem;
	}

	public SubCategory getSubCategory() {
		return subCategory;
	}

	public void setSubCategory(SubCategory subCategory) {
		this.subCategory = subCategory;
	}

	public Set<ItemShoppingList> getItemShoppingList() {
		return itemShoppingList;
	}

	public void setItemShoppingList(Set<ItemShoppingList> itemShoppingList) {
		this.itemShoppingList = itemShoppingList;
	}

}

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

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public LocalDate getDateShopping() {
		return dateShopping;
	}

	public void setDateShopping(LocalDate dateShopping) {
		this.dateShopping = dateShopping;
	}

	public Float getAllocatedAmount() {
		return allocatedAmount;
	}

	public void setAllocatedAmount(Float allocatedAmount) {
		this.allocatedAmount = allocatedAmount;
	}

	public Set<ItemShoppingList> getItemShoppingList() {
		return itemShoppingList;
	}

	public void setItemShoppingList(Set<ItemShoppingList> itemShoppingList) {
		this.itemShoppingList = itemShoppingList;
	}
	
	
}

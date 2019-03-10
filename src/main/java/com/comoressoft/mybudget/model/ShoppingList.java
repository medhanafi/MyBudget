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
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Type;

@Entity
@Table(name = "shopping_list")
public class ShoppingList {

	@Id
	@GeneratedValue(generator = "seq_shopping_list", strategy = GenerationType.SEQUENCE)
	@Column(name = "id", nullable = false)
	private Long id;

	@Column( length = 130)
	private String shoppingListName;
	
	@Column(nullable = false)
	private LocalDate dateCreated;

	@Column(nullable = false, precision = 10, scale = 2)
	@Type(type = "big_decimal")
	private BigDecimal allocatedAmount;

	// @ManyToMany(fetch = FetchType.LAZY, mappedBy = "shoppingList")
	// @JsonIgnore
	// private List<Item> items = new ArrayList<>();

	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "shoppingList", orphanRemoval = true)
	private Set<ItemShoppingList> itemShoppingList = new HashSet<>();

	public ShoppingList(Long idSHL) {
		this.id=idSHL;
	}

	public ShoppingList() {
		super();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}


	public String getShoppingListName() {
		return shoppingListName;
	}

	public void setShoppingListName(String shoppingListName) {
		this.shoppingListName = shoppingListName;
	}

	public void setDateCreated(LocalDate dateCreated) {
		this.dateCreated = dateCreated;
	}

	public LocalDate getDateCreated() {
		return dateCreated;
	}

	public void setDateShopping(LocalDate dateCreated) {
		this.dateCreated = dateCreated;
	}

	public BigDecimal getAllocatedAmount() {
		return allocatedAmount;
	}

	public void setAllocatedAmount(BigDecimal allocatedAmount) {
		this.allocatedAmount = allocatedAmount;
	}

	public Set<ItemShoppingList> getItemShoppingList() {
		return itemShoppingList;
	}

	public void setItemShoppingList(Set<ItemShoppingList> itemShoppingList) {
		this.itemShoppingList = itemShoppingList;
	}

}

package com.comoressoft.mybudget.entity;

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

import org.hibernate.annotations.Type;

@Entity
@Table(name = "shopping_list")
public class ShoppingListEntity {

	@Id
	@GeneratedValue(generator = "seq_shopping_list", strategy = GenerationType.SEQUENCE)
	@Column(name = "id", nullable = false)
	private Long id;

	@Column(length = 130)
	private String shoppingListName;

	@Column(nullable = false)
	private LocalDate dateCreated;

	@Column(nullable = false, precision = 10, scale = 2)
	@Type(type = "big_decimal")
	private BigDecimal allocatedAmount;
	
	@ManyToOne
	private FamilyEntity family;

	// @ManyToMany(fetch = FetchType.LAZY, mappedBy = "shoppingList")
	// @JsonIgnore
	// private List<Item> items = new ArrayList<>();

	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "shoppingList")
	private Set<ItemShoppingListEntity> itemShoppingList = new HashSet<>();

	public ShoppingListEntity(Long idSHL) {
		this.id = idSHL;
	}

	public ShoppingListEntity() {
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

	public Set<ItemShoppingListEntity> getItemShoppingList() {
		return itemShoppingList;
	}

	public void setItemShoppingList(Set<ItemShoppingListEntity> itemShoppingList) {
		this.itemShoppingList = itemShoppingList;
	}

	public FamilyEntity getFamily() {
		return family;
	}

	public void setFamily(FamilyEntity family) {
		this.family = family;
	}

}

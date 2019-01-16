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
public class Item extends AbstractEntity<Long> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -81812658402126840L;

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

	public String getLabelle() {
		return this.labelle;
	}

	public void setLabelle(String labelle) {
		this.labelle = labelle;
	}

	public Float getExpectedCost() {
		return this.expectedCost;
	}

	public void setExpectedCost(Float expectedCost) {
		this.expectedCost = expectedCost;
	}

	public Float getActualCost() {
		return this.actualCost;
	}

	public void setActualCost(Float actualCost) {
		this.actualCost = actualCost;
	}

	public UsePeriod getPeriode() {
		return this.periode;
	}

	public void setPeriode(UsePeriod periode) {
		this.periode = periode;
	}

	public SubCategory getSubCategory() {
		return this.subCategory;
	}

	public void setSubCategory(SubCategory subCategory) {
		this.subCategory = subCategory;
	}

	public Set<ShoppingList> getShoppinglist() {
		return this.shoppinglist;
	}

	public void setShoppinglist(Set<ShoppingList> shoppinglist) {
		this.shoppinglist = shoppinglist;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Override
	public Long primaryKey() {
		return this.id;
	}
}

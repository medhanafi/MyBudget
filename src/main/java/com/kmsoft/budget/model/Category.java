package com.kmsoft.budget.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity(name = "category")
public class Category extends AbstractEntity<Long> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6740473779197078157L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private Long id;

	@Column(nullable = false, length = 64)
	private String categoryLabel;

	@Column(length = 25)
	private String categoryState;

	@Column(length = 11)
	private Float totalCost;

	@OneToMany(mappedBy = "category")
	private Set<SubCategory> subCategory = new HashSet<>();

	public String getCategoryLabel() {
		return this.categoryLabel;
	}

	public void setCategoryLabel(String categoryLabel) {
		this.categoryLabel = categoryLabel;
	}

	public String getCategoryState() {
		return this.categoryState;
	}

	public void setCategoryState(String categoryState) {
		this.categoryState = categoryState;
	}

	public Float getTotalCost() {
		return this.totalCost;
	}

	public void setTotalCost(Float totalCost) {
		this.totalCost = totalCost;
	}

	public Set<SubCategory> getSubCategory() {
		return this.subCategory;
	}

	public void setSubCategory(Set<SubCategory> subCategory) {
		this.subCategory = subCategory;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Override
	public Long primaryKey() {
		return this.id;
	}

}

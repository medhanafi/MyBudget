package com.comoressoft.mybudget.model;

import java.math.BigDecimal;
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
@Table(name = "category")
public class Category {

	@Id
	@GeneratedValue(generator = "seq_category", strategy = GenerationType.SEQUENCE)
	@Column(name = "id", nullable = false)
	private Long id;

	@Column(nullable = false, length = 64)
	private String categoryLabel;

	@Column(length = 25)
	private String categoryState;

	@Column(length = 11, precision = 10, scale = 2)
	@Type(type = "big_decimal")
	private BigDecimal categoryTotalCost;

	@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, mappedBy = "category", orphanRemoval = true)
	private Set<SubCategory> subCategory = new HashSet<>();

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCategoryLabel() {
		return categoryLabel;
	}

	public void setCategoryLabel(String categoryLabel) {
		this.categoryLabel = categoryLabel;
	}

	public String getCategoryState() {
		return categoryState;
	}

	public void setCategoryState(String categoryState) {
		this.categoryState = categoryState;
	}

	public Float getCategoryTotalCost() {
		return Float.parseFloat(String.valueOf(categoryTotalCost));
	}

	public void setCategoryTotalCost(Float categoryTotalCost) {
		this.categoryTotalCost = BigDecimal.valueOf(categoryTotalCost);
	}

	public Set<SubCategory> getSubCategory() {
		return subCategory;
	}

	public void setSubCategory(Set<SubCategory> subCategory) {
		this.subCategory = subCategory;
	}

}

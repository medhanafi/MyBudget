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
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Type;

@Entity
@Table(name = "sub_category")
public class SubCategory {
	@Id
	@GeneratedValue(generator = "seq_sub_category", strategy = GenerationType.SEQUENCE)
	@Column(name = "id", nullable = false)
	private Long id;

	@Column(nullable = false, length = 64)
	private String subCategoryLabel;

	@Column(length = 25)
	private String subCategoryState;

	@Column(length = 11, nullable = false, precision = 10, scale = 2)
	@Type(type = "big_decimal")
	private BigDecimal subCategoryTotalCost;

	@ManyToOne
	private Category category;

	@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, mappedBy = "subCategory", orphanRemoval = true)
	private Set<Item> item = new HashSet<>();

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getSubCategoryLabel() {
		return subCategoryLabel;
	}

	public void setSubCategoryLabel(String subCategoryLabel) {
		this.subCategoryLabel = subCategoryLabel;
	}

	public String getSubCategoryState() {
		return subCategoryState;
	}

	public void setSubCategoryState(String subCategoryState) {
		this.subCategoryState = subCategoryState;
	}

	public Float getSubCategoryTotalCost() {
		return Float.parseFloat(String.valueOf(this.subCategoryTotalCost));
	}

	public void setSubCategoryTotalCost(Float subCategoryTotalCost) {
		this.subCategoryTotalCost = BigDecimal.valueOf(subCategoryTotalCost);
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public Set<Item> getItem() {
		return item;
	}

	public void setItem(Set<Item> item) {
		this.item = item;
	}

}

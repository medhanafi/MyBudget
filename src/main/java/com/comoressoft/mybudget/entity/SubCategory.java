package com.comoressoft.mybudget.entity;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

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

	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "subCategory")
	private List<Item> item = new ArrayList<>();

	public SubCategory(Long subCategorie) {
		this.id = subCategorie;
	}

	public SubCategory() {
	}

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

	public BigDecimal getSubCategoryTotalCost() {
		return subCategoryTotalCost;
	}

	public void setSubCategoryTotalCost(BigDecimal subCategoryTotalCost) {
		this.subCategoryTotalCost = subCategoryTotalCost;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public List<Item> getItem() {
		return item;
	}

	public void setItem(List<Item> item) {
		this.item = item;
	}

}

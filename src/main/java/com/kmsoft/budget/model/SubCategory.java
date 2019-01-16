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

@Entity(name = "subcategory")
public class SubCategory extends AbstractEntity<Long> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2303223172281706943L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private Long id;

	@Column(nullable = false, length = 128)
	private String sCategoryLabel;

	@Column(length = 25)
	private String subCategoryState;

	@Column(nullable = false, length = 11)
	private Float totalCost;

	@ManyToOne
	private Category category;

	@OneToMany(mappedBy = "subCategory")
	private Set<Item> items = new HashSet<>();

	public String getsCategoryLabel() {
		return this.sCategoryLabel;
	}

	public void setsCategoryLabel(String sCategoryLabel) {
		this.sCategoryLabel = sCategoryLabel;
	}

	public String getSubCategoryState() {
		return this.subCategoryState;
	}

	public void setSubCategoryState(String subCategoryState) {
		this.subCategoryState = subCategoryState;
	}

	public Float getTotalCost() {
		return this.totalCost;
	}

	public void setTotalCost(Float totalCost) {
		this.totalCost = totalCost;
	}

	public Category getCategory() {
		return this.category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public Set<Item> getItems() {
		return this.items;
	}

	public void setItems(Set<Item> items) {
		this.items = items;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Override
	public Long primaryKey() {
		return this.id;
	}

}

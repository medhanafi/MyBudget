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
public class SubCategory {

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

}

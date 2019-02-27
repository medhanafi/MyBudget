package com.comoressoft.mybudget.model;

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

@Entity(name = "sub_category")
public class SubCategory {
	@Id
	@GeneratedValue(generator = "seq_sub_category", strategy = GenerationType.SEQUENCE)
	private Long id;

	@Column(nullable = false, length = 64)
	private String subCategoryLabel;

	@Column(length = 25)
	private String subCategoryState;

	@Column(length = 11)
	private Float subCategoryTotalCost;

	@ManyToOne
	private Category category;

	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "subCategory", orphanRemoval = true)
	private Set<Item> item = new HashSet<>();

}

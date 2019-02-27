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
import javax.persistence.OneToMany;

@Entity(name = "category")
public class Category {

	@Id
	@GeneratedValue(generator = "seq_category", strategy = GenerationType.SEQUENCE)
	private Long id;

	@Column(nullable = false, length = 64)
	private String categoryLabel;

	@Column(length = 25)
	private String categoryState;

	@Column(length = 11)
	private Float categoryTotalCost;

	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "category", orphanRemoval = true)
	private Set<SubCategory> subCategory = new HashSet<>();

}

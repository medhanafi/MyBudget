package com.comoressoft.mybudget.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity(name = "shopping_list")
public class ShoppingList {

	@Id
	@GeneratedValue(generator = "seq_shopping_list", strategy = GenerationType.SEQUENCE)
	private Long id;

	@Column(nullable = false)
	private LocalDate dateShopping;

	@Column(nullable = false)
	private Float allocatedAmount;

	@ManyToMany(fetch = FetchType.LAZY, mappedBy = "shoppingList")
	@JsonIgnore
	private List<Item> items = new ArrayList<>();
}

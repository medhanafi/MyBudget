package com.kmsoft.budget.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity(name = "useperiod")
public class UsePeriod {
	@Id
	@Column(nullable = false, length = 8)
	private Long id;

	@Column(nullable = false, length = 4)
	private int year;

	@Column(nullable = false, length = 2)
	private int month;

	@Column(nullable = false, length = 10)
	private String monthName;

	@Column(nullable = false, length = 2)
	private int day;

	@Column(nullable = false, length = 8)
	private String dayName;

	@Column(nullable = false, length = 10)
	private String date;

	@OneToMany(mappedBy = "periode")
	private Set<Item> items = new HashSet<>();
}

package com.kmsoft.budget.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity(name = "useperiod")
public class UsePeriod extends AbstractEntity<Long> {
	/**
	 * 
	 */
	private static final long serialVersionUID = -311156747461296094L;

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

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public int getYear() {
		return this.year;
	}

	public void setYear(int year) {
		this.year = year;
	}

	public int getMonth() {
		return this.month;
	}

	public void setMonth(int month) {
		this.month = month;
	}

	public String getMonthName() {
		return this.monthName;
	}

	public void setMonthName(String monthName) {
		this.monthName = monthName;
	}

	public int getDay() {
		return this.day;
	}

	public void setDay(int day) {
		this.day = day;
	}

	public String getDayName() {
		return this.dayName;
	}

	public void setDayName(String dayName) {
		this.dayName = dayName;
	}

	public String getDate() {
		return this.date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public Set<Item> getItems() {
		return this.items;
	}

	public void setItems(Set<Item> items) {
		this.items = items;
	}

	@Override
	public Long primaryKey() {
		return this.getId();
	}
}

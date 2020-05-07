package com.comoressoft.mybudget.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
@Entity
@Table(name = "family")
public class Family implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -4361263345050484444L;
	@Id
	@GeneratedValue(generator = "seq_family", strategy = GenerationType.SEQUENCE)
	@Column(name = "id", nullable = false)
	private Long id;
	private String code;
	private String pwd;
	
	
	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "family")
	private List<User> user = new ArrayList<>();
	
	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "family")
	private List<Item> item = new ArrayList<>();
	
	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "family")
	private List<ShoppingList> shoppingList = new ArrayList<>();

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getPwd() {
		return pwd;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}

	public List<Item> getItem() {
		return item;
	}

	public void setItem(List<Item> item) {
		this.item = item;
	}

	public List<ShoppingList> getShoppingList() {
		return shoppingList;
	}

	public void setShoppingList(List<ShoppingList> shoppingList) {
		this.shoppingList = shoppingList;
	}
	
	
}

package com.comoressoft.mybudget.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.comoressoft.mybudget.model.ShoppingList;

public interface ShoppingListRepository extends JpaRepository<ShoppingList, Long> {

}

package com.comoressoft.mybudget.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.comoressoft.mybudget.model.Item;

public interface ItemRepository extends JpaRepository<Item, Long> {

}

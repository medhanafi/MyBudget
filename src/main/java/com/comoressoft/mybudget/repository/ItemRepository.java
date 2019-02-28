package com.comoressoft.mybudget.repository;

import java.util.Date;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;

import com.comoressoft.mybudget.model.Item;

public interface ItemRepository extends JpaRepository<Item, Long> {

	Set<Item> findByDateItem(Date time);

}

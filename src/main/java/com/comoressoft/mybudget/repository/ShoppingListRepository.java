package com.comoressoft.mybudget.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.comoressoft.mybudget.entity.ShoppingList;

public interface ShoppingListRepository extends JpaRepository<ShoppingList, Long> {

	@Query("SELECT s FROM #{#entityName} s WHERE extract(month from s.dateCreated)=:monthValue")
	List<ShoppingList> findByCurrentDate(@Param("monthValue") int month);


}

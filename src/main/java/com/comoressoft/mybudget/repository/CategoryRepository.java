package com.comoressoft.mybudget.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.comoressoft.mybudget.entity.Category;

public interface CategoryRepository extends JpaRepository<Category, Long> {

	@Query("SELECT i FROM #{#entityName} i WHERE categoryLabel=:item_label")
	Category findByCategoryLabel(String item_label);

}

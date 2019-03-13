package com.comoressoft.mybudget.repository;

import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;

import com.comoressoft.mybudget.entity.Category;
import com.comoressoft.mybudget.entity.SubCategory;

public interface SubCategoryRepository extends JpaRepository<SubCategory, Long> {

	Set<SubCategory> findByCategory(Category category);

}

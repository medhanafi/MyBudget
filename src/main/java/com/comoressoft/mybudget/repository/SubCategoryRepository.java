package com.comoressoft.mybudget.repository;

import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;

import com.comoressoft.mybudget.entity.CategoryEntity;
import com.comoressoft.mybudget.entity.SubCategoryEntity;

public interface SubCategoryRepository extends JpaRepository<SubCategoryEntity, Long> {

	Set<SubCategoryEntity> findByCategory(CategoryEntity category);

}

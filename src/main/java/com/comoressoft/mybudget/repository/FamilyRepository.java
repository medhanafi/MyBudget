package com.comoressoft.mybudget.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.comoressoft.mybudget.entity.Family;

public interface FamilyRepository extends JpaRepository<Family, Long> {
	
	Family findByCode(String code);

}

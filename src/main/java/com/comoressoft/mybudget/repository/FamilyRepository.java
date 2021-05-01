package com.comoressoft.mybudget.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.comoressoft.mybudget.entity.FamilyEntity;

public interface FamilyRepository extends JpaRepository<FamilyEntity, Long> {
	
	FamilyEntity findByCode(String code);
	FamilyEntity  findByCodeAndPwd(String code, String pwd);

}

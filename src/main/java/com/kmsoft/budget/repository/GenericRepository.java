package com.kmsoft.budget.repository;

import java.io.Serializable;

import com.kmsoft.budget.model.AbstractEntity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface GenericRepository<E extends AbstractEntity<PK>, PK extends Serializable> extends JpaRepository<E, PK> {

}

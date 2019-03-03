package com.comoressoft.mybudget.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.comoressoft.mybudget.model.Item;

public interface ItemRepository extends JpaRepository<Item, Long> {

	List<Item> findByDateItem(LocalDate localDate);
	@Query("SELECT i FROM #{#entityName} i WHERE i.dateItem >=:dateStart AND i.dateItem <=:dateEnd")
	List<Item> findByDateItemLtAndGt(@Param("dateStart") LocalDate dateStart, @Param("dateEnd") LocalDate dateEnd);

}

package com.comoressoft.mybudget.repository;

import java.time.LocalDate;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.comoressoft.mybudget.entity.ItemEntity;
import com.comoressoft.mybudget.entity.SubCategoryEntity;

public interface ItemRepository extends JpaRepository<ItemEntity, Long> {

	List<ItemEntity> findByDateItem(LocalDate localDate);

	// @Query("SELECT i FROM #{#entityName} i WHERE i.dateItem >=:dateStart AND
	// i.dateItem <=:dateEnd")
	// List<Item> findByDateItemLtAndGt(@Param("dateStart") LocalDate dateStart,
	// @Param("dateEnd") LocalDate dateEnd);

	@Query("SELECT i FROM #{#entityName} i WHERE extract(month from i.dateItem)=:monthValue")
	List<ItemEntity> findByDateItemLtAndGt(@Param("monthValue") int month);

	@Query("select i from #{#entityName} i where extract(month from i.dateItem)=:monthValue and subCategory.id=:subCat")
	List<ItemEntity> findByMonthAndSubCat(@Param("monthValue") int month, @Param("subCat") Long subCategorie);

	List<ItemEntity> findBySubCategory(SubCategoryEntity subCategory);

	@Modifying
	@Query("DELETE FROM #{#entityName} where id=:itemId")
	void deleteItemById(@Param("itemId") Long itemId);

}

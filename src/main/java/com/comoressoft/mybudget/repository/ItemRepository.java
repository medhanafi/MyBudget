package com.comoressoft.mybudget.repository;

import java.time.LocalDate;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.comoressoft.mybudget.entity.Item;
import com.comoressoft.mybudget.entity.SubCategory;

public interface ItemRepository extends JpaRepository<Item, Long> {

	List<Item> findByDateItem(LocalDate localDate);

	// @Query("SELECT i FROM #{#entityName} i WHERE i.dateItem >=:dateStart AND
	// i.dateItem <=:dateEnd")
	// List<Item> findByDateItemLtAndGt(@Param("dateStart") LocalDate dateStart,
	// @Param("dateEnd") LocalDate dateEnd);

	@Query("SELECT i FROM #{#entityName} i WHERE extract(month from i.dateItem)=:monthValue")
	List<Item> findByDateItemLtAndGt(@Param("monthValue") int month);

	@Query("select i from #{#entityName} i where extract(month from i.dateItem)=:monthValue and subCategory.id=:subCat")
	List<Item> findByMonthAndSubCat(@Param("monthValue") int month, @Param("subCat") Long subCategorie);

	List<Item> findBySubCategory(SubCategory subCategory);

	@Modifying
	@Query("DELETE FROM #{#entityName} where id=:itemId")
	void deleteItemById(@Param("itemId") Long itemId);

	
	@Query("select i from #{#entityName} i where extract(month from i.dateItem)=:monthValue and subCategory.subCategoryLabel=:item_label")
	List<Item> findByMonthAndSubCat(Integer monthValue, String item_label);

	

}

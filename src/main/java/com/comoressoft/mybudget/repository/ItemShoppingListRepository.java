package com.comoressoft.mybudget.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.comoressoft.mybudget.entity.ItemEntity;
import com.comoressoft.mybudget.entity.ItemShoppingListEntity;
import com.comoressoft.mybudget.entity.ShoppingListEntity;

public interface ItemShoppingListRepository extends JpaRepository<ItemShoppingListEntity, Long> {

	List<ItemShoppingListEntity> findByShoppingList(ShoppingListEntity shl);

	ItemShoppingListEntity findByShoppingListAndItem(ShoppingListEntity shl, ItemEntity item);

	List<ItemShoppingListEntity>  findByItem(ItemEntity item);

}

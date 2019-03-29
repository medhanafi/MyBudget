package com.comoressoft.mybudget.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.comoressoft.mybudget.entity.Item;
import com.comoressoft.mybudget.entity.ItemShoppingList;
import com.comoressoft.mybudget.entity.ShoppingList;

public interface ItemShoppingListRepository extends JpaRepository<ItemShoppingList, Long> {

	List<ItemShoppingList> findByShoppingList(ShoppingList shl);

	ItemShoppingList findByShoppingListAndItem(ShoppingList shl, Item item);

	List<ItemShoppingList>  findByItem(Item item);

}

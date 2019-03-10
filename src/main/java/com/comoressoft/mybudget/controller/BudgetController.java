package com.comoressoft.mybudget.controller;

import java.util.List;

import org.hibernate.service.spi.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.comoressoft.mybudget.dto.CategoryDTO;
import com.comoressoft.mybudget.dto.ItemDTO;
import com.comoressoft.mybudget.dto.ItemShoppingListDTO;
import com.comoressoft.mybudget.dto.ShoppingListDTO;
import com.comoressoft.mybudget.dto.SubCategoryDTO;
import com.comoressoft.mybudget.model.ShoppingList;
import com.comoressoft.mybudget.service.BudgetServiceImpl;

@RestController
@RequestMapping("/api")
public class BudgetController {

	@Autowired
	private BudgetServiceImpl budgetService;

	@GetMapping(value = "/categories")
	ResponseEntity<?> getCateegories(@RequestParam(value = "month", required = false) Integer month)
			throws ServiceException {

		List<CategoryDTO> result = this.budgetService.getCategories(month);
		return this.getResponseWithStatus(result);
	}

	@GetMapping(value = "/items")
	ResponseEntity<?> getitems(@RequestParam(value = "month", required = false) Integer month) throws ServiceException {

		List<ItemDTO> result = this.budgetService.getItems(month);
		return this.getResponseWithStatus(result);
	}

	@GetMapping(value = "/itemsBySubcat")
	ResponseEntity<?> getitemsBySubCat(@RequestParam(value = "subcat_id", required = false) Long subcatId,
			@RequestParam(value = "month", required = false) Integer month) throws ServiceException {

		List<ItemDTO> result = this.budgetService.getItemsBySubCat(subcatId, month);
		return this.getResponseWithStatus(result);
	}

	@GetMapping(value = "/subcatsBycate")
	ResponseEntity<?> getSubCatByCat(@RequestParam(value = "cat_id", required = false) Long catId,
			@RequestParam(value = "month", required = false) Integer month) throws ServiceException {

		List<SubCategoryDTO> result = this.budgetService.getSubCategoryByCategory(catId, month);
		return this.getResponseWithStatus(result);
	}

	@PostMapping(value = "/additem")
	ResponseEntity<?> addItem(@RequestBody ItemDTO itemDto) throws ServiceException {

		ItemDTO result = this.budgetService.addItem(itemDto);
		return this.getResponseWithStatus(result);
	}

	@PostMapping(value = "/addShoppingList")
	ResponseEntity<?> createShoppingList(@RequestBody ShoppingList shop) throws ServiceException {

		ShoppingListDTO result = this.budgetService.addShoppingLists(shop);
		return this.getResponseWithStatus(result);
	}

	@PostMapping(value = "/addItemToShoppingList")
	ResponseEntity<?> addItemShoppingList(@RequestParam(value = "itemId") Long itemId,
			@RequestParam(value = "idSHL") Long idSHL) throws ServiceException {

		ItemShoppingListDTO result = this.budgetService.addItemToShoppingList(itemId, idSHL);
		return this.getResponseWithStatus(result);
	}
	
	@GetMapping(value = "itemShoppingList")
	ResponseEntity<?> getItemShoppingList(@RequestParam(value = "idSHL") Long idSHL) throws ServiceException {

		List<ItemShoppingListDTO> result = this.budgetService.getItemShoppingList(idSHL);
		return this.getResponseWithStatus(result);
	}
	
	@GetMapping(value = "/shoppingLists")
	ResponseEntity<?> getShoppingLists(@RequestParam(value = "month", required = false) Integer month) throws ServiceException {

		List<ShoppingListDTO> result = this.budgetService.getShoppingLists(month);
		return this.getResponseWithStatus(result);
	}

	@GetMapping(value = { "/summary/{month}", "/summary/", "/summary", "/summary{month}" })
	ResponseEntity<?> getSummary(@PathVariable(value = "month", required = false) Integer month)
			throws ServiceException {

		if (month != null) {
			return this.getResponseWithStatus(this.budgetService.getSummary(month));
		} else {
			return this.getResponseWithStatus(this.budgetService.getSummary());
		}
	}

	private <T> ResponseEntity<?> getResponseWithStatus(T result) {
		if (result == null) {
			return new ResponseEntity<T>(result, HttpStatus.NO_CONTENT);
		} else {
			return new ResponseEntity<T>(result, HttpStatus.OK);
		}
	}

}

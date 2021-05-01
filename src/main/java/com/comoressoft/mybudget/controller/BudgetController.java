package com.comoressoft.mybudget.controller;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.service.spi.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.trace.http.HttpTrace.Principal;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.comoressoft.mybudget.dto.Category;
import com.comoressoft.mybudget.dto.Family;
import com.comoressoft.mybudget.dto.Item;
import com.comoressoft.mybudget.dto.ItemShoppingList;
import com.comoressoft.mybudget.dto.ShoppingList;
import com.comoressoft.mybudget.dto.SubCategory;
import com.comoressoft.mybudget.entity.FamilyEntity;
import com.comoressoft.mybudget.entity.ShoppingListEntity;
import com.comoressoft.mybudget.service.BudgetServiceImpl;

@RestController
@RequestMapping("/api")
public class BudgetController {

	@Autowired
	private BudgetServiceImpl budgetService;

	@GetMapping(value = "/categories")
	ResponseEntity<?> getCateegories(@RequestParam(value = "month", required = false) Integer month)
			throws ServiceException {

		List<Category> result = this.budgetService.getCategories(month);
		return this.getResponseWithStatus(result);
	}

	@GetMapping(value = "/items")
	ResponseEntity<?> getitems(@RequestParam(value = "month", required = false) Integer month, Principal principal)
			throws ServiceException {
		String codeFamily = budgetService.getCodeFamily();

		List<Item> result = new ArrayList<>();
		if (codeFamily != null && !codeFamily.isEmpty()) {
			result = this.budgetService.getItems(month, codeFamily);
		} else {
			result = this.budgetService.getItems(month);
		}
		return this.getResponseWithStatus(result);
	}

	@GetMapping(value = "/itemsBySubcat")
	ResponseEntity<?> getitemsBySubCat(@RequestParam(value = "subcat_id", required = false) Long subcatId,
			@RequestParam(value = "month", required = false) Integer month) throws ServiceException {
		String codeFamily = budgetService.getCodeFamily();

		List<Item> result = new ArrayList<>();
		if (codeFamily != null && !codeFamily.isEmpty()) {
			result = this.budgetService.getItemsBySubCat(subcatId, month, codeFamily);
		} else {
			result = this.budgetService.getItemsBySubCat(subcatId, month);
		}
		return this.getResponseWithStatus(result);
	}

	@GetMapping(value = "/subcatsBycate")
	ResponseEntity<?> getSubCatByCat(@RequestParam(value = "cat_id", required = false) Long catId,
			@RequestParam(value = "month", required = false) Integer month) throws ServiceException {

		List<SubCategory> result = this.budgetService.getSubCategoryByCategory(catId, month);
		return this.getResponseWithStatus(result);
	}

	@PostMapping(value = "/addcategory")
	ResponseEntity<?> addCategory(@RequestBody Category categoryDto) throws ServiceException {

		Category result = this.budgetService.addCategory(categoryDto);
		return this.getResponseWithStatus(result);
	}

	@PostMapping(value = "/addsubcategory")
	ResponseEntity<?> addSubCategory(@RequestBody SubCategory subCategoryDto) throws ServiceException {

		SubCategory result = this.budgetService.addSubCategory(subCategoryDto);
		return this.getResponseWithStatus(result);
	}

	@PutMapping(value = "/deletecategory")
	ResponseEntity<?> delCategory(@RequestParam(value = "cat_id") Long catId) throws ServiceException {

		this.budgetService.deleteCategory(catId);
		return new ResponseEntity<String>("{\r\n" + "        \"error\":0,\r\n" + "    }", HttpStatus.OK);
	}

	@PutMapping(value = "/deletesubcategory")
	ResponseEntity<?> delSubCategory(@RequestParam(value = "sub_cat_id") Long subCatId) throws ServiceException {

		this.budgetService.deleteSubCategory(subCatId);
		return new ResponseEntity<String>("{\r\n" + "        \"error\":0,\r\n" + "    }", HttpStatus.OK);
	}

	@PutMapping(value = "/deleteitem")
	ResponseEntity<?> delItem(@RequestParam(value = "item_id") Long itemId) throws ServiceException {

		this.budgetService.deleteItem(itemId);
		return new ResponseEntity<String>("{\r\n" + "        \"error\":0,\r\n" + "    }", HttpStatus.OK);
	}

	@PostMapping(value = "/additem")
	ResponseEntity<?> addItem(@RequestBody Item itemDto) throws ServiceException {

		Item result = this.budgetService.addItem(itemDto);
		return this.getResponseWithStatus(result);
	}

	@PostMapping(value = "/addShoppingList")
	ResponseEntity<?> createShoppingList(@RequestBody ShoppingListEntity shop) throws ServiceException {

		ShoppingList result = this.budgetService.addShoppingLists(shop);
		return this.getResponseWithStatus(result);
	}

	@PostMapping(value = "/addItemToShoppingList")
	ResponseEntity<?> addItemShoppingList(@RequestParam(value = "itemId") Long itemId,
			@RequestParam(value = "idSHL") Long idSHL) throws ServiceException {

		ItemShoppingList result = this.budgetService.addItemToShoppingList(itemId, idSHL);
		return this.getResponseWithStatus(result);
	}

	@PostMapping(value = "/updateItemShoppingList")
	ResponseEntity<?> updateItemShoppingList(@RequestBody ItemShoppingList shop) throws ServiceException {

		ItemShoppingList result = this.budgetService.updateItemShoppingList(shop);
		return this.getResponseWithStatus(result);
	}

	@GetMapping(value = "itemShoppingList")
	ResponseEntity<?> getItemShoppingList(@RequestParam(value = "idSHL") Long idSHL) throws ServiceException {

		List<ItemShoppingList> result = this.budgetService.getItemShoppingList(idSHL);
		return this.getResponseWithStatus(result);
	}

	@GetMapping(value = "/shoppingLists")
	ResponseEntity<?> getShoppingLists(@RequestParam(value = "month", required = false) Integer month,
			@RequestParam(value = "code_family") String codeFamily) throws ServiceException {
		List<ShoppingList> result = new ArrayList<>();
		if (codeFamily != null && !codeFamily.isEmpty()) {
			result = this.budgetService.getShoppingLists(month, codeFamily);
		} else {
			result = this.budgetService.getShoppingLists(month);
		}
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

	@GetMapping(value = "/preloaditems")
	ResponseEntity<?> preloaditems(@RequestParam(value = "month", required = false) Integer month)
			throws ServiceException {

		List<Item> result = this.budgetService.preloadItems(month);
		return this.getResponseWithStatus(result);
	}

	private <T> ResponseEntity<?> getResponseWithStatus(T result) {
		if (result == null) {
			return new ResponseEntity<T>(result, HttpStatus.NO_CONTENT);
		} else {
			return new ResponseEntity<T>(result, HttpStatus.OK);
		}
	}

	@PostMapping(value = "/addfamily")
	ResponseEntity<?> addFamily(@RequestBody FamilyEntity family) throws ServiceException {

		Family result = this.budgetService.addFamily(family);
		return this.getResponseWithStatus(result);
	}

}

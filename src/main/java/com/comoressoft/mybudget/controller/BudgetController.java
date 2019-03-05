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
import com.comoressoft.mybudget.dto.SubCategoryDTO;
import com.comoressoft.mybudget.model.Item;
import com.comoressoft.mybudget.model.SubCategory;
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
	ResponseEntity<?> getitemsBySubCat(@RequestParam(value = "subcat_id", required = false) Long subcatId)
			throws ServiceException {

		List<ItemDTO> result = this.budgetService.getItemsBySubCat(subcatId);
		return this.getResponseWithStatus(result);
	}

	@GetMapping(value = "/subcatsBycate")
	ResponseEntity<?> getSubCatByCat(@RequestParam(value = "cat_id", required = false) Long catId)
			throws ServiceException {

		List<SubCategoryDTO> result = this.budgetService.getSubCategoryByCategory(catId);
		return this.getResponseWithStatus(result);
	}

	@PostMapping(value = "/additem")
	ResponseEntity<?> addItem(@RequestBody ItemDTO itemDto) throws ServiceException {

		Item item = this.getItemDto(itemDto);

		ItemDTO result = this.getItemDto(this.budgetService.addItem(item));
		return this.getResponseWithStatus(result);
	}

	private ItemDTO getItemDto(Item item) {
		ItemDTO itemDto = new ItemDTO();
		itemDto.setItemId(item.getId());
		itemDto.setItemLabelle(item.getItemLabelle());
		itemDto.setExpectedAmount(item.getExpectedAmount());
		itemDto.setExpectedQuantity(item.getExpectedQuantity());
		itemDto.setSubCategorie(item.getSubCategory().getId());
		itemDto.setDateItem(item.getDateItem());
		return itemDto;
	}

	private Item getItemDto(ItemDTO itemDto) {
		Item item = new Item();
		item.setId(itemDto.getItemId());
		item.setItemLabelle(itemDto.getItemLabelle());
		item.setExpectedAmount(itemDto.getExpectedAmount());
		item.setExpectedQuantity(itemDto.getExpectedQuantity());
		item.setSubCategory(new SubCategory(itemDto.getSubCategorie()));
		item.setDateItem(itemDto.getDateItem());
		return item;
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

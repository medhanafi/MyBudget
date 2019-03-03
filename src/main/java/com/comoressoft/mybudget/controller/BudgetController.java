package com.comoressoft.mybudget.controller;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;

import org.hibernate.service.spi.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.comoressoft.mybudget.dto.CategoryDTO;
import com.comoressoft.mybudget.model.Item;
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

	@PostMapping(value = "/additem")
	ResponseEntity<?> addItem(@RequestParam(value = "item_labelle", required = true) String itemLabelle,
			@RequestParam(value = "expected_amount", required = true) String expectedAmount,
			@RequestParam(value = "expected_quantity", required = true) String expectedQuantity,
			@RequestParam(value = "date_item", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDate dateItem)
			throws ServiceException {

		Item item = new Item();
		item.setItemLabelle(itemLabelle);
		item.setExpectedAmount(new BigDecimal(expectedAmount));
		item.setExpectedQuantity(Integer.parseInt(expectedQuantity));
		item.setDateItem(dateItem);

		Item result = this.budgetService.addItem(item);
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

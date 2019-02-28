package com.comoressoft.mybudget.controller;

import java.util.Set;

import org.hibernate.service.spi.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.comoressoft.mybudget.dto.CategoryDTO;
import com.comoressoft.mybudget.dto.SummaryDTO;
import com.comoressoft.mybudget.service.BudgetServiceImpl;

@RestController
@RequestMapping("/api")
public class BudgetController {

	@Autowired
	private BudgetServiceImpl ewsService;

	@GetMapping(value = "/categories/{month}")
	ResponseEntity<?> getCateegories(@PathVariable(value = "month", required = false) String month)
			throws ServiceException {

		Set<CategoryDTO> result = this.ewsService.getCategories();
		return this.getResponseWithStatus(result);
	}

	@GetMapping(value = "/summary{month}")
	ResponseEntity<?> getSummary(@PathVariable(value = "month", required = true) int month) throws ServiceException {

		Set<SummaryDTO> result = this.ewsService.getSummary(month);
		return this.getResponseWithStatus(result);
	}

	private ResponseEntity<?> getResponseWithStatus(Set<?> result) {
		if (result.isEmpty()) {
			return new ResponseEntity<Set<?>>(result, HttpStatus.NO_CONTENT);
		} else {
			return new ResponseEntity<Set<?>>(result, HttpStatus.OK);
		}
	}

}

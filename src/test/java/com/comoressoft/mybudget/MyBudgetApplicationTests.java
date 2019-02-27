package com.comoressoft.mybudget;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.comoressoft.mybudget.service.BudgetServiceImpl;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MyBudgetApplicationTests {

	@Autowired
	BudgetServiceImpl test;
	
	@Test
	public void contextLoads() {
		test.getCategories();
	}

}

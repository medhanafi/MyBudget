package com.kmsoft.budget.repository;

import com.kmsoft.budget.Application;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = Application.class)
class CategoryRepositoryTest {

	@Autowired
	CategoryRepository categoryRepository;

	@BeforeEach
	void setUp() throws Exception {
	}

	@AfterEach
	void tearDown() throws Exception {
	}

	@Test
	public void greetShouldRetunAValidOutput() {
		System.out.println("greetShouldRetunAValidOutput");
		boolean result = categoryRepository.existsById(1L);
		Assertions.assertTrue(result == false);
	}

	@Test
	void testSave() {

	}

}

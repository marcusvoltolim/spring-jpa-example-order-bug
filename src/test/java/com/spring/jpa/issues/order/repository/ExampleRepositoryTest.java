package com.spring.jpa.issues.order.repository;

import com.spring.jpa.issues.order.model.Example;
import com.spring.jpa.issues.order.model.QExample;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ActiveProfiles("test")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@DataJpaTest
class ExampleRepositoryTest {

	@Autowired
	private ExampleRepository repository;

	private static final Example exampleOne = new Example("exampleOne", LocalDate.of(2020, 9, 18), LocalDate.of(2020, 9, 18).atStartOfDay());

	private static final Example exampleTwo = new Example("exampleTwo", LocalDate.of(2020, 10, 17), LocalDate.of(2020, 10, 17).atStartOfDay());

	@BeforeAll
	void beforeAll() {
		repository.save(exampleOne);
		repository.save(exampleTwo);
	}

	//OK
	@Test
	void findAllJpaRepositoryOrderByDateProperty() {
		List<Example> all = repository.findAll(getSort("localDate"));
		assertEquals(exampleOne, all.get(0));
		assertEquals(exampleTwo, all.get(1));
	}

	//OK
	@Test
	void findAllQueryDslOrderByStringProperty() {
		List<Example> all = (List<Example>) repository.findAll(QExample.example.isNotNull(), getSort("name"));
		assertEquals(exampleOne, all.get(0));
		assertEquals(exampleTwo, all.get(1));
	}

	//BUG
	@Test
	void findAllQueryDslOrderByNumericProperty() {
		List<Example> all = (List<Example>) repository.findAll(QExample.example.isNotNull(), getSort("id"));
		assertEquals(exampleOne, all.get(0));
		assertEquals(exampleTwo, all.get(1));
	}

	//BUG
	@Test
	void findAllQueryDslOrderByDateProperty() {
		List<Example> all = (List<Example>) repository.findAll(QExample.example.isNotNull(), getSort("localDate"));
		assertEquals(exampleOne, all.get(0));
		assertEquals(exampleTwo, all.get(1));
	}


	private static Sort getSort(String property) {
		return Sort.by(Sort.Order.asc(property).ignoreCase());
	}

}

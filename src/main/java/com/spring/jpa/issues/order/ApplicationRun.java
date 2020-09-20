package com.spring.jpa.issues.order;

import com.spring.jpa.issues.order.model.Example;
import com.spring.jpa.issues.order.model.QExample;
import com.spring.jpa.issues.order.repository.ExampleRepository;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Profile("!test")
@Component
public class ApplicationRun implements ApplicationRunner {

	@Autowired
	private ExampleRepository repository;

	@Override
	public void run(ApplicationArguments args) throws Exception {
		var exampleOne = new Example("exampleOne", LocalDate.of(2020, 9, 18), LocalDate.of(2020, 9, 18).atStartOfDay());
		var exampleTwo = new Example("exampleTwo", LocalDate.of(2020, 10, 17), LocalDate.of(2020, 10, 17).atStartOfDay());

		repository.save(exampleOne);
		repository.save(exampleTwo);

		repository.findAll(Sort.by(Sort.Order.asc("localDate").ignoreCase()));
		try {
			repository.findAll(QExample.example.isNotNull(), Sort.by(Sort.Order.asc("localDate").ignoreCase()));
			repository.findAll(QExample.example.isNotNull(), Sort.by(Sort.Order.asc("id").ignoreCase()));
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}finally {
			repository.deleteAll();
		}
	}

}

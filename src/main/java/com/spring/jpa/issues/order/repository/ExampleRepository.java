package com.spring.jpa.issues.order.repository;

import com.spring.jpa.issues.order.model.Example;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface ExampleRepository extends JpaRepository<Example, Long>, QuerydslPredicateExecutor<Example> {}

package com.spring.jpa.issues.order.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;

import org.springframework.data.jpa.domain.AbstractPersistable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Example extends AbstractPersistable<Long> {

	@Column
	private String name;

	@Column
	private LocalDate localDate;

	@Column
	private LocalDateTime localDateTime;


}

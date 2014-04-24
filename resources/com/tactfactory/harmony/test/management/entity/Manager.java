package com.tactfactory.harmony.test.management.entity;

import com.tactfactory.harmony.annotation.Column;
import com.tactfactory.harmony.annotation.Entity;

@Entity
public class Manager extends Person {

	@Column(nullable=true)
	private int salary;
}

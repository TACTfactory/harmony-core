package com.tactfactory.harmony.test.management.entity;

import com.tactfactory.harmony.annotation.Column;
import com.tactfactory.harmony.annotation.Entity;
import com.tactfactory.harmony.annotation.Id;
import com.tactfactory.harmony.annotation.OneToOne;

@Entity
public class Office {

	@Id
	private int floor;
	
	@Id
	private String name;
	
	@OneToOne
	@Column(nullable=true)
	private Day cleaningDay;
	
	@OneToOne
	@Column(nullable=true)
	private Furniture furniture;
}

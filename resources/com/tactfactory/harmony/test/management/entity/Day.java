package com.tactfactory.harmony.test.management.entity;

import com.tactfactory.harmony.annotation.Column;
import com.tactfactory.harmony.annotation.Entity;
import com.tactfactory.harmony.annotation.Id;
import com.tactfactory.harmony.annotation.OneToOne;

@Entity
public class Day {

	@Id
	private int identifier;

	@Column
	private int day;

	@Column
	private int month;

	@Column
	private int year;
	
	@Column
	private boolean publicHoliday;
	
	@OneToOne
	@Column(nullable=true)
	private Office officeToClean;
}

package com.tactfactory.harmony.test.management.entity;

import java.util.ArrayList;

import com.tactfactory.harmony.annotation.Column;
import com.tactfactory.harmony.annotation.Column.Type;
import com.tactfactory.harmony.annotation.ColumnResult;
import com.tactfactory.harmony.annotation.Entity;
import com.tactfactory.harmony.annotation.Id;
import com.tactfactory.harmony.annotation.InheritanceType;
import com.tactfactory.harmony.annotation.ManyToMany;
import com.tactfactory.harmony.annotation.ManyToOne;
import com.tactfactory.harmony.annotation.InheritanceType.InheritanceMode;

@Entity
@InheritanceType(InheritanceMode.SINGLE_TABLE)
public class Person {

	@Id
	private String firstname;
	
	@Id
	private String lastname;
	
	@ColumnResult(columnName="firstname || ' ' || lastname")
	private String name;
	
	@Column(type=Type.PHONE)
	private String phoneNumber;
	
	@ManyToOne(inversedBy="employees")
	private Company company;
	
	@ManyToMany(inversedBy="inhabitants", mappedBy="inhabitants")
	private ArrayList<Address> addresses;
	
	@ManyToMany
	private ArrayList<Day> holidays;
}

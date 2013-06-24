package com.tactfactory.harmony.test.demact.entity;

import java.util.ArrayList;

import com.tactfactory.harmony.annotation.Column;
import com.tactfactory.harmony.annotation.Entity;
import com.tactfactory.harmony.annotation.Id;
import com.tactfactory.harmony.annotation.OneToMany;

@Entity
public class Category {

	@Id
	@Column
	private int id;
	
	@Column
	private String name;
	
	@OneToMany(mappedBy="category")
	private ArrayList<CategoryToComment> comments;
}

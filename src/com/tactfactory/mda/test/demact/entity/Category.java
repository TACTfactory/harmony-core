package com.tactfactory.mda.test.demact.entity;

import java.util.ArrayList;

import com.tactfactory.mda.annotation.Column;
import com.tactfactory.mda.annotation.Entity;
import com.tactfactory.mda.annotation.Id;
import com.tactfactory.mda.annotation.OneToMany;

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

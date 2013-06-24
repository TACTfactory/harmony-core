package com.tactfactory.harmony.test.demact.entity;

import com.tactfactory.harmony.annotation.Column;
import com.tactfactory.harmony.annotation.Entity;
import com.tactfactory.harmony.annotation.Id;
import com.tactfactory.harmony.annotation.ManyToOne;

@Entity
public class CategoryToComment {

	@Id
	@Column
	private int id;

	@Column
	private String displayName;
	
	@ManyToOne(inversedBy="comments")
	private Category category;
}

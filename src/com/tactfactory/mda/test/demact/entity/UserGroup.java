package com.tactfactory.mda.test.demact.entity;

import com.tactfactory.mda.annotation.Column;
import com.tactfactory.mda.annotation.Entity;
import com.tactfactory.mda.annotation.Id;

@Entity
public class UserGroup {

	@Id
	@Column
	private int id;
	
	@Column
	private String name;
	
	@Column
	private boolean writePermission;
	
	@Column
	private boolean deletePermission;
	
	
}

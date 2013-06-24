package com.tactfactory.harmony.test.demact.entity;

import com.tactfactory.harmony.annotation.Column;
import com.tactfactory.harmony.annotation.Entity;
import com.tactfactory.harmony.annotation.Id;

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

package com.tactfactory.harmony.test.management.entity;

import com.tactfactory.harmony.annotation.Column;
import com.tactfactory.harmony.annotation.Entity;
import com.tactfactory.harmony.annotation.Column.Type;

@Entity
public class Projector extends Furniture {

	@Column
	private int lensSize;
	
	@Column(type=Type.ENUM)
	private Lamp lampType;
	
	public enum Lamp {
		LCD_BULB,
		LCD_LAMP;
	}
}

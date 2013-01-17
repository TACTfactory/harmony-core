package com.tactfactory.mda.test.demact.entity;

import java.io.Serializable;

import org.joda.time.DateTime;

import com.tactfactory.mda.annotation.Column;
import com.tactfactory.mda.annotation.Entity;
import com.tactfactory.mda.annotation.Column.Type;
import com.tactfactory.mda.annotation.Id;

@Entity
public class ViewComponent implements Serializable {
	private static final long serialVersionUID = -6623985483853173832L;
	
	@Column(type=Type.STRING)
	protected String string;
	
	@Column(type=Type.TEXT)
	protected String text;

	@Column(type=Type.DATETIME)
	protected DateTime dateTime;
	
	@Column(type=Type.DATE)
	protected DateTime date;
	
	@Column(type=Type.TIME)
	protected DateTime time;
	
	@Column(type=Type.LOGIN)
	protected String login;
	
	@Column(type=Type.PASSWORD)
	protected String password;
	
	@Column(type=Type.EMAIL)
	protected String email;
	
	@Column(type=Type.PHONE)
	protected String phone;
	
	@Column(type=Type.CITY)
	protected String city;
	
	@Column(type=Type.ZIPCODE)
	protected int zipCode;
	
	@Column(type=Type.COUNTRY)
	protected String country;
	
}

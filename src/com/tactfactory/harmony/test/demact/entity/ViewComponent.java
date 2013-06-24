/**
 * This file is part of the Harmony package.
 *
 * (c) Mickael Gaillard <mickael.gaillard@tactfactory.com>
 *
 * For the full copyright and license information, please view the LICENSE
 * file that was distributed with this source code.
 */
package com.tactfactory.harmony.test.demact.entity;

import java.io.Serializable;

import org.joda.time.DateTime;

import com.tactfactory.harmony.annotation.Column;
import com.tactfactory.harmony.annotation.Entity;
import com.tactfactory.harmony.annotation.GeneratedValue;
import com.tactfactory.harmony.annotation.Id;
import com.tactfactory.harmony.annotation.Column.Type;

@Entity
public class ViewComponent implements Serializable {
	private static final long serialVersionUID = -6623985483853173832L;
	
	@Id
    @Column(type = Type.INTEGER, hidden = true)
    @GeneratedValue(strategy = "IDENTITY")
	private int id;
	
	@Column(type = Type.STRING)
	private String string;
	
	@Column(type = Type.TEXT)
	private String text;

	@Column(type = Type.DATETIME)
	private DateTime dateTime;
	
	@Column(type = Type.DATE)
	private DateTime date;
	
	@Column(type = Type.TIME)
	private DateTime time;
	
	@Column(type = Type.LOGIN)
	private String login;
	
	@Column(type = Type.PASSWORD)
	private String password;
	
	@Column(type = Type.EMAIL)
	private String email;
	
	@Column(type = Type.PHONE)
	private String phone;
	
	@Column(type = Type.CITY)
	private String city;
	
	@Column(type = Type.ZIPCODE)
	private int zipCode;
	
	@Column(type = Type.COUNTRY)
	private String country;
	
}

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

/** Test Application entity. */
@Entity
public class ViewComponent implements Serializable {
	/** Serial UID. */
	private static final long serialVersionUID = -6623985483853173832L;
	
	/** Entity's technical id. */
	@Id
    @Column(type = Type.INTEGER, hidden = true)
    @GeneratedValue(strategy = "IDENTITY")
	private int id;
	
	/** String type. */
	@Column(type = Type.STRING)
	private String string;
	
	/** Text type. */
	@Column(type = Type.TEXT)
	private String text;

	/** DateTime type. */
	@Column(type = Type.DATETIME)
	private DateTime dateTime;
	
	/** Date type. */
	@Column(type = Type.DATE)
	private DateTime date;
	
	/** Time type. */
	@Column(type = Type.TIME)
	private DateTime time;
	
	/** Login type. */
	@Column(type = Type.LOGIN)
	private String login;
	
	/** Password type. */
	@Column(type = Type.PASSWORD)
	private String password;
	
	/** EMail type. */
	@Column(type = Type.EMAIL)
	private String email;
	
	/** Phone type. */
	@Column(type = Type.PHONE)
	private String phone;
	
	/** city type. */
	@Column(type = Type.CITY)
	private String city;
	
	/** Zipcode type. */
	@Column(type = Type.ZIPCODE)
	private int zipCode;
	
	/** Country type. */
	@Column(type = Type.COUNTRY)
	private String country;
	
	/**
	 * @return the id
	 */
	public int getId() {
	     return this.id;
	}


	/**
	 * @param value the id to set
	 */
	public void setId(final int value) {
	     this.id = value;
	}

	/**
	 * @return the string
	 */
	public String getString() {
	     return this.string;
	}


	/**
	 * @param value the string to set
	 */
	public void setString(final String value) {
	     this.string = value;
	}

	/**
	 * @return the text
	 */
	public String getText() {
	     return this.text;
	}


	/**
	 * @param value the text to set
	 */
	public void setText(final String value) {
	     this.text = value;
	}

	/**
	 * @return the dateTime
	 */
	public DateTime getDateTime() {
	     return this.dateTime;
	}


	/**
	 * @param value the dateTime to set
	 */
	public void setDateTime(final DateTime value) {
	     this.dateTime = value;
	}

	/**
	 * @return the date
	 */
	public DateTime getDate() {
	     return this.date;
	}


	/**
	 * @param value the date to set
	 */
	public void setDate(final DateTime value) {
	     this.date = value;
	}

	/**
	 * @return the time
	 */
	public DateTime getTime() {
	     return this.time;
	}


	/**
	 * @param value the time to set
	 */
	public void setTime(final DateTime value) {
	     this.time = value;
	}

	/**
	 * @return the login
	 */
	public String getLogin() {
	     return this.login;
	}


	/**
	 * @param value the login to set
	 */
	public void setLogin(final String value) {
	     this.login = value;
	}

	/**
	 * @return the password
	 */
	public String getPassword() {
	     return this.password;
	}


	/**
	 * @param value the password to set
	 */
	public void setPassword(final String value) {
	     this.password = value;
	}

	/**
	 * @return the email
	 */
	public String getEmail() {
	     return this.email;
	}


	/**
	 * @param value the email to set
	 */
	public void setEmail(final String value) {
	     this.email = value;
	}

	/**
	 * @return the phone
	 */
	public String getPhone() {
	     return this.phone;
	}


	/**
	 * @param value the phone to set
	 */
	public void setPhone(final String value) {
	     this.phone = value;
	}

	/**
	 * @return the city
	 */
	public String getCity() {
	     return this.city;
	}


	/**
	 * @param value the city to set
	 */
	public void setCity(final String value) {
	     this.city = value;
	}

	/**
	 * @return the zipCode
	 */
	public int getZipCode() {
	     return this.zipCode;
	}


	/**
	 * @param value the zipCode to set
	 */
	public void setZipCode(final int value) {
	     this.zipCode = value;
	}

	/**
	 * @return the country
	 */
	public String getCountry() {
	     return this.country;
	}


	/**
	 * @param value the country to set
	 */
	public void setCountry(final String value) {
	     this.country = value;
	}

}

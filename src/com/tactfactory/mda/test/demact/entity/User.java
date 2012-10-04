package com.tactfactory.mda.test.demact.entity;

import java.io.Serializable;
import java.util.Date;

import com.tactfactory.mda.orm.annotation.*;
import com.tactfactory.mda.orm.annotation.Column.Type;

@Table
@Entity
public class User implements Serializable {
	@Id
    @Column()					// type="integer",
    @GeneratedValue(strategy="IDENTITY")
    protected int id;

	@Column(unique=true)		// type="string", length=255
    protected String login;
	
	@Column(type=Type.PASSWORD)	// type="string", length=255
    protected String password;
	
	@Column()					// type="string", length=255
    protected String firstname;
	
	@Column()					// type="string", length=255
    protected String lastname;
	
	@Column(name="created_at")	// type="datetime",
    protected Date createdAt;


	public User() {
		this.id = -1;
    	this.createdAt = new Date();
    }
	
	/**
	 * @return the id
	 */
	public final int getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public final void setId(int id) {
		this.id = id;
	}

	/**
	 * @return the login
	 */
	public final String getLogin() {
		return login;
	}

	/**
	 * @param login the login to set
	 */
	public final void setLogin(String login) {
		this.login = login;
	}

	/**
	 * @return the password
	 */
	public final String getPassword() {
		return password;
	}

	/**
	 * @param password the password to set
	 */
	public final void setPassword(String password) {
		this.password = password;
	}

	/**
	 * @return the firstname
	 */
	public final String getFirstname() {
		return firstname;
	}

	/**
	 * @param firstname the firstname to set
	 */
	public final void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	/**
	 * @return the lastname
	 */
	public final String getLastname() {
		return lastname;
	}

	/**
	 * @param lastname the lastname to set
	 */
	public final void setLastname(String lastname) {
		this.lastname = lastname;
	}

	/**
	 * @return the createdAt
	 */
	public final Date getCreatedAt() {
		return createdAt;
	}

	/**
	 * @param createdAt the createdAt to set
	 */
	public final void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}
}

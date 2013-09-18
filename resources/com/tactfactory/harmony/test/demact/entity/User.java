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
import com.tactfactory.harmony.annotation.ManyToOne;
import com.tactfactory.harmony.annotation.Table;
import com.tactfactory.harmony.annotation.Column.Type;

/** Test Application entity. */
//All annotation with forced value/parameter
@Table(name = "local_user")
@Entity
public class User extends Object implements Cloneable, Serializable {
	/** Serial UID. */
	private static final long serialVersionUID = 7032873279928549706L;

	/** Entity's technical id. */
	@Id
    @Column(type = Type.INTEGER, hidden = true)
    @GeneratedValue(strategy = "IDENTITY")
    private int id;

	/** Login. */
	@Column(type = Type.LOGIN)
    private String login;

	/** Password. */
	@Column(type = Type.PASSWORD)
    private String password;

	/** First name. */
	@Column(nullable = true)
    private String firstname;

	/** Last name. */
	@Column()
    private String lastname;

	/** Created at.. */
	@Column(name = "created_at")
    private DateTime createdAt;

	/** Birthdate. */
	@Column(type = Type.DATE, locale = true)
    private DateTime birthdate;

	/** Group this user belong to. */
	@ManyToOne
	private UserGroup userGroup;

	@Column(type = Type.ENUM)
	private Title title;

	/**
	 * Constructor.
	 */
	public User() {
		this.id = -1;
    	this.createdAt = new DateTime();
    }

	@Override
	public final User clone() throws CloneNotSupportedException {
		final User u = (User) super.clone();
		u.id = this.id;
		u.login = this.login;
		u.password = this.password;
		u.firstname = this.firstname;
		u.lastname = this.lastname;
		u.createdAt = new DateTime(this.createdAt);
		u.birthdate = new DateTime(this.birthdate);

		return u;
	}

	/**
	 * @return the id
	 */
	public final int getId() {
		return this.id;
	}

	/**
	 * @param id the id to set
	 */
	public final void setId(final int id) {
		this.id = id;
	}

	/**
	 * @return the login
	 */
	public final String getLogin() {
		return this.login;
	}

	/**
	 * @param login the login to set
	 */
	public final void setLogin(final String login) {
		this.login = login;
	}

	/**
	 * @return the password
	 */
	public final String getPassword() {
		return this.password;
	}

	/**
	 * @param password the password to set
	 */
	public final void setPassword(final String password) {
		this.password = password;
	}

	/**
	 * @return the firstname
	 */
	public final String getFirstname() {
		return this.firstname;
	}

	/**
	 * @param firstname the firstname to set
	 */
	public final void setFirstname(final String firstname) {
		this.firstname = firstname;
	}

	/**
	 * @return the lastname
	 */
	public final String getLastname() {
		return this.lastname;
	}

	/**
	 * @param lastname the lastname to set
	 */
	public final void setLastname(final String lastname) {
		this.lastname = lastname;
	}

	/**
	 * @return the createdAt
	 */
	public final DateTime getCreatedAt() {
		return this.createdAt;
	}

	/**
	 * @param createdAt the createdAt to set
	 */
	public final void setCreatedAt(final DateTime createdAt) {
		this.createdAt = createdAt;
	}

	/**
	 * @return the birthdate
	 */
	public final DateTime getBirthdate() {
		return this.birthdate;
	}

	/**
	 * @param birthdate the birthdate to set
	 */
	public final void setBirthdate(final DateTime birthdate) {
		this.birthdate = birthdate;
	}
}

/**
 * This file is part of the Harmony package.
 *
 * (c) Mickael Gaillard <mickael.gaillard@tactfactory.com>
 *
 * For the full copyright and license information, please view the LICENSE
 * file that was distributed with this source code.
 */
package com.tactfactory.mda.test.demact.entity;

import org.joda.time.DateTime;

import com.tactfactory.mda.annotation.*;
import com.tactfactory.mda.annotation.Column.Type;
import com.tactfactory.mda.rest.annotation.Rest;
import com.tactfactory.mda.sync.annotation.Sync;
import com.tactfactory.mda.sync.annotation.Sync.Mode;

//All annotation with forced value/parameter
@Table(name="local_user")
@Entity
@Rest(security=Rest.Security.SESSION, uri="user-uri")
@Sync(mode=Mode.REAL_TIME)
public class User extends Object implements Cloneable {
	@SuppressWarnings("unused")
	private static final long serialVersionUID = 7032873279928549706L;

	@Id
    @Column(type=Type.INTEGER, hidden=true)
    @GeneratedValue(strategy="IDENTITY")
    protected int id;

	@Column(type=Type.LOGIN)
    protected String login;
	
	@Column(type=Type.PASSWORD)
    protected String password;
	
	@Column(nullable=true)
    protected String firstname;
	
	@Column()
    protected String lastname;
	
	@Column(name="created_at")
    protected DateTime createdAt;


	public User() {
		this.id = -1;
    	this.createdAt = new DateTime();
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
	public final DateTime getCreatedAt() {
		return createdAt;
	}

	/**
	 * @param createdAt the createdAt to set
	 */
	public final void setCreatedAt(DateTime createdAt) {
		this.createdAt = createdAt;
	}
}

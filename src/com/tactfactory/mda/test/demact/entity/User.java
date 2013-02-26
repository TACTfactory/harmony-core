/**
 * This file is part of the Harmony package.
 *
 * (c) Mickael Gaillard <mickael.gaillard@tactfactory.com>
 *
 * For the full copyright and license information, please view the LICENSE
 * file that was distributed with this source code.
 */
package com.tactfactory.mda.test.demact.entity;

import java.io.Serializable;

import org.joda.time.DateTime;

import com.tactfactory.mda.annotation.Column;
import com.tactfactory.mda.annotation.Column.Type;
import com.tactfactory.mda.annotation.Entity;
import com.tactfactory.mda.annotation.GeneratedValue;
import com.tactfactory.mda.annotation.Id;
import com.tactfactory.mda.annotation.Table;
import com.tactfactory.mda.bundles.rest.annotation.Rest;
import com.tactfactory.mda.bundles.sync.annotation.Sync;
import com.tactfactory.mda.bundles.sync.annotation.Sync.Mode;

//All annotation with forced value/parameter
@Table(name = "local_user")
@Entity
@Rest(security = Rest.Security.SESSION, uri = "user-uri")
@Sync(mode = Mode.REAL_TIME)
public class User extends Object implements Cloneable, Serializable {
	private static final long serialVersionUID = 7032873279928549706L;

	@Id
    @Column(type = Type.INTEGER, hidden = true)
    @GeneratedValue(strategy = "IDENTITY")
    private int id;

	@Column(type = Type.LOGIN)
    private String login;
	
	@Column(type = Type.PASSWORD)
    private String password;
	
	@Column(nullable = true)
    private String firstname;
	
	@Column()
    private String lastname;
	
	@Column(name = "created_at")
    private DateTime createdAt;
	
	@Column(type = Type.DATE, locale = true)
    private DateTime birthdate;


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

package com.tactfactory.mda.test.jobeet.entity;

import java.util.ArrayList;
import java.util.Date;

import com.tactfactory.mda.annotation.Column;
import com.tactfactory.mda.annotation.Entity;
import com.tactfactory.mda.annotation.GeneratedValue;
import com.tactfactory.mda.annotation.Id;
import com.tactfactory.mda.annotation.ManyToMany;
import com.tactfactory.mda.annotation.Table;

@Table
@Entity
public class Affiliate {
	@Id
    @Column()					// type="integer",
    @GeneratedValue(strategy="IDENTITY")
    protected int id;
	
	@Column(nullable=true)		// type="string", length=255 
    protected String url;
	
	@Column()					// type="string", length="255"
    protected String email;
	
	@Column(unique=true)		// type="string", length="255", 
    protected String token;
	
	@Column(name="is_activated")// type="boolean", 
    protected boolean isActivated;
	
	@Column(name="created_at")	// type="datetime",
    protected Date createdAt;
	
	@ManyToMany()
	protected ArrayList<Category> categories;

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
	 * @return the url
	 */
	public final String getUrl() {
		return this.url;
	}

	/**
	 * @param url the url to set
	 */
	public final void setUrl(final String url) {
		this.url = url;
	}

	/**
	 * @return the email
	 */
	public final String getEmail() {
		return this.email;
	}

	/**
	 * @param email the email to set
	 */
	public final void setEmail(final String email) {
		this.email = email;
	}

	/**
	 * @return the token
	 */
	public final String getToken() {
		return this.token;
	}

	/**
	 * @param token the token to set
	 */
	public final void setToken(final String token) {
		this.token = token;
	}

	/**
	 * @return the isActivated
	 */
	public final boolean isActivated() {
		return this.isActivated;
	}

	/**
	 * @param isActivated the isActivated to set
	 */
	public final void setActivated(final boolean isActivated) {
		this.isActivated = isActivated;
	}

	/**
	 * @return the createdAt
	 */
	public final Date getCreatedAt() {
		return new Date(this.createdAt.getTime());
	}

	/**
	 * @param createdAt the createdAt to set
	 */
	public final void setCreatedAt(final Date createdAt) {
		this.createdAt = new Date(createdAt.getTime());
	}

	/**
	 * @return the categories
	 */
	public final ArrayList<Category> getCategories() {
		return this.categories;
	}

	/**
	 * @param categories the categories to set
	 */
	public final void setCategories(final ArrayList<Category> categories) {
		this.categories = categories;
	}

	public Affiliate() {
		this.createdAt = new Date();
	}
}

package com.tactfactory.mda.test.jobeet.entity;

import java.util.ArrayList;
import java.util.Date;

import com.tactfactory.mda.orm.annotation.*;

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
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public final void setId(int id) {
		this.id = id;
	}

	/**
	 * @return the url
	 */
	public final String getUrl() {
		return url;
	}

	/**
	 * @param url the url to set
	 */
	public final void setUrl(String url) {
		this.url = url;
	}

	/**
	 * @return the email
	 */
	public final String getEmail() {
		return email;
	}

	/**
	 * @param email the email to set
	 */
	public final void setEmail(String email) {
		this.email = email;
	}

	/**
	 * @return the token
	 */
	public final String getToken() {
		return token;
	}

	/**
	 * @param token the token to set
	 */
	public final void setToken(String token) {
		this.token = token;
	}

	/**
	 * @return the isActivated
	 */
	public final boolean isActivated() {
		return isActivated;
	}

	/**
	 * @param isActivated the isActivated to set
	 */
	public final void setActivated(boolean isActivated) {
		this.isActivated = isActivated;
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

	/**
	 * @return the categories
	 */
	public final ArrayList<Category> getCategories() {
		return categories;
	}

	/**
	 * @param categories the categories to set
	 */
	public final void setCategories(ArrayList<Category> categories) {
		this.categories = categories;
	}

	public Affiliate() {
		this.createdAt = new Date();
	}
}

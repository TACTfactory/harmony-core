package com.tactfactory.harmony.test.jobeet.entity;

import java.util.ArrayList;
import java.util.Date;

import com.tactfactory.harmony.annotation.Column;
import com.tactfactory.harmony.annotation.Entity;
import com.tactfactory.harmony.annotation.GeneratedValue;
import com.tactfactory.harmony.annotation.Id;
import com.tactfactory.harmony.annotation.ManyToMany;
import com.tactfactory.harmony.annotation.Table;

@Table
@Entity
public class Affiliate {
	@Id
    @Column()					// typ ="integer",
    @GeneratedValue(strategy = "IDENTITY")
	private int id;
	
	@Column(nullable = true)		// typ ="string", lengt =255 
	private String url;
	
	@Column()					// typ ="string", lengt ="255"
	private String email;
	
	@Column(unique = true)		// typ ="string", lengt ="255", 
	private String token;
	
	@Column(name = "is_activated")// typ ="boolean", 
	private boolean isActivated;
	
	@Column(name = "created_at")	// typ ="datetime",
	private Date createdAt;
	
	@ManyToMany()
	private ArrayList<Category> categories;

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

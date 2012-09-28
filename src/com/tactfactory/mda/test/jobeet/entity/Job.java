package com.tactfactory.mda.test.jobeet.entity;

import java.util.Date;
import com.tactfactory.mda.orm.annotation.*;

@Table
@Entity
public class Job {
    @Id
    @Column()					// type="integer",
    @GeneratedValue(strategy="IDENTITY")
    protected int id;

    @ManyToOne() 				// targetEntity="Category"
    @JoinColumn(name="category_id", referencedColumnName="id")
    protected Category category;

    @Column()					// type="string", length=255
    protected String type;

    @Column(nullable=true)		// type="string", length=255
    protected String company;

    @Column()					// type="string", length=255
    protected String logo;

    @Column(nullable=true)		// type="string", length=255 
    protected String url;

    @Column(nullable=true)		// type="string", length=255
    protected String position;

    @Column()					// type="string", length=255
    protected String location;

    @Column(length=4000)		// type="string",
    protected String description;

    @Column(length=4000, name="how_to_apply")	// type="string", 
    protected String howToApply;

    @Column(unique=true)		// type="string", length="255", 
    protected String token;

    @Column(name="is_public")	// type="boolean", 
    protected boolean isPublic;

    @Column(name="is_activated")// type="boolean", 
    protected boolean isActivated;

    @Column()					// type="string", length="255"
    protected String email;

    @Column(name="created_at")	// type="datetime",
    protected Date createdAt;

    @Column(name="updated_at")	// type="datetime", 
    protected Date updatedAt;

    @Column(name="expires_at")	// type="datetime", 
    protected Date expiresAt;

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
	 * @return the category
	 */
	public final Category getCategory() {
		return category;
	}

	/**
	 * @param category the category to set
	 */
	public final void setCategory(Category category) {
		this.category = category;
	}

	/**
	 * @return the type
	 */
	public final String getType() {
		return type;
	}

	/**
	 * @param type the type to set
	 */
	public final void setType(String type) {
		this.type = type;
	}

	/**
	 * @return the company
	 */
	public final String getCompany() {
		return company;
	}

	/**
	 * @param company the company to set
	 */
	public final void setCompany(String company) {
		this.company = company;
	}

	/**
	 * @return the logo
	 */
	public final String getLogo() {
		return logo;
	}

	/**
	 * @param logo the logo to set
	 */
	public final void setLogo(String logo) {
		this.logo = logo;
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
	 * @return the position
	 */
	public final String getPosition() {
		return position;
	}

	/**
	 * @param position the position to set
	 */
	public final void setPosition(String position) {
		this.position = position;
	}

	/**
	 * @return the location
	 */
	public final String getLocation() {
		return location;
	}

	/**
	 * @param location the location to set
	 */
	public final void setLocation(String location) {
		this.location = location;
	}

	/**
	 * @return the description
	 */
	public final String getDescription() {
		return description;
	}

	/**
	 * @param description the description to set
	 */
	public final void setDescription(String description) {
		this.description = description;
	}

	/**
	 * @return the howToApply
	 */
	public final String getHowToApply() {
		return howToApply;
	}

	/**
	 * @param howToApply the howToApply to set
	 */
	public final void setHowToApply(String howToApply) {
		this.howToApply = howToApply;
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
	 * @return the isPublic
	 */
	public final boolean isPublic() {
		return isPublic;
	}

	/**
	 * @param isPublic the isPublic to set
	 */
	public final void setPublic(boolean isPublic) {
		this.isPublic = isPublic;
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
	 * @return the updatedAt
	 */
	public final Date getUpdatedAt() {
		return updatedAt;
	}

	/**
	 * @param updatedAt the updatedAt to set
	 */
	public final void setUpdatedAt(Date updatedAt) {
		this.updatedAt = updatedAt;
	}

	/**
	 * @return the expiresAt
	 */
	public final Date getExpiresAt() {
		return expiresAt;
	}

	/**
	 * @param expiresAt the expiresAt to set
	 */
	public final void setExpiresAt(Date expiresAt) {
		this.expiresAt = expiresAt;
	}

	public Job() {
    	this.createdAt = new Date();
    	this.updatedAt = new Date();
    }
}

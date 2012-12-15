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
import java.util.ArrayList;
import java.util.Date;

import org.joda.time.DateTime;

import com.tactfactory.mda.orm.annotation.*;
import com.tactfactory.mda.orm.annotation.Column.Type;

@Table
@Entity
public class Post implements Serializable {
	private static final long serialVersionUID = -6549779793416923128L;

	@Id
    @Column(type="integer")			// type="integer",
    @GeneratedValue(strategy="IDENTITY")
    protected int id;
	
	@Column(unique=true)	// type="string", length=255
    protected String title;
	
	@Column(length=40000, type="String")	// type="string"
    protected String content;
	
	@ManyToOne
	protected User owner;
	
	@OneToMany
	protected ArrayList<Comment> comments;
	
	@Column(name="created_at")	// type="datetime",
    protected Date createdAt;

    @Column(name="updated_at")	// type="datetime", 
    protected Date updatedAt;

    @Column(name="expires_at")	// type="datetime", 
    protected Date expiresAt;


	public Post() {
    	this.createdAt = new DateTime().toDate();
    	this.updatedAt = new DateTime().toDate();
    	this.expiresAt = new DateTime().toDate();
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
	 * @return the title
	 */
	public final String getTitle() {
		return title;
	}

	/**
	 * @param title the title to set
	 */
	public final void setTitle(String title) {
		this.title = title;
	}

	/**
	 * @return the content
	 */
	public final String getContent() {
		return content;
	}

	/**
	 * @param content the content to set
	 */
	public final void setContent(String content) {
		this.content = content;
	}

	/**
	 * @return the owner
	 */
	public final User getOwner() {
		return owner;
	}

	/**
	 * @param owner the owner to set
	 */
	public final void setOwner(User owner) {
		this.owner = owner;
	}

	/**
	 * @return the comments
	 */
	public final ArrayList<Comment> getComments() {
		return comments;
	}

	/**
	 * @param comments the comments to set
	 */
	public final void setComments(ArrayList<Comment> comments) {
		this.comments = comments;
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
}

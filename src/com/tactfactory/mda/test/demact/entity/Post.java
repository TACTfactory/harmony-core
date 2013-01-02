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

import org.joda.time.DateTime;

import com.tactfactory.mda.orm.annotation.*;
import com.tactfactory.mda.orm.annotation.Column.Type;

@Table
@Entity
public class Post implements Serializable {
	private static final long serialVersionUID = -6549779793416923128L;

	@Id
    @Column(type=Type.INTEGER, hidden=true)
    @GeneratedValue(strategy="IDENTITY")
    protected int id;
	
	@Column(unique=true)	// type="string", length=255
    protected String title;
	
	@Column(length=40000, type=Type.STRING)	// type="string"
    protected String content;
	
	@Column
	protected String categories;
	
	@ManyToOne
	protected User owner;
	
	@OneToMany
	protected ArrayList<Comment> comments;
	
	@Column(name="created_at")
    protected DateTime createdAt;

    @Column(name="updated_at")
    protected DateTime updatedAt;

    @Column(name="expires_at")
    protected DateTime expiresAt;


	public Post() {
    	this.createdAt = new DateTime();
    	this.updatedAt = new DateTime();
    	this.expiresAt = new DateTime();
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
	public final DateTime getCreatedAt() {
		return createdAt;
	}

	/**
	 * @param createdAt the createdAt to set
	 */
	public final void setCreatedAt(DateTime createdAt) {
		this.createdAt = createdAt;
	}

	/**
	 * @return the updatedAt
	 */
	public final DateTime getUpdatedAt() {
		return updatedAt;
	}

	/**
	 * @param updatedAt the updatedAt to set
	 */
	public final void setUpdatedAt(DateTime updatedAt) {
		this.updatedAt = updatedAt;
	}

	/**
	 * @return the expiresAt
	 */
	public final DateTime getExpiresAt() {
		return expiresAt;
	}

	/**
	 * @param expiresAt the expiresAt to set
	 */
	public final void setExpiresAt(DateTime expiresAt) {
		this.expiresAt = expiresAt;
	}
}

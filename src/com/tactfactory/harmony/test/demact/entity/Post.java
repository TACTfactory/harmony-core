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
import java.util.ArrayList;

import org.joda.time.DateTime;

import com.tactfactory.harmony.annotation.Column;
import com.tactfactory.harmony.annotation.Entity;
import com.tactfactory.harmony.annotation.GeneratedValue;
import com.tactfactory.harmony.annotation.Id;
import com.tactfactory.harmony.annotation.ManyToOne;
import com.tactfactory.harmony.annotation.OneToMany;
import com.tactfactory.harmony.annotation.Table;
import com.tactfactory.harmony.annotation.Column.Type;

// All annotation with default value/parameter
@Table
@Entity
public class Post implements Serializable {
	private static final long serialVersionUID = -6549779793416923128L;

	@Id
    @Column(type = Type.INTEGER, hidden = true)
    @GeneratedValue(strategy = "IDENTITY")
    private int id;
	
	@Column(unique = true, length = 140)
    private String title;
	
	@Column(length = 40000, type = Type.TEXT)
    private String content;
	
	@Column
	private String categories;
	
	@ManyToOne
	private User owner;
	
	@OneToMany(mappedBy = "post")
	private ArrayList<Comment> comments;
	
	@Column(name = "created_at")
    private DateTime createdAt;

    @Column(name = "updated_at")
    private DateTime updatedAt;

    @Column(name = "expires_at")
    private DateTime expiresAt;


	public Post() {
    	this.createdAt = new DateTime();
    	this.updatedAt = new DateTime();
    	this.expiresAt = new DateTime();
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
	 * @return the title
	 */
	public final String getTitle() {
		return this.title;
	}

	/**
	 * @param title the title to set
	 */
	public final void setTitle(final String title) {
		this.title = title;
	}

	/**
	 * @return the content
	 */
	public final String getContent() {
		return this.content;
	}

	/**
	 * @param content the content to set
	 */
	public final void setContent(final String content) {
		this.content = content;
	}

	/**
	 * @return the owner
	 */
	public final User getOwner() {
		return this.owner;
	}

	/**
	 * @param owner the owner to set
	 */
	public final void setOwner(final User owner) {
		this.owner = owner;
	}

	/**
	 * @return the comments
	 */
	public final ArrayList<Comment> getComments() {
		return this.comments;
	}

	/**
	 * @param comments the comments to set
	 */
	public final void setComments(final ArrayList<Comment> comments) {
		this.comments = comments;
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
	 * @return the updatedAt
	 */
	public final DateTime getUpdatedAt() {
		return this.updatedAt;
	}

	/**
	 * @param updatedAt the updatedAt to set
	 */
	public final void setUpdatedAt(final DateTime updatedAt) {
		this.updatedAt = updatedAt;
	}

	/**
	 * @return the expiresAt
	 */
	public final DateTime getExpiresAt() {
		return this.expiresAt;
	}

	/**
	 * @param expiresAt the expiresAt to set
	 */
	public final void setExpiresAt(final DateTime expiresAt) {
		this.expiresAt = expiresAt;
	}
}

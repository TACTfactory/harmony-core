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
import com.tactfactory.harmony.annotation.GeneratedValue.Strategy;
import com.tactfactory.harmony.annotation.Id;
import com.tactfactory.harmony.annotation.ManyToOne;
import com.tactfactory.harmony.annotation.Table;
import com.tactfactory.harmony.annotation.Column.Type;
import com.tactfactory.harmony.annotation.OneToMany;

// Annotation with default and forced value/parameter
/** Test Application entity. */
@Table
@Entity
@SuppressWarnings(value = "serial")
public class Comment implements Serializable { //TODO extends EntityBase {

	/** Entity's technical id. */
	@Id
    @Column(type = Type.INTEGER)
    @GeneratedValue(strategy = Strategy.MODE_IDENTITY)
    private int id;

	/** Content. */
	@Column(length = 2000, type = Type.TEXT)
    private String content;

	/** Author. */
	@ManyToOne
	private User owner;

	/** Post associated. */
	@ManyToOne
	private Post post;

	/** Creation date.*/
	@Column(name = "created_at")	// typ ="datetime",
    private DateTime createdAt;

	/** Is comment validated ?. */
	@Column
	private boolean validate = false;

	/** Categories of this comment. */
	@OneToMany
	private ArrayList<CategoryToComment> categories;

	/**
	 * Constructor.
	 */
	public Comment() {
		this.id = -1;
    	this.createdAt = new DateTime();
    }

	/**
	 * @return the id
	 */
	public int getId() {
	     return this.id;
	}


	/**
	 * @param value the id to set
	 */
	public void setId(final int value) {
	     this.id = value;
	}

	/**
	 * @return the content
	 */
	public String getContent() {
	     return this.content;
	}


	/**
	 * @param value the content to set
	 */
	public void setContent(final String value) {
	     this.content = value;
	}

	/**
	 * @return the owner
	 */
	public User getOwner() {
	     return this.owner;
	}


	/**
	 * @param value the owner to set
	 */
	public void setOwner(final User value) {
	     this.owner = value;
	}

	/**
	 * @return the post
	 */
	public Post getPost() {
	     return this.post;
	}


	/**
	 * @param value the post to set
	 */
	public void setPost(final Post value) {
	     this.post = value;
	}

	/**
	 * @return the createdAt
	 */
	public DateTime getCreatedAt() {
	     return this.createdAt;
	}


	/**
	 * @param value the createdAt to set
	 */
	public void setCreatedAt(final DateTime value) {
	     this.createdAt = value;
	}

	/**
	 * @return the validate
	 */
	public boolean isValidate() {
	     return this.validate;
	}


	/**
	 * @param value the validate to set
	 */
	public void setValidate(final boolean value) {
	     this.validate = value;
	}

}

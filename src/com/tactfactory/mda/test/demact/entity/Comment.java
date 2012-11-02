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
import java.util.Date;

import org.joda.time.DateTime;

import com.tactfactory.mda.orm.annotation.*;

@Table
@Entity
public class Comment implements Serializable {
	@Id
    @Column()					// type="integer",
    @GeneratedValue(strategy="IDENTITY")
    protected int id;

	@Column(length=2000)	// type="string"
    protected String content;
	
	@ManyToOne
	protected User owner;
	
	@ManyToOne
	protected Post post;
	
	@Column(name="created_at")	// type="datetime",
    protected Date createdAt;


	public Comment() {
		this.id = -1;
    	this.createdAt = new DateTime().toDate();
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
	 * @return the post
	 */
	public final Post getPost() {
		return post;
	}

	/**
	 * @param post the post to set
	 */
	public final void setPost(Post post) {
		this.post = post;
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
}

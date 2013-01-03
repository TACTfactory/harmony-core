/**
 * This file is part of the Harmony package.
 *
 * (c) Mickael Gaillard <mickael.gaillard@tactfactory.com>
 *
 * For the full copyright and license information, please view the LICENSE
 * file that was distributed with this source code.
 */
package com.tactfactory.mda.test.demact.entity;

import org.joda.time.DateTime;

import com.tactfactory.mda.orm.annotation.*;
import com.tactfactory.mda.orm.annotation.Column.Type;

@Table
@Entity
public class Comment extends Object {
	
	@Id
    @Column(type=Type.INTEGER, hidden=true)
    @GeneratedValue(strategy="IDENTITY")
    protected int id;

	@Column(length=2000, type=Type.TEXT)
    protected String content;
	
	@ManyToOne
	protected User owner;
	
	@ManyToOne
	protected Post post;
	
	@Column(name="created_at")	// type="datetime",
    protected DateTime createdAt;
	
	@Column
	protected boolean validate = false;

	public Comment() {
		this.id = -1;
    	this.createdAt = new DateTime();
    }
	
}

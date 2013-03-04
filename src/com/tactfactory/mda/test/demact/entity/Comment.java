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

import org.joda.time.DateTime;

import com.tactfactory.mda.annotation.Column;
import com.tactfactory.mda.annotation.Column.Type;
import com.tactfactory.mda.annotation.Entity;
import com.tactfactory.mda.annotation.GeneratedValue;
import com.tactfactory.mda.annotation.Id;
import com.tactfactory.mda.annotation.ManyToOne;
import com.tactfactory.mda.annotation.Table;

// Annotation with default and forced value/parameter
@Table
@Entity
@SuppressWarnings(value = "serial")
public class Comment implements Serializable { //TODO extends EntityBase {
	
	@Id
    @Column(type = Type.INTEGER, hidden = true)
    @GeneratedValue(strategy = "IDENTITY")
    private int id;

	@Column(length = 2000, type = Type.TEXT)
    private String content;
	
	@ManyToOne
	private User owner;
	
	@ManyToOne
	private Post post;
	
	@Column(name = "created_at")	// typ ="datetime",
    private DateTime createdAt;
	
	@Column
	private boolean validate = false;

	public Comment() {
		this.id = -1;
    	this.createdAt = new DateTime();
    }
	
}

/**
 * This file is part of the Harmony package.
 *
 * (c) Mickael Gaillard <mickael.gaillard@tactfactory.com>
 *
 * For the full copyright and license information, please view the LICENSE
 * file that was distributed with this source code.
 */
package com.tactfactory.harmony.test.demact.entity;

import com.tactfactory.harmony.annotation.Column;
import com.tactfactory.harmony.annotation.Entity;
import com.tactfactory.harmony.annotation.Id;
import com.tactfactory.harmony.annotation.ManyToOne;

@Entity
/** Link entity between category and comment. */
public class CategoryToComment {

	@Id
	@Column
	/** Entity's technical id. */
	private int id;

	/** The displayed name of the category. */
	@Column
	private String displayName;
	
	/** The category in which this CategoryToComment is. */
	@ManyToOne(inversedBy="comments")
	private Category category;
}

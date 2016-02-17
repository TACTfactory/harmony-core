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
import com.tactfactory.harmony.annotation.View;

@Entity
@View(list = false)
/** Link entity between group and comment. */
public class GroupToComment {

	@Id
	@Column
	/** Entity's technical id. */
	private int id;

	/** The displayed name of the group. */
	@Column
	private String displayName;

	/** The group in which this GroupToComment is. */
	@ManyToOne(inversedBy="comments")
	private Group group;
}


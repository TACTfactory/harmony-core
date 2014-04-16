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
import com.tactfactory.harmony.annotation.GeneratedValue;
import com.tactfactory.harmony.annotation.GeneratedValue.Strategy;
import com.tactfactory.harmony.annotation.Id;

/** Test Application entity. */
@Entity
public class UserGroup {

	/** Entity's technical id. */
	@Id
	@GeneratedValue(strategy=Strategy.MODE_IDENTITY)
	@Column
	private int id;

	/** User group's name. */
	@Column
	private String name;

	/** Permission to write. */
	@Column
	private boolean writePermission;

	/** Permission to delete. */
	@Column
	private boolean deletePermission;


}

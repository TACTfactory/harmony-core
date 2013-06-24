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

@Entity
public class UserGroup {

	@Id
	@Column
	private int id;
	
	@Column
	private String name;
	
	@Column
	private boolean writePermission;
	
	@Column
	private boolean deletePermission;
	
	
}

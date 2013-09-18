/**
 * This file is part of the Harmony package.
 *
 * (c) Mickael Gaillard <mickael.gaillard@tactfactory.com>
 *
 * For the full copyright and license information, please view the LICENSE
 * file that was distributed with this source code.
 */
package com.tactfactory.harmony.test.demact.entity;

import com.tactfactory.harmony.annotation.Id;

/** Test Application entity. */
public enum Title {
	MR(0),
	MME(1),
	NONE(2);

	@Id
	private int id;

	private Title(int id) {
		this.id = id;
	}
}

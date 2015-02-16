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
import com.tactfactory.harmony.annotation.Column.Type;

import java.io.Serializable;

/** Simple entity containing only an id.
 * (For test purposes only)
 */
@Entity
public class SimpleEntity implements Serializable {
	/** Entity's technical id. */
	@Id
    @Column(type = Type.INTEGER, hidden = true)
    @GeneratedValue(strategy = Strategy.MODE_IDENTITY)
	private int id;
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

}

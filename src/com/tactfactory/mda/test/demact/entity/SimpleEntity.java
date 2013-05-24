/**
 * This file is part of the Harmony package.
 *
 * (c) Mickael Gaillard <mickael.gaillard@tactfactory.com>
 *
 * For the full copyright and license information, please view the LICENSE
 * file that was distributed with this source code.
 */
package com.tactfactory.mda.test.demact.entity;

import java.io.Serializable;import com.tactfactory.mda.annotation.Column;
import com.tactfactory.mda.annotation.Column.Type;
import com.tactfactory.mda.annotation.Entity;
import com.tactfactory.mda.annotation.GeneratedValue;
import com.tactfactory.mda.annotation.Id;

/** Simple entity containing only an id.
 * (For test purposes only)
 */
@Entity
public class SimpleEntity implements Serializable {
	@Id
    @Column(type = Type.INTEGER, hidden = true)
    @GeneratedValue(strategy = "IDENTITY")
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

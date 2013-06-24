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
import com.tactfactory.harmony.annotation.Id;
import com.tactfactory.harmony.annotation.Column.Type;

/** Simple entity containing only an id.
 * (For test purposes only)
 */
@Entity
public class SimpleEntity {
	@Id
    @Column(type = Type.INTEGER, hidden = true)
    @GeneratedValue(strategy = "IDENTITY")
	private int id;
}

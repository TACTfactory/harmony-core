/**
 * This file is part of the Harmony package.
 *
 * (c) Mickael Gaillard <mickael.gaillard@tactfactory.com>
 *
 * For the full copyright and license information, please view the LICENSE
 * file that was distributed with this source code.
 */
package com.tactfactory.mda.test.demact.entity;

import com.tactfactory.mda.annotation.*;
import com.tactfactory.mda.annotation.Column.Type;
import com.tactfactory.mda.bundles.rest.annotation.Rest;

@Entity
@Rest()
public class SimpleEntity {
	@Id
    @Column(type=Type.INTEGER, hidden=true)
    @GeneratedValue(strategy="IDENTITY")
    protected int id;

}

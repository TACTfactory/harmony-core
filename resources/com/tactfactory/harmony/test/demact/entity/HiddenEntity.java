/**
 * This file is part of the Harmony package.
 *
 * (c) Mickael Gaillard <mickael.gaillard@tactfactory.com>
 *
 * For the full copyright and license information, please view the LICENSE
 * file that was distributed with this source code.
 */
package com.tactfactory.harmony.test.demact.entity;

import java.io.Serializable;
import java.util.ArrayList;

import org.joda.time.DateTime;

import com.tactfactory.harmony.annotation.Column;
import com.tactfactory.harmony.annotation.Entity;
import com.tactfactory.harmony.annotation.Crud;
import com.tactfactory.harmony.annotation.GeneratedValue;
import com.tactfactory.harmony.annotation.Id;
import com.tactfactory.harmony.annotation.ManyToMany;
import com.tactfactory.harmony.annotation.ManyToOne;
import com.tactfactory.harmony.annotation.OneToMany;
import com.tactfactory.harmony.annotation.Table;
import com.tactfactory.harmony.annotation.Column.Type;

/** Test Application entity. */
// All annotation with default value/parameter
@Table
@Entity
@Crud(hidden=true)
public class HiddenEntity implements Serializable {
	/** Serial UID. */
	private static final long serialVersionUID = -6549779793416923128L;

	/** Entity's technical id. */
	@Id
    @Column(type = Type.INTEGER)
    @GeneratedValue(strategy = "IDENTITY")
	private int id;

	/** Content of the post. */
	@Column()
    private String content;


}

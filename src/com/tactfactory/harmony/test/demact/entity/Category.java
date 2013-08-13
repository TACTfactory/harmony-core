/**
 * This file is part of the Harmony package.
 *
 * (c) Mickael Gaillard <mickael.gaillard@tactfactory.com>
 *
 * For the full copyright and license information, please view the LICENSE
 * file that was distributed with this source code.
 */
package com.tactfactory.harmony.test.demact.entity;

import java.util.ArrayList;

import com.tactfactory.harmony.annotation.Column;
import com.tactfactory.harmony.annotation.Entity;
import com.tactfactory.harmony.annotation.Id;
import com.tactfactory.harmony.annotation.OneToMany;

/** Category class. */
@Entity
public class Category {

	/** Entity's technical id. */
	@Id
	@Column
	private int id;
	
	/** Name of the category. */
	@Column(defaultValue="Default Category Name")
	private String name;
	
	/** List of comments of this category. */
	@OneToMany(mappedBy = "category")
	private ArrayList<CategoryToComment> comments;
}

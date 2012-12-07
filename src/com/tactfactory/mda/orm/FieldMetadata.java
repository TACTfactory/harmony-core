/**
 * This file is part of the Harmony package.
 *
 * (c) Mickael Gaillard <mickael.gaillard@tactfactory.com>
 *
 * For the full copyright and license information, please view the LICENSE
 * file that was distributed with this source code.
 */
package com.tactfactory.mda.orm;

import com.tactfactory.mda.plateforme.BaseAdapter;

/** Entity field metadata */
public class FieldMetadata {
	
	/** Field name */
	public String name;
	
	/** Field type */
	public String type;
	public String relation_type;
	public String entity_type = "string";
	
	public boolean nullable = false;
	public boolean unique = false;
	public int length = 255;
	public int precision = 0;
	public int scale = 0;
	
	/** GUI show field type */
	public String customShowType;
	
	/** GUI edit field type */
	public String customEditType;
	
	/** Customize edit and show GUI field */
	public void customize(BaseAdapter adapter) {
		this.customShowType = adapter.getViewComponentShow(this);
		this.customEditType = adapter.getViewComponentEdit(this);
	}
}

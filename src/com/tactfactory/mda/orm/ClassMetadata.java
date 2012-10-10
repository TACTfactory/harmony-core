/**
 * This file is part of the Harmony package.
 *
 * (c) Mickael Gaillard <mickael.gaillard@tactfactory.com>
 *
 * For the full copyright and license information, please view the LICENSE
 * file that was distributed with this source code.
 */
package com.tactfactory.mda.orm;

import java.util.HashMap;

/** Entity class metadata */
public class ClassMetadata {
	
	/** Namespace of entity class */
	public String space = "";
	
	/** Name of entity class */
	public String name = "";
	
	/** List of fields of entity class*/
	public HashMap<String, FieldMetadata> fields = new HashMap<String, FieldMetadata>();

	/** List of ids of entity class*/
	public HashMap<String, FieldMetadata> ids = new HashMap<String, FieldMetadata>();
	
	/** List of relations of entity class*/
	public HashMap<String, FieldMetadata> relations = new HashMap<String, FieldMetadata>();
}

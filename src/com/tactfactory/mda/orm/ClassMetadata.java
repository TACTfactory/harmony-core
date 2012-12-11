/**
 * This file is part of the Harmony package.
 *
 * (c) Mickael Gaillard <mickael.gaillard@tactfactory.com>
 *
 * For the full copyright and license information, please view the LICENSE
 * file that was distributed with this source code.
 */
package com.tactfactory.mda.orm;

import java.util.ArrayList;
import java.util.HashMap;

import com.tactfactory.mda.Harmony;

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
	
	/** Relations**/
	public HashMap<String, HashMap<String, String>> relat = new HashMap<String, HashMap<String, String>>();
	
	/** Class inherited by the entity class or null if none*/
	public String exts = null;
	
	/** Implemented class list of the entity class */
	public ArrayList<String> impls = new ArrayList<String>();
	
	/** Implemented class list of the entity class */
	public ArrayList<MethodMetadata> methods = new ArrayList<MethodMetadata>();

	/** Imports of the class */
	public ArrayList<String> imports = new ArrayList<String>();
	
	/** Returns the relation identified by fieldName (and create it if it doesn't exist yet 
	 * @throws Exception */
	public void putRelation(String fieldName, HashMap<String, String> rel) throws Exception{
		if(!this.relat.containsKey("fieldName")){
			this.relat.put(fieldName, rel);
		}else{
			throw new Exception("Duplicate relation on field "+fieldName);
		}
	}
}

/**
 * This file is part of the Symfodroid package.
 *
 * (c) Mickael Gaillard <mickael.gaillard@tactfactory.com>
 *
 * For the full copyright and license information, please view the LICENSE
 * file that was distributed with this source code.
 */
package com.tactfactory.mda.orm;

import java.util.HashMap;

public class ClassMetadata {
	public String nameSpace = "";
	public String nameClass = "";
	public HashMap<String, FieldMetadata> fields = new HashMap<String, FieldMetadata>();
	
	
}

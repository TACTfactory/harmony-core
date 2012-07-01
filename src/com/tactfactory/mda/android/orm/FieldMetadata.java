/**
 * This file is part of the Symfodroid package.
 *
 * (c) Mickael Gaillard <mickael.gaillard@tactfactory.com>
 *
 * For the full copyright and license information, please view the LICENSE
 * file that was distributed with this source code.
 */
package com.tactfactory.mda.android.orm;

public class FieldMetadata {
	public String name;
	public String type;
	public String customShowType;
	public String customEditType;
	
	public void customize() {
		if (type.equals("String") || type.equals("int") || type.equals("Date") ) {
			this.customShowType = "TextView";
			this.customEditType = "EditText";
		} else if (type.equals("Boolean")) {
			this.customShowType = "TextView";
			this.customEditType = "CheckBox";
		}
	}
}

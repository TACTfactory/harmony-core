<@header?interpret />
package ${project_namespace}.criterias.base;

import java.io.Serializable;
import java.util.ArrayList;

import android.database.DatabaseUtils;

import ${project_namespace}.criterias.base.value.CriteriaValue;

/** Criteria. Criteria used for some db requests.*/
public class Criteria implements Serializable, ICriteria {
	/** Criteria key. */
	private String key;
	/** Criteria value. */
	private CriteriaValue value;
	/** Criteria Type. */
	private Type type = Type.EQUALS;


	@Override
	public String toSQLiteString() {
		return "(" 
		+ this.key + " " 
		+ this.type.getSQL() + " " 
		+ DatabaseUtils.sqlEscapeString(this.value.toSQLiteString()) 
		+ ")";
	}

	@Override
	public String toSQLiteSelection() {
		return "(" 
			+ this.key + " " 
			+ this.type.getSQL() + " " 
			+ this.value.toSQLiteSelection() 
			+ ")";
	}
	
	@Override
	public void toSQLiteSelectionArgs(final ArrayList<String> array) {
		this.value.toSQLiteSelectionArgs(array);
	}
	
	/**
	 * Get the Criteria key.
	 * @return The Criteria's key
	 */
	public String getKey() {
		return this.key;
	}
	
	/**
	 * Get the Criteria value.
	 * @return The Criteria's value
	 */
	public CriteriaValue getValue() {
		return this.value;
	}
	
	/**
	 * Set the Criteria key.
	 * @param key The new key to set
	 */
	public void setKey(final String key) {
		this.key = key;
	}
	
	/**
	 * Set the Criteria value.
	 * @param value The new value to set
	 */
	public void addValue(final CriteriaValue value) {
		this.value = value;
	}
	
	/**
	 * Set the Criteria Type.
	 * @param type The new Type to set
	 */
	public void setType(final Type type) {
		this.type = type;
	}
	
	/**
	 * Get the Criteria Type.
	 * @return The Criteria's type
	 */
	public Type getType() {
		return this.type;
	}
	
	/**
	 * Enum Type for SQL purpose.
	 */
	public static enum Type {
		/** Equals "=". */
		EQUALS("="),
		/** Greater than ">". */
		SUPERIOR(">"),
		/** Smaller than "<". */
		INFERIOR("<"),
		/** Inferior or equal "<=". */
		INFERIOR_EQUALS("<="),
		/** Superior or equals ">=". */
		SUPERIOR_EQUALS(">="),
		/** Like "LIKE". */
		LIKE("LIKE"),
		/** IN "IN" (May be used with ArrayValue or SelectValue). */
		IN("IN");
		
		/** SQLite representation of this type. */
		private String sql;
		
		/**
		 * Constructor.
		 * @param sql The SQL version of the Enum
		 */
		private Type(final String sql) {
			this.sql = sql;
		}
		
		/**
		 * Get the SQL String transcryption.
		 * @return The SQL version of the Enum
		 */
		public String getSQL() {
			return this.sql;
		}
	}
	
	/**
	 * Equals function.
	 * @param obj The Object to compare with
	 * @return true if objects are the same, otherwise false
	 */
	@Override
	public boolean equals(final Object obj) {
		boolean result = true;
	
		if (obj == null) {
			result = false;
		} else if (this.getClass() != obj.getClass()) {
			result = false;
		}
		
		if (result) {
			final Criteria other = (Criteria) obj;
			if (this.key == null) {
				if (other.key != null) {
					result = false;
				}
			} else if (!this.key.equals(other.key)) {
				result = false;
			} else if (this.type != other.type) {
				result = false;
			} else if (this.value == null) {
				if (other.value != null) {
					result = false;
				}
			} else if (!this.value.equals(other.value)) {
				result = false;
			}
		}
	
		return result;
	}
}

package ${project_namespace}.criterias.base;

import java.io.Serializable;
import android.database.DatabaseUtils;

/** Criteria. Criteria used for some db requests.*/
public class Criteria implements Serializable, ICriteria {
	/** Criteria key. */
	private String key;
	/** Criteria value. */
	private String value;
	/** Criteria Type. */
	private Type type = Type.EQUALS;

	/**
	 * Convert the criteria to a SQLite String.
	 * @return The SQLite String representation of the criteria. ex : <br />
	 * "(price > 15.0)" 
	 */
	@Override
	public String toSQLiteString() {
		return "(" + key + " " + type.getSQL() + " " + DatabaseUtils.sqlEscapeString(value) + ")";
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
	public String getValue() {
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
	public void addValue(final String value) {
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
		EQUALS("="),
		SUPERIOR(">"),
		INFERIOR("<"),
		INFERIOR_EQUALS(">="),
		SUPERIOR_EQUALS("<="),
		LIKE("LIKE"),
		IN("IN");
		
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
		} else if (getClass() != obj.getClass()) {
			result = false;
		}
		
		if (result) {
			final Criteria other = (Criteria) obj;
			if (key == null) {
				if (other.key != null) {
					result = false;
				}
			} else if (!key.equals(other.key)) {
				result = false;
			} else if (type != other.type) {
				result = false;
			} else if (value == null) {
				if (other.value != null) {
					result = false;
				}
			} else if (!value.equals(other.value)) {
				result = false;
			}
		}
	
		return result;
	}
}

package ${project_namespace}.criterias.base;

import java.io.Serializable;

/** Criteria. Criteria used for some db requests.*/
public class Criteria implements Serializable, ICriteria {
	private String key;
	private String value;
	private Type type = Type.EQUALS;

	/**
	 * Convert the criteria to a SQLite String.
	 * @return The SQLite String representation of the criteria. ex : "(price > 15.0)" 
	 */
	@Override
	public String toSQLiteString() {
		return "(" + key + " " + type.getSQL() + " '" + value + "')";
	}
	
	/**
	 * Get the Criteria key.
	 */
	public String getKey() {
		return this.key;
	}
	
	/**
	 * Get the Criteria value.
	 */
	public String getValue() {
		return this.value;
	}
	
	/**
	 * Set the Criteria key.
	 */
	public void setKey(final String key) {
		this.key = key;
	}
	
	/**
	 * Set the Criteria value.
	 */
	public void addValue(final String value) {
		this.value = value;
	}
	
	/**
	 * Set the Criteria Type.
	 */
	public void setType(final Type type) {
		this.type = type;
	}
	
	/**
	 * Get the Criteria Type.
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
		 */
		private Type(final String sql) {
			this.sql = sql;
		}
		
		/**
		 * Get the SQL String transcryption.
		 */
		public String getSQL() {
			return this.sql;
		}
	}
	
	/**
	 * Equals function.
	 */
	@Override
	public boolean equals(final Object obj) {
		boolean result = true;
	
		if (obj == null) {
			result = false;
		}
		else if (getClass() != obj.getClass()) {
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
			}
			else if (type != other.type) {
				result = false;
			}
			else if (value == null) {
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

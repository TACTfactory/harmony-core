<@header?interpret />
package ${project_namespace}.criterias.base;

import java.io.Serializable;
import java.util.ArrayList;

import com.google.common.base.Joiner;
import com.google.common.collect.ObjectArrays;
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
	 * Set the Criteria key with a datetime function.
	 *
	 * @param key The new key to set
	 * @param options The options of the datetime function
	 */
	public void setDateTimeKey(final String key,
			final String... options) {
		this.setMethodKey("datetime",
				ObjectArrays.concat(key, options));
	}
	
	/**
	 * Set the Criteria key with a date function.
	 *
	 * @param key The new key to set
	 * @param options The options of the date function
	 */
	public void setDateKey(final String key,
			final String... options) {
		
		this.setMethodKey("date",
				ObjectArrays.concat(key, options));
	}
	
	/**
	 * Set the Criteria key with a time function.
	 *
	 * @param key The new key to set
	 * @param options The options of the time function
	 */	
	public void setTimeKey(final String key,
			final String... options) {
		
		this.setMethodKey("time",
				ObjectArrays.concat(key, options));
	}
	
	/**
	 * Set the Criteria key with a strtf function.
	 *
	 * @param format The strtf format
	 * @param key The new key to set
	 * @param options The options of the strtf function
	 */
	public void setStrtfKey(final String format,
			final String key,
			final String... options) {
		
		this.setMethodKey("strtf",
				ObjectArrays.concat(format, ObjectArrays.concat(key, options)));
	}
	
	/**
	 * Set the Criteria key as a sqlite method.
	 *
	 * @param methodName the sqlite method name
	 * @param options The options of the sqlite method
	 */
	public void setMethodKey(final String methodName,
			final String... options) {
		
		this.key = methodName + "(";
		this.key += Joiner.on(", ").skipNulls().join(options);
		this.key += ")";
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

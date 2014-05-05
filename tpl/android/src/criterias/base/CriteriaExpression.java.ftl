<@header?interpret />
package ${project_namespace}.criterias.base;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import ${project_namespace}.criterias.base.Criterion.Type;
import ${project_namespace}.criterias.base.value.StringValue;

/**
 * CriteriaExpression.
 * An array of Criterion and CriteriaExpression. Used for db requests.
 * A CriteriaExpression represents the WHERE clause of a request.
 * @param T The entity type
 */
public class CriteriaExpression implements Serializable, ICriteria {
	/** Expression GroupType. */
	private GroupType type;
	/** Array of ICriteria. */
	private List<ICriteria> criteria = new ArrayList<ICriteria>();
	/** KEY constant for serializing a criteria expression inside a bundle. */
	public static final String PARCELABLE = "CriteriaExpression";

	/**
	 * Constructor.
	 * @param type The Criteria's GroupType
	 */
	public CriteriaExpression(final GroupType type) {
		this.type = type;
	}

	@Override
	public String toSQLiteString() {
		StringBuilder ret = new StringBuilder("(");

		for (int i = 0; i < this.criteria.size(); i++) {
			final ICriteria crit = this.criteria.get(i);
			ret.append(crit.toSQLiteString());
			if (i != this.criteria.size() - 1) {
				ret.append(' ');
				ret.append(this.type.getSqlType());
				ret.append(' ');
			}
		}
		ret.append(')');
		return ret.toString();
	}

	@Override
	public String toSQLiteSelection() {
		if (this.criteria.isEmpty()) {
			return null;
		} else {
			StringBuilder ret = new StringBuilder("(");

			for (int i = 0; i < this.criteria.size(); i++) {
				final ICriteria crit = this.criteria.get(i);
				ret.append(crit.toSQLiteSelection());
				if (i != this.criteria.size() - 1) {
					ret.append(' ');
					ret.append(this.type.getSqlType());
					ret.append(' ');
				}
			}
			ret.append(')');
			return ret.toString();
		}
	}

	@Override
	public void toSQLiteSelectionArgs(final ArrayList<String> array) {

		for (int i = 0; i < this.criteria.size(); i++) {
			final ICriteria crit = this.criteria.get(i);
			crit.toSQLiteSelectionArgs(array);
		}

	}

	/**
	 * Converts the criteria expression to a Selection args String array.
	 * @return The String[] of selection args
	 */
	public String[] toSQLiteSelectionArgs() {
		if (this.criteria.isEmpty()) {
			return null;
		} else {
			ArrayList<String> tmpArray = new ArrayList<String>();
			this.toSQLiteSelectionArgs(tmpArray);
			return tmpArray.toArray(new String[tmpArray.size()]);
		}
	}

	/**
	 * Adds a criterion of form : (key TYPE value).
	 * @param crit The criterion to add
	 * @return True if the criterion is valid and doesn't exists yet
	 */
	public boolean add(final Criterion crit) {
		boolean result;

		if (!this.criteria.contains(crit)) {
			this.criteria.add(crit);
			result = true;
		} else {
			result = false;
		}

		return result;
	}

	/**
	 * Adds a criterion of form : (key TYPE value).
	 * @param crit The criterion to add
	 * @return True if the criterion is valid and doesn't exists yet
	 */
	public boolean add(final ICriteria crit) {
		boolean result;

		if (!this.criteria.contains(crit)) {
			this.criteria.add(crit);
			result = true;
		} else {
			result = false;
		}

		return result;
	}

	/**
	 * Add a criterion of form : (key TYPE value).
	 * @param key The db column
	 * @param value The value
	 * @param type The type of criterion (can be Equals, Superior, etc.)
	 * @return True if the criterion is valid and doesn't exists yet
	 */
	public boolean add(final String key, final String value, final Type type) {
		final Criterion criterion = new Criterion();
		criterion.setKey(key);
		criterion.addValue(new StringValue(value));
		criterion.setType(type);

		return this.add(criterion);
	}

	/**
	 * Add a criterion of form : (key EQUALS value).
	 * @param key The db column
	 * @param value The value
	 * @return True if the criterion is valid and doesn't exists yet
	 */
	public boolean add(final String key, final String value) {
		return this.add(key, value, Type.EQUALS);
	}

	/**
	 * Enum GroupType.
	 */
	public enum GroupType {
		/** AND type.*/
		AND("AND"),
		/** OR type.*/
		OR("OR");

		/** SQLite representation of this type. */
		private String sql;

		/**
		 * Constructor.
		 * @param sql The SQL version of the Enum
		 */
		private GroupType(final String sql) {
			this.sql = sql;
		}

		/**
		 * Get the SQL String transcryption.
		 * @return The SQL version of the Enum
		 */
		public String getSqlType() { return this.sql; }
	}

	/**
	 * Checks if the List is empty.
	 * @return true if the List is empty, false otherwise
	 */
	public boolean isEmpty() {
		return this.criteria.isEmpty();
	}
}

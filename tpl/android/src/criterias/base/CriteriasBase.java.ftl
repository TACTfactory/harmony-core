package ${project_namespace}.criterias.base;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import ${project_namespace}.criterias.base.Criteria.Type;

/** CriteriasBase. 
 *	An array of Criteria and CriteriasBase. Used for db requests.   
 */
public abstract class CriteriasBase implements Serializable, ICriteria {
	private GroupType type;
	private List<ICriteria> arr = new ArrayList<ICriteria>(); 
	 
	/**
	 * Constructor.
	 * @param type The Criteria's GroupType
	 */
	public CriteriasBase(final GroupType type) {
		this.type = type;
	}
		
	/**
	 * Convert the criteria to a SQLite String.
	 * @return The SQLite String representation of the criteria. ex : <br />
	 * "(price > 15.0)" 
	 */
	public String toSQLiteString() {
		StringBuilder ret = new StringBuilder('(');
		
		for (int i = 0; i < this.arr.size(); i++) {
			final ICriteria crit = this.arr.get(i);
			ret.append(crit.toSQLiteString());
			if (i != this.arr.size() - 1) {
				ret.append(' ');
				ret.append(type.getSqlType());
				ret.append(' ');
			}
		}
		ret.append(')');
		return ret.toString();
	}
	
	/**
	 * Test if the given criteria is valid.
	 * @param c The criteria to test
	 * @return true if the criteria is valid
	 */
	public abstract boolean validCriteria(Criteria c);
	
	/**
	 * Adds a criteria of form : (key TYPE value).
	 * @param c The criteria to add
	 * @return True if the criterias is valid and doesn't exists yet
	 */
	public boolean add(final Criteria crit) {
		boolean result;
	
		if (this.validCriteria(crit) && !this.arr.contains(crit)) {
			arr.add(crit);
			result = true;
		} else {
			result = false;
		}
		
		return result;
	}
	
	/**
	 * Add a criteria of form : (key TYPE value).
	 * @param key The db column 
	 * @param value The value
	 * @param type The type of criteria (can be Equals, Superior, etc.)
	 * @return True if the criterias is valid and doesn't exists yet
	 */
	public boolean add(final String key, final String value, final Type type) {
		final Criteria criteria = new Criteria();
		criteria.setKey(key);
		criteria.addValue(value);
		criteria.setType(type);
		
		return this.add(criteria);
	}
	
	/**
	 * Add a criteria of form : (key EQUALS value).
	 * @param key The db column 
	 * @param value The value 
	 * @return True if the criterias is valid and doesn't exists yet
	 */
	public boolean add(final String key, final String value) {
		return this.add(key, value, Type.EQUALS);
	}
	
	/**
	 * Enum GroupType.
	 */
	public enum GroupType {
		AND("AND"),
		OR("OR");
		
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
		return this.arr.isEmpty();
	}
}

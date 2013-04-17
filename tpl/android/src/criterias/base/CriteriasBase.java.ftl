package ${project_namespace}.criterias.base;

import java.io.Serializable;
import java.util.ArrayList;

import ${project_namespace}.criterias.base.Criteria.Type;

/** CriteriasBase. 
 *	An array of Criteria and CriteriasBase. Used for db requests.   
 */
public abstract class CriteriasBase implements Serializable, ICriteria{
	private GroupType type;
	private ArrayList<ICriteria> arr = new ArrayList<ICriteria>(); 
	 
	public CriteriasBase(GroupType type){
		this.type = type;
	}
		
	public String toSQLiteString(){
		String ret = "(";
		for (int i=0;i<this.arr.size();i++){
			ICriteria crit = this.arr.get(i);
			ret+=crit.toSQLiteString();
			if (i!=this.arr.size()-1){
				ret+=" "+type.getSqlType()+" ";
			}
		}
		ret+=")";
		return ret;
	}
	
	/**
	 * Test if the given criteria is valid
	 * @param The criteria to test
	 * @return true if the criteria is valid
	 */
	public abstract boolean validCriteria(Criteria c);
	
	/**
	 * Adds a criteria of form : (key TYPE value)
	 * @param c The criteria to add
	 * @return True if the criterias is valid and doesn't exists yet
	 */
	public boolean add(Criteria c){
		if (this.validCriteria(c) && !this.arr.contains(c)){
			arr.add(c);
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * Add a criteria of form : (key TYPE value)
	 * @param key The db column 
	 * @param value The value
	 * @param type The type of criteria (can be Equals, Superior, etc.)
	 * @return True if the criterias is valid and doesn't exists yet
	 */
	public boolean add(String key, String value, Type type){
		Criteria c = new Criteria();
		c.setKey(key);
		c.addValue(value);
		c.setType(type);
		
		return this.add(c);
	}
	
	/**
	 * Add a criteria of form : (key EQUALS value)
	 * @param key The db column 
	 * @param value The value 
	 * @return True if the criterias is valid and doesn't exists yet
	 */
	public boolean add(String key, String value){
		return this.add(key,value,Type.EQUALS);
	}
	
	public enum GroupType{
		AND("AND"),
		OR("OR");
		
		private String sql;
		
		private GroupType(String sql){
			this.sql = sql;
		}
		
		public String getSqlType(){return this.sql;}
	}
	
	public boolean isEmpty(){
		return this.arr.isEmpty();
	}
}

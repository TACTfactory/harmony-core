package ${project_namespace}.criterias.base;

import java.io.Serializable;
import java.util.ArrayList;

import ${project_namespace}.criterias.base.Criteria.Type;

public abstract class CriteriasBase implements Serializable, ICriteria{
	private GroupType type;
	private ArrayList<ICriteria> arr = new ArrayList<ICriteria>(); 
	 
	public CriteriasBase(GroupType type){
		this.type = type;
	}
		
	public String toSQLiteString(){
		String ret = "(";
		for(int i=0;i<this.arr.size();i++){
			ICriteria crit = this.arr.get(i);
			ret+=crit.toSQLiteString();
			if(i!=this.arr.size()-1){
				ret+=" "+type.getSqlType()+" ";
			}
		}
		ret+=")";
		return ret;
	}
	
	public abstract boolean validCriteria(Criteria c);
	
	public boolean add(Criteria c){
		if(this.validCriteria(c) && !this.arr.contains(c)){
			arr.add(c);
			return true;
		}else{
			return false;
		}
	}
	
	public boolean add(String key, String value, Type type){
		Criteria c = new Criteria();
		c.setKey(key);
		c.addValue(value);
		c.setType(type);
		
		return this.add(c);
	}
	
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

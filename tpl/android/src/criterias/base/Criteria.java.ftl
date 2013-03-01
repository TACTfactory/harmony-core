package ${project_namespace}.criterias.base;

import java.io.Serializable;

/** Criteria. Criteria used for some db requests.*/
public class Criteria implements Serializable, ICriteria{
	private String key;
	private String value;
	private Type type = Type.EQUALS;

	/**
	 *  Convert the criteria to an SQLite String
	 * @return The SQLite String representation of the criteria. ex : "(price > 15.0)" 
	 */
	@Override
	public String toSQLiteString(){
		return "("+key +" "+ type.getSQL() +" '"+ value+"')";
	}
	
	public String getKey(){
		return this.key;
	}
	
	public String getValue(){
		return this.value;
	}
	
	public void setKey(String key){
		this.key = key;
	}
	
	public void addValue(String value){
		this.value = value;
	}
	
	public void setType(Type type){
		this.type = type;
	}
	
	public Type getType(){
		return this.type;
	}
	
	public static enum Type{
		EQUALS("="),
		SUPERIOR(">"),
		INFERIOR("<"),
		INFERIOR_EQUALS(">="),
		SUPERIOR_EQUALS("<="),
		LIKE("LIKE"),
		IN("IN");
		
		private String sql;
		
		private Type(String sql){
			this.sql = sql;
		}
		
		public String getSQL(){
			return this.sql;
		}
	}
	

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Criteria other = (Criteria) obj;
		if (key == null) {
			if (other.key != null)
				return false;
		} else if (!key.equals(other.key))
			return false;
		if (type != other.type)
			return false;
		if (value == null) {
			if (other.value != null)
				return false;
		} else if (!value.equals(other.value))
			return false;
		return true;
	}
}

package ${project_namespace}.criterias.base.value;

import java.util.ArrayList;

public class StringValue extends CriteriaValue {
	private String value;
	
	public StringValue(String value) {
		this.value = value;
	}
	
	public void setValue(String value) {
		this.value = value;
	}
	
	public String getValue() {
		return this.value;
	}
	
	public String toSQLiteString() {
		return this.value;
	}
	
	public String toSQLiteSelection() {
		return "?";
	}
	
	public void toSQLiteSelectionArgs(ArrayList<String> array) {
		array.add(value);
	}
}

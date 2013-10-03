package ${project_namespace}.criterias.base.value;

import com.google.common.collect.ObjectArrays;

public class DateTimeValue extends MethodValue {

	public DateTimeValue(Type type, String value, String... options) {
		super(type.getValue(), ObjectArrays.concat(value, options));
		// TODO Auto-generated constructor stub
	}
	
	public DateTimeValue(String format, String value, String... options) {
		super("strtf", ObjectArrays.concat(value, options));
		// TODO Auto-generated constructor stub
	}

	
	public enum Type {
		DATE("date"),
		DATETIME("datetime"),
		TIME("time");
		
		private String value;
		
		private Type(String value) {
			this.value = value;
		}
		
		public String getValue() {
			return this.value;
		}
	}
}


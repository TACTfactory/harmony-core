package ${local_namespace};

import android.content.Context;

/** ${name} contract. */
public final class ${name}Contract {
	//public static final String AUTHORITY = "com.android.contacts";
	
	//public static final Uri AUTHORITY_URI = Uri.parse("content://" + AUTHORITY);
	
	//public static final Uri CONTENT_URI = Uri.withAppendedPath(AUTHORITY_URI, CONTENT_DIRECTORY);
	
	// Fields
	<#list fields as field>
	public static final String ${field.alias} = "${field.name}";
	</#list>
}

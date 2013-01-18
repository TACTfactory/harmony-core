<#assign curr = entities[current_entity] />
package ${curr.data_namespace};

import ${data_namespace}.base.${curr.name}WebServiceClientAdapterBase;
import android.content.Context;

public class ${curr.name}WebServiceClientAdapter extends ${curr.name}WebServiceClientAdapterBase{
	
	public ${curr.name}WebServiceClientAdapter(Context context){
		super(context);
	}
}

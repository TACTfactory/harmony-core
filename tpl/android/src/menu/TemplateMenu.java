package ${project_namespace}.menu;

import android.content.Context;
import android.support.v4.app.Fragment;

public class ${project_name?cap_first}Menu extends ${project_name?cap_first}MenuBase {

	private volatile static ${project_name?cap_first}Menu singleton;
	
	public ${project_name?cap_first}Menu(Context context) throws Exception {
		super(context);
	}

	public ${project_name?cap_first}Menu(Context context, Fragment fragment) throws Exception {
		super(context, fragment);
	}

	/** Get unique instance */
	public final synchronized static ${project_name?cap_first}Menu getInstance(Context context) throws Exception {
		return getInstance(context, null);
	}
	
	public final synchronized static ${project_name?cap_first}Menu getInstance(Context context, Fragment fragment) throws Exception {
		if (singleton == null) {
			singleton = new ${project_name?cap_first}Menu(context, fragment);
		}  else {
			singleton.context = context;
			singleton.fragment = fragment;
		}
			
		return singleton;
	}
}

package ${project_namespace};

import java.util.ArrayList;

import android.content.Context;
import android.content.SharedPreferences;

public abstract class ${project_name?cap_first}Preferences {
	// Constants
	private static final String shareName = "sharedata";
	//private static final String sync_last = "sync_last"
	
	/**
	 * Constructor
	 */
	public ${project_name?cap_first}Preferences(Context context) {
		this.settings = context.getSharedPreferences(
				shareName, Context.MODE_PRIVATE);
	}
	
	/**
	 * Clear All preferences values
	 */
	public abstract void ClearPreferences();

}
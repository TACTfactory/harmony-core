<@header?interpret />
package ${project_namespace};

import java.util.ArrayList;


import android.content.SharedPreferences;

public abstract class ${project_name?cap_first}Preferences {
	/** Constants.
	 *
	 */
	private static final String shareName = "sharedata";
	//private static final String sync_last = "sync_last"

	/**
	 * Constructor.
	 * @param ctx context
	 */
	public ${project_name?cap_first}Preferences(android.content.Context ctx) {
		this.settings = ctx.getSharedPreferences(
				shareName, android.content.Context.MODE_PRIVATE);
	}

	/**
	 * Clear All preferences values.
	 */
	public abstract void ClearPreferences();

}

<@header?interpret />
package ${project_namespace};

import java.util.ArrayList;


import android.content.SharedPreferences;

public class ${project_name?cap_first}Preferences
                        extends ${project_name?cap_first}PreferencesBase {
    /** Constants
     *
     */
    private static final String shareName = "sharedata";

    /** Common
     *
     */
    public ${project_name?cap_first}Preferences(android.content.Context ctx) {
        this.settings = ctx.getSharedPreferences(
                shareName, android.content.Context.MODE_PRIVATE);
    }

    /**
     * Clear All preferences values
     */
    @Override
    public void ClearPreferences() {
        // Save tempory old values
        // sample : String email = this.settings.getString(ACCOUNT_LOGIN, "");

        // Clear all
        SharedPreferences.Editor editor = this.settings.edit();
        editor.clear();

        // Restor old values
        // sample : editor.putString(ACCOUNT_LOGIN, email);

        editor.commit();
    }

}

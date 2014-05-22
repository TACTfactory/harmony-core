<@header?interpret />
package ${project_namespace}.view.${viewPackage};

import android.os.Bundle;

import ${project_namespace}.R;
import ${project_namespace}.harmony.view.HarmonyFragmentActivity;

/**
 * ${viewName}Activity. 
 *
 * This activity has been generated by harmony 
 * and only contains a ${viewName}Fragment.
 * Feel free to modify it.
 *
 * @see android.app.Activity
 */
public class ${viewName}Activity 
        extends HarmonyFragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_${viewName?lower_case});
    }
}

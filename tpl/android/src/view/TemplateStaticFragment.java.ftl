<@header?interpret />
package ${project_namespace}.view.${viewPackage};

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import ${project_namespace}.R;
import ${project_namespace}.harmony.view.HarmonyFragment;

/**
 * ${viewName}Fragment. 
 * This fragment has been generated by harmony.
 * Feel free to modify it.
 */
public class ${viewName}Fragment extends HarmonyFragment {

    @Override
    public View onCreateView(
            LayoutInflater inflater,
            ViewGroup container,
            Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        final View view =
                inflater.inflate(
                        R.layout.fragment_${viewName?lower_case},
                        container,
                        false);

        return view;
    }
}

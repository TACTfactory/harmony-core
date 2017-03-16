<#assign curr = entities[current_entity] />
<@header?interpret />
package ${curr.controller_namespace};

import ${curr.namespace}.R;

import ${project_namespace}.harmony.view.HarmonyFragmentActivity;
<#if curr.deleteAction>import ${project_namespace}.view.${curr.name?lower_case}.${curr.name}ShowFragment.DeleteCallback;
</#if>
import android.os.Bundle;

/** ${curr.name} show Activity.
 *
 * This only contains a ${curr.name}ShowFragment.
 *
 * @see android.app.Activity
 */
public class ${curr.name}ShowActivity 
        extends HarmonyFragmentActivity<#if curr.deleteAction> 
        implements DeleteCallback</#if> {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setNavigationBack(true);
    }
    
    @Override
    protected int getContentView() {
        return R.layout.activity_${curr.name?lower_case}_show;
    }

<#if curr.deleteAction>
    @Override
    public void onItemDeleted() {
        this.finish();
    }
</#if>
}

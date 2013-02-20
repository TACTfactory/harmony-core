<#assign search_entities = [] />
<#function isInArray array entity>
	<#list search_entities as entityIn>
		<#if entityIn.name==entity.name>
			<#return true>
		</#if>
	</#list>
	<#return false>
</#function>
<#list entities?values as entity>
	<#list entity.fields as field>
		<#if (field.options.search?? && !isInArray(search_entities, entity))>
			<#assign search_entities = search_entities + [entity] />
		</#if>
	</#list>
</#list>

package ${project_namespace}.menu;

import com.actionbarsherlock.internal.view.menu.ActionMenuItem;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;

import ${project_namespace}.HarmonyFragmentActivity;
import ${project_namespace}.HarmonyListFragment;

<#list search_entities as entity>
import ${entity.controller_namespace}.${entity.name?cap_first}ListFragment;
import ${entity.controller_namespace}.${entity.name?cap_first}SearchActivity;
</#list>

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;

public class SearchMenuWrapper extends MenuWrapperBase{
	private MenuItem searchItem;
	private final static String menuName = "Search";

	@Override
	protected void initializeMenu(Menu menu) {
		this.searchItem 	= menu.add(0, ${project_name?cap_first}Menu.SEARCH , Menu.NONE, menuName);
		this.searchItem.setShowAsAction(ActionMenuItem.SHOW_AS_ACTION_IF_ROOM|ActionMenuItem.SHOW_AS_ACTION_WITH_TEXT);
		this.searchItem.setVisible(false);
	}

	@Override
	protected void updateMenu(Menu menu, int currentActivityHashCode,
			int currentFragmentHashCode, Context context) {
		
		<#list search_entities as entity>
		if(currentFragmentHashCode == ${entity.name?cap_first}ListFragment.class.hashCode()){
			this.searchItem.setVisible(true);
	    }
		</#list>
		
	}

	@Override
	protected boolean dispatch(MenuItem item, Context ctx, Fragment fragment) {
		int currentActivityHashCode = ctx.getClass().hashCode();
		int currentFragmentHashCode = fragment.getClass().hashCode();
		
		Intent intent = null;
		
		<#list search_entities as entity>
		if(currentFragmentHashCode == ${entity.name?cap_first}ListFragment.class.hashCode()){
			intent = new Intent(ctx, ${entity.name?cap_first}SearchActivity.class);
	    }
		</#list>
		
		if(intent!=null){
			fragment.startActivityForResult(intent, ${project_name?cap_first}Menu.SEARCH);
			return true;
		}else{
			return false;
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode,
			Intent data, Context context, Fragment fragment) {
		if(requestCode==${project_name?cap_first}Menu.SEARCH){
			if(resultCode==HarmonyFragmentActivity.RESULT_OK){
				((HarmonyListFragment)fragment).getLoaderManager().restartLoader(0, data.getExtras(), (HarmonyListFragment)fragment);
			}
		}
	}
}

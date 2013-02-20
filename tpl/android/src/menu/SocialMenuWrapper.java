package ${project_namespace}.menu;

import com.actionbarsherlock.internal.view.menu.ActionMenuItem;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.actionbarsherlock.widget.ShareActionProvider;
<#list entities?values as entity>
	<#if entity.options.social??>
import ${entity.controller_namespace}.${entity.name?cap_first}ShowFragment;	
	</#if>
</#list>

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;

public class SocialMenuWrapper extends MenuWrapperBase{
	private MenuItem socialItem;
	private final static String menuName = "Share";

	@Override
	protected void initializeMenu(Menu menu) {
		this.socialItem 	= menu.add(0, ${project_name?cap_first}Menu.SHARE , Menu.NONE, menuName);
		this.socialItem.setShowAsAction(ActionMenuItem.SHOW_AS_ACTION_IF_ROOM|ActionMenuItem.SHOW_AS_ACTION_WITH_TEXT);
		this.socialItem.setVisible(false);
	}

	@Override
	protected void updateMenu(Menu menu, int currentActivityHashCode,
			int currentFragmentHashCode, Context context) {
			
		<#list entities?values as entity>
			<#if entity.options.social??>
		if (currentFragmentHashCode == ${entity.name?cap_first}ShowFragment.class.hashCode()){
			/** Getting the actionprovider associated with the menu item whose id is share */
	        ShareActionProvider mShareActionProvider = new ShareActionProvider(context);
	        
	        /** Getting the target intent */
	        Intent shareIntent = new Intent(Intent.ACTION_SEND);
	        shareIntent.setType("text/plain");
	        shareIntent.putExtra(Intent.EXTRA_SUBJECT, "SUBJECT");
	        shareIntent.putExtra(Intent.EXTRA_TEXT,"Sample Content !!!");
	        
	        /** Setting a share intent */
	        if (shareIntent!=null)
	            mShareActionProvider.setShareIntent(shareIntent);
			
	        this.socialItem.setActionProvider(mShareActionProvider);
	        this.socialItem.setVisible(true);
	    }
			</#if>
		</#list>
		
	}

	@Override
	protected boolean dispatch(MenuItem item, Context ctx, Fragment fragment) {
		return true;
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode,
			Intent data, Context context, Fragment fragment) {
	}
}

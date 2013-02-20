/**************************************************************************
 * ${project_name?cap_first}MenuBase.java, Android
 * 
 * Copyright 2012 TACTfactory SARL
 * Description : Global Menu Action of application
 * Author(s)   : Mickael Gaillard <mickael.gaillard@tactfactory.com> ,
 * License     : all right reserved
 * Create      : 2012
 * 
 **************************************************************************/
package ${project_namespace}.menu;

import com.actionbarsherlock.internal.view.menu.ActionMenuItem;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.actionbarsherlock.widget.ShareActionProvider;

import ${project_namespace}.HarmonyFragmentActivity;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.support.v4.app.Fragment;
import android.util.SparseArray;

public abstract class ${project_name?cap_first}MenuBase {
	public final static int SHARE			= 1;
	public final static int SEARCH			= 2;
	
	SparseArray<MenuWrapperBase> menus = new SparseArray<MenuWrapperBase>();
	
	protected Context context;
	protected Fragment fragment;
	protected String share;
	
	protected Menu menu;
	
	protected ${project_name?cap_first}MenuBase(Context context) throws Exception {
		this(context, null);
	}
	
	protected ${project_name?cap_first}MenuBase(Context context, Fragment fragment) throws Exception {
		if (context == null)
			throw new Exception("Unable to Initialise Menu Helper with no context");
		
		this.fragment	= fragment;
		this.context 	= context;
		this.menus.put(SEARCH, new SearchMenuWrapper());
		this.menus.put(SHARE, new SocialMenuWrapper());
	}
	
	/** Initialize Menu component */
	private void initializeMenu(Menu menu) {
		this.menu = menu;
		
		for(int i=0; i<this.menus.size();i++){
			this.menus.valueAt(i).initializeMenu(menu);
		}
		
	}
	
	/** Update Menu component */
	public void updateMenu(Menu menu, Context context) {
		if (context != null)
			this.context = context;

		this.initializeMenu(menu);
		this.updateMenu(menu);
	}
	
	/** Update Menu component */
	public void updateMenu(Menu menu) {
		int currentClass = this.context.getClass().hashCode();
		int currentFragment = this.fragment.getClass().hashCode();
		
		for(int i=0; i<this.menus.size();i++){
			this.menus.valueAt(i).updateMenu(menu, currentClass, currentFragment, this.context);
		}
	}
	
	/** Call intent associate to menu item selected*/
	public boolean dispatch(MenuItem item, Context context) {
		if (context != null)
			this.context = context;
		
		return this.dispatch(item);
	}
	
	/** Call intent associate to menu item selected*/
	private boolean dispatch(MenuItem item) {
		return this.menus.get(item.getItemId()).dispatch(item, this.context, this.fragment);
	}
	
	public void onActivityResult(int requestCode, int resultCode, Intent data, Context context){
		this.onActivityResult(requestCode, resultCode, data, context, null);
	}
	
	public void onActivityResult(int requestCode, int resultCode, Intent data, Context context, Fragment fragment){
		if (context != null)
			this.context = context;
		
		if (fragment != null)
			this.fragment = fragment;
		
		this.onActivityResult(requestCode, resultCode, data);
	}
	
	private void onActivityResult(int requestCode, int resultCode, Intent data){
		for(int i=0; i<this.menus.size();i++){
			this.menus.valueAt(i).onActivityResult(requestCode, resultCode, data, this.context, this.fragment);
		}
	}
}

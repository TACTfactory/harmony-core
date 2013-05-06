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

import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.util.SparseArray;

/**
 * ${project_name?cap_first}MenuBase.
 */
public abstract class ${project_name?cap_first}MenuBase {
	public static final int SHARE			= 1;
	public static final int SEARCH			= 2;
	
	protected SparseArray<MenuWrapperBase> menus = 
					new SparseArray<MenuWrapperBase>();
	
	protected Context context;
	protected Fragment fragment;
	protected String share;
	
	protected Menu menu;
	
	/**
	 * Constructor.
	 * @param context context
	 */
	protected ${project_name?cap_first}MenuBase(final Context context) 
														throws Exception {
		this(context, null);
	}
	
	/**
	 * Constructor.
	 * @param context context
	 * @param fragment parent fragment
	 */
	protected ${project_name?cap_first}MenuBase(final Context context, 
								final Fragment fragment) throws Exception {
		if (context == null) {
			throw new Exception(
					"Unable to Initialise Menu Helper with no context");
		}
		
		this.fragment	= fragment;
		this.context 	= context;
		//this.menus.put(SEARCH, new SearchMenuWrapper());
		//this.menus.put(SHARE, new SocialMenuWrapper());
	}
	
	/** Initialize Menu component. 
	 * @param menu menu
	 */
	private void initializeMenu(final Menu menu) {
		this.menu = menu;
		
		for (int i = 0; i < this.menus.size(); i++) {
			this.menus.valueAt(i).initializeMenu(menu);
		}
		
	}
	
	/** Update Menu component.  
	 * @param menu menu
	 * @param context context
	 */
	public void updateMenu(final Menu menu, final Context context) {
		if (context != null) {
			this.context = context;
		}

		this.initializeMenu(menu);
		this.updateMenu(menu);
	}
	
	/** Update Menu component. 
	 * @param menu menu 
	 */
	public void updateMenu(final Menu menu) {
		final int currentClass = this.context.getClass().hashCode();
		int currentFragment;
		if (this.fragment != null) {
			currentFragment = this.fragment.getClass().hashCode();
		} else {
			currentFragment = -1;
		}
		
		for (int i = 0; i < this.menus.size(); i++) {
			this.menus.valueAt(i).updateMenu(menu, 
					currentClass, 
					currentFragment,
					this.context);
		}
	}
	
	/** Call intent associate to menu item selected.
	 * @param item item
	 * @param context context 
	 */
	public boolean dispatch(final MenuItem item, final Context context) {
		if (context != null) {
			this.context = context;
		}
		
		return this.dispatch(item);
	}
	
	/** Call intent associate to menu item selected.
	 * @param item item 
	 */
	private boolean dispatch(final MenuItem item) {
		return this.menus.get(item.getItemId()).dispatch(item, this.context, 
				this.fragment);
	}
	
	/**
	 * Called when an activity you launched exits.
	 * @see android.app.Activity#onActivityResult
	 */
	public void onActivityResult(int requestCode, int resultCode, Intent data,
															 Context context) {
		this.onActivityResult(requestCode, resultCode, data, context, null);
	}
	
	/**
	 * Called when an activity you launched exits.
	 * @see android.app.Activity#onActivityResult
	 */
	public void onActivityResult(int requestCode, int resultCode, Intent data, 
										  Context context, Fragment fragment) {
		if (context != null) {
			this.context = context;
		}
		
		if (fragment != null) {
			this.fragment = fragment;
		}
		
		this.onActivityResult(requestCode, resultCode, data);
	}
	
	/**
	 * Called when an activity you launched exits.
	 * @see android.app.Activity#onActivityResult
	 */
	private void onActivityResult(int requestCode, int resultCode,
															     Intent data) {
		for (int i = 0; i < this.menus.size(); i++) {
			this.menus.valueAt(i).onActivityResult(requestCode, 
					resultCode, 
					data, 
					this.context, 
					this.fragment);
		}
	}
}

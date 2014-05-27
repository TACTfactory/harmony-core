/**
 * This file is part of the Harmony package.
 *
 * (c) Mickael Gaillard <mickael.gaillard@tactfactory.com>
 *
 * For the full copyright and license information, please view the LICENSE
 * file that was distributed with this source code.
 */
package com.tactfactory.harmony.generator;

import java.util.List;

import com.tactfactory.harmony.platform.IAdapter;
import com.tactfactory.harmony.updater.IUpdater;

/**
 * Utility class used to add buttons to the home activity. 
 */
public class HomeActivityGenerator extends BaseGenerator<IAdapter> {
	
	public HomeActivityGenerator(IAdapter adapter) {
	    super(adapter);
	}
	
	/**
	 * Update Class : HomeActivity.
	 * HomeActivity : add new Map button to Entity activity
	 * @param activity Activity without Entity name (GMapsActivity, OSMActivity)
	 * @param entity Entity to add
	 */
	public final void addLaunchActivityButton(
			final String activity,
			final String buttonId) {
	    List<IUpdater> updaters = this.getAdapter().getAdapterProject()
	           .updateHomeActivity(activity, buttonId);
	    
	    this.processUpdater(updaters);
	}
}

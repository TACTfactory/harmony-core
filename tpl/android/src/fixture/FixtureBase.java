<#assign fixtureType = options["fixture"].type />
package ${fixture_namespace};

import android.content.Context;
import android.content.res.AssetManager;
import java.io.InputStream;
import java.io.IOException;
import android.util.Log;


public abstract class FixtureBase<T> {	
	private static String TAG = "FixtureBase";
	protected Context context;
	public static int MODE_BASE = 0x00;
	public static int MODE_TEST = 0x01;
	
	public FixtureBase(Context context){
		this.context = context;
	}
	/**
     * Load the fixtures for the current model.
     */
	public abstract void getModelFixtures(int mode);

	/**
	 * Load data fixtures
	 */
	public abstract void load(DataManager manager);
	
	public abstract T getModelFixture(String id);

	/**
	 * Get the order of this fixture
	 * 
	 * @return index order
	 */
	public int getOrder() {
		// TODO Auto-generated method stub
		return 0;
	}

	<#if (fixtureType=="xml")>
	// Retrieve an xml file from the assets
	public InputStream getXml(String entityName){
		AssetManager assetManager = this.context.getAssets();
		InputStream ret = null;
		try {
			ret = assetManager.open(entityName+".xml");
		} catch (IOException e){
			// TODO Auto-generated method stub
			Log.e(TAG, e.getMessage());
		}
		return ret;
	}
	<#elseif (fixtureType=="yml")>
	// Retrieve an xml file from the assets
	public InputStream getYml(String entityName){
		AssetManager assetManager = this.context.getAssets();
		InputStream ret = null;
		try {
			ret = assetManager.open(entityName+".yml");
		} catch (IOException e){
			// TODO Auto-generated method stub
			Log.e(TAG, e.getMessage());
		}
		return ret;
	}
	</#if>
}

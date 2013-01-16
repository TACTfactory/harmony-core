package ${fixture_namespace};

import android.content.Context;
import android.content.res.AssetManager;
import java.io.InputStream;

public abstract class FixtureBase {	
	protected Context context;
	
	public FixtureBase(Context context){
		this.context = context;
	}
	/**
     * Load the fixtures for the current model.
     */
	public abstract void getModelFixtures();

	/**
	 * Load data fixtures
	 */
	public abstract void load(DataManager manager);
	
	public abstract Object getModelFixture(String id);

	/**
	 * Get the order of this fixture
	 * 
	 * @return index order
	 */
	public int getOrder() {
		// TODO Auto-generated method stub
		return 0;
	}

	
	// Retrieve an xml file from the assets
	public InputStream getXml(String entityName){
		AssetManager assetManager = this.context.getAssets();
		InputStream ret = null;
		try{
			ret = assetManager.open(entityName+".xml");
		}catch(Exception e){
			// TODO Auto-generated method stub
			e.printStackTrace();
		}
		return ret;
	}
}

package ${fixture_namespace};


import java.util.ArrayList;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.SparseArray;

public class DataLoader {
	public static int MODE_TEST 	= Integer.parseInt("0001", 2);
	public static int MODE_APP 	= Integer.parseInt("0010", 2);
	public static int MODE_DEBUG 	= Integer.parseInt("0100", 2);
	
	private ArrayList<FixtureBase<?>> dataLoaders;
	private static SparseArray<String> fixtureFolders;
	private Context ctx;

	
	static {
		fixtureFolders = new SparseArray<String>();
		
		// Add your own folder and mode here for new fixture cases
		fixtureFolders.put(MODE_APP, "app/");
		fixtureFolders.put(MODE_DEBUG, "debug/");
		fixtureFolders.put(MODE_TEST, "test/");
	}
	
	public DataLoader(Context ctx) {
		this.ctx = ctx;
		this.dataLoaders = new ArrayList<FixtureBase<?>>();
		<#list entities?values as entity>
			<#if (!(entity.internal?? && entity.internal=='true') && (entity.fields?size>0))>
		this.dataLoaders.add(${entity.name}DataLoader.getInstance(this.ctx));
			</#if>
		</#list>
	}
	
	public void loadData(SQLiteDatabase db, int modes){
		DataManager manager = new DataManager(this.ctx, db);
		
		for(FixtureBase<?> dataLoader : this.dataLoaders) {
			if (this.isType(modes, MODE_APP)) {
				dataLoader.getModelFixtures(MODE_APP);
			}
			if (this.isType(modes, MODE_TEST)) {
				dataLoader.getModelFixtures(MODE_TEST);
			}
			dataLoader.load(manager);
		}
	}
	
	private boolean isType(int modes, int mode) {
		if ((modes & mode) == mode) {
			return true;
		} else {
			return false;
		}
	}
	
	public static String getPathToFixtures(int mode) {
		return fixtureFolders.get(mode);
	}
}

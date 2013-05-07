<#function hasOnlyRecursiveRelations entity>
	<#list entity.relations as relation>
		<#if relation.relation.targetEntity!=entity.name> 
			<#return false>
		</#if>
	</#list>
	<#return true>
</#function>
<#function getZeroRelationsEntities>
	<#assign ret = [] />
	<#list entities?values as entity>
		<#if (entity.fields?size!=0 && hasOnlyRecursiveRelations(entity))>
			<#assign ret = ret + [entity.name]>
		</#if>
	</#list>
	<#return ret />
</#function>
<#function isInArray array val>
	<#list array as val_ref>
		<#if val_ref==val>
			<#return true />
		</#if>
	</#list>
	<#return false />
</#function>
<#function isOnlyDependantOf entity entity_list>
	<#list entity.relations as rel>
		<#if rel.relation.type=="ManyToOne">
			<#if !isInArray(entity_list, rel.relation.targetEntity)>
				<#return false />
			</#if>
		</#if>	
	</#list>
	<#return true />
</#function>
<#function orderEntitiesByRelation>
	<#assign ret = getZeroRelationsEntities() />
	<#assign maxLoop = entities?size />
	<#list 1..maxLoop as i>
		<#list entities?values as entity>	
			<#if (entity.fields?size>0)>
				<#if !isInArray(ret, entity.name)>
					<#if isOnlyDependantOf(entity, ret)>
						<#assign ret = ret + [entity.name] />
					</#if>
				</#if>
			</#if>
		</#list>
	</#list>
	<#return ret>
</#function>
<#assign orderedEntities = orderEntitiesByRelation() />
package ${fixture_namespace};

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.SparseArray;
import android.util.Log;

/**
 * DataLoader for fixture purpose.
 */
public class DataLoader {
	/** TAG for debug purpose */
	protected static final String TAG = "DataLoader";

	/** Test mode. */
	public static final int MODE_TEST 	= Integer.parseInt("0001", 2);
	/** Application mode. */
	public static final int MODE_APP 	= Integer.parseInt("0010", 2);
	/** Debug mode. */
	public static final int MODE_DEBUG 	= Integer.parseInt("0100", 2);
	/** List of DataLoaders. */ 
	private List<FixtureBase<?>> dataLoaders;
	/** List of Fixture folders. */ 
	private static SparseArray<String> fixtureFolders;
	/** Context. */
	private Context ctx;

	/**
	 * Static constructor.
	 */
	static {
		fixtureFolders = new SparseArray<String>();
		
		// Add your own folder and mode here for new fixture cases
		fixtureFolders.put(MODE_APP, "app/");
		fixtureFolders.put(MODE_DEBUG, "debug/");
		fixtureFolders.put(MODE_TEST, "test/");
	}
	
	/**
	 * Constructor.
	 * @param ctx The context
	 */
	public DataLoader(final Context ctx) {
		this.ctx = ctx;
		this.dataLoaders = new ArrayList<FixtureBase<?>>();
		<#list orderedEntities as entityName>
			<#assign entity = entities[entityName] />
			<#if (!(entity.internal?? && entity.internal=='true') && (entity.fields?size>0))>
		this.dataLoaders.add(
				${entity.name}DataLoader.getInstance(this.ctx));
			</#if>
		</#list>
	}
	
	/**
	 * LoadData from fixtures.
	 * @param db The DB to work in
	 * @param modes Mode
	 */
	public void loadData(final SQLiteDatabase db, final int modes) {
		Log.i(TAG, "Initializing fixtures.");
		final DataManager manager = new DataManager(this.ctx, db);
		for (final FixtureBase<?> dataLoader : this.dataLoaders) {
			Log.d(TAG, "Loading xxx fixtures");

			if (this.isType(modes, MODE_APP)) {
				Log.d(TAG, "Loading APP fixtures");

				dataLoader.getModelFixtures(MODE_APP);
			}
			if (this.isType(modes, MODE_DEBUG)) {
				Log.d(TAG, "Loading DEBUG fixtures");

				dataLoader.getModelFixtures(MODE_DEBUG);
			}
			if (this.isType(modes, MODE_TEST)) {
				Log.d(TAG, "Loading TEST fixtures");

				dataLoader.getModelFixtures(MODE_TEST);
			}
			dataLoader.load(manager);
		}
	}
	
	/**
	 * isType.
	 * @param modes Modes
	 * @param mode Mode
	 */
	private boolean isType(final int modes, final int mode) {
		boolean result;
		
		if ((modes & mode) == mode) {
			result = true;
		} else {
			result = false;
		}
		
		return result;
	}
	
	/**
	 * Get path to fixtures.
	 * @param mode Mode 
	 * @return A String representing the path to fixtures
	 */
	public static String getPathToFixtures(final int mode) {
		return fixtureFolders.get(mode);
	}

	/**
	 * Clean dataLoaders.
	 */
	public void clean() {
		for (FixtureBase<?> dataLoader: this.dataLoaders) {
			dataLoader.items.clear();
		}
	}
}

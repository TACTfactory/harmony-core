<#include utilityPath + "all_imports.ftl" />
<@header?interpret />
package ${curr.test_namespace}.base;

import java.util.ArrayList;

import android.os.Parcel;
import android.test.suitebuilder.annotation.SmallTest;

import ${curr.namespace}.entity.${curr.name};
<#if dataLoader?? && dataLoader>import ${fixture_namespace}.${curr.name?cap_first}DataLoader;</#if>
import ${curr.test_namespace}.utils.TestUtils;
import ${curr.test_namespace}.utils.${curr.name}Utils;

/** ${curr.name} parcellisation test class <br/>
 * <b><i>This class will be overwrited whenever you regenerate the project with Harmony.
 * You should edit ${curr.name}TestDB class instead of this one or you will lose all your modifications.</i></b>
 */
public abstract class ${curr.name}TestParcelableBase extends TestDBBase {
	/** Current tested entity. */
	protected ${curr.name} entity;

	@Override
	protected void setUp() throws Exception {
		super.setUp();

		<#if dataLoader?? && dataLoader>
		ArrayList<${curr.name?cap_first}> entities = 
				new ArrayList<${curr.name?cap_first}>(
						${curr.name?cap_first}DataLoader.getInstance(
								this.getContext()).getMap().values());
		if (entities.size()>0){
			this.entity = entities.get(TestUtils.generateRandomInt(0,entities.size()-1));
		}
		</#if>
	}

	/** Test case Create Entity */
	@SmallTest
	public void testParcellisation() {
		if (this.entity != null) {
			Parcel parcel = Parcel.obtain();			
			parcel.writeParcelable(this.entity, 0);
			parcel.setDataPosition(0);
			${curr.name} deparcelled = parcel.readParcelable(
					${curr.name}.class.getClassLoader());
			${curr.name}Utils.equals(this.entity, deparcelled);
		}
	}
}

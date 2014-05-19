<#include utilityPath + "all_imports.ftl" />
<@header?interpret />
package ${curr.test_namespace}.base;

import android.test.suitebuilder.annotation.SmallTest;

import ${project_namespace}.provider.${curr.name?cap_first}ProviderAdapter;
import ${project_namespace}.provider.utils.${curr.name?cap_first}ProviderUtils;
import ${project_namespace}.provider.contract.${curr.name?cap_first}Contract;
<#if (InheritanceUtils.isExtended(curr))>
import ${project_namespace}.provider.contract.${curr.inheritance.superclass.name?cap_first}Contract;
</#if>

import ${curr.namespace}.data.${curr.name}SQLiteAdapter;
<#if (InheritanceUtils.isExtended(curr))>import ${data_namespace}.${curr.inheritance.superclass.name}SQLiteAdapter;</#if>
import ${curr.namespace}.entity.${curr.name};
<#list curr_relations as relation><#if ((relation.relation.type == "ManyToMany" || relation.relation.type == "OneToMany") && (!MetadataUtils.getInversingField(relation)?? || !MetadataUtils.getInversingField(relation).nullable))>import ${project_namespace}.entity.${relation.relation.targetEntity};
</#if></#list>

<#if dataLoader?? && dataLoader>
	<#list InheritanceUtils.getAllChildren(curr) as child>
import ${fixture_namespace}.${child.name?cap_first}DataLoader;
	</#list>
</#if>

import java.util.ArrayList;
import ${curr.test_namespace}.utils.*;


import android.content.ContentResolver;
import android.content.ContentValues;


import android.net.Uri;

import junit.framework.Assert;

/** ${curr.name} database test abstract class <br/>
 * <b><i>This class will be overwrited whenever you regenerate the project with Harmony.
 * You should edit ${curr.name}TestDB class instead of this one or you will lose all your modifications.</i></b>
 */
public abstract class ${curr.name}TestProviderBase extends TestDBBase {
	protected android.content.Context ctx;

	protected ${curr.name}SQLiteAdapter adapter;

	protected ${curr.name} entity;
	protected ContentResolver provider;
	protected ${curr.name}ProviderUtils providerUtils;

	protected ArrayList<${curr.name}> entities;

	protected int nbEntities = 0;
	/* (non-Javadoc)
	 * @see junit.framework.TestCase#setUp()
	 */
	protected void setUp() throws Exception {
		super.setUp();

		this.ctx = this.getContext();

		this.adapter = new ${curr.name}SQLiteAdapter(this.ctx);

		<#if dataLoader?? && dataLoader>
		this.entities = new ArrayList<${curr.name?cap_first}>();		
		<#list InheritanceUtils.getAllChildren(curr) as child>
		this.entities.addAll(${child.name?cap_first}DataLoader.getInstance(this.ctx).getMap().values());
		</#list>
		if (this.entities.size()>0) {
			this.entity = this.entities.get(TestUtils.generateRandomInt(0,entities.size()-1));
		}

		<#list InheritanceUtils.getAllChildren(curr) as child>
		this.nbEntities += ${child.name?cap_first}DataLoader.getInstance(this.ctx).getMap().size();
		</#list>
		</#if>
		this.provider = this.getContext().getContentResolver();
		this.providerUtils = new ${curr.name}ProviderUtils(this.getContext());
	}

	/* (non-Javadoc)
	 * @see junit.framework.TestCase#tearDown()
	 */
	protected void tearDown() throws Exception {
		super.tearDown();
	}

	/********** Direct Provider calls. *******/

	/** Test case Create Entity */
	@SmallTest
	public void testCreate() {
		Uri result = null;
		if (this.entity != null) {
			${curr.name} ${curr.name?uncap_first} = ${curr.name?cap_first}Utils.generateRandom(this.ctx);

			try {
				ContentValues values = ${curr.name}Contract.${curr.name}.itemToContentValues(${curr.name?uncap_first}<#list curr.relations as relation><#if relation.relation.type=="ManyToOne" && relation.internal>, 0</#if></#list>);
				<#list curr_ids as id>
					<#if id.strategy == "IDENTITY">
				values.remove(${ContractUtils.getContractCol(id)});
					</#if>
				</#list>
				result = this.provider.insert(${curr.name?cap_first}ProviderAdapter.${curr.name?upper_case}_URI, values);

			} catch (Exception e) {
				e.printStackTrace();
			}

			Assert.assertNotNull(result);
			<#assign idGetters = IdsUtils.getAllIdsGetters(curr) />
			<#list curr_ids as id>
				<#list IdsUtils.getAllIdsTypesFromArray([id]) as idType>
					<#assign varName = "result.getPathSegments().get(${id_index + 1})" />
					<#if id.strategy == "IDENTITY">
			Assert.assertTrue(${FieldsUtils.getStringParser(idType, varName)}<#if MetadataUtils.isPrimitiveType(idType)> > <#else>.equals(</#if>0)<#if !MetadataUtils.isPrimitiveType(idType)>)</#if>;		
					<#else>
			Assert.assertTrue(${FieldsUtils.getStringParser(idType, varName)}<#if MetadataUtils.isPrimitiveType(idType)> == <#else>.equals(</#if>${curr.name?uncap_first}${idGetters[id_index]})<#if !MetadataUtils.isPrimitiveType(idType)>)</#if>;
					</#if>
				</#list>
			</#list>
			
		}
	}

	/** Test case Read Entity */
	@SmallTest
	public void testRead() {
		${curr.name} result = null;

		if (this.entity != null) {
			try {
				android.database.Cursor c = this.provider.query(Uri.parse(
						${curr.name?cap_first}ProviderAdapter.${curr.name?upper_case}_URI<#list IdsUtils.getAllIdsGetters(curr) as id>
								+ "/" 
								+ this.entity${id}</#list>),
						this.adapter.getCols(),
						null,
						null,
						null);
				c.moveToFirst();
				result = ${curr.name}Contract.${curr.name}.cursorToItem(c);
				c.close();
			} catch (Exception e) {
				e.printStackTrace();
			}

			${curr.name}Utils.equals(this.entity, result);
		}
	}

	/** Test case ReadAll Entity */
	@SmallTest
	public void testReadAll() {
		ArrayList<${curr.name}> result = null;
		try {
			android.database.Cursor c = this.provider.query(${curr.name?cap_first}ProviderAdapter.${curr.name?upper_case}_URI, this.adapter.getCols(), null, null, null);
			result = ${curr.name}Contract.${curr.name}.cursorToItems(c);
			c.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

		Assert.assertNotNull(result);
		if (result != null) {
			Assert.assertEquals(result.size(), this.nbEntities);
		}
	}

	/** Test case Update Entity */
	@SmallTest
	public void testUpdate() {
		int result = -1;
		if (this.entity != null) {
			${curr.name} ${curr.name?uncap_first} = ${curr.name?cap_first}Utils.generateRandom(this.ctx);

			try {
				<#list curr_ids as id>
				${curr.name?uncap_first}.set${id.name?cap_first}(this.entity.get${id.name?cap_first}());
				</#list>
				<#list curr_relations as relation>
					<#if ((relation.relation.type == "ManyToMany" || relation.relation.type == "OneToMany") && (!MetadataUtils.getInversingField(relation)?? || !MetadataUtils.getInversingField(relation).nullable))>
				if (this.entity.get${relation.name?cap_first}() != null) {
					${curr.name?uncap_first}.get${relation.name?cap_first}().addAll(this.entity.get${relation.name?cap_first}());
				}
					</#if>
				</#list>

				ContentValues values = ${curr.name}Contract.${curr.name}.itemToContentValues(${curr.name?uncap_first}<#list curr.relations as relation><#if relation.relation.type=="ManyToOne" && relation.internal>, 0</#if></#list>);
				result = this.provider.update(
					Uri.parse(${curr.name?cap_first}ProviderAdapter.${curr.name?upper_case}_URI<#list IdsUtils.getAllIdsGetters(curr) as id>
						+ "/"
						+ ${curr.name?uncap_first}${id}</#list>),
					values,
					null,
					null);

			} catch (Exception e) {
				e.printStackTrace();
			}

			Assert.assertTrue(result > 0);
		}
	}

	/** Test case UpdateAll Entity */
	@SmallTest
	public void testUpdateAll() {
		int result = -1;
		if (this.entities.size() > 0) {
			${curr.name} ${curr.name?uncap_first} = ${curr.name?cap_first}Utils.generateRandom(this.ctx);

			try {
				ContentValues values = ${curr.name}Contract.${curr.name}.itemToContentValues(${curr.name?uncap_first}<#list curr.relations as relation><#if relation.relation.type=="ManyToOne" && relation.internal>, 0</#if></#list>);
				<#list IdsUtils.getAllIdsColsFromArray(curr_ids) as id>
				values.remove(${id});
				</#list>
				<#list ViewUtils.getAllFields(curr)?values as field>
					<#if field.unique?? && field.unique>
				values.remove(${field.owner}Contract.${field.owner}.COL_${field.name?upper_case});
					</#if>
				</#list>

				result = this.provider.update(${curr.name?cap_first}ProviderAdapter.${curr.name?upper_case}_URI, values, null, null);
			} catch (Exception e) {
				e.printStackTrace();
			}

			Assert.assertEquals(result, this.nbEntities);
		}
	}

	/** Test case Delete Entity */
	@SmallTest
	public void testDelete() {
		int result = -1;
		if (this.entity != null) {
			try {
				result = this.provider.delete(
						Uri.parse(${curr.name?cap_first}ProviderAdapter.${curr.name?upper_case}_URI<#list IdsUtils.getAllIdsGetters(curr) as id>
							+ "/" 
							+ this.entity${id}</#list>),
						null,
						null);

			} catch (Exception e) {
				e.printStackTrace();
			}
			Assert.assertTrue(result >= 0);
		}

	}

	/** Test case DeleteAll Entity */
	@SmallTest
	public void testDeleteAll() {
		int result = -1;
		if (this.entities.size() > 0) {

			try {
				result = this.provider.delete(${curr.name?cap_first}ProviderAdapter.${curr.name?upper_case}_URI, null, null);

			} catch (Exception e) {
				e.printStackTrace();
			}

			Assert.assertEquals(result, this.nbEntities);
		}
	}

	/****** Provider Utils calls ********/

	/** Test case Read Entity by provider utils. */
	@SmallTest
	public void testUtilsRead() {
		${curr.name} result = null;

		if (this.entity != null) {
			result = this.providerUtils.query(this.entity);

			${curr.name}Utils.equals(this.entity, result);
		}
	}

	/** Test case ReadAll Entity by provider utils. */
	@SmallTest
	public void testUtilsReadAll() {
		ArrayList<${curr.name}> result = null;
		result = this.providerUtils.queryAll();

		Assert.assertNotNull(result);
		if (result != null) {
			Assert.assertEquals(result.size(), this.nbEntities);
		}
	}

	/** Test case Update Entity by provider utils. */
	@SmallTest
	public void testUtilsUpdate() {
		int result = -1;
		if (this.entity != null) {
			${curr.name} ${curr.name?uncap_first} = ${curr.name?cap_first}Utils.generateRandom(this.ctx);

			<#list curr_ids as id>
			${curr.name?uncap_first}.set${id.name?cap_first}(this.entity.get${id.name?cap_first}());
			</#list>
			<#list curr_relations as relation>
				<#if ((relation.relation.type == "ManyToMany" || relation.relation.type == "OneToMany") && (!MetadataUtils.getInversingField(relation)?? || !MetadataUtils.getInversingField(relation).nullable))>
			if (this.entity.get${relation.name?cap_first}() != null) {
				for (${relation.relation.targetEntity} ${relation.name} : this.entity.get${relation.name?cap_first}()) {
					boolean found = false;
					for (${relation.relation.targetEntity} ${relation.name}2 : ${curr.name?uncap_first}.get${relation.name?cap_first}()) {
						if (<#list IdsUtils.getAllIdsGetters(entities[relation.relation.targetEntity]) as id>${relation.name}${id}<#if MetadataUtils.isPrimitive(entities[relation.relation.targetEntity].ids[id_index])> == <#else>.equals(</#if>${relation.name}2${id}<#if !MetadataUtils.isPrimitive(entities[relation.relation.targetEntity].ids[id_index])>)</#if><#if id_has_next>
							 && </#if></#list> ) {
							found = true;
							break;
						}
					}					
					if(!found) {
						${curr.name?uncap_first}.get${relation.name?cap_first}().add(${relation.name});
					}
				}
			}
				</#if>
			</#list>
			result = this.providerUtils.update(${curr.name?uncap_first});

			Assert.assertTrue(result > 0);
		}
	}


	/** Test case Delete Entity by provider utils. */
	@SmallTest
	public void testUtilsDelete() {
		int result = -1;
		if (this.entity != null) {
			result = this.providerUtils.delete(this.entity);
			Assert.assertTrue(result >= 0);
		}

	}
}

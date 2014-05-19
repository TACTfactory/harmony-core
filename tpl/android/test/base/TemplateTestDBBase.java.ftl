<#include utilityPath + "all_imports.ftl" />
<@header?interpret />
package ${curr.test_namespace}.base;

import java.util.ArrayList;

import android.test.suitebuilder.annotation.SmallTest;

import ${curr.namespace}.data.${curr.name}SQLiteAdapter;
import ${curr.namespace}.entity.${curr.name};

<#if dataLoader?? && dataLoader>
    <#list InheritanceUtils.getAllChildren(curr) as child>
import ${fixture_namespace}.${child.name?cap_first}DataLoader;
    </#list>
</#if>

import ${curr.test_namespace}.utils.*;

import junit.framework.Assert;

/** ${curr.name} database test abstract class <br/>
 * <b><i>This class will be overwrited whenever you regenerate the project with Harmony.
 * You should edit ${curr.name}TestDB class instead of this one or you will lose all your modifications.</i></b>
 */
public abstract class ${curr.name}TestDBBase extends TestDBBase {
    protected android.content.Context ctx;

    protected ${curr.name}SQLiteAdapter adapter;

    protected ${curr.name} entity;
    protected ArrayList<${curr.name}> entities;
    protected int nbEntities = 0;
    /* (non-Javadoc)
     * @see junit.framework.TestCase#setUp()
     */
    protected void setUp() throws Exception {
        super.setUp();

        this.ctx = this.getContext();

        this.adapter = new ${curr.name}SQLiteAdapter(this.ctx);
        this.adapter.open();

        <#if dataLoader?? && dataLoader>
        this.entities = new ArrayList<${curr.name?cap_first}>();        
        <#list InheritanceUtils.getAllChildren(curr) as child>
        this.entities.addAll(${child.name?cap_first}DataLoader.getInstance(this.ctx).getMap().values());
        </#list>
        if (entities.size()>0){
            this.entity = this.entities.get(TestUtils.generateRandomInt(0,entities.size()-1));
        }

        <#list InheritanceUtils.getAllChildren(curr) as child>
        this.nbEntities += ${child.name?cap_first}DataLoader.getInstance(this.ctx).getMap().size();
        </#list>
        </#if>
    }

    /* (non-Javadoc)
     * @see junit.framework.TestCase#tearDown()
     */
    protected void tearDown() throws Exception {
        this.adapter.close();

        super.tearDown();
    }

    /** Test case Create Entity */
    @SmallTest
    public void testCreate() {
        int result = -1;
        if (this.entity != null) {
            ${curr.name?cap_first} ${curr.name?uncap_first} = ${curr.name?cap_first}Utils.generateRandom(this.ctx);

            result = (int)this.adapter.insert(${curr.name?uncap_first});

            Assert.assertTrue(result >= 0);
        }
    }

    /** Test case Read Entity */
    @SmallTest
    public void testRead() {
        ${curr.name?cap_first} result = null;
        if (this.entity != null) {
            result = this.adapter.getByID(<#list curr_ids as id>this.entity.get${id.name?cap_first}()<#if id_has_next>,
                    </#if></#list>);

            ${curr.name?cap_first}Utils.equals(this.entity, result);
        }
    }

    /** Test case Update Entity */
    @SmallTest
    public void testUpdate() {
        int result = -1;
        if (this.entity != null) {
            ${curr.name?cap_first} ${curr.name?uncap_first} = ${curr.name?cap_first}Utils.generateRandom(this.ctx);
            <#list curr_ids as id>
            ${curr.name?uncap_first}.set${id.name?cap_first}(this.entity.get${id.name?cap_first}());
            </#list>

            result = (int) this.adapter.update(${curr.name?uncap_first});

            Assert.assertTrue(result >= 0);
        }
    }

    /** Test case Update Entity */
    @SmallTest
    public void testDelete() {
        int result = -1;
        if (this.entity != null) {
            result = (int) this.adapter.remove(<#list curr_ids as id>this.entity.get${id.name?cap_first}()<#if id_has_next>,
                    </#if></#list>);
            Assert.assertTrue(result >= 0);
        }
    }
    
    /** Test the get all method. */
    @SmallTest
    public void testAll() {
        int result = this.adapter.getAll().size();
        int expectedSize = this.nbEntities;
        Assert.assertEquals(expectedSize, result);
    }
}

package ${project_namespace}.test.base;

import ${project_namespace}.criterias.base.CriteriaExpression;
import ${project_namespace}.criterias.base.Criterion;
import ${project_namespace}.criterias.base.CriteriaExpression.GroupType;
import ${project_namespace}.criterias.base.Criterion.Type;
import ${project_namespace}.criterias.base.value.ArrayValue;
import ${project_namespace}.criterias.base.value.DateTimeValue;
import ${project_namespace}.criterias.base.value.MethodValue;
import ${project_namespace}.criterias.base.value.SelectValue;

import android.test.suitebuilder.annotation.SmallTest;

public class TestCriteriaBase extends TestDBBase {

    @SmallTest
    public void testStringValue() {
        CriteriaExpression andExpression = new CriteriaExpression(GroupType.AND);
        CriteriaExpression orExpression = new CriteriaExpression(GroupType.OR);
        andExpression.add("key", "value");
        andExpression.add("key2", "value2");
        orExpression.add("key3", "value3");
        orExpression.add("key4", "value4");
        andExpression.add(orExpression);
        assertEquals("((key = ?) AND (key2 = ?) AND ((key3 = ?) OR (key4 = ?)))", andExpression.toSQLiteSelection());
        assertEquals("value", andExpression.toSQLiteSelectionArgs()[0]);
        assertEquals("value2", andExpression.toSQLiteSelectionArgs()[1]);
        assertEquals("value3", andExpression.toSQLiteSelectionArgs()[2]);
        assertEquals("value4", andExpression.toSQLiteSelectionArgs()[3]);
    }

    @SmallTest
    public void testArrayValue() {
        CriteriaExpression andExpression = new CriteriaExpression(GroupType.AND);
        ArrayValue inValue = new ArrayValue();
        for (int i = 1; i <= 10; i ++) {
            inValue.addValue("value" + i);
        }
        Criterion crit = new Criterion();
        crit.setKey("key");
        crit.setType(Type.IN);
        crit.addValue(inValue);
        andExpression.add(crit);
        assertEquals("((key IN (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)))", andExpression.toSQLiteSelection());
        assertEquals("value1", andExpression.toSQLiteSelectionArgs()[0]);
        assertEquals("value2", andExpression.toSQLiteSelectionArgs()[1]);
        assertEquals("value3", andExpression.toSQLiteSelectionArgs()[2]);
        assertEquals("value4", andExpression.toSQLiteSelectionArgs()[3]);
        assertEquals("value5", andExpression.toSQLiteSelectionArgs()[4]);
        assertEquals("value6", andExpression.toSQLiteSelectionArgs()[5]);
        assertEquals("value7", andExpression.toSQLiteSelectionArgs()[6]);
        assertEquals("value8", andExpression.toSQLiteSelectionArgs()[7]);
        assertEquals("value9", andExpression.toSQLiteSelectionArgs()[8]);
        assertEquals("value10", andExpression.toSQLiteSelectionArgs()[9]);
    }
    
    @SmallTest
    public void testDateTimeValue() {
        CriteriaExpression andExpression = new CriteriaExpression(GroupType.AND);
        DateTimeValue value = new DateTimeValue(DateTimeValue.Type.DATETIME, "value");
        Criterion crit = new Criterion();
        crit.setType(Type.SUPERIOR);
        crit.setDateTimeKey("key");
        crit.addValue(value);
        andExpression.add(crit);
        
        assertEquals("((datetime(key) > datetime(?)))", andExpression.toSQLiteSelection());
        assertEquals("value", andExpression.toSQLiteSelectionArgs()[0]);
    }

    @SmallTest
    public void testMethodValue() {
        CriteriaExpression andExpression = new CriteriaExpression(GroupType.AND);
        MethodValue value = new MethodValue("sqlite_method", "value1", "value2");
        Criterion crit = new Criterion();
        crit.setType(Type.LIKE);
        crit.setKey("key");
        crit.addValue(value);
        andExpression.add(crit);
        
        assertEquals("((key LIKE sqlite_method(?, ?)))", andExpression.toSQLiteSelection());
        assertEquals("value1", andExpression.toSQLiteSelectionArgs()[0]);
        assertEquals("value2", andExpression.toSQLiteSelectionArgs()[1]);
    }

    @SmallTest
    public void testSelectValue() {
        CriteriaExpression subExpression = new CriteriaExpression(GroupType.OR);
        subExpression.add("refField1", "value1");
        subExpression.add("refField2", "value2");
        CriteriaExpression andExpression = new CriteriaExpression(GroupType.AND);
        SelectValue value = new SelectValue();
        value.setRefKey("refKey");
        value.setRefTable("refTable");
        value.setCriteria(subExpression);
        Criterion crit = new Criterion();
        crit.setType(Type.IN);
        crit.setKey("key");
        crit.addValue(value);
        andExpression.add(crit);
        
        assertEquals("((key IN (SELECT refKey FROM refTable WHERE ((refField1 = ?) OR (refField2 = ?)))))", andExpression.toSQLiteSelection());
        assertEquals("value1", andExpression.toSQLiteSelectionArgs()[0]);
        assertEquals("value2", andExpression.toSQLiteSelectionArgs()[1]);
    }
}


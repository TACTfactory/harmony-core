<@header?interpret />
package ${project_namespace}.criterias.base.value;

import java.util.ArrayList;

import ${project_namespace}.criterias.base.CriteriasBase;


/** A SelectValue is used to query another table in a criteria.
 * (Use only with IN type criterias)
 * Example : SELECT * FROM table1 WHERE id IN (
 *		SELECT refKey FROM refTable WHERE criteria);
 */ 
public class SelectValue extends CriteriaValue {
	/** The table referenced by this SelectValue. */
	private String refTable;
	/** The field in refTable that will match this criteria's key. */
	private String refKey;
	/** A criteria of the refTable type. */
	private CriteriasBase criteria;
	
	/**
	 * @return the refTable
	 */
	public final String getRefTable() {
		return this.refTable;
	}

	/**
	 * @param refTable the refTable to set
	 */
	public final void setRefTable(String refTable) {
		this.refTable = refTable;
	}

	/**
	 * @return the refKey
	 */
	public final String getRefKey() {
		return this.refKey;
	}

	/**
	 * @param refKey the refKey to set
	 */
	public final void setRefKey(String refKey) {
		this.refKey = refKey;
	}

	/**
	 * @return the criteria
	 */
	public final CriteriasBase getCriteria() {
		return this.criteria;
	}

	/**
	 * @param criteria the criteria to set
	 */
	public final void setCriteria(CriteriasBase criteria) {
		this.criteria = criteria;
	}

	@Override
	public String toSQLiteString() {
		return 	"(SELECT " + this.refKey 
				+ " FROM " + this.refTable 
				+ " WHERE " + this.criteria.toSQLiteString()
				+ ")";
	}

	@Override
	public String toSQLiteSelection() {
		return 	"(SELECT " + this.refKey 
				+ " FROM " + this.refTable 
				+ " WHERE " + this.criteria.toSQLiteSelection()
				+ ")";
	}

	@Override
	public void toSQLiteSelectionArgs(final ArrayList<String> array) {
		this.criteria.toSQLiteSelectionArgs(array);
		
	}

}

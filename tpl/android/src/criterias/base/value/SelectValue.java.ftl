package ${project_namespace}.criterias.base.value;

import java.util.ArrayList;

import ${project_namespace}.criterias.base.CriteriasBase;


public class SelectValue extends CriteriaValue {
	private String refTable;
	private String refKey;
	private CriteriasBase criteria;
	
	/**
	 * @return the refTable
	 */
	public final String getRefTable() {
		return refTable;
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
		return refKey;
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
		return criteria;
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
	public void toSQLiteSelectionArgs(ArrayList<String> array) {
		this.criteria.toSQLiteSelectionArgs(array);
		
	}

}

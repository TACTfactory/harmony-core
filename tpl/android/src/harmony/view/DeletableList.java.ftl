package ${project_namespace}.harmony.view;

/** 
 * Interface for a list where items can be deleted.
 */
public interface DeletableList {
	/**
	 * Delete the selected item.
	 * @param id The id of the item.
	 */
	void delete(int id);
}

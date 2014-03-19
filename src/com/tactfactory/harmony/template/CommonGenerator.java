package com.tactfactory.harmony.template;

import com.tactfactory.harmony.meta.EntityMetadata;
import com.tactfactory.harmony.plateforme.BaseAdapter;

/**
 * Generator class for common commands. (Generate static view, etc.)
 */
public class CommonGenerator extends BaseGenerator {

	/**
	 * Constructor.
	 * 
	 * @param adapt The adapter to use
	 */
	public CommonGenerator(BaseAdapter adapt) {
		super(adapt);
	}

	
	/**
	 * Generates a static view in the project.
	 * 
	 * @param packageName the package name for the view
	 * @param viewName The view name
	 * @param type The type of the view
	 * @param linkedEntity The linked entity if any
	 */
	public void generateStaticView(
			final String packageName,
			final String viewName,
			final ViewType type,
			final EntityMetadata linkedEntity) {
		
		
	}
	
	/**
	 * ViewType.
	 * Used for static view generation. The view type will define the type of
	 * view to generate.
	 */
	public enum ViewType {
		/** Create view. (contains activity and fragment with async task) */
		CREATE(1, "Create"),
		/** Show view. (contains activity and fragment with loader) */
		SHOW(2, "Show"),
		/** Edit view. (contains activity and fragment with async task) */
		EDIT(3, "Edit"),
		/** List view. (contains activity, fragment, adapter and loader) */
		LIST(4, "List"),
		/** Grid view. (contains activity, fragment, adapter and loader) */
		GRID(5, "Grid"),
		/** Empty view. (contains only activity and fragment) */
		EMPTY(6, "Empty");
		
		/** Id used to retrieve the view type. */
		private int id;
		
		/** Choice name. */
		private String choiceString;
		
		/**
		 * Private constructor for enum.
		 * @param id The id of the view type
		 */
		private ViewType(int id, String choiceString) {
			this.id = id;
			this.choiceString = choiceString;
		}
		
		/**
		 * Gets a view type from its id.
		 * @param id The id of the view type
		 * @return The found view type or null if none found
		 */
		public static ViewType fromId(int id) {
			ViewType result = null;
			for (ViewType value : ViewType.values()) {
				if (id == value.getId()) {
					result = value;
				}
			}
			return result;
		}
		
		/**
		 * Get the id of the viewtype.
		 * @return The id
		 */
		public int getId() {
			return this.id;
		}
		
		/**
		 * Returns the choice string to be displayed in the user input.
		 * (ie. "1. Create"
		 * @return The choice string
		 */
		public String getChoiceString() {
			return String.format("%1$s. %2$s", this.id, this.choiceString);
		}
	}
}

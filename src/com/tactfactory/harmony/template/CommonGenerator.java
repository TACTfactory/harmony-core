/**
 * This file is part of the Harmony package.
 *
 * (c) Mickael Gaillard <mickael.gaillard@tactfactory.com>
 *
 * For the full copyright and license information, please view the LICENSE
 * file that was distributed with this source code.
 */
package com.tactfactory.harmony.template;

import com.tactfactory.harmony.meta.ApplicationMetadata;
import com.tactfactory.harmony.meta.EntityMetadata;
import com.tactfactory.harmony.plateforme.IAdapter;
import com.tactfactory.harmony.plateforme.android.AndroidAdapter;
import com.tactfactory.harmony.template.androidxml.ManifestUpdater;

/**
 * Generator class for common commands. (Generate static view, etc.)
 */
public class CommonGenerator extends BaseGenerator {

	/**
	 * Constructor.
	 * 
	 * @param adapter The adapter to use
	 */
	public CommonGenerator(IAdapter adapter) {
		super(adapter);
		this.setDatamodel(ApplicationMetadata.INSTANCE.toMap(adapter));
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
		
		this.getDatamodel().put("viewPackage", packageName);
		this.getDatamodel().put("viewName", viewName);
		
		ManifestUpdater updater = new ManifestUpdater(this.getAdapter());
		
		if (type.equals(ViewType.EMPTY)) {
			this.makeSourceControler(
					packageName,
					"TemplateStaticFragment.java",
					viewName + "Fragment.java");

			this.makeSourceControler(
					packageName,
					"TemplateStaticActivity.java",
					viewName + "Activity.java");
			
			this.makeResourceLayout(
					"fragment_template_static.xml",
					"fragment_" + viewName.toLowerCase() + ".xml");
			
			this.makeResourceLayout(
					"activity_template_static.xml",
					"activity_" + viewName.toLowerCase() + ".xml");
			
			updater.addActivity(
					this.getAppMetas().getProjectNameSpace(),
					"Activity",
					viewName,
					packageName);
		}
		
		updater.save();
		
	}
	
	/** Make Java Source Code.
	 *
	 * @param template Template path file.
	 *		For list activity is "TemplateListActivity.java"
	 * @param filename The destination file name.
	 */
	private void makeSourceControler(
			final String packageName,
			final String template,
			final String filename) {
		
		final String fullFilePath = String.format("%s%s/view/%s/%s",
						this.getAdapter().getSourcePath(),
						ApplicationMetadata.INSTANCE.getProjectNameSpace(),
						packageName.replace('.', '/'),
						filename);
		final String fullTemplatePath = String.format("%s%s",
				this.getAdapter().getTemplateSourceControlerPath(),
				template);
	
		super.makeSource(fullTemplatePath, fullFilePath, false);
	}
	
	/**
	 * Make Resource file.
	 *
	 * @param template Template path file.
	 * @param filename Resource file.
	 * 	prefix is type of view "row_" or "activity_" or "fragment_" with
	 *	postfix is type of action and extension file :
	 *		"_list.xml" or "_edit.xml".
	 */
	private void makeResourceLayout(final String template,
			final String filename) {
		final String fullFilePath = String.format("%s/%s",
									((AndroidAdapter)this.getAdapter()).getRessourceLayoutPath(),
									filename);
		final String fullTemplatePath = String.format("%s/%s",
		        ((AndroidAdapter)this.getAdapter()).getTemplateRessourceLayoutPath(),
				template);

		super.makeSource(fullTemplatePath, fullFilePath, false);
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

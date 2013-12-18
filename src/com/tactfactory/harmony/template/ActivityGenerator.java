/**
 * This file is part of the Harmony package.
 *
 * (c) Mickael Gaillard <mickael.gaillard@tactfactory.com>
 *
 * For the full copyright and license information, please view the LICENSE
 * file that was distributed with this source code.
 */
package com.tactfactory.harmony.template;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Locale;

import com.tactfactory.harmony.Context;
import com.tactfactory.harmony.Harmony;
import com.tactfactory.harmony.annotation.Column.Type;
import com.tactfactory.harmony.meta.ApplicationMetadata;
import com.tactfactory.harmony.meta.ClassMetadata;
import com.tactfactory.harmony.meta.EntityMetadata;
import com.tactfactory.harmony.meta.FieldMetadata;
import com.tactfactory.harmony.meta.TranslationMetadata;
import com.tactfactory.harmony.meta.TranslationMetadata.Group;
import com.tactfactory.harmony.plateforme.BaseAdapter;
import com.tactfactory.harmony.template.androidxml.AttrsFile;
import com.tactfactory.harmony.template.androidxml.ColorsFile;
import com.tactfactory.harmony.template.androidxml.DimensFile;
import com.tactfactory.harmony.template.androidxml.ManifestUpdater;
import com.tactfactory.harmony.template.androidxml.StylesFile;
import com.tactfactory.harmony.utils.ConsoleUtils;
import com.tactfactory.harmony.utils.PackageUtils;

import freemarker.template.TemplateException;

/**
 * Generate the CRUD activities.
 */
public class ActivityGenerator extends BaseGenerator {
	/** "template". */
	private static final String LOWER_TEMPLATE = "template";
	/** "Template". */
	private static final String TEMPLATE = "Template";

	/** The local namespace. */
	private String localNameSpace;

	/** Are the entities writable ? */
	private boolean isWritable = true;

	/** Has the project a date ? */
	private boolean isDate;

	/** Has the project a time ? */
	private boolean isTime;
	
	private ManifestUpdater manifestUpdater;

	/**
	 * Constructor.
	 * @param adapter The adapter to use
	 * @throws Exception if adapter is null
	 */
	public ActivityGenerator(final BaseAdapter adapter) throws Exception {
		this(adapter, true);
		this.manifestUpdater = new ManifestUpdater(this.getAdapter());
		// Make entities
		for (final EntityMetadata meta
				: this.getAppMetas().getEntities().values()) {
			if (meta.hasFields() && !meta.isInternal()) {
				// copy Widget
				if (this.isDate && this.isTime) {
					break;
				} else {
					for (final FieldMetadata field
							: meta.getFields().values()) {
						final String type = field.getHarmonyType();
						if (!this.isDate && (
								type.equals(Type.DATE.getValue())
								|| type.equals(Type.DATETIME.getValue()))) {
							this.isDate = true;
						}

						if (!this.isTime && (
								type.equals(Type.TIME.getValue())
								|| type.equals(Type.DATETIME.getValue()))) {
							this.isTime = true;
						}
					}
				}
			}
		}
	}

	/**
	 * Constructor.
	 * @param adapter The adapter to use.
	 * @param writable Are the entities writable ? (default to true)
	 * @throws Exception if adapter is null
	 */
	public ActivityGenerator(final BaseAdapter adapter,
			final Boolean writable) throws Exception {
		super(adapter);

		this.isWritable = writable;
		this.setDatamodel(
				ApplicationMetadata.INSTANCE.toMap(this.getAdapter()));
	}

	/**
	 * Generate all activities for every entity.
	 */
	public final void generateAll() {
		ConsoleUtils.display(">> Generate CRUD view...");

		for (final EntityMetadata cm
				: this.getAppMetas().getEntities().values()) {
			if (!cm.isInternal() 
					&& cm.hasFields()
					&& !cm.isHidden()) {
				
				cm.makeString("label");
				this.getDatamodel().put(
						TagConstant.CURRENT_ENTITY, cm.getName());
				this.localNameSpace =
						this.getAdapter().getNameSpace(cm,
								this.getAdapter().getController())
						+ "."
						+ cm.getName().toLowerCase(Locale.ENGLISH);
				this.generateAllAction(cm.getName());
			}
		}

		this.updateLibrary("universal-image-loader-1.8.6-with-sources.jar");
		this.updateLibrary("ImageViewTouch.jar");
		this.updateWidget("ProgressImageLoaderListener.java");

		this.updateWidget("ValidationButtons.java",
				"widget_validation_buttons.xml");

		this.updateWidget("EnumSpinner.java");
		
		String pinnedHeaderFolder = String.format("%s/%s/%s/%s/%s/%s/",
				Harmony.getProjectPath(),
				this.getAdapter().getPlatform(),
				this.getAdapter().getSource(),
				"com",
				"google",
				"android");
		
		this.updateWidget("pinnedheader/AutoScrollListView.java",
				null,
				pinnedHeaderFolder);
		this.updateWidget("pinnedheader/SelectionItemView.java",
				null,
				pinnedHeaderFolder);
		this.updateWidget("pinnedheader/headerlist/HeaderAdapter.java",
				null,
				pinnedHeaderFolder);
		this.updateWidget("pinnedheader/headerlist/HeaderSectionIndexer.java",
				null,
				pinnedHeaderFolder);
		this.updateWidget("pinnedheader/headerlist/ListPinnedHeaderView.java",
				"directory_header.xml",
				pinnedHeaderFolder);
		this.updateWidget("pinnedheader/headerlist/PinnedHeaderListView.java",
				null,
				pinnedHeaderFolder);
		this.updateWidget("pinnedheader/util/ComponentUtils.java",
				null,
				pinnedHeaderFolder);
		

		AttrsFile.mergeFromTo(this.getAdapter(),
				Context.getCurrentBundleFolder() 
					+ this.getAdapter().getTemplateRessourceValuesPath() 
					+ "/attrs.xml",
				this.getAdapter().getRessourceValuesPath() 
					+ "/attrs.xml");
		StylesFile.mergeFromTo(this.getAdapter(),
				Context.getCurrentBundleFolder() 
					+ this.getAdapter().getTemplateRessourceValuesPath() 
					+ "/styles.xml",
				this.getAdapter().getRessourceValuesPath() 
					+ "/styles.xml");

		ColorsFile.mergeFromTo(this.getAdapter(),
				Context.getCurrentBundleFolder() 
					+ this.getAdapter().getTemplateRessourceValuesPath() 
					+ "/colors.xml",
				this.getAdapter().getRessourceValuesPath() 
					+ "/colors.xml");
		
		this.manifestUpdater.setApplicationTheme("@style/PinnedTheme");

		DimensFile.mergeFromTo(this.getAdapter(),
				Context.getCurrentBundleFolder() 
					+ this.getAdapter().getTemplateRessourceValuesPath()
					+ "dimens.xml",
				this.getAdapter().getRessourceValuesPath()
					+ "dimens.xml");
		

		this.makeSource(
				this.getAdapter().getTemplateRessourcePath()
						+ "/color/primary_text_color.xml",
				this.getAdapter().getRessourcePath()
						+ "/color/primary_text_color.xml",
				false);

		this.makeSource(
				this.getAdapter().getTemplateRessourcePath()
						+ "/color/secondary_text_color.xml",
				this.getAdapter().getRessourcePath()
						+ "/color/secondary_text_color.xml",
				false);
		

		this.makeSource(
				this.getAdapter().getTemplateRessourcePath()
						+ "/color-xlarge/primary_text_color.xml",
				this.getAdapter().getRessourcePath()
						+ "/color-xlarge/primary_text_color.xml",
				false);

		this.makeSource(
				this.getAdapter().getTemplateRessourcePath()
						+ "/color-xlarge/secondary_text_color.xml",
				this.getAdapter().getRessourcePath()
						+ "/color-xlarge/secondary_text_color.xml",
				false);
		

		this.makeSource(
				this.getAdapter().getTemplateRessourcePath()
						+ "/drawable/list_item_activated_background.xml",
				this.getAdapter().getRessourcePath()
						+ "/drawable/list_item_activated_background.xml",
				false);
		

		MenuGenerator menuGenerator = new MenuGenerator(this.getAdapter());
		menuGenerator.generateMenu("CrudCreate");
		menuGenerator.generateMenu("CrudEditDelete");
		menuGenerator.generateMenu("Save");
		menuGenerator.updateMenu();
		
		TranslationMetadata.addDefaultTranslation("menu_item_create",
				"Add",
				Group.COMMON);
		
		TranslationMetadata.addDefaultTranslation("menu_item_edit",
				"Edit",
				Group.COMMON);
		
		TranslationMetadata.addDefaultTranslation("menu_item_delete",
				"Delete",
				Group.COMMON);
		
		TranslationMetadata.addDefaultTranslation("menu_item_save",
				"Save",
				Group.COMMON);

		
		if (this.isDate || this.isTime) {	
		
			if (this.isDate) {
				this.updateWidget("CustomDatePickerDialog.java",
						"dialog_date_picker.xml");
				this.updateWidget("DateWidget.java",
						"widget_date.xml");
			}

			if (this.isTime) {
				this.updateWidget("CustomTimePickerDialog.java",
						"dialog_time_picker.xml");
				this.updateWidget("TimeWidget.java",
						"widget_time.xml");
			}

			if (this.isDate && this.isTime) {
				this.updateWidget("DateTimeWidget.java",
						"widget_datetime.xml");
			}
		}

		// create HarmonyFragmentActivity
		this.updateWidget("MultiEntityWidget.java",
				"widget_multi_entity.xml");
		this.updateWidget("SingleEntityWidget.java",
				"widget_single_entity.xml");

		this.makeResourceLayout("dialog_delete_confirmation.xml",
				"dialog_delete_confirmation.xml");

		// create HarmonyFragment
		super.makeSource(
			this.getAdapter().getTemplateSourcePath()
			+ "harmony/view/DeleteDialog.java",
			this.getAdapter().getSourcePath()
			+ this.getAppMetas().getProjectNameSpace()
			+ "/harmony/view/"
			+ "DeleteDialog.java",
			false);

		TranslationMetadata.addDefaultTranslation("dialog_delete_title",
				"Delete",
				Group.COMMON);

		TranslationMetadata.addDefaultTranslation("dialog_delete_message",
				"Are you sure you want to delete this item ?",
				Group.COMMON);

		try {
			new TranslationGenerator(this.getAdapter()).generateStringsXml();
		} catch (Exception e) {
			ConsoleUtils.displayError(e);
		}
		
		this.manifestUpdater.save();
	}

	/**
	 * Generate all actions (List, Show, Edit, Create).
	 * @param entityName The entity for which to generate the crud.
	 */
	public final void generateAllAction(final String entityName) {
		ConsoleUtils.display(">>> Generate CRUD view for " +  entityName);

		try {
			if (this.isWritable) {
				ConsoleUtils.display("   with write actions");

				this.generateCreateAction(entityName);
				this.generateEditAction(entityName);

				TranslationMetadata.addDefaultTranslation(
						"common_create",
						"Create",
						Group.COMMON);

				TranslationMetadata.addDefaultTranslation(
						"common_edit",
						"Edit",
						Group.COMMON);

				TranslationMetadata.addDefaultTranslation(
						"common_delete",
						"Del",
						Group.COMMON);
				
				TranslationMetadata.addDefaultTranslation(
						entityName.toLowerCase(Locale.ENGLISH)
							+ "_progress_load_relations_title",
						entityName + " related entities loading",
						Group.MODEL);
				
				TranslationMetadata.addDefaultTranslation(
						entityName.toLowerCase(Locale.ENGLISH)
							+ "_progress_load_relations_message",
						entityName + " related entities are loading...",
						Group.MODEL);

				TranslationMetadata.addDefaultTranslation(
						entityName.toLowerCase(Locale.ENGLISH)
							+ "_progress_save_title",
						entityName + " save progress",
						Group.MODEL);
				TranslationMetadata.addDefaultTranslation(
						entityName.toLowerCase(Locale.ENGLISH)
							+ "_progress_save_message",
						entityName + " is saving to database…",
						Group.MODEL);
			}

			this.generateShowAction(entityName);
			this.generateListAction(entityName);

			TranslationMetadata.addDefaultTranslation(
					entityName.toLowerCase(Locale.ENGLISH)
						+ "_progress_load_title",
					entityName + " Loading progress",
					Group.MODEL);
			TranslationMetadata.addDefaultTranslation(
					entityName.toLowerCase(Locale.ENGLISH)
						+ "_progress_load_message",
					entityName + " is loading…",
					Group.MODEL);

			new TranslationGenerator(this.getAdapter()).generateStringsXml();
		} catch (final Exception e) {
			ConsoleUtils.displayError(e);
		}
	}

	/** List Action.
	 * @param entityName The entity to generate
	 * @throws IOException
	 * @throws TemplateException
	 */
	protected final void generateListAction(final String entityName) {
		final ArrayList<String> javas = new ArrayList<String>();
		javas.add("%sListActivity.java");
		javas.add("%sListFragment.java");
		javas.add("%sListAdapter.java");
		javas.add("%sListLoader.java");

		final ArrayList<String> xmls = new ArrayList<String>();
		xmls.add("activity_%s_list.xml");
		xmls.add("fragment_%s_list.xml");
		xmls.add("row_%s.xml");

		final ArrayList<String> largeXmls = new ArrayList<String>();
		largeXmls.add("activity_%s_list.xml");

		for (final String java : javas) {
			this.makeSourceControler(
					String.format(java, TEMPLATE),
					String.format(java, entityName));
		}

		for (final String xml : xmls) {
			this.makeResourceLayout(
					String.format(xml, LOWER_TEMPLATE),
					String.format(xml, entityName.toLowerCase(Locale.ENGLISH)));
		}
		
		for (final String largeXml : largeXmls) {
			this.makeLargeResourceLayout(
					String.format(largeXml, LOWER_TEMPLATE),
					String.format(largeXml, entityName.toLowerCase(Locale.ENGLISH)));
		}

		this.manifestUpdater.addActivity(
				this.getAppMetas().getProjectNameSpace(),
				"ListActivity",
				entityName);

		TranslationMetadata.addDefaultTranslation(
				entityName.toLowerCase(Locale.ENGLISH) + "_empty_list",
				entityName + " list is empty !",
				Group.MODEL);
	}

	/** Show Action.
	 * @param entityName The entity to generate
	 * @throws IOException
	 * @throws TemplateException
	 */
	protected final void generateShowAction(final String entityName) {

		final ArrayList<String> javas = new ArrayList<String>();
		javas.add("%sShowActivity.java");
		javas.add("%sShowFragment.java");

		final ArrayList<String> xmls = new ArrayList<String>();
		xmls.add("activity_%s_show.xml");
		xmls.add("fragment_%s_show.xml");


		for (final String java : javas) {
			this.makeSourceControler(
					String.format(java, TEMPLATE),
					String.format(java, entityName));
		}

		for (final String xml : xmls) {
			this.makeResourceLayout(
					String.format(xml, LOWER_TEMPLATE),
					String.format(xml, entityName.toLowerCase(Locale.ENGLISH)));
		}

		this.manifestUpdater.addActivity(
				this.getAppMetas().getProjectNameSpace(),
				"ShowActivity",
				entityName);

		TranslationMetadata.addDefaultTranslation(
				entityName.toLowerCase(Locale.ENGLISH) + "_error_load",
				entityName + " loading error…",
				Group.MODEL);
		

		TranslationMetadata.addDefaultTranslation(
				entityName.toLowerCase(Locale.ENGLISH) + "_not_found",
				"No " + entityName + " found to display…",
				Group.MODEL);
	}

	/** Edit Action.
	 * @param entityName The entity to generate
	 * @throws IOException
	 * @throws TemplateException
	 */
	protected final void generateEditAction(final String entityName) {

		final ArrayList<String> javas = new ArrayList<String>();
		javas.add("%sEditActivity.java");
		javas.add("%sEditFragment.java");

		final ArrayList<String> xmls = new ArrayList<String>();
		xmls.add("activity_%s_edit.xml");
		xmls.add("fragment_%s_edit.xml");


		for (final String java : javas) {
			this.makeSourceControler(
					String.format(java, TEMPLATE),
					String.format(java, entityName));
		}

		for (final String xml : xmls) {
			this.makeResourceLayout(
					String.format(xml, LOWER_TEMPLATE),
					String.format(xml, entityName.toLowerCase(Locale.ENGLISH)));
		}

		this.manifestUpdater.addActivity(
				this.getAppMetas().getProjectNameSpace(),
				"EditActivity",
				entityName);

		TranslationMetadata.addDefaultTranslation(
				entityName.toLowerCase(Locale.ENGLISH) + "_error_edit",
				entityName + " edition error…",
				Group.MODEL);

	}

	/** Create Action.
	 * @param entityName The entity to generate
	 * @throws IOException
	 * @throws TemplateException
	 */
	protected final void generateCreateAction(final String entityName) {

		final ArrayList<String> javas = new ArrayList<String>();
		javas.add("%sCreateActivity.java");
		javas.add("%sCreateFragment.java");

		final ArrayList<String> xmls = new ArrayList<String>();
		xmls.add("activity_%s_create.xml");
		xmls.add("fragment_%s_create.xml");


		for (final String java : javas) {
			this.makeSourceControler(
					String.format(java, TEMPLATE),
					String.format(java, entityName));
		}

		for (final String xml : xmls) {
			this.makeResourceLayout(
					String.format(xml, LOWER_TEMPLATE),
					String.format(xml, entityName.toLowerCase(Locale.ENGLISH)));
		}

		this.manifestUpdater.addActivity(
				this.getAppMetas().getProjectNameSpace(),
				"CreateActivity",
				entityName);

		final ClassMetadata classMeta =
				this.getAppMetas().getEntities().get(entityName);

		for (final FieldMetadata fm : classMeta.getFields().values()) {
			if (!fm.isInternal()
					&& !fm.isHidden()
					&& fm.getRelation() != null) {
				TranslationMetadata.addDefaultTranslation(
						entityName.toLowerCase(Locale.ENGLISH)
							+ "_"
							+ fm.getName().toLowerCase(Locale.ENGLISH)
							+ "_dialog_title",
						"Select " + fm.getName(),
						Group.MODEL);
			}
		}

		TranslationMetadata.addDefaultTranslation(
				entityName.toLowerCase(Locale.ENGLISH) + "_error_create",
				entityName + " creation error…",
				Group.MODEL);
	}

	/** Make Java Source Code.
	 *
	 * @param template Template path file.
	 *		For list activity is "TemplateListActivity.java"
	 * @param filename The destination file name.
	 */
	private void makeSourceControler(final String template,
			final String filename) {
		final String fullFilePath = String.format("%s%s/%s",
						this.getAdapter().getSourcePath(),
						PackageUtils.extractPath(this.localNameSpace)
							.toLowerCase(Locale.ENGLISH),
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
									this.getAdapter().getRessourceLayoutPath(),
									filename);
		final String fullTemplatePath = String.format("%s/%s",
				this.getAdapter().getTemplateRessourceLayoutPath(),
				template);

		super.makeSource(fullTemplatePath, fullFilePath, false);
	}
	
	/**
	 * Make Large Resource file.
	 *
	 * @param template Template path file.
	 * @param filename Resource file.
	 * 	prefix is type of view "row_" or "activity_" or "fragment_" with
	 *	postfix is type of action and extension file :
	 *		"_list.xml" or "_edit.xml".
	 */
	private void makeLargeResourceLayout(final String template,
			final String filename) {
		final String fullFilePath = String.format("%s/%s",
									this.getAdapter().getRessourceLargeLayoutPath(),
									filename);
		final String fullTemplatePath = String.format("%s/%s",
				this.getAdapter().getTemplateRessourceLargeLayoutPath(),
				template);

		super.makeSource(fullTemplatePath, fullFilePath, false);
	}
	
	/**
	 * Update Widget.
	 * @param widgetName The widget name.
	 * @param layoutName The layout name.
	 */
	protected final void updateWidget(final String widgetName,
			final String layoutName,
			final String destFolder) {
		super.makeSource(
				String.format("%s%s",
						this.getAdapter().getTemplateWidgetPath(),
						widgetName),

				String.format("%s%s",
						destFolder,
						widgetName),
				false);

		if (layoutName != null) {
			this.makeResourceLayout(layoutName, layoutName);
		}
	}

	/**
	 * Update Widget.
	 * @param widgetName The widget name.
	 * @param layoutName The layout name.
	 */
	protected final void updateWidget(final String widgetName,
			final String layoutName) {
		this.updateWidget(
				widgetName,
				layoutName,
				this.getAdapter().getWidgetPath());
	}

	/**
	 * Update Widget.
	 * @param widgetName The widget name.
	 */
	protected final void updateWidget(final String widgetName) {
		this.updateWidget(widgetName, null);
	}
}

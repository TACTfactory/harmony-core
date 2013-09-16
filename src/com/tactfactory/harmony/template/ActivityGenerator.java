/**
 * This file is part of the Harmony package.
 *
 * (c) Mickael Gaillard <mickael.gaillard@tactfactory.com>
 *
 * For the full copyright and license information, please view the LICENSE
 * file that was distributed with this source code.
 */
package com.tactfactory.harmony.template;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Locale;

import com.tactfactory.harmony.annotation.Column.Type;
import com.tactfactory.harmony.meta.ApplicationMetadata;
import com.tactfactory.harmony.meta.ClassMetadata;
import com.tactfactory.harmony.meta.EntityMetadata;
import com.tactfactory.harmony.meta.FieldMetadata;
import com.tactfactory.harmony.meta.TranslationMetadata;
import com.tactfactory.harmony.meta.TranslationMetadata.Group;
import com.tactfactory.harmony.plateforme.BaseAdapter;
import com.tactfactory.harmony.utils.ConsoleUtils;
import com.tactfactory.harmony.utils.PackageUtils;
import com.tactfactory.harmony.utils.TactFileUtils;

import freemarker.template.Configuration;
import freemarker.template.Template;
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

	/**
	 * Constructor.
	 * @param adapter The adapter to use
	 * @throws Exception if adapter is null
	 */
	public ActivityGenerator(final BaseAdapter adapter) throws Exception {
		this(adapter, true);

		// Make entities
		for (final EntityMetadata meta
				: this.getAppMetas().getEntities().values()) {
			if (!meta.getFields().isEmpty() && !meta.isInternal()) {
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
			if (!cm.isInternal() && !cm.getFields().isEmpty()) {
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


		this.updateWidget("ValidationButtons.java",
				"widget_validation_buttons.xml");

		this.updateWidget("PinchZoomImageView.java");

		if (this.isDate || this.isTime) {
			this.makeSource(
					this.getAdapter().getTemplateRessourceValuesPath()
							+ "/attrs.xml",
					this.getAdapter().getRessourceValuesPath()
							+ "/attrs.xml",
					false);
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
		super.makeSource(
			this.getAdapter().getTemplateSourcePath()
			+ "harmony/view/DeletableList.java",
			this.getAdapter().getSourcePath()
			+ this.getAppMetas().getProjectNameSpace()
			+ "/harmony/view/"
			+ "DeletableList.java",
			false);

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

		this.updateManifest("ListActivity", entityName);

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

		this.updateManifest("ShowActivity", entityName);

		TranslationMetadata.addDefaultTranslation(
				entityName.toLowerCase(Locale.ENGLISH) + "_error_load",
				entityName + " loading error…",
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

		this.updateManifest("EditActivity", entityName);

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


		this.updateManifest("CreateActivity", entityName);

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

	/** Make Manifest file.
	 *
	 * @param cfg Template engine
	 * @throws IOException if an IO exception occurred 
	 * @throws TemplateException If the template generation failed
	 */
	public final void makeManifest(final Configuration cfg)
			throws IOException, TemplateException {
		final File file =
				TactFileUtils.makeFile(this.getAdapter().getManifestPathFile());

		// Debug Log
		ConsoleUtils.displayDebug(
				"Generate Manifest : " + file.getAbsoluteFile());

		// Create
		final Template tpl = cfg.getTemplate(
				this.getAdapter().getTemplateManifestPathFile());
		final OutputStreamWriter output =
				new OutputStreamWriter(
						new FileOutputStream(file),
						TactFileUtils.DEFAULT_ENCODING);
		tpl.process(this.getDatamodel(), output);
		output.flush();
		output.close();
	}

	/**
	 * Update Android Manifest.
	 * @param classF The class file name
	 * @param entityName the entity for which to update the manifest for.
	 */
	private void updateManifest(final String classF, final String entityName) {
		ManifestUpdater updater = new ManifestUpdater(this.getAdapter());
		updater.addActivity(this.getAppMetas().getProjectNameSpace(),
				classF, entityName);
	}

	/**
	 * Update Widget.
	 * @param widgetName The widget name.
	 * @param layoutName The layout name.
	 */
	protected final void updateWidget(final String widgetName,
			final String layoutName) {
		super.makeSource(
				String.format("%s%s",
						this.getAdapter().getTemplateWidgetPath(),
						widgetName),

				String.format("%s%s",
						this.getAdapter().getWidgetPath(),
						widgetName),
				false);
		if (layoutName != null) {
			this.makeResourceLayout(layoutName, layoutName);
		}
	}

	/**
	 * Update Widget.
	 * @param widgetName The widget name.
	 */
	protected final void updateWidget(final String widgetName) {
		this.updateWidget(widgetName, null);
	}
}

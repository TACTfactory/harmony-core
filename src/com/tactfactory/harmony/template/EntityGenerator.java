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
import java.io.IOException;
import java.io.StringWriter;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.common.base.CaseFormat;
import com.tactfactory.harmony.meta.ApplicationMetadata;
import com.tactfactory.harmony.meta.ClassMetadata;
import com.tactfactory.harmony.meta.EntityMetadata;
import com.tactfactory.harmony.meta.FieldMetadata;
import com.tactfactory.harmony.meta.MethodMetadata;
import com.tactfactory.harmony.plateforme.BaseAdapter;
import com.tactfactory.harmony.utils.ConsoleUtils;
import com.tactfactory.harmony.utils.MetadataUtils;
import com.tactfactory.harmony.utils.TactFileUtils;

import freemarker.template.Template;
import freemarker.template.TemplateException;

/**
 * Entity Generator.
 * Used to decorate or generate entities.
 */
public class EntityGenerator extends BaseGenerator {
	/** remove HARD CODED String. */
	private String getterTemplate = "itemGetter.java";

	/** remove HARD CODED String. */
	private String setterTemplate = "itemSetter.java";
	
	/** remove HARD CODED String. */
	private String writeToParcelTemplate = "writeToParcel.java";
	
	/** remove HARD CODED String. */
	private String parcelConstructorTemplate = "parcelConstructor.java";
	
	/** remove HARD CODED String. */
	private String defaultConstructorTemplate = "defaultConstructor.java";
	
	/** remove HARD CODED String. */
	private String parcelableCreatorTemplate = "parcelable.creator.java";
	
	/** remove HARD CODED String. */
	private String describeContentsTemplate = "describeContents.java";
	/** Entities folder. */
	private String entityFolder;

	/** Constructor.
	 * @param adapter Adapter used by this generator
	 * @throws Exception if adapter is null
	 */
	public EntityGenerator(final BaseAdapter adapter) throws Exception {
		super(adapter);
		this.setDatamodel(this.getAppMetas().toMap(this.getAdapter()));
		this.entityFolder = 
				this.getAdapter().getSourcePath() 

				+ this.getAppMetas()
					.getProjectNameSpace().replaceAll("\\.", "/")
				+ "/entity/";
	}

	/**
	 * Implements serializable
	 * and add necessary getters and setters for all classes.
	 */
	public final void generateAll() {
		ConsoleUtils.display(">> Decorate entities...");

		for (final EntityMetadata classMeta
				: this.getAppMetas().getEntities().values()) {
			final String filepath = String.format("%s/%s",
					this.entityFolder,
					String.format("%s.java", classMeta.getName()));
			
			this.getDatamodel().put(
					TagConstant.CURRENT_ENTITY,
					classMeta.getName());
			
			ConsoleUtils.display(">>> Decorate " + classMeta.getName());

			final File entityFile = TactFileUtils.getFile(filepath);
			if (entityFile.exists()) {
				// Load the file once in a String buffer
				final StringBuffer fileString = 
						TactFileUtils.fileToStringBuffer(entityFile);
				this.implementEmptyConstructor(fileString, classMeta);
				this.addImplementsSerializable(fileString, classMeta);
				this.addImportSerializable(fileString, classMeta);
				this.generateGetterAndSetters(fileString, classMeta);
				this.implementParcelable(fileString, classMeta);
				
				 // After treatment on entity, write it in the original file
				TactFileUtils.stringBufferToFile(fileString, entityFile);
			}
		}
	}

	/**
	 * Implements serializable in the class if it doesn't already.
	 * @param fileString The stringbuffer containing the class java code
	 * @param classMeta The Metadata containing the infos on the java class
	 * @param className the name of the class to implement
	 */
	protected final void addImplements(
			final StringBuffer fileString,
			final EntityMetadata classMeta,
			final String className) {
		if (!this.alreadyImplementsClass(classMeta, className)) {
			ConsoleUtils.displayDebug("Add " + className + " implement");
			final int firstAccolade = fileString.indexOf("{");
			
			// Class already implements an interface which is not the class
			if (classMeta.getImplementTypes().size() > 0) { 
				fileString.insert(firstAccolade, ", " + className);
			} else {
				fileString.insert(firstAccolade, " implements " + className);
			}		
			classMeta.getImplementTypes().add(className);
		}
	}

	/**
	 * Implements serializable in the class if it doesn't already.
	 * @param fileString The stringbuffer containing the class java code
	 * @param classMeta The Metadata containing the infos on the java class
	 */
	protected final void addImplementsSerializable(
			final StringBuffer fileString,
			final EntityMetadata classMeta) {
		this.addImplements(fileString, classMeta, "Serializable");
	}
	
	/**
	 * Implements Parcelable in the class if it doesn't already.
	 * @param fileString The stringbuffer containing the class java code
	 * @param classMeta The Metadata containing the infos on the java class
	 */
	protected final void addImplementsParcelable(
			final StringBuffer fileString,
			final EntityMetadata classMeta) {
		this.addImplements(fileString, classMeta, "Parcelable");
	}
	
	/**
	 * Import serializable in the class if it doesn't already.
	 * @param fileString The stringbuffer containing the class java code
	 * @param classMeta The Metadata containing the infos on the java class
	 */
	protected final void addImportSerializable(final StringBuffer fileString,
			final ClassMetadata classMeta) {
		this.addImport(fileString,
				classMeta,
				"Serializable",
				"java.io.Serializable");
	}
	
	/**
	 * Import parcelable in the class if it doesn't already.
	 * @param fileString The stringbuffer containing the class java code
	 * @param classMeta The Metadata containing the infos on the java class
	 */
	protected final void addImportParcelable(final StringBuffer fileString,
			final ClassMetadata classMeta) {
		this.addImport(fileString,
				classMeta,
				"Parcelable",
				"android.os.Parcelable");
	}
	
	/**
	 * Import serializable in the class if it doesn't already.
	 * @param fileString The stringbuffer containing the class java code
	 * @param classMeta The Metadata containing the infos on the java class
	 * @param className The name of the class to import
	 * @param classPackage The package of the class to import
	 */
	protected final void addImport(final StringBuffer fileString,
			final ClassMetadata classMeta,
			final String className,
			final String classPackage) {
		if (!this.alreadyImportsClass(classMeta, className)) {
			ConsoleUtils.displayDebug("Add " + className + " import");
			int insertPos;

			if (classMeta.getImports().size() > 0) {
				insertPos = fileString.indexOf("import");
			} else {
				insertPos = fileString.indexOf(";") + 1;
			}
			fileString.insert(
					insertPos, 
					"\rimport " + classPackage + ";\r");
		}
	}


	/**
	 * Generate the necessary getters and setters for the class.
	 * @param fileString The stringbuffer containing the class java code
	 * @param classMeta The Metadata containing the infos on the java class
	 */
	protected final void generateGetterAndSetters(final StringBuffer fileString,
			final EntityMetadata classMeta) {
		final Collection<FieldMetadata> fields = classMeta.getFields().values();
		final boolean childClass = MetadataUtils.inheritsFromEntity(classMeta,
				ApplicationMetadata.INSTANCE);
		for (final FieldMetadata field : fields) {
			final boolean isInheritedId = 
					childClass 
					&& classMeta.getIds().containsKey(field.getName());
			if (!field.isInternal() && !isInheritedId) {
				// Getter
				if (!this.alreadyImplementsGet(field, classMeta)) {
					ConsoleUtils.displayDebug("Add implements getter of "
							+ field.getName()
							+ " => get"
							+ CaseFormat.LOWER_CAMEL.to(
									CaseFormat.UPPER_CAMEL,
									field.getName()));

					this.generateMethod(fileString, field, this.getterTemplate);
				}

				// Setter
				if (!this.alreadyImplementsSet(field, classMeta)) {
					ConsoleUtils.displayDebug("Add implements setter of "
							+ field.getName()
							+ " => set"
							+ CaseFormat.LOWER_CAMEL.to(
									CaseFormat.UPPER_CAMEL,
									field.getName()));

					this.generateMethod(fileString, field, this.setterTemplate);
				}
			}
		}
	}


	/**
	 * Generate a get or set method following the given template.
	 * @param fileString The stringbuffer containing the class java code
	 * @param f The concerned field
	 * @param templateName The template file name
	 */
	protected final void generateMethod(final StringBuffer fileString,
			final FieldMetadata f,
			final String templateName) {
		final int lastAccolade = fileString.lastIndexOf("}");

		final HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("property", f.getName());
		map.put("property_type", this.getAdapter().getNativeType(f.getType()));

		try {
			final StringWriter writer = new StringWriter();

			final Template tpl = this.getCfg().getTemplate(
					String.format("%s%s",
							this.getAdapter().getTemplateSourceCommonPath(),
							templateName + ".ftl"));
			// Load template file in engine

			tpl.process(map, writer);
			final StringBuffer getString = writer.getBuffer();
			fileString.insert(lastAccolade, getString + "\n\n");

		} catch (final IOException e) {
			ConsoleUtils.displayError(e);
		} catch (final TemplateException e) {
			ConsoleUtils.displayError(e);
		}
	}
	
	/**
	 * Checks if given class already implements given class.
	 * @param classMeta The class to check
	 * @param className The interface name 
	 * @return True if already implements
	 */
	protected final boolean alreadyImplementsClass(
			final EntityMetadata classMeta,
			final String className) {
		boolean ret = false;
		for (final String implement : classMeta.getImplementTypes()) {
			if (className.equals(implement)) {				
				ret = true;
				
				ConsoleUtils.displayDebug(
						"Already implements " + className + " !");

			}
		}

		return ret;
	}

	/**
	 * Check if the class implements the class Serializable.
	 * @param classMeta The Metadata containing the infos on the java class
	 * @return True if it already implements serializable
	 */
	protected final boolean alreadyImplementsSerializable(
			final EntityMetadata classMeta) {
		return this.alreadyImplementsClass(classMeta, "Serializable");
	}
	
	/**
	 * Check if the class implements the class Parcelable.
	 * @param classMeta The Metadata containing the infos on the java class
	 * @return True if it already implements parcelable
	 */
	protected final boolean alreadyImplementsParcelable(
			final EntityMetadata classMeta) {
		return this.alreadyImplementsClass(classMeta, "Parcelable");
	}
	
	/**
	 * Check if the class already has a getter for the given field.
	 * @param fieldMeta The Metadata of the field
	 * @param classMeta The Metadata containing the infos on the java class
	 * @return True if it already has getter for this field
	 */
	protected final boolean alreadyImplementsGet(final FieldMetadata fieldMeta,
			final ClassMetadata classMeta) {
		boolean ret = false;
		final List<MethodMetadata> methods = classMeta.getMethods();
		final String capitalizedName =
				fieldMeta.getName().substring(0, 1).toUpperCase()
				+ fieldMeta.getName().substring(1);
		String prefix = "get";
		if ("boolean".equals(fieldMeta.getType())) {
			prefix = "is";
		}
		for (final MethodMetadata m : methods) {
			if (m.getName().equals(prefix + capitalizedName)
					&& m.getArgumentsTypes().size() == 0
					&& m.getType().equals(this.getAdapter().getNativeType(
							fieldMeta.getType()))) {
				ret = true;

				ConsoleUtils.displayDebug("Already implements getter of "
						+ fieldMeta.getName()
						+ " => "
						+ m.getName());
			}
		}

		return ret;
	}

	/**
	 * Check if the class already has a setter for the given field.
	 * @param fieldMeta The Metadata of the field
	 * @param classMeta The Metadata containing the infos on the java class
	 * @return True if it already has setter for this field
	 */
	protected final boolean alreadyImplementsSet(
			final FieldMetadata fieldMeta,
			final ClassMetadata classMeta) {
		boolean result = false;
		final List<MethodMetadata> methods = classMeta.getMethods();
		final String capitalizedName =
				fieldMeta.getName().substring(0, 1).toUpperCase()
				+ fieldMeta.getName().substring(1);

		for (final MethodMetadata method : methods) {
			if (method.getName().equals("set" + capitalizedName)
					&& method.getArgumentsTypes().size() == 1
					&& method.getArgumentsTypes().get(0).equals(
							this.getAdapter().getNativeType(
									fieldMeta.getType()))) {
				result = true;

				ConsoleUtils.displayDebug("Already implements setter of "
						+ fieldMeta.getName()
						+ " => "
						+ method.getName());
			}
		}

		return result;
	}	
	
	/**
	 * Check if the class already has a getter for the given field.
	 * @param classMeta The Metadata containing the infos on the java class
	 * @return True if it already has getter for this field
	 */
	protected final boolean alreadyImplementsDefaultConstructor(
			final ClassMetadata classMeta) {
		boolean ret = false;
		
		for (final MethodMetadata methodMeta : classMeta.getMethods()) {
			if (methodMeta.getName().equals(classMeta.getName()) 
					&& methodMeta.getArgumentsTypes().size() == 0) {
				ret = true;
				
				ConsoleUtils.displayDebug("Already implements " 
						+ "empty constructor");
			}
		}
					
		return ret;
	}
	
	/**
	 * Check if the class already imports the given class.
	 * @param classMeta The Metadata containing the infos on the java class
	 * @param className the name of the class
	 * @return True if it already imports serializable
	 */
	protected final boolean alreadyImportsClass(final ClassMetadata classMeta,
			final String className) {
		boolean ret = false;
		for (final String imported : classMeta.getImports()) {
			if (className.equals(imported)) {
				ret = true;
			}
		}

		return ret;
	}
	
	/**
	 * Check if the class already imports Serializable.
	 * @param classMeta The Metadata containing the infos on the java class
	 * @return True if it already imports serializable
	 */
	protected final boolean alreadyImportsSerializable(
			final ClassMetadata classMeta) {
		return this.alreadyImportsClass(classMeta, "Serializable");
	}
	
	/**
	 * Check if the class already imports Parcelable.
	 * @param classMeta The Metadata containing the infos on the java class
	 * @return True if it already imports Parcelable
	 */
	protected final boolean alreadyImportsParcelable(
			final ClassMetadata classMeta) {
		return this.alreadyImportsClass(classMeta, "Parcelable");
	}
	
	/**
	 * Implement all methods needed by parcelable.
	 * @param fileString The string buffer representation of the file 
	 * @param classMeta The classmetadata
	 */
	protected final void implementParcelable(
			final StringBuffer fileString,
			final EntityMetadata classMeta) {
		if (!this.alreadyImplementsParcelable(classMeta)) {
			this.addImplementsParcelable(fileString, classMeta);
			this.addImportParcelable(fileString, classMeta);
			this.addImport(
					fileString, classMeta, "Parcel", "android.os.Parcel");
			this.generateMethod(fileString, this.parcelConstructorTemplate);
			this.generateMethod(fileString, this.writeToParcelTemplate);
			this.generateMethod(fileString, this.describeContentsTemplate);
			this.generateMethod(fileString, this.parcelableCreatorTemplate);
		}
	}
	
	/**
	 * Generate a get or set method following the given template.
	 * @param fileString The stringbuffer containing the class java code
	 * @param templateName The template file name
	 */
	protected final void generateMethod(final StringBuffer fileString, 
			final String templateName) {
		final int lastAccolade = fileString.lastIndexOf("}");
		
		final Map<String, Object> map = this.getDatamodel();
		
		try {
			final StringWriter writer = new StringWriter();
			
			final Template tpl = this.getCfg().getTemplate(
					String.format("%s%s",
							this.getAdapter().getTemplateSourceCommonPath(),
							templateName + ".ftl"));
			// Load template file in engine
			
			tpl.process(map, writer);
			final StringBuffer getString = writer.getBuffer();
			fileString.insert(lastAccolade, getString + "\n\n");
			
		} catch (final IOException e) {
			ConsoleUtils.displayError(e);
		} catch (final TemplateException e) {
			ConsoleUtils.displayError(e);
		}		
	}
	
	/**
	 * Implement an empty contructor if it doesn't already exists.
	 * @param fileString The string buffer representing the file
	 * @param classMeta The classMetadata
	 */
	protected final void implementEmptyConstructor(
			final StringBuffer fileString,
			final ClassMetadata classMeta) {
		if (!this.alreadyImplementsDefaultConstructor(classMeta)) {
			this.generateMethod(fileString, this.defaultConstructorTemplate);
		}
	}
}

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
import java.util.Collection;
import java.util.List;
import com.google.common.base.CaseFormat;
import com.tactfactory.harmony.meta.ApplicationMetadata;
import com.tactfactory.harmony.meta.ClassMetadata;
import com.tactfactory.harmony.meta.EntityMetadata;
import com.tactfactory.harmony.meta.FieldMetadata;
import com.tactfactory.harmony.meta.MethodMetadata;
import com.tactfactory.harmony.plateforme.BaseAdapter;
import com.tactfactory.harmony.plateforme.buffers.SourceFileManipulator;
import com.tactfactory.harmony.utils.ConsoleUtils;
import com.tactfactory.harmony.utils.MetadataUtils;
import com.tactfactory.harmony.utils.TactFileUtils;

/**
 * Entity Generator.
 * Used to decorate or generate entities.
 */
public class EntityGenerator extends BaseGenerator {
	/** Parcel constant declaration. */
	private static final String PARCEL_CONSTANT_DECL =
			"public static final String PARCEL";

	/** Write to parcel method declaration. */
	private static final String WRITE_TO_PARCEL_REGEN_DECL =
			"writeToParcelRegen(Parcel dest, int flags) {";
	
	/** Read from parcel method declaration. */
	private static final String READ_FROM_PARCEL_REGEN_DECL =
			"readFromParcel(Parcel parc) {";
	
	/** remove HARD CODED String. */
	private String getterTemplate = "itemGetter.java";

	/** remove HARD CODED String. */
	private String setterTemplate = "itemSetter.java";

	/** remove HARD CODED String. */
	private String writeToParcelTemplate = "writeToParcel.java";

	/** remove HARD CODED String. */
	private String writeToParcelRegenTemplate = "writeToParcelRegen.java";
	
	/** remove HARD CODED String. */
	private String parcelConstructorTemplate = "parcelConstructor.java";
	
	/** remove HARD CODED String. */
	private String defaultConstructorTemplate = "defaultConstructor.java";
	
	/** remove HARD CODED String. */
	private String parcelableCreatorTemplate = "parcelable.creator.java";

	/** remove HARD CODED String. */
	private String describeContentsTemplate = "describeContents.java";

	/** remove HARD CODED String. */
	private String readFromParcelTemplate = "readFromParcel.java";

	/** remove HARD CODED String. */
	private String parcelConstantTemplate = "parcelConstant.java";
	
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
				final SourceFileManipulator manipulator =
						this.getAdapter().getFileManipulator(
								entityFile,
								this.getCfg());
				
				this.implementEmptyConstructor(manipulator, classMeta);
				manipulator.addImplement(classMeta, "Serializable");
				manipulator.addImport(
						classMeta,
						"Serializable",
						"java.io.Serializable");
				this.generateGetterAndSetters(manipulator, classMeta);
				this.implementParcelable(manipulator, classMeta);
				this.addParcelConstant(manipulator, classMeta);
				
				 // After treatment on entity, write it in the original file
				manipulator.writeFile();
			}
		}
	}


	/**
	 * Generate the necessary getters and setters for the class.
	 * @param fileString The stringbuffer containing the class java code
	 * @param classMeta The Metadata containing the infos on the java class
	 */
	protected final void generateGetterAndSetters(
			final SourceFileManipulator manipulator,
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
							+ field.getName(),
							" => get"
							+ CaseFormat.LOWER_CAMEL.to(
									CaseFormat.UPPER_CAMEL,
									field.getName()));

					manipulator.generateFieldAccessor(field, this.getterTemplate);
				}

				// Setter
				if (!this.alreadyImplementsSet(field, classMeta)) {
					ConsoleUtils.displayDebug("Add implements setter of "
							+ field.getName(),
							" => set"
							+ CaseFormat.LOWER_CAMEL.to(
									CaseFormat.UPPER_CAMEL,
									field.getName()));

					manipulator.generateFieldAccessor(field, this.setterTemplate);
				}
				
				// Import ArrayList if relation
				if (field.getRelation() != null 
					&& (field.getRelation().getType().equals("ManyToMany")
						|| field.getRelation().getType().equals("OneToMany"))) {
					manipulator.addImport(
							classMeta,
							"ArrayList",
							"java.util.ArrayList");
				}
			}
		}
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
		if ("boolean".equalsIgnoreCase(fieldMeta.getType())) {
			prefix = "is";
		}
		for (final MethodMetadata m : methods) {
			if (m.getName().equals(prefix + capitalizedName)
					&& m.getArgumentsTypes().size() == 0
					&& m.getType().equals(this.getAdapter().getNativeType(
							fieldMeta.getType()))) {
				ret = true;

				ConsoleUtils.displayDebug("Already implements getter of "
						+ fieldMeta.getName(),
						" => " + m.getName());
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
						+ fieldMeta.getName(),
						" => "
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
	 * Implement all methods needed by parcelable.
	 * @param fileString The string buffer representation of the file 
	 * @param classMeta The classmetadata
	 */
	protected final void implementParcelable(
			final SourceFileManipulator manipulator,
			final EntityMetadata classMeta) {

		manipulator.regenerateMethod(
				this.writeToParcelRegenTemplate,
				WRITE_TO_PARCEL_REGEN_DECL,
				this.getDatamodel());
		
		manipulator.regenerateMethod(
				this.readFromParcelTemplate,
				READ_FROM_PARCEL_REGEN_DECL,
				this.getDatamodel());
		
		if (manipulator.addImplement(classMeta, "Parcelable")) {
			manipulator.addImport(
					classMeta,
					"Parcelable",
					"android.os.Parcelable");
			manipulator.addImport(classMeta, "Parcel", "android.os.Parcel");

			manipulator.generateMethod(
					this.parcelConstructorTemplate,
					this.getDatamodel());

			manipulator.generateMethod(
					this.writeToParcelTemplate,
					this.getDatamodel());
			
			manipulator.generateMethod(
					this.describeContentsTemplate,
					this.getDatamodel());

			manipulator.generateMethod(
					this.parcelableCreatorTemplate,
					this.getDatamodel());
		}
	}
	
	/**
	 * Implement an empty contructor if it doesn't already exists.
	 * @param fileString The string buffer representing the file
	 * @param classMeta The classMetadata
	 */
	protected final void implementEmptyConstructor(
			final SourceFileManipulator manipulator,
			final ClassMetadata classMeta) {
		if (!this.alreadyImplementsDefaultConstructor(classMeta)) {
			manipulator.generateMethod(
					this.defaultConstructorTemplate,
					this.getDatamodel());
		}
	}
			
	/**
	 * Add parcel constant.
	 * 
	 * @param fileString The string buffer representing the file
	 * @param classMeta The classMetadata
	 */
	protected final void addParcelConstant(
			final SourceFileManipulator manipulator,
			final ClassMetadata classMeta) {
		if (!manipulator.alreadyHasField(PARCEL_CONSTANT_DECL)) {
			manipulator.generateField(
					this.parcelConstantTemplate, 
					this.getDatamodel());
		}
	}
}

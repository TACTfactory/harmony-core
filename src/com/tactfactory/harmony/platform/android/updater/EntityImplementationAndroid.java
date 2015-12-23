/**
 * This file is part of the Harmony package.
 *
 * (c) Mickael Gaillard <mickael.gaillard@tactfactory.com>
 *
 * For the full copyright and license information, please view the LICENSE
 * file that was distributed with this source code.
 */
package com.tactfactory.harmony.platform.android.updater;

import java.io.File;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import com.google.common.base.CaseFormat;
import com.tactfactory.harmony.annotation.Column;
import com.tactfactory.harmony.annotation.Column.Type;
import com.tactfactory.harmony.completor.ResourceCompletor;
import com.tactfactory.harmony.meta.ApplicationMetadata;
import com.tactfactory.harmony.meta.ClassMetadata;
import com.tactfactory.harmony.meta.EntityMetadata;
import com.tactfactory.harmony.meta.FieldMetadata;
import com.tactfactory.harmony.meta.MethodMetadata;
import com.tactfactory.harmony.platform.IAdapter;
import com.tactfactory.harmony.platform.SqliteAdapter;
import com.tactfactory.harmony.platform.manipulator.SourceFileManipulator;
import com.tactfactory.harmony.updater.IUpdaterFile;
import com.tactfactory.harmony.utils.ConsoleUtils;
import com.tactfactory.harmony.utils.MetadataUtils;

import freemarker.template.Configuration;

//TODO remove
public class EntityImplementationAndroid implements IUpdaterFile {
    private final IAdapter adapter;
    private final Configuration configuration;
    private final Map<String, Object> dataModel;
    private final File entityFile;
    private final EntityMetadata entity;

    public EntityImplementationAndroid(IAdapter adapter, Configuration cfg,
            Map<String, Object> dataModel, File entityFile,
            EntityMetadata entity) {
        this.adapter = adapter;
        this.configuration = cfg;
        this.dataModel = dataModel;
        this.entityFile = entityFile;
        this.entity = entity;
    }

    protected void updateEntity() {
        final SourceFileManipulator manipulator =
                this.adapter.getFileManipulator(
                        entityFile,
                        this.configuration);

        if (this.entity.isResource()) {
            this.generateResourceField(manipulator);
        }

        this.implementEmptyConstructor(manipulator, entity);

        manipulator.addImplement(entity, "Serializable");
        manipulator.addImport(
                entity,
                "Serializable",
                "java.io.Serializable");

        this.implementResource(manipulator, this.entity);
        this.generateGetterAndSetters(manipulator, entity);
        this.implementParcelable(manipulator, entity);
        // After treatment on entity, write it in the original file
        manipulator.writeFile();
    }
    /**
     * Generate the necessary getters and setters for the class.
     * @param fileString The stringbuffer containing the class java code
     * @param classMeta The Metadata containing the infos on the java class
     */
    private final void generateGetterAndSetters(
            final SourceFileManipulator manipulator,
            final EntityMetadata classMeta) {

        final Collection<FieldMetadata> fields = classMeta.getFields().values();
        final boolean childClass = MetadataUtils.inheritsFromEntity(classMeta,
                ApplicationMetadata.INSTANCE);
//        if (this.entity.isResource()) {
//            resourceField.setName("path");
//
//            fields.add(resourceField);
//        }

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

                    manipulator.generateFieldAccessor(field, "itemGetter.java", this.entity);
                }

                // Setter
                if (!this.alreadyImplementsSet(field, classMeta)) {
                    ConsoleUtils.displayDebug("Add implements setter of "
                            + field.getName(),
                            " => set"
                            + CaseFormat.LOWER_CAMEL.to(
                                    CaseFormat.UPPER_CAMEL,
                                    field.getName()));

                    manipulator.generateFieldAccessor(field, "itemSetter.java", this.entity);
                }
            }
        }

        manipulator.addImport(
                classMeta,
                "ArrayList",
                "java.util.ArrayList");

        manipulator.addImport(
				classMeta,
				"List",
				"java.util.List");
    }

    /**
     * Check if the class already has a getter for the given field.
     * @param fieldMeta The Metadata of the field
     * @param classMeta The Metadata containing the infos on the java class
     * @return True if it already has getter for this field
     */
    private final boolean alreadyImplementsGet(final FieldMetadata fieldMeta,
            final ClassMetadata classMeta) {
    	boolean ret = false;
		final List<MethodMetadata> methods = classMeta.getMethods();
		final String capitalizedName =
				fieldMeta.getName().substring(0, 1).toUpperCase()
				+ fieldMeta.getName().substring(1);
		String prefix = "get";
		if ("boolean".equalsIgnoreCase(fieldMeta.getHarmonyType())) {
			prefix = "is";
		}
		for (final MethodMetadata m : methods) {
			if (m.getName().equals(prefix + capitalizedName)
					&& m.getArgumentsTypes().size() == 0
					&& m.getType().equals(this.adapter.getNativeType(
							fieldMeta))) {
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
    private final boolean alreadyImplementsSet(
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
							this.adapter.getNativeType(
									fieldMeta))) {
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
    private final boolean alreadyImplementsDefaultConstructor(
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
    private final void implementParcelable(
            final SourceFileManipulator manipulator,
            final EntityMetadata classMeta) {

        manipulator.regenerateMethod(
                "writeToParcelRegen.java",
                "writeToParcelRegen(Parcel dest, int flags) {",
                this.dataModel);

        manipulator.regenerateMethod(
                "readFromParcel.java",
                "readFromParcel(Parcel parc) {",
                this.dataModel);

        if (manipulator.addImplement(classMeta, "Parcelable")) {
            manipulator.addImport(
                    classMeta,
                    "Parcelable",
                    "android.os.Parcelable");
            manipulator.addImport(classMeta, "Parcel", "android.os.Parcel");

            manipulator.generateMethod(
                    "parcelConstructor.java",
                    this.dataModel);

            manipulator.generateMethod(
                    "writeToParcel.java",
                    this.dataModel);

            manipulator.generateMethod(
					"writeToParcel2.java",
					this.dataModel);

            manipulator.generateMethod(
                    "describeContents.java",
                    this.dataModel);

            manipulator.generateMethod(
                    "parcelable.creator.java",
                    this.dataModel);

            if (!(classMeta.getInheritance() != null
					&& classMeta.getInheritance().getSuperclass() != null
					&& classMeta.getInheritance().getSuperclass().hasBeenParsed())) {
				manipulator.generateField(
						"parcelParent.java",
						this.dataModel);
			}

            boolean hasDateTime = false;
			for (FieldMetadata field : classMeta.getFields().values()) {
				if (field.getHarmonyType().equalsIgnoreCase("DateTime")
						|| field.getHarmonyType().equalsIgnoreCase("Date")
						|| field.getHarmonyType().equalsIgnoreCase("Time")) {
					hasDateTime = true;
				}
			}
			if (hasDateTime) {
				manipulator.addImport(classMeta,
						"ISODateTimeFormat",
						"org.joda.time.format.ISODateTimeFormat");
			}
        }
    }

    /**
     * Implement all methods needed by parcelable.
     * @param fileString The string buffer representation of the file
     * @param classMeta The classmetadata
     */
    private final void implementResource(
            final SourceFileManipulator manipulator,
            final EntityMetadata classMeta) {

        if (this.entity.isResource()) {
            if (manipulator.addImplement(classMeta, "Resource")) {
                manipulator.addImport(
                        classMeta,
                        "Resource",
                        this.adapter.getApplicationMetadata().getProjectNameSpace().replace("/", ".")
                        + ".entity.base.Resource");
            }
        }
    }

    /**
     * Implement an empty contructor if it doesn't already exists.
     * @param fileString The string buffer representing the file
     * @param classMeta The classMetadata
     */
    private final void implementEmptyConstructor(
            final SourceFileManipulator manipulator,
            final ClassMetadata classMeta) {
        if (!this.alreadyImplementsDefaultConstructor(classMeta)) {
            manipulator.generateMethod(
                    "defaultConstructor.java",
                    this.dataModel);
        }
    }

    private final void generateResourceField(SourceFileManipulator manipulator) {
        ResourceCompletor.completeEntities();
    }

    @Override
    public void execute() {
        this.updateEntity();
    }
}

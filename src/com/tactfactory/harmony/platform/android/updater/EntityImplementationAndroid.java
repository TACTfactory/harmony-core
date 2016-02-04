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

import com.google.common.base.CaseFormat;
import com.tactfactory.harmony.generator.BaseGenerator;
import com.tactfactory.harmony.meta.ApplicationMetadata;
import com.tactfactory.harmony.meta.ClassMetadata;
import com.tactfactory.harmony.meta.EntityMetadata;
import com.tactfactory.harmony.meta.FieldMetadata;
import com.tactfactory.harmony.meta.MethodMetadata;
import com.tactfactory.harmony.platform.IAdapter;
import com.tactfactory.harmony.platform.manipulator.SourceFileManipulator;
import com.tactfactory.harmony.updater.IUpdaterFile;
import com.tactfactory.harmony.utils.ConsoleUtils;
import com.tactfactory.harmony.utils.MetadataUtils;
import com.tactfactory.harmony.utils.TactFileUtils;

//TODO remove
public class EntityImplementationAndroid implements IUpdaterFile {
    private final File entityFile;
    private final EntityMetadata entity;

    public EntityImplementationAndroid(
            File entityFile,
            EntityMetadata entity) {
        this.entityFile = entityFile;
        this.entity = entity;
    }

    protected void updateEntity(BaseGenerator<? extends IAdapter> generator) {
        final SourceFileManipulator manipulator =
                generator.getAdapter().getFileManipulator(
                        entityFile,
                        generator.getCfg());

        this.implementEmptyConstructor(generator, manipulator, entity);

        manipulator.addImplement(entity, "Serializable");
        manipulator.addImport(
                entity,
                "Serializable",
                "java.io.Serializable");

        this.implementResource(generator, manipulator, entity);
        this.generateGetterAndSetters(generator, manipulator, entity);
        this.implementParcelable(generator, manipulator, entity);

        // After treatment on entity, write it in the original file
        manipulator.writeFile();
    }
    /**
     * Generate the necessary getters and setters for the class.
     * @param generator
     * @param fileString The stringbuffer containing the class java code
     * @param classMeta The Metadata containing the infos on the java class
     */
    private final void generateGetterAndSetters(
            BaseGenerator<? extends IAdapter> generator,
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
                if (!this.alreadyImplementsGet(generator, field, classMeta)) {
                    ConsoleUtils.displayDebug("Add implements getter of "
                            + field.getName(),
                            " => get"
                            + CaseFormat.LOWER_CAMEL.to(
                                    CaseFormat.UPPER_CAMEL,
                                    field.getName()));

                    manipulator.generateFieldAccessor(field, "itemGetter.java", this.entity);
                }

                // Setter
                if (!this.alreadyImplementsSet(generator, field, classMeta)) {
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
     * @param generator
     * @param fieldMeta The Metadata of the field
     * @param classMeta The Metadata containing the infos on the java class
     * @return True if it already has getter for this field
     */
    private final boolean alreadyImplementsGet(
            final BaseGenerator<? extends IAdapter> generator,
            final FieldMetadata fieldMeta,
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
					&& m.getType().equals(generator.getAdapter().getNativeType(fieldMeta))) {
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
     * @param generator
     * @param fieldMeta The Metadata of the field
     * @param classMeta The Metadata containing the infos on the java class
     * @return True if it already has setter for this field
     */
    private final boolean alreadyImplementsSet(
            BaseGenerator<? extends IAdapter> generator, final FieldMetadata fieldMeta,
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
							generator.getAdapter().getNativeType(
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
     * @param generator
     * @param fileString The string buffer representation of the file
     * @param classMeta The classmetadata
     */
    private final void implementParcelable(
            BaseGenerator<? extends IAdapter> generator,
            final SourceFileManipulator manipulator,
            final EntityMetadata classMeta) {

        manipulator.regenerateMethod(
                "writeToParcelRegen.java",
                "writeToParcelRegen(Parcel dest, int flags) {",
                generator.getDatamodel());

        manipulator.regenerateMethod(
                "readFromParcel.java",
                "readFromParcel(Parcel parc) {",
                generator.getDatamodel());

        if (manipulator.addImplement(classMeta, "Parcelable")) {
            manipulator.addImport(
                    classMeta,
                    "Parcelable",
                    "android.os.Parcelable");
            manipulator.addImport(classMeta, "Parcel", "android.os.Parcel");

            manipulator.generateMethod(
                    "parcelConstructor.java",
                    generator.getDatamodel());

            manipulator.generateMethod(
                    "writeToParcel.java",
                    generator.getDatamodel());

            manipulator.generateMethod(
					"writeToParcel2.java",
                    generator.getDatamodel());

            manipulator.generateMethod(
                    "describeContents.java",
                    generator.getDatamodel());

            manipulator.generateMethod(
                    "parcelable.creator.java",
                    generator.getDatamodel());

            if (!(classMeta.getInheritance() != null
					&& classMeta.getInheritance().getSuperclass() != null
					&& classMeta.getInheritance().getSuperclass().hasBeenParsed())) {
				manipulator.generateField(
						"parcelParent.java",
						generator.getDatamodel());
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
     * @param generator
     * @param fileString The string buffer representation of the file
     * @param classMeta The classmetadata
     */
    private final void implementResource(
            BaseGenerator<? extends IAdapter> generator,
            final SourceFileManipulator manipulator,
            final EntityMetadata classMeta) {

        if (this.entity.isResource()) {
            this.addExtendResource(generator);
        }
    }

    /**
     * Add inheritance to java Entity and complete parcelable methods.
     * @param cm The entity metadata
     */
    private void addExtendResource(BaseGenerator<? extends IAdapter> generator) {
        final String entityName = this.entity.getName();
        final File entityFile =
                new File(generator.getAdapter().getSourcePath()
                        + generator.getAdapter().getApplicationMetadata().getProjectNameSpace()
                        + "/entity/" + entityName + ".java");
        final StringBuffer sb = TactFileUtils.fileToStringBuffer(entityFile);
        final int indexEntityBase = this.indexOf(sb, "extends", false);
        final String classDeclaration = "class " + entityName;
        String classExtends;

        if (this.entity.getInheritance() != null || indexEntityBase > -1) {
            classExtends = " extends EntityBase";
        } else {
            classExtends = "";
        }

        final int aClassDefinitionIndex =
                this.indexOf(sb, classDeclaration + classExtends, false)
                + classDeclaration.length();

        if (indexEntityBase == -1) {
            sb.insert(aClassDefinitionIndex, " extends EntityResourceBase");
        } else {
            sb.replace(aClassDefinitionIndex, aClassDefinitionIndex + "EntityBase".length(), " EntityResourceBase");
        }
//
        // Add import EntityBase if it doesn't exist yet
        if (!this.entity.getImports().contains("EntityResourceBase")) {
            final int packageIndex = this.indexOf(sb, "package", false);
            final int lineAfterPackageIndex =
                    sb.indexOf("\n", packageIndex) + 1;
            sb.insert(lineAfterPackageIndex,
                    String.format("%nimport %s.%s.base.EntityResourceBase;%n",
                            generator.getAdapter().getApplicationMetadata()
                            .getProjectNameSpace().replace('/', '.'),
                            generator.getAdapter().getModel()));
        }

        TactFileUtils.stringBufferToFile(sb, entityFile);
    }

    /**
     * Implement an empty contructor if it doesn't already exists.
     * @param generator
     * @param fileString The string buffer representing the file
     * @param classMeta The classMetadata
     */
    private final void implementEmptyConstructor(
            BaseGenerator<? extends IAdapter> generator,
            final SourceFileManipulator manipulator,
            final ClassMetadata classMeta) {
        if (!this.alreadyImplementsDefaultConstructor(classMeta)) {
            manipulator.generateMethod(
                    "defaultConstructor.java",
                    generator.getDatamodel());
        }
    }

    @Override
    public void execute(BaseGenerator<? extends IAdapter> generator) {
        this.updateEntity(generator);
    }

    /**
     * Returns the first index of a content in a String buffer.
     * (can exclude comments)
     * @param sb The Strinbuffer to parse.
     * @param content The content to search for.
     * @param allowComments True to include comments in the search.
     * @return the index of the found String. -1 if nothing found.
     */
    private int indexOf(final StringBuffer sb,
            final String content,
            final boolean allowComments) {
        return this.indexOf(sb, content, 0, allowComments);
    }

    /**
     * Returns the first index of a content in a String buffer
     * after the given index.
     * (can exclude comments)
     * @param sb The Strinbuffer to parse.
     * @param content The content to search for.
     * @param fromIndex The index where to begin the search
     * @param allowComments True to include comments in the search.
     * @return the index of the found String. -1 if nothing found.
     */
    private int indexOf(final StringBuffer sb,
            final String content,
            final int fromIndex,
            final boolean allowComments) {
        int fIndex = fromIndex;
        int index = -1;
        if (allowComments) {
            index = sb.indexOf(content, fIndex);
        } else {
            int tmpIndex;
            do {
                tmpIndex = sb.indexOf(content, fIndex);
                final int lastCommentClose = sb.lastIndexOf("*/", tmpIndex);
                final int lastCommentOpen = sb.lastIndexOf("/*", tmpIndex);
                final int lastLineComment = sb.lastIndexOf("//", tmpIndex);
                final int lastCarriotRet = sb.lastIndexOf("\n", tmpIndex);
                // If the last multi-line comment is close
                // and if there is a carriot return
                // after the last single-line comment
                if (lastCommentClose >= lastCommentOpen
                    &&  lastLineComment  <= lastCarriotRet) {
                    // Index is good
                    index = tmpIndex;
                    break;
                } else {
                    fIndex = tmpIndex + 1;
                }
             } while (tmpIndex != -1);
        }

        return index;
    }
}

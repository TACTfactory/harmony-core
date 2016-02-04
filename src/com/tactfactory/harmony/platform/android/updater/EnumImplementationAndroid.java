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
import java.io.IOException;
import java.io.StringWriter;
import java.util.Map;

import com.tactfactory.harmony.generator.BaseGenerator;
import com.tactfactory.harmony.generator.TagConstant;
import com.tactfactory.harmony.meta.ClassMetadata;
import com.tactfactory.harmony.meta.EnumMetadata;
import com.tactfactory.harmony.meta.MethodMetadata;
import com.tactfactory.harmony.platform.IAdapter;
import com.tactfactory.harmony.updater.IUpdaterFile;
import com.tactfactory.harmony.utils.ConsoleUtils;
import com.tactfactory.harmony.utils.TactFileUtils;

import freemarker.template.Template;
import freemarker.template.TemplateException;

//TODO remove
public class EnumImplementationAndroid implements IUpdaterFile {
    private final EnumMetadata enumMeta;

    public EnumImplementationAndroid(EnumMetadata enumMeta) {
        this.enumMeta = enumMeta;
    }

    protected void updateEnum(BaseGenerator<? extends IAdapter> generator) {
        File entityFile = new File(String.format("%s/%s.java",
                generator.getAdapter().getSourceEntityPath(),
                this.getOldestMother(generator, enumMeta).getName()));

        ConsoleUtils.display(">>> Decorate " + enumMeta.getName());

        if (entityFile.exists()) {
            // Load the file once in a String buffer
            final StringBuffer fileString =
                    TactFileUtils.fileToStringBuffer(entityFile);

            this.addGetterAndRetriever(generator, fileString, enumMeta);
            //this.addRetriever(fileString, enumMeta);

            // After treatment on entity, write it in the original file
            TactFileUtils.stringBufferToFile(fileString, entityFile);
        }
    }

    /**
     * Implements serializable in the class if it doesn't already.
     * @param fileString The stringbuffer containing the class java code
     * @param enumMeta The Metadata containing the infos on the enum
     */
    private final void addGetterAndRetriever(
            final BaseGenerator<? extends IAdapter> generator,
            final StringBuffer fileString,
            final EnumMetadata enumMeta) {

        if (!this.alreadyHasGetter(enumMeta)) {
            ConsoleUtils.displayDebug("Add method getValue()");
            this.generateMethod(generator, fileString, enumMeta, "enumGetter.java");
        }

        if (!this.alreadyHasRetriever(enumMeta)) {
            ConsoleUtils.displayDebug("Add method fromValue()");
            this.generateMethod(generator, fileString, enumMeta, "enumRetriever.java");
        }
    }

    /**
     * Check if the class implements the class Serializable.
     * @param enumMeta The Metadata containing the infos on the enum
     * @return True if it already implements serializable
     */
    private final boolean alreadyHasGetter(
            final EnumMetadata enumMeta) {
        boolean ret = false;

        for (final MethodMetadata method : enumMeta.getMethods()) {
            if (method.getName().equals("getValue")
                    && method.getArgumentsTypes().size() == 0) {
                ret = true;
                ConsoleUtils.displayDebug("Already has getValue() !");
            }
        }

        return ret;
    }

    /**
     * Check if the class implements the class Serializable.
     * @param enumMeta The Metadata containing the infos on the enum
     * @return True if it already implements serializable
     */
    private final boolean alreadyHasRetriever(
            final EnumMetadata enumMeta) {
        boolean ret = false;

        for (final MethodMetadata method : enumMeta.getMethods()) {
            if (method.getName().equals("fromValue")
                    && method.getArgumentsTypes().size() == 1
                    && method.getType().equals(enumMeta.getName())) {

                ret = true;
                ConsoleUtils.displayDebug("Already has fromValue() !");
            }
        }

        return ret;
    }

    /**
     * Calculate the indentation level of an EnumMetadata.
     * @param enumMeta The EnumMetadata
     * @return N "\t" appended in a String to get the correct indentation.
     */
    private final String calculateIndentLevel(
            final BaseGenerator<? extends IAdapter> generator,
            final EnumMetadata enumMeta) {
        final StringBuffer buffer = new StringBuffer();

        for (int i = 0; i < this.nbMotherClass(generator, enumMeta); i++) {
            buffer.append('\t');
        }

        return buffer.toString();
    }

    /**
     * Get the number of classes above the given class.
     * @param classMeta The class
     * @return The number of mother classes
     */
    private final int nbMotherClass(final BaseGenerator<? extends IAdapter> generator, final ClassMetadata classMeta) {
        int result = 1;

        if (classMeta.getOuterClass() != null) {
            result += this.nbMotherClass(
                    generator,
                    generator.getAdapter().getApplicationMetadata().getClasses().get(
                            classMeta.getOuterClass()));
        }

        return result;
    }

    /** Recursively get the oldest mother of the given ClassMetadata.
     *
     * @param classMeta The class metadata
     * @return The oldest mother
     */
    private final ClassMetadata getOldestMother(
            final BaseGenerator<? extends IAdapter> generator,
            final ClassMetadata classMeta) {
        ClassMetadata result;

        if (classMeta.getOuterClass() != null) {
            result = getOldestMother(
                    generator,
                    generator.getAdapter().getApplicationMetadata().getClasses().get(
                            classMeta.getOuterClass()));
        } else {
            result = classMeta;
        }

        return result;
    }

    /**
     * Generate a get or set method following the given template.
     * @param fileString The stringbuffer containing the class java code
     * @param enumMeta The concerned enum
     * @param templateName The template file name
     */
    private final void generateMethod(
            final BaseGenerator<? extends IAdapter> generator,
            final StringBuffer fileString,
            final EnumMetadata enumMeta,
            final String templateName) {
        final int insertionIndex = this.getEnumClosingBracketIndex(fileString,
                enumMeta);

        final Map<String, Object> map =
                generator.getAdapter().getApplicationMetadata().toMap(generator.getAdapter());
        map.put("indentLevel", this.calculateIndentLevel(generator, enumMeta));
        map.put(TagConstant.CURRENT_ENTITY, enumMeta.getName());

        try {
            final StringWriter writer = new StringWriter();

            final Template tpl = generator.getCfg().getTemplate(
                    String.format("%s%s",
                            generator.getAdapter().getTemplateSourceCommonPath(),
                            templateName + ".ftl"));
            // Load template file in engine

            tpl.process(map, writer);
            final StringBuffer getString = writer.getBuffer();
            fileString.insert(insertionIndex, getString + "\n\n");

        } catch (final IOException e) {
            ConsoleUtils.displayError(e);
        } catch (final TemplateException e) {
            ConsoleUtils.displayError(e);
        }
    }

    /**
     * Get declaration closing bracket of
     * the given EnumMetadata in the given text.
     * @param text The text to search in.
     * @param enumMeta The enum metadata
     * @return The index of the closing bracket in text.
     */
    private final int getEnumClosingBracketIndex(
            final StringBuffer text,
            final EnumMetadata enumMeta) {

        int openingBracketIndex;
        final int enumDeclarationIndex =
                text.indexOf("enum " + enumMeta.getName());

        openingBracketIndex = text.indexOf("{", enumDeclarationIndex);

        return this.getClosingBracketIndex(text, openingBracketIndex);
    }

    /**
     * Get closing bracket corresponding to the opening bracket.
     * @param text The text to search in.
     * @param openingBracketIndex The opening bracket index
     * @return The index of the closing bracket in text.
     */
    private final int getClosingBracketIndex(
            final StringBuffer text,
            final int openingBracketIndex) {
        int result = -1;

        int openedBlockCount = 0;

        for (int i = openingBracketIndex; i < text.length(); i++) {
            if (text.charAt(i) == '{') {
                openedBlockCount++;
            } else if (text.charAt(i) == '}') {
                openedBlockCount--;
            }

            if (openedBlockCount == 0) {
                result = i;
                break;
            }
        }

        return result;
    }

    @Override
    public void execute(BaseGenerator<? extends IAdapter> generator) {
        this.updateEnum(generator);
    }
}

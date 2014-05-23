/**
 * This file is part of the Harmony package.
 *
 * (c) Mickael Gaillard <mickael.gaillard@tactfactory.com>
 *
 * For the full copyright and license information, please view the LICENSE
 * file that was distributed with this source code.
 */
package com.tactfactory.harmony.plateforme.android.updater;

import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.util.Map;

import com.tactfactory.harmony.meta.ClassMetadata;
import com.tactfactory.harmony.meta.EnumMetadata;
import com.tactfactory.harmony.meta.MethodMetadata;
import com.tactfactory.harmony.plateforme.IAdapter;
import com.tactfactory.harmony.template.TagConstant;
import com.tactfactory.harmony.updater.IUpdaterFile;
import com.tactfactory.harmony.utils.ConsoleUtils;
import com.tactfactory.harmony.utils.TactFileUtils;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

//TODO remove
public class EnumImplementationAndroid implements IUpdaterFile {
    private final IAdapter adapter;
    private final Configuration configuration;
    private final EnumMetadata enumMeta;
    
    public EnumImplementationAndroid(IAdapter adapter, Configuration cfg,
            EnumMetadata enumMeta) {
        this.adapter = adapter;
        this.configuration = cfg;
        this.enumMeta = enumMeta;
    }
    
    protected void updateEnum() {
        File entityFile = new File(String.format("%s/%s.java",
                this.adapter.getSourceEntityPath(),
                this.getOldestMother(enumMeta).getName()));

        ConsoleUtils.display(">>> Decorate " + enumMeta.getName());
        
        if (entityFile.exists()) {
            // Load the file once in a String buffer
            final StringBuffer fileString =
                    TactFileUtils.fileToStringBuffer(entityFile);
    
            this.addGetterAndRetriever(fileString, enumMeta);
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
            final StringBuffer fileString,
            final EnumMetadata enumMeta) {

        if (!this.alreadyHasGetter(enumMeta)) {
            ConsoleUtils.displayDebug("Add method getValue()");
            this.generateMethod(fileString, enumMeta, "enumGetter.java");
        }

        if (!this.alreadyHasRetriever(enumMeta)) {
            ConsoleUtils.displayDebug("Add method fromValue()");
            this.generateMethod(fileString, enumMeta, "enumRetriever.java");
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
    private final String calculateIndentLevel(final EnumMetadata enumMeta) {
        final StringBuffer buffer = new StringBuffer();

        for (int i = 0; i < this.nbMotherClass(enumMeta); i++) {
            buffer.append('\t');
        }

        return buffer.toString();
    }

    /**
     * Get the number of classes above the given class.
     * @param classMeta The class
     * @return The number of mother classes
     */
    private final int nbMotherClass(final ClassMetadata classMeta) {
        int result = 1;
        
        if (classMeta.getOuterClass() != null) {
            result += this.nbMotherClass(
                    this.adapter.getApplicationMetadata().getClasses().get(
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
            final ClassMetadata classMeta) {
        ClassMetadata result;
        
        if (classMeta.getOuterClass() != null) {
            result = getOldestMother(
                    this.adapter.getApplicationMetadata().getClasses().get(
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
    private final void generateMethod(final StringBuffer fileString,
            final EnumMetadata enumMeta,
            final String templateName) {
        final int insertionIndex = this.getEnumClosingBracketIndex(fileString,
                enumMeta);

        final Map<String, Object> map =
                this.adapter.getApplicationMetadata().toMap(this.adapter);
        map.put("indentLevel", this.calculateIndentLevel(enumMeta));
        map.put(TagConstant.CURRENT_ENTITY, enumMeta.getName());

        try {
            final StringWriter writer = new StringWriter();

            final Template tpl = this.configuration.getTemplate(
                    String.format("%s%s",
                            this.adapter.getTemplateSourceCommonPath(),
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
    public void execute() {
        this.updateEnum();
    }
}

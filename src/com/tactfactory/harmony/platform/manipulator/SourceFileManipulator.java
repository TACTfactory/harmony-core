/**
 * This file is part of the Harmony package.
 *
 * (c) Mickael Gaillard <mickael.gaillard@tactfactory.com>
 *
 * For the full copyright and license information, please view the LICENSE
 * file that was distributed with this source code.
 */
package com.tactfactory.harmony.platform.manipulator;

import java.io.File;
import java.util.Map;

import com.tactfactory.harmony.meta.ClassMetadata;
import com.tactfactory.harmony.meta.EntityMetadata;
import com.tactfactory.harmony.meta.FieldMetadata;
import com.tactfactory.harmony.platform.BaseAdapter;
import com.tactfactory.harmony.platform.IAdapter;
import com.tactfactory.harmony.utils.ConsoleUtils;
import com.tactfactory.harmony.utils.TactFileUtils;

import freemarker.template.Configuration;

/**
 * Basic source string buffer.
 */
public abstract class SourceFileManipulator {
	/** The file. */
	protected File file;
	/** The buffer. */
	protected StringBuffer buffer;
	/** Associated adapter. */
	protected BaseAdapter adapter;
	/** Template configuration. */
	protected Configuration config;

	/**
	 * Constructor.
	 *
	 * @param file The file to associate this buffer with.
	 */
	public SourceFileManipulator(
			final File file,
			final BaseAdapter adapter,
			final Configuration config) {
		this.file = file;
		this.buffer = TactFileUtils.fileToStringBuffer(this.file);
		this.adapter = adapter;
		this.config = config;
	}

	/**
	 * Generate a method at the end of the class.
	 *
	 * @param template The template to used to generate the method.
	 * @param model The datamodel.
	 *
	 * @return True if the method has been generated successfully. False if it
	 *     already exists.
	 */
	public abstract boolean generateMethod(
			final String templateName,
			final Map<String, Object> model);


	/**
	 * Implements serializable in the class if it doesn't already.
	 *
	 * @param classMeta The Metadata containing the infos on the java class
	 * @param className the name of the class to implement
	 *
	 * @return True if the implement has been added successfully. False if it
	 *     already exists.
	 */
	public abstract boolean addImplement(
			final ClassMetadata classMeta,
			final String className);

    /**
     * Implements serializable in the class if it doesn't already.
     *
     * @param classMeta The Metadata containing the infos on the java class
     * @param className the name of the class to implement
     *
     * @return True if the implement has been added successfully. False if it
     *     already exists.
     */
    public abstract boolean addField(
            final IAdapter iAdapter,
            final ClassMetadata classMeta,
            final String className,
            final FieldMetadata fieldMetadata);

	/**
	 * Import serializable in the class if it doesn't already.
	 *
	 * @param classMeta The Metadata containing the infos on the java class
	 * @param className The name of the class to import
	 * @param classPackage The package of the class to import
	 *
	 * @return True if the import has been added successfully. False if it
	 *     already exists.
	 */
	public abstract boolean addImport(
			final ClassMetadata classMeta,
			final String className,
			final String classPackage);

	/**
	 * Generate a get or set method following the given template.
	 *
	 * @param f The concerned field
	 * @param templateName The template file name
	 *
	 * @return True if the accessors has been added successfully. False if they
	 *     already exists.
	 */
	public abstract boolean generateFieldAccessor(
			final FieldMetadata f,
			final String templateName,
			final EntityMetadata model);

	/**
	 * Generate a field following the given template.
	 *
	 * @param templateName The template file name
	 *
	 * @return True if the field has been added successfully. False if it
	 *     already exists.
	 */
	public abstract boolean generateField(final String templateName,
			final Map<String, Object> model);

	/**
	 * Check if the given field declaration is already present in the file.
	 *
	 * @param fieldDeclaration The field declaration.
	 *
	 * @return True if already exists. False otherwise.
	 */
	public abstract boolean alreadyHasField(final String fieldDeclaration);

	/**
	 * Regenerate a method following the given template.
	 *
	 * @param templateName The template file name
	 * @param methodSignature The signature of the method
	 *
	 * @return True if the method has been added successfully. False if it
	 *     already exists.
	 */
	public abstract boolean regenerateMethod(
			final String templateName,
			final String methodSignature,
			final Map<String, Object> model);

	/**
	 * Returns the first index of a content in a String buffer
	 * after the given index.
	 * (can exclude comments)
	 *
	 * @param sb The Strinbuffer to parse.
	 * @param content The content to search for.
	 * @param fromIndex The index where to begin the search
	 * @param allowComments True to include comments in the search.
	 * @return the index of the found String. -1 if nothing found.
	 */
	protected abstract int indexOf(
			final String content,
			final int fromIndex,
			final boolean allowComments);

	/**
	 * Returns the first index of a content in a String buffer.
	 * (can exclude comments)
	 * @param sb The Strinbuffer to parse.
	 * @param content The content to search for.
	 * @param allowComments True to include comments in the search.
	 * @return the index of the found String. -1 if nothing found.
	 */
	protected int indexOf(
			final String content,
			final boolean allowComments) {
		return this.indexOf(content, 0, allowComments);
	}

	/**
	 * Checks if given class already implements given class.
	 * @param classMeta The class to check
	 * @param className The interface name
	 * @return True if already implements
	 */
	public final boolean alreadyImplementsClass(
			final ClassMetadata classMeta,
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
     * Checks if given class already implements given class.
     * @param classMeta The class to check
     * @param className The interface name
     * @return True if already implements
     */
    public final boolean alreadyFieldClass(
            final ClassMetadata classMeta,
            final String className) {
        boolean ret = false;
        for (Map.Entry<String, FieldMetadata> entry : classMeta.getFields().entrySet()) {
            if (className.equals(entry.getValue())) {
                ret = true;

                ConsoleUtils.displayDebug(
                        "Already implements " + className + " !");

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
	public final boolean alreadyImportsClass(final ClassMetadata classMeta,
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
	 * Returns the position of the corresponding closing bracket.
	 * @param fileString The file to parse
	 * @param openingBracketIndex The opening bracket
	 * @return The position
	 */
	protected int findClosingBracket(
			final int openingBracketIndex) {
		final String file = this.buffer.toString();
		int i;
		int bracketCounter = 0;
		for (i = openingBracketIndex; i < file.length(); i++) {
			if (file.charAt(i) == '{') {
				bracketCounter++;
			} else if (file.charAt(i) == '}') {
				bracketCounter--;
			}

			if (bracketCounter == 0) {
				break;
			}
		}

		return i;
	}

	/**
	 * Overwrite the file with the modifications.
	 */
	public void writeFile() {
		TactFileUtils.stringBufferToFile(this.buffer, this.file);
	}
}

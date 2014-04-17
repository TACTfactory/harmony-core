/**
 * This file is part of the Harmony package.
 *
 * (c) Mickael Gaillard <mickael.gaillard@tactfactory.com>
 *
 * For the full copyright and license information, please view the LICENSE
 * file that was distributed with this source code.
 */
package com.tactfactory.harmony.template;

import java.util.Locale;

import com.tactfactory.harmony.meta.TranslationMetadata;
import com.tactfactory.harmony.plateforme.IAdapter;
import com.tactfactory.harmony.updater.ITranslateFileUtil;
import com.tactfactory.harmony.utils.ConsoleUtils;

/**
 * Strings and config generator.
 *
 */
public class TranslationGenerator extends BaseGenerator {

	/**
	 * Constructor.
	 * @param adapter The adapter to use.
	 * @throws Exception if adapter is null
	 */
	public TranslationGenerator(final IAdapter adapter) throws Exception {
		super(adapter);
	}

	/**
	 * Update XML Strings.
	 */
	public final void generateStringsXml() {

		ConsoleUtils.display(">> Generate translate string...");

		ITranslateFileUtil translateUtil =
		        this.getAdapter().getAdapterProject().getTranslateFileUtil();
		
		translateUtil.open(this.getAdapter().getStringsPathFile());
		
		for (final TranslationMetadata translationMeta
				: this.getAppMetas().getTranslates().values()) {
		    String addedString = translateUtil.addElement(
		            translationMeta.getKey(),
		            translationMeta.getI18n().get(Locale.getDefault()));

            translationMeta.getI18n().put(
                    Locale.getDefault(),
                    addedString);
		}

		translateUtil.save();
	}
}

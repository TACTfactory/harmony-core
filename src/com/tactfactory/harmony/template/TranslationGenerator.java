/**
 * This file is part of the Harmony package.
 *
 * (c) Mickael Gaillard <mickael.gaillard@tactfactory.com>
 *
 * For the full copyright and license information, please view the LICENSE
 * file that was distributed with this source code.
 */
package com.tactfactory.harmony.template;

import java.util.Comparator;
import java.util.Locale;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.Namespace;

import com.tactfactory.harmony.meta.TranslationMetadata;
import com.tactfactory.harmony.plateforme.BaseAdapter;
import com.tactfactory.harmony.utils.ConsoleUtils;
import com.tactfactory.harmony.utils.TactFileUtils;
import com.tactfactory.harmony.utils.XMLUtils;

/**
 * Strings and config generator.
 *
 */
public class TranslationGenerator extends BaseGenerator {
	/**
	 * Bundle name.
	 */
	private static final String NAME = "name";

	/**
	 * Constructor.
	 * @param adapter The adapter to use.
	 * @throws Exception if adapter is null
	 */
	public TranslationGenerator(final BaseAdapter adapter) throws Exception {
		super(adapter);
	}

	/**
	 * Update XML Strings.
	 */
	public final void generateStringsXml() {

		ConsoleUtils.display(">> Generate translate string...");

		final Document doc;
		
		if (TactFileUtils.exists(this.getAdapter().getStringsPathFile())) {
			doc = XMLUtils.openXMLFile(
					this.getAdapter().getStringsPathFile());
		} else {
			Element rootElement = new Element("resources");
			doc = new Document(rootElement);
		}

		// Load Root element
		final Element rootNode =
				doc.getRootElement();

		// Load Name space (required for manipulate attributes)
		final Namespace ns =
				rootNode.getNamespace("android");

		for (final TranslationMetadata translationMeta
				: this.getAppMetas().getTranslates().values()) {
			final Element newNode = new Element("string");

			// Add name to element
			newNode.setAttribute(NAME,
					translationMeta.getKey(),
					ns);

			// Set values
			newNode.setText(
					translationMeta.getI18n().get(
							Locale.getDefault()));

			// If not found Node, create it
			if (!XMLUtils.addValue(newNode, NAME, rootNode)) {
				translationMeta.getI18n().put(Locale.getDefault(),
						newNode.getText());
			}
		}

		// Clean code
		rootNode.sortChildren(new Comparator<Element>() {

			@Override
			public int compare(final Element o1, final Element o2) {
				final String metaName1 = o1.getAttributeValue(NAME, ns);
				final String metaName2 = o2.getAttributeValue(NAME, ns);
				final TranslationMetadata meta1 =
						TranslationGenerator.this.getAppMetas()
						.getTranslates()
						.get(metaName1);
				final TranslationMetadata meta2 =
						TranslationGenerator.this.getAppMetas()
						.getTranslates()
						.get(metaName2);

				if (meta1 != null && meta2 != null) {
					final int groupScore =
							meta1.getGroup().getValue()
							- meta2.getGroup().getValue();
					if (groupScore != 0) {
						return groupScore;
					}
				}

				return metaName1.compareToIgnoreCase(metaName2);
			}
		});

		XMLUtils.writeXMLToFile(doc, this.getAdapter().getStringsPathFile());
	}
}

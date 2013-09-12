/**
 * This file is part of the Harmony package.
 *
 * (c) Mickael Gaillard <mickael.gaillard@tactfactory.com>
 *
 * For the full copyright and license information, please view the LICENSE
 * file that was distributed with this source code.
 */
package com.tactfactory.harmony.parser.java.visitor;

import japa.parser.ast.body.ConstructorDeclaration;
import japa.parser.ast.body.ModifierSet;
import japa.parser.ast.body.Parameter;

import java.util.List;

import com.tactfactory.harmony.meta.ClassMetadata;
import com.tactfactory.harmony.meta.MethodMetadata;
import com.tactfactory.harmony.utils.ConsoleUtils;

/**
 * JavaParser Constructor Visitor.
 */
public class ConstructorVisitor {

	/**
	 * Visit a constructor declaration to extract its metadata.
	 * @param constructor The constructor declaration to visit.
	 * @param meta The metadata of the class containing this method.
	 * @return The MethodMetadata extracted
	 */
	public final MethodMetadata visit(final ConstructorDeclaration constructor,
			final ClassMetadata meta) {
    	// Call the parsers which have been registered by the bundle
    	/*for (final BaseParser bParser
    			: JavaModelParser.getBundleParsers()) {
    		bParser.visitMethod(method, meta);
    	}*/

		final MethodMetadata methodMeta = new MethodMetadata();
		methodMeta.setName(constructor.getName());
		methodMeta.setFinal(ModifierSet.isFinal(constructor.getModifiers()));

		// Add Parameters
		final List<Parameter> parameters = constructor.getParameters();
		if (parameters != null) {
			for (final Parameter param : parameters) {
				methodMeta.getArgumentsTypes().add(
						param.getType().toString());
			}
		}

		//meta.getMethods().add(methodMeta);

		// Debug Log
		if (ConsoleUtils.isDebug()) {
			final StringBuilder builder = new StringBuilder(
					String.format("\tMethod : %s %s(",
							methodMeta.getType(), methodMeta.getName()));

			for (final String args : methodMeta.getArgumentsTypes()) {
				if (!args.equals(methodMeta.getArgumentsTypes().get(0))) {
					builder.append(", ");
				}

				builder.append(String.format("%s", args));
			}

			builder.append(')');

			ConsoleUtils.displayDebug(builder.toString());
		}

		return methodMeta;
	}
}

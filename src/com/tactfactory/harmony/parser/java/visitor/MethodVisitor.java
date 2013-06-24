package com.tactfactory.harmony.parser.java.visitor;

import japa.parser.ast.body.MethodDeclaration;
import japa.parser.ast.body.ModifierSet;
import japa.parser.ast.body.Parameter;

import java.util.List;

import com.tactfactory.harmony.parser.JavaModelParser;
import com.tactfactory.harmony.meta.ClassMetadata;
import com.tactfactory.harmony.meta.MethodMetadata;
import com.tactfactory.harmony.parser.BaseParser;
import com.tactfactory.harmony.utils.ConsoleUtils;

/**
 * JavaParser Method Visitor.
 */
public class MethodVisitor {
	
	/**
	 * Visit a method declaration to extract its metadata.
	 * @param method The method declaration to visit.
	 * @param meta The metadata of the class containing this method. 
	 * @return The MethodMetadata extracted
	 */
	public final MethodMetadata visit(final MethodDeclaration method,
			final ClassMetadata meta) {
    	// Call the parsers which have been registered by the bundle
    	for (final BaseParser bParser 
    			: JavaModelParser.getBundleParsers()) {
    		bParser.visitMethod(method, meta);
    	}
		
		final MethodMetadata methodMeta = new MethodMetadata();
		methodMeta.setName(method.getName());
		methodMeta.setType(method.getType().toString()); 
		methodMeta.setFinal(ModifierSet.isFinal(method.getModifiers()));
		
		// Add Parameters
		final List<Parameter> parameters = method.getParameters();
		if (parameters != null) {
			for (final Parameter param : parameters) {
				methodMeta.getArgumentsTypes().add(
						param.getType().toString());
			}
		}
		
		meta.getMethods().add(methodMeta);
		
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

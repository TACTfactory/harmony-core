package com.tactfactory.mda.parser.java.visitor;

import japa.parser.ast.ImportDeclaration;
import japa.parser.ast.visitor.GenericVisitorAdapter;

import com.tactfactory.mda.meta.ClassMetadata;
import com.tactfactory.mda.parser.BaseParser;
import com.tactfactory.mda.parser.java.JavaModelParser;
import com.tactfactory.mda.utils.ConsoleUtils;

/**
 * JavaParser import Visitor.
 */
public class ImportVisitor 
		extends GenericVisitorAdapter<String, ClassMetadata> {
	
	/**
	 * Visit an import declaration.
	 * @param imp The import declaration to visit
	 * @param meta The classAssociated with this import
	 * @return the import name.  
	 */
	@Override
	public final String visit(final ImportDeclaration imp, 
			final ClassMetadata meta) {
    	// Call the parsers which have been registered by the bundle
    	for (final BaseParser bParser 
    			: JavaModelParser.getBundleParsers()) {
    		bParser.visitImport(imp, meta);
    	}
    	
		final String impName = imp.getName().getName();
		//meta.getImports().add(impName);
		
		// Debug Log
		ConsoleUtils.displayDebug("\tImport : " + impName);
		return impName;
	}
}

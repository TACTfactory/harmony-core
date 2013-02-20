/**
 * This file is part of the Harmony package.
 *
 * (c) Gregg Cesarine <gregg.cesarine@tactfactory.com>
 *
 * For the full copyright and license information, please view the LICENSE
 * file that was distributed with this source code.
 */
package com.tactfactory.mda.bundles.search.parser;

import japa.parser.ast.ImportDeclaration;
import japa.parser.ast.body.ClassOrInterfaceDeclaration;
import japa.parser.ast.body.FieldDeclaration;
import japa.parser.ast.body.MethodDeclaration;
import japa.parser.ast.expr.AnnotationExpr;

import com.tactfactory.mda.bundles.search.annotation.Searchable;
import com.tactfactory.mda.bundles.search.meta.SearchMetadata;
import com.tactfactory.mda.meta.ClassMetadata;
import com.tactfactory.mda.meta.FieldMetadata;
import com.tactfactory.mda.parser.BaseParser;
import com.tactfactory.mda.utils.PackageUtils;

public class SearchParser  extends BaseParser{
	private static final String SEARCH = "search";
	private static final String ANNOT_SEARCHABLE = PackageUtils.extractNameEntity(Searchable.class);
	
	@Override
	public void visitClass(final ClassOrInterfaceDeclaration field, final ClassMetadata meta) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visitField(final FieldDeclaration field, final ClassMetadata meta) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visitMethod(final MethodDeclaration method, final ClassMetadata meta) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visitImport(final ImportDeclaration imp, final ClassMetadata meta) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visitClassAnnotation(final ClassMetadata cm,
			final AnnotationExpr fieldAnnot) {
		// TODO Auto-generated method stub
	}

	@Override
	public void visitFieldAnnotation(final FieldMetadata field,
			final AnnotationExpr fieldAnnot, final ClassMetadata meta) {

		if(fieldAnnot.getName().toString().equals(ANNOT_SEARCHABLE)){
			final SearchMetadata sm = new SearchMetadata();
			field.options.put(SEARCH, sm);
		}
		
	}
}
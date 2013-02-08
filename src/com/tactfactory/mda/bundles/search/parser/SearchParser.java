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
	public void visitClass(ClassOrInterfaceDeclaration field, ClassMetadata meta) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visitField(FieldDeclaration field, ClassMetadata meta) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visitMethod(MethodDeclaration method, ClassMetadata meta) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visitImport(ImportDeclaration imp, ClassMetadata meta) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visitClassAnnotation(ClassMetadata cm,
			AnnotationExpr fieldAnnot) {
		// TODO Auto-generated method stub
	}

	@Override
	public void visitFieldAnnotation(FieldMetadata field,
			AnnotationExpr fieldAnnot, ClassMetadata meta) {

		if(fieldAnnot.getName().toString().equals(ANNOT_SEARCHABLE)){
			SearchMetadata sm = new SearchMetadata();
			field.options.put(SEARCH, sm);
		}
		
	}
}
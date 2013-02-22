/**
 * This file is part of the Harmony package.
 *
 * (c) Mickael Gaillard <mickael.gaillard@tactfactory.com>
 *
 * For the full copyright and license information, please view the LICENSE
 * file that was distributed with this source code.
 */
package com.tactfactory.mda.bundles.social.parser;

import japa.parser.ast.ImportDeclaration;
import japa.parser.ast.body.ClassOrInterfaceDeclaration;
import japa.parser.ast.body.FieldDeclaration;
import japa.parser.ast.body.MethodDeclaration;
import japa.parser.ast.expr.AnnotationExpr;

import com.tactfactory.mda.bundles.social.annotation.Social;
import com.tactfactory.mda.bundles.social.meta.SocialMetadata;
import com.tactfactory.mda.meta.ClassMetadata;
import com.tactfactory.mda.meta.FieldMetadata;
import com.tactfactory.mda.parser.BaseParser;
import com.tactfactory.mda.utils.PackageUtils;

/**
 * Parser for social annotation.
 *
 */
public class SocialParser extends BaseParser {
	/** Bundle name. */ 
	private static final String SOCIAL = "social";
	/** Social Annotation name. */
	private static final String ANNOT_SOCIAL =
			PackageUtils.extractNameEntity(Social.class);
	
	@Override
	public void visitClass(final ClassOrInterfaceDeclaration field, 
			final ClassMetadata meta) {
		// TODO Auto-generated method stub
		
	}

	@Override
	
	public void visitField(final FieldDeclaration field,
			final ClassMetadata meta) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visitMethod(final MethodDeclaration method, 
			final ClassMetadata meta) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visitImport(final ImportDeclaration imp,
			final ClassMetadata meta) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public final void visitClassAnnotation(final ClassMetadata cm,
			final AnnotationExpr fieldAnnot) {
		if (fieldAnnot.getName().toString().equals(ANNOT_SOCIAL)) {
			final SocialMetadata rm = new SocialMetadata();
			rm.isEnabled = true;
			cm.options.put(SOCIAL, rm);
		}
		
	}

	@Override
	public void visitFieldAnnotation(final FieldMetadata field,
			final AnnotationExpr fieldAnnot, final ClassMetadata meta) {
		// TODO Auto-generated method stub
		
	}

}

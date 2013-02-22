/**
 * This file is part of the Harmony package.
 *
 * (c) Mickael Gaillard <mickael.gaillard@tactfactory.com>
 *
 * For the full copyright and license information, please view the LICENSE
 * file that was distributed with this source code.
 */
package com.tactfactory.mda.bundles.rest.parser;

import japa.parser.ast.ImportDeclaration;
import japa.parser.ast.body.ClassOrInterfaceDeclaration;
import japa.parser.ast.body.FieldDeclaration;
import japa.parser.ast.body.MethodDeclaration;
import japa.parser.ast.expr.AnnotationExpr;
import japa.parser.ast.expr.MemberValuePair;
import japa.parser.ast.expr.NormalAnnotationExpr;
import japa.parser.ast.expr.StringLiteralExpr;

import java.util.List;

import com.tactfactory.mda.bundles.rest.annotation.Rest;
import com.tactfactory.mda.bundles.rest.meta.RestMetadata;
import com.tactfactory.mda.meta.ClassMetadata;
import com.tactfactory.mda.meta.FieldMetadata;
import com.tactfactory.mda.parser.BaseParser;
import com.tactfactory.mda.utils.PackageUtils;

public class RestParser extends BaseParser {
	private static final String REST = "rest";
	private static final String ANNOT_REST = 
			PackageUtils.extractNameEntity(Rest.class);
	private static final String ANNOT_REST_SECURITY = "security";
	private static final String ANNOT_REST_URI = "uri";
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
	public void visitClassAnnotation(final ClassMetadata cm,
			final AnnotationExpr fieldAnnot) {
		if (fieldAnnot.getName().toString().equals(ANNOT_REST)) {
			final RestMetadata rm = new RestMetadata();
			rm.isEnabled = true;
			if (fieldAnnot instanceof NormalAnnotationExpr) {
				final NormalAnnotationExpr norm =
						(NormalAnnotationExpr) fieldAnnot;
				final List<MemberValuePair> pairs = norm.getPairs();
				if (pairs != null) {
					for (final MemberValuePair pair : pairs) {
						if (pair.getName().equals(ANNOT_REST_SECURITY)) {
							//TODO : Generate warning if type not recognized
							String security = "";
							
							if (pair.getValue() instanceof StringLiteralExpr) {
								security = ((StringLiteralExpr)
										pair.getValue()).getValue();
							} else {
								security = pair.getValue().toString();
							}
							
							rm.security = Rest.Security.fromName(security);
						} else
						
						if (pair.getName().equals(ANNOT_REST_URI)) {
							if (pair.getValue() instanceof StringLiteralExpr) {
								rm.uri = ((StringLiteralExpr)
										pair.getValue()).getValue();
							} else {
								rm.uri = pair.getValue().toString();
							}
						}
					}
				}
			}
			cm.options.put(REST, rm);
		}
		
	}

	@Override
	public void visitFieldAnnotation(final FieldMetadata field,
			final AnnotationExpr fieldAnnot, final ClassMetadata meta) {
		// TODO Auto-generated method stub
		
	}

}

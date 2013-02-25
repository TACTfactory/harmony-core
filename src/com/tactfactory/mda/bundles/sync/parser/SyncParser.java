/**
 * This file is part of the Harmony package.
 *
 * (c) Mickael Gaillard <mickael.gaillard@tactfactory.com>
 *
 * For the full copyright and license information, please view the LICENSE
 * file that was distributed with this source code.
 */
package com.tactfactory.mda.bundles.sync.parser;

import japa.parser.ast.ImportDeclaration;
import japa.parser.ast.body.ClassOrInterfaceDeclaration;
import japa.parser.ast.body.FieldDeclaration;
import japa.parser.ast.body.MethodDeclaration;
import japa.parser.ast.expr.AnnotationExpr;
import japa.parser.ast.expr.IntegerLiteralExpr;
import japa.parser.ast.expr.MemberValuePair;
import japa.parser.ast.expr.NormalAnnotationExpr;
import japa.parser.ast.expr.StringLiteralExpr;

import java.util.List;

import com.tactfactory.mda.bundles.sync.annotation.Sync;
import com.tactfactory.mda.bundles.sync.meta.SyncMetadata;
import com.tactfactory.mda.meta.ClassMetadata;
import com.tactfactory.mda.meta.FieldMetadata;
import com.tactfactory.mda.parser.BaseParser;
import com.tactfactory.mda.utils.PackageUtils;

/**
 * Parser for Sync annotations.
 */
public class SyncParser extends BaseParser {
	/** Bundle name. */
	private static final String SYNC = "sync";
	/** Sync annotation name. */
	private static final String ANNOT_SYNC = 
			PackageUtils.extractNameEntity(Sync.class);
	/** Sync argument Level name. */
	private static final String ANNOT_SYNC_LEVEL = "level";
	/** Sync argument Mode name. */
	private static final String ANNOT_SYNC_MODE = "mode";
	/** Sync argument Priority name. */
	private static final String ANNOT_SYNC_PRIORITY = "priority";

	@Override
	public void visitClass(final ClassOrInterfaceDeclaration n,
			final ClassMetadata meta) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public final void visitClassAnnotation(final ClassMetadata cm,
			final AnnotationExpr fieldAnnot) {
		if (fieldAnnot.getName().toString().equals(ANNOT_SYNC)) {
			// Parse Sync arguments
			final SyncMetadata sm = new SyncMetadata();
			if (fieldAnnot instanceof NormalAnnotationExpr) {
				final NormalAnnotationExpr norm =
						(NormalAnnotationExpr) fieldAnnot;
				final List<MemberValuePair> pairs = norm.getPairs();
				for (final MemberValuePair pair : pairs) {
					if (pair.getName().equals(ANNOT_SYNC_PRIORITY)) {
						//TODO : Generate warning if type not recognized
						int priority  = 0;
						
						if (pair.getValue() instanceof IntegerLiteralExpr) {
							priority = Integer.parseInt(((IntegerLiteralExpr) 
									pair.getValue()).getValue());
						} else if (pair.getValue() 
								instanceof StringLiteralExpr) {
							priority = Sync.Priority.fromName(
									((StringLiteralExpr) 
											pair.getValue()).getValue());
						} else {
							priority = Sync.Priority.fromName(
									pair.getValue().toString());
						}
						
						sm.setPriority(priority);
					} else
					
					if (pair.getName().equals(ANNOT_SYNC_MODE)) {
						String mode = "";
						if (pair.getValue() instanceof StringLiteralExpr) {
							mode = ((StringLiteralExpr) 
									pair.getValue()).getValue();
						} else {
							mode = pair.getValue().toString();
						}
						sm.setMode(Sync.Mode.fromName(mode));
					}
					
					if (pair.getName().equals(ANNOT_SYNC_LEVEL)) {
						String level = "";
						if (pair.getValue() instanceof StringLiteralExpr) {
							level = ((StringLiteralExpr) 
									pair.getValue()).getValue();
						} else {
							level = pair.getValue().toString();
						}
						sm.setLevel(Sync.Level.fromName(level));
					}
				}
			}
			cm.getOptions().put(SYNC, sm);
			
			// Update fields of entity (fields of extends base)
			final FieldMetadata serverId = new FieldMetadata(cm);
			serverId.setName("serverId");
			serverId.setColumnName("serverId");
			serverId.setType("integer");
			serverId.setColumnDefinition("integer");
			serverId.setHidden(true);
			serverId.setNullable(true);
			cm.getFields().put(serverId.getName(), serverId);
			
			final FieldMetadata syncDtag = new FieldMetadata(cm);
			syncDtag.setName("sync_dtag");
			syncDtag.setColumnName("sync_dtag");
			syncDtag.setType("boolean");
			syncDtag.setColumnDefinition("boolean");
			syncDtag.setHidden(true);
			syncDtag.setNullable(false);
			cm.getFields().put(syncDtag.getName(), syncDtag);
			
			final FieldMetadata syncUDate = new FieldMetadata(cm);
			syncUDate.setName("sync_uDate");
			syncUDate.setColumnName("sync_uDate");
			syncUDate.setType("datetime");
			syncUDate.setColumnDefinition("datetime");
			syncUDate.setHidden(true);
			syncUDate.setNullable(true);
			syncUDate.setIsLocale(true);
			cm.getFields().put(syncUDate.getName(), syncUDate);
		}
	}

	@Override
	public void visitField(final FieldDeclaration field,
			final ClassMetadata meta) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visitFieldAnnotation(final FieldMetadata field,
			final AnnotationExpr fieldAnnot, final ClassMetadata meta) {
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

}

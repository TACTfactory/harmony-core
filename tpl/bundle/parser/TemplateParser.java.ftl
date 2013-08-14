package ${bundle_namespace}.parser;

import japa.parser.ast.ImportDeclaration;
import japa.parser.ast.body.ClassOrInterfaceDeclaration;
import japa.parser.ast.body.FieldDeclaration;
import japa.parser.ast.body.MethodDeclaration;
import japa.parser.ast.expr.AnnotationExpr;

import com.tactfactory.harmony.meta.ClassMetadata;
import com.tactfactory.harmony.meta.FieldMetadata;
import com.tactfactory.harmony.parser.BaseParser;

/**
 * TODO : Javadoc
 * Default parser generated for ${bundle_name} Bundle.
 */
public class ${bundle_name?cap_first}Parser extends BaseParser {

@	Override
	public void visitClass(ClassOrInterfaceDeclaration n, ClassMetadata meta) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visitClassAnnotation(ClassMetadata cm, AnnotationExpr fieldAnnot) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visitField(FieldDeclaration field, ClassMetadata meta) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visitFieldAnnotation(FieldMetadata field,
			AnnotationExpr fieldAnnot, ClassMetadata meta) {
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
}

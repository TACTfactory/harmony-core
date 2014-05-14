package com.tactfactory.harmony.plateforme.manipulator;

import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

import com.tactfactory.harmony.meta.ClassMetadata;
import com.tactfactory.harmony.meta.FieldMetadata;
import com.tactfactory.harmony.plateforme.BaseAdapter;
import com.tactfactory.harmony.utils.ConsoleUtils;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

/**
 * Source File Manipulator dedicated to Java files.
 */
public class JavaFileManipulator extends SourceFileManipulator {

	/**
	 * Constructor.
	 * 
	 * @param file The file to associate this buffer with.
	 */
	public JavaFileManipulator(
			final File file,
			final BaseAdapter adapter,
			final Configuration config) {
		super(file, adapter, config);
	}

	@Override
	public boolean generateMethod(
			final String templateName,
			final Map<String, Object> model) {
		
		boolean result;
		final int lastAccolade = this.buffer.lastIndexOf("}");
		
		try {
			final StringWriter writer = new StringWriter();
			final Template tpl = this.config.getTemplate(
					String.format("%s%s",
							this.adapter.getTemplateSourceCommonPath(),
							templateName + ".ftl"));
			tpl.process(model, writer);
			final StringBuffer getString = writer.getBuffer();
			this.buffer.insert(lastAccolade, getString + "\n");
			result = true;
		} catch (final IOException e) {
			ConsoleUtils.displayError(e);
			result = false;
		} catch (final TemplateException e) {
			ConsoleUtils.displayError(e);
			result = false;
		}
		return result;
	}

	@Override
	public boolean addImplement(ClassMetadata classMeta, String className) {
		boolean result;
		if (!this.alreadyImplementsClass(classMeta, className)) {
			ConsoleUtils.displayDebug("Add " + className + " implement");
			final int firstAccolade = this.indexOf("{", false);
			
			// Class already implements an interface which is not the class
			if (classMeta.getImplementTypes().size() > 0) { 
				this.buffer.insert(firstAccolade, ", " + className + " ");
			} else {
				this.buffer.insert(firstAccolade, 
						" implements " + className + " ");
			}		
			classMeta.getImplementTypes().add(className);
			result = true;
		} else {
			result = false;
		}
		return result;
	}

	@Override
	public boolean addImport(ClassMetadata classMeta, String className,
			String classPackage) {
		boolean result;
		
		if (!this.alreadyImportsClass(classMeta, className)) {
			ConsoleUtils.displayDebug("Add " + className + " import");
			int insertPos;
			String prefix = "";
			if (classMeta.getImports().size() > 0) {
				insertPos = this.indexOf("import", false);
			} else {
				insertPos = this.indexOf(";", false) + 1;
				prefix = "\n\n";
			}
			this.buffer.insert(
					insertPos, 
					prefix + "import " + classPackage + ";\n");
			
			classMeta.getImports().add(className);
			result = true;
		} else {
			result = false;
		}
		
		return result;
	}

	@Override
	public boolean generateFieldAccessor(
			final FieldMetadata f,
			final String templateName) {
		boolean result;

		final int lastAccolade = this.buffer.lastIndexOf("}");

		final Map<String, Object> map = new HashMap<String, Object>();
		map.put("property", f.getName());
		map.put("property_type", this.adapter.getNativeType(f));

		try {
			final StringWriter writer = new StringWriter();

			final Template tpl = this.config.getTemplate(
					String.format("%s%s",
							this.adapter.getTemplateSourceCommonPath(),
							templateName + ".ftl"));
			// Load template file in engine

			tpl.process(map, writer);
			final StringBuffer getString = writer.getBuffer();
			this.buffer.insert(lastAccolade, 
					getString.toString() + "\n");

			result = true;
		} catch (final IOException e) {
			ConsoleUtils.displayError(e);
			result = false;
		} catch (final TemplateException e) {
			ConsoleUtils.displayError(e);
			result = false;
		}
		return result;
	}

	@Override
	public boolean generateField(
			String templateName,
			Map<String, Object> model) {
		boolean result;
		
		final int firstAccolade = this.indexOf("{", false) + 1;
		
		try {
			final StringWriter writer = new StringWriter();
			
			final Template tpl = this.config.getTemplate(
					String.format("%s%s",
							this.adapter.getTemplateSourceCommonPath(),
							templateName + ".ftl"));
			// Load template file in engine
			
			tpl.process(model, writer);
			final StringBuffer getString = writer.getBuffer();
			this.buffer.insert(firstAccolade, "\n\n" + getString);
			
			result = true;
		} catch (final IOException e) {
			ConsoleUtils.displayError(e);
			result = false;
		} catch (final TemplateException e) {
			ConsoleUtils.displayError(e);
			result = false;
		}		
		
		return result;
	}

	@Override
	public boolean alreadyHasField(String fieldDeclaration) {
		return this.indexOf(fieldDeclaration, false) > -1;
	}

	@Override
	public boolean regenerateMethod(
			final String templateName,
			final String methodSignature,
			final Map<String, Object> model) {
		
		boolean result;
		
		final int methodStart = this.indexOf(methodSignature, false);
		
		if (methodStart != -1) {
			final int methodEnd = this.findClosingBracket(
						methodStart + (methodSignature.lastIndexOf('{'))) + 2;
			
			final int realMethodStart = this.buffer.toString().lastIndexOf('}',
					methodStart) + 2;
			
			try {
				final StringWriter writer = new StringWriter();
				
				final Template tpl = this.config.getTemplate(
						String.format("%s%s",
								this.adapter.getTemplateSourceCommonPath(),
								templateName + ".ftl"));
				// Load template file in engine
				
				tpl.process(model, writer);
				final StringBuffer getString = writer.getBuffer();
				this.buffer.replace(
						realMethodStart,
						methodEnd,
						"\n" + getString);
				result = true;
				
			} catch (final IOException e) {
				ConsoleUtils.displayError(e);
				result = false;
			} catch (final TemplateException e) {
				ConsoleUtils.displayError(e);
				result = false;
			}		

		} else {
			result = this.generateMethod(templateName, model);
		}
		return result;
	}

	@Override
	protected int indexOf(
			final String content,
			final int fromIndex,
			final boolean allowComments) {
		int fIndex = fromIndex;
		int index = -1;
		if (allowComments) {
			index = this.buffer.indexOf(content, fIndex);
		} else {
			int tmpIndex;
			do {
				tmpIndex = this.buffer.indexOf(content, fIndex);
				final int lastCommentClose = 
						this.buffer.lastIndexOf("*/", tmpIndex);
				
				final int lastCommentOpen = 
						this.buffer.lastIndexOf("/*", tmpIndex);
				
				final int lastLineComment = 
						this.buffer.lastIndexOf("//", tmpIndex);
				
				final int lastCarriotRet = 
						this.buffer.lastIndexOf("\n", tmpIndex);
				// If the last multi-line comment is close
				// and if there is a carriot return
				// after the last single-line comment
				if (lastCommentClose >= lastCommentOpen
					&& 	lastLineComment  <= lastCarriotRet) {
					// Index is good
					index = tmpIndex;
					break;
				} else {
					fIndex = tmpIndex + 1;
				}
			 } while (tmpIndex != -1);
		}

		return index;
	}

}

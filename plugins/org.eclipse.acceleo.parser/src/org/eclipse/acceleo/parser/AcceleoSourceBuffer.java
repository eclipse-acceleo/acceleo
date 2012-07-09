/*******************************************************************************
 * Copyright (c) 2008, 2012 Obeo.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.acceleo.parser;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.acceleo.internal.parser.ast.CST2ASTConverterWithDocumentationResolver;
import org.eclipse.acceleo.internal.parser.ast.IASTProvider;
import org.eclipse.acceleo.internal.parser.ast.ocl.OCLParser;
import org.eclipse.acceleo.internal.parser.cst.CSTParser;
import org.eclipse.acceleo.internal.parser.cst.utils.FileContent;
import org.eclipse.acceleo.internal.parser.documentation.IDocumentationProvider;
import org.eclipse.acceleo.parser.cst.CstFactory;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EOperation;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.ocl.Environment;

/**
 * The source to parse. It can be created from a file or a buffer. The file property is null if this object
 * has been created from a buffer. You can create an AST model with the method 'createAST'. An AST is created
 * in a single resource. All the dependencies of this resource will be searched in the other resources of the
 * same 'ResourceSet'. You can resolve the links between the different AST models of the current 'ResourceSet'
 * with the method 'resolveAST'. The parsing issues are accessible by using the method 'getProblems'.
 * <p>
 * The resolution step must always be done at the end of the parsing of each file of the 'ResourceSet', like
 * in the following example.
 * <p>
 * <br>
 * Resource resource1 = resourceSet.createResource(uriTarget1);
 * <p>
 * Resource resource2 = resourceSet.createResource(uriTarget2);
 * <p>
 * AcceleoSourceBuffer source1 = new AcceleoSourceBuffer(file1);
 * <p>
 * AcceleoSourceBuffer source2 = new AcceleoSourceBuffer(file1);
 * <p>
 * source1.createAST(resource1);
 * <p>
 * source2.createAST(resource2);
 * <p>
 * <b> source1.resolveAST();
 * <p>
 * source2.resolveAST();
 * <p>
 * </b> </br>
 * 
 * @author <a href="mailto:jonathan.musset@obeo.fr">Jonathan Musset</a>
 */
public class AcceleoSourceBuffer implements IASTProvider, IDocumentationProvider {

	/**
	 * The AST model. It is created when you call the 'createAST' function.
	 */
	protected org.eclipse.acceleo.model.mtl.Module ast;

	/**
	 * The AST creator. It is created when you call the 'createAST' function.
	 * 
	 * @noreference This field is not intended to be referenced by clients.
	 */
	protected CST2ASTConverterWithDocumentationResolver astCreator;

	/**
	 * The file to parse, can be null if the object is created with a buffer.
	 */
	private final AcceleoFile acceleoFile;

	/**
	 * The buffer to parse.
	 */
	private final StringBuffer buffer;

	/**
	 * This list will be initialized with the beginning offset of all lines in the given source buffer.
	 * 
	 * @since 0.8
	 */
	private final List<Integer> lines = new ArrayList<Integer>();

	/**
	 * Parsing problems.
	 */
	private final AcceleoParserProblems problems;

	/**
	 * Parsing warnings.
	 */
	private final AcceleoParserWarnings warnings;

	/**
	 * Parsing informations.
	 */
	private final AcceleoParserInfos infos;

	/**
	 * The encoding of the buffer to parse.
	 */
	private final String encoding;

	/**
	 * The CST model. It is created when you call the 'createAST' function.
	 */
	private org.eclipse.acceleo.parser.cst.Module cst;

	/**
	 * Constructor.
	 * 
	 * @param acceleoFile
	 *            is the Acceleo file to parse
	 * @since 3.0
	 */
	public AcceleoSourceBuffer(AcceleoFile acceleoFile) {
		this.acceleoFile = acceleoFile;
		this.buffer = FileContent.getFileContent(getFile());
		this.encoding = FileContent.getEncoding(buffer);
		this.problems = new AcceleoParserProblems();
		this.warnings = new AcceleoParserWarnings();
		this.infos = new AcceleoParserInfos();
		init();
	}

	/**
	 * Constructor.
	 * 
	 * @param file
	 *            is the file to parse
	 */
	public AcceleoSourceBuffer(File file) {
		this(new AcceleoFile(file, AcceleoFile.simpleModuleName(file)));
	}

	/**
	 * Constructor.
	 * 
	 * @param buffer
	 *            is the buffer to parse, the file property will be null
	 */
	public AcceleoSourceBuffer(StringBuffer buffer) {
		this.acceleoFile = null;
		this.buffer = buffer;
		this.encoding = FileContent.getEncoding(buffer);
		this.problems = new AcceleoParserProblems();
		this.warnings = new AcceleoParserWarnings();
		this.infos = new AcceleoParserInfos();
		init();
	}

	/**
	 * Initializes the line information for the underlying buffer.
	 */
	private void init() {
		lines.add(Integer.valueOf(0));
		int offset = 0;
		while (offset < buffer.length()) {
			if (buffer.charAt(offset) == '\n') {
				lines.add(Integer.valueOf(offset));
			} else if (buffer.charAt(offset) == '\r') {
				lines.add(Integer.valueOf(offset));
				if (buffer.length() > offset + 1 && buffer.charAt(offset + 1) == '\n') {
					offset++;
				}
			}
			offset++;
		}
	}

	/***
	 * Returns the line number of the given offset.
	 * 
	 * @param offset
	 *            Offset we need to know the line of.
	 * @return The line number of the given offset.
	 * @since 0.8
	 */
	public int getLineOfOffset(int offset) {
		int soughtLine = -1;
		// shortcut
		if (offset == 0) {
			soughtLine = 0;
		}
		if (offset == buffer.length()) {
			soughtLine = lines.size();
		}
		if (soughtLine == -1) {
			for (int i = 0; i < lines.size(); i++) {
				if (lines.get(i).intValue() > offset) {
					soughtLine = i;
					break;
				}
			}
		}
		if (soughtLine == -1) {
			soughtLine = lines.size();
		}
		return soughtLine;
	}

	/**
	 * Returns the parsed file, can be null if the object is created with a buffer.
	 * 
	 * @return the parsed file
	 */
	public File getFile() {
		if (acceleoFile != null) {
			return acceleoFile.getMtlFile();
		}
		return null;

	}

	/**
	 * Gets parsing issues.
	 * 
	 * @return the parsing issues
	 */
	public AcceleoParserProblems getProblems() {
		return problems;
	}

	/**
	 * Returns the string buffer to parse.
	 * 
	 * @return the buffer
	 */
	public StringBuffer getBuffer() {
		return buffer;
	}

	/**
	 * Returns the encoding of the buffer to parse.
	 * 
	 * @return the encoding, or null if it isn't specified in the buffer
	 * @since 3.0
	 */
	public String getEncoding() {
		return encoding;
	}

	/**
	 * Gets the AST model. It is created when you call the 'createAST' function.
	 * 
	 * @return the AST model
	 */
	public org.eclipse.acceleo.model.mtl.Module getAST() {
		return ast;
	}

	/**
	 * Gets the CST model. It is created when you call the 'createCST' function.
	 * 
	 * @return the CST model
	 */
	public org.eclipse.acceleo.parser.cst.Module getCST() {
		return cst;
	}

	/**
	 * Gets the OCL parser (from the OCL plug-in).
	 * 
	 * @return the OCL parser, can be null
	 * @deprecated Unused, this will be removed beefore 3.0 is released.
	 * @noreference This method is not intended to be referenced by clients.
	 * @nooverride This method is not intended to be re-implemented or extended by clients.
	 */
	@Deprecated
	public OCLParser getOCL() {
		if (astCreator != null) {
			return astCreator.getOCL();
		}
		return null;
	}

	/**
	 * Returns the environment instance that was used under the covers by the ocl parser. It returns null when
	 * we don't know the OCL environment.
	 * 
	 * @return The environment instance that was used under the covers by the ocl parser, can be null
	 * @since 3.0
	 */
	public Environment<?, EClassifier, EOperation, EStructuralFeature, ?, ?, ?, ?, ?, ?, ?, ?> getOCLEnvironment() {
		if (astCreator != null && astCreator.getOCL() != null) {
			return astCreator.getOCL().getOCLEnvironment();
		}
		return null;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.acceleo.parser.ast.IASTProvider#createAST(org.eclipse.emf.ecore.resource.Resource)
	 */
	public void createAST(Resource resource) {
		if (cst == null) {
			createCST();
		}
		astCreator = new CST2ASTConverterWithDocumentationResolver();
		astCreator.setASTProvider(this);
		astCreator.createAST(cst, resource);
		if (resource.getContents().size() > 0
				&& resource.getContents().get(0) instanceof org.eclipse.acceleo.model.mtl.Module) {
			ast = (org.eclipse.acceleo.model.mtl.Module)resource.getContents().get(0);
			if (ast != null && acceleoFile != null) {
				ast.setNsURI(acceleoFile.getFullModuleName());
			}
		} else {
			ast = null;
		}
	}

	/**
	 * Ensure to compute the whole AST before to use it.
	 */
	public void refreshAST() {
		ast = null;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.acceleo.parser.ast.IASTProvider#resolveAST()
	 */
	public void resolveAST() {
		if (astCreator != null) {
			astCreator.resolveAST(cst);
		}
	}

	/**
	 * Resolution step of the AST creation : OCL and invocations. This function must be called after
	 * 'createAST'. The resolution step can be limited to the specified region, for increasing performances.
	 * Remark : The -1 value means everywhere.
	 * 
	 * @param posBegin
	 *            is the lower bound
	 * @param posEnd
	 *            is the upper bound
	 */
	public void resolveAST(int posBegin, int posEnd) {
		if (astCreator != null) {
			astCreator.resolveAST(cst, posBegin, posEnd);
		}
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.acceleo.parser.ast.IASTProvider#createCST()
	 */
	public void createCST() {
		ast = null;
		CSTParser parser = new CSTParser(this);
		cst = parser.parse();
		if (cst == null) {
			cst = CstFactory.eINSTANCE.createModule();
		}
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.acceleo.internal.parser.ast.IASTProvider#log(java.lang.String, int, int)
	 * @deprecated Use {@link #logProblem(String, int, int)} instead.
	 */
	@Deprecated
	public void log(String message, int posBegin, int posEnd) {
		this.logProblem(message, posBegin, posEnd);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.acceleo.internal.parser.ast.IASTProvider2#logProblem(java.lang.String, int, int)
	 * @since 3.1
	 */
	public void logProblem(final String message, final int posBegin, final int posEnd) {
		int[] pos = trim(posBegin, posEnd);
		int line = FileContent.lineNumber(buffer, pos[0]);
		problems.addProblem(getFile(), message, line, pos[0], pos[1]);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.acceleo.internal.parser.ast.IASTProvider2#logWarning(java.lang.String, int, int)
	 * @since 3.1
	 */
	public void logWarning(final String message, final int posBegin, final int posEnd) {
		int[] pos = trim(posBegin, posEnd);
		int line = FileContent.lineNumber(this.buffer, pos[0]);
		this.warnings.addWarning(message, line, pos[0], pos[1]);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.acceleo.internal.parser.ast.IASTProvider2#logInfo(java.lang.String, int, int)
	 * @since 3.1
	 */
	public void logInfo(final String message, final int posBegin, final int posEnd) {
		int[] pos = trim(posBegin, posEnd);
		int line = FileContent.lineNumber(this.buffer, pos[0]);
		this.infos.addInfo(message, line, pos[0], pos[1]);
	}

	/**
	 * Returns the warnings.
	 * 
	 * @return the warnings
	 * @since 3.1
	 */
	public AcceleoParserWarnings getWarnings() {
		return this.warnings;
	}

	/**
	 * Returns the infos.
	 * 
	 * @return the infos
	 * @since 3.1
	 */
	public AcceleoParserInfos getInfos() {
		return this.infos;
	}

	/**
	 * Gets the smallest range that contains all the significant characters. Returns an array with 2 elements
	 * {trim.begin, trim.end}.
	 * 
	 * @see java.lang.String#trim()
	 * @param posBegin
	 *            is the beginning index of the range
	 * @param posEnd
	 *            is the ending index of the range
	 * @return the smallest range that contains all the significant characters, the length of the array is
	 *         always 2
	 */
	public int[] trim(int posBegin, int posEnd) {
		int b;
		if (posBegin < 0) {
			b = 0;
		} else {
			b = posBegin;
		}
		int e;
		if (posEnd < 0 || posEnd > buffer.length()) {
			e = buffer.length();
		} else {
			e = posEnd;
		}
		while (b < e && Character.isWhitespace(buffer.charAt(b))) {
			b++;
		}
		if (!((posEnd < 0 || b == e) && b + 1 < buffer.length())) {
			while ((e > b + 1) && Character.isWhitespace(buffer.charAt(e - 1))) {
				e--;
			}
		}
		return new int[] {b, e };
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.acceleo.internal.parser.documentation.IDocumentationProvider#resolveASTDocumentation()
	 * @since 3.1
	 */
	public void resolveASTDocumentation() {
		if (this.astCreator != null && this.ast != null) {
			this.astCreator.resolveASTDocumentation(this.ast, this.buffer);
		}
	}
}

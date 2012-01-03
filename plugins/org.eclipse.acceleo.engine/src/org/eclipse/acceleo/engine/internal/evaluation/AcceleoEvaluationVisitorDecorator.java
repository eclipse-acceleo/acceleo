/*******************************************************************************
 * Copyright (c) 2009, 2012 Obeo.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.acceleo.engine.internal.evaluation;

import java.io.File;
import java.util.List;

import org.eclipse.acceleo.engine.AcceleoEvaluationException;
import org.eclipse.acceleo.model.mtl.Block;
import org.eclipse.acceleo.model.mtl.FileBlock;
import org.eclipse.acceleo.model.mtl.ForBlock;
import org.eclipse.acceleo.model.mtl.IfBlock;
import org.eclipse.acceleo.model.mtl.InitSection;
import org.eclipse.acceleo.model.mtl.LetBlock;
import org.eclipse.acceleo.model.mtl.ProtectedAreaBlock;
import org.eclipse.acceleo.model.mtl.Query;
import org.eclipse.acceleo.model.mtl.QueryInvocation;
import org.eclipse.acceleo.model.mtl.Template;
import org.eclipse.acceleo.model.mtl.TemplateInvocation;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.ocl.EvaluationVisitorDecorator;

/**
 * This will allow us to decorate Acceleo-specific evaluation visitor calls. This will mainly be used for the
 * profiler and debugguer functionalities and is not intended to be subclassed by clients.
 * 
 * @author <a href="mailto:laurent.goubet@obeo.fr">Laurent Goubet</a>
 * @noextend This class is not intended to be subclassed by clients.
 * @param <PK>
 *            see {@link #org.eclipse.ocl.AbstractEvaluationVisitor}.
 * @param <C>
 *            see {@link #org.eclipse.ocl.AbstractEvaluationVisitor}.
 * @param <O>
 *            see {@link #org.eclipse.ocl.AbstractEvaluationVisitor}.
 * @param <P>
 *            see {@link #org.eclipse.ocl.AbstractEvaluationVisitor}.
 * @param <EL>
 *            see {@link #org.eclipse.ocl.AbstractEvaluationVisitor}.
 * @param <PM>
 *            see {@link #org.eclipse.ocl.AbstractEvaluationVisitor}.
 * @param <S>
 *            see {@link #org.eclipse.ocl.AbstractEvaluationVisitor}.
 * @param <COA>
 *            see {@link #org.eclipse.ocl.AbstractEvaluationVisitor}.
 * @param <SSA>
 *            see {@link #org.eclipse.ocl.AbstractEvaluationVisitor}.
 * @param <CT>
 *            see {@link #org.eclipse.ocl.AbstractEvaluationVisitor}.
 * @param <CLS>
 *            see {@link #org.eclipse.ocl.AbstractEvaluationVisitor}.
 * @param <E>
 *            see {@link #org.eclipse.ocl.AbstractEvaluationVisitor}.
 */
public class AcceleoEvaluationVisitorDecorator<PK, C, O, P, EL, PM, S, COA, SSA, CT, CLS, E> extends EvaluationVisitorDecorator<PK, C, O, P, EL, PM, S, COA, SSA, CT, CLS, E> {
	/**
	 * Default constructor.
	 * 
	 * @param decoratedVisitor
	 *            The evaluation visitor this instance will decorate.
	 */
	public AcceleoEvaluationVisitorDecorator(
			AcceleoEvaluationVisitor<PK, C, O, P, EL, PM, S, COA, SSA, CT, CLS, E> decoratedVisitor) {
		super(decoratedVisitor);
		decoratedVisitor.setVisitor(this);
	}

	/**
	 * Generates the given text with the given information. This will allow the notification of the generation
	 * listeners.
	 * 
	 * @param string
	 *            String that is to be appended to the current file.
	 * @param sourceBlock
	 *            The block for which this text has been generated.
	 * @param source
	 *            The Object for which was generated this text.
	 * @param fireEvent
	 *            Tells us whether we should fire generation events.
	 */
	public void append(String string, Block sourceBlock, EObject source, boolean fireEvent) {
		getAcceleoDelegate().append(string, sourceBlock, source, fireEvent);
	}

	/**
	 * Demands creation of a writer for the given file path from the generation strategy.
	 * 
	 * @param generatedFile
	 *            File that is to be created.
	 * @param fileBlock
	 *            The file block which asked for this writer. Only used for generation events.
	 * @param source
	 *            The source EObject for this file block. Only used for generation events.
	 * @param appendMode
	 *            If <code>false</code>, the file will be replaced by a new one.
	 * @param charset
	 *            Charset of the target file.
	 * @throws AcceleoEvaluationException
	 *             Thrown if the file cannot be created.
	 */
	public void createFileWriter(File generatedFile, Block fileBlock, EObject source, boolean appendMode,
			String charset) throws AcceleoEvaluationException {
		getAcceleoDelegate().createFileWriter(generatedFile, fileBlock, source, appendMode, charset);
	}

	/**
	 * This will be called to alter all lines of the given <em>text</em> so that they all fit with the given
	 * indentation.
	 * 
	 * @param source
	 *            Text which indentation is to be altered.
	 * @param indentation
	 *            Indentation that is to be given to all of <em>source</em> lines.
	 * @return The input <em>text</em> after its indentation has been modified to fit the given
	 *         <em>indentation</em>.
	 */
	public String fitIndentationTo(String source, String indentation) {
		return getAcceleoDelegate().fitIndentationTo(source, indentation);
	}

	/**
	 * Return the cached result for the given invocation.
	 * 
	 * @param query
	 *            The query for which we need cached results.
	 * @param arguments
	 *            Arguments of the invocation.
	 * @return The cached result if any.
	 */
	public Object getCachedResult(Query query, List<Object> arguments) {
		return getAcceleoDelegate().getCachedResult(query, arguments);
	}

	/**
	 * Caches the result of the given invocation.
	 * 
	 * @param query
	 *            The query for which we need to cache results.
	 * @param arguments
	 *            Arguments of the invocation.
	 * @param result
	 *            The result that is to be cached.
	 */
	public void cacheResult(Query query, List<Object> arguments, Object result) {
		getAcceleoDelegate().cacheResult(query, arguments, result);
	}

	/**
	 * Handles the evaluation of an Acceleo {@link Block}.
	 * 
	 * @param block
	 *            The Acceleo block that is to be evaluated.
	 */
	public void visitAcceleoBlock(Block block) {
		getAcceleoDelegate().visitAcceleoBlock(block);
	}

	/**
	 * Handles the evaluation of an Acceleo {@link FileBlock}.
	 * 
	 * @param fileBlock
	 *            The file block that need be evaluated.
	 */
	public void visitAcceleoFileBlock(FileBlock fileBlock) {
		getAcceleoDelegate().visitAcceleoFileBlock(fileBlock);
	}

	/**
	 * Handles the evaluation of an Acceleo {@link ForBlock}.
	 * 
	 * @param forBlock
	 *            The Acceleo block that is to be evaluated.
	 */
	public void visitAcceleoForBlock(ForBlock forBlock) {
		getAcceleoDelegate().visitAcceleoForBlock(forBlock);
	}

	/**
	 * Handles the evaluation of an Acceleo {@link IfBlock}.
	 * 
	 * @param ifBlock
	 *            The Acceleo block that is to be evaluated.
	 */
	public void visitAcceleoIfBlock(IfBlock ifBlock) {
		getAcceleoDelegate().visitAcceleoIfBlock(ifBlock);
	}

	/**
	 * Handles the evaluation of an Acceleo {@link InitSection}. This will simply add all the declared
	 * variables to the evaluation environment. Evaluation of all blocks should use this in order to save the
	 * variables state <b>before</b> evaluation and be able to restore it afterwards through
	 * {@link #restoreVariables()} .
	 * 
	 * @param init
	 *            The init section containing the variables to process.
	 */
	public void visitAcceleoInitSection(InitSection init) {
		getAcceleoDelegate().visitAcceleoInitSection(init);
	}

	/**
	 * Handles the evaluation of an Acceleo {@link LetBlock}.
	 * 
	 * @param letBlock
	 *            The Acceleo let block that is to be evaluated.
	 */
	public void visitAcceleoLetBlock(LetBlock letBlock) {
		getAcceleoDelegate().visitAcceleoLetBlock(letBlock);
	}

	/**
	 * Handles the evaluation of an Acceleo {@link ProtectedAreaBlock}.
	 * 
	 * @param protectedArea
	 *            The Acceleo protected area that is to be evaluated.
	 */
	public void visitAcceleoProtectedArea(ProtectedAreaBlock protectedArea) {
		getAcceleoDelegate().visitAcceleoProtectedArea(protectedArea);
	}

	/**
	 * Handles the evaluation of an Acceleo {@link QueryInvocation}.
	 * 
	 * @param invocation
	 *            The Acceleo query invocation that is to be evaluated.
	 * @return result of the invocation.
	 */
	public Object visitAcceleoQueryInvocation(QueryInvocation invocation) {
		return getAcceleoDelegate().visitAcceleoQueryInvocation(invocation);
	}

	/**
	 * Handles the evaluation of an Acceleo {@link Template}.
	 * 
	 * @param template
	 *            The Acceleo Template that is to be evaluated.
	 * @return Result of the template evaluation.
	 */
	public String visitAcceleoTemplate(Template template) {
		return getAcceleoDelegate().visitAcceleoTemplate(template);
	}

	/**
	 * Handles the evaluation of an Acceleo {@link TemplateInvocation}.
	 * 
	 * @param invocation
	 *            The Acceleo template invocation that is to be evaluated.
	 * @return result of the invocation.
	 */
	public Object visitAcceleoTemplateInvocation(TemplateInvocation invocation) {
		return getAcceleoDelegate().visitAcceleoTemplateInvocation(invocation);
	}

	/**
	 * Returns the delegate visitor, cast as an Acceleo visitor.
	 * 
	 * @return The delegate visitor.
	 */
	protected AcceleoEvaluationVisitor<PK, C, O, P, EL, PM, S, COA, SSA, CT, CLS, E> getAcceleoDelegate() {
		return (AcceleoEvaluationVisitor<PK, C, O, P, EL, PM, S, COA, SSA, CT, CLS, E>)getDelegate();
	}
}

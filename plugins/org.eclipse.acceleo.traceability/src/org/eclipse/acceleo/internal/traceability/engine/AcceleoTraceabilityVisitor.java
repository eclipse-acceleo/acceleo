/*******************************************************************************
 * Copyright (c) 2009 Obeo.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.acceleo.internal.traceability.engine;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Set;

import org.eclipse.acceleo.common.utils.AcceleoNonStandardLibrary;
import org.eclipse.acceleo.common.utils.AcceleoStandardLibrary;
import org.eclipse.acceleo.engine.AcceleoEngineMessages;
import org.eclipse.acceleo.engine.AcceleoEvaluationCancelledException;
import org.eclipse.acceleo.engine.AcceleoEvaluationException;
import org.eclipse.acceleo.engine.internal.environment.AcceleoEnvironment;
import org.eclipse.acceleo.engine.internal.evaluation.AcceleoEvaluationVisitor;
import org.eclipse.acceleo.engine.internal.evaluation.AcceleoEvaluationVisitorDecorator;
import org.eclipse.acceleo.model.mtl.Block;
import org.eclipse.acceleo.model.mtl.FileBlock;
import org.eclipse.acceleo.model.mtl.ForBlock;
import org.eclipse.acceleo.model.mtl.MtlPackage;
import org.eclipse.acceleo.model.mtl.ProtectedAreaBlock;
import org.eclipse.acceleo.model.mtl.QueryInvocation;
import org.eclipse.acceleo.model.mtl.Template;
import org.eclipse.acceleo.model.mtl.TemplateInvocation;
import org.eclipse.acceleo.traceability.GeneratedFile;
import org.eclipse.acceleo.traceability.GeneratedText;
import org.eclipse.acceleo.traceability.InputElement;
import org.eclipse.acceleo.traceability.ModelFile;
import org.eclipse.acceleo.traceability.ModuleElement;
import org.eclipse.acceleo.traceability.ModuleFile;
import org.eclipse.acceleo.traceability.TraceabilityFactory;
import org.eclipse.acceleo.traceability.TraceabilityModel;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EOperation;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.ocl.expressions.AssociationClassCallExp;
import org.eclipse.ocl.expressions.EnumLiteralExp;
import org.eclipse.ocl.expressions.ExpressionsPackage;
import org.eclipse.ocl.expressions.IfExp;
import org.eclipse.ocl.expressions.IterateExp;
import org.eclipse.ocl.expressions.IteratorExp;
import org.eclipse.ocl.expressions.LetExp;
import org.eclipse.ocl.expressions.OCLExpression;
import org.eclipse.ocl.expressions.OperationCallExp;
import org.eclipse.ocl.expressions.PropertyCallExp;
import org.eclipse.ocl.expressions.StateExp;
import org.eclipse.ocl.expressions.StringLiteralExp;
import org.eclipse.ocl.expressions.Variable;
import org.eclipse.ocl.expressions.VariableExp;
import org.eclipse.ocl.types.PrimitiveType;
import org.eclipse.ocl.utilities.PredefinedType;

/**
 * This implementation of an evaluation visitor will record traceability information as the evaluation
 * processes. All actual evaluations and generations will be delegated to the standard Acceleo evaluation
 * visitor.
 * 
 * @author <a href="mailto:laurent.goubet@obeo.fr">Laurent Goubet</a>
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
public class AcceleoTraceabilityVisitor<PK, C, O, P, EL, PM, S, COA, SSA, CT, CLS, E> extends AcceleoEvaluationVisitorDecorator<PK, C, O, P, EL, PM, S, COA, SSA, CT, CLS, E> {
	/** All traceability information for this session will be saved in this instance. */
	private final TraceabilityModel evaluationTrace;

	/** This will hold the stack of generated files. */
	private LinkedList<GeneratedFile> currentFiles = new LinkedList<GeneratedFile>();

	/**
	 * Records all variable traces for this session. Note that only primitive type variables will be recorded.
	 */
	private final Map<Variable<C, PM>, VariableTrace> variableTraces = new HashMap<Variable<C, PM>, VariableTrace>();

	/** This will hold the stack of all created traceability contexts. */
	private final LinkedList<ExpressionTrace> recordedTraces = new LinkedList<ExpressionTrace>();

	/** Keeps track of the variable currently being initialized. */
	private Variable<C, PM> initializingVariable;

	/** Retrieve invalid once and for all. */
	private final Object invalid = ((AcceleoEnvironment)getEnvironment()).getOCLStandardLibraryReflection()
			.getInvalid();

	/** This we'll allow us to retrieve the proper source value of a given operation call. */
	private OCLExpression<C> operationCallSourceExpression;

	/**
	 * Along with {@link #operationCallSourceExpression}, this allows us to retrieve the source value of an
	 * operation call.
	 */
	private Object operationCallSource;

	/** This we'll allow us to retrieve the proper source value of a given property call. */
	private OCLExpression<C> propertyCallSourceExpression;

	/**
	 * Along with {@link #propertyCallSourceExpression}, this allows us to retrieve the source value of a
	 * property call.
	 */
	private EObject propertyCallSource;

	/** This will be updated each time we enter a for/template/query/... with the scope variable. */
	private LinkedList<EObject> scopeEObjects = new LinkedList<EObject>();

	/**
	 * This will be set as soon as we enter the evaluation of a protected area, and reset to <code>null</code>
	 * as soon as we exit this area. It will be used to shortcut all input recorded inside of such an area.
	 */
	private InputElement protectedAreaSource;

	/**
	 * This will be set to <code>true</code> in order to bypass trace recording when evaluating if conditions.
	 */
	private boolean evaluatingIfCondition;

	/**
	 * Default constructor.
	 * 
	 * @param decoratedVisitor
	 *            The evaluation visitor this instance will decorate.
	 * @param trace
	 *            Model in which evaluation traces are to be recorded.
	 */
	public AcceleoTraceabilityVisitor(
			AcceleoEvaluationVisitor<PK, C, O, P, EL, PM, S, COA, SSA, CT, CLS, E> decoratedVisitor,
			TraceabilityModel trace) {
		super(decoratedVisitor);
		evaluationTrace = trace;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.acceleo.engine.internal.evaluation.AcceleoEvaluationVisitorDecorator#append(java.lang.String,
	 *      org.eclipse.acceleo.model.mtl.Block, org.eclipse.emf.ecore.EObject, boolean)
	 */
	@Override
	public void append(String string, Block sourceBlock, EObject source, boolean fireEvent) {
		if (fireEvent && string.length() > 0) {
			GeneratedFile generatedFile = currentFiles.getLast();
			final ExpressionTrace trace = recordedTraces.removeLast();
			if (protectedAreaSource != null) {
				alterProtectedAreaTrace(string, sourceBlock, trace);
			}
			final int fileLength = generatedFile.getLength();
			int addedLength = 0;
			for (Map.Entry<InputElement, Set<GeneratedText>> entry : trace.getTraces().entrySet()) {
				for (GeneratedText text : entry.getValue()) {
					addedLength += text.getEndOffset() - text.getStartOffset();
					text.setStartOffset(text.getStartOffset() + fileLength);
					text.setEndOffset(text.getEndOffset() + fileLength);
					generatedFile.getGeneratedRegions().add(text);
				}
			}
			generatedFile.setLength(fileLength + addedLength);
			trace.dispose();
		}
		super.append(string, sourceBlock, source, fireEvent);
	}

	/**
	 * This will be used to create the evaluation trace for a given protected area's content and alter the
	 * trace for its marker; indeed traces aren't recorded anywhere else than for the marker evaluation as the
	 * remainder are mainly hard-coded strings.
	 * 
	 * @param content
	 *            Full content of the protected area, starting/ending strings and marker included.
	 * @param protectedArea
	 *            The {@link ProtectedAreaBlock} from which we're generating <em>content</em>.
	 * @param trace
	 *            The execution trace recorded for this area.
	 */
	private void alterProtectedAreaTrace(String content, Block protectedArea, ExpressionTrace trace) {
		final String areaStart = AcceleoEngineMessages.getString("usercode.start") + ' ';
		final int markerIndex = content.indexOf(areaStart) + areaStart.length();

		int markerEndIndex = -1;
		// Alter indices of the "marker" traces
		for (Map.Entry<InputElement, Set<GeneratedText>> entry : trace.getTraces().entrySet()) {
			for (GeneratedText text : entry.getValue()) {
				text.setStartOffset(text.getStartOffset() + markerIndex);
				text.setEndOffset(text.getEndOffset() + markerIndex);
				if (text.getEndOffset() > markerEndIndex) {
					markerEndIndex = text.getEndOffset();
				}
			}
		}

		// Create a region containing all text preceding the marker
		final GeneratedText startRegion = TraceabilityFactory.eINSTANCE.createGeneratedText();
		startRegion.setEndOffset(markerIndex);
		startRegion.setModuleElement(getModuleElement(protectedArea));
		startRegion.setSourceElement(protectedAreaSource);

		// Then a region containing all text following the marker
		final GeneratedText endRegion = TraceabilityFactory.eINSTANCE.createGeneratedText();
		endRegion.setStartOffset(markerEndIndex);
		endRegion.setEndOffset(content.length());
		endRegion.setModuleElement(getModuleElement(protectedArea));
		endRegion.setSourceElement(protectedAreaSource);

		trace.getTraces().get(protectedAreaSource).add(startRegion);
		trace.getTraces().get(protectedAreaSource).add(endRegion);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.acceleo.engine.internal.evaluation.AcceleoEvaluationVisitorDecorator#createFileWriter(java.io.File,
	 *      org.eclipse.acceleo.model.mtl.Block, org.eclipse.emf.ecore.EObject, boolean, java.lang.String)
	 */
	@Override
	public void createFileWriter(File generatedFile, Block fileBlock, EObject source, boolean appendMode,
			String charset) throws AcceleoEvaluationException {
		boolean fileExisted = generatedFile.exists();

		GeneratedFile file = getGeneratedFile(generatedFile, appendMode);
		file.setCharset(charset);
		file.setFileBlock(getModuleElement(fileBlock));
		currentFiles.add(file);

		ExpressionTrace traceContext = recordedTraces.removeLast();
		for (Map.Entry<InputElement, Set<GeneratedText>> entry : traceContext.getTraces().entrySet()) {
			file.getSourceElements().add(entry.getKey());
			file.getNameRegions().addAll(entry.getValue());
		}
		traceContext.dispose();

		if (appendMode && fileExisted) {
			file.setLength(file.getLength() + 1);
		}

		super.createFileWriter(generatedFile, fileBlock, source, appendMode, charset);
	}

	/**
	 * Returns an existing GeneratedFile in the trace model, creating it if needed.
	 * 
	 * @param generatedFile
	 *            File that is to be created.
	 * @param appendMode
	 *            If <code>true</code>, we need to retrieve the current file length if it exists.
	 * @return {@link GeneratedFile} contained in the {@link #evaluationTrace} model.
	 */
	private GeneratedFile getGeneratedFile(File generatedFile, boolean appendMode) {
		GeneratedFile soughtFile = evaluationTrace.getGeneratedFile(generatedFile.getPath());
		if (soughtFile == null) {
			soughtFile = TraceabilityFactory.eINSTANCE.createGeneratedFile();
			soughtFile.setPath(generatedFile.getPath());
			soughtFile.setName(stripFileNameFrom(generatedFile.getPath()));
			if (appendMode && generatedFile.exists() && generatedFile.canRead()) {
				int length = 0;
				try {
					BufferedReader reader = new BufferedReader(new FileReader(generatedFile));
					String line = reader.readLine();
					while (line != null) {
						// add 1 for the carriage return
						length += line.length() + 1;
						line = reader.readLine();
					}
				} catch (IOException e) {
					// FIXME log warning : traceability may not be good on this one
				}
				soughtFile.setLength(length);
			}
			evaluationTrace.getGeneratedFiles().add(soughtFile);
		}
		return soughtFile;
	}

	/**
	 * Returns an existing ModelFile for the given model element, creating it if needed.
	 * 
	 * @param modelElement
	 *            Model element we need the {@link ModelFile} of.
	 * @return {@link ModelFile} contained in the {@link #evaluationTrace} model.
	 */
	private ModelFile getModelFile(EObject modelElement) {
		final URI modelURI = modelElement.eResource().getURI();
		final String name = modelURI.lastSegment();
		ModelFile soughtModel = evaluationTrace.getInputModel(modelURI.path());
		if (soughtModel == null) {
			soughtModel = TraceabilityFactory.eINSTANCE.createModelFile();
			soughtModel.setPath(modelURI.toString());
			soughtModel.setName(name);
			evaluationTrace.getModelFiles().add(soughtModel);
		}
		return soughtModel;
	}

	/**
	 * Returns an existing ModuleFile for the given module element, creating it if needed.
	 * 
	 * @param moduleElement
	 *            Module element we need the {@link ModuleFile} of.
	 * @return {@link ModuleFile} contained in the {@link #evaluationTrace} model.
	 */
	private ModuleFile getModuleFile(EObject moduleElement) {
		final String moduleURI = moduleElement.eResource().getURI().toString();
		ModuleFile soughtModule = evaluationTrace.getGenerationModule(moduleURI);
		if (soughtModule == null) {
			soughtModule = TraceabilityFactory.eINSTANCE.createModuleFile();
			soughtModule.setPath(moduleURI);
			soughtModule.setName(stripFileNameFrom(moduleURI));
			evaluationTrace.getModules().add(soughtModule);
		}
		return soughtModule;
	}

	/**
	 * This will return an InputElement contained within {@link #evaluationTrace} corresponding to the given
	 * model element and neither structural feature nor operation linked. Note that it will be created if
	 * needed.
	 * 
	 * @param modelElement
	 *            Model element we seek the corresponding InputElement of.
	 * @return {@link InputElement} contained in the {@link #evaluationTrace} model.
	 */
	private InputElement getInputElement(EObject modelElement) {
		ModelFile soughtModel = getModelFile(modelElement);
		for (InputElement input : soughtModel.getInputElements()) {
			if (input.getFeature() == null && input.getModelElement() == modelElement) {
				return input;
			}
		}
		// If we're here, such an InputElement does not already exist
		InputElement soughtElement = TraceabilityFactory.eINSTANCE.createInputElement();
		soughtElement.setModelElement(modelElement);
		soughtModel.getInputElements().add(soughtElement);
		return soughtElement;
	}

	/**
	 * This will return an InputElement contained within {@link #evaluationTrace} corresponding to the given
	 * model element and structural feature. Note that it will be created if needed.
	 * 
	 * @param modelElement
	 *            Model element we seek the corresponding InputElement of.
	 * @param feature
	 *            Structural feature of this input element. Can be <code>null</code>.
	 * @return {@link InputElement} contained in the {@link #evaluationTrace} model.
	 */
	private InputElement getInputElement(EObject modelElement, EStructuralFeature feature) {
		ModelFile soughtModel = getModelFile(modelElement);
		for (InputElement input : soughtModel.getInputElements()) {
			if (input.getFeature() == feature && input.getModelElement() == modelElement) {
				return input;
			}
		}
		// If we're here, such an InputElement does not already exist
		InputElement soughtElement = TraceabilityFactory.eINSTANCE.createInputElement();
		soughtElement.setModelElement(modelElement);
		soughtElement.setFeature(feature);
		soughtModel.getInputElements().add(soughtElement);
		return soughtElement;
	}

	/**
	 * This will return an InputElement contained within {@link #evaluationTrace} corresponding to the given
	 * model element and operation. Note that it will be created if needed.
	 * 
	 * @param modelElement
	 *            Model element we seek the corresponding InputElement of.
	 * @param operation
	 *            EOperation of this input element. Can be <code>null</code>.
	 * @return {@link InputElement} contained in the {@link #evaluationTrace} model.
	 */
	private InputElement getInputElement(EObject modelElement, EOperation operation) {
		ModelFile soughtModel = getModelFile(modelElement);
		for (InputElement input : soughtModel.getInputElements()) {
			if (input.getOperation() == operation && input.getModelElement() == modelElement) {
				return input;
			}
		}
		// If we're here, such an InputElement does not already exist
		InputElement soughtElement = TraceabilityFactory.eINSTANCE.createInputElement();
		soughtElement.setModelElement(modelElement);
		soughtElement.setOperation(operation);
		soughtModel.getInputElements().add(soughtElement);
		return soughtElement;
	}

	/**
	 * This will return a {@link ModuleElement} contained within {@link #evaluationTrace} corresponding to the
	 * given module element. Note that it will be created if needed.
	 * 
	 * @param moduleElement
	 *            The module element we need a {@link ModuleElement} instance for.
	 * @return {@link ModuleElement} contained in the {@link #evaluationTrace} model.
	 */
	private ModuleElement getModuleElement(EObject moduleElement) {
		ModuleFile soughtModule = getModuleFile(moduleElement);
		for (ModuleElement element : soughtModule.getModuleElements()) {
			if (element.getModuleElement() == moduleElement) {
				return element;
			}
		}
		// If we're here, such a ModuleElement does not already exist
		ModuleElement soughtElement = TraceabilityFactory.eINSTANCE.createModuleElement();
		soughtElement.setModuleElement(moduleElement);
		soughtModule.getModuleElements().add(soughtElement);
		return soughtElement;
	}

	/**
	 * This will return only the "name" section of a given path. This excludes any parent directory and file
	 * extension info.
	 * <p>
	 * For example, this will return <code>"MyClass"</code> from path <code>c:\dev\java\MyClass.java</code>.
	 * </p>
	 * 
	 * @param filePath
	 *            Path that is to be stripped.
	 * @return The extracted file name.
	 */
	private String stripFileNameFrom(String filePath) {
		String fileName = filePath;
		if (fileName.indexOf(File.separator) != -1) {
			fileName = fileName.substring(fileName.lastIndexOf(File.separator) + 1);
		}
		if (fileName.indexOf('.') > 0) {
			fileName = fileName.substring(0, fileName.lastIndexOf('.'));
		}
		return fileName;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.ocl.EvaluationVisitorDecorator#visitExpression(org.eclipse.ocl.expressions.OCLExpression)
	 */
	@Override
	public Object visitExpression(OCLExpression<C> expression) {
		// Very first call of a template comes from IAcceleoEngine#doEvaluate()
		if (scopeEObjects.size() == 0 && expression instanceof Template) {
			for (org.eclipse.ocl.ecore.Variable var : ((Template)expression).getParameter()) {
				Object value = getEvaluationEnvironment().getValueOf(var.getName());
				if (value instanceof EObject) {
					scopeEObjects.add((EObject)value);
					break;
				}
			}
		}

		boolean oldEvaluatingCondition = evaluatingIfCondition;
		if (expression.eContainingFeature() == MtlPackage.eINSTANCE.getIfBlock_IfExpr()
				|| expression.eContainingFeature() == ExpressionsPackage.eINSTANCE.getIfExp_Condition()) {
			evaluatingIfCondition = true;
		}
		if (shouldRecordTrace((EReference)expression.eContainingFeature())
				&& !(expression.eContainer() instanceof ProtectedAreaBlock)) {
			recordedTraces.add(new ExpressionTrace(expression));
		}
		Object result = null;
		try {
			result = getDelegate().visitExpression(expression);
		} catch (AcceleoEvaluationCancelledException e) {
			cancel();
			throw e;
		}
		evaluatingIfCondition = oldEvaluatingCondition;

		if (isPropertyCallSource(expression)) {
			propertyCallSource = (EObject)result;
		} else if (isOperationCallSource(expression)) {
			operationCallSource = result;
		}
		if (expression.eContainingFeature() == MtlPackage.eINSTANCE.getProtectedAreaBlock_Marker()) {
			visitTrimOperation((String)result);
		}
		return result;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.ocl.EvaluationVisitorDecorator#visitVariable(org.eclipse.ocl.expressions.Variable)
	 */
	@Override
	public Object visitVariable(Variable<C, PM> variable) {
		final boolean isPrimitive = variable.getType() instanceof PrimitiveType<?>;
		Variable<C, PM> oldVar = null;
		if (isPrimitive) {
			variableTraces.put(variable, new VariableTrace(variable));
			oldVar = initializingVariable;
			initializingVariable = variable;
		}

		final Object result = getDelegate().visitVariable(variable);

		if (scopeEObjects.getLast() == variable) {
			if (result instanceof EObject) {
				scopeEObjects.removeLast();
				scopeEObjects.add((EObject)result);
			} else {
				/*
				 * we won't remove the "variable" scope object; it'll be removed when we exit the current
				 * scope.
				 */
			}
		}

		initializingVariable = oldVar;
		return result;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.ocl.EvaluationVisitorDecorator#visitPropertyCallExp(org.eclipse.ocl.expressions.PropertyCallExp)
	 */
	@Override
	public Object visitPropertyCallExp(PropertyCallExp<C, P> callExp) {
		OCLExpression<C> oldPropertyCallSourceExpression = propertyCallSourceExpression;
		propertyCallSourceExpression = callExp.getSource();

		final Object result = getDelegate().visitPropertyCallExp(callExp);

		if (propertyCallSource != null) {
			InputElement propertyCallInput = getInputElement(propertyCallSource, (EStructuralFeature)callExp
					.getReferredProperty());
			propertyCallSource = null;

			if (initializingVariable != null && !(result instanceof EObject)) {
				ModuleElement modElement = getModuleElement(callExp);
				GeneratedText text = TraceabilityFactory.eINSTANCE.createGeneratedText();
				text.setModuleElement(modElement);
				variableTraces.get(initializingVariable).addTrace(propertyCallInput, text, result);
			} else if (recordedTraces.size() > 0 && shouldRecordTrace(callExp)) {
				ModuleElement modElement = getModuleElement(callExp);
				GeneratedText text = TraceabilityFactory.eINSTANCE.createGeneratedText();
				text.setModuleElement(modElement);
				recordedTraces.getLast().addTrace(propertyCallInput, text, result);
			}
		}
		propertyCallSourceExpression = oldPropertyCallSourceExpression;

		if (isPropertyCallSource(callExp)) {
			propertyCallSource = (EObject)result;
		} else if (isOperationCallSource(callExp)) {
			operationCallSource = result;
		}

		return result;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.ocl.EvaluationVisitorDecorator#visitAssociationClassCallExp(org.eclipse.ocl.expressions.AssociationClassCallExp)
	 */
	@Override
	public Object visitAssociationClassCallExp(AssociationClassCallExp<C, P> callExp) {
		final Object result = super.visitAssociationClassCallExp(callExp);

		if (isPropertyCallSource(callExp)) {
			propertyCallSource = (EObject)result;
		} else if (isOperationCallSource(callExp)) {
			operationCallSource = result;
		}
		return result;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.ocl.EvaluationVisitorDecorator#visitEnumLiteralExp(org.eclipse.ocl.expressions.EnumLiteralExp)
	 */
	@Override
	public Object visitEnumLiteralExp(EnumLiteralExp<C, EL> literalExp) {
		final Object result = super.visitEnumLiteralExp(literalExp);

		if (isPropertyCallSource(literalExp)) {
			propertyCallSource = (EObject)result;
		} else if (isOperationCallSource(literalExp)) {
			operationCallSource = result;
		}
		return result;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.ocl.EvaluationVisitorDecorator#visitIfExp(org.eclipse.ocl.expressions.IfExp)
	 */
	@Override
	public Object visitIfExp(IfExp<C> ifExp) {
		final Object result = super.visitIfExp(ifExp);

		if (isPropertyCallSource(ifExp)) {
			propertyCallSource = (EObject)result;
		} else if (isOperationCallSource(ifExp)) {
			operationCallSource = result;
		}
		return result;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.ocl.EvaluationVisitorDecorator#visitIterateExp(org.eclipse.ocl.expressions.IterateExp)
	 */
	@Override
	public Object visitIterateExp(IterateExp<C, PM> callExp) {
		scopeEObjects.add(callExp.getIterator().get(0));

		final Object result = super.visitIterateExp(callExp);

		scopeEObjects.removeLast();

		if (isPropertyCallSource(callExp)) {
			propertyCallSource = (EObject)result;
		} else if (isOperationCallSource(callExp)) {
			operationCallSource = result;
		}
		return result;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.acceleo.engine.internal.evaluation.AcceleoEvaluationVisitorDecorator#visitAcceleoForBlock(org.eclipse.acceleo.model.mtl.ForBlock)
	 */
	@Override
	public void visitAcceleoForBlock(ForBlock forBlock) {
		scopeEObjects.add(forBlock.getLoopVariable());
		// FIXME what to do if the iteration set is of a primitive type? ([for (eClass.eSupertypes.name)/])

		super.visitAcceleoForBlock(forBlock);

		scopeEObjects.removeLast();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.acceleo.engine.internal.evaluation.AcceleoEvaluationVisitorDecorator#visitAcceleoFileBlock(org.eclipse.acceleo.model.mtl.FileBlock)
	 */
	@Override
	public void visitAcceleoFileBlock(FileBlock fileBlock) {
		super.visitAcceleoFileBlock(fileBlock);

		currentFiles.removeLast();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.acceleo.engine.internal.evaluation.AcceleoEvaluationVisitorDecorator#visitAcceleoProtectedArea(org.eclipse.acceleo.model.mtl.ProtectedAreaBlock)
	 */
	@Override
	public void visitAcceleoProtectedArea(ProtectedAreaBlock protectedArea) {
		protectedAreaSource = getInputElement(retrieveScopeEObjectValue());

		super.visitAcceleoProtectedArea(protectedArea);

		protectedAreaSource = null;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.acceleo.engine.internal.evaluation.AcceleoEvaluationVisitorDecorator#visitAcceleoQueryInvocation(org.eclipse.acceleo.model.mtl.QueryInvocation)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public Object visitAcceleoQueryInvocation(QueryInvocation invocation) {
		scopeEObjects.add(invocation.getArgument().get(0));

		final Object result = super.visitAcceleoQueryInvocation(invocation);

		scopeEObjects.removeLast();

		if (isPropertyCallSource((OCLExpression<C>)invocation)) {
			propertyCallSource = (EObject)result;
		} else if (isOperationCallSource((OCLExpression<C>)invocation)) {
			operationCallSource = result;
		}
		return result;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.acceleo.engine.internal.evaluation.AcceleoEvaluationVisitorDecorator#visitAcceleoTemplateInvocation(org.eclipse.acceleo.model.mtl.TemplateInvocation)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public Object visitAcceleoTemplateInvocation(TemplateInvocation invocation) {
		if (invocation.getArgument().size() > 0) {
			scopeEObjects.add(invocation.getArgument().get(0));
		}

		final Object result = super.visitAcceleoTemplateInvocation(invocation);

		if (invocation.getArgument().size() > 0) {
			scopeEObjects.removeLast();
		}

		if (isPropertyCallSource((OCLExpression<C>)invocation)) {
			propertyCallSource = (EObject)result;
		} else if (isOperationCallSource((OCLExpression<C>)invocation)) {
			operationCallSource = result;
		}
		return result;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.ocl.EvaluationVisitorDecorator#visitIteratorExp(org.eclipse.ocl.expressions.IteratorExp)
	 */
	@Override
	public Object visitIteratorExp(IteratorExp<C, PM> callExp) {
		scopeEObjects.add(callExp.getIterator().get(0));

		final Object result = super.visitIteratorExp(callExp);

		scopeEObjects.removeLast();

		if (isPropertyCallSource(callExp)) {
			propertyCallSource = (EObject)result;
		} else if (isOperationCallSource(callExp)) {
			operationCallSource = result;
		}
		return result;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.ocl.EvaluationVisitorDecorator#visitLetExp(org.eclipse.ocl.expressions.LetExp)
	 */
	@Override
	public Object visitLetExp(LetExp<C, PM> letExp) {
		scopeEObjects.add(letExp.getVariable());

		final Object result = super.visitLetExp(letExp);

		scopeEObjects.removeLast();

		if (isPropertyCallSource(letExp)) {
			propertyCallSource = (EObject)result;
		} else if (isOperationCallSource(letExp)) {
			operationCallSource = result;
		}
		return result;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.ocl.EvaluationVisitorDecorator#visitOperationCallExp(org.eclipse.ocl.expressions.OperationCallExp)
	 */
	@Override
	public Object visitOperationCallExp(OperationCallExp<C, O> callExp) {
		OCLExpression<C> oldOperationCallSourceExpression = operationCallSourceExpression;
		operationCallSourceExpression = callExp.getSource();

		final Object result;
		if (isTraceabilityImpactingOperation(callExp)) {
			result = internalVisitOperationCallExp(callExp);
		} else {
			result = super.visitOperationCallExp(callExp);
		}

		operationCallSource = null;
		operationCallSourceExpression = oldOperationCallSourceExpression;

		if (isPropertyCallSource(callExp)) {
			propertyCallSource = (EObject)result;
		} else if (isOperationCallSource(callExp)) {
			operationCallSource = result;
		}
		return result;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.ocl.EvaluationVisitorDecorator#visitStateExp(org.eclipse.ocl.expressions.StateExp)
	 */
	@Override
	public Object visitStateExp(StateExp<C, S> stateExp) {
		final Object result = super.visitStateExp(stateExp);

		if (isPropertyCallSource(stateExp)) {
			propertyCallSource = (EObject)result;
		} else if (isOperationCallSource(stateExp)) {
			operationCallSource = result;
		}
		return result;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.ocl.EvaluationVisitorDecorator#visitVariableExp(org.eclipse.ocl.expressions.VariableExp)
	 */
	@Override
	public Object visitVariableExp(VariableExp<C, PM> variableExp) {
		final Object result = super.visitVariableExp(variableExp);

		if (initializingVariable == null) {
			if (recordedTraces.size() > 0 && result instanceof String && ((String)result).length() > 0) {
				VariableTrace referredVarTrace = variableTraces.get(variableExp.getReferredVariable());
				for (Map.Entry<InputElement, Set<GeneratedText>> entry : referredVarTrace.getTraces()
						.entrySet()) {
					InputElement input = entry.getKey();
					for (GeneratedText text : entry.getValue()) {
						recordedTraces.getLast().addTrace(input, text, result);
					}
				}
			}
		} else if (variableTraces.get(variableExp.getReferredVariable()) != null
				&& shouldRecordTrace(variableExp)) {
			VariableTrace referredVarTrace = variableTraces.get(variableExp.getReferredVariable());
			for (Map.Entry<InputElement, Set<GeneratedText>> entry : referredVarTrace.getTraces().entrySet()) {
				InputElement input = entry.getKey();
				for (GeneratedText text : entry.getValue()) {
					variableTraces.get(initializingVariable).addTrace(input, text, result);
				}
			}
		}

		if (isPropertyCallSource(variableExp)) {
			propertyCallSource = (EObject)result;
		} else if (isOperationCallSource(variableExp)) {
			operationCallSource = result;
		}
		return result;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.ocl.EvaluationVisitorDecorator#visitStringLiteralExp(org.eclipse.ocl.expressions.StringLiteralExp)
	 */
	@Override
	public Object visitStringLiteralExp(StringLiteralExp<C> literalExp) {
		final Object result = super.visitStringLiteralExp(literalExp);

		if (recordedTraces.size() > 0 && ((String)result).length() > 0 && shouldRecordTrace(literalExp)) {
			InputElement input = getInputElement(retrieveScopeEObjectValue());
			ModuleElement modElement = getModuleElement(literalExp);
			GeneratedText text = TraceabilityFactory.eINSTANCE.createGeneratedText();
			text.setModuleElement(modElement);
			recordedTraces.getLast().addTrace(input, text, result);
		}
		return result;
	}

	/**
	 * Retrieve the scope value of the currently evaluated expression.
	 * 
	 * @return The scope value of the currently evaluated expression.
	 */
	@SuppressWarnings("unchecked")
	private EObject retrieveScopeEObjectValue() {
		EObject scopeValue = null;
		for (int i = scopeEObjects.size() - 1; i >= 0; i--) {
			EObject scope = scopeEObjects.get(i);
			if (scope instanceof Variable<?, ?>) {
				final Object value = getEvaluationEnvironment()
						.getValueOf(((Variable<C, PM>)scope).getName());
				if (value instanceof EObject) {
					scopeValue = (EObject)value;
					break;
				}
			} else if (scope instanceof VariableExp) {
				final Object value = getEvaluationEnvironment().getValueOf(
						(((VariableExp)scope).getReferredVariable()).getName());
				if (value instanceof EObject) {
					scopeValue = (EObject)value;
					break;
				}
			} else {
				scopeValue = scope;
				break;
			}
		}
		return scopeValue;
	}

	/**
	 * This will be called when the evaluation has been cancelled somehow.
	 */
	private void cancel() {
		initializingVariable = null;
		propertyCallSource = null;
		propertyCallSourceExpression = null;
		evaluatingIfCondition = false;
		for (VariableTrace trace : variableTraces.values()) {
			trace.dispose();
		}
		variableTraces.clear();
	}

	/**
	 * We'll intercept each and every calls to traceability-impacting operations as returned by
	 * {@link #isTraceabilityImpactingOperation(OperationCallExp)} and reroute them here. We'll then
	 * specialize the evaluation of that given operation call so as to record evaluation traces.
	 * 
	 * @param callExp
	 *            Operation that is to be evaluated.
	 * @return result of the invocation.
	 */
	private Object internalVisitOperationCallExp(OperationCallExp<C, O> callExp) {
		final String operationName = ((EOperation)callExp.getReferredOperation()).getName();
		final Object result;

		/*
		 * FIXME when all cases are handled, externalize source/args evaluation before the if - else if, and
		 * check for invalid values.
		 */
		if (callExp.getOperationCode() == PredefinedType.SUBSTRING) {
			Object sourceObject = super.visitExpression(callExp.getSource());
			Object startIndex = super.visitExpression(callExp.getArgument().get(0));
			Object endIndex = super.visitExpression(callExp.getArgument().get(1));
			result = visitSubstringOperation((String)sourceObject, ((Integer)startIndex).intValue() - 1,
					((Integer)endIndex).intValue());
		} else if (operationName.equals(AcceleoNonStandardLibrary.OPERATION_STRING_TRIM)) {
			Object sourceObject = super.visitExpression(callExp.getSource());
			result = visitTrimOperation((String)sourceObject);
		} else if (operationName.equals(AcceleoStandardLibrary.OPERATION_STRING_FIRST)) {
			Object sourceObject = super.visitExpression(callExp.getSource());
			Object endIndex = super.visitExpression(callExp.getArgument().get(0));
			result = visitFirstOperation((String)sourceObject, ((Integer)endIndex).intValue());
		} else if (operationName.equals(AcceleoStandardLibrary.OPERATION_STRING_LAST)) {
			Object sourceObject = super.visitExpression(callExp.getSource());
			Object charCount = super.visitExpression(callExp.getArgument().get(0));
			result = visitLastOperation((String)sourceObject, ((Integer)charCount).intValue());
		} else if (operationName.equals(AcceleoStandardLibrary.OPERATION_STRING_INDEX)) {
			Object sourceObject = super.visitExpression(callExp.getSource());
			Object substring = super.visitExpression(callExp.getArgument().get(0));
			result = visitIndexOperation((String)sourceObject, (String)substring);
		} else if (operationName.equals(AcceleoStandardLibrary.OPERATION_STRING_ISALPHA)) {
			Object sourceObject = super.visitExpression(callExp.getSource());
			result = visitIsAlphaOperation((String)sourceObject);
		} else if (operationName.equals(AcceleoStandardLibrary.OPERATION_STRING_ISALPHANUM)) {
			Object sourceObject = super.visitExpression(callExp.getSource());
			result = visitIsAlphanumOperation((String)sourceObject);
		} else {
			result = super.visitOperationCallExp(callExp);
		}

		return result;
	}

	/**
	 * Handles the "trim" non-standard operation directly from the traceability visitor as we need to alter
	 * recorded traceability information.
	 * 
	 * @param source
	 *            String that is to be trimmed.
	 * @return The trimmed String. Traceability information will have been changed directly within
	 *         {@link #recordedTraces}.
	 */
	private String visitTrimOperation(String source) {
		int start = 0;
		int end = source.length();
		char[] chars = source.toCharArray();
		while (start < end && chars[start] <= ' ') {
			start++;
		}
		while (start < end && chars[end - 1] <= ' ') {
			end--;
		}

		changeTraceabilityIndicesSubstringReturn(start, end);

		return source.trim();
	}

	/**
	 * Handles the "first" OCL operation directly from the traceability visitor as we need to alter recorded
	 * traceability information.
	 * 
	 * @param source
	 *            String from which to take out a substring.
	 * @param endIndex
	 *            Index at which the substring ends.
	 * @return The substring. Traceability information will have been changed directly within
	 *         {@link #recordedTraces}.
	 */
	private String visitFirstOperation(String source, int endIndex) {
		final String result;
		if (endIndex < 0 || endIndex > source.length()) {
			result = source;
		} else {
			result = visitSubstringOperation(source, 0, endIndex);
		}
		return result;
	}

	/**
	 * Handles the "index" OCL operation directly from the traceability visitor as we need to alter recorded
	 * traceability information.
	 * 
	 * @param source
	 *            String from which to find a given index.
	 * @param substring
	 *            The substring we seek within <em>source</em>.
	 * @return Index of <em>substring</em> within <em>source</em>, -1 if it wasn't contained. Traceability
	 *         information will have been changed directly within {@link #recordedTraces}.
	 */
	private Integer visitIndexOperation(String source, String substring) {
		int result = source.indexOf(substring);
		final int digitCount;
		if (result == -1) {
			digitCount = 2;
		} else {
			digitCount = 1 + (int)Math.log(result);
		}

		/*
		 * We need to remove all recorded traces before this call, retaining the one that gave the region
		 * containing our index.
		 */
		ExpressionTrace trace = recordedTraces.getLast();
		for (Map.Entry<InputElement, Set<GeneratedText>> entry : trace.getTraces().entrySet()) {
			for (GeneratedText text : new LinkedHashSet<GeneratedText>(entry.getValue())) {
				if (text.getEndOffset() < result || text.getStartOffset() > result) {
					entry.getValue().remove(text);
				} else {
					text.setStartOffset(0);
					text.setEndOffset(digitCount);
				}
			}
		}
		trace.setOffset(digitCount);

		// Increment java index value by 1 for OCL
		result++;
		if (result == Integer.valueOf(0)) {
			result = Integer.valueOf(-1);
		}
		return Integer.valueOf(result);
	}

	/**
	 * Handles the "isAlphanum" OCL operation directly from the traceability visitor as we need to alter
	 * recorded traceability information.
	 * 
	 * @param source
	 *            String tht is to be considered.
	 * @return <code>true</code> iff the string is composed of alphanumeric characters. Traceability
	 *         information will have been changed directly within {@link #recordedTraces}.
	 */
	private Boolean visitIsAlphanumOperation(String source) {
		boolean result = true;
		final char[] chars = source.toCharArray();
		for (final char c : chars) {
			if (!Character.isLetterOrDigit(c)) {
				result = false;
				break;
			}
		}

		changeTraceabilityIndicesBooleanReturn(result);

		return Boolean.valueOf(result);
	}

	/**
	 * Handles the "isAlpha" OCL operation directly from the traceability visitor as we need to alter recorded
	 * traceability information.
	 * 
	 * @param source
	 *            String tht is to be considered.
	 * @return <code>true</code> iff the string is composed of alphabetic characters. Traceability information
	 *         will have been changed directly within {@link #recordedTraces}.
	 */
	private Boolean visitIsAlphaOperation(String source) {
		boolean result = true;
		final char[] chars = source.toCharArray();
		for (final char c : chars) {
			if (!Character.isLetter(c)) {
				result = false;
				break;
			}
		}

		changeTraceabilityIndicesBooleanReturn(result);

		return Boolean.valueOf(result);
	}

	/**
	 * Handles the "last" OCL operation directly from the traceability visitor as we need to alter recorded
	 * traceability information.
	 * 
	 * @param source
	 *            String from which to take out a substring.
	 * @param charCount
	 *            Number of characters to keep.
	 * @return The substring. Traceability information will have been changed directly within
	 *         {@link #recordedTraces}.
	 */
	private String visitLastOperation(String source, int charCount) {
		final String result;
		if (charCount < 0 || charCount > source.length()) {
			result = source;
		} else {
			result = visitSubstringOperation(source, source.length() - charCount, source.length());
		}
		return result;
	}

	/**
	 * Handles the "substring" OCL operation directly from the traceability visitor as we need to alter
	 * recorded traceability information.
	 * 
	 * @param source
	 *            String from which to take out a substring.
	 * @param startIndex
	 *            Index at which the substring starts.
	 * @param endIndex
	 *            Index at which the substring ends.
	 * @return The substring. Traceability information will have been changed directly within
	 *         {@link #recordedTraces}.
	 */
	private String visitSubstringOperation(String source, int startIndex, int endIndex) {
		changeTraceabilityIndicesSubstringReturn(startIndex, endIndex);

		return source.substring(startIndex, endIndex);
	}

	/**
	 * isAlpha, isAlphanum and possibily other traceability impacting operations share the same "basic"
	 * behavior of altering indices to reflect a boolean return. This behavior is externalized here.
	 * 
	 * @param result
	 *            Boolean result of the operation. We'll only leave a four-characters long legion if
	 *            <code>true</code>, five-characters long if <code>false</code>.
	 */
	private void changeTraceabilityIndicesBooleanReturn(boolean result) {
		// We'll keep only the very last trace and alter its indices
		ExpressionTrace trace = recordedTraces.getLast();
		Map.Entry<InputElement, Set<GeneratedText>> lastEntry = null;
		GeneratedText lastRegion = null;
		for (Map.Entry<InputElement, Set<GeneratedText>> entry : trace.getTraces().entrySet()) {
			for (GeneratedText text : new LinkedHashSet<GeneratedText>(entry.getValue())) {
				if (lastRegion == null) {
					lastRegion = text;
					lastEntry = entry;
				} else if (text.getEndOffset() > lastRegion.getEndOffset()) {
					// lastEntry cannot be null once we get here
					lastEntry.getValue().remove(lastRegion);
					lastRegion = text;
					lastEntry = entry;
				} else {
					entry.getValue().remove(text);
				}
			}
		}
		final int length;
		if (result) {
			length = 4;
		} else {
			length = 5;
		}
		lastRegion.setStartOffset(0);
		lastRegion.setStartOffset(length);
		trace.setOffset(length);
	}

	/**
	 * Substring, trim and possibily other traceability impacting operations share the same "basic" behavior
	 * of altering start and end indices without changing "inside". This behavior is externalized here.
	 * 
	 * @param startIndex
	 *            Index at which the substring starts.
	 * @param endIndex
	 *            Index at which the substring ends.
	 */
	private void changeTraceabilityIndicesSubstringReturn(int startIndex, int endIndex) {
		ExpressionTrace trace = recordedTraces.getLast();
		for (Map.Entry<InputElement, Set<GeneratedText>> entry : trace.getTraces().entrySet()) {
			for (GeneratedText text : new LinkedHashSet<GeneratedText>(entry.getValue())) {
				if (text.getEndOffset() <= startIndex || text.getStartOffset() >= endIndex) {
					entry.getValue().remove(text);
				} else {
					/*
					 * We have four cases : either 1) the region overlaps with the start index, 2) it overlaps
					 * with the end index, 3) it overlaps with both or 4) it overlaps with none.
					 */
					if (text.getStartOffset() < startIndex && text.getEndOffset() > endIndex) {
						text.setStartOffset(0);
						text.setEndOffset(endIndex - startIndex);
					} else if (text.getStartOffset() < startIndex) {
						text.setStartOffset(0);
						text.setEndOffset(text.getEndOffset() - startIndex);
					} else if (text.getEndOffset() > endIndex) {
						text.setStartOffset(text.getStartOffset() - startIndex);
						text.setEndOffset(endIndex - startIndex);
					} else {
						text.setStartOffset(text.getStartOffset() - startIndex);
						text.setEndOffset(text.getEndOffset() - startIndex);
					}
				}
			}
		}
	}

	/**
	 * Returns <code>true</code> if the given expression is the source of the property call we seek.
	 * 
	 * @param expression
	 *            Expression to compare.
	 * @return <code>true</code> if <code>expression</code> is the source of the current property call,
	 *         <code>false</code> otherwise.
	 */
	private boolean isPropertyCallSource(OCLExpression<C> expression) {
		return expression == propertyCallSourceExpression;
	}

	/**
	 * Returns <code>true</code> if the value is equal to the OCL standard library's OCLInvalid object.
	 * 
	 * @param value
	 *            Value we need to test.
	 * @return <code>true</code> if the value is equal to the OCL standard library's OCLInvalid object,
	 *         <code>false</code> otherwise.
	 */
	private boolean isInvalid(Object value) {
		return value == invalid;
	}

	/**
	 * Returns <code>true</code> if the given expression is the source of the operation call we seek.
	 * 
	 * @param expression
	 *            Expression to compare.
	 * @return <code>true</code> if <code>expression</code> is the source of the current operation call,
	 *         <code>false</code> otherwise.
	 */
	private boolean isOperationCallSource(OCLExpression<C> expression) {
		return expression == operationCallSourceExpression;
	}

	/**
	 * This will return <code>true</code> if the given operation call refers to a String operation which
	 * invocation will result in an alteration of the traceability information for the source.
	 * 
	 * @param operationCall
	 *            Operation call that is to be considered.
	 * @return <code>true</code> if calling this operation would alter the traceability information.
	 */
	private boolean isTraceabilityImpactingOperation(OperationCallExp<C, O> operationCall) {
		boolean isImpacting = false;
		final int operationCode = operationCall.getOperationCode();

		EClassifier operationEType = ((EOperation)operationCall.getReferredOperation()).getEType();
		if (operationEType == getEnvironment().getOCLStandardLibrary().getString()
				|| operationEType.getName().equals(AcceleoStandardLibrary.PRIMITIVE_STRING_NAME)) {
			// first, switch on the predefined OCL operations
			if (operationCode > 0) {
				isImpacting = operationCode == PredefinedType.SUBSTRING;
			} else {
				final String operationName = ((EOperation)operationCall.getReferredOperation()).getName();
				isImpacting = operationName.equals(AcceleoStandardLibrary.OPERATION_STRING_FIRST);
				isImpacting = operationName.equals(AcceleoStandardLibrary.OPERATION_STRING_INDEX);
				isImpacting = operationName.equals(AcceleoStandardLibrary.OPERATION_STRING_ISALPHA);
				isImpacting = operationName.equals(AcceleoStandardLibrary.OPERATION_STRING_ISALPHANUM);
				isImpacting = operationName.equals(AcceleoStandardLibrary.OPERATION_STRING_LAST);
				isImpacting = operationName.equals(AcceleoStandardLibrary.OPERATION_STRING_STRCMP);
				isImpacting = operationName.equals(AcceleoStandardLibrary.OPERATION_STRING_STRSTR);
				isImpacting = operationName.equals(AcceleoStandardLibrary.OPERATION_STRING_STRTOK);
				isImpacting = operationName.equals(AcceleoStandardLibrary.OPERATION_STRING_SUBSTITUTE);
				isImpacting = operationName.equals(AcceleoNonStandardLibrary.OPERATION_STRING_CONTAINS);
				isImpacting = operationName.equals(AcceleoNonStandardLibrary.OPERATION_STRING_ENDSWITH);
				isImpacting = operationName.equals(AcceleoNonStandardLibrary.OPERATION_STRING_REPLACE);
				isImpacting = operationName.equals(AcceleoNonStandardLibrary.OPERATION_STRING_REPLACEALL);
				isImpacting = operationName.equals(AcceleoNonStandardLibrary.OPERATION_STRING_STARTSWITH);
				isImpacting = operationName.equals(AcceleoNonStandardLibrary.OPERATION_STRING_SUBSTITUTEALL);
				isImpacting = operationName.equals(AcceleoNonStandardLibrary.OPERATION_STRING_TOKENIZE);
				isImpacting = operationName.equals(AcceleoNonStandardLibrary.OPERATION_STRING_TRIM);
			}
		}

		return isImpacting;
	}

	/**
	 * This will check if the given reference should cause trace recording. More specifically, it will return
	 * <code>false</code> for any reference that isn't one of the following :
	 * <table>
	 * <tr>
	 * <td>Metaclass</td>
	 * <td>Reference</td>
	 * </tr>
	 * <tr>
	 * <td>Block</td>
	 * <td>body</td>
	 * </tr>
	 * <tr>
	 * <td>FileBlock</td>
	 * <td>fileURL</td>
	 * </tr>
	 * <tr>
	 * <td>ForBlock</td>
	 * <td>before</td>
	 * </tr>
	 * <tr>
	 * <td>ForBlock</td>
	 * <td>each</td>
	 * </tr>
	 * <tr>
	 * <td>ForBlock</td>
	 * <td>after</td>
	 * </tr>
	 * <tr>
	 * <td>TemplateInvocation</td>
	 * <td>before</td>
	 * </tr>
	 * <tr>
	 * <td>TemplateInvocation</td>
	 * <td>each</td>
	 * </tr>
	 * <tr>
	 * <td>TemplateInvocation</td>
	 * <td>after</td>
	 * </tr>
	 * </table>
	 * 
	 * @param reference
	 *            The reference for which we need to know if text is to be generated.
	 * @return <code>True</code> if we need to generate text for the given reference, <code>false</code>
	 *         otherwise.
	 */
	private boolean shouldRecordTrace(EReference reference) {
		// Note : sort this by order of frequency to allow shot-circuit evaluation
		boolean generate = reference == MtlPackage.eINSTANCE.getBlock_Body();
		generate = generate || reference == MtlPackage.eINSTANCE.getForBlock_Each();
		generate = generate || reference == MtlPackage.eINSTANCE.getTemplateInvocation_Each();
		generate = generate || reference == MtlPackage.eINSTANCE.getFileBlock_FileUrl();
		generate = generate || reference == MtlPackage.eINSTANCE.getForBlock_Before();
		generate = generate || reference == MtlPackage.eINSTANCE.getForBlock_After();
		generate = generate || reference == MtlPackage.eINSTANCE.getTemplateInvocation_Before();
		generate = generate || reference == MtlPackage.eINSTANCE.getTemplateInvocation_After();
		generate = generate || reference == MtlPackage.eINSTANCE.getProtectedAreaBlock_Marker();
		return generate;
	}

	/**
	 * For a given expression, we'll check whether execution traces mst be recorded. This involves checking
	 * whether the expression is the source of a property or operation call, then looking at which operation
	 * will be called on the result if needed.
	 * 
	 * @param expression
	 *            Expression we're currently evaluating.
	 * @return <code>true</code> if traces are to be recorded, <code>false</code> otherwise.
	 */
	@SuppressWarnings("unchecked")
	private boolean shouldRecordTrace(OCLExpression<C> expression) {
		boolean record = true;
		if (expression.eContainingFeature() == MtlPackage.eINSTANCE.getForBlock_IterSet()
				|| evaluatingIfCondition) {
			return false;
		}
		if (isPropertyCallSource(expression)) {
			record = false;
		} else if (isOperationCallSource(expression)) {
			OperationCallExp<C, O> call = (OperationCallExp<C, O>)expression.eContainer();
			EOperation op = (EOperation)call.getReferredOperation();
			if (isTraceabilityImpactingOperation(call)) {
				record = false;
			} else if (op.getEType() != getEnvironment().getOCLStandardLibrary().getString()) {
				record = false;
			}
		}
		return record;
	}

	/**
	 * This will serve as the base class for traceability contexts.
	 * 
	 * @author <a href="mailto:laurent.goubet@obeo.fr">Laurent Goubet</a>
	 */
	protected abstract class AbstractTraceabilityContext {
		/** maps input elements corresponding to this expression to the bits they were used to generate. */
		protected final LinkedHashMap<InputElement, Set<GeneratedText>> traces = new LinkedHashMap<InputElement, Set<GeneratedText>>();

		/** This will keep track of the current starting offset for added traces. */
		protected int currentOffset;

		/**
		 * Adds the given trace to the list of traces corresponding to this expression, setting the offset as
		 * it goes.
		 * 
		 * @param input
		 *            Input element from which this region has been generated.
		 * @param trace
		 *            The actual trace that is to be recorded for this expression.
		 * @param value
		 *            Generated text value. This will be used to properly set the trace's offsets.
		 */
		public void addTrace(InputElement input, GeneratedText trace, Object value) {
			InputElement actualInput = input;
			if (protectedAreaSource != null) {
				actualInput = protectedAreaSource;
			}
			Set<GeneratedText> referredTraces = traces.get(actualInput);
			if (referredTraces == null) {
				referredTraces = new LinkedHashSet<GeneratedText>();
				traces.put(actualInput, referredTraces);
			}
			final String stringValue = value.toString();
			int startOffset = currentOffset;
			currentOffset = currentOffset + stringValue.length();
			trace.setSourceElement(actualInput);
			trace.setStartOffset(startOffset);
			trace.setEndOffset(currentOffset);
			referredTraces.add(trace);
		}

		/**
		 * Disposes of this trace.
		 */
		public void dispose() {
			for (Map.Entry<InputElement, Set<GeneratedText>> entry : traces.entrySet()) {
				entry.getValue().clear();
			}
			traces.clear();
		}

		/**
		 * Returns this context's traces. Note that the returned Map will be a copy of the actual one. Use
		 * {@link #dispose()} afterwards to clean up this context.
		 * 
		 * @return A copy of this context's traces.
		 */
		public LinkedHashMap<InputElement, Set<GeneratedText>> getTraces() {
			return new LinkedHashMap<InputElement, Set<GeneratedText>>(traces);
		}

		/**
		 * We might need to reset the offset to a given value. This can be used to this end.
		 * 
		 * @param offset
		 *            Offset to reset to.
		 */
		public void setOffset(int offset) {
			currentOffset = offset;
		}
	}

	/**
	 * This will be used to map a given OCL expression to both its input element and generated artifact.
	 * 
	 * @author <a href="mailto:laurent.goubet@obeo.fr">Laurent Goubet</a>
	 */
	private class ExpressionTrace extends AbstractTraceabilityContext {
		/** Expression for which this context trace has been created. */
		private final OCLExpression<C> referredExpression;

		/**
		 * Prepares trace recording for the given expression.
		 * 
		 * @param expression
		 *            Expression we wish to record traceability information for.
		 */
		public ExpressionTrace(OCLExpression<C> expression) {
			referredExpression = expression;
		}

		/**
		 * Returns the expression this trace refers to.
		 * 
		 * @return The expression this trace refers to.
		 */
		public OCLExpression<C> getReferredExpression() {
			return referredExpression;
		}
	}

	/**
	 * This will be used to map a given OCL variable to both its input element and generated artifact.
	 * 
	 * @author <a href="mailto:laurent.goubet@obeo.fr">Laurent Goubet</a>
	 */
	private final class VariableTrace extends AbstractTraceabilityContext {
		/** The actual variable we created this trace for. */
		private final Variable<C, PM> referredVariable;

		/**
		 * Prepares trace recording for the given variable.
		 * 
		 * @param variable
		 *            Variable we wish to record traceability information for.
		 */
		public VariableTrace(Variable<C, PM> variable) {
			referredVariable = variable;
		}

		/**
		 * Returns the variable this trace refers to.
		 * 
		 * @return The variable this trace refers to.
		 */
		public Variable<C, PM> getReferredVariable() {
			return referredVariable;
		}
	}
}

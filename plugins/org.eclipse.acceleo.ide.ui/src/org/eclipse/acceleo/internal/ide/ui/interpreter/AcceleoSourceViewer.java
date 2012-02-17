/*******************************************************************************
 * Copyright (c) 2010, 2012 Obeo.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.acceleo.internal.ide.ui.interpreter;

import java.math.BigDecimal;
import java.text.MessageFormat;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.eclipse.acceleo.common.internal.utils.AcceleoPackageRegistry;
import org.eclipse.acceleo.ui.interpreter.language.IInterpreterSourceViewer;
import org.eclipse.acceleo.ui.interpreter.language.InterpreterContext;
import org.eclipse.acceleo.ui.interpreter.view.Variable;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.IPath;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.jface.text.GapTextStore;
import org.eclipse.jface.text.ITextStore;
import org.eclipse.jface.text.TextSelection;
import org.eclipse.jface.text.source.IVerticalRuler;
import org.eclipse.jface.text.source.SourceViewer;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.ocl.ecore.EcoreEnvironment;
import org.eclipse.ocl.ecore.EcoreEnvironmentFactory;
import org.eclipse.ocl.expressions.CollectionKind;
import org.eclipse.ocl.types.OCLStandardLibrary;
import org.eclipse.ocl.util.Bag;
import org.eclipse.swt.widgets.Composite;

/**
 * This subclass of a {@link SourceViewer} will allow us to maintain an AcceleoSourceContent while the user
 * changes the source.
 * 
 * @author <a href="mailto:laurent.goubet@obeo.fr">Laurent Goubet</a>
 */
public class AcceleoSourceViewer extends SourceViewer implements IInterpreterSourceViewer {
	/**
	 * This will hold the system specific line separator ("\n" for unix, "\r\n" for dos, "\r" for mac, ...).
	 */
	protected static final String LINE_SEPARATOR = System.getProperty("line.separator"); //$NON-NLS-1$

	/** The module prefix. */
	protected static final String MODULE = "[module"; //$NON-NLS-1$

	/** The query prefix. */
	protected static final String QUERY = "[query"; //$NON-NLS-1$

	/** The template prefix. */
	protected static final String TEMPLATE = "[template"; //$NON-NLS-1$

	/** If we have an import, this will be added to the expression. */
	private static final String DUMMY_IMPORT = "[import {0}]" + LINE_SEPARATOR; //$NON-NLS-1$

	/** If the text doesn't start with "[module", we'll use this as the module's signature. */
	private static final String DUMMY_MODULE = "[module temporaryInterpreterModule({0})]" + LINE_SEPARATOR; //$NON-NLS-1$

	/**
	 * If the text doesn't start with "[module", we'll use this as a query signature.
	 */
	private static final String DUMMY_QUERY = "[query public temporaryInterpreterQuery(target : {0}, model : {1}{2}) : OclAny = " + LINE_SEPARATOR; //$NON-NLS-1$

	/**
	 * The default query end.
	 */
	private static final String DUMMY_QUERY_TAIL = LINE_SEPARATOR + "/]"; //$NON-NLS-1$

	/**
	 * If the text doesn't start with "[module", we'll use this as the template's signature. Otherwise, we'll
	 * assume the user specified both module and query/template in his expression.
	 */
	private static final String DUMMY_TEMPLATE = "[template public temporaryInterpreterTemplate({0})]"; //$NON-NLS-1$

	/** The "currentModel" dummy template parameter. Will be set to the root of the selected target EObject. */
	private static final String DUMMY_TEMPLATE_MODEL_PARAM = "currentModel : {0}"; //$NON-NLS-1$

	/**
	 * If the text doesn't start with "[module", we'll use this to close the template. Otherwise, we'll assume
	 * the user specified both module and query/template in his expression.
	 */
	private static final String DUMMY_TEMPLATE_TAIL = LINE_SEPARATOR + "[/template]"; //$NON-NLS-1$

	/** The "thisEObject" dummy template parameter. Will be set to the selected target EObject. */
	private static final String DUMMY_TEMPLATE_TARGET_PARAM = "thisEObject : {0}"; //$NON-NLS-1$

	/**
	 * This will be updated with the exact text as entered by the user and displayed on the viewer. However,
	 * this is not what will be parsed : it will be inserted into {@link #fullExpression}.
	 */
	private ITextStore buffer = new GapTextStore();

	/** This will hold the Acceleo source of the expressions entered in the viewer. */
	private AcceleoInterpreterSourceContent content;

	/** This will contain the full expression as it will be parsed. */
	private String fullExpression;

	/**
	 * This is the gap between the text as entered by the user and the text that is actually getting parsed.
	 */
	private int gap;

	/**
	 * This will be set to <code>true</code> if the user explicitly defined a module signature in his
	 * expression.
	 */
	private boolean hasExplicitModule;

	/** This will be set to the imported file when we are linked with an Acceleo editor. */
	private IFile moduleImport;

	/**
	 * Simply delegates to the super constructor.
	 * 
	 * @param parent
	 *            Parent composite for this viewer.
	 * @param ruler
	 *            The vertical ruler of this viewer.
	 * @param style
	 *            Style bits of this viewer.
	 * @see SourceViewer(Composite, IVerticalRuler, int)
	 */
	public AcceleoSourceViewer(Composite parent, IVerticalRuler ruler, int style) {
		super(parent, ruler, style);
	}

	/**
	 * Returns the qualified name of the given module file.
	 * 
	 * @param moduleFile
	 *            The file of which we need the qualified name.
	 * @return The qualified name of the given module file.
	 */
	private static String getQualifiedName(IPath moduleFile) {
		String path = moduleFile.removeFileExtension().toString();
		if (path.contains("src/")) { //$NON-NLS-1$
			path = path.substring(path.indexOf("src/") + 4); //$NON-NLS-1$
		}
		return path.replaceAll("/", "::"); //$NON-NLS-1$ //$NON-NLS-2$
	}

	/**
	 * Returns the Acceleo source content of this viewer.
	 * 
	 * @return The Acceleo source content of this viewer.
	 */
	public org.eclipse.acceleo.internal.ide.ui.editors.template.AcceleoSourceContent getContent() {
		return content;
	}

	/**
	 * Returns the offset gap between the displayed text and the actual parsed expression.
	 * 
	 * @return The offset gap between the displayed text and the actual parsed expression.
	 */
	public int getGap() {
		return gap;
	}

	/**
	 * Returns the current module import.
	 * 
	 * @return The current module import.
	 */
	public IFile getModuleImport() {
		return moduleImport;
	}

	/**
	 * A position updater is responsible for adapting document positions. This method is called by a position
	 * updater. A modification has been detected in the template text, at the specified start, until the
	 * character at index end - 1 or to the end of the sequence if no such character exists.
	 * 
	 * @param offset
	 *            the beginning index, inclusive
	 * @param posEnd
	 *            the ending index, exclusive
	 * @param newText
	 *            the string that will replace previous contents
	 */
	public void handlePositionUpdate(int offset, int posEnd, String newText) {
		if (getContent() == null) {
			return;
		}
		int replacementLength = 0;
		// Does the new text overlap with ancient?
		if (offset < buffer.getLength()) {
			replacementLength = posEnd - offset;
		}
		buffer.replace(offset, replacementLength, newText);

		// Did the user alter the signatures in any way?
		final String moduleSignature = MODULE;
		if (offset <= moduleSignature.length()) {
			if (buffer.getLength() >= moduleSignature.length()
					&& moduleSignature.equals(buffer.get(0, buffer.getLength()))) {
				hasExplicitModule = true;
			} else if (hasExplicitModule) {
				hasExplicitModule = false;
			}
		}
	}

	/**
	 * Creates and initialize the content of this viewer.
	 */
	public void initializeContent() {
		content = new AcceleoInterpreterSourceContent();
		content.init(new StringBuffer(getDocument().get()));
	}

	/**
	 * Sets the imported module for this viewer to the given file.
	 * 
	 * @param moduleImport
	 *            The new imported module.
	 */
	public void setModuleImport(IFile moduleImport) {
		this.moduleImport = moduleImport;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.acceleo.ui.interpreter.language.IInterpreterSourceViewer#showContentAssist(org.eclipse.acceleo.ui.interpreter.language.InterpreterContext)
	 */
	public void showContentAssist(InterpreterContext context) {
		if (fContentAssistant != null) {
			updateCST(context);
			fContentAssistant.showPossibleCompletions();
		}
	}

	/**
	 * Asks for a full update of the CST according to the text contained in {@link #buffer}.
	 * 
	 * @param context
	 *            The current interpreter context.
	 */
	public void updateCST(InterpreterContext context) {
		fullExpression = rebuildFullExpression(context);

		getContent().updateCST(0, fullExpression.length(), fullExpression);
		if (getContent() instanceof AcceleoInterpreterSourceContent) {
			((AcceleoInterpreterSourceContent)getContent()).setGap(gap);
		}
	}

	/**
	 * Creates a module with the selected content of the source viewer in a query.
	 * 
	 * @param context
	 *            The current interpreter context.
	 * @param queryName
	 *            The name of the query to be used or <code>null</code> if the dummy name should be used
	 * @return The module content.
	 */
	protected String computeQueryFromContext(InterpreterContext context, String queryName) {
		StringBuilder expressionBuffer = new StringBuilder();

		String text = this.getTextWidget().getText();
		ISelection selection = this.getSelection();
		if (selection != null && selection instanceof TextSelection
				&& ((TextSelection)selection).getLength() == 0) {
			// No selection, let's build a whole module
		} else if (selection != null && selection instanceof TextSelection) {
			// Only the selection will be used
			text = ((TextSelection)selection).getText();
		}

		// Does the text contains a whole module? If yes, do not touch it
		if (text.contains(MODULE)) {
			return text;
		}

		// Otherwise, let's build new module with a query inside

		EObject root = null;
		if (!context.getTargetEObjects().isEmpty()) {
			root = EcoreUtil.getRootContainer(context.getTargetEObjects().get(0));
		}

		String moduleSignature = DUMMY_MODULE;
		final Set<String> metamodelURIs = getMetamodelURIs(context);
		if (metamodelURIs.size() == 0) {
			// Use ecore as the default metamodel
			metamodelURIs.add(EcorePackage.eNS_URI);
		}

		StringBuilder nsURIs = new StringBuilder();
		Iterator<String> uriIterator = metamodelURIs.iterator();
		while (uriIterator.hasNext()) {
			nsURIs.append('\'' + uriIterator.next() + '\'');
			if (uriIterator.hasNext()) {
				nsURIs.append(',');
			}
		}

		moduleSignature = MessageFormat.format(moduleSignature, nsURIs.toString());
		expressionBuffer.append(moduleSignature);

		if (moduleImport != null) {
			final String modulePath = MessageFormat.format(DUMMY_IMPORT, getQualifiedName(moduleImport
					.getFullPath()));
			expressionBuffer.append(modulePath);
		}

		/*
		 * FIXME We should strip all the "import" section off the expression and continue treatment past that
		 * offset. For now we'll assume the expression past the import section is well formed with a query.
		 */
		boolean appendTail = false;
		if (!text.contains(TEMPLATE) && !text.contains(QUERY)) {
			appendTail = true;
			String querySignature = ""; //$NON-NLS-1$
			if (queryName == null) {
				querySignature = DUMMY_QUERY;
			} else {
				querySignature = "[query public " + queryName + "(thisEObject : {0}, currentModel : {1}{2}) : OclAny = " + LINE_SEPARATOR; //$NON-NLS-1$ //$NON-NLS-2$
			}
			List<EObject> target = context.getTargetEObjects();

			String argumentType = null;
			if (target != null && !target.isEmpty()) {
				argumentType = inferArgumentType(target);
			}
			if (argumentType == null) {
				// We use ecore as the default metamodel, we'll use EObject as the default argument type
				argumentType = EcorePackage.eINSTANCE.getEObject().getName();
			}

			String modelType;
			if (root == null) {
				modelType = EcorePackage.eINSTANCE.getEPackage().getName();
			} else {
				modelType = root.eClass().getName();
			}

			StringBuilder additionalVariables = computeVariables(context);

			querySignature = MessageFormat.format(querySignature, argumentType, modelType,
					additionalVariables);
			expressionBuffer.append(querySignature);
		}

		expressionBuffer.append(text);

		if (appendTail) {
			expressionBuffer.append(DUMMY_QUERY_TAIL);
		}

		return expressionBuffer.toString();
	}

	/**
	 * Creates a module with the selected content of the source viewer in a template.
	 * 
	 * @param context
	 *            The current interpreter context.
	 * @param templateName
	 *            The name of the template to be used or <code>null</code> if the dummy name should be used
	 * @return The module content.
	 */
	protected String computeTemplateFromContext(InterpreterContext context, String templateName) {
		StringBuilder expressionBuffer = new StringBuilder();

		String text = this.getTextWidget().getText();
		ISelection selection = this.getSelection();
		if (selection != null && selection instanceof TextSelection
				&& ((TextSelection)selection).getLength() == 0) {
			// No selection, let's build a whole module
		} else if (selection != null && selection instanceof TextSelection) {
			// Only the selection will be used
			text = ((TextSelection)selection).getText();
		}

		// Does the text contains a whole module? If yes, do not touch it
		if (text.contains(MODULE)) {
			return text;
		}

		// Otherwise, let's build new module with a template inside

		EObject root = null;
		if (!context.getTargetEObjects().isEmpty()) {
			root = EcoreUtil.getRootContainer(context.getTargetEObjects().get(0));
		}

		String moduleSignature = DUMMY_MODULE;
		final Set<String> metamodelURIs = getMetamodelURIs(context);
		if (metamodelURIs.size() == 0) {
			// Use ecore as the default metamodel
			metamodelURIs.add(EcorePackage.eNS_URI);
		}

		StringBuilder nsURIs = new StringBuilder();
		Iterator<String> uriIterator = metamodelURIs.iterator();
		while (uriIterator.hasNext()) {
			nsURIs.append('\'' + uriIterator.next() + '\'');
			if (uriIterator.hasNext()) {
				nsURIs.append(',');
			}
		}

		moduleSignature = MessageFormat.format(moduleSignature, nsURIs.toString());
		expressionBuffer.append(moduleSignature);

		if (moduleImport != null) {
			final String modulePath = MessageFormat.format(DUMMY_IMPORT, getQualifiedName(moduleImport
					.getFullPath()));
			expressionBuffer.append(modulePath);
		}

		/*
		 * FIXME We should strip all the "import" section off the expression and continue treatment past that
		 * offset. For now we'll assume the expression past the import section is well formed with a query.
		 */
		boolean appendTail = false;
		if (!text.contains(TEMPLATE) && !text.contains(QUERY)) {
			appendTail = true;
			String templateSignature = ""; //$NON-NLS-1$
			if (templateName == null) {
				templateSignature = DUMMY_TEMPLATE;
			} else {
				templateSignature = "[template public " + templateName + "({0})]" + LINE_SEPARATOR; //$NON-NLS-1$ //$NON-NLS-2$
			}
			List<EObject> target = context.getTargetEObjects();

			String targetType = null;
			if (target != null && !target.isEmpty()) {
				targetType = inferArgumentType(target);
			}

			String modelType = null;
			if (root != null) {
				modelType = root.eClass().getName();
			}

			StringBuilder templateParameters = new StringBuilder();
			if (targetType != null) {
				templateParameters.append(MessageFormat.format(DUMMY_TEMPLATE_TARGET_PARAM, targetType));
			}
			if (modelType != null) {
				if (templateParameters.length() != 0) {
					templateParameters.append(',').append(' ');
				}
				templateParameters.append(MessageFormat.format(DUMMY_TEMPLATE_MODEL_PARAM, modelType));
			}

			StringBuilder additionalVariables = computeVariables(context);
			if (templateParameters.length() > 0 && additionalVariables.length() > 0) {
				templateParameters.append(',').append(' ');
			}
			templateParameters.append(additionalVariables);

			templateSignature = MessageFormat.format(templateSignature, templateParameters);
			expressionBuffer.append(templateSignature);
		}

		expressionBuffer.append(text);

		if (appendTail) {
			expressionBuffer.append(DUMMY_TEMPLATE_TAIL);
		}

		return expressionBuffer.toString();
	}

	/**
	 * Returns the full expression as it was last rebuilt.
	 * 
	 * @return The full expression as it was last rebuilt.
	 */
	protected String getFullExpression() {
		return fullExpression;
	}

	/**
	 * This will be used before any update of the CST in order to rebuild the expression that is to be parsed.
	 * This expression should contain a module signature, a template signature, and the expression as
	 * contained in {@link #buffer} nested within this template.
	 * <p>
	 * However, if the expression entered in {@link #buffer} starts with a module signature, we'll return it
	 * unaltered.
	 * </p>
	 * 
	 * @param context
	 *            The current interpreter context.
	 * @return The full expression that is to be parsed in order to get its CST.
	 */
	protected String rebuildFullExpression(InterpreterContext context) {
		gap = 0;
		String expression = buffer.get(0, buffer.getLength());
		if (hasExplicitModule) {
			return expression;
		}

		StringBuilder expressionBuffer = new StringBuilder();

		EObject root = null;
		if (!context.getTargetEObjects().isEmpty()) {
			root = EcoreUtil.getRootContainer(context.getTargetEObjects().get(0));
		}

		String moduleSignature = DUMMY_MODULE;
		final Set<String> metamodelURIs = getMetamodelURIs(context);
		if (metamodelURIs.size() == 0) {
			// Use ecore as the default metamodel
			metamodelURIs.add(EcorePackage.eNS_URI);
		}

		StringBuilder nsURIs = new StringBuilder();
		Iterator<String> uriIterator = metamodelURIs.iterator();
		while (uriIterator.hasNext()) {
			nsURIs.append('\'' + uriIterator.next() + '\'');
			if (uriIterator.hasNext()) {
				nsURIs.append(',');
			}
		}

		moduleSignature = MessageFormat.format(moduleSignature, nsURIs.toString());
		expressionBuffer.append(moduleSignature);
		gap += moduleSignature.length();

		if (moduleImport != null) {
			final String modulePath = MessageFormat.format(DUMMY_IMPORT, getQualifiedName(moduleImport
					.getFullPath()));
			expressionBuffer.append(modulePath);
			gap += modulePath.length();
		}

		/*
		 * FIXME We should strip all the "import" section off the expression and continue treatment past that
		 * offset. For now we'll assume the expression past the import section is well formed with a query.
		 */
		boolean appendTail = false;
		if (!expression.contains(TEMPLATE) && !expression.contains(QUERY)) {
			appendTail = true;
			String templateSignature = DUMMY_TEMPLATE;
			List<EObject> target = context.getTargetEObjects();

			String targetType = null;
			if (target != null && !target.isEmpty()) {
				targetType = inferArgumentType(target);
			}

			String modelType = null;
			if (root != null) {
				modelType = root.eClass().getName();
			}

			StringBuilder templateParameters = new StringBuilder();
			if (targetType != null) {
				templateParameters.append(MessageFormat.format(DUMMY_TEMPLATE_TARGET_PARAM, targetType));
			}
			if (modelType != null) {
				if (templateParameters.length() != 0) {
					templateParameters.append(',').append(' ');
				}
				templateParameters.append(MessageFormat.format(DUMMY_TEMPLATE_MODEL_PARAM, modelType));
			}

			templateParameters.append(computeVariables(context));

			templateSignature = MessageFormat.format(templateSignature, templateParameters);
			expressionBuffer.append(templateSignature);
			gap += templateSignature.length();
		}

		expressionBuffer.append(expression);

		if (appendTail) {
			expressionBuffer.append(DUMMY_TEMPLATE_TAIL);
		}

		return expressionBuffer.toString();
	}

	/**
	 * Computes the signature of the variables.
	 * 
	 * @param context
	 *            The interpreter context.
	 * @return The signature of the variables to be used for a template or a query declaration.
	 */
	private StringBuilder computeVariables(InterpreterContext context) {
		StringBuilder additionalVariables = new StringBuilder();
		Iterator<Variable> variables = context.getVariables().iterator();
		while (variables.hasNext()) {
			Variable variable = variables.next();
			final String varName = variable.getName();
			final String varType = inferOCLType(variable.getValue());

			if (varName != null && varName.length() > 0 && !"self".equals(varName)) { //$NON-NLS-1$
				if (varType != null && varType.length() > 0) {
					additionalVariables.append(',').append(' ');
					additionalVariables.append(varName);
					additionalVariables.append(" : "); //$NON-NLS-1$
					additionalVariables.append(varType);
				}
			}
		}
		return additionalVariables;
	}

	/**
	 * Extracts the NsURI of the given EObject's metamodel.
	 * 
	 * @param object
	 *            The EObject for which we need the metamodel URI.
	 * @return The NsURI of the given EObject's metamodel, <code>null</code> if we could not retrieve it.
	 */
	private String extractNsURI(EObject object) {
		final EPackage pack = object.eClass().getEPackage();
		if (pack != null) {
			final String uri = pack.getNsURI();
			if (uri != null && uri.length() > 0) {
				// Check package registration
				ensurePackageRegistration(pack, uri);

				return uri;
			}
		}
		return null;
	}

	/**
	 * Ensures that the package with the given URI has been registered.
	 * 
	 * @param pack
	 *            The package.
	 * @param uri
	 *            The NS URI of the package.
	 */
	private void ensurePackageRegistration(final EPackage pack, final String uri) {
		Object ePackage = AcceleoPackageRegistry.INSTANCE.get(uri);
		if (ePackage == null && pack.eResource() != null) {
			// Package not registered
			AcceleoPackageRegistry.INSTANCE.registerEcorePackage(pack);
		}
	}

	/**
	 * Tries and extract the NsURIs of the given object if it is an EObject, or of its children if it is a
	 * Collection.
	 * 
	 * @param object
	 *            The object from which to try and detect metamodel URIs.
	 * @return The extracted URIs.
	 */
	private Set<String> extractNsURIs(Object object) {
		final Set<String> uris = new HashSet<String>();
		if (object instanceof EObject) {
			final String uri = extractNsURI((EObject)object);
			if (uri != null) {
				uris.add(uri);
			}
		} else if (object instanceof Collection<?>) {
			for (Object child : (Collection<?>)object) {
				final Set<String> childURIs = extractNsURIs(child);
				if (!childURIs.isEmpty()) {
					uris.addAll(childURIs);
				}
			}
		}
		return uris;
	}

	/**
	 * Retrieve all metamodel URIs from the given context.
	 * 
	 * @param context
	 *            The context from which to retrieve URIs.
	 * @return All metamodel URIs from the given context.
	 */
	private Set<String> getMetamodelURIs(InterpreterContext context) {
		Set<String> uris = new HashSet<String>();

		EObject root = null;
		if (!context.getTargetEObjects().isEmpty()) {
			root = EcoreUtil.getRootContainer(context.getTargetEObjects().get(0));
			final String targetNsURI = extractNsURI(context.getTargetEObjects().get(0));
			if (targetNsURI != null) {
				uris.add(targetNsURI);
			}
		}
		if (root != null) {
			final String modelNsURI = extractNsURI(root);
			if (modelNsURI != null) {
				uris.add(modelNsURI);
			}
		}
		Iterator<Variable> variables = context.getVariables().iterator();
		while (variables.hasNext()) {
			Variable variable = variables.next();
			final Set<String> variableValueURIs = extractNsURIs(variable.getValue());
			if (!variableValueURIs.isEmpty()) {
				uris.addAll(variableValueURIs);
			}
		}

		return uris;
	}

	/**
	 * Returns the OCL type of the given Object.
	 * 
	 * @param env
	 *            the ecore environment from which to seek types.
	 * @param obj
	 *            The Object we need an OCL type for.
	 * @return The OCL type of the given Object.
	 */
	private EClassifier getOCLType(EcoreEnvironment env, Object obj) {
		OCLStandardLibrary<EClassifier> library = env.getOCLStandardLibrary();
		EClassifier oclType = library.getOclAny();
		if (obj instanceof Number) {
			if (obj instanceof BigDecimal || obj instanceof Double || obj instanceof Float) {
				oclType = library.getReal();
			} else {
				oclType = library.getInteger();
			}
		} else if (obj instanceof String) {
			oclType = library.getString();
		} else if (obj instanceof Boolean) {
			oclType = library.getBoolean();
		} else if (obj instanceof EObject) {
			oclType = env.getUMLReflection().asOCLType(((EObject)obj).eClass());
		} else if (obj instanceof Collection<?>) {
			if (obj instanceof LinkedHashSet<?>) {
				oclType = library.getOrderedSet();
			} else if (obj instanceof Set<?>) {
				oclType = library.getSet();
			} else if (obj instanceof Bag<?>) {
				oclType = library.getBag();
			} else {
				oclType = library.getSequence();
			}
		}
		return oclType;
	}

	/**
	 * This will try and infer a meaningful argument String given the list of actual arguments. In short, if
	 * the list's size is equal to <code>1</code>, we'll consider the argument to be this list's single
	 * element. Otherwise we'll assume the argument should be a Sequence of this list's elements' common
	 * supertype.
	 * 
	 * @param arguments
	 *            The list of arguments for which we need an OCL argument String.
	 * @return The inferred OCL type String.
	 */
	private String inferArgumentType(List<EObject> arguments) {
		Object actualArgument = arguments;
		if (arguments.size() == 1) {
			actualArgument = arguments.get(0);
		}
		return inferOCLType(actualArgument);
	}

	/**
	 * Tries and infer the OCL type of the given Collection's content.
	 * 
	 * @param env
	 *            the ecore environment from which to seek types.
	 * @param coll
	 *            Collection for which we need an OCL type.
	 * @return The inferred OCL type. OCLAny if we could not infer anything more sensible.
	 */
	private EClassifier inferCollectionContentOCLType(EcoreEnvironment env, Collection<?> coll) {
		if (coll.isEmpty()) {
			return env.getOCLStandardLibrary().getOclAny();
		}

		Set<EClassifier> types = new HashSet<EClassifier>();
		for (Object child : coll) {
			types.add(getOCLType(env, child));
		}

		Iterator<EClassifier> iterator = types.iterator();

		EClassifier elementType = iterator.next();
		while (iterator.hasNext()) {
			elementType = env.getUMLReflection().getCommonSuperType(elementType, iterator.next());
		}

		if (elementType == null) {
			elementType = env.getOCLStandardLibrary().getOclAny();
		}

		return elementType;
	}

	/**
	 * Tries and infer the OCL type of the given Object.
	 * 
	 * @param obj
	 *            Object for which we need an OCL type.
	 * @return The inferred OCL type. OCLAny if we could not infer anything more sensible.
	 */
	private String inferOCLType(Object obj) {
		String oclType = "OCLAny"; //$NON-NLS-1$
		EcoreEnvironment env = (EcoreEnvironment)new EcoreEnvironmentFactory().createEnvironment();
		if (obj instanceof Collection<?>) {
			EClassifier elementType = inferCollectionContentOCLType(env, (Collection<?>)obj);
			CollectionKind kind = CollectionKind.SEQUENCE_LITERAL;
			if (obj instanceof LinkedHashSet<?>) {
				kind = CollectionKind.ORDERED_SET_LITERAL;
			} else if (obj instanceof Set<?>) {
				kind = CollectionKind.SET_LITERAL;
			} else if (obj instanceof Bag<?>) {
				kind = CollectionKind.BAG_LITERAL;
			}
			oclType = env.getTypeResolver().resolveCollectionType(kind, elementType).getName();
		} else {
			oclType = getOCLType(env, obj).getName();
		}
		return oclType;
	}
}

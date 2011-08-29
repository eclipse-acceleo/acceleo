/*******************************************************************************
 * Copyright (c) 2010, 2011 Obeo.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.acceleo.ui.interpreter.internal.language.acceleo;

import java.math.BigDecimal;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.eclipse.acceleo.internal.ide.ui.editors.template.AcceleoSourceContent;
import org.eclipse.acceleo.ui.interpreter.language.IInterpreterSourceViewer;
import org.eclipse.acceleo.ui.interpreter.language.InterpreterContext;
import org.eclipse.acceleo.ui.interpreter.view.Variable;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.jface.text.GapTextStore;
import org.eclipse.jface.text.ITextStore;
import org.eclipse.jface.text.source.IVerticalRuler;
import org.eclipse.jface.text.source.SourceViewer;
import org.eclipse.ocl.ecore.EcoreEnvironment;
import org.eclipse.ocl.ecore.EcoreEnvironmentFactory;
import org.eclipse.ocl.expressions.CollectionKind;
import org.eclipse.ocl.util.Bag;
import org.eclipse.swt.widgets.Composite;

/**
 * This subclass of a {@link SourceViewer} will allow us to maintain an {@link AcceleoSourceContent} while the
 * user changes the source.
 * 
 * @author <a href="mailto:laurent.goubet@obeo.fr">Laurent Goubet</a>
 */
public class AcceleoSourceViewer extends SourceViewer implements IInterpreterSourceViewer {
	/**
	 * This will hold the system specific line separator ("\n" for unix, "\r\n" for dos, "\r" for mac, ...).
	 */
	private static final String LINE_SEPARATOR = System.getProperty("line.separator"); //$NON-NLS-1$

	/** If the text doesn't start with "[module", we'll use this as the module's signature. */
	private static final String DUMMY_MODULE = "[module temporaryInterpreterModule(''{0}'')]" + LINE_SEPARATOR; //$NON-NLS-1$

	/**
	 * If the text doesn't start with "[module", we'll use this as the template's signature. Otherwise, we'll
	 * assume the user specified both module and query/template in his expression.
	 */
	private static final String DUMMY_TEMPLATE = "[template public temporaryInterpreterTemplate(target : {0}, model : {1}{2})]" + LINE_SEPARATOR; //$NON-NLS-1$ 

	/**
	 * If the text doesn't start with "[module", we'll use this to close the template. Otherwise, we'll assume
	 * the user specified both module and query/template in his expression.
	 */
	private static final String DUMMY_TAIL = LINE_SEPARATOR + "[/template]"; //$NON-NLS-1$

	/** This will hold the Acceleo source of the expressions entered in the viewer. */
	private AcceleoInterpreterSourceContent content;

	/**
	 * This will be set to <code>true</code> if the user explicitly defined a module signature in his
	 * expression.
	 */
	private boolean hasExplicitModule;

	/**
	 * This will be updated with the exact text as entered by the user and displayed on the viewer. However,
	 * this is not what will be parsed : it will be inserted into {@link #fullExpression}.
	 */
	private ITextStore buffer = new GapTextStore();

	/** This will contain the full expression as it will be parsed. */
	private String fullExpression;

	/**
	 * This is the gap between the text as entered by the user and the text that is actually getting parsed.
	 */
	private int gap;

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
	 * Creates and initialize the content of this viewer.
	 */
	public void initializeContent() {
		content = new AcceleoInterpreterSourceContent();
		content.init(new StringBuffer(getDocument().get()));
	}

	/**
	 * Returns the Acceleo source content of this viewer.
	 * 
	 * @return The Acceleo source content of this viewer.
	 */
	public AcceleoSourceContent getContent() {
		return content;
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
		final String moduleSignature = "[module"; //$NON-NLS-1$
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
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.acceleo.ui.interpreter.language.IInterpreterSourceViewer#showContentAssist(org.eclipse.acceleo.ui.interpreter.language.InterpreterContext)
	 */
	public void showContentAssist(InterpreterContext context) {
		updateCST(context);
		fContentAssistant.showPossibleCompletions();
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

		String moduleSignature = DUMMY_MODULE;
		EObject root = null;
		if (!context.getTargetEObjects().isEmpty()) {
			root = EcoreUtil.getRootContainer(context.getTargetEObjects().get(0));
		}
		String targetNsURI = null;
		if (root != null) {
			targetNsURI = root.eClass().getEPackage().getNsURI();
		}
		if (targetNsURI == null || "".equals(targetNsURI)) { //$NON-NLS-1$
			// Use ecore as the default metamodel
			targetNsURI = EcorePackage.eNS_URI;
		}
		moduleSignature = MessageFormat.format(moduleSignature, targetNsURI);
		expressionBuffer.append(moduleSignature);
		gap += moduleSignature.length();

		/*
		 * FIXME We should strip all the "import" section off the expression and continue treatment past that
		 * offset. For now we'll assume the expression past the import section is well formed with a query.
		 */
		boolean appendTail = false;
		if (!expression.contains("[template") && !expression.contains("[query")) { //$NON-NLS-1$ //$NON-NLS-2$ 
			appendTail = true;
			String templateSignature = DUMMY_TEMPLATE;
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

			StringBuilder additionalVariables = new StringBuilder();
			Iterator<Variable> variables = context.getVariables().iterator();
			while (variables.hasNext()) {
				Variable variable = variables.next();
				final String varName = variable.getName();
				final String varType = inferOCLType(variable.getValue());

				if (varName != null && varName.length() > 0 && varType != null && varType.length() > 0) {
					additionalVariables.append(", "); //$NON-NLS-1$
					additionalVariables.append(varName);
					additionalVariables.append(" : "); //$NON-NLS-1$
					additionalVariables.append(varType);
				}
			}

			templateSignature = MessageFormat.format(templateSignature, argumentType, modelType,
					additionalVariables);
			expressionBuffer.append(templateSignature);
			gap += templateSignature.length();
		}

		expressionBuffer.append(expression);

		if (appendTail) {
			expressionBuffer.append(DUMMY_TAIL);
		}

		return expressionBuffer.toString();
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
		if (arguments.size() == 1) {
			return arguments.get(0).eClass().getName();
		}
		EClass commonSuperType = null;
		for (EObject argument : arguments) {
			if (commonSuperType == null) {
				commonSuperType = argument.eClass();
			} else {
				commonSuperType = getCommonSuperType(commonSuperType, argument.eClass());
			}
		}
		assert commonSuperType != null;
		return "Sequence(" + commonSuperType.getName() + ")"; //$NON-NLS-1$ //$NON-NLS-2$
	}

	/**
	 * This will return the lowest (in the type hierarchy) common super type of the two given EClasses. If no
	 * common super type is found, we'll return EObject.
	 * 
	 * @param eClass1
	 *            The first of the two EClasses for which we need a super type.
	 * @param eClass2
	 *            The second of the two EClasses for which we need a super type.
	 * @return The lowest common super type found in the hierarchy of the two given EClasses.
	 */
	private EClass getCommonSuperType(EClass eClass1, EClass eClass2) {
		EClass commonSuperType = EcorePackage.eINSTANCE.getEObject();

		if (eClass1.equals(eClass2) || eClass1.isSuperTypeOf(eClass2)) {
			commonSuperType = eClass1;
		} else if (eClass2.isSuperTypeOf(eClass1)) {
			commonSuperType = eClass2;
		} else {
			List<EClass> allSuperTypes1 = new ArrayList<EClass>(eClass1.getEAllSuperTypes());
			allSuperTypes1.retainAll(eClass2.getEAllSuperTypes());

			if (!allSuperTypes1.isEmpty()) {
				commonSuperType = allSuperTypes1.get(0);
			}
		}
		return commonSuperType;
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
		EClassifier oclType = env.getOCLStandardLibrary().getOclAny();
		if (obj instanceof Number) {
			if (obj instanceof BigDecimal || obj instanceof Double || obj instanceof Float) {
				oclType = env.getOCLStandardLibrary().getReal();
			} else {
				oclType = env.getOCLStandardLibrary().getInteger();
			}
		} else if (obj instanceof String) {
			oclType = env.getOCLStandardLibrary().getString();
		} else if (obj instanceof Boolean) {
			oclType = env.getOCLStandardLibrary().getBoolean();
		} else if (obj instanceof EObject) {
			oclType = env.getUMLReflection().asOCLType(((EObject)obj).eClass());
		} else if (obj instanceof Collection<?>) {
			if (obj instanceof LinkedHashSet<?>) {
				oclType = env.getOCLStandardLibrary().getOrderedSet();
			} else if (obj instanceof Set<?>) {
				oclType = env.getOCLStandardLibrary().getSet();
			} else if (obj instanceof Bag<?>) {
				oclType = env.getOCLStandardLibrary().getBag();
			} else {
				oclType = env.getOCLStandardLibrary().getSequence();
			}
		}
		return oclType;
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

		return elementType;
	}
}

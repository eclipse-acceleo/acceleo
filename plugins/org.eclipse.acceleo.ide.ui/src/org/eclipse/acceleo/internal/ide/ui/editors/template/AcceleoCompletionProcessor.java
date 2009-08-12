/*******************************************************************************
 * Copyright (c) 2008, 2009 Obeo.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.acceleo.internal.ide.ui.editors.template;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import org.eclipse.acceleo.common.IAcceleoConstants;
import org.eclipse.acceleo.ide.ui.AcceleoUIActivator;
import org.eclipse.acceleo.internal.parser.cst.utils.Sequence;
import org.eclipse.acceleo.internal.parser.cst.utils.SequenceBlock;
import org.eclipse.acceleo.parser.cst.Block;
import org.eclipse.acceleo.parser.cst.CSTNode;
import org.eclipse.acceleo.parser.cst.CstPackage;
import org.eclipse.acceleo.parser.cst.IfBlock;
import org.eclipse.acceleo.parser.cst.InitSection;
import org.eclipse.acceleo.parser.cst.LetBlock;
import org.eclipse.acceleo.parser.cst.ModelExpression;
import org.eclipse.acceleo.parser.cst.Module;
import org.eclipse.acceleo.parser.cst.ModuleElement;
import org.eclipse.acceleo.parser.cst.ModuleExtendsValue;
import org.eclipse.acceleo.parser.cst.ModuleImportsValue;
import org.eclipse.acceleo.parser.cst.Query;
import org.eclipse.acceleo.parser.cst.Template;
import org.eclipse.acceleo.parser.cst.TemplateOverridesValue;
import org.eclipse.acceleo.parser.cst.TextExpression;
import org.eclipse.acceleo.parser.cst.TypedModel;
import org.eclipse.core.runtime.Path;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EOperation;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EParameter;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.jface.text.ITextSelection;
import org.eclipse.jface.text.ITextViewer;
import org.eclipse.jface.text.contentassist.CompletionProposal;
import org.eclipse.jface.text.contentassist.ICompletionProposal;
import org.eclipse.jface.text.contentassist.IContentAssistProcessor;
import org.eclipse.jface.text.contentassist.IContextInformation;
import org.eclipse.jface.text.contentassist.IContextInformationValidator;
import org.eclipse.ocl.expressions.CollectionKind;
import org.eclipse.ocl.helper.Choice;
import org.eclipse.swt.graphics.Image;

/**
 * A completion processor for the Acceleo template editor.
 * 
 * @author <a href="mailto:jonathan.musset@obeo.fr">Jonathan Musset</a>
 */
public class AcceleoCompletionProcessor implements IContentAssistProcessor {

	/**
	 * The auto activation characters for completion proposal.
	 */
	private static final char[] AUTO_ACTIVATION_CHARACTERS = new char[] {' ', '.', '[', '-', '>', ':' }; // Unless

	/**
	 * The source content.
	 */
	protected AcceleoSourceContent content;

	/**
	 * The text used to compute the proposals.
	 */
	private String text;

	/**
	 * An offset within the text for which completions should be computed.
	 */
	private int offset;

	/**
	 * The CST node at the current offset.
	 */
	private CSTNode cstNode;

	/**
	 * The default type of the variables.
	 */
	private String defaultVariableType;

	/**
	 * Constructor.
	 * 
	 * @param content
	 *            is the content
	 */
	public AcceleoCompletionProcessor(AcceleoSourceContent content) {
		super();
		this.content = content;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.jface.text.contentassist.IContentAssistProcessor#computeCompletionProposals(org.eclipse.jface.text.ITextViewer,
	 *      int)
	 */
	public ICompletionProposal[] computeCompletionProposals(ITextViewer viewer, int pos) {
		if (viewer != null) {
			text = viewer.getDocument().get();
			ITextSelection selection = (ITextSelection)viewer.getSelectionProvider().getSelection();
			if (selection != null && selection.getOffset() == pos) {
				offset = selection.getOffset() + selection.getLength();
			} else {
				offset = pos;
			}
		} else {
			text = content.getText();
			offset = pos;
		}
		cstNode = content.getCSTNode(offset, offset);
		if (cstNode instanceof InitSection && offset == cstNode.getStartPosition()
				&& cstNode.eContainer() != null) {
			// We change the CSTNode if we are on the first index of an InitSection
			cstNode = (CSTNode)cstNode.eContainer();
		}
		defaultVariableType = "EObject"; //$NON-NLS-1$
		if (text.indexOf("http://www.eclipse.org/uml2/") > -1) { //$NON-NLS-1$
			defaultVariableType = "Element"; //$NON-NLS-1$
		}
		try {
			return computeCompletionProposals();
		} finally {
			text = null;
			offset = 0;
			cstNode = null;
		}
	}

	/**
	 * Returns a list of completion proposals based on the specified location within the text.
	 * 
	 * @return an array of completion proposals or null if no proposals are possible
	 */
	private ICompletionProposal[] computeCompletionProposals() {
		List<ICompletionProposal> proposals = new ArrayList<ICompletionProposal>();
		if (cstNode == null
				|| (cstNode instanceof Module && cstNode.getEndPosition() == 0 && cstNode.getStartPosition() == 0)) {
			computeModulePatternProposals(proposals);
		} else if (cstNode instanceof TypedModel) {
			computeEPackageProposals(proposals);
		} else if (cstNode instanceof ModuleImportsValue || cstNode instanceof ModuleExtendsValue) {
			computeImportProposals(proposals);
		} else {
			if (offset > 0 && text.charAt(offset - 1) != '/') {
				computeEClassifierProposals(proposals);
				if (proposals.size() == 0) {
					computeKeywordsProposals(proposals);
					if (!isNextSignificantChar(']')) {
						computePatternsProposals(proposals);
					}
				}
				if (text.charAt(offset - 1) != ']') {
					if (cstNode instanceof ModelExpression || cstNode instanceof InitSection) {
						computeOCLProposals(proposals);
					} else if (cstNode instanceof Block && !(cstNode instanceof Template)) {
						computeOCLProposals(proposals);
					} else if (cstNode instanceof Query && cstNode.getStartPosition() > -1
							&& offset > cstNode.getStartPosition()
							&& isHeaderAfterParenthesis(text.substring(cstNode.getStartPosition(), offset))) {
						computeOCLProposals(proposals);
					} else if (cstNode instanceof TemplateOverridesValue) {
						computeTemplatesProposals(proposals);
					}
				}
			}
		}
		return proposals.toArray(new ICompletionProposal[proposals.size()]);
	}

	/**
	 * Indicates if the next significant character matches with the given one.
	 * 
	 * @param ref
	 *            is the character to match
	 * @return true if 'ref' is the next significant character
	 */
	private boolean isNextSignificantChar(char ref) {
		if (offset < text.length()) {
			int i = offset;
			char c;
			do {
				c = text.charAt(i);
				i++;
			} while (Character.isWhitespace(c) && i < text.length());
			return c == ref;
		} else {
			return false;
		}
	}

	/**
	 * Computes a list of templates completion proposals based on the specified location within the text.
	 * 
	 * @param proposals
	 *            are the completion proposals (in out parameter)
	 */
	private void computeTemplatesProposals(List<ICompletionProposal> proposals) {
		int i = offset;
		while (i > 0 && Character.isJavaIdentifierPart(text.charAt(i - 1))) {
			i--;
		}
		String start = text.substring(i, offset);
		int startPosition = cstNode.getStartPosition();
		if (startPosition < 0) {
			startPosition = 0;
		}
		if (startPosition > offset) {
			startPosition = offset;
		}
		String textOCL = text.substring(startPosition, offset);
		Collection<Choice> choices = content.getSyntaxHelp(textOCL, offset);
		Set<String> duplicated = new HashSet<String>();
		for (Choice next : choices) {
			String replacementString = next.getName();
			if (replacementString.toLowerCase().startsWith(start.toLowerCase())) {
				switch (next.getKind()) {
					case OPERATION:
						if (next.getElement() instanceof EOperation) {
							Image image = AcceleoUIActivator.getDefault().getImage(
									"icons/template-editor/completion/Template.gif"); //$NON-NLS-1$
							EOperation eOperation = (EOperation)next.getElement();
							String description;
							if (eOperation.getEContainingClass() != null) {
								description = eOperation.getEContainingClass().getName() + "." //$NON-NLS-1$
										+ next.getDescription();
							} else {
								description = next.getDescription();
							}
							if (!duplicated.contains(next.getDescription())) {
								duplicated.add(next.getDescription());
								proposals.add(new CompletionProposal(replacementString, offset
										- start.length(), start.length(), replacementString.length(), image,
										next.getDescription(), null, description));
							}
						}
						break;
					default:
						break;
				}
			}
		}
	}

	/**
	 * Computes a list of OCL completion proposals based on the specified location within the text.
	 * 
	 * @param proposals
	 *            are the completion proposals (in out parameter)
	 */
	private void computeOCLProposals(List<ICompletionProposal> proposals) {
		int i = offset;
		while (i > 0 && Character.isJavaIdentifierPart(text.charAt(i - 1))) {
			i--;
		}
		String start = text.substring(i, offset);
		int startPosition = cstNode.getStartPosition();
		if (startPosition < 0) {
			startPosition = 0;
		}
		if (startPosition > offset) {
			startPosition = offset;
		}
		String textOCL = text.substring(startPosition, offset);
		Collection<Choice> choices = content.getSyntaxHelp(textOCL, offset);
		Set<String> duplicated = new HashSet<String>();
		for (Choice next : choices) {
			String replacementString = next.getName();
			if (replacementString.toLowerCase().startsWith(start.toLowerCase())) {
				switch (next.getKind()) {
					case OPERATION:
						addOCLOperationChoice(proposals, next, start, duplicated);
						break;
					case SIGNAL:
					case PROPERTY:
						String displayProperty = next.getDescription();
						String descriptionProperty;
						if (next.getElement() instanceof EStructuralFeature) {
							displayProperty = getPropertyDisplay((EStructuralFeature)next.getElement());
							StringBuilder descriptionPropertyBuilder = new StringBuilder(displayProperty);
							descriptionPropertyBuilder.append("\n\t defined on ");
							descriptionPropertyBuilder.append(((EStructuralFeature)next.getElement())
									.getEContainingClass().getName());
							descriptionProperty = descriptionPropertyBuilder.toString();
						} else {
							descriptionProperty = displayProperty;
						}
						if (!duplicated.contains(displayProperty)) {
							duplicated.add(displayProperty);
							proposals
									.add(new CompletionProposal(
											replacementString,
											offset - start.length(),
											start.length(),
											replacementString.length(),
											AcceleoUIActivator.getDefault().getImage(
													"icons/template-editor/completion/Property.gif"), displayProperty, //$NON-NLS-1$
											null, descriptionProperty));
						}
						break;
					case ENUMERATION_LITERAL:
						if (!duplicated.contains(replacementString)) {
							duplicated.add(replacementString);
							proposals.add(new CompletionProposal(replacementString, offset - start.length(),
									start.length(), replacementString.length(), AcceleoUIActivator
											.getDefault().getImage(
													"icons/template-editor/completion/EnumLiteral.gif"), //$NON-NLS-1$
									replacementString, null, next.getDescription()));
						}
						break;
					case VARIABLE:
						String displayVariable;
						String description;
						if (IAcceleoConstants.SELF.equals(replacementString) || next.getDescription() == null) {
							displayVariable = replacementString;
							description = ""; //$NON-NLS-1$
						} else {
							displayVariable = replacementString + ":" + next.getDescription(); //$NON-NLS-1$
							description = next.getDescription();
						}
						if (!duplicated.contains(displayVariable)) {
							duplicated.add(displayVariable);
							proposals
									.add(new CompletionProposal(
											replacementString,
											offset - start.length(),
											start.length(),
											replacementString.length(),
											AcceleoUIActivator.getDefault().getImage(
													"icons/template-editor/completion/Variable.gif"), displayVariable, //$NON-NLS-1$
											null, description));
						}
						break;
					default:
						break;
				}
			}
		}
	}

	/**
	 * Adds (or not) an OCL operation in the proposals list.
	 * 
	 * @param proposals
	 *            is the output proposals list
	 * @param nextOperationChoice
	 *            is the next operation to add (or not) in the proposals list
	 * @param start
	 *            is the current text used to keep the completion proposals, the name of a valid proposal must
	 *            start with this string
	 * @param duplicated
	 *            are the descriptions that already appear in the completion list
	 */
	private void addOCLOperationChoice(List<ICompletionProposal> proposals, Choice nextOperationChoice,
			String start, Set<String> duplicated) {
		Image image;
		if (nextOperationChoice instanceof AcceleoCompletionChoice) {
			if (((AcceleoCompletionChoice)nextOperationChoice).getAcceleoElement() instanceof org.eclipse.acceleo.model.mtl.Template) {
				image = AcceleoUIActivator.getDefault().getImage(
						"icons/template-editor/completion/Template.gif"); //$NON-NLS-1$
			} else if (((AcceleoCompletionChoice)nextOperationChoice).getAcceleoElement() instanceof org.eclipse.acceleo.model.mtl.Query) {
				image = AcceleoUIActivator.getDefault()
						.getImage("icons/template-editor/completion/Query.gif"); //$NON-NLS-1$
			} else if (((AcceleoCompletionChoice)nextOperationChoice).getAcceleoElement() instanceof org.eclipse.acceleo.model.mtl.Macro) {
				image = AcceleoUIActivator.getDefault()
						.getImage("icons/template-editor/completion/Macro.gif"); //$NON-NLS-1$
			} else {
				image = AcceleoUIActivator.getDefault().getImage(
						"icons/template-editor/completion/Operation.gif"); //$NON-NLS-1$
			}
		} else {
			image = AcceleoUIActivator.getDefault()
					.getImage("icons/template-editor/completion/Operation.gif"); //$NON-NLS-1$
		}
		if (nextOperationChoice.getElement() instanceof EOperation) {
			EOperation eOperation = (EOperation)nextOperationChoice.getElement();
			String replacementStringWithArgsBefore = eOperation.getName()
					+ IAcceleoConstants.PARENTHESIS_BEGIN;
			String replacementStringWithArgsAfter = ""; //$NON-NLS-1$
			Iterator<EParameter> eParametersIt = eOperation.getEParameters().iterator();
			while (eParametersIt.hasNext()) {
				EParameter eParameter = eParametersIt.next();
				replacementStringWithArgsAfter += eParameter.getName();
				if (eParametersIt.hasNext()) {
					replacementStringWithArgsAfter += ", "; //$NON-NLS-1$
				}
			}
			replacementStringWithArgsAfter += ')';
			String description;
			if (eOperation.getEContainingClass() != null) {
				description = eOperation.getEContainingClass().getName() + "." //$NON-NLS-1$
						+ nextOperationChoice.getDescription();
			} else {
				description = nextOperationChoice.getDescription();
			}
			if (!duplicated.contains(nextOperationChoice.getDescription())) {
				duplicated.add(nextOperationChoice.getDescription());
				proposals.add(new CompletionProposal(replacementStringWithArgsBefore
						+ replacementStringWithArgsAfter, offset - start.length(), start.length(),
						replacementStringWithArgsBefore.length(), image,
						nextOperationChoice.getDescription(), null, description));
			}
		} else {
			if (!duplicated.contains(nextOperationChoice.getDescription())) {
				duplicated.add(nextOperationChoice.getDescription());
				proposals.add(new CompletionProposal(nextOperationChoice.getName(), offset - start.length(),
						start.length(), nextOperationChoice.getName().length(), image, nextOperationChoice
								.getDescription(), null, nextOperationChoice.getDescription()));
			}
		}
	}

	/**
	 * Gets the buffer to display for each structural feature.
	 * 
	 * @param eStructuralFeature
	 *            is the feature to display
	 * @return the string to display
	 */
	private String getPropertyDisplay(EStructuralFeature eStructuralFeature) {
		StringBuffer displayProperty = new StringBuffer();
		displayProperty.append(eStructuralFeature.getName());
		if (eStructuralFeature.getEType() != null) {
			displayProperty.append(':');
			displayProperty.append(eStructuralFeature.getEType().getName());
		}
		if (eStructuralFeature.getLowerBound() == eStructuralFeature.getUpperBound()) {
			displayProperty.append(' ');
			displayProperty.append('[');
			displayProperty.append(eStructuralFeature.getLowerBound());
			displayProperty.append(']');
		} else {
			displayProperty.append(' ');
			displayProperty.append('[');
			displayProperty.append(eStructuralFeature.getLowerBound());
			displayProperty.append(".."); //$NON-NLS-1$
			if (eStructuralFeature.getUpperBound() == -1) {
				displayProperty.append("*"); //$NON-NLS-1$
			} else {
				displayProperty.append(eStructuralFeature.getUpperBound());
			}
			displayProperty.append(']');
		}
		return displayProperty.toString();
	}

	/**
	 * Computes an import declaration based on the specified location within the text.
	 * 
	 * @param proposals
	 *            are the completion proposals (in out parameter)
	 */
	private void computeImportProposals(List<ICompletionProposal> proposals) {
		int i = offset;
		while (i > 0 && !Character.isWhitespace(text.charAt(i - 1))) {
			i--;
		}
		String start = text.substring(i, offset);
		Iterator<URI> dependencies = content.getAccessibleOutputFiles().iterator();
		while (dependencies.hasNext()) {
			URI uri = dependencies.next();
			String replacementString = new Path(uri.lastSegment()).removeFileExtension().lastSegment();
			if (replacementString.toLowerCase().startsWith(start.toLowerCase())) {
				proposals.add(new CompletionProposal(replacementString, offset - start.length(), start
						.length(), replacementString.length(), AcceleoUIActivator.getDefault().getImage(
						"icons/template-editor/completion/Module.gif"), replacementString, null, uri.path())); //$NON-NLS-1$
			}
		}
	}

	/**
	 * Computes a list of meta-model URIs based on the specified location within the text.
	 * 
	 * @param proposals
	 *            are the completion proposals (in out parameter)
	 */
	private void computeEPackageProposals(List<ICompletionProposal> proposals) {
		final String uriImagePath = "icons/template-editor/completion/URI.gif"; //$NON-NLS-1$
		int i = offset;
		while (i > 0 && text.charAt(i - 1) != '(' && text.charAt(i - 1) != ',' && text.charAt(i - 1) != '\'') {
			i--;
		}
		String start = text.substring(i, offset);
		Iterator<String> entries = new TreeSet<String>(EPackage.Registry.INSTANCE.keySet()).iterator();
		while (entries.hasNext()) {
			String pURI = entries.next();
			if (pURI.toLowerCase().startsWith(start.toLowerCase())) {
				proposals.add(new CompletionProposal(pURI, offset - start.length(), start.length(), pURI
						.length(), AcceleoUIActivator.getDefault().getImage(uriImagePath), pURI, null, pURI));
			}
		}
		if (start.length() > 0) {
			entries = new TreeSet<String>(EPackage.Registry.INSTANCE.keySet()).iterator();
			while (entries.hasNext()) {
				String pURI = entries.next();
				EPackage ePackage = EPackage.Registry.INSTANCE.getEPackage(pURI);
				if (ePackage != null) {
					String shortName = ePackage.getName();
					if (shortName.startsWith(start.toLowerCase())) {
						proposals.add(new CompletionProposal(pURI, offset - start.length(), start.length(),
								pURI.length(), AcceleoUIActivator.getDefault().getImage(uriImagePath), pURI,
								null, pURI));
					}
				}
			}
		}
	}

	/**
	 * Computes a list of classifiers based on the specified location within the text.
	 * 
	 * @param proposals
	 *            are the completion proposals (in out parameter)
	 */
	private void computeEClassifierProposals(List<ICompletionProposal> proposals) {
		if (content.getCST() != null) {
			int i = offset;
			while (i > 0 && Character.isJavaIdentifierPart(text.charAt(i - 1))) {
				i--;
			}
			int j = i;
			while (j > 0 && Character.isWhitespace(text.charAt(j - 1))) {
				j--;
			}
			if (j > 0 && (text.charAt(j - 1) == ':' || typeIsRequiredAfterParenthesis(j))) {
				String start = text.substring(i, offset);
				Iterator<EClassifier> eClassifierIt = content.getTypes().iterator();
				while (eClassifierIt.hasNext()) {
					EClassifier eClassifier = eClassifierIt.next();
					if (eClassifier.getName().toLowerCase().startsWith(start.toLowerCase())) {
						proposals.add(new CompletionProposal(eClassifier.getName(), offset - start.length(),
								start.length(), eClassifier.getName().length(),
								AcceleoUIActivator.getDefault().getImage(
										"icons/template-editor/completion/Type.gif"), eClassifier //$NON-NLS-1$
										.getName(), null, eClassifier.getName()));
					}
				}
			}
		}
	}

	/**
	 * Indicates if one meta type is required at the given index.
	 * 
	 * @param index
	 *            is the index in the text
	 * @return true if the meta types are requested at the given index
	 */
	private boolean typeIsRequiredAfterParenthesis(int index) {
		if (index <= 0 || text.charAt(index - 1) != '(') {
			return false;
		}
		int i = index - 1;
		while (i > 0 && Character.isJavaIdentifierPart(text.charAt(i - 1))) {
			i--;
		}
		String start = text.substring(i, index - 1);
		boolean result;
		if (start.length() == 0) {
			result = false;
		} else if (CollectionKind.getByName(start) != null) {
			result = true;
		} else {
			result = false;
			int startPosition = cstNode.getStartPosition();
			if (startPosition < 0) {
				startPosition = 0;
			}
			if (startPosition > index - 1) {
				startPosition = index - 1;
			}
			String textOCL = text.substring(startPosition, index - 1);
			Iterator<Choice> choices = content.getSyntaxHelp(textOCL, index - 1).iterator();
			while (!result && choices.hasNext()) {
				Choice next = choices.next();
				if (start.length() > 0 && next.getName().startsWith(start)
						&& next.getElement() instanceof EOperation) {
					EOperation eOperation = (EOperation)next.getElement();
					for (EParameter eParameter : eOperation.getEParameters()) {
						if (eParameter.getEType() != null
								&& "OclType".equals(eParameter.getEType().getName())) { //$NON-NLS-1$
							result = true;
							break;
						}
					}
				}
			}
		}
		return result;
	}

	/**
	 * Computes a module declaration pattern based on the specified location within the text.
	 * 
	 * @param proposals
	 *            are the completion proposals (in out parameter)
	 */
	private void computeModulePatternProposals(List<ICompletionProposal> proposals) {
		int i = offset;
		while (i > 0 && Character.isJavaIdentifierPart(text.charAt(i - 1))) {
			i--;
		}
		if (i > 0 && text.charAt(i - 1) == '[') {
			i--;
		}
		String start = text.substring(i, offset);
		StringBuffer tabBuffer = new StringBuffer();
		while (i > 0 && Character.isWhitespace(text.charAt(i - 1)) && text.charAt(i - 1) != '\n') {
			tabBuffer.insert(0, text.charAt(i - 1));
			i--;
		}
		if (i == 0 || text.charAt(i - 1) == '\n') {
			String fileName;
			if (content.getFile() != null) {
				fileName = content.getFile().getName();
			} else {
				fileName = ""; //$NON-NLS-1$
			}
			if (IAcceleoConstants.MODULE.startsWith(start.toLowerCase())
					|| ('[' + IAcceleoConstants.MODULE).startsWith(start.toLowerCase())) {
				String replacementStringBefore = '[' + IAcceleoConstants.MODULE + ' '
						+ new Path(fileName).removeFileExtension().lastSegment() + "('"; //$NON-NLS-1$
				String replacementStringAfter = "') /]\n" + tabBuffer.toString(); //$NON-NLS-1$
				String replacementString = replacementStringBefore + replacementStringAfter;
				proposals.add(new CompletionProposal(replacementString, offset - start.length(), start
						.length(), replacementStringBefore.length(), AcceleoUIActivator.getDefault()
						.getImage("icons/template-editor/completion/Pattern.gif"), //$NON-NLS-1$
						'[' + IAcceleoConstants.MODULE + ']', null, replacementString));
			}
		}
	}

	/**
	 * Computes a list of patterns based on the specified location within the text.
	 * 
	 * @param proposals
	 *            are the completion proposals (in out parameter)
	 */
	private void computePatternsProposals(List<ICompletionProposal> proposals) {
		int i = offset;
		while (i > 0 && (Character.isJavaIdentifierPart(text.charAt(i - 1)) || text.charAt(i - 1) == '@')) {
			i--;
		}
		if (i > 0 && (text.charAt(i - 1) == '[' || text.charAt(i - 1) == ']')) {
			i--;
		}
		String start = text.substring(i, offset);
		StringBuffer tabBuffer = new StringBuffer();
		while (i > 0 && Character.isWhitespace(text.charAt(i - 1)) && text.charAt(i - 1) != '\n') {
			tabBuffer.insert(0, text.charAt(i - 1));
			i--;
		}
		String tab = tabBuffer.toString();
		Image patternImage = AcceleoUIActivator.getDefault().getImage(
				"icons/template-editor/completion/Pattern.gif"); //$NON-NLS-1$
		if (i > 0 && text.charAt(i - 1) == '\n') {
			// We are not interested by the first line
			if (content.getCSTParent(cstNode, org.eclipse.acceleo.parser.cst.ModuleElement.class) == null
					&& text.substring(0, i).indexOf('[' + IAcceleoConstants.TEMPLATE) == -1
					&& text.substring(0, i).indexOf('[' + IAcceleoConstants.QUERY) == -1
					&& text.substring(0, i).indexOf('[' + IAcceleoConstants.MACRO) == -1) {
				if (IAcceleoConstants.IMPORT.startsWith(start.toLowerCase())
						|| ('[' + IAcceleoConstants.IMPORT).startsWith(start.toLowerCase())) {
					String replacementStringBefore = '[' + IAcceleoConstants.IMPORT + ' ';
					String replacementStringAfter = " /]\n" + tab; //$NON-NLS-1$
					String replacementString = replacementStringBefore + replacementStringAfter;
					proposals.add(new CompletionProposal(replacementString, offset - start.length(), start
							.length(), replacementStringBefore.length(), patternImage,
							'[' + IAcceleoConstants.IMPORT + ']', null, replacementString));
				}
			}
			if (!(cstNode instanceof ModuleElement)
					&& content.getCSTParent(cstNode, org.eclipse.acceleo.parser.cst.ModuleElement.class) == null) {
				computeModuleElementsPatternsProposals(proposals, start, tab, patternImage);
			}
			if (cstNode instanceof IfBlock
					|| (cstNode instanceof TextExpression && cstNode.eContainer() instanceof IfBlock)) {
				if (IAcceleoConstants.ELSE_IF.startsWith(start.toLowerCase())
						|| ('[' + IAcceleoConstants.ELSE_IF).startsWith(start.toLowerCase())) {
					String replacementStringBefore = '[' + IAcceleoConstants.ELSE_IF + ' ' + '(';
					String replacementStringAfter = ")]" + '\n' + tab + '\t'; //$NON-NLS-1$
					String replacementString = replacementStringBefore + replacementStringAfter;
					proposals.add(new CompletionProposal(replacementString, offset - start.length(), start
							.length(), replacementStringBefore.length(), patternImage,
							'[' + IAcceleoConstants.ELSE_IF + ']', null, replacementString));
				}
				if (IAcceleoConstants.ELSE.startsWith(start.toLowerCase())
						|| ('[' + IAcceleoConstants.ELSE).startsWith(start.toLowerCase())) {
					String replacementString = '[' + IAcceleoConstants.ELSE + ']' + '\n' + tab + '\t';
					proposals.add(new CompletionProposal(replacementString, offset - start.length(), start
							.length(), replacementString.length(), patternImage,
							'[' + IAcceleoConstants.ELSE + ']', null, replacementString));
				}
			}
			if (cstNode instanceof LetBlock
					|| (cstNode instanceof TextExpression && cstNode.eContainer() instanceof LetBlock)) {
				if (IAcceleoConstants.ELSE_LET.startsWith(start.toLowerCase())
						|| ('[' + IAcceleoConstants.ELSE_LET).startsWith(start.toLowerCase())) {
					String replacementStringBefore = '[' + IAcceleoConstants.ELSE_LET + ' ';
					String replacementStringAfter = " " + ":" + " " + defaultVariableType + "]" + '\n' //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ 
							+ tab + '\t';
					String replacementString = replacementStringBefore + replacementStringAfter;
					proposals.add(new CompletionProposal(replacementString, offset - start.length(), start
							.length(), replacementStringBefore.length(), patternImage,
							'[' + IAcceleoConstants.ELSE_LET + ']', null, replacementString));
				}
				if (IAcceleoConstants.ELSE.startsWith(start.toLowerCase())
						|| ('[' + IAcceleoConstants.ELSE).startsWith(start.toLowerCase())) {
					String replacementString = '[' + IAcceleoConstants.ELSE + ']' + '\n' + tab + '\t';
					proposals.add(new CompletionProposal(replacementString, offset - start.length(), start
							.length(), replacementString.length(), patternImage,
							'[' + IAcceleoConstants.ELSE + ']', null, replacementString));
				}
			}
			if (!(cstNode instanceof ModelExpression)
					&& (IAcceleoConstants.COMMENT.startsWith(start.toLowerCase()) || ('[' + IAcceleoConstants.COMMENT)
							.startsWith(start.toLowerCase()))) {
				String replacementStringBefore = '[' + IAcceleoConstants.COMMENT + ' ';
				String replacementStringAfter = " /]\n" + tab; //$NON-NLS-1$
				String replacementString = replacementStringBefore + replacementStringAfter;
				proposals.add(new CompletionProposal(replacementString, offset - start.length(), start
						.length(), replacementStringBefore.length(), patternImage,
						'[' + IAcceleoConstants.COMMENT + ']', null, replacementString));
			}
		}
		if (!start.startsWith("@")) { //$NON-NLS-1$
			computeBlocksPatternsProposals(proposals, start, tab, patternImage);
		}
		computeMainTagPatternsProposals(proposals, start, tab, patternImage);
	}

	/**
	 * Computes a list of patterns for the main tag (@main).
	 * 
	 * @param proposals
	 *            are the completion proposals (in out parameter)
	 * @param start
	 *            is the beginning text
	 * @param tab
	 *            is the current indentation of the text
	 * @param patternImage
	 *            is the image to show in the completion popup menu
	 */
	private void computeMainTagPatternsProposals(List<ICompletionProposal> proposals, String start,
			String tab, Image patternImage) {
		if (cstNode instanceof TextExpression) {
			if (IAcceleoConstants.TAG_MAIN.startsWith(start.toLowerCase())
					|| ('[' + IAcceleoConstants.TAG_MAIN).startsWith(start.toLowerCase())) {
				String replacementStringBefore = '[' + IAcceleoConstants.COMMENT + ' '
						+ IAcceleoConstants.TAG_MAIN + ' ' + "/]\n" + tab; //$NON-NLS-1$
				String replacementString = replacementStringBefore;
				proposals.add(new CompletionProposal(replacementString, offset - start.length(), start
						.length(), replacementStringBefore.length(), patternImage,
						IAcceleoConstants.TAG_MAIN, null, replacementString));
			}
		}
	}

	/**
	 * Computes a list of patterns for all the module elements : template, query, macro...
	 * 
	 * @param proposals
	 *            are the completion proposals (in out parameter)
	 * @param start
	 *            is the beginning text
	 * @param tab
	 *            is the current indentation of the text
	 * @param patternImage
	 *            is the image to show in the completion popup menu
	 */
	private void computeModuleElementsPatternsProposals(List<ICompletionProposal> proposals, String start,
			String tab, Image patternImage) {
		if (IAcceleoConstants.TEMPLATE.startsWith(start.toLowerCase())
				|| ('[' + IAcceleoConstants.TEMPLATE).startsWith(start.toLowerCase())) {
			String replacementStringBefore = '[' + IAcceleoConstants.TEMPLATE + ' '
					+ IAcceleoConstants.VISIBILITY_KIND_PUBLIC + ' ';
			String replacementStringAfter = '(' + "e : " + defaultVariableType + ")]\n" + tab //$NON-NLS-1$ //$NON-NLS-2$
					+ '\t' + '\n' + tab + '[' + '/' + IAcceleoConstants.TEMPLATE + ']';
			String replacementString = replacementStringBefore + replacementStringAfter;
			proposals.add(new CompletionProposal(replacementString, offset - start.length(), start.length(),
					replacementStringBefore.length(), patternImage, '[' + IAcceleoConstants.TEMPLATE + ']',
					null, tab + replacementString));
		}
		if (IAcceleoConstants.QUERY.startsWith(start.toLowerCase())
				|| ('[' + IAcceleoConstants.QUERY).startsWith(start.toLowerCase())) {
			String replacementStringBefore = '[' + IAcceleoConstants.QUERY + ' '
					+ IAcceleoConstants.VISIBILITY_KIND_PUBLIC + ' ';
			String replacementStringAfter = "(e : " + defaultVariableType + ") : " + defaultVariableType //$NON-NLS-1$ //$NON-NLS-2$
					+ " = /]\n"; //$NON-NLS-1$
			String replacementString = replacementStringBefore + replacementStringAfter;
			proposals.add(new CompletionProposal(replacementString, offset - start.length(), start.length(),
					replacementStringBefore.length(), patternImage, '[' + IAcceleoConstants.QUERY + ']',
					null, tab + replacementString));
		}
		if (IAcceleoConstants.MACRO.startsWith(start.toLowerCase())
				|| ('[' + IAcceleoConstants.MACRO).startsWith(start.toLowerCase())) {
			String replacementStringBefore = '[' + IAcceleoConstants.MACRO + ' '
					+ IAcceleoConstants.VISIBILITY_KIND_PUBLIC + ' ';
			String replacementStringAfter = "(e : " + defaultVariableType + ") : " + defaultVariableType //$NON-NLS-1$ //$NON-NLS-2$
					+ "]\n" + tab + '\t' + '\n' + tab + '[' + '/' + IAcceleoConstants.MACRO //$NON-NLS-1$
					+ ']';
			String replacementString = replacementStringBefore + replacementStringAfter;
			proposals.add(new CompletionProposal(replacementString, offset - start.length(), start.length(),
					replacementStringBefore.length(), patternImage, '[' + IAcceleoConstants.MACRO + ']',
					null, tab + replacementString));
		}
	}

	/**
	 * Checks the position and computes a list of patterns for all the blocks : for, if, file...
	 * 
	 * @param proposals
	 *            are the completion proposals (in out parameter)
	 * @param start
	 *            is the beginning text
	 * @param tab
	 *            is the current indentation of the text
	 * @param patternImage
	 *            is the image to show in the completion popup menu
	 */
	private void computeBlocksPatternsProposals(List<ICompletionProposal> proposals, String start,
			String tab, Image patternImage) {
		if (cstNode instanceof Template) {
			if (((Template)cstNode).getBody().size() == 0
					|| offset >= ((Template)cstNode).getBody().get(0).getStartPosition()) {
				computeBlocksPatternsProposalsSub(proposals, start, tab, patternImage);
			}
		} else if (cstNode instanceof Block) {
			computeBlocksPatternsProposalsSub(proposals, start, tab, patternImage);
		}
		if (content.getCSTParent(cstNode, org.eclipse.acceleo.parser.cst.ModuleElement.class) != null) {
			if (cstNode instanceof org.eclipse.acceleo.parser.cst.TextExpression) {
				int size = proposals.size();
				computeBlocksPatternsProposalsSub(proposals, start, tab, patternImage);
				if (proposals.size() == size) {
					computeBlocksPatternsProposalsSub(proposals, "", tab, patternImage); //$NON-NLS-1$
				}
			} else if (cstNode instanceof org.eclipse.acceleo.parser.cst.ModelExpression) {
				if (start.startsWith("[") && (offset == text.length() || text.charAt(offset) != '/')) { //$NON-NLS-1$
					computeBlocksPatternsProposalsSub(proposals, start, tab, patternImage);
				}
			}
		}
	}

	/**
	 * Computes a list of patterns for all the blocks : for, if, file...
	 * 
	 * @param proposals
	 *            are the completion proposals (in out parameter)
	 * @param start
	 *            is the beginning text
	 * @param tab
	 *            is the current indentation of the text
	 * @param patternImage
	 *            is the image to show in the completion popup menu
	 */
	private void computeBlocksPatternsProposalsSub(List<ICompletionProposal> proposals, String start,
			String tab, Image patternImage) {
		if (IAcceleoConstants.DEFAULT_BEGIN.startsWith(start.toLowerCase())) {
			String replacementStringBefore = "["; //$NON-NLS-1$
			String replacementStringAfter = "/]"; //$NON-NLS-1$
			String replacementString = replacementStringBefore + replacementStringAfter;
			proposals.add(new CompletionProposal(replacementString, offset - start.length(), start.length(),
					replacementStringBefore.length(), patternImage, "[ ]", null, replacementString)); //$NON-NLS-1$
		}
		if (IAcceleoConstants.FOR.startsWith(start.toLowerCase())
				|| ('[' + IAcceleoConstants.FOR).startsWith(start.toLowerCase())) {
			String replacementStringBefore = '[' + IAcceleoConstants.FOR + ' ' + '(';
			String replacementStringAfter = " : " + defaultVariableType + " | )]\n" + tab + '\t' //$NON-NLS-1$ //$NON-NLS-2$
					+ '\n' + tab + '[' + '/' + IAcceleoConstants.FOR + ']';
			String replacementString = replacementStringBefore + replacementStringAfter;
			proposals.add(new CompletionProposal(replacementString, offset - start.length(), start.length(),
					replacementStringBefore.length(), patternImage, '[' + IAcceleoConstants.FOR + ']', null,
					tab + replacementString));
		}
		if (IAcceleoConstants.IF.startsWith(start.toLowerCase())
				|| ('[' + IAcceleoConstants.IF).startsWith(start.toLowerCase())) {
			String replacementStringBefore = '[' + IAcceleoConstants.IF + ' ' + '(';
			String replacementStringAfter = ")]\n" + tab + '\t' + '\n' + tab + '[' //$NON-NLS-1$
					+ '/' + IAcceleoConstants.IF + ']';
			String replacementString = replacementStringBefore + replacementStringAfter;
			proposals.add(new CompletionProposal(replacementString, offset - start.length(), start.length(),
					replacementStringBefore.length(), patternImage, '[' + IAcceleoConstants.IF + ']', null,
					tab.toString() + replacementString));
		}
		if (IAcceleoConstants.FILE.startsWith(start.toLowerCase())
				|| ('[' + IAcceleoConstants.FILE).startsWith(start.toLowerCase())) {
			computeFileBlockPatternsProposals(proposals, start, tab, patternImage, true);
			computeFileBlockPatternsProposals(proposals, start, tab, patternImage, false);
		}
		if (IAcceleoConstants.LET.startsWith(start.toLowerCase())
				|| ('[' + IAcceleoConstants.LET).startsWith(start.toLowerCase())) {
			String replacementStringBefore = '[' + IAcceleoConstants.LET + ' ';
			String replacementStringAfter = " : " + defaultVariableType + "]\n" + tab + '\t' //$NON-NLS-1$ //$NON-NLS-2$
					+ '\n' + tab + '[' + '/' + IAcceleoConstants.LET + ']';
			String replacementString = replacementStringBefore + replacementStringAfter;
			proposals.add(new CompletionProposal(replacementString, offset - start.length(), start.length(),
					replacementStringBefore.length(), patternImage, '[' + IAcceleoConstants.LET + ']', null,
					tab.toString() + replacementString));
		}
		if (IAcceleoConstants.TRACE.startsWith(start.toLowerCase())
				|| ('[' + IAcceleoConstants.TRACE).startsWith(start.toLowerCase())) {
			String replacementStringBefore = '[' + IAcceleoConstants.TRACE + " ('"; //$NON-NLS-1$
			String replacementStringAfter = "')]\n" + tab + '\t' + '\n' + tab + '[' //$NON-NLS-1$
					+ '/' + IAcceleoConstants.TRACE + ']';
			String replacementString = replacementStringBefore + replacementStringAfter;
			proposals.add(new CompletionProposal(replacementString, offset - start.length(), start.length(),
					replacementStringBefore.length(), patternImage, '[' + IAcceleoConstants.TRACE + ']',
					null, tab + replacementString));
		}
		if (IAcceleoConstants.PROTECTED_AREA.startsWith(start.toLowerCase())
				|| ('[' + IAcceleoConstants.PROTECTED_AREA).startsWith(start.toLowerCase())) {
			String replacementStringBefore = '[' + IAcceleoConstants.PROTECTED_AREA + " ('"; //$NON-NLS-1$
			String replacementStringAfter = "')]\n" + tab + '\t' + '\n' + tab + '[' //$NON-NLS-1$
					+ '/' + IAcceleoConstants.PROTECTED_AREA + ']';
			String replacementString = replacementStringBefore + replacementStringAfter;
			proposals.add(new CompletionProposal(replacementString, offset - start.length(), start.length(),
					replacementStringBefore.length(), patternImage,
					'[' + IAcceleoConstants.PROTECTED_AREA + ']', null, tab + replacementString));
		}
		if (IAcceleoConstants.SUPER.startsWith(start.toLowerCase())
				|| ('[' + IAcceleoConstants.SUPER).startsWith(start.toLowerCase())) {
			String replacementStringBefore = '[' + IAcceleoConstants.SUPER;
			String replacementStringAfter = "/]"; //$NON-NLS-1$
			String replacementString = replacementStringBefore + replacementStringAfter;
			proposals.add(new CompletionProposal(replacementString, offset - start.length(), start.length(),
					replacementStringBefore.length(), patternImage, '[' + IAcceleoConstants.SUPER + ']',
					null, replacementString));
		}
		if (IAcceleoConstants.DEFAULT_BEGIN.startsWith(start.toLowerCase())) {
			String replacementString = "[ '[' /] "; //$NON-NLS-1$
			proposals.add(new CompletionProposal(replacementString, offset - start.length(), start.length(),
					replacementString.length(), patternImage, "'['", null, replacementString)); //$NON-NLS-1$
		}
		if ("".equals(start.toLowerCase())) { //$NON-NLS-1$
			String replacementString = "[ ']' /] "; //$NON-NLS-1$
			proposals.add(new CompletionProposal(replacementString, offset - start.length(), start.length(),
					replacementString.length(), patternImage, "']'", null, replacementString)); //$NON-NLS-1$
		}
	}

	/**
	 * Computes a list of patterns for the file block.
	 * 
	 * @param proposals
	 *            are the completion proposals (in out parameter)
	 * @param start
	 *            is the beginning text
	 * @param tab
	 *            is the current indentation of the text
	 * @param patternImage
	 *            is the image to show in the completion popup menu
	 * @param withMainTag
	 *            indicates if a main tag (@main) must be added before the file block
	 */
	private void computeFileBlockPatternsProposals(List<ICompletionProposal> proposals, String start,
			String tab, Image patternImage, boolean withMainTag) {
		String mainTagText;
		if (withMainTag) {
			mainTagText = '[' + IAcceleoConstants.COMMENT + ' ' + IAcceleoConstants.TAG_MAIN + ' ' + '/'
					+ ']' + '\n' + tab;
		} else {
			mainTagText = ""; //$NON-NLS-1$
		}
		String replacementStringBefore = mainTagText + '[' + IAcceleoConstants.FILE + ' ' + "('file://"; //$NON-NLS-1$
		org.eclipse.acceleo.parser.cst.ModuleElement cstModuleElement = (org.eclipse.acceleo.parser.cst.ModuleElement)content
				.getCSTParent(cstNode, org.eclipse.acceleo.parser.cst.ModuleElement.class);
		String currentModuleElementName;
		if (cstModuleElement != null) {
			currentModuleElementName = cstModuleElement.getName();
		} else {
			currentModuleElementName = ""; //$NON-NLS-1$
		}
		String replacementStringAfter = "', false, '" + currentModuleElementName.toUpperCase() //$NON-NLS-1$
				+ "-ID')]\n" + tab + '\t' + '\n' + tab + '[' + '/' //$NON-NLS-1$
				+ IAcceleoConstants.FILE + ']';
		String replacementString = replacementStringBefore + replacementStringAfter;
		String displayString = '[' + IAcceleoConstants.FILE + ']';
		if (withMainTag) {
			displayString += " - " + IAcceleoConstants.TAG_MAIN; //$NON-NLS-1$
		}
		proposals.add(new CompletionProposal(replacementString, offset - start.length(), start.length(),
				replacementStringBefore.length(), patternImage, displayString, null, tab.toString()
						+ replacementString));
	}

	/**
	 * Computes a list of keywords based on the specified location within the text.
	 * 
	 * @param proposals
	 *            are the completion proposals (in out parameter)
	 */
	private void computeKeywordsProposals(List<ICompletionProposal> proposals) {
		if (cstNode != null) {
			int i = offset;
			while (i > 0 && Character.isJavaIdentifierPart(text.charAt(i - 1))) {
				i--;
			}
			String start = text.substring(i, offset);
			int bHeader = cstNode.getStartPosition();
			String bHeaderText;
			if (bHeader > -1 && bHeader < i) {
				bHeaderText = text.substring(bHeader, i).trim();
			} else {
				bHeaderText = ""; //$NON-NLS-1$
			}
			Image keywordImage = AcceleoUIActivator.getDefault().getImage(
					"icons/template-editor/completion/Keyword.gif"); //$NON-NLS-1$
			if (cstNode instanceof org.eclipse.acceleo.parser.cst.Module) {
				if (isHeaderAfterParenthesis(bHeaderText)
						&& ((org.eclipse.acceleo.parser.cst.Module)cstNode).getExtends().size() == 0) {
					computeKeywordProposal(proposals, start, IAcceleoConstants.EXTENDS + ' ',
							"", keywordImage); //$NON-NLS-1$
				}
			}
			if (cstNode instanceof org.eclipse.acceleo.parser.cst.ModelExpression
					&& cstNode.eContainingFeature() == CstPackage.eINSTANCE.getBlock_Body()) {
				computeKeywordsProposalsInTemplateInvocationHeader(proposals, start, bHeaderText,
						keywordImage);
			}
			if (cstNode instanceof org.eclipse.acceleo.parser.cst.Template) {
				computeKeywordsProposalsInTemplateHeader(proposals, start, bHeaderText, keywordImage);
			}
			if (cstNode instanceof org.eclipse.acceleo.parser.cst.Macro) {
				computeKeywordsProposalsInMacroHeader(proposals, start, bHeaderText, keywordImage);
			}
			if (cstNode instanceof org.eclipse.acceleo.parser.cst.ForBlock) {
				computeKeywordsProposalsInForBlockHeader(proposals, start, bHeaderText, keywordImage);
			}
		}
	}

	/**
	 * Computes a list of keywords for a 'Template' header.
	 * 
	 * @param proposals
	 *            are the completion proposals (in out parameter)
	 * @param start
	 *            is the beginning text of the candidates
	 * @param bHeaderText
	 *            is the current beginning text of the header, from the beginning index to the current offset
	 * @param keywordImage
	 *            is the image to show in the completion popup menu
	 */
	private void computeKeywordsProposalsInTemplateHeader(List<ICompletionProposal> proposals, String start,
			String bHeaderText, Image keywordImage) {
		if (bHeaderText.equals(IAcceleoConstants.TEMPLATE)) {
			computeKeywordProposal(proposals, start, IAcceleoConstants.VISIBILITY_KIND_PUBLIC + ' ', "", //$NON-NLS-1$
					keywordImage);
			computeKeywordProposal(proposals, start, IAcceleoConstants.VISIBILITY_KIND_PROTECTED + ' ', "", //$NON-NLS-1$
					keywordImage);
			computeKeywordProposal(proposals, start, IAcceleoConstants.VISIBILITY_KIND_PRIVATE + ' ', "", //$NON-NLS-1$
					keywordImage);
		}
		if (isHeaderAfterParenthesis(bHeaderText)) {
			StringBuffer bHeaderBuffer = new StringBuffer(bHeaderText);
			Sequence pGuard = new Sequence(IAcceleoConstants.GUARD, IAcceleoConstants.PARENTHESIS_BEGIN);
			Sequence pInit = new Sequence(IAcceleoConstants.BRACKETS_BEGIN);
			if (((org.eclipse.acceleo.parser.cst.Template)cstNode).getOverrides().size() == 0
					&& pGuard.search(bHeaderBuffer).b() == -1 && pInit.search(bHeaderBuffer).b() == -1) {
				computeKeywordProposal(proposals, start, IAcceleoConstants.OVERRIDES + ' ', "", keywordImage); //$NON-NLS-1$
			}
			if (((org.eclipse.acceleo.parser.cst.Template)cstNode).getGuard() == null
					&& pInit.search(bHeaderBuffer).b() == -1) {
				computeKeywordProposal(proposals, start, IAcceleoConstants.GUARD + ' '
						+ IAcceleoConstants.PARENTHESIS_BEGIN, IAcceleoConstants.PARENTHESIS_END,
						keywordImage);
			}
			if (((org.eclipse.acceleo.parser.cst.Template)cstNode).getInit() == null
					&& "{".startsWith(start.toLowerCase())) { //$NON-NLS-1$
				String replacementStringBefore = "{ "; //$NON-NLS-1$
				String replacementStringAfter = ": " + defaultVariableType + "; }"; //$NON-NLS-1$ //$NON-NLS-2$
				String replacementString = replacementStringBefore + replacementStringAfter;
				proposals.add(new CompletionProposal(replacementString, offset - start.length(), start
						.length(), replacementStringBefore.length(), keywordImage, "{ }", null, //$NON-NLS-1$
						replacementString));
			}
		}
	}

	/**
	 * Computes a list of keywords for a 'Macro' header.
	 * 
	 * @param proposals
	 *            are the completion proposals (in out parameter)
	 * @param start
	 *            is the beginning text of the candidates
	 * @param bHeaderText
	 *            is the current beginning text of the header, from the beginning index to the current offset
	 * @param keywordImage
	 *            is the image to show in the completion popup menu
	 */
	private void computeKeywordsProposalsInMacroHeader(List<ICompletionProposal> proposals, String start,
			String bHeaderText, Image keywordImage) {
		if (bHeaderText.equals(IAcceleoConstants.MACRO)) {
			computeKeywordProposal(proposals, start, IAcceleoConstants.VISIBILITY_KIND_PUBLIC + ' ', "", //$NON-NLS-1$
					keywordImage);
			computeKeywordProposal(proposals, start, IAcceleoConstants.VISIBILITY_KIND_PROTECTED + ' ', "", //$NON-NLS-1$
					keywordImage);
			computeKeywordProposal(proposals, start, IAcceleoConstants.VISIBILITY_KIND_PRIVATE + ' ', "", //$NON-NLS-1$
					keywordImage);
		}
	}

	/**
	 * Computes a list of keywords for a 'TemplateInvocation'.
	 * 
	 * @param proposals
	 *            are the completion proposals (in out parameter)
	 * @param start
	 *            is the beginning text of the candidates
	 * @param bHeaderText
	 *            is the current beginning text of the header, from the beginning index to the current offset
	 * @param keywordImage
	 *            is the image to show in the completion popup menu
	 */
	private void computeKeywordsProposalsInTemplateInvocationHeader(List<ICompletionProposal> proposals,
			String start, String bHeaderText, Image keywordImage) {
		if (isHeaderAfterParenthesis(bHeaderText) || bHeaderText.indexOf(IAcceleoConstants.SUPER) > -1) {
			StringBuffer bHeaderBuffer = new StringBuffer(bHeaderText);
			Sequence pSeparator = new Sequence(IAcceleoConstants.SEPARATOR,
					IAcceleoConstants.PARENTHESIS_BEGIN);
			Sequence pAfter = new Sequence(IAcceleoConstants.AFTER, IAcceleoConstants.PARENTHESIS_BEGIN);
			Sequence pGuard = new Sequence(IAcceleoConstants.GUARD, IAcceleoConstants.PARENTHESIS_BEGIN);
			if (((org.eclipse.acceleo.parser.cst.ModelExpression)cstNode).getBefore() == null) {
				if (pSeparator.search(bHeaderBuffer).b() == -1 && pAfter.search(bHeaderBuffer).b() == -1
						&& pGuard.search(bHeaderBuffer).b() == -1) {
					computeKeywordProposal(proposals, start, IAcceleoConstants.BEFORE + ' '
							+ IAcceleoConstants.PARENTHESIS_BEGIN, IAcceleoConstants.PARENTHESIS_END,
							keywordImage);
				}
			}
			if (((org.eclipse.acceleo.parser.cst.ModelExpression)cstNode).getEach() == null
					&& pAfter.search(bHeaderBuffer).b() == -1 && pGuard.search(bHeaderBuffer).b() == -1) {
				computeKeywordProposal(proposals, start, IAcceleoConstants.SEPARATOR + ' '
						+ IAcceleoConstants.PARENTHESIS_BEGIN, IAcceleoConstants.PARENTHESIS_END,
						keywordImage);
			}
			if (((org.eclipse.acceleo.parser.cst.ModelExpression)cstNode).getAfter() == null
					&& pGuard.search(bHeaderBuffer).b() == -1) {
				computeKeywordProposal(proposals, start, IAcceleoConstants.AFTER + ' '
						+ IAcceleoConstants.PARENTHESIS_BEGIN, IAcceleoConstants.PARENTHESIS_END,
						keywordImage);
			}
		}
	}

	/**
	 * Computes a list of keywords for a 'ForBlock' header.
	 * 
	 * @param proposals
	 *            are the completion proposals (in out parameter)
	 * @param start
	 *            is the beginning text of the candidates
	 * @param bHeaderText
	 *            is the current beginning text of the header, from the beginning index to the current offset
	 * @param keywordImage
	 *            is the image to show in the completion popup menu
	 */
	private void computeKeywordsProposalsInForBlockHeader(List<ICompletionProposal> proposals, String start,
			String bHeaderText, Image keywordImage) {
		if (isHeaderAfterParenthesis(bHeaderText)) {
			StringBuffer bHeaderBuffer = new StringBuffer(bHeaderText);
			Sequence pSeparator = new Sequence(IAcceleoConstants.SEPARATOR,
					IAcceleoConstants.PARENTHESIS_BEGIN);
			Sequence pAfter = new Sequence(IAcceleoConstants.AFTER, IAcceleoConstants.PARENTHESIS_BEGIN);
			Sequence pGuard = new Sequence(IAcceleoConstants.GUARD, IAcceleoConstants.PARENTHESIS_BEGIN);
			Sequence pInit = new Sequence(IAcceleoConstants.BRACKETS_BEGIN);
			if (((org.eclipse.acceleo.parser.cst.ForBlock)cstNode).getBefore() == null) {
				if (pSeparator.search(bHeaderBuffer).b() == -1 && pAfter.search(bHeaderBuffer).b() == -1
						&& pGuard.search(bHeaderBuffer).b() == -1 && pInit.search(bHeaderBuffer).b() == -1) {
					computeKeywordProposal(proposals, start, IAcceleoConstants.BEFORE + ' '
							+ IAcceleoConstants.PARENTHESIS_BEGIN, IAcceleoConstants.PARENTHESIS_END,
							keywordImage);
				}
			}
			if (((org.eclipse.acceleo.parser.cst.ForBlock)cstNode).getEach() == null
					&& pAfter.search(bHeaderBuffer).b() == -1 && pGuard.search(bHeaderBuffer).b() == -1
					&& pInit.search(bHeaderBuffer).b() == -1) {
				computeKeywordProposal(proposals, start, IAcceleoConstants.SEPARATOR + ' '
						+ IAcceleoConstants.PARENTHESIS_BEGIN, IAcceleoConstants.PARENTHESIS_END,
						keywordImage);
			}
			if (((org.eclipse.acceleo.parser.cst.ForBlock)cstNode).getAfter() == null
					&& pGuard.search(bHeaderBuffer).b() == -1 && pInit.search(bHeaderBuffer).b() == -1) {
				computeKeywordProposal(proposals, start, IAcceleoConstants.AFTER + ' '
						+ IAcceleoConstants.PARENTHESIS_BEGIN, IAcceleoConstants.PARENTHESIS_END,
						keywordImage);
			}
			if (((org.eclipse.acceleo.parser.cst.ForBlock)cstNode).getGuard() == null
					&& pInit.search(bHeaderBuffer).b() == -1) {
				computeKeywordProposal(proposals, start, IAcceleoConstants.GUARD + ' '
						+ IAcceleoConstants.PARENTHESIS_BEGIN, IAcceleoConstants.PARENTHESIS_END,
						keywordImage);
			}
			if (((org.eclipse.acceleo.parser.cst.ForBlock)cstNode).getInit() == null && "{".startsWith(start)) { //$NON-NLS-1$
				String replacementStringBefore = "{ "; //$NON-NLS-1$
				String replacementStringAfter = ": " + defaultVariableType + "; }"; //$NON-NLS-1$ //$NON-NLS-2$
				String replacementString = replacementStringBefore + replacementStringAfter;
				proposals.add(new CompletionProposal(replacementString, offset - start.length(), start
						.length(), replacementStringBefore.length(), keywordImage, "{ }", null, //$NON-NLS-1$
						replacementString));
			}
		}
	}

	/**
	 * Indicates if there are parenthesis in the text, and if there are finished. It works only if we are
	 * before ']'
	 * 
	 * @param aText
	 *            is the text to parse
	 * @return true if there are parenthesis in the text, and if there are finished
	 */
	private boolean isHeaderAfterParenthesis(String aText) {
		final String tag = "___TAG___"; //$NON-NLS-1$
		StringBuffer buffer = new StringBuffer();
		buffer.append(aText);
		buffer.append(tag);
		Sequence literalEscape = new Sequence(IAcceleoConstants.LITERAL_ESCAPE);
		SequenceBlock pLiteral = new SequenceBlock(new Sequence(IAcceleoConstants.LITERAL_BEGIN),
				new Sequence(IAcceleoConstants.LITERAL_END), literalEscape, false, null);
		SequenceBlock pParenthesis = new SequenceBlock(new Sequence(IAcceleoConstants.PARENTHESIS_BEGIN),
				new Sequence(IAcceleoConstants.PARENTHESIS_END), null, true, new SequenceBlock[] {pLiteral });
		Sequence pHeaderEnd = new Sequence(IAcceleoConstants.DEFAULT_END);
		Sequence pTag = new Sequence(tag);
		return (pHeaderEnd.search(buffer, 0, buffer.length(), null, new SequenceBlock[] {pLiteral, }).b() == -1)
				&& (pParenthesis.search(buffer, 0, buffer.length()).b() > -1)
				&& (pTag.search(buffer, 0, buffer.length(), null,
						new SequenceBlock[] {pLiteral, pParenthesis, }).b() > -1);
	}

	/**
	 * Creates a keyword proposal.
	 * 
	 * @param proposals
	 *            are the completion proposals (in out parameter)
	 * @param start
	 *            is the beginning text
	 * @param replacementStringBefore
	 *            is the replacement string before the new offset
	 * @param replacementStringAfter
	 *            is the replacement string after the new offset
	 * @param keywordImage
	 *            is the image to show in the completion popup menu
	 */
	private void computeKeywordProposal(List<ICompletionProposal> proposals, String start,
			String replacementStringBefore, String replacementStringAfter, Image keywordImage) {
		String replacementString = replacementStringBefore + replacementStringAfter;
		if (replacementString.toLowerCase().startsWith(start.toLowerCase())) {
			proposals.add(new CompletionProposal(replacementString, offset - start.length(), start.length(),
					replacementStringBefore.length(), keywordImage, replacementString, null,
					replacementString));
		}
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.jface.text.contentassist.IContentAssistProcessor#computeContextInformation(org.eclipse.jface.text.ITextViewer,
	 *      int)
	 */
	public IContextInformation[] computeContextInformation(ITextViewer viewer, int documentOffset) {
		return null;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.jface.text.contentassist.IContentAssistProcessor#getCompletionProposalAutoActivationCharacters()
	 */
	public char[] getCompletionProposalAutoActivationCharacters() {
		// TODO JMU should return a copy of the array to avoid accidental modifications.
		return AUTO_ACTIVATION_CHARACTERS;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.jface.text.contentassist.IContentAssistProcessor#getContextInformationAutoActivationCharacters()
	 */
	public char[] getContextInformationAutoActivationCharacters() {
		return null;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.jface.text.contentassist.IContentAssistProcessor#getContextInformationValidator()
	 */
	public IContextInformationValidator getContextInformationValidator() {
		return null;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.jface.text.contentassist.IContentAssistProcessor#getErrorMessage()
	 */
	public String getErrorMessage() {
		return null;
	}

}

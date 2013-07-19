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
package org.eclipse.acceleo.internal.ide.ui.editors.template;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.eclipse.acceleo.common.IAcceleoConstants;
import org.eclipse.acceleo.common.internal.utils.AcceleoPackageRegistry;
import org.eclipse.acceleo.common.internal.utils.LazyEPackageDescriptor;
import org.eclipse.acceleo.common.internal.utils.compatibility.AcceleoCompatibilityEclipseHelper;
import org.eclipse.acceleo.common.internal.utils.compatibility.AcceleoOCLReflection;
import org.eclipse.acceleo.common.internal.utils.compatibility.OCLVersion;
import org.eclipse.acceleo.common.utils.CompactHashSet;
import org.eclipse.acceleo.common.utils.CompactLinkedHashSet;
import org.eclipse.acceleo.common.utils.ModelUtils;
import org.eclipse.acceleo.ide.ui.AcceleoUIActivator;
import org.eclipse.acceleo.internal.ide.ui.editors.template.scanner.AcceleoPartitionScanner;
import org.eclipse.acceleo.internal.ide.ui.editors.template.utils.AcceleoUIDocumentationUtils;
import org.eclipse.acceleo.internal.ide.ui.editors.template.utils.IAcceleoContantsImage;
import org.eclipse.acceleo.internal.ide.ui.views.overrides.OverridesBrowser;
import org.eclipse.acceleo.internal.ide.ui.views.proposals.ProposalsBrowser;
import org.eclipse.acceleo.internal.parser.ast.ocl.environment.AcceleoEnvironment;
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
import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.Path;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EOperation;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EParameter;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.jface.text.ITextSelection;
import org.eclipse.jface.text.ITextViewer;
import org.eclipse.jface.text.Region;
import org.eclipse.jface.text.contentassist.ICompletionProposal;
import org.eclipse.jface.text.contentassist.IContentAssistProcessor;
import org.eclipse.jface.text.contentassist.IContextInformation;
import org.eclipse.jface.text.contentassist.IContextInformationValidator;
import org.eclipse.jface.text.templates.DocumentTemplateContext;
import org.eclipse.jface.text.templates.TemplateContext;
import org.eclipse.jface.text.templates.TemplateContextType;
import org.eclipse.ocl.expressions.CollectionKind;
import org.eclipse.ocl.helper.Choice;
import org.eclipse.swt.graphics.Image;
import org.eclipse.ui.IViewPart;
import org.eclipse.ui.IViewReference;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;

/**
 * A completion processor for the Acceleo template editor.
 * 
 * @author <a href="mailto:jonathan.musset@obeo.fr">Jonathan Musset</a>
 */
public class AcceleoCompletionProcessor implements IContentAssistProcessor {

	/** The auto activation characters for completion proposal. */
	private static final char[] AUTO_ACTIVATION_CHARACTERS;

	/**
	 * The current text viewer.
	 */
	protected ITextViewer textViewer;

	/**
	 * The source content.
	 */
	protected AcceleoSourceContent content;

	/**
	 * The text used to compute the proposals.
	 */
	protected String text;

	/**
	 * The default start for the completion proposal.
	 */
	private final String defaultStart = " ${"; //$NON-NLS-1$

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

	static {
		/*
		 * Assume the version of Eclipse is tied to the associated OCL Version. If this assumption is false,
		 * the user built his own version of OCL and he'll have to cope with content assist problems. We have
		 * to have the "space" char in the auto activation triggers as explicit calls to "ctrl+space" wouldn't
		 * trigger the completion popup otherwise before Galileo (3.5). In general though, having the content
		 * assistant triggered on whitespaces is useless and, in most cases, stupid. We then only activate
		 * this under Ganymede when it is mandatory.
		 */
		if (AcceleoCompatibilityEclipseHelper.getCurrentOCLVersion() == OCLVersion.GANYMEDE) {
			AUTO_ACTIVATION_CHARACTERS = new char[] {' ', '.', '[', '-', '>', ':' };
		} else {
			AUTO_ACTIVATION_CHARACTERS = new char[] {'.', '[', '-', '>', ':' };
		}
	}

	/**
	 * Constructor.
	 * 
	 * @param content
	 *            is the content
	 */
	public AcceleoCompletionProcessor(AcceleoSourceContent content) {
		super();
		this.content = content;
		this.defaultVariableType = "Type"; //$NON-NLS-1$
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.jface.text.contentassist.IContentAssistProcessor#computeCompletionProposals(org.eclipse.jface.text.ITextViewer,
	 *      int)
	 */
	public ICompletionProposal[] computeCompletionProposals(ITextViewer viewer, int pos) {
		textViewer = viewer;
		setText(viewer);
		if (viewer != null) {
			ITextSelection selection = (ITextSelection)viewer.getSelectionProvider().getSelection();
			if (selection != null && selection.getOffset() == pos) {
				setCompletionOffset(selection.getOffset() + selection.getLength());
			} else {
				setCompletionOffset(pos);
			}
		} else {
			setCompletionOffset(pos);
		}
		cstNode = content.getCSTNode(offset, offset);
		if (cstNode instanceof InitSection && offset == cstNode.getStartPosition()
				&& cstNode.eContainer() != null) {
			// We change the CSTNode if we are on the first index of an InitSection
			cstNode = (CSTNode)cstNode.eContainer();
		}
		try {
			return computeCompletionProposals();
		} finally {
			textViewer = null;
			text = null;
			setCompletionOffset(0);
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
		if (textViewer != null && cstNode instanceof TextExpression) {
			ITextSelection selection = (ITextSelection)textViewer.getSelectionProvider().getSelection();
			if (selection != null && selection.getLength() > 0) {
				Template template = (Template)content.getCSTParent(cstNode, Template.class);
				computeSelectionReplacementProposals(proposals, template, selection.getOffset(), selection
						.getOffset()
						+ selection.getLength());
			}
		}
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
				boolean addPatterns = false;
				if (proposals.size() == 0) {
					computeKeywordsProposals(proposals);
					if (!isNextSignificantChar(']')) {
						addPatterns = true;
					}
				}
				if (text.charAt(offset - 1) != ']') {
					if (cstNode instanceof ModelExpression || cstNode instanceof InitSection) {
						computeOCLProposals(proposals);
					} else if (cstNode instanceof Template
							&& text.substring(cstNode.getStartPosition(), offset).contains("]")) { //$NON-NLS-1$
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
				if (addPatterns) {
					computePatternsProposals(proposals);
				}
			}
		}
		computeProposalsBrowserView(proposals);
		if (cstNode instanceof Module) {
			computeOverridesBrowserView(proposals);
		}
		if (proposals.size() == 0 && textViewer != null && textViewer.getTextWidget() != null) {
			if (offset < text.length() && text.charAt(offset) == ']' && text.charAt(offset - 1) == '[') {
				textViewer.getTextWidget().replaceTextRange(offset, 0,
						IAcceleoConstants.DEFAULT_END_BODY_CHAR);
			}
		}
		return proposals.toArray(new ICompletionProposal[proposals.size()]);
	}

	/**
	 * This method is only called if the current text selection isn't empty. It creates a new completion
	 * proposal that contains the ending text of the current template, it means from the current offset to the
	 * ending index of the template. The text is modified by replacing everywhere the current selected text.
	 * An OCL expression can be created for each occurrence.
	 * 
	 * @param proposals
	 *            are the proposals, it's an input/output parameter
	 * @param template
	 *            is the current template, to delimit the modifications
	 * @param begin
	 *            is the beginning index of the selection
	 * @param end
	 *            is the ending index of the selection
	 */
	private void computeSelectionReplacementProposals(List<ICompletionProposal> proposals, Template template,
			int begin, int end) {
		if (template != null && template.getEndPosition() > -1 && template.getEndPosition() <= text.length()) {
			String stringToReplace = text.substring(begin, end);
			String stringToReplaceLower = stringToReplace.toLowerCase();
			String endingTextS = text.substring(begin, template.getEndPosition());
			StringBuffer endingText = new StringBuffer(endingTextS);
			StringBuffer endingTextLower = new StringBuffer(endingTextS.toLowerCase());
			int count = 0;
			int i = endingTextLower.indexOf(stringToReplaceLower);
			int shift = 0;
			while (i > -1 && i < endingText.length()) {
				int b = i;
				int e = b + stringToReplace.length();
				CSTNode newNode = content.getCSTNode(begin + b - shift, begin + b - shift);
				if (newNode instanceof TextExpression) {
					count++;
					String stringFound = endingText.substring(b, e);
					String replacementString;
					if (stringFound.length() > 1 && stringFound.equals(stringFound.toUpperCase())) {
						replacementString = "[${name}.toUpper()/]"; //$NON-NLS-1$
					} else if (Character.isUpperCase(stringFound.charAt(0))) {
						replacementString = "[${name}.toUpperFirst()/]"; //$NON-NLS-1$
					} else {
						replacementString = "[${name}/]"; //$NON-NLS-1$
					}
					endingText.replace(b, e, replacementString);
					endingTextLower.replace(b, e, replacementString);
					shift += replacementString.length() - (e - b);
					i = endingTextLower.indexOf(stringToReplaceLower, b + replacementString.length());
				} else if (newNode == null) {
					i = -1;
				} else {
					i = endingTextLower.indexOf(stringToReplaceLower, e);
				}
			}
			Image image = AcceleoUIActivator.getDefault().getImage(
					"icons/template-editor/completion/RefactorTextSelection.gif"); //$NON-NLS-1$
			String label;
			if (count > 1) {
				label = "Replacing by [ ] - " + count + " times"; //$NON-NLS-1$ //$NON-NLS-2$
			} else {
				label = "Replacing by [ ]"; //$NON-NLS-1$
			}
			int last = endingText.length() - 1;
			for (i = last; i >= 0; i--) {
				char c = endingText.charAt(i);
				if (c == '$' && (i == last || endingText.charAt(i + 1) != '{')) {
					endingText.insert(i, '$');
				}
			}
			proposals.add(createTemplateProposal(endingText.toString(), begin, template.getEndPosition()
					- begin, begin, image, label, null, stringToReplace + " -> [name/]")); //$NON-NLS-1$
		}
	}

	/**
	 * Computes the proposals specified in the ProposalsBrowser view.
	 * 
	 * @param proposals
	 *            are the completion proposals (in out parameter)
	 */
	private void computeProposalsBrowserView(List<ICompletionProposal> proposals) {
		IWorkbenchWindow activeWindow = PlatformUI.getWorkbench().getActiveWorkbenchWindow();
		if (activeWindow == null || activeWindow.getActivePage() == null) {
			return;
		}
		IWorkbenchPage page = activeWindow.getActivePage();
		IViewReference[] references = page.getViewReferences();
		for (int i = 0; i < references.length; i++) {
			IViewReference viewReference = references[i];
			IViewPart view = viewReference.getView(false);
			if (view instanceof ProposalsBrowser && page.isPartVisible(view) && textViewer != null) {
				List<ICompletionProposal> advancedCompletionProposals = ((ProposalsBrowser)view)
						.getPatternCompletionProposals(textViewer.getDocument(), text, offset, cstNode);
				if (advancedCompletionProposals.size() > 0) {
					proposals.addAll(0, advancedCompletionProposals);
				}
			}
		}
	}

	/**
	 * Computes the 'overrides' proposals specified in the OverridesBrowser view.
	 * 
	 * @param proposals
	 *            are the completion proposals (in out parameter)
	 */
	private void computeOverridesBrowserView(List<ICompletionProposal> proposals) {
		IWorkbenchWindow activeWindow = PlatformUI.getWorkbench().getActiveWorkbenchWindow();
		if (activeWindow == null || activeWindow.getActivePage() == null) {
			return;
		}
		IWorkbenchPage page = activeWindow.getActivePage();
		IViewReference[] references = page.getViewReferences();
		for (int i = 0; i < references.length; i++) {
			IViewReference viewReference = references[i];
			IViewPart view = viewReference.getView(false);
			if (view instanceof OverridesBrowser && page.isPartVisible(view) && textViewer != null) {
				List<ICompletionProposal> advancedCompletionProposals = ((OverridesBrowser)view)
						.getExtendCompletionProposals(textViewer.getDocument(), text, offset);
				if (advancedCompletionProposals.size() > 0) {
					proposals.addAll(0, advancedCompletionProposals);
				}
			}
		}
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
		}
		return false;
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
		Set<String> duplicated = new CompactHashSet<String>();
		for (Choice next : choices) {
			String replacementString = next.getName();
			if (replacementString.toLowerCase().startsWith(start.toLowerCase())) {
				switch (next.getKind()) {
					case OPERATION:
						if (next.getElement() instanceof EOperation) {
							Image image = AcceleoUIActivator.getDefault().getImage(
									IAcceleoContantsImage.TemplateEditor.Completion.TEMPLATE_PUBLIC);
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
								proposals.add(createCompletionProposal(replacementString, offset
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
		Set<String> duplicated = new CompactHashSet<String>();
		for (Choice next : choices) {
			String choiceValue = next.getName();
			String replacement = getReplacementStringFor(choiceValue);
			if (choiceValue.toLowerCase().startsWith(start.toLowerCase())
					|| replacement.toLowerCase().startsWith(start.toLowerCase())) {
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
							// TODO NLS This should probably be externalized
							descriptionPropertyBuilder.append("\n\t defined on "); //$NON-NLS-1$
							descriptionPropertyBuilder.append(((EStructuralFeature)next.getElement())
									.getEContainingClass().getName());
							descriptionProperty = descriptionPropertyBuilder.toString();
						} else {
							descriptionProperty = displayProperty;
						}
						if (!duplicated.contains(displayProperty)) {
							duplicated.add(displayProperty);
							proposals.add(createCompletionProposal(replacement, offset - start.length(),
									start.length(), replacement.length(), AcceleoUIActivator.getDefault()
											.getImage("icons/template-editor/completion/Property.gif"), //$NON-NLS-1$
									displayProperty, null, descriptionProperty));
						}
						break;
					case ENUMERATION_LITERAL:
						if (!duplicated.contains(choiceValue)) {
							duplicated.add(choiceValue);
							proposals.add(createCompletionProposal(replacement, offset - start.length(),
									start.length(), replacement.length(), AcceleoUIActivator.getDefault()
											.getImage("icons/template-editor/completion/EnumLiteral.gif"), //$NON-NLS-1$
									choiceValue, null, next.getDescription()));
						}
						break;
					case VARIABLE:
						String displayVariable;
						String description;
						if (IAcceleoConstants.SELF.equals(choiceValue) || next.getDescription() == null) {
							displayVariable = choiceValue;
							description = ""; //$NON-NLS-1$
						} else {
							displayVariable = choiceValue + ":" + next.getDescription(); //$NON-NLS-1$
							description = next.getDescription();
						}
						if (!duplicated.contains(displayVariable)) {
							duplicated.add(displayVariable);
							proposals.add(createCompletionProposal(replacement, offset - start.length(),
									start.length(), replacement.length(), AcceleoUIActivator.getDefault()
											.getImage("icons/template-editor/completion/Variable.gif"), //$NON-NLS-1$
									displayVariable, null, description));
						}
						break;
					default:
						break;
				}
			}
		}
	}

	/**
	 * This will be in charge of disambigating conflicts between feature/operations and OCL reserved keywords.
	 * Specifically, we'll prefix any reserved keyword with an underscore.
	 * 
	 * @param choiceValue
	 *            String that is to be replaced.
	 * @return The replacement String that's to be used instead of <em>choiceValue</em> if it is a reserved
	 *         keyword, <em>choiceValue</em> otherwise.
	 */
	private String getReplacementStringFor(String choiceValue) {
		if (content.getOCLEnvironment() instanceof AcceleoEnvironment) {
			if (AcceleoOCLReflection.getReservedKeywords().contains(choiceValue)) {
				return '_' + choiceValue;
			}
		}
		return choiceValue;
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
		String description = ""; //$NON-NLS-1$
		Image image;
		if (nextOperationChoice instanceof AcceleoCompletionChoice) {
			AcceleoCompletionChoice acceleoCompletionChoice = (AcceleoCompletionChoice)nextOperationChoice;
			org.eclipse.acceleo.model.mtl.ModuleElement acceleoElement = acceleoCompletionChoice
					.getAcceleoElement();

			image = this.computeImage(acceleoElement);
			description = this.computeDescription(acceleoElement);
		} else {
			image = AcceleoUIActivator.getDefault().getImage(
					IAcceleoContantsImage.TemplateEditor.Completion.OPERATION);
		}
		if (nextOperationChoice.getElement() instanceof EOperation) {
			EOperation eOperation = (EOperation)nextOperationChoice.getElement();
			String replacementStringWithArgsBefore = getReplacementStringFor(eOperation.getName())
					+ IAcceleoConstants.PARENTHESIS_BEGIN;
			String replacementStringWithArgsAfter = ""; //$NON-NLS-1$
			Iterator<EParameter> eParametersIt = eOperation.getEParameters().iterator();
			while (eParametersIt.hasNext()) {
				EParameter eParameter = eParametersIt.next();
				replacementStringWithArgsAfter += "${" + eParameter.getName() + "}"; //$NON-NLS-1$ //$NON-NLS-2$
				if (eParametersIt.hasNext()) {
					replacementStringWithArgsAfter += ", "; //$NON-NLS-1$
				}
			}
			replacementStringWithArgsAfter += ')';
			if (description.length() == 0) {
				description = AcceleoUIDocumentationUtils.getTextFrom(eOperation);
			}
			if (description.length() == 0) {
				if (eOperation.getEContainingClass() != null) {
					description = "\n" + eOperation.getEContainingClass().getName() + "." //$NON-NLS-1$ //$NON-NLS-2$
							+ nextOperationChoice.getDescription();
				} else {
					description = "\n" + nextOperationChoice.getDescription(); //$NON-NLS-1$
				}
			}
			if (!duplicated.contains(nextOperationChoice.getDescription())) {
				duplicated.add(nextOperationChoice.getDescription());
				proposals.add(createTemplateProposal(replacementStringWithArgsBefore
						+ replacementStringWithArgsAfter, offset - start.length(), start.length(),
						replacementStringWithArgsBefore.length(), image,
						nextOperationChoice.getDescription(), null, description));
			}
		} else {
			if (!duplicated.contains(nextOperationChoice.getDescription())) {
				duplicated.add(nextOperationChoice.getDescription());
				String replacementString = nextOperationChoice.getName();
				if (replacementString.contains("(")) { //$NON-NLS-1$
					replacementString = getReplacementStringFor(replacementString.substring(0,
							replacementString.indexOf('(')));
				}
				proposals.add(createCompletionProposal(replacementString, offset - start.length(), start
						.length(), replacementString.length(), image, nextOperationChoice.getDescription(),
						null, nextOperationChoice.getDescription() + description));
			}
		}
	}

	/**
	 * Computes the description of the module element.
	 * 
	 * @param acceleoElement
	 *            The module element
	 * @return the description of the module element
	 */
	private String computeDescription(org.eclipse.acceleo.model.mtl.ModuleElement acceleoElement) {
		String description = ""; //$NON-NLS-1$
		if (acceleoElement instanceof org.eclipse.acceleo.model.mtl.Template) {
			org.eclipse.acceleo.model.mtl.Template template = (org.eclipse.acceleo.model.mtl.Template)acceleoElement;
			description = AcceleoUIDocumentationUtils.getDocumentation(template);
		} else if (acceleoElement instanceof org.eclipse.acceleo.model.mtl.Query) {
			org.eclipse.acceleo.model.mtl.Query query = (org.eclipse.acceleo.model.mtl.Query)acceleoElement;
			description = AcceleoUIDocumentationUtils.getDocumentation(query);
		} else if (acceleoElement instanceof org.eclipse.acceleo.model.mtl.Macro) {
			org.eclipse.acceleo.model.mtl.Macro macro = (org.eclipse.acceleo.model.mtl.Macro)acceleoElement;
			description = AcceleoUIDocumentationUtils.getDocumentation(macro);
		}
		return description;
	}

	/**
	 * Computes the image of the module element.
	 * 
	 * @param acceleoElement
	 *            The module element
	 * @return The image of the module element
	 */
	private Image computeImage(org.eclipse.acceleo.model.mtl.ModuleElement acceleoElement) {
		Image image = null;
		if (acceleoElement instanceof org.eclipse.acceleo.model.mtl.Template) {
			org.eclipse.acceleo.model.mtl.Template template = (org.eclipse.acceleo.model.mtl.Template)acceleoElement;
			image = AcceleoUIDocumentationUtils.getCompletionImage(template);
		} else if (acceleoElement instanceof org.eclipse.acceleo.model.mtl.Query) {
			org.eclipse.acceleo.model.mtl.Query query = (org.eclipse.acceleo.model.mtl.Query)acceleoElement;
			image = AcceleoUIDocumentationUtils.getCompletionImage(query);
		} else if (acceleoElement instanceof org.eclipse.acceleo.model.mtl.Macro) {
			image = AcceleoUIActivator.getDefault().getImage(
					IAcceleoContantsImage.TemplateEditor.Completion.MACRO_PUBLIC);
		} else {
			image = AcceleoUIActivator.getDefault().getImage(
					IAcceleoContantsImage.TemplateEditor.Completion.OPERATION);
		}
		return image;
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
			String displayString = new Path(uri.lastSegment()).removeFileExtension().lastSegment();
			if (displayString.toLowerCase().startsWith(start.toLowerCase())) {
				proposals.add(new AcceleoCompletionImportProposal(uri, offset - start.length(), start
						.length(), AcceleoUIActivator.getDefault().getImage(
						IAcceleoContantsImage.TemplateEditor.Completion.MODULE), displayString));
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
		final String uriImagePath = IAcceleoContantsImage.TemplateEditor.Completion.URI;
		int i = offset;
		while (i > 0 && text.charAt(i - 1) != '(' && text.charAt(i - 1) != ',' && text.charAt(i - 1) != '\'') {
			i--;
		}
		String start = text.substring(i, offset);
		Iterator<String> entries = new CompactLinkedHashSet<String>(AcceleoPackageRegistry.INSTANCE.keySet())
				.iterator();
		while (entries.hasNext()) {
			String pURI = entries.next();
			if (pURI.toLowerCase().startsWith(start.toLowerCase())) {
				proposals.add(createCompletionProposal(pURI, offset - start.length(), start.length(), pURI
						.length(), AcceleoUIActivator.getDefault().getImage(uriImagePath), pURI, null, pURI));
			}
		}
		if (start.length() > 0) {
			entries = new CompactLinkedHashSet<String>(AcceleoPackageRegistry.INSTANCE.keySet()).iterator();
			while (entries.hasNext()) {
				String pURI = entries.next();
				Object obj = ModelUtils.getEPackageOrDescriptor(pURI);
				String shortName = ""; //$NON-NLS-1$
				if (obj instanceof LazyEPackageDescriptor) {
					shortName = ((LazyEPackageDescriptor)obj).getName();
				} else if (obj instanceof EPackage) {
					shortName = ((EPackage)obj).getName();
				} else {
					EPackage resolved = ModelUtils.getEPackage(pURI);
					if (resolved != null) {
						shortName = resolved.getName();
					}
				}
				if (shortName.startsWith(start.toLowerCase())) {
					proposals.add(createCompletionProposal(pURI, offset - start.length(), start.length(),
							pURI.length(), AcceleoUIActivator.getDefault().getImage(uriImagePath), pURI,
							null, pURI));
				}
			}
		}
		List<IFile> ecoreFiles = new ArrayList<IFile>();
		try {
			computeEcoreFiles(ecoreFiles, ResourcesPlugin.getWorkspace().getRoot());
			for (IFile ecoreFile : ecoreFiles) {
				String ecorePath = ecoreFile.getFullPath().toString();
				if (ecorePath.toLowerCase().startsWith(start.toLowerCase())) {
					proposals.add(createCompletionProposal(ecorePath, offset - start.length(),
							start.length(), ecorePath.length(), AcceleoUIActivator.getDefault().getImage(
									uriImagePath), ecorePath, null, ecorePath));
				}
				if (start.length() > 0) {
					String shortName = new Path(ecorePath).removeFileExtension().lastSegment();
					if (shortName.startsWith(start.toLowerCase())) {
						proposals.add(createCompletionProposal(ecorePath, offset - start.length(), start
								.length(), ecorePath.length(), AcceleoUIActivator.getDefault().getImage(
								uriImagePath), ecorePath, null, ecorePath));
					}
				}
			}
		} catch (CoreException e) {
			AcceleoUIActivator.getDefault().getLog().log(e.getStatus());
		}
		Collections.sort(proposals, new Comparator<ICompletionProposal>() {
			public int compare(ICompletionProposal o1, ICompletionProposal o2) {
				// All completion proposal have the actual package URI as their display name
				return o1.getDisplayString().compareTo(o2.getDisplayString());
			}
		});
	}

	/**
	 * Returns a list of existing ecore files in the workspace.
	 * 
	 * @param ecoreFiles
	 *            an output parameter to get all the ecore files
	 * @param container
	 *            is the container to browse
	 * @throws CoreException
	 *             contains a status object describing the cause of the exception
	 */
	private void computeEcoreFiles(List<IFile> ecoreFiles, IContainer container) throws CoreException {
		if (container != null) {
			IResource[] children;
			if (container instanceof IWorkspaceRoot) {
				children = ((IWorkspaceRoot)container).getProjects();
			} else {
				children = container.members();
			}
			if (children != null) {
				for (int i = 0; i < children.length; ++i) {
					IResource resource = children[i];
					if (resource instanceof IFile && "ecore".equals(((IFile)resource).getFileExtension())) { //$NON-NLS-1$
						ecoreFiles.add((IFile)resource);
					} else if (resource instanceof IContainer && resource.isAccessible()) {
						computeEcoreFiles(ecoreFiles, (IContainer)resource);
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
		if (content.getCST() == null) {
			return;
		}
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
					String name = eClassifier.getName();
					if (name.endsWith(")")) { //$NON-NLS-1$
						name = name.replaceAll("\\(", "(\\${"); //$NON-NLS-1$ //$NON-NLS-2$
						name = name.replaceAll("\\)", "})"); //$NON-NLS-1$ //$NON-NLS-2$
						proposals.add(createTemplateProposal(name, offset - start.length(), start.length(),
								name.length(), AcceleoUIActivator.getDefault().getImage(
										IAcceleoContantsImage.TemplateEditor.Completion.TYPE), eClassifier
										.getName(), null, name));
					} else {
						proposals.add(createCompletionProposal(eClassifier.getName(),
								offset - start.length(), start.length(), eClassifier.getName().length(),
								AcceleoUIActivator.getDefault().getImage(
										"icons/template-editor/completion/Type.gif"), eClassifier.getName(), //$NON-NLS-1$
								null, eClassifier.getName()));
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
				String replacementStringAfter = "${ecore}') /]\n" + tabBuffer.toString(); //$NON-NLS-1$
				String replacementString = replacementStringBefore + replacementStringAfter;
				proposals.add(createTemplateProposal(replacementString, offset - start.length(), start
						.length(), replacementStringBefore.length(), AcceleoUIActivator.getDefault()
						.getImage(IAcceleoContantsImage.TemplateEditor.Completion.PATTERN),
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
				IAcceleoContantsImage.TemplateEditor.Completion.PATTERN);
		if (i > 0 && text.charAt(i - 1) == '\n') {
			// We are not interested by the first line
			if (content.getCSTParent(cstNode, org.eclipse.acceleo.parser.cst.ModuleElement.class) == null
					&& text.substring(0, i).indexOf('[' + IAcceleoConstants.TEMPLATE) == -1
					&& text.substring(0, i).indexOf('[' + IAcceleoConstants.QUERY) == -1
					&& text.substring(0, i).indexOf('[' + IAcceleoConstants.MACRO) == -1) {
				if (IAcceleoConstants.IMPORT.startsWith(start.toLowerCase())
						|| ('[' + IAcceleoConstants.IMPORT).startsWith(start.toLowerCase())) {
					String replacementStringBefore = '[' + IAcceleoConstants.IMPORT + ' ';
					String replacementStringAfter = "${common} /]" + tab; //$NON-NLS-1$
					String replacementString = replacementStringBefore + replacementStringAfter;
					proposals.add(createTemplateProposal(replacementString, offset - start.length(), start
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
					String replacementStringAfter = "${expression})]" + '\n' + tab + '\t'; //$NON-NLS-1$
					String replacementString = replacementStringBefore + replacementStringAfter;
					proposals.add(createTemplateProposal(replacementString, offset - start.length(), start
							.length(), replacementStringBefore.length(), patternImage,
							'[' + IAcceleoConstants.ELSE_IF + ']', null, replacementString));
				}
				if (IAcceleoConstants.ELSE.startsWith(start.toLowerCase())
						|| ('[' + IAcceleoConstants.ELSE).startsWith(start.toLowerCase())) {
					String replacementString = '[' + IAcceleoConstants.ELSE + ']' + '\n' + tab + '\t';
					proposals.add(createCompletionProposal(replacementString, offset - start.length(), start
							.length(), replacementString.length(), patternImage,
							'[' + IAcceleoConstants.ELSE + ']', null, replacementString));
				}
			}
			if (cstNode instanceof LetBlock
					|| (cstNode instanceof TextExpression && cstNode.eContainer() instanceof LetBlock)) {
				if (IAcceleoConstants.ELSE_LET.startsWith(start.toLowerCase())
						|| ('[' + IAcceleoConstants.ELSE_LET).startsWith(start.toLowerCase())) {
					String replacementStringBefore = '[' + IAcceleoConstants.ELSE_LET + ' ';
					String replacementStringAfter = "${var} " + ":" + defaultStart + defaultVariableType + "} = ${self}]" + '\n' //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ 
							+ tab + '\t';
					String replacementString = replacementStringBefore + replacementStringAfter;
					proposals.add(createTemplateProposal(replacementString, offset - start.length(), start
							.length(), replacementStringBefore.length(), patternImage,
							'[' + IAcceleoConstants.ELSE_LET + ']', null, replacementString));
				}
				if (IAcceleoConstants.ELSE.startsWith(start.toLowerCase())
						|| ('[' + IAcceleoConstants.ELSE).startsWith(start.toLowerCase())) {
					String replacementString = '[' + IAcceleoConstants.ELSE + ']' + '\n' + tab + '\t';
					proposals.add(createCompletionProposal(replacementString, offset - start.length(), start
							.length(), replacementString.length(), patternImage,
							'[' + IAcceleoConstants.ELSE + ']', null, replacementString));
				}
			}
			if (!start.startsWith("@")) { //$NON-NLS-1$
				computeBlocksPatternsProposals(proposals, start, tab, patternImage);
			}
			if (!(cstNode instanceof ModelExpression)
					&& (IAcceleoConstants.COMMENT.startsWith(start.toLowerCase()) || ('[' + IAcceleoConstants.COMMENT)
							.startsWith(start.toLowerCase()))) {
				String replacementStringBefore = '[' + IAcceleoConstants.COMMENT + ' ';
				String replacementStringAfter = " /]\n" + tab; //$NON-NLS-1$
				String replacementString = replacementStringBefore + replacementStringAfter;
				proposals.add(createCompletionProposal(replacementString, offset - start.length(), start
						.length(), replacementStringBefore.length(), patternImage,
						'[' + IAcceleoConstants.COMMENT + ']', null, replacementString));
			}
		} else {
			if (!start.startsWith("@")) { //$NON-NLS-1$
				computeBlocksPatternsProposals(proposals, start, tab, patternImage);
			}
		}
		computeMainTagPatternsProposals(proposals, start, tab, patternImage);
	}

	/**
	 * Computes a list of patterns for the main annotation (@main).
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
				proposals.add(createCompletionProposal(replacementString, offset - start.length(), start
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
			String replacementStringBefore = '[' + IAcceleoConstants.TEMPLATE + defaultStart
					+ IAcceleoConstants.VISIBILITY_KIND_PUBLIC + "} "; //$NON-NLS-1$
			String replacementStringAfter = "${name}(" + "${arg}" + " : ${" + defaultVariableType + "})]\n" + tab //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$
					+ '\t'
					+ "${template_expression}" + '\n' + tab + '[' + '/' + IAcceleoConstants.TEMPLATE + ']'; //$NON-NLS-1$
			String replacementString = replacementStringBefore + replacementStringAfter;
			proposals.add(createTemplateProposal(replacementString, offset - start.length(), start.length(),
					replacementStringBefore.length(), patternImage, '[' + IAcceleoConstants.TEMPLATE + ']',
					null, tab + replacementString));
		}
		if (IAcceleoConstants.QUERY.startsWith(start.toLowerCase())
				|| ('[' + IAcceleoConstants.QUERY).startsWith(start.toLowerCase())) {
			String replacementStringBefore = '[' + IAcceleoConstants.QUERY + defaultStart
					+ IAcceleoConstants.VISIBILITY_KIND_PUBLIC + "} "; //$NON-NLS-1$
			String replacementStringAfter = "${name}(${arg} : ${" + defaultVariableType + "}) : ${OclAny} = ${self} /]\n"; //$NON-NLS-1$ //$NON-NLS-2$
			String replacementString = replacementStringBefore + replacementStringAfter;
			proposals.add(createTemplateProposal(replacementString, offset - start.length(), start.length(),
					replacementStringBefore.length(), patternImage, '[' + IAcceleoConstants.QUERY + ']',
					null, tab + replacementString));
		}
		if (IAcceleoConstants.MACRO.startsWith(start.toLowerCase())
				|| ('[' + IAcceleoConstants.MACRO).startsWith(start.toLowerCase())) {
			String replacementStringBefore = '[' + IAcceleoConstants.MACRO + ' '
					+ IAcceleoConstants.VISIBILITY_KIND_PUBLIC + ' ';
			String replacementStringAfter = "${name}(${arg} : ${" + defaultVariableType + "}) : ${String}" //$NON-NLS-1$ //$NON-NLS-2$
					+ "]\n" + tab + '\t' + '\n' + tab + '[' + '/' + IAcceleoConstants.MACRO //$NON-NLS-1$
					+ ']';
			String replacementString = replacementStringBefore + replacementStringAfter;
			proposals.add(createTemplateProposal(replacementString, offset - start.length(), start.length(),
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
		final String cursor = "${block_expression}"; //$NON-NLS-1$
		if (IAcceleoConstants.DEFAULT_BEGIN.startsWith(start.toLowerCase())) {
			String replacementStringBefore = "[${expression}"; //$NON-NLS-1$
			String replacementStringAfter = "/]"; //$NON-NLS-1$
			String replacementString = replacementStringBefore + replacementStringAfter;
			proposals.add(createTemplateProposal(replacementString, offset - start.length(), start.length(),
					replacementStringBefore.length(), patternImage, "[ ]", null, replacementString)); //$NON-NLS-1$
		}
		if (IAcceleoConstants.FOR.startsWith(start.toLowerCase())
				|| ('[' + IAcceleoConstants.FOR).startsWith(start.toLowerCase())) {
			String virtualText = getVirtualTextInBlock();
			String replacementStringBefore = '[' + IAcceleoConstants.FOR + ' ' + '(';
			String replacementStringAfter;
			if (virtualText.length() > 0) {
				replacementStringAfter = "${it} : ${" + defaultVariableType + "} | ${expression})]\n" + //$NON-NLS-1$ //$NON-NLS-2$
						tab + virtualText + tab + '[' + '/' + IAcceleoConstants.FOR + ']' + '\n';
			} else {
				replacementStringAfter = "${it} : ${" + defaultVariableType + "} | ${expression})]\n" + tab + '\t' //$NON-NLS-1$ //$NON-NLS-2$
						+ cursor + '\n' + tab + '[' + '/' + IAcceleoConstants.FOR + ']';
			}
			String replacementString = replacementStringBefore + replacementStringAfter;
			proposals.add(createTemplateProposal(replacementString, offset - start.length(), start.length()
					+ virtualText.length(), replacementStringBefore.length(), patternImage,
					'[' + IAcceleoConstants.FOR + ']', null, tab + replacementString));
		}
		if (IAcceleoConstants.IF.startsWith(start.toLowerCase())
				|| ('[' + IAcceleoConstants.IF).startsWith(start.toLowerCase())) {
			String virtualText = getVirtualTextInBlock();
			String replacementStringBefore = '[' + IAcceleoConstants.IF + ' ' + '(';
			String replacementStringAfter;
			if (virtualText.length() > 0) {
				replacementStringAfter = "${condition})]\n" + tab + virtualText + tab + '[' //$NON-NLS-1$
						+ '/' + IAcceleoConstants.IF + ']' + '\n';
			} else {
				replacementStringAfter = "${condition})]\n" + tab + '\t' + cursor + '\n' + tab + '[' //$NON-NLS-1$
						+ '/' + IAcceleoConstants.IF + ']';
			}
			String replacementString = replacementStringBefore + replacementStringAfter;
			proposals.add(createTemplateProposal(replacementString, offset - start.length(), start.length()
					+ virtualText.length(), replacementStringBefore.length(), patternImage,
					'[' + IAcceleoConstants.IF + ']', null, tab + replacementString));
		}
		if (IAcceleoConstants.FILE.startsWith(start.toLowerCase())
				|| ('[' + IAcceleoConstants.FILE).startsWith(start.toLowerCase())) {
			computeFileBlockPatternsProposals(proposals, start, tab, patternImage, true);
			computeFileBlockPatternsProposals(proposals, start, tab, patternImage, false);
		}
		if (IAcceleoConstants.LET.startsWith(start.toLowerCase())
				|| ('[' + IAcceleoConstants.LET).startsWith(start.toLowerCase())) {
			String replacementStringBefore = '[' + IAcceleoConstants.LET + ' ';
			String replacementStringAfter = "${var}" + " : ${" + defaultVariableType + "} = ${self}]\n" + tab + '\t' + cursor //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
					+ '\n' + tab + '[' + '/' + IAcceleoConstants.LET + ']';
			String replacementString = replacementStringBefore + replacementStringAfter;
			proposals.add(createTemplateProposal(replacementString, offset - start.length(), start.length(),
					replacementStringBefore.length(), patternImage, '[' + IAcceleoConstants.LET + ']', null,
					tab + replacementString));
		}
		if (IAcceleoConstants.TRACE.startsWith(start.toLowerCase())
				|| ('[' + IAcceleoConstants.TRACE).startsWith(start.toLowerCase())) {
			String replacementStringBefore = '[' + IAcceleoConstants.TRACE + " ('"; //$NON-NLS-1$
			String replacementStringAfter = "${message}')]\n" + tab + '\t' + cursor + '\n' + tab + '[' //$NON-NLS-1$
					+ '/' + IAcceleoConstants.TRACE + ']';
			String replacementString = replacementStringBefore + replacementStringAfter;
			proposals.add(createTemplateProposal(replacementString, offset - start.length(), start.length(),
					replacementStringBefore.length(), patternImage, '[' + IAcceleoConstants.TRACE + ']',
					null, tab + replacementString));
		}
		if (IAcceleoConstants.PROTECTED_AREA.startsWith(start.toLowerCase())
				|| ('[' + IAcceleoConstants.PROTECTED_AREA).startsWith(start.toLowerCase())) {
			String replacementStringBefore = '[' + IAcceleoConstants.PROTECTED_AREA + " ('"; //$NON-NLS-1$
			String replacementStringAfter = "${protected}')]\n" + tab + '\t' + cursor + '\n' + tab + '[' //$NON-NLS-1$
					+ '/' + IAcceleoConstants.PROTECTED_AREA + ']';
			String replacementString = replacementStringBefore + replacementStringAfter;
			proposals.add(createTemplateProposal(replacementString, offset - start.length(), start.length(),
					replacementStringBefore.length(), patternImage,
					'[' + IAcceleoConstants.PROTECTED_AREA + ']', null, tab + replacementString));
		}
		if (IAcceleoConstants.SUPER.startsWith(start.toLowerCase())
				|| ('[' + IAcceleoConstants.SUPER).startsWith(start.toLowerCase())) {
			String replacementStringBefore = '[' + IAcceleoConstants.SUPER + "/]\n" + tab; //$NON-NLS-1$
			String replacementString = replacementStringBefore;
			proposals.add(createCompletionProposal(replacementString, offset - start.length(),
					start.length(), replacementStringBefore.length(), patternImage,
					'[' + IAcceleoConstants.SUPER + ']', null, replacementString));
		}
		if (IAcceleoConstants.DEFAULT_BEGIN.startsWith(start.toLowerCase())) {
			String replacementString = "[ '[' /] "; //$NON-NLS-1$
			proposals
					.add(createCompletionProposal(replacementString, offset - start.length(), start.length(),
							replacementString.length(), patternImage, "'['", null, replacementString)); //$NON-NLS-1$
		}
		if ("".equals(start.toLowerCase())) { //$NON-NLS-1$
			String replacementString = "[ ']' /] "; //$NON-NLS-1$
			proposals
					.add(createCompletionProposal(replacementString, offset - start.length(), start.length(),
							replacementString.length(), patternImage, "']'", null, replacementString)); //$NON-NLS-1$
		}
	}

	/**
	 * Gets the example text in the block to create. If the current line contains a significant character,
	 * we'll try to embed the following text in the new block.
	 * 
	 * @return the content of the new block
	 */
	private String getVirtualTextInBlock() {
		boolean lineContainsTextAfter = false;
		int i = offset;
		while (i < text.length()) {
			char c = text.charAt(i);
			if (!Character.isWhitespace(c)) {
				lineContainsTextAfter = true;
				break;
			} else if (c == '\n') {
				break;
			} else {
				i++;
			}
		}
		if (lineContainsTextAfter) {
			i = offset;
			int iEmptyLineAndNoMTL = -1;
			int iBeginLine = i;
			boolean currentLineIsEmpty = false;
			while (i < text.length()) {
				char c = text.charAt(i);
				if (!Character.isWhitespace(c)) {
					if (currentLineIsEmpty && c == '[') {
						iEmptyLineAndNoMTL = iBeginLine;
						break;
					}
					currentLineIsEmpty = false;
				} else if (c == '\n') {
					if (currentLineIsEmpty) {
						iEmptyLineAndNoMTL = i + 1;
						break;
					}
					currentLineIsEmpty = true;
					iBeginLine = i + 1;
				}
				i++;
			}
			if (iEmptyLineAndNoMTL > -1) {
				return text.substring(offset, iEmptyLineAndNoMTL).replace("$", "$$"); //$NON-NLS-1$ //$NON-NLS-2$
			}
		}
		return ""; //$NON-NLS-1$
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
	 *            indicates if a main annotation (@main) must be added before the file block
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
		String replacementStringBefore = mainTagText + '[' + IAcceleoConstants.FILE + ' ' + "(${path}"; //$NON-NLS-1$
		String defaultEncoding;
		try {
			if (content != null && content.getFile() != null) {
				defaultEncoding = content.getFile().getCharset();
			} else {
				defaultEncoding = System.getProperty("file.encoding"); //$NON-NLS-1$
			}
		} catch (CoreException e) {
			AcceleoUIActivator.log(e, true);
			defaultEncoding = System.getProperty("file.encoding"); //$NON-NLS-1$
		}
		String replacementStringAfter = ", ${false}, '" + defaultEncoding //$NON-NLS-1$
				+ "')]\n" + tab + '\t' + "${file_expression}" + '\n' + tab + '[' + '/' //$NON-NLS-1$ //$NON-NLS-2$
				+ IAcceleoConstants.FILE + ']';
		String replacementString = replacementStringBefore + replacementStringAfter;
		String displayString = '[' + IAcceleoConstants.FILE + ']';
		if (withMainTag) {
			displayString += " - " + IAcceleoConstants.TAG_MAIN; //$NON-NLS-1$
		}
		proposals
				.add(createTemplateProposal(replacementString, offset - start.length(), start.length(),
						replacementStringBefore.length(), patternImage, displayString, null, tab
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
					IAcceleoContantsImage.TemplateEditor.Completion.KEYWORD);
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
			// Remark : The completion proposals have to be ordered : overrides -> guard -> post...
			StringBuffer bHeaderBuffer = new StringBuffer(bHeaderText);
			Sequence pGuard = new Sequence(IAcceleoConstants.GUARD, IAcceleoConstants.PARENTHESIS_BEGIN);
			Sequence pPost = new Sequence(IAcceleoConstants.POST, IAcceleoConstants.PARENTHESIS_BEGIN);
			Sequence pInit = new Sequence(IAcceleoConstants.BRACKETS_BEGIN);
			org.eclipse.acceleo.parser.cst.Template cstTemplate = (org.eclipse.acceleo.parser.cst.Template)cstNode;
			if (cstTemplate.getOverrides().size() == 0 && pGuard.search(bHeaderBuffer).b() == -1
					&& pPost.search(bHeaderBuffer).b() == -1 && pInit.search(bHeaderBuffer).b() == -1) {
				computeKeywordProposal(proposals, start, IAcceleoConstants.OVERRIDES + ' ', "", keywordImage); //$NON-NLS-1$
			}
			boolean isAfterOverrides = cstTemplate.getOverrides().size() == 0
					|| cstTemplate.getOverrides().get(cstTemplate.getOverrides().size() - 1).getEndPosition() < offset;
			if (cstTemplate.getGuard() == null && pPost.search(bHeaderBuffer).b() == -1
					&& pInit.search(bHeaderBuffer).b() == -1) {
				if (isAfterOverrides) {
					computeKeywordProposal(proposals, start, IAcceleoConstants.GUARD + ' '
							+ IAcceleoConstants.PARENTHESIS_BEGIN, IAcceleoConstants.PARENTHESIS_END,
							keywordImage);
				}
			}
			boolean isAfterGuard = cstTemplate.getGuard() == null
					|| cstTemplate.getGuard().getEndPosition() < offset;
			if (cstTemplate.getPost() == null && pInit.search(bHeaderBuffer).b() == -1) {
				if (isAfterOverrides && isAfterGuard) {
					computeKeywordProposal(proposals, start, IAcceleoConstants.POST + ' '
							+ IAcceleoConstants.PARENTHESIS_BEGIN, IAcceleoConstants.PARENTHESIS_END,
							keywordImage);
				}
			}
			boolean isAfterPost = cstTemplate.getPost() == null
					|| cstTemplate.getPost().getEndPosition() < offset;
			if (cstTemplate.getInit() == null && "{".startsWith(start.toLowerCase())) { //$NON-NLS-1$
				if (isAfterOverrides && isAfterGuard && isAfterPost) {
					String replacementStringBefore = "{ "; //$NON-NLS-1$
					String replacementStringAfter = "${var} : ${" + defaultVariableType + "}; }"; //$NON-NLS-1$ //$NON-NLS-2$
					String replacementString = replacementStringBefore + replacementStringAfter;
					proposals.add(createTemplateProposal(replacementString, offset - start.length(), start
							.length(), replacementStringBefore.length(), keywordImage, "{ }", null, //$NON-NLS-1$
							replacementString));
				}
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
				String replacementStringAfter = "${var} : ${" + defaultVariableType + "}; }"; //$NON-NLS-1$ //$NON-NLS-2$
				String replacementString = replacementStringBefore + replacementStringAfter;
				proposals.add(createTemplateProposal(replacementString, offset - start.length(), start
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
		final String tag = " ---TAG--- "; //$NON-NLS-1$
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
			proposals.add(createCompletionProposal(replacementString, offset - start.length(),
					start.length(), replacementStringBefore.length(), keywordImage, replacementString, null,
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

	/**
	 * Creates a new template completion proposal. All fields are initialized based on the provided
	 * information. The replacement string contains the variables like in the following example : this.${name}
	 * = ${value}.
	 * 
	 * @param replacementString
	 *            the actual string to be inserted into the document, it contains the variables ${name}
	 * @param replacementOffset
	 *            the offset of the text to be replaced
	 * @param replacementLength
	 *            the length of the text to be replaced
	 * @param cursorPosition
	 *            the position of the cursor following the insert relative to replacementOffset
	 * @param image
	 *            the image to display for this proposal
	 * @param displayString
	 *            the string to be displayed for the proposal
	 * @param contextInformation
	 *            the context information associated with this proposal
	 * @param additionalProposalInfo
	 *            the additional information associated with this proposal
	 */
	// CHECKSTYLE:OFF (8 parameters)
	protected ICompletionProposal createTemplateProposal(String replacementString, int replacementOffset,
			int replacementLength, int cursorPosition, Image image, String displayString,
			IContextInformation contextInformation, String additionalProposalInfo) {
		String info = additionalProposalInfo;

		if (textViewer != null && textViewer.getDocument() != null) {
			org.eclipse.jface.text.templates.Template template = new org.eclipse.jface.text.templates.Template(
					displayString, displayString, AcceleoPartitionScanner.ACCELEO_BLOCK, replacementString,
					true);
			TemplateContextType type = new TemplateContextType(AcceleoPartitionScanner.ACCELEO_BLOCK,
					AcceleoPartitionScanner.ACCELEO_BLOCK);
			TemplateContext context = new DocumentTemplateContext(type, textViewer.getDocument(),
					replacementOffset, replacementLength);
			Region region = new Region(replacementOffset, replacementLength);
			return new AcceleoCompletionTemplateProposal(template, context, region, image, info);
		}
		return createCompletionProposal(replacementString, replacementOffset, replacementLength,
				cursorPosition, image, displayString, contextInformation, info);
	}

	/**
	 * Creates an instance of {@link CompletionProposal} given all of the mandatory information.
	 * 
	 * @param replacementString
	 *            the actual string to be inserted into the document.
	 * @param replacementOffset
	 *            the offset of the text to be replaced.
	 * @param replacementLength
	 *            the length of the text to be replaced.
	 * @param cursorPosition
	 *            the position of the cursor following the insert relative to replacementOffset.
	 * @param image
	 *            the image to display for this proposal.
	 * @param displayString
	 *            the string to be displayed for the proposal.
	 * @param contextInformation
	 *            the context information associated with this proposal.
	 * @param additionalProposalInfo
	 *            the additional information associated with this proposal.
	 * @return The created completion proposal instance.
	 */
	protected ICompletionProposal createCompletionProposal(String replacementString, int replacementOffset,
			int replacementLength, int cursorPosition, Image image, String displayString,
			IContextInformation contextInformation, String additionalProposalInfo) {
		return new AcceleoCompletionProposal(replacementString, replacementOffset, replacementLength,
				cursorPosition, image, displayString, contextInformation, additionalProposalInfo);
	}

	// CHECKSTYLE:ON

	/**
	 * This will be used to write the "offset" field in order to alter the point in the text where completion
	 * is demanded.
	 * 
	 * @param newOffset
	 *            The new offset.
	 */
	protected void setCompletionOffset(int newOffset) {
		offset = newOffset;
	}

	/**
	 * This will be used to write the "text" field with the whole text for which completion is needed.
	 * 
	 * @param viewer
	 *            Viewer from which to retrieve the text.
	 */
	protected void setText(ITextViewer viewer) {
		if (viewer != null) {
			text = viewer.getDocument().get();
		} else {
			text = content.getText();
		}
	}

}

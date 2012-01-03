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
package org.eclipse.acceleo.internal.ide.ui.editors.template.actions.refactor;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.eclipse.acceleo.ide.ui.AcceleoUIActivator;
import org.eclipse.acceleo.internal.ide.ui.editors.template.AcceleoSourceContent;
import org.eclipse.acceleo.parser.cst.Block;
import org.eclipse.acceleo.parser.cst.CSTNode;
import org.eclipse.acceleo.parser.cst.ForBlock;
import org.eclipse.acceleo.parser.cst.IfBlock;
import org.eclipse.acceleo.parser.cst.ModelExpression;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.IDocument;

/**
 * An action to put the selected text as a for/if block. We split the selected text in different parts. "If"
 * conditions are created for each part of the text. Empty lines are used to identify the text regions.
 * 
 * @author <a href="mailto:jonathan.musset@obeo.fr">Jonathan Musset</a>
 */
public class CreateForIfAction extends AbstractRefactoringWithVariableContextAction {

	/**
	 * The action ID.
	 */
	public static final String ACTION_ID = "org.eclipse.acceleo.ide.ui.editors.template.actions.refactor.createForIf"; //$NON-NLS-1$

	/**
	 * The associated command ID.
	 */
	public static final String COMMAND_ID = "org.eclipse.acceleo.ide.ui.createForIf"; //$NON-NLS-1$

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.acceleo.internal.ide.ui.editors.template.actions.refactor.AbstractRefactoringWithVariableContextAction#modify(org.eclipse.jface.text.IDocument,
	 *      org.eclipse.acceleo.internal.ide.ui.editors.template.AcceleoSourceContent, int, int)
	 */
	@Override
	protected int modify(IDocument document, AcceleoSourceContent content, int offset, int length)
			throws BadLocationException {
		int b = offset;
		int e = offset + length;
		try {
			final String newLine = "\n"; //$NON-NLS-1$
			int outputIndex = b;
			String text = document.get(offset, length);
			CSTNode currentNode = content.getCSTNode(b, e);
			boolean singleLine = !text.contains(newLine);
			StringBuilder newBuffer = new StringBuilder();
			List<String> parts = getParts(text);
			String indentIf;
			if (singleLine) {
				indentIf = ""; //$NON-NLS-1$
			} else if (parts.size() > 1) {
				indentIf = "\t\t"; //$NON-NLS-1$
			} else {
				indentIf = "\t"; //$NON-NLS-1$
			}
			StringBuilder prefix = new StringBuilder();
			if (parts.size() > 1) {
				prefix.append("\t[for ("); //$NON-NLS-1$
				prefix.append(computeForExpression(content, text, currentNode));
				outputIndex = b + prefix.length();
				prefix.append(")"); //$NON-NLS-1$
				prefix.append("]"); //$NON-NLS-1$
				if (!singleLine) {
					prefix.append(newLine);
				}
			}
			prefix.append(indentIf);
			String ifCondition;
			if (parts.size() > 0) {
				ifCondition = computeIfExpression(content, parts.get(0));
			} else {
				ifCondition = ""; //$NON-NLS-1$ 
			}
			prefix.append("[if ("); //$NON-NLS-1$
			prefix.append(ifCondition);
			outputIndex = b + prefix.length();
			prefix.append(")]"); //$NON-NLS-1$
			if (!singleLine) {
				prefix.append(newLine);
			}
			newBuffer.append(prefix);
			if (parts.size() > 0) {
				for (int i = 0; i < parts.size(); i++) {
					String part = parts.get(i);
					if (i > 0) {
						StringBuilder elseIfPrefix = new StringBuilder();
						elseIfPrefix.append(indentIf);
						elseIfPrefix.append("[elseif ("); //$NON-NLS-1$ 
						elseIfPrefix.append(computeIfExpression(content, part));
						elseIfPrefix.append(")]"); //$NON-NLS-1$
						if (!singleLine) {
							elseIfPrefix.append(newLine);
						}
						newBuffer.append(elseIfPrefix);
					}
					newBuffer.append(part);
				}
			}
			StringBuilder suffix = new StringBuilder();
			if (!singleLine) {
				suffix.append(newLine);
			}
			suffix.append(indentIf);
			suffix.append("[/if]"); //$NON-NLS-1$
			if (!singleLine) {
				suffix.append(newLine);
			}
			if (parts.size() > 1) {
				suffix.append("\t[/for]"); //$NON-NLS-1$
				if (!singleLine) {
					suffix.append(newLine);
				}
			}
			newBuffer.append(suffix);
			document.replace(b, e - b, newBuffer.toString());
			return outputIndex;
		} catch (BadLocationException ex) {
			AcceleoUIActivator.log(ex, true);
			return offset;
		}
	}

	/**
	 * Gets the significant regions of the given text. Empty lines are used to identify the text regions.
	 * 
	 * @param text
	 *            is the text
	 * @return the parts list
	 */
	private List<String> getParts(String text) {
		List<String> result = new ArrayList<String>();
		if (text.trim().length() == 0) {
			return result;
		}
		StringBuilder current = new StringBuilder();
		boolean emptyLine = true;
		for (int i = 0; i < text.length(); i++) {
			char c = text.charAt(i);
			current.append(c);
			if (c == '\n') {
				if (emptyLine) {
					if (current.toString().trim().length() > 0) {
						result.add(current.toString());
						current = new StringBuilder();
					}
				} else {
					emptyLine = true;
				}
			} else if (!Character.isWhitespace(c)) {
				emptyLine = false;
			}
		}
		if (current.toString().trim().length() > 0) {
			result.add(current.toString());
		}
		return result;
	}

	/**
	 * Try to compute the best "If" expression.
	 * 
	 * @param content
	 *            is the editor content
	 * @param text
	 *            is the text that we would like to put in the new "If" block
	 * @return the best OCL expression for the "If" block
	 */
	private String computeIfExpression(AcceleoSourceContent content, String text) {
		StringBuilder result = new StringBuilder();
		String ifCondition = findBestAvailableExpression(content, text, true, false);
		if (ifCondition != null && ifCondition.length() > 0) {
			result.append(ifCondition);
		} else {
			result.append("oclIsKindOf()"); //$NON-NLS-1$
		}
		return result.toString();
	}

	/**
	 * Try to compute the best "For" expression.
	 * 
	 * @param content
	 *            is the editor content
	 * @param text
	 *            is the text that we would like to put in the new "For" block
	 * @param currentNode
	 *            is the current CST node, can be null
	 * @return the best OCL expression for the "For" block
	 */
	private String computeForExpression(AcceleoSourceContent content, String text, CSTNode currentNode) {
		StringBuilder result = new StringBuilder();
		String forCondition = findBestAvailableExpression(content, text, false, true);
		if (forCondition != null && forCondition.length() > 0) {
			result.append(forCondition);
		} else if (currentNode == null) {
			result.append("e : E | eAllContents()"); //$NON-NLS-1$
		} else {
			EReference eRef = null;
			String currentType = getCurrentVariableTypeName(currentNode, ""); //$NON-NLS-1$
			for (Iterator<EClassifier> eTypes = content.getTypes().iterator(); eRef == null
					&& eTypes.hasNext();) {
				EClassifier eType = eTypes.next();
				if (eType instanceof EClass && ((EClass)eType).getName() != null
						&& ((EClass)eType).getName().equals(currentType)) {
					for (EReference eReference : ((EClass)eType).getEAllReferences()) {
						if (eReference.isContainment() && !eReference.isDerived()) {
							eRef = eReference;
							break;
						}
					}
				}
			}
			if (eRef != null && eRef.getName() != null && eRef.getEType() != null
					&& eRef.getEType().getName() != null) {
				if (eRef.getEType().getName().length() > 0) {
					result.append(Character.toLowerCase(eRef.getEType().getName().charAt(0)));
				}
				result.append(" : "); //$NON-NLS-1$
				result.append(eRef.getEType().getName());
				result.append(" | "); //$NON-NLS-1$
				result.append(eRef.getName());
			} else {
				result.append("e : E | eAllContents()"); //$NON-NLS-1$
			}
		}
		return result.toString();
	}

	/**
	 * Gets the best available OCL expression that matches with the text that we would like to put in the new
	 * block.
	 * 
	 * @param content
	 *            is the editor content
	 * @param body
	 *            is the text that we would like to put in the new block
	 * @param includeIf
	 *            indicates if we search the OCL expression in the If blocks
	 * @param includeFor
	 *            indicates if we search the OCL expression in the For blocks
	 * @return the best OCL expression for the given block body
	 */
	private String findBestAvailableExpression(AcceleoSourceContent content, String body, boolean includeIf,
			boolean includeFor) {
		EObject eRoot = content.getCST();
		if (eRoot == null) {
			return ""; //$NON-NLS-1$
		}
		int maxScore = -1;
		String maxScoreExpression = ""; //$NON-NLS-1$
		List<String> words = getOrderedWords(body);
		Iterator<EObject> eAllContents = eRoot.eAllContents();
		while (eAllContents.hasNext()) {
			EObject eObject = eAllContents.next();
			if (includeIf && eObject instanceof IfBlock) {
				IfBlock ifBlock = (IfBlock)eObject;
				ModelExpression modelExpression = ifBlock.getIfExpr();
				if (modelExpression != null && modelExpression.getBody() != null) {
					int score = computeScore(ifBlock, content, words);
					if (score >= maxScore) {
						maxScore = score;
						maxScoreExpression = modelExpression.getBody();
					}
				}
			}
			if (includeFor && eObject instanceof ForBlock) {
				ForBlock forBlock = (ForBlock)eObject;
				int score = computeScore(forBlock, content, words);
				if (score >= maxScore && forBlock.getIterSet() != null
						&& forBlock.getIterSet().getBody() != null) {
					String iterSet = forBlock.getIterSet().getBody();
					maxScore = score;
					if (forBlock.getLoopVariable() != null) {
						maxScoreExpression = forBlock.getLoopVariable().getName() + " : " //$NON-NLS-1$
								+ forBlock.getLoopVariable().getType() + " | " + iterSet; //$NON-NLS-1$
					} else {
						maxScoreExpression = iterSet;
					}
				}
			}
		}
		if (maxScoreExpression != null) {
			maxScoreExpression = maxScoreExpression.trim();
			if (maxScoreExpression.startsWith("(")) { //$NON-NLS-1$
				maxScoreExpression = maxScoreExpression.substring(1).trim();
			}
			if (maxScoreExpression.endsWith(")")) { //$NON-NLS-1$
				maxScoreExpression = maxScoreExpression.substring(0, maxScoreExpression.length() - 1).trim();
			}
		}
		return maxScoreExpression;
	}

	/**
	 * Compute the matching score of the block with the given words. The score is higher when the words in the
	 * block match perfectly with the words list.
	 * 
	 * @param block
	 *            is the block (If, For)
	 * @param content
	 *            is the editor content
	 * @param words
	 *            is the words list
	 * @return the score
	 */
	private int computeScore(Block block, AcceleoSourceContent content, List<String> words) {
		int b;
		int e;
		if (block.getBody().size() > 0) {
			b = block.getBody().get(0).getStartPosition();
			e = block.getBody().get(block.getBody().size() - 1).getEndPosition();
		} else {
			b = 0;
			e = 0;
		}
		String thenText = content.getText().substring(b, e);
		List<String> refCopy = new ArrayList<String>(words);
		int score = 0;
		for (String token : getOrderedWords(thenText)) {
			if (refCopy.contains(token)) {
				refCopy.remove(token);
				score += token.length();
			}
		}
		return score;
	}

	/**
	 * Gets the significant words of the given text. It keeps only the java identifier characters. It is also
	 * possible to have multiple words in a single java identifier. Let's take some simple examples... "Aaa",
	 * "Bbb", and "Ccc" are 3 words in "Aaa  Bbb  Ccc". "Aaa", "Bbb", and "Ccc" are 3 words in "Aaa.Bbb.Ccc".
	 * "Aaa", "Bbb", and "Ccc" are 3 words in "AaaBbbCcc".
	 * 
	 * @param text
	 *            is the text
	 * @return the words list
	 */
	private List<String> getOrderedWords(String text) {
		List<String> words = new ArrayList<String>();
		StringBuilder current = new StringBuilder();
		for (int i = 0; i < text.length(); i++) {
			char c = text.charAt(i);
			if (Character.isJavaIdentifierPart(c)) {
				if (Character.isUpperCase(c)) {
					if (current.length() > 0) {
						words.add(current.toString().toLowerCase());
						current = new StringBuilder();
					}
				}
				current.append(c);
			} else {
				if (current.length() > 0) {
					words.add(current.toString().toLowerCase());
					current = new StringBuilder();
				}
			}
		}
		if (current.length() > 0) {
			words.add(current.toString().toLowerCase());
		}
		return words;
	}

}

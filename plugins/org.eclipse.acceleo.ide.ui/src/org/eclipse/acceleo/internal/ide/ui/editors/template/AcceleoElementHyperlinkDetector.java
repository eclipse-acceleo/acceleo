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
package org.eclipse.acceleo.internal.ide.ui.editors.template;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.acceleo.ide.ui.AcceleoUIActivator;
import org.eclipse.acceleo.internal.ide.ui.AcceleoUIMessages;
import org.eclipse.acceleo.internal.ide.ui.editors.template.utils.OpenDeclarationUtils;
import org.eclipse.acceleo.parser.cst.CSTNode;
import org.eclipse.acceleo.parser.cst.TypedModel;
import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.provider.ComposedAdapterFactory;
import org.eclipse.emf.edit.provider.IItemLabelProvider;
import org.eclipse.emf.edit.provider.ReflectiveItemProviderAdapterFactory;
import org.eclipse.jface.text.IRegion;
import org.eclipse.jface.text.ITextViewer;
import org.eclipse.jface.text.Region;
import org.eclipse.jface.text.hyperlink.AbstractHyperlinkDetector;
import org.eclipse.jface.text.hyperlink.IHyperlink;
import org.eclipse.ocl.ecore.IteratorExp;
import org.eclipse.ocl.utilities.ASTNode;
import org.eclipse.ui.texteditor.ITextEditor;

/**
 * This will allow us to plug the CTRL+click "open declaration" in Acceleo editors.
 * 
 * @author <a href="mailto:laurent.goubet@obeo.fr">Laurent Goubet</a>
 */
public class AcceleoElementHyperlinkDetector extends AbstractHyperlinkDetector {
	/** Adapter factory instance. This contains all factories registered in the global registry. */
	private static final ComposedAdapterFactory FACTORY = createAdapterFactory();

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.jface.text.hyperlink.IHyperlinkDetector#detectHyperlinks(org.eclipse.jface.text.ITextViewer,
	 *      org.eclipse.jface.text.IRegion, boolean)
	 */
	public IHyperlink[] detectHyperlinks(ITextViewer textViewer, IRegion region,
			boolean canShowMultipleHyperlinks) {
		final ITextEditor textEditor = (ITextEditor)getAdapter(ITextEditor.class);
		if (region != null && textEditor instanceof AcceleoEditor) {
			int offset = region.getOffset();
			final AcceleoEditor editor = (AcceleoEditor)textEditor;
			final int start = Math.max(0, offset - 10);
			final int end = Math.min(editor.getContent().getText().length(), offset + 10);
			// Creates a new String to avoid keeping the whole document in memory
			final String expressionSurroundings = new String(editor.getContent().getText().substring(start,
					end));
			if (isRelevant(expressionSurroundings, offset - start)) {
				return detectHyperlinks(editor, offset);
			}
		}
		return null;
	}

	/**
	 * This will be called from {@link #detectHyperlinks(ITextViewer, IRegion, boolean)} when we've taken care
	 * of all shortcut routes.
	 * 
	 * @param editor
	 *            The AcceleoEditor in which to detect hyperlinks.
	 * @param offset
	 *            Offset at which to search for hyperlinks.
	 * @return The detected hyperlinks if any.
	 */
	private IHyperlink[] detectHyperlinks(AcceleoEditor editor, int offset) {
		EObject res = null;
		int wordStart = -1;
		int wordLength = -1;

		/*
		 * This boolean will be used to determine whether we need to compute a smarter region than what's
		 * carried on by the AST/CST node.
		 */
		boolean inferWordRegion = true;
		ASTNode astNode = editor.getContent().getResolvedASTNode(offset, offset);
		if (astNode != null) {
			res = OpenDeclarationUtils.findDeclarationFromAST(astNode);
			if (res instanceof IteratorExp && editor.getContent().getOCLEnvironment() != null) {
				res = OpenDeclarationUtils.findIteratorEOperation(editor.getContent().getOCLEnvironment(),
						(IteratorExp)res);
			}
			wordStart = astNode.getStartPosition();
			wordLength = astNode.getEndPosition() - astNode.getStartPosition();
		}
		if (res == null) {
			CSTNode cstNode = editor.getContent().getCSTNode(offset, offset);
			if (cstNode != null) {
				res = OpenDeclarationUtils.findDeclarationFromCST(editor, astNode, cstNode);
				wordStart = cstNode.getStartPosition();
				wordLength = cstNode.getEndPosition() - cstNode.getStartPosition();
			}
			if (cstNode instanceof TypedModel) {
				inferWordRegion = false;
			}
		}
		IHyperlink[] links = null;
		if (res != null) {
			final IRegion wordRegion;
			if (inferWordRegion) {
				wordRegion = getWordRegion(editor, offset, wordStart, wordLength);
			} else {
				wordRegion = new Region(wordStart, wordLength);
			}
			if (wordRegion != null) {
				links = new IHyperlink[1];
				links[0] = new AcceleoElementHyperlink(editor, wordRegion, res);
			}
		}
		return links;
	}

	/**
	 * Tries and return the actual region span of the word under "cursorOffset". Note that a "word" can
	 * actualy contain spaces, dots, colons, ...
	 * <p>
	 * For example, if the cursor is currently on the "metamodel" declaration, then this will return the full
	 * extent of the metamodel's URI, regardless of slashes, dots, colons, sharps, ...
	 * </p>
	 * <p>
	 * On a type expression, this can and will return the full type if it is qualified, packages included
	 * (mt::core::Query) and whether or not there are spaces in-between colons and package/type names.
	 * </p>
	 * <p>
	 * On a variable declaration, this will return the region containing both the variable name and its type,
	 * once again regardless of spaces (templ : mt::core::Template).
	 * </p>
	 * 
	 * @param currentEditor
	 *            The editor currently displaying the text we search a region from.
	 * @param cursorOffset
	 *            Offset above which the mouse currently hovers.
	 * @param expressionStart
	 *            Starting offset of the expression in which we seek a particular region.
	 * @param expressionLength
	 *            Ending offset of the expression in which we seek a particular region.
	 * @return Region of the word we're currently hovering over.
	 */
	private IRegion getWordRegion(AcceleoEditor currentEditor, int cursorOffset, int expressionStart,
			int expressionLength) {
		if ((expressionStart + expressionLength) > currentEditor.getContent().getText().length()) {
			return null;
		}

		// Creates a new String to avoid keeping the whole document in memory
		final String expression = new String(currentEditor.getContent().getText().substring(expressionStart,
				expressionStart + expressionLength));
		int cursorPositionInExpression = cursorOffset - expressionStart;
		final int wordStart;
		final int wordEnd;

		int prev = cursorPositionInExpression - 1;
		if (prev >= 0) {
			while (prev >= 0 && isRelevant(expression, prev)) {
				prev--;
			}
		}

		int next = cursorPositionInExpression + 1;
		if (next < expression.length()) {
			while (next < expression.length() - 1 && isRelevant(expression, next)) {
				next++;
			}
		}

		if (prev == -1) {
			wordStart = 0;
		} else {
			// We found a non relevant character at "prev". Add 1 to start at the last browsed.
			wordStart = prev + 1;
		}
		if (next == expression.length() - 1) {
			wordEnd = expression.length();
		} else {
			wordEnd = next;
		}

		// now set back the offsets in document range
		int wordLength = wordEnd - wordStart;
		return new Region(wordStart + expressionStart, wordLength);
	}

	/**
	 * Given an expression and offset in that expression, this method will check wether the character at
	 * <em>offset</em> is relevant for hyperlinking. Some examples are given in the javadoc of
	 * {@link #getWordRegion(AcceleoEditor, int, int, int)}.
	 * 
	 * @param expression
	 *            Expression in which a character is to be considered.
	 * @param offset
	 *            Offset of the character we need to determine relevancy of.
	 * @return <code>true</code> if the character at <em>offset</em> in <em>expression</em> is relevant for
	 *         hyperlink regions.
	 */
	private boolean isRelevant(String expression, int offset) {
		char character = expression.charAt(offset);
		// shortcut
		if (Character.isJavaIdentifierPart(character)) {
			return true;
		}

		boolean relevant = false;
		// initialize at random "relevant" character : doesn't matter which as long as it isn't '-' (arrows)
		char previous = 'a';
		char next = 'a';

		if (offset > 1) {
			previous = expression.charAt(offset - 1);
		}
		if (offset < expression.length() - 2) {
			next = expression.charAt(offset + 1);
		}

		/*
		 * ':' is somewhat special in that it is relevant and can serve as a "junction" in variable
		 * declaration and type expressions. In both of these, ':', '::' *and* the spaces before and after are
		 * relevant.
		 */
		if (Character.isWhitespace(character)) {
			int curOffset = offset;
			while (curOffset < expression.length() - 2 && !Character.isJavaIdentifierPart(next)
					&& next != ':') {
				next = expression.charAt(++curOffset);
			}
			curOffset = offset;
			while (curOffset > 1 && !Character.isJavaIdentifierPart(previous) && previous != ':') {
				previous = expression.charAt(--curOffset);
			}
			if (previous == ':' || next == ':') {
				relevant = true;
			}
		} else if (character == ':') {
			relevant = true;
		} else if (character == '>' && previous == '-') {
			relevant = false;
		} else if (character == '-' && next == '>') {
			relevant = false;
		} else if (character == '<' || character == '>' || character == '=') {
			relevant = true;
		} else if (character == '+' || character == '-' || character == '/' || character == '*') {
			relevant = true;
		}
		return relevant;
	}

	/**
	 * Returns an adapter factory containing all the global EMF registry's factories.
	 * 
	 * @return An adapter factory made of all the adapter factories declared in the registry.
	 */
	private static ComposedAdapterFactory createAdapterFactory() {
		final List<AdapterFactory> factories = new ArrayList<AdapterFactory>();
		factories.add(new ComposedAdapterFactory(ComposedAdapterFactory.Descriptor.Registry.INSTANCE));
		factories.add(new ReflectiveItemProviderAdapterFactory());
		return new ComposedAdapterFactory(factories);
	}

	/**
	 * This will try and get the IItemLabelProvider associated to the given EObject if its ItemProviderFactory
	 * is registered, then return the text it provides.
	 * 
	 * @param eObj
	 *            EObject we need the text of.
	 * @return The text provided by the IItemLabelProvider associated with <tt>eObj</tt>, <code>null</code> if
	 *         it cannot be found.
	 * @see IItemLabelProvider#getText(Object)
	 * @since 0.8
	 */
	protected static String getLabelFor(EObject eObj) {
		final String text;
		if (eObj == null) {
			text = "null"; //$NON-NLS-1$
		} else {
			final IItemLabelProvider labelProvider = (IItemLabelProvider)FACTORY.adapt(eObj,
					IItemLabelProvider.class);
			if (labelProvider != null) {
				text = labelProvider.getText(eObj);
			} else {
				text = ""; //$NON-NLS-1$
			}
		}
		return text;
	}

	/**
	 * This implementation of an hyperlink allows for the opening of Acceleo elements declarations.
	 * 
	 * @author <a href="mailto:laurent.goubet@obeo.fr">Laurent Goubet</a>
	 */
	private class AcceleoElementHyperlink implements IHyperlink {
		/** Region of this hyperlink. */
		private final IRegion hyperLinkRegion;

		/** EObject that will be opened via this hyperlink. */
		private final EObject target;

		/** Editor on which this link appears. */
		private final ITextEditor sourceEditor;

		/**
		 * Instantiates an Acceleo hyperlink given the editor it appears on, the text region it spans to, and
		 * the link's target.
		 * 
		 * @param editor
		 *            Editor on which this hyperlink is shown.
		 * @param region
		 *            Region of the editor where this hyperlink appears.
		 * @param linkTarget
		 *            Target of the hyperlink.
		 */
		public AcceleoElementHyperlink(ITextEditor editor, IRegion region, EObject linkTarget) {
			sourceEditor = editor;
			hyperLinkRegion = region;
			target = linkTarget;
		}

		/**
		 * {@inheritDoc}
		 * 
		 * @see org.eclipse.jface.text.hyperlink.IHyperlink#getHyperlinkRegion()
		 */
		public IRegion getHyperlinkRegion() {
			return hyperLinkRegion;
		}

		/**
		 * {@inheritDoc}
		 * 
		 * @see org.eclipse.jface.text.hyperlink.IHyperlink#getHyperlinkText()
		 */
		public String getHyperlinkText() {
			return AcceleoUIMessages.getString("AcceleoElementHyperlinkDetector.OpenDeclarationLabel", //$NON-NLS-1$
					getLabelFor(target));
		}

		/**
		 * {@inheritDoc}
		 * 
		 * @see org.eclipse.jface.text.hyperlink.IHyperlink#getTypeLabel()
		 */
		public String getTypeLabel() {
			return null;
		}

		/**
		 * {@inheritDoc}
		 * 
		 * @see org.eclipse.jface.text.hyperlink.IHyperlink#open()
		 */
		public void open() {
			if (target.eResource() != null && sourceEditor.getSite() != null
					&& sourceEditor.getSite().getPage() != null) {
				OpenDeclarationUtils.showEObject(sourceEditor.getSite().getPage(), target.eResource()
						.getURI(), OpenDeclarationUtils.createRegion(target), target);
			} else {
				AcceleoUIActivator.log(AcceleoUIMessages.getString(
						"AcceleoElementHyperlinkDetector.MetamodelNotInAResource", target.eClass() //$NON-NLS-1$
								.getName()), false);
			}
		}
	}
}

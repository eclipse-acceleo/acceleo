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
package org.eclipse.acceleo.internal.ide.ui.editors.template;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.acceleo.internal.ide.ui.AcceleoUIMessages;
import org.eclipse.acceleo.internal.ide.ui.editors.template.utils.OpenDeclarationUtils;
import org.eclipse.acceleo.parser.cst.CSTNode;
import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.provider.ComposedAdapterFactory;
import org.eclipse.emf.edit.provider.IItemLabelProvider;
import org.eclipse.emf.edit.provider.ReflectiveItemProviderAdapterFactory;
import org.eclipse.jface.text.IRegion;
import org.eclipse.jface.text.ITextViewer;
import org.eclipse.jface.text.hyperlink.AbstractHyperlinkDetector;
import org.eclipse.jface.text.hyperlink.IHyperlink;
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
		if (region == null || !(textEditor instanceof AcceleoEditor)) {
			return null;
		}

		EObject res = null;
		final AcceleoEditor editor = (AcceleoEditor)textEditor;
		int offset = region.getOffset();

		ASTNode astNode = editor.getContent().getASTNode(offset, offset);
		if (astNode != null) {
			res = OpenDeclarationUtils.findDeclarationFromAST(astNode);
		}
		if (res == null) {
			CSTNode cstNode = editor.getContent().getCSTNode(offset, offset);
			if (cstNode != null) {
				res = OpenDeclarationUtils.findDeclarationFromCST(editor, astNode, cstNode);
			}
		}
		IHyperlink[] links = null;
		if (res != null) {
			links = new IHyperlink[1];
			links[0] = new AcceleoElementHyperlink(editor, region, res);
		}
		return links;
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
		 * Instantiates an Acceleo hyperlink given the editor it appears on, the text region it spans to, and the
		 * link's target.
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
			OpenDeclarationUtils.showEObject(sourceEditor.getSite().getPage(), target.eResource().getURI(),
					OpenDeclarationUtils.createRegion(target), target);
		}
	}
}

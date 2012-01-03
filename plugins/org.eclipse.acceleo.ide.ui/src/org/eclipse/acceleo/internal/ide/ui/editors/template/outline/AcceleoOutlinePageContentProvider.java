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
package org.eclipse.acceleo.internal.ide.ui.editors.template.outline;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.eclipse.acceleo.parser.cst.CSTNode;
import org.eclipse.acceleo.parser.cst.Module;
import org.eclipse.acceleo.parser.cst.ModuleImportsValue;
import org.eclipse.acceleo.parser.cst.Query;
import org.eclipse.acceleo.parser.cst.Template;
import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.ui.provider.AdapterFactoryContentProvider;

/**
 * The content provider used to show the CST objects in the outline view.
 * 
 * @author <a href="mailto:jonathan.musset@obeo.fr">Jonathan Musset</a>
 */
public class AcceleoOutlinePageContentProvider extends AdapterFactoryContentProvider {

	/**
	 * Constructor.
	 * 
	 * @param adapterFactory
	 *            is the adapter factory
	 */
	public AcceleoOutlinePageContentProvider(AdapterFactory adapterFactory) {
		super(adapterFactory);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.edit.ui.provider.AdapterFactoryContentProvider#getElements(java.lang.Object)
	 */
	@Override
	public Object[] getElements(Object object) {
		Object[] result = null;
		if (object instanceof List<?>) {
			final Comparator<CSTNode> cstNodeComparator = new Comparator<CSTNode>() {
				public int compare(CSTNode n0, CSTNode n1) {
					if (n0.getStartPosition() < n1.getStartPosition()) {
						return -1;
					}
					return 1;
				}
			};
			List<CSTNode> orderedCollection = new ArrayList<CSTNode>(((List<?>)object).size());
			for (Object element : (List<?>)object) {
				if (element instanceof CSTNode
						&& !(element instanceof org.eclipse.acceleo.parser.cst.TextExpression
								|| element instanceof org.eclipse.acceleo.parser.cst.ModelExpression || element instanceof org.eclipse.acceleo.parser.cst.Comment)) {
					orderedCollection.add((CSTNode)element);
				}
			}
			Collections.sort(orderedCollection, cstNodeComparator);
			result = orderedCollection.toArray();

			EObject obj = null;
			if (orderedCollection.size() > 0) {
				obj = orderedCollection.get(0).eContainer();
				while (obj != null && !(obj instanceof Module)) {
					obj = obj.eContainer();
				}
			}

			if (obj != null && ((Module)obj).getImports().size() > 0) {
				Module module = (Module)obj;
				List<Object> resultTmp = new ArrayList<Object>();
				for (CSTNode cstNode : orderedCollection) {
					if (!(cstNode instanceof ModuleImportsValue)) {
						resultTmp.add(cstNode);
					}
				}

				for (int i = 0; i < resultTmp.size(); i++) {
					CSTNode node = (CSTNode)resultTmp.get(i);
					if (node instanceof Template || node instanceof Query) {
						resultTmp.add(i, new AcceleoOutlineImportContainer(module));
						break;
					}
				}
				if (resultTmp.size() > 0) {
					result = resultTmp.toArray();
				}
			}
		} else {
			result = super.getElements(object);
		}
		return result;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.edit.ui.provider.AdapterFactoryContentProvider#hasChildren(java.lang.Object)
	 */
	@Override
	public boolean hasChildren(Object object) {
		if (object instanceof AcceleoOutlineImportContainer) {
			AcceleoOutlineImportContainer imports = (AcceleoOutlineImportContainer)object;
			return imports.getImports().size() > 0;
		}
		return super.hasChildren(object);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.edit.ui.provider.AdapterFactoryContentProvider#getChildren(java.lang.Object)
	 */
	@Override
	public Object[] getChildren(Object object) {
		if (object instanceof AcceleoOutlineImportContainer) {
			AcceleoOutlineImportContainer imports = (AcceleoOutlineImportContainer)object;
			return imports.getImports().toArray();
		}
		return super.getChildren(object);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.edit.ui.provider.AdapterFactoryContentProvider#notifyChanged(org.eclipse.emf.common.notify.Notification)
	 */
	@Override
	public void notifyChanged(Notification notification) {
		// Disables inherited behavior
	}
}

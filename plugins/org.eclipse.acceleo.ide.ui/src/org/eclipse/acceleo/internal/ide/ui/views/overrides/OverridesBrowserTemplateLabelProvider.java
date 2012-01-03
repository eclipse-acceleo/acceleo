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
package org.eclipse.acceleo.internal.ide.ui.views.overrides;

import java.util.Iterator;

import org.eclipse.acceleo.common.IAcceleoConstants;
import org.eclipse.acceleo.ide.ui.AcceleoUIActivator;
import org.eclipse.acceleo.internal.ide.ui.editors.template.color.AcceleoColor;
import org.eclipse.acceleo.internal.ide.ui.editors.template.color.AcceleoColorManager;
import org.eclipse.acceleo.model.mtl.Macro;
import org.eclipse.acceleo.model.mtl.Module;
import org.eclipse.acceleo.model.mtl.ModuleElement;
import org.eclipse.acceleo.model.mtl.Query;
import org.eclipse.acceleo.model.mtl.Template;
import org.eclipse.acceleo.parser.cst.Comment;
import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.ui.provider.AdapterFactoryLabelProvider;
import org.eclipse.ocl.ecore.Variable;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Image;

/**
 * The 'OverridesBrowser' view is used to update the settings of the current 'overrides' completion proposal.
 * This label provider displays the Templates of the current Eclipse instance. This label provider wraps an
 * AdapterFactory and it delegates its JFace provider interfaces to corresponding adapter-implemented item
 * provider interfaces.
 * 
 * @author <a href="mailto:jonathan.musset@obeo.fr">Jonathan Musset</a>
 */
public class OverridesBrowserTemplateLabelProvider extends AdapterFactoryLabelProvider {
	/**
	 * Construct an instance that wraps this factory.
	 * 
	 * @param adapterFactory
	 *            should yield adapters that implement the various item label provider interfaces.
	 */
	public OverridesBrowserTemplateLabelProvider(AdapterFactory adapterFactory) {
		super(adapterFactory);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.edit.ui.provider.AdapterFactoryLabelProvider#getBackground(java.lang.Object)
	 */
	@Override
	public Color getBackground(Object object) {
		if (object instanceof ModuleProjectHandler && !((ModuleProjectHandler)object).isResolved()) {
			return AcceleoColorManager.getColor(AcceleoColor.RED);
		}
		return super.getBackground(object);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.edit.ui.provider.AdapterFactoryLabelProvider#getText(java.lang.Object)
	 */
	@Override
	public String getText(Object object) {
		String result;
		if (object == null) {
			result = ""; //$NON-NLS-1$
		} else if (object instanceof String) {
			result = (String)object;
		} else if (object instanceof ModuleProjectHandler) {
			result = ((ModuleProjectHandler)object).getName();
		} else if (object instanceof Module) {
			result = ((Module)object).getName();
		} else if (object instanceof ModuleElement) {
			ModuleElement element = (ModuleElement)object;
			StringBuilder signature = new StringBuilder();
			signature.append(element.getName());
			if (element instanceof Template) {
				signature.append('(');
				boolean first = true;
				for (Variable iVariable : ((Template)element).getParameter()) {
					if (first) {
						first = false;
					} else {
						signature.append(',');
					}
					if (iVariable.getType() != null) {
						signature.append(iVariable.getType().getName());
					}
				}
				signature.append(')');
			} else if (element instanceof Macro) {
				signature.append('(');
				boolean first = true;
				for (Variable iVariable : ((Macro)element).getParameter()) {
					if (first) {
						first = false;
					} else {
						signature.append(',');
					}
					if (iVariable.getType() != null) {
						signature.append(iVariable.getType().getName());
					}
				}
				signature.append(')');
			} else if (element instanceof Query) {
				signature.append('(');
				boolean first = true;
				for (Variable iVariable : ((Query)element).getParameter()) {
					if (first) {
						first = false;
					} else {
						signature.append(',');
					}
					if (iVariable.getType() != null) {
						signature.append(iVariable.getType().getName());
					}
				}
				signature.append(')');
			}
			result = signature.toString();
		} else {
			result = super.getText(object);
		}
		return result;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.edit.ui.provider.AdapterFactoryLabelProvider#getImage(java.lang.Object)
	 */
	@Override
	public Image getImage(Object object) {
		Image result;
		if (object instanceof ModuleProjectHandler) {
			if (((ModuleProjectHandler)object).isResolved()) {
				result = AcceleoUIActivator.getDefault().getImage(
						"icons/overrides/AcceleoProjectResolved.gif"); //$NON-NLS-1$
			} else {
				result = AcceleoUIActivator.getDefault().getImage(
						"icons/overrides/AcceleoProjectNotResolved.gif"); //$NON-NLS-1$
			}
		} else if (object instanceof Module) {
			result = AcceleoUIActivator.getDefault().getImage("icons/overrides/Module.gif"); //$NON-NLS-1$
		} else if (object instanceof ModuleElement) {
			result = getModuleElementImage((ModuleElement)object);
		} else {
			result = super.getImage(object);
		}
		return result;
	}

	/**
	 * Gets the image for the given module element.
	 * 
	 * @param eModuleElement
	 *            is the module element, it means a template or a macro or a query
	 * @return the image
	 */
	private Image getModuleElementImage(ModuleElement eModuleElement) {
		if (eModuleElement instanceof Template) {
			boolean isMain = false;
			Iterator<EObject> iChildren = ((Template)eModuleElement).eAllContents();
			while (!isMain && iChildren.hasNext()) {
				EObject iChild = iChildren.next();
				if (iChild instanceof Comment && ((Comment)iChild).getBody() != null
						&& ((Comment)iChild).getBody().indexOf(IAcceleoConstants.TAG_MAIN) > -1) {
					isMain = true;
				}
			}
			if (isMain) {
				return AcceleoUIActivator.getDefault().getImage("icons/overrides/Template_main.gif"); //$NON-NLS-1$
			}
		}
		return AcceleoUIActivator.getDefault().getImage(
				"icons/overrides/" + eModuleElement.eClass().getName() + ".gif"); //$NON-NLS-1$ //$NON-NLS-2$
	}

}

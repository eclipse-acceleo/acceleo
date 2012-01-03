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
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

import org.eclipse.acceleo.common.IAcceleoConstants;
import org.eclipse.acceleo.ide.ui.AcceleoUIActivator;
import org.eclipse.acceleo.internal.ide.ui.AcceleoUIMessages;
import org.eclipse.acceleo.parser.cst.CSTNode;
import org.eclipse.acceleo.parser.cst.Comment;
import org.eclipse.acceleo.parser.cst.CstPackage;
import org.eclipse.acceleo.parser.cst.FileBlock;
import org.eclipse.acceleo.parser.cst.Module;
import org.eclipse.acceleo.parser.cst.ModuleImportsValue;
import org.eclipse.acceleo.parser.cst.Query;
import org.eclipse.acceleo.parser.cst.Template;
import org.eclipse.acceleo.parser.cst.VisibilityKind;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.edit.provider.ReflectiveItemProvider;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.DecorationOverlayIcon;
import org.eclipse.jface.viewers.IDecoration;
import org.eclipse.swt.graphics.Image;

/**
 * Specific item provider for the outline view.
 * 
 * @author <a href="mailto:jonathan.musset@obeo.fr">Jonathan Musset</a>
 */
public class AcceleoOutlinePageItemProvider extends ReflectiveItemProvider {

	/**
	 * Constructor.
	 * 
	 * @param adapterFactory
	 *            is the adapter factory
	 */
	public AcceleoOutlinePageItemProvider(AcceleoOutlinePageItemProviderAdapterFactory adapterFactory) {
		super(adapterFactory);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.edit.provider.ReflectiveItemProvider#getImage(java.lang.Object)
	 */
	@Override
	public Object getImage(Object object) {
		Object result = null;

		EObject eObject = (EObject)object;
		if (eObject instanceof Template) {
			Template template = (Template)eObject;
			result = this.computeImage(template);
		} else if (eObject instanceof Query) {
			Query query = (Query)eObject;
			result = this.computeImage(query);
		} else {
			result = AcceleoUIActivator.getDefault().getImage(
					"icons/template-editor/" + eObject.eClass().getName() + ".gif"); //$NON-NLS-1$ //$NON-NLS-2$
		}

		return result;
	}

	/**
	 * Computes the image of the template that should appears in the outline view.
	 * 
	 * @param template
	 *            The template
	 * @return The image that will illustrate the template in the outline view
	 */
	private Object computeImage(final Template template) {
		Object result = null;

		if (template.getVisibility().getValue() == VisibilityKind.PROTECTED_VALUE) {
			result = AcceleoUIActivator.getDefault().getImage("icons/template-editor/Template_protected.gif"); //$NON-NLS-1$
		} else if (template.getVisibility().getValue() == VisibilityKind.PRIVATE_VALUE) {
			result = AcceleoUIActivator.getDefault().getImage("icons/template-editor/Template_private.gif"); //$NON-NLS-1$
		} else {
			boolean isMain = false;
			Iterator<EObject> iChildren = template.eAllContents();
			while (!isMain && iChildren.hasNext()) {
				EObject iChild = iChildren.next();
				if (iChild instanceof Comment && ((Comment)iChild).getBody() != null
						&& ((Comment)iChild).getBody().indexOf(IAcceleoConstants.TAG_MAIN) > -1) {
					isMain = true;
				}
			}
			if (isMain) {
				result = AcceleoUIActivator.getDefault().getImage("icons/template-editor/Template_main.gif"); //$NON-NLS-1$
			} else {
				result = AcceleoUIActivator.getDefault().getImage("icons/template-editor/Template.gif"); //$NON-NLS-1$
			}
		}

		if (template.getOverrides().size() > 0 && result instanceof Image) {
			Image image = AcceleoUIActivator.getDefault().getImage(
					"icons/template-editor/Override_overlay.gif"); //$NON-NLS-1$
			ImageDescriptor descriptor = ImageDescriptor.createFromImage(image);
			result = new DecorationOverlayIcon((Image)result, descriptor, IDecoration.TOP_RIGHT)
					.createImage();
		}

		return result;
	}

	/**
	 * Computes the image of the query that should appears in the outline view.
	 * 
	 * @param query
	 *            The query
	 * @return The image that will illustrate the query in the outline view
	 */
	private Object computeImage(final Query query) {
		Object result = null;

		if (query.getVisibility().getValue() == VisibilityKind.PROTECTED_VALUE) {
			result = AcceleoUIActivator.getDefault().getImage("icons/template-editor/Query_protected.gif"); //$NON-NLS-1$
		} else if (query.getVisibility().getValue() == VisibilityKind.PRIVATE_VALUE) {
			result = AcceleoUIActivator.getDefault().getImage("icons/template-editor/Query_private.gif"); //$NON-NLS-1$
		} else {
			result = AcceleoUIActivator.getDefault().getImage("icons/template-editor/Query.gif"); //$NON-NLS-1$
		}

		return result;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.edit.provider.ReflectiveItemProvider#getText(java.lang.Object)
	 */
	@Override
	public String getText(Object object) {
		StringBuffer text = new StringBuffer();
		if (object instanceof EObject) {
			EObject eObject = (EObject)object;
			EClass eClass = eObject.eClass();
			switch (eClass.getClassifierID()) {
				case CstPackage.MODULE:
					org.eclipse.acceleo.parser.cst.Module eModule = (org.eclipse.acceleo.parser.cst.Module)eObject;
					text.append(eModule.getName());
					break;
				case CstPackage.MODULE_EXTENDS_VALUE:
					org.eclipse.acceleo.parser.cst.ModuleExtendsValue eModuleExtendsValue = (org.eclipse.acceleo.parser.cst.ModuleExtendsValue)eObject;
					text.append("extends "); //$NON-NLS-1$
					text.append(eModuleExtendsValue.getName());
					break;
				case CstPackage.MODULE_IMPORTS_VALUE:
					org.eclipse.acceleo.parser.cst.ModuleImportsValue eModuleImportsValue = (org.eclipse.acceleo.parser.cst.ModuleImportsValue)eObject;
					text.append("import "); //$NON-NLS-1$
					text.append(eModuleImportsValue.getName());
					break;
				case CstPackage.TYPED_MODEL:
					org.eclipse.acceleo.parser.cst.TypedModel eTypedModel = (org.eclipse.acceleo.parser.cst.TypedModel)eObject;
					text.append(getTextForTypedModel(eTypedModel));
					break;
				case CstPackage.COMMENT:
					text.append("[comment/]"); //$NON-NLS-1$
					break;
				case CstPackage.TEMPLATE:
					org.eclipse.acceleo.parser.cst.Template eTemplate = (org.eclipse.acceleo.parser.cst.Template)eObject;
					text.append(getTextForTemplate(eTemplate));
					break;
				case CstPackage.TEMPLATE_OVERRIDES_VALUE:
					org.eclipse.acceleo.parser.cst.TemplateOverridesValue eTemplateOverridesValue = (org.eclipse.acceleo.parser.cst.TemplateOverridesValue)eObject;
					text.append("overrides "); //$NON-NLS-1$
					text.append(eTemplateOverridesValue.getName());
					break;
				case CstPackage.VARIABLE:
					org.eclipse.acceleo.parser.cst.Variable eVariable = (org.eclipse.acceleo.parser.cst.Variable)eObject;
					text.append(getTextForVariable(eVariable));
					break;
				case CstPackage.TEMPLATE_EXPRESSION:
					break;
				case CstPackage.MODEL_EXPRESSION:
					org.eclipse.acceleo.parser.cst.ModelExpression eModelExpression = (org.eclipse.acceleo.parser.cst.ModelExpression)eObject;
					if (eModelExpression.getBody() != null) {
						text.append(eModelExpression.getBody());
					}
					break;
				case CstPackage.TEXT_EXPRESSION:
					break;
				case CstPackage.BLOCK:
					break;
				case CstPackage.INIT_SECTION:
					text.append("{}"); //$NON-NLS-1$
					break;
				case CstPackage.PROTECTED_AREA_BLOCK:
					text.append("[protected/]"); //$NON-NLS-1$
					break;
				case CstPackage.FOR_BLOCK:
					text.append("[for/]"); //$NON-NLS-1$
					break;
				case CstPackage.IF_BLOCK:
					text.append("[if/]"); //$NON-NLS-1$
					break;
				case CstPackage.LET_BLOCK:
					text.append("[let/]"); //$NON-NLS-1$
					break;
				case CstPackage.FILE_BLOCK:
					if (eObject instanceof FileBlock && ((FileBlock)eObject).getFileUrl() != null) {
						FileBlock fileBlock = (FileBlock)eObject;
						text.append("[file(" + fileBlock.getFileUrl().getBody() + ")/]"); //$NON-NLS-1$ //$NON-NLS-2$
					} else {
						text.append("[file/]"); //$NON-NLS-1$
					}
					break;
				case CstPackage.TRACE_BLOCK:
					text.append("[trace/]"); //$NON-NLS-1$
					break;
				case CstPackage.MACRO:
					org.eclipse.acceleo.parser.cst.Macro eMacro = (org.eclipse.acceleo.parser.cst.Macro)eObject;
					text.append("macro " + eMacro.getName()); //$NON-NLS-1$
					break;
				case CstPackage.QUERY:
					org.eclipse.acceleo.parser.cst.Query eQuery = (org.eclipse.acceleo.parser.cst.Query)eObject;
					text.append(getTextForQuery(eQuery));
					break;
				default:
					throw new IllegalArgumentException(AcceleoUIMessages.getString(
							"AcceleoOutlinePageItemProvider.InvalidClassifier", eClass.getName())); //$NON-NLS-1$
			}
		}
		return text.toString().trim();
	}

	/**
	 * Gets the label text specific to a 'TypedModel' instance.
	 * 
	 * @param eTypedModel
	 *            is the model object '<em><b>Typed Model</b></em>'.
	 * @return the text
	 */
	private StringBuffer getTextForTypedModel(org.eclipse.acceleo.parser.cst.TypedModel eTypedModel) {
		StringBuffer text = new StringBuffer();
		text.append("model "); //$NON-NLS-1$
		for (Iterator<EPackage> ePackages = eTypedModel.getTakesTypesFrom().iterator(); ePackages.hasNext();) {
			text.append(ePackages.next().getName());
			if (ePackages.hasNext()) {
				text.append(IAcceleoConstants.COMMA_SEPARATOR + " "); //$NON-NLS-1$
			}
		}
		return text;
	}

	/**
	 * Gets the label text specific to a 'Query' instance.
	 * 
	 * @param eQuery
	 *            is the model object '<em><b>Query</b></em>'.
	 * @return the text
	 */
	private StringBuffer getTextForQuery(org.eclipse.acceleo.parser.cst.Query eQuery) {
		StringBuffer text = new StringBuffer();
		text.append(eQuery.getName());
		text.append("("); //$NON-NLS-1$
		for (Iterator<org.eclipse.acceleo.parser.cst.Variable> eParameters = eQuery.getParameter().iterator(); eParameters
				.hasNext();) {
			org.eclipse.acceleo.parser.cst.Variable eVariable = eParameters.next();
			if (eVariable.getType() != null) {
				text.append(eVariable.getType());
			}
			if (eParameters.hasNext()) {
				text.append(IAcceleoConstants.COMMA_SEPARATOR + " "); //$NON-NLS-1$
			}
		}
		text.append(") : "); //$NON-NLS-1$
		text.append(eQuery.getType());
		return text;
	}

	/**
	 * Gets the label text specific to a 'Template' instance.
	 * 
	 * @param eTemplate
	 *            is the model object '<em><b>Template</b></em>'.
	 * @return the text
	 */
	private StringBuffer getTextForTemplate(org.eclipse.acceleo.parser.cst.Template eTemplate) {
		StringBuffer text = new StringBuffer();
		text.append(eTemplate.getName());
		text.append("("); //$NON-NLS-1$
		for (Iterator<org.eclipse.acceleo.parser.cst.Variable> eParameters = eTemplate.getParameter()
				.iterator(); eParameters.hasNext();) {
			org.eclipse.acceleo.parser.cst.Variable eVariable = eParameters.next();
			if (eVariable.getType() != null) {
				text.append(eVariable.getType());
			}
			if (eParameters.hasNext()) {
				text.append(", "); //$NON-NLS-1$
			}
		}
		text.append(") : String"); //$NON-NLS-1$
		return text;
	}

	/**
	 * Gets the label text specific to a 'Variable' instance.
	 * 
	 * @param eVariable
	 *            is the model object '<em><b>Variable</b></em>'.
	 * @return the text
	 */
	private StringBuffer getTextForVariable(org.eclipse.acceleo.parser.cst.Variable eVariable) {
		StringBuffer text = new StringBuffer();
		text.append(eVariable.getName());
		text.append(":"); //$NON-NLS-1$
		if (eVariable.getType() != null) {
			text.append(eVariable.getType());
		}
		return text;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.edit.provider.ItemProviderAdapter#getChildren(java.lang.Object)
	 */
	@Override
	public Collection<?> getChildren(Object object) {
		Collection<?> children = super.getChildren(object);
		Comparator<CSTNode> cstNodeComparator = new Comparator<CSTNode>() {
			public int compare(CSTNode n0, CSTNode n1) {
				if (n0.getStartPosition() < n1.getStartPosition()) {
					return -1;
				}
				return 1;
			}
		};
		List<CSTNode> orderedCollection = new ArrayList<CSTNode>(children.size());
		for (Object child : children) {
			if (child instanceof CSTNode
					&& !(child instanceof org.eclipse.acceleo.parser.cst.TextExpression
							|| child instanceof org.eclipse.acceleo.parser.cst.ModelExpression || child instanceof org.eclipse.acceleo.parser.cst.Comment)) {
				orderedCollection.add((CSTNode)child);
			}
		}
		Collections.sort(orderedCollection, cstNodeComparator);

		List<Object> result = new ArrayList<Object>();
		if (object instanceof Module) {
			Module module = (Module)object;
			if (module.getImports().size() > 0) {
				for (CSTNode cstNode : orderedCollection) {
					if (!(cstNode instanceof ModuleImportsValue)) {
						result.add(cstNode);
					}
				}

				for (int i = 0; i < result.size(); i++) {
					CSTNode node = (CSTNode)result.get(i);
					if (node instanceof Template || node instanceof Query) {
						result.add(i, new AcceleoOutlineImportContainer(module));
						break;
					}
				}
			}
		}

		if (result.size() > 0) {
			return result;
		}
		return orderedCollection;
	}
}

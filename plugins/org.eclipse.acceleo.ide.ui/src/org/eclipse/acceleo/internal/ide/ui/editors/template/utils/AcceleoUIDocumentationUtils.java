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
package org.eclipse.acceleo.internal.ide.ui.editors.template.utils;

import java.io.IOException;

import org.eclipse.acceleo.common.IAcceleoConstants;
import org.eclipse.acceleo.ide.ui.AcceleoUIActivator;
import org.eclipse.acceleo.internal.ide.ui.resource.AcceleoUIResourceSet;
import org.eclipse.acceleo.model.mtl.Documentation;
import org.eclipse.acceleo.model.mtl.DocumentedElement;
import org.eclipse.acceleo.model.mtl.Macro;
import org.eclipse.acceleo.model.mtl.Module;
import org.eclipse.acceleo.model.mtl.ModuleDocumentation;
import org.eclipse.acceleo.model.mtl.ModuleElement;
import org.eclipse.acceleo.model.mtl.ModuleElementDocumentation;
import org.eclipse.acceleo.model.mtl.ParameterDocumentation;
import org.eclipse.acceleo.model.mtl.Query;
import org.eclipse.acceleo.model.mtl.Template;
import org.eclipse.acceleo.model.mtl.VisibilityKind;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.common.util.WrappedException;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EOperation;
import org.eclipse.emf.ecore.EParameter;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.ocl.ecore.Variable;
import org.eclipse.swt.graphics.Image;

/**
 * This class contains a collection of utility methods to manipulate the documentation.
 * 
 * @author <a href="mailto:stephane.begaudeau@obeo.fr">Stephane Begaudeau</a>
 */
public final class AcceleoUIDocumentationUtils {

	/**
	 * the default begin.
	 */
	public static final String DEFAULT_BEGIN = "["; //$NON-NLS-1$

	/**
	 * The default end.
	 */
	public static final String DEFAULT_END = "] "; //$NON-NLS-1$

	/**
	 * The default variable separator.
	 */
	public static final String DEFAULT_VARIABLE_SEPARATOR = " : "; //$NON-NLS-1$

	/**
	 * The HTML bold begin.
	 */
	public static final String HTML_BOLD_BEGIN = "<b>"; //$NON-NLS-1$

	/**
	 * The HTML bold end.
	 */
	public static final String HTML_BOLD_END = "</b>"; //$NON-NLS-1$

	/**
	 * A tabulation.
	 */
	public static final String TAB = "<dd>"; //$NON-NLS-1$

	/**
	 * The end of line.
	 */
	public static final String EOL = "<br/>"; //$NON-NLS-1$

	/**
	 * The constructor.
	 */
	private AcceleoUIDocumentationUtils() {
		// prevent instantiation
	}

	/**
	 * Returns the documentation of the element or its signature if there is no documentation available.
	 * 
	 * @param element
	 *            The documented element.
	 * @return The documentation of the element or its signature if there is no documentation available.
	 */
	public static String getDocumentation(DocumentedElement element) {
		String result = ""; //$NON-NLS-1$
		Documentation documentation = element.getDocumentation();
		if (documentation == null) {
			documentation = AcceleoUIDocumentationUtils.getDocumentationFromFile(element);
		}

		if (documentation != null) {
			result = AcceleoUIDocumentationUtils.getTextFrom(documentation);
		} else {
			result = AcceleoUIDocumentationUtils.getSignatureFrom(element);
		}

		/*
		 * [Doc unloading] unloading resource loaded from AcceleoUIDocumentationUtils#getDocumentationFromFile
		 */
		// if (documentation != null && documentation.eResource() != null
		// && documentation.eResource() != element.eResource()) {
		// documentation.eResource().unload();
		// }

		return result;
	}

	/**
	 * Returns the image that describe the template for the completion window.
	 * 
	 * @param template
	 *            The template
	 * @return The image
	 */
	public static Image getCompletionImage(Template template) {
		VisibilityKind visibility = template.getVisibility();
		Image image = null;
		switch (visibility) {
			case PRIVATE:
				image = AcceleoUIActivator.getDefault().getImage(
						IAcceleoContantsImage.TemplateEditor.Completion.TEMPLATE_PRIVATE);
				break;
			case PROTECTED:
				image = AcceleoUIActivator.getDefault().getImage(
						IAcceleoContantsImage.TemplateEditor.Completion.TEMPLATE_PROTECTED);
				break;
			case PUBLIC:
				image = AcceleoUIActivator.getDefault().getImage(
						IAcceleoContantsImage.TemplateEditor.Completion.TEMPLATE_PUBLIC);
				break;
			default:
				image = AcceleoUIActivator.getDefault().getImage(
						IAcceleoContantsImage.TemplateEditor.Completion.TEMPLATE_PUBLIC);
				break;
		}
		return image;
	}

	/**
	 * Returns the image that describe the query for the completion window.
	 * 
	 * @param query
	 *            The query
	 * @return The image
	 */
	public static Image getCompletionImage(Query query) {
		VisibilityKind visibility = query.getVisibility();
		Image image = null;
		switch (visibility) {
			case PRIVATE:
				image = AcceleoUIActivator.getDefault().getImage(
						IAcceleoContantsImage.TemplateEditor.Completion.COMPLETION_QUERY_PRIVATE);
				break;
			case PROTECTED:
				image = AcceleoUIActivator.getDefault().getImage(
						IAcceleoContantsImage.TemplateEditor.Completion.COMPLETION_QUERY_PROTECTED);
				break;
			case PUBLIC:
				image = AcceleoUIActivator.getDefault().getImage(
						IAcceleoContantsImage.TemplateEditor.Completion.COMPLETION_QUERY_PUBLIC);
				break;
			default:
				image = AcceleoUIActivator.getDefault().getImage(
						IAcceleoContantsImage.TemplateEditor.Completion.COMPLETION_QUERY_PUBLIC);
				break;
		}
		return image;
	}

	/**
	 * Gets the documentation of the documented element from the 'emtl' file instead of the AST.
	 * 
	 * @param element
	 *            The documented element
	 * @return The documentation or null if there is none
	 */
	public static Documentation getDocumentationFromFile(DocumentedElement element) {
		/*
		 * [Doc unloading] The resources loaded from here will be unloaded later when their information will
		 * have been extracted. Search for comments tagged [Doc unloading] for exact locations.
		 */
		Resource eResource = element.eResource();
		Documentation documentation = null;
		// No use trying to load http schemes
		if (eResource != null && !"http".equals(eResource.getURI().scheme())) { //$NON-NLS-1$
			try {
				final EObject eObject;
				if (!element.eIsProxy()) {
					eObject = EcoreUtil.getRootContainer(element);
				} else {
					eObject = AcceleoUIResourceSet.getResource(eResource.getURI());
				}
				if (eObject instanceof Module) {
					Module module = (Module)eObject;
					if (element instanceof Module) {
						documentation = module.getDocumentation();
					}
					if (element instanceof ModuleElement) {
						documentation = getDocumentationFromModule(module, element);
					}
				}
			} catch (IOException e) {
				// The emtl file does not exist, we will return null it is not a problem, do not log anything
			} catch (WrappedException e) {
				// The file has an invalid URI, or if there is another EMF problem with it, we return null
			}
		}
		return documentation;
	}

	/**
	 * Finds the documentation of the given element in the module.
	 * 
	 * @param module
	 *            The module
	 * @param element
	 *            The documented element for which we want the documentation
	 * @return The documentation or null if there is none
	 */
	private static Documentation getDocumentationFromModule(Module module, DocumentedElement element) {
		EList<EObject> eContents = module.eContents();
		for (EObject eObject : eContents) {
			URI uriObject = EcoreUtil.getURI(eObject);
			URI uriElement = EcoreUtil.getURI(element);
			if (eObject instanceof DocumentedElement && uriObject != null && uriObject.equals(uriElement)) {
				DocumentedElement documentedElement = (DocumentedElement)eObject;
				return documentedElement.getDocumentation();
			}
		}

		return null;
	}

	/**
	 * Returns the text from the EOperation.
	 * 
	 * @param eOperation
	 *            The eOperation
	 * @return The text from the EOperation.
	 */
	public static String getTextFrom(final EOperation eOperation) {
		StringBuffer buffer = new StringBuffer();

		String parameters = ""; //$NON-NLS-1$
		for (EParameter eParameter : eOperation.getEParameters()) {
			parameters += eParameter.getEType().getName() + ' ' + eParameter.getName();
			if (!eParameter.equals(eOperation.getEParameters().get(eOperation.getEParameters().size() - 1))) {
				parameters += ", "; //$NON-NLS-1$
			}
		}

		String type = ""; //$NON-NLS-1$
		if (eOperation.getEType() != null) {
			type = eOperation.getEType().getName();
		}
		buffer.append(HTML_BOLD_BEGIN + type + ' ' + eOperation.getName() + '(' + parameters + ')'
				+ HTML_BOLD_END + EOL);

		String name = eOperation.getName();
		if (name.contains("=")) { //$NON-NLS-1$
			name = name.replaceAll("=", "EQUALS"); //$NON-NLS-1$//$NON-NLS-2$
		}

		String documentation = AcceleoUIDocumentationMessages.getString(name + '_'
				+ eOperation.getEParameters().size());
		if (!documentation.startsWith("!") && !documentation.endsWith("!")) { //$NON-NLS-1$ //$NON-NLS-2$!
			buffer.append(documentation.replaceAll("\n", EOL) + EOL); //$NON-NLS-1$
		} else {
			return getSignatureFrom((EObject)eOperation);
		}

		return buffer.toString();
	}

	/**
	 * Returns the text from the documentation.
	 * 
	 * @param documentation
	 *            The documentation
	 * @return The text from the documentation.
	 */
	public static String getTextFrom(final Documentation documentation) {
		String result = ""; //$NON-NLS-1$
		if (documentation instanceof ModuleElementDocumentation) {
			result = AcceleoUIDocumentationUtils.getTextFrom((ModuleElementDocumentation)documentation);
		} else if (documentation instanceof ModuleDocumentation) {
			result = AcceleoUIDocumentationUtils.getTextFrom((ModuleDocumentation)documentation);
		}
		if (documentation.getDocumentedElement().isDeprecated()) {
			result += computeDepreciationReason(documentation);
		}
		return result;
	}

	/**
	 * Returns the text from the documentation.
	 * 
	 * @param templateDocumentation
	 *            The documentation
	 * @return The text from the documentation.
	 */
	private static String getTextFrom(final ModuleElementDocumentation templateDocumentation) {
		StringBuffer res = new StringBuffer();
		res.append(HTML_BOLD_BEGIN + getSignatureFrom(templateDocumentation.getDocumentedElement())
				+ HTML_BOLD_END + EOL + EOL);
		res.append(computeDescriptionFrom(templateDocumentation) + EOL);

		EList<ParameterDocumentation> parametersDocumentation = templateDocumentation
				.getParametersDocumentation();
		if (parametersDocumentation.size() > 0) {
			res.append(AcceleoUIDocumentationUtils.getParameterTextFrom(templateDocumentation));
		}

		DocumentedElement element = templateDocumentation.getDocumentedElement();
		if (element instanceof Template) {
			Template template = (Template)element;
			EList<Template> overrides = template.getOverrides();
			for (Template override : overrides) {
				res.append(EOL + HTML_BOLD_BEGIN + "Overrides:" + HTML_BOLD_END + EOL); //$NON-NLS-1$
				if (override.eContainer() instanceof Module) {
					Module module = (Module)override.eContainer();
					res.append(TAB + "Template " + override.getName() + " in Module " + module.getName() //$NON-NLS-1$ //$NON-NLS-2$
							+ EOL);
				}
			}
		}

		return res.toString();
	}

	/**
	 * Gets the text of the documentation of the parameters.
	 * 
	 * @param moduleElementDocumentation
	 *            the module element documentation
	 * @return The text of the documentation of the parameters
	 */
	private static String getParameterTextFrom(final ModuleElementDocumentation moduleElementDocumentation) {
		StringBuffer res = new StringBuffer();

		DocumentedElement documentedElement = moduleElementDocumentation.getDocumentedElement();
		EList<Variable> parameters = null;

		if (documentedElement instanceof Template) {
			Template template = (Template)documentedElement;
			parameters = template.getParameter();
		}
		if (documentedElement instanceof Query) {
			Query query = (Query)documentedElement;
			parameters = query.getParameter();
		}
		if (documentedElement instanceof Macro) {
			Macro macro = (Macro)documentedElement;
			parameters = macro.getParameter();
		}

		if (parameters != null) {
			if (parameters.size() == 1) {
				res.append(EOL + HTML_BOLD_BEGIN + "Parameter:" + HTML_BOLD_END + EOL); //$NON-NLS-1$
			} else {
				res.append(EOL + HTML_BOLD_BEGIN + "Parameters:" + HTML_BOLD_END + EOL); //$NON-NLS-1$
			}
			for (ParameterDocumentation parameterDocumentation : moduleElementDocumentation
					.getParametersDocumentation()) {
				res.append(TAB + HTML_BOLD_BEGIN + parameterDocumentation.getName() + '('
						+ getTypeOfParameter(parameters, parameterDocumentation.getName()) + ')'
						+ HTML_BOLD_END + ' ' + parameterDocumentation.getBody().getValue() + EOL);
			}
		}

		return res.toString();
	}

	/**
	 * Returns the name of the type of the parameter with the given name.
	 * 
	 * @param parameters
	 *            The list of parameters
	 * @param parameterName
	 *            The name of the parameter we are looking for
	 * @return The name of the type of the parameter
	 */
	private static String getTypeOfParameter(EList<Variable> parameters, String parameterName) {
		for (Variable variable : parameters) {
			if (variable != null && variable.getType() != null && variable.getName().equals(parameterName)) {
				return variable.getType().getName();
			}
		}
		return ""; //$NON-NLS-1$
	}

	/**
	 * Returns the text from the documentation.
	 * 
	 * @param moduleDocumentation
	 *            The documentation
	 * @return The text from the documentation.
	 */
	private static String getTextFrom(final ModuleDocumentation moduleDocumentation) {
		StringBuffer res = new StringBuffer();

		res.append(HTML_BOLD_BEGIN + getSignatureFrom(moduleDocumentation.getDocumentedElement())
				+ HTML_BOLD_END + EOL);
		res.append(computeDescriptionFrom(moduleDocumentation) + EOL);
		if (moduleDocumentation.getVersion() != null && !"".equals(moduleDocumentation.getVersion())) { //$NON-NLS-1$
			res.append(HTML_BOLD_BEGIN + "Version:" + HTML_BOLD_END + EOL); //$NON-NLS-1$
			res.append(TAB + moduleDocumentation.getVersion() + '\n');
		}
		if (moduleDocumentation.getAuthor() != null && !"".equals(moduleDocumentation.getAuthor())) { //$NON-NLS-1$
			res.append(HTML_BOLD_BEGIN + "Author:" + HTML_BOLD_END + EOL); //$NON-NLS-1$
			res.append(TAB + moduleDocumentation.getAuthor() + EOL);
		}

		return res.toString();
	}

	/**
	 * Finds the description in the documentation body.
	 * 
	 * @param documentation
	 *            The documentation
	 * @return The description
	 */
	private static String computeDescriptionFrom(Documentation documentation) {
		String value = documentation.getBody().getValue();
		int index = value.indexOf("@"); //$NON-NLS-1$
		if (index != -1) {
			return value.substring(0, index);
		}
		return value;
	}

	/**
	 * Finds the reason for the depreciation.
	 * 
	 * @param documentation
	 *            The documentation
	 * @return The reason for the depreciation
	 */
	private static String computeDepreciationReason(Documentation documentation) {
		String value = documentation.getBody().getValue();
		int index = value.indexOf(IAcceleoConstants.TAG_DEPRECATED);
		if (index != -1) {
			value = HTML_BOLD_BEGIN + "Deprecated:" + EOL + HTML_BOLD_END //$NON-NLS-1$
					+ TAB + value.substring(index + IAcceleoConstants.TAG_DEPRECATED.length()).trim();
		} else {
			DocumentedElement element = documentation.getDocumentedElement();
			if (element instanceof ModuleElement && element.eContainer() instanceof Module) {
				Module module = (Module)element.eContainer();
				if (module.isDeprecated()) {
					value = HTML_BOLD_BEGIN + "Deprecated:" + EOL + HTML_BOLD_END + TAB + "See module \"" //$NON-NLS-1$ //$NON-NLS-2$
							+ module.getName() + "\""; //$NON-NLS-1$
				}
			} else {
				value = ""; //$NON-NLS-1$
			}
		}
		return value;
	}

	/**
	 * Returns the signature of the object.
	 * 
	 * @param object
	 *            The object
	 * @return The signature of the object
	 */
	public static String getSignatureFrom(final EObject object) {
		StringBuffer res = new StringBuffer();
		res.append(DEFAULT_BEGIN);
		res.append(object.eClass().getName());
		res.append(DEFAULT_END);
		if (object instanceof Module) {
			res.append(AcceleoUIDocumentationUtils.getSignatureFrom((Module)object));
		} else if (object instanceof Variable) {
			res.append(AcceleoUIDocumentationUtils.getSignatureFrom((Variable)object));
		} else if (object instanceof ModuleElement) {
			res.append(AcceleoUIDocumentationUtils.getSignatureFrom((ModuleElement)object));
		} else if (object instanceof EOperation) {
			res.append(AcceleoUIDocumentationUtils.getSignatureFrom((EOperation)object));
		} else if (object instanceof EStructuralFeature) {
			res.append(AcceleoUIDocumentationUtils.getSignatureFrom((EStructuralFeature)object));
		} else if (object instanceof EClass) {
			res.append(AcceleoUIDocumentationUtils.getSignatureFrom((EClass)object));
		}
		return res.toString();
	}

	/**
	 * Returns the signature of the module.
	 * 
	 * @param module
	 *            The module
	 * @return The signature of the module
	 */
	private static String getSignatureFrom(final Module module) {
		return module.getName();
	}

	/**
	 * Returns the signature of the variable.
	 * 
	 * @param variable
	 *            The variable
	 * @return The signature of the variable
	 */
	private static String getSignatureFrom(final Variable variable) {
		StringBuffer res = new StringBuffer();
		res.append(variable.getName());
		if (variable.getType() != null) {
			res.append(DEFAULT_VARIABLE_SEPARATOR);
			res.append(variable.getType().getName());
		}
		return res.toString();
	}

	/**
	 * Returns the signature of the module element.
	 * 
	 * @param moduleElement
	 *            The module element
	 * @return The signature of the module element
	 */
	private static String getSignatureFrom(final ModuleElement moduleElement) {
		StringBuffer res = new StringBuffer();
		res.append(moduleElement.getVisibility().getName() + ' ');
		res.append(moduleElement.getName());
		if (moduleElement instanceof Template) {
			res.append(AcceleoUIDocumentationUtils.getSignatureFrom((Template)moduleElement));
		} else if (moduleElement instanceof Macro) {
			res.append(AcceleoUIDocumentationUtils.getSignatureFrom((Macro)moduleElement));
		} else if (moduleElement instanceof Query) {
			res.append(AcceleoUIDocumentationUtils.getSignatureFrom((Query)moduleElement));
		}
		return res.toString();
	}

	/**
	 * Returns the signature of the template.
	 * 
	 * @param template
	 *            The template
	 * @return The signature of the template
	 */
	private static String getSignatureFrom(final Template template) {
		StringBuffer res = new StringBuffer();
		res.append('(');
		boolean first = true;
		for (Variable iVariable : template.getParameter()) {
			if (first) {
				first = false;
			} else {
				res.append(',');
			}
			if (iVariable.getType() != null) {
				res.append(iVariable.getType().getName());
			}
		}
		res.append(')');
		return res.toString();
	}

	/**
	 * Returns the signature of the macro.
	 * 
	 * @param macro
	 *            The macro
	 * @return The signature of the macro
	 */
	private static String getSignatureFrom(final Macro macro) {
		StringBuffer res = new StringBuffer();
		res.append('(');
		boolean first = true;
		for (Variable iVariable : macro.getParameter()) {
			if (first) {
				first = false;
			} else {
				res.append(',');
			}
			if (iVariable.getType() != null) {
				res.append(iVariable.getType().getName());
			}
		}
		res.append(')');
		if (macro.getType() != null) {
			res.append(DEFAULT_VARIABLE_SEPARATOR);
			res.append(macro.getType().getName());
		}
		return res.toString();
	}

	/**
	 * Returns the signature of the query.
	 * 
	 * @param query
	 *            The query
	 * @return The signature of the query
	 */
	private static String getSignatureFrom(final Query query) {
		StringBuffer res = new StringBuffer();
		res.append('(');
		boolean first = true;
		for (Variable iVariable : query.getParameter()) {
			if (first) {
				first = false;
			} else {
				res.append(',');
			}
			if (iVariable.getType() != null) {
				res.append(iVariable.getType().getName());
			}
		}
		res.append(')');
		if (query.getType() != null) {
			res.append(DEFAULT_VARIABLE_SEPARATOR);
			res.append(query.getType().getName());
		}
		return res.toString();
	}

	/**
	 * Returns the signature of the operation.
	 * 
	 * @param operation
	 *            The operation
	 * @return The signature of the operation
	 */
	private static String getSignatureFrom(final EOperation operation) {
		StringBuffer res = new StringBuffer();
		res.append(operation.getName());
		res.append('(');
		boolean first = true;
		for (EParameter eParameter : operation.getEParameters()) {
			if (first) {
				first = false;
			} else {
				res.append(',');
			}
			if (eParameter.getEType() != null) {
				res.append(eParameter.getEType().getName());
			}
		}
		res.append(')');
		if (operation.getEType() != null) {
			res.append(DEFAULT_VARIABLE_SEPARATOR);
			res.append(operation.getEType().getName());
		}
		return res.toString();
	}

	/**
	 * Returns the signature of the feature.
	 * 
	 * @param feature
	 *            The feature
	 * @return The signature of the feature
	 */
	private static String getSignatureFrom(final EStructuralFeature feature) {
		StringBuffer res = new StringBuffer();
		res.append(feature.getName());
		if (feature.getEType() != null) {
			res.append(DEFAULT_VARIABLE_SEPARATOR);
			res.append(feature.getEType().getName());
			res.append(" ["); //$NON-NLS-1$
			res.append(feature.getLowerBound());
			res.append(".."); //$NON-NLS-1$
			if (feature.getUpperBound() == -1) {
				res.append("*"); //$NON-NLS-1$
			} else {
				res.append(feature.getUpperBound());
			}
			res.append("]"); //$NON-NLS-1$
		}
		return res.toString();
	}

	/**
	 * Returns the signature of the eClass.
	 * 
	 * @param eClass
	 *            The eClass
	 * @return The signature of the eClass
	 */
	private static String getSignatureFrom(final EClass eClass) {
		return eClass.getName();
	}
}

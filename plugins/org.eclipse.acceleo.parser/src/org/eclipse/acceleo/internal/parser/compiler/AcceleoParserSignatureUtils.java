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
package org.eclipse.acceleo.internal.parser.compiler;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import org.eclipse.acceleo.common.IAcceleoConstants;
import org.eclipse.acceleo.common.utils.ModelUtils;
import org.eclipse.acceleo.model.mtl.Module;
import org.eclipse.acceleo.model.mtl.ModuleElement;
import org.eclipse.acceleo.model.mtl.Query;
import org.eclipse.acceleo.model.mtl.Template;
import org.eclipse.acceleo.model.mtl.TypedModel;
import org.eclipse.acceleo.parser.cst.ModuleExtendsValue;
import org.eclipse.emf.common.util.WrappedException;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.ocl.ecore.Variable;

/**
 * Utility class to manage the signature of Acceleo modules.
 * 
 * @author <a href="mailto:stephane.begaudeau@obeo.fr">Stephane Begaudeau</a>
 * @since 3.2
 */
public final class AcceleoParserSignatureUtils {

	/**
	 * The constructor.
	 */
	private AcceleoParserSignatureUtils() {
		// hides constructor.
	}

	/**
	 * This will create and return the list of signatures for the given Acceleo module. The list will contain
	 * all signatures in a particular order :
	 * <ul>
	 * <li>The module itself (in the form
	 * <code>&lt;moduleName&gt;(&lt;metamodeluri&gt[,&lt;metamodeluri&gt;]*)</code>).</li>
	 * <li>All of the <b>template</b> signatures (in the form
	 * <code>&lt;visibility&gt; &lt;templateName&gt;(&lt;templateParam&gt;[,&lt;templateParam&gt;]*)</code>,
	 * "templateParam" being of the form <code>&lt;paramName&gt;:&lt;paramType&gt;</code>).</li>
	 * <li>All of the <b>query</b> signatures (in the form
	 * <code>&lt;visibility&gt; &lt;queryName&gt;(&lt;queryParam&gt;[,&lt;queryParam&gt;]*):&lt;queryType&gt;</code>
	 * , "queryParam" being of the form <code>&lt;paramName&gt;:&lt;paramType&gt;</code>).</li>
	 * </ul>
	 * 
	 * @param file
	 *            The module from which to extract the signatures.
	 * @param resourceSet
	 *            The resource set in which the module will be loaded.
	 * @return The list of signatures for the given Acceleo module.
	 */
	public static List<String> signature(File file, final ResourceSet resourceSet) {
		List<String> previousSignatures = new ArrayList<String>();
		try {
			EObject previousRoot = ModelUtils.load(file, resourceSet);
			if (previousRoot instanceof Module) {
				previousSignatures = createSignatureList((Module)previousRoot);
			}
		} catch (IOException e) {
			// Swallow this : we just didn't have a precompiled state
		} catch (WrappedException e) {
			// Swallow this : we just didn't have a precompiled state
			// CHECKSTYLE:OFF
		} catch (RuntimeException e) {
			// CHECKSTYLE:ON
			// Swallow this : we just didn't have a precompiled state (maven build)
		} finally {
			Thread unloadThread = new Thread() {
				/**
				 * {@inheritDoc}
				 * 
				 * @see java.lang.Thread#run()
				 */
				@Override
				public void run() {
					for (Resource res : resourceSet.getResources()) {
						res.unload();
					}
					resourceSet.getResources().clear();
				}
			};
			unloadThread.start();
		}
		return previousSignatures;
	}

	/**
	 * This will create and return the list of signatures for the given Acceleo module. The list will contain
	 * all signatures in a particular order :
	 * <ul>
	 * <li>The module itself (in the form
	 * <code>&lt;moduleName&gt;(&lt;metamodeluri&gt[,&lt;metamodeluri&gt;]*)</code>).</li>
	 * <li>All of the <b>template</b> signatures (in the form
	 * <code>&lt;visibility&gt; &lt;templateName&gt;(&lt;templateParam&gt;[,&lt;templateParam&gt;]*)</code>,
	 * "templateParam" being of the form <code>&lt;paramName&gt;:&lt;paramType&gt;</code>).</li>
	 * <li>All of the <b>query</b> signatures (in the form
	 * <code>&lt;visibility&gt; &lt;queryName&gt;(&lt;queryParam&gt;[,&lt;queryParam&gt;]*):&lt;queryType&gt;</code>
	 * , "queryParam" being of the form <code>&lt;paramName&gt;:&lt;paramType&gt;</code>).</li>
	 * </ul>
	 * 
	 * @param module
	 *            The module from which to extract the signatures.
	 * @return The list of signatures for the given Acceleo module.
	 */
	public static List<String> signature(org.eclipse.acceleo.parser.cst.Module module) {
		return createSignatureList(module);
	}

	/**
	 * This will create and return the list of signatures for the given Acceleo module. The list will contain
	 * all signatures in a particular order :
	 * <ul>
	 * <li>The module itself (in the form
	 * <code>&lt;moduleName&gt;(&lt;metamodeluri&gt[,&lt;metamodeluri&gt;]*)</code>).</li>
	 * <li>All of the <b>template</b> signatures (in the form
	 * <code>&lt;visibility&gt; &lt;templateName&gt;(&lt;templateParam&gt;[,&lt;templateParam&gt;]*)</code>,
	 * "templateParam" being of the form <code>&lt;paramName&gt;:&lt;paramType&gt;</code>).</li>
	 * <li>All of the <b>query</b> signatures (in the form
	 * <code>&lt;visibility&gt; &lt;queryName&gt;(&lt;queryParam&gt;[,&lt;queryParam&gt;]*):&lt;queryType&gt;</code>
	 * , "queryParam" being of the form <code>&lt;paramName&gt;:&lt;paramType&gt;</code>).</li>
	 * </ul>
	 * 
	 * @param module
	 *            The module from which to extract the signatures.
	 * @return The list of signatures for the given Acceleo module.
	 */
	private static List<String> createSignatureList(org.eclipse.acceleo.parser.cst.Module module) {
		if (module == null) {
			return Collections.emptyList();
		}

		final List<String> signatures = new ArrayList<String>();

		StringBuilder moduleSignature = new StringBuilder();
		if (module.getName() != null) {
			moduleSignature.append(module.getName());
		}
		moduleSignature.append('(');
		Iterator<org.eclipse.acceleo.parser.cst.TypedModel> modelIterator = module.getInput().iterator();
		while (modelIterator.hasNext()) {
			org.eclipse.acceleo.parser.cst.TypedModel model = modelIterator.next();
			for (EPackage packaje : model.getTakesTypesFrom()) {
				moduleSignature.append(packaje.getNsURI());
			}
			if (modelIterator.hasNext()) {
				moduleSignature.append(',');
			}
		}
		moduleSignature.append(')');

		Iterator<ModuleExtendsValue> iterator = module.getExtends().iterator();
		int c = 0;
		while (iterator.hasNext()) {
			ModuleExtendsValue moduleExtendsValue = iterator.next();
			if (c == 0) {
				moduleSignature.append(" extends "); //$NON-NLS-1$
			}
			String name = moduleExtendsValue.getName();
			if (name.contains(IAcceleoConstants.NAMESPACE_SEPARATOR)) {
				int indexOf = name.lastIndexOf(IAcceleoConstants.NAMESPACE_SEPARATOR);
				name = name.substring(indexOf + IAcceleoConstants.NAMESPACE_SEPARATOR.length());
			}
			moduleSignature.append(name);
			if (iterator.hasNext()) {
				moduleSignature.append(',');
			}
			c++;
		}

		final List<String> templateSignatures = new ArrayList<String>();
		final List<String> querySignatures = new ArrayList<String>();
		for (org.eclipse.acceleo.parser.cst.ModuleElement moduleElement : module.getOwnedModuleElement()) {
			if (moduleElement instanceof org.eclipse.acceleo.parser.cst.Template) {
				templateSignatures
						.add(createTemplateSignature((org.eclipse.acceleo.parser.cst.Template)moduleElement));
			} else if (moduleElement instanceof org.eclipse.acceleo.parser.cst.Query) {
				querySignatures
						.add(createQuerySignature((org.eclipse.acceleo.parser.cst.Query)moduleElement));
			}
		}

		signatures.add(moduleSignature.toString());
		signatures.addAll(templateSignatures);
		signatures.addAll(querySignatures);

		return signatures;
	}

	/**
	 * This will create a String representing the signature of the given template. The form of this String is
	 * fixed and will be :
	 * <code>&lt;visibility&gt; &lt;templateName&gt;(&lt;templateParam&gt;[,&lt;templateParam&gt;]*)</code>,
	 * "templateParam" being of the form <code>&lt;paramName&gt;:&lt;paramType&gt;</code>.
	 * 
	 * @param template
	 *            The template for which we need a signature.
	 * @return The signature of <code>template</code>.
	 */
	private static String createTemplateSignature(org.eclipse.acceleo.parser.cst.Template template) {
		StringBuilder signature = new StringBuilder();
		signature.append("Template "); //$NON-NLS-1$
		if (template.getVisibility() != null) {
			signature.append(template.getVisibility().getLiteral());
		}
		signature.append(' ');
		if (template.getName() != null) {
			signature.append(template.getName());
		}
		signature.append('(');
		Iterator<org.eclipse.acceleo.parser.cst.Variable> paramIterator = template.getParameter().iterator();
		while (paramIterator.hasNext()) {
			org.eclipse.acceleo.parser.cst.Variable param = paramIterator.next();
			if (param.getName() != null) {
				signature.append(param.getName());
			}
			signature.append(':');
			if (param.getType() != null) {
				signature.append(param.getType());
			}
			if (paramIterator.hasNext()) {
				signature.append(',');
			}
		}
		signature.append(')');
		return signature.toString();
	}

	/**
	 * This will create a String representing the signature of the given query. The form of this String is
	 * fixed and will be :
	 * <code>&lt;visibility&gt; &lt;queryName&gt;(&lt;queryParam&gt;[,&lt;queryParam&gt;]*):&lt;queryType&gt;</code>
	 * , "queryParam" being of the form <code>&lt;paramName&gt;:&lt;paramType&gt;</code>
	 * 
	 * @param query
	 *            The query for which we need a signature.
	 * @return The signature of <code>query</code>.
	 */
	private static String createQuerySignature(org.eclipse.acceleo.parser.cst.Query query) {
		StringBuilder signature = new StringBuilder();
		signature.append("Query "); //$NON-NLS-1$
		if (query.getVisibility() != null) {
			signature.append(query.getVisibility().getLiteral());
		}
		signature.append(' ');
		if (query.getName() != null) {
			signature.append(query.getName());
		}
		signature.append('(');
		Iterator<org.eclipse.acceleo.parser.cst.Variable> paramIterator = query.getParameter().iterator();
		while (paramIterator.hasNext()) {
			org.eclipse.acceleo.parser.cst.Variable param = paramIterator.next();
			if (param.getName() != null) {
				signature.append(param.getName());
			}
			signature.append(':');
			if (param.getType() != null) {
				signature.append(param.getType());
			}
			if (paramIterator.hasNext()) {
				signature.append(',');
			}
		}
		signature.append(')');
		signature.append(':');
		if (query.getType() != null) {
			signature.append(query.getType());
		}
		return signature.toString();
	}

	/**
	 * This will create and return the list of signatures for the given Acceleo module. The list will contain
	 * all signatures in a particular order :
	 * <ul>
	 * <li>The module itself (in the form
	 * <code>&lt;moduleName&gt;(&lt;metamodeluri&gt[,&lt;metamodeluri&gt;]*)</code>).</li>
	 * <li>All of the <b>template</b> signatures (in the form
	 * <code>&lt;visibility&gt; &lt;templateName&gt;(&lt;templateParam&gt;[,&lt;templateParam&gt;]*)</code>,
	 * "templateParam" being of the form <code>&lt;paramName&gt;:&lt;paramType&gt;</code>).</li>
	 * <li>All of the <b>query</b> signatures (in the form
	 * <code>&lt;visibility&gt; &lt;queryName&gt;(&lt;queryParam&gt;[,&lt;queryParam&gt;]*):&lt;queryType&gt;</code>
	 * , "queryParam" being of the form <code>&lt;paramName&gt;:&lt;paramType&gt;</code>).</li>
	 * </ul>
	 * 
	 * @param module
	 *            The module from which to extract the signatures.
	 * @return The list of signatures for the given Acceleo module.
	 */
	private static List<String> createSignatureList(Module module) {
		if (module == null) {
			return Collections.emptyList();
		}

		final List<String> signatures = new ArrayList<String>();

		StringBuilder moduleSignature = new StringBuilder();
		if (module.getName() != null) {
			moduleSignature.append(module.getName());
		}
		moduleSignature.append('(');
		Iterator<TypedModel> modelIterator = module.getInput().iterator();
		while (modelIterator.hasNext()) {
			TypedModel model = modelIterator.next();
			for (EPackage packaje : model.getTakesTypesFrom()) {
				moduleSignature.append(packaje.getNsURI());
			}
			if (modelIterator.hasNext()) {
				moduleSignature.append(',');
			}
		}
		moduleSignature.append(')');

		Iterator<Module> iterator = module.getExtends().iterator();
		int c = 0;
		while (iterator.hasNext()) {
			Module extendedModule = iterator.next();
			if (c == 0) {
				moduleSignature.append(" extends "); //$NON-NLS-1$
			}
			moduleSignature.append(extendedModule.getName());
			if (iterator.hasNext()) {
				moduleSignature.append(',');
			}
			c++;
		}

		final List<String> templateSignatures = new ArrayList<String>();
		final List<String> querySignatures = new ArrayList<String>();
		for (ModuleElement moduleElement : module.getOwnedModuleElement()) {
			if (moduleElement instanceof Template) {
				templateSignatures.add(createTemplateSignature((Template)moduleElement));
			} else if (moduleElement instanceof Query) {
				querySignatures.add(createQuerySignature((Query)moduleElement));
			}
		}

		signatures.add(moduleSignature.toString());
		signatures.addAll(templateSignatures);
		signatures.addAll(querySignatures);

		return signatures;
	}

	/**
	 * This will create a String representing the signature of the given template. The form of this String is
	 * fixed and will be :
	 * <code>&lt;visibility&gt; &lt;templateName&gt;(&lt;templateParam&gt;[,&lt;templateParam&gt;]*)</code>,
	 * "templateParam" being of the form <code>&lt;paramName&gt;:&lt;paramType&gt;</code>.
	 * 
	 * @param template
	 *            The template for which we need a signature.
	 * @return The signature of <code>template</code>.
	 */
	private static String createTemplateSignature(Template template) {
		StringBuilder signature = new StringBuilder();
		signature.append("Template "); //$NON-NLS-1$
		if (template.getVisibility() != null) {
			signature.append(template.getVisibility().getLiteral());
		}
		signature.append(' ');
		if (template.getName() != null) {
			signature.append(template.getName());
		}
		signature.append('(');
		Iterator<Variable> paramIterator = template.getParameter().iterator();
		while (paramIterator.hasNext()) {
			Variable param = paramIterator.next();
			if (param.getName() != null) {
				signature.append(param.getName());
			}
			signature.append(':');
			if (param.getType() != null) {
				signature.append(param.getType().getName());
			}
			if (paramIterator.hasNext()) {
				signature.append(',');
			}
		}
		signature.append(')');
		return signature.toString();
	}

	/**
	 * This will create a String representing the signature of the given query. The form of this String is
	 * fixed and will be :
	 * <code>&lt;visibility&gt; &lt;queryName&gt;(&lt;queryParam&gt;[,&lt;queryParam&gt;]*):&lt;queryType&gt;</code>
	 * , "queryParam" being of the form <code>&lt;paramName&gt;:&lt;paramType&gt;</code>
	 * 
	 * @param query
	 *            The query for which we need a signature.
	 * @return The signature of <code>query</code>.
	 */
	private static String createQuerySignature(Query query) {
		StringBuilder signature = new StringBuilder();
		signature.append("Query "); //$NON-NLS-1$
		if (query.getVisibility() != null) {
			signature.append(query.getVisibility().getLiteral());
		}
		signature.append(' ');
		if (query.getName() != null) {
			signature.append(query.getName());
		}
		signature.append('(');
		Iterator<Variable> paramIterator = query.getParameter().iterator();
		while (paramIterator.hasNext()) {
			Variable param = paramIterator.next();
			if (param.getName() != null) {
				signature.append(param.getName());
			}
			signature.append(':');
			if (param.getType() != null) {
				signature.append(param.getType().getName());
			}
			if (paramIterator.hasNext()) {
				signature.append(',');
			}
		}
		signature.append(')');
		signature.append(':');
		if (query.getType() != null) {
			signature.append(query.getType().getName());
		}
		return signature.toString();
	}
}

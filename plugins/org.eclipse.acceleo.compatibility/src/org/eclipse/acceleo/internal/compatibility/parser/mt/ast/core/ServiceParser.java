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
package org.eclipse.acceleo.internal.compatibility.parser.mt.ast.core;

import java.lang.reflect.Method;
import java.util.Iterator;

import org.eclipse.acceleo.common.internal.utils.workspace.AcceleoWorkspaceUtil;
import org.eclipse.acceleo.compatibility.model.mt.Resource;
import org.eclipse.acceleo.compatibility.model.mt.ResourceSet;
import org.eclipse.acceleo.compatibility.model.mt.core.CoreFactory;
import org.eclipse.acceleo.compatibility.model.mt.core.Service;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;

/**
 * The utility class to create java services objects in the model.
 * 
 * @author <a href="mailto:yvan.lussaud@obeo.fr">Yvan Lussaud</a>
 */
public final class ServiceParser {

	/**
	 * No public access to the constructor.
	 */
	private ServiceParser() {
		// nothing to do here
	}

	/**
	 * Gets or creates the service that matches the given qualified name in the resource set. This qualified
	 * name often appears in the import section of the template to parse.
	 * 
	 * @param qualifiedName
	 *            is the qualified name of the class to load
	 * @param root
	 *            is the root element of the model
	 * @return the new service, or null if the qualified name is not a java service
	 */
	public static Service createImportedService(String qualifiedName, ResourceSet root) {
		// Remark : script "fr.package.template" -> class "fr.package.Template"
		String shortName = qualifiedName;
		String path = ""; //$NON-NLS-1$
		final int jDot = qualifiedName.lastIndexOf("."); //$NON-NLS-1$
		if (jDot > -1) {
			shortName = qualifiedName.substring(jDot + 1);
			path = qualifiedName.substring(0, jDot);
		}
		if (shortName.length() > 0) {
			shortName = shortName.substring(0, 1).toUpperCase() + shortName.substring(1);
		}
		if (path.length() > 0) {
			return getOrCreateService(path + '.' + shortName, root);
		}
		return getOrCreateService(shortName, root);
	}

	/**
	 * Gets or creates in the resource set the implicit service corresponding to the given '.mt' generator
	 * file. The qualified name of the service is computed with the qualified name of the generator.
	 * 
	 * @param file
	 *            is the '.mt' generator file
	 * @param root
	 *            is the root element of the model
	 * @return the new service, or null if this generator doesn't define an implicit java service
	 */
	public static Service createImplicitService(IFile file, ResourceSet root) {
		// Remark : script "/fr/package/template.mt" -> class
		// "fr.package.Template"
		String name = new Path(file.getName()).removeFileExtension().lastSegment();
		if (name.length() > 0) {
			name = Character.toUpperCase(name.charAt(0)) + name.substring(1);
		}
		final String[] segments = getPackagePath(file).append(name).segments();
		// ASSERT (segments.length > 0){
		// Java class name
		final StringBuffer qualifiedName = new StringBuffer();
		qualifiedName.append(segments[0]);
		for (int i = 1; i < segments.length; i++) {
			qualifiedName.append('.');
			qualifiedName.append(segments[i]);
		}
		return getOrCreateService(qualifiedName.toString(), root);
	}

	/**
	 * Gets the qualified path of the given generator file. As an example, the qualified path of the file
	 * '/MyProject/src/org/eclipse/acceleo/my.mt' is 'org/eclipse/acceleo'
	 * 
	 * @param file
	 *            is the '.mt' generator
	 * @return the qualified path, it removes the first and the last segments of the project relative path
	 */
	private static IPath getPackagePath(IFile file) {
		if (file != null && file.isAccessible()) {
			return file.getProjectRelativePath().removeLastSegments(1).removeFirstSegments(1);
		}
		return new Path(""); //$NON-NLS-1$
	}

	/**
	 * Gets or creates the service that matches the given qualified name in the resource set.
	 * 
	 * @param qualifiedName
	 *            is the qualified name of the class to load
	 * @param root
	 *            is the root element of the model
	 * @return the new service, or null if the qualified name is not a java service
	 */
	private static Service getOrCreateService(String qualifiedName, ResourceSet root) {
		Iterator<Resource> resources = root.getResources().iterator();
		while (resources.hasNext()) {
			Resource resource = resources.next();
			if (resource instanceof Service && resource.getName() != null
					&& resource.getName().equals(qualifiedName)) {
				return (Service)resource;
			}
		}
		final Class<?> javaClass = AcceleoWorkspaceUtil.INSTANCE.getClass(qualifiedName, false);
		Service result;
		if (javaClass != null) {
			result = createService(javaClass, root);
		} else {
			result = null;
		}
		return result;
	}

	/**
	 * Creates the service corresponding to the given class.
	 * 
	 * @param javaClass
	 *            is the java class which is an Acceleo service
	 * @param root
	 *            is the root element of the model
	 * @return the new service
	 */
	private static Service createService(Class<?> javaClass, ResourceSet root) {
		Service service = CoreFactory.eINSTANCE.createService();
		root.getResources().add(service);
		service.setName(javaClass.getName());
		Method[] javaMethods = javaClass.getDeclaredMethods();
		for (int i = 0; i < javaMethods.length; i++) {
			Method javaMethod = javaMethods[i];
			org.eclipse.acceleo.compatibility.model.mt.core.Method method = CoreFactory.eINSTANCE
					.createMethod();
			service.getMethods().add(method);
			method.setName(javaMethod.getName());
			if (javaMethod.getReturnType() != null) {
				method.setReturn(javaMethod.getReturnType().getName());
			} else {
				method.setReturn("void"); //$NON-NLS-1$
			}
			Class<?>[] javaParameters = javaMethod.getParameterTypes();
			for (int j = 0; j < javaParameters.length; j++) {
				Class<?> javaParameter = javaParameters[j];
				org.eclipse.acceleo.compatibility.model.mt.core.Parameter parameter = CoreFactory.eINSTANCE
						.createParameter();
				method.getParameters().add(parameter);
				if (javaParameter != null) {
					parameter.setType(javaParameter.getName());
				} else {
					parameter.setType("void"); //$NON-NLS-1$
				}
			}
		}
		return service;
	}

}

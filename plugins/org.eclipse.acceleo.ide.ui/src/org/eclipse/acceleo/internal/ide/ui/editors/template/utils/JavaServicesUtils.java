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

import com.google.common.io.ByteStreams;
import com.google.common.io.Closeables;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import org.eclipse.acceleo.common.IAcceleoConstants;
import org.eclipse.acceleo.ide.ui.AcceleoUIActivator;
import org.eclipse.acceleo.internal.ide.ui.AcceleoUIMessages;
import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.IMethod;
import org.eclipse.jdt.core.ISourceRange;
import org.eclipse.jdt.core.IType;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jdt.core.Signature;

/**
 * Provides utility methods to create and use Java services.
 * 
 * @author <a href="mailto:jonathan.musset@obeo.fr">Jonathan Musset</a>
 */
public final class JavaServicesUtils {

	/**
	 * The line separator to use.
	 */
	private static final String LS = System.getProperty("line.separator"); //$NON-NLS-1$

	/**
	 * Private constructor.
	 */
	private JavaServicesUtils() {
	}

	/**
	 * Indicates if the compilation unit can be used.
	 * 
	 * @param iCompilationUnit
	 *            The compilation unit
	 * @return <code>true</code> if the compilation unit can be used, <code>false</code> otherwise.
	 */
	public static boolean isAcceleoJavaServicesClass(ICompilationUnit iCompilationUnit) {
		try {
			IType[] types = iCompilationUnit.getTypes();
			for (IType iType : types) {
				ISourceRange javadocRange = iType.getJavadocRange();
				if (javadocRange != null) {
					String javadoc = iCompilationUnit.getOpenable().getBuffer().getText(
							javadocRange.getOffset(), javadocRange.getLength());
					if (javadoc != null && javadoc.contains(IAcceleoConstants.JAVADOC_TAG_NS_URI)) {
						return true;
					}
				}
			}
		} catch (JavaModelException e) {
			// do not log
		}
		return false;
	}

	/**
	 * Generates an Acceleo module for a given compilation unit.
	 * 
	 * @param iCompilationUnit
	 *            The compilation unit
	 * @param monitor
	 *            The progress monitor
	 */
	public static void generateAcceleoServicesModule(ICompilationUnit iCompilationUnit,
			IProgressMonitor monitor) {
		monitor.subTask(AcceleoUIMessages.getString("JavaServiceUtils.GenerateAcceleoModuleWrapper")); //$NON-NLS-1$
		List<String> nsURIs = new ArrayList<String>();
		try {
			IType[] types = iCompilationUnit.getTypes();
			for (IType iType : types) {
				ISourceRange javadocRange = iType.getJavadocRange();
				if (javadocRange != null) {
					String javadoc = iCompilationUnit.getOpenable().getBuffer().getText(
							javadocRange.getOffset(), javadocRange.getLength());
					Scanner scanner = new Scanner(javadoc);
					while (scanner.hasNextLine()) {
						String line = scanner.nextLine();
						if (javadoc != null) {
							int index = line.indexOf(IAcceleoConstants.JAVADOC_TAG_NS_URI);
							if (index != -1) {
								String trimmed = line.substring(
										index + IAcceleoConstants.JAVADOC_TAG_NS_URI.length()).trim();
								nsURIs.add(trimmed);
							}
						}
					}
					scanner.close();
				}
			}

			// Write the file
			IResource resource = iCompilationUnit.getResource();
			if (resource instanceof IFile) {
				IFile javaFile = (IFile)resource;
				String moduleName = javaFile.getName();
				if (moduleName.endsWith(".java")) { //$NON-NLS-1$
					moduleName = moduleName.substring(0, moduleName.length() - ".java".length()); //$NON-NLS-1$
				}
				String fileName = moduleName;
				moduleName = moduleName + '.' + IAcceleoConstants.MTL_FILE_EXTENSION;
				IContainer parent = javaFile.getParent();
				IFile file = parent.getFile(new Path(moduleName.substring(0, 1).toLowerCase()
						+ moduleName.substring(1)));

				String content = JavaServicesUtils.getFileContent(iCompilationUnit, fileName, nsURIs);

				ByteArrayInputStream source = new ByteArrayInputStream(content.getBytes());
				if (file.exists()) {
					InputStream prevVersionStream = file.getContents();
					boolean sameContent = false;
					byte[] oldContent;
					try {
						oldContent = ByteStreams.toByteArray(prevVersionStream);
						sameContent = Arrays.equals(content.getBytes(), oldContent);
					} catch (IOException e) {
						/*
						 * If we have an IOException we'll consider the content is not the same.
						 */
					} finally {
						Closeables.closeQuietly(prevVersionStream);
					}
					if (!sameContent) {
						file.setContents(source, true, false, monitor);
					}
				} else {
					file.create(source, true, monitor);
				}
			}
		} catch (JavaModelException e) {
			// do not log
		} catch (CoreException e) {
			AcceleoUIActivator.log(e, true);
		}
	}

	/**
	 * Returns the content of the Acceleo Java Services module.
	 * 
	 * @param iCompilationUnit
	 *            The compilation unit containing the Java services.
	 * @param fileName
	 *            The name of the file
	 * @param nsURIs
	 *            The list of the URIs to use
	 * @return The content of the Acceleo Java Services module.
	 */
	private static String getFileContent(ICompilationUnit iCompilationUnit, String fileName,
			List<String> nsURIs) {
		StringBuffer buffer = new StringBuffer();

		// Header
		buffer.append("[comment encoding=UTF-8/]" + LS); //$NON-NLS-1$
		buffer.append("[**" + LS); //$NON-NLS-1$
		buffer.append(" * Generated by Acceleo." + LS); //$NON-NLS-1$
		buffer.append(" */]" + LS); //$NON-NLS-1$
		buffer.append("[module " + fileName.substring(0, 1).toLowerCase() + fileName.substring(1) + "('"); //$NON-NLS-1$ //$NON-NLS-2$
		int count = 1;
		for (String nsURI : nsURIs) {
			buffer.append(nsURI);
			if (count < nsURIs.size()) {
				buffer.append("', '"); //$NON-NLS-1$
			} else {
				buffer.append("')"); //$NON-NLS-1$
			}
			count++;
		}
		buffer.append("/]" + LS); //$NON-NLS-1$
		buffer.append(LS);

		try {
			IType[] types = iCompilationUnit.getTypes();
			for (IType iType : types) {
				IMethod[] methods = iType.getMethods();
				for (IMethod iMethod : methods) {
					buffer.append(JavaServicesUtils.createQuery(iType, iMethod, true));
				}
			}
		} catch (JavaModelException e) {
			// do not log
		}

		return buffer.toString();
	}

	/**
	 * Create the query that is able to invoke the given Java method.
	 * 
	 * @param type
	 *            The type containing the method
	 * @param javaMethod
	 *            is the current method of the class
	 * @param withDocumentation
	 *            Indicates if we should generate some documentation too
	 * @return the textual format of the query
	 */
	public static String createQuery(IType type, IMethod javaMethod, boolean withDocumentation) {
		final String argPrefix = "arg"; //$NON-NLS-1$
		final String parenthesisStart = "("; //$NON-NLS-1$

		StringBuilder buffer = new StringBuilder();
		try {
			String[] javaParameters = javaMethod.getParameterTypes();
			if (withDocumentation) {
				buffer.append("[**\n * The documentation of the query\n"); //$NON-NLS-1$
				for (int i = 0; i < javaParameters.length; i++) {
					buffer.append(" * @param arg"); //$NON-NLS-1$
					buffer.append(i);
					buffer.append("\n"); //$NON-NLS-1$
				}
				buffer.append(" */]\n"); //$NON-NLS-1$
			}

			buffer.append("[query public "); //$NON-NLS-1$
			buffer.append(javaMethod.getElementName());
			buffer.append(parenthesisStart);
			if (javaParameters.length == 0) {
				buffer.append("anOclAny: OclAny"); //$NON-NLS-1$
			} else {
				for (int i = 0; i < javaParameters.length; i++) {
					if (i > 0) {
						buffer.append(',');
						buffer.append(' ');
					}
					buffer.append(argPrefix);
					buffer.append(i);
					buffer.append(" : "); //$NON-NLS-1$
					String resolvedType = javaClassToOclType(type, Signature.getTypeErasure(Signature
							.toString(javaParameters[i])));
					buffer.append(resolvedType);
				}
			}
			buffer.append(") : "); //$NON-NLS-1$
			buffer.append(javaClassToOclType(type, Signature.getTypeErasure(Signature
					.getSignatureSimpleName(javaMethod.getReturnType()))));
			buffer.append("\n\t= invoke('"); //$NON-NLS-1$
			buffer.append(type.getFullyQualifiedName());
			buffer.append("', '"); //$NON-NLS-1$
			buffer.append(javaMethod.getElementName());
			buffer.append(parenthesisStart);
			for (int i = 0; i < javaParameters.length; i++) {
				if (i > 0) {
					buffer.append(',');
					buffer.append(' ');
				}
				String[][] resolvedType = type.resolveType(Signature.getTypeErasure(Signature
						.toString(javaParameters[i])));
				if (resolvedType != null) {
					for (String[] strings : resolvedType) {
						for (int j = 0; j < strings.length; j++) {
							buffer.append(strings[j]);
							if (j < strings.length - 1) {
								buffer.append("."); //$NON-NLS-1$
							}
						}
					}
				}
			}
			buffer.append(")', Sequence{"); //$NON-NLS-1$
			for (int i = 0; i < javaParameters.length; i++) {
				if (i > 0) {
					buffer.append(", "); //$NON-NLS-1$
				}
				buffer.append(argPrefix);
				buffer.append(i);
			}
			buffer.append("})\n/]\n\n"); //$NON-NLS-1$
		} catch (JavaModelException e) {
			AcceleoUIActivator.log(e, true);
		}
		return buffer.toString();
	}

	/**
	 * Gets the OCL type name that corresponds to the java class. By default, it means the last segment of the
	 * fully qualified name.
	 * 
	 * @param type
	 *            the type containing the operation
	 * @param typeName
	 *            The name of the type to resolve.
	 * @return the OCL type name
	 */
	private static String javaClassToOclType(IType type, String typeName) {
		String qualifiedType = typeName;
		if (!"void".equals(qualifiedType)) { //$NON-NLS-1$			
			try {
				StringBuffer buffer = new StringBuffer();
				String[][] resolveType = type.resolveType(qualifiedType);
				if (resolveType != null) {
					for (String[] strings : resolveType) {
						for (int i = 0; i < strings.length; i++) {
							buffer.append(strings[i]);
							if (i < strings.length - 1) {
								buffer.append('.');
							}
						}
					}
				}

				if (buffer.toString().length() > 0) {
					qualifiedType = buffer.toString();
				}
			} catch (JavaModelException e) {
				// do not log
			}
		}

		String result;
		if ("java.math.BigDecimal".equals(qualifiedType) || "java.lang.Double".equals(qualifiedType) //$NON-NLS-1$ //$NON-NLS-2$
				|| "double".equals(qualifiedType)) { //$NON-NLS-1$
			result = "Real"; //$NON-NLS-1$
		} else if ("java.math.BigInteger".equals(qualifiedType) || "java.lang.Integer".equals(qualifiedType) //$NON-NLS-1$ //$NON-NLS-2$
				|| "int".equals(qualifiedType)) { //$NON-NLS-1$
			result = "Integer"; //$NON-NLS-1$
		} else if ("java.lang.Short".equals(qualifiedType) || "short".equals(qualifiedType)) { //$NON-NLS-1$ //$NON-NLS-2$
			result = "Integer"; //$NON-NLS-1$
		} else if ("java.lang.Boolean".equals(qualifiedType) || "boolean".equals(qualifiedType)) { //$NON-NLS-1$ //$NON-NLS-2$
			result = "Boolean"; //$NON-NLS-1$
		} else if ("java.lang.String".equals(qualifiedType)) { //$NON-NLS-1$
			result = "String"; //$NON-NLS-1$
		} else if ("java.util.List".equals(qualifiedType) || "java.util.ArrayList".equals(qualifiedType) //$NON-NLS-1$ //$NON-NLS-2$
				|| "java.util.LinkedList".equals(qualifiedType)) { //$NON-NLS-1$
			result = "Sequence(OclAny)"; //$NON-NLS-1$
		} else if ("java.util.Set".equals(qualifiedType) || "java.util.HashSet".equals(qualifiedType)) { //$NON-NLS-1$ //$NON-NLS-2$
			result = "Set(OclAny)"; //$NON-NLS-1$
		} else if ("java.util.LinkedHashSet".equals(qualifiedType)) { //$NON-NLS-1$
			result = "OrderedSet(OclAny)"; //$NON-NLS-1$
		} else if ("java.util.Collection".equals(qualifiedType)) { //$NON-NLS-1$
			result = "Collection(OclAny)"; //$NON-NLS-1$
		} else if ("java.lang.Object".equals(qualifiedType)) { //$NON-NLS-1$
			result = "OclAny"; //$NON-NLS-1$
		} else if ("void".equals(qualifiedType)) { //$NON-NLS-1$
			result = "OclVoid"; //$NON-NLS-1$
		} else {
			result = qualifiedType;
			int lastDot = result.lastIndexOf('.');
			if (lastDot > -1) {
				result = result.substring(lastDot + 1);
			}
			if (result.endsWith(";")) { //$NON-NLS-1$
				result = result.substring(0, result.length() - 1);
			}
		}
		return result;
	}

}

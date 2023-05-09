/*******************************************************************************
 * Copyright (c) 2021, 2023 Obeo.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 * 
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.acceleo.query.ide.jdt;

import java.lang.reflect.Method;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.eclipse.acceleo.query.runtime.IService;
import org.eclipse.acceleo.query.runtime.impl.namespace.JavaLoader;
import org.eclipse.acceleo.query.runtime.impl.namespace.Position;
import org.eclipse.acceleo.query.runtime.impl.namespace.Range;
import org.eclipse.acceleo.query.runtime.impl.namespace.SourceLocation;
import org.eclipse.acceleo.query.runtime.namespace.IQualifiedNameResolver;
import org.eclipse.acceleo.query.runtime.namespace.ISourceLocation;
import org.eclipse.acceleo.query.runtime.namespace.ISourceLocation.IPosition;
import org.eclipse.acceleo.query.runtime.namespace.ISourceLocation.IRange;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.URIUtil;
import org.eclipse.jdt.core.IClassFile;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.IMethod;
import org.eclipse.jdt.core.ISourceRange;
import org.eclipse.jdt.core.IType;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jdt.core.Signature;
import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.ASTParser;
import org.eclipse.jdt.core.dom.CompilationUnit;

/**
 * Eclipse JDT {@link JavaLoader}.
 * 
 * @author <a href="mailto:yvan.lussaud@obeo.fr">Yvan Lussaud</a>
 */
public class EclipseJDTJavaLoader extends JavaLoader {

	public static final String PATH_SEPARATOR = "/";

	public static final String PERIOD = ".";

	public static final String SRC = "src";

	private static final String JDT_SCHEME = "jdt";

	public static final String SYNTAX_SERVER_ID = "syntaxserver";

	/**
	 * Constructor.
	 * 
	 * @param qualifierSeparator
	 *            the qualifier separator
	 */
	public EclipseJDTJavaLoader(String qualifierSeparator) {
		super(qualifierSeparator);
	}

	@Override
	public ISourceLocation getSourceLocation(IQualifiedNameResolver resolver, IService<?> service) {
		ISourceLocation res = null;

		IPosition identifierStart = new Position(0, 0, 0);
		IPosition identifierEnd = new Position(0, 0, 0);
		final IRange identifierRange;

		IPosition start = new Position(0, 0, 0);
		IPosition end = new Position(0, 0, 0);
		final IRange range;

		if (service.getOrigin() instanceof Method) {
			final Method method = (Method)service.getOrigin();
			URI sourceURI = null;
			if (resolver instanceof EclipseJDTQualifiedNameResolver) {
				final IJavaProject project = ((EclipseJDTQualifiedNameResolver)resolver).getProject();
				try {
					final IType type = project.findType(method.getDeclaringClass().getCanonicalName());
					if (type != null) {
						type.getOpenable().open(new NullProgressMonitor());
						final IResource typeResource = type.getResource();
						final ASTParser parser = ASTParser.newParser(AST.getJLSLatest());
						if (typeResource != null) {
							sourceURI = typeResource.getLocationURI();
							parser.setSource(type.getCompilationUnit());
						} else {
							try {
								sourceURI = new URI(replaceUriFragment(toJDTUri(type.getClassFile())
										.toASCIIString(), SYNTAX_SERVER_ID));
								parser.setSource(type.getSource().toCharArray());
							} catch (Exception e) {
								// TODO: handle exception
							}
						}
						final IMethod javaMethod = type.getMethod(method.getName(), getParamterTypes(method));
						javaMethod.getOpenable().open(new NullProgressMonitor());
						final ISourceRange methodIdentifierRange = javaMethod.getNameRange();
						final ISourceRange sourceRange = javaMethod.getSourceRange();
						final CompilationUnit cu = (CompilationUnit)parser.createAST(null);
						final int identifierStartOffset = methodIdentifierRange.getOffset();
						identifierStart = new Position(cu.getLineNumber(identifierStartOffset) - 1, cu
								.getColumnNumber(identifierStartOffset), identifierStartOffset);
						final int identifierEndOffset = identifierStartOffset + methodIdentifierRange
								.getLength();
						identifierEnd = new Position(cu.getLineNumber(identifierEndOffset) - 1, cu
								.getColumnNumber(identifierEndOffset), identifierEndOffset);

						final int startOffset = sourceRange.getOffset();
						start = new Position(cu.getLineNumber(startOffset) - 1, cu.getColumnNumber(
								startOffset), startOffset);
						final int endOffset = startOffset + sourceRange.getLength();
						end = new Position(cu.getLineNumber(endOffset) - 1, cu.getColumnNumber(endOffset),
								endOffset);

						identifierRange = new Range(identifierStart, identifierEnd);
						range = new Range(start, end);
						res = new SourceLocation(sourceURI, identifierRange, range);
					} else {
						res = null;
					}
				} catch (JavaModelException e) {
					sourceURI = getDefaultSourceURI(resolver, method);
				}
			} else {
				sourceURI = getDefaultSourceURI(resolver, method);
			}
		} else {
			res = null;
		}

		return res;
	}

	private URI toJDTUri(IClassFile classFile) {
		URI res;

		final String packageName = classFile.getParent().getElementName();
		final String jarName = classFile.getParent().getParent().getElementName();
		try {
			res = new URI(JDT_SCHEME, "contents", PATH_SEPARATOR + jarName + PATH_SEPARATOR + packageName
					+ PATH_SEPARATOR + classFile.getElementName(), classFile.getHandleIdentifier(), null);
		} catch (URISyntaxException e) {
			res = null;
		}
		return res;
	}

	public static String replaceUriFragment(String uriString, String fragment) {
		if (uriString != null) {
			URI uri = toURI(uriString);
			if (uri != null && Objects.equals(JDT_SCHEME, uri.getScheme())) {
				try {
					return new URI(JDT_SCHEME, uri.getAuthority(), uri.getPath(), uri.getQuery(), fragment)
							.toASCIIString();
				} catch (URISyntaxException e) {
					// do nothing
				}
			}
		}

		return uriString;
	}

	public static URI toURI(String uriString) {
		if (uriString == null || uriString.isEmpty()) {
			return null;
		}
		try {
			URI uri = new URI(uriString);
			if (Platform.OS_WIN32.equals(Platform.getOS()) && URIUtil.isFileURI(uri)) {
				uri = URIUtil.toFile(uri).toURI();
			}
			return uri;
		} catch (URISyntaxException e) {
			return null;
		}
	}

	@Override
	public ISourceLocation getSourceLocation(IQualifiedNameResolver resolver, String qualifiedName) {
		ISourceLocation res = null;

		IPosition identifierStart = new Position(0, 0, 0);
		IPosition identifierEnd = new Position(0, 0, 0);
		final IRange identifierRange;

		IPosition start = new Position(0, 0, 0);
		IPosition end = new Position(0, 0, 0);
		final IRange range;

		final Object resolved = resolver.resolve(qualifiedName);
		if (resolved instanceof Class<?>) {
			URI sourceURI = null;
			if (resolver instanceof EclipseJDTQualifiedNameResolver) {
				final IJavaProject project = ((EclipseJDTQualifiedNameResolver)resolver).getProject();
				try {
					final IType type = project.findType(((Class<?>)resolved).getCanonicalName());
					if (type != null) {
						type.getOpenable().open(new NullProgressMonitor());
						final IResource typeResource = type.getResource();
						final ASTParser parser = ASTParser.newParser(AST.getJLSLatest());
						if (typeResource != null) {
							sourceURI = typeResource.getLocationURI();
							parser.setSource(type.getCompilationUnit());
						} else {
							sourceURI = toJDTUri(type.getClassFile());
							parser.setSource(type.getSource().toCharArray());
						}
						final ISourceRange classIdentifierRange = type.getNameRange();
						final ISourceRange sourceRange = type.getSourceRange();

						final CompilationUnit cu = (CompilationUnit)parser.createAST(null);
						final int identifierStartOffset = classIdentifierRange.getOffset();
						identifierStart = new Position(cu.getLineNumber(identifierStartOffset) - 1, cu
								.getColumnNumber(identifierStartOffset), identifierStartOffset);
						final int identifierEndOffset = identifierStartOffset + classIdentifierRange
								.getLength();
						identifierEnd = new Position(cu.getLineNumber(identifierEndOffset) - 1, cu
								.getColumnNumber(identifierEndOffset), identifierEndOffset);

						final int startOffset = sourceRange.getOffset();
						start = new Position(cu.getLineNumber(startOffset) - 1, cu.getColumnNumber(
								startOffset), startOffset);
						final int endOffset = startOffset + sourceRange.getLength();
						end = new Position(cu.getLineNumber(endOffset) - 1, cu.getColumnNumber(endOffset),
								endOffset);

						identifierRange = new Range(identifierStart, identifierEnd);
						range = new Range(start, end);
						res = new SourceLocation(sourceURI, identifierRange, range);
					} else {
						res = null;
					}
				} catch (JavaModelException e) {
					// nothing to do here
				}
			} else {
				// nothing to do here
			}
		} else {
			res = null;
		}

		return res;
	}

	private String[] getParamterTypes(Method method) {
		final List<String> res = new ArrayList<String>();

		for (Class<?> type : method.getParameterTypes()) {
			res.add(Signature.createTypeSignature(type.getSimpleName(), false));
		}

		return res.toArray(new String[res.size()]);
	}

	private URI getDefaultSourceURI(IQualifiedNameResolver resolver, final Method method) {
		final URI sourceURI;
		// TODO this will not work if the method is in a super class of the registered class
		final String qualifiedName = resolver.getQualifiedName(method.getDeclaringClass());
		sourceURI = resolver.getSourceURI(qualifiedName);
		return sourceURI;
	}

}

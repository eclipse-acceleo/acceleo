/*******************************************************************************
 * Copyright (c) 2021, 2024 Obeo.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 * 
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.acceleo.query.ide.jdt.runtime.impl.namespace;

import java.lang.reflect.Method;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import org.eclipse.acceleo.query.ide.jdt.Activator;
import org.eclipse.acceleo.query.runtime.IService;
import org.eclipse.acceleo.query.runtime.impl.namespace.JavaLoader;
import org.eclipse.acceleo.query.runtime.impl.namespace.Position;
import org.eclipse.acceleo.query.runtime.impl.namespace.Range;
import org.eclipse.acceleo.query.runtime.impl.namespace.SourceLocation;
import org.eclipse.acceleo.query.runtime.namespace.IQualifiedNameLookupEngine;
import org.eclipse.acceleo.query.runtime.namespace.IQualifiedNameResolver;
import org.eclipse.acceleo.query.runtime.namespace.ISourceLocation;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.Status;
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
	 * @param forWorkspace
	 *            tells if the {@link IService} will be used in a workspace
	 */
	public EclipseJDTJavaLoader(String qualifierSeparator, boolean forWorkspace) {
		super(qualifierSeparator, forWorkspace);
	}

	@Override
	public Set<IService<?>> getServices(IQualifiedNameLookupEngine lookupEngine, Object object,
			String contextQualifiedName) {
		// TODO Auto-generated method stub
		return super.getServices(lookupEngine, object, contextQualifiedName);
	}

	@Override
	public ISourceLocation getSourceLocation(IQualifiedNameResolver resolver, IService<?> service) {
		ISourceLocation res;

		final String contextQualifiedName = getQualifiedName(getContext(service));
		if (contextQualifiedName != null) {
			URI sourceURI = null;
			if (resolver instanceof EclipseJDTQualifiedNameResolver) {
				final IJavaProject project = ((EclipseJDTQualifiedNameResolver)resolver).getProject();
				try {
					final IType type = project.findType(contextQualifiedName);
					if (type != null) {
						type.getOpenable().open(new NullProgressMonitor());
						final IResource typeResource = type.getResource();
						final ASTParser parser = ASTParser.newParser(AST.getJLSLatest());
						if (typeResource != null) {
							sourceURI = typeResource.getLocationURI();
							parser.setSource(type.getCompilationUnit());
						} else if (type.getSource() != null) {
							try {
								sourceURI = new URI(replaceUriFragment(toJDTUri(type.getClassFile())
										.toASCIIString(), SYNTAX_SERVER_ID));
								parser.setSource(type.getSource().toCharArray());
							} catch (Exception e) {
								Activator.getPlugin().getLog().log(new Status(IStatus.ERROR,
										Activator.PLUGIN_ID, "can't get source location from:"
												+ contextQualifiedName, e));
							}
						}
						if (sourceURI != null) {
							final IMethod javaMethod = getIMethod(type, service);
							javaMethod.getOpenable().open(new NullProgressMonitor());
							final ISourceRange methodIdentifierRange = javaMethod.getNameRange();
							final ISourceRange sourceRange = javaMethod.getSourceRange();
							res = getIdentifierLocation(sourceURI, parser, methodIdentifierRange,
									sourceRange);
						} else {
							res = null;
						}
					} else {
						res = null;
					}
				} catch (JavaModelException e) {
					res = null;
				}
			} else {
				res = null;
			}
		} else {
			res = null;
		}

		return res;
	}

	/**
	 * Gets the qualified name of the given resolved {@link Object}.
	 * 
	 * @param resolved
	 *            the resolved {@link Object}
	 * @return the qualified name of the given resolved {@link Object} if any, <code>null</code> otherwise
	 */
	private String getQualifiedName(Object resolved) {
		final String res;

		if (resolved instanceof Class<?>) {
			res = ((Class<?>)resolved).getCanonicalName();
		} else {
			res = null;
		}

		return res;
	}

	/**
	 * Gets the context for the given {@link IService}.
	 * 
	 * @param service
	 *            the {@link IService}
	 * @return the context for the given {@link IService} if any, <code>null</code> otherwisse
	 */
	private Object getContext(IService<?> service) {
		final Object res;

		final Object origin = service.getOrigin();
		if (origin instanceof Method) {
			res = ((Method)origin).getDeclaringClass();
		} else {
			res = null;
		}

		return res;
	}

	/**
	 * Gets the {@link IMethod} form the given {@link IType} for the given {@link IService}.
	 * 
	 * @param type
	 *            the containing {@link IType}
	 * @param service
	 *            the {@link IService}
	 * @return the {@link IMethod} form the given {@link IType} for the given {@link IService} if nay,
	 *         <code>null</code> otherwise
	 */
	private IMethod getIMethod(IType type, IService<?> service) {
		final IMethod res;

		final Object origin = service.getOrigin();
		if (origin instanceof IMethod) {
			res = (IMethod)origin;
		} else if (origin instanceof Method) {
			final Method method = (Method)origin;
			res = type.getMethod(method.getName(), getParamterTypes(method));
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
		ISourceLocation res;

		final Object resolved = resolver.resolve(qualifiedName);
		if (resolved != null) {
			URI sourceURI = null;
			if (resolver instanceof EclipseJDTQualifiedNameResolver) {
				final IJavaProject project = ((EclipseJDTQualifiedNameResolver)resolver).getProject();
				try {
					final IType type = getJDTIType(resolved, project);
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

						res = getIdentifierLocation(sourceURI, parser, classIdentifierRange, sourceRange);
					} else {
						res = null;
					}
				} catch (JavaModelException e) {
					res = null;
				}
			} else {
				res = null;
			}
		} else {
			res = null;
		}

		return res;
	}

	private IType getJDTIType(final Object resolved, final IJavaProject project) throws JavaModelException {
		final IType res;

		if (resolved instanceof Class<?>) {
			res = project.findType(((Class<?>)resolved).getCanonicalName());
		} else {
			res = null;
		}

		return res;
	}

	/**
	 * Gets the identifier {@link ISourceLocation} from the given {@link URI} {@link ASTParser} and
	 * {@link ISourceRange}.
	 * 
	 * @param sourceURI
	 *            the source resource {@link URI}
	 * @param parser
	 *            the {@link ASTParser}
	 * @param javaIdentifierRange
	 *            the Java identiefier {@link ISourceRange}
	 * @param javaSourceRange
	 *            the containing Java {@link ISourceRange}
	 * @return the identifier {@link ISourceLocation} from the given {@link URI} {@link ASTParser} and
	 *         {@link ISourceRange}
	 */
	private ISourceLocation getIdentifierLocation(URI sourceURI, final ASTParser parser,
			final ISourceRange javaIdentifierRange, final ISourceRange javaSourceRange) {
		try {
			final CompilationUnit cu = (CompilationUnit)parser.createAST(null);
			final int identifierStartOffset = javaIdentifierRange.getOffset();
			final Position identifierStart = new Position(cu.getLineNumber(identifierStartOffset) - 1, cu
					.getColumnNumber(identifierStartOffset), identifierStartOffset);
			final int identifierEndOffset = identifierStartOffset + javaIdentifierRange.getLength();
			final Position identifierEnd = new Position(cu.getLineNumber(identifierEndOffset) - 1, cu
					.getColumnNumber(identifierEndOffset), identifierEndOffset);

			final int startOffset = javaSourceRange.getOffset();
			final Position start = new Position(cu.getLineNumber(startOffset) - 1, cu.getColumnNumber(
					startOffset), startOffset);
			final int endOffset = startOffset + javaSourceRange.getLength();
			final Position end = new Position(cu.getLineNumber(endOffset) - 1, cu.getColumnNumber(endOffset),
					endOffset);

			final Range identifierRange = new Range(identifierStart, identifierEnd);
			final Range range = new Range(start, end);

			return new SourceLocation(sourceURI, identifierRange, range);
		} catch (IllegalStateException e) {
			return null;
		}
	}

	private String[] getParamterTypes(Method method) {
		final List<String> res = new ArrayList<String>();

		for (Class<?> type : method.getParameterTypes()) {
			res.add(Signature.createTypeSignature(type.getSimpleName(), false));
		}

		return res.toArray(new String[res.size()]);
	}

}

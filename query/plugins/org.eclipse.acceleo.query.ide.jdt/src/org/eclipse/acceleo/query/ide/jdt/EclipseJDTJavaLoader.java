/*******************************************************************************
 * Copyright (c) 2021, 2023 Obeo.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.acceleo.query.ide.jdt;

import java.lang.reflect.Method;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.acceleo.query.runtime.IService;
import org.eclipse.acceleo.query.runtime.impl.namespace.JavaLoader;
import org.eclipse.acceleo.query.runtime.impl.namespace.Position;
import org.eclipse.acceleo.query.runtime.impl.namespace.Range;
import org.eclipse.acceleo.query.runtime.impl.namespace.SourceLocation;
import org.eclipse.acceleo.query.runtime.namespace.IQualifiedNameResolver;
import org.eclipse.acceleo.query.runtime.namespace.ISourceLocation;
import org.eclipse.acceleo.query.runtime.namespace.ISourceLocation.IPosition;
import org.eclipse.acceleo.query.runtime.namespace.ISourceLocation.IRange;
import org.eclipse.core.runtime.NullProgressMonitor;
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
						sourceURI = type.getResource().getLocationURI();
						final IMethod javaMethod = type.getMethod(method.getName(), getParamterTypes(method));
						javaMethod.getOpenable().open(new NullProgressMonitor());
						final ISourceRange methodIdentifierRange = javaMethod.getNameRange();
						final ISourceRange sourceRange = javaMethod.getSourceRange();

						final ASTParser parser = ASTParser.newParser(AST.getJLSLatest());
						parser.setSource(type.getCompilationUnit());
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
						sourceURI = type.getResource().getLocationURI();
						final ISourceRange classIdentifierRange = type.getNameRange();
						final ISourceRange sourceRange = type.getSourceRange();

						final ASTParser parser = ASTParser.newParser(AST.getJLSLatest());
						parser.setSource(type.getCompilationUnit());
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

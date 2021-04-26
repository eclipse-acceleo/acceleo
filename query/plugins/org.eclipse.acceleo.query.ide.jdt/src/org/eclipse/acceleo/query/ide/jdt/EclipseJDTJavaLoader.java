/*******************************************************************************
 * Copyright (c) 2021 Obeo.
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
import java.net.MalformedURLException;
import java.net.URL;
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
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.IMethod;
import org.eclipse.jdt.core.ISourceRange;
import org.eclipse.jdt.core.IType;
import org.eclipse.jdt.core.JavaModelException;
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
		final ISourceLocation res;

		IPosition identifierStart = new Position(0, 0, 0);
		IPosition identifierEnd = new Position(0, 0, 0);
		final IRange identifierRange;

		IPosition start = new Position(0, 0, 0);
		IPosition end = new Position(0, 0, 0);
		final IRange range;

		// TODO double check all of this
		if (service.getOrigin() instanceof Method) {
			final Method method = (Method)service.getOrigin();
			URL sourceURL = null;
			if (resolver instanceof EclipseJDTQualifiedNameResolver) {
				final IJavaProject project = ((EclipseJDTQualifiedNameResolver)resolver).getProject();
				try {
					final IType type = project.findType(method.getDeclaringClass().getCanonicalName());
					// TODO might be null and also not reference the source file
					sourceURL = type.getResource().getLocationURI().toURL();
					final IMethod javaMethod = type.getMethod(method.getName(), getParamterTypes(method));
					final ISourceRange methodIdentifierRange = javaMethod.getNameRange();
					final ISourceRange sourceRange = javaMethod.getSourceRange();

					final ASTParser parser = ASTParser.newParser(AST.JLS10);
					parser.setSource(type.getCompilationUnit());
					final CompilationUnit cu = (CompilationUnit)parser.createAST(null);
					final int identifierStartOffset = methodIdentifierRange.getOffset();
					identifierStart = new Position(cu.getLineNumber(identifierStartOffset), cu
							.getColumnNumber(identifierStartOffset), identifierStartOffset);
					final int identifierEndOffset = identifierStartOffset + methodIdentifierRange.getLength();
					identifierEnd = new Position(cu.getLineNumber(identifierEndOffset), cu.getColumnNumber(
							identifierEndOffset), identifierEndOffset);

					final int startOffset = sourceRange.getOffset();
					start = new Position(cu.getLineNumber(startOffset), cu.getColumnNumber(startOffset),
							startOffset);
					final int endOffset = startOffset + sourceRange.getLength();
					end = new Position(cu.getLineNumber(endOffset), cu.getColumnNumber(endOffset), endOffset);
				} catch (JavaModelException | MalformedURLException e) {
					sourceURL = getDefaultSourceURL(resolver, method);
				}
			} else {
				sourceURL = getDefaultSourceURL(resolver, method);
			}

			identifierRange = new Range(identifierStart, identifierEnd);
			range = new Range(start, end);
			res = new SourceLocation(sourceURL, identifierRange, range);
		} else {
			res = null;
		}

		return res;
	}

	private String[] getParamterTypes(Method method) {
		final List<String> res = new ArrayList<String>();

		for (Class<?> type : method.getParameterTypes()) {
			res.add(type.getTypeName());
		}

		return res.toArray(new String[res.size()]);
	}

	private URL getDefaultSourceURL(IQualifiedNameResolver resolver, final Method method) {
		final URL sourceURL;
		// TODO this will not work is the method is in a super class of the registered class
		final String qualifiedName = resolver.getQualifiedName(method.getDeclaringClass());
		sourceURL = resolver.getSourceURL(qualifiedName);
		return sourceURL;
	}

}

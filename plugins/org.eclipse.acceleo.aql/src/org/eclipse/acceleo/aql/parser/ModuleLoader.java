/*******************************************************************************
 * Copyright (c) 2020, 2023 Obeo.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 * 
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.acceleo.aql.parser;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.eclipse.acceleo.Import;
import org.eclipse.acceleo.Module;
import org.eclipse.acceleo.ModuleElement;
import org.eclipse.acceleo.ModuleReference;
import org.eclipse.acceleo.Query;
import org.eclipse.acceleo.Template;
import org.eclipse.acceleo.aql.evaluation.AcceleoEvaluator;
import org.eclipse.acceleo.aql.evaluation.QueryService;
import org.eclipse.acceleo.aql.evaluation.TemplateService;
import org.eclipse.acceleo.query.runtime.IService;
import org.eclipse.acceleo.query.runtime.impl.namespace.AbstractLoader;
import org.eclipse.acceleo.query.runtime.impl.namespace.Position;
import org.eclipse.acceleo.query.runtime.impl.namespace.Range;
import org.eclipse.acceleo.query.runtime.impl.namespace.SourceLocation;
import org.eclipse.acceleo.query.runtime.namespace.IQualifiedNameLookupEngine;
import org.eclipse.acceleo.query.runtime.namespace.IQualifiedNameResolver;
import org.eclipse.acceleo.query.runtime.namespace.ISourceLocation;
import org.eclipse.acceleo.query.runtime.namespace.ISourceLocation.IPosition;
import org.eclipse.acceleo.query.runtime.namespace.ISourceLocation.IRange;

/**
 * Loader for {@link Module}.
 * 
 * @author <a href="mailto:yvan.lussaud@obeo.fr">Yvan Lussaud</a>
 */
public class ModuleLoader extends AbstractLoader {

	/**
	 * The {@link AcceleoEvaluator}.
	 */
	private final AcceleoEvaluator evaluator;

	/**
	 * The {@link AcceleoParser}.
	 */
	private AcceleoParser parser;

	/**
	 * Constructor.
	 * 
	 * @param parser
	 *            the {@link AcceleoParser}
	 * @param evaluator
	 *            the {@link AcceleoEvaluator}
	 */
	public ModuleLoader(AcceleoParser parser, AcceleoEvaluator evaluator) {
		super(AcceleoParser.QUALIFIER_SEPARATOR, AcceleoParser.MODULE_FILE_EXTENSION,
				AcceleoParser.MODULE_FILE_EXTENSION);
		this.parser = parser;
		this.evaluator = evaluator;
	}

	@Override
	public Object load(IQualifiedNameResolver resolver, String qualifiedName) {
		Module res;

		// TODO use the proper charset (using the comment on first line)
		try (InputStream is = resolver.getInputStream(resourceName(qualifiedName))) {
			if (is != null) {
				res = parser.parse(is, StandardCharsets.UTF_8, qualifiedName).getModule();
			} else {
				res = null;
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			res = null;
		}

		return res;
	}

	@Override
	public boolean canHandle(Object object) {
		return object instanceof Module;
	}

	@Override
	public Set<IService<?>> getServices(IQualifiedNameLookupEngine lookupEngine, Object object,
			String contextQualifiedName) {
		final Set<IService<?>> res = new LinkedHashSet<IService<?>>();

		final Module module = (Module)object;
		for (ModuleElement element : module.getModuleElements()) {
			if (element instanceof Template) {
				res.add(new TemplateService((Template)element, evaluator, lookupEngine,
						contextQualifiedName));
			} else if (element instanceof Query) {
				res.add(new QueryService((Query)element, evaluator, lookupEngine, contextQualifiedName));
			}
		}

		return res;
	}

	@Override
	public List<String> getImports(Object object) {
		final List<String> res = new ArrayList<String>();

		final Module module = (Module)object;
		for (Import imp : module.getImports()) {
			final ModuleReference moduleRef = imp.getModule();
			if (moduleRef != null && moduleRef.getQualifiedName() != null) {
				res.add(moduleRef.getQualifiedName());
			}
		}
		return res;
	}

	@Override
	public String getExtends(Object object) {
		final String res;

		final Module module = (Module)object;
		final ModuleReference ext = module.getExtends();
		if (ext != null) {
			res = ext.getQualifiedName();
		} else {
			res = null;
		}

		return res;
	}

	@Override
	public ISourceLocation getSourceLocation(IQualifiedNameResolver resolver, IService<?> service) {
		final ISourceLocation res;

		if (service.getOrigin() instanceof ModuleElement) {
			final ModuleElement moduleElement = (ModuleElement)service.getOrigin();
			if (moduleElement.eContainer() instanceof Module) {
				final Module module = (Module)moduleElement.eContainer();
				if (module.eResource().getURI() != null) {
					final String qualifiedName = module.eResource().getURI().toString().substring(
							AcceleoParser.ACCELEOENV_URI_PROTOCOL.length());

					final URI sourceURI = resolver.getSourceURI(qualifiedName);

					final int identifierStartLine = module.getAst().getIdentifierStartLine(moduleElement);
					final int identifierStartColumn = module.getAst().getIdentifierStartColumn(moduleElement);
					final int identifierStartPosition = module.getAst().getIdentifierStartPosition(
							moduleElement);
					final IPosition identifierStart = new Position(identifierStartLine, identifierStartColumn,
							identifierStartPosition);

					final int identifierEndLine = module.getAst().getIdentifierEndLine(moduleElement);
					final int identifierEndColumn = module.getAst().getIdentifierEndColumn(moduleElement);
					final int identifierEndPosition = module.getAst().getIdentifierEndPosition(moduleElement);
					final IPosition identifierEnd = new Position(identifierEndLine, identifierEndColumn,
							identifierEndPosition);

					final IRange identifierRange = new Range(identifierStart, identifierEnd);

					final int startLine = module.getAst().getStartLine(moduleElement);
					final int startColumn = module.getAst().getStartColumn(moduleElement);
					final int startPosition = module.getAst().getStartPosition(moduleElement);
					final IPosition start = new Position(startLine, startColumn, startPosition);

					final int endLine = module.getAst().getEndLine(moduleElement);
					final int endColumn = module.getAst().getEndColumn(moduleElement);
					final int endPosition = module.getAst().getEndPosition(moduleElement);
					final IPosition end = new Position(endLine, endColumn, endPosition);

					final IRange range = new Range(start, end);

					res = new SourceLocation(sourceURI, identifierRange, range);
				} else {
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

	@Override
	public ISourceLocation getSourceLocation(IQualifiedNameResolver resolver, String qualifiedName) {
		final ISourceLocation res;

		final Object resolved = resolver.resolve(qualifiedName);
		if (resolved instanceof Module) {
			final Module module = (Module)resolved;
			final URI sourceURI = resolver.getSourceURI(qualifiedName);

			final int identifierStartLine = module.getAst().getIdentifierStartLine(module);
			final int identifierStartColumn = module.getAst().getIdentifierStartColumn(module);
			final int identifierStartPosition = module.getAst().getIdentifierStartPosition(module);
			final IPosition identifierStart = new Position(identifierStartLine, identifierStartColumn,
					identifierStartPosition);

			final int identifierEndLine = module.getAst().getIdentifierEndLine(module);
			final int identifierEndColumn = module.getAst().getIdentifierEndColumn(module);
			final int identifierEndPosition = module.getAst().getIdentifierEndPosition(module);
			final IPosition identifierEnd = new Position(identifierEndLine, identifierEndColumn,
					identifierEndPosition);

			final IRange identifierRange = new Range(identifierStart, identifierEnd);

			final int startLine = module.getAst().getStartLine(module);
			final int startColumn = module.getAst().getStartColumn(module);
			final int startPosition = module.getAst().getStartPosition(module);
			final IPosition start = new Position(startLine, startColumn, startPosition);

			final int endLine = module.getAst().getEndLine(module);
			final int endColumn = module.getAst().getEndColumn(module);
			final int endPosition = module.getAst().getEndPosition(module);
			final IPosition end = new Position(endLine, endColumn, endPosition);

			final IRange range = new Range(start, end);

			res = new SourceLocation(sourceURI, identifierRange, range);
		} else {
			res = null;
		}

		return res;
	}

}

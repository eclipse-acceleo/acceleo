/*******************************************************************************
 * Copyright (c) 2023 Obeo.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 * 
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.acceleo.tests.utils;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import org.eclipse.acceleo.Module;
import org.eclipse.acceleo.aql.AcceleoUtil;
import org.eclipse.acceleo.aql.evaluation.AcceleoEvaluator;
import org.eclipse.acceleo.aql.parser.AcceleoParser;
import org.eclipse.acceleo.aql.parser.ModuleLoader;
import org.eclipse.acceleo.query.runtime.namespace.IQualifiedNameResolver;

/**
 * A {@link ModuleLoader} that convert end of line to MS Windows end of line (\r\n).
 * 
 * @author <a href="mailto:yvan.lussaud@obeo.fr">Yvan Lussaud</a>
 */
public class ModuleLoaderWindowsEndLine extends ModuleLoader {

	public ModuleLoaderWindowsEndLine(AcceleoParser parser, AcceleoEvaluator evaluator) {
		super(parser, evaluator);
	}

	@Override
	public Object load(IQualifiedNameResolver resolver, String qualifiedName) {
		Module res;

		// TODO use the proper charset (using the comment on first line)
		try (InputStream is = resolver.getInputStream(resourceName(qualifiedName))) {
			if (is != null) {
				final String text = AcceleoUtil.getContent(is, StandardCharsets.UTF_8.name()).replaceAll("\n",
						"\r\n");
				res = getParser().parse(text, qualifiedName).getModule();
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

}

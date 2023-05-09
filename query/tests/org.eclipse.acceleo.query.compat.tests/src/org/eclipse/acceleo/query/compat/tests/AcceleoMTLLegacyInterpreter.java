/*******************************************************************************
 * Copyright (c) 2015 Obeo.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 * 
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.acceleo.query.compat.tests;

import com.google.common.collect.Iterables;
import com.google.common.collect.ListMultimap;
import com.google.common.collect.Lists;

import java.lang.reflect.Field;
import java.util.List;

import org.eclipse.acceleo.common.interpreter.CompilationResult;
import org.eclipse.acceleo.common.interpreter.EvaluationResult;
import org.eclipse.acceleo.engine.service.EvaluationContext;
import org.eclipse.acceleo.parser.interpreter.CompilationContext;
import org.eclipse.acceleo.query.tests.InterpreterUnderTest;
import org.eclipse.acceleo.query.tests.qmodel.EObjectVariable;
import org.eclipse.acceleo.query.tests.qmodel.ErrorResult;
import org.eclipse.acceleo.query.tests.qmodel.QmodelFactory;
import org.eclipse.acceleo.query.tests.qmodel.Query;
import org.eclipse.acceleo.query.tests.qmodel.QueryEvaluationResult;
import org.eclipse.acceleo.query.tests.qmodel.QueryEvaluationResultFactory;
import org.eclipse.acceleo.query.tests.qmodel.QueryValidationResult;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.sirius.common.acceleo.mtl.business.internal.interpreter.AcceleoMTLInterpreter;
import org.eclipse.sirius.common.acceleo.mtl.business.internal.interpreter.DynamicAcceleoModule.QueryIdentifier;
import org.eclipse.sirius.common.tools.api.interpreter.EvaluationException;
import org.eclipse.sirius.common.tools.api.interpreter.IInterpreter;

public class AcceleoMTLLegacyInterpreter implements InterpreterUnderTest {

	private static final class TestMTLInterpreter extends AcceleoMTLInterpreter {

		public CompilationResult compile(EObject context, String expression) {
			getModule().invalidate();
			final CompilationContext compilationContext = createCompilationContext(context, expression);

			final QueryIdentifier identifier = getModule().ensureQueryExists(compilationContext);
			return getModule().compile(compilationContext, identifier);
		}

		public EvaluationResult eval(EObject context, CompilationResult compilationResult)
				throws EvaluationException {
			if (compilationResult.getStatus() == null
					|| compilationResult.getStatus().getSeverity() == IStatus.OK) {
				EvaluationContext evaluationContext = new EvaluationContext(context, getVariablesInternal(),
						compilationResult);
				return getModule().evaluate(evaluationContext);
			}
			return new EvaluationResult(null, compilationResult.getStatus());
		}

		private ListMultimap<String, Object> getVariablesInternal() {
			try {
				Field field = null;
				for (Field f : AcceleoMTLInterpreter.class.getDeclaredFields()) {
					if (f.getName().equals("variables")) {
						field = f;
						break;
					}
				}
				field.setAccessible(true);
				final Object value = field.get(this);
				return (ListMultimap<String, Object>)value;
			} catch (IllegalArgumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SecurityException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return null;
		}

	}

	private final TestMTLInterpreter inter;

	private CompilationResult compilationResult;

	public AcceleoMTLLegacyInterpreter(Query q) {
		inter = new TestMTLInterpreter();
		List<String> files = Lists.newArrayList();
		for (String pluginID : q.getPluginsInClassPath()) {
			files.add(pluginID + "/META-INF/MANIFEST.MF");
		}
		inter.setProperty(IInterpreter.FILES, files);

		inter.clearImports();
		for (String imports : q.getClassesToImport()) {
			inter.addImport(imports);
		}

		for (EObjectVariable var : Iterables.filter(q.getVariables(), EObjectVariable.class)) {
			inter.setVariable(var.getName(), var.getValue().getTarget());
		}
	}

	@Override
	public void compileQuery(Query query) {
		compilationResult = inter.compile(query.getStartingPoint().getTarget(), query.getExpression());
	}

	@Override
	public QueryEvaluationResult computeQuery(Query query) {

		QueryEvaluationResult res = QmodelFactory.eINSTANCE.createEmptyResult();
		res.setInterpreter("mtl");

		if (query.getStartingPoint() != null && query.getStartingPoint().getTarget() != null) {
			try {
				EvaluationResult result = inter.eval(query.getStartingPoint().getTarget(), compilationResult);
				if (result.getStatus().isOK()) {
					res = new QueryEvaluationResultFactory().createFromValue(result.getEvaluationResult());
				} else {
					res = QmodelFactory.eINSTANCE.createInvalidResult();
				}

				query.getCurrentResults().add(res);
			} catch (EvaluationException e) {
				ErrorResult error = QmodelFactory.eINSTANCE.createErrorResult();
				error.setMessage(e.getMessage());
				query.getCurrentResults().add(error);
				res = error;
			}
		}
		return res;

	}

	@Override
	public QueryValidationResult validateQuery(Query q) {
		throw new UnsupportedOperationException("This shouldn't not be implemented.");
	}

}

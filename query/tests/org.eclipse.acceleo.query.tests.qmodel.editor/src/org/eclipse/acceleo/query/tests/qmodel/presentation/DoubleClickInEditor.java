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
package org.eclipse.acceleo.query.tests.qmodel.presentation;

import org.eclipse.acceleo.query.compat.tests.AcceleoMTLLegacyInterpreter;
import org.eclipse.acceleo.query.tests.qmodel.Query;
import org.eclipse.acceleo.query.tests.qmodel.QueryEvaluationResult;
import org.eclipse.emf.edit.command.ChangeCommand;
import org.eclipse.emf.edit.domain.AdapterFactoryEditingDomain;
import org.eclipse.jface.viewers.DoubleClickEvent;
import org.eclipse.jface.viewers.IDoubleClickListener;
import org.eclipse.jface.viewers.IStructuredSelection;

public class DoubleClickInEditor implements IDoubleClickListener {

	private AdapterFactoryEditingDomain editingDomain;

	public DoubleClickInEditor(AdapterFactoryEditingDomain editingDomain) {
		this.editingDomain = editingDomain;
	}

	@Override
	public void doubleClick(DoubleClickEvent event) {
		if (event.getSelection() instanceof IStructuredSelection) {
			Object selected = ((IStructuredSelection)event.getSelection()).getFirstElement();
			if (selected instanceof Query) {
				final Query q = (Query)selected;
				ChangeCommand command = new ChangeCommand(q) {

					@Override
					protected void doExecute() {
						q.getCurrentResults().clear();
						AcceleoMTLLegacyInterpreter inter = new AcceleoMTLLegacyInterpreter(q);
						inter.compileQuery(q);
						QueryEvaluationResult res = inter.computeQuery(q);
						res.setInterpreter("mtl");
						q.getCurrentResults().add(res);

					}
				};
				editingDomain.getCommandStack().execute(command);
			}
		}

	}
}

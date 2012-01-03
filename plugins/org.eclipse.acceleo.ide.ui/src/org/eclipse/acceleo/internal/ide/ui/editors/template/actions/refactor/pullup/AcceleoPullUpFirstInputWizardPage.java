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
package org.eclipse.acceleo.internal.ide.ui.editors.template.actions.refactor.pullup;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.acceleo.model.mtl.Module;
import org.eclipse.acceleo.model.mtl.Template;
import org.eclipse.jface.viewers.CheckboxTableViewer;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.ltk.core.refactoring.Refactoring;
import org.eclipse.ltk.ui.refactoring.UserInputWizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;

/**
 * This will create the first page of the PullUp refactoring wizard in which the user will select the
 * templates to pull up.
 * 
 * @author <a href="mailto:stephane.begaudeau@obeo.fr">Stephane Begaudeau</a>
 */
public class AcceleoPullUpFirstInputWizardPage extends UserInputWizardPage {

	/**
	 * The table viewer listing all the template from the module.
	 */
	private CheckboxTableViewer viewer;

	/**
	 * The module.
	 */
	private Module module;

	/**
	 * The constructor.
	 * 
	 * @param name
	 *            The name of the wizard page.
	 * @param m
	 *            The module
	 */
	public AcceleoPullUpFirstInputWizardPage(String name, Module m) {
		super(name);
		this.module = m;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.jface.dialogs.IDialogPage#createControl(org.eclipse.swt.widgets.Composite)
	 */
	public void createControl(Composite parent) {
		Composite composite = new Composite(parent, SWT.NONE);
		composite.setLayout(new FillLayout());
		this.setControl(composite);
		this.viewer = CheckboxTableViewer.newCheckList(composite, SWT.H_SCROLL | SWT.V_SCROLL | SWT.BORDER);
		this.viewer.setLabelProvider(new AcceleoPullUpTableLabelProvider());
		this.viewer.setContentProvider(new AcceleoPullUpTableContentProvider());
		this.viewer.addSelectionChangedListener(new ISelectionChangedListener() {
			public void selectionChanged(SelectionChangedEvent event) {
				Refactoring refactoring = getRefactoring();
				if (refactoring instanceof AcceleoPullUpRefactoring) {
					AcceleoPullUpRefactoring acceleoPullUpRefactoring = (AcceleoPullUpRefactoring)refactoring;
					acceleoPullUpRefactoring.setTemplates(getSelectedTemplate());
					setPageComplete(getSelectedTemplate().size() > 0);
				}
			}
		});

		this.viewer.setInput(this.module);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.ltk.ui.refactoring.RefactoringWizardPage#getRefactoring()
	 */
	@Override
	protected Refactoring getRefactoring() {
		// enhances visibility
		return super.getRefactoring();
	}

	/**
	 * Returns the list of the selected template in the table viewer.
	 * 
	 * @return The list of the selected template in the table viewer.
	 */
	protected List<Template> getSelectedTemplate() {
		List<Template> result = new ArrayList<Template>();
		Object[] checkedElements = this.viewer.getCheckedElements();
		for (Object object : checkedElements) {
			if (object instanceof Template) {
				result.add((Template)object);
			}
		}
		return result;
	}

	@Override
	public boolean isPageComplete() {
		return this.getSelectedTemplate().size() > 0;
	}

}

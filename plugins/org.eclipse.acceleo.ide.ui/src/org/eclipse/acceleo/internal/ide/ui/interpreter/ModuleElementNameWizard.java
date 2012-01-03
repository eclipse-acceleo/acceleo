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
package org.eclipse.acceleo.internal.ide.ui.interpreter;

import org.eclipse.jface.wizard.Wizard;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

/**
 * This wizard will let the user enter a template or query name for the save action.
 * 
 * @author <a href="mailto:stephane.begaudeau@obeo.fr">Stephane Begaudeau</a>
 */
public class ModuleElementNameWizard extends Wizard {

	/**
	 * The name of the page of the wizard.
	 */
	private String pageName;

	/**
	 * The description of the page.
	 */
	private String pageDescription;

	/**
	 * The label of the page of the wizard.
	 */
	private String pageLabel;

	/**
	 * The page of the wizard.
	 */
	private ModuleElementNameWizardPage page;

	/**
	 * The name of the module element.
	 */
	private String name;

	/**
	 * The constructor.
	 * 
	 * @param pageName
	 *            The name of the page.
	 * @param pageDescription
	 *            The description of the page.
	 * @param pageLabel
	 *            The label of the page.
	 */
	public ModuleElementNameWizard(String pageName, String pageDescription, String pageLabel) {
		this.pageLabel = pageLabel;
		this.pageDescription = pageDescription;
		this.pageName = pageName;
	}

	@Override
	public void addPages() {
		page = new ModuleElementNameWizardPage(pageName, pageDescription, pageLabel);
		addPage(page);
	}

	/**
	 * Returns the module element name.
	 * 
	 * @return The module element name.
	 */
	public String getModuleElementName() {
		return name;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.jface.wizard.Wizard#performFinish()
	 */
	@Override
	public boolean performFinish() {
		return true;
	}

	/**
	 * The first and only page of the wizard used to select the name of the module element.
	 * 
	 * @author <a href="mailto:stephane.begaudeau@obeo.fr">Stephane Begaudeau</a>
	 */
	private final class ModuleElementNameWizardPage extends WizardPage {
		/**
		 * The label of the wizard.
		 */
		private String label;

		/**
		 * The name of the module element.
		 */
		private Text moduleElementNameText;

		/**
		 * The constructor.
		 * 
		 * @param pageName
		 *            The name of the page.
		 * @param pageDescription
		 *            The description of the page
		 * @param label
		 *            The label in the wizard
		 */
		private ModuleElementNameWizardPage(String pageName, String pageDescription, String label) {
			super(pageName);
			this.setTitle(pageName);
			this.setDescription(pageDescription);
			this.label = label;
		}

		/**
		 * {@inheritDoc}
		 * 
		 * @see org.eclipse.jface.dialogs.IDialogPage#createControl(org.eclipse.swt.widgets.Composite)
		 */
		public void createControl(Composite parent) {
			final Composite composite = new Composite(parent, SWT.NONE);
			composite.setFont(parent.getFont());

			GridLayout layout = new GridLayout(2, false);
			composite.setLayout(layout);

			Label moduleElementNameLabel = new Label(composite, SWT.LEFT | SWT.WRAP);
			moduleElementNameLabel.setFont(composite.getFont());
			moduleElementNameLabel.setText(label + ':');
			GridData gridData = new GridData(GridData.HORIZONTAL_ALIGN_FILL);
			moduleElementNameLabel.setLayoutData(gridData);

			moduleElementNameText = new Text(composite, SWT.SINGLE | SWT.BORDER);
			gridData = new GridData();
			gridData.horizontalAlignment = GridData.FILL;
			gridData.grabExcessHorizontalSpace = true;
			moduleElementNameText.setLayoutData(gridData);
			moduleElementNameText.addKeyListener(new KeyListener() {

				public void keyReleased(KeyEvent e) {
					name = moduleElementNameText.getText();
				}

				public void keyPressed(KeyEvent e) {
					name = moduleElementNameText.getText();
				}
			});

			this.setControl(parent);
		}
	}
}

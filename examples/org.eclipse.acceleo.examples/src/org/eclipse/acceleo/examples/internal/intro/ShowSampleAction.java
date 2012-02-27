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
package org.eclipse.acceleo.examples.internal.intro;

import java.util.Properties;

import org.eclipse.acceleo.examples.internal.wizard.Ecore2PythonExampleWizard;
import org.eclipse.acceleo.examples.internal.wizard.Ecore2UnitTestsExampleWizard;
import org.eclipse.acceleo.examples.internal.wizard.Uml2JavaExampleWizard;
import org.eclipse.jface.action.Action;
import org.eclipse.ui.intro.IIntroSite;
import org.eclipse.ui.intro.config.IIntroAction;

/**
 * Utility class to unzip the sample.
 * 
 * @author <a href="mailto:stephane.begaudeau@obeo.fr">Stephane Begaudeau</a>
 * @since 3.2
 */
public class ShowSampleAction extends Action implements IIntroAction {

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.ui.intro.config.IIntroAction#run(org.eclipse.ui.intro.IIntroSite,
	 *      java.util.Properties)
	 */
	public void run(IIntroSite site, Properties params) {
		String sampleId = params.getProperty("id"); //$NON-NLS-1$
		if (sampleId == null) {
			return;
		}

		if ("uml2java".equals(sampleId)) {
			Uml2JavaExampleWizard wizard = new Uml2JavaExampleWizard();
			wizard.performFinish();
		} else if ("ecore2junit".equals(sampleId)) {
			Ecore2UnitTestsExampleWizard wizard = new Ecore2UnitTestsExampleWizard();
			wizard.performFinish();
		} else if ("ecore2python".equals(sampleId)) {
			Ecore2PythonExampleWizard wizard = new Ecore2PythonExampleWizard();
			wizard.performFinish();
		}
	}
}

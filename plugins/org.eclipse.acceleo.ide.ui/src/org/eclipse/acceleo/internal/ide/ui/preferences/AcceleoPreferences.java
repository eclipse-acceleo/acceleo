/*******************************************************************************
 * Copyright (c) 2011, 2012 Obeo.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.acceleo.internal.ide.ui.preferences;

import org.eclipse.acceleo.internal.ide.ui.AcceleoUIMessages;
import org.eclipse.jface.preference.PreferencePage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;

/**
 * This is the "base" preference page of Acceleo, it will serve as a category for our other preferences.
 * 
 * @author <a href="mailto:laurent.goubet@obeo.fr">Laurent Goubet</a>
 */
public class AcceleoPreferences extends PreferencePage implements IWorkbenchPreferencePage {

	/**
	 * The activate query cache button.
	 */
	private Button toggleQueryCache;

	/**
	 * The activate debug messages button.
	 */
	private Button toggleDebugMessages;

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.ui.IWorkbenchPreferencePage#init(org.eclipse.ui.IWorkbench)
	 */
	public void init(IWorkbench workbench) {
		// Nothing for now
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.jface.preference.PreferencePage#createContents(org.eclipse.swt.widgets.Composite)
	 */
	@Override
	protected Control createContents(Composite parent) {
		Composite composite = new Composite(parent, SWT.NONE);
		composite.setLayout(new GridLayout());

		toggleQueryCache = new Button(composite, SWT.CHECK);
		toggleQueryCache.setText(AcceleoUIMessages.getString("AcceleoPreferences.QueryCacheButton")); //$NON-NLS-1$
		toggleQueryCache.setToolTipText(AcceleoUIMessages
				.getString("AcceleoPreferences.QueryCacheButtonTooltip")); //$NON-NLS-1$
		toggleQueryCache.setSelection(org.eclipse.acceleo.common.preference.AcceleoPreferences
				.isQueryCacheEnabled());

		toggleDebugMessages = new Button(composite, SWT.CHECK);
		toggleDebugMessages.setText(AcceleoUIMessages.getString("AcceleoPreferences.DebugMessagesButton")); //$NON-NLS-1$
		toggleDebugMessages.setToolTipText(AcceleoUIMessages
				.getString("AcceleoPreferences.DebugMessagesButtonTooltip")); //$NON-NLS-1$
		toggleDebugMessages.setSelection(org.eclipse.acceleo.common.preference.AcceleoPreferences
				.isDebugMessagesEnabled());

		return composite;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.jface.preference.PreferencePage#performDefaults()
	 */
	@Override
	protected void performDefaults() {
		toggleQueryCache.setSelection(true);
		org.eclipse.acceleo.common.preference.AcceleoPreferences.switchQueryCache(toggleQueryCache
				.getSelection());
		toggleDebugMessages.setSelection(true);
		org.eclipse.acceleo.common.preference.AcceleoPreferences.switchDebugMessages(toggleDebugMessages
				.getSelection());
		super.performDefaults();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.jface.preference.PreferencePage#performApply()
	 */
	@Override
	protected void performApply() {
		org.eclipse.acceleo.common.preference.AcceleoPreferences.switchQueryCache(toggleQueryCache
				.getSelection());
		org.eclipse.acceleo.common.preference.AcceleoPreferences.switchDebugMessages(toggleDebugMessages
				.getSelection());
		super.performApply();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.jface.preference.PreferencePage#performOk()
	 */
	@Override
	public boolean performOk() {
		org.eclipse.acceleo.common.preference.AcceleoPreferences.switchQueryCache(toggleQueryCache
				.getSelection());
		org.eclipse.acceleo.common.preference.AcceleoPreferences.switchDebugMessages(toggleDebugMessages
				.getSelection());
		return super.performOk();
	}
}

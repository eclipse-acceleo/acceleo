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
package org.eclipse.acceleo.internal.ide.ui.preferences;

import org.eclipse.acceleo.common.preference.AcceleoPreferences;
import org.eclipse.acceleo.internal.ide.ui.AcceleoUIMessages;
import org.eclipse.jface.preference.PreferencePage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;

/**
 * This is the preference page of the notifications of Acceleo.
 * 
 * @author <a href="mailto:stephane.begaudeau@obeo.fr">Stephane Begaudeau</a>
 */
public class AcceleoNotificationsPreferences extends PreferencePage implements IWorkbenchPreferencePage {

	/**
	 * The checkbox indicating if the notifications should be disabled by force.
	 */
	private Button forcedDisableNotificationButton;

	/**
	 * The checkbox indicating if the error notifications are enabled.
	 */
	private Button errorNotificationEnableButton;

	/**
	 * The checkbox indicating if the warning notifications are enabled.
	 */
	private Button warningNotificationEnableButton;

	/**
	 * The checkbox indicating if the ok notifications are enabled.
	 */
	private Button okNotificationEnableButton;

	/**
	 * The checkbox indicating if the success notifications are enabled.
	 */
	private Button successNotificationEnableButton;

	/**
	 * The checkbox indicating if the info notifications are enabled.
	 */
	private Button infoNotificationEnableButton;

	/**
	 * The checkbox indicating if the cancel notifications are enabled.
	 */
	private Button cancelNotificationEnableButton;

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.ui.IWorkbenchPreferencePage#init(org.eclipse.ui.IWorkbench)
	 */
	public void init(IWorkbench workbench) {
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

		forcedDisableNotificationButton = new Button(composite, SWT.CHECK);
		forcedDisableNotificationButton.setText(AcceleoUIMessages
				.getString("AcceleoNotificationsPreferences.ForceDisableButton")); //$NON-NLS-1$
		forcedDisableNotificationButton.addSelectionListener(new SelectionListener() {
			public void widgetSelected(SelectionEvent e) {
				updateForceDisable();
			}

			public void widgetDefaultSelected(SelectionEvent e) {
				updateForceDisable();
			}
		});

		errorNotificationEnableButton = new Button(composite, SWT.CHECK);
		errorNotificationEnableButton.setText(AcceleoUIMessages
				.getString("AcceleoNotificationsPreferences.ErrorNotificationsButton")); //$NON-NLS-1$

		warningNotificationEnableButton = new Button(composite, SWT.CHECK);
		warningNotificationEnableButton.setText(AcceleoUIMessages
				.getString("AcceleoNotificationsPreferences.WarningNotificationsButton")); //$NON-NLS-1$

		okNotificationEnableButton = new Button(composite, SWT.CHECK);
		okNotificationEnableButton.setText(AcceleoUIMessages
				.getString("AcceleoNotificationsPreferences.OKNotificationsButton")); //$NON-NLS-1$

		successNotificationEnableButton = new Button(composite, SWT.CHECK);
		successNotificationEnableButton.setText(AcceleoUIMessages
				.getString("AcceleoNotificationsPreferences.SuccessNotificationsButton")); //$NON-NLS-1$

		infoNotificationEnableButton = new Button(composite, SWT.CHECK);
		infoNotificationEnableButton.setText(AcceleoUIMessages
				.getString("AcceleoNotificationsPreferences.InfoNotificationsButton")); //$NON-NLS-1$

		cancelNotificationEnableButton = new Button(composite, SWT.CHECK);
		cancelNotificationEnableButton.setText(AcceleoUIMessages
				.getString("AcceleoNotificationsPreferences.CancelNotificationsButton")); //$NON-NLS-1$

		errorNotificationEnableButton.setSelection(AcceleoPreferences.areErrorNotificationsEnabled());
		warningNotificationEnableButton.setSelection(AcceleoPreferences.areWarningNotificationsEnabled());
		okNotificationEnableButton.setSelection(AcceleoPreferences.areOKNotificationsEnabled());
		successNotificationEnableButton.setSelection(AcceleoPreferences.areSuccessNotificationsEnabled());
		infoNotificationEnableButton.setSelection(AcceleoPreferences.areInfoNotificationsEnabled());
		cancelNotificationEnableButton.setSelection(AcceleoPreferences.areCancelNotificationsEnabled());

		forcedDisableNotificationButton.setSelection(AcceleoPreferences.areNotificationsForcedDisabled());
		updateForceDisable();

		return composite;
	}

	/**
	 * Update the states of the button to match the state of the force deactivation of the notification
	 * button.
	 */
	private void updateForceDisable() {
		boolean shouldDisable = !forcedDisableNotificationButton.getSelection();
		errorNotificationEnableButton.setEnabled(shouldDisable);
		warningNotificationEnableButton.setEnabled(shouldDisable);
		okNotificationEnableButton.setEnabled(shouldDisable);
		successNotificationEnableButton.setEnabled(shouldDisable);
		infoNotificationEnableButton.setEnabled(shouldDisable);
		cancelNotificationEnableButton.setEnabled(shouldDisable);
	}

	/**
	 * Update the preferences.
	 */
	private void updatePreferences() {
		AcceleoPreferences.switchForceDeactivationNotifications(forcedDisableNotificationButton
				.getSelection());
		AcceleoPreferences.switchErrorNotifications(errorNotificationEnableButton.getSelection());
		AcceleoPreferences.switchWarningNotifications(warningNotificationEnableButton.getSelection());
		AcceleoPreferences.switchOKNotifications(okNotificationEnableButton.getSelection());
		AcceleoPreferences.switchSuccessNotifications(successNotificationEnableButton.getSelection());
		AcceleoPreferences.switchInfoNotifications(infoNotificationEnableButton.getSelection());
		AcceleoPreferences.switchCancelNotifications(cancelNotificationEnableButton.getSelection());
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.jface.preference.PreferencePage#performApply()
	 */
	@Override
	protected void performApply() {
		updatePreferences();
		super.performApply();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.jface.preference.PreferencePage#performDefaults()
	 */
	@Override
	protected void performDefaults() {
		forcedDisableNotificationButton.setSelection(false);
		updateForceDisable();

		errorNotificationEnableButton.setSelection(true);
		warningNotificationEnableButton.setSelection(true);
		okNotificationEnableButton.setSelection(true);
		successNotificationEnableButton.setSelection(true);
		infoNotificationEnableButton.setSelection(true);
		cancelNotificationEnableButton.setSelection(true);
		super.performDefaults();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.jface.preference.PreferencePage#performCancel()
	 */
	@Override
	public boolean performCancel() {
		return super.performCancel();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.jface.preference.PreferencePage#performOk()
	 */
	@Override
	public boolean performOk() {
		updatePreferences();
		return super.performOk();
	}
}

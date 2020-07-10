package org.eclipse.acceleo.aql.ls.profile.ide.ui.launch;

import org.eclipse.acceleo.aql.ls.debug.ide.ui.dialog.FileTreeContentProvider;
import org.eclipse.acceleo.aql.ls.debug.ide.ui.dialog.ResourceSelectionDialog;
import org.eclipse.acceleo.aql.ls.debug.ide.ui.launch.AcceleoMainTab;
import org.eclipse.acceleo.aql.ls.profile.AcceleoProfiler;
import org.eclipse.acceleo.aql.ls.profile.ide.AcceleoProfilePlugin;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Status;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.debug.core.ILaunchConfigurationWorkingCopy;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Text;

public class AcceleoProfilerMainTab extends AcceleoMainTab {

	/**
	 * Profile model extension.
	 */
	private static final String PROFILE_EXTENSION = "mtlp"; //$NON-NLS-1$

	/**
	 * The profile model.
	 */
	private String profileModel;

	/**
	 * The initial profile model.
	 */
	private String initialProfileModel;

	/**
	 * The profile model {@link Text}.
	 */
	private Text profileModelText;

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.acceleo.aql.ls.debug.ide.ui.launch.AcceleoMainTab#setDefaults(org.eclipse.debug.core.ILaunchConfigurationWorkingCopy)
	 */
	@Override
	public void setDefaults(ILaunchConfigurationWorkingCopy configuration) {
		super.setDefaults(configuration);
		configuration.setAttribute(AcceleoProfiler.PROFILE_MODEL, profileModel);
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.acceleo.aql.ls.debug.ide.ui.launch.AcceleoMainTab#initializeFrom(org.eclipse.debug.core.ILaunchConfiguration)
	 */
	@Override
	public void initializeFrom(ILaunchConfiguration configuration) {
		super.initializeFrom(configuration);
		try {
			if (configuration.hasAttribute(AcceleoProfiler.PROFILE_MODEL)) {
				profileModelText.setText(configuration.getAttribute(AcceleoProfiler.PROFILE_MODEL, ""));
				initialProfileModel = profileModel;
			}
		} catch (CoreException e) {
			AcceleoProfilePlugin.getPlugin().log(new Status(IStatus.ERROR, AcceleoProfilePlugin.ID,
					"couldn't initialize from launch configuration", e));
		}
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.acceleo.aql.ls.debug.ide.ui.launch.AcceleoMainTab#performApply(org.eclipse.debug.core.ILaunchConfigurationWorkingCopy)
	 */
	@Override
	public void performApply(ILaunchConfigurationWorkingCopy configuration) {
		super.performApply(configuration);
		initialProfileModel = profileModel;
		setDirty(isDirty());
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.acceleo.aql.ls.debug.ide.ui.launch.AcceleoMainTab#isValid(org.eclipse.debug.core.ILaunchConfiguration)
	 */
	@Override
	public boolean isValid(ILaunchConfiguration launchConfig) {
		boolean res = super.isValid(launchConfig);
		try {
			if (res) {
				if (!launchConfig.hasAttribute(AcceleoProfiler.PROFILE_MODEL)) {
					setErrorMessage("Select a profile destination folder.");
					res = false;
				}
			}
		} catch (CoreException e) {
			AcceleoProfilePlugin.getPlugin().log(new Status(IStatus.ERROR, AcceleoProfilePlugin.ID,
					"couldn't validate launch configuration", e));
		}
		if (res) {
			setErrorMessage(null);
		}
		return res;
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.acceleo.aql.ls.debug.ide.ui.launch.AcceleoMainTab#isDirty()
	 */
	@Override
	protected boolean isDirty() {
		return super.isDirty() || profileModel != initialProfileModel;
	}

	@Override
	public void createControl(Composite parent) {
		super.createControl(parent);
		profileModelText = createProfileModelEditor((Composite)getControl());
	}

	/**
	 * Creates the profile destination {@link Composite}.
	 * 
	 * @param parent
	 *            the parent
	 * @return the created {@link Text}
	 */
	private Text createProfileModelEditor(final Composite parent) {
		final Composite destinationComposite = new Composite(parent, parent.getStyle());
		destinationComposite.setLayout(new GridLayout(3, false));
		destinationComposite.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		final Label destinationLabel = new Label(destinationComposite, parent.getStyle());
		destinationLabel.setText("Profile Destination:");
		final Text res = new Text(destinationComposite, parent.getStyle());
		res.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		res.addModifyListener(new ModifyListener() {

			@Override
			public void modifyText(ModifyEvent e) {
				profileModel = res.getText();
				setDirty(isDirty());
				updateLaunchConfigurationDialog();
			}
		});
		Button modelBrowseButton = new Button(destinationComposite, SWT.BORDER);
		modelBrowseButton.setText(BROWSE);
		modelBrowseButton.addListener(SWT.Selection, new Listener() {

			@Override
			public void handleEvent(Event event) {
				handleBrowseProfileModelButton();
			}
		});

		return res;
	}

	/**
	 * Show a dialog that lists all the models.
	 */
	private void handleBrowseProfileModelButton() {
		IResource initialResource;
		if (new Path(profileModelText.getText()).segmentCount() >= 2 && ResourcesPlugin.getWorkspace()
				.getRoot().getFile(new Path(profileModelText.getText())).exists()) {
			initialResource = ResourcesPlugin.getWorkspace().getRoot().getFile(new Path(profileModelText
					.getText()));
		} else {
			initialResource = ResourcesPlugin.getWorkspace().getRoot();
		}
		ResourceSelectionDialog dialog = new ResourceSelectionDialog(getShell(), initialResource,
				"Select profile destination folder."); //$NON-NLS-1$
		dialog.setContentProvider(new FileTreeContentProvider(true, PROFILE_EXTENSION));
		dialog.open();

		if (dialog.getResult() != null && dialog.getResult().length > 0 && dialog
				.getResult()[0] instanceof IPath && ((IPath)dialog.getResult()[0]).segmentCount() > 0) {

			String path = dialog.getResult()[0].toString();
			if (path.endsWith(PROFILE_EXTENSION)) {
				profileModelText.setText(path);
			} else if (path.endsWith("/")) { //$NON-NLS-1$
				profileModelText.setText(path + "profiling.mtlp"); //$NON-NLS-1$
			} else {
				profileModelText.setText(path + "/profiling.mtlp"); //$NON-NLS-1$
			}
		}
	}
}

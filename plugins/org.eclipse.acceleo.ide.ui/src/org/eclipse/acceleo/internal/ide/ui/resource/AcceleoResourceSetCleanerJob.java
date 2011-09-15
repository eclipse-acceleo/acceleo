package org.eclipse.acceleo.internal.ide.ui.resource;

import org.eclipse.acceleo.internal.ide.ui.AcceleoUIMessages;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;

/**
 * This job will clean the global resource set of the UI 5s after the closure of the latest Acceleo editor.
 * 
 * @author <a href="mailto:stephane.begaudeau@obeo.fr">Stephane Begaudeau</a>
 */
public class AcceleoResourceSetCleanerJob extends Job {

	/**
	 * The constructor.
	 */
	public AcceleoResourceSetCleanerJob() {
		super(AcceleoUIMessages.getString("AcceleoResourceSetCleanerJob.Name")); //$NON-NLS-1$
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.core.runtime.jobs.Job#run(org.eclipse.core.runtime.IProgressMonitor)
	 */
	@Override
	protected IStatus run(IProgressMonitor monitor) {
		AcceleoUIResourceSet.clear();
		return Status.OK_STATUS;
	}

}

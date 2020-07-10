package org.eclipse.acceleo.aql.ls.profile.ide.ui.launch;

import org.eclipse.acceleo.aql.ls.debug.ide.ui.launch.AcceleoMainTab;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;

public class AcceleoProfilerMainTab extends AcceleoMainTab {
	@Override
	public void createControl(Composite parent) {
		super.createControl(parent);
		Button button = new Button(parent, SWT.CHECK);
		button.setText("Activate profiling");
	}
}

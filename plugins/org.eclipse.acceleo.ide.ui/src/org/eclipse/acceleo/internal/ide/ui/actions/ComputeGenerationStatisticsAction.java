/*******************************************************************************
 * Copyright (c) 2008, 2013 Obeo.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.acceleo.internal.ide.ui.actions;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

import org.eclipse.acceleo.engine.AcceleoEngineMessages;
import org.eclipse.acceleo.ide.ui.AcceleoUIActivator;
import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IResourceVisitor;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.IObjectActionDelegate;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.PlatformUI;

/**
 * This class is used to compute some statistic on the generated files.
 * 
 * @author <a href="mailto:stephane.begaudeau@obeo.fr">Stephane Begaudeau</a>
 */
public class ComputeGenerationStatisticsAction implements IObjectActionDelegate {

	/**
	 * The current selection.
	 */
	private IStructuredSelection structuredSelection;

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.ui.IActionDelegate#run(org.eclipse.jface.action.IAction)
	 */
	public void run(IAction action) {
		final List<IContainer> containerToAnalyze = new ArrayList<IContainer>();

		// The report will be written in the output folder, let's compute the files to analyze
		Iterator<?> iterator = this.structuredSelection.iterator();
		while (iterator.hasNext()) {
			Object next = iterator.next();
			if (next instanceof IContainer) {
				containerToAnalyze.add((IContainer)next);
			} else if (next instanceof IFile) {
				containerToAnalyze.add(((IFile)next).getParent());
			}
		}

		IRunnableWithProgress runnableWithProgress = new IRunnableWithProgress() {

			public void run(IProgressMonitor monitor) throws InvocationTargetException, InterruptedException {
				// For all the containers selected that we should analyze
				for (IContainer iContainer : containerToAnalyze) {
					// Compute the list of the folders to take into account for this analysis
					final List<IContainer> containers = new ArrayList<IContainer>();
					try {
						iContainer.accept(new IResourceVisitor() {
							public boolean visit(IResource resource) throws CoreException {
								if (resource instanceof IContainer) {
									containers.add((IContainer)resource);
								}
								return true;
							}
						});
					} catch (CoreException e) {
						AcceleoUIActivator.log(e, true);
					}

					ComputeGenerationStatisticsAction.this.computeStatisticReport(iContainer, containers,
							monitor);
				}
			}
		};

		try {
			PlatformUI.getWorkbench().getActiveWorkbenchWindow().run(true, true, runnableWithProgress);
		} catch (InvocationTargetException e) {
			AcceleoUIActivator.log(e, true);
		} catch (InterruptedException e) {
			AcceleoUIActivator.log(e, true);
		}
	}

	/**
	 * Computes and write the statistic report file.
	 * 
	 * @param outputFolder
	 *            The folder where the report should be created
	 * @param containersToAnalyze
	 *            The containers to analyze
	 * @param monitor
	 *            the progress monitor
	 */
	private void computeStatisticReport(IContainer outputFolder, List<IContainer> containersToAnalyze,
			IProgressMonitor monitor) {
		List<StatisticEntry> statisticEntries = new ArrayList<StatisticEntry>();

		// The resource visitor will order the container in their reverse order, so we will sort them here
		Collections.sort(containersToAnalyze, new Comparator<IContainer>() {

			public int compare(IContainer arg0, IContainer arg1) {
				return arg0.getFullPath().toString().compareTo(arg1.getFullPath().toString());
			}
		});

		for (IContainer iContainer : containersToAnalyze) {
			String path = iContainer.getFullPath().toString();

			int linesOfCode = 0;
			int linesOfUserCode = 0;

			IResource[] members = new IResource[0];
			try {
				members = iContainer.members();
			} catch (CoreException e) {
				AcceleoUIActivator.log(e, true);
			}

			int numerOfFiles = 0;
			for (IResource iResource : members) {
				if (iResource instanceof IFile) {
					IFile iFile = (IFile)iResource;
					numerOfFiles++;

					FileInputStream fileInputStream = null;
					InputStreamReader inputStreamReader = null;
					BufferedReader bufferedReader = null;
					try {
						fileInputStream = new FileInputStream(iFile.getLocation().toFile());
						inputStreamReader = new InputStreamReader(fileInputStream);
						bufferedReader = new BufferedReader(inputStreamReader);

						boolean isUserCode = false;

						String strLine = ""; //$NON-NLS-1$
						while ((strLine = bufferedReader.readLine()) != null) {
							linesOfCode++;

							if (strLine.contains(AcceleoEngineMessages.getString("usercode.start"))) { //$NON-NLS-1$
								isUserCode = true;
							} else if (strLine.contains(AcceleoEngineMessages.getString("usercode.end"))) { //$NON-NLS-1$
								isUserCode = false;
							}

							if (isUserCode
									&& !strLine.contains(AcceleoEngineMessages.getString("usercode.start"))) { //$NON-NLS-1$
								linesOfUserCode++;
							}
						}
					} catch (FileNotFoundException e) {
						AcceleoUIActivator.log(e, true);
					} catch (IOException e) {
						AcceleoUIActivator.log(e, true);
					} finally {
						if (bufferedReader != null) {
							try {
								bufferedReader.close();
							} catch (IOException e) {
								AcceleoUIActivator.log(e, true);
							}
						}
					}
				}
			}

			// Skip if there are no data
			if (numerOfFiles > 0) {
				statisticEntries.add(new StatisticEntry(path, linesOfCode, linesOfUserCode));
			}
		}

		if (!monitor.isCanceled()) {
			StringBuffer buffer = new StringBuffer();
			buffer.append("Path,Lines of code,Lines of code generated,Lines of user code, Percentage of generated code"); //$NON-NLS-1$
			buffer.append(System.getProperty("line.separator")); //$NON-NLS-1$
			for (StatisticEntry statisticEntry : statisticEntries) {
				buffer.append(statisticEntry.toString() + System.getProperty("line.separator")); //$NON-NLS-1$
			}

			IFile csvFile = outputFolder.getFile(new Path("acceleo_statistics.csv")); //$NON-NLS-1$
			try {
				if (!csvFile.exists()) {
					csvFile.create(new ByteArrayInputStream(buffer.toString().getBytes()), true, monitor);
				} else {
					csvFile.setContents(new ByteArrayInputStream(buffer.toString().getBytes()), true, true,
							monitor);
				}
			} catch (CoreException e) {
				AcceleoUIActivator.log(e, true);
			}
		}
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.ui.IActionDelegate#selectionChanged(org.eclipse.jface.action.IAction,
	 *      org.eclipse.jface.viewers.ISelection)
	 */
	public void selectionChanged(IAction action, ISelection selection) {
		if (selection instanceof IStructuredSelection) {
			this.structuredSelection = (IStructuredSelection)selection;
		}
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.ui.IObjectActionDelegate#setActivePart(org.eclipse.jface.action.IAction,
	 *      org.eclipse.ui.IWorkbenchPart)
	 */
	public void setActivePart(IAction action, IWorkbenchPart targetPart) {
		// do nothing
	}

	/**
	 * An entry of the statistic.
	 * 
	 * @author <a href="mailto:stephane.begaudeau@obeo.fr">Stephane Begaudeau</a>
	 */
	public class StatisticEntry {
		/**
		 * The relative path of the file.
		 */
		private String path;

		/**
		 * The number of lines of code.
		 */
		private int linesOfCode;

		/**
		 * The number of lines of user code.
		 */
		private int linesOfUserCode;

		/**
		 * The constructor.
		 * 
		 * @param path
		 *            The relative path of the file
		 * @param linesOfCode
		 *            The number of lines of code
		 * @param linesOfUserCode
		 *            The number of lines of user code
		 */
		public StatisticEntry(String path, int linesOfCode, int linesOfUserCode) {
			super();
			this.path = path;
			this.linesOfCode = linesOfCode;
			this.linesOfUserCode = linesOfUserCode;
		}

		/**
		 * Returns the relative path of the file.
		 * 
		 * @return The relative path of the file
		 */
		public String getPath() {
			return this.path;
		}

		/**
		 * Returns the number of lines of code.
		 * 
		 * @return The number of lines of code
		 */
		public int getLinesOfCode() {
			return this.linesOfCode;
		}

		/**
		 * Returns the number of lines of code generated.
		 * 
		 * @return The number of lines of code generated
		 */
		public int getLinesOfCodeGenerated() {
			return this.linesOfCode - this.linesOfUserCode;
		}

		/**
		 * Returns the number of lines of user code.
		 * 
		 * @return The number of lines of user code
		 */
		public int getLinesOfUserCode() {
			return this.linesOfUserCode;
		}

		/**
		 * Returns the percentage of generated code.
		 * 
		 * @return The percentage of generated code
		 */
		public double getPercentageOfGeneratedCode() {
			if (Double.valueOf(this.linesOfCode).doubleValue()
					* Double.valueOf(this.getLinesOfCodeGenerated()).doubleValue() > 0) {
				return 100d / Double.valueOf(this.linesOfCode).doubleValue()
						* Double.valueOf(this.getLinesOfCodeGenerated()).doubleValue();
			}
			return 0;
		}

		/**
		 * {@inheritDoc}
		 * 
		 * @see java.lang.Object#toString()
		 */
		@Override
		public String toString() {
			return this.getPath() + ',' + this.getLinesOfCode() + ',' + this.getLinesOfCodeGenerated() + ','
					+ this.getLinesOfUserCode() + ",\"" + String.format("%.2f", Double.valueOf(this //$NON-NLS-1$ //$NON-NLS-2$
							.getPercentageOfGeneratedCode())) + "\""; //$NON-NLS-1$
		}
	}
}

package org.eclipse.acceleo.internal.ide.ui.wizards.newproject;

public class CreateModuleUIPopupMenuActionWriter
{
  protected static String nl;
  public static synchronized CreateModuleUIPopupMenuActionWriter create(String lineSeparator)
  {
    nl = lineSeparator;
    CreateModuleUIPopupMenuActionWriter result = new CreateModuleUIPopupMenuActionWriter();
    nl = null;
    return result;
  }

  public final String NL = nl == null ? (System.getProperties().getProperty("line.separator")) : nl;
  protected final String TEXT_1 = "/*******************************************************************************" + NL + " * Copyright (c) 2008, 2012 Obeo." + NL + " * All rights reserved. This program and the accompanying materials" + NL + " * are made available under the terms of the Eclipse Public License v1.0" + NL + " * which accompanies this distribution, and is available at" + NL + " * http://www.eclipse.org/legal/epl-v10.html" + NL + " * " + NL + " * Contributors:" + NL + " *     Obeo - initial API and implementation" + NL + " *******************************************************************************/" + NL + "package ";
  protected final String TEXT_2 = ".popupMenus;" + NL + "" + NL + "import java.io.IOException;" + NL + "import java.lang.reflect.InvocationTargetException;" + NL + "import java.util.Iterator;" + NL + "import java.util.List;" + NL + "import java.util.ArrayList;" + NL + "" + NL + "import org.eclipse.core.resources.IFile;" + NL + "import org.eclipse.core.resources.IContainer;" + NL + "import org.eclipse.core.resources.IResource;" + NL + "import org.eclipse.core.runtime.CoreException;" + NL + "import org.eclipse.core.runtime.IProgressMonitor;" + NL + "import org.eclipse.core.runtime.IStatus;" + NL + "import org.eclipse.core.runtime.Status;" + NL + "import org.eclipse.emf.common.util.URI;" + NL + "import org.eclipse.jface.action.IAction;" + NL + "import org.eclipse.jface.operation.IRunnableWithProgress;" + NL + "import org.eclipse.jface.viewers.ISelection;" + NL + "import org.eclipse.jface.viewers.IStructuredSelection;" + NL + "import ";
  protected final String TEXT_3 = ".Activator;" + NL + "import ";
  protected final String TEXT_4 = ".common.GenerateAll;" + NL + "import org.eclipse.ui.IActionDelegate;" + NL + "import org.eclipse.ui.PlatformUI;" + NL + "import org.eclipse.ui.actions.ActionDelegate;" + NL + "" + NL + "/**" + NL + " * ";
  protected final String TEXT_5 = " code generation." + NL + " */" + NL + "public class AcceleoGenerate";
  protected final String TEXT_6 = "Action extends ActionDelegate implements IActionDelegate {" + NL + "\t" + NL + "\t/**" + NL + "\t * Selected model files." + NL + "\t */" + NL + "\tprotected List<IFile> files;" + NL + "" + NL + "\t/**{@inheritDoc}" + NL + "\t *" + NL + "\t * @see org.eclipse.ui.actions.ActionDelegate#selectionChanged(org.eclipse.jface.action.IAction, org.eclipse.jface.viewers.ISelection)" + NL + "\t * @generated" + NL + "\t */" + NL + "\t@SuppressWarnings(\"unchecked\")" + NL + "\tpublic void selectionChanged(IAction action, ISelection selection) {" + NL + "\t\tif (selection instanceof IStructuredSelection) {" + NL + "\t\t\tfiles = ((IStructuredSelection) selection).toList();" + NL + "\t\t}" + NL + "\t}" + NL + "" + NL + "\t/**{@inheritDoc}" + NL + "\t *" + NL + "\t * @see org.eclipse.ui.actions.ActionDelegate#run(org.eclipse.jface.action.IAction)" + NL + "\t * @generated" + NL + "\t */" + NL + "\tpublic void run(IAction action) {" + NL + "\t\tif (files != null) {" + NL + "\t\t\tIRunnableWithProgress operation = new IRunnableWithProgress() {" + NL + "\t\t\t\tpublic void run(IProgressMonitor monitor) {" + NL + "\t\t\t\t\ttry {" + NL + "\t\t\t\t\t\tIterator<IFile> filesIt = files.iterator();" + NL + "\t\t\t\t\t\twhile (filesIt.hasNext()) {" + NL + "\t\t\t\t\t\t\tIFile model = (IFile)filesIt.next();" + NL + "\t\t\t\t\t\t\tURI modelURI = URI.createPlatformResourceURI(model.getFullPath().toString(), true);" + NL + "\t\t\t\t\t\t\ttry {" + NL + "\t\t\t\t\t\t\t\t";
  protected final String TEXT_7 = NL + "\t\t\t\t\t\t\t\tGenerateAll generator = new GenerateAll(modelURI, target, getArguments());" + NL + "\t\t\t\t\t\t\t\tgenerator.doGenerate(monitor);" + NL + "\t\t\t\t\t\t\t} catch (IOException e) {" + NL + "\t\t\t\t\t\t\t\tIStatus status = new Status(IStatus.ERROR, Activator.PLUGIN_ID, e.getMessage(), e);" + NL + "\t\t\t\t\t\t\t\tActivator.getDefault().getLog().log(status);" + NL + "\t\t\t\t\t\t\t} finally {" + NL + "\t\t\t\t\t\t\t\tmodel.getProject().refreshLocal(IResource.DEPTH_INFINITE, monitor);" + NL + "\t\t\t\t\t\t\t}" + NL + "\t\t\t\t\t\t}" + NL + "\t\t\t\t\t} catch (CoreException e) {" + NL + "\t\t\t\t\t\tIStatus status = new Status(IStatus.ERROR, Activator.PLUGIN_ID, e.getMessage(), e);" + NL + "\t\t\t\t\t\tActivator.getDefault().getLog().log(status);" + NL + "\t\t\t\t\t}" + NL + "\t\t\t\t}" + NL + "\t\t\t};" + NL + "\t\t\ttry {" + NL + "\t\t\t\tPlatformUI.getWorkbench().getProgressService().run(true, true, operation);" + NL + "\t\t\t} catch (InvocationTargetException e) {" + NL + "\t\t\t\tIStatus status = new Status(IStatus.ERROR, Activator.PLUGIN_ID, e.getMessage(), e);" + NL + "\t\t\t\tActivator.getDefault().getLog().log(status);" + NL + "\t\t\t} catch (InterruptedException e) {" + NL + "\t\t\t\tIStatus status = new Status(IStatus.ERROR, Activator.PLUGIN_ID, e.getMessage(), e);" + NL + "\t\t\t\tActivator.getDefault().getLog().log(status);" + NL + "\t\t\t}" + NL + "\t\t}" + NL + "\t}" + NL + "" + NL + "\t/**" + NL + "\t * Computes the arguments of the generator." + NL + "\t * " + NL + "\t * @return the arguments" + NL + "\t * @generated" + NL + "\t */" + NL + "\tprotected List<? extends Object> getArguments() {" + NL + "\t\treturn new ArrayList<String>();" + NL + "\t}" + NL + "" + NL + "}";

  public String generate(Object argument)
  {
    final StringBuffer stringBuffer = new StringBuffer();
    
 CreateModuleUIData content = (CreateModuleUIData) argument;

    stringBuffer.append(TEXT_1);
    stringBuffer.append(content.getProjectName());
    stringBuffer.append(TEXT_2);
    stringBuffer.append(content.getProjectName());
    stringBuffer.append(TEXT_3);
    stringBuffer.append(content.getProjectName());
    stringBuffer.append(TEXT_4);
    stringBuffer.append(content.getModuleNameWithSpaces());
    stringBuffer.append(TEXT_5);
    stringBuffer.append(content.getModuleNameWithoutSpaces());
    stringBuffer.append(TEXT_6);
    stringBuffer.append(content.getTargetFolderAccess().replaceAll("\n", "\n\t\t\t\t\t\t\t\t"));
    stringBuffer.append(TEXT_7);
    return stringBuffer.toString();
  }
}

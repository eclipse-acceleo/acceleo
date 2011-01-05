package org.eclipse.acceleo.internal.ide.ui.wizards.newproject;

public class CreateModuleActivatorWriter
{
  protected static String nl;
  public static synchronized CreateModuleActivatorWriter create(String lineSeparator)
  {
    nl = lineSeparator;
    CreateModuleActivatorWriter result = new CreateModuleActivatorWriter();
    nl = null;
    return result;
  }

  public final String NL = nl == null ? (System.getProperties().getProperty("line.separator")) : nl;
  protected final String TEXT_1 = "/*******************************************************************************" + NL + " * Copyright (c) 2008, 2011 Obeo." + NL + " * All rights reserved. This program and the accompanying materials" + NL + " * are made available under the terms of the Eclipse Public License v1.0" + NL + " * which accompanies this distribution, and is available at" + NL + " * http://www.eclipse.org/legal/epl-v10.html" + NL + " * " + NL + " * Contributors:" + NL + " *     Obeo - initial API and implementation" + NL + " *******************************************************************************/" + NL + "package ";
  protected final String TEXT_2 = ";" + NL + "" + NL + "import org.eclipse.core.runtime.Plugin;" + NL + "import org.osgi.framework.BundleContext;" + NL + "" + NL + "/**" + NL + " * The activator class controls the plug-in life cycle." + NL + " */" + NL + "public class Activator extends Plugin {" + NL + "" + NL + "\t/**" + NL + "\t * The plug-in ID." + NL + "\t */" + NL + "\tpublic static final String PLUGIN_ID = \"";
  protected final String TEXT_3 = "\";" + NL + "" + NL + "\t/**" + NL + "\t * The shared instance." + NL + "\t */" + NL + "\tprivate static Activator plugin;" + NL + "\t" + NL + "\t/**" + NL + "\t * The constructor." + NL + "\t */" + NL + "\tpublic Activator() {" + NL + "\t}" + NL + "" + NL + "\t/**{@inheritDoc}" + NL + "\t *" + NL + "\t * @see org.eclipse.core.runtime.Plugin#start(org.osgi.framework.BundleContext)" + NL + "\t */" + NL + "\tpublic void start(BundleContext context) throws Exception {" + NL + "\t\tsuper.start(context);" + NL + "\t\tplugin = this;" + NL + "\t}" + NL + "" + NL + "\t/**{@inheritDoc}" + NL + "\t *" + NL + "\t * @see org.eclipse.core.runtime.Plugin#stop(org.osgi.framework.BundleContext)" + NL + "\t */" + NL + "\tpublic void stop(BundleContext context) throws Exception {" + NL + "\t\tplugin = null;" + NL + "\t\tsuper.stop(context);" + NL + "\t}" + NL + "" + NL + "\t/**" + NL + "\t * Returns the shared instance." + NL + "\t *" + NL + "\t * @return the shared instance" + NL + "\t */" + NL + "\tpublic static Activator getDefault() {" + NL + "\t\treturn plugin;" + NL + "\t}" + NL + "" + NL + "}";
  protected final String TEXT_4 = NL;

  public String generate(Object argument)
  {
    final StringBuffer stringBuffer = new StringBuffer();
    
 CreateModuleData content = (CreateModuleData) argument;

    stringBuffer.append(TEXT_1);
    stringBuffer.append(content.getProjectName());
    stringBuffer.append(TEXT_2);
    stringBuffer.append(content.getProjectName());
    stringBuffer.append(TEXT_3);
    stringBuffer.append(TEXT_4);
    return stringBuffer.toString();
  }
}

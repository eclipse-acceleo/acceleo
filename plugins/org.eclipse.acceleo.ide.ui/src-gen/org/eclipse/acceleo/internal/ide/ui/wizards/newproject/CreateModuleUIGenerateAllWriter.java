package org.eclipse.acceleo.internal.ide.ui.wizards.newproject;

public class CreateModuleUIGenerateAllWriter
{
  protected static String nl;
  public static synchronized CreateModuleUIGenerateAllWriter create(String lineSeparator)
  {
    nl = lineSeparator;
    CreateModuleUIGenerateAllWriter result = new CreateModuleUIGenerateAllWriter();
    nl = null;
    return result;
  }

  public final String NL = nl == null ? (System.getProperties().getProperty("line.separator")) : nl;
  protected final String TEXT_1 = "/*******************************************************************************" + NL + " * Copyright (c) 2008, 2012 Obeo." + NL + " * All rights reserved. This program and the accompanying materials" + NL + " * are made available under the terms of the Eclipse Public License v1.0" + NL + " * which accompanies this distribution, and is available at" + NL + " * http://www.eclipse.org/legal/epl-v10.html" + NL + " * " + NL + " * Contributors:" + NL + " *     Obeo - initial API and implementation" + NL + " *******************************************************************************/" + NL + "package ";
  protected final String TEXT_2 = ".common;" + NL + "" + NL + "import java.io.IOException;" + NL + "import java.net.URL;" + NL + "import java.util.ArrayList;" + NL + "import java.util.Enumeration;" + NL + "import java.util.List;" + NL + "" + NL + "import org.eclipse.emf.common.util.BasicMonitor;" + NL + "import org.eclipse.core.resources.IContainer;" + NL + "import org.eclipse.core.runtime.IPath;" + NL + "import org.eclipse.core.runtime.IProgressMonitor;" + NL + "import org.eclipse.core.runtime.Path;" + NL + "import org.eclipse.core.runtime.Platform;" + NL + "import org.eclipse.emf.common.util.URI;";
  protected final String TEXT_3 = NL + "import org.eclipse.emf.ecore.EObject;";
  protected final String TEXT_4 = NL + "import org.osgi.framework.Bundle;" + NL + "" + NL + "" + NL + "/**" + NL + " * Main entry point of the '";
  protected final String TEXT_5 = "' generation module." + NL + " */" + NL + "public class GenerateAll {" + NL + "" + NL + "\t/**" + NL + "\t * The model URI." + NL + "\t */" + NL + "\tprivate URI modelURI;" + NL + "" + NL + "\t/**" + NL + "\t * The output folder." + NL + "\t */" + NL + "\tprivate IContainer targetFolder;" + NL + "" + NL + "\t/**" + NL + "\t * The other arguments." + NL + "\t */" + NL + "\tList<? extends Object> arguments;" + NL + "" + NL + "\t/**" + NL + "\t * Constructor." + NL + "\t * " + NL + "\t * @param modelURI" + NL + "\t *            is the URI of the model." + NL + "\t * @param targetFolder" + NL + "\t *            is the output folder" + NL + "\t * @param arguments" + NL + "\t *            are the other arguments" + NL + "\t * @throws IOException" + NL + "\t *             Thrown when the output cannot be saved." + NL + "\t * @generated" + NL + "\t */" + NL + "\tpublic GenerateAll(URI modelURI, IContainer targetFolder, List<? extends Object> arguments) {" + NL + "\t\tthis.modelURI = modelURI;" + NL + "\t\tthis.targetFolder = targetFolder;" + NL + "\t\tthis.arguments = arguments;" + NL + "\t}" + NL + "" + NL + "\t/**" + NL + "\t * Launches the generation." + NL + "\t *" + NL + "\t * @param monitor" + NL + "\t *            This will be used to display progress information to the user." + NL + "\t * @throws IOException" + NL + "\t *             Thrown when the output cannot be saved." + NL + "\t * @generated" + NL + "\t */" + NL + "\tpublic void doGenerate(IProgressMonitor monitor) throws IOException {" + NL + "\t\tif (!targetFolder.getLocation().toFile().exists()) {" + NL + "\t\t\ttargetFolder.getLocation().toFile().mkdirs();" + NL + "\t\t}" + NL + "\t\t";
  protected final String TEXT_6 = NL + "\t\t// final URI template0 = getTemplateURI(\"";
  protected final String TEXT_7 = "\", new Path(\"";
  protected final String TEXT_8 = "\"));" + NL + "\t\t// ";
  protected final String TEXT_9 = " gen0 = new ";
  protected final String TEXT_10 = "(modelURI, targetFolder.getLocation().toFile(), arguments) {" + NL + "\t\t//\tprotected URI createTemplateURI(String entry) {" + NL + "\t\t//\t\treturn template0;" + NL + "\t\t//\t}" + NL + "\t\t//};" + NL + "\t\t//gen0.doGenerate(BasicMonitor.toMonitor(monitor));" + NL + "\t\tmonitor.subTask(\"Loading...\");" + NL + "\t\t";
  protected final String TEXT_11 = " gen0 = new ";
  protected final String TEXT_12 = "(modelURI, targetFolder.getLocation().toFile(), arguments);" + NL + "\t\tmonitor.worked(1);" + NL + "\t\tString generationID = org.eclipse.acceleo.engine.utils.AcceleoLaunchingUtil.computeUIProjectID(\"";
  protected final String TEXT_13 = "\", \"";
  protected final String TEXT_14 = "\", modelURI.toString(), targetFolder.getFullPath().toString(), new ArrayList<String>());" + NL + "\t\tgen0.setGenerationID(generationID);" + NL + "\t\tgen0.doGenerate(BasicMonitor.toMonitor(monitor));" + NL + "\t\t\t";
  protected final String TEXT_15 = NL + "\t\tEObject model = gen0.getModel();" + NL + "\t\tif (model != null) {" + NL + "\t\t\t\t";
  protected final String TEXT_16 = NL + "\t\t\t//final URI template";
  protected final String TEXT_17 = " = getTemplateURI(\"";
  protected final String TEXT_18 = "\", new Path(\"";
  protected final String TEXT_19 = "\"));" + NL + "\t\t\t//";
  protected final String TEXT_20 = " gen";
  protected final String TEXT_21 = " = new ";
  protected final String TEXT_22 = "(model, targetFolder.getLocation().toFile(), arguments) {" + NL + "\t\t\t//\tprotected URI createTemplateURI(String entry) {" + NL + "\t\t\t//\t\treturn template";
  protected final String TEXT_23 = ";" + NL + "\t\t\t//\t}" + NL + "\t\t\t//};" + NL + "\t\t\t//gen";
  protected final String TEXT_24 = ".doGenerate(BasicMonitor.toMonitor(monitor));" + NL + "\t\t\t" + NL + "\t\t\tmonitor.subTask(\"Loading...\");" + NL + "\t\t\t";
  protected final String TEXT_25 = " gen";
  protected final String TEXT_26 = " = new ";
  protected final String TEXT_27 = "(model, targetFolder.getLocation().toFile(), arguments);" + NL + "\t\t\tmonitor.worked(1);" + NL + "\t\t\tgenerationID = org.eclipse.acceleo.engine.utils.AcceleoLaunchingUtil.computeUIProjectID(\"";
  protected final String TEXT_28 = "\", \"";
  protected final String TEXT_29 = "\", modelURI.toString(), targetFolder.getFullPath().toString(), new ArrayList<String>());" + NL + "\t\t\tgen";
  protected final String TEXT_30 = ".setGenerationID(generationID);" + NL + "\t\t\tgen";
  protected final String TEXT_31 = ".doGenerate(BasicMonitor.toMonitor(monitor));";
  protected final String TEXT_32 = NL + "\t\t}" + NL + "\t\t\t";
  protected final String TEXT_33 = NL + "\t\t";
  protected final String TEXT_34 = NL + "\t}" + NL + "\t" + NL + "\t/**" + NL + "\t * Finds the template in the plug-in. Returns the template plug-in URI." + NL + "\t * " + NL + "\t * @param bundleID" + NL + "\t *            is the plug-in ID" + NL + "\t * @param relativePath" + NL + "\t *            is the relative path of the template in the plug-in" + NL + "\t * @return the template URI" + NL + "\t * @throws IOException" + NL + "\t * @generated" + NL + "\t */" + NL + "\t@SuppressWarnings(\"unchecked\")" + NL + "\tprivate URI getTemplateURI(String bundleID, IPath relativePath) throws IOException {" + NL + "\t\tBundle bundle = Platform.getBundle(bundleID);" + NL + "\t\tif (bundle == null) {" + NL + "\t\t\t// no need to go any further" + NL + "\t\t\treturn URI.createPlatformResourceURI(new Path(bundleID).append(relativePath).toString(), false);" + NL + "\t\t}" + NL + "\t\tURL url = bundle.getEntry(relativePath.toString());" + NL + "\t\tif (url == null && relativePath.segmentCount() > 1) {" + NL + "\t\t\tEnumeration<URL> entries = bundle.findEntries(\"/\", \"*.emtl\", true);" + NL + "\t\t\tif (entries != null) {" + NL + "\t\t\t\tString[] segmentsRelativePath = relativePath.segments();" + NL + "\t\t\t\twhile (url == null && entries.hasMoreElements()) {" + NL + "\t\t\t\t\tURL entry = entries.nextElement();" + NL + "\t\t\t\t\tIPath path = new Path(entry.getPath());" + NL + "\t\t\t\t\tif (path.segmentCount() > relativePath.segmentCount()) {" + NL + "\t\t\t\t\t\tpath = path.removeFirstSegments(path.segmentCount() - relativePath.segmentCount());" + NL + "\t\t\t\t\t}" + NL + "\t\t\t\t\tString[] segmentsPath = path.segments();" + NL + "\t\t\t\t\tboolean equals = segmentsPath.length == segmentsRelativePath.length;" + NL + "\t\t\t\t\tfor (int i = 0; equals && i < segmentsPath.length; i++) {" + NL + "\t\t\t\t\t\tequals = segmentsPath[i].equals(segmentsRelativePath[i]);" + NL + "\t\t\t\t\t}" + NL + "\t\t\t\t\tif (equals) {" + NL + "\t\t\t\t\t\turl = bundle.getEntry(entry.getPath());" + NL + "\t\t\t\t\t}" + NL + "\t\t\t\t}" + NL + "\t\t\t}" + NL + "\t\t}" + NL + "\t\tURI result;" + NL + "\t\tif (url != null) {" + NL + "\t\t\tresult = URI.createPlatformPluginURI(new Path(bundleID).append(new Path(url.getPath())).toString(), false);" + NL + "\t\t} else {" + NL + "\t\t\tresult = URI.createPlatformResourceURI(new Path(bundleID).append(relativePath).toString(), false);" + NL + "\t\t}" + NL + "\t\treturn result;" + NL + "\t}" + NL + "" + NL + "}";
  protected final String TEXT_35 = NL;

  public String generate(Object argument)
  {
    final StringBuffer stringBuffer = new StringBuffer();
    
 CreateModuleUIData content = (CreateModuleUIData) argument;

    stringBuffer.append(TEXT_1);
    stringBuffer.append(content.getProjectName());
    stringBuffer.append(TEXT_2);
    if (content.getModuleJavaClasses().size() > 1) {
    stringBuffer.append(TEXT_3);
    }
    stringBuffer.append(TEXT_4);
    stringBuffer.append(content.getModuleNameWithSpaces());
    stringBuffer.append(TEXT_5);
    if (content.getModuleJavaClasses().size() > 0) {
    stringBuffer.append(TEXT_6);
    stringBuffer.append(content.getModuleTemplatesPlugins().get(0));
    stringBuffer.append(TEXT_7);
    stringBuffer.append(content.getModuleTemplates().get(0));
    stringBuffer.append(TEXT_8);
    stringBuffer.append(content.getModuleJavaClasses().get(0));
    stringBuffer.append(TEXT_9);
    stringBuffer.append(content.getModuleJavaClasses().get(0));
    stringBuffer.append(TEXT_10);
    stringBuffer.append(content.getModuleJavaClasses().get(0));
    stringBuffer.append(TEXT_11);
    stringBuffer.append(content.getModuleJavaClasses().get(0));
    stringBuffer.append(TEXT_12);
    stringBuffer.append(content.getProjectFromClass(content.getModuleJavaClasses().get(0)));
    stringBuffer.append(TEXT_13);
    stringBuffer.append(content.getModuleJavaClasses().get(0));
    stringBuffer.append(TEXT_14);
    if (content.getModuleJavaClasses().size() > 1) {
    stringBuffer.append(TEXT_15);
    for (int i = 1; i < content.getModuleJavaClasses().size(); i++) {
    stringBuffer.append(TEXT_16);
    stringBuffer.append(i);
    stringBuffer.append(TEXT_17);
    stringBuffer.append(content.getModuleTemplatesPlugins().get(i));
    stringBuffer.append(TEXT_18);
    stringBuffer.append(content.getModuleTemplates().get(i));
    stringBuffer.append(TEXT_19);
    stringBuffer.append(content.getModuleJavaClasses().get(i));
    stringBuffer.append(TEXT_20);
    stringBuffer.append(i);
    stringBuffer.append(TEXT_21);
    stringBuffer.append(content.getModuleJavaClasses().get(i));
    stringBuffer.append(TEXT_22);
    stringBuffer.append(i);
    stringBuffer.append(TEXT_23);
    stringBuffer.append(i);
    stringBuffer.append(TEXT_24);
    stringBuffer.append(content.getModuleJavaClasses().get(i));
    stringBuffer.append(TEXT_25);
    stringBuffer.append(i);
    stringBuffer.append(TEXT_26);
    stringBuffer.append(content.getModuleJavaClasses().get(i));
    stringBuffer.append(TEXT_27);
    stringBuffer.append(content.getProjectFromClass(content.getModuleJavaClasses().get(i)));
    stringBuffer.append(TEXT_28);
    stringBuffer.append(content.getModuleJavaClasses().get(i));
    stringBuffer.append(TEXT_29);
    stringBuffer.append(i);
    stringBuffer.append(TEXT_30);
    stringBuffer.append(i);
    stringBuffer.append(TEXT_31);
    }
    stringBuffer.append(TEXT_32);
    }
    stringBuffer.append(TEXT_33);
    }
    stringBuffer.append(TEXT_34);
    stringBuffer.append(TEXT_35);
    return stringBuffer.toString();
  }
}

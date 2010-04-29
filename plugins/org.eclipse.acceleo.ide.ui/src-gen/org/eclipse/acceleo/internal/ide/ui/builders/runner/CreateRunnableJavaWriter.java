package org.eclipse.acceleo.internal.ide.ui.builders.runner;

public class CreateRunnableJavaWriter
{
  protected static String nl;
  public static synchronized CreateRunnableJavaWriter create(String lineSeparator)
  {
    nl = lineSeparator;
    CreateRunnableJavaWriter result = new CreateRunnableJavaWriter();
    nl = null;
    return result;
  }

  public final String NL = nl == null ? (System.getProperties().getProperty("line.separator")) : nl;
  protected final String TEXT_1 = "/*******************************************************************************" + NL + " * Copyright (c) 2008, 2010 Obeo." + NL + " * All rights reserved. This program and the accompanying materials" + NL + " * are made available under the terms of the Eclipse Public License v1.0" + NL + " * which accompanies this distribution, and is available at" + NL + " * http://www.eclipse.org/legal/epl-v10.html" + NL + " * " + NL + " * Contributors:" + NL + " *     Obeo - initial API and implementation" + NL + " *******************************************************************************/" + NL + "package ";
  protected final String TEXT_2 = ";" + NL + "" + NL + "import java.io.File;" + NL + "import java.io.IOException;" + NL + "import java.util.ArrayList;" + NL + "import java.util.List;" + NL + "" + NL + "import org.eclipse.acceleo.engine.event.IAcceleoTextGenerationListener;" + NL + "import org.eclipse.acceleo.engine.generation.strategy.IAcceleoGenerationStrategy;" + NL + "import org.eclipse.acceleo.engine.service.AbstractAcceleoGenerator;" + NL + "import org.eclipse.emf.common.util.BasicMonitor;" + NL + "import org.eclipse.emf.common.util.Monitor;" + NL + "import org.eclipse.emf.common.util.URI;" + NL + "import org.eclipse.emf.ecore.EObject;" + NL + "import org.eclipse.emf.ecore.resource.ResourceSet;" + NL + "" + NL + "/**" + NL + " * Entry point of the '";
  protected final String TEXT_3 = "' generation module." + NL + " *" + NL + " * @generated" + NL + " */" + NL + "public class ";
  protected final String TEXT_4 = " extends AbstractAcceleoGenerator {" + NL + "\t/**" + NL + "\t * The name of the module." + NL + "\t *" + NL + "\t * @generated" + NL + "\t */" + NL + "\tpublic static final String MODULE_FILE_NAME = \"";
  protected final String TEXT_5 = "\";" + NL + "\t" + NL + "\t/**" + NL + "\t * The name of the templates that are to be generated." + NL + "\t *" + NL + "\t * @generated" + NL + "\t */" + NL + "\tpublic static final String[] TEMPLATE_NAMES = { ";
  protected final String TEXT_6 = "\"";
  protected final String TEXT_7 = "\", ";
  protected final String TEXT_8 = "};" + NL + "" + NL + "\t/**" + NL + "\t * Allows the public constructor to be used. Note that a generator created" + NL + "\t * this way cannot be used to launch generations before one of" + NL + "\t * {@link #initialize(EObject, File, List)} or" + NL + "\t * {@link #initialize(URI, File, List)} is called." + NL + "\t * <p>" + NL + "\t * The main reason for this constructor is to allow clients of this" + NL + "\t * generation to call it from another Java file, as it allows for the" + NL + "\t * retrieval of {@link #getProperties()} and" + NL + "\t * {@link #getGenerationListeners()}." + NL + "\t * </p>" + NL + "\t *" + NL + "\t * @generated" + NL + "\t */" + NL + "\tpublic ";
  protected final String TEXT_9 = "() {" + NL + "\t\t// Empty implementation" + NL + "\t}" + NL + "" + NL + "\t/**" + NL + "\t * This allows clients to instantiates a generator with all required information." + NL + "\t * " + NL + "\t * @param modelURI" + NL + "\t *            URI where the model on which this generator will be used is located." + NL + "\t * @param targetFolder" + NL + "\t *            This will be used as the output folder for this generation : it will be the base path" + NL + "\t *            against which all file block URLs will be resolved." + NL + "\t * @param arguments" + NL + "\t *            If the template which will be called requires more than one argument taken from the model," + NL + "\t *            pass them here." + NL + "\t * @throws IOException" + NL + "\t *             This can be thrown in three scenarios : the module cannot be found, it cannot be loaded, or" + NL + "\t *             the model cannot be loaded." + NL + "\t * @generated" + NL + "\t */" + NL + "\tpublic ";
  protected final String TEXT_10 = "(URI modelURI, File targetFolder," + NL + "\t\t\tList<? extends Object> arguments) throws IOException {" + NL + "\t\tinitialize(modelURI, targetFolder, arguments);" + NL + "\t}" + NL + "" + NL + "\t/**" + NL + "\t * This allows clients to instantiates a generator with all required information." + NL + "\t * " + NL + "\t * @param model" + NL + "\t *            We'll iterate over the content of this element to find Objects matching the first parameter" + NL + "\t *            of the template we need to call." + NL + "\t * @param targetFolder" + NL + "\t *            This will be used as the output folder for this generation : it will be the base path" + NL + "\t *            against which all file block URLs will be resolved." + NL + "\t * @param arguments" + NL + "\t *            If the template which will be called requires more than one argument taken from the model," + NL + "\t *            pass them here." + NL + "\t * @throws IOException" + NL + "\t *             This can be thrown in two scenarios : the module cannot be found, or it cannot be loaded." + NL + "\t * @generated" + NL + "\t */" + NL + "\tpublic ";
  protected final String TEXT_11 = "(EObject model, File targetFolder," + NL + "\t\t\tList<? extends Object> arguments) throws IOException {" + NL + "\t\tinitialize(model, targetFolder, arguments);" + NL + "\t}" + NL + "\t" + NL + "\t/**" + NL + "\t * This can be used to launch the generation from a standalone application." + NL + "\t * " + NL + "\t * @param args" + NL + "\t *            Arguments of the generation." + NL + "\t * @generated" + NL + "\t */" + NL + "\tpublic static void main(String[] args) {" + NL + "\t\ttry {" + NL + "\t\t\tif (args.length < 2) {" + NL + "\t\t\t\tSystem.out.println(\"Arguments not valid : {model, folder}.\");" + NL + "\t\t\t} else {" + NL + "\t\t\t\tURI modelURI = URI.createFileURI(args[0]);" + NL + "\t\t\t\tFile folder = new File(args[1]);" + NL + "\t\t\t\tList<String> arguments = new ArrayList<String>();" + NL + "\t\t\t\tfor (int i = 2; i < args.length; i++) {" + NL + "\t\t\t\t\targuments.add(args[i]);" + NL + "\t\t\t\t}" + NL + "\t\t\t\t";
  protected final String TEXT_12 = " generator = new ";
  protected final String TEXT_13 = "();" + NL + "\t\t\t\tgenerator.initialize(modelURI, folder, arguments);" + NL + "\t\t\t\tgenerator.doGenerate(new BasicMonitor());" + NL + "\t\t\t}" + NL + "\t\t} catch (IOException e) {" + NL + "\t\t\te.printStackTrace();" + NL + "\t\t}" + NL + "\t}" + NL + "" + NL + "\t/**" + NL + "\t * Launches the generation described by this instance." + NL + "\t * " + NL + "\t * @param monitor" + NL + "\t *            This will be used to display progress information to the user." + NL + "\t * @throws IOException" + NL + "\t *             This will be thrown if any of the output files cannot be saved to disk." + NL + "\t * @generated" + NL + "\t */" + NL + "\t@Override" + NL + "\tpublic void doGenerate(Monitor monitor) throws IOException {" + NL + "\t\t/*" + NL + "\t\t * TODO if you wish to change the generation as a whole, override this." + NL + "\t\t * The default behavior should be sufficient in most cases." + NL + "\t\t */" + NL + "\t\tsuper.doGenerate(monitor);" + NL + "\t}" + NL + "\t" + NL + "\t/**" + NL + "\t * If this generator needs to listen to text generation events, listeners can be returned from here." + NL + "\t * " + NL + "\t * @return List of listeners that are to be notified when text is generated through this launch." + NL + "\t * @generated" + NL + "\t */" + NL + "\t@Override" + NL + "\tpublic List<IAcceleoTextGenerationListener> getGenerationListeners() {" + NL + "\t\tList<IAcceleoTextGenerationListener> listeners = super.getGenerationListeners();" + NL + "\t\t// TODO if you need to listen to generation event, add listeners to the list here" + NL + "\t\treturn listeners;" + NL + "\t}" + NL + "\t" + NL + "\t/**" + NL + "\t * If you need to change the way files are generated, this is your entry point." + NL + "\t * <p>" + NL + "\t * The default is {@link org.eclipse.acceleo.engine.generation.strategy.DefaultStrategy}; it generates" + NL + "\t * files on the fly. If you only need to preview the results, return a new" + NL + "\t * {@link org.eclipse.acceleo.engine.generation.strategy.PreviewStrategy}. Both of these aren't aware of" + NL + "\t * the running Eclipse and can be used standalone." + NL + "\t * </p>" + NL + "\t * <p>" + NL + "\t * If you need the file generation to be aware of the workspace (A typical example is when you wanna" + NL + "\t * override files that are under clear case or any other VCS that could forbid the overriding), then" + NL + "\t * return a new {@link org.eclipse.acceleo.engine.generation.strategy.WorkspaceAwareStrategy}." + NL + "\t * <b>Note</b>, however, that this <b>cannot</b> be used standalone." + NL + "\t * </p>" + NL + "\t * <p>" + NL + "\t * All three of these default strategies support merging through JMerge." + NL + "\t * </p>" + NL + "\t * " + NL + "\t * @return The generation strategy that is to be used for generations launched through this launcher." + NL + "\t * @generated" + NL + "\t */" + NL + "\tpublic IAcceleoGenerationStrategy getGenerationStrategy() {" + NL + "\t\treturn super.getGenerationStrategy();" + NL + "\t}" + NL + "\t" + NL + "\t/**" + NL + "\t * This will be called in order to find and load the module that will be launched through this launcher." + NL + "\t * We expect this name not to contain file extension, and the module to be located beside the launcher." + NL + "\t * " + NL + "\t * @return The name of the module that is to be launched." + NL + "\t * @generated" + NL + "\t */" + NL + "\t@Override" + NL + "\tpublic String getModuleName() {" + NL + "\t\treturn MODULE_FILE_NAME;" + NL + "\t}" + NL + "\t" + NL + "\t/**" + NL + "\t * If the module(s) called by this launcher require properties files, return their qualified path from" + NL + "\t * here.Take note that the first added properties files will take precedence over subsequent ones if they" + NL + "\t * contain conflicting keys." + NL + "\t * <p>" + NL + "\t * Properties need to be in source folders, the path that we expect to get as a result of this call are of" + NL + "\t * the form &lt;package>.&lt;properties file name without extension>. For example, if you have a file" + NL + "\t * named \"messages.properties\" in package \"org.eclipse.acceleo.sample\", the path that needs be returned by" + NL + "\t * a call to {@link #getProperties()} is \"org.eclipse.acceleo.sample.messages\"." + NL + "\t * </p>" + NL + "\t * " + NL + "\t * @return The list of properties file we need to add to the generation context." + NL + "\t * @see java.util.ResourceBundle#getBundle(String)" + NL + "\t * @generated" + NL + "\t */" + NL + "\t@Override" + NL + "\tpublic List<String> getProperties() {" + NL + "\t\tList<String> propertiesFiles = super.getProperties();" + NL + "\t\t/*" + NL + "\t\t * TODO if your generation module requires access to properties files," + NL + "\t\t * add their qualified path to the list here. Properties files are" + NL + "\t\t * expected to be in source folders, and the path here to be the" + NL + "\t\t * qualified path as if referring to a Java class. For example, if you" + NL + "\t\t * have a file named \"messages.properties\" in package" + NL + "\t\t * \"org.eclipse.acceleo.sample\", the path that needs be added to this" + NL + "\t\t * list is \"org.eclipse.acceleo.sample.messages\"." + NL + "\t\t */" + NL + "\t\treturn propertiesFiles;" + NL + "\t}" + NL + "\t" + NL + "\t/**" + NL + "\t * This will be used to get the list of templates that are to be launched by this launcher." + NL + "\t * " + NL + "\t * @return The list of templates to call on the module {@link #getModuleName()}." + NL + "\t * @generated" + NL + "\t */" + NL + "\t@Override" + NL + "\tpublic String[] getTemplateNames() {" + NL + "\t\treturn TEMPLATE_NAMES;" + NL + "\t}" + NL + "\t" + NL + "\t/**" + NL + "\t * This can be used to update the resource set's package registry with all needed EPackages." + NL + "\t * " + NL + "\t * @param resourceSet" + NL + "\t *            The resource set which registry has to be updated." + NL + "\t * @generated" + NL + "\t */" + NL + "\t@Override" + NL + "\tpublic void registerPackages(ResourceSet resourceSet) {" + NL + "\t\tsuper.registerPackages(resourceSet);";
  protected final String TEXT_14 = NL + "\t\tresourceSet.getPackageRegistry().put(";
  protected final String TEXT_15 = ".eINSTANCE.getNsURI(), ";
  protected final String TEXT_16 = ".eINSTANCE);";
  protected final String TEXT_17 = NL + "\t\t// TODO Uncomment the following line if generating from UML models" + NL + "\t\t// resourceSet.getPackageRegistry().put(UMLPackage.eNS_URI, UMLPackage.eINSTANCE);" + NL + "\t}" + NL + "" + NL + "\t/**" + NL + "\t * This can be used to update the resource set's resource factory registry with all needed factories." + NL + "\t * " + NL + "\t * @param resourceSet" + NL + "\t *            The resource set which registry has to be updated." + NL + "\t * @generated" + NL + "\t */" + NL + "\t@Override" + NL + "\tpublic void registerResourceFactories(ResourceSet resourceSet) {" + NL + "\t\tsuper.registerResourceFactories(resourceSet);" + NL + "\t\t// TODO Uncomment the following line if generating from UML models" + NL + "\t\t// resourceSet.getResourceFactoryRegistry().getExtensionToFactoryMap().put(UMLResource.FILE_EXTENSION, UMLResource.Factory.INSTANCE);" + NL + "\t}" + NL + "\t" + NL + "}";
  protected final String TEXT_18 = NL;

  public String generate(Object argument)
  {
    final StringBuffer stringBuffer = new StringBuffer();
    
 CreateRunnableAcceleoContent content = (CreateRunnableAcceleoContent) argument;

    stringBuffer.append(TEXT_1);
    stringBuffer.append(content.getBasePackage());
    stringBuffer.append(TEXT_2);
    stringBuffer.append(content.getClassShortName());
    stringBuffer.append(TEXT_3);
    stringBuffer.append(content.getClassShortName());
    stringBuffer.append(TEXT_4);
    stringBuffer.append(content.getModuleFileShortName());
    stringBuffer.append(TEXT_5);
    
		for (int i = 0; i < content.getTemplateNames().size(); i++) {
		
    stringBuffer.append(TEXT_6);
    stringBuffer.append(content.getTemplateNames().get(i));
    stringBuffer.append(TEXT_7);
    }
    stringBuffer.append(TEXT_8);
    stringBuffer.append(content.getClassShortName());
    stringBuffer.append(TEXT_9);
    stringBuffer.append(content.getClassShortName());
    stringBuffer.append(TEXT_10);
    stringBuffer.append(content.getClassShortName());
    stringBuffer.append(TEXT_11);
    stringBuffer.append(content.getClassShortName());
    stringBuffer.append(TEXT_12);
    stringBuffer.append(content.getClassShortName());
    stringBuffer.append(TEXT_13);
    for (int i = 0; i < content.getPackages().size(); i++) {
    stringBuffer.append(TEXT_14);
    stringBuffer.append(content.getPackages().get(i));
    stringBuffer.append(TEXT_15);
    stringBuffer.append(content.getPackages().get(i));
    stringBuffer.append(TEXT_16);
    }
    stringBuffer.append(TEXT_17);
    stringBuffer.append(TEXT_18);
    return stringBuffer.toString();
  }
}

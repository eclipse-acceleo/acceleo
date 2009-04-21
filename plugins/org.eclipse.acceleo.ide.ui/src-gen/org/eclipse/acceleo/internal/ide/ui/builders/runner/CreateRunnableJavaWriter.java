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
  protected final String TEXT_1 = "/*******************************************************************************" + NL + " * Copyright (c) 2008, 2009 Obeo." + NL + " * All rights reserved. This program and the accompanying materials" + NL + " * are made available under the terms of the Eclipse Public License v1.0" + NL + " * which accompanies this distribution, and is available at" + NL + " * http://www.eclipse.org/legal/epl-v10.html" + NL + " * " + NL + " * Contributors:" + NL + " *     Obeo - initial API and implementation" + NL + " *******************************************************************************/" + NL + "package ";
  protected final String TEXT_2 = ";" + NL + "" + NL + "import java.io.File;" + NL + "import java.io.IOException;" + NL + "import java.net.URL;" + NL + "import java.util.HashMap;" + NL + "import java.util.Map;" + NL + "import java.util.List;" + NL + "import java.util.ArrayList;" + NL + "" + NL + "import org.eclipse.emf.common.util.URI;" + NL + "import org.eclipse.emf.ecore.EObject;" + NL + "import org.eclipse.emf.ecore.EPackage;" + NL + "import org.eclipse.emf.ecore.resource.Resource;" + NL + "import org.eclipse.emf.ecore.resource.ResourceSet;" + NL + "import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;" + NL + "import org.eclipse.emf.ecore.util.EcoreUtil;" + NL + "import org.eclipse.emf.ecore.xmi.XMLResource;" + NL + "import org.eclipse.emf.ecore.xmi.impl.EcoreResourceFactoryImpl;" + NL + "import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl;" + NL + "import org.eclipse.acceleo.model.mtl.Module;" + NL + "import org.eclipse.acceleo.model.mtl.MtlPackage;" + NL + "import org.eclipse.core.runtime.FileLocator;" + NL + "import org.eclipse.emf.common.EMFPlugin;" + NL + "import org.eclipse.acceleo.engine.service.AcceleoService;" + NL + "import org.eclipse.ocl.ecore.EcoreEnvironment;" + NL + "import org.eclipse.ocl.ecore.EcoreEnvironmentFactory;" + NL + "" + NL + "/**" + NL + " * Entry point of the '";
  protected final String TEXT_3 = "' generation module." + NL + " * " + NL + " * @author <a href=\"mailto:jonathan.musset@obeo.fr\">Jonathan Musset</a>" + NL + " */" + NL + "public class ";
  protected final String TEXT_4 = " {" + NL + "" + NL + "\t/**" + NL + "\t * The name of the module." + NL + "\t * @generated" + NL + "\t */" + NL + "\tpublic static final String MODULE_FILE_NAME = \"";
  protected final String TEXT_5 = "\";" + NL + "\t" + NL + "\t/**" + NL + "\t * The name of the templates that are to be generated." + NL + "\t * @generated" + NL + "\t */" + NL + "\tpublic static final String[] TEMPLATE_NAMES = { ";
  protected final String TEXT_6 = "\"";
  protected final String TEXT_7 = "\", ";
  protected final String TEXT_8 = "};" + NL + "" + NL + "\t/**" + NL + "\t * The root element of the module." + NL + "\t */" + NL + "\tprivate Module module;" + NL + "" + NL + "\t/**" + NL + "\t * The model." + NL + "\t */" + NL + "\tprivate EObject model;" + NL + "" + NL + "\t/**" + NL + "\t * The output folder." + NL + "\t */" + NL + "\tprivate File targetFolder;" + NL + "" + NL + "\t/**" + NL + "\t * The other arguments." + NL + "\t */" + NL + "\tList<? extends Object> arguments;" + NL + "" + NL + "\t/**" + NL + "\t * Constructor." + NL + "\t * " + NL + "\t * @param modelURI" + NL + "\t *            is the URI of the model." + NL + "\t * @param targetFolder" + NL + "\t *            is the output folder" + NL + "\t * @param arguments" + NL + "\t *            are the other arguments" + NL + "\t * @throws IOException" + NL + "\t *             Thrown when the output cannot be saved." + NL + "\t * @generated" + NL + "\t */" + NL + "\tpublic ";
  protected final String TEXT_9 = "(URI modelURI, File targetFolder, List<? extends Object> arguments) throws IOException {" + NL + "\t\tResourceSet resourceSet = new ResourceSetImpl();" + NL + "\t\tregisterResourceFactories(resourceSet);" + NL + "\t\tregisterPackages(resourceSet);" + NL + "\t\tfinal URL templateURL;" + NL + "\t\tif (EMFPlugin.IS_ECLIPSE_RUNNING) {" + NL + "\t\t\ttemplateURL = FileLocator.toFileURL(";
  protected final String TEXT_10 = ".class.getResource(MODULE_FILE_NAME + \".emtl\"));" + NL + "\t\t} else {" + NL + "\t\t\ttemplateURL = ";
  protected final String TEXT_11 = ".class.getResource(MODULE_FILE_NAME + \".emtl\");" + NL + "\t\t}" + NL + "\t\tif (templateURL == null) {" + NL + "\t\t\tthrow new IOException(\"'\" + MODULE_FILE_NAME + \".emtl' not found\");" + NL + "\t\t} else {" + NL + "\t\t\tURI templateURI = createTemplateURI(templateURL.getPath());" + NL + "\t\t\tmodule = (Module)load(templateURI, resourceSet);" + NL + "\t\t\tmodel = load(modelURI, resourceSet);" + NL + "\t\t\tthis.targetFolder = targetFolder;" + NL + "\t\t\tthis.arguments = arguments;" + NL + "\t\t}" + NL + "\t}" + NL + "" + NL + "\t/**" + NL + "\t * Constructor." + NL + "\t * " + NL + "\t * @param model" + NL + "\t *            is the root element of the model." + NL + "\t * @param targetFolder" + NL + "\t *            is the output folder" + NL + "\t * @param arguments" + NL + "\t *            are the other arguments" + NL + "\t * @throws IOException" + NL + "\t *             Thrown when the output cannot be saved." + NL + "\t * @generated" + NL + "\t */" + NL + "\tpublic ";
  protected final String TEXT_12 = "(EObject model, File targetFolder, List<? extends Object> arguments) throws IOException {" + NL + "\t\tResourceSet resourceSet = model.eResource().getResourceSet();" + NL + "\t\tregisterResourceFactories(resourceSet);" + NL + "\t\tregisterPackages(resourceSet);" + NL + "\t\tfinal URL templateURL;" + NL + "\t\tif (EMFPlugin.IS_ECLIPSE_RUNNING) {" + NL + "\t\t\ttemplateURL = FileLocator.toFileURL(";
  protected final String TEXT_13 = ".class.getResource(MODULE_FILE_NAME + \".emtl\"));" + NL + "\t\t} else {" + NL + "\t\t\ttemplateURL = ";
  protected final String TEXT_14 = ".class.getResource(MODULE_FILE_NAME + \".emtl\");" + NL + "\t\t}" + NL + "\t\tif (templateURL == null) {" + NL + "\t\t\tthrow new IOException(\"'\" + MODULE_FILE_NAME + \".emtl' not found\");" + NL + "\t\t} else {" + NL + "\t\t\tURI templateURI = createTemplateURI(templateURL.getPath());" + NL + "\t\t\tmodule = (Module)load(templateURI, resourceSet);" + NL + "\t\t\tthis.model = model;" + NL + "\t\t\tthis.targetFolder = targetFolder;" + NL + "\t\t\tthis.arguments = arguments;" + NL + "\t\t}" + NL + "\t}" + NL + "" + NL + "\t/**" + NL + "\t * Creates the template URI." + NL + "\t * " + NL + "\t * @param entry" + NL + "\t *            is the local path of the EMTL file" + NL + "\t * @generated" + NL + "\t */" + NL + "\tprotected URI createTemplateURI(String entry) {" + NL + "\t\treturn URI.createFileURI(URI.decode(entry));" + NL + "\t}" + NL + "" + NL + "\t/**" + NL + "\t * Gets the model." + NL + "\t * @return the model root element" + NL + "\t */" + NL + "\tpublic EObject getModel() {" + NL + "\t\treturn model;" + NL + "\t}" + NL + "" + NL + "\t/**" + NL + "\t * Updates the registry used for looking up a package based namespace, in the resource set." + NL + "\t * " + NL + "\t * @param resourceSet" + NL + "\t *            is the resource set" + NL + "\t * @generated" + NL + "\t */" + NL + "\tprivate void registerPackages(ResourceSet resourceSet) {";
  protected final String TEXT_15 = NL + "\t\tresourceSet.getPackageRegistry().put(";
  protected final String TEXT_16 = ".eINSTANCE.getNsURI(), ";
  protected final String TEXT_17 = ".eINSTANCE);";
  protected final String TEXT_18 = NL + "\t\tresourceSet.getPackageRegistry().put(org.eclipse.ocl.ecore.EcorePackage.eINSTANCE.getNsURI(), org.eclipse.ocl.ecore.EcorePackage.eINSTANCE);" + NL + "\t\tresourceSet.getPackageRegistry().put(org.eclipse.ocl.expressions.ExpressionsPackage.eINSTANCE.getNsURI(), org.eclipse.ocl.expressions.ExpressionsPackage.eINSTANCE);" + NL + "\t\tresourceSet.getPackageRegistry().put(MtlPackage.eINSTANCE.getNsURI(), MtlPackage.eINSTANCE);" + NL + "\t\tresourceSet.getPackageRegistry().put(\"http://www.eclipse.org/ocl/1.1.0/oclstdlib.ecore\", getOCLStdLibPackage());" + NL + "\t}" + NL + "\t" + NL + "\t/**" + NL + "\t * Returns the package containing the OCL standard library." + NL + "\t * " + NL + "\t * @return The package containing the OCL standard library." + NL + "\t * @generated" + NL + "\t */" + NL + "\tprivate EPackage getOCLStdLibPackage() {" + NL + "\t\tEcoreEnvironmentFactory factory = new EcoreEnvironmentFactory();" + NL + "\t\tEcoreEnvironment environment = (EcoreEnvironment)factory.createEnvironment();" + NL + "\t\treturn (EPackage)EcoreUtil.getRootContainer(environment.getOCLStandardLibrary().getBag());" + NL + "\t}" + NL + "\t" + NL + "\t/**" + NL + "\t * Updates the registry used for looking up resources factory in the given resource set." + NL + "\t *" + NL + "\t * @param resourceSet" + NL + "\t *            The resource set that is to be updated." + NL + "\t * @generated" + NL + "\t */" + NL + "\tprivate void registerResourceFactories(ResourceSet resourceSet) {" + NL + "\t\tresourceSet.getResourceFactoryRegistry().getExtensionToFactoryMap().put(\"ecore\", new EcoreResourceFactoryImpl());" + NL + "\t\tresourceSet.getResourceFactoryRegistry().getExtensionToFactoryMap().put(\"emtl\", new org.eclipse.acceleo.model.mtl.resource.EMtlResourceFactoryImpl());" + NL + "\t\tresourceSet.getResourceFactoryRegistry().getExtensionToFactoryMap().put(Resource.Factory.Registry.DEFAULT_EXTENSION, new XMIResourceFactoryImpl());" + NL + "\t}" + NL + "" + NL + "\t/**" + NL + "\t * The main method." + NL + "\t * " + NL + "\t * @param args" + NL + "\t *            are the arguments" + NL + "\t * @generated" + NL + "\t */" + NL + "\tpublic static void main(String[] args) {" + NL + "\t\ttry {" + NL + "\t\t\tif (args.length < 2) {" + NL + "\t\t\t\tSystem.out.println(\"Arguments not valid : {model, folder}.\");" + NL + "\t\t\t} else {" + NL + "\t\t\t\tURI modelURI = URI.createFileURI(args[0]);" + NL + "\t\t\t\tFile folder = new File(args[1]);" + NL + "\t\t\t\tList<String> arguments = new ArrayList<String>();" + NL + "\t\t\t\tfor (int i = 2; i < args.length; i++) {" + NL + "\t\t\t\t\targuments.add(args[i]);" + NL + "\t\t\t\t}" + NL + "\t\t\t\t";
  protected final String TEXT_19 = " generator = new ";
  protected final String TEXT_20 = "(modelURI, folder, arguments);" + NL + "\t\t\t\tgenerator.doGenerate();" + NL + "\t\t\t}" + NL + "\t\t} catch (IOException e) {" + NL + "\t\t\te.printStackTrace();" + NL + "\t\t}" + NL + "\t}" + NL + "" + NL + "\t/**" + NL + "\t * Launches the generation." + NL + "\t * " + NL + "\t * @throws IOException" + NL + "\t *             Thrown when the output cannot be saved." + NL + "\t * @generated" + NL + "\t */" + NL + "\tpublic void doGenerate() throws IOException {" + NL + "\t\tif (!targetFolder.exists()) {" + NL + "\t\t\ttargetFolder.mkdirs();" + NL + "\t\t}" + NL + "\t\tfor (int i = 0; i < TEMPLATE_NAMES.length; i++) {" + NL + "\t\t\tAcceleoService.doGenerate(module, TEMPLATE_NAMES[i], model, arguments, targetFolder, false);" + NL + "\t\t}" + NL + "\t}" + NL + "" + NL + "\t/**" + NL + "\t * Loads a model from an {@link org.eclipse.emf.common.util.URI URI} in a given {@link ResourceSet}." + NL + "\t * <p>" + NL + "\t * This will return the first root of the loaded model, other roots can be accessed via the resource's" + NL + "\t * content." + NL + "\t * </p>" + NL + "\t * " + NL + "\t * @param modelURI" + NL + "\t *            {@link org.eclipse.emf.common.util.URI URI} where the model is stored." + NL + "\t * @param resourceSet" + NL + "\t *            The {@link ResourceSet} to load the model in." + NL + "\t * @return The model loaded from the URI." + NL + "\t * @throws IOException" + NL + "\t *             If the given file does not exist." + NL + "\t * @generated" + NL + "\t */" + NL + "\tprivate EObject load(URI modelURI, ResourceSet resourceSet) throws IOException {" + NL + "\t\tEObject result = null;" + NL + "\t\tfinal Resource modelResource = createResource(modelURI, resourceSet);" + NL + "\t\tfinal Map<String, String> options = new HashMap<String, String>();" + NL + "\t\toptions.put(XMLResource.OPTION_ENCODING, System.getProperty(\"file.encoding\"));" + NL + "\t\tmodelResource.load(options);" + NL + "\t\tif (modelResource.getContents().size() > 0) {" + NL + "\t\t\tresult = modelResource.getContents().get(0);" + NL + "\t\t}" + NL + "\t\treturn result;" + NL + "\t}" + NL + "" + NL + "\t/**" + NL + "\t * This will create a {@link Resource} given the model extension it is intended for and a ResourceSet." + NL + "\t * " + NL + "\t * @param modelURI" + NL + "\t *            {@link org.eclipse.emf.common.util.URI URI} where the model is stored." + NL + "\t * @param resourceSet" + NL + "\t *            The {@link ResourceSet} to load the model in." + NL + "\t * @return The {@link Resource} given the model extension it is intended for." + NL + "\t * @generated" + NL + "\t */" + NL + "\tprivate Resource createResource(URI modelURI, ResourceSet resourceSet) {" + NL + "\t\tString fileExtension = modelURI.fileExtension();" + NL + "\t\tif (fileExtension == null || fileExtension.length() == 0) {" + NL + "\t\t\tfileExtension = Resource.Factory.Registry.DEFAULT_EXTENSION;" + NL + "\t\t}" + NL + "\t\tfinal Resource.Factory.Registry registry = Resource.Factory.Registry.INSTANCE;" + NL + "\t\tfinal Object resourceFactory = registry.getExtensionToFactoryMap().get(fileExtension);" + NL + "\t\tif (resourceFactory != null) {" + NL + "\t\t\tresourceSet.getResourceFactoryRegistry().getExtensionToFactoryMap().put(fileExtension," + NL + "\t\t\t\t\tresourceFactory);" + NL + "\t\t} else {" + NL + "\t\t\tresourceSet.getResourceFactoryRegistry().getExtensionToFactoryMap().put(fileExtension," + NL + "\t\t\t\t\tnew XMIResourceFactoryImpl());" + NL + "\t\t}" + NL + "\t\treturn resourceSet.createResource(modelURI);" + NL + "\t}" + NL + "" + NL + "}";
  protected final String TEXT_21 = NL;

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
    stringBuffer.append(content.getClassShortName());
    stringBuffer.append(TEXT_14);
    for (int i = 0; i < content.getPackages().size(); i++) {
    stringBuffer.append(TEXT_15);
    stringBuffer.append(content.getPackages().get(i));
    stringBuffer.append(TEXT_16);
    stringBuffer.append(content.getPackages().get(i));
    stringBuffer.append(TEXT_17);
    }
    stringBuffer.append(TEXT_18);
    stringBuffer.append(content.getClassShortName());
    stringBuffer.append(TEXT_19);
    stringBuffer.append(content.getClassShortName());
    stringBuffer.append(TEXT_20);
    stringBuffer.append(TEXT_21);
    return stringBuffer.toString();
  }
}

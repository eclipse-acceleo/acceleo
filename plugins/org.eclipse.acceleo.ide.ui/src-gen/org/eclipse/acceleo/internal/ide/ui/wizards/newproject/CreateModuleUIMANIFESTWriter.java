package org.eclipse.acceleo.internal.ide.ui.wizards.newproject;

public class CreateModuleUIMANIFESTWriter
{
  protected static String nl;
  public static synchronized CreateModuleUIMANIFESTWriter create(String lineSeparator)
  {
    nl = lineSeparator;
    CreateModuleUIMANIFESTWriter result = new CreateModuleUIMANIFESTWriter();
    nl = null;
    return result;
  }

  public final String NL = nl == null ? (System.getProperties().getProperty("line.separator")) : nl;
  protected final String TEXT_1 = "Manifest-Version: 1.0" + NL + "Bundle-ManifestVersion: 2" + NL + "Bundle-Name: Acceleo ";
  protected final String TEXT_2 = " Module IDE Plug-in" + NL + "Bundle-SymbolicName: ";
  protected final String TEXT_3 = ";singleton:=true" + NL + "Bundle-Version: 1.0.0.qualifier" + NL + "Bundle-Activator: ";
  protected final String TEXT_4 = ".Activator" + NL + "Bundle-Vendor: Eclipse Modeling Project" + NL + "Require-Bundle: org.eclipse.ui," + NL + " org.eclipse.core.runtime," + NL + " org.eclipse.core.resources, ";
  protected final String TEXT_5 = NL + " ";
  protected final String TEXT_6 = ",";
  protected final String TEXT_7 = NL + " org.eclipse.emf.ecore," + NL + " org.eclipse.emf.ecore.xmi," + NL + " org.eclipse.ocl," + NL + " org.eclipse.ocl.ecore," + NL + " org.eclipse.acceleo.model," + NL + " org.eclipse.acceleo.engine" + NL + "Bundle-RequiredExecutionEnvironment: J2SE-1.5" + NL + "Bundle-ActivationPolicy: lazy" + NL + "Eclipse-LazyStart: true";
  protected final String TEXT_8 = NL;

  public String generate(Object argument)
  {
    final StringBuffer stringBuffer = new StringBuffer();
    
 CreateModuleUIData content = (CreateModuleUIData) argument;

    stringBuffer.append(TEXT_1);
    stringBuffer.append(content.getModuleNameWithSpaces());
    stringBuffer.append(TEXT_2);
    stringBuffer.append(content.getProjectName());
    stringBuffer.append(TEXT_3);
    stringBuffer.append(content.getProjectName());
    stringBuffer.append(TEXT_4);
    
 for (int i = 0; i < content.getPluginDependencies().size(); i++) {
    stringBuffer.append(TEXT_5);
    stringBuffer.append(content.getPluginDependencies().get(i));
    stringBuffer.append(TEXT_6);
    }
    stringBuffer.append(TEXT_7);
    stringBuffer.append(TEXT_8);
    return stringBuffer.toString();
  }
}

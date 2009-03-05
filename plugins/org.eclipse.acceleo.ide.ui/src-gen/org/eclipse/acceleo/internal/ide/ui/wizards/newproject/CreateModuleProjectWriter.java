package org.eclipse.acceleo.internal.ide.ui.wizards.newproject;

public class CreateModuleProjectWriter
{
  protected static String nl;
  public static synchronized CreateModuleProjectWriter create(String lineSeparator)
  {
    nl = lineSeparator;
    CreateModuleProjectWriter result = new CreateModuleProjectWriter();
    nl = null;
    return result;
  }

  public final String NL = nl == null ? (System.getProperties().getProperty("line.separator")) : nl;
  protected final String TEXT_1 = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>" + NL + "<projectDescription>" + NL + "\t<name>";
  protected final String TEXT_2 = "</name>" + NL + "\t<comment></comment>" + NL + "\t<projects>" + NL + "\t</projects>" + NL + "\t<buildSpec>" + NL + "\t\t<buildCommand>" + NL + "\t\t\t<name>org.eclipse.jdt.core.javabuilder</name>" + NL + "\t\t\t<arguments>" + NL + "\t\t\t</arguments>" + NL + "\t\t</buildCommand>" + NL + "\t\t<buildCommand>" + NL + "\t\t\t<name>org.eclipse.pde.ManifestBuilder</name>" + NL + "\t\t\t<arguments>" + NL + "\t\t\t</arguments>" + NL + "\t\t</buildCommand>" + NL + "\t\t<buildCommand>" + NL + "\t\t\t<name>org.eclipse.pde.SchemaBuilder</name>" + NL + "\t\t\t<arguments>" + NL + "\t\t\t</arguments>" + NL + "\t\t</buildCommand>" + NL + "\t\t<buildCommand>" + NL + "\t\t\t<name>org.eclipse.acceleo.ide.ui.acceleoBuilder</name>" + NL + "\t\t\t<arguments>" + NL + "\t\t\t</arguments>" + NL + "\t\t</buildCommand>" + NL + "\t</buildSpec>" + NL + "\t<natures>" + NL + "\t\t<nature>org.eclipse.acceleo.ide.ui.acceleoNature</nature>" + NL + "\t\t<nature>org.eclipse.pde.PluginNature</nature>" + NL + "\t\t<nature>org.eclipse.jdt.core.javanature</nature>" + NL + "\t</natures>" + NL + "</projectDescription>";
  protected final String TEXT_3 = NL;

  public String generate(Object argument)
  {
    final StringBuffer stringBuffer = new StringBuffer();
    
 CreateModuleData content = (CreateModuleData) argument;

    stringBuffer.append(TEXT_1);
    stringBuffer.append(content.getProjectName());
    stringBuffer.append(TEXT_2);
    stringBuffer.append(TEXT_3);
    return stringBuffer.toString();
  }
}

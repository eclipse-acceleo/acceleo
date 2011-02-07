package org.eclipse.acceleo.internal.ide.ui.builders.runner;

public class CreateBuildAcceleoWriter
{
  protected static String nl;
  public static synchronized CreateBuildAcceleoWriter create(String lineSeparator)
  {
    nl = lineSeparator;
    CreateBuildAcceleoWriter result = new CreateBuildAcceleoWriter();
    nl = null;
    return result;
  }

  public final String NL = nl == null ? (System.getProperties().getProperty("line.separator")) : nl;
  protected final String TEXT_1 = "<!-- ===================================================================== -->" + NL + "<!-- Custom targets.                                                       -->" + NL + "<!-- Set customBuildCallbacks = build.acceleo in your build.properties.    -->" + NL + "<!-- ===================================================================== -->" + NL + "<project name=\"Build Acceleo Module\" default=\"noDefault\">" + NL + "" + NL + "\t<!-- ================================================================= -->" + NL + "\t<!-- Default target                                                    -->" + NL + "\t<!-- ================================================================= -->" + NL + "\t<target name=\"noDefault\">" + NL + "\t\t<echo message=\"This file must be called with explicit targets\" />" + NL + "\t</target>" + NL + "" + NL + "\t<!-- ================================================================= -->" + NL + "\t<!-- This will be called automatically after the compilation of each   -->" + NL + "\t<!-- Bundle... in dependency order.                                    -->" + NL + "\t<!-- ================================================================= -->" + NL + "\t<target name=\"post.compile.@dot\">" + NL + "\t\t<acceleoCompiler " + NL + "\t\t\tsourceFolders=\"${target.folder}\" " + NL + "\t\t\tdependencies=\"";
  protected final String TEXT_2 = "${target.folder}/../../";
  protected final String TEXT_3 = ";";
  protected final String TEXT_4 = "\">" + NL + "\t\t</acceleoCompiler>" + NL + "\t</target>\t" + NL + "" + NL + "</project>";
  protected final String TEXT_5 = NL;

  public String generate(Object argument)
  {
    final StringBuffer stringBuffer = new StringBuffer();
    
java.util.List<org.eclipse.core.resources.IProject> dependencies = (java.util.List<org.eclipse.core.resources.IProject>) ((Object[])argument)[0];

    stringBuffer.append(TEXT_1);
    for (org.eclipse.core.resources.IProject project : dependencies) {
    stringBuffer.append(TEXT_2);
    stringBuffer.append(project.getName());
    stringBuffer.append(TEXT_3);
    }
    stringBuffer.append(TEXT_4);
    stringBuffer.append(TEXT_5);
    return stringBuffer.toString();
  }
}

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
  protected final String TEXT_1 = "<!-- ===================================================================== -->" + NL + "<!-- Custom targets.                                                       -->" + NL + "<!-- Set customBuildCallbacks = build.acceleo in your build.properties.    -->" + NL + "<!-- ===================================================================== -->" + NL + "<project name=\"Build Acceleo Module\" default=\"noDefault\">" + NL + "" + NL + "\t<!-- ================================================================= -->" + NL + "\t<!-- Default target                                                    -->" + NL + "\t<!-- ================================================================= -->" + NL + "\t<target name=\"noDefault\">" + NL + "\t\t<echo message=\"This file must be called with explicit targets\" />" + NL + "\t</target>" + NL + "" + NL + "\t<!-- ================================================================= -->" + NL + "\t<!-- Steps to do after the target gather.bin.parts                     -->" + NL + "\t<!-- Available parameters :                                            -->" + NL + "\t<!--   build.result.folder - folder containing the build results       -->" + NL + "\t<!--   target.folder - destination folder                              -->" + NL + "\t<!-- ================================================================= -->" + NL + "\t<target name=\"post.gather.bin.parts\">" + NL + "\t\t<eclipse.incrementalBuild " + NL + "\t\t  project=\"";
  protected final String TEXT_2 = "\"" + NL + "\t\t  kind=\"full\" />" + NL + "\t\t<copydir dest=\"${target.folder}\" src=\"${basedir}";
  protected final String TEXT_3 = "\"></copydir>" + NL + "\t</target>" + NL + "" + NL + "</project>";
  protected final String TEXT_4 = NL;

  public String generate(Object argument)
  {
    final StringBuffer stringBuffer = new StringBuffer();
    
 String projectName = (String) ((Object[])argument)[0];
 String outputFolder = (String) ((Object[])argument)[1];

    stringBuffer.append(TEXT_1);
    stringBuffer.append(projectName);
    stringBuffer.append(TEXT_2);
    stringBuffer.append(outputFolder);
    stringBuffer.append(TEXT_3);
    stringBuffer.append(TEXT_4);
    return stringBuffer.toString();
  }
}

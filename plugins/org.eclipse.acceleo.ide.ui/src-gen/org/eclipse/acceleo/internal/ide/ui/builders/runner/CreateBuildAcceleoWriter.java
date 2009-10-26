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
  protected final String TEXT_1 = "<!-- ===================================================================== -->" + NL + "<!-- Custom targets.                                                       -->" + NL + "<!-- Set customBuildCallbacks = build.acceleo in your build.properties.    -->" + NL + "<!-- ===================================================================== -->" + NL + "<project name=\"Build Acceleo Module\" default=\"run\">" + NL + "" + NL + "\t<!-- ================================================================= -->" + NL + "\t<!-- Steps to do after the target gather.bin.parts                     -->" + NL + "\t<!-- Available parameters :                                            -->" + NL + "\t<!--   build.result.folder - folder containing the build results       -->" + NL + "\t<!--   target.folder - destination folder                              -->" + NL + "\t<!-- ================================================================= -->" + NL + "" + NL + "\t<target name=\"post.gather.bin.parts\">" + NL + "\t\t<acceleoCompiler sourceFolders=\"${target.folder}\"/>" + NL + "\t</target>" + NL + "" + NL + "</project>";
  protected final String TEXT_2 = NL;

  public String generate(Object argument)
  {
    final StringBuffer stringBuffer = new StringBuffer();
    
// String projectName = (String) ((Object[])argument)[0];
// String outputFolder = (String) ((Object[])argument)[1];

    stringBuffer.append(TEXT_1);
    stringBuffer.append(TEXT_2);
    return stringBuffer.toString();
  }
}

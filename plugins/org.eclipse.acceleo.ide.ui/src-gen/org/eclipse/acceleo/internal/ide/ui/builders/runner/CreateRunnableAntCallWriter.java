package org.eclipse.acceleo.internal.ide.ui.builders.runner;

public class CreateRunnableAntCallWriter
{
  protected static String nl;
  public static synchronized CreateRunnableAntCallWriter create(String lineSeparator)
  {
    nl = lineSeparator;
    CreateRunnableAntCallWriter result = new CreateRunnableAntCallWriter();
    nl = null;
    return result;
  }

  public final String NL = nl == null ? (System.getProperties().getProperty("line.separator")) : nl;
  protected final String TEXT_1 = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>" + NL + "" + NL + "<!-- Rename this file, put it in the target project and call it with \"External Tools > Run As > Ant Build\" -->" + NL + "<!-- You have to change the MODEL and the TARGET values -->" + NL + "" + NL + "<project basedir=\".\" default=\"";
  protected final String TEXT_2 = "Sample\" name=\"";
  protected final String TEXT_3 = "Sample\">" + NL + "\t<import file=\"../";
  protected final String TEXT_4 = "/tasks/";
  protected final String TEXT_5 = ".xml\"/>" + NL + "\t<property name=\"MODEL\" value=\"${basedir}/model/file.xmi\"/>" + NL + "\t<property name=\"TARGET\" value=\"${basedir}/src-gen\"/>" + NL + "\t<target name=\"";
  protected final String TEXT_6 = "Sample\" description=\"Generate files in '${TARGET}'\">" + NL + "\t\t<antcall target=\"";
  protected final String TEXT_7 = "\" >" + NL + "\t\t\t<param name=\"model\" value=\"${MODEL}\"/>" + NL + "\t\t\t<param name=\"target\" value=\"${TARGET}\"/>" + NL + "\t\t</antcall>" + NL + "\t</target>" + NL + "</project>";

  public String generate(Object argument)
  {
    final StringBuffer stringBuffer = new StringBuffer();
    
 CreateRunnableAcceleoContent content = (CreateRunnableAcceleoContent) argument;

    stringBuffer.append(TEXT_1);
    stringBuffer.append(content.getModuleFileShortName());
    stringBuffer.append(TEXT_2);
    stringBuffer.append(content.getProjectName());
    stringBuffer.append(TEXT_3);
    stringBuffer.append(content.getProjectName());
    stringBuffer.append(TEXT_4);
    stringBuffer.append(content.getModuleFileShortName());
    stringBuffer.append(TEXT_5);
    stringBuffer.append(content.getModuleFileShortName());
    stringBuffer.append(TEXT_6);
    stringBuffer.append(content.getModuleFileShortName());
    stringBuffer.append(TEXT_7);
    return stringBuffer.toString();
  }
}

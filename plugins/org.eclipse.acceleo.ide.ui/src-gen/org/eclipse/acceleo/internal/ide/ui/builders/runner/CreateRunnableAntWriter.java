package org.eclipse.acceleo.internal.ide.ui.builders.runner;

public class CreateRunnableAntWriter
{
  protected static String nl;
  public static synchronized CreateRunnableAntWriter create(String lineSeparator)
  {
    nl = lineSeparator;
    CreateRunnableAntWriter result = new CreateRunnableAntWriter();
    nl = null;
    return result;
  }

  public final String NL = nl == null ? (System.getProperties().getProperty("line.separator")) : nl;
  protected final String TEXT_1 = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>" + NL + "<project default=\"";
  protected final String TEXT_2 = "\" name=\"";
  protected final String TEXT_3 = "\">" + NL + "    <property name=\"ECLIPSE_HOME\" value=\"../../\"/>" + NL + "    <property name=\"ECLIPSE_WORKSPACE\" value=\"../\"/>" + NL + "    <path id=\"";
  protected final String TEXT_4 = ".libraryclasspath\">" + NL + "    \t<pathelement location=\"../";
  protected final String TEXT_5 = "/bin\"/>" + NL + "    \t";
  protected final String TEXT_6 = NL + "    \t<pathelement location=\"";
  protected final String TEXT_7 = "\"/>";
  protected final String TEXT_8 = NL + "    </path>" + NL + "    <path id=\"";
  protected final String TEXT_9 = ".classpath\">" + NL + "        <path refid=\"";
  protected final String TEXT_10 = ".libraryclasspath\"/>" + NL + "    </path>" + NL + "" + NL + "\t<target name=\"";
  protected final String TEXT_11 = "\">" + NL + "" + NL + "\t\t<java classname=\"";
  protected final String TEXT_12 = ".";
  protected final String TEXT_13 = "\" classpathref=\"";
  protected final String TEXT_14 = ".classpath\">" + NL + "\t\t\t<arg value=\"${model}\"/>" + NL + "\t\t\t<arg value=\"${target}\"/>" + NL + "\t\t</java>" + NL + "" + NL + "\t</target>" + NL + "" + NL + "</project>";
  protected final String TEXT_15 = NL;

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
    stringBuffer.append(content.getProjectName());
    stringBuffer.append(TEXT_5);
    for (int i = 0; i < content.getResolvedClasspath().size(); i++) { 
    stringBuffer.append(TEXT_6);
    stringBuffer.append(content.getResolvedClasspath().get(i));
    stringBuffer.append(TEXT_7);
    }
    stringBuffer.append(TEXT_8);
    stringBuffer.append(content.getProjectName());
    stringBuffer.append(TEXT_9);
    stringBuffer.append(content.getProjectName());
    stringBuffer.append(TEXT_10);
    stringBuffer.append(content.getModuleFileShortName());
    stringBuffer.append(TEXT_11);
    stringBuffer.append(content.getBasePackage());
    stringBuffer.append(TEXT_12);
    stringBuffer.append(content.getClassShortName());
    stringBuffer.append(TEXT_13);
    stringBuffer.append(content.getProjectName());
    stringBuffer.append(TEXT_14);
    stringBuffer.append(TEXT_15);
    return stringBuffer.toString();
  }
}

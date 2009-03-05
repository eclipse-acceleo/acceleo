package org.eclipse.acceleo.internal.ide.ui.wizards.newproject;

public class CreateModuleUIClasspathWriter
{
  protected static String nl;
  public static synchronized CreateModuleUIClasspathWriter create(String lineSeparator)
  {
    nl = lineSeparator;
    CreateModuleUIClasspathWriter result = new CreateModuleUIClasspathWriter();
    nl = null;
    return result;
  }

  public final String NL = nl == null ? (System.getProperties().getProperty("line.separator")) : nl;
  protected final String TEXT_1 = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>" + NL + "<classpath>" + NL + "\t<classpathentry kind=\"con\" path=\"org.eclipse.jdt.launching.JRE_CONTAINER/org.eclipse.jdt.internal.debug.ui.launcher.StandardVMType/J2SE-1.5\"/>" + NL + "\t<classpathentry kind=\"con\" path=\"org.eclipse.pde.core.requiredPlugins\"/>" + NL + "\t<classpathentry kind=\"src\" path=\"src\"/>" + NL + "\t<classpathentry kind=\"output\" path=\"bin\"/>" + NL + "</classpath>";
  protected final String TEXT_2 = NL;

  public String generate(Object argument)
  {
    final StringBuffer stringBuffer = new StringBuffer();
    stringBuffer.append(TEXT_1);
    stringBuffer.append(TEXT_2);
    return stringBuffer.toString();
  }
}

package org.eclipse.acceleo.internal.ide.ui.wizards.newproject;

public class CreateModuleUISettingsWriter
{
  protected static String nl;
  public static synchronized CreateModuleUISettingsWriter create(String lineSeparator)
  {
    nl = lineSeparator;
    CreateModuleUISettingsWriter result = new CreateModuleUISettingsWriter();
    nl = null;
    return result;
  }

  public final String NL = nl == null ? (System.getProperties().getProperty("line.separator")) : nl;
  protected final String TEXT_1 = "eclipse.preferences.version=1" + NL + "org.eclipse.jdt.core.compiler.codegen.targetPlatform=1.5" + NL + "org.eclipse.jdt.core.compiler.compliance=1.5" + NL + "org.eclipse.jdt.core.compiler.problem.assertIdentifier=error" + NL + "org.eclipse.jdt.core.compiler.problem.enumIdentifier=error" + NL + "org.eclipse.jdt.core.compiler.source=1.5";
  protected final String TEXT_2 = NL;

  public String generate(Object argument)
  {
    final StringBuffer stringBuffer = new StringBuffer();
    stringBuffer.append(TEXT_1);
    stringBuffer.append(TEXT_2);
    return stringBuffer.toString();
  }
}

package org.eclipse.acceleo.internal.ide.ui.wizards.newproject;

public class CreateModuleUIPluginXMLWriter
{
  protected static String nl;
  public static synchronized CreateModuleUIPluginXMLWriter create(String lineSeparator)
  {
    nl = lineSeparator;
    CreateModuleUIPluginXMLWriter result = new CreateModuleUIPluginXMLWriter();
    nl = null;
    return result;
  }

  public final String NL = nl == null ? (System.getProperties().getProperty("line.separator")) : nl;
  protected final String TEXT_1 = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>" + NL + "<?eclipse version=\"3.2\"?>" + NL + "<plugin>" + NL + "   <extension point=\"org.eclipse.ui.popupMenus\">" + NL + "      <objectContribution" + NL + "            adaptable=\"true\"" + NL + "            objectClass=\"org.eclipse.core.resources.IFile\"" + NL + "            nameFilter=\"";
  protected final String TEXT_2 = "\"" + NL + "            id=\"";
  protected final String TEXT_3 = ".popupMenus.contribution.IFile\">" + NL + "         <menu id=\"org.eclipse.acceleo.module.menu\" label=\"Acceleo Model to Text\" path=\"additionsAcceleo\">" + NL + "               <groupMarker name=\"acceleo\"/> " + NL + "         </menu>" + NL + "         <action" + NL + "               class=\"";
  protected final String TEXT_4 = ".popupMenus.AcceleoGenerate";
  protected final String TEXT_5 = "Action\"" + NL + "               enablesFor=\"+\"" + NL + "               id=\"";
  protected final String TEXT_6 = ".popupMenus.acceleoGenerate";
  protected final String TEXT_7 = "Action\"" + NL + "               icon=\"icons/default.gif\"" + NL + "               label=\"Generate ";
  protected final String TEXT_8 = "\"" + NL + "               menubarPath=\"org.eclipse.acceleo.module.menu/acceleo\"/>" + NL + "      </objectContribution>" + NL + "   </extension>" + NL + "</plugin>";
  protected final String TEXT_9 = NL;

  public String generate(Object argument)
  {
    final StringBuffer stringBuffer = new StringBuffer();
    
 CreateModuleUIData content = (CreateModuleUIData) argument;

    stringBuffer.append(TEXT_1);
    stringBuffer.append(content.getModelNameFilter());
    stringBuffer.append(TEXT_2);
    stringBuffer.append(content.getProjectName());
    stringBuffer.append(TEXT_3);
    stringBuffer.append(content.getProjectName());
    stringBuffer.append(TEXT_4);
    stringBuffer.append(content.getModuleNameWithoutSpaces());
    stringBuffer.append(TEXT_5);
    stringBuffer.append(content.getProjectName());
    stringBuffer.append(TEXT_6);
    stringBuffer.append(content.getModuleNameWithoutSpaces());
    stringBuffer.append(TEXT_7);
    stringBuffer.append(content.getModuleNameWithSpaces());
    stringBuffer.append(TEXT_8);
    stringBuffer.append(TEXT_9);
    return stringBuffer.toString();
  }
}

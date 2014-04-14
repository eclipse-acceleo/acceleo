/*******************************************************************************
 * Copyright (c) 2006, 2014 Obeo.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.acceleo.unit.ui.internal.util;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.acceleo.parser.cst.Module;
import org.eclipse.acceleo.parser.cst.ModuleElement;
import org.eclipse.acceleo.parser.cst.Query;
import org.eclipse.acceleo.parser.cst.Template;
import org.eclipse.acceleo.parser.cst.Variable;
import org.eclipse.jdt.core.IPackageFragment;

/**
 * The Test Case class helper.
 * 
 * @author <a href="mailto:hugo.marchadour@obeo.fr">Hugo Marchadour</a>
 */
public final class AUnitTestCaseHelper {

	/**
	 * New line text.
	 */
	private static final String NL = AUnitUtils.getLineSeparator();

	/**
	 * Curly bracket right text.
	 */
	private static final String CURLY_BRACKET_RIGHT = "}"; //$NON-NLS-1$

	/**
	 * The constructor.
	 */
	private AUnitTestCaseHelper() {
		// never used.
	}

	/**
	 * Generate the content of the class test case.
	 * 
	 * @param className
	 *            the class name.
	 * @param pack
	 *            the package fragment.
	 * @param emtlPath
	 *            the compiled module path.
	 * @param module
	 *            The module.
	 * @return the content of the class test case.
	 */
	public static String generateClassTestCase(String className, IPackageFragment pack, String emtlPath,
			Module module) {

		StringBuffer fileContent = new StringBuffer();
		fileContent.append("package " + pack.getElementName() + ";" + NL); //$NON-NLS-1$//$NON-NLS-2$
		fileContent.append("" + NL); //$NON-NLS-1$
		// imports
		fileContent.append(generateImports());

		// class
		fileContent.append("@CompiledModuleTest(emtl = \"" + emtlPath + "\")" + NL); //$NON-NLS-1$ //$NON-NLS-2$
		fileContent.append("@RunWith(AcceleoSuite.class)" + NL); //$NON-NLS-1$
		fileContent.append("public class " + className + " {" + NL); //$NON-NLS-1$ //$NON-NLS-2$
		fileContent.append("" + NL); //$NON-NLS-1$

		// attributes
		fileContent.append(generateAttributes());

		// setUp
		// loadModel
		fileContent.append(generateStaticMethods());

		List<ModuleElement> moduleElements = module.getOwnedModuleElement();
		// test cases
		fileContent.append(generateTestCases(moduleElements));

		fileContent.append(CURLY_BRACKET_RIGHT);
		return fileContent.toString();
	}

	/**
	 * Generates imports.
	 * 
	 * @return the content.
	 */
	private static String generateImports() {

		StringBuffer fileContent = new StringBuffer();

		fileContent
				.append("import static org.eclipse.acceleo.unit.core.assertion.AcceleoAssert.assertThatGeneratedArea;" + NL); //$NON-NLS-1$
		fileContent
				.append("import static org.eclipse.acceleo.unit.core.assertion.AcceleoAssert.assertThatTemplateResultFrom;" + NL); //$NON-NLS-1$
		fileContent.append("import static org.eclipse.acceleo.unit.core.matchers.TemplateExp.and;" + NL); //$NON-NLS-1$
		fileContent
				.append("import static org.eclipse.acceleo.unit.core.matchers.TemplateExp.templateLocation;" + NL); //$NON-NLS-1$
		fileContent
				.append("import static org.eclipse.acceleo.unit.core.matchers.TemplateExp.templateText;" + NL); //$NON-NLS-1$
		fileContent.append("import static org.junit.Assert.assertNotNull;" + NL); //$NON-NLS-1$
		fileContent.append("import static org.junit.Assert.assertTrue;" + NL); //$NON-NLS-1$
		fileContent.append("import static org.junit.Assert.fail;" + NL); //$NON-NLS-1$
		fileContent.append("" + NL); //$NON-NLS-1$
		fileContent.append("import java.io.File;" + NL); //$NON-NLS-1$
		fileContent.append("import java.io.IOException;" + NL); //$NON-NLS-1$
		fileContent.append("" + NL); //$NON-NLS-1$
		fileContent.append("import org.eclipse.acceleo.common.utils.ModelUtils;" + NL); //$NON-NLS-1$
		fileContent.append("import org.eclipse.emf.common.*;" + NL); //$NON-NLS-1$
		fileContent.append("import org.eclipse.emf.common.util.*;" + NL); //$NON-NLS-1$
		fileContent.append("import org.eclipse.emf.ecore.*;" + NL); //$NON-NLS-1$
		fileContent.append("import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;" + NL); //$NON-NLS-1$
		fileContent.append("import org.junit.BeforeClass;" + NL); //$NON-NLS-1$
		fileContent.append("import org.junit.runner.RunWith;" + NL); //$NON-NLS-1$
		fileContent.append("" + NL); //$NON-NLS-1$
		fileContent.append("import org.eclipse.acceleo.unit.core.annotation.CompiledModuleTest;" + NL); //$NON-NLS-1$
		fileContent.append("import org.eclipse.acceleo.unit.core.annotation.TemplateTest;" + NL); //$NON-NLS-1$
		fileContent.append("import org.eclipse.acceleo.unit.core.annotation.QueryTest;" + NL); //$NON-NLS-1$
		fileContent.append("import org.eclipse.acceleo.unit.core.generation.QueryGenerationHelper;" + NL); //$NON-NLS-1$
		fileContent.append("import org.eclipse.acceleo.unit.core.generation.TemplateGenerationHelper;" + NL); //$NON-NLS-1$
		fileContent.append("import org.eclipse.acceleo.unit.core.suite.AcceleoSuite;" + NL); //$NON-NLS-1$
		fileContent.append("" + NL); //$NON-NLS-1$

		return fileContent.toString();
	}

	/**
	 * Generates attributes.
	 * 
	 * @return the content.
	 */
	private static String generateAttributes() {

		StringBuffer fileContent = new StringBuffer();

		fileContent.append("private static final String MODEL_PROJECT_NAME = \"<Model project name>\";" + NL); //$NON-NLS-1$
		fileContent
				.append("private static final String MODEL_RELATIVE_PATH = \"<Model relative path>\";" + NL); //$NON-NLS-1$
		fileContent.append("private static EObject[] modelPackages;" + NL); //$NON-NLS-1$
		fileContent.append("" + NL); //$NON-NLS-1$

		return fileContent.toString();
	}

	/**
	 * Generates static methods setUp and loadModel.
	 * 
	 * @return the content.
	 */
	private static String generateStaticMethods() {

		StringBuffer fileContent = new StringBuffer();

		// setUp
		fileContent.append("@BeforeClass" + NL); //$NON-NLS-1$
		fileContent.append("public static void setUp() throws Exception {" + NL); //$NON-NLS-1$
		fileContent.append("modelPackages = loadModel();" + NL); //$NON-NLS-1$
		fileContent.append("assertNotNull(modelPackages);" + NL); //$NON-NLS-1$
		fileContent.append(CURLY_BRACKET_RIGHT + NL);
		fileContent.append("" + NL); //$NON-NLS-1$

		// loadModel
		fileContent.append("public static EObject[] loadModel() {" + NL); //$NON-NLS-1$
		fileContent.append("try {" + NL); //$NON-NLS-1$

		fileContent.append("if (EMFPlugin.IS_ECLIPSE_RUNNING) {" + NL); //$NON-NLS-1$
		fileContent
				.append("EObject eObject = ModelUtils.load(URI.createURI(\"platform:/plugin/\" + MODEL_PROJECT_NAME + \"/\" + MODEL_RELATIVE_PATH), new ResourceSetImpl());" + NL); //$NON-NLS-1$
		fileContent.append("return new EObject[]{eObject};" + NL); //$NON-NLS-1$

		fileContent.append("} else {" + NL); //$NON-NLS-1$
		fileContent.append("String path = System.getProperty(\"user.dir\");" + NL); //$NON-NLS-1$
		fileContent.append("File filePath = new File(path);" + NL); //$NON-NLS-1$
		fileContent
				.append("filePath = new File(filePath,  \"../\" + MODEL_PROJECT_NAME + \"/\" + MODEL_RELATIVE_PATH);" + NL); //$NON-NLS-1$
		fileContent
				.append("EObject eObject = ModelUtils.load(URI.createFileURI(filePath.getAbsolutePath()), new ResourceSetImpl());" + NL); //$NON-NLS-1$
		fileContent.append("return new EObject[]{eObject};" + NL); //$NON-NLS-1$
		fileContent.append(CURLY_BRACKET_RIGHT + NL);

		fileContent.append("} catch (IOException e) {" + NL); //$NON-NLS-1$
		fileContent.append("fail(e.getMessage());" + NL); //$NON-NLS-1$

		fileContent.append(CURLY_BRACKET_RIGHT + NL);
		fileContent.append("return new EObject[]{};" + NL); //$NON-NLS-1$
		fileContent.append(CURLY_BRACKET_RIGHT + NL);

		return fileContent.toString();
	}

	/**
	 * Generates test cases with a list of module.
	 * 
	 * @param moduleElements
	 *            the module elements list.
	 * @return the content of the text case.
	 */
	private static String generateTestCases(List<ModuleElement> moduleElements) {
		StringBuffer fileContent = new StringBuffer();
		Map<String, Integer> moduleNameIndexMap = new HashMap<String, Integer>(moduleElements.size());

		for (ModuleElement moduleElement : moduleElements) {
			String moduleName = moduleElement.getName();

			if (moduleNameIndexMap.containsKey(moduleName)) {
				Integer index = moduleNameIndexMap.get(moduleName);
				moduleNameIndexMap.put(moduleName, Integer.valueOf(index.intValue() + 1));
			} else {
				moduleNameIndexMap.put(moduleName, Integer.valueOf(0));
			}
			int currentIndex = moduleNameIndexMap.get(moduleName).intValue();

			String annotationSuffix = ""; //$NON-NLS-1$
			String functionNameSuffix = ""; //$NON-NLS-1$
			if (currentIndex > 0) {
				annotationSuffix = ", index=" + currentIndex; //$NON-NLS-1$
				functionNameSuffix = String.valueOf(currentIndex);
			}

			if (moduleElement instanceof Template) {
				Template template = (Template)moduleElement;
				Variable firstParam = template.getParameter().get(0);
				fileContent.append("/**" + NL); //$NON-NLS-1$
				fileContent
						.append("* Template test : " + moduleName + " (" + firstParam.getType() + " " + firstParam.getName() + ") " + NL); //$NON-NLS-1$//$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$
				fileContent.append("* @param helper the template helper" + NL); //$NON-NLS-1$
				fileContent.append("*/" + NL); //$NON-NLS-1$
				fileContent
						.append("@TemplateTest(qualifiedName = \"" + template.getName() + "\"" + annotationSuffix + ") " + NL); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
				fileContent
						.append("public void test" + AUnitUtils.convert2ClassIdent(template.getName()) + functionNameSuffix + "(TemplateGenerationHelper helper){" + NL); //$NON-NLS-1$ //$NON-NLS-2$
				fileContent.append("fail(\"Not yet implemented\");" + NL); //$NON-NLS-1$
				fileContent.append(CURLY_BRACKET_RIGHT + NL);
			} else if (moduleElement instanceof Query) {
				Query query = (Query)moduleElement;
				Variable firstParam = query.getParameter().get(0);
				fileContent.append("/**" + NL); //$NON-NLS-1$
				fileContent
						.append("* Query Test : " + moduleName + " (" + firstParam.getType() + " " + firstParam.getName() + ")" + NL); //$NON-NLS-1$//$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$
				fileContent.append("* @param helper the query helper" + NL); //$NON-NLS-1$
				fileContent.append("*/" + NL); //$NON-NLS-1$
				fileContent
						.append("@QueryTest(qualifiedName = \"" + query.getName() + "\"" + annotationSuffix + ")" + NL); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
				fileContent
						.append("public void test" + AUnitUtils.convert2ClassIdent(query.getName()) + functionNameSuffix + "(QueryGenerationHelper helper){" + NL); //$NON-NLS-1$ //$NON-NLS-2$
				fileContent.append("fail(\"Not yet implemented\");" + NL); //$NON-NLS-1$
				fileContent.append(CURLY_BRACKET_RIGHT + NL);
			}
		}
		return fileContent.toString();
	}
}

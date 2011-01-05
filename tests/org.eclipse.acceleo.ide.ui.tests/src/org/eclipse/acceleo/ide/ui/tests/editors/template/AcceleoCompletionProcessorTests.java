/*******************************************************************************
 * Copyright (c) 2008, 2011 Obeo.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.acceleo.ide.ui.tests.editors.template;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import junit.framework.AssertionFailedError;
import junit.framework.TestCase;

import org.eclipse.acceleo.internal.ide.ui.editors.template.AcceleoCompletionProcessor;
import org.eclipse.acceleo.internal.ide.ui.editors.template.AcceleoSourceContent;
import org.eclipse.acceleo.internal.parser.cst.utils.FileContent;
import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.Platform;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.jface.text.contentassist.ICompletionProposal;
import org.osgi.framework.Bundle;

@SuppressWarnings("nls")
public class AcceleoCompletionProcessorTests extends TestCase {

	private Bundle bundle;

	private StringBuffer text;

	private AcceleoCompletionProcessor processor;

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		bundle = Platform.getBundle("org.eclipse.acceleo.ide.ui.tests");
		assertNotNull(bundle);
		assertFalse(EPackage.Registry.INSTANCE.isEmpty());
		File file = createFile("/data/template/mtlCompletionProcessor.mtl");
		text = FileContent.getFileContent(file);
		AcceleoSourceContent content = new AcceleoSourceContent() {
			@Override
			public List<URI> getAccessibleOutputFiles() {
				List<URI> dependencies = new ArrayList<URI>();
				dependencies.add(createFileURI("/data/template/mtlCompletionProcessorCommon.emtl"));
				return dependencies;
			}
		};
		content.init(text);
		content.createCST();
		processor = new AcceleoCompletionProcessor(content);
	}

	@Override
	protected void tearDown() throws Exception {
		super.tearDown();
		bundle = null;
	}

	private File createFile(String pathName) {
		try {
			String fileLocation = FileLocator.resolve(bundle.getEntry(pathName)).getPath();
			return new File(fileLocation);
		} catch (IOException e) {
			throw new AssertionFailedError(e.getMessage());
		}

	}

	protected URI createFileURI(String pathName) {
		try {
			String fileLocation = FileLocator.resolve(bundle.getEntry(pathName)).getPath();
			return URI.createFileURI(fileLocation);
		} catch (IOException e) {
			throw new AssertionFailedError(e.getMessage());
		}
	}

	public void testModule() {
		List<String> messages = new ArrayList<String>();
		checkProposalsAt(messages, 1, 1, "");
		checkProposalsAt(messages, 1, 69, "http://www.eclipse.org/emf/2002/Ecore");
		checkProposalsAt(messages, 1, 71, "extends");
		if (messages.size() > 0) {
			fail(messages.toString());
		}
	}

	public void testImport() {
		List<String> messages = new ArrayList<String>();
		checkProposalsAt(messages, 2, 1, "[import]$[template]$[query]$[macro]$[comment]");
		checkProposalsAt(messages, 3, 9, "mtlCompletionProcessorCommon");
		checkProposalsAt(messages, 4, 1, "[import]$[template]$[query]$[macro]$[comment]");
		if (messages.size() > 0) {
			fail(messages.toString());
		}
	}

	public void testTemplate() {
		List<String> messages = new ArrayList<String>();
		checkProposalsAt(messages, 5, 41, "overrides$? ()$post ()${ }");
		checkProposalsAt(messages, 5, 42, "overrides$? ()$post ()${ }");
		checkProposalsAt(messages, 6, 5,
				"[ ]$[for]$[if]$[file] - @main$[file]$[let]$[trace]$[protected]$[super]$'['$']'$[comment]$@main");
		if (messages.size() > 0) {
			fail(messages.toString());
		}
	}

	public void testIf() {
		List<String> messages = new ArrayList<String>();
		checkProposalsAt(messages, 8, 5, "[elseif]$[else]$[ ]");
		checkProposalsAt(messages, 10, 5, "[ ]$[for]$[if]");
		if (messages.size() > 0) {
			fail(messages.toString());
		}
	}

	public void testFor() {
		List<String> messages = new ArrayList<String>();
		checkProposalsAt(messages, 13, 24, "EClassifier");
		checkProposalsAt(messages, 13, 35, "eClassifiers:EClassifier [0..*]");
		checkProposalsAt(messages, 13, 43, "before ()$separator ()$after ()$? ()");
		checkProposalsAt(messages, 13, 59, "Integer");
		checkProposalsAt(messages, 17, 43, "before ()$after ()$? ()${ }");
		checkProposalsAt(messages, 17, 57, "p:EPackage$self$eAnnotations:EAnnotation [0..*]");
		if (messages.size() > 0) {
			fail(messages.toString());
		}
	}

	public void testTemplateInvocation() {
		List<String> messages = new ArrayList<String>();
		checkProposalsAt(messages, 21, 10, "name:EString [0..1]");
		checkProposalsAt(messages, 21, 22, "toLowerFirst() : String");
		checkProposalsAt(messages, 21, 25, "before ()$separator ()$after ()");
		if (messages.size() > 0) {
			fail(messages.toString());
		}
	}

	public void testLet() {
		List<String> messages = new ArrayList<String>();
		checkProposalsAt(messages, 23, 23,
				"p:EPackage$self$classToJava(p: EPackage) : String$ancestors() : Sequence(OclAny)");
		checkProposalsAt(messages, 24, 5, "[elselet]$[else]$[ ]");
		if (messages.size() > 0) {
			fail(messages.toString());
		}
	}

	private void checkProposalsAt(List<String> messages, int line, int column, String nrText) {
		int offset = 0;
		int offsetLine = 1;
		int offsetColumn = 1;
		while (offsetLine < line || (offsetLine == line && offsetColumn < column)) {
			char c = text.charAt(offset);
			if (c == '\n') {
				offsetLine++;
				offsetColumn = 1;
			} else if (c == '\t') {
				offsetColumn += 5;
			} else {
				offsetColumn++;
			}
			offset++;
		}
		ICompletionProposal[] proposals = processor.computeCompletionProposals(null, offset);
		boolean ok;
		StringTokenizer nrST = new StringTokenizer(nrText, "$");
		int nrSTCount = nrST.countTokens();
		if ((nrSTCount == 0 && proposals.length > 0) || (proposals.length < nrSTCount)) {
			ok = false;
		} else {
			ok = true;
			for (int i = 0; ok && i < nrSTCount; i++) {
				String s1 = nrST.nextToken().trim();
				int pos = s1.lastIndexOf(":");
				if (pos > -1) {
					s1 = s1.substring(0, pos).trim();
				}
				String s2 = proposals[i].getDisplayString().trim();
				pos = s2.lastIndexOf(":");
				if (pos > -1) {
					s2 = s2.substring(0, pos).trim();
				}
				ok = s1.equals(s2);
			}
		}
		if (!ok) {
			StringBuffer txt = new StringBuffer();
			for (int i = 0; i < proposals.length; i++) {
				txt.append(proposals[i].getDisplayString().trim());
				if (i + 1 < proposals.length) {
					txt.append('$');
				}
			}
			String message = "\n[" + line + "," + column + "] : proposals not valid, NR=" + txt.toString();
			messages.add(message);
		}
	}

}

/*******************************************************************************
 * Copyright (c) 2008, 2012 Obeo.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.acceleo.internal.parser.ast;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.acceleo.common.IAcceleoConstants;
import org.eclipse.acceleo.internal.parser.AcceleoParserMessages;
import org.eclipse.acceleo.internal.parser.cst.utils.ParserUtils;
import org.eclipse.acceleo.internal.parser.cst.utils.Region;
import org.eclipse.acceleo.internal.parser.cst.utils.SequenceBlock;
import org.eclipse.acceleo.model.mtl.Comment;
import org.eclipse.acceleo.model.mtl.CommentBody;
import org.eclipse.acceleo.model.mtl.Documentation;
import org.eclipse.acceleo.model.mtl.DocumentedElement;
import org.eclipse.acceleo.model.mtl.Module;
import org.eclipse.acceleo.model.mtl.ModuleDocumentation;
import org.eclipse.acceleo.model.mtl.ModuleElement;
import org.eclipse.acceleo.model.mtl.ModuleElementDocumentation;
import org.eclipse.acceleo.model.mtl.MtlFactory;
import org.eclipse.acceleo.model.mtl.ParameterDocumentation;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;

/**
 * The main class used to transform a CST model to an AST model. This class is able to run the 'Resolve' step.
 * This class is also able to run the 'Get & Resolve documentation' step.
 * 
 * @author <a href="mailto:stephane.begaudeau@obeo.fr">Stephane Begaudeau</a>
 */
public class CST2ASTConverterWithDocumentationResolver extends CST2ASTConverterWithResolver {

	/**
	 * The beginning of all documentation tags.
	 */
	private static final String BEGINNING_DOCUMENTATION_TAG = "@"; //$NON-NLS-1$

	/**
	 * The constructor.
	 */
	public CST2ASTConverterWithDocumentationResolver() {
		super();
	}

	/**
	 * Resolve the documentation of the AST.
	 * 
	 * @param module
	 *            The module
	 * @param buffer
	 *            The buffer
	 */
	public void resolveASTDocumentation(final Module module, final StringBuffer buffer) {
		int endHeaderPosition = buffer.indexOf(IAcceleoConstants.DEFAULT_END_BODY_CHAR
				+ IAcceleoConstants.DEFAULT_END, module.getStartHeaderPosition());
		module.setEndHeaderPosition(endHeaderPosition);
		if (module.isDeprecated()) {
			logWarning(AcceleoParserMessages.getString(DEPRECATED_MODULE_MESSAGE, module.getName()), module
					.getStartHeaderPosition(), module.getEndHeaderPosition());
		}

		transformStepResolveModuleDocumentation(module, buffer);

		List<EObject> eContents = module.eContents();
		for (int i = 0; i < eContents.size(); i++) {
			EObject eObject = eContents.get(i);
			if (eObject instanceof Comment) {
				transformStepResolveComments((Comment)eObject, buffer);
			}

			// If we are on a documentation node, and it is not the last node (i + 1) < eContents.size(), we
			// will try to see if that next node is a DocumentedElement. If it is, we will attach the current
			// documentation node as the documentation of the next node.
			if ((i + 1) < eContents.size() && eObject instanceof Documentation) {
				Documentation documentation = (Documentation)eObject;
				EObject next = eContents.get(i + 1);

				if (next instanceof DocumentedElement) {
					DocumentedElement documentedElement = (DocumentedElement)next;
					documentation.setDocumentedElement(documentedElement);

					ModuleElementDocumentation moduleElementDocumentation = MtlFactory.eINSTANCE
							.createModuleElementDocumentation();

					moduleElementDocumentation.setBody(documentation.getBody());
					moduleElementDocumentation.setStartPosition(documentation.getStartPosition());
					moduleElementDocumentation.setEndPosition(documentation.getEndPosition());
					moduleElementDocumentation.setName(documentation.getName());

					// With EMF, you can't change an object that is contained (containment = true) by another
					// object, you have to use EcloreUtil.replace to exchange the two objects. We had a
					// Documentation, now we will have a ModuleElementDocumentation.
					EcoreUtil.replace(documentation, moduleElementDocumentation);

					transformStepResolveModuleElementDocumentation(moduleElementDocumentation);

				}
			}
		}
	}

	/**
	 * Resolve the positions of all the documentation of the module and transform it into a
	 * ModuleDocumentation.
	 * 
	 * @param module
	 *            The module
	 * @param buffer
	 *            The buffer
	 */
	private void transformStepResolveModuleDocumentation(Module module, StringBuffer buffer) {
		Documentation documentation = module.getDocumentation();
		if (documentation != null) {
			transformStepResolveComments(documentation, buffer);

			// We create a ModuleDocumentation element from the Documentation one
			ModuleDocumentation moduleDocumentation = MtlFactory.eINSTANCE.createModuleDocumentation();
			moduleDocumentation.setBody(documentation.getBody());
			moduleDocumentation.setStartPosition(documentation.getStartPosition());
			moduleDocumentation.setEndPosition(documentation.getEndPosition());
			moduleDocumentation.setName(documentation.getName());

			// With EMF, you can't change an object that is contained (containment = true) by another object,
			// you have to use EcloreUtil.replace to exchange the two objects. We had a Documentation, now we
			// will have a ModuleDocumentation.
			EcoreUtil.replace(documentation, moduleDocumentation);

			transformStepResolveModuleDocumentation(moduleDocumentation);
		}
	}

	/**
	 * Parse the content of the module element documentation.
	 * 
	 * @param moduleElementDocumentation
	 *            The documentation
	 * @return The module element documentation
	 */
	private ModuleElementDocumentation transformStepResolveModuleElementDocumentation(
			ModuleElementDocumentation moduleElementDocumentation) {
		List<DocumentationPositionedKeyword> keywordOrder = this
				.computeModuleElementKeywordOrder(moduleElementDocumentation.getBody());
		for (int i = 0; i < keywordOrder.size(); i++) {
			DocumentationPositionedKeyword k = keywordOrder.get(i);
			String value = null;
			if ((i + 1) < keywordOrder.size()) {
				// If we are not at the end of the list of tags in the documentation, we parse the text
				// between the current tag and the next one
				value = this.computeDocumentationValue(moduleElementDocumentation.getBody().getValue(), k
						.getKeyword(), k.getOffset(), keywordOrder.get(i + 1).getOffset());
			} else {
				// Else, we parse the text between the current tag and the end of the documentation
				value = this.computeDocumentationValue(moduleElementDocumentation.getBody().getValue(), k
						.getKeyword(), k.getOffset(), moduleElementDocumentation.getBody().getValue()
						.length());
			}
			this.addValueToDocumentation(value.trim(), k.getKeyword(), moduleElementDocumentation);
		}

		return moduleElementDocumentation;
	}

	/**
	 * Parse the content of the module documentation.
	 * 
	 * @param moduleDocumentation
	 *            The documentation
	 * @return The module documentation
	 */
	private ModuleDocumentation transformStepResolveModuleDocumentation(
			ModuleDocumentation moduleDocumentation) {
		List<DocumentationPositionedKeyword> keywordOrder = this
				.computeModuleKeywordOrder(moduleDocumentation.getBody());
		for (int i = 0; i < keywordOrder.size(); i++) {
			DocumentationPositionedKeyword positionedTag = keywordOrder.get(i);
			String value = ""; //$NON-NLS-1$
			if ((i + 1) < keywordOrder.size()) {
				// If we are not at the and of the list of tags in the documentation, we parse the text
				// between the current tag and the next one
				value = this.computeDocumentationValue(moduleDocumentation.getBody().getValue(),
						positionedTag.getKeyword(), positionedTag.getOffset(), keywordOrder.get(i + 1)
								.getOffset());
			} else {
				// Else, we parse the text between the current tag and the end of the documentation
				value = this.computeDocumentationValue(moduleDocumentation.getBody().getValue(),
						positionedTag.getKeyword(), positionedTag.getOffset(), moduleDocumentation.getBody()
								.getValue().length());
			}
			this.addValueToDocumentation(value.trim(), positionedTag.getKeyword(), moduleDocumentation);
		}

		return moduleDocumentation;
	}

	/**
	 * Resolve the positions of the comments.
	 * 
	 * @param comment
	 *            The comment
	 * @param buffer
	 *            The buffer
	 */
	private void transformStepResolveComments(Comment comment, StringBuffer buffer) {
		// There are 3 cases:
		// 1- We have a documentation block '[**.../]', then the start position of the body is start position
		// of the documentation + 3 (size of '[**') and the end position of the body is the end position of
		// the documentation -2 (size of '/]')
		// 2- We have a comment block where everything is in the header '[comment .../]', the start of the
		// body is start of the comment + 8 and its end is the end of the comment - 2
		// 3- We have a comment block '[comment]...[/comment]', the start of the body is the start of the
		// comment + 9 and its end is the end of the comment - 10

		if (comment instanceof org.eclipse.acceleo.model.mtl.Documentation) {
			SequenceBlock pDocumentation = ParserUtils.createAcceleoSequenceBlock(true,
					IAcceleoConstants.DOCUMENTATION_BEGIN, null, null);
			Region beginHeader = pDocumentation.searchBeginHeader(buffer, comment.getStartPosition(), comment
					.getEndPosition());
			Region endHeaderAtBeginHeader = pDocumentation.searchEndHeaderAtBeginHeader(buffer, beginHeader,
					comment.getEndPosition());

			// [**.../]
			comment.getBody().setStartPosition(beginHeader.e());
			comment.getBody().setEndPosition(endHeaderAtBeginHeader.b());
		} else {
			SequenceBlock pComment = ParserUtils.createAcceleoSequenceBlock(false, IAcceleoConstants.COMMENT,
					null, null);
			Region beginHeader = pComment.searchBeginHeader(buffer, comment.getStartPosition(), comment
					.getEndPosition());
			Region endHeaderAtBeginHeader = pComment.searchEndHeaderAtBeginHeader(buffer, beginHeader,
					comment.getEndPosition());

			if (endHeaderAtBeginHeader.getSequence() == pComment.getEndHeaderBody()) {
				// [comment .../]
				comment.getBody().setStartPosition(beginHeader.e());
				comment.getBody().setEndPosition(endHeaderAtBeginHeader.b());
			} else {
				// [comment]...[/comment]
				Region eB = pComment.searchEndBodyAtEndHeader(buffer, endHeaderAtBeginHeader, comment
						.getEndPosition());
				comment.getBody().setStartPosition(endHeaderAtBeginHeader.e());
				comment.getBody().setEndPosition(eB.b());
			}
		}
	}

	/**
	 * Compute the value of the comment associated with the given keyword.
	 * 
	 * @param body
	 *            The body of the comment to parse
	 * @param keyword
	 *            The keyword
	 * @param posBegin
	 *            The begin position
	 * @param posEnd
	 *            The end position
	 * @return The value of the comment associated with the given keyword
	 */
	private String computeDocumentationValue(final String body, final String keyword, final int posBegin,
			final int posEnd) {
		String newString = body.substring(posBegin, posEnd);
		newString = newString.trim();
		newString = newString.substring(keyword.length());
		return newString;
	}

	/**
	 * Find the order of the keyword in the body of the comment.
	 * 
	 * @param commentBody
	 *            The body of the comment to parse
	 * @return The order of the keywords in the body of the comment
	 */
	private List<DocumentationPositionedKeyword> computeModuleKeywordOrder(final CommentBody commentBody) {
		List<DocumentationPositionedKeyword> list = new ArrayList<DocumentationPositionedKeyword>();
		final String body = commentBody.getValue();
		int currentPos = 0;
		while (currentPos < body.length() && currentPos != -1) {
			int indexOf = body.indexOf(BEGINNING_DOCUMENTATION_TAG, currentPos);
			if (indexOf != -1) {
				if (body.startsWith(IAcceleoConstants.TAG_AUTHOR, indexOf)) {
					list.add(new DocumentationPositionedKeyword(IAcceleoConstants.TAG_AUTHOR, indexOf));
				} else if (body.startsWith(IAcceleoConstants.TAG_DEPRECATED, indexOf)) {
					list.add(new DocumentationPositionedKeyword(IAcceleoConstants.TAG_DEPRECATED, indexOf));
				} else if (body.startsWith(IAcceleoConstants.TAG_SINCE, indexOf)) {
					list.add(new DocumentationPositionedKeyword(IAcceleoConstants.TAG_SINCE, indexOf));
				} else if (body.startsWith(IAcceleoConstants.TAG_VERSION, indexOf)) {
					list.add(new DocumentationPositionedKeyword(IAcceleoConstants.TAG_VERSION, indexOf));
				}
				currentPos = indexOf + 1;
			} else {
				currentPos = indexOf;
			}
		}
		return list;
	}

	/**
	 * Find the order of the keyword in the comment of the module element.
	 * 
	 * @param commentBody
	 *            The body of the comment of the module element to parse
	 * @return The order of the keywords in the comment of the module element
	 */
	private List<DocumentationPositionedKeyword> computeModuleElementKeywordOrder(
			final CommentBody commentBody) {
		List<DocumentationPositionedKeyword> list = new ArrayList<DocumentationPositionedKeyword>();
		final String body = commentBody.getValue();
		int currentPos = 0;
		while (currentPos < body.length() && currentPos != -1) {
			int indexOf = body.indexOf(BEGINNING_DOCUMENTATION_TAG, currentPos);
			if (indexOf != -1) {
				if (body.startsWith(IAcceleoConstants.TAG_DEPRECATED, indexOf)) {
					list.add(new DocumentationPositionedKeyword(IAcceleoConstants.TAG_DEPRECATED, indexOf));
				} else if (body.startsWith(IAcceleoConstants.TAG_PARAM, indexOf)) {
					list.add(new DocumentationPositionedKeyword(IAcceleoConstants.TAG_PARAM, indexOf));
				}
				currentPos = indexOf + 1;
			} else {
				currentPos = indexOf;
			}
		}
		return list;
	}

	/**
	 * Add the value of the comment to the documentation.
	 * 
	 * @param value
	 *            The value
	 * @param keyword
	 *            The keyword
	 * @param doc
	 *            the documentation
	 */
	private void addValueToDocumentation(final String value, final String keyword, final Documentation doc) {
		if (doc instanceof ModuleDocumentation) {
			ModuleDocumentation moduleDoc = (ModuleDocumentation)doc;
			if (IAcceleoConstants.TAG_AUTHOR.equals(keyword)) {
				moduleDoc.setAuthor(value);
			} else if (IAcceleoConstants.TAG_DEPRECATED.equals(keyword)
					&& doc.getDocumentedElement() instanceof Module) {
				Module module = (Module)moduleDoc.getDocumentedElement();
				module.setDeprecated(true);

				List<ModuleElement> moduleElements = module.getOwnedModuleElement();

				// The module is depreciated and all its templates, queries and macros too
				for (ModuleElement moduleElement : moduleElements) {
					if (moduleElement instanceof DocumentedElement) {
						DocumentedElement documentedElement = (DocumentedElement)moduleElement;
						documentedElement.setDeprecated(true);
					}
				}
			} else if (IAcceleoConstants.TAG_SINCE.equals(keyword)) {
				moduleDoc.setSince(value);
			} else if (IAcceleoConstants.TAG_VERSION.equals(keyword)) {
				moduleDoc.setVersion(value);
			}
		} else if (doc instanceof ModuleElementDocumentation) {
			ModuleElementDocumentation nodeDoc = (ModuleElementDocumentation)doc;
			if (IAcceleoConstants.TAG_DEPRECATED.equals(keyword) && nodeDoc.getDocumentedElement() != null) {
				DocumentedElement documentedElement = nodeDoc.getDocumentedElement();
				documentedElement.setDeprecated(true);
			} else if (IAcceleoConstants.TAG_PARAM.equals(keyword)) {
				List<ParameterDocumentation> parametersDocumentation = nodeDoc.getParametersDocumentation();

				String parameterName = ""; //$NON-NLS-1$
				final String space = " "; //$NON-NLS-1$

				CommentBody commentBody = MtlFactory.eINSTANCE.createCommentBody();

				if (value.contains(space)) {
					parameterName = value.substring(0, value.indexOf(space));
					commentBody.setValue(value.substring(value.indexOf(space)));
				} else {
					parameterName = value;
					commentBody.setValue(""); //$NON-NLS-1$
				}

				ParameterDocumentation parameterDocumentation = MtlFactory.eINSTANCE
						.createParameterDocumentation();
				parameterDocumentation.setBody(commentBody);
				parameterDocumentation.setName(parameterName);

				parametersDocumentation.add(parameterDocumentation);
			}
		}
	}

	/**
	 * A data class to store a keyword and its position.
	 * 
	 * @author <a href="mailto:stephane.begaudeau@obeo.fr">Stephane Begaudeau</a>
	 */
	private class DocumentationPositionedKeyword {
		/**
		 * the keyword.
		 */
		private String keyword;

		/**
		 * the position of the keyword.
		 */
		private int offset;

		/**
		 * The constructor.
		 * 
		 * @param k
		 *            The keyword
		 * @param b
		 *            The offset of the keyword
		 */
		public DocumentationPositionedKeyword(String k, int b) {
			this.keyword = k;
			this.offset = b;
		}

		/**
		 * Returns the keyword.
		 * 
		 * @return the keyword
		 */
		public String getKeyword() {
			return keyword;
		}

		/**
		 * Returns the offset of the keyword.
		 * 
		 * @return the offset of the keyword
		 */
		public int getOffset() {
			return offset;
		}
	}
}

/*******************************************************************************
 * Copyright (c) 2024 Obeo.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 * 
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.acceleo.aql.ls.services.workspace.command;

import org.eclipse.acceleo.Query;
import org.eclipse.acceleo.aql.ls.services.textdocument.AcceleoTextDocument;
import org.eclipse.acceleo.query.ast.Expression;
import org.eclipse.lsp4j.Range;
import org.eclipse.lsp4j.WorkspaceEdit;

/**
 * Extract selected {@link Expression} in {@link Query} command.
 * 
 * @author <a href="mailto:yvan.lussaud@obeo.fr">Yvan Lussaud</a>
 */
public class ExtractQueryCommand extends AbstractDocumentRangeCommand {

	public WorkspaceEdit exec(AcceleoTextDocument document, Range range) {
		// TODO Auto-generated method stub
		return null;
	}

}

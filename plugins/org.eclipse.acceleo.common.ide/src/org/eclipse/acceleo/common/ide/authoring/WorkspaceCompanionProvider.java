/*******************************************************************************
 * Copyright (c) 2015 Obeo.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.acceleo.common.ide.authoring;

import org.eclipse.acceleo.common.internal.utils.workspace.ClassLoadingCompanion;
import org.eclipse.acceleo.common.internal.utils.workspace.ClassLoadingCompanionProvider;

/**
 * A provider returning an implementation of {@link ClassLoadingCompanion} providing tight integration with
 * the Eclipse workspace.
 * 
 * @author <a href="mailto:cedric.brun@obeo.fr">Cedric Brun</a>
 * @since 3.6
 */
public class WorkspaceCompanionProvider implements ClassLoadingCompanionProvider {

	@Override
	public ClassLoadingCompanion getCompanion() {
		return AcceleoWorkspaceUtil.INSTANCE;
	}

}

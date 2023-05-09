/*******************************************************************************
 * Copyright (c) 2015, 2020 Obeo.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 *
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.acceleo.debug.event.model;

import org.eclipse.acceleo.debug.event.IDSLDebugEvent;

/**
 * An {@link IDSLDebugEvent debugger event} from the
 * {@link org.eclipse.acceleo.debug.ide.adapter.DSLDebugTargetAdapter debug target} to the
 * {@link org.eclipse.acceleo.debug.ide.IDSLDebugger debugger}.
 * 
 * @author <a href="mailto:yvan.lussaud@obeo.fr">Yvan Lussaud</a>
 */
public interface IDSLModelRequest extends IDSLDebugEvent {

}

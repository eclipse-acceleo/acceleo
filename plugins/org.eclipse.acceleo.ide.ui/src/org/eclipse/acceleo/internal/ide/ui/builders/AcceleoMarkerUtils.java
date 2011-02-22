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
package org.eclipse.acceleo.internal.ide.ui.builders;

import org.eclipse.acceleo.parser.AcceleoParserInfo;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.runtime.CoreException;

/**
 * Acceleo Marker Utils.
 * 
 * @author <a href="mailto:jonathan.musset@obeo.fr">Jonathan Musset</a>
 */
public final class AcceleoMarkerUtils {

	/**
	 * Acceleo Problem marker ID.
	 */
	public static final String PROBLEM_MARKER_ID = "org.eclipse.acceleo.ide.ui.problem"; //$NON-NLS-1$

	/**
	 * Acceleo Warning marker ID.
	 */
	public static final String WARNING_MARKER_ID = "org.eclipse.acceleo.ide.ui.warning"; //$NON-NLS-1$

	/**
	 * Acceleo Info marker ID.
	 */
	public static final String INFO_MARKER_ID = "org.eclipse.acceleo.ide.ui.info"; //$NON-NLS-1$

	/**
	 * Acceleo Override marker ID.
	 */
	public static final String OVERRIDE_MARKER_ID = "org.eclipse.acceleo.ide.ui.override"; //$NON-NLS-1$

	/**
	 * The ID of the marker of a task, those marker will appear in the Tasks view.
	 */
	public static final String TASK_MARKER_ID = "org.eclipse.core.resources.taskmarker"; //$NON-NLS-1$

	/**
	 * The constructor.
	 */
	private AcceleoMarkerUtils() {
		// prevent instantiation
	}

	/**
	 * Creates a marker on the given file.
	 * 
	 * @param markerId
	 *            The ID of the marker
	 * @param file
	 *            File on which to create a marker.
	 * @param line
	 *            is the line of the problem
	 * @param posBegin
	 *            is the beginning position of the data
	 * @param posEnd
	 *            is the ending position of the data
	 * @param message
	 *            Message of the data, it is the message displayed when you hover on the marker.
	 * @throws CoreException
	 *             This will be thrown if we couldn't set marker attributes.
	 */
	public static void createMarkerOnFile(String markerId, IFile file, int line, int posBegin, int posEnd,
			String message) throws CoreException {
		IMarker marker = createMarker(markerId, file, message);
		int priority = determinePriority(markerId, message);

		if (marker != null) {
			marker.setAttribute(IMarker.PRIORITY, priority);
			marker.setAttribute(IMarker.SEVERITY, IMarker.SEVERITY_WARNING);
			marker.setAttribute(IMarker.LINE_NUMBER, line);
			marker.setAttribute(IMarker.CHAR_START, posBegin);
			marker.setAttribute(IMarker.CHAR_END, posEnd);
			marker.setAttribute(IMarker.MESSAGE, message);
		} else {
			return;
		}

		if (AcceleoMarkerUtils.PROBLEM_MARKER_ID.equals(markerId)) {
			marker.setAttribute(IMarker.SEVERITY, IMarker.SEVERITY_ERROR);
		} else if (AcceleoMarkerUtils.INFO_MARKER_ID.equals(markerId)) {
			if (message.startsWith(AcceleoParserInfo.TEMPLATE_OVERRIDE)) {
				// Info markers that start with the template override message appears as a green arrow
				marker.setAttribute(IMarker.SEVERITY, IMarker.SEVERITY_INFO);
				marker.setAttribute(IMarker.MESSAGE, message.substring(AcceleoParserInfo.TEMPLATE_OVERRIDE
						.length()));
			} else if (message.startsWith(AcceleoParserInfo.TODO_COMMENT)) {
				// Info markers that start with the todo comment appears as todo tasks
				marker.setAttribute(IMarker.USER_EDITABLE, false);
				marker.setAttribute(IMarker.MESSAGE, message.substring(AcceleoParserInfo.TODO_COMMENT
						.length()));
			} else if (message.startsWith(AcceleoParserInfo.FIXME_COMMENT)) {
				// Info markers that start with the fixme comment appears as fixme tasks
				marker.setAttribute(IMarker.USER_EDITABLE, false);
				marker.setAttribute(IMarker.MESSAGE, message.substring(AcceleoParserInfo.FIXME_COMMENT
						.length()));
			}
		}
	}

	/**
	 * Creates the marker instance and associates it with the given file.
	 * 
	 * @param markerId
	 *            ID of the marker that's to be created.
	 * @param file
	 *            File on which to create a marker.
	 * @param message
	 *            Message of the data, it is the message displayed when you hover on the marker.
	 * @return The created marker.
	 * @throws CoreException
	 *             This will be thrown if we couldn't set marker attributes.
	 */
	private static IMarker createMarker(String markerId, IFile file, String message) throws CoreException {
		IMarker marker = null;
		if (AcceleoMarkerUtils.PROBLEM_MARKER_ID.equals(markerId)
				|| AcceleoMarkerUtils.WARNING_MARKER_ID.equals(markerId)) {
			marker = file.createMarker(markerId);
		} else if (AcceleoMarkerUtils.INFO_MARKER_ID.equals(markerId)) {
			/*
			 * For 'info' markers we've positionned a tag at the beginning of the displayed message, we'll
			 * check these to create the different markers.
			 */
			if (message.startsWith(AcceleoParserInfo.TEMPLATE_OVERRIDE)) {
				// Info markers that start with the template override message appears as a green arrow
				marker = file.createMarker(AcceleoMarkerUtils.OVERRIDE_MARKER_ID);
			} else if (message.startsWith(AcceleoParserInfo.TODO_COMMENT)) {
				// Info markers that start with the todo comment appears as todo tasks
				marker = file.createMarker(TASK_MARKER_ID);
			} else if (message.startsWith(AcceleoParserInfo.FIXME_COMMENT)) {
				// Info markers that start with the fixme comment appears as fixme tasks
				marker = file.createMarker(TASK_MARKER_ID);
			} else {
				// otherwise info markers are created as info markers
				marker = file.createMarker(AcceleoMarkerUtils.INFO_MARKER_ID);
			}
		}
		return marker;
	}

	/**
	 * Some of our markers have different priorities, this will allow us to determine which it should be.
	 * 
	 * @param markerId
	 *            ID of the marker that's to be created.
	 * @param message
	 *            Message of the data, it is the message displayed when you hover on the marker.
	 * @return Priority this particular marker should sport.
	 */
	private static int determinePriority(String markerId, String message) {
		// Only information markers for 'TODO' tasks are not high priority
		int priority = IMarker.PRIORITY_HIGH;
		if (AcceleoMarkerUtils.INFO_MARKER_ID.equals(markerId)
				&& message.startsWith(AcceleoParserInfo.TODO_COMMENT)) {
			priority = IMarker.PRIORITY_NORMAL;
		}
		return priority;
	}
}

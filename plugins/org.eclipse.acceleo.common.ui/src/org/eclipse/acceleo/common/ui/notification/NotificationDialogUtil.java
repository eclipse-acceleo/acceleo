/*******************************************************************************
 * Copyright (c) 2009, 2011 Emil Crumhorn.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Emil Crumhorn - initial API and implementation
 *     Stephane Begaudeau - improvements
 *******************************************************************************/
package org.eclipse.acceleo.common.ui.notification;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.eclipse.acceleo.common.ui.internal.notification.ColorCache;
import org.eclipse.acceleo.common.ui.internal.notification.FontCache;
import org.eclipse.core.runtime.preferences.IEclipsePreferences;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Link;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;

/**
 * The notification dialog.
 * 
 * @author <a href="mailto:emil.crumhorn@gmail.com">Emil Crumhorn</a>
 */
public final class NotificationDialogUtil {

	/**
	 * how long the the tray popup is displayed after fading in (in milliseconds).
	 */
	private static final int DISPLAY_TIME = 4500;

	/**
	 * how long each tick is when fading in (in ms).
	 */
	private static final int FADE_TIMER = 50;

	/**
	 * how long each tick is when fading out (in ms).
	 */
	private static final int FADE_IN_STEP = 30;

	/**
	 * how many tick steps we use when fading out.
	 */
	private static final int FADE_OUT_STEP = 8;

	/**
	 * how high the alpha value is when we have finished fading in.
	 */
	private static final int FINAL_ALPHA = 225;

	/**
	 * title foreground color.
	 */
	private static Color titleFgColor;

	/**
	 * text foreground color.
	 */
	private static Color fgColor;

	/**
	 * shell gradient background color - top.
	 */
	private static Color bgFgGradient;

	/**
	 * shell gradient background color - bottom.
	 */
	private static Color bgBgGradient;

	/**
	 * shell border color.
	 */
	private static Color borderColor;

	/**
	 * contains list of all active popup shells.
	 */
	private static List<Shell> activeShells = new ArrayList<Shell>();

	/**
	 * image used when drawing.
	 */
	private static Image oldImage;

	/**
	 * The shell.
	 */
	private static Shell shell;

	/**
	 * The constructor.
	 */
	private NotificationDialogUtil() {
		// prevent instantiation
	}

	/**
	 * Creates and shows a notification dialog with a specific title and message.
	 * 
	 * @param title
	 *            The title of the notification.
	 * @param message
	 *            The message.
	 * @param type
	 *            The type of notification.
	 */
	public static void notify(String title, String message, NotificationType type) {
		notify(title, message, type, null, NotificationUtils.getDefaultPreferences());
	}

	/**
	 * Creates and shows a notification dialog with a specific title and message.
	 * 
	 * @param title
	 *            The title of the notification.
	 * @param message
	 *            The message.
	 * @param type
	 *            The type of notification.
	 * @param hyperlinkListener
	 *            The hyperlink listener for the message.
	 * @param preferences
	 *            The preferences used to configure the notification, see {@link INotificationConstants} and
	 *            {@link NotificationPreference}.
	 */
	public static void notify(String title, String message, NotificationType type,
			SelectionListener hyperlinkListener, IEclipsePreferences preferences) {
		initColor(preferences);

		shell = new Shell(Display.getDefault().getActiveShell(), SWT.NO_FOCUS | SWT.NO_TRIM);
		if (!shell.isDisposed()) {
			shell.setLayout(new FillLayout());
			shell.setForeground(fgColor);
			shell.setBackgroundMode(SWT.INHERIT_DEFAULT);
			shell.addListener(SWT.Dispose, new Listener() {
				public void handleEvent(Event event) {
					activeShells.remove(shell);
				}
			});
		}

		final Composite inner = new Composite(shell, SWT.NONE);
		GridLayout gl = new GridLayout(2, false);
		gl.marginLeft = 5;
		gl.marginTop = 0;
		gl.marginRight = 5;
		gl.marginBottom = 5;

		inner.setLayout(gl);
		if (!shell.isDisposed()) {
			shell.addListener(SWT.Resize, new Listener() {
				public void handleEvent(Event e) {
					NotificationDialogUtil.handleEventDelegate();
				}
			});
		}

		if (shell.isDisposed()) {
			return;
		}
		GC gc = new GC(shell);
		String[] lines = message.split("\n");
		Point longest = null;
		int typicalHeight = gc.stringExtent("W").y;

		for (String line : lines) {
			Point extent = gc.stringExtent(line);
			if (longest == null) {
				longest = extent;
				continue;
			}
			if (extent.x > longest.x) {
				longest = extent;
			}
		}
		gc.dispose();
		if (longest.y != typicalHeight) {
			typicalHeight = longest.y;
		}
		final int titleSize = 60;
		int minHeight = (typicalHeight * lines.length) + titleSize;
		CLabel imgLabel = new CLabel(inner, SWT.NONE);
		imgLabel.setLayoutData(new GridData(GridData.VERTICAL_ALIGN_BEGINNING
				| GridData.HORIZONTAL_ALIGN_BEGINNING));
		imgLabel.setImage(type.getImage());

		CLabel titleLabel = new CLabel(inner, SWT.NONE);
		titleLabel.setLayoutData(new GridData(GridData.FILL_HORIZONTAL | GridData.VERTICAL_ALIGN_CENTER));
		titleLabel.setText(title);
		titleLabel.setForeground(titleFgColor);
		Font f = titleLabel.getFont();
		FontData fd = f.getFontData()[0];
		fd.setStyle(SWT.BOLD);
		fd.height = 10;
		titleLabel.setFont(FontCache.getFont(fd));

		Link text = new Link(inner, SWT.WRAP);
		Font tf = text.getFont();
		FontData tfd = tf.getFontData()[0];
		tfd.setStyle(SWT.BOLD);
		text.setFont(FontCache.getFont(tfd));
		GridData gd = new GridData(GridData.FILL_BOTH);
		gd.horizontalSpan = 2;
		text.setLayoutData(gd);
		text.setForeground(fgColor);
		text.setText(message);
		if (hyperlinkListener != null) {
			text.addSelectionListener(hyperlinkListener);
		}

		if (!shell.isDisposed()) {
			shell.setSize(NotificationUtils.getNotificationWidth(preferences), minHeight);
		}

		fadeInAndOut(preferences, minHeight);
	}

	/**
	 * Fades in and out the notification.
	 * 
	 * @param preferences
	 *            The preferences.
	 * @param minHeight
	 *            The minimal height of the notification.
	 */
	private static void fadeInAndOut(IEclipsePreferences preferences, int minHeight) {
		if (Display.getDefault().getActiveShell() == null
				|| Display.getDefault().getActiveShell().getMonitor() == null) {
			return;
		}

		Rectangle clientArea = Display.getDefault().getActiveShell().getMonitor().getClientArea();
		int startX = clientArea.x + clientArea.width
				- (NotificationUtils.getNotificationWidth(preferences) + 2);
		int startY = clientArea.y + clientArea.height - (minHeight + 2);

		// move other shells up
		if (!activeShells.isEmpty()) {
			List<Shell> modifiable = new ArrayList<Shell>(activeShells);
			Collections.reverse(modifiable);
			for (Shell activeShell : modifiable) {
				if (!activeShell.isDisposed()) {
					Point curLoc = activeShell.getLocation();
					activeShell.setLocation(curLoc.x, curLoc.y - minHeight);
					if (curLoc.y - minHeight < 0) {
						activeShells.remove(activeShell);
						activeShell.dispose();
					}
				}
			}
		}
		if (!shell.isDisposed()) {
			shell.setLocation(startX, startY);
			shell.setAlpha(0);
			shell.setVisible(true);

			activeShells.add(shell);

			fadeIn(shell, preferences);
		}
	}

	/**
	 * Initialize the colors of the notification.
	 * 
	 * @param preferences
	 *            The eclipse preferences.
	 */
	private static void initColor(IEclipsePreferences preferences) {
		titleFgColor = ColorCache.getColorFromRGB(NotificationUtils.getNotificationTitleColor(preferences));
		bgFgGradient = ColorCache.getColorFromRGB(NotificationUtils
				.getNotificationBackgroundTopColor(preferences));
		bgBgGradient = ColorCache.getColorFromRGB(NotificationUtils
				.getNotificationBackgroundBottomColor(preferences));
		borderColor = ColorCache.getColorFromRGB(NotificationUtils.getNotificationBorderColor(preferences));
		fgColor = ColorCache.getColorFromRGB(NotificationUtils.getNotificationMessageColor(preferences));
	}

	/**
	 * Handle a new event.
	 */
	private static void handleEventDelegate() {
		// get the size of the drawing area
		Rectangle rect = shell.getClientArea();
		// create a new image with that size
		Image newImage = new Image(Display.getDefault(), Math.max(1, rect.width), rect.height);
		// create a GC object we can use to draw with
		GC gc = new GC(newImage);

		// fill background
		gc.setForeground(bgFgGradient);
		gc.setBackground(bgBgGradient);
		gc.fillGradientRectangle(rect.x, rect.y, rect.width, rect.height, true);

		// draw shell edge
		gc.setLineWidth(2);
		gc.setForeground(borderColor);
		gc.drawRectangle(rect.x + 1, rect.y + 1, rect.width - 2, rect.height - 2);
		// remember to dipose the GC object!
		gc.dispose();

		// now set the background image on the shell
		shell.setBackgroundImage(newImage);

		// remember/dispose old used iamge
		if (oldImage != null) {
			oldImage.dispose();
		}
		oldImage = newImage;
	}

	/**
	 * Fades in the notification.
	 * 
	 * @param aShell
	 *            The shell.
	 * @param preferences
	 *            The preferences.
	 */
	private static void fadeIn(final Shell aShell, final IEclipsePreferences preferences) {
		Runnable run = new Runnable() {
			public void run() {
				if (aShell == null || aShell.isDisposed()) {
					return;
				}

				int cur = aShell.getAlpha();
				cur += FADE_IN_STEP;

				if (cur > FINAL_ALPHA) {
					aShell.setAlpha(FINAL_ALPHA);
					if (NotificationUtils.getNotificationFadeOutAuto(preferences)) {
						startTimer(aShell, preferences);
					}
					return;
				}

				aShell.setAlpha(cur);
				Display.getDefault().timerExec(FADE_TIMER, this);
			}
		};
		Display.getDefault().timerExec(FADE_TIMER, run);
	}

	/**
	 * Starts the timer.
	 * 
	 * @param aShell
	 *            The shell.
	 * @param preferences
	 *            The preferences.
	 */
	private static void startTimer(final Shell aShell, final IEclipsePreferences preferences) {
		Runnable run = new Runnable() {
			public void run() {
				if (aShell == null || aShell.isDisposed()) {
					return;
				}
				fadeOut(aShell, preferences);
			}

		};
		Display.getDefault().timerExec(DISPLAY_TIME, run);
	}

	/**
	 * Fades out the notification.
	 * 
	 * @param aShell
	 *            The shell.
	 * @param preferences
	 *            The preferences
	 */
	private static void fadeOut(final Shell aShell, final IEclipsePreferences preferences) {
		Runnable run = new Runnable() {

			private int cur = aShell.getAlpha();

			public void run() {
				if (shell == null || shell.isDisposed()) {
					return;
				}
				cur -= FADE_OUT_STEP;

				if (cur <= 0) {
					shell.setAlpha(0);
					if (oldImage != null) {
						oldImage.dispose();
					}
					shell.dispose();
					activeShells.remove(shell);
					return;
				}
				shell.setAlpha(cur);
				Display.getDefault().timerExec(NotificationUtils.getNotificationFadeOutTimer(preferences),
						this);

			}
		};
		Display.getDefault().timerExec(NotificationUtils.getNotificationFadeOutTimer(preferences), run);
	}

}

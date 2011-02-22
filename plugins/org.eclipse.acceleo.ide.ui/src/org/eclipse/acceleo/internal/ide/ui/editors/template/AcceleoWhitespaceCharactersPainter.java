/*******************************************************************************
 * Copyright (c) 2006, 2010 Wind River Systems, Inc., IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Anton Leherbauer (Wind River Systems) - initial API and implementation - https://bugs.eclipse.org/bugs/show_bug.cgi?id=22712
 *     Anton Leherbauer (Wind River Systems) - [painting] Long lines take too long to display when "Show Whitespace Characters" is enabled - https://bugs.eclipse.org/bugs/show_bug.cgi?id=196116
 *     Anton Leherbauer (Wind River Systems) - [painting] Whitespace characters not drawn when scrolling to right slowly - https://bugs.eclipse.org/bugs/show_bug.cgi?id=206633
 *     Tom Eicher (Avaloq Evolution AG) - block selection mode
 *     Stephane Begaudeau (Obeo) - Acceleo whitespaces shouldn't be drawn out of String literals
 *******************************************************************************/
package org.eclipse.acceleo.internal.ide.ui.editors.template;

import org.eclipse.acceleo.ide.ui.AcceleoUIActivator;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.IPaintPositionManager;
import org.eclipse.jface.text.IPainter;
import org.eclipse.jface.text.IRegion;
import org.eclipse.jface.text.ITextViewer;
import org.eclipse.jface.text.ITextViewerExtension5;
import org.eclipse.ocl.ecore.StringLiteralExp;
import org.eclipse.ocl.utilities.ASTNode;
import org.eclipse.swt.custom.StyleRange;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.custom.StyledTextContent;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.FontMetrics;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Point;

/**
 * This painter will draw visible characters for the invisible characters in the Acceleo Editor. Created from
 * org.eclipse.jface.text.WhitespaceCharacterPainter.
 * 
 * @author <a href="mailto:stephane.begaudeau@obeo.fr">Stephane Begaudeau</a>
 */
public class AcceleoWhitespaceCharactersPainter implements IPainter, PaintListener {

	/**
	 * The space sign.
	 */
	private static final char SPACE_SIGN = '\u00b7';

	/**
	 * The ideographic space sign.
	 */
	private static final char IDEOGRAPHIC_SPACE_SIGN = '\u00b0';

	/**
	 * The tab sign.
	 */
	private static final char TAB_SIGN = '\u00bb';

	/**
	 * The carriage return sign.
	 */
	private static final char CARRIAGE_RETURN_SIGN = '\u00a4';

	/**
	 * The line feed sign.
	 */
	private static final char LINE_FEED_SIGN = '\u00b6';

	/**
	 * Indicates whether this painter is active.
	 **/
	private boolean fIsActive;

	/**
	 * The Acceleo Editor.
	 */
	private AcceleoEditor fEditor;

	/**
	 * The source viewer this painter is attached to.
	 **/
	private ITextViewer fTextViewer;

	/**
	 * The viewer's widget.
	 **/
	private StyledText fTextWidget;

	/**
	 * Tells whether the advanced graphics sub system is available.
	 **/
	private final boolean fIsAdvancedGraphicsPresent;

	/**
	 * The constructor.
	 * 
	 * @param editor
	 *            The acceleo editor
	 */
	public AcceleoWhitespaceCharactersPainter(AcceleoEditor editor) {
		super();
		this.fEditor = editor;
		this.fTextViewer = editor.getAcceleoSourceViewer();
		this.fTextWidget = this.fTextViewer.getTextWidget();
		GC gc = new GC(this.fTextWidget);
		gc.setAdvanced(true);
		this.fIsAdvancedGraphicsPresent = gc.getAdvanced();
		gc.dispose();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.swt.events.PaintListener#paintControl(org.eclipse.swt.events.PaintEvent)
	 */
	public void paintControl(PaintEvent event) {
		if (this.fTextWidget != null) {
			handleDrawRequest(event.gc, event.x, event.y, event.width, event.height);
		}
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.jface.text.IPainter#dispose()
	 */
	public void dispose() {
		fTextViewer = null;
		fTextWidget = null;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.jface.text.IPainter#paint(int)
	 */
	public void paint(int reason) {
		IDocument document = this.fTextViewer.getDocument();
		if (document == null) {
			deactivate(false);
			return;
		}
		if (!this.fIsActive) {
			this.fIsActive = true;
			this.fTextWidget.addPaintListener(this);
			redrawAll();
		} else if (reason == CONFIGURATION || reason == INTERNAL) {
			redrawAll();
		} else if (reason == TEXT_CHANGE) {
			// redraw current line only
			try {
				IRegion lineRegion = document.getLineInformationOfOffset(getDocumentOffset(this.fTextWidget
						.getCaretOffset()));
				int widgetOffset = getWidgetOffset(lineRegion.getOffset());
				int charCount = this.fTextWidget.getCharCount();
				int redrawLength = Math.min(lineRegion.getLength(), charCount - widgetOffset);
				if (widgetOffset >= 0 && redrawLength > 0) {
					this.fTextWidget.redrawRange(widgetOffset, redrawLength, true);
				}
			} catch (BadLocationException e) {
				// ignore
				AcceleoUIActivator.log(e, true);
			}
		}
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.jface.text.IPainter#deactivate(boolean)
	 */
	public void deactivate(boolean redraw) {
		if (this.fIsActive) {
			this.fIsActive = false;
			this.fTextWidget.removePaintListener(this);
			if (redraw) {
				redrawAll();
			}
		}
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.jface.text.IPainter#setPositionManager(org.eclipse.jface.text.IPaintPositionManager)
	 */
	public void setPositionManager(IPaintPositionManager manager) {
		// no need for a position manager
	}

	/**
	 * Draw characters in view range.
	 * 
	 * @param gc
	 *            The GC
	 * @param x
	 *            x
	 * @param y
	 *            y
	 * @param w
	 *            The width
	 * @param h
	 *            The height
	 */
	private void handleDrawRequest(GC gc, int x, int y, int w, int h) {
		int startLine = this.fTextWidget.getLineIndex(y);
		int endLine = this.fTextWidget.getLineIndex(y + h - 1);
		if (startLine <= endLine && startLine < this.fTextWidget.getLineCount()) {
			if (this.fIsAdvancedGraphicsPresent) {
				int alpha = gc.getAlpha();
				gc.setAlpha(100);
				drawLineRange(gc, startLine, endLine, x, w);
				gc.setAlpha(alpha);
			} else {
				drawLineRange(gc, startLine, endLine, x, w);
			}
		}
	}

	/**
	 * Draw the given line range.
	 * 
	 * @param gc
	 *            the GC
	 * @param startLine
	 *            first line number
	 * @param endLine
	 *            last line number (inclusive)
	 * @param x
	 *            the X-coordinate of the drawing range
	 * @param w
	 *            the width of the drawing range
	 */
	private void drawLineRange(GC gc, int startLine, int endLine, int x, int w) {
		final int viewPortWidth = fTextWidget.getClientArea().width;
		for (int line = startLine; line <= endLine; line++) {

			int lineOffset = fTextWidget.getOffsetAtLine(line);
			// line end offset including line delimiter
			int lineEndOffset;
			if (line < fTextWidget.getLineCount() - 1) {
				lineEndOffset = fTextWidget.getOffsetAtLine(line + 1);
			} else {
				lineEndOffset = fTextWidget.getCharCount();
			}
			// line length excluding line delimiter
			int lineLength = lineEndOffset - lineOffset;
			while (lineLength > 0) {
				char c = fTextWidget.getTextRange(lineOffset + lineLength - 1, 1).charAt(0);
				if (c != '\r' && c != '\n') {
					break;
				}
				--lineLength;
			}
			// compute coordinates of last character on line
			Point endOfLine = fTextWidget.getLocationAtOffset(lineOffset + lineLength);
			if (x - endOfLine.x > viewPortWidth) {
				// line is not visible
				continue;
			}
			// Y-coordinate of line
			int y = fTextWidget.getLinePixel(line);
			// compute first visible char offset
			int startOffset;
			try {
				startOffset = fTextWidget.getOffsetAtLocation(new Point(x, y)) - 1;
				if (startOffset - 2 <= lineOffset) {
					startOffset = lineOffset;
				}
			} catch (IllegalArgumentException iae) {
				// Indicates that there is no character at the given line, should not be logged.
				startOffset = lineOffset;
			}
			// compute last visible char offset
			int endOffset;
			if (x + w >= endOfLine.x) {
				// line end is visible
				endOffset = lineEndOffset;
			} else {
				try {
					endOffset = fTextWidget.getOffsetAtLocation(new Point(x + w - 1, y)) + 1;
					if (endOffset + 2 >= lineEndOffset) {
						endOffset = lineEndOffset;
					}
				} catch (IllegalArgumentException iae) {
					AcceleoUIActivator.log(iae, true);
					endOffset = lineEndOffset;
				}
			}
			// draw character range
			if (endOffset > startOffset) {
				drawCharRange(gc, startOffset, endOffset);
			}
		}
	}

	/**
	 * Draw characters of content range.
	 * 
	 * @param gc
	 *            the GC
	 * @param startOffset
	 *            inclusive start index
	 * @param endOffset
	 *            exclusive end index
	 */
	private void drawCharRange(GC gc, int startOffset, int endOffset) {
		StyledTextContent content = fTextWidget.getContent();
		int length = endOffset - startOffset;
		String text = content.getTextRange(startOffset, length);
		StringBuffer visibleChar = new StringBuffer(10);
		for (int textOffset = 0; textOffset <= length; ++textOffset) {
			int delta = 0;
			boolean eol = false;
			if (textOffset < length) {
				delta = 1;
				char c = text.charAt(textOffset);

				boolean shouldDraw = this.shouldDraw(startOffset + textOffset);

				if (shouldDraw) {
					switch (c) {
						case ' ':
							visibleChar.append(SPACE_SIGN);
							// 'continue' would improve performance but may produce drawing errors
							// for long runs of space if width of space and dot differ
							break;
						case '\u3000': // ideographic whitespace
							visibleChar.append(IDEOGRAPHIC_SPACE_SIGN);
							// 'continue' would improve performance but may produce drawing errors
							// for long runs of space if width of space and dot differ
							break;
						case '\t':
							visibleChar.append(TAB_SIGN);
							break;
						case '\r':
							visibleChar.append(CARRIAGE_RETURN_SIGN);
							if (textOffset >= length - 1 || text.charAt(textOffset + 1) != '\n') {
								eol = true;
								break;
							}
							continue;
						case '\n':
							visibleChar.append(LINE_FEED_SIGN);
							eol = true;
							break;
						default:
							delta = 0;
							break;
					}
				}
			}
			if (visibleChar.length() > 0) {
				int widgetOffset = startOffset + textOffset - visibleChar.length() + delta;
				if (!eol || !isFoldedLine(content.getLineAtOffset(widgetOffset))) {
					this.drawCharacter(widgetOffset, gc, visibleChar);
				}
				visibleChar.delete(0, visibleChar.length());
			}
		}
	}

	/**
	 * Draw the visible char at the given widget offset.
	 * 
	 * @param widgetOffset
	 *            The widget offset
	 * @param gc
	 *            The GC
	 * @param visibleChar
	 *            The visible char to draw
	 */
	private void drawCharacter(int widgetOffset, GC gc, StringBuffer visibleChar) {
		Color fg = null;
		StyleRange styleRange = null;

		/*
		 * Block selection is drawn using alpha and no selection-inverting takes place, we always draw as
		 * 'unselected' in block selection mode.
		 */
		// Acceleo : compatibility with 3.4 breaks when using this, commenting out
		if (/* !fTextWidget.getBlockSelection() && */isOffsetSelected(fTextWidget, widgetOffset)) {
			fg = fTextWidget.getSelectionForeground();
		} else {
			styleRange = fTextWidget.getStyleRangeAtOffset(widgetOffset);
			if (styleRange == null || styleRange.foreground == null) {
				fg = fTextWidget.getForeground();
			} else {
				fg = styleRange.foreground;
			}
		}
		draw(gc, widgetOffset, visibleChar.toString(), fg);
	}

	/**
	 * Indicates if we should draw the character at the given offset.
	 * 
	 * @param offset
	 *            The given offset
	 * @return true if we should draw it, false otherwise
	 */
	private boolean shouldDraw(int offset) {
		ASTNode astNode = this.fEditor.getContent().getASTNode(offset, offset);
		return astNode instanceof StringLiteralExp;
	}

	/**
	 * Returns <code>true</code> if <code>offset</code> is selection in <code>widget</code>,
	 * <code>false</code> otherwise.
	 * 
	 * @param widget
	 *            the widget
	 * @param offset
	 *            the offset
	 * @return <code>true</code> if <code>offset</code> is selection, <code>false</code> otherwise
	 * @since 3.5
	 */
	private static boolean isOffsetSelected(StyledText widget, int offset) {
		Point selection = widget.getSelection();
		return offset >= selection.x && offset < selection.y;
	}

	/**
	 * Check if the given widget line is a folded line.
	 * 
	 * @param widgetLine
	 *            the widget line number
	 * @return <code>true</code> if the line is folded
	 */
	private boolean isFoldedLine(int widgetLine) {
		if (fTextViewer instanceof ITextViewerExtension5) {
			ITextViewerExtension5 extension = (ITextViewerExtension5)fTextViewer;
			int modelLine = extension.widgetLine2ModelLine(widgetLine);
			int widgetLine2 = extension.modelLine2WidgetLine(modelLine + 1);
			return widgetLine2 == -1;
		}
		return false;
	}

	/**
	 * Redraw all of the text widgets visible content.
	 */
	private void redrawAll() {
		fTextWidget.redraw();
	}

	/**
	 * Draw string at widget offset.
	 * 
	 * @param gc
	 *            the GC
	 * @param offset
	 *            the widget offset
	 * @param s
	 *            the string to be drawn
	 * @param fg
	 *            the foreground color
	 */
	private void draw(GC gc, int offset, String s, Color fg) {
		// Compute baseline delta (see https://bugs.eclipse.org/bugs/show_bug.cgi?id=165640)
		int baseline = fTextWidget.getBaseline(offset);
		FontMetrics fontMetrics = gc.getFontMetrics();
		int fontBaseline = fontMetrics.getAscent() + fontMetrics.getLeading();
		int baslineDelta = baseline - fontBaseline;

		Point pos = fTextWidget.getLocationAtOffset(offset);
		gc.setForeground(fg);
		gc.drawString(s, pos.x, pos.y + baslineDelta, true);
	}

	/**
	 * Convert a document offset to the corresponding widget offset.
	 * 
	 * @param documentOffset
	 *            the document offset
	 * @return widget offset
	 */
	private int getWidgetOffset(int documentOffset) {
		int result = -1;
		if (fTextViewer instanceof ITextViewerExtension5) {
			ITextViewerExtension5 extension = (ITextViewerExtension5)fTextViewer;
			result = extension.modelOffset2WidgetOffset(documentOffset);
		} else {
			IRegion visible = fTextViewer.getVisibleRegion();
			int widgetOffset = documentOffset - visible.getOffset();
			if (widgetOffset > visible.getLength()) {
				result = -1;
			} else {
				result = widgetOffset;
			}
		}
		return result;
	}

	/**
	 * Convert a widget offset to the corresponding document offset.
	 * 
	 * @param widgetOffset
	 *            the widget offset
	 * @return document offset
	 */
	private int getDocumentOffset(int widgetOffset) {
		int result = -1;
		if (fTextViewer instanceof ITextViewerExtension5) {
			ITextViewerExtension5 extension = (ITextViewerExtension5)fTextViewer;
			result = extension.widgetOffset2ModelOffset(widgetOffset);
		} else {
			IRegion visible = fTextViewer.getVisibleRegion();
			if (widgetOffset > visible.getLength()) {
				result = -1;
			} else {
				result = widgetOffset + visible.getOffset();
			}
		}
		return result;
	}
}

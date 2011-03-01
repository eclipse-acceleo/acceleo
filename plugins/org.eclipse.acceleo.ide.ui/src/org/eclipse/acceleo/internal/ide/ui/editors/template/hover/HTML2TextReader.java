/*******************************************************************************
 * Copyright (c) 2000, 2010 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *     Obeo - minor modifications for checkstyle
 *******************************************************************************/
package org.eclipse.acceleo.internal.ide.ui.editors.template.hover;

import java.io.IOException;
import java.io.PushbackReader;
import java.io.Reader;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.eclipse.acceleo.common.utils.CompactHashSet;
import org.eclipse.acceleo.ide.ui.AcceleoUIActivator;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.text.TextPresentation;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.StyleRange;

/**
 * Reads the text contents from a reader of HTML contents and translates the tags or cut them out.
 * <p>
 * Moved into this package from <code>org.eclipse.jface.internal.text.revisions</code>.
 * </p>
 * Copied from org.eclipse.jface.internal.text.html.HTML2TextReader.
 * <p>
 * Checkstyle needs an author, and since somebody wrote this class...
 * </p>
 * 
 * @author <a href="mailto:some.body@ibm.com">Some Body</a>
 */
public class HTML2TextReader extends AbstractSubstitutionTextReader {

	/**
	 * The empty string.
	 */
	private static final String EMPTY_STRING = ""; //$NON-NLS-1$

	/**
	 * The map of entity to lookup.
	 */
	private static final Map<String, String> ENTITY_LOOKUP;

	/**
	 * The list of all tags supported.
	 */
	private static final Set<String> TAGS;

	static {

		TAGS = new CompactHashSet<String>();
		TAGS.add("b"); //$NON-NLS-1$
		TAGS.add("br"); //$NON-NLS-1$
		TAGS.add("br/"); //$NON-NLS-1$
		TAGS.add("div"); //$NON-NLS-1$
		TAGS.add("h1"); //$NON-NLS-1$
		TAGS.add("h2"); //$NON-NLS-1$
		TAGS.add("h3"); //$NON-NLS-1$
		TAGS.add("h4"); //$NON-NLS-1$
		TAGS.add("h5"); //$NON-NLS-1$
		TAGS.add("p"); //$NON-NLS-1$
		TAGS.add("dl"); //$NON-NLS-1$
		TAGS.add("dt"); //$NON-NLS-1$
		TAGS.add("dd"); //$NON-NLS-1$
		TAGS.add("li"); //$NON-NLS-1$
		TAGS.add("ul"); //$NON-NLS-1$
		TAGS.add("pre"); //$NON-NLS-1$
		TAGS.add("head"); //$NON-NLS-1$

		final int seven = 7;
		ENTITY_LOOKUP = new HashMap<String, String>(seven);
		ENTITY_LOOKUP.put("lt", "<"); //$NON-NLS-1$ //$NON-NLS-2$
		ENTITY_LOOKUP.put("gt", ">"); //$NON-NLS-1$ //$NON-NLS-2$
		ENTITY_LOOKUP.put("nbsp", " "); //$NON-NLS-1$ //$NON-NLS-2$
		ENTITY_LOOKUP.put("amp", "&"); //$NON-NLS-1$ //$NON-NLS-2$
		ENTITY_LOOKUP.put("circ", "^"); //$NON-NLS-1$ //$NON-NLS-2$
		ENTITY_LOOKUP.put("tilde", "\u223C"); //$NON-NLS-2$ //$NON-NLS-1$
		ENTITY_LOOKUP.put("quot", "\""); //$NON-NLS-1$ //$NON-NLS-2$
	}

	/**
	 * The counter.
	 */
	private int fCounter;

	/**
	 * The text presentation.
	 */
	private TextPresentation fTextPresentation;

	/**
	 * The bold.
	 */
	private int fBold;

	/**
	 * The starting offset.
	 */
	private int fStartOffset = -1;

	/**
	 * Indicates if it is in a paragraph.
	 */
	private boolean fInParagraph;

	/**
	 * Indicates if it is in preformatted text.
	 */
	private boolean fIsPreformattedText;

	/**
	 * Indicates if we ignore it.
	 */
	private boolean fIgnore;

	/**
	 * Indicates if we have detected a header.
	 */
	private boolean fHeaderDetected;

	/**
	 * Transforms the HTML text from the reader to formatted text.
	 * 
	 * @param reader
	 *            the reader
	 * @param presentation
	 *            If not <code>null</code>, formattings will be applied to the presentation.
	 */
	public HTML2TextReader(Reader reader, TextPresentation presentation) {
		super(new PushbackReader(reader));
		fTextPresentation = presentation;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.acceleo.internal.ide.ui.editors.template.hover.AbstractSubstitutionTextReader#read()
	 */
	@Override
	public int read() throws IOException {
		int c = super.read();
		if (c != -1) {
			++fCounter;
		}
		return c;
	}

	/**
	 * Starts recording the bold area.
	 */
	protected void startBold() {
		if (fBold == 0) {
			fStartOffset = fCounter;
		}
		++fBold;
	}

	/**
	 * Starts the recording of the preformatted text.
	 */
	protected void startPreformattedText() {
		fIsPreformattedText = true;
		setSkipWhitespace(false);
	}

	/**
	 * Stops the recording of the preformatted text.
	 */
	protected void stopPreformattedText() {
		fIsPreformattedText = false;
		setSkipWhitespace(true);
	}

	/**
	 * Stops the recording of the bold area.
	 */
	protected void stopBold() {
		--fBold;
		if (fBold == 0) {
			if (fTextPresentation != null) {
				fTextPresentation.addStyleRange(new StyleRange(fStartOffset, fCounter - fStartOffset, null,
						null, SWT.BOLD));
			}
			fStartOffset = -1;
		}
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.acceleo.internal.ide.ui.editors.template.hover.AbstractSubstitutionTextReader#computeSubstitution(int)
	 */
	@Override
	protected String computeSubstitution(int c) throws IOException {
		String result = null;

		if (c == '<') {
			result = processHTMLTag();
		} else if (fIgnore) {
			result = EMPTY_STRING;
		} else if (c == '&') {
			result = processEntity();
		} else if (fIsPreformattedText) {
			result = processPreformattedText(c);
		}

		return result;
	}

	/**
	 * Transform the html in a simple text.
	 * 
	 * @param htmlText
	 *            The html text.
	 * @return The text
	 */
	// CHECKSTYLE:OFF too many 'return', too lazy to care
	private String html2Text(String htmlText) {
		String html = htmlText;

		if (html == null || html.length() == 0) {
			return EMPTY_STRING;
		}

		html = html.toLowerCase();

		String tag = html;
		if ('/' == tag.charAt(0)) {
			tag = tag.substring(1);
		}

		if (!TAGS.contains(tag)) {
			return EMPTY_STRING;
		}

		if ("pre".equals(html)) { //$NON-NLS-1$
			startPreformattedText();
			return EMPTY_STRING;
		}

		if ("/pre".equals(html)) { //$NON-NLS-1$
			stopPreformattedText();
			return EMPTY_STRING;
		}

		if (fIsPreformattedText) {
			return EMPTY_STRING;
		}

		if ("b".equals(html)) { //$NON-NLS-1$
			startBold();
			return EMPTY_STRING;
		}

		if ((html.length() > 1 && html.charAt(0) == 'h' && Character.isDigit(html.charAt(1)))
				|| "dt".equals(html)) { //$NON-NLS-1$
			startBold();
			return EMPTY_STRING;
		}

		if ("dl".equals(html)) { //$NON-NLS-1$
			return LINE_DELIM;
		}

		if ("dd".equals(html)) { //$NON-NLS-1$
			return "\t"; //$NON-NLS-1$
		}

		if ("li".equals(html)) { //$NON-NLS-1$
			// FIXME: this hard-coded prefix does not work for RTL languages, see
			// https://bugs.eclipse.org/bugs/show_bug.cgi?id=91682
			return LINE_DELIM + "\t"; //$NON-NLS-1$
		}

		if ("/b".equals(html)) { //$NON-NLS-1$
			stopBold();
			return EMPTY_STRING;
		}

		if ("p".equals(html)) { //$NON-NLS-1$
			fInParagraph = true;
			return LINE_DELIM;
		}

		if ("br".equals(html) || "br/".equals(html) || "div".equals(html)) { //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
			return LINE_DELIM;
		}

		if ("/p".equals(html)) { //$NON-NLS-1$
			boolean inParagraph = fInParagraph;
			fInParagraph = false;
			return inParagraph ? EMPTY_STRING : LINE_DELIM;
		}

		if ((html.startsWith("/h") && html.length() > 2 && Character.isDigit(html.charAt(2))) || "/dt".equals(html)) { //$NON-NLS-1$ //$NON-NLS-2$
			stopBold();
			return LINE_DELIM;
		}

		if ("/dd".equals(html)) { //$NON-NLS-1$
			return LINE_DELIM;
		}

		if ("head".equals(html) && !fHeaderDetected) { //$NON-NLS-1$
			fHeaderDetected = true;
			fIgnore = true;
			return EMPTY_STRING;
		}

		if ("/head".equals(html) && fHeaderDetected && fIgnore) { //$NON-NLS-1$
			fIgnore = false;
			return EMPTY_STRING;
		}

		return EMPTY_STRING;
	}

	// CHECKSTYLE:ON

/**
	 * A '<' has been read. Process a html tag
	 * @return The html tag.
	 * @throws IOException In case of problems
	 */
	// CHECKSTYLE:OFF too many 'return', too lazy to care
	private String processHTMLTag() throws IOException {

		StringBuffer buf = new StringBuffer();
		int ch;
		do {

			ch = nextChar();

			while (ch != -1 && ch != '>') {
				buf.append(Character.toLowerCase((char)ch));
				ch = nextChar();
				if (ch == '"') {
					buf.append(Character.toLowerCase((char)ch));
					ch = nextChar();
					while (ch != -1 && ch != '"') {
						buf.append(Character.toLowerCase((char)ch));
						ch = nextChar();
					}
				}
				if (ch == '<' && !isInComment(buf)) {
					unread(ch);
					return '<' + buf.toString();
				}
			}

			if (ch == -1) {
				return null;
			}

			if (!isInComment(buf) || isCommentEnd(buf)) {
				break;
			}
			// unfinished comment
			buf.append((char)ch);
		} while (true);

		return html2Text(buf.toString());
	}

	// CHECKSTYLE:ON

	/**
	 * Indicates if it is in a comment.
	 * 
	 * @param buf
	 *            The string buffer
	 * @return true if it is in a comment
	 */
	private static boolean isInComment(StringBuffer buf) {
		return buf.length() >= 3 && "!--".equals(buf.substring(0, 3)); //$NON-NLS-1$
	}

	/**
	 * Indicates if it is a comment end.
	 * 
	 * @param buf
	 *            The string buffer
	 * @return true if it is a comment end
	 */
	private static boolean isCommentEnd(StringBuffer buf) {
		int tagLen = buf.length();
		return tagLen >= 5 && "--".equals(buf.substring(tagLen - 2)); //$NON-NLS-1$
	}

	/**
	 * Processes the preformatted text.
	 * 
	 * @param c
	 *            The given char
	 * @return null
	 */
	private String processPreformattedText(int c) {
		if (c == '\r' || c == '\n') {
			fCounter++;
		}
		return null;
	}

	/**
	 * Unread the last character.
	 * 
	 * @param ch
	 *            The char
	 * @throws IOException
	 *             In case of problems
	 */
	private void unread(int ch) throws IOException {
		((PushbackReader)getReader()).unread(ch);
	}

	/**
	 * Transform the entity into a text.
	 * 
	 * @param symbol
	 *            The symbol
	 * @return the text
	 */
	// CHECKSTYLE:OFF too many 'return', too lazy to care
	protected String entity2Text(String symbol) {
		if (symbol.length() > 1 && symbol.charAt(0) == '#') {
			int ch;
			try {
				if (symbol.charAt(1) == 'x') {
					final int sixteen = 16;
					ch = Integer.parseInt(symbol.substring(2), sixteen);
				} else {
					ch = Integer.parseInt(symbol.substring(1), 10);
				}
				return EMPTY_STRING + (char)ch;
			} catch (NumberFormatException e) {
				// do nothing
				AcceleoUIActivator.getDefault().getLog().log(
						new Status(IStatus.ERROR, AcceleoUIActivator.PLUGIN_ID, e.getMessage(), e));
			}
		} else {
			String str = ENTITY_LOOKUP.get(symbol);
			if (str != null) {
				return str;
			}
		}
		return "&" + symbol; // not found //$NON-NLS-1$
	}

	// CHECKSTYLE:ON

	/**
	 * A '&' has been read. Process a entity.
	 * 
	 * @return The text of the entity.
	 * @throws IOException
	 *             In case of problems
	 */
	private String processEntity() throws IOException {
		StringBuffer buf = new StringBuffer();
		int ch = nextChar();
		while (Character.isLetterOrDigit((char)ch) || ch == '#') {
			buf.append((char)ch);
			ch = nextChar();
		}

		if (ch == ';') {
			return entity2Text(buf.toString());
		}

		buf.insert(0, '&');
		if (ch != -1) {
			buf.append((char)ch);
		}
		return buf.toString();
	}
}

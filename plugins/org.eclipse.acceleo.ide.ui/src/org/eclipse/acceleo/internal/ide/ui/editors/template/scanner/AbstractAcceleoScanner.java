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
package org.eclipse.acceleo.internal.ide.ui.editors.template.scanner;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.acceleo.ide.ui.AcceleoUIActivator;
import org.eclipse.acceleo.internal.ide.ui.editors.template.color.AcceleoColor;
import org.eclipse.acceleo.internal.ide.ui.editors.template.color.AcceleoColorManager;
import org.eclipse.core.runtime.preferences.DefaultScope;
import org.eclipse.core.runtime.preferences.IEclipsePreferences;
import org.eclipse.core.runtime.preferences.InstanceScope;
import org.eclipse.jface.text.TextAttribute;
import org.eclipse.jface.text.rules.IPredicateRule;
import org.eclipse.jface.text.rules.IRule;
import org.eclipse.jface.text.rules.IToken;
import org.eclipse.jface.text.rules.RuleBasedScanner;
import org.eclipse.swt.graphics.Color;

/**
 * A rule based scanner. All the scanners of the template editor should extend this class.
 * 
 * @author <a href="mailto:jonathan.musset@obeo.fr">Jonathan Musset</a>
 */
public abstract class AbstractAcceleoScanner extends RuleBasedScanner {
	/** This will determine the order in which we'll look preferences up. */
	private final IEclipsePreferences[] lookupOrder;

	/** Keeps track of the rules used by this scanner. */
	private IRule[] rules;

	/** Keeps track of the default token used by this scanner. */
	private IToken defaultToken;

	/**
	 * Instantiates our scanner given the order into which we should seek the colors for syntax coloring.
	 * 
	 * @param lookupOrder
	 *            This will determine the order in which we'll look preferences up. Can be <code>null</code>.
	 */
	public AbstractAcceleoScanner(IEclipsePreferences[] lookupOrder) {
		if (lookupOrder != null && lookupOrder.length > 0) {
			this.lookupOrder = lookupOrder;
		} else {
			IEclipsePreferences defaultScope = new DefaultScope().getNode(AcceleoUIActivator.PLUGIN_ID);
			IEclipsePreferences instanceScope = new InstanceScope().getNode(AcceleoUIActivator.PLUGIN_ID);
			this.lookupOrder = new IEclipsePreferences[] {instanceScope, defaultScope, };
		}
		createRules();
	}

	/**
	 * Returns the preference lookup order for this scanner.
	 * 
	 * @return The preference lookup order for this scanner.
	 */
	protected IEclipsePreferences[] getLookupOrder() {
		return lookupOrder;
	}

	/**
	 * Returns the SWT {@link Color} corresponding to the given {@link AcceleoColor} in the Acceleo UI
	 * preferences.
	 * 
	 * @param color
	 *            Color constant we seek an SWT color for.
	 * @return The SWT {@link Color} corresponding to the given {@link AcceleoColor} in the Acceleo UI
	 *         preferences.
	 */
	protected Color getColor(AcceleoColor color) {
		return AcceleoColorManager.getColor(color, getLookupOrder());
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.jface.text.rules.RuleBasedScanner#setRules(org.eclipse.jface.text.rules.IRule[])
	 */
	@Override
	public void setRules(IRule[] rules) {
		super.setRules(rules);
		this.rules = rules;
	}

	@Override
	public void setDefaultReturnToken(IToken defaultReturnToken) {
		super.setDefaultReturnToken(defaultReturnToken);
		this.defaultToken = defaultReturnToken;
	}

	/**
	 * Creates a token with the given foreground.
	 * 
	 * @param foreground
	 *            Foreground color of the newly created token.
	 * @return The newly created token.
	 */
	protected AcceleoToken createToken(AcceleoColor foreground) {
		Color swtForeground = getColor(foreground);

		TextAttribute tokenData = new TextAttribute(swtForeground);
		return new AcceleoToken(tokenData, foreground.getPreferenceKey());
	}

	/**
	 * Creates a token with the given colors and style.
	 * 
	 * @param foreground
	 *            Foreground color of the newly created token.
	 * @param background
	 *            Background color of the newly created token.
	 * @param style
	 *            Style of the newly created token.
	 * @return The newly created token.
	 */
	protected AcceleoToken createToken(AcceleoColor foreground, AcceleoColor background, int style) {
		Color swtForeground = getColor(foreground);

		Color swtBackground = null;
		if (background != null) {
			swtBackground = getColor(background);
		}

		TextAttribute tokenData = new TextAttribute(swtForeground, swtBackground, style);
		return new AcceleoToken(tokenData, foreground.getPreferenceKey());
	}

	/** This will be called to initialize this scanner's rules. */
	protected abstract void createRules();

	/**
	 * Returns the type of the tokens read by this scanner.
	 * 
	 * @return the type of the tokens
	 */
	public abstract String getConfiguredContentType();

	/**
	 * This will be used to check whether this scanner is affected by a change to the given preference, and
	 * retrieve the affected tokens.
	 * 
	 * @param preferenceKey
	 *            Key of the preference we need to react to.
	 * @return The affected tokens if any, an empty list otherwise.
	 */
	public List<AcceleoToken> getAffectedToken(String preferenceKey) {
		List<AcceleoToken> affectedTokens = new ArrayList<AcceleoToken>();
		if (preferenceKey == null || "".equals(preferenceKey)) { //$NON-NLS-1$
			return affectedTokens;
		}

		for (IRule rule : rules) {
			if (rule instanceof IPredicateRule) {
				IToken token = ((IPredicateRule)rule).getSuccessToken();
				if (token instanceof AcceleoToken) {
					String tokenKey = ((AcceleoToken)token).getColorKey();
					if (tokenKey != null && tokenKey.equals(preferenceKey)) {
						affectedTokens.add((AcceleoToken)token);
					}
				}
			}
		}

		if (this.defaultToken instanceof AcceleoToken) {
			String tokenKey = ((AcceleoToken)this.defaultToken).getColorKey();
			if (tokenKey != null && tokenKey.equals(preferenceKey)) {
				affectedTokens.add((AcceleoToken)this.defaultToken);
			}
		}

		return affectedTokens;
	}
}

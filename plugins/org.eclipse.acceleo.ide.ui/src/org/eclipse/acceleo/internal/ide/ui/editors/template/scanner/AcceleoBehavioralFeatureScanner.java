/*******************************************************************************
 * Copyright (c) 2008, 2009 Obeo.
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

import org.eclipse.acceleo.common.IAcceleoConstants;
import org.eclipse.acceleo.internal.ide.ui.editors.template.ColorManager;
import org.eclipse.acceleo.internal.ide.ui.editors.template.rules.FirstVariableRule;
import org.eclipse.acceleo.internal.ide.ui.editors.template.rules.KeywordRule;
import org.eclipse.acceleo.internal.ide.ui.editors.template.rules.SequenceBlockRule;
import org.eclipse.jface.text.TextAttribute;
import org.eclipse.jface.text.rules.IRule;
import org.eclipse.jface.text.rules.Token;
import org.eclipse.jface.text.rules.WhitespaceRule;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;

/**
 * A scanner for detecting 'template', 'query' or 'macro' sequences.
 * 
 * @author <a href="mailto:jonathan.musset@obeo.fr">Jonathan Musset</a>
 */
public class AcceleoBehavioralFeatureScanner extends AbstractAcceleoScanner {

	/**
	 * Constructor.
	 * 
	 * @param manager
	 *            is the color manager
	 */
	public AcceleoBehavioralFeatureScanner(ColorManager manager) {
		List<IRule> rules = new ArrayList<IRule>();
		rules.add(new SequenceBlockRule(new KeywordRule(IAcceleoConstants.LITERAL_BEGIN), new KeywordRule(
				IAcceleoConstants.LITERAL_END), new KeywordRule(IAcceleoConstants.LITERAL_ESCAPE), new Token(
				new TextAttribute(manager.getColor(IAcceleoColorConstants.LITERAL)))));
		computeFirstVariableRule(rules, IAcceleoConstants.TEMPLATE, manager);
		computeFirstVariableRule(rules, IAcceleoConstants.QUERY, manager);
		computeFirstVariableRule(rules, IAcceleoConstants.MACRO, manager);
		rules.add(new WhitespaceRule(new AcceleoWhitespaceDetector()));
		setRules(rules.toArray(new IRule[rules.size()]));
		setDefaultReturnToken(new Token(new TextAttribute(manager
				.getColor(IAcceleoColorConstants.BEHAVIORAL_FEATURE), null, SWT.BOLD)));
	}

	/**
	 * Creates the rule for the first variable highlighting.
	 * 
	 * @param rules
	 *            is a list of rules (output parameter)
	 * @param behaviorType
	 *            is the name of the behavioral feature : 'template', 'query', or 'macro'
	 * @param manager
	 *            is the color manager
	 */
	private void computeFirstVariableRule(List<IRule> rules, String behaviorType, ColorManager manager) {
		final Color foreGroundColor = manager.getColor(IAcceleoColorConstants.BEHAVIORAL_FEATURE);
		final Color backGroundColor = manager.getColor(IAcceleoColorConstants.FIRST_VARIABLE);
		final String unknown = "*"; //$NON-NLS-1$
		rules.add(new FirstVariableRule(new String[] {IAcceleoConstants.DEFAULT_BEGIN, behaviorType, unknown,
				unknown, IAcceleoConstants.PARENTHESIS_BEGIN,}, new Token(new TextAttribute(foreGroundColor,
				backGroundColor, SWT.BOLD))));
		rules.add(new FirstVariableRule(new String[] {IAcceleoConstants.DEFAULT_BEGIN, behaviorType, unknown,
				IAcceleoConstants.PARENTHESIS_BEGIN,}, new Token(new TextAttribute(foreGroundColor,
				backGroundColor, SWT.BOLD))));
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.acceleo.ide.ui.editors.template.scanner.AbstractAcceleoScanner#getConfiguredContentType()
	 */
	@Override
	public String getConfiguredContentType() {
		return AcceleoPartitionScanner.ACCELEO_BEHAVIORAL_FEATURE;
	}

}

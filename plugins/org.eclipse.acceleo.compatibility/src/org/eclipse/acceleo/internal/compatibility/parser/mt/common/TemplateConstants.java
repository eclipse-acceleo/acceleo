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
package org.eclipse.acceleo.internal.compatibility.parser.mt.common;

import org.eclipse.acceleo.internal.compatibility.AcceleoCompatibilityMessages;

/**
 * Constants for the generation tool.
 * 
 * @author <a href="mailto:yvan.lussaud@obeo.fr">Yvan Lussaud</a>
 */
public final class TemplateConstants {

	/**
	 * The single instance.
	 */
	private static TemplateConstants instance;

	// Template's global constants.
	/** String representing spec. */
	private String spec = ""; //$NON-NLS-1$

	// Template's user code.
	/** Default tag used to start the user code. */
	private String defaultUserBegin = AcceleoCompatibilityMessages
			.getString("TemplateConstants.UserCodeStart"); //$NON-NLS-1$

	/** Default tag used to stop the user code. */
	private String defaultUserEnd = AcceleoCompatibilityMessages.getString("TemplateConstants.UserCodeEnd"); //$NON-NLS-1$

	// Template's literals.
	/** Boolean literal : true. */
	private String literalTrue = "true"; //$NON-NLS-1$

	/** Boolean literal : false. */
	private String literalFalse = "false"; //$NON-NLS-1$

	/** Language literal : null. */
	private String literalNull = "null"; //$NON-NLS-1$

	// Template's comments.
	/** String representing comment begin. */
	private String commentBegin;

	/** String representing comment end. */
	private String commentEnd;

	/** Strings array containing comment begin and comment end. */
	private String[] comment;

	/** Strings matrix of comments constants. */
	private String[][] inhibsComment;

	// Template's imports.
	/** String representing import begin. */
	private String importBegin;

	/** String representing import end. */
	private String importEnd;

	/** String representing import keyword. */
	private String importWord = "import"; //$NON-NLS-1$

	/** String representing model type keyword. */
	private String modelTypeWord = "metamodel"; //$NON-NLS-1$

	// Template's user code.
	/** String representing start user section keyword. */
	private String userBeginName = "startUserCode"; //$NON-NLS-1$

	/** String representing start user section tag. */
	private String userBegin;

	/** String representing end user section keyword. */
	private String userEndName = "endUserCode"; //$NON-NLS-1$

	/** String representing end user section tag. */
	private String userEnd;

	// Template's expressions.
	/** String array representing parenthesis begin and end. */
	private String[] parenth;

	/** Strings array representing brackets begin and end. */
	private String[] brackets;

	/** Strings array representing literals. */
	private String[] literal;

	/** Strings representing literal spec. */
	private String literalSpec;

	/** String matrix of expressions constants. */
	private String[][] inhibsExpression;

	/** String representing not operator. */
	private String not = "!"; //$NON-NLS-1$

	/** String representing OR operator. */
	private String operatorOr = "||"; //$NON-NLS-1$

	/** String representing AND operator. */
	private String operatorAnd = "&&"; //$NON-NLS-1$

	/** String representing EQUALS operator. */
	private String operatorEquals = "=="; //$NON-NLS-1$

	/** String representing not EQUALS operator. */
	private String operatorNotEquals = "!="; //$NON-NLS-1$

	/** String representing SUP EQUALS operator. */
	private String operatorSupEquals = ">="; //$NON-NLS-1$

	/** String representing INF EQUALS operator. */
	private String operatorInfEquals = "<="; //$NON-NLS-1$

	/** String representing SUP operator. */
	private String operatorSup = ">"; //$NON-NLS-1$

	/** String representing INF operator. */
	private String operatorInf = "<"; //$NON-NLS-1$

	/** String representing ADD operator. */
	private String operatorAdd = "+"; //$NON-NLS-1$

	/** String representing SUB operator. */
	private String operatorSub = "-"; //$NON-NLS-1$

	/** String representing DIV operator. */
	private String operatorDiv = "/"; //$NON-NLS-1$

	/** String representing MUL operator. */
	private String operatorMul = "*"; //$NON-NLS-1$

	/** Strings array containing operators representation. */
	private String[] operators;

	/** String representing call separator. */
	private String callSep;

	/** String representing argument separator. */
	private String argSep;

	// Template's statements.
	/** String representing if beginning. */
	private String ifBegin;

	/** String representing then declaration. */
	private String ifThen;

	/** String representing else declaration. */
	private String ifElse;

	/** String representing else if declaration. */
	private String ifElseIf;

	/** String representing end declaration. */
	private String ifEnd;

	/** Strings array with if begin and if end. */
	private String[] ifStatement;

	/** String representing for begin. */
	private String forBegin;

	/** String representing for then. */
	private String forThen;

	/** String representing for end. */
	private String forEnd;

	/** Strings array with for begin and for end. */
	private String[] forStatement;

	/** String representing feature begin. */
	private String featureBegin;

	/** String representing feature end. */
	private String featureEnd;

	/** Strings array with feature begin and feature end. */
	private String[] featureStatement;

	/** Strings matrix of statements constants. */
	private String[][] inhibsStatement;

	// Template's declaration line.

	/** String representing script begin. */
	private String scriptBegin;

	/** String representing script type keyword. */
	private String scriptType = "type"; //$NON-NLS-1$

	/** String representing script name keyword. */
	private String scriptName = "name"; //$NON-NLS-1$

	/** String representing script desc keyword. */
	private String scriptDesc = "description"; //$NON-NLS-1$

	/** String representing script file keyword. */
	private String scriptFile = "file"; //$NON-NLS-1$

	/** String representing script post keyword. */
	private String scriptPost = "post"; //$NON-NLS-1$

	/** String representing script property assignment operator. */
	private String scriptPropertyAssignment = "="; //$NON-NLS-1$

	/** String representing script end. */
	private String scriptEnd;

	/** Strings array containing properties separators. */
	private String[] scriptPropertiesSeparator;

	/** Strings matrix of scripts declaration constants. */
	private String[][] inhibsScriptDecla;

	/** Strings matrix of script content constants. */
	private String[][] inhibsScriptContent;

	// Link prefix
	/** String representing script keyword. */
	private String linkPrefixScript = "script"; //$NON-NLS-1$

	/** String representing metamodel keyword. */
	private String linkPrefixMetamodel = "metamodel"; //$NON-NLS-1$

	/** String representing short metamodel keyword. */
	private String linkPrefixMetamodelShort = "m"; //$NON-NLS-1$

	/** String representing service keyword. */
	private String linkPrefixService = "service"; //$NON-NLS-1$

	/** String representing separator keyword. */
	private String linkPrefixSeparator = "::"; //$NON-NLS-1$

	// Predefined links
	/** String representing args keyword. */
	private String linkNameArgs = "args"; //$NON-NLS-1$

	/** String representing i keyword. */
	private String linkNameIndex = "i"; //$NON-NLS-1$

	/** String representing sep keyword. */
	private String serviceSep = "sep"; //$NON-NLS-1$

	/** String representing sepStr keyword. */
	private String serviceSepStr = "sepStr"; //$NON-NLS-1$

	/** String representing first char. */
	private Character lastFirstChar;

	/**
	 * Private Constructor. Utility class should not have default or public constructor.
	 */
	private TemplateConstants() {
		// nothing to do here
	}

	/**
	 * Gets the single instance.
	 * 
	 * @return the single instance
	 */
	public static TemplateConstants getDefault() {
		if (instance == null) {
			instance = new TemplateConstants();
		}
		return instance;
	}

	/**
	 * Initializes all the constants for the generation tool.
	 */
	public void initConstants() {
		initConstants(""); //$NON-NLS-1$
	}

	/**
	 * Initializes all the constants for the generation tool.
	 * 
	 * @param content
	 *            is the text that is used to determine the first and the last character of the tags
	 */
	public void initConstants(String content) {
		String localContent;
		// Choose tag
		if (content == null) {
			localContent = ""; //$NON-NLS-1$
		} else {
			localContent = content.trim();
		}
		boolean tab = localContent.length() > 0 && localContent.charAt(0) == '[';
		char first;
		char last;
		if (tab) {
			first = '[';
			last = ']';
		} else {
			first = '<';
			last = '>';
		}

		if (lastFirstChar != null && first == lastFirstChar.charValue()) {
			return;
		}
		lastFirstChar = Character.valueOf(first);

		// contains String equal to first + "%" and "%" + last, avoid too much
		// concatenations later on
		final String tagStart = Character.toString(first) + '%';
		final String tagEnd = '%' + Character.toString(last);

		// Update constants
		setSpec("\\\""); //$NON-NLS-1$
		setCommentBegin(tagStart + "--"); //$NON-NLS-1$
		setCommentEnd("--" + tagEnd); //$NON-NLS-1$
		setComment(new String[] {getCommentBegin(), getCommentEnd(), TextSearch.FORCE_NOT_RECURSIVE });
		setInhibsComment(new String[][] {getComment() });
		setImportBegin(tagStart);
		setImportEnd(tagEnd);
		setUserBegin(tagStart + getUserBeginName() + tagEnd);
		setUserEnd(tagStart + getUserEndName() + tagEnd);
		setParenth(new String[] {"(", ")" }); //$NON-NLS-1$ //$NON-NLS-2$
		setBrackets(new String[] {"[", "]" }); //$NON-NLS-1$ //$NON-NLS-2$
		setLiteral(new String[] {"\"", "\"", TextSearch.FORCE_NOT_RECURSIVE }); //$NON-NLS-1$ //$NON-NLS-2$
		setLiteralSpec("\\\""); //$NON-NLS-1$
		setInhibsExpression(new String[][] {getParenth(), getLiteral(), getBrackets() });
		setOperators(new String[] {getOperatorOr(), getOperatorAnd(), getOperatorEquals(),
				getOperatorNotEquals(), getOperatorSupEquals(), getOperatorInfEquals(), getOperatorSup(),
				getOperatorInf(), getOperatorAdd(), getOperatorSub(), getOperatorDiv(), getOperatorMul(), });
		setCallSep("."); //$NON-NLS-1$
		setArgSep(","); //$NON-NLS-1$
		setIfBegin(tagStart + "if "); //$NON-NLS-1$
		setIfThen('{' + tagEnd);
		setIfElse(tagStart + "}else{" + tagEnd); //$NON-NLS-1$
		setIfElseIf(tagStart + "}else if"); //$NON-NLS-1$
		setIfEnd(tagStart + '}' + tagEnd);
		setIfStatement(new String[] {getIfBegin(), getIfEnd() });
		setForBegin(tagStart + "for "); //$NON-NLS-1$
		setForThen('{' + tagEnd);
		setForEnd(tagStart + '}' + tagEnd);
		setForStatement(new String[] {getForBegin(), getForEnd() });
		setFeatureBegin(tagStart);
		setFeatureEnd(tagEnd);
		setFeatureStatement(new String[] {getFeatureBegin(), getFeatureEnd() });
		setInhibsStatement(new String[][] {getComment(), getIfStatement(), getForStatement(),
				getFeatureStatement(), });
		setScriptBegin(tagStart + "script "); //$NON-NLS-1$
		setScriptEnd(tagEnd);
		setScriptPropertiesSeparator(new String[] {getScriptPropertyAssignment(), " ", "\t" }); //$NON-NLS-1$ //$NON-NLS-2$
		setInhibsScriptDecla(new String[][] {getLiteral() });
		setInhibsScriptContent(new String[][] {getComment(), getFeatureStatement() });
	}

	private void setSpec(String spec) {
		this.spec = spec;
	}

	public String getSpec() {
		return spec;
	}

	public String getDefaultUserBegin() {
		return defaultUserBegin;
	}

	public String getDefaultUserEnd() {
		return defaultUserEnd;
	}

	public String getLiteraltrue() {
		return literalTrue;
	}

	public String getLiteralfalse() {
		return literalFalse;
	}

	public String getLiteralnull() {
		return literalNull;
	}

	private void setCommentBegin(String commentBegin) {
		this.commentBegin = commentBegin;
	}

	public String getCommentBegin() {
		return commentBegin;
	}

	private void setCommentEnd(String commentEnd) {
		this.commentEnd = commentEnd;
	}

	public String getCommentEnd() {
		return commentEnd;
	}

	private void setComment(String[] comment) {
		this.comment = comment;
	}

	public String[] getComment() {
		return comment;
	}

	private void setInhibsComment(String[][] inhibsComment) {
		this.inhibsComment = inhibsComment;
	}

	public String[][] getInhibsComment() {
		return inhibsComment;
	}

	private void setImportBegin(String importBegin) {
		this.importBegin = importBegin;
	}

	public String getImportBegin() {
		return importBegin;
	}

	private void setImportEnd(String importEnd) {
		this.importEnd = importEnd;
	}

	public String getImportEnd() {
		return importEnd;
	}

	public String getImportWord() {
		return importWord;
	}

	public String getModelTypeWord() {
		return modelTypeWord;
	}

	public String getUserBeginName() {
		return userBeginName;
	}

	private void setUserBegin(String userBegin) {
		this.userBegin = userBegin;
	}

	public String getUserBegin() {
		return userBegin;
	}

	public String getUserEndName() {
		return userEndName;
	}

	private void setUserEnd(String userEnd) {
		this.userEnd = userEnd;
	}

	public String getUserEnd() {
		return userEnd;
	}

	private void setParenth(String[] parenth) {
		this.parenth = parenth;
	}

	public String[] getParenth() {
		return parenth;
	}

	private void setBrackets(String[] brackets) {
		this.brackets = brackets;
	}

	public String[] getBrackets() {
		return brackets;
	}

	private void setLiteral(String[] literal) {
		this.literal = literal;
	}

	public String[] getLiteral() {
		return literal;
	}

	private void setLiteralSpec(String literalSpec) {
		this.literalSpec = literalSpec;
	}

	public String getLiteralSpec() {
		return literalSpec;
	}

	private void setInhibsExpression(String[][] inhibsExpression) {
		this.inhibsExpression = inhibsExpression;
	}

	public String[][] getInhibsExpression() {
		return inhibsExpression;
	}

	public String getNot() {
		return not;
	}

	public String getOperatorOr() {
		return operatorOr;
	}

	public String getOperatorAnd() {
		return operatorAnd;
	}

	public String getOperatorEquals() {
		return operatorEquals;
	}

	public String getOperatorNotEquals() {
		return operatorNotEquals;
	}

	public String getOperatorSupEquals() {
		return operatorSupEquals;
	}

	public String getOperatorInfEquals() {
		return operatorInfEquals;
	}

	public String getOperatorSup() {
		return operatorSup;
	}

	public String getOperatorInf() {
		return operatorInf;
	}

	public String getOperatorAdd() {
		return operatorAdd;
	}

	public String getOperatorSub() {
		return operatorSub;
	}

	public String getOperatorDiv() {
		return operatorDiv;
	}

	public String getOperatorMul() {
		return operatorMul;
	}

	private void setOperators(String[] operators) {
		this.operators = operators;
	}

	public String[] getOperators() {
		return operators;
	}

	private void setCallSep(String callSep) {
		this.callSep = callSep;
	}

	public String getCallSep() {
		return callSep;
	}

	private void setArgSep(String argSep) {
		this.argSep = argSep;
	}

	public String getArgSep() {
		return argSep;
	}

	private void setIfBegin(String ifBegin) {
		this.ifBegin = ifBegin;
	}

	public String getIfBegin() {
		return ifBegin;
	}

	private void setIfThen(String ifThen) {
		this.ifThen = ifThen;
	}

	public String getIfThen() {
		return ifThen;
	}

	private void setIfElse(String ifElse) {
		this.ifElse = ifElse;
	}

	public String getIfElse() {
		return ifElse;
	}

	private void setIfElseIf(String ifElseIf) {
		this.ifElseIf = ifElseIf;
	}

	public String getIfElseIf() {
		return ifElseIf;
	}

	private void setIfEnd(String ifEnd) {
		this.ifEnd = ifEnd;
	}

	public String getIfEnd() {
		return ifEnd;
	}

	private void setIfStatement(String[] ifStatement) {
		this.ifStatement = ifStatement;
	}

	public String[] getIfStatement() {
		return ifStatement;
	}

	private void setForBegin(String forBegin) {
		this.forBegin = forBegin;
	}

	public String getForBegin() {
		return forBegin;
	}

	private void setForThen(String forThen) {
		this.forThen = forThen;
	}

	public String getForThen() {
		return forThen;
	}

	private void setForEnd(String forEnd) {
		this.forEnd = forEnd;
	}

	public String getForEnd() {
		return forEnd;
	}

	private void setForStatement(String[] forStatement) {
		this.forStatement = forStatement;
	}

	public String[] getForStatement() {
		return forStatement;
	}

	private void setFeatureBegin(String featureBegin) {
		this.featureBegin = featureBegin;
	}

	public String getFeatureBegin() {
		return featureBegin;
	}

	private void setFeatureEnd(String featureEnd) {
		this.featureEnd = featureEnd;
	}

	public String getFeatureEnd() {
		return featureEnd;
	}

	private void setFeatureStatement(String[] featureStatement) {
		this.featureStatement = featureStatement;
	}

	public String[] getFeatureStatement() {
		return featureStatement;
	}

	private void setInhibsStatement(String[][] inhibsStatement) {
		this.inhibsStatement = inhibsStatement;
	}

	public String[][] getInhibsStatement() {
		return inhibsStatement;
	}

	private void setScriptBegin(String scriptBegin) {
		this.scriptBegin = scriptBegin;
	}

	public String getScriptBegin() {
		return scriptBegin;
	}

	public String getScriptType() {
		return scriptType;
	}

	public String getScriptName() {
		return scriptName;
	}

	public String getScriptDesc() {
		return scriptDesc;
	}

	public String getScriptFile() {
		return scriptFile;
	}

	public String getScriptPost() {
		return scriptPost;
	}

	public String getScriptPropertyAssignment() {
		return scriptPropertyAssignment;
	}

	private void setScriptEnd(String scriptEnd) {
		this.scriptEnd = scriptEnd;
	}

	public String getScriptEnd() {
		return scriptEnd;
	}

	private void setScriptPropertiesSeparator(String[] scriptPropertiesSeparator) {
		this.scriptPropertiesSeparator = scriptPropertiesSeparator;
	}

	public String[] getScriptPropertiesSeparator() {
		return scriptPropertiesSeparator;
	}

	private void setInhibsScriptDecla(String[][] inhibsScriptDecla) {
		this.inhibsScriptDecla = inhibsScriptDecla;
	}

	public String[][] getInhibsScriptDecla() {
		return inhibsScriptDecla;
	}

	private void setInhibsScriptContent(String[][] inhibsScriptContent) {
		this.inhibsScriptContent = inhibsScriptContent;
	}

	public String[][] getInhibsScriptContent() {
		return inhibsScriptContent;
	}

	public String getLinkPrefixScript() {
		return linkPrefixScript;
	}

	public String getLinkPrefixMetamodel() {
		return linkPrefixMetamodel;
	}

	public String getLinkprefixMetamodelShort() {
		return linkPrefixMetamodelShort;
	}

	public String getLinkPrefixService() {
		return linkPrefixService;
	}

	public String getLinkPrefixSeparator() {
		return linkPrefixSeparator;
	}

	public String getLinkNameArgs() {
		return linkNameArgs;
	}

	public String getLinkNameIndex() {
		return linkNameIndex;
	}

	public String getServiceSep() {
		return serviceSep;
	}

	public String getServiceSepStr() {
		return serviceSepStr;
	}

}

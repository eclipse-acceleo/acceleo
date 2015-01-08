// Generated from Query.g4 by ANTLR 4.2.2

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
package org.eclipse.acceleo.query.parser;

import org.antlr.v4.runtime.misc.NotNull;
import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by {@link QueryParser}.
 */
public interface QueryListener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by {@link QueryParser#EnumOrClassifierLit}.
	 * 
	 * @param ctx
	 *            the parse tree
	 */
	void enterEnumOrClassifierLit(@NotNull QueryParser.EnumOrClassifierLitContext ctx);

	/**
	 * Exit a parse tree produced by {@link QueryParser#EnumOrClassifierLit}.
	 * 
	 * @param ctx
	 *            the parse tree
	 */
	void exitEnumOrClassifierLit(@NotNull QueryParser.EnumOrClassifierLitContext ctx);

	/**
	 * Enter a parse tree produced by {@link QueryParser#varRef}.
	 * 
	 * @param ctx
	 *            the parse tree
	 */
	void enterVarRef(@NotNull QueryParser.VarRefContext ctx);

	/**
	 * Exit a parse tree produced by {@link QueryParser#varRef}.
	 * 
	 * @param ctx
	 *            the parse tree
	 */
	void exitVarRef(@NotNull QueryParser.VarRefContext ctx);

	/**
	 * Enter a parse tree produced by {@link QueryParser#SeqLit}.
	 * 
	 * @param ctx
	 *            the parse tree
	 */
	void enterSeqLit(@NotNull QueryParser.SeqLitContext ctx);

	/**
	 * Exit a parse tree produced by {@link QueryParser#SeqLit}.
	 * 
	 * @param ctx
	 *            the parse tree
	 */
	void exitSeqLit(@NotNull QueryParser.SeqLitContext ctx);

	/**
	 * Enter a parse tree produced by {@link QueryParser#addOp}.
	 * 
	 * @param ctx
	 *            the parse tree
	 */
	void enterAddOp(@NotNull QueryParser.AddOpContext ctx);

	/**
	 * Exit a parse tree produced by {@link QueryParser#addOp}.
	 * 
	 * @param ctx
	 *            the parse tree
	 */
	void exitAddOp(@NotNull QueryParser.AddOpContext ctx);

	/**
	 * Enter a parse tree produced by {@link QueryParser#Feature}.
	 * 
	 * @param ctx
	 *            the parse tree
	 */
	void enterFeature(@NotNull QueryParser.FeatureContext ctx);

	/**
	 * Exit a parse tree produced by {@link QueryParser#Feature}.
	 * 
	 * @param ctx
	 *            the parse tree
	 */
	void exitFeature(@NotNull QueryParser.FeatureContext ctx);

	/**
	 * Enter a parse tree produced by {@link QueryParser#EContent}.
	 * 
	 * @param ctx
	 *            the parse tree
	 */
	void enterEContent(@NotNull QueryParser.EContentContext ctx);

	/**
	 * Exit a parse tree produced by {@link QueryParser#EContent}.
	 * 
	 * @param ctx
	 *            the parse tree
	 */
	void exitEContent(@NotNull QueryParser.EContentContext ctx);

	/**
	 * Enter a parse tree produced by {@link QueryParser#expressionSequence}.
	 * 
	 * @param ctx
	 *            the parse tree
	 */
	void enterExpressionSequence(@NotNull QueryParser.ExpressionSequenceContext ctx);

	/**
	 * Exit a parse tree produced by {@link QueryParser#expressionSequence}.
	 * 
	 * @param ctx
	 *            the parse tree
	 */
	void exitExpressionSequence(@NotNull QueryParser.ExpressionSequenceContext ctx);

	/**
	 * Enter a parse tree produced by {@link QueryParser#IntType}.
	 * 
	 * @param ctx
	 *            the parse tree
	 */
	void enterIntType(@NotNull QueryParser.IntTypeContext ctx);

	/**
	 * Exit a parse tree produced by {@link QueryParser#IntType}.
	 * 
	 * @param ctx
	 *            the parse tree
	 */
	void exitIntType(@NotNull QueryParser.IntTypeContext ctx);

	/**
	 * Enter a parse tree produced by {@link QueryParser#Lit}.
	 * 
	 * @param ctx
	 *            the parse tree
	 */
	void enterLit(@NotNull QueryParser.LitContext ctx);

	/**
	 * Exit a parse tree produced by {@link QueryParser#Lit}.
	 * 
	 * @param ctx
	 *            the parse tree
	 */
	void exitLit(@NotNull QueryParser.LitContext ctx);

	/**
	 * Enter a parse tree produced by {@link QueryParser#Add}.
	 * 
	 * @param ctx
	 *            the parse tree
	 */
	void enterAdd(@NotNull QueryParser.AddContext ctx);

	/**
	 * Exit a parse tree produced by {@link QueryParser#Add}.
	 * 
	 * @param ctx
	 *            the parse tree
	 */
	void exitAdd(@NotNull QueryParser.AddContext ctx);

	/**
	 * Enter a parse tree produced by {@link QueryParser#Siblings}.
	 * 
	 * @param ctx
	 *            the parse tree
	 */
	void enterSiblings(@NotNull QueryParser.SiblingsContext ctx);

	/**
	 * Exit a parse tree produced by {@link QueryParser#Siblings}.
	 * 
	 * @param ctx
	 *            the parse tree
	 */
	void exitSiblings(@NotNull QueryParser.SiblingsContext ctx);

	/**
	 * Enter a parse tree produced by {@link QueryParser#SetLit}.
	 * 
	 * @param ctx
	 *            the parse tree
	 */
	void enterSetLit(@NotNull QueryParser.SetLitContext ctx);

	/**
	 * Exit a parse tree produced by {@link QueryParser#SetLit}.
	 * 
	 * @param ctx
	 *            the parse tree
	 */
	void exitSetLit(@NotNull QueryParser.SetLitContext ctx);

	/**
	 * Enter a parse tree produced by {@link QueryParser#Min}.
	 * 
	 * @param ctx
	 *            the parse tree
	 */
	void enterMin(@NotNull QueryParser.MinContext ctx);

	/**
	 * Exit a parse tree produced by {@link QueryParser#Min}.
	 * 
	 * @param ctx
	 *            the parse tree
	 */
	void exitMin(@NotNull QueryParser.MinContext ctx);

	/**
	 * Enter a parse tree produced by {@link QueryParser#ModelObjectType}.
	 * 
	 * @param ctx
	 *            the parse tree
	 */
	void enterModelObjectType(@NotNull QueryParser.ModelObjectTypeContext ctx);

	/**
	 * Exit a parse tree produced by {@link QueryParser#ModelObjectType}.
	 * 
	 * @param ctx
	 *            the parse tree
	 */
	void exitModelObjectType(@NotNull QueryParser.ModelObjectTypeContext ctx);

	/**
	 * Enter a parse tree produced by {@link QueryParser#RealType}.
	 * 
	 * @param ctx
	 *            the parse tree
	 */
	void enterRealType(@NotNull QueryParser.RealTypeContext ctx);

	/**
	 * Exit a parse tree produced by {@link QueryParser#RealType}.
	 * 
	 * @param ctx
	 *            the parse tree
	 */
	void exitRealType(@NotNull QueryParser.RealTypeContext ctx);

	/**
	 * Enter a parse tree produced by {@link QueryParser#EContainer}.
	 * 
	 * @param ctx
	 *            the parse tree
	 */
	void enterEContainer(@NotNull QueryParser.EContainerContext ctx);

	/**
	 * Exit a parse tree produced by {@link QueryParser#EContainer}.
	 * 
	 * @param ctx
	 *            the parse tree
	 */
	void exitEContainer(@NotNull QueryParser.EContainerContext ctx);

	/**
	 * Enter a parse tree produced by {@link QueryParser#TrueLit}.
	 * 
	 * @param ctx
	 *            the parse tree
	 */
	void enterTrueLit(@NotNull QueryParser.TrueLitContext ctx);

	/**
	 * Exit a parse tree produced by {@link QueryParser#TrueLit}.
	 * 
	 * @param ctx
	 *            the parse tree
	 */
	void exitTrueLit(@NotNull QueryParser.TrueLitContext ctx);

	/**
	 * Enter a parse tree produced by {@link QueryParser#Paren}.
	 * 
	 * @param ctx
	 *            the parse tree
	 */
	void enterParen(@NotNull QueryParser.ParenContext ctx);

	/**
	 * Exit a parse tree produced by {@link QueryParser#Paren}.
	 * 
	 * @param ctx
	 *            the parse tree
	 */
	void exitParen(@NotNull QueryParser.ParenContext ctx);

	/**
	 * Enter a parse tree produced by {@link QueryParser#IsKind}.
	 * 
	 * @param ctx
	 *            the parse tree
	 */
	void enterIsKind(@NotNull QueryParser.IsKindContext ctx);

	/**
	 * Exit a parse tree produced by {@link QueryParser#IsKind}.
	 * 
	 * @param ctx
	 *            the parse tree
	 */
	void exitIsKind(@NotNull QueryParser.IsKindContext ctx);

	/**
	 * Enter a parse tree produced by {@link QueryParser#SetType}.
	 * 
	 * @param ctx
	 *            the parse tree
	 */
	void enterSetType(@NotNull QueryParser.SetTypeContext ctx);

	/**
	 * Exit a parse tree produced by {@link QueryParser#SetType}.
	 * 
	 * @param ctx
	 *            the parse tree
	 */
	void exitSetType(@NotNull QueryParser.SetTypeContext ctx);

	/**
	 * Enter a parse tree produced by {@link QueryParser#IterationCall}.
	 * 
	 * @param ctx
	 *            the parse tree
	 */
	void enterIterationCall(@NotNull QueryParser.IterationCallContext ctx);

	/**
	 * Exit a parse tree produced by {@link QueryParser#IterationCall}.
	 * 
	 * @param ctx
	 *            the parse tree
	 */
	void exitIterationCall(@NotNull QueryParser.IterationCallContext ctx);

	/**
	 * Enter a parse tree produced by {@link QueryParser#RealLit}.
	 * 
	 * @param ctx
	 *            the parse tree
	 */
	void enterRealLit(@NotNull QueryParser.RealLitContext ctx);

	/**
	 * Exit a parse tree produced by {@link QueryParser#RealLit}.
	 * 
	 * @param ctx
	 *            the parse tree
	 */
	void exitRealLit(@NotNull QueryParser.RealLitContext ctx);

	/**
	 * Enter a parse tree produced by {@link QueryParser#StringLit}.
	 * 
	 * @param ctx
	 *            the parse tree
	 */
	void enterStringLit(@NotNull QueryParser.StringLitContext ctx);

	/**
	 * Exit a parse tree produced by {@link QueryParser#StringLit}.
	 * 
	 * @param ctx
	 *            the parse tree
	 */
	void exitStringLit(@NotNull QueryParser.StringLitContext ctx);

	/**
	 * Enter a parse tree produced by {@link QueryParser#Filter}.
	 * 
	 * @param ctx
	 *            the parse tree
	 */
	void enterFilter(@NotNull QueryParser.FilterContext ctx);

	/**
	 * Exit a parse tree produced by {@link QueryParser#Filter}.
	 * 
	 * @param ctx
	 *            the parse tree
	 */
	void exitFilter(@NotNull QueryParser.FilterContext ctx);

	/**
	 * Enter a parse tree produced by {@link QueryParser#StrType}.
	 * 
	 * @param ctx
	 *            the parse tree
	 */
	void enterStrType(@NotNull QueryParser.StrTypeContext ctx);

	/**
	 * Exit a parse tree produced by {@link QueryParser#StrType}.
	 * 
	 * @param ctx
	 *            the parse tree
	 */
	void exitStrType(@NotNull QueryParser.StrTypeContext ctx);

	/**
	 * Enter a parse tree produced by {@link QueryParser#ExplicitSeqLit}.
	 * 
	 * @param ctx
	 *            the parse tree
	 */
	void enterExplicitSeqLit(@NotNull QueryParser.ExplicitSeqLitContext ctx);

	/**
	 * Exit a parse tree produced by {@link QueryParser#ExplicitSeqLit}.
	 * 
	 * @param ctx
	 *            the parse tree
	 */
	void exitExplicitSeqLit(@NotNull QueryParser.ExplicitSeqLitContext ctx);

	/**
	 * Enter a parse tree produced by {@link QueryParser#Or}.
	 * 
	 * @param ctx
	 *            the parse tree
	 */
	void enterOr(@NotNull QueryParser.OrContext ctx);

	/**
	 * Exit a parse tree produced by {@link QueryParser#Or}.
	 * 
	 * @param ctx
	 *            the parse tree
	 */
	void exitOr(@NotNull QueryParser.OrContext ctx);

	/**
	 * Enter a parse tree produced by {@link QueryParser#AsType}.
	 * 
	 * @param ctx
	 *            the parse tree
	 */
	void enterAsType(@NotNull QueryParser.AsTypeContext ctx);

	/**
	 * Exit a parse tree produced by {@link QueryParser#AsType}.
	 * 
	 * @param ctx
	 *            the parse tree
	 */
	void exitAsType(@NotNull QueryParser.AsTypeContext ctx);

	/**
	 * Enter a parse tree produced by {@link QueryParser#EAContent}.
	 * 
	 * @param ctx
	 *            the parse tree
	 */
	void enterEAContent(@NotNull QueryParser.EAContentContext ctx);

	/**
	 * Exit a parse tree produced by {@link QueryParser#EAContent}.
	 * 
	 * @param ctx
	 *            the parse tree
	 */
	void exitEAContent(@NotNull QueryParser.EAContentContext ctx);

	/**
	 * Enter a parse tree produced by {@link QueryParser#BooleanType}.
	 * 
	 * @param ctx
	 *            the parse tree
	 */
	void enterBooleanType(@NotNull QueryParser.BooleanTypeContext ctx);

	/**
	 * Exit a parse tree produced by {@link QueryParser#BooleanType}.
	 * 
	 * @param ctx
	 *            the parse tree
	 */
	void exitBooleanType(@NotNull QueryParser.BooleanTypeContext ctx);

	/**
	 * Enter a parse tree produced by {@link QueryParser#And}.
	 * 
	 * @param ctx
	 *            the parse tree
	 */
	void enterAnd(@NotNull QueryParser.AndContext ctx);

	/**
	 * Exit a parse tree produced by {@link QueryParser#And}.
	 * 
	 * @param ctx
	 *            the parse tree
	 */
	void exitAnd(@NotNull QueryParser.AndContext ctx);

	/**
	 * Enter a parse tree produced by {@link QueryParser#PrecSiblings}.
	 * 
	 * @param ctx
	 *            the parse tree
	 */
	void enterPrecSiblings(@NotNull QueryParser.PrecSiblingsContext ctx);

	/**
	 * Exit a parse tree produced by {@link QueryParser#PrecSiblings}.
	 * 
	 * @param ctx
	 *            the parse tree
	 */
	void exitPrecSiblings(@NotNull QueryParser.PrecSiblingsContext ctx);

	/**
	 * Enter a parse tree produced by {@link QueryParser#compOp}.
	 * 
	 * @param ctx
	 *            the parse tree
	 */
	void enterCompOp(@NotNull QueryParser.CompOpContext ctx);

	/**
	 * Exit a parse tree produced by {@link QueryParser#compOp}.
	 * 
	 * @param ctx
	 *            the parse tree
	 */
	void exitCompOp(@NotNull QueryParser.CompOpContext ctx);

	/**
	 * Enter a parse tree produced by {@link QueryParser#As}.
	 * 
	 * @param ctx
	 *            the parse tree
	 */
	void enterAs(@NotNull QueryParser.AsContext ctx);

	/**
	 * Exit a parse tree produced by {@link QueryParser#As}.
	 * 
	 * @param ctx
	 *            the parse tree
	 */
	void exitAs(@NotNull QueryParser.AsContext ctx);

	/**
	 * Enter a parse tree produced by {@link QueryParser#ExplicitSetLit}.
	 * 
	 * @param ctx
	 *            the parse tree
	 */
	void enterExplicitSetLit(@NotNull QueryParser.ExplicitSetLitContext ctx);

	/**
	 * Exit a parse tree produced by {@link QueryParser#ExplicitSetLit}.
	 * 
	 * @param ctx
	 *            the parse tree
	 */
	void exitExplicitSetLit(@NotNull QueryParser.ExplicitSetLitContext ctx);

	/**
	 * Enter a parse tree produced by {@link QueryParser#Is}.
	 * 
	 * @param ctx
	 *            the parse tree
	 */
	void enterIs(@NotNull QueryParser.IsContext ctx);

	/**
	 * Exit a parse tree produced by {@link QueryParser#Is}.
	 * 
	 * @param ctx
	 *            the parse tree
	 */
	void exitIs(@NotNull QueryParser.IsContext ctx);

	/**
	 * Enter a parse tree produced by {@link QueryParser#EInverse}.
	 * 
	 * @param ctx
	 *            the parse tree
	 */
	void enterEInverse(@NotNull QueryParser.EInverseContext ctx);

	/**
	 * Exit a parse tree produced by {@link QueryParser#EInverse}.
	 * 
	 * @param ctx
	 *            the parse tree
	 */
	void exitEInverse(@NotNull QueryParser.EInverseContext ctx);

	/**
	 * Enter a parse tree produced by {@link QueryParser#CallService}.
	 * 
	 * @param ctx
	 *            the parse tree
	 */
	void enterCallService(@NotNull QueryParser.CallServiceContext ctx);

	/**
	 * Exit a parse tree produced by {@link QueryParser#CallService}.
	 * 
	 * @param ctx
	 *            the parse tree
	 */
	void exitCallService(@NotNull QueryParser.CallServiceContext ctx);

	/**
	 * Enter a parse tree produced by {@link QueryParser#ServiceCall}.
	 * 
	 * @param ctx
	 *            the parse tree
	 */
	void enterServiceCall(@NotNull QueryParser.ServiceCallContext ctx);

	/**
	 * Exit a parse tree produced by {@link QueryParser#ServiceCall}.
	 * 
	 * @param ctx
	 *            the parse tree
	 */
	void exitServiceCall(@NotNull QueryParser.ServiceCallContext ctx);

	/**
	 * Enter a parse tree produced by {@link QueryParser#IsType}.
	 * 
	 * @param ctx
	 *            the parse tree
	 */
	void enterIsType(@NotNull QueryParser.IsTypeContext ctx);

	/**
	 * Exit a parse tree produced by {@link QueryParser#IsType}.
	 * 
	 * @param ctx
	 *            the parse tree
	 */
	void exitIsType(@NotNull QueryParser.IsTypeContext ctx);

	/**
	 * Enter a parse tree produced by {@link QueryParser#Apply}.
	 * 
	 * @param ctx
	 *            the parse tree
	 */
	void enterApply(@NotNull QueryParser.ApplyContext ctx);

	/**
	 * Exit a parse tree produced by {@link QueryParser#Apply}.
	 * 
	 * @param ctx
	 *            the parse tree
	 */
	void exitApply(@NotNull QueryParser.ApplyContext ctx);

	/**
	 * Enter a parse tree produced by {@link QueryParser#FalseLit}.
	 * 
	 * @param ctx
	 *            the parse tree
	 */
	void enterFalseLit(@NotNull QueryParser.FalseLitContext ctx);

	/**
	 * Exit a parse tree produced by {@link QueryParser#FalseLit}.
	 * 
	 * @param ctx
	 *            the parse tree
	 */
	void exitFalseLit(@NotNull QueryParser.FalseLitContext ctx);

	/**
	 * Enter a parse tree produced by {@link QueryParser#Mult}.
	 * 
	 * @param ctx
	 *            the parse tree
	 */
	void enterMult(@NotNull QueryParser.MultContext ctx);

	/**
	 * Exit a parse tree produced by {@link QueryParser#Mult}.
	 * 
	 * @param ctx
	 *            the parse tree
	 */
	void exitMult(@NotNull QueryParser.MultContext ctx);

	/**
	 * Enter a parse tree produced by {@link QueryParser#variableDefinition}.
	 * 
	 * @param ctx
	 *            the parse tree
	 */
	void enterVariableDefinition(@NotNull QueryParser.VariableDefinitionContext ctx);

	/**
	 * Exit a parse tree produced by {@link QueryParser#variableDefinition}.
	 * 
	 * @param ctx
	 *            the parse tree
	 */
	void exitVariableDefinition(@NotNull QueryParser.VariableDefinitionContext ctx);

	/**
	 * Enter a parse tree produced by {@link QueryParser#Nav}.
	 * 
	 * @param ctx
	 *            the parse tree
	 */
	void enterNav(@NotNull QueryParser.NavContext ctx);

	/**
	 * Exit a parse tree produced by {@link QueryParser#Nav}.
	 * 
	 * @param ctx
	 *            the parse tree
	 */
	void exitNav(@NotNull QueryParser.NavContext ctx);

	/**
	 * Enter a parse tree produced by {@link QueryParser#NullLit}.
	 * 
	 * @param ctx
	 *            the parse tree
	 */
	void enterNullLit(@NotNull QueryParser.NullLitContext ctx);

	/**
	 * Exit a parse tree produced by {@link QueryParser#NullLit}.
	 * 
	 * @param ctx
	 *            the parse tree
	 */
	void exitNullLit(@NotNull QueryParser.NullLitContext ctx);

	/**
	 * Enter a parse tree produced by {@link QueryParser#qualifiedName}.
	 * 
	 * @param ctx
	 *            the parse tree
	 */
	void enterQualifiedName(@NotNull QueryParser.QualifiedNameContext ctx);

	/**
	 * Exit a parse tree produced by {@link QueryParser#qualifiedName}.
	 * 
	 * @param ctx
	 *            the parse tree
	 */
	void exitQualifiedName(@NotNull QueryParser.QualifiedNameContext ctx);

	/**
	 * Enter a parse tree produced by {@link QueryParser#SeqType}.
	 * 
	 * @param ctx
	 *            the parse tree
	 */
	void enterSeqType(@NotNull QueryParser.SeqTypeContext ctx);

	/**
	 * Exit a parse tree produced by {@link QueryParser#SeqType}.
	 * 
	 * @param ctx
	 *            the parse tree
	 */
	void exitSeqType(@NotNull QueryParser.SeqTypeContext ctx);

	/**
	 * Enter a parse tree produced by {@link QueryParser#Not}.
	 * 
	 * @param ctx
	 *            the parse tree
	 */
	void enterNot(@NotNull QueryParser.NotContext ctx);

	/**
	 * Exit a parse tree produced by {@link QueryParser#Not}.
	 * 
	 * @param ctx
	 *            the parse tree
	 */
	void exitNot(@NotNull QueryParser.NotContext ctx);

	/**
	 * Enter a parse tree produced by {@link QueryParser#entry}.
	 * 
	 * @param ctx
	 *            the parse tree
	 */
	void enterEntry(@NotNull QueryParser.EntryContext ctx);

	/**
	 * Exit a parse tree produced by {@link QueryParser#entry}.
	 * 
	 * @param ctx
	 *            the parse tree
	 */
	void exitEntry(@NotNull QueryParser.EntryContext ctx);

	/**
	 * Enter a parse tree produced by {@link QueryParser#Var}.
	 * 
	 * @param ctx
	 *            the parse tree
	 */
	void enterVar(@NotNull QueryParser.VarContext ctx);

	/**
	 * Exit a parse tree produced by {@link QueryParser#Var}.
	 * 
	 * @param ctx
	 *            the parse tree
	 */
	void exitVar(@NotNull QueryParser.VarContext ctx);

	/**
	 * Enter a parse tree produced by {@link QueryParser#lambdaExpression}.
	 * 
	 * @param ctx
	 *            the parse tree
	 */
	void enterLambdaExpression(@NotNull QueryParser.LambdaExpressionContext ctx);

	/**
	 * Exit a parse tree produced by {@link QueryParser#lambdaExpression}.
	 * 
	 * @param ctx
	 *            the parse tree
	 */
	void exitLambdaExpression(@NotNull QueryParser.LambdaExpressionContext ctx);

	/**
	 * Enter a parse tree produced by {@link QueryParser#collectionIterator}.
	 * 
	 * @param ctx
	 *            the parse tree
	 */
	void enterCollectionIterator(@NotNull QueryParser.CollectionIteratorContext ctx);

	/**
	 * Exit a parse tree produced by {@link QueryParser#collectionIterator}.
	 * 
	 * @param ctx
	 *            the parse tree
	 */
	void exitCollectionIterator(@NotNull QueryParser.CollectionIteratorContext ctx);

	/**
	 * Enter a parse tree produced by {@link QueryParser#IntegerLit}.
	 * 
	 * @param ctx
	 *            the parse tree
	 */
	void enterIntegerLit(@NotNull QueryParser.IntegerLitContext ctx);

	/**
	 * Exit a parse tree produced by {@link QueryParser#IntegerLit}.
	 * 
	 * @param ctx
	 *            the parse tree
	 */
	void exitIntegerLit(@NotNull QueryParser.IntegerLitContext ctx);

	/**
	 * Enter a parse tree produced by {@link QueryParser#Comp}.
	 * 
	 * @param ctx
	 *            the parse tree
	 */
	void enterComp(@NotNull QueryParser.CompContext ctx);

	/**
	 * Exit a parse tree produced by {@link QueryParser#Comp}.
	 * 
	 * @param ctx
	 *            the parse tree
	 */
	void exitComp(@NotNull QueryParser.CompContext ctx);
}

// Generated from Query.g4 by ANTLR 4.13.2

/*******************************************************************************
 * Copyright (c) 2015, 2025 Obeo.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 * 
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.acceleo.query.parser;
  

import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link QueryParser}.
 */
public interface QueryListener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by {@link QueryParser#entry}.
	 * @param ctx the parse tree
	 */
	void enterEntry(QueryParser.EntryContext ctx);
	/**
	 * Exit a parse tree produced by {@link QueryParser#entry}.
	 * @param ctx the parse tree
	 */
	void exitEntry(QueryParser.EntryContext ctx);
	/**
	 * Enter a parse tree produced by the {@code VarRef}
	 * labeled alternative in {@link QueryParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterVarRef(QueryParser.VarRefContext ctx);
	/**
	 * Exit a parse tree produced by the {@code VarRef}
	 * labeled alternative in {@link QueryParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitVarRef(QueryParser.VarRefContext ctx);
	/**
	 * Enter a parse tree produced by the {@code Add}
	 * labeled alternative in {@link QueryParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterAdd(QueryParser.AddContext ctx);
	/**
	 * Exit a parse tree produced by the {@code Add}
	 * labeled alternative in {@link QueryParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitAdd(QueryParser.AddContext ctx);
	/**
	 * Enter a parse tree produced by the {@code Nav}
	 * labeled alternative in {@link QueryParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterNav(QueryParser.NavContext ctx);
	/**
	 * Exit a parse tree produced by the {@code Nav}
	 * labeled alternative in {@link QueryParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitNav(QueryParser.NavContext ctx);
	/**
	 * Enter a parse tree produced by the {@code Or}
	 * labeled alternative in {@link QueryParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterOr(QueryParser.OrContext ctx);
	/**
	 * Exit a parse tree produced by the {@code Or}
	 * labeled alternative in {@link QueryParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitOr(QueryParser.OrContext ctx);
	/**
	 * Enter a parse tree produced by the {@code LetExpr}
	 * labeled alternative in {@link QueryParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterLetExpr(QueryParser.LetExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code LetExpr}
	 * labeled alternative in {@link QueryParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitLetExpr(QueryParser.LetExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code Conditional}
	 * labeled alternative in {@link QueryParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterConditional(QueryParser.ConditionalContext ctx);
	/**
	 * Exit a parse tree produced by the {@code Conditional}
	 * labeled alternative in {@link QueryParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitConditional(QueryParser.ConditionalContext ctx);
	/**
	 * Enter a parse tree produced by the {@code Comp}
	 * labeled alternative in {@link QueryParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterComp(QueryParser.CompContext ctx);
	/**
	 * Exit a parse tree produced by the {@code Comp}
	 * labeled alternative in {@link QueryParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitComp(QueryParser.CompContext ctx);
	/**
	 * Enter a parse tree produced by the {@code Not}
	 * labeled alternative in {@link QueryParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterNot(QueryParser.NotContext ctx);
	/**
	 * Exit a parse tree produced by the {@code Not}
	 * labeled alternative in {@link QueryParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitNot(QueryParser.NotContext ctx);
	/**
	 * Enter a parse tree produced by the {@code Min}
	 * labeled alternative in {@link QueryParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterMin(QueryParser.MinContext ctx);
	/**
	 * Exit a parse tree produced by the {@code Min}
	 * labeled alternative in {@link QueryParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitMin(QueryParser.MinContext ctx);
	/**
	 * Enter a parse tree produced by the {@code Implies}
	 * labeled alternative in {@link QueryParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterImplies(QueryParser.ImpliesContext ctx);
	/**
	 * Exit a parse tree produced by the {@code Implies}
	 * labeled alternative in {@link QueryParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitImplies(QueryParser.ImpliesContext ctx);
	/**
	 * Enter a parse tree produced by the {@code Mult}
	 * labeled alternative in {@link QueryParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterMult(QueryParser.MultContext ctx);
	/**
	 * Exit a parse tree produced by the {@code Mult}
	 * labeled alternative in {@link QueryParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitMult(QueryParser.MultContext ctx);
	/**
	 * Enter a parse tree produced by the {@code Lit}
	 * labeled alternative in {@link QueryParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterLit(QueryParser.LitContext ctx);
	/**
	 * Exit a parse tree produced by the {@code Lit}
	 * labeled alternative in {@link QueryParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitLit(QueryParser.LitContext ctx);
	/**
	 * Enter a parse tree produced by the {@code And}
	 * labeled alternative in {@link QueryParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterAnd(QueryParser.AndContext ctx);
	/**
	 * Exit a parse tree produced by the {@code And}
	 * labeled alternative in {@link QueryParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitAnd(QueryParser.AndContext ctx);
	/**
	 * Enter a parse tree produced by the {@code Xor}
	 * labeled alternative in {@link QueryParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterXor(QueryParser.XorContext ctx);
	/**
	 * Exit a parse tree produced by the {@code Xor}
	 * labeled alternative in {@link QueryParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitXor(QueryParser.XorContext ctx);
	/**
	 * Enter a parse tree produced by the {@code Paren}
	 * labeled alternative in {@link QueryParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterParen(QueryParser.ParenContext ctx);
	/**
	 * Exit a parse tree produced by the {@code Paren}
	 * labeled alternative in {@link QueryParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitParen(QueryParser.ParenContext ctx);
	/**
	 * Enter a parse tree produced by {@link QueryParser#binding}.
	 * @param ctx the parse tree
	 */
	void enterBinding(QueryParser.BindingContext ctx);
	/**
	 * Exit a parse tree produced by {@link QueryParser#binding}.
	 * @param ctx the parse tree
	 */
	void exitBinding(QueryParser.BindingContext ctx);
	/**
	 * Enter a parse tree produced by {@link QueryParser#addOp}.
	 * @param ctx the parse tree
	 */
	void enterAddOp(QueryParser.AddOpContext ctx);
	/**
	 * Exit a parse tree produced by {@link QueryParser#addOp}.
	 * @param ctx the parse tree
	 */
	void exitAddOp(QueryParser.AddOpContext ctx);
	/**
	 * Enter a parse tree produced by {@link QueryParser#compOp}.
	 * @param ctx the parse tree
	 */
	void enterCompOp(QueryParser.CompOpContext ctx);
	/**
	 * Exit a parse tree produced by {@link QueryParser#compOp}.
	 * @param ctx the parse tree
	 */
	void exitCompOp(QueryParser.CompOpContext ctx);
	/**
	 * Enter a parse tree produced by the {@code Feature}
	 * labeled alternative in {@link QueryParser#navigationSegment}.
	 * @param ctx the parse tree
	 */
	void enterFeature(QueryParser.FeatureContext ctx);
	/**
	 * Exit a parse tree produced by the {@code Feature}
	 * labeled alternative in {@link QueryParser#navigationSegment}.
	 * @param ctx the parse tree
	 */
	void exitFeature(QueryParser.FeatureContext ctx);
	/**
	 * Enter a parse tree produced by the {@code CallOrApply}
	 * labeled alternative in {@link QueryParser#navigationSegment}.
	 * @param ctx the parse tree
	 */
	void enterCallOrApply(QueryParser.CallOrApplyContext ctx);
	/**
	 * Exit a parse tree produced by the {@code CallOrApply}
	 * labeled alternative in {@link QueryParser#navigationSegment}.
	 * @param ctx the parse tree
	 */
	void exitCallOrApply(QueryParser.CallOrApplyContext ctx);
	/**
	 * Enter a parse tree produced by the {@code CollectionCall}
	 * labeled alternative in {@link QueryParser#navigationSegment}.
	 * @param ctx the parse tree
	 */
	void enterCollectionCall(QueryParser.CollectionCallContext ctx);
	/**
	 * Exit a parse tree produced by the {@code CollectionCall}
	 * labeled alternative in {@link QueryParser#navigationSegment}.
	 * @param ctx the parse tree
	 */
	void exitCollectionCall(QueryParser.CollectionCallContext ctx);
	/**
	 * Enter a parse tree produced by {@link QueryParser#serviceCall}.
	 * @param ctx the parse tree
	 */
	void enterServiceCall(QueryParser.ServiceCallContext ctx);
	/**
	 * Exit a parse tree produced by {@link QueryParser#serviceCall}.
	 * @param ctx the parse tree
	 */
	void exitServiceCall(QueryParser.ServiceCallContext ctx);
	/**
	 * Enter a parse tree produced by {@link QueryParser#lambdaExpression}.
	 * @param ctx the parse tree
	 */
	void enterLambdaExpression(QueryParser.LambdaExpressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link QueryParser#lambdaExpression}.
	 * @param ctx the parse tree
	 */
	void exitLambdaExpression(QueryParser.LambdaExpressionContext ctx);
	/**
	 * Enter a parse tree produced by {@link QueryParser#arguments}.
	 * @param ctx the parse tree
	 */
	void enterArguments(QueryParser.ArgumentsContext ctx);
	/**
	 * Exit a parse tree produced by {@link QueryParser#arguments}.
	 * @param ctx the parse tree
	 */
	void exitArguments(QueryParser.ArgumentsContext ctx);
	/**
	 * Enter a parse tree produced by {@link QueryParser#expressionSequence}.
	 * @param ctx the parse tree
	 */
	void enterExpressionSequence(QueryParser.ExpressionSequenceContext ctx);
	/**
	 * Exit a parse tree produced by {@link QueryParser#expressionSequence}.
	 * @param ctx the parse tree
	 */
	void exitExpressionSequence(QueryParser.ExpressionSequenceContext ctx);
	/**
	 * Enter a parse tree produced by {@link QueryParser#lambda}.
	 * @param ctx the parse tree
	 */
	void enterLambda(QueryParser.LambdaContext ctx);
	/**
	 * Exit a parse tree produced by {@link QueryParser#lambda}.
	 * @param ctx the parse tree
	 */
	void exitLambda(QueryParser.LambdaContext ctx);
	/**
	 * Enter a parse tree produced by {@link QueryParser#variableDefinition}.
	 * @param ctx the parse tree
	 */
	void enterVariableDefinition(QueryParser.VariableDefinitionContext ctx);
	/**
	 * Exit a parse tree produced by {@link QueryParser#variableDefinition}.
	 * @param ctx the parse tree
	 */
	void exitVariableDefinition(QueryParser.VariableDefinitionContext ctx);
	/**
	 * Enter a parse tree produced by the {@code StringLit}
	 * labeled alternative in {@link QueryParser#literal}.
	 * @param ctx the parse tree
	 */
	void enterStringLit(QueryParser.StringLitContext ctx);
	/**
	 * Exit a parse tree produced by the {@code StringLit}
	 * labeled alternative in {@link QueryParser#literal}.
	 * @param ctx the parse tree
	 */
	void exitStringLit(QueryParser.StringLitContext ctx);
	/**
	 * Enter a parse tree produced by the {@code ErrorStringLit}
	 * labeled alternative in {@link QueryParser#literal}.
	 * @param ctx the parse tree
	 */
	void enterErrorStringLit(QueryParser.ErrorStringLitContext ctx);
	/**
	 * Exit a parse tree produced by the {@code ErrorStringLit}
	 * labeled alternative in {@link QueryParser#literal}.
	 * @param ctx the parse tree
	 */
	void exitErrorStringLit(QueryParser.ErrorStringLitContext ctx);
	/**
	 * Enter a parse tree produced by the {@code IntegerLit}
	 * labeled alternative in {@link QueryParser#literal}.
	 * @param ctx the parse tree
	 */
	void enterIntegerLit(QueryParser.IntegerLitContext ctx);
	/**
	 * Exit a parse tree produced by the {@code IntegerLit}
	 * labeled alternative in {@link QueryParser#literal}.
	 * @param ctx the parse tree
	 */
	void exitIntegerLit(QueryParser.IntegerLitContext ctx);
	/**
	 * Enter a parse tree produced by the {@code RealLit}
	 * labeled alternative in {@link QueryParser#literal}.
	 * @param ctx the parse tree
	 */
	void enterRealLit(QueryParser.RealLitContext ctx);
	/**
	 * Exit a parse tree produced by the {@code RealLit}
	 * labeled alternative in {@link QueryParser#literal}.
	 * @param ctx the parse tree
	 */
	void exitRealLit(QueryParser.RealLitContext ctx);
	/**
	 * Enter a parse tree produced by the {@code TrueLit}
	 * labeled alternative in {@link QueryParser#literal}.
	 * @param ctx the parse tree
	 */
	void enterTrueLit(QueryParser.TrueLitContext ctx);
	/**
	 * Exit a parse tree produced by the {@code TrueLit}
	 * labeled alternative in {@link QueryParser#literal}.
	 * @param ctx the parse tree
	 */
	void exitTrueLit(QueryParser.TrueLitContext ctx);
	/**
	 * Enter a parse tree produced by the {@code FalseLit}
	 * labeled alternative in {@link QueryParser#literal}.
	 * @param ctx the parse tree
	 */
	void enterFalseLit(QueryParser.FalseLitContext ctx);
	/**
	 * Exit a parse tree produced by the {@code FalseLit}
	 * labeled alternative in {@link QueryParser#literal}.
	 * @param ctx the parse tree
	 */
	void exitFalseLit(QueryParser.FalseLitContext ctx);
	/**
	 * Enter a parse tree produced by the {@code NullLit}
	 * labeled alternative in {@link QueryParser#literal}.
	 * @param ctx the parse tree
	 */
	void enterNullLit(QueryParser.NullLitContext ctx);
	/**
	 * Exit a parse tree produced by the {@code NullLit}
	 * labeled alternative in {@link QueryParser#literal}.
	 * @param ctx the parse tree
	 */
	void exitNullLit(QueryParser.NullLitContext ctx);
	/**
	 * Enter a parse tree produced by the {@code ExplicitSeqLit}
	 * labeled alternative in {@link QueryParser#literal}.
	 * @param ctx the parse tree
	 */
	void enterExplicitSeqLit(QueryParser.ExplicitSeqLitContext ctx);
	/**
	 * Exit a parse tree produced by the {@code ExplicitSeqLit}
	 * labeled alternative in {@link QueryParser#literal}.
	 * @param ctx the parse tree
	 */
	void exitExplicitSeqLit(QueryParser.ExplicitSeqLitContext ctx);
	/**
	 * Enter a parse tree produced by the {@code ExplicitSetLit}
	 * labeled alternative in {@link QueryParser#literal}.
	 * @param ctx the parse tree
	 */
	void enterExplicitSetLit(QueryParser.ExplicitSetLitContext ctx);
	/**
	 * Exit a parse tree produced by the {@code ExplicitSetLit}
	 * labeled alternative in {@link QueryParser#literal}.
	 * @param ctx the parse tree
	 */
	void exitExplicitSetLit(QueryParser.ExplicitSetLitContext ctx);
	/**
	 * Enter a parse tree produced by the {@code EnumLit}
	 * labeled alternative in {@link QueryParser#literal}.
	 * @param ctx the parse tree
	 */
	void enterEnumLit(QueryParser.EnumLitContext ctx);
	/**
	 * Exit a parse tree produced by the {@code EnumLit}
	 * labeled alternative in {@link QueryParser#literal}.
	 * @param ctx the parse tree
	 */
	void exitEnumLit(QueryParser.EnumLitContext ctx);
	/**
	 * Enter a parse tree produced by the {@code ErrorEnumLit}
	 * labeled alternative in {@link QueryParser#literal}.
	 * @param ctx the parse tree
	 */
	void enterErrorEnumLit(QueryParser.ErrorEnumLitContext ctx);
	/**
	 * Exit a parse tree produced by the {@code ErrorEnumLit}
	 * labeled alternative in {@link QueryParser#literal}.
	 * @param ctx the parse tree
	 */
	void exitErrorEnumLit(QueryParser.ErrorEnumLitContext ctx);
	/**
	 * Enter a parse tree produced by the {@code TypeLit}
	 * labeled alternative in {@link QueryParser#literal}.
	 * @param ctx the parse tree
	 */
	void enterTypeLit(QueryParser.TypeLitContext ctx);
	/**
	 * Exit a parse tree produced by the {@code TypeLit}
	 * labeled alternative in {@link QueryParser#literal}.
	 * @param ctx the parse tree
	 */
	void exitTypeLit(QueryParser.TypeLitContext ctx);
	/**
	 * Enter a parse tree produced by the {@code StrType}
	 * labeled alternative in {@link QueryParser#typeLiteral}.
	 * @param ctx the parse tree
	 */
	void enterStrType(QueryParser.StrTypeContext ctx);
	/**
	 * Exit a parse tree produced by the {@code StrType}
	 * labeled alternative in {@link QueryParser#typeLiteral}.
	 * @param ctx the parse tree
	 */
	void exitStrType(QueryParser.StrTypeContext ctx);
	/**
	 * Enter a parse tree produced by the {@code IntType}
	 * labeled alternative in {@link QueryParser#typeLiteral}.
	 * @param ctx the parse tree
	 */
	void enterIntType(QueryParser.IntTypeContext ctx);
	/**
	 * Exit a parse tree produced by the {@code IntType}
	 * labeled alternative in {@link QueryParser#typeLiteral}.
	 * @param ctx the parse tree
	 */
	void exitIntType(QueryParser.IntTypeContext ctx);
	/**
	 * Enter a parse tree produced by the {@code RealType}
	 * labeled alternative in {@link QueryParser#typeLiteral}.
	 * @param ctx the parse tree
	 */
	void enterRealType(QueryParser.RealTypeContext ctx);
	/**
	 * Exit a parse tree produced by the {@code RealType}
	 * labeled alternative in {@link QueryParser#typeLiteral}.
	 * @param ctx the parse tree
	 */
	void exitRealType(QueryParser.RealTypeContext ctx);
	/**
	 * Enter a parse tree produced by the {@code BooleanType}
	 * labeled alternative in {@link QueryParser#typeLiteral}.
	 * @param ctx the parse tree
	 */
	void enterBooleanType(QueryParser.BooleanTypeContext ctx);
	/**
	 * Exit a parse tree produced by the {@code BooleanType}
	 * labeled alternative in {@link QueryParser#typeLiteral}.
	 * @param ctx the parse tree
	 */
	void exitBooleanType(QueryParser.BooleanTypeContext ctx);
	/**
	 * Enter a parse tree produced by the {@code SeqType}
	 * labeled alternative in {@link QueryParser#typeLiteral}.
	 * @param ctx the parse tree
	 */
	void enterSeqType(QueryParser.SeqTypeContext ctx);
	/**
	 * Exit a parse tree produced by the {@code SeqType}
	 * labeled alternative in {@link QueryParser#typeLiteral}.
	 * @param ctx the parse tree
	 */
	void exitSeqType(QueryParser.SeqTypeContext ctx);
	/**
	 * Enter a parse tree produced by the {@code SetType}
	 * labeled alternative in {@link QueryParser#typeLiteral}.
	 * @param ctx the parse tree
	 */
	void enterSetType(QueryParser.SetTypeContext ctx);
	/**
	 * Exit a parse tree produced by the {@code SetType}
	 * labeled alternative in {@link QueryParser#typeLiteral}.
	 * @param ctx the parse tree
	 */
	void exitSetType(QueryParser.SetTypeContext ctx);
	/**
	 * Enter a parse tree produced by the {@code ClsType}
	 * labeled alternative in {@link QueryParser#typeLiteral}.
	 * @param ctx the parse tree
	 */
	void enterClsType(QueryParser.ClsTypeContext ctx);
	/**
	 * Exit a parse tree produced by the {@code ClsType}
	 * labeled alternative in {@link QueryParser#typeLiteral}.
	 * @param ctx the parse tree
	 */
	void exitClsType(QueryParser.ClsTypeContext ctx);
	/**
	 * Enter a parse tree produced by the {@code ClassifierSetType}
	 * labeled alternative in {@link QueryParser#typeLiteral}.
	 * @param ctx the parse tree
	 */
	void enterClassifierSetType(QueryParser.ClassifierSetTypeContext ctx);
	/**
	 * Exit a parse tree produced by the {@code ClassifierSetType}
	 * labeled alternative in {@link QueryParser#typeLiteral}.
	 * @param ctx the parse tree
	 */
	void exitClassifierSetType(QueryParser.ClassifierSetTypeContext ctx);
	/**
	 * Enter a parse tree produced by the {@code ClassifierType}
	 * labeled alternative in {@link QueryParser#classifierTypeRule}.
	 * @param ctx the parse tree
	 */
	void enterClassifierType(QueryParser.ClassifierTypeContext ctx);
	/**
	 * Exit a parse tree produced by the {@code ClassifierType}
	 * labeled alternative in {@link QueryParser#classifierTypeRule}.
	 * @param ctx the parse tree
	 */
	void exitClassifierType(QueryParser.ClassifierTypeContext ctx);
	/**
	 * Enter a parse tree produced by the {@code ErrorClassifierType}
	 * labeled alternative in {@link QueryParser#classifierTypeRule}.
	 * @param ctx the parse tree
	 */
	void enterErrorClassifierType(QueryParser.ErrorClassifierTypeContext ctx);
	/**
	 * Exit a parse tree produced by the {@code ErrorClassifierType}
	 * labeled alternative in {@link QueryParser#classifierTypeRule}.
	 * @param ctx the parse tree
	 */
	void exitErrorClassifierType(QueryParser.ErrorClassifierTypeContext ctx);
}
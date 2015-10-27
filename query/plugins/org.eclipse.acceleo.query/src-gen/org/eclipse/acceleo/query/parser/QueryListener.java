// Generated from Query.g4 by ANTLR 4.3

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
 * This interface defines a complete listener for a parse tree produced by
 * {@link QueryParser}.
 */
public interface QueryListener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by the {@code Add}
	 * labeled alternative in {@link QueryParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterAdd(@NotNull QueryParser.AddContext ctx);
	/**
	 * Exit a parse tree produced by the {@code Add}
	 * labeled alternative in {@link QueryParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitAdd(@NotNull QueryParser.AddContext ctx);

	/**
	 * Enter a parse tree produced by the {@code NullLit}
	 * labeled alternative in {@link QueryParser#literal}.
	 * @param ctx the parse tree
	 */
	void enterNullLit(@NotNull QueryParser.NullLitContext ctx);
	/**
	 * Exit a parse tree produced by the {@code NullLit}
	 * labeled alternative in {@link QueryParser#literal}.
	 * @param ctx the parse tree
	 */
	void exitNullLit(@NotNull QueryParser.NullLitContext ctx);

	/**
	 * Enter a parse tree produced by the {@code Nav}
	 * labeled alternative in {@link QueryParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterNav(@NotNull QueryParser.NavContext ctx);
	/**
	 * Exit a parse tree produced by the {@code Nav}
	 * labeled alternative in {@link QueryParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitNav(@NotNull QueryParser.NavContext ctx);

	/**
	 * Enter a parse tree produced by the {@code TypeLit}
	 * labeled alternative in {@link QueryParser#literal}.
	 * @param ctx the parse tree
	 */
	void enterTypeLit(@NotNull QueryParser.TypeLitContext ctx);
	/**
	 * Exit a parse tree produced by the {@code TypeLit}
	 * labeled alternative in {@link QueryParser#literal}.
	 * @param ctx the parse tree
	 */
	void exitTypeLit(@NotNull QueryParser.TypeLitContext ctx);

	/**
	 * Enter a parse tree produced by the {@code Or}
	 * labeled alternative in {@link QueryParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterOr(@NotNull QueryParser.OrContext ctx);
	/**
	 * Exit a parse tree produced by the {@code Or}
	 * labeled alternative in {@link QueryParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitOr(@NotNull QueryParser.OrContext ctx);

	/**
	 * Enter a parse tree produced by the {@code CallOrApply}
	 * labeled alternative in {@link QueryParser#navigationSegment}.
	 * @param ctx the parse tree
	 */
	void enterCallOrApply(@NotNull QueryParser.CallOrApplyContext ctx);
	/**
	 * Exit a parse tree produced by the {@code CallOrApply}
	 * labeled alternative in {@link QueryParser#navigationSegment}.
	 * @param ctx the parse tree
	 */
	void exitCallOrApply(@NotNull QueryParser.CallOrApplyContext ctx);

	/**
	 * Enter a parse tree produced by the {@code ErrorEnumLit}
	 * labeled alternative in {@link QueryParser#literal}.
	 * @param ctx the parse tree
	 */
	void enterErrorEnumLit(@NotNull QueryParser.ErrorEnumLitContext ctx);
	/**
	 * Exit a parse tree produced by the {@code ErrorEnumLit}
	 * labeled alternative in {@link QueryParser#literal}.
	 * @param ctx the parse tree
	 */
	void exitErrorEnumLit(@NotNull QueryParser.ErrorEnumLitContext ctx);

	/**
	 * Enter a parse tree produced by {@link QueryParser#binding}.
	 * @param ctx the parse tree
	 */
	void enterBinding(@NotNull QueryParser.BindingContext ctx);
	/**
	 * Exit a parse tree produced by {@link QueryParser#binding}.
	 * @param ctx the parse tree
	 */
	void exitBinding(@NotNull QueryParser.BindingContext ctx);

	/**
	 * Enter a parse tree produced by the {@code FalseLit}
	 * labeled alternative in {@link QueryParser#literal}.
	 * @param ctx the parse tree
	 */
	void enterFalseLit(@NotNull QueryParser.FalseLitContext ctx);
	/**
	 * Exit a parse tree produced by the {@code FalseLit}
	 * labeled alternative in {@link QueryParser#literal}.
	 * @param ctx the parse tree
	 */
	void exitFalseLit(@NotNull QueryParser.FalseLitContext ctx);

	/**
	 * Enter a parse tree produced by the {@code Conditional}
	 * labeled alternative in {@link QueryParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterConditional(@NotNull QueryParser.ConditionalContext ctx);
	/**
	 * Exit a parse tree produced by the {@code Conditional}
	 * labeled alternative in {@link QueryParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitConditional(@NotNull QueryParser.ConditionalContext ctx);

	/**
	 * Enter a parse tree produced by the {@code Feature}
	 * labeled alternative in {@link QueryParser#navigationSegment}.
	 * @param ctx the parse tree
	 */
	void enterFeature(@NotNull QueryParser.FeatureContext ctx);
	/**
	 * Exit a parse tree produced by the {@code Feature}
	 * labeled alternative in {@link QueryParser#navigationSegment}.
	 * @param ctx the parse tree
	 */
	void exitFeature(@NotNull QueryParser.FeatureContext ctx);

	/**
	 * Enter a parse tree produced by the {@code RealType}
	 * labeled alternative in {@link QueryParser#typeLiteral}.
	 * @param ctx the parse tree
	 */
	void enterRealType(@NotNull QueryParser.RealTypeContext ctx);
	/**
	 * Exit a parse tree produced by the {@code RealType}
	 * labeled alternative in {@link QueryParser#typeLiteral}.
	 * @param ctx the parse tree
	 */
	void exitRealType(@NotNull QueryParser.RealTypeContext ctx);

	/**
	 * Enter a parse tree produced by the {@code Comp}
	 * labeled alternative in {@link QueryParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterComp(@NotNull QueryParser.CompContext ctx);
	/**
	 * Exit a parse tree produced by the {@code Comp}
	 * labeled alternative in {@link QueryParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitComp(@NotNull QueryParser.CompContext ctx);

	/**
	 * Enter a parse tree produced by the {@code StringLit}
	 * labeled alternative in {@link QueryParser#literal}.
	 * @param ctx the parse tree
	 */
	void enterStringLit(@NotNull QueryParser.StringLitContext ctx);
	/**
	 * Exit a parse tree produced by the {@code StringLit}
	 * labeled alternative in {@link QueryParser#literal}.
	 * @param ctx the parse tree
	 */
	void exitStringLit(@NotNull QueryParser.StringLitContext ctx);

	/**
	 * Enter a parse tree produced by the {@code Implies}
	 * labeled alternative in {@link QueryParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterImplies(@NotNull QueryParser.ImpliesContext ctx);
	/**
	 * Exit a parse tree produced by the {@code Implies}
	 * labeled alternative in {@link QueryParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitImplies(@NotNull QueryParser.ImpliesContext ctx);

	/**
	 * Enter a parse tree produced by the {@code SetType}
	 * labeled alternative in {@link QueryParser#typeLiteral}.
	 * @param ctx the parse tree
	 */
	void enterSetType(@NotNull QueryParser.SetTypeContext ctx);
	/**
	 * Exit a parse tree produced by the {@code SetType}
	 * labeled alternative in {@link QueryParser#typeLiteral}.
	 * @param ctx the parse tree
	 */
	void exitSetType(@NotNull QueryParser.SetTypeContext ctx);

	/**
	 * Enter a parse tree produced by {@link QueryParser#collectionIterator}.
	 * @param ctx the parse tree
	 */
	void enterCollectionIterator(@NotNull QueryParser.CollectionIteratorContext ctx);
	/**
	 * Exit a parse tree produced by {@link QueryParser#collectionIterator}.
	 * @param ctx the parse tree
	 */
	void exitCollectionIterator(@NotNull QueryParser.CollectionIteratorContext ctx);

	/**
	 * Enter a parse tree produced by {@link QueryParser#lambdaExpression}.
	 * @param ctx the parse tree
	 */
	void enterLambdaExpression(@NotNull QueryParser.LambdaExpressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link QueryParser#lambdaExpression}.
	 * @param ctx the parse tree
	 */
	void exitLambdaExpression(@NotNull QueryParser.LambdaExpressionContext ctx);

	/**
	 * Enter a parse tree produced by the {@code LetExpr}
	 * labeled alternative in {@link QueryParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterLetExpr(@NotNull QueryParser.LetExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code LetExpr}
	 * labeled alternative in {@link QueryParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitLetExpr(@NotNull QueryParser.LetExprContext ctx);

	/**
	 * Enter a parse tree produced by the {@code IntegerLit}
	 * labeled alternative in {@link QueryParser#literal}.
	 * @param ctx the parse tree
	 */
	void enterIntegerLit(@NotNull QueryParser.IntegerLitContext ctx);
	/**
	 * Exit a parse tree produced by the {@code IntegerLit}
	 * labeled alternative in {@link QueryParser#literal}.
	 * @param ctx the parse tree
	 */
	void exitIntegerLit(@NotNull QueryParser.IntegerLitContext ctx);

	/**
	 * Enter a parse tree produced by {@link QueryParser#variableDefinition}.
	 * @param ctx the parse tree
	 */
	void enterVariableDefinition(@NotNull QueryParser.VariableDefinitionContext ctx);
	/**
	 * Exit a parse tree produced by {@link QueryParser#variableDefinition}.
	 * @param ctx the parse tree
	 */
	void exitVariableDefinition(@NotNull QueryParser.VariableDefinitionContext ctx);

	/**
	 * Enter a parse tree produced by {@link QueryParser#entry}.
	 * @param ctx the parse tree
	 */
	void enterEntry(@NotNull QueryParser.EntryContext ctx);
	/**
	 * Exit a parse tree produced by {@link QueryParser#entry}.
	 * @param ctx the parse tree
	 */
	void exitEntry(@NotNull QueryParser.EntryContext ctx);

	/**
	 * Enter a parse tree produced by the {@code ClassifierSetType}
	 * labeled alternative in {@link QueryParser#typeLiteral}.
	 * @param ctx the parse tree
	 */
	void enterClassifierSetType(@NotNull QueryParser.ClassifierSetTypeContext ctx);
	/**
	 * Exit a parse tree produced by the {@code ClassifierSetType}
	 * labeled alternative in {@link QueryParser#typeLiteral}.
	 * @param ctx the parse tree
	 */
	void exitClassifierSetType(@NotNull QueryParser.ClassifierSetTypeContext ctx);

	/**
	 * Enter a parse tree produced by the {@code Not}
	 * labeled alternative in {@link QueryParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterNot(@NotNull QueryParser.NotContext ctx);
	/**
	 * Exit a parse tree produced by the {@code Not}
	 * labeled alternative in {@link QueryParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitNot(@NotNull QueryParser.NotContext ctx);

	/**
	 * Enter a parse tree produced by the {@code Min}
	 * labeled alternative in {@link QueryParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterMin(@NotNull QueryParser.MinContext ctx);
	/**
	 * Exit a parse tree produced by the {@code Min}
	 * labeled alternative in {@link QueryParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitMin(@NotNull QueryParser.MinContext ctx);

	/**
	 * Enter a parse tree produced by the {@code And}
	 * labeled alternative in {@link QueryParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterAnd(@NotNull QueryParser.AndContext ctx);
	/**
	 * Exit a parse tree produced by the {@code And}
	 * labeled alternative in {@link QueryParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitAnd(@NotNull QueryParser.AndContext ctx);

	/**
	 * Enter a parse tree produced by the {@code Xor}
	 * labeled alternative in {@link QueryParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterXor(@NotNull QueryParser.XorContext ctx);
	/**
	 * Exit a parse tree produced by the {@code Xor}
	 * labeled alternative in {@link QueryParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitXor(@NotNull QueryParser.XorContext ctx);

	/**
	 * Enter a parse tree produced by the {@code TrueLit}
	 * labeled alternative in {@link QueryParser#literal}.
	 * @param ctx the parse tree
	 */
	void enterTrueLit(@NotNull QueryParser.TrueLitContext ctx);
	/**
	 * Exit a parse tree produced by the {@code TrueLit}
	 * labeled alternative in {@link QueryParser#literal}.
	 * @param ctx the parse tree
	 */
	void exitTrueLit(@NotNull QueryParser.TrueLitContext ctx);

	/**
	 * Enter a parse tree produced by the {@code ExplicitSeqLit}
	 * labeled alternative in {@link QueryParser#literal}.
	 * @param ctx the parse tree
	 */
	void enterExplicitSeqLit(@NotNull QueryParser.ExplicitSeqLitContext ctx);
	/**
	 * Exit a parse tree produced by the {@code ExplicitSeqLit}
	 * labeled alternative in {@link QueryParser#literal}.
	 * @param ctx the parse tree
	 */
	void exitExplicitSeqLit(@NotNull QueryParser.ExplicitSeqLitContext ctx);

	/**
	 * Enter a parse tree produced by the {@code VarRef}
	 * labeled alternative in {@link QueryParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterVarRef(@NotNull QueryParser.VarRefContext ctx);
	/**
	 * Exit a parse tree produced by the {@code VarRef}
	 * labeled alternative in {@link QueryParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitVarRef(@NotNull QueryParser.VarRefContext ctx);

	/**
	 * Enter a parse tree produced by the {@code ClassifierType}
	 * labeled alternative in {@link QueryParser#classifierTypeRule}.
	 * @param ctx the parse tree
	 */
	void enterClassifierType(@NotNull QueryParser.ClassifierTypeContext ctx);
	/**
	 * Exit a parse tree produced by the {@code ClassifierType}
	 * labeled alternative in {@link QueryParser#classifierTypeRule}.
	 * @param ctx the parse tree
	 */
	void exitClassifierType(@NotNull QueryParser.ClassifierTypeContext ctx);

	/**
	 * Enter a parse tree produced by the {@code SeqType}
	 * labeled alternative in {@link QueryParser#typeLiteral}.
	 * @param ctx the parse tree
	 */
	void enterSeqType(@NotNull QueryParser.SeqTypeContext ctx);
	/**
	 * Exit a parse tree produced by the {@code SeqType}
	 * labeled alternative in {@link QueryParser#typeLiteral}.
	 * @param ctx the parse tree
	 */
	void exitSeqType(@NotNull QueryParser.SeqTypeContext ctx);

	/**
	 * Enter a parse tree produced by {@link QueryParser#expressionSequence}.
	 * @param ctx the parse tree
	 */
	void enterExpressionSequence(@NotNull QueryParser.ExpressionSequenceContext ctx);
	/**
	 * Exit a parse tree produced by {@link QueryParser#expressionSequence}.
	 * @param ctx the parse tree
	 */
	void exitExpressionSequence(@NotNull QueryParser.ExpressionSequenceContext ctx);

	/**
	 * Enter a parse tree produced by the {@code IntType}
	 * labeled alternative in {@link QueryParser#typeLiteral}.
	 * @param ctx the parse tree
	 */
	void enterIntType(@NotNull QueryParser.IntTypeContext ctx);
	/**
	 * Exit a parse tree produced by the {@code IntType}
	 * labeled alternative in {@link QueryParser#typeLiteral}.
	 * @param ctx the parse tree
	 */
	void exitIntType(@NotNull QueryParser.IntTypeContext ctx);

	/**
	 * Enter a parse tree produced by the {@code EnumLit}
	 * labeled alternative in {@link QueryParser#literal}.
	 * @param ctx the parse tree
	 */
	void enterEnumLit(@NotNull QueryParser.EnumLitContext ctx);
	/**
	 * Exit a parse tree produced by the {@code EnumLit}
	 * labeled alternative in {@link QueryParser#literal}.
	 * @param ctx the parse tree
	 */
	void exitEnumLit(@NotNull QueryParser.EnumLitContext ctx);

	/**
	 * Enter a parse tree produced by the {@code RealLit}
	 * labeled alternative in {@link QueryParser#literal}.
	 * @param ctx the parse tree
	 */
	void enterRealLit(@NotNull QueryParser.RealLitContext ctx);
	/**
	 * Exit a parse tree produced by the {@code RealLit}
	 * labeled alternative in {@link QueryParser#literal}.
	 * @param ctx the parse tree
	 */
	void exitRealLit(@NotNull QueryParser.RealLitContext ctx);

	/**
	 * Enter a parse tree produced by the {@code ExplicitSetLit}
	 * labeled alternative in {@link QueryParser#literal}.
	 * @param ctx the parse tree
	 */
	void enterExplicitSetLit(@NotNull QueryParser.ExplicitSetLitContext ctx);
	/**
	 * Exit a parse tree produced by the {@code ExplicitSetLit}
	 * labeled alternative in {@link QueryParser#literal}.
	 * @param ctx the parse tree
	 */
	void exitExplicitSetLit(@NotNull QueryParser.ExplicitSetLitContext ctx);

	/**
	 * Enter a parse tree produced by the {@code ErrorClassifierType}
	 * labeled alternative in {@link QueryParser#classifierTypeRule}.
	 * @param ctx the parse tree
	 */
	void enterErrorClassifierType(@NotNull QueryParser.ErrorClassifierTypeContext ctx);
	/**
	 * Exit a parse tree produced by the {@code ErrorClassifierType}
	 * labeled alternative in {@link QueryParser#classifierTypeRule}.
	 * @param ctx the parse tree
	 */
	void exitErrorClassifierType(@NotNull QueryParser.ErrorClassifierTypeContext ctx);

	/**
	 * Enter a parse tree produced by the {@code ServiceCall}
	 * labeled alternative in {@link QueryParser#callExp}.
	 * @param ctx the parse tree
	 */
	void enterServiceCall(@NotNull QueryParser.ServiceCallContext ctx);
	/**
	 * Exit a parse tree produced by the {@code ServiceCall}
	 * labeled alternative in {@link QueryParser#callExp}.
	 * @param ctx the parse tree
	 */
	void exitServiceCall(@NotNull QueryParser.ServiceCallContext ctx);

	/**
	 * Enter a parse tree produced by {@link QueryParser#addOp}.
	 * @param ctx the parse tree
	 */
	void enterAddOp(@NotNull QueryParser.AddOpContext ctx);
	/**
	 * Exit a parse tree produced by {@link QueryParser#addOp}.
	 * @param ctx the parse tree
	 */
	void exitAddOp(@NotNull QueryParser.AddOpContext ctx);

	/**
	 * Enter a parse tree produced by the {@code BooleanType}
	 * labeled alternative in {@link QueryParser#typeLiteral}.
	 * @param ctx the parse tree
	 */
	void enterBooleanType(@NotNull QueryParser.BooleanTypeContext ctx);
	/**
	 * Exit a parse tree produced by the {@code BooleanType}
	 * labeled alternative in {@link QueryParser#typeLiteral}.
	 * @param ctx the parse tree
	 */
	void exitBooleanType(@NotNull QueryParser.BooleanTypeContext ctx);

	/**
	 * Enter a parse tree produced by the {@code ClsType}
	 * labeled alternative in {@link QueryParser#typeLiteral}.
	 * @param ctx the parse tree
	 */
	void enterClsType(@NotNull QueryParser.ClsTypeContext ctx);
	/**
	 * Exit a parse tree produced by the {@code ClsType}
	 * labeled alternative in {@link QueryParser#typeLiteral}.
	 * @param ctx the parse tree
	 */
	void exitClsType(@NotNull QueryParser.ClsTypeContext ctx);

	/**
	 * Enter a parse tree produced by the {@code StrType}
	 * labeled alternative in {@link QueryParser#typeLiteral}.
	 * @param ctx the parse tree
	 */
	void enterStrType(@NotNull QueryParser.StrTypeContext ctx);
	/**
	 * Exit a parse tree produced by the {@code StrType}
	 * labeled alternative in {@link QueryParser#typeLiteral}.
	 * @param ctx the parse tree
	 */
	void exitStrType(@NotNull QueryParser.StrTypeContext ctx);

	/**
	 * Enter a parse tree produced by {@link QueryParser#compOp}.
	 * @param ctx the parse tree
	 */
	void enterCompOp(@NotNull QueryParser.CompOpContext ctx);
	/**
	 * Exit a parse tree produced by {@link QueryParser#compOp}.
	 * @param ctx the parse tree
	 */
	void exitCompOp(@NotNull QueryParser.CompOpContext ctx);

	/**
	 * Enter a parse tree produced by the {@code Mult}
	 * labeled alternative in {@link QueryParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterMult(@NotNull QueryParser.MultContext ctx);
	/**
	 * Exit a parse tree produced by the {@code Mult}
	 * labeled alternative in {@link QueryParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitMult(@NotNull QueryParser.MultContext ctx);

	/**
	 * Enter a parse tree produced by the {@code Lit}
	 * labeled alternative in {@link QueryParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterLit(@NotNull QueryParser.LitContext ctx);
	/**
	 * Exit a parse tree produced by the {@code Lit}
	 * labeled alternative in {@link QueryParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitLit(@NotNull QueryParser.LitContext ctx);

	/**
	 * Enter a parse tree produced by the {@code CollectionCall}
	 * labeled alternative in {@link QueryParser#navigationSegment}.
	 * @param ctx the parse tree
	 */
	void enterCollectionCall(@NotNull QueryParser.CollectionCallContext ctx);
	/**
	 * Exit a parse tree produced by the {@code CollectionCall}
	 * labeled alternative in {@link QueryParser#navigationSegment}.
	 * @param ctx the parse tree
	 */
	void exitCollectionCall(@NotNull QueryParser.CollectionCallContext ctx);

	/**
	 * Enter a parse tree produced by the {@code IterationCall}
	 * labeled alternative in {@link QueryParser#callExp}.
	 * @param ctx the parse tree
	 */
	void enterIterationCall(@NotNull QueryParser.IterationCallContext ctx);
	/**
	 * Exit a parse tree produced by the {@code IterationCall}
	 * labeled alternative in {@link QueryParser#callExp}.
	 * @param ctx the parse tree
	 */
	void exitIterationCall(@NotNull QueryParser.IterationCallContext ctx);

	/**
	 * Enter a parse tree produced by the {@code ErrorStringLit}
	 * labeled alternative in {@link QueryParser#literal}.
	 * @param ctx the parse tree
	 */
	void enterErrorStringLit(@NotNull QueryParser.ErrorStringLitContext ctx);
	/**
	 * Exit a parse tree produced by the {@code ErrorStringLit}
	 * labeled alternative in {@link QueryParser#literal}.
	 * @param ctx the parse tree
	 */
	void exitErrorStringLit(@NotNull QueryParser.ErrorStringLitContext ctx);

	/**
	 * Enter a parse tree produced by the {@code Paren}
	 * labeled alternative in {@link QueryParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterParen(@NotNull QueryParser.ParenContext ctx);
	/**
	 * Exit a parse tree produced by the {@code Paren}
	 * labeled alternative in {@link QueryParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitParen(@NotNull QueryParser.ParenContext ctx);
}
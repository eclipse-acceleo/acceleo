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
package org.eclipse.acceleo.internal.parser.ast;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.acceleo.internal.parser.ast.ocl.OCLParser;
import org.eclipse.acceleo.internal.parser.ast.ocl.WrappedOCLException;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.ocl.ParserException;

/**
 * The class used to create and store the objects of the AST model. You have to call 'initOCL' to initialize
 * the OCL environment.
 * 
 * @author <a href="mailto:jonathan.musset@obeo.fr">Jonathan Musset</a>
 */
public class ASTFactory {

	/**
	 * To parse an OCL Expression with the OCL plug-in.
	 */
	protected OCLParser pOCL;

	/**
	 * A log Handler to save AST logging messages.
	 */
	private IASTProvider logHandler;

	/**
	 * To get an AST Module corresponding to a CST Module.
	 */
	private Map<org.eclipse.acceleo.parser.cst.Module, org.eclipse.acceleo.model.mtl.Module> ioModule = new HashMap<org.eclipse.acceleo.parser.cst.Module, org.eclipse.acceleo.model.mtl.Module>();

	/**
	 * To get an AST TypedModel corresponding to a CST TypedModel.
	 */
	private Map<org.eclipse.acceleo.parser.cst.TypedModel, org.eclipse.acceleo.model.mtl.TypedModel> ioTypedModel = new HashMap<org.eclipse.acceleo.parser.cst.TypedModel, org.eclipse.acceleo.model.mtl.TypedModel>();

	/**
	 * To get an AST Template corresponding to a CST Template.
	 */
	private Map<org.eclipse.acceleo.parser.cst.Template, org.eclipse.acceleo.model.mtl.Template> ioTemplate = new HashMap<org.eclipse.acceleo.parser.cst.Template, org.eclipse.acceleo.model.mtl.Template>();

	/**
	 * To get an AST Variable corresponding to a CST Variable.
	 */
	private Map<org.eclipse.acceleo.parser.cst.Variable, org.eclipse.ocl.ecore.Variable> ioVariable = new HashMap<org.eclipse.acceleo.parser.cst.Variable, org.eclipse.ocl.ecore.Variable>();

	/**
	 * To get an AST TemplateExpression corresponding to a CST TemplateExpression. It is used as a temporary
	 * transformation. The final output model hasn't any 'org.eclipse.acceleo.model.mtl.TemplateExpression'.
	 */
	private Map<org.eclipse.acceleo.parser.cst.TemplateExpression, org.eclipse.acceleo.model.mtl.TemplateExpression> ioTemplateExpression = new HashMap<org.eclipse.acceleo.parser.cst.TemplateExpression, org.eclipse.acceleo.model.mtl.TemplateExpression>();

	/**
	 * To get an AST OCLExpression corresponding to a CST ModelExpression.
	 */
	private Map<org.eclipse.acceleo.parser.cst.ModelExpression, org.eclipse.ocl.ecore.OCLExpression> ioModelExpression = new HashMap<org.eclipse.acceleo.parser.cst.ModelExpression, org.eclipse.ocl.ecore.OCLExpression>();

	/**
	 * To get an AST StringLiteralExp corresponding to a CST TextExpression.
	 */
	private Map<org.eclipse.acceleo.parser.cst.TextExpression, org.eclipse.ocl.ecore.StringLiteralExp> ioTextExpression = new HashMap<org.eclipse.acceleo.parser.cst.TextExpression, org.eclipse.ocl.ecore.StringLiteralExp>();

	/**
	 * To get an AST Block corresponding to a CST Block.
	 */
	private Map<org.eclipse.acceleo.parser.cst.Block, org.eclipse.acceleo.model.mtl.Block> ioBlock = new HashMap<org.eclipse.acceleo.parser.cst.Block, org.eclipse.acceleo.model.mtl.Block>();

	/**
	 * To get an AST InitSection corresponding to a CST InitSection.
	 */
	private Map<org.eclipse.acceleo.parser.cst.InitSection, org.eclipse.acceleo.model.mtl.InitSection> ioInitSection = new HashMap<org.eclipse.acceleo.parser.cst.InitSection, org.eclipse.acceleo.model.mtl.InitSection>();

	/**
	 * To get an AST ProtectedAreaBlock corresponding to a CST ProtectedAreaBlock.
	 */
	private Map<org.eclipse.acceleo.parser.cst.ProtectedAreaBlock, org.eclipse.acceleo.model.mtl.ProtectedAreaBlock> ioProtectedAreaBlock = new HashMap<org.eclipse.acceleo.parser.cst.ProtectedAreaBlock, org.eclipse.acceleo.model.mtl.ProtectedAreaBlock>();

	/**
	 * To get an AST ForBlock corresponding to a CST ForBlock.
	 */
	private Map<org.eclipse.acceleo.parser.cst.ForBlock, org.eclipse.acceleo.model.mtl.ForBlock> ioForBlock = new HashMap<org.eclipse.acceleo.parser.cst.ForBlock, org.eclipse.acceleo.model.mtl.ForBlock>();

	/**
	 * To get an AST IfBlock corresponding to a CST IfBlock.
	 */
	private Map<org.eclipse.acceleo.parser.cst.IfBlock, org.eclipse.acceleo.model.mtl.IfBlock> ioIfBlock = new HashMap<org.eclipse.acceleo.parser.cst.IfBlock, org.eclipse.acceleo.model.mtl.IfBlock>();

	/**
	 * To get an AST LetBlock corresponding to a CST LetBlock.
	 */
	private Map<org.eclipse.acceleo.parser.cst.LetBlock, org.eclipse.acceleo.model.mtl.LetBlock> ioLetBlock = new HashMap<org.eclipse.acceleo.parser.cst.LetBlock, org.eclipse.acceleo.model.mtl.LetBlock>();

	/**
	 * To get an AST FileBlock corresponding to a CST FileBlock.
	 */
	private Map<org.eclipse.acceleo.parser.cst.FileBlock, org.eclipse.acceleo.model.mtl.FileBlock> ioFileBlock = new HashMap<org.eclipse.acceleo.parser.cst.FileBlock, org.eclipse.acceleo.model.mtl.FileBlock>();

	/**
	 * To get an AST TraceBlock corresponding to a CST TraceBlock.
	 */
	private Map<org.eclipse.acceleo.parser.cst.TraceBlock, org.eclipse.acceleo.model.mtl.TraceBlock> ioTraceBlock = new HashMap<org.eclipse.acceleo.parser.cst.TraceBlock, org.eclipse.acceleo.model.mtl.TraceBlock>();

	/**
	 * To get an AST Macro corresponding to a CST Macro.
	 */
	private Map<org.eclipse.acceleo.parser.cst.Macro, org.eclipse.acceleo.model.mtl.Macro> ioMacro = new HashMap<org.eclipse.acceleo.parser.cst.Macro, org.eclipse.acceleo.model.mtl.Macro>();

	/**
	 * To get an AST Query corresponding to a CST Query.
	 */
	private Map<org.eclipse.acceleo.parser.cst.Query, org.eclipse.acceleo.model.mtl.Query> ioQuery = new HashMap<org.eclipse.acceleo.parser.cst.Query, org.eclipse.acceleo.model.mtl.Query>();

	/**
	 * To get an AST Query corresponding to a CST Comment.
	 */
	private Map<org.eclipse.acceleo.parser.cst.Comment, org.eclipse.acceleo.model.mtl.Comment> ioComment = new HashMap<org.eclipse.acceleo.parser.cst.Comment, org.eclipse.acceleo.model.mtl.Comment>();

	/**
	 * To get an AST Query corresponding to a CST Documentation.
	 */
	private Map<org.eclipse.acceleo.parser.cst.Documentation, org.eclipse.acceleo.model.mtl.Documentation> ioDocumentation = new HashMap<org.eclipse.acceleo.parser.cst.Documentation, org.eclipse.acceleo.model.mtl.Documentation>();

	/**
	 * Initializes the OCL environment.
	 * 
	 * @param resourceOCL
	 *            is the resource used to keep the OCL environment
	 */
	public void initOCL(Resource resourceOCL) {
		if (pOCL == null) {
			pOCL = new OCLParser(resourceOCL);
		} else {
			pOCL.init(resourceOCL);
		}
	}

	/**
	 * Gets the OCL parser (from the OCL plug-in).
	 * 
	 * @return the OCL parser, can be null
	 */
	public OCLParser getOCL() {
		return pOCL;
	}

	/**
	 * Sets the log Handler to save AST logging messages.
	 * 
	 * @param logHandler
	 *            is the new log handler
	 */
	public void setLogHandler(IASTProvider logHandler) {
		this.logHandler = logHandler;
	}

	/**
	 * Gets or creates an AST Module corresponding to a CST Module.
	 * 
	 * @param iModule
	 *            is the input object of type 'org.eclipse.acceleo.parser.cst.Module'
	 * @return the output object of type 'org.eclipse.acceleo.model.mtl.Module'
	 */
	protected org.eclipse.acceleo.model.mtl.Module getOrCreateModule(
			org.eclipse.acceleo.parser.cst.Module iModule) {
		if (iModule != null) {
			org.eclipse.acceleo.model.mtl.Module oModule = ioModule.get(iModule);
			if (oModule == null) {
				oModule = org.eclipse.acceleo.model.mtl.MtlFactory.eINSTANCE.createModule();
				ioModule.put(iModule, oModule);
			}
			return oModule;
		}
		return null;
	}

	/**
	 * Gets or creates an AST TypedModel corresponding to a CST TypedModel.
	 * 
	 * @param iTypedModel
	 *            is the input object of type 'org.eclipse.acceleo.parser.cst.TypedModel'
	 * @return the output object of type 'org.eclipse.acceleo.model.mtl.TypedModel'
	 */
	protected org.eclipse.acceleo.model.mtl.TypedModel getOrCreateTypedModel(
			org.eclipse.acceleo.parser.cst.TypedModel iTypedModel) {
		if (iTypedModel != null) {
			org.eclipse.acceleo.model.mtl.TypedModel oTypedModel = ioTypedModel.get(iTypedModel);
			if (oTypedModel == null) {
				oTypedModel = org.eclipse.acceleo.model.mtl.MtlFactory.eINSTANCE.createTypedModel();
				ioTypedModel.put(iTypedModel, oTypedModel);
			}
			return oTypedModel;
		}
		return null;
	}

	/**
	 * Gets or creates an AST Template corresponding to a CST Template.
	 * 
	 * @param iTemplate
	 *            is the input object of type 'org.eclipse.acceleo.parser.cst.Template'
	 * @return the output object of type 'org.eclipse.acceleo.model.mtl.Template'
	 */
	protected org.eclipse.acceleo.model.mtl.Template getOrCreateTemplate(
			org.eclipse.acceleo.parser.cst.Template iTemplate) {
		if (iTemplate != null) {
			org.eclipse.acceleo.model.mtl.Template oTemplate = ioTemplate.get(iTemplate);
			if (oTemplate == null) {
				oTemplate = org.eclipse.acceleo.model.mtl.MtlFactory.eINSTANCE.createTemplate();
				ioTemplate.put(iTemplate, oTemplate);
			}
			return oTemplate;
		}
		return null;
	}

	/**
	 * Gets or creates an AST Variable corresponding to a CST Variable.
	 * 
	 * @param iVariable
	 *            is the input object of type 'org.eclipse.acceleo.parser.cst.Variable'
	 * @return the output object of type 'org.eclipse.ocl.ecore.Variable'
	 */
	protected org.eclipse.ocl.ecore.Variable getOrCreateVariable(
			org.eclipse.acceleo.parser.cst.Variable iVariable) {
		if (iVariable != null) {
			org.eclipse.ocl.ecore.Variable oVariable = ioVariable.get(iVariable);
			if (oVariable == null) {
				oVariable = org.eclipse.ocl.ecore.EcoreFactory.eINSTANCE.createVariable();
				ioVariable.put(iVariable, oVariable);
			}
			return oVariable;
		}
		return null;
	}

	/**
	 * Gets or creates an AST TemplateExpression corresponding to a CST TemplateExpression. The output object
	 * is a temporary object. It will be replaced by an OCL expression in the OCL step. It's a strategy to
	 * keep the good index in a list like 'Block.body'.
	 * 
	 * @param iTemplateExpression
	 *            is the input object of type 'org.eclipse.acceleo.parser.cst.TemplateExpression'
	 * @return the output object of type 'org.eclipse.acceleo.model.mtl.TemplateExpression'
	 */
	protected org.eclipse.acceleo.model.mtl.TemplateExpression getOrCreateTemporaryTemplateExpression(
			org.eclipse.acceleo.parser.cst.TemplateExpression iTemplateExpression) {
		if (iTemplateExpression != null) {
			org.eclipse.acceleo.model.mtl.TemplateExpression oTemplateExpression = ioTemplateExpression
					.get(iTemplateExpression);
			if (oTemplateExpression == null) {
				oTemplateExpression = org.eclipse.acceleo.model.mtl.MtlFactory.eINSTANCE
						.createTemplateExpression();
				ioTemplateExpression.put(iTemplateExpression, oTemplateExpression);
			}
			return oTemplateExpression;
		}
		return null;
	}

	/**
	 * Gets (but not create) an AST TemplateExpression corresponding to a CST TemplateExpression. The output
	 * object is a temporary object. It will be replaced by an OCL expression in the OCL step. It's a strategy
	 * to keep the good index in a list like 'Block.body'.
	 * 
	 * @param iTemplateExpression
	 *            is the input object of type 'org.eclipse.acceleo.parser.cst.TemplateExpression'
	 * @return the output object of type 'org.eclipse.acceleo.model.mtl.TemplateExpression'
	 */
	protected org.eclipse.acceleo.model.mtl.TemplateExpression getTemporaryTemplateExpression(
			org.eclipse.acceleo.parser.cst.TemplateExpression iTemplateExpression) {
		if (iTemplateExpression != null) {
			org.eclipse.acceleo.model.mtl.TemplateExpression oTemplateExpression = ioTemplateExpression
					.get(iTemplateExpression);
			return oTemplateExpression;
		}
		return null;
	}

	/**
	 * Gets or creates an AST OCLExpression corresponding to a CST OCLExpression.
	 * 
	 * @param iModelExpression
	 *            is the input object of type 'org.eclipse.acceleo.parser.cst.ModelExpression'
	 * @return the output object of type 'org.eclipse.ocl.ecore.OCLExpression'
	 */
	protected org.eclipse.ocl.ecore.OCLExpression getOrCreateOCLExpression(
			org.eclipse.acceleo.parser.cst.ModelExpression iModelExpression) {
		if (iModelExpression != null) {
			org.eclipse.ocl.ecore.OCLExpression oOCLExpression = ioModelExpression.get(iModelExpression);
			if (oOCLExpression == null) {
				try {
					oOCLExpression = pOCL.parseOCLExpression(iModelExpression.getBody(), iModelExpression
							.getStartPosition(), iModelExpression);
					ioModelExpression.put(iModelExpression, oOCLExpression);
				} catch (WrappedOCLException e) {
					if (e.getStartPosition() > -1 && e.getEndPosition() > -1) {
						logProblem(e.getMessage(),
								iModelExpression.getStartPosition() + e.getStartPosition(), iModelExpression
										.getStartPosition()
										+ e.getEndPosition());
					} else {
						logProblem(e.getMessage(), iModelExpression.getStartPosition(), iModelExpression
								.getEndPosition());
					}
				} catch (ParserException e) {
					logProblem(e.getMessage(), iModelExpression.getStartPosition(), iModelExpression
							.getEndPosition());
				}
			}
			return oOCLExpression;
		}
		return null;
	}

	/**
	 * Gets or creates an AST StringLiteralExp corresponding to a CST TextExpression.
	 * 
	 * @param iTextExpression
	 *            is the input object of type 'org.eclipse.acceleo.parser.cst.TextExpression'
	 * @return the output object of type 'org.eclipse.ocl.ecore.StringLiteralExp'
	 */
	protected org.eclipse.ocl.ecore.StringLiteralExp getOrCreateStringLiteralExp(
			org.eclipse.acceleo.parser.cst.TextExpression iTextExpression) {
		if (iTextExpression != null) {
			org.eclipse.ocl.ecore.StringLiteralExp oTextExpression = ioTextExpression.get(iTextExpression);
			if (oTextExpression == null) {
				oTextExpression = org.eclipse.ocl.ecore.EcoreFactory.eINSTANCE.createStringLiteralExp();
				ioTextExpression.put(iTextExpression, oTextExpression);
			}
			return oTextExpression;
		}
		return null;
	}

	/**
	 * Gets or creates an AST Block corresponding to a CST Block.
	 * 
	 * @param iBlock
	 *            is the input object of type 'org.eclipse.acceleo.parser.cst.Block'
	 * @return the output object of type 'org.eclipse.acceleo.model.mtl.Block'
	 */
	protected org.eclipse.acceleo.model.mtl.Block getOrCreateBlock(org.eclipse.acceleo.parser.cst.Block iBlock) {
		if (iBlock != null) {
			org.eclipse.acceleo.model.mtl.Block oBlock = ioBlock.get(iBlock);
			if (oBlock == null) {
				oBlock = org.eclipse.acceleo.model.mtl.MtlFactory.eINSTANCE.createBlock();
				ioBlock.put(iBlock, oBlock);
			}
			return oBlock;
		}
		return null;
	}

	/**
	 * Gets or creates an AST InitSection corresponding to a CST InitSection.
	 * 
	 * @param iInitSection
	 *            is the input object of type 'org.eclipse.acceleo.parser.cst.InitSection'
	 * @return the output object of type 'org.eclipse.acceleo.model.mtl.InitSection'
	 */
	protected org.eclipse.acceleo.model.mtl.InitSection getOrCreateInitSection(
			org.eclipse.acceleo.parser.cst.InitSection iInitSection) {
		if (iInitSection != null) {
			org.eclipse.acceleo.model.mtl.InitSection oInitSection = ioInitSection.get(iInitSection);
			if (oInitSection == null) {
				oInitSection = org.eclipse.acceleo.model.mtl.MtlFactory.eINSTANCE.createInitSection();
				ioInitSection.put(iInitSection, oInitSection);
			}
			return oInitSection;
		}
		return null;
	}

	/**
	 * Gets or creates an AST ProtectedAreaBlock corresponding to a CST ProtectedAreaBlock.
	 * 
	 * @param iProtectedAreaBlock
	 *            is the input object of type 'org.eclipse.acceleo.parser.cst.ProtectedAreaBlock'
	 * @return the output object of type 'org.eclipse.acceleo.model.mtl.ProtectedAreaBlock'
	 */
	protected org.eclipse.acceleo.model.mtl.ProtectedAreaBlock getOrCreateProtectedAreaBlock(
			org.eclipse.acceleo.parser.cst.ProtectedAreaBlock iProtectedAreaBlock) {
		if (iProtectedAreaBlock != null) {
			org.eclipse.acceleo.model.mtl.ProtectedAreaBlock oProtectedAreaBlock = ioProtectedAreaBlock
					.get(iProtectedAreaBlock);
			if (oProtectedAreaBlock == null) {
				oProtectedAreaBlock = org.eclipse.acceleo.model.mtl.MtlFactory.eINSTANCE
						.createProtectedAreaBlock();
				ioProtectedAreaBlock.put(iProtectedAreaBlock, oProtectedAreaBlock);
			}
			return oProtectedAreaBlock;
		}
		return null;
	}

	/**
	 * Gets or creates an AST ForBlock corresponding to a CST ForBlock.
	 * 
	 * @param iForBlock
	 *            is the input object of type 'org.eclipse.acceleo.parser.cst.ForBlock'
	 * @return the output object of type 'org.eclipse.acceleo.model.mtl.ForBlock'
	 */
	protected org.eclipse.acceleo.model.mtl.ForBlock getOrCreateForBlock(
			org.eclipse.acceleo.parser.cst.ForBlock iForBlock) {
		if (iForBlock != null) {
			org.eclipse.acceleo.model.mtl.ForBlock oForBlock = ioForBlock.get(iForBlock);
			if (oForBlock == null) {
				oForBlock = org.eclipse.acceleo.model.mtl.MtlFactory.eINSTANCE.createForBlock();
				ioForBlock.put(iForBlock, oForBlock);
			}
			return oForBlock;
		}
		return null;
	}

	/**
	 * Gets or creates an AST IfBlock corresponding to a CST IfBlock.
	 * 
	 * @param iIfBlock
	 *            is the input object of type 'org.eclipse.acceleo.parser.cst.IfBlock'
	 * @return the output object of type 'org.eclipse.acceleo.model.mtl.IfBlock'
	 */
	protected org.eclipse.acceleo.model.mtl.IfBlock getOrCreateIfBlock(
			org.eclipse.acceleo.parser.cst.IfBlock iIfBlock) {
		if (iIfBlock != null) {
			org.eclipse.acceleo.model.mtl.IfBlock oIfBlock = ioIfBlock.get(iIfBlock);
			if (oIfBlock == null) {
				oIfBlock = org.eclipse.acceleo.model.mtl.MtlFactory.eINSTANCE.createIfBlock();
				ioIfBlock.put(iIfBlock, oIfBlock);
			}
			return oIfBlock;
		}
		return null;
	}

	/**
	 * Gets or creates an AST LetBlock corresponding to a CST LetBlock.
	 * 
	 * @param iLetBlock
	 *            is the input object of type 'org.eclipse.acceleo.parser.cst.LetBlock'
	 * @return the output object of type 'org.eclipse.acceleo.model.mtl.LetBlock'
	 */
	protected org.eclipse.acceleo.model.mtl.LetBlock getOrCreateLetBlock(
			org.eclipse.acceleo.parser.cst.LetBlock iLetBlock) {
		if (iLetBlock != null) {
			org.eclipse.acceleo.model.mtl.LetBlock oLetBlock = ioLetBlock.get(iLetBlock);
			if (oLetBlock == null) {
				oLetBlock = org.eclipse.acceleo.model.mtl.MtlFactory.eINSTANCE.createLetBlock();
				ioLetBlock.put(iLetBlock, oLetBlock);
			}
			return oLetBlock;
		}
		return null;
	}

	/**
	 * Gets or creates an AST FileBlock corresponding to a CST FileBlock.
	 * 
	 * @param iFileBlock
	 *            is the input object of type 'org.eclipse.acceleo.parser.cst.FileBlock'
	 * @return the output object of type 'org.eclipse.acceleo.model.mtl.FileBlock'
	 */
	protected org.eclipse.acceleo.model.mtl.FileBlock getOrCreateFileBlock(
			org.eclipse.acceleo.parser.cst.FileBlock iFileBlock) {
		if (iFileBlock != null) {
			org.eclipse.acceleo.model.mtl.FileBlock oFileBlock = ioFileBlock.get(iFileBlock);
			if (oFileBlock == null) {
				oFileBlock = org.eclipse.acceleo.model.mtl.MtlFactory.eINSTANCE.createFileBlock();
				ioFileBlock.put(iFileBlock, oFileBlock);
			}
			return oFileBlock;
		}
		return null;
	}

	/**
	 * Gets or creates an AST TraceBlock corresponding to a CST TraceBlock.
	 * 
	 * @param iTraceBlock
	 *            is the input object of type 'org.eclipse.acceleo.parser.cst.TraceBlock'
	 * @return the output object of type 'org.eclipse.acceleo.model.mtl.TraceBlock'
	 */
	protected org.eclipse.acceleo.model.mtl.TraceBlock getOrCreateTraceBlock(
			org.eclipse.acceleo.parser.cst.TraceBlock iTraceBlock) {
		if (iTraceBlock != null) {
			org.eclipse.acceleo.model.mtl.TraceBlock oTraceBlock = ioTraceBlock.get(iTraceBlock);
			if (oTraceBlock == null) {
				oTraceBlock = org.eclipse.acceleo.model.mtl.MtlFactory.eINSTANCE.createTraceBlock();
				ioTraceBlock.put(iTraceBlock, oTraceBlock);
			}
			return oTraceBlock;
		}
		return null;
	}

	/**
	 * Gets or creates an AST Macro corresponding to a CST Macro.
	 * 
	 * @param iMacro
	 *            is the input object of type 'org.eclipse.acceleo.parser.cst.Macro'
	 * @return the output object of type 'org.eclipse.acceleo.model.mtl.Macro'
	 */
	protected org.eclipse.acceleo.model.mtl.Macro getOrCreateMacro(org.eclipse.acceleo.parser.cst.Macro iMacro) {
		if (iMacro != null) {
			org.eclipse.acceleo.model.mtl.Macro oMacro = ioMacro.get(iMacro);
			if (oMacro == null) {
				oMacro = org.eclipse.acceleo.model.mtl.MtlFactory.eINSTANCE.createMacro();
				ioMacro.put(iMacro, oMacro);
			}
			return oMacro;
		}
		return null;
	}

	/**
	 * Gets or creates an AST Query corresponding to a CST Query.
	 * 
	 * @param iQuery
	 *            is the input object of type 'org.eclipse.acceleo.parser.cst.Query'
	 * @return the output object of type 'org.eclipse.acceleo.model.mtl.Query'
	 */
	protected org.eclipse.acceleo.model.mtl.Query getOrCreateQuery(org.eclipse.acceleo.parser.cst.Query iQuery) {
		if (iQuery != null) {
			org.eclipse.acceleo.model.mtl.Query oQuery = ioQuery.get(iQuery);
			if (oQuery == null) {
				oQuery = org.eclipse.acceleo.model.mtl.MtlFactory.eINSTANCE.createQuery();
				ioQuery.put(iQuery, oQuery);
			}
			return oQuery;
		}
		return null;
	}

	/**
	 * Gets or creates an AST Comment corresponding to a CST Comment.
	 * 
	 * @param iComment
	 *            is the input object of type 'org.eclipse.acceleo.parser.cst.Comment'
	 * @return the output object of type 'org.eclipse.acceleo.model.mtl.Comment'
	 */
	protected org.eclipse.acceleo.model.mtl.Comment getOrCreateComment(
			org.eclipse.acceleo.parser.cst.Comment iComment) {
		if (iComment != null) {
			org.eclipse.acceleo.model.mtl.Comment oComment = ioComment.get(iComment);
			if (oComment == null) {
				oComment = org.eclipse.acceleo.model.mtl.MtlFactory.eINSTANCE.createComment();
				ioComment.put(iComment, oComment);
			}
			return oComment;
		}
		return null;
	}

	/**
	 * Gets or creates an AST Documentation corresponding to a CST Documentation.
	 * 
	 * @param iDocumentation
	 *            is the input object of type 'org.eclipse.acceleo.parser.cst.Documentation'
	 * @return the output object of type 'org.eclipse.acceleo.model.mtl.Documentation'
	 */
	protected org.eclipse.acceleo.model.mtl.Documentation getOrCreateDocumentation(
			org.eclipse.acceleo.parser.cst.Documentation iDocumentation) {
		if (iDocumentation != null) {
			org.eclipse.acceleo.model.mtl.Documentation oDocumentation = ioDocumentation.get(iDocumentation);
			if (oDocumentation == null) {
				oDocumentation = org.eclipse.acceleo.model.mtl.MtlFactory.eINSTANCE.createDocumentation();
				ioDocumentation.put(iDocumentation, oDocumentation);
			}
			return oDocumentation;
		}
		return null;
	}

	/**
	 * To log a new problem.
	 * 
	 * @param message
	 *            is the message
	 * @param posBegin
	 *            is the beginning index of the problem
	 * @param posEnd
	 *            is the ending index of the problem
	 */
	protected void logProblem(String message, int posBegin, int posEnd) {
		if (logHandler != null) {
			logHandler.logProblem(message, posBegin, posEnd);
		}
	}

	/**
	 * Clears some cache of the ASTFactory.
	 */
	public void clear() {
		this.pOCL.dispose();
	}

}

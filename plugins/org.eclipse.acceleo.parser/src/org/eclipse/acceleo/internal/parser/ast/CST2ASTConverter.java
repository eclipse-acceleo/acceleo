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
package org.eclipse.acceleo.internal.parser.ast;

import java.util.Iterator;
import java.util.List;

import org.eclipse.acceleo.common.IAcceleoConstants;
import org.eclipse.acceleo.internal.parser.ast.ocl.OCLParser;
import org.eclipse.acceleo.parser.cst.Block;
import org.eclipse.acceleo.parser.cst.Comment;
import org.eclipse.acceleo.parser.cst.CstPackage;
import org.eclipse.acceleo.parser.cst.ProtectedAreaBlock;
import org.eclipse.acceleo.parser.cst.Template;
import org.eclipse.emf.ecore.EAnnotation;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EcoreFactory;
import org.eclipse.emf.ecore.resource.Resource;

/**
 * The main class used to transform a CST model to an AST model. This class is not able to run the 'Resolve'
 * step.
 * 
 * @author <a href="mailto:jonathan.musset@obeo.fr">Jonathan Musset</a>
 */
public class CST2ASTConverter {

	/**
	 * The factory used to create the objects of the AST model.
	 */
	protected ASTFactory factory;

	/**
	 * A log Handler to save AST logging messages.
	 */
	protected IASTLogHandler logHandler;

	/**
	 * Indicates if the current CST to AST action has been canceled.
	 */
	protected boolean isCanceled;

	/**
	 * Constructor.
	 */
	public CST2ASTConverter() {
		super();
		factory = new ASTFactory();
		logHandler = null;
		isCanceled = false;
	}

	/**
	 * Gets the OCL parser (from the OCL plug-in).
	 * 
	 * @return the OCL parser
	 */
	public OCLParser getOCL() {
		return factory.getOCL();
	}

	/**
	 * Sets the log Handler to save AST logging messages.
	 * 
	 * @param logHandler
	 *            is the new log handler
	 */
	public void setLogHandler(IASTLogHandler logHandler) {
		this.logHandler = logHandler;
		factory.setLogHandler(logHandler);
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
	protected void log(String message, int posBegin, int posEnd) {
		if (logHandler != null) {
			logHandler.log(message, posBegin, posEnd);
		}
	}

	/**
	 * Creates an AST model in the given resource. This method is not able to run the 'Resolve' step.
	 * 
	 * @param rootCST
	 *            is the root element of the input module (CST)
	 * @param resourceAST
	 *            is the resource to be updated with the output model (AST)
	 */
	public void createAST(org.eclipse.acceleo.parser.cst.Module rootCST, Resource resourceAST) {
		if (rootCST != null) {
			org.eclipse.acceleo.model.mtl.Module oModule = factory.getOrCreateModule(rootCST);
			resourceAST.getContents().add(0, oModule);
			factory.initOCL(resourceAST);
			transformStepCopy(rootCST);
		}
	}

	/**
	 * The step 'StepCopy' of the transformation for each 'org.eclipse.acceleo.parser.cst.Module' of the input model.
	 * 
	 * @param iModule
	 *            is the input object of type 'org.eclipse.acceleo.parser.cst.Module'
	 */
	private void transformStepCopy(org.eclipse.acceleo.parser.cst.Module iModule) {
		org.eclipse.acceleo.model.mtl.Module oModule = factory.getOrCreateModule(iModule);
		if (iModule != null && oModule != null) {
			String ioName = iModule.getName();
			oModule.setName(ioName);

			transformStepCopyOwnedModuleElement(iModule, oModule);
			Iterator<org.eclipse.acceleo.parser.cst.TypedModel> iInputIt = iModule.getInput().iterator();
			while (iInputIt.hasNext()) {
				org.eclipse.acceleo.parser.cst.TypedModel iNext = iInputIt.next();
				org.eclipse.acceleo.model.mtl.TypedModel oNext = factory.getOrCreateTypedModel(iNext);
				if (oNext != null) {
					oModule.getInput().add(oNext);
				}
				transformStepCopy(iNext);

			}
		}
	}

	/**
	 * The step 'StepCopy' of the transformation for each 'org.eclipse.acceleo.parser.cst.TypedModel' of the input model.
	 * 
	 * @param iTypedModel
	 *            is the input object of type 'org.eclipse.acceleo.parser.cst.TypedModel'
	 */
	private void transformStepCopy(org.eclipse.acceleo.parser.cst.TypedModel iTypedModel) {
		org.eclipse.acceleo.model.mtl.TypedModel oTypedModel = factory.getOrCreateTypedModel(iTypedModel);
		if (iTypedModel != null && oTypedModel != null) {
			Iterator<EPackage> iTakesTypesFromIt = iTypedModel.getTakesTypesFrom().iterator();
			while (iTakesTypesFromIt.hasNext()) {
				EPackage ioNext = iTakesTypesFromIt.next();
				oTypedModel.getTakesTypesFrom().add(ioNext);
			}
		}
	}

	/**
	 * The step 'StepCopy' of the transformation for each 'org.eclipse.acceleo.parser.cst.Template' of the input model.
	 * 
	 * @param iTemplate
	 *            is the input object of type 'org.eclipse.acceleo.parser.cst.Template'
	 */
	private void transformStepCopy(org.eclipse.acceleo.parser.cst.Template iTemplate) {
		org.eclipse.acceleo.model.mtl.Template oTemplate = factory.getOrCreateTemplate(iTemplate);
		if (iTemplate != null && oTemplate != null) {
			org.eclipse.acceleo.parser.cst.VisibilityKind iVisibility = iTemplate.getVisibility();
			org.eclipse.acceleo.model.mtl.VisibilityKind oVisibility = org.eclipse.acceleo.model.mtl.VisibilityKind.get(iVisibility
					.getValue());
			oTemplate.setVisibility(oVisibility);
			transformStepCopyPositions(iTemplate, oTemplate);
			String ioName = iTemplate.getName();
			oTemplate.setName(ioName);
			org.eclipse.acceleo.parser.cst.InitSection iInit = iTemplate.getInit();
			org.eclipse.acceleo.model.mtl.InitSection oInit = factory.getOrCreateInitSection(iInit);
			if (oInit != null) {
				oTemplate.setInit(oInit);
			}
			transformStepCopy(iInit);

			org.eclipse.acceleo.parser.cst.ModelExpression iGuard = iTemplate.getGuard();
			transformStepCopy(iGuard);

			transformStepCopyBody(iTemplate, oTemplate);
			Iterator<org.eclipse.acceleo.parser.cst.Variable> iParameterIt = iTemplate.getParameter().iterator();
			while (iParameterIt.hasNext()) {
				org.eclipse.acceleo.parser.cst.Variable iNext = iParameterIt.next();
				org.eclipse.ocl.ecore.Variable oNext = factory.getOrCreateVariable(iNext);
				if (oNext != null) {
					oTemplate.getParameter().add(oNext);
				}
				transformStepCopy(iNext);

			}

			boolean isMain = false;
			Iterator<EObject> iChildren = iTemplate.eAllContents();
			while (!isMain && iChildren.hasNext()) {
				EObject iChild = iChildren.next();
				if (iChild instanceof Comment && ((Comment)iChild).getBody() != null
						&& ((Comment)iChild).getBody().indexOf(IAcceleoConstants.TAG_MAIN) > -1) {
					isMain = true;
				}
			}
			oTemplate.setMain(isMain);
		}
	}

	/**
	 * The step 'StepCopy' of the transformation for each 'org.eclipse.acceleo.parser.cst.Variable' of the input model.
	 * 
	 * @param iVariable
	 *            is the input object of type 'org.eclipse.acceleo.parser.cst.Variable'
	 */
	private void transformStepCopy(org.eclipse.acceleo.parser.cst.Variable iVariable) {
		org.eclipse.ocl.ecore.Variable oVariable = factory.getOrCreateVariable(iVariable);
		if (iVariable != null && oVariable != null) {
			transformStepCopyPositions(iVariable, oVariable);
			String ioName = iVariable.getName();
			oVariable.setName(ioName);
			String ioType = iVariable.getType();
			EAnnotation eAnnotation = EcoreFactory.eINSTANCE.createEAnnotation();
			eAnnotation.setSource(OCLParser.ANNOTATION_SOURCE);
			eAnnotation.getDetails().put(OCLParser.ANNOTATION_KEY_TYPE, ioType);
			oVariable.getEAnnotations().add(eAnnotation);
		}
	}

	/**
	 * The step 'StepCopy' of the transformation for each 'org.eclipse.acceleo.parser.cst.ModelExpression' of the input
	 * model.
	 * 
	 * @param iModelExpression
	 *            is the input object of type 'org.eclipse.acceleo.parser.cst.ModelExpression'
	 */
	private void transformStepCopy(org.eclipse.acceleo.parser.cst.ModelExpression iModelExpression) {
		// wait step OCL
	}

	/**
	 * The step 'StepCopy' of the transformation for each 'org.eclipse.acceleo.parser.cst.TextExpression' of the input
	 * model.
	 * 
	 * @param iTextExpression
	 *            is the input object of type 'org.eclipse.acceleo.parser.cst.TextExpression'
	 */
	private void transformStepCopy(org.eclipse.acceleo.parser.cst.TextExpression iTextExpression) {
		org.eclipse.ocl.ecore.StringLiteralExp oTextExpression = factory
				.getOrCreateStringLiteralExp(iTextExpression);
		if (iTextExpression != null && oTextExpression != null) {
			transformStepCopyPositions(iTextExpression, oTextExpression);
			transformFormattedText(iTextExpression, oTextExpression);
		}
	}

	/**
	 * Deletes the characters to ignore at the beginning and at the end of the given text. (Indentation
	 * strategy method)
	 * 
	 * @param iTextExpression
	 *            is the input object of type 'org.eclipse.acceleo.parser.cst.TextExpression'
	 * @param oTextExpression
	 *            is the output object of type 'org.eclipse.ocl.ecore.StringLiteralExp'
	 */
	@SuppressWarnings("unchecked")
	private void transformFormattedText(org.eclipse.acceleo.parser.cst.TextExpression iTextExpression,
			org.eclipse.ocl.ecore.StringLiteralExp oTextExpression) {
		String ioValue = iTextExpression.getValue();
		if (ioValue != null && iTextExpression.eContainingFeature() == CstPackage.eINSTANCE.getBlock_Body()
				&& iTextExpression.eContainer() != null) {
			List eBody = (List)iTextExpression.eContainer().eGet(CstPackage.eINSTANCE.getBlock_Body());
			if (eBody != null && eBody.size() > 0) {
				int index = eBody.indexOf(iTextExpression);
				int shiftBegin;
				if (index == 0 && iTextExpression.eContainer() instanceof ProtectedAreaBlock) {
					shiftBegin = 0;
				} else if (index == 0 || eBody.get(index - 1) instanceof Block) {
					shiftBegin = shiftBegin(ioValue);
				} else {
					shiftBegin = 0;
				}
				if (shiftBegin > ioValue.length()) {
					oTextExpression.setStartPosition(oTextExpression.getStartPosition() + ioValue.length());
					ioValue = ""; //$NON-NLS-1$
				} else if (shiftBegin > 0) {
					oTextExpression.setStartPosition(oTextExpression.getStartPosition() + shiftBegin);
					ioValue = ioValue.substring(shiftBegin);
				}
				int shiftEnd;
				if (index == eBody.size() - 1 && iTextExpression.eContainer() instanceof Template) {
					shiftEnd = shiftEnd(ioValue, false);
				} else if (index == eBody.size() - 1) {
					shiftEnd = shiftEnd(ioValue, true);
				} else if (index + 1 < eBody.size() && eBody.get(index + 1) instanceof Block) {
					shiftEnd = shiftEnd(ioValue, true);
				} else {
					shiftEnd = 0;
				}
				if (shiftEnd > ioValue.length()) {
					oTextExpression.setEndPosition(oTextExpression.getEndPosition() - ioValue.length());
					ioValue = ""; //$NON-NLS-1$
				} else if (shiftEnd > 0) {
					oTextExpression.setEndPosition(oTextExpression.getEndPosition() - shiftEnd);
					ioValue = ioValue.substring(0, ioValue.length() - shiftEnd);
				}

			}
		}
		oTextExpression.setStringSymbol(ioValue);
	}

	/**
	 * Gets the characters to ignore at the beginning of the given text.
	 * 
	 * @param ioValue
	 *            is the text to format
	 * @return the number of characters to ignore
	 */
	private int shiftBegin(String ioValue) {
		int shiftBegin = -1;
		for (int b = 0; shiftBegin == -1 && b < ioValue.length(); b++) {
			char c = ioValue.charAt(b);
			if (c == '\n') {
				shiftBegin = b + 1;
			} else if (!Character.isWhitespace(c)) {
				shiftBegin = 0;
			}
		}
		if (shiftBegin == -1) {
			shiftBegin = ioValue.length();
		}
		return shiftBegin;
	}

	/**
	 * Gets the characters to ignore at the end of the given text.
	 * 
	 * @param ioValue
	 *            is the text to format
	 * @param keepPreviousReturn
	 *            indicates if the we have to keep the new line character
	 * @return the number of characters to ignore
	 */
	private int shiftEnd(String ioValue, boolean keepPreviousReturn) {
		int shiftEnd = -1;
		for (int e = ioValue.length() - 1; shiftEnd == -1 && e >= 0; e--) {
			char c = ioValue.charAt(e);
			if (c == '\n') {
				if (keepPreviousReturn) {
					shiftEnd = ioValue.length() - (e + 1);
				} else if (e > 0 && ioValue.charAt(e - 1) == '\r') {
					shiftEnd = ioValue.length() - (e - 1);
				} else {
					shiftEnd = ioValue.length() - e;
				}
			} else if (!Character.isWhitespace(c)) {
				shiftEnd = 0;
			}
		}
		if (shiftEnd == -1) {
			shiftEnd = ioValue.length();
		}
		return shiftEnd;
	}

	/**
	 * The step 'StepCopy' of the transformation for each 'org.eclipse.acceleo.parser.cst.Block' of the input model.
	 * 
	 * @param iBlock
	 *            is the input object of type 'org.eclipse.acceleo.parser.cst.Block'
	 */
	private void transformStepCopy(org.eclipse.acceleo.parser.cst.Block iBlock) {
		org.eclipse.acceleo.model.mtl.Block oBlock = factory.getOrCreateBlock(iBlock);
		if (iBlock != null && oBlock != null) {
			transformStepCopyPositions(iBlock, oBlock);
			org.eclipse.acceleo.parser.cst.InitSection iInit = iBlock.getInit();
			org.eclipse.acceleo.model.mtl.InitSection oInit = factory.getOrCreateInitSection(iInit);
			if (oInit != null) {
				oBlock.setInit(oInit);
			}
			transformStepCopy(iInit);

			transformStepCopyBody(iBlock, oBlock);
		}
	}

	/**
	 * The step 'StepCopy' of the transformation for each 'org.eclipse.acceleo.parser.cst.InitSection' of the input
	 * model.
	 * 
	 * @param iInitSection
	 *            is the input object of type 'org.eclipse.acceleo.parser.cst.InitSection'
	 */
	private void transformStepCopy(org.eclipse.acceleo.parser.cst.InitSection iInitSection) {
		org.eclipse.acceleo.model.mtl.InitSection oInitSection = factory.getOrCreateInitSection(iInitSection);
		if (iInitSection != null && oInitSection != null) {
			transformStepCopyPositions(iInitSection, oInitSection);
			Iterator<org.eclipse.acceleo.parser.cst.Variable> iVariableIt = iInitSection.getVariable().iterator();
			while (iVariableIt.hasNext()) {
				org.eclipse.acceleo.parser.cst.Variable iNext = iVariableIt.next();
				org.eclipse.ocl.ecore.Variable oNext = factory.getOrCreateVariable(iNext);
				if (oNext != null) {
					oInitSection.getVariable().add(oNext);
				}
				transformStepCopy(iNext);

			}
		}
	}

	/**
	 * The step 'StepCopy' of the transformation for each 'org.eclipse.acceleo.parser.cst.ProtectedAreaBlock' of the
	 * input model.
	 * 
	 * @param iProtectedAreaBlock
	 *            is the input object of type 'org.eclipse.acceleo.parser.cst.ProtectedAreaBlock'
	 */
	private void transformStepCopy(org.eclipse.acceleo.parser.cst.ProtectedAreaBlock iProtectedAreaBlock) {
		org.eclipse.acceleo.model.mtl.ProtectedAreaBlock oProtectedAreaBlock = factory
				.getOrCreateProtectedAreaBlock(iProtectedAreaBlock);
		if (iProtectedAreaBlock != null && oProtectedAreaBlock != null) {
			transformStepCopyPositions(iProtectedAreaBlock, oProtectedAreaBlock);
			org.eclipse.acceleo.parser.cst.InitSection iInit = iProtectedAreaBlock.getInit();
			org.eclipse.acceleo.model.mtl.InitSection oInit = factory.getOrCreateInitSection(iInit);
			if (oInit != null) {
				oProtectedAreaBlock.setInit(oInit);
			}
			transformStepCopy(iInit);

			org.eclipse.acceleo.parser.cst.ModelExpression iMarker = iProtectedAreaBlock.getMarker();
			transformStepCopy(iMarker);

			transformStepCopyBody(iProtectedAreaBlock, oProtectedAreaBlock);
		}
	}

	/**
	 * The step 'StepCopy' of the transformation for each 'org.eclipse.acceleo.parser.cst.ForBlock' of the input model.
	 * 
	 * @param iForBlock
	 *            is the input object of type 'org.eclipse.acceleo.parser.cst.ForBlock'
	 */
	private void transformStepCopy(org.eclipse.acceleo.parser.cst.ForBlock iForBlock) {
		org.eclipse.acceleo.model.mtl.ForBlock oForBlock = factory.getOrCreateForBlock(iForBlock);
		if (iForBlock != null && oForBlock != null) {
			transformStepCopyPositions(iForBlock, oForBlock);
			org.eclipse.acceleo.parser.cst.InitSection iInit = iForBlock.getInit();
			org.eclipse.acceleo.model.mtl.InitSection oInit = factory.getOrCreateInitSection(iInit);
			if (oInit != null) {
				oForBlock.setInit(oInit);
			}
			transformStepCopy(iInit);

			org.eclipse.acceleo.parser.cst.ModelExpression iIterSet = iForBlock.getIterSet();
			transformStepCopy(iIterSet);

			org.eclipse.acceleo.parser.cst.ModelExpression iBefore = iForBlock.getBefore();
			transformStepCopy(iBefore);

			org.eclipse.acceleo.parser.cst.ModelExpression iEach = iForBlock.getEach();
			transformStepCopy(iEach);

			org.eclipse.acceleo.parser.cst.ModelExpression iAfter = iForBlock.getAfter();
			transformStepCopy(iAfter);

			org.eclipse.acceleo.parser.cst.ModelExpression iGuard = iForBlock.getGuard();
			transformStepCopy(iGuard);

			transformStepCopyBody(iForBlock, oForBlock);
			org.eclipse.acceleo.parser.cst.Variable iLoopVariable = iForBlock.getLoopVariable();
			org.eclipse.ocl.ecore.Variable oLoopVariable = factory.getOrCreateVariable(iLoopVariable);
			if (oLoopVariable != null) {
				oForBlock.setLoopVariable(oLoopVariable);
			}
			transformStepCopy(iLoopVariable);

		}
	}

	/**
	 * The step 'StepCopy' of the transformation for each 'org.eclipse.acceleo.parser.cst.IfBlock' of the input model.
	 * 
	 * @param iIfBlock
	 *            is the input object of type 'org.eclipse.acceleo.parser.cst.IfBlock'
	 */
	private void transformStepCopy(org.eclipse.acceleo.parser.cst.IfBlock iIfBlock) {
		org.eclipse.acceleo.model.mtl.IfBlock oIfBlock = factory.getOrCreateIfBlock(iIfBlock);
		if (iIfBlock != null && oIfBlock != null) {
			org.eclipse.acceleo.parser.cst.Block iElse = iIfBlock.getElse();
			if (iElse instanceof org.eclipse.acceleo.parser.cst.Template) {
				org.eclipse.acceleo.model.mtl.Template oElse = factory
						.getOrCreateTemplate((org.eclipse.acceleo.parser.cst.Template)iElse);
				if (oElse != null) {
					oIfBlock.setElse(oElse);
				}
				transformStepCopy((org.eclipse.acceleo.parser.cst.Template)iElse);
			} else if (iElse instanceof org.eclipse.acceleo.parser.cst.ProtectedAreaBlock) {
				org.eclipse.acceleo.model.mtl.ProtectedAreaBlock oElse = factory
						.getOrCreateProtectedAreaBlock((org.eclipse.acceleo.parser.cst.ProtectedAreaBlock)iElse);
				if (oElse != null) {
					oIfBlock.setElse(oElse);
				}
				transformStepCopy((org.eclipse.acceleo.parser.cst.ProtectedAreaBlock)iElse);
			} else if (iElse instanceof org.eclipse.acceleo.parser.cst.ForBlock) {
				org.eclipse.acceleo.model.mtl.ForBlock oElse = factory
						.getOrCreateForBlock((org.eclipse.acceleo.parser.cst.ForBlock)iElse);
				if (oElse != null) {
					oIfBlock.setElse(oElse);
				}
				transformStepCopy((org.eclipse.acceleo.parser.cst.ForBlock)iElse);
			} else if (iElse instanceof org.eclipse.acceleo.parser.cst.IfBlock) {
				org.eclipse.acceleo.model.mtl.IfBlock oElse = factory
						.getOrCreateIfBlock((org.eclipse.acceleo.parser.cst.IfBlock)iElse);
				if (oElse != null) {
					oIfBlock.setElse(oElse);
				}
				transformStepCopy((org.eclipse.acceleo.parser.cst.IfBlock)iElse);
			} else if (iElse instanceof org.eclipse.acceleo.parser.cst.LetBlock) {
				org.eclipse.acceleo.model.mtl.LetBlock oElse = factory
						.getOrCreateLetBlock((org.eclipse.acceleo.parser.cst.LetBlock)iElse);
				if (oElse != null) {
					oIfBlock.setElse(oElse);
				}
				transformStepCopy((org.eclipse.acceleo.parser.cst.LetBlock)iElse);
			} else if (iElse instanceof org.eclipse.acceleo.parser.cst.FileBlock) {
				org.eclipse.acceleo.model.mtl.FileBlock oElse = factory
						.getOrCreateFileBlock((org.eclipse.acceleo.parser.cst.FileBlock)iElse);
				if (oElse != null) {
					oIfBlock.setElse(oElse);
				}
				transformStepCopy((org.eclipse.acceleo.parser.cst.FileBlock)iElse);
			} else if (iElse instanceof org.eclipse.acceleo.parser.cst.TraceBlock) {
				org.eclipse.acceleo.model.mtl.TraceBlock oElse = factory
						.getOrCreateTraceBlock((org.eclipse.acceleo.parser.cst.TraceBlock)iElse);
				if (oElse != null) {
					oIfBlock.setElse(oElse);
				}
				transformStepCopy((org.eclipse.acceleo.parser.cst.TraceBlock)iElse);
			} else if (iElse instanceof org.eclipse.acceleo.parser.cst.Macro) {
				org.eclipse.acceleo.model.mtl.Macro oElse = factory.getOrCreateMacro((org.eclipse.acceleo.parser.cst.Macro)iElse);
				if (oElse != null) {
					oIfBlock.setElse(oElse);
				}
				transformStepCopy((org.eclipse.acceleo.parser.cst.Macro)iElse);
			} else {
				org.eclipse.acceleo.model.mtl.Block oElse = factory.getOrCreateBlock(iElse);
				if (oElse != null) {
					oIfBlock.setElse(oElse);
				}
				transformStepCopy(iElse);
			}

			transformStepCopyPositions(iIfBlock, oIfBlock);
			Iterator<org.eclipse.acceleo.parser.cst.IfBlock> iElseIfIt = iIfBlock.getElseIf().iterator();
			while (iElseIfIt.hasNext()) {
				org.eclipse.acceleo.parser.cst.IfBlock iNext = iElseIfIt.next();
				org.eclipse.acceleo.model.mtl.IfBlock oNext = factory.getOrCreateIfBlock(iNext);
				if (oNext != null) {
					oIfBlock.getElseIf().add(oNext);
				}
				transformStepCopy(iNext);

			}
			org.eclipse.acceleo.parser.cst.InitSection iInit = iIfBlock.getInit();
			org.eclipse.acceleo.model.mtl.InitSection oInit = factory.getOrCreateInitSection(iInit);
			if (oInit != null) {
				oIfBlock.setInit(oInit);
			}
			transformStepCopy(iInit);

			org.eclipse.acceleo.parser.cst.ModelExpression iIfExpr = iIfBlock.getIfExpr();
			transformStepCopy(iIfExpr);

			transformStepCopyBody(iIfBlock, oIfBlock);
		}
	}

	/**
	 * The step 'StepCopy' of the transformation for each 'org.eclipse.acceleo.parser.cst.LetBlock' of the input model.
	 * 
	 * @param iLetBlock
	 *            is the input object of type 'org.eclipse.acceleo.parser.cst.LetBlock'
	 */
	private void transformStepCopy(org.eclipse.acceleo.parser.cst.LetBlock iLetBlock) {
		org.eclipse.acceleo.model.mtl.LetBlock oLetBlock = factory.getOrCreateLetBlock(iLetBlock);
		if (iLetBlock != null && oLetBlock != null) {
			org.eclipse.acceleo.parser.cst.Block iElse = iLetBlock.getElse();
			if (iElse instanceof org.eclipse.acceleo.parser.cst.Template) {
				org.eclipse.acceleo.model.mtl.Template oElse = factory
						.getOrCreateTemplate((org.eclipse.acceleo.parser.cst.Template)iElse);
				if (oElse != null) {
					oLetBlock.setElse(oElse);
				}
				transformStepCopy((org.eclipse.acceleo.parser.cst.Template)iElse);
			} else if (iElse instanceof org.eclipse.acceleo.parser.cst.ProtectedAreaBlock) {
				org.eclipse.acceleo.model.mtl.ProtectedAreaBlock oElse = factory
						.getOrCreateProtectedAreaBlock((org.eclipse.acceleo.parser.cst.ProtectedAreaBlock)iElse);
				if (oElse != null) {
					oLetBlock.setElse(oElse);
				}
				transformStepCopy((org.eclipse.acceleo.parser.cst.ProtectedAreaBlock)iElse);
			} else if (iElse instanceof org.eclipse.acceleo.parser.cst.ForBlock) {
				org.eclipse.acceleo.model.mtl.ForBlock oElse = factory
						.getOrCreateForBlock((org.eclipse.acceleo.parser.cst.ForBlock)iElse);
				if (oElse != null) {
					oLetBlock.setElse(oElse);
				}
				transformStepCopy((org.eclipse.acceleo.parser.cst.ForBlock)iElse);
			} else if (iElse instanceof org.eclipse.acceleo.parser.cst.IfBlock) {
				org.eclipse.acceleo.model.mtl.IfBlock oElse = factory
						.getOrCreateIfBlock((org.eclipse.acceleo.parser.cst.IfBlock)iElse);
				if (oElse != null) {
					oLetBlock.setElse(oElse);
				}
				transformStepCopy((org.eclipse.acceleo.parser.cst.IfBlock)iElse);
			} else if (iElse instanceof org.eclipse.acceleo.parser.cst.LetBlock) {
				org.eclipse.acceleo.model.mtl.LetBlock oElse = factory
						.getOrCreateLetBlock((org.eclipse.acceleo.parser.cst.LetBlock)iElse);
				if (oElse != null) {
					oLetBlock.setElse(oElse);
				}
				transformStepCopy((org.eclipse.acceleo.parser.cst.LetBlock)iElse);
			} else if (iElse instanceof org.eclipse.acceleo.parser.cst.FileBlock) {
				org.eclipse.acceleo.model.mtl.FileBlock oElse = factory
						.getOrCreateFileBlock((org.eclipse.acceleo.parser.cst.FileBlock)iElse);
				if (oElse != null) {
					oLetBlock.setElse(oElse);
				}
				transformStepCopy((org.eclipse.acceleo.parser.cst.FileBlock)iElse);
			} else if (iElse instanceof org.eclipse.acceleo.parser.cst.TraceBlock) {
				org.eclipse.acceleo.model.mtl.TraceBlock oElse = factory
						.getOrCreateTraceBlock((org.eclipse.acceleo.parser.cst.TraceBlock)iElse);
				if (oElse != null) {
					oLetBlock.setElse(oElse);
				}
				transformStepCopy((org.eclipse.acceleo.parser.cst.TraceBlock)iElse);
			} else if (iElse instanceof org.eclipse.acceleo.parser.cst.Macro) {
				org.eclipse.acceleo.model.mtl.Macro oElse = factory.getOrCreateMacro((org.eclipse.acceleo.parser.cst.Macro)iElse);
				if (oElse != null) {
					oLetBlock.setElse(oElse);
				}
				transformStepCopy((org.eclipse.acceleo.parser.cst.Macro)iElse);
			} else {
				org.eclipse.acceleo.model.mtl.Block oElse = factory.getOrCreateBlock(iElse);
				if (oElse != null) {
					oLetBlock.setElse(oElse);
				}
				transformStepCopy(iElse);
			}

			transformStepCopyPositions(iLetBlock, oLetBlock);
			org.eclipse.acceleo.parser.cst.InitSection iInit = iLetBlock.getInit();
			org.eclipse.acceleo.model.mtl.InitSection oInit = factory.getOrCreateInitSection(iInit);
			if (oInit != null) {
				oLetBlock.setInit(oInit);
			}
			transformStepCopy(iInit);

			Iterator<org.eclipse.acceleo.parser.cst.LetBlock> iElseLetIt = iLetBlock.getElseLet().iterator();
			while (iElseLetIt.hasNext()) {
				org.eclipse.acceleo.parser.cst.LetBlock iNext = iElseLetIt.next();
				org.eclipse.acceleo.model.mtl.LetBlock oNext = factory.getOrCreateLetBlock(iNext);
				if (oNext != null) {
					oLetBlock.getElseLet().add(oNext);
				}
				transformStepCopy(iNext);

			}

			org.eclipse.acceleo.parser.cst.Variable iLetVariable = iLetBlock.getLetVariable();
			org.eclipse.ocl.ecore.Variable oLetVariable = factory.getOrCreateVariable(iLetVariable);
			if (oLetVariable != null) {
				oLetBlock.setLetVariable(oLetVariable);
			}
			transformStepCopy(iLetVariable);

			transformStepCopyBody(iLetBlock, oLetBlock);
		}
	}

	/**
	 * The step 'StepCopy' of the transformation for each 'org.eclipse.acceleo.parser.cst.FileBlock' of the input model.
	 * 
	 * @param iFileBlock
	 *            is the input object of type 'org.eclipse.acceleo.parser.cst.FileBlock'
	 */
	private void transformStepCopy(org.eclipse.acceleo.parser.cst.FileBlock iFileBlock) {
		org.eclipse.acceleo.model.mtl.FileBlock oFileBlock = factory.getOrCreateFileBlock(iFileBlock);
		if (iFileBlock != null && oFileBlock != null) {
			org.eclipse.acceleo.parser.cst.OpenModeKind iOpenMode = iFileBlock.getOpenMode();
			org.eclipse.acceleo.model.mtl.OpenModeKind oOpenMode = org.eclipse.acceleo.model.mtl.OpenModeKind.get(iOpenMode.getValue());
			oFileBlock.setOpenMode(oOpenMode);
			transformStepCopyPositions(iFileBlock, oFileBlock);
			org.eclipse.acceleo.parser.cst.InitSection iInit = iFileBlock.getInit();
			org.eclipse.acceleo.model.mtl.InitSection oInit = factory.getOrCreateInitSection(iInit);
			if (oInit != null) {
				oFileBlock.setInit(oInit);
			}
			transformStepCopy(iInit);

			org.eclipse.acceleo.parser.cst.ModelExpression iFileUrl = iFileBlock.getFileUrl();
			transformStepCopy(iFileUrl);

			org.eclipse.acceleo.parser.cst.ModelExpression iUniqId = iFileBlock.getUniqId();
			transformStepCopy(iUniqId);

			transformStepCopyBody(iFileBlock, oFileBlock);
		}
	}

	/**
	 * The step 'StepCopy' of the transformation for each 'org.eclipse.acceleo.parser.cst.TraceBlock' of the input model.
	 * 
	 * @param iTraceBlock
	 *            is the input object of type 'org.eclipse.acceleo.parser.cst.TraceBlock'
	 */
	private void transformStepCopy(org.eclipse.acceleo.parser.cst.TraceBlock iTraceBlock) {
		org.eclipse.acceleo.model.mtl.TraceBlock oTraceBlock = factory.getOrCreateTraceBlock(iTraceBlock);
		if (iTraceBlock != null && oTraceBlock != null) {
			transformStepCopyPositions(iTraceBlock, oTraceBlock);
			org.eclipse.acceleo.parser.cst.InitSection iInit = iTraceBlock.getInit();
			org.eclipse.acceleo.model.mtl.InitSection oInit = factory.getOrCreateInitSection(iInit);
			if (oInit != null) {
				oTraceBlock.setInit(oInit);
			}
			transformStepCopy(iInit);

			org.eclipse.acceleo.parser.cst.ModelExpression iModelElement = iTraceBlock.getModelElement();
			transformStepCopy(iModelElement);

			transformStepCopyBody(iTraceBlock, oTraceBlock);
		}
	}

	/**
	 * The step 'StepCopy' of the transformation for each 'org.eclipse.acceleo.parser.cst.Macro' of the input model.
	 * 
	 * @param iMacro
	 *            is the input object of type 'org.eclipse.acceleo.parser.cst.Macro'
	 */
	private void transformStepCopy(org.eclipse.acceleo.parser.cst.Macro iMacro) {
		org.eclipse.acceleo.model.mtl.Macro oMacro = factory.getOrCreateMacro(iMacro);
		if (iMacro != null && oMacro != null) {
			org.eclipse.acceleo.parser.cst.VisibilityKind iVisibility = iMacro.getVisibility();
			org.eclipse.acceleo.model.mtl.VisibilityKind oVisibility = org.eclipse.acceleo.model.mtl.VisibilityKind.get(iVisibility
					.getValue());
			oMacro.setVisibility(oVisibility);
			transformStepCopyPositions(iMacro, oMacro);
			String ioName = iMacro.getName();
			oMacro.setName(ioName);
			org.eclipse.acceleo.parser.cst.InitSection iInit = iMacro.getInit();
			org.eclipse.acceleo.model.mtl.InitSection oInit = factory.getOrCreateInitSection(iInit);
			if (oInit != null) {
				oMacro.setInit(oInit);
			}
			transformStepCopy(iInit);

			transformStepCopyBody(iMacro, oMacro);
			Iterator<org.eclipse.acceleo.parser.cst.Variable> iParameterIt = iMacro.getParameter().iterator();
			while (iParameterIt.hasNext()) {
				org.eclipse.acceleo.parser.cst.Variable iNext = iParameterIt.next();
				org.eclipse.ocl.ecore.Variable oNext = factory.getOrCreateVariable(iNext);
				if (oNext != null) {
					oMacro.getParameter().add(oNext);
				}
				transformStepCopy(iNext);

			}

			String ioType = iMacro.getType();
			EAnnotation eAnnotation = EcoreFactory.eINSTANCE.createEAnnotation();
			eAnnotation.setSource(OCLParser.ANNOTATION_SOURCE);
			eAnnotation.getDetails().put(OCLParser.ANNOTATION_KEY_TYPE, ioType);
			oMacro.getEAnnotations().add(eAnnotation);
		}
	}

	/**
	 * The step 'StepCopy' of the transformation for each 'org.eclipse.acceleo.parser.cst.Query' of the input model.
	 * 
	 * @param iQuery
	 *            is the input object of type 'org.eclipse.acceleo.parser.cst.Query'
	 */
	private void transformStepCopy(org.eclipse.acceleo.parser.cst.Query iQuery) {
		org.eclipse.acceleo.model.mtl.Query oQuery = factory.getOrCreateQuery(iQuery);
		if (iQuery != null && oQuery != null) {
			org.eclipse.acceleo.parser.cst.VisibilityKind iVisibility = iQuery.getVisibility();
			org.eclipse.acceleo.model.mtl.VisibilityKind oVisibility = org.eclipse.acceleo.model.mtl.VisibilityKind.get(iVisibility
					.getValue());
			oQuery.setVisibility(oVisibility);
			transformStepCopyPositions(iQuery, oQuery);
			String ioName = iQuery.getName();
			oQuery.setName(ioName);

			org.eclipse.acceleo.parser.cst.ModelExpression iExpression = iQuery.getExpression();
			transformStepCopy(iExpression);

			Iterator<org.eclipse.acceleo.parser.cst.Variable> iParameterIt = iQuery.getParameter().iterator();
			while (iParameterIt.hasNext()) {
				org.eclipse.acceleo.parser.cst.Variable iNext = iParameterIt.next();
				org.eclipse.ocl.ecore.Variable oNext = factory.getOrCreateVariable(iNext);
				if (oNext != null) {
					oQuery.getParameter().add(oNext);
				}
				transformStepCopy(iNext);
			}

			String ioType = iQuery.getType();
			EAnnotation eAnnotation = EcoreFactory.eINSTANCE.createEAnnotation();
			eAnnotation.setSource(OCLParser.ANNOTATION_SOURCE);
			eAnnotation.getDetails().put(OCLParser.ANNOTATION_KEY_TYPE, ioType);
			oQuery.getEAnnotations().add(eAnnotation);

		}
	}

	/**
	 * The step 'StepCopy' of the transformation for each reference 'ownedModuleElement' of an input module.
	 * 
	 * @param iModule
	 *            is the input module
	 * @param oModule
	 *            is the output module
	 */
	private void transformStepCopyOwnedModuleElement(org.eclipse.acceleo.parser.cst.Module iModule,
			org.eclipse.acceleo.model.mtl.Module oModule) {
		if (!isCanceled) {
			Iterator<org.eclipse.acceleo.parser.cst.ModuleElement> iOwnedModuleElementIt = iModule
					.getOwnedModuleElement().iterator();
			while (iOwnedModuleElementIt.hasNext()) {
				org.eclipse.acceleo.parser.cst.ModuleElement iNext = iOwnedModuleElementIt.next();
				if (iNext instanceof org.eclipse.acceleo.parser.cst.Template) {
					org.eclipse.acceleo.model.mtl.Template oNext = factory
							.getOrCreateTemplate((org.eclipse.acceleo.parser.cst.Template)iNext);
					if (oNext != null) {
						oModule.getOwnedModuleElement().add(oNext);
					}
					transformStepCopy((org.eclipse.acceleo.parser.cst.Template)iNext);
				} else if (iNext instanceof org.eclipse.acceleo.parser.cst.Macro) {
					org.eclipse.acceleo.model.mtl.Macro oNext = factory.getOrCreateMacro((org.eclipse.acceleo.parser.cst.Macro)iNext);
					if (oNext != null) {
						oModule.getOwnedModuleElement().add(oNext);
					}
					transformStepCopy((org.eclipse.acceleo.parser.cst.Macro)iNext);
				} else if (iNext instanceof org.eclipse.acceleo.parser.cst.Query) {
					org.eclipse.acceleo.model.mtl.Query oNext = factory.getOrCreateQuery((org.eclipse.acceleo.parser.cst.Query)iNext);
					if (oNext != null) {
						oModule.getOwnedModuleElement().add(oNext);
					}
					transformStepCopy((org.eclipse.acceleo.parser.cst.Query)iNext);
				}
			}
		}
	}

	/**
	 * The step 'StepCopy' of the transformation for each reference 'body' of an input block.
	 * 
	 * @param iBlock
	 *            is the input block
	 * @param oBlock
	 *            is the output block
	 */
	private void transformStepCopyBody(org.eclipse.acceleo.parser.cst.Block iBlock, org.eclipse.acceleo.model.mtl.Block oBlock) {
		if (!isCanceled) {
			Iterator<org.eclipse.acceleo.parser.cst.TemplateExpression> iBodyIt = iBlock.getBody().iterator();
			while (iBodyIt.hasNext()) {
				org.eclipse.acceleo.parser.cst.TemplateExpression iNext = iBodyIt.next();
				if (iNext instanceof org.eclipse.acceleo.parser.cst.Template) {
					org.eclipse.acceleo.model.mtl.Template oNext = factory
							.getOrCreateTemplate((org.eclipse.acceleo.parser.cst.Template)iNext);
					if (oNext != null) {
						oBlock.getBody().add(oNext);
					}
					transformStepCopy((org.eclipse.acceleo.parser.cst.Template)iNext);
				} else if (iNext instanceof org.eclipse.acceleo.parser.cst.TextExpression) {
					org.eclipse.ocl.ecore.StringLiteralExp oNext = factory
							.getOrCreateStringLiteralExp((org.eclipse.acceleo.parser.cst.TextExpression)iNext);
					if (oNext != null) {
						oBlock.getBody().add(oNext);
					}
					transformStepCopy((org.eclipse.acceleo.parser.cst.TextExpression)iNext);
				} else if (iNext instanceof org.eclipse.acceleo.parser.cst.ProtectedAreaBlock) {
					org.eclipse.acceleo.model.mtl.ProtectedAreaBlock oNext = factory
							.getOrCreateProtectedAreaBlock((org.eclipse.acceleo.parser.cst.ProtectedAreaBlock)iNext);
					if (oNext != null) {
						oBlock.getBody().add(oNext);
					}
					transformStepCopy((org.eclipse.acceleo.parser.cst.ProtectedAreaBlock)iNext);
				} else if (iNext instanceof org.eclipse.acceleo.parser.cst.ForBlock) {
					org.eclipse.acceleo.model.mtl.ForBlock oNext = factory
							.getOrCreateForBlock((org.eclipse.acceleo.parser.cst.ForBlock)iNext);
					if (oNext != null) {
						oBlock.getBody().add(oNext);
					}
					transformStepCopy((org.eclipse.acceleo.parser.cst.ForBlock)iNext);
				} else if (iNext instanceof org.eclipse.acceleo.parser.cst.IfBlock) {
					org.eclipse.acceleo.model.mtl.IfBlock oNext = factory
							.getOrCreateIfBlock((org.eclipse.acceleo.parser.cst.IfBlock)iNext);
					if (oNext != null) {
						oBlock.getBody().add(oNext);
					}
					transformStepCopy((org.eclipse.acceleo.parser.cst.IfBlock)iNext);
				} else if (iNext instanceof org.eclipse.acceleo.parser.cst.LetBlock) {
					org.eclipse.acceleo.model.mtl.LetBlock oNext = factory
							.getOrCreateLetBlock((org.eclipse.acceleo.parser.cst.LetBlock)iNext);
					if (oNext != null) {
						oBlock.getBody().add(oNext);
					}
					transformStepCopy((org.eclipse.acceleo.parser.cst.LetBlock)iNext);
				} else if (iNext instanceof org.eclipse.acceleo.parser.cst.FileBlock) {
					org.eclipse.acceleo.model.mtl.FileBlock oNext = factory
							.getOrCreateFileBlock((org.eclipse.acceleo.parser.cst.FileBlock)iNext);
					if (oNext != null) {
						oBlock.getBody().add(oNext);
					}
					transformStepCopy((org.eclipse.acceleo.parser.cst.FileBlock)iNext);
				} else if (iNext instanceof org.eclipse.acceleo.parser.cst.TraceBlock) {
					org.eclipse.acceleo.model.mtl.TraceBlock oNext = factory
							.getOrCreateTraceBlock((org.eclipse.acceleo.parser.cst.TraceBlock)iNext);
					if (oNext != null) {
						oBlock.getBody().add(oNext);
					}
					transformStepCopy((org.eclipse.acceleo.parser.cst.TraceBlock)iNext);
				} else if (iNext instanceof org.eclipse.acceleo.parser.cst.Macro) {
					org.eclipse.acceleo.model.mtl.Macro oNext = factory.getOrCreateMacro((org.eclipse.acceleo.parser.cst.Macro)iNext);
					if (oNext != null) {
						oBlock.getBody().add(oNext);
					}
					transformStepCopy((org.eclipse.acceleo.parser.cst.Macro)iNext);
				} else if (iNext instanceof org.eclipse.acceleo.parser.cst.Block) {
					org.eclipse.acceleo.model.mtl.Block oNext = factory.getOrCreateBlock((org.eclipse.acceleo.parser.cst.Block)iNext);
					if (oNext != null) {
						oBlock.getBody().add(oNext);
					}
					transformStepCopy((org.eclipse.acceleo.parser.cst.Block)iNext);
				} else if (!(iNext instanceof org.eclipse.acceleo.parser.cst.Comment)) {
					org.eclipse.acceleo.model.mtl.TemplateExpression oNext = factory
							.getOrCreateTemporaryTemplateExpression(iNext);
					if (oNext != null) {
						oBlock.getBody().add(oNext);
					}
				}
			}
		}
	}

	/**
	 * The step 'StepCopy' of the transformation for the positions of each 'CST' node.
	 * 
	 * @param iNode
	 *            is the input node
	 * @param oNode
	 *            is the output node
	 */
	private void transformStepCopyPositions(org.eclipse.acceleo.parser.cst.CSTNode iNode,
			org.eclipse.ocl.utilities.ASTNode oNode) {
		int ioStartPosition = iNode.getStartPosition();
		oNode.setStartPosition(ioStartPosition);
		int ioEndPosition = iNode.getEndPosition();
		oNode.setEndPosition(ioEndPosition);
	}

	/**
	 * The way to cancel the current CST to AST action.
	 * 
	 * @param cancel
	 *            is the 'cancel' state of the current action, you must use 'true' to disable the action and
	 *            'false' to enable it
	 */
	public void canceling(boolean cancel) {
		isCanceled = cancel;
	}

}

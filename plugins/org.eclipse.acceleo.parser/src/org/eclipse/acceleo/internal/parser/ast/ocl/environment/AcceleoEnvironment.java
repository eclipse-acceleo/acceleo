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
package org.eclipse.acceleo.internal.parser.ast.ocl.environment;

import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.acceleo.common.IAcceleoConstants;
import org.eclipse.acceleo.common.internal.utils.AcceleoPackageRegistry;
import org.eclipse.acceleo.common.internal.utils.compatibility.AcceleoCompatibilityHelper;
import org.eclipse.acceleo.common.internal.utils.compatibility.AcceleoOCLReflection;
import org.eclipse.acceleo.common.internal.utils.compatibility.OCLVersion;
import org.eclipse.acceleo.common.utils.AcceleoNonStandardLibrary;
import org.eclipse.acceleo.common.utils.AcceleoStandardLibrary;
import org.eclipse.acceleo.common.utils.CircularArrayDeque;
import org.eclipse.acceleo.common.utils.Deque;
import org.eclipse.acceleo.internal.compatibility.parser.ast.ocl.environment.AcceleoUMLReflectionHelios;
import org.eclipse.emf.common.util.Diagnostic;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EEnumLiteral;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EOperation;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EParameter;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.ocl.AmbiguousLookupException;
import org.eclipse.ocl.Environment;
import org.eclipse.ocl.EnvironmentFactory;
import org.eclipse.ocl.LookupException;
import org.eclipse.ocl.TypeResolver;
import org.eclipse.ocl.ecore.BagType;
import org.eclipse.ocl.ecore.CallOperationAction;
import org.eclipse.ocl.ecore.CollectionType;
import org.eclipse.ocl.ecore.Constraint;
import org.eclipse.ocl.ecore.EcoreEnvironment;
import org.eclipse.ocl.ecore.OrderedSetType;
import org.eclipse.ocl.ecore.PrimitiveType;
import org.eclipse.ocl.ecore.SendSignalAction;
import org.eclipse.ocl.ecore.SequenceType;
import org.eclipse.ocl.ecore.SetType;
import org.eclipse.ocl.expressions.CollectionKind;
import org.eclipse.ocl.expressions.Variable;
import org.eclipse.ocl.internal.l10n.OCLMessages;
import org.eclipse.ocl.lpg.ProblemHandler;
import org.eclipse.ocl.options.ProblemOption;
import org.eclipse.ocl.parser.AbstractOCLAnalyzer;
import org.eclipse.ocl.util.OCLStandardLibraryUtil;
import org.eclipse.ocl.utilities.PredefinedType;
import org.eclipse.ocl.utilities.TypedElement;
import org.eclipse.ocl.utilities.UMLReflection;

/**
 * The environment that will be used throughout the evaluation of Acceleo templates.
 * 
 * @author <a href="mailto:jonathan.musset@obeo.fr">Jonathan Musset</a>
 */
public class AcceleoEnvironment extends EcoreEnvironment {
	/** Acceleo non-standard library. */
	private AcceleoNonStandardLibrary acceleoNonStdLib;

	/** Acceleo standard library. */
	private AcceleoStandardLibrary acceleoStdLib;

	/**
	 * The first problem object of the last compilation made by this environment.
	 */
	private Object firstProblemObject;

	/** Used to generate implicit iterator variables. */
	private int generatorInt;

	/** List of {@link EPackage} the parser knows about. */
	private List<EPackage> metamodels = new ArrayList<EPackage>();

	/** Instance of the OCL standard library reflection for this environment. */
	private AcceleoOCLReflection oclStdLibReflection;

	/**
	 * Allows us to totally get rid of the inherited map. This will mainly serve the purpose of allowing
	 * multiple bindings against the same variable name.
	 */
	private final Deque<Map<String, Deque<VariableEntry>>> scopedVariableMap = new CircularArrayDeque<Map<String, Deque<VariableEntry>>>();

	/** List of {@link EClassifier} the parser knows about. */
	private List<EClassifier> types = new ArrayList<EClassifier>();

	/** We'll only create a single instance of the uml reflection. */
	private UMLReflection<EPackage, EClassifier, EOperation, EStructuralFeature, EEnumLiteral, EParameter, EObject, CallOperationAction, SendSignalAction, Constraint> umlReflection;

	/**
	 * Delegates instantiation to the super constructor.
	 * 
	 * @param parent
	 *            Parent for this Acceleo environment.
	 */
	public AcceleoEnvironment(
			Environment<EPackage, EClassifier, EOperation, EStructuralFeature, EEnumLiteral, EParameter, EObject, CallOperationAction, SendSignalAction, Constraint, EClass, EObject> parent) {
		super(parent);
		if (!(parent instanceof AcceleoEnvironment)) {
			addAdditionalOperations();
		}
		scopedVariableMap.add(new HashMap<String, Deque<VariableEntry>>());
	}

	/**
	 * Delegates instantiation to the super-constructor.
	 * 
	 * @param oclEnvironmentResource
	 *            resource used to keep the OCL environment.
	 */
	public AcceleoEnvironment(Resource oclEnvironmentResource) {
		super(AcceleoPackageRegistry.INSTANCE, oclEnvironmentResource);
		addAdditionalOperations();
		scopedVariableMap.add(new HashMap<String, Deque<VariableEntry>>());
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.ocl.AbstractEnvironment#addElement(java.lang.String,
	 *      org.eclipse.ocl.expressions.Variable, boolean)
	 */
	@Override
	public boolean addElement(String name, Variable<EClassifier, EParameter> elem, boolean isExplicit) {
		String newName = name;
		if (newName == null) {
			newName = generateName();
			while (lookup(newName) != null) {
				newName = generateName();
			}
		}

		Map<String, Deque<VariableEntry>> last = scopedVariableMap.getLast();
		Deque<VariableEntry> deque = last.get(newName);
		if (deque == null) {
			deque = new CircularArrayDeque<AcceleoEnvironment.VariableEntry>();
			last.put(newName, deque);
		}

		getUMLReflection().setName(elem, newName);
		VariableEntry newelem = new VariableEntry(newName, elem, isExplicit);
		addedVariable(newName, elem, isExplicit);

		return deque.add(newelem);
	}

	/**
	 * Convenience method allowing us to add multiple additional operations at once.
	 * 
	 * @param owner
	 *            The owner of the added operations.
	 * @param operations
	 *            List of EOperations that are to be added to the environment.
	 */
	public void addHelperOperations(EClassifier owner, List<EOperation> operations) {
		for (EOperation operation : operations) {
			addHelperOperation(owner, operation);
		}
	}

	/**
	 * Add a new {@link EPackage} in the list of the metamodels considered during the parsing.
	 * 
	 * @param metamodel
	 *            {@link EPackage} to add in the current {@link EPackage}'s known by the parser.
	 */
	public void addMetamodel(EPackage metamodel) {
		metamodels.add(metamodel);
		types.clear();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.ocl.lpg.AbstractBasicEnvironment#analyzerError(java.lang.String, java.lang.String,
	 *      java.util.List)
	 */
	@Override
	public void analyzerError(String problemMessage, String problemContext, List<?> problemObjects) {
		if (problemObjects != null && problemObjects.size() > 0) {
			setFirstProblemObjectIfNull(problemObjects.get(0));
		}
		super.analyzerError(problemMessage, problemContext, problemObjects);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.ocl.lpg.AbstractBasicEnvironment#analyzerError(java.lang.String, java.lang.String,
	 *      java.lang.Object)
	 */
	@Override
	public void analyzerError(String problemMessage, String problemContext, Object problemObject) {
		setFirstProblemObjectIfNull(problemObject);
		super.analyzerError(problemMessage, problemContext, problemObject);
	}

	/**
	 * Creates a new variable scope. This will typically be called when we enter a new TemplateInvocation or
	 * QueryInvocation.
	 */
	public void createVariableScope() {
		scopedVariableMap.add(new HashMap<String, Deque<VariableEntry>>());
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.ocl.AbstractEnvironment#deleteElement(java.lang.String)
	 */
	@Override
	public void deleteElement(String name) {
		Map<String, Deque<VariableEntry>> last = scopedVariableMap.getLast();
		Deque<VariableEntry> deque = last.get(name);
		if (deque != null && !deque.isEmpty()) {
			deque.removeLast();
		}
		if (deque != null && deque.isEmpty()) {
			last.remove(name);
		}
	}

	/**
	 * Deletes the first problem object to clear the context. You'll have the newest information when you'll
	 * call getFirstProblemObject().
	 */
	public void deleteFirstProblemObject() {
		firstProblemObject = null;
		if (getInternalParent() instanceof AcceleoEnvironment) {
			((AcceleoEnvironment)getInternalParent()).deleteFirstProblemObject();
		}
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.ocl.AbstractEnvironment#dispose()
	 */
	@Override
	public void dispose() {
		super.dispose();
		acceleoNonStdLib = null;
		acceleoStdLib = null;
		firstProblemObject = null;
		metamodels.clear();
		metamodels = null;
		oclStdLibReflection = null;
		types.clear();
		types = null;
		umlReflection = null;
	}

	/**
	 * Returns the Acceleo non-standard library.
	 * 
	 * @return The Acceleo non-standard library.
	 */
	public AcceleoNonStandardLibrary getAcceleoNonStandardLibrary() {
		if (acceleoNonStdLib == null) {
			acceleoNonStdLib = new AcceleoNonStandardLibrary();
		}
		return acceleoNonStdLib;
	}

	/**
	 * Returns the Acceleo standard library.
	 * 
	 * @return The Acceleo standard library.
	 */
	public AcceleoStandardLibrary getAcceleoStandardLibrary() {
		if (acceleoStdLib == null) {
			acceleoStdLib = new AcceleoStandardLibrary();
		}
		return acceleoStdLib;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.ocl.AbstractEnvironment#getAdditionalOperations(java.lang.Object)
	 */
	@Override
	public List<EOperation> getAdditionalOperations(EClassifier classifier) {
		/*
		 * This should no longer be used. OCL might call it, but #getAdditionalOperations(EClassifier, String)
		 * should be preferred.
		 */
		List<EOperation> result = new ArrayList<EOperation>();
		result.addAll(super.getAdditionalOperations(classifier));
		if (!(classifier instanceof PrimitiveType)) {
			result.addAll(super.getAdditionalOperations(EcorePackage.eINSTANCE.getEObject()));
		}
		return result;
	}

	/**
	 * Obtains the additional operations defined in this environment in the context of the specified
	 * classifier. This should always be preferred to {@link #getAdditionalOperations(EClassifier)}.
	 * 
	 * @param classifier
	 *            The classifier on which to seek additional operations.
	 * @param name
	 *            The name filter for the classifier's operations.
	 * @return The classifier's additional operations if any, an empty list otherwise.
	 */
	public List<EOperation> getAdditionalOperations(EClassifier classifier, String name) {
		final Environment<EPackage, EClassifier, EOperation, EStructuralFeature, EEnumLiteral, EParameter, EObject, CallOperationAction, SendSignalAction, Constraint, EClass, EObject> parent = getInternalParent();
		if (parent instanceof AcceleoEnvironment) {
			return ((AcceleoEnvironment)parent).getAdditionalOperations(classifier, name);
		}

		final TypeResolver<EClassifier, EOperation, EStructuralFeature> typeResolver = getTypeResolver();

		Iterable<EOperation> candidates;
		if (parent != null) {
			candidates = parent.getAdditionalOperations(classifier);
		} else {
			final List<EOperation> additionals;
			if (typeResolver instanceof AcceleoTypeResolver) {
				additionals = ((AcceleoTypeResolver)typeResolver).getAdditionalOperations(classifier, name);
			} else {
				additionals = typeResolver.getAdditionalOperations(classifier);
			}
			if (!additionals.isEmpty()) {
				candidates = additionals;
			} else {
				candidates = new ArrayList<EOperation>();
			}
		}

		final Collection<? extends EClassifier> allParents;
		if (classifier instanceof PredefinedType<?>) {
			allParents = OCLStandardLibraryUtil.getAllSupertypes(this, (PredefinedType<?>)classifier);
		} else {
			allParents = getUMLReflection().getAllSupertypes(classifier);
		}

		for (EClassifier general : allParents) {
			final List<EOperation> additionals;
			if (typeResolver instanceof AcceleoTypeResolver) {
				additionals = ((AcceleoTypeResolver)typeResolver).getAdditionalOperations(general, name);
			} else {
				additionals = typeResolver.getAdditionalOperations(general);
			}
			if (!additionals.isEmpty()) {
				candidates = Iterables.concat(candidates, additionals);
			}
		}

		if (candidates == null) {
			candidates = Collections.emptyList();
		}

		final List<EOperation> result = new ArrayList<EOperation>();
		for (EOperation candidate : candidates) {
			if (name.equals(candidate.getName())) {
				result.add(candidate);
			}
		}

		return result;
	}

	/**
	 * Gets the first problem object of the last compilation made by this environment. Make sure to call
	 * deleteFirstProblemObject() before to get the last compilation issues.
	 * 
	 * @return the first registered problem object, or null
	 */
	public Object getFirstProblemObject() {
		Object result;
		if (firstProblemObject != null) {
			result = firstProblemObject;
		} else if (getInternalParent() instanceof AcceleoEnvironment) {
			result = ((AcceleoEnvironment)getInternalParent()).getFirstProblemObject();
		} else {
			result = null;
		}
		return result;
	}

	/**
	 * Returns this environment's reflection of the OCL standard library.
	 * 
	 * @return This environment's reflection of the OCL standard library.
	 */
	public AcceleoOCLReflection getOCLStandardLibraryReflection() {
		if (oclStdLibReflection == null) {
			oclStdLibReflection = new AcceleoOCLReflection(this);
		}
		return oclStdLibReflection;
	}

	/**
	 * Gets the meta-model types of the current module.
	 * 
	 * @return the meta-model objects, or an empty list
	 */
	public List<EClassifier> getTypes() {
		if (types.isEmpty()) {
			final Set<EClassifier> temp = Sets.newLinkedHashSet();
			final List<EPackage> packages = getMetamodels();
			final int size = packages.size();
			for (int i = 0; i < size; i++) {
				final EPackage ePackage = packages.get(i);
				final List<EClassifier> classifiers = ePackage.getEClassifiers();
				final int classCount = classifiers.size();
				for (int j = 0; j < classCount; j++) {
					computeOCLType(temp, classifiers.get(j));
				}
			}
			computeOCLType(temp, getOCLStandardLibrary().getBag());
			computeOCLType(temp, getOCLStandardLibrary().getBoolean());
			computeOCLType(temp, getOCLStandardLibrary().getCollection());
			computeOCLType(temp, getOCLStandardLibrary().getInteger());
			computeOCLType(temp, getOCLStandardLibraryReflection().getOCLInvalid());
			computeOCLType(temp, getOCLStandardLibrary().getOclAny());
			computeOCLType(temp, getOCLStandardLibrary().getOclElement());
			computeOCLType(temp, getOCLStandardLibrary().getOclExpression());
			computeOCLType(temp, getOCLStandardLibrary().getOclMessage());
			computeOCLType(temp, getOCLStandardLibrary().getOclType());
			computeOCLType(temp, getOCLStandardLibrary().getOclVoid());
			computeOCLType(temp, getOCLStandardLibrary().getOrderedSet());
			computeOCLType(temp, getOCLStandardLibrary().getReal());
			computeOCLType(temp, getOCLStandardLibrary().getSequence());
			computeOCLType(temp, getOCLStandardLibrary().getSet());
			computeOCLType(temp, getOCLStandardLibrary().getState());
			computeOCLType(temp, getOCLStandardLibrary().getString());
			computeOCLType(temp, getOCLStandardLibrary().getT());
			computeOCLType(temp, getOCLStandardLibrary().getT2());
			computeOCLType(temp, getOCLStandardLibrary().getUnlimitedNatural());
			types = Lists.newArrayList(temp);
		}
		return types;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.ocl.ecore.EcoreEnvironment#getUMLReflection()
	 */
	@Override
	public UMLReflection<EPackage, EClassifier, EOperation, EStructuralFeature, EEnumLiteral, EParameter, EObject, CallOperationAction, SendSignalAction, Constraint> getUMLReflection() {
		if (umlReflection == null) {
			if (AcceleoCompatibilityHelper.getCurrentVersion() == OCLVersion.HELIOS) {
				umlReflection = new AcceleoUMLReflectionHelios(super.getUMLReflection());
			} else {
				umlReflection = new AcceleoUMLReflection(super.getUMLReflection());
			}
		}
		return umlReflection;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.ocl.AbstractEnvironment#getVariables()
	 */
	@Override
	public Collection<Variable<EClassifier, EParameter>> getVariables() {
		Collection<Variable<EClassifier, EParameter>> result = new ArrayList<Variable<EClassifier, EParameter>>();

		Map<String, Deque<VariableEntry>> last = scopedVariableMap.getLast();
		Collection<Deque<VariableEntry>> values = last.values();
		for (Deque<VariableEntry> deque : values) {
			for (VariableEntry variableEntry : deque) {
				if (variableEntry.isExplicit) {
					result.add(variableEntry.variable);
				}
			}
		}

		if (getInternalParent() != null) {
			// add all non-shadowed parent variables
			for (Variable<EClassifier, EParameter> parentVar : getInternalParent().getVariables()) {
				if (lookupLocal(parentVar.getName()) == null) {
					result.add(parentVar);
				}
			}
		}

		return result;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.ocl.AbstractEnvironment#isEmpty()
	 */
	@Override
	public boolean isEmpty() {
		return scopedVariableMap.isEmpty();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.ocl.ecore.EcoreEnvironment#lookupClassifier(java.util.List)
	 */
	@Override
	public EClassifier lookupClassifier(List<String> names) {
		EClassifier classifier = null;
		final int nameCount = names.size();
		if (nameCount > 0) {
			final List<EClassifier> classifiers = getTypes();
			final int size = classifiers.size();
			for (int i = 0; i < size && classifier == null; i++) {
				EClassifier eClassifier = classifiers.get(i);
				if (names.get(nameCount - 1).equals(eClassifier.getName())
						&& (nameCount < 2 || names.get(nameCount - 2).equals(
								eClassifier.getEPackage().getName()))) {
					classifier = eClassifier;
				}
			}
			if (classifier == null) {
				classifier = super.lookupClassifier(names);
			}
		}
		return classifier;
	}

	/**
	 * Selects the meta-model object for the given name in the current module.
	 * 
	 * @param name
	 *            is the name of the type to search
	 * @return the meta-model object, or null if it doesn't exist
	 */
	public EClassifier lookupClassifier(String name) {
		EClassifier classifier = null;
		if (name != null) {
			classifier = lookupSequenceClassifier(name);
			if (classifier == null) {
				List<String> names = new ArrayList<String>();
				int eNamespace = name.indexOf(IAcceleoConstants.NAMESPACE_SEPARATOR);
				if (eNamespace > -1) {
					String packageName = name.substring(0, eNamespace).trim();
					String className = name.substring(
							eNamespace + IAcceleoConstants.NAMESPACE_SEPARATOR.length()).trim();
					names.add(packageName);
					names.add(className);
				} else {
					names.add(name);
				}
				classifier = lookupClassifier(names);
			}
		}
		if (classifier != null) {
			/*
			 * We need to check if the UML Reflection doesn't give a replacement for this EClassifier. We
			 * could be on a user-defined datatype that overlaps with one of the standard library's. An
			 * example of this would be the UML "String" that need be converted to the standard library's
			 * String before being returned.
			 */
			classifier = getUMLReflection().getOCLType(classifier);
		}
		return classifier;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.ocl.AbstractEnvironment#lookupImplicitSourceForAssociationClass(java.lang.String)
	 */
	@Override
	public Variable<EClassifier, EParameter> lookupImplicitSourceForAssociationClass(String name) {
		Variable<EClassifier, EParameter> vdlc = null;

		boolean found = false;

		Map<String, Deque<VariableEntry>> last = scopedVariableMap.getLast();
		Collection<Deque<VariableEntry>> values = last.values();
		for (Deque<VariableEntry> deque : values) {
			for (VariableEntry variableEntry : deque) {
				vdlc = variableEntry.variable;
				EClassifier owner = vdlc.getType();

				if (!variableEntry.isExplicit && (owner != null)) {
					EClassifier reference = lookupAssociationClassReference(owner, name);
					if (reference != null) {
						found = true;
						break;
					}
				}
			}
			if (found) {
				break;
			}
		}

		if (!found) {
			vdlc = getSelfVariable();
			if (vdlc != null) {
				EClassifier owner = vdlc.getType();
				if (owner != null && lookupAssociationClassReference(owner, name) != null) {
					return vdlc;
				}
			}
		}

		return vdlc;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.ocl.AbstractEnvironment#lookupImplicitSourceForOperation(java.lang.String,
	 *      java.util.List)
	 */
	@Override
	public Variable<EClassifier, EParameter> lookupImplicitSourceForOperation(String name,
			List<? extends TypedElement<EClassifier>> args) {
		Variable<EClassifier, EParameter> vdlc = null;

		boolean found = false;

		Map<String, Deque<VariableEntry>> last = scopedVariableMap.getLast();
		Collection<Deque<VariableEntry>> values = last.values();
		for (Deque<VariableEntry> deque : values) {
			for (VariableEntry variableEntry : deque) {
				vdlc = variableEntry.variable;
				EClassifier owner = vdlc.getType();

				if (!variableEntry.isExplicit && owner != null) {
					EOperation operation = lookupOperation(owner, name, args);
					if (operation != null) {
						found = true;
						break;
					}
				}
			}
			if (found) {
				break;
			}
		}

		if (!found) {
			vdlc = getSelfVariable();
			if (vdlc != null) {
				EClassifier owner = vdlc.getType();
				if (owner != null && lookupOperation(owner, name, args) != null) {
					return vdlc;
				}
			}
		}

		return vdlc;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.ocl.AbstractEnvironment#lookupImplicitSourceForProperty(java.lang.String)
	 */
	@Override
	public Variable<EClassifier, EParameter> lookupImplicitSourceForProperty(String name) {
		Variable<EClassifier, EParameter> vdlc = null;

		boolean found = false;

		Map<String, Deque<VariableEntry>> last = scopedVariableMap.getLast();
		Collection<Deque<VariableEntry>> values = last.values();
		for (Deque<VariableEntry> deque : values) {
			for (VariableEntry variableEntry : deque) {
				vdlc = variableEntry.variable;
				EClassifier owner = vdlc.getType();

				if (!variableEntry.isExplicit && owner != null) {
					EStructuralFeature property = safeTryLookupProperty(owner, name);
					if (property != null) {
						found = true;
						break;
					}
				}
			}
			if (found) {
				break;
			}
		}

		if (!found) {
			vdlc = getSelfVariable();
			if (vdlc != null) {
				EClassifier owner = vdlc.getType();
				if (owner != null && safeTryLookupProperty(owner, name) != null) {
					return vdlc;
				}
			}
		}

		return vdlc;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.ocl.AbstractEnvironment#lookupImplicitSourceForSignal(java.lang.String,
	 *      java.util.List)
	 */
	@Override
	public Variable<EClassifier, EParameter> lookupImplicitSourceForSignal(String name,
			List<? extends TypedElement<EClassifier>> args) {
		Variable<EClassifier, EParameter> vdlc = null;

		boolean found = false;

		Map<String, Deque<VariableEntry>> last = scopedVariableMap.getLast();
		Collection<Deque<VariableEntry>> values = last.values();
		for (Deque<VariableEntry> deque : values) {
			for (VariableEntry variableEntry : deque) {
				vdlc = variableEntry.variable;
				EClassifier owner = vdlc.getType();

				if (!variableEntry.isExplicit && owner != null) {
					EClassifier signal = lookupSignal(owner, name, args);
					if (signal != null) {
						found = true;
						break;
					}
				}
			}
			if (found) {
				break;
			}
		}

		if (!found) {
			vdlc = getSelfVariable();
			if (vdlc != null) {
				EClassifier owner = vdlc.getType();
				if (owner != null && lookupSignal(owner, name, args) != null) {
					return vdlc;
				}
			}
		}

		return vdlc;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.ocl.AbstractEnvironment#lookupImplicitSourceForState(java.util.List)
	 */
	@Override
	public Variable<EClassifier, EParameter> lookupImplicitSourceForState(List<String> path)
			throws LookupException {
		Variable<EClassifier, EParameter> vdlc = null;

		boolean found = true;

		Map<String, Deque<VariableEntry>> last = scopedVariableMap.getLast();
		Collection<Deque<VariableEntry>> values = last.values();
		for (Deque<VariableEntry> deque : values) {
			for (VariableEntry variableEntry : deque) {
				vdlc = variableEntry.variable;
				EClassifier owner = vdlc.getType();

				if (!variableEntry.isExplicit && owner != null) {
					EObject state = lookupState(owner, path);
					if (state != null) {
						found = true;
						break;
					}
				}
			}
			if (found) {
				break;
			}
		}

		if (!found) {
			vdlc = getSelfVariable();
			if (vdlc != null) {
				EClassifier owner = vdlc.getType();
				if (owner != null && lookupState(owner, path) != null) {
					return vdlc;
				}
			}
		}

		return vdlc;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.ocl.AbstractEnvironment#lookupLocal(java.lang.String)
	 */
	@Override
	public Variable<EClassifier, EParameter> lookupLocal(String name) {
		Map<String, Deque<VariableEntry>> last = scopedVariableMap.getLast();
		if (last != null) {
			Deque<VariableEntry> deque = last.get(name);
			if (deque != null && !deque.isEmpty()) {
				VariableEntry variableEntry = deque.getLast();
				return variableEntry.variable;
			}
		}
		return null;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.ocl.AbstractEnvironment#lookupOperation(java.lang.Object, java.lang.String,
	 *      java.util.List)
	 */
	@Override
	public EOperation lookupOperation(EClassifier owner, String name,
			List<? extends TypedElement<EClassifier>> args) {
		EOperation oper = super.lookupOperation(owner, name, args);
		if (oper == null) {
			/*
			 * We need to try a second lookup with the "default" collection types. i.e. if we looked up for an
			 * operation on "Sequence(OclAny)", we need to do it a second time on "Sequence(T)" because of
			 * OCL's resolution paradigm. Note that this behavior might change with OCL 3.0 and should be
			 * tried on both 1.x and 3.x versions before modifications.
			 */
			if (owner instanceof SequenceType) {
				oper = super.lookupOperation(getOCLStandardLibrary().getSequence(), name, args);
			} else if (owner instanceof BagType) {
				oper = super.lookupOperation(getOCLStandardLibrary().getBag(), name, args);
			} else if (owner instanceof OrderedSetType) {
				oper = super.lookupOperation(getOCLStandardLibrary().getOrderedSet(), name, args);
			} else if (owner instanceof SetType) {
				oper = super.lookupOperation(getOCLStandardLibrary().getSet(), name, args);
			} else if (owner instanceof CollectionType) {
				oper = super.lookupOperation(getOCLStandardLibrary().getCollection(), name, args);
			}
		}
		/*
		 * We don't want users to be stuck with oclAsType(ecore::EObject) when trying to access
		 * EObject-defined operations.
		 */
		if (oper == null && !(owner instanceof PrimitiveType)) {
			oper = super.lookupOperation(EcorePackage.eINSTANCE.getEObject(), name, args);
		}
		return oper;
	}

	/**
	 * Remove a {@link EPackage} in the list of the metamodels considered during the parsing.
	 * 
	 * @param metamodel
	 *            {@link EPackage} to remove in the current {@link EPackage}'s known by the parser.
	 */
	public void removeMetamodel(EPackage metamodel) {
		metamodels.remove(metamodel);
		types.clear();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.ocl.ecore.EcoreEnvironment#setFactory(org.eclipse.ocl.EnvironmentFactory)
	 */
	@Override
	public void setFactory(
			EnvironmentFactory<EPackage, EClassifier, EOperation, EStructuralFeature, EEnumLiteral, EParameter, EObject, CallOperationAction, SendSignalAction, Constraint, EClass, EObject> factory) {
		super.setFactory(factory);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.ocl.AbstractEnvironment#tryLookupProperty(java.lang.Object, java.lang.String)
	 */
	@Override
	public EStructuralFeature tryLookupProperty(EClassifier owner, String name) throws LookupException {
		EStructuralFeature result = lookupProperty(owner, name);

		if (result == null) {
			// looks up non-navigable named ends as well as unnamed ends. Hence
			// the possibility of ambiguity
			result = lookupNonNavigableEnd(owner, name);

			if ((result == null) && AbstractOCLAnalyzer.isEscaped(name)) {
				result = lookupNonNavigableEnd(owner, AbstractOCLAnalyzer.unescape(name));
			}
		}
		return result;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.ocl.AbstractEnvironment#findNonNavigableAssociationEnds(java.lang.Object,
	 *      java.lang.String, java.util.List)
	 */
	@Override
	protected void findNonNavigableAssociationEnds(EClassifier classifier, String name,
			List<EStructuralFeature> ends) {
		// do nothing
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.ocl.AbstractEnvironment#findUnnamedAssociationEnds(java.lang.Object, java.lang.String,
	 *      java.util.List)
	 */
	@Override
	protected void findUnnamedAssociationEnds(EClassifier classifier, String name,
			List<EStructuralFeature> ends) {
		// do nothing
	}

	/**
	 * This will return all metamodels in the scope of this environment (along with metamodels registered on
	 * parent environments).
	 * 
	 * @return All metamodels in the scope of this environment.
	 */
	protected List<EPackage> getMetamodels() {
		List<EPackage> metamodelsInScope = new ArrayList<EPackage>(metamodels);
		Environment.Internal<EPackage, EClassifier, EOperation, EStructuralFeature, EEnumLiteral, EParameter, EObject, CallOperationAction, SendSignalAction, Constraint, EClass, EObject> parent = getInternalParent();
		while (parent != null) {
			if (parent instanceof AcceleoEnvironment) {
				metamodelsInScope.addAll(((AcceleoEnvironment)parent).getMetamodels());
				// We only need this once : calling getMetamodels() on the parent will have itself checked
				// parent
				// environments.
				break;
			}
			parent = getInternalParent();
		}
		return metamodelsInScope;
	}

	/**
	 * Looks up a non-navigable association end on behalf of the specified <code>owner</code> classifier
	 * (which is at that end).
	 * 
	 * @param owner
	 *            a classifier in the context of which the property is used
	 * @param name
	 *            the end name to look up
	 * @return the non-navigable end, or <code>null</code> if it cannot be found
	 * @throws LookupException
	 *             in case that multiple non-navigable properties are found that have the same name and the
	 *             problem option is ERROR or worse
	 */
	@SuppressWarnings("restriction")
	protected EStructuralFeature lookupNonNavigableEnd(EClassifier owner, String name) throws LookupException {
		EClassifier tmpOwner = owner;
		if (tmpOwner == null) {
			Variable<EClassifier, EParameter> vdcl = lookupImplicitSourceForProperty(name);

			if (vdcl == null) {
				return null;
			}

			tmpOwner = vdcl.getType();
		}

		List<EStructuralFeature> matches = new java.util.ArrayList<EStructuralFeature>(2);
		findNonNavigableAssociationEnds(tmpOwner, name, matches);

		if (matches.isEmpty()) {
			// search for unnamed ends (named but non-navigable ends take priority)
			findUnnamedAssociationEnds(tmpOwner, name, matches);
		}

		EStructuralFeature result = null;
		if (matches.size() > 0) {
			result = matches.get(0);
		}
		if (matches.size() > 1) {
			// ambiguous matches. What to do?
			if (notOK(ProblemOption.AMBIGUOUS_ASSOCIATION_ENDS)) {
				ProblemHandler.Severity sev = getValue(ProblemOption.AMBIGUOUS_ASSOCIATION_ENDS);

				// will have to report the problem
				String message = OCLMessages.bind(OCLMessages.Ambig_AssocEnd_, name, getUMLReflection()
						.getName(tmpOwner));

				if (sev.getDiagnosticSeverity() >= Diagnostic.ERROR) {
					throw new AmbiguousLookupException(message, matches);
				}
				getProblemHandler().analyzerProblem(sev, message, "lookupNonNavigableProperty", -1, -1); //$NON-NLS-1$
			}
		}

		return result;
	}

	/**
	 * Adds custom operations from the standard and non-standard libraries to the environment.
	 */
	private void addAdditionalOperations() {
		// Add standard Acceleo operations
		addHelperOperations(getOCLStandardLibrary().getString(), getAcceleoStandardLibrary()
				.getExistingOperations(getOCLStandardLibrary().getString()));
		addHelperOperations(getOCLStandardLibrary().getInteger(), getAcceleoStandardLibrary()
				.getExistingOperations(getOCLStandardLibrary().getInteger()));
		addHelperOperations(getOCLStandardLibrary().getReal(), getAcceleoStandardLibrary()
				.getExistingOperations(getOCLStandardLibrary().getReal()));

		// TODO we should provide a way to desactivate non-standard library
		// Add non-standard Acceleo operations
		addHelperOperations(getOCLStandardLibrary().getString(), getAcceleoNonStandardLibrary()
				.getExistingOperations(getOCLStandardLibrary().getString()));
		addHelperOperations(EcorePackage.eINSTANCE.getEObject(), getAcceleoNonStandardLibrary()
				.getExistingOperations(EcorePackage.eINSTANCE.getEObject()));
		addHelperOperations(getOCLStandardLibrary().getOclAny(), getAcceleoNonStandardLibrary()
				.getExistingOperations(getOCLStandardLibrary().getOclAny()));
		addHelperOperations(getOCLStandardLibrary().getCollection(), getAcceleoNonStandardLibrary()
				.getExistingOperations(getOCLStandardLibrary().getCollection()));
		addHelperOperations(getOCLStandardLibrary().getSequence(), getAcceleoNonStandardLibrary()
				.getExistingOperations(getOCLStandardLibrary().getSequence()));
		addHelperOperations(getOCLStandardLibrary().getOrderedSet(), getAcceleoNonStandardLibrary()
				.getExistingOperations(getOCLStandardLibrary().getOrderedSet()));
	}

	/**
	 * Gets the type of the OCL environment that matches with the given type. It is put in the 'result' list.
	 * 
	 * @param result
	 *            is the list where to put the OCL type (in out parameter)
	 * @param type
	 *            is the type to find in the OCL environment
	 */
	private void computeOCLType(Set<EClassifier> result, EClassifier type) {
		EClassifier oclType = getTypeResolver().resolve(type);
		if (oclType != null) {
			result.add(oclType);
		} else {
			result.add(type);
		}
	}

	/**
	 * Generates a new, unique name for an implicit iterator variable.
	 * 
	 * @return the new name
	 */
	private String generateName() {
		generatorInt++;
		return "temp" + generatorInt; //$NON-NLS-1$
	}

	/**
	 * Selects the sequence type for the given name in the current module.
	 * 
	 * @param name
	 *            is the name of the sequence type to search
	 * @return the sequence object, or null if it doesn't exist
	 */
	private EClassifier lookupSequenceClassifier(String name) {
		if (name.endsWith(IAcceleoConstants.PARENTHESIS_END)) {
			int iPar = name.indexOf(IAcceleoConstants.PARENTHESIS_BEGIN);
			if (iPar > -1) {
				String sequenceType = name.substring(0, iPar).trim();
				String elementType = name.substring(iPar + IAcceleoConstants.PARENTHESIS_BEGIN.length(),
						name.length() - IAcceleoConstants.PARENTHESIS_END.length()).trim();
				EClassifier elementClassifier = lookupClassifier(elementType);
				Object sequenceClassifier = null;
				if (elementClassifier != null && CollectionKind.getByName(sequenceType) != null) {
					sequenceClassifier = getTypeResolver().resolveCollectionType(
							CollectionKind.getByName(sequenceType), elementClassifier);
				}
				if (sequenceClassifier instanceof EClassifier) {
					return (EClassifier)sequenceClassifier;
				}

			}
		}
		return null;
	}

	/**
	 * Wrapper for the "try" operation that doesn't throw, but just returns the first ambiguous match in case
	 * of ambiguity.
	 * 
	 * @param owner
	 *            The owner.
	 * @param name
	 *            The name.
	 * @return a wrapper for the try operation.
	 */
	private EStructuralFeature safeTryLookupProperty(EClassifier owner, String name) {
		EStructuralFeature result = null;

		try {
			result = tryLookupProperty(owner, name);
		} catch (LookupException e) {
			if (!e.getAmbiguousMatches().isEmpty()) {
				result = (EStructuralFeature)e.getAmbiguousMatches().get(0);
			}
		}

		return result;
	}

	/**
	 * Try to set the current problem object of the last compilation made by this environment. We only keep it
	 * if it is the first one.
	 * 
	 * @param problemObject
	 *            the current problem object
	 */
	private void setFirstProblemObjectIfNull(Object problemObject) {
		if (firstProblemObject == null) {
			firstProblemObject = problemObject;
		}
		if (getInternalParent() instanceof AcceleoEnvironment) {
			((AcceleoEnvironment)getInternalParent()).setFirstProblemObjectIfNull(problemObject);
		}
	}

	/**
	 * Wrapper for OCL variable declarations that additionally tracks whether they are explicit or implicit
	 * variables.
	 * 
	 * @author Christian W. Damus (cdamus)
	 */
	protected final class VariableEntry {
		/**
		 * Indicates if the variable is explicit.
		 */
		final boolean isExplicit;

		/**
		 * The name.
		 */
		final String name;

		/**
		 * The variable.
		 */
		final Variable<EClassifier, EParameter> variable;

		/**
		 * The constructor.
		 * 
		 * @param name
		 *            The name.
		 * @param variable
		 *            The variable
		 * @param isExplicit
		 *            Indicates if the variable is explicit.
		 */
		VariableEntry(String name, Variable<EClassifier, EParameter> variable, boolean isExplicit) {
			this.name = name;
			this.variable = variable;
			this.isExplicit = isExplicit;
		}

		/**
		 * {@inheritDoc}
		 * 
		 * @see java.lang.Object#toString()
		 */
		@Override
		public String toString() {
			String result = "VariableEntry[" + name + ", "; //$NON-NLS-1$ //$NON-NLS-2$
			if (isExplicit) {
				result += "explicit, "; //$NON-NLS-1$
			} else {
				result += "implicit, "; //$NON-NLS-1$
			}
			result += variable + "]"; //$NON-NLS-1$
			return result;
		}
	}
}

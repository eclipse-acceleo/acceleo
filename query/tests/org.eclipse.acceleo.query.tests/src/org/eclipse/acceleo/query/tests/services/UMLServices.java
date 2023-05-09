/*******************************************************************************
 * Copyright (c) 2015 Obeo.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 * 
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.acceleo.query.tests.services;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.uml2.uml.Activity;
import org.eclipse.uml2.uml.Association;
import org.eclipse.uml2.uml.Class;
import org.eclipse.uml2.uml.Classifier;
import org.eclipse.uml2.uml.Comment;
import org.eclipse.uml2.uml.DataType;
import org.eclipse.uml2.uml.Dependency;
import org.eclipse.uml2.uml.Element;
import org.eclipse.uml2.uml.EncapsulatedClassifier;
import org.eclipse.uml2.uml.Generalization;
import org.eclipse.uml2.uml.InstanceSpecification;
import org.eclipse.uml2.uml.Interaction;
import org.eclipse.uml2.uml.Interface;
import org.eclipse.uml2.uml.InterfaceRealization;
import org.eclipse.uml2.uml.LiteralBoolean;
import org.eclipse.uml2.uml.LiteralInteger;
import org.eclipse.uml2.uml.LiteralString;
import org.eclipse.uml2.uml.Model;
import org.eclipse.uml2.uml.NamedElement;
import org.eclipse.uml2.uml.Package;
import org.eclipse.uml2.uml.PackageImport;
import org.eclipse.uml2.uml.Port;
import org.eclipse.uml2.uml.PrimitiveType;
import org.eclipse.uml2.uml.Profile;
import org.eclipse.uml2.uml.Property;
import org.eclipse.uml2.uml.Slot;
import org.eclipse.uml2.uml.StateMachine;
import org.eclipse.uml2.uml.Stereotype;
import org.eclipse.uml2.uml.StructuredClassifier;
import org.eclipse.uml2.uml.Type;
import org.eclipse.uml2.uml.UMLFactory;
import org.eclipse.uml2.uml.UMLPlugin;
import org.eclipse.uml2.uml.Usage;
import org.eclipse.uml2.uml.ValueSpecification;
import org.eclipse.uml2.uml.util.UMLSwitch;

public class UMLServices {

	/**
	 * Scenario element name prefix.
	 */
	private static final String SCENARIO_PREFIX = "Scenario_";

	/**
	 * Stereotype argument delimiter.
	 */
	private static final String SEPARATOR = ",";

	/**
	 * Find a {@link Type} element that match the given name in the ResourceSet of the given element.
	 * 
	 * @param object
	 *            the object for which to find a corresponding type.
	 * @param typeName
	 *            the type name to match.
	 * @return the found {@link Type} element or <code>null</code>
	 */
	public Type findTypeByName(EObject object, String typeName) {
		final Type result = findTypeByName(getAllRootsInResourceSet(object), typeName);
		return result;
	}

	public Type getStringType(EObject object) {
		final Type result = findTypeByName(getAllRootsInResourceSet(object), "String");
		return result;
	}

	/**
	 * Iterate over the given {@link Collection} of root elements to find a {@link Type} element with the
	 * given name.
	 * 
	 * @param roots
	 *            the elements to inspect
	 * @param typeName
	 *            the name to match
	 * @return the found {@link Type} or <code>null</code>
	 */
	public Type findTypeByName(Collection<EObject> roots, String typeName) {
		for (EObject root : roots) {
			final Type result = findTypeByNameFrom(root, typeName);
			if (result != null) {
				return result;
			}
		}
		return null;
	}

	/**
	 * Retrieves all the root elements of the resource in the resource set of the given context object.
	 * 
	 * @param context
	 *            the context object on which to execute this service.
	 * @return a {@link Collection} of all the root element of the current resource set.
	 */
	public Collection<EObject> getAllRootsInResourceSet(EObject context) {
		final Resource res = context.eResource();
		if (res != null && res.getResourceSet() != null) {
			final Collection<EObject> roots = new ArrayList<EObject>();
			for (Resource childRes : res.getResourceSet().getResources()) {
				roots.addAll(childRes.getContents());
			}
			return roots;
		} else {
			return Collections.emptySet();
		}
	}

	public List<EObject> getAllStereotypesAndProfiles(Element element) {
		List<EObject> stereotypesAndProfiles = new ArrayList<>();
		Collection<Profile> profiles = getAllProfilesInPlatform(element);
		// Get all stereotypes
		stereotypesAndProfiles.addAll(getAllStereotypes(element, profiles));
		// Get all profiles
		stereotypesAndProfiles.addAll(profiles);
		return stereotypesAndProfiles;
	}

	private List<Stereotype> getAllStereotypes(Element element, Collection<Profile> profiles) {
		List<Stereotype> stereotypes = new ArrayList<>();
		for (Profile profile : profiles) {
			org.eclipse.uml2.uml.Package pkg = element.getNearestPackage();
			boolean isProfileApplied = false;
			if (pkg.isProfileApplied(profile)) {
				isProfileApplied = true;
			}

			if (!isProfileApplied) {
				pkg.applyProfile(profile);
			}
			stereotypes.addAll(element.getApplicableStereotypes());

			if (!isProfileApplied) {
				pkg.unapplyProfile(profile);
			}
		}

		return stereotypes;
	}

	/**
	 * Retrieves all the possible profiles in the platform for the given context object.
	 * 
	 * @param context
	 *            the context object on which to execute this service.
	 * @return a {@link Collection} of all the profiles of the current platform.
	 */
	static public Collection<Profile> getAllProfilesInPlatform(Element element) {
		// Get element package container
		org.eclipse.uml2.uml.Package package_ = element.getNearestPackage();
		final List<Profile> roots = new ArrayList<>();

		if (package_ instanceof org.eclipse.uml2.uml.Package) {
			final org.eclipse.uml2.uml.Package packageUML = (org.eclipse.uml2.uml.Package)package_;
			ResourceSet resourceSet = packageUML.eResource().getResourceSet();

			for (URI profileURI : UMLPlugin.getEPackageNsURIToProfileLocationMap().values()) {
				try {
					resourceSet.getResource(profileURI.trimFragment(), true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}

			for (Resource resource : resourceSet.getResources()) {
				TreeIterator<EObject> allContents = resource.getAllContents();
				while (allContents.hasNext()) {
					new UMLSwitch<Object>() {
						@Override
						public Object caseProfile(Profile profile) {
							if (profile.isDefined()) {
								// ProfileApplication profileApplication = packageUML
								// .getProfileApplication(profile);
								// use this condition in order to not add the already applied profiles to the
								// result list
								// if (profileApplication == null
								// || profileApplication.getAppliedDefinition() != profile
								// .getDefinition()) {
								// roots.add(profile);
								// }
								roots.add(profile);
							}
							return profile;
						}
					}.doSwitch(allContents.next());
				}
			}
		}
		return roots;
	}

	/**
	 * Iterate over the root children to find a {@link Type} element with the given name.
	 * 
	 * @param root
	 *            the root object to iterate.
	 * @param typeName
	 *            the name to match
	 * @return the found {@link Type} or <code>null</code>
	 */
	private Type findTypeByNameFrom(EObject root, String typeName) {
		if (root instanceof Type && nameMatches((Type)root, typeName)) {
			return (Type)root;
		}

		for (final Iterator<EObject> i = root.eAllContents(); i.hasNext();) {
			final EObject obj = i.next();
			if (obj instanceof Type && nameMatches((Type)obj, typeName)) {
				return (Type)obj;
			}
		}

		return null;
	}

	/**
	 * Check if the given element's name match the given String.
	 * 
	 * @param namedElt
	 *            the {@link NamedElement} to check.
	 * @param name
	 *            the name to match.
	 * @return <code>true</code> if the name match, <code>false</code> otherwise.
	 */
	private boolean nameMatches(NamedElement namedElt, String name) {
		if (namedElt != null && namedElt.getName() != null && name != null) {
			return namedElt.getName().trim().equalsIgnoreCase(name.trim());
		} else {
			return false;
		}
	}

	public String computeUmlLabel(NamedElement element) {
		return element.getName();
	}

	/**
	 * Generate a dependency label.
	 * 
	 * @param source
	 *            the source
	 * @param target
	 *            the target
	 * @return a dependency label
	 */
	public String genDependencyName(NamedElement source, NamedElement target) {
		return source.getName() + "To" + target.getName();
	}

	/**
	 * Create a new primitive slot.
	 * 
	 * @param source
	 *            Instance under which slot will be created
	 * @param property
	 *            Property attached to the slot
	 */
	public void createSlot(InstanceSpecification source, Property property) {
		// Create new slot
		final Slot slot = UMLFactory.eINSTANCE.createSlot();
		slot.setDefiningFeature(property);
		source.getSlots().add(slot);

		// Set value
		if (property.getType() instanceof PrimitiveType) {
			final String typeName = ((PrimitiveType)property.getType()).getName();
			ValueSpecification value = null;
			if ("Integer".equals(typeName)) {
				LiteralInteger aLiteralInteger = UMLFactory.eINSTANCE.createLiteralInteger();
				aLiteralInteger.setValue(Integer.parseInt(property.getDefault()));
				value = aLiteralInteger;
			} else if ("Boolean".equals(typeName)) {
				LiteralBoolean aLiteralBoolean = UMLFactory.eINSTANCE.createLiteralBoolean();
				aLiteralBoolean.setValue(Boolean.parseBoolean(property.getDefault()));
				value = aLiteralBoolean;
			} else if ("String".equals(typeName)) {
				LiteralString aLiteralString = UMLFactory.eINSTANCE.createLiteralString();
				aLiteralString.setValue(property.getDefault());
				value = aLiteralString;
			}
			// TODO: handle other case like Double, Float...
			if (value != null)
				slot.getValues().add(value);
		}
	}

	/**
	 * Get all available types in model.
	 * 
	 * @param pkg
	 *            Package
	 * @return All the available types
	 */
	public Set<Type> getAvailableTypes(Package pkg) {
		Set<Type> availableTypes = new LinkedHashSet<>();
		Set<Package> availablePackages = getAvailablePackages(pkg);

		for (Package availablePackage : availablePackages) {
			Set<Type> types = availablePackage.getOwnedTypes().stream().filter(type -> type instanceof Class
					|| type instanceof Interface || type instanceof DataType).collect(Collectors.toCollection(
							LinkedHashSet::new));
			availableTypes.addAll(types);
		}
		return availableTypes;
	}

	/**
	 * Get all available packages in model.
	 * 
	 * @param pkg
	 *            Package
	 * @return All the available packages
	 */
	public Set<Package> getAvailablePackages(Package pkg) {
		Set<Package> packages = new LinkedHashSet<>();
		packages.add(pkg);
		for (Iterator<EObject> iterator = pkg.getModel().eAllContents(); iterator.hasNext();) {
			EObject eObject = iterator.next();
			if (eObject instanceof Package) {
				packages.add((Package)eObject);
				for (PackageImport packageImport : pkg.getPackageImports()) {
					packages.add(packageImport.getImportedPackage());
				}
			}
		}

		return packages;
	}

	/**
	 * Parse the edited package to find the name of the new interaction.
	 * 
	 * @param pkg
	 *            the container {@link Package} object.
	 * @return Name for new interaction.
	 */
	public static String getNewInteractionName(EObject pkg) {
		final StringBuffer name = new StringBuffer(SCENARIO_PREFIX);
		name.append(getNumberOfElements(((Package)pkg).getPackagedElements(), SCENARIO_PREFIX));
		return name.toString();
	}

	/**
	 * Search the index of the last created element.
	 * 
	 * @param elements
	 *            List of elements.
	 * @param prefix
	 *            Prefix defining the index
	 * @return The index to use for a new element
	 */
	@SuppressWarnings("rawtypes")
	private static int getNumberOfElements(List elements, String prefix) {
		int lastUsedIndex = -1;
		for (Object element : elements) {
			final String name = ((NamedElement)element).getName();
			if (name != null && name.startsWith(prefix)) {
				final int index = Integer.valueOf(name.substring(name.lastIndexOf("_") + 1));
				if (index > lastUsedIndex)
					lastUsedIndex = index;
			}
		}

		return lastUsedIndex + 1;
	}

	/**
	 * Returns a string describing the stereotype applied to the given element.<br/>
	 * It will display the name of each stereotype, then the value of its attributes if required.
	 * 
	 * @param elt
	 *            the stereotyped element to describe.
	 * @param attributesToDisplay
	 *            the comma separated list of stereotype attributes to describe.
	 * @return A String describing the stereotypes applied to the given element.
	 */
	public String getStereotypesDescription(Element elt, String attributesToDisplay) {
		String description = "";

		final ArrayList<String> displayedAttributeList = new ArrayList<String>(Arrays.asList(
				attributesToDisplay.split(SEPARATOR)));

		for (final Iterator<Stereotype> stereotypesIterator = elt.getAppliedStereotypes()
				.iterator(); stereotypesIterator.hasNext();) {
			final Stereotype stereotype = stereotypesIterator.next();

			description = description.concat("<<" + stereotype.getName() + ">>\n");

			for (final Iterator<Property> attributeIterator = stereotype.getAllAttributes()
					.iterator(); attributeIterator.hasNext();) {
				final Property attribute = attributeIterator.next();

				if (displayedAttributeList.contains(attribute.getName())) {
					final Object obj = getDisplayContent(elt, stereotype, attribute.getName());
					if (obj != null) {
						description = description.concat(attribute.getName() + " = " + obj + "\n");
					}
				}
			}
		}

		return description;
	}

	/**
	 * Return the stereotype attribute value of the given stereotyped element.
	 * 
	 * @param elt
	 *            the stereotyped element
	 * @param stereotype
	 *            the stereotype applied to the stereotyped element
	 * @param attributeToDisplay
	 *            the attribute name we want to retreive
	 * @return the value of the stereotype attribute.
	 */
	private Object getDisplayContent(Element elt, Stereotype stereotype, String attributeToDisplay) {
		final Object obj = elt.getValue(stereotype, attributeToDisplay);

		if (obj instanceof NamedElement) {
			return ((NamedElement)obj).getName();
		}

		return obj;
	}

	public List<Package> getAllAvailableRootPackages(Element element) {
		// <%script type="uml.Element" name="allAvailableRootPackages"%>
		// <%(getRootContainer().filter("Package") + rootPackagesFromImportedModel).nMinimize()%>
		List<Package> packages = new ArrayList<>();
		packages.add(element.getModel());
		element.getModel().getImportedPackages().stream().filter(Model.class::isInstance).forEach(
				packages::add);
		return packages;
	}

	public List<EObject> getValidsForComponentDiagram(EObject cur) {
		Iterable<EObject> allContents = () -> cur.eResource().getAllContents();
		return StreamSupport.stream(allContents.spliterator(), false).filter(input -> input instanceof Package
				|| input instanceof Interface || "Class".equals(input.eClass().getName()) || "Component"
						.equals(input.eClass().getName())).collect(Collectors.toList());
	}

	public List<EObject> getValidsForCompositeDiagram(EObject cur) {
		Iterable<EObject> allContents = () -> cur.eResource().getAllContents();
		Predicate<EObject> validForCompositeDiagram = new Predicate<EObject>() {

			public boolean test(EObject input) {
				if (input instanceof StructuredClassifier) {
					return !(input instanceof Interaction || input instanceof StateMachine
							|| input instanceof Activity);
				} else {
					return input instanceof Package || input instanceof Interface || "Port".equals(input
							.eClass().getName()) || "Property".equals(input.eClass().getName());
				}

			}
		};
		return StreamSupport.stream(allContents.spliterator(), false).filter(validForCompositeDiagram)
				.collect(Collectors.toList());
	}

	public Comment getComment(Element element) {
		if (element.getOwnedComments().size() > 0)
			return element.getOwnedComments().get(0);
		return null;
	}

	public boolean canCreateAnInstanceSlot(InstanceSpecification preSource, InstanceSpecification preTarget) {
		boolean result = !candidatesForInstanceSlot(preSource, preTarget).isEmpty();
		return result;
	}

	public Set<Property> candidatesForInstanceSlot(InstanceSpecification source,
			InstanceSpecification target) {
		Set<Property> candidates = new HashSet<Property>();

		for (Classifier sourceClassifier : source.getClassifiers()) {
			for (Classifier targetClassifier : target.getClassifiers()) {
				candidates.addAll(candidatesForInstanceSlot(sourceClassifier, targetClassifier));
			}
		}

		return candidates;
	}

	private static Set<Property> candidatesForInstanceSlot(Classifier source, Classifier target) {
		Set<Property> candidates = new HashSet<Property>();
		Set<Association> associations = new HashSet<Association>();

		associations.addAll(source.getAssociations());
		for (Classifier superType : getSuperTypes(source)) {
			associations.addAll(superType.getAssociations());
		}

		for (Association association : associations) {
			Property sourceAsso = getSource(association);
			Property targetAsso = getTarget(association);
			if (conformTo(source, sourceAsso.getType()) && conformTo(target, targetAsso.getType())) {
				if (association.getNavigableOwnedEnds().contains(sourceAsso)) {
					candidates.add(sourceAsso);
				} else if (association.getNavigableOwnedEnds().contains(targetAsso)) {
					candidates.add(targetAsso);
				}
			}
		}
		return candidates;
	}

	/**
	 * Return the source of an association.
	 * 
	 * @param association
	 *            the {@link Association} context
	 * @return first end of the association
	 */
	public static Property getSource(Association association) {
		if (association.getMemberEnds() != null && association.getMemberEnds().size() > 0) {
			return association.getMemberEnds().get(0);
		}
		return null;
	}

	/**
	 * Return the target of an association.
	 * 
	 * @param association
	 *            the {@link Association} context
	 * @return second end of the association
	 */
	public static Property getTarget(Association association) {
		if (association.getMemberEnds() != null && association.getMemberEnds().size() > 1) {
			return association.getMemberEnds().get(1);
		}
		return null;
	}

	private static boolean conformTo(Type type1, Type type2) {

		boolean conform = false;

		Set<Type> allTypes1 = new HashSet<Type>();
		allTypes1.add(type1);
		if (type1 instanceof Classifier) {
			allTypes1.addAll(getSuperTypes((Classifier)type1));
		}

		Set<Type> allTypes2 = new HashSet<Type>();
		allTypes2.add(type2);
		if (type2 instanceof Classifier) {
			allTypes2.addAll(getSuperTypes((Classifier)type2));
		}

		for (Type oneOfType1 : allTypes1) {
			for (Type oneOfType2 : allTypes2) {
				if (oneOfType1.conformsTo(oneOfType2)) {
					conform = true;
					break;
				}
			}
			if (conform) {
				break;
			}
		}
		return conform;
	}

	private static Set<Classifier> getSuperTypes(Classifier type) {
		HashSet<Classifier> result = new HashSet<Classifier>();
		List<Classifier> generals = type.getGenerals();
		for (Classifier general : generals) {
			result.add(general);
			result.addAll(getSuperTypes(general));
		}
		for (Dependency dependency : type.getClientDependencies()) {
			if (dependency instanceof InterfaceRealization) {
				Interface contract = ((InterfaceRealization)dependency).getContract();
				result.add(contract);
				result.addAll(getSuperTypes(contract));
			}
		}
		return result;
	}

	public Set<Element> candidatesForSlot(InstanceSpecification anInstanceSpecification) {
		HashSet<Element> result = new HashSet<Element>();
		for (Classifier classifier : anInstanceSpecification.getClassifiers()) {
			result.add(classifier);
			result.addAll(classifier.getAllAttributes());
			for (Classifier superType : getSuperTypes(classifier)) {
				result.add(superType);
				result.addAll(superType.getAllAttributes());
			}
		}

		return result;
	}

	/**
	 * check that source and target are connectable. We explore recursively source generalizations to handle
	 * super type cases.
	 * 
	 * @param source
	 *            the source element
	 * @param target
	 *            the target element
	 * @return true if connectable
	 */
	public boolean isConnectable(Element source, Element target) {
		boolean result = false;

		if (source instanceof Interface) {
			if (target instanceof Interface) {
				result = isConnectable((Interface)source, (Interface)target);
			} else if (target instanceof Port) {
				result = isConnectable((Interface)source, (Port)target);
			} else if (target instanceof Property) {
				result = isConnectable((Interface)source, (Property)target);
			}
		} else if (source instanceof Port) {
			if (target instanceof Interface) {
				result = isConnectable((Port)source, (Interface)target);
			} else if (target instanceof Port) {
				result = false;
			} else if (target instanceof Property) {
				result = isConnectable((Port)source, (Property)target);
			}
		} else if (source instanceof Property) {
			if (target instanceof Interface) {
				result = isConnectable((Property)source, (Interface)target);
			} else if (target instanceof Port) {
				result = isConnectable((Property)source, (Port)target);
			} else if (target instanceof Property) {
				result = isConnectable((Property)source, (Property)target);
			}
		}
		return result;
	}

	/**
	 * check that source and target are connectable. We explore recursively source generalizations to handle
	 * super type cases.
	 * 
	 * @param source
	 *            the source element
	 * @param target
	 *            the target element
	 * @return true if connectable
	 */
	protected boolean isConnectable(Interface source, Interface target) {
		boolean res = source.conformsTo(target);
		if (!res) {
			List<Generalization> generalizations = source.getGeneralizations();

			for (Generalization generalization : generalizations) {
				if (generalization.getGeneral() instanceof Interface) {
					res = isConnectable((Interface)generalization.getGeneral(), target);
					if (res) {
						break;
					}
				}
			}
		}

		return res;
	}

	/**
	 * check that source and target are connectable. We explore recursively source generalizations to handle
	 * super type cases.
	 * 
	 * @param source
	 *            the source element
	 * @param target
	 *            the target element
	 * @return true if connectable
	 */
	protected boolean isConnectable(Interface source, Port target) {

		boolean res = false;
		List<Dependency> clientDependencies = new ArrayList<>(target.getClientDependencies());

		if (target.getType() instanceof EncapsulatedClassifier) {
			clientDependencies.addAll(target.getType().getClientDependencies());
		}
		for (Dependency dependency : clientDependencies) {
			if (dependency instanceof InterfaceRealization) {
				List<NamedElement> suppliers = dependency.getSuppliers();
				for (NamedElement interfaceSupplier : suppliers) {
					if (interfaceSupplier instanceof Interface) {
						res = isConnectable(source, (Interface)interfaceSupplier);
						if (res) {
							break;
						}
					}
				}
				if (res) {
					break;
				}
			}
		}

		return res;
	}

	/**
	 * check that source and target are connectable. We explore recursively source generalizations to handle
	 * super type cases.
	 * 
	 * @param source
	 *            the source element
	 * @param target
	 *            the target element
	 * @return true if connectable
	 */
	protected boolean isConnectable(Port source, Interface target) {

		boolean res = false;

		List<Dependency> clientDependencies = new ArrayList<>(source.getClientDependencies());
		if (source.getType() instanceof EncapsulatedClassifier) {
			clientDependencies.addAll(source.getType().getClientDependencies());
		}
		for (Dependency dependency : clientDependencies) {
			if (dependency instanceof Usage) {
				List<NamedElement> suppliers = dependency.getSuppliers();
				for (NamedElement interfaceSupplier : suppliers) {
					if (interfaceSupplier instanceof Interface) {
						res = isConnectable((Interface)interfaceSupplier, target);
						if (res) {
							break;
						}
					}
				}
				if (res) {
					break;
				}
			}
		}

		return res;
	}

	/**
	 * check that source and target are connectable. We explore recursively source generalizations to handle
	 * super type cases.
	 * 
	 * @param source
	 *            the source element
	 * @param target
	 *            the target element
	 * @return true if connectable
	 */
	protected boolean isConnectable(Property source, Property target) {
		boolean res = true;
		if (source.getType() != null && target.getType() != null) {
			if (source.getType() instanceof Interface && target.getType() instanceof Interface) {
				res = isConnectable((Interface)source.getType(), (Interface)target.getType());
			} else {
				res = source.isCompatibleWith(target);
			}
		}
		return res;
	}

	/**
	 * We have not handle this case. We have not any case with this scenario.
	 * 
	 * @param source
	 *            the source element
	 * @param target
	 *            the target element
	 * @return true if connectable
	 */
	protected boolean isConnectable(Interface source, Property target) {
		return false;
	}

	/**
	 * We have not handle this case. We have not any case with this scenario.
	 * 
	 * @param source
	 *            the source element
	 * @param target
	 *            the target element
	 * @return true if connectable
	 */
	protected boolean isConnectable(Property source, Interface target) {
		return false;
	}

	/**
	 * We have not handle this case. We have not any case with this scenario.
	 * 
	 * @param source
	 *            the source element
	 * @param target
	 *            the target element
	 * @return true if connectable
	 */
	protected boolean isConnectable(Port source, Property target) {
		return false;
	}

	/**
	 * We have not handle this case. We have not any case with this scenario.
	 * 
	 * @param source
	 *            the source element
	 * @param target
	 *            the target element
	 * @return true if connectable
	 */
	protected boolean isConnectable(Property source, Port target) {
		return false;
	}

}

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
package org.eclipse.acceleo.query.services;

//CHECKSTYLE:OFF
import com.google.common.base.Predicate;
import com.google.common.collect.Iterables;
import com.google.common.collect.Sets;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.ListIterator;
import java.util.Set;

import org.eclipse.acceleo.query.collections.LazyList;
import org.eclipse.acceleo.query.runtime.CrossReferenceProvider;
import org.eclipse.acceleo.query.runtime.IService;
import org.eclipse.acceleo.query.runtime.impl.AbstractServiceProvider;
import org.eclipse.acceleo.query.runtime.impl.EPackageProvider;
import org.eclipse.acceleo.query.runtime.impl.ValidationServices;
import org.eclipse.acceleo.query.runtime.lookup.basic.Service;
import org.eclipse.acceleo.query.validation.type.EClassifierLiteralType;
import org.eclipse.acceleo.query.validation.type.EClassifierType;
import org.eclipse.acceleo.query.validation.type.IType;
import org.eclipse.acceleo.query.validation.type.NothingType;
import org.eclipse.acceleo.query.validation.type.SequenceType;
import org.eclipse.acceleo.query.validation.type.SetType;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.resource.Resource;

//CHECKSTYLE:ON
/**
 * Services on {@link EObject}.
 * 
 * @author <a href="mailto:romain.guider@obeo.fr">Romain Guider</a>
 */
public class EObjectServices extends AbstractServiceProvider {

	/**
	 * A cross referencer needed to realize the service eInverse().
	 */
	private CrossReferenceProvider crossReferencer;

	/**
	 * An adapter that makes an EObject looks like an {@link Iterable} with respect to eAllContents.
	 * 
	 * @author <a href="mailto:romain.guider@obeo.fr">Romain Guider</a>
	 */
	private class EObjectIterableAdapter implements Iterable<EObject> {
		/**
		 * The root of the EAllContent iteration.
		 */
		private EObject root;

		/**
		 * Create a new {@link EObjectIterableAdapter} instance.
		 * 
		 * @param root
		 *            the root EObject.
		 */
		public EObjectIterableAdapter(EObject root) {
			this.root = root;
		}

		@Override
		public Iterator<EObject> iterator() {
			return root.eAllContents();
		}

	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.acceleo.query.runtime.impl.AbstractServiceProvider#getService(java.lang.reflect.Method)
	 */
	@Override
	protected IService getService(Method publicMethod) {
		final IService result;

		if (publicMethod.getParameterTypes().length == 2
				&& EClassifier.class.isAssignableFrom(publicMethod.getParameterTypes()[1])) {
			result = new Service(publicMethod, this) {

				@Override
				public Set<IType> getType(ValidationServices services, EPackageProvider provider,
						List<IType> argTypes) {
					final Set<IType> result = new LinkedHashSet<IType>();

					final EClassifierType rawType = new EClassifierType(((EClassifierLiteralType)argTypes
							.get(1)).getType());
					if (List.class.isAssignableFrom(getServiceMethod().getReturnType())) {
						result.add(new SequenceType(rawType));
					} else if (Set.class.isAssignableFrom(getServiceMethod().getReturnType())) {
						result.add(new SetType(rawType));
					} else {
						result.add(rawType);
					}

					return result;
				}
			};
		} else if ("eContents".equals(publicMethod.getName())) {
			result = new Service(publicMethod, this) {

				@Override
				public Set<IType> getType(ValidationServices services, EPackageProvider provider,
						List<IType> argTypes) {
					final Set<IType> result;

					if (argTypes.size() == 1 && argTypes.get(0).getType() instanceof EClass) {
						final EClass eCls = (EClass)argTypes.get(0).getType();
						result = new LinkedHashSet<IType>();
						for (EStructuralFeature feature : eCls.getEAllStructuralFeatures()) {
							if (feature instanceof EReference && ((EReference)feature).isContainment()
									&& feature.getEType() instanceof EClass) {
								result.add(new SequenceType(new EClassifierType(((EReference)feature)
										.getEType())));
							}
						}
					} else {
						result = super.getType(services, provider, argTypes);
					}

					return result;
				}
			};
		} else if ("eContainer".equals(publicMethod.getName())) {
			result = new Service(publicMethod, this) {

				@Override
				public Set<IType> getType(ValidationServices services, EPackageProvider provider,
						List<IType> argTypes) {
					final Set<IType> result;

					if (argTypes.size() == 1 && argTypes.get(0).getType() instanceof EClass) {
						final EClass eCls = (EClass)argTypes.get(0).getType();
						result = new LinkedHashSet<IType>();
						for (EClass containingEClass : provider.getAllContainingEClasses(eCls)) {
							result.add(new EClassifierType(containingEClass));
						}
						if (result.size() == 0) {
							result.add(new NothingType("No EObject can contain " + eCls.getName()));
						}
					} else {
						result = super.getType(services, provider, argTypes);
					}

					return result;
				}
			};
		} else {
			result = new Service(publicMethod, this);
		}

		return result;
	}

	/**
	 * Returns a list of the {@link EObject} recursively contained in the specified root eObject.
	 * 
	 * @param eObject
	 *            the root of the content tree
	 * @return the recursive content of the specified eObject.
	 */
	public List<EObject> eAllContents(EObject eObject) {
		return new LazyList<EObject>(new EObjectIterableAdapter(eObject));
	}

	/**
	 * Returns a list of the {@link EObject} recursively contained in the specified root eObject and that are
	 * instances of the specified EClass.
	 * 
	 * @param eObject
	 *            the root of the content tree
	 * @param type
	 *            the type used to select elements.
	 * @return the recursive content of the specified eObject.
	 */
	public List<EObject> eAllContents(EObject eObject, final EClass type) {
		// TODO optimize by pruning dead branches according to EClasses.
		return new LazyList<EObject>(Iterables.filter(new EObjectIterableAdapter(eObject),
				new Predicate<EObject>() {

					@Override
					public boolean apply(EObject input) {
						return type.isSuperTypeOf(input.eClass());
					}
				}));
	}

	/**
	 * Returns the contents of the specified {@link EObject} instance.
	 * 
	 * @param eObject
	 *            the eObject which content is requested.
	 * @return the content of the specified eObject.
	 */
	public List<EObject> eContents(EObject eObject) {
		return eObject.eContents();
	}

	/**
	 * Returns a list made of the instances of the specified type in the contents of the specified eObject.
	 * 
	 * @param eObject
	 *            the eObject which content is requested.
	 * @param type
	 *            the type filter
	 * @return the filtered content of the specified eObject
	 */
	public List<EObject> eContents(EObject eObject, final EClass type) {
		return new LazyList<EObject>(Iterables.filter(eObject.eContents(), new Predicate<EObject>() {
			@Override
			public boolean apply(EObject input) {
				return type.isSuperTypeOf(input.eClass());
			}
		}));
	}

	/**
	 * the container of the specified eObject.
	 * 
	 * @param eObject
	 *            the eObject which container is requested.
	 * @return the container of the specified eObject.
	 */
	public EObject eContainer(EObject eObject) {
		return eObject.eContainer();
	}

	/**
	 * <p>
	 * Returns the first container of the receiver that if of the given type.
	 * </p>
	 * 
	 * @param eObject
	 *            the eObject which container is seeked.
	 * @param type
	 *            the type filter.
	 * @return the first container of the receiver that if of the given type.
	 */
	public EObject eContainer(EObject eObject, EClass type) {
		final EObject result;

		EObject current = eObject.eContainer();
		while (current != null && !type.isSuperTypeOf(current.eClass())) {
			current = current.eContainer();
		}
		if (current != null && type.isSuperTypeOf(current.eClass())) {
			result = current;
		} else {
			result = null;
		}

		return result;
	}

	/**
	 * Returns the {@link EClass} of the specified {@link EObject}.
	 * 
	 * @param eObject
	 *            the eObject which {@link EClass} is seeked.
	 * @return the {@link EClass} of the specified {@link EObject}.
	 */
	public EClass eClass(EObject eObject) {
		return eObject.eClass();
	}

	public void setCrossReferencer(CrossReferenceProvider crossReferencer) {
		this.crossReferencer = crossReferencer;
	}

	/**
	 * Returns the sequence containing the full set of inverse references.
	 * 
	 * @param self
	 *            The EObject we seek the inverse references of.
	 * @return The sequence containing the full set of inverse references.
	 */
	public Set<EObject> eInverse(EObject self) {
		final Set<EObject> result;

		final Collection<EStructuralFeature.Setting> settings = crossReferencer.getInverseReferences(self);
		if (settings == null) {
			result = Collections.emptySet();
		} else {
			result = Sets.newLinkedHashSet();
			for (EStructuralFeature.Setting setting : settings) {
				result.add(setting.getEObject());
			}
		}

		return result;
	}

	/**
	 * Returns the elements of the given type from the set of the inverse references of the receiver.
	 * 
	 * @param self
	 *            The EObject we seek the inverse references of.
	 * @param filter
	 *            Types of the EObjects we seek to retrieve.
	 * @return The sequence containing the full set of inverse references.
	 */
	public Set<EObject> eInverse(EObject self, EClassifier filter) {
		final Set<EObject> result;

		final Collection<EStructuralFeature.Setting> settings = crossReferencer.getInverseReferences(self);
		if (settings == null || filter == null) {
			result = Collections.emptySet();
		} else {
			result = Sets.newLinkedHashSet();
			for (EStructuralFeature.Setting setting : settings) {
				if (filter.isInstance(setting.getEObject())) {
					result.add(setting.getEObject());
				}
			}
		}

		return result;
	}

	/**
	 * Returns the elements from the set of the inverse {@link EStructuralFeature#getName() feature name} of
	 * the receiver.
	 * 
	 * @param self
	 *            The EObject we seek the inverse references of.
	 * @param featureName
	 *            the {@link EStructuralFeature#getName() feature name}.
	 * @return The sequence containing the full set of inverse references.
	 */
	public Set<EObject> eInverse(EObject self, String featureName) {
		final Set<EObject> result;

		final Collection<EStructuralFeature.Setting> settings = crossReferencer.getInverseReferences(self);
		if (settings == null) {
			result = Collections.emptySet();
		} else {
			result = Sets.newLinkedHashSet();
			for (EStructuralFeature.Setting setting : settings) {
				if (setting.getEStructuralFeature().getName().equals(featureName)) {
					result.add(setting.getEObject());
				}
			}
		}

		return result;
	}

	/**
	 * Returns a Sequence containing the full set of <code>object</code>'s ancestors.
	 * 
	 * @param object
	 *            The EObject we seek the ancestors of.
	 * @return Sequence containing the full set of the receiver's ancestors.
	 */
	public List<EObject> ancestors(EObject object) {
		// TODO lazy collection
		return ancestors(object, null);
	}

	/**
	 * Returns a Sequence containing the full set of <code>object</code>'s ancestors.
	 * 
	 * @param object
	 *            The EObject we seek the ancestors of.
	 * @param filter
	 *            Types of the EObjects we seek to retrieve.
	 * @return Sequence containing the full set of the receiver's ancestors.
	 */
	public List<EObject> ancestors(EObject object, EClassifier filter) {
		// TODO lazy collection + predicate
		final List<EObject> result = new ArrayList<EObject>();
		EObject container = object.eContainer();
		while (container != null) {
			if (filter == null || filter.isInstance(container)) {
				result.add(container);
			}
			container = container.eContainer();
		}
		return result;
	}

	/**
	 * Handles calls to the operation "eGet". This will fetch the value of the feature named
	 * <em>featureName</em> on <em>source</em>.
	 * 
	 * @param source
	 *            The EObject we seek to retrieve a feature value of.
	 * @param featureName
	 *            Name of the feature which value we need to retrieve.
	 * @return Value of the given feature on the given object.
	 */
	public Object eGet(EObject source, String featureName) {
		for (EStructuralFeature feature : source.eClass().getEAllStructuralFeatures()) {
			if (feature.getName().equals(featureName)) {
				return source.eGet(feature);
			}
		}

		return null;
	}

	/**
	 * Returns a Sequence containing all following siblings of <code>source</code>.
	 * 
	 * @param eObject
	 *            The EObject we seek the following siblings of.
	 * @return Sequence containing the sought set of the receiver's following siblings.
	 */
	public Object followingSiblings(EObject eObject) {
		return siblings(eObject, null, false);
	}

	/**
	 * Returns a Sequence containing all following siblings of <code>source</code>.
	 * 
	 * @param eObject
	 *            The EObject we seek the following siblings of.
	 * @param filter
	 *            Types of the EObjects we seek to retrieve.
	 * @return Sequence containing the sought set of the receiver's following siblings.
	 */
	public Object followingSiblings(EObject eObject, EClassifier filter) {
		return siblings(eObject, filter, false);
	}

	/**
	 * Returns a Sequence containing all preceding siblings of <code>source</code>.
	 * 
	 * @param eObject
	 *            The EObject we seek the preceding siblings of.
	 * @return Sequence containing the sought set of the receiver's preceding siblings.
	 */
	public Object precedingSiblings(EObject eObject) {
		return siblings(eObject, null, true);
	}

	/**
	 * Returns a Sequence containing all preceding siblings of <code>source</code>.
	 * 
	 * @param eObject
	 *            The EObject we seek the preceding siblings of.
	 * @param filter
	 *            Types of the EObjects we seek to retrieve.
	 * @return Sequence containing the sought set of the receiver's preceding siblings.
	 */
	public Object precedingSiblings(EObject eObject, EClassifier filter) {
		return siblings(eObject, filter, true);
	}

	/**
	 * Returns a Sequence containing the full set of <code>source</code>'s siblings.
	 * 
	 * @param eObject
	 *            The EObject we seek the siblings of.
	 * @return Sequence containing the full set of the receiver's siblings.
	 */
	public Object siblings(EObject eObject) {
		return siblings(eObject, null);
	}

	/**
	 * Returns a Sequence containing the full set of <code>source</code>'s siblings.
	 * 
	 * @param eObject
	 *            The EObject we seek the siblings of.
	 * @param filter
	 *            Types of the EObjects we seek to retrieve.
	 * @return Sequence containing the full set of the receiver's siblings.
	 */
	public Object siblings(final EObject eObject, final EClassifier filter) {
		Object container = getContainer(eObject);
		if (container != null) {
			return new LazyList<EObject>(Iterables.filter(getContents(container), new Predicate<EObject>() {
				@Override
				public boolean apply(EObject input) {
					return input != eObject
							&& (filter == null || filter.isInstance(input.eClass()) || filter.equals(input
									.eClass()));
				}
			}));
		}
		return Collections.<EObject> emptyList();
	}

	/**
	 * Returns a Sequence containing either all preceding siblings of <code>source</code>, or all of its
	 * following siblings.
	 * 
	 * @param eObject
	 *            The EObject we seek the siblings of.
	 * @param filter
	 *            Types of the EObjects we seek to retrieve.
	 * @param preceding
	 *            If <code>true</code>, we'll return the preceding siblings of <em>source</em>. Otherwise,
	 *            this will return its followingSiblings.
	 * @return Sequence containing the sought set of the receiver's siblings.
	 */
	private List<EObject> siblings(final EObject eObject, final EClassifier filter, Boolean preceding) {
		final Object container = getContainer(eObject);
		if (container != null) {
			final List<EObject> siblings = getContents(container);
			int startIndex = 0;
			int endIndex = siblings.size();

			if (preceding) {
				endIndex = siblings.indexOf(eObject);
			} else {
				startIndex = siblings.indexOf(eObject) + 1;
			}
			return new LazyList<EObject>(Iterables.filter(siblings.subList(startIndex, endIndex),
					new Predicate<EObject>() {
						@Override
						public boolean apply(EObject input) {
							return filter == null || filter.isInstance(input.eClass())
									|| filter.equals(input.eClass());
						}
					}));
		}
		return Collections.<EObject> emptyList();
	}

	/**
	 * Obtains the container of an object for the purpose of accessing its siblings. This is often the
	 * {@link EObject#eContainer() eContainer}, but for top-level objects it may be the
	 * {@link EObject#eResource() eResource}.
	 * 
	 * @param object
	 *            an object
	 * @return its logical container
	 */
	private Object getContainer(EObject object) {
		Object result = object.eContainer();

		if (result == null && object instanceof InternalEObject) {
			// maybe it's a resource root
			result = ((InternalEObject)object).eDirectResource();
		}

		return result;
	}

	/**
	 * Obtains the contents of a container, as determined by {@link #getContainer(EObject)}.
	 * 
	 * @param container
	 *            a container of objects
	 * @return the contained objects. <b>Note</b> that, for efficiency, the resulting list may be the
	 *         {@code container}'s actual contents list. Callers must treat the result as unmodifiable
	 */
	private List<EObject> getContents(Object container) {
		final List<EObject> contents;

		if (container instanceof EObject) {
			contents = getContents((EObject)container);
		} else if (container instanceof Resource) {
			contents = getRoots((Resource)container);
		} else {
			contents = Collections.<EObject> emptyList();
		}

		return contents;
	}

	/**
	 * Elements held by a reference with containment=true and derived=true are not returned by
	 * {@link EObject#eContents()}. This allows us to return the list of all contents from an EObject
	 * <b>including</b> those references.
	 * 
	 * @param eObject
	 *            The EObject we seek the content of.
	 * @return The list of all the content of a given EObject, derived containment references included.
	 */
	private List<EObject> getContents(EObject eObject) {
		final List<EObject> result = new ArrayList<EObject>(eObject.eContents());
		for (final EReference reference : eObject.eClass().getEAllReferences()) {
			if (reference.isContainment() && reference.isDerived()) {
				final Object value = eObject.eGet(reference);
				if (value instanceof Collection<?>) {
					for (Object newValue : (Collection<?>)value) {
						if (!result.contains(newValue) && newValue instanceof EObject) {
							result.add((EObject)newValue);
						}
					}
				} else if (!result.contains(value) && value instanceof EObject) {
					result.add((EObject)value);
				}
			}
		}
		return result;
	}

	/**
	 * Like the standard {@link Resource#getContents()} method except that we retrieve only objects that do
	 * not have containers (i.e., they are not cross-resource-contained). This is an important distinction
	 * because we want only peers (or "siblings") of an object that is a root (having no container), and those
	 * are defined as the other objects that are also roots.
	 * 
	 * @param resource
	 *            a resource from which to get root objects
	 * @return the root objects. <b>Note</b> that, for efficiency, the resulting list may be the
	 *         {@code resource}'s actual contents list. Callers must treat the result as unmodifiable
	 */
	private List<EObject> getRoots(Resource resource) {
		// optimize for the vast majority of cases in which none of the objects are cross-resource-contained
		// (i.e., they are all roots)
		List<EObject> result = resource.getContents();

		for (ListIterator<EObject> iter = result.listIterator(); iter.hasNext();) {
			if (iter.next().eContainer() != null) {
				// don't include this in the result

				// need to copy the result so that we don't modify the resource
				int where = iter.previousIndex();
				List<EObject> newResult = new ArrayList<EObject>(result.size() - 1);
				newResult.addAll(result.subList(0, where));
				result = newResult;

				// continue adding roots
				while (iter.hasNext()) {
					EObject next = iter.next();
					if (next.eContainer() == null) {
						result.add(next);
					}
				}
			}
		}

		return result;
	}

}
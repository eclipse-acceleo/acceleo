/*******************************************************************************
 * Copyright (c) 2015, 2024 Obeo.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 * 
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.acceleo.query.runtime.impl;

import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.eclipse.acceleo.query.runtime.IReadOnlyQueryEnvironment;
import org.eclipse.acceleo.query.runtime.IService;
import org.eclipse.acceleo.query.validation.type.CollectionType;
import org.eclipse.acceleo.query.validation.type.ICollectionType;
import org.eclipse.acceleo.query.validation.type.IType;
import org.eclipse.acceleo.query.validation.type.NothingType;
import org.eclipse.acceleo.query.validation.type.SequenceType;
import org.eclipse.acceleo.query.validation.type.SetType;

/**
 * This class stores validation result for multiple service resolution attempts.
 * 
 * @author <a href="mailto:yvan.lussaud@obeo.fr">Yvan Lussaud</a>
 */
public class ServicesValidationResult {

	/**
	 * The current line separator which will be used by the tooling in order to compute the description.
	 */
	private static final String LS = System.getProperty("line.separator");

	/**
	 * The {@link IReadOnlyQueryEnvironment}.
	 */
	private final IReadOnlyQueryEnvironment queryEnvironment;

	/**
	 * The {@link ValidationServices}.
	 */
	private final ValidationServices validationServices;

	/**
	 * Mapping from an {@link IService} and its parameter {@link IType} to the resulting call {@link IType}.
	 */
	private final Map<IService<?>, Map<List<IType>, Set<IType>>> typesPerService = new LinkedHashMap<IService<?>, Map<List<IType>, Set<IType>>>();

	/**
	 * Mapping from a service not found message to its {@link NothingType}.
	 */
	private final Map<String, NothingType> serviceNotFoundMessages = new LinkedHashMap<String, NothingType>();

	/**
	 * Constructor.
	 * 
	 * @param queryEnvironment
	 *            the {@link IReadOnlyQueryEnvironment}
	 * @param validationServices
	 *            the {@link ValidationServices}
	 */
	public ServicesValidationResult(IReadOnlyQueryEnvironment queryEnvironment,
			ValidationServices validationServices) {
		this.queryEnvironment = queryEnvironment;
		this.validationServices = validationServices;
	}

	/**
	 * Adds the given {@link IService} with its parameter {@link IType} to the resulting call {@link IType}
	 * mapping.
	 * 
	 * @param service
	 *            the {@link IService}
	 * @param types
	 *            the parameter {@link IType} to the resulting call {@link IType} mapping
	 */
	public void addServiceTypes(IService<?> service, Map<List<IType>, Set<IType>> types) {
		final Map<List<IType>, Set<IType>> existingTypes = typesPerService.get(service);
		if (existingTypes == null) {
			typesPerService.put(service, types);
		} else {
			for (Entry<List<IType>, Set<IType>> entry : types.entrySet()) {
				final Set<IType> existingResultTypes = existingTypes.get(entry.getKey());
				if (existingResultTypes == null) {
					existingTypes.put(entry.getKey(), entry.getValue());
				} else {
					existingResultTypes.addAll(entry.getValue());
				}
			}
		}
	}

	/**
	 * Add the given {@link NothingType} for a not found service.
	 * 
	 * @param notFound
	 *            the {@link NothingType} for a not found service
	 */
	public void addServiceNotFound(NothingType notFound) {
		serviceNotFoundMessages.put(notFound.getMessage(), notFound);
	}

	/**
	 * Merges this {@link ServicesValidationResult} with the given one.
	 * 
	 * @param other
	 *            the {@link ServicesValidationResult} to merge
	 */
	public void merge(ServicesValidationResult other) {
		for (Entry<IService<?>, Map<List<IType>, Set<IType>>> entry : other.typesPerService.entrySet()) {
			this.addServiceTypes(entry.getKey(), entry.getValue());
		}
		this.serviceNotFoundMessages.putAll(other.serviceNotFoundMessages);
	}

	/**
	 * Flatten the resulting {@link IType} to reflect a call on a set.
	 */
	public void flattenSet() {
		for (Map<List<IType>, Set<IType>> map : typesPerService.values()) {
			for (Set<IType> resultTypes : map.values()) {
				Set<IType> flattenedTypes = new LinkedHashSet<IType>();
				for (IType resultType : resultTypes) {
					// flatten
					if (resultType instanceof ICollectionType) {
						flattenedTypes.add(new SetType(queryEnvironment, ((ICollectionType)resultType)
								.getCollectionType()));
					} else {
						flattenedTypes.add(new SetType(queryEnvironment, resultType));
					}
				}
				resultTypes.clear();
				resultTypes.addAll(flattenedTypes);
			}
		}
	}

	/**
	 * Flatten the resulting {@link IType} to reflect a call on a sequence.
	 */
	public void flattenSequence() {
		for (Map<List<IType>, Set<IType>> map : typesPerService.values()) {
			for (Set<IType> resultTypes : map.values()) {
				Set<IType> flattenedTypes = new LinkedHashSet<IType>();
				for (IType resultType : resultTypes) {
					// flatten
					if (resultType instanceof ICollectionType) {
						flattenedTypes.add(new SequenceType(queryEnvironment, ((ICollectionType)resultType)
								.getCollectionType()));
					} else {
						flattenedTypes.add(new SequenceType(queryEnvironment, resultType));
					}
				}
				resultTypes.clear();
				resultTypes.addAll(flattenedTypes);
			}
		}
	}

	/**
	 * Flatten the resulting {@link IType} to reflect a call on a collection.
	 */
	public void flattenCollection() {
		for (Map<List<IType>, Set<IType>> map : typesPerService.values()) {
			for (Set<IType> resultTypes : map.values()) {
				Set<IType> flattenedTypes = new LinkedHashSet<IType>();
				for (IType resultType : resultTypes) {
					// flatten
					if (resultType instanceof ICollectionType) {
						flattenedTypes.add(new CollectionType(queryEnvironment, ((ICollectionType)resultType)
								.getCollectionType()));
					} else {
						flattenedTypes.add(new CollectionType(queryEnvironment, resultType));
					}
				}
				resultTypes.clear();
				resultTypes.addAll(flattenedTypes);
			}
		}
	}

	/**
	 * Gets the {@link Set} of resulting {@link IType}.
	 * 
	 * @return the {@link Set} of resulting {@link IType}
	 */
	public Set<IType> getResultingTypes() {
		final Set<IType> result = new LinkedHashSet<IType>();

		final Set<IType> aggregated = new LinkedHashSet<IType>();
		if (!typesPerService.isEmpty()) {
			for (Entry<IService<?>, Map<List<IType>, Set<IType>>> entry : typesPerService.entrySet()) {
				final IService<?> service = entry.getKey();
				final Map<List<IType>, Set<IType>> types = entry.getValue();
				Set<IType> validatedTypes = service.validateAllType(validationServices, queryEnvironment,
						types);
				aggregated.addAll(validatedTypes);
			}
		} else {
			aggregated.addAll(serviceNotFoundMessages.values());
		}

		final StringBuilder builderSequenceNothing = new StringBuilder();
		final StringBuilder builderSetNothing = new StringBuilder();
		final StringBuilder builderNothing = new StringBuilder();
		for (IType type : aggregated) {
			if (type instanceof ICollectionType) {
				if (((ICollectionType)type).getCollectionType() instanceof NothingType) {
					final NothingType collectionType = (NothingType)((ICollectionType)type)
							.getCollectionType();
					if (type instanceof SequenceType) {
						builderSequenceNothing.append(collectionType.getMessage());
						builderSequenceNothing.append(LS);
					} else if (type instanceof SetType) {
						builderSetNothing.append(collectionType.getMessage());
						builderSetNothing.append(LS);
					} else {
						throw new IllegalStateException("new collection type ?");
					}
				} else {
					result.add(type);
				}
			} else if (type instanceof NothingType) {
				builderNothing.append(((NothingType)type).getMessage());
				builderNothing.append(LS);
			} else {
				result.add(type);
			}
		}

		if (builderSequenceNothing.length() != 0) {
			result.add(new SequenceType(queryEnvironment, new NothingType(builderSequenceNothing.substring(0,
					builderSequenceNothing.length() - LS.length()))));
		}
		if (builderSetNothing.length() != 0) {
			result.add(new SetType(queryEnvironment, new NothingType(builderSetNothing.substring(0,
					builderSetNothing.length() - LS.length()))));
		}
		if (builderNothing.length() != 0) {
			result.add(new NothingType(builderNothing.substring(0, builderNothing.length() - LS.length())));
		}

		return result;
	}

	public Set<IService<?>> getResolvedServices() {
		return typesPerService.keySet();
	}

}

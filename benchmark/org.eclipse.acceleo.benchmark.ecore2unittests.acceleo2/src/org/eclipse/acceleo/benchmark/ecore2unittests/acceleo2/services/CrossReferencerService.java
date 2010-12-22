package org.eclipse.acceleo.benchmark.ecore2unittests.acceleo2.services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.eclipse.emf.codegen.ecore.genmodel.GenModelPackage;
import org.eclipse.emf.common.notify.Notifier;
import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecore.util.EcoreUtil.ContentTreeIterator;
import org.eclipse.emf.ecore.util.EcoreUtil.CrossReferencer;

public class CrossReferencerService {
	private CrossReferencer referencer;
	
	public Set<EObject> eInverse(EObject target, String classifier) {
		EClassifier filter;
		if ("GenClass".equals(classifier)) {
			filter = GenModelPackage.eINSTANCE.getGenClass();
		} else if("GenClassifier".equals(classifier)) {
			filter = GenModelPackage.eINSTANCE.getGenClassifier();
		} else if("EClass".equals(classifier)) {
			filter = EcorePackage.eINSTANCE.getEClass();
		} else {
			return new LinkedHashSet<EObject>();
		}
		
		final Set<EObject> result = new LinkedHashSet<EObject>();
		if (referencer == null) {
			createEInverseCrossreferencer(target);
		}
		Collection<EStructuralFeature.Setting> settings = referencer.get(target);
		if (settings == null) {
			return Collections.emptySet();
		}
		for (EStructuralFeature.Setting setting : settings) {
			if (filter == null || filter.isInstance(setting.getEObject())) {
				result.add(setting.getEObject());
			}
		}
		return result;
	}
	
	private void createEInverseCrossreferencer(EObject target) {
		if (target.eResource() != null && target.eResource().getResourceSet() != null) {
			final ResourceSet rs = target.eResource().getResourceSet();
			final ContentTreeIterator<Notifier> contentIterator = new ContentTreeIterator<Notifier>(
					Collections.singleton(rs)) {
				/** Default SUID. */
				private static final long serialVersionUID = 1L;

				@Override
				protected Iterator<Resource> getResourceSetChildren(ResourceSet resourceSet) {
					List<Resource> resources = new ArrayList<Resource>();
					for (Resource res : resourceSet.getResources()) {
							resources.add(res);
					}
					resourceSetIterator = new ResourcesIterator(resources);
					return resourceSetIterator;
				}
			};
			referencer = new CrossReferencer(rs) {
				/** Default SUID. */
				private static final long serialVersionUID = 1L;

				// static initializer
				{
					crossReference();
				}

				@Override
				protected TreeIterator<Notifier> newContentsIterator() {
					return contentIterator;
				}
			};
		} else if (target.eResource() != null) {
			referencer = new CrossReferencer(target.eResource()) {
				/** Default SUID. */
				private static final long serialVersionUID = 1L;

				// static initializer
				{
					crossReference();
				}
			};
		} else {
			referencer = new CrossReferencer(EcoreUtil.getRootContainer(target)) {
				/** Default SUID. */
				private static final long serialVersionUID = 1L;

				// static initializer
				{
					crossReference();
				}
			};
		}
	}
}

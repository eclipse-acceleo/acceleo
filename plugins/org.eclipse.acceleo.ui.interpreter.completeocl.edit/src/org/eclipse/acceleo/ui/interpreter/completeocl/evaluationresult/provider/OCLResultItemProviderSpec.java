package org.eclipse.acceleo.ui.interpreter.completeocl.evaluationresult.provider;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.eclipse.acceleo.ui.interpreter.completeocl.evaluationresult.OCLResult;
import org.eclipse.acceleo.ui.interpreter.completeocl.evaluationresult.provider.OCLResultItemProvider;
import org.eclipse.acceleo.ui.interpreter.language.EvaluationResult;
import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.provider.ComposeableAdapterFactory;
import org.eclipse.emf.edit.provider.ComposedImage;
import org.eclipse.emf.edit.provider.IItemLabelProvider;

public class OCLResultItemProviderSpec extends OCLResultItemProvider {
	public OCLResultItemProviderSpec(AdapterFactory adapterFactory) {
		super(adapterFactory);
	}
	
	@Override
	public String getText(Object object) {
		final OCLResult result = (OCLResult)object;
		final EObject target = result.getEvaluationTarget();
		final IItemLabelProvider labelProvider = getLabelProvider(target);
		if (labelProvider != null) {
			return labelProvider.getText(target);
		}
		return super.getText(object);
	}
	
	@Override
	public Object getImage(Object object) {
		final OCLResult result = (OCLResult)object;
		final EObject target = result.getEvaluationTarget();
		final IItemLabelProvider labelProvider = getLabelProvider(target);
		final Object baseImage;
		if (labelProvider != null) {
			baseImage = labelProvider.getImage(target);
		} else {
			baseImage = super.getImage(object);
		}
		
		if (result.getInterpreterResult().getStatus() != null && !result.getInterpreterResult().getStatus().isOK()) {
			List<Object> images = new ArrayList<Object>(2);
			images.add(baseImage);
			images.add(getResourceLocator().getImage("full/ovr16/error_ovr"));
			return new ComposedImage(images);
		}
		return baseImage;
	}
	
	@Override
	public boolean hasChildren(Object object) {
		return true;
	}
	
	@Override
	public Collection<?> getChildren(Object object) {
		final EvaluationResult result = ((OCLResult)object).getInterpreterResult();
		if (result.getStatus() != null && !result.getStatus().isOK()) {
			return Collections.singleton(result.getStatus().getMessage());
		}
		if (result.getEvaluationResult() instanceof Object[]) {
			return Arrays.asList((Object[])result.getEvaluationResult());
		} else if (result.getEvaluationResult() instanceof Collection<?>) {
			return (Collection<?>)result.getEvaluationResult();
		}
		return Collections.singleton(result.getEvaluationResult());
	}
	
	private IItemLabelProvider getLabelProvider(EObject target) {
		if (adapterFactory instanceof ComposeableAdapterFactory) {
			return (IItemLabelProvider)((ComposeableAdapterFactory)adapterFactory).getRootAdapterFactory().adapt(target, IItemLabelProvider.class);
		}
		return (IItemLabelProvider)adapterFactory.adapt(target, IItemLabelProvider.class);
	}
}

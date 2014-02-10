package org.eclipse.acceleo.ui.interpreter.completeocl.evaluationresult.provider;

import java.util.Collection;
import java.util.Collections;

import org.eclipse.acceleo.ui.interpreter.completeocl.evaluationresult.ConstraintResult;
import org.eclipse.acceleo.ui.interpreter.completeocl.evaluationresult.provider.ConstraintResultItemProvider;
import org.eclipse.acceleo.ui.interpreter.completeocl.evaluationresult.provider.util.SeverityOverlay;
import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.provider.ComposeableAdapterFactory;
import org.eclipse.emf.edit.provider.IItemLabelProvider;

public class ConstraintResultItemProviderSpec extends ConstraintResultItemProvider {
	public ConstraintResultItemProviderSpec(AdapterFactory adapterFactory) {
		super(adapterFactory);
	}
	
	@Override
	public String getText(Object object) {
		final ConstraintResult result = (ConstraintResult)object;
		final EObject target = result.getEvaluationTarget();
		IItemLabelProvider labelProvider = getLabelProvider(target);
		if (labelProvider != null) {
			return labelProvider.getText(target);
		}
		return super.getText(object);
	}
	
	@Override
	public Object getImage(Object object) {
		final ConstraintResult result = (ConstraintResult)object;
		final EObject target = result.getEvaluationTarget();
		IItemLabelProvider labelProvider = getLabelProvider(target);
		final Object baseImage;
		if (labelProvider != null) {
			baseImage = labelProvider.getImage(target);
		} else {
			baseImage = super.getImage(object);
		}
		return SeverityOverlay.overlaySeverityOn(result.getSeverity(), baseImage);
	}
	
	@Override
	public boolean hasChildren(Object object) {
		final ConstraintResult result = (ConstraintResult)object;
		return result.getMessage() != null && !"".equals(result.getMessage());
	}
	
	@Override
	public Collection<?> getChildren(Object object) {
		final ConstraintResult result = (ConstraintResult)object;
		return Collections.singleton(result.getMessage());
	}
	
	private IItemLabelProvider getLabelProvider(EObject target) {
		if (adapterFactory instanceof ComposeableAdapterFactory) {
			return (IItemLabelProvider)((ComposeableAdapterFactory)adapterFactory).getRootAdapterFactory().adapt(target, IItemLabelProvider.class);
		}
		return (IItemLabelProvider)adapterFactory.adapt(target, IItemLabelProvider.class);
	}
}

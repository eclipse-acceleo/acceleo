package org.eclipse.acceleo.internal.parser.ast.ocl.environment;

import java.util.Collection;
import java.util.Iterator;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EOperation;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.impl.DynamicEObjectImpl;
import org.eclipse.emf.ecore.impl.EFactoryImpl;
import org.eclipse.ocl.ecore.VoidType;
import org.eclipse.ocl.types.TupleType;
import org.eclipse.ocl.util.CollectionUtil;
import org.eclipse.ocl.util.ObjectUtil;
import org.eclipse.ocl.util.Tuple;

// CHECKSTYLE:OFF (forked from OCL)

/**
 * A specialized factory that creates tuple instances implementing value equality.
 * 
 * @author Christian W. Damus (cdamus)
 */
public class TupleFactory extends EFactoryImpl {
	@Override
	protected EObject basicCreate(EClass eClass) {
		TupleInstance result = new TupleInstance();
		result.eSetClass(eClass);
		return result;
	}

	private static final class TupleInstance extends DynamicEObjectImpl implements Tuple<EOperation, EStructuralFeature> {
		private int hashCode;

		@Override
		public boolean equals(Object o) {
			if (!(o instanceof TupleInstance)) {
				return false;
			}

			TupleInstance other = (TupleInstance)o;
			EClass otherType = other.eClass();

			EList<EStructuralFeature> myFeatures = eClass().getEStructuralFeatures();
			EList<EStructuralFeature> otherFeatures = otherType.getEStructuralFeatures();

			if (myFeatures.size() != otherFeatures.size()) {
				return false;
			}

			Iterator<EStructuralFeature> iter = myFeatures.iterator();

			boolean result = true; // assume equality unless ...

			while (result && iter.hasNext()) {
				EStructuralFeature next = iter.next();

				Object myValue = null;
				if (!(next.getEType() instanceof VoidType)) {
					// don't attempt to dynamically access a VoidType attribute; just leave null
					// which is the only value of VoidType
					myValue = eGet(next);
				}

				EStructuralFeature otherNext = otherType.getEStructuralFeature(next.getName());
				if (otherNext == null) {
					result = false;
				} else {
					Object otherValue = null;
					if (!(otherNext.getEType() instanceof VoidType)) {
						// don't attempt to dynamically access a VoidType attribute; just leave null
						// which is the only value of VoidType
						otherValue = other.eGet(otherNext);
					}
					result = ObjectUtil.equal(myValue, otherValue);
				}
			}

			return result;
		}

		@Override
		public int hashCode() {
			// if hash code really computes to 0, well, then we just happen to re-compute
			if (hashCode == 0) {
				int result = 0;

				for (EStructuralFeature next : eClass().getEStructuralFeatures()) {
					Object myValue = null;
					if (!(next.getEType() instanceof VoidType)) {
						myValue = eGet(next);
					}
					result = 31 * result + ObjectUtil.hashCode(myValue);
				}
				hashCode = result;
			}
			return hashCode;
		}

		public TupleType<EOperation, EStructuralFeature> getTupleType() {
			return (org.eclipse.ocl.ecore.TupleType)eClass();
		}

		public Object getValue(String partName) {
			return getValue(eClass().getEStructuralFeature(partName));
		}

		public Object getValue(EStructuralFeature part) {
			if (part.getEType() instanceof VoidType) {
				return null;
			} else {
				return eGet(part);
			}
		}

		@Override
		public String toString() {
			StringBuilder result = new StringBuilder();
			result.append("Tuple{"); //$NON-NLS-1$

			for (Iterator<EStructuralFeature> iter = getTupleType().oclProperties().iterator(); iter
					.hasNext();) {

				EStructuralFeature p = iter.next();

				result.append(p.getName());
				result.append(" = "); //$NON-NLS-1$
				result.append(toString(getValue(p)));

				if (iter.hasNext()) {
					result.append(", "); //$NON-NLS-1$
				}
			}

			result.append("}"); //$NON-NLS-1$
			return result.toString();
		}

		private String toString(Object o) {
			if (o instanceof String) {
				return "'" + (String)o + "'"; //$NON-NLS-1$ //$NON-NLS-2$
			} else if (o instanceof Collection<?>) {
				return CollectionUtil.toString((Collection<?>)o);
			} else if (o == null) {
				return "null"; //$NON-NLS-1$
			} else {
				return o.toString();
			}
		}
	}
}

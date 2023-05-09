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
package org.eclipse.acceleo.query.tests.anydsl.impl;

import java.lang.reflect.InvocationTargetException;
import java.util.Collection;

import org.eclipse.acceleo.query.tests.anydsl.AnydslPackage;
import org.eclipse.acceleo.query.tests.anydsl.Caliber;
import org.eclipse.acceleo.query.tests.anydsl.Color;
import org.eclipse.acceleo.query.tests.anydsl.Food;
import org.eclipse.acceleo.query.tests.anydsl.Group;
import org.eclipse.acceleo.query.tests.anydsl.Producer;
import org.eclipse.acceleo.query.tests.anydsl.Source;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;
import org.eclipse.emf.ecore.util.EDataTypeUniqueEList;
import org.eclipse.emf.ecore.util.EObjectResolvingEList;
import org.eclipse.emf.ecore.util.EObjectWithInverseResolvingEList;
import org.eclipse.emf.ecore.util.InternalEList;

/**
 * <!-- begin-user-doc --> An implementation of the model object '<em><b>Food</b></em>'. <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 * <li>{@link org.eclipse.acceleo.query.tests.anydsl.impl.FoodImpl#getName <em>Name</em>}</li>
 * <li>{@link org.eclipse.acceleo.query.tests.anydsl.impl.FoodImpl#getColor <em>Color</em>}</li>
 * <li>{@link org.eclipse.acceleo.query.tests.anydsl.impl.FoodImpl#getCaliber <em>Caliber</em>}</li>
 * <li>{@link org.eclipse.acceleo.query.tests.anydsl.impl.FoodImpl#getRelatedFoods <em>Related Foods</em>}</li>
 * <li>{@link org.eclipse.acceleo.query.tests.anydsl.impl.FoodImpl#getGroup <em>Group</em>}</li>
 * <li>{@link org.eclipse.acceleo.query.tests.anydsl.impl.FoodImpl#getLabel <em>Label</em>}</li>
 * <li>{@link org.eclipse.acceleo.query.tests.anydsl.impl.FoodImpl#getSource <em>Source</em>}</li>
 * <li>{@link org.eclipse.acceleo.query.tests.anydsl.impl.FoodImpl#getProducers <em>Producers</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class FoodImpl extends MinimalEObjectImpl.Container implements Food {
	/**
	 * The default value of the '{@link #getName() <em>Name</em>}' attribute. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @see #getName()
	 * @generated
	 * @ordered
	 */
	protected static final String NAME_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getName() <em>Name</em>}' attribute. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @see #getName()
	 * @generated
	 * @ordered
	 */
	protected String name = NAME_EDEFAULT;

	/**
	 * The cached value of the '{@link #getColor() <em>Color</em>}' attribute list. <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @see #getColor()
	 * @generated
	 * @ordered
	 */
	protected EList<Color> color;

	/**
	 * The default value of the '{@link #getCaliber() <em>Caliber</em>}' attribute. <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @see #getCaliber()
	 * @generated
	 * @ordered
	 */
	protected static final Caliber CALIBER_EDEFAULT = Caliber.S;

	/**
	 * The cached value of the '{@link #getCaliber() <em>Caliber</em>}' attribute. <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @see #getCaliber()
	 * @generated
	 * @ordered
	 */
	protected Caliber caliber = CALIBER_EDEFAULT;

	/**
	 * The cached value of the '{@link #getRelatedFoods() <em>Related Foods</em>}' reference list. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getRelatedFoods()
	 * @generated
	 * @ordered
	 */
	protected EList<Food> relatedFoods;

	/**
	 * The default value of the '{@link #getGroup() <em>Group</em>}' attribute. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @see #getGroup()
	 * @generated
	 * @ordered
	 */
	protected static final Group GROUP_EDEFAULT = Group.WATER;

	/**
	 * The cached value of the '{@link #getGroup() <em>Group</em>}' attribute. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @see #getGroup()
	 * @generated
	 * @ordered
	 */
	protected Group group = GROUP_EDEFAULT;

	/**
	 * The default value of the '{@link #getLabel() <em>Label</em>}' attribute. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @see #getLabel()
	 * @generated
	 * @ordered
	 */
	protected static final String LABEL_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getLabel() <em>Label</em>}' attribute. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @see #getLabel()
	 * @generated
	 * @ordered
	 */
	protected String label = LABEL_EDEFAULT;

	/**
	 * The cached value of the '{@link #getSource() <em>Source</em>}' reference. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @see #getSource()
	 * @generated
	 * @ordered
	 */
	protected Source source;

	/**
	 * The cached value of the '{@link #getProducers() <em>Producers</em>}' reference list. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getProducers()
	 * @generated
	 * @ordered
	 */
	protected EList<Producer> producers;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	protected FoodImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return AnydslPackage.Literals.FOOD;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public String getName() {
		return name;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public void setName(String newName) {
		String oldName = name;
		name = newName;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, AnydslPackage.FOOD__NAME, oldName, name));
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EList<Color> getColor() {
		if (color == null) {
			color = new EDataTypeUniqueEList<Color>(Color.class, this, AnydslPackage.FOOD__COLOR);
		}
		return color;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public Caliber getCaliber() {
		return caliber;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public void setCaliber(Caliber newCaliber) {
		Caliber oldCaliber = caliber;
		caliber = newCaliber == null ? CALIBER_EDEFAULT : newCaliber;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, AnydslPackage.FOOD__CALIBER, oldCaliber,
					caliber));
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EList<Food> getRelatedFoods() {
		if (relatedFoods == null) {
			relatedFoods = new EObjectResolvingEList<Food>(Food.class, this,
					AnydslPackage.FOOD__RELATED_FOODS);
		}
		return relatedFoods;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public Group getGroup() {
		return group;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public void setGroup(Group newGroup) {
		Group oldGroup = group;
		group = newGroup == null ? GROUP_EDEFAULT : newGroup;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, AnydslPackage.FOOD__GROUP, oldGroup, group));
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public String getLabel() {
		return label;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public void setLabel(String newLabel) {
		String oldLabel = label;
		label = newLabel;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, AnydslPackage.FOOD__LABEL, oldLabel, label));
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public Source getSource() {
		if (source != null && source.eIsProxy()) {
			InternalEObject oldSource = (InternalEObject)source;
			source = (Source)eResolveProxy(oldSource);
			if (source != oldSource) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, AnydslPackage.FOOD__SOURCE,
							oldSource, source));
			}
		}
		return source;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public Source basicGetSource() {
		return source;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public NotificationChain basicSetSource(Source newSource, NotificationChain msgs) {
		Source oldSource = source;
		source = newSource;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET,
					AnydslPackage.FOOD__SOURCE, oldSource, newSource);
			if (msgs == null)
				msgs = notification;
			else
				msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public void setSource(Source newSource) {
		if (newSource != source) {
			NotificationChain msgs = null;
			if (source != null)
				msgs = ((InternalEObject)source).eInverseRemove(this, AnydslPackage.SOURCE__FOODS,
						Source.class, msgs);
			if (newSource != null)
				msgs = ((InternalEObject)newSource).eInverseAdd(this, AnydslPackage.SOURCE__FOODS,
						Source.class, msgs);
			msgs = basicSetSource(newSource, msgs);
			if (msgs != null)
				msgs.dispatch();
		} else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, AnydslPackage.FOOD__SOURCE, newSource,
					newSource));
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EList<Producer> getProducers() {
		if (producers == null) {
			producers = new EObjectWithInverseResolvingEList.ManyInverse<Producer>(Producer.class, this,
					AnydslPackage.FOOD__PRODUCERS, AnydslPackage.PRODUCER__FOODS);
		}
		return producers;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public boolean ripen(Color color) {
		// TODO: implement this method
		// Ensure that you remove @generated or mark it @generated NOT
		throw new UnsupportedOperationException();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public Color preferredColor() {
		// TODO: implement this method
		// Ensure that you remove @generated or mark it @generated NOT
		throw new UnsupportedOperationException();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public Food newFood() {
		// TODO: implement this method
		// Ensure that you remove @generated or mark it @generated NOT
		throw new UnsupportedOperationException();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public void setColor(Food food, Color newColor) {
		// TODO: implement this method
		// Ensure that you remove @generated or mark it @generated NOT
		throw new UnsupportedOperationException();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public void setCaliber(Food food, EList<Caliber> newCaliber) {
		// TODO: implement this method
		// Ensure that you remove @generated or mark it @generated NOT
		throw new UnsupportedOperationException();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public boolean acceptedCaliber(Caliber caliber) {
		// TODO: implement this method
		// Ensure that you remove @generated or mark it @generated NOT
		throw new UnsupportedOperationException();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public void label(String text) {
		// TODO: implement this method
		// Ensure that you remove @generated or mark it @generated NOT
		throw new UnsupportedOperationException();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated NOT
	 */
	public String preferredLabel(String text) {
		return text;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated NOT
	 */
	public EObject identity(EObject eObject) {
		return eObject;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	@Override
	public NotificationChain eInverseAdd(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case AnydslPackage.FOOD__SOURCE:
				if (source != null)
					msgs = ((InternalEObject)source).eInverseRemove(this, AnydslPackage.SOURCE__FOODS,
							Source.class, msgs);
				return basicSetSource((Source)otherEnd, msgs);
			case AnydslPackage.FOOD__PRODUCERS:
				return ((InternalEList<InternalEObject>)(InternalEList<?>)getProducers()).basicAdd(otherEnd,
						msgs);
		}
		return super.eInverseAdd(otherEnd, featureID, msgs);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case AnydslPackage.FOOD__SOURCE:
				return basicSetSource(null, msgs);
			case AnydslPackage.FOOD__PRODUCERS:
				return ((InternalEList<?>)getProducers()).basicRemove(otherEnd, msgs);
		}
		return super.eInverseRemove(otherEnd, featureID, msgs);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case AnydslPackage.FOOD__NAME:
				return getName();
			case AnydslPackage.FOOD__COLOR:
				return getColor();
			case AnydslPackage.FOOD__CALIBER:
				return getCaliber();
			case AnydslPackage.FOOD__RELATED_FOODS:
				return getRelatedFoods();
			case AnydslPackage.FOOD__GROUP:
				return getGroup();
			case AnydslPackage.FOOD__LABEL:
				return getLabel();
			case AnydslPackage.FOOD__SOURCE:
				if (resolve)
					return getSource();
				return basicGetSource();
			case AnydslPackage.FOOD__PRODUCERS:
				return getProducers();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
			case AnydslPackage.FOOD__NAME:
				setName((String)newValue);
				return;
			case AnydslPackage.FOOD__COLOR:
				getColor().clear();
				getColor().addAll((Collection<? extends Color>)newValue);
				return;
			case AnydslPackage.FOOD__CALIBER:
				setCaliber((Caliber)newValue);
				return;
			case AnydslPackage.FOOD__RELATED_FOODS:
				getRelatedFoods().clear();
				getRelatedFoods().addAll((Collection<? extends Food>)newValue);
				return;
			case AnydslPackage.FOOD__GROUP:
				setGroup((Group)newValue);
				return;
			case AnydslPackage.FOOD__LABEL:
				setLabel((String)newValue);
				return;
			case AnydslPackage.FOOD__SOURCE:
				setSource((Source)newValue);
				return;
			case AnydslPackage.FOOD__PRODUCERS:
				getProducers().clear();
				getProducers().addAll((Collection<? extends Producer>)newValue);
				return;
		}
		super.eSet(featureID, newValue);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public void eUnset(int featureID) {
		switch (featureID) {
			case AnydslPackage.FOOD__NAME:
				setName(NAME_EDEFAULT);
				return;
			case AnydslPackage.FOOD__COLOR:
				getColor().clear();
				return;
			case AnydslPackage.FOOD__CALIBER:
				setCaliber(CALIBER_EDEFAULT);
				return;
			case AnydslPackage.FOOD__RELATED_FOODS:
				getRelatedFoods().clear();
				return;
			case AnydslPackage.FOOD__GROUP:
				setGroup(GROUP_EDEFAULT);
				return;
			case AnydslPackage.FOOD__LABEL:
				setLabel(LABEL_EDEFAULT);
				return;
			case AnydslPackage.FOOD__SOURCE:
				setSource((Source)null);
				return;
			case AnydslPackage.FOOD__PRODUCERS:
				getProducers().clear();
				return;
		}
		super.eUnset(featureID);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public boolean eIsSet(int featureID) {
		switch (featureID) {
			case AnydslPackage.FOOD__NAME:
				return NAME_EDEFAULT == null ? name != null : !NAME_EDEFAULT.equals(name);
			case AnydslPackage.FOOD__COLOR:
				return color != null && !color.isEmpty();
			case AnydslPackage.FOOD__CALIBER:
				return caliber != CALIBER_EDEFAULT;
			case AnydslPackage.FOOD__RELATED_FOODS:
				return relatedFoods != null && !relatedFoods.isEmpty();
			case AnydslPackage.FOOD__GROUP:
				return group != GROUP_EDEFAULT;
			case AnydslPackage.FOOD__LABEL:
				return LABEL_EDEFAULT == null ? label != null : !LABEL_EDEFAULT.equals(label);
			case AnydslPackage.FOOD__SOURCE:
				return source != null;
			case AnydslPackage.FOOD__PRODUCERS:
				return producers != null && !producers.isEmpty();
		}
		return super.eIsSet(featureID);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	@SuppressWarnings("unchecked")
	public Object eInvoke(int operationID, EList<?> arguments) throws InvocationTargetException {
		switch (operationID) {
			case AnydslPackage.FOOD___RIPEN__COLOR:
				return ripen((Color)arguments.get(0));
			case AnydslPackage.FOOD___PREFERRED_COLOR:
				return preferredColor();
			case AnydslPackage.FOOD___NEW_FOOD:
				return newFood();
			case AnydslPackage.FOOD___SET_COLOR__FOOD_COLOR:
				setColor((Food)arguments.get(0), (Color)arguments.get(1));
				return null;
			case AnydslPackage.FOOD___SET_CALIBER__FOOD_ELIST:
				setCaliber((Food)arguments.get(0), (EList<Caliber>)arguments.get(1));
				return null;
			case AnydslPackage.FOOD___ACCEPTED_CALIBER__CALIBER:
				return acceptedCaliber((Caliber)arguments.get(0));
			case AnydslPackage.FOOD___LABEL__STRING:
				label((String)arguments.get(0));
				return null;
			case AnydslPackage.FOOD___PREFERRED_LABEL__STRING:
				return preferredLabel((String)arguments.get(0));
			case AnydslPackage.FOOD___IDENTITY__EOBJECT:
				return identity((EObject)arguments.get(0));
		}
		return super.eInvoke(operationID, arguments);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public String toString() {
		if (eIsProxy())
			return super.toString();

		StringBuffer result = new StringBuffer(super.toString());
		result.append(" (name: ");
		result.append(name);
		result.append(", color: ");
		result.append(color);
		result.append(", caliber: ");
		result.append(caliber);
		result.append(", group: ");
		result.append(group);
		result.append(", label: ");
		result.append(label);
		result.append(')');
		return result.toString();
	}

} // FoodImpl

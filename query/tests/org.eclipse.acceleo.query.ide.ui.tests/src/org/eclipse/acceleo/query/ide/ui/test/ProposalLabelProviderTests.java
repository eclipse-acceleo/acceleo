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
package org.eclipse.acceleo.query.ide.ui.test;

import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.eclipse.acceleo.query.ide.ui.ProposalLabelProvider;
import org.eclipse.acceleo.query.runtime.ICompletionProposal;
import org.eclipse.acceleo.query.runtime.IService;
import org.eclipse.acceleo.query.runtime.impl.EPackageProvider;
import org.eclipse.acceleo.query.runtime.impl.ValidationServices;
import org.eclipse.acceleo.query.runtime.impl.completion.EClassifierCompletionProposal;
import org.eclipse.acceleo.query.runtime.impl.completion.EEnumLiteralCompletionProposal;
import org.eclipse.acceleo.query.runtime.impl.completion.EFeatureCompletionProposal;
import org.eclipse.acceleo.query.runtime.impl.completion.ServiceCompletionProposal;
import org.eclipse.acceleo.query.runtime.impl.completion.TextCompletionProposal;
import org.eclipse.acceleo.query.runtime.impl.completion.VariableCompletionProposal;
import org.eclipse.acceleo.query.tests.anydsl.AnydslPackage;
import org.eclipse.acceleo.query.validation.type.ClassType;
import org.eclipse.acceleo.query.validation.type.IType;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.swt.graphics.Image;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Tests {@link ProposalLabelProviderTests}.
 * 
 * @author <a href="mailto:yvan.lussaud@obeo.fr">Yvan Lussaud</a>
 */
public class ProposalLabelProviderTests {

	/**
	 * A test {@link IService}.
	 * 
	 * @author <a href="mailto:yvan.lussaud@obeo.fr">Yvan Lussaud</a>
	 */
	private final class TestService implements IService {

		/**
		 * {@inheritDoc}
		 *
		 * @see org.eclipse.acceleo.query.runtime.IService#getType(ValidationServices, EPackageProvider, List)
		 */
		public Set<IType> getType(ValidationServices services, EPackageProvider provider, List<IType> argTypes) {
			final Set<IType> result = new HashSet<IType>();
			result.add(new ClassType(String.class));
			return result;
		}

		/**
		 * {@inheritDoc}
		 *
		 * @see org.eclipse.acceleo.query.runtime.IService#getServiceMethod()
		 */
		public Method getServiceMethod() {
			Method result = null;
			try {
				result = ProposalLabelProviderTests.this.getClass().getMethod("testService", String.class,
						String.class);
			} catch (NoSuchMethodException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SecurityException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return result;
		}

		/**
		 * {@inheritDoc}
		 *
		 * @see org.eclipse.acceleo.query.runtime.IService#getServiceInstance()
		 */
		public Object getServiceInstance() {
			return ProposalLabelProviderTests.this;
		}
	}

	/**
	 * A test {@link ProposalLabelProvider}.
	 * 
	 * @author <a href="mailto:yvan.lussaud@obeo.fr">Yvan Lussaud</a>
	 */
	private static final class TestProposalLabelProvider extends ProposalLabelProvider {

		/**
		 * Gets the {@link ServiceCompletionProposal} {@link Image}.
		 * 
		 * @return the {@link ServiceCompletionProposal} {@link Image}
		 */
		public Image getServiceImage() {
			return service;
		}

		/**
		 * Gets the {@link VariableCompletionProposal} {@link Image}.
		 * 
		 * @return the {@link VariableCompletionProposal} {@link Image}
		 */
		public Image getVariableImage() {
			return service;
		}

		/**
		 * Gets the {@link TextCompletionProposal} {@link Image}.
		 * 
		 * @return the {@link TextCompletionProposal} {@link Image}
		 */
		public Image getTextImage() {
			return service;
		}

		/**
		 * Gets the EMF {@link ILabelProvider}.
		 * 
		 * @return the EMF {@link ILabelProvider}
		 */
		public ILabelProvider getELabelProvider() {
			return eLabelProvider;
		}

	}

	/**
	 * The {@link ProposalLabelProvider} to test.
	 */
	private static TestProposalLabelProvider labelProvider;

	/**
	 * Initializes the {@link TestProposalLabelProvider}.
	 */
	@BeforeClass
	public static void beforeClass() {
		labelProvider = new TestProposalLabelProvider();
	}

	/**
	 * Disposes the {@link TestProposalLabelProvider}.
	 */
	@AfterClass
	public static void afterClass() {
		labelProvider.dispose();
	}

	/**
	 * Tests an {@link EClassifierCompletionProposal} with an {@link org.eclipse.emf.ecore.EClass EClass}.
	 */
	@Test
	public void eClassifierCompletionProposalEClass() {
		ICompletionProposal completionProposal = new EClassifierCompletionProposal(AnydslPackage.eINSTANCE
				.getCompany());

		assertEquals("anydsl::Company", labelProvider.getText(completionProposal));
		assertImagesEquals(labelProvider.getELabelProvider().getImage(AnydslPackage.eINSTANCE.getCompany()),
				labelProvider.getImage(completionProposal));
	}

	/**
	 * Tests an {@link EClassifierCompletionProposal} with an {@link org.eclipse.emf.ecore.EDataType
	 * EDataType}.
	 */
	@Test
	public void eClassifierCompletionProposalEDataType() {
		ICompletionProposal completionProposal = new EClassifierCompletionProposal(AnydslPackage.eINSTANCE
				.getCountryData());

		assertEquals("anydsl::CountryData", labelProvider.getText(completionProposal));
		assertImagesEquals(labelProvider.getELabelProvider().getImage(
				AnydslPackage.eINSTANCE.getCountryData()), labelProvider.getImage(completionProposal));
	}

	/**
	 * Tests an {@link EClassifierCompletionProposal} with an {@link org.eclipse.emf.ecore.EEnum EEnum}.
	 */
	@Test
	public void eClassifierCompletionProposalEEnum() {
		ICompletionProposal completionProposal = new EClassifierCompletionProposal(AnydslPackage.eINSTANCE
				.getColor());

		assertEquals("anydsl::Color", labelProvider.getText(completionProposal));
		assertImagesEquals(labelProvider.getELabelProvider().getImage(AnydslPackage.eINSTANCE.getColor()),
				labelProvider.getImage(completionProposal));
	}

	/**
	 * Tests an {@link EEnumLiteralCompletionProposal}.
	 */
	@Test
	public void eEnumLiteralCompletionProposal() {
		ICompletionProposal completionProposal = new EEnumLiteralCompletionProposal(AnydslPackage.eINSTANCE
				.getColor().getEEnumLiteral(0));

		assertEquals("anydsl::Color::black", labelProvider.getText(completionProposal));
		assertImagesEquals(labelProvider.getELabelProvider().getImage(
				AnydslPackage.eINSTANCE.getColor().getEEnumLiteral(0)), labelProvider
				.getImage(completionProposal));
	}

	/**
	 * Tests an {@link EFeatureCompletionProposal}.
	 */
	@Test
	public void eFeatureCompletionProposal() {
		ICompletionProposal completionProposal = new EFeatureCompletionProposal(AnydslPackage.eINSTANCE
				.getCompany().getEAllStructuralFeatures().get(0));

		assertEquals("name", labelProvider.getText(completionProposal));
		assertImagesEquals(labelProvider.getELabelProvider().getImage(
				AnydslPackage.eINSTANCE.getCompany().getEAllStructuralFeatures().get(0)), labelProvider
				.getImage(completionProposal));
	}

	/**
	 * Tests an {@link ServiceCompletionProposal}.
	 */
	@Test
	public void serviceCompletionProposal() {
		ICompletionProposal completionProposal = new ServiceCompletionProposal(new TestService());

		assertEquals("testService(java.lang.String, java.lang.String)", labelProvider
				.getText(completionProposal));
		assertImagesEquals(labelProvider.getServiceImage(), labelProvider.getImage(completionProposal));
	}

	/**
	 * Tests an {@link TextCompletionProposal}.
	 */
	@Test
	public void textCompletionProposal() {
		ICompletionProposal completionProposal = new TextCompletionProposal("someThing", 0);

		assertEquals("someThing", labelProvider.getText(completionProposal));
		assertImagesEquals(labelProvider.getTextImage(), labelProvider.getImage(completionProposal));
	}

	/**
	 * Tests an {@link VariableCompletionProposal}.
	 */
	@Test
	public void variableCompletionProposal() {
		ICompletionProposal completionProposal = new VariableCompletionProposal("aVariable");

		assertEquals("aVariable", labelProvider.getText(completionProposal));
		assertImagesEquals(labelProvider.getVariableImage(), labelProvider.getImage(completionProposal));
	}

	/**
	 * Asserts that the two given {@link Image} are equals.
	 * 
	 * @param expected
	 *            the expected {@link Image}
	 * @param actual
	 *            the actual {@link Image}
	 */
	public void assertImagesEquals(Image expected, Image actual) {
		assertEquals(expected.getBounds().x, expected.getBounds().x);
		assertEquals(expected.getBounds().y, expected.getBounds().y);
		for (int x = 0; x < expected.getBounds().x; ++x) {
			for (int y = 0; y < expected.getBounds().y; ++y) {
				assertEquals(expected.getImageData().getPixel(x, y), actual.getImageData().getPixel(x, y));
			}
		}
	}

	/**
	 * A test {@link IService} {@link Method}.
	 * 
	 * @param a
	 *            a {@link String}
	 * @param b
	 *            a {@link String}
	 * @return a {@link String}
	 */
	public String testService(String a, String b) {
		// method for testService
		return "";
	}

}

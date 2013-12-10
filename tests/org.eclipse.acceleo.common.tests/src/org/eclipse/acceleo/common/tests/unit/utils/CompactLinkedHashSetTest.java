package org.eclipse.acceleo.common.tests.unit.utils;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Collection;
import java.util.Iterator;
import java.util.Set;

import org.eclipse.acceleo.common.utils.CompactLinkedHashSet;
import org.junit.Test;

/**
 * Tests for the {@link CompactLinkedHashSet} behavior.
 * <p>
 * Most of the tests for the {@link CompactLinkedHashSet} are simply inherited from the
 * {@link CompactHashSetTest}s. We'll only add custom tests to check iteration order.
 * </p>
 * 
 * @author <a href="mailto:laurent.goubet@obeo.fr">Laurent Goubet</a>
 */
public class CompactLinkedHashSetTest extends CompactHashSetTest {
	/**
	 * Checks that this set's entries are iterated over in insertion order.
	 **/
	@Test
	public void testIteratorOrder() {
		Collection<Integer> listInt10 = randomIntegerList(10);
		Collection<String> setString20 = randomStringSet(20);
		Collection<String> dequeString40 = randomStringDeque(40);

		Set<Object> set = createSet();

		set.addAll(listInt10);
		set.addAll(setString20);
		set.addAll(dequeString40);

		Iterator<Integer> listIterator = listInt10.iterator();
		Iterator<String> setIterator = setString20.iterator();
		Iterator<String> dequeIterator = dequeString40.iterator();
		Iterator<Object> containedValues = set.iterator();
		while (listIterator.hasNext()) {
			assertEquals(listIterator.next(), containedValues.next());
		}
		assertTrue(containedValues.hasNext());
		while (setIterator.hasNext()) {
			assertEquals(setIterator.next(), containedValues.next());
		}
		assertTrue(containedValues.hasNext());
		while (dequeIterator.hasNext()) {
			assertEquals(dequeIterator.next(), containedValues.next());
		}
		assertFalse(containedValues.hasNext());

		for (Integer val : listInt10) {
			set.remove(val);
			assertFalse(set.contains(val));
		}
		set.addAll(listInt10);

		listIterator = listInt10.iterator();
		setIterator = setString20.iterator();
		dequeIterator = dequeString40.iterator();
		containedValues = set.iterator();
		while (setIterator.hasNext()) {
			assertEquals(setIterator.next(), containedValues.next());
		}
		assertTrue(containedValues.hasNext());
		while (dequeIterator.hasNext()) {
			assertEquals(dequeIterator.next(), containedValues.next());
		}
		assertTrue(containedValues.hasNext());
		while (listIterator.hasNext()) {
			assertEquals(listIterator.next(), containedValues.next());
		}
		assertFalse(containedValues.hasNext());
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.acceleo.common.tests.unit.utils.CompactHashSetTest#createSet()
	 */
	@Override
	protected Set<Object> createSet() {
		return new CompactLinkedHashSet<Object>();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.acceleo.common.tests.unit.utils.CompactHashSetTest#createSet(java.util.Collection)
	 */
	@Override
	protected Set<Object> createSet(Collection<? extends Object> collection) {
		return new CompactLinkedHashSet<Object>(collection);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.acceleo.common.tests.unit.utils.CompactHashSetTest#createSet(int)
	 */
	@Override
	protected Set<Object> createSet(int elementCount) {
		return new CompactLinkedHashSet<Object>(elementCount);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.acceleo.common.tests.unit.utils.CompactHashSetTest#createSet(int, float)
	 */
	@Override
	protected Set<Object> createSet(int elementCount, float loadFactor) {
		return new CompactLinkedHashSet<Object>(elementCount, loadFactor);
	}
}

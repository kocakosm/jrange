/*----------------------------------------------------------------------------*
 * This file is part of JRange.                                               *
 * Copyright (C) 2012 Osman KOCAK <kocakosm@gmail.com>                        *
 *                                                                            *
 * This program is free software: you can redistribute it and/or modify it    *
 * under the terms of the GNU Lesser General Public License as published by   *
 * the Free Software Foundation, either version 3 of the License, or (at your *
 * option) any later version.                                                 *
 * This program is distributed in the hope that it will be useful, but        *
 * WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY *
 * or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public     *
 * License for more details.                                                  *
 * You should have received a copy of the GNU Lesser General Public License   *
 * along with this program. If not, see <http://www.gnu.org/licenses/>.       *
 *----------------------------------------------------------------------------*/

package org.jrange;

import static org.jrange.SimpleInterval.from;

import static junit.framework.Assert.*;

import java.util.Arrays;
import java.util.Iterator;

import org.junit.Test;

/**
 * Empty interval tests.
 *
 * @author Osman KOCAK
 */
public final class EmptyIntervalTest
{
	private final Interval<Integer> e1;
	private final Interval<Integer> e2;
	private final Interval<Integer> i;

	public EmptyIntervalTest()
	{
		int lower = Utils.randomInt();
		int upper = lower + Utils.randomInt();
		this.i = from(Bound.closed(lower)).to(Bound.closed(upper));
		this.e1 = from(Bound.opened(0)).to(Bound.opened(0));
		this.e2 = (Interval<Integer>) Ranges.EMPTY_RANGE;
	}

	@Test(expected=NoSuchBoundException.class)
	public void testLowerBound1()
	{
		e1.lowerBound();
	}

	@Test(expected=NoSuchBoundException.class)
	public void testLowerBound2()
	{
		e2.lowerBound();
	}

	@Test(expected=NoSuchBoundException.class)
	public void testUpperBound1()
	{
		e1.upperBound();
	}

	@Test(expected=NoSuchBoundException.class)
	public void testUpperBound2()
	{
		e2.upperBound();
	}

	@Test(expected=IllegalStateException.class)
	public void testGap1()
	{
		e1.gap(i);
	}

	@Test(expected=IllegalStateException.class)
	public void testGap2()
	{
		e2.gap(i);
	}

	@Test(expected=NullPointerException.class)
	public void testGapWithNull1()
	{
		e1.gap(null);
	}

	@Test(expected=NullPointerException.class)
	public void testGapWithNull2()
	{
		e2.gap(null);
	}

	@Test
	public void testExpandTo()
	{
		int value = Utils.randomInt();
		Bound<Integer> bound = Bound.closed(value);
		Interval<Integer> degenerate = from(bound).to(bound);

		assertEquals(degenerate, e1.expandTo(value));
		assertEquals(degenerate, e2.expandTo(value));
	}

	@Test(expected=NullPointerException.class)
	public void testExpandToNull1()
	{
		e1.expandTo(null);
	}

	@Test(expected=NullPointerException.class)
	public void testExpandToNull2()
	{
		e2.expandTo(null);
	}

	@Test
	public void testClosure()
	{
		assertTrue(e1.closure().isEmpty());
		assertTrue(e2.closure().isEmpty());
	}

	@Test
	public void testInterior()
	{
		assertTrue(e1.interior().isEmpty());
		assertTrue(e2.interior().isEmpty());
	}

	@Test
	public void testIsConsecutiveTo()
	{
		assertFalse(e1.isConsecutiveTo(i));
		assertFalse(e1.isConsecutiveTo(e2));
		assertFalse(e1.isConsecutiveTo(e1));

		assertFalse(e2.isConsecutiveTo(i));
		assertFalse(e2.isConsecutiveTo(e2));
		assertFalse(e2.isConsecutiveTo(e1));
	}

	@Test(expected=NullPointerException.class)
	public void testIsConsecutiveToNull1()
	{
		e1.isConsecutiveTo(null);
	}

	@Test(expected=NullPointerException.class)
	public void testIsConsecutiveToNull2()
	{
		e2.isConsecutiveTo(null);
	}

	@Test
	public void testIsPartitionedBy1()
	{
		assertFalse(e1.isPartitionedBy(i));
		assertTrue(e1.isPartitionedBy(e1));
		assertTrue(e1.isPartitionedBy(e2));

		assertFalse(e2.isPartitionedBy(i));
		assertTrue(e2.isPartitionedBy(e1));
		assertTrue(e2.isPartitionedBy(e2));
	}

	@Test(expected=IllegalArgumentException.class)
	public void testIsPartitionedBy2()
	{
		e1.isPartitionedBy();
	}

	@Test(expected=IllegalArgumentException.class)
	public void testIsPartitionedBy3()
	{
		e2.isPartitionedBy();
	}

	@Test(expected=NullPointerException.class)
	public void testIsPartitionedByNull1()
	{
		Interval<Integer>[] intervals = null;
		e1.isPartitionedBy(intervals);
	}

	@Test(expected=NullPointerException.class)
	public void testIsPartitionedByNull2()
	{
		Interval<Integer>[] intervals = null;
		e2.isPartitionedBy(intervals);
	}

	@Test
	public void testIsEmpty()
	{
		assertTrue(e1.isEmpty());
		assertTrue(e2.isEmpty());
	}

	@Test
	public void testContains()
	{
		assertFalse(e1.contains(Utils.randomInt()));
		assertFalse(e2.contains(Utils.randomInt()));
	}

	@Test(expected=NullPointerException.class)
	public void testContainsNull1()
	{
		e1.contains(null);
	}

	@Test(expected=NullPointerException.class)
	public void testContainsNull2()
	{
		e2.contains(null);
	}

	@Test
	public void testIncludes()
	{
		assertTrue(e1.includes(e1));
		assertTrue(e1.includes(e2));

		assertTrue(e2.includes(e1));
		assertTrue(e2.includes(e2));

		assertFalse(e1.includes(i));
		assertFalse(e2.includes(i));
	}

	@Test(expected=NullPointerException.class)
	public void testIncludesNull1()
	{
		e1.includes(null);
	}

	@Test(expected=NullPointerException.class)
	public void testIncludesNull2()
	{
		e2.includes(null);
	}

	@Test
	public void testIntersects()
	{
		assertFalse(e1.intersects(e2));
		assertFalse(e2.intersects(e1));

		assertFalse(e1.intersects(i));
		assertFalse(e2.intersects(i));
	}

	@Test(expected=NullPointerException.class)
	public void testIntersectsWithNull1()
	{
		e1.intersects(null);
	}

	@Test(expected=NullPointerException.class)
	public void testIntersectsWithNull2()
	{
		e2.intersects(null);
	}

	@Test
	public void testIntersection()
	{
		assertTrue(e1.intersection(e1).isEmpty());
		assertTrue(e1.intersection(e2).isEmpty());

		assertTrue(e2.intersection(e1).isEmpty());
		assertTrue(e2.intersection(e2).isEmpty());

		assertTrue(e1.intersection(i).isEmpty());
		assertTrue(e2.intersection(i).isEmpty());
	}

	@Test(expected=NullPointerException.class)
	public void testIntersectionWithNull1()
	{
		e1.intersection(null);
	}

	@Test(expected=NullPointerException.class)
	public void testIntersectionWithNull2()
	{
		e2.intersection(null);
	}

	@Test
	public void testUnion()
	{
		assertTrue(e1.union(e1).isEmpty());
		assertTrue(e1.union(e2).isEmpty());

		assertTrue(e2.union(e1).isEmpty());
		assertTrue(e2.union(e2).isEmpty());
	}

	@Test(expected=NullPointerException.class)
	public void testUnionWithNull1()
	{
		e1.union(null);
	}

	@Test(expected=NullPointerException.class)
	public void testUnionWithNull2()
	{
		e2.union(null);
	}

	@Test
	public void testSubtraction()
	{
		assertTrue(e1.subtraction(e1).isEmpty());
		assertTrue(e1.subtraction(e2).isEmpty());
		assertTrue(e1.subtraction(i).isEmpty());

		assertTrue(e2.subtraction(e1).isEmpty());
		assertTrue(e2.subtraction(e2).isEmpty());
		assertTrue(e2.subtraction(i).isEmpty());
	}

	@Test(expected=NullPointerException.class)
	public void testSubtractionWithNull1()
	{
		e1.subtraction(null);
	}

	@Test(expected=NullPointerException.class)
	public void testSubtractionWithNull2()
	{
		e2.subtraction(null);
	}

	@Test
	public void testSplit()
	{
		assertEquals(Arrays.asList(e1), e1.split());
		assertEquals(Arrays.asList(e2), e2.split());
	}

	@Test
	public void testSequence()
	{
		int step = Utils.randomInt();
		Sequencer<Integer> sequencer = new IntegerSequencer(step);
		Sequence<Integer> sequence = new EmptySequence();

		assertEquals(sequence, e1.sequence(sequencer));
		assertEquals(sequence, e2.sequence(sequencer));
	}

	@Test(expected=NullPointerException.class)
	public void testSequenceByNull1()
	{
		e1.sequence(null);
	}

	@Test(expected=NullPointerException.class)
	public void testSequenceByNull2()
	{
		e2.sequence(null);
	}

	@Test
	public void testToString()
	{
		assertEquals(Character.toString((char) 8709), e1.toString());
		assertEquals(Character.toString((char) 8709), e2.toString());
	}

	@Test
	public void testEquals1()
	{
		assertEquals(e1, e2);
		assertEquals(e2, e1);
		assertEquals(e1.hashCode(), e2.hashCode());
	}

	@Test
	public void testEquals2()
	{
		assertFalse(e1.equals(i));
		assertFalse(e2.equals(i));
	}

	@Test
	public void testEquals3()
	{
		assertFalse(e1.equals((Interval<Integer>) null));
		assertFalse(e2.equals((Interval<Integer>) null));
	}

	private static class EmptySequence extends AbstractSequence<Integer>
	{
		@Override
		public Iterator<Integer> iterator()
		{
			return new EmptyIterator();
		}
	}

	private static class EmptyIterator implements Iterator<Integer>
	{
		@Override
		public boolean hasNext()
		{
			return false;
		}

		@Override
		public Integer next()
		{
			throw new UnsupportedOperationException();
		}

		@Override
		public void remove()
		{
			throw new UnsupportedOperationException();
		}
	}
}

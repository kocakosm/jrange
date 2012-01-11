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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;

/**
 * Interval tests.
 *
 * @author	Osman KOCAK
 */
public final class IntervalTest
{
	@Test(expected=NullPointerException.class)
	public void testCreationWithNullLowerBound()
	{
		Bound<Integer> bound = null;
		from(bound);
	}

	@Test(expected=NullPointerException.class)
	public void testCreationWithNullUpperBound()
	{
		Bound<Integer> lower = Bound.closed(1);
		Bound<Integer> upper = null;
		from(lower).to(upper);
	}

	@Test
	public void testLowerBound()
	{
		Bound<Integer> lower = Bound.closed(1);
		Bound<Integer> upper = Bound.opened(3);
		Interval<Integer> interval = from(lower).to(upper);

		assertEquals(lower, interval.lowerBound());
	}

	@Test
	public void testUpperBound()
	{
		Bound<Integer> lower = Bound.closed(1);
		Bound<Integer> upper = Bound.opened(3);
		Interval<Integer> interval = from(lower).to(upper);

		assertEquals(upper, interval.upperBound());
	}

	@Test
	public void testIntersection1()
	{
		Interval<Integer> interval1, interval2, intersection;
		interval1 = from(Bound.closed(1)).to(Bound.opened(10));
		interval2 = from(Bound.opened(15)).to(Bound.closed(25));
		intersection = (Interval<Integer>) Ranges.EMPTY_RANGE;

		assertEquals(intersection, interval1.intersection(interval2));
		assertEquals(intersection, interval2.intersection(interval1));
	}

	@Test
	public void testIntersection2()
	{
		Interval<Integer> interval1, interval2, intersection;
		interval1 = from(Bound.closed(1)).to(Bound.closed(15));
		interval2 = from(Bound.opened(15)).to(Bound.closed(25));
		intersection = (Interval<Integer>) Ranges.EMPTY_RANGE;

		assertEquals(intersection, interval1.intersection(interval2));
		assertEquals(intersection, interval2.intersection(interval1));
	}

	@Test
	public void testIntersection3()
	{
		Interval<Integer> interval1, interval2, intersection;
		interval1 = from(Bound.closed(1)).to(Bound.closed(15));
		interval2 = from(Bound.closed(15)).to(Bound.closed(25));
		intersection = from(Bound.closed(15)).to(Bound.closed(15));

		assertEquals(intersection, interval1.intersection(interval2));
		assertEquals(intersection, interval2.intersection(interval1));
	}

	@Test
	public void testIntersection4()
	{
		Interval<Integer> interval1, interval2, intersection;
		interval1 = from(Bound.opened(1)).to(Bound.opened(15));
		interval2 = from(Bound.closed(10)).to(Bound.closed(25));
		intersection = from(Bound.closed(10)).to(Bound.opened(15));

		assertEquals(intersection, interval1.intersection(interval2));
		assertEquals(intersection, interval2.intersection(interval1));
	}

	@Test
	public void testIntersection5()
	{
		Interval<Integer> interval1, interval2, intersection;
		interval1 = from(Bound.opened(1)).to(Bound.closed(25));
		interval2 = from(Bound.opened(10)).to(Bound.opened(20));
		intersection = from(Bound.opened(10)).to(Bound.opened(20));

		assertEquals(intersection, interval1.intersection(interval2));
		assertEquals(intersection, interval2.intersection(interval1));
	}

	@Test
	public void testIntersection6()
	{
		Interval<Integer> interval1, interval2, intersection;
		interval1 = from(Bound.opened(1)).to(Bound.closed(15));
		interval2 = from(Bound.closed(10)).to(Bound.opened(20));
		intersection = from(Bound.closed(10)).to(Bound.closed(15));

		assertEquals(intersection, interval1.intersection(interval2));
		assertEquals(intersection, interval2.intersection(interval1));
	}

	@Test
	public void testIntersection7()
	{
		Interval<Integer> interval1, interval2, intersection;
		interval1 = from(Bound.opened(1)).to(Bound.closed(15));
		interval2 = from(Bound.closed(15)).to(Bound.opened(20));
		intersection = from(Bound.closed(15)).to(Bound.closed(15));

		assertEquals(intersection, interval1.intersection(interval2));
		assertEquals(intersection, interval2.intersection(interval1));
	}

	@Test
	public void testIntersection8()
	{
		Interval<Integer> interval;
		Range<Integer> range, intersection;

		interval = from(Bound.opened(10)).to(Bound.opened(20));
		range = from(Bound.opened(5)).to(Bound.closed(15))
			.union(from(Bound.closed(17)).to(Bound.opened(25)));
		intersection = from(Bound.opened(10)).to(Bound.closed(15))
			.union(from(Bound.closed(17)).to(Bound.opened(20)));

		assertEquals(intersection, interval.intersection(range));
		assertEquals(intersection, range.intersection(interval));
	}

	@Test
	public void testIntersection9()
	{
		Interval<Integer> interval;
		interval = from(Bound.closed(1)).to(Bound.opened(20));

		assertEquals(interval, interval.intersection(interval));
	}

	@Test
	public void testIntersectionWithEmptyInterval()
	{
		Interval<Integer> interval1, interval2, intersection;
		interval1 = from(Bound.closed(1)).to(Bound.opened(20));
		interval2 = (Interval<Integer>) Ranges.EMPTY_RANGE;
		intersection = (Interval<Integer>) Ranges.EMPTY_RANGE;

		assertEquals(intersection, interval1.intersection(interval2));
		assertEquals(intersection, interval2.intersection(interval1));
	}

	@Test(expected=NullPointerException.class)
	public void testIntersectionWithNull()
	{
		Interval<Integer> interval1, interval2;
		interval1 = from(Bound.closed(1)).to(Bound.opened(20));
		interval2 = null;
		interval1.intersection(interval2);
	}

	@Test
	public void testGap1()
	{
		Interval<Integer> interval1, interval2, gap;
		interval1 = from(Bound.opened(0)).to(Bound.closed(9));
		interval2 = from(Bound.opened(11)).to(Bound.closed(20));
		gap = from(Bound.opened(9)).to(Bound.closed(11));

		assertEquals(gap, interval1.gap(interval2));
		assertEquals(gap, interval2.gap(interval1));
	}

	@Test
	public void testGap2()
	{
		Interval<Integer> interval1, interval2, gap;
		interval1 = from(Bound.closed(0)).to(Bound.opened(9));
		interval2 = from(Bound.closed(11)).to(Bound.opened(20));
		gap = from(Bound.closed(9)).to(Bound.opened(11));

		assertEquals(gap, interval1.gap(interval2));
		assertEquals(gap, interval2.gap(interval1));
	}

	@Test
	public void testGap3()
	{
		Interval<Integer> interval, gap;
		interval = from(Bound.opened(0)).to(Bound.closed(9));
		gap = (Interval<Integer>) Ranges.EMPTY_RANGE;

		assertEquals(gap, interval.gap(interval));
	}

	@Test
	public void testGap4()
	{
		Interval<Integer> interval1, interval2, gap;
		interval1 = from(Bound.opened(0)).to(Bound.closed(9));
		interval2 = from(Bound.closed(5)).to(Bound.opened(15));
		gap = (Interval<Integer>) Ranges.EMPTY_RANGE;

		assertEquals(gap, interval1.gap(interval2));
		assertEquals(gap, interval2.gap(interval1));
	}

	@Test(expected=IllegalArgumentException.class)
	public void testGapWithEmptyInterval()
	{
		Interval<Integer> interval1, interval2;
		interval1 = from(Bound.closed(1)).to(Bound.opened(20));
		interval2 = (Interval<Integer>) Ranges.EMPTY_RANGE;
		interval1.gap(interval2);
	}

	@Test(expected=NullPointerException.class)
	public void testGapWithNull()
	{
		Interval<Integer> interval1, interval2;
		interval1 = from(Bound.closed(1)).to(Bound.opened(20));
		interval2 = null;
		interval1.gap(interval2);
	}

	@Test
	public void testExpandTo()
	{
		Interval<Integer> interval, expanded;
		interval = from(Bound.opened(0)).to(Bound.opened(9));

		assertEquals(interval, interval.expandTo(4));
	}

	@Test
	public void testExpandTo2()
	{
		Interval<Integer> interval, expanded;
		interval = from(Bound.opened(0)).to(Bound.opened(9));
		int value = 9 + Utils.randomInt();
		expanded = from(Bound.opened(0)).to(Bound.closed(value));

		assertEquals(expanded, interval.expandTo(value));
	}

	@Test
	public void testExpandTo3()
	{
		Interval<Integer> interval, expanded;
		interval = from(Bound.opened(0)).to(Bound.opened(9));
		int value = 0 - Utils.randomInt();
		expanded = from(Bound.closed(value)).to(Bound.opened(9));

		assertEquals(expanded, interval.expandTo(value));
	}

	@Test(expected=NullPointerException.class)
	public void testExpandToNull()
	{
		Interval<Integer> interval;
		interval = from(Bound.closed(1)).to(Bound.opened(20));
		Integer value = null;
		interval.expandTo(value);
	}

	@Test
	public void testClosure()
	{
		Interval<Integer> interval, closure;

		interval = from(Bound.closed(0)).to(Bound.closed(10));
		closure = from(Bound.closed(0)).to(Bound.closed(10));
		assertEquals(closure, interval.closure());

		interval = from(Bound.opened(0)).to(Bound.closed(10));
		assertEquals(closure, interval.closure());

		interval = from(Bound.closed(0)).to(Bound.opened(10));
		assertEquals(closure, interval.closure());

		interval = from(Bound.opened(0)).to(Bound.opened(10));
		assertEquals(closure, interval.closure());
	}

	@Test
	public void testInterior()
	{
		Interval<Integer> interval, interior;

		interval = from(Bound.closed(0)).to(Bound.closed(10));
		interior = from(Bound.opened(0)).to(Bound.opened(10));
		assertEquals(interior, interval.interior());

		interval = from(Bound.opened(0)).to(Bound.closed(10));
		assertEquals(interior, interval.interior());

		interval = from(Bound.closed(0)).to(Bound.opened(10));
		assertEquals(interior, interval.interior());

		interval = from(Bound.opened(0)).to(Bound.opened(10));
		assertEquals(interior, interval.interior());
	}

	@Test
	public void testIsConsecutiveTo1()
	{
		Interval<Integer> interval1, interval2;
		interval1 = from(Bound.closed(1)).to(Bound.opened(20));
		interval2 = from(Bound.closed(20)).to(Bound.opened(24));

		assertFalse(interval1.isConsecutiveTo(interval2));
		assertTrue(interval2.isConsecutiveTo(interval1));
	}

	@Test
	public void testIsConsecutiveTo2()
	{
		Interval<Integer> interval1, interval2;

		interval1 = from(Bound.closed(1)).to(Bound.opened(20));
		interval2 = from(Bound.opened(20)).to(Bound.opened(24));
		assertFalse(interval1.isConsecutiveTo(interval2));

		interval1 = from(Bound.closed(1)).to(Bound.opened(15));
		interval2 = from(Bound.closed(20)).to(Bound.opened(24));
		assertFalse(interval1.isConsecutiveTo(interval2));
	}

	@Test
	public void testIsConsecutiveToEmptyInterval()
	{
		Interval<Integer> interval1, interval2;
		interval1 = from(Bound.closed(1)).to(Bound.opened(20));
		interval2 = (Interval<Integer>) Ranges.EMPTY_RANGE;

		assertFalse(interval1.isConsecutiveTo(interval2));
		assertFalse(interval2.isConsecutiveTo(interval1));
	}

	@Test(expected=NullPointerException.class)
	public void testIsConsecutiveToNull()
	{
		Interval<Integer> interval1, interval2;
		interval1 = from(Bound.closed(1)).to(Bound.opened(20));
		interval2 = null;
		interval1.isConsecutiveTo(interval2);
	}

	@Test
	public void testIsPartitionedBy1()
	{
		Interval<Integer> interval;
		interval = from(Bound.closed(1)).to(Bound.closed(40));

		assertTrue(interval.isPartitionedBy(interval));
	}

	@Test
	public void testIsPartitionedBy2()
	{
		Interval<Integer> interval1, interval2, interval3, interval4;
		interval1 = from(Bound.closed(1)).to(Bound.closed(40));
		interval2 = from(Bound.closed(1)).to(Bound.opened(20));
		interval3 = from(Bound.closed(20)).to(Bound.closed(30));
		interval4 = from(Bound.opened(30)).to(Bound.closed(40));

		assertTrue(interval1
			.isPartitionedBy(interval2, interval3, interval4));
	}

	@Test
	public void testIsPartitionedBy3()
	{
		Interval<Integer> interval1, interval2, interval3;
		interval1 = from(Bound.closed(10)).to(Bound.closed(1));
		interval2 = from(Bound.closed(1)).to(Bound.opened(20));
		interval3 = from(Bound.closed(20)).to(Bound.closed(30));

		assertFalse(interval1.isPartitionedBy(interval2, interval3));
	}

	@Test
	public void testIsPartitionedBy4()
	{
		Interval<Integer> interval1, interval2, interval3;
		interval1 = from(Bound.closed(1)).to(Bound.closed(30));
		interval2 = from(Bound.closed(1)).to(Bound.closed(19));
		interval3 = from(Bound.closed(20)).to(Bound.closed(30));

		assertFalse(interval1.isPartitionedBy(interval2, interval3));
	}

	@Test
	public void testIsPartitionedBy5()
	{
		Interval<Integer> interval1, interval2, interval3;
		interval1 = from(Bound.closed(1)).to(Bound.closed(-1));
		interval2 = from(Bound.closed(10)).to(Bound.closed(5));
		interval3 = from(Bound.closed(20)).to(Bound.closed(5));

		assertTrue(interval1.isPartitionedBy(interval2, interval3));
	}

	@Test(expected=IllegalArgumentException.class)
	public void testIsPartitionedByNoInterval()
	{
		Interval<Integer> interval;
		interval = from(Bound.closed(1)).to(Bound.opened(15));
		interval.isPartitionedBy();
	}

	@Test
	public void testIsPartitionedByEmptyInterval()
	{
		Interval<Integer> interval1, interval2;
		interval1 = from(Bound.closed(1)).to(Bound.opened(15));
		interval2 = (Interval<Integer>) Ranges.EMPTY_RANGE;

		assertFalse(interval1.isPartitionedBy(interval2));
	}

	@Test(expected=NullPointerException.class)
	public void testIsPartitionedByNull()
	{
		Interval<Integer> interval1, interval2;
		interval1 = from(Bound.closed(1)).to(Bound.opened(20));
		interval2 = null;
		interval1.isPartitionedBy(interval2);
	}

	@Test
	public void testIsEmpty()
	{
		Interval<Integer> interval1, interval2;
		interval1 = from(Bound.closed(0)).to(Bound.opened(0));
		interval2 = from(Bound.opened(-1)).to(Bound.opened(12));

		assertTrue(interval1.isEmpty());
		assertFalse(interval2.isEmpty());
	}

	@Test
	public void testContains()
	{
		Interval<Integer> interval;
		interval = from(Bound.closed(2)).to(Bound.opened(12));

		assertTrue(interval.contains(2));
		assertTrue(interval.contains(5));
		assertTrue(interval.contains(10));
		assertFalse(interval.contains(12));
		assertFalse(interval.contains(15));
		assertFalse(interval.contains(0));
	}

	@Test
	public void testIncludes1()
	{
		Interval<Integer> interval1, interval2;
		interval1 = from(Bound.closed(2)).to(Bound.opened(12));
		interval2 = from(Bound.closed(3)).to(Bound.opened(7));

		assertTrue(interval1.includes(interval2));
		assertFalse(interval2.includes(interval1));
	}

	@Test
	public void testIncludes2()
	{
		Interval<Integer> interval1, interval2;
		interval1 = from(Bound.closed(2)).to(Bound.opened(12));
		interval2 = from(Bound.closed(2)).to(Bound.opened(7));

		assertTrue(interval1.includes(interval2));
		assertFalse(interval2.includes(interval1));
	}

	@Test
	public void testIncludes3()
	{
		Interval<Integer> interval1, interval2;
		interval1 = from(Bound.closed(2)).to(Bound.opened(12));
		interval2 = from(Bound.closed(5)).to(Bound.opened(12));

		assertTrue(interval1.includes(interval2));
		assertFalse(interval2.includes(interval1));
	}

	@Test
	public void testIncludes4()
	{
		Interval<Integer> interval1, interval2;
		interval1 = from(Bound.closed(2)).to(Bound.closed(12));
		interval2 = from(Bound.opened(3)).to(Bound.opened(7));

		assertTrue(interval1.includes(interval1));
		assertTrue(interval2.includes(interval2));
	}

	@Test
	public void testIncludesEmptyInterval()
	{
		Interval<Integer> interval1, interval2;
		interval1 = from(Bound.closed(2)).to(Bound.opened(12));
		interval2 = (Interval<Integer>) Ranges.EMPTY_RANGE;

		assertTrue(interval1.includes(interval2));
		assertFalse(interval2.includes(interval1));
	}

	@Test(expected=NullPointerException.class)
	public void testIncludesNull()
	{
		Interval<Integer> interval1, interval2;
		interval1 = from(Bound.closed(2)).to(Bound.opened(12));
		interval2 = null;

		interval1.includes(interval2);
	}

	@Test
	public void testIntersects1()
	{
		Interval<Integer> interval1, interval2;

		interval1 = from(Bound.closed(2)).to(Bound.opened(12));
		interval2 = from(Bound.closed(12)).to(Bound.opened(18));
		assertFalse(interval1.intersects(interval2));
		assertFalse(interval2.intersects(interval1));
	}

	@Test
	public void testIntersects2()
	{
		Interval<Integer> interval1, interval2;

		interval1 = from(Bound.opened(2)).to(Bound.closed(12));
		interval2 = from(Bound.closed(12)).to(Bound.opened(18));
		assertTrue(interval1.intersects(interval2));
		assertTrue(interval2.intersects(interval1));
	}

	@Test
	public void testIntersects3()
	{
		Interval<Integer> interval1, interval2;

		interval1 = from(Bound.opened(2)).to(Bound.closed(12));
		interval2 = from(Bound.closed(5)).to(Bound.opened(10));
		assertTrue(interval1.intersects(interval2));
		assertTrue(interval2.intersects(interval1));
	}

	@Test
	public void testIntersectsEmptyInterval()
	{
		Interval<Integer> interval1, interval2;
		interval1 = from(Bound.closed(2)).to(Bound.opened(12));
		interval2 = (Interval<Integer>) Ranges.EMPTY_RANGE;

		assertFalse(interval1.intersects(interval2));
		assertFalse(interval2.intersects(interval1));
	}

	@Test(expected=NullPointerException.class)
	public void testIntersectsNull()
	{
		Interval<Integer> interval1, interval2;
		interval1 = from(Bound.closed(2)).to(Bound.opened(12));
		interval2 = null;

		interval1.intersects(interval2);
	}

	@Test
	public void testUnion1()
	{
		Interval<Integer> interval1, interval2, union;
		interval1 = from(Bound.closed(2)).to(Bound.opened(12));
		interval2 = from(Bound.opened(10)).to(Bound.closed(20));
		union = from(Bound.closed(2)).to(Bound.closed(20));

		assertEquals(union, interval1.union(interval2));
		assertEquals(union, interval2.union(interval1));
	}

	@Test
	public void testUnion2()
	{
		Interval<Integer> interval;
		interval = from(Bound.closed(2)).to(Bound.opened(12));

		assertEquals(interval, interval.union(interval));
	}

	@Test
	public void testUnion3()
	{
		Interval<Integer> interval1, interval2, union;
		interval1 = from(Bound.closed(2)).to(Bound.closed(20));
		interval2 = from(Bound.opened(5)).to(Bound.opened(10));
		union = from(Bound.closed(2)).to(Bound.closed(20));

		assertEquals(union, interval1.union(interval2));
		assertEquals(union, interval2.union(interval1));
	}

	@Test
	public void testUnionWithEmptyInterval()
	{
		Interval<Integer> interval1, interval2;
		interval1 = from(Bound.closed(2)).to(Bound.opened(12));
		interval2 = (Interval<Integer>) Ranges.EMPTY_RANGE;

		assertEquals(interval1, interval1.union(interval2));
		assertEquals(interval1, interval2.union(interval1));
	}

	@Test(expected=NullPointerException.class)
	public void testUnionWithNull()
	{
		Interval<Integer> interval1, interval2;
		interval1 = from(Bound.closed(2)).to(Bound.opened(12));
		interval2 = null;

		interval1.union(interval2);
	}

	@Test
	public void testSubtraction1()
	{
		Interval<Integer> interval1, interval2;
		Range<Integer> subtraction;
		interval1 = from(Bound.closed(2)).to(Bound.opened(15));
		interval2 = from(Bound.opened(5)).to(Bound.closed(10));
		subtraction = from(Bound.closed(2)).to(Bound.closed(5))
			.union(from(Bound.opened(10)).to(Bound.opened(15)));

		assertEquals(subtraction, interval1.subtraction(interval2));
	}

	@Test
	public void testSubtraction2()
	{
		Interval<Integer> interval, subtraction;
		interval = from(Bound.closed(2)).to(Bound.opened(12));
		subtraction = (Interval<Integer>) Ranges.EMPTY_RANGE;

		assertEquals(subtraction, interval.subtraction(interval));
	}

	@Test
	public void testSubtraction3()
	{
		Interval<Integer> interval1, interval2;
		Range<Integer> subtraction;
		interval1 = from(Bound.closed(2)).to(Bound.opened(15));
		interval2 = from(Bound.opened(0)).to(Bound.closed(20));
		subtraction = (Interval<Integer>) Ranges.EMPTY_RANGE;

		assertEquals(subtraction, interval1.subtraction(interval2));
	}

	@Test
	public void testSubtractionByEmptyInterval()
	{
		Interval<Integer> interval1, interval2;
		interval1 = from(Bound.closed(2)).to(Bound.opened(12));
		interval2 = (Interval<Integer>) Ranges.EMPTY_RANGE;

		assertEquals(interval1, interval1.subtraction(interval2));
	}

	@Test(expected=NullPointerException.class)
	public void testSubstractionByNull()
	{
		Interval<Integer> interval1, interval2;
		interval1 = from(Bound.closed(2)).to(Bound.opened(12));
		interval2 = null;

		interval1.subtraction(interval2);
	}

	@Test
	public void testSplit()
	{
		Interval<Integer> interval;
		interval = from(Bound.closed(2)).to(Bound.opened(12));

		assertEquals(Arrays.asList(interval), interval.split());
	}

	@Test
	public void testSequence()
	{
		Interval<Integer> interval;
		interval = from(Bound.opened(0)).to(Bound.closed(15));
		Sequencer<Integer> sequencer = new IntegerSequencer(3);
		Sequence<Integer> sequence = interval.sequence(sequencer);
		List<Integer> sequenced = new ArrayList<Integer>();
		for (Integer i : sequence) {
			sequenced.add(i);
		}

		assertEquals(sequence, sequence);
		assertEquals(sequence.hashCode(), sequence.hashCode());
		assertFalse(sequence.equals((Sequence<Integer>) null));
		assertEquals(Arrays.asList(3, 6, 9, 12, 15), sequenced);
	}

	@Test(expected=NullPointerException.class)
	public void testSequenceByNull()
	{
		Interval<Integer> interval;
		interval = from(Bound.opened(0)).to(Bound.closed(15));
		Sequencer<Integer> sequencer = null;
		interval.sequence(sequencer);
	}

	@Test
	public void testToString()
	{
		Interval<Integer> interval;

		interval = from(Bound.opened(5)).to(Bound.closed(11));
		assertEquals("]5, 11]", interval.toString());

		interval = from(Bound.closed(42)).to(Bound.opened(65));
		assertEquals("[42, 65[", interval.toString());

		interval = from(Bound.opened(9)).to(Bound.opened(15));
		assertEquals("]9, 15[", interval.toString());

		interval = from(Bound.closed(-5)).to(Bound.closed(-1));
		assertEquals("[-5, -1]", interval.toString());
	}

	@Test
	public void testEquals1()
	{
		Interval<Integer> interval1, interval2;
		interval1 = from(Bound.opened(5)).to(Bound.closed(11));
		interval2 = from(Bound.opened(5)).to(Bound.closed(11));

		assertEquals(interval1, interval2);
		assertEquals(interval2, interval1);
		assertEquals(interval1.hashCode(), interval2.hashCode());
	}

	@Test
	public void testEquals2()
	{
		Interval<Integer> interval;
		interval = from(Bound.opened(5)).to(Bound.closed(11));

		assertEquals(interval, interval);
		assertEquals(interval.hashCode(), interval.hashCode());
	}

	@Test
	public void testEquals3()
	{
		Interval<Integer> interval1, interval2;
		interval1 = from(Bound.opened(5)).to(Bound.closed(11));
		interval2 = null;

		assertFalse(interval1.equals(interval2));
	}

	@Test
	public void testEquals4()
	{
		Interval<Integer> interval1, interval2;
		interval1 = from(Bound.opened(5)).to(Bound.closed(11));
		interval2 = from(Bound.opened(5)).to(Bound.closed(10));

		assertFalse(interval1.equals(interval2));
		assertFalse(interval2.equals(interval1));
	}
}

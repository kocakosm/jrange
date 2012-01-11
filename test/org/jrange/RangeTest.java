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
 * Range tests.
 *
 * @author	Osman KOCAK
 */
public final class RangeTest
{
	@Test
	public void testIsEmpty()
	{
		Range<Integer> range1, range2;
		range1 = from(Bound.closed(0)).to(Bound.opened(0))
			.union(from(Bound.closed(0)).to(Bound.closed(-1)));
		range2 = from(Bound.opened(-1)).to(Bound.opened(12))
			.union(from(Bound.closed(15)).to(Bound.opened(20)));

		assertTrue(range1.isEmpty());
		assertFalse(range2.isEmpty());
	}

	@Test
	public void testContains()
	{
		Range<Integer> range;
		range = from(Bound.closed(2)).to(Bound.opened(12))
			.union(from(Bound.closed(15)).to(Bound.opened(20)));

		assertFalse(range.contains(0));
		assertTrue(range.contains(2));
		assertTrue(range.contains(5));
		assertTrue(range.contains(10));
		assertFalse(range.contains(12));
		assertFalse(range.contains(13));
		assertFalse(range.contains(14));
		assertTrue(range.contains(15));
		assertTrue(range.contains(17));
		assertTrue(range.contains(19));
		assertFalse(range.contains(20));
	}

	@Test
	public void testIncludes1()
	{
		Range<Integer> range1, range2;
		range1 = from(Bound.closed(2)).to(Bound.opened(12))
			.union(from(Bound.closed(15)).to(Bound.opened(20)));
		range2 = from(Bound.closed(3)).to(Bound.opened(6))
			.union(from(Bound.closed(7)).to(Bound.opened(11)))
			.union(from(Bound.closed(16)).to(Bound.opened(19)));

		assertTrue(range1.includes(range2));
		assertFalse(range2.includes(range1));
	}

	@Test
	public void testIncludes2()
	{
		Range<Integer> range;
		range = from(Bound.closed(3)).to(Bound.opened(6))
			.union(from(Bound.closed(7)).to(Bound.opened(11)))
			.union(from(Bound.closed(16)).to(Bound.opened(19)));

		assertTrue(range.includes(range));
	}

	@Test
	public void testIncludesEmptyInterval()
	{
		Range<Integer> range1, range2;
		range1 = from(Bound.closed(2)).to(Bound.opened(12))
			.union(from(Bound.closed(15)).to(Bound.opened(20)));
		range2 = (Range<Integer>) Ranges.EMPTY_RANGE;

		assertTrue(range1.includes(range2));
		assertFalse(range2.includes(range1));
	}

	@Test(expected=NullPointerException.class)
	public void testIncludesNull()
	{
		Range<Integer> range1, range2;
		range1 = from(Bound.closed(2)).to(Bound.opened(10))
			.union(from(Bound.opened(15)).to(Bound.opened(20)));
		range2 = null;

		range1.includes(range2);
	}

	@Test
	public void testIntersects1()
	{
		Range<Integer> range1, range2;
		range1 = from(Bound.closed(2)).to(Bound.opened(10))
			.union(from(Bound.opened(15)).to(Bound.opened(20)));
		range2 = from(Bound.closed(5)).to(Bound.opened(9))
			.union(from(Bound.opened(21)).to(Bound.opened(25)));

		assertTrue(range1.intersects(range2));
		assertTrue(range2.intersects(range1));
	}

	@Test
	public void testIntersects2()
	{
		Range<Integer> range;
		range = from(Bound.closed(2)).to(Bound.opened(10))
			.union(from(Bound.opened(15)).to(Bound.opened(20)))
			.union(from(Bound.opened(-8)).to(Bound.closed(-1)));

		assertTrue(range.intersects(range));
	}

	@Test
	public void testIntersectsEmptyInterval()
	{
		Range<Integer> range1, range2;
		range1 = from(Bound.closed(2)).to(Bound.opened(10))
			.union(from(Bound.opened(15)).to(Bound.opened(20)));
		range2 = (Range<Integer>) Ranges.EMPTY_RANGE;

		assertFalse(range1.intersects(range2));
		assertFalse(range2.intersects(range1));
	}

	@Test(expected=NullPointerException.class)
	public void testIntersectsNull()
	{
		Range<Integer> range1, range2;
		range1 = from(Bound.closed(2)).to(Bound.opened(10))
			.union(from(Bound.opened(15)).to(Bound.opened(20)));
		range2 = null;

		range1.intersects(range2);
	}

	@Test
	public void testIntersection1()
	{
		Range<Integer> range1, range2, intersection;
		range1 = from(Bound.closed(1)).to(Bound.opened(20))
			.union(from(Bound.closed(0)).to(Bound.closed(5)));
		range2 = from(Bound.opened(5)).to(Bound.closed(15))
			.union(from(Bound.closed(17)).to(Bound.opened(25)));
		intersection = from(Bound.opened(5)).to(Bound.closed(15))
			.union(from(Bound.closed(17)).to(Bound.opened(20)));

		assertEquals(intersection, range1.intersection(range2));
		assertEquals(intersection, range2.intersection(range1));
	}

	@Test
	public void testIntersection2()
	{
		Range<Integer> range1, range2, intersection;
		range1 = from(Bound.closed(1)).to(Bound.opened(5))
			.union(from(Bound.closed(6)).to(Bound.closed(12)));
		range2 = from(Bound.opened(15)).to(Bound.closed(25))
			.union(from(Bound.opened(30)).to(Bound.opened(35)));
		intersection = (Interval<Integer>) Ranges.EMPTY_RANGE;

		assertEquals(intersection, range1.intersection(range2));
		assertEquals(intersection, range2.intersection(range1));
	}

	@Test
	public void testIntersection3()
	{
		Range<Integer> range;
		range = from(Bound.opened(-1)).to(Bound.closed(9))
			.union(from(Bound.opened(11)).to(Bound.opened(19)));

		assertEquals(range, range.intersection(range));
	}

	@Test
	public void testIntersectionWithEmptyRange()
	{
		Range<Integer> range1, range2, intersection;
		range1 = from(Bound.closed(1)).to(Bound.opened(10))
			.union(from(Bound.closed(15)).to(Bound.opened(20)));
		range2 = (Range<Integer>) Ranges.EMPTY_RANGE;
		intersection = (Range<Integer>) Ranges.EMPTY_RANGE;

		assertEquals(intersection, range1.intersection(range2));
		assertEquals(intersection, range2.intersection(range1));
	}

	@Test(expected=NullPointerException.class)
	public void testIntersectionWithNull()
	{
		Range<Integer> range1, range2;
		range1 = from(Bound.opened(-1)).to(Bound.closed(9))
			.union(from(Bound.opened(11)).to(Bound.opened(19)));
		range2 = null;

		range1.intersection(range2);
	}

	@Test
	public void testUnion1()
	{
		Range<Integer> range1, range2, union;
		range1 = from(Bound.closed(2)).to(Bound.opened(12));
		range2 = from(Bound.opened(15)).to(Bound.closed(20));
		union = from(Bound.closed(2)).to(Bound.opened(12))
			.union(from(Bound.opened(15)).to(Bound.closed(20)));

		assertEquals(union, range1.union(range2));
		assertEquals(union, range2.union(range1));
		assertTrue(union.includes(range1));
		assertTrue(union.includes(range2));
	}

	@Test
	public void testUnion2()
	{
		Range<Integer> range;
		range = from(Bound.closed(2)).to(Bound.opened(10))
			.union(from(Bound.opened(15)).to(Bound.opened(20)));

		assertEquals(range, range.union(range));
	}

	@Test
	public void testUnionWithEmptyInterval()
	{
		Range<Integer> range1, range2;
		range1 = from(Bound.closed(2)).to(Bound.opened(10))
			.union(from(Bound.opened(15)).to(Bound.opened(20)));
		range2 = (Interval<Integer>) Ranges.EMPTY_RANGE;

		assertEquals(range1, range1.union(range2));
		assertEquals(range1, range2.union(range1));
	}

	@Test(expected=NullPointerException.class)
	public void testUnionWithNull()
	{
		Range<Integer> range1, range2;
		range1 = from(Bound.closed(2)).to(Bound.opened(10))
			.union(from(Bound.opened(15)).to(Bound.opened(20)));
		range2 = null;
		range1.union(range2);
	}

	@Test
	public void testSubtraction1()
	{
		Range<Integer> range1, range2, subtraction;
		range1 = from(Bound.opened(0)).to(Bound.closed(20));
		range2 = from(Bound.closed(5)).to(Bound.closed(5))
			.union(from(Bound.opened(10)).to(Bound.closed(15)));
		subtraction = from(Bound.opened(0)).to(Bound.opened(5))
			.union(from(Bound.opened(5)).to(Bound.closed(10)))
			.union(from(Bound.opened(15)).to(Bound.closed(20)));

		assertEquals(subtraction, range1.subtraction(range2));
	}

	@Test
	public void testSubtraction2()
	{
		Range<Integer> range1, range2, subtraction;
		range1 = from(Bound.opened(0)).to(Bound.closed(20))
			.union(from(Bound.closed(25)).to(Bound.opened(30)));
		range2 = from(Bound.closed(-5)).to(Bound.opened(2))
			.union(from(Bound.closed(5)).to(Bound.closed(5)))
			.union(from(Bound.opened(10)).to(Bound.closed(15)))
			.union(from(Bound.closed(19)).to(Bound.closed(26)))
			.union(from(Bound.opened(28)).to(Bound.closed(35)));
		subtraction = from(Bound.closed(2)).to(Bound.opened(5))
			.union(from(Bound.opened(5)).to(Bound.closed(10)))
			.union(from(Bound.opened(15)).to(Bound.opened(19)))
			.union(from(Bound.opened(26)).to(Bound.closed(28)));

		assertEquals(subtraction, range1.subtraction(range2));
	}

	@Test
	public void testSubtraction3()
	{
		Range<Integer> range;
		range = from(Bound.closed(2)).to(Bound.opened(10))
			.union(from(Bound.opened(15)).to(Bound.opened(20)));

		assertEquals(Ranges.EMPTY_RANGE, range.subtraction(range));
	}

	@Test
	public void testSubtractionByEmptyInterval()
	{
		Range<Integer> range1, range2;
		range1 = from(Bound.closed(2)).to(Bound.opened(10))
			.union(from(Bound.opened(15)).to(Bound.opened(20)));
		range2 = (Range<Integer>) Ranges.EMPTY_RANGE;

		assertEquals(range1, range1.subtraction(range2));
	}

	@Test(expected=NullPointerException.class)
	public void testSubstractionByNull()
	{
		Range<Integer> range1, range2;
		range1 = from(Bound.opened(-2)).to(Bound.opened(5));
		range2 = null;
		range1.subtraction(range2);
	}

	@Test
	public void testSplit()
	{
		Range<Integer> range;
		Interval<Integer> interval1, interval2, interval3;
		interval1 = from(Bound.closed(2)).to(Bound.opened(12));
		interval2 = from(Bound.opened(12)).to(Bound.opened(18));
		interval3 = from(Bound.closed(20)).to(Bound.opened(25));
		range = interval3.union(interval1).union(interval2)
			.union(interval2).union(interval3).union(interval1);
		List<Interval<Integer>> expected =
			Arrays.asList(interval1, interval2, interval3);

		assertEquals(expected, range.split());
	}

	@Test
	public void testSequence()
	{
		Range<Integer> range;
		range = from(Bound.opened(-2)).to(Bound.closed(3))
			.union(from(Bound.closed(5)).to(Bound.opened(9)))
			.union(from(Bound.closed(11)).to(Bound.closed(14)));
		Sequencer<Integer> sequencer = new IntegerSequencer(2);
		Sequence<Integer> sequence = range.sequence(sequencer);
		List<Integer> sequenced = new ArrayList<Integer>();
		for (Integer i : sequence) {
			sequenced.add(i);
		}

		assertEquals(sequence, sequence);
		assertEquals(sequence.hashCode(), sequence.hashCode());
		assertFalse(sequence.equals((Sequence<Integer>) null));
		assertEquals(Arrays.asList(0, 2, 5, 7, 11, 13), sequenced);
	}

	@Test(expected=NullPointerException.class)
	public void testSequenceByNull()
	{
		Range<Integer> range;
		range = from(Bound.opened(0)).to(Bound.closed(15))
			.union(from(Bound.opened(20)).to(Bound.closed(25)));
		Sequencer<Integer> sequencer = null;
		range.sequence(sequencer);
	}

	@Test
	public void testToString()
	{
		Range<Integer> range;
		range = from(Bound.opened(0)).to(Bound.closed(15))
			.union(from(Bound.closed(20)).to(Bound.opened(25)));

		assertEquals("]0, 15] U [20, 25[", range.toString());
	}

	@Test
	public void testEquals1()
	{
		Range<Integer> range1, range2;
		range1 = from(Bound.closed(-10)).to(Bound.closed(-5))
			.union(from(Bound.closed(3)).to(Bound.opened(9)));
		range2 = from(Bound.closed(-10)).to(Bound.closed(-5))
			.union(from(Bound.closed(3)).to(Bound.opened(9)));

		assertEquals(range1, range2);
		assertEquals(range2, range1);
		assertEquals(range1.hashCode(), range2.hashCode());
	}

	@Test
	public void testEquals2()
	{
		Range<Integer> range;
		range = from(Bound.closed(-1)).to(Bound.closed(5))
			.union(from(Bound.closed(10)).to(Bound.opened(15)));

		assertEquals(range, range);
		assertEquals(range.hashCode(), range.hashCode());
	}

	@Test
	public void testEquals3()
	{
		Range<Integer> range1, range2;
		range1 = from(Bound.closed(-1)).to(Bound.closed(5))
			.union(from(Bound.closed(10)).to(Bound.opened(15)));
		range2 = null;

		assertFalse(range1.equals(range2));
	}

	@Test
	public void testEquals4()
	{
		Range<Integer> range1, range2;
		range1 = from(Bound.closed(-1)).to(Bound.closed(5))
			.union(from(Bound.closed(10)).to(Bound.opened(15)));
		range2 = from(Bound.closed(10)).to(Bound.closed(15))
			.union(from(Bound.closed(15)).to(Bound.opened(20)));

		assertFalse(range1.equals(range2));
		assertFalse(range2.equals(range1));
	}
}

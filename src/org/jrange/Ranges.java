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

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Utility class that contains only static methods that operate on or return
 * {@code Range}s.
 *
 * @author Osman KOCAK
 */
public final class Ranges
{
	/** The empty range (immutable). */
	public static final Range EMPTY_RANGE = new EmptyInterval();

	/**
	 * Returns the immutable empty range for a particular type (type-safe).
	 * Note that unlike this method, the like-named field does not provide
	 * type safety.
	 *
	 * @param <E> the type of the range.
	 *
	 * @return the immutable empty range.
	 */
	public static <E extends Comparable<? super E>> Range<E> emptyRange()
	{
		return (Range<E>) EMPTY_RANGE;
	}

	private static final class EmptyInterval
		extends AbstractRange implements Interval
	{
		EmptyInterval()
		{
			/* ... */
		}

		@Override
		public Bound lowerBound()
		{
			throw new NoSuchBoundException();
		}

		@Override
		public Bound upperBound()
		{
			throw new NoSuchBoundException();
		}

		@Override
		public Interval intersection(Interval interval)
		{
			Parameters.checkNotNull(interval);
			return this;
		}

		@Override
		public Interval gap(Interval interval)
		{
			Parameters.checkNotNull(interval);
			throw new IllegalStateException();
		}

		@Override
		public Interval expandTo(Comparable value)
		{
			Bound bound = Bound.closed(value);
			return SimpleInterval.from(bound).to(bound);
		}

		@Override
		public Interval closure()
		{
			return this;
		}

		@Override
		public Interval interior()
		{
			return this;
		}

		@Override
		public boolean isConsecutiveTo(Interval interval)
		{
			Parameters.checkNotNull(interval);
			return false;
		}

		@Override
		public boolean isPartitionedBy(Interval... intervals)
		{
			Parameters.checkCondition(intervals.length > 0);
			for (Interval interval : intervals) {
				if (!interval.isEmpty()) {
					return false;
				}
			}
			return true;
		}

		@Override
		public boolean isEmpty()
		{
			return true;
		}

		@Override
		public boolean contains(Comparable value)
		{
			Parameters.checkNotNull(value);
			return false;
		}

		@Override
		public boolean includes(Range range)
		{
			return range.isEmpty();
		}

		@Override
		public boolean intersects(Range range)
		{
			Parameters.checkNotNull(range);
			return false;
		}

		@Override
		public Range intersection(Range range)
		{
			Parameters.checkNotNull(range);
			return this;
		}

		@Override
		public Range union(Range range)
		{
			Parameters.checkNotNull(range);
			return range;
		}

		@Override
		public Range subtraction(Range range)
		{
			Parameters.checkNotNull(range);
			return this;
		}

		@Override
		public List split()
		{
			List intervals = Arrays.asList(this);
			return Collections.unmodifiableList(intervals);
		}

		@Override
		public Sequence sequence(Sequencer sequencer)
		{
			return new LazySequence(
				Collections.EMPTY_LIST, sequencer);
		}
	}

	private Ranges()
	{
		/* ... */
	}
}

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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Default implementation of the {@code Interval} interface.
 *
 * @param <E> the type of the elements in the interval.
 *
 * @author Osman KOCAK
 */
public final class SimpleInterval<E extends Comparable<? super E>>
	extends AbstractRange<E> implements Interval<E>
{
	/**
	 * Initiates the creation of a new interval.
	 *
	 * @param <E> the type of the element in the interval.
	 * @param lower the interval's lower bound.
	 *
	 * @return a builder to finalize the interval's creation.
	 *
	 * @throws NullPointerException if the given bound is {@code null}.
	 */
	public static <E extends Comparable<? super E>>
		Builder<E> from(Bound<E> lower)
	{
		Parameters.checkNotNull(lower);
		return new Builder<E>(lower);
	}

	/**
	 * An interval builder.
	 *
	 * @param <E> the type of the elements in the interval to build.
	 */
	public static final class Builder<E extends Comparable<? super E>>
	{
		private final Bound<E> lower;

		private Builder(Bound<E> lower)
		{
			this.lower = lower;
		}

		/**
		 * Finalizes the creation of the interval. Note that if the
		 * upper bound's value is not greater than the lower bound's
		 * one, or, if the bounds' values are equal and at least one of
		 * them is opened, the returned interval will be empty.
		 *
		 * @param upper the interval's upper bound.
		 *
		 * @return the built interval.
		 *
		 * @throws NullPointerException if the bound is {@code null}.
		 */
		public Interval<E> to(Bound<E> upper)
		{
			Segment<E> segment = new Segment<E>(lower, upper);
			return new SimpleInterval<E>(segment);
		}
	}

	private final Segment<E> segment;
	private final Range<E> range;

	private SimpleInterval(Segment<E> segment)
	{
		this.segment = segment;
		this.range = new SimpleRange<E>(Arrays.asList(this.segment));
	}

	@Override
	public Bound<E> lowerBound()
	{
		if (isEmpty()) {
			throw new NoSuchBoundException();
		}
		return segment.lowerBound();
	}

	@Override
	public Bound<E> upperBound()
	{
		if (isEmpty()) {
			throw new NoSuchBoundException();
		}
		return segment.upperBound();
	}

	@Override
	public Interval<E> intersection(Interval<? extends E> interval)
	{
		Parameters.checkNotNull(interval);
		if (isEmpty() || interval.isEmpty()) {
			return (Interval<E>) Ranges.EMPTY_RANGE;
		}
		Segment<E> s = Segments.toSegment(interval);
		Segment<E> intersection = segment.intersection(s);
		if (intersection == null) {
			return (Interval<E>) Ranges.EMPTY_RANGE;
		}
		return new SimpleInterval(intersection);
	}

	@Override
	public Interval<E> gap(Interval<? extends E> interval)
	{
		Segment<E> gap = segment.gap(Segments.toSegment(interval));
		if (gap == null) {
			return (Interval<E>) Ranges.EMPTY_RANGE;
		}
		return new SimpleInterval<E>(gap);
	}

	@Override
	public Interval<E> expandTo(E value)
	{
		return new SimpleInterval<E>(segment.expandTo(value));
	}

	@Override
	public Interval<E> closure()
	{
		return new SimpleInterval<E>(segment.closure());
	}

	@Override
	public Interval<E> interior()
	{
		return new SimpleInterval<E>(segment.interior());
	}

	@Override
	public boolean isConsecutiveTo(Interval<? extends E> interval)
	{
		if (interval.isEmpty()) {
			return false;
		}
		return segment.isConsecutiveTo(Segments.toSegment(interval));
	}

	@Override
	public boolean isPartitionedBy(Interval<? extends E>... intervals)
	{
		Parameters.checkCondition(intervals.length > 0);
		List<Segment<E>> segments = new ArrayList<Segment<E>>();
		for (Interval<? extends E> interval : intervals) {
			if (!interval.isEmpty()) {
				segments.add(Segments.toSegment(interval));
			} else if (!isEmpty()) {
				return false;
			}
		}
		return segment.isPartitionedBy(segments);
	}

	@Override
	public boolean isEmpty()
	{
		return segment.isEmpty();
	}

	@Override
	public boolean contains(E value)
	{
		return segment.contains(value);
	}

	@Override
	public boolean includes(Range<? extends E> range)
	{
		return this.range.includes(range);
	}

	@Override
	public boolean intersects(Range<? extends E> range)
	{
		return this.range.intersects(range);
	}

	@Override
	public Range<E> intersection(Range<? extends E> range)
	{
		return this.range.intersection(range);
	}

	@Override
	public Range<E> union(Range<? extends E> range)
	{
		return this.range.union(range);
	}

	@Override
	public Range<E> subtraction(Range<? extends E> range)
	{
		return this.range.subtraction(range);
	}

	@Override
	public List<Interval<E>> split()
	{
		List<Interval<E>> intervals = Arrays.asList((Interval<E>) this);
		return Collections.unmodifiableList(intervals);
	}

	@Override
	public Sequence<E> sequence(Sequencer<E> sequencer)
	{
		return this.range.sequence(sequencer);
	}
}

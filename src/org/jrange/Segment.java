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

import static org.jrange.Segments.areConsecutive;
import static org.jrange.Segments.canonicalize;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * This class represents a continuous segment.
 *
 * @param <E> the type of the elements in this segment.
 *
 * @author Osman KOCAK
 */
final class Segment<E extends Comparable<? super E>>
	implements Comparable<Segment<E>>
{
	private final LowerBound<E> lower;
	private final UpperBound<E> upper;

	/**
	 * Creates a new {@code Segment}.
	 *
	 * @param lower the lower bound.
	 * @param upper the upper bound.
	 *
	 * @throws NullPointerException if one of the bounds is {@code null}.
	 */
	Segment(Bound<? extends E> lower, Bound<? extends E> upper)
	{
		this.lower = new LowerBound<E>(lower);
		this.upper = new UpperBound<E>(upper);
	}

	private Segment(LowerBound<E> lower, UpperBound<E> upper)
	{
		this.lower = lower;
		this.upper = upper;
	}

	/**
	 * Returns the lower bound of this segment.
	 *
	 * @return this segment's lower bound.
	 */
	public Bound<E> lowerBound()
	{
		return lower.bound;
	}

	/**
	 * Returns the upper bound of this segment.
	 *
	 * @return this segment's upper bound.
	 */
	public Bound<E> upperBound()
	{
		return upper.bound;
	}

	/**
	 * Returns whether this segment contains the given value.
	 *
	 * @param value the value to test.
	 *
	 * @return whether this segment contains the given value.
	 *
	 * @throws NullPointerException if {@code value} is {@code null}.
	 */
	public boolean contains(E value)
	{
		Parameters.checkNotNull(value);
		return lower.compareTo(value) <= 0
			&& upper.compareTo(value) >= 0;
	}

	/**
	 * Returns whether this segment contains the given one.
	 *
	 * @param segment the segment to test.
	 *
	 * @return whether this segment contains the given one.
	 *
	 * @throws NullPointerException if {@code segment} is {@code null}.
	 */
	public boolean includes(Segment<E> segment)
	{
		if (isEmpty()) {
			return segment.isEmpty();
		}
		return (contains(segment.lower.bound.value())
			|| lower.equals(segment.lower))
			&& (contains(segment.upper.bound.value())
			|| upper.equals(segment.upper));
	}

	/**
	 * Returns whether this segment intersects the given one.
	 *
	 * @param segment the segment to test.
	 *
	 * @return whether this segment intersects the given one.
	 *
	 * @throws NullPointerException if {@code segment} is {@code null}.
	 */
	public boolean intersects(Segment<E> segment)
	{
		Parameters.checkNotNull(segment);
		if (isEmpty() || segment.isEmpty()) {
			return false;
		}
		if (includes(segment) || segment.includes(this)) {
			return true;
		}
		if (compareTo(segment) < 0) {
			return contains(segment.lower.bound.value())
				&& segment.contains(upper.bound.value());
		}
		return contains(segment.upper.bound.value())
			&& segment.contains(lower.bound.value());
	}

	/**
	 * Returns whether this segment is consecutive to the given one.
	 *
	 * @param segment the segment to test.
	 *
	 * @return whether this segment is consecutive to the given one.
	 *
	 * @throws NullPointerException if {@code segment} is {@code null}.
	 */
	public boolean isConsecutiveTo(Segment<E> segment)
	{
		Parameters.checkNotNull(segment);
		if (isEmpty() || segment.isEmpty() || intersects(segment)) {
			return false;
		}
		if (segment.upper.bound.isOpened() && lower.bound.isOpened()) {
			return false;
		}
		return lower.bound.value()
			.compareTo(segment.upper.bound.value()) == 0;
	}

	/**
	 * Returns whether this segment is empty.
	 *
	 * @return whether this segment is empty.
	 */
	public boolean isEmpty()
	{
		int cmp = upper.bound.value().compareTo(lower.bound.value());
		if (cmp == 0) {
			return lower.bound.isOpened() || upper.bound.isOpened();
		}
		return cmp < 0;
	}

	/**
	 * Returns the intersection of this segment with the given one, returns
	 * {@code null} if such an intersection doesn't exist.
	 *
	 * @param segment the segment to intersect.
	 *
	 * @return the intersection of this segment with the given one.
	 *
	 * @throws NullPointerException if {@code segment} is {@code null}.
	 */
	public Segment<E> intersection(Segment<E> segment)
	{
		if (!intersects(segment)) {
			return null;
		}
		if (includes(segment)) {
			return segment;
		}
		if (segment.includes(this)) {
			return this;
		}
		if (compareTo(segment) > 0) {
			return new Segment<E>(lower, segment.upper);
		}
		return new Segment<E>(segment.lower, upper);
	}

	/**
	 * Returns the union of this segment with the given one.
	 *
	 * @param segment the segment to add.
	 *
	 * @return the union of this segment with the given one.
	 *
	 * @throws NullPointerException if {@code segment} is {@code null}.
	 */
	public List<Segment<E>> union(Segment<E> segment)
	{
		if (!intersects(segment) && !areConsecutive(this, segment)) {
			if (compareTo(segment) > 0) {
				return Arrays.asList(segment, this);
			}
			return Arrays.asList(this, segment);
		}
		Segment<E> union;
		if (includes(segment)) {
			union = this;
		} else if (segment.includes(this)) {
			union = segment;
		} else if (compareTo(segment) > 0) {
			union = new Segment<E>(segment.lower, upper);
		} else {
			union = new Segment<E>(lower, segment.upper);
		}
		return Arrays.asList(union);
	}

	/**
	 * Subtracts the given segment from this segment.
	 *
	 * @param segment the segment to subtract from this one.
	 *
	 * @return the subtraction of the given segment from this segment.
	 *
	 * @throws NullPointerException if {@code segment} is {@code null}.
	 */
	public List<Segment<E>> subtraction(Segment<E> segment)
	{
		if (segment.includes(this)) {
			return Collections.emptyList();
		}
		List<Segment<E>> segments = new ArrayList<Segment<E>>();
		E lowerValue = segment.lower.bound.value();
		E upperValue = segment.upper.bound.value();
		if (!intersects(segment)) {
			segments.add(this);
		} else if (includes(segment)) {
			Bound<E> upperBound;
			if (segment.lower.bound.isClosed()) {
				upperBound = Bound.opened(lowerValue);
			} else {
				upperBound = Bound.closed(lowerValue);
			}
			segments.add(new Segment<E>(lower.bound, upperBound));
			Bound<E> lowerBound;
			if (segment.upper.bound.isClosed()) {
				lowerBound = Bound.opened(upperValue);
			} else {
				lowerBound = Bound.closed(upperValue);
			}
			segments.add(new Segment<E>(lowerBound, upper.bound));
		} else if (compareTo(segment) > 0) {
			Bound<E> lowerBound;
			if (segment.upper.bound.isClosed()) {
				lowerBound = Bound.opened(upperValue);
			} else {
				lowerBound = Bound.closed(upperValue);
			}
			segments.add(new Segment<E>(lowerBound, upper.bound));
		} else {
			Bound<E> upperBound;
			if (segment.lower.bound.isClosed()) {
				upperBound = Bound.opened(lowerValue);
			} else {
				upperBound = Bound.closed(lowerValue);
			}
			segments.add(new Segment<E>(lower.bound, upperBound));
		}
		return canonicalize(segments);
	}

	/**
	 * Returns the smallest segment that "fills" the gap between this
	 * segment and the given one. Returns {@code null} if such a gap does
	 * not exist.
	 *
	 * @param segment the segment.
	 *
	 * @return the gap between this segment and the given one.
	 *
	 * @throws NullPointerException if {@code segment} is {@code null}.
	 * @throws IllegalStateException if this segment is empty.
	 * @throws IllegalArgumentException if the given segment is empty.
	 */
	public Segment<E> gap(Segment<E> segment)
	{
		Parameters.checkCondition(!segment.isEmpty());
		if (isEmpty()) {
			throw new IllegalStateException();
		}
		if (intersects(segment)) {
			return null;
		}
		if (compareTo(segment) > 0) {
			Bound<E> lowerBound;
			E upperValue = segment.upper.bound.value();
			if (segment.upper.bound.isClosed()) {
				lowerBound = Bound.opened(upperValue);
			} else {
				lowerBound = Bound.closed(upperValue);
			}
			Bound<E> upperBound;
			if (lower.bound.isClosed()) {
				upperBound = Bound.opened(lower.bound.value());
			} else {
				upperBound = Bound.closed(lower.bound.value());
			}
			return new Segment<E>(lowerBound, upperBound);
		}
		Bound<E> lowerBound;
		if (upper.bound.isClosed()) {
			lowerBound = Bound.opened(upper.bound.value());
		} else {
			lowerBound = Bound.closed(upper.bound.value());
		}
		Bound<E> upperBound;
		if (segment.lower.bound.isClosed()) {
			upperBound = Bound.opened(segment.lower.bound.value());
		} else {
			upperBound = Bound.closed(segment.lower.bound.value());
		}
		return new Segment<E>(lowerBound, upperBound);
	}

	/**
	 * Returns the smallest segment that contains both this segment and the
	 * given value.
	 *
	 * @param value the value to expand this segment to.
	 *
	 * @return the expanded segment.
	 *
	 * @throws NullPointerException if {@code value} is {@code null}.
	 */
	public Segment<E> expandTo(E value)
	{
		if (isEmpty()) {
			Bound<E> bound = Bound.closed(value);
			return new Segment(bound, bound);
		}
		if (contains(value)) {
			return this;
		}
		if (lower.compareTo(value) > 0) {
			return new Segment<E>(Bound.closed(value), upper.bound);
		}
		return new Segment<E>(lower.bound, Bound.closed(value));
	}

	/**
	 * Returns the closure of this segment (the smallest closed segment that
	 * contains this one).
	 *
	 * @return the closure of this segment.
	 */
	public Segment<E> closure()
	{
		if (isEmpty()) {
			return this;
		}
		if (lower.bound.isClosed() && upper.bound.isClosed()) {
			return this;
		}
		Bound<E> lowerBound = Bound.closed(lower.bound.value());
		Bound<E> upperBound = Bound.closed(upper.bound.value());
		return new Segment<E>(lowerBound, upperBound);
	}

	/**
	 * Returns the interior of this segment (the largest open segment that
	 * is contained in this one).
	 *
	 * @return the interior of this segment.
	 */
	public Segment<E> interior()
	{
		if (isEmpty()) {
			return this;
		}
		if (lower.bound.isOpened() && upper.bound.isOpened()) {
			return this;
		}
		Bound<E> lowerBound = Bound.opened(lower.bound.value());
		Bound<E> upperBound = Bound.opened(upper.bound.value());
		return new Segment<E>(lowerBound, upperBound);
	}

	/**
	 * Returns whether the given segments completely partitions this one.
	 *
	 * @param segments the segments
	 *
	 * @return whether the given segments completely partitions this one.
	 *
	 * @throws NullPointerException if {@code segments} is {@code null}.
	 */
	public boolean isPartitionedBy(List<Segment<E>> segments)
	{
		if (segments.size() == 1) {
			return equals(segments.get(0));
		}
		if (isEmpty()) {
			for (Segment<E> segment : segments) {
				if (!segment.isEmpty()) {
					return false;
				}
			}
			return true;
		}
		if (segments.isEmpty() || !areConsecutive(segments)) {
			return false;
		}
		List<Segment<E>> union = canonicalize(segments);
		return union.size() != 1 ? false : equals(union.get(0));
	}

	@Override
	public int compareTo(Segment<E> segment)
	{
		if (isEmpty()) {
			if (segment.isEmpty()) {
				return 0;
			}
			return -1;
		} else if (segment.isEmpty()) {
			return 1;
		}
		if (lower.equals(segment.lower)) {
			if (upper.equals(segment.upper)) {
				return 0;
			}
			return upper.compareTo(segment.upper.bound.value());
		}
		return lower.compareTo(segment.lower.bound.value());
	}

	@Override
	public String toString()
	{
		return lower + ", " + upper;
	}

	@Override
	public boolean equals(Object o)
	{
		if (o == this) {
			return true;
		}
		if (!(o instanceof Segment)) {
			return false;
		}
		final Segment<E> s = (Segment<E>) o;
		if (isEmpty()) {
			return s.isEmpty();
		}
		return lower.equals(s.lower) && upper.equals(s.upper);
	}

	@Override
	public int hashCode()
	{
		if (isEmpty()) {
			return 7;
		}
		int hash = 7;
		hash = 59 * hash + upper.hashCode();
		hash = 59 * hash + lower.hashCode();
		return hash;
	}

	private static final class LowerBound<E extends Comparable<? super E>>
		implements Comparable<E>
	{
		private final Bound<E> bound;

		LowerBound(Bound<? extends E> bound)
		{
			if (bound.isClosed()) {
				this.bound = Bound.closed(bound.value());
			} else {
				this.bound = Bound.opened(bound.value());
			}
		}

		@Override
		public int compareTo(E e)
		{
			int cmp = bound.value().compareTo(e);
			if (cmp == 0 && bound.isOpened()) {
				return 1;
			}
			return cmp;
		}

		@Override
		public String toString()
		{
			if (bound.isClosed()) {
				return "[" + bound.value();
			}
			return "]" + bound.value();
		}

		@Override
		public boolean equals(Object o)
		{
			if (o == this) {
				return true;
			}
			if (!(o instanceof LowerBound)) {
				return false;
			}
			final LowerBound<E> b = (LowerBound<E>) o;
			return bound.equals(b.bound);
		}

		@Override
		public int hashCode()
		{
			return bound.hashCode();
		}
	}

	private static final class UpperBound<E extends Comparable<? super E>>
		implements Comparable<E>
	{
		private final Bound<E> bound;

		UpperBound(Bound<? extends E> bound)
		{
			if (bound.isClosed()) {
				this.bound = Bound.closed(bound.value());
			} else {
				this.bound = Bound.opened(bound.value());
			}
		}

		@Override
		public int compareTo(E e)
		{
			int cmp = bound.value().compareTo(e);
			if (cmp == 0 && bound.isOpened()) {
				return -1;
			}
			return cmp;
		}

		@Override
		public String toString()
		{
			if (bound.isClosed()) {
				return bound.value() + "]";
			}
			return bound.value() + "[";
		}

		@Override
		public boolean equals(Object o)
		{
			if (o == this) {
				return true;
			}
			if (!(o instanceof UpperBound)) {
				return false;
			}
			final UpperBound<E> b = (UpperBound<E>) o;
			return bound.equals(b.bound);
		}

		@Override
		public int hashCode()
		{
			return bound.hashCode();
		}
	}
}

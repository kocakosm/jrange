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

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Default implementation of the {@code Range} interface.
 *
 * @param <E> the type of the elements in this range.
 *
 * @author Osman KOCAK
 */
final class SimpleRange<E extends Comparable<? super E>>
	extends AbstractRange<E>
{
	private final List<Segment<E>> segments;

	/**
	 * Creates a new {@code SimpleRange} from the given {@code Segment}s.
	 *
	 * @param segments the segments constituing the range.
	 */
	SimpleRange(List<Segment<E>> segments)
	{
		this.segments = Segments.canonicalize(segments);
	}

	@Override
	public boolean isEmpty()
	{
		return segments.isEmpty();
	}

	@Override
	public boolean contains(E value)
	{
		Parameters.checkNotNull(value);
		for (Segment<E> i : segments) {
			if (i.contains(value)) {
				return true;
			}
		}
		return false;
	}

	@Override
	public boolean includes(Range<? extends E> range)
	{
		if (isEmpty()) {
			return range.isEmpty();
		}
		for (Segment<E> i : Segments.split(range)) {
			boolean contains = false;
			for (Segment<E> j : segments) {
				if (j.includes(i)) {
					contains = true;
					break;
				}
			}
			if (!contains) {
				return false;
			}
		}
		return true;
	}

	@Override
	public boolean intersects(Range<? extends E> range)
	{
		Parameters.checkNotNull(range);
		for (Segment<E> i : segments) {
			for (Segment<E> j : Segments.split(range)) {
				if (i.intersects(j)) {
					return true;
				}
			}
		}
		return false;
	}

	@Override
	public Range<E> intersection(Range<? extends E> range)
	{
		Parameters.checkNotNull(range);
		List<Segment<E>> res = new ArrayList<Segment<E>>();
		for (Segment<E> i : segments) {
			for (Segment<E> j : Segments.split(range)) {
				Segment<E> segment = i.intersection(j);
				if (segment != null) {
					res.add(segment);
				}
			}
		}
		return new SimpleRange<E>(res);
	}

	@Override
	public Range<E> union(Range<? extends E> range)
	{
		List<Segment<E>> res = new ArrayList<Segment<E>>(segments);
		res.addAll(Segments.split(range));
		return new SimpleRange<E>(res);
	}

	@Override
	public Range<E> subtraction(Range<? extends E> range)
	{
		if (range.isEmpty()) {
			return this;
		}
		List<Segment<E>> res = new ArrayList<Segment<E>>(segments);
		for (Segment<E> i : segments) {
			for (Segment<E> j : Segments.split(range)) {
				if (i.intersects(j)) {
					res.remove(i);
					res.addAll(i.subtraction(j));
					Range<E> r = new SimpleRange<E>(res);
					return r.subtraction(range);
				}
			}
		}
		return new SimpleRange<E>(res);
	}

	@Override
	public List<Interval<E>> split()
	{
		List<Interval<E>> intervals = new ArrayList<Interval<E>>();
		if (isEmpty()) {
			intervals.add((Interval<E>) Ranges.EMPTY_RANGE);
		} else {
			for (Segment<E> segment : segments) {
				Bound<E> lower = segment.lowerBound();
				Bound<E> upper = segment.upperBound();
				intervals.add(from(lower).to(upper));
			}
		}
		return Collections.unmodifiableList(intervals);
	}

	@Override
	public Sequence<E> sequence(Sequencer<E> sequencer)
	{
		return new LazySequence<E>(segments, sequencer);
	}
}

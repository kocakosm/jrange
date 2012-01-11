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
import java.util.Iterator;
import java.util.List;

/**
 * Utility class containing only static methods that operate on or return
 * {@code Segment}s.
 *
 * @author Osman KOCAK
 */
final class Segments
{
	/**
	 * Canonicalizes the given segments. Canonicalization consists in
	 * deleting empty segments (if any), computing the union of all the
	 * remaining segments and, finally, sorting them.
	 *
	 * @param <E> the type of the elements in the handled segments.
	 * @param segments the segments to canonicalize.
	 *
	 * @return the canonicalized segments.
	 *
	 * @throws NullPointerException if {@code segments} is {@code null}.
	 */
	static <E extends Comparable<? super E>>
		Segment<E>[] canonicalize(Segment<E>... segments)
	{
		List<Segment<E>> res = canonicalize(Arrays.asList(segments));
		return res.toArray(new Segment[res.size()]);
	}

	/**
	 * Canonicalizes the given segments. Canonicalization consists in
	 * deleting empty segments (if any), computing the union of all the
	 * remaining segments and, finally, sorting them.
	 *
	 * @param <E> the type of the elements in the handled segments.
	 * @param segments the segments to canonicalize.
	 *
	 * @return the canonicalized segments as an unmodifiable {@code List}.
	 *
	 * @throws NullPointerException if {@code segments} is {@code null}.
	 */
	static <E extends Comparable<? super E>>
		List<Segment<E>> canonicalize(List<Segment<E>> segments)
	{
		List<Segment<E>> tmp = new ArrayList<Segment<E>>(segments);
		Iterator<Segment<E>> i = tmp.iterator();
		while (i.hasNext()) {
			if (i.next().isEmpty()) {
				i.remove();
			}
		}
		int n = tmp.size();
		if (n < 2) {
			return tmp;
		}
		if (n == 2) {
			return tmp.get(0).union(tmp.get(1));
		}
		Collections.sort(tmp);
		List<Segment<E>> head = canonicalize(tmp.subList(0, 2));
		List<Segment<E>> tail = canonicalize(tmp.subList(2, n));
		List<Segment<E>> fusion = new ArrayList<Segment<E>>();
		if (head.size() > 1) {
			fusion.add(head.get(0));
			fusion.addAll(head.get(1).union(tail.get(0)));
		} else {
			fusion.addAll(head.get(0).union(tail.get(0)));
		}
		if (tail.size() > 1) {
			fusion.addAll(tail.subList(1, tail.size()));
		}
		return Collections.unmodifiableList(fusion);
	}

	/**
	 * Returns whether the given segments are (strictly) consecutive.
	 *
	 * @param <E> the type of the elements in the given segments.
	 * @param segments the segments to test for consecutiveness.
	 *
	 * @return whether the given segments are (strictly) consecutive.
	 *
	 * @throws NullPointerException if {@code segments} is {@code null}.
	 * @throws IllegalArgumentException if less than 2 segments are given.
	 */
	static <E extends Comparable<? super E>>
		boolean areConsecutive(Segment<E>... segments)
	{
		return areConsecutive(Arrays.asList(segments));
	}

	/**
	 * Returns whether the given segments are (strictly) consecutive.
	 *
	 * @param <E> the type of the elements in the given segments.
	 * @param segments the segments to test for consecutiveness.
	 *
	 * @return whether the given segments are (strictly) consecutive.
	 *
	 * @throws NullPointerException if {@code segments} is {@code null}.
	 * @throws IllegalArgumentException if less than 2 segments are given.
	 */
	static <E extends Comparable<? super E>>
		boolean areConsecutive(List<Segment<E>> segments)
	{
		Parameters.checkCondition(segments.size() > 1);
		Collections.sort(segments);
		for (int i = segments.size() - 1; i > 0; i--) {
			Segment<E> next = segments.get(i);
			Segment<E> current = segments.get(i - 1);
			if (!next.isConsecutiveTo(current)) {
				return false;
			}
		}
		return true;
	}

	/**
	 * Splits the given range into segments.
	 *
	 * @param <E> the type of the elements in the given range.
	 * @param range the range to split.
	 *
	 * @return the range's segments.
	 *
	 * @throws NullPointerException if {@code range} is {@code null}.
	 */
	static <E extends Comparable<? super E>>
		List<Segment<E>> split(Range<? extends E> range)
	{
		List<Segment<E>> segments = new ArrayList<Segment<E>>();
		for (Interval<? extends E> interval : range.split()) {
			if (interval.isEmpty()) {
				continue;
			}
			segments.add(toSegment(interval));
		}
		return canonicalize(segments);
	}

	/**
	 * Converts the given {@code Interval} into a {@code Segment}.
	 *
	 * @param <E> the type of the elements in the given interval.
	 * @param interval the interval to convert.
	 *
	 * @return the converted segment.
	 *
	 * @throws NullPointerException if the given interval is {@code null}.
	 * @throws IllegalArgumentException if the given interval is empty.
	 */
	static <E extends Comparable<? super E>>
		Segment<E> toSegment(Interval<? extends E> interval)
	{
		Parameters.checkCondition(!interval.isEmpty());
		Bound<? extends E> lower = interval.lowerBound();
		Bound<? extends E> upper = interval.upperBound();
		return new Segment<E>(lower, upper);
	}

	private Segments()
	{
		/* ... */
	}
}

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

import java.util.List;

/**
 * This interface represents a range. A range is a union of intervals.
 *
 * @param <E> the type of the elements in this range.
 *
 * @author Osman KOCAK
 */
public interface Range<E extends Comparable<? super E>>
{
	/**
	 * Returns whether this range is empty (an empty range only contains
	 * itself).
	 *
	 * @return whether this range is empty.
	 */
	boolean isEmpty();

	/**
	 * Returns whether this range contains the given value.
	 *
	 * @param value the value to test.
	 *
	 * @return whether this range contains the given value.
	 *
	 * @throws NullPointerException if the given value is {@code null}.
	 */
	boolean contains(E value);

	/**
	 * Returns whether this range includes the given one.
	 *
	 * @param range the range to test.
	 *
	 * @return whether this range includes the given range.
	 *
	 * @throws NullPointerException if the given range is {@code null}.
	 */
	boolean includes(Range<? extends E> range);

	/**
	 * Returns whether this range intersects the given range.
	 *
	 * @param range the range to test for intesection.
	 *
	 * @return whether this range intersects the given one.
	 *
	 * @throws NullPointerException if the given range is {@code null}.
	 */
	boolean intersects(Range<? extends E> range);

	/**
	 * Returns the intersection of this range and the given one.
	 *
	 * @param range the range to compute the intersection with this one.
	 *
	 * @return the intersection of this range and the given one.
	 *
	 * @throws NullPointerException if the given range is {@code null}.
	 */
	Range<E> intersection(Range<? extends E> range);

	/**
	 * Returns the union of this range and the given one.
	 *
	 * @param range the range to compute the union with this one.
	 *
	 * @return the union of this range with the given one.
	 *
	 * @throws NullPointerException if the given range is {@code null}.
	 */
	Range<E> union(Range<? extends E> range);

	/**
	 * Subtracts the given range from this one.
	 *
	 * @param range the range to subtract.
	 *
	 * @return the subtraction of the given range from this one.
	 *
	 * @throws NullPointerException if the given range is {@code null}.
	 */
	Range<E> subtraction(Range<? extends E> range);

	/**
	 * Splits this range into intervals.
	 *
	 * @return the intervals constituing this range.
	 */
	List<Interval<E>> split();

	/**
	 * Sequences this range.
	 *
	 * @param sequencer the sequencer to sequence this range with.
	 *
	 * @return the extracted sequence.
	 *
	 * @throws NullPointerException if the given sequencer is {@code null}.
	 */
	Sequence<E> sequence(Sequencer<E> sequencer);
}

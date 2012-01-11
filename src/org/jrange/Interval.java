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

/**
 * An {@code Interval} represents a continuous part of a range, it is more or
 * less a mathematical interval.
 *
 * @param <E> the type of the elements in the interval.
 *
 * @author Osman KOCAK
 */
public interface Interval<E extends Comparable<? super E>> extends Range<E>
{
	/**
	 * Returns the lower bound of this interval.
	 *
	 * @return the lower bound of this interval.
	 *
	 * @throws NoSuchBoundException if this interval is empty.
	 */
	Bound<E> lowerBound();

	/**
	 * Returns the upper bound of this interval.
	 *
	 * @return the upper bound of this interval.
	 *
	 * @throws NoSuchBoundException if this interval is empty.
	 */
	Bound<E> upperBound();

	/**
	 * Returns the intersection of this interval with the given one.
	 *
	 * @param interval the interval to intersect with this one.
	 *
	 * @return the intersection of this interval with the given one.
	 *
	 * @throws NullPointerException if the given interval is {@code null}.
	 */
	Interval<E> intersection(Interval<? extends E> interval);

	/**
	 * Returns the smallest interval that "fills" the gap between this
	 * interval and the given one.
	 *
	 * @param interval the interval.
	 *
	 * @return the gap between this interval and the given one.
	 *
	 * @throws NullPointerException if the given interval is {@code null}.
	 * @throws IllegalArgumentException if the given interval is empty.
	 * @throws IllegalStateException if this interval is empty.
	 */
	Interval<E> gap(Interval<? extends E> interval);

	/**
	 * Returns the smallest interval that includes this interval and 
	 * contains the given value.
	 *
	 * @param value the value to expand this interval to.
	 *
	 * @return the expanded interval.
	 *
	 * @throws NullPointerException if {@code value} is {@code null}.
	 */
	Interval<E> expandTo(E value);

	/**
	 * Returns the closure of this interval (the smallest closed interval
	 * that includes this one).
	 *
	 * @return the closure of this interval.
	 */
	Interval<E> closure();

	/**
	 * Returns the interior of this interval (the largest open interval that
	 * is included in this one).
	 *
	 * @return the interior of this interval.
	 */
	Interval<E> interior();

	/**
	 * Returns whether this interval is strictly consecutive to the given
	 * one.
	 *
	 * @param interval the interval to test for consecutiveness.
	 *
	 * @return whether this interval is consecutive to the given one.
	 *
	 * @throws NullPointerException if {@code interval} is {@code null}.
	 */
	boolean isConsecutiveTo(Interval<? extends E> interval);

	/**
	 * Returns whether the given intervals completely partitions this one.
	 *
	 * @param intervals the intervals.
	 *
	 * @return whether the given intervals completely partitions this one.
	 *
	 * @throws NullPointerException if {@code intervals} is {@code null}.
	 * @throws IllegalArgumentException if {@code intervals} is empty.
	 */
	boolean isPartitionedBy(Interval<? extends E>... intervals);
}

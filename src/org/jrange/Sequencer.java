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
 * A sequencer, used to extract a sequence from a range.
 *
 * @param <E> the type of the sequenced elements.
 *
 * @author Osman KOCAK
 */
public interface Sequencer<E extends Comparable<? super E>>
{
	/**
	 * Returns the value that comes next to the given one. More formally,
	 * this method returns a value such that it is strictly greater than
	 * the given one. Also, this method is deterministic, i.e from one call
	 * to another it returns the same value for any particular value.
	 *
	 * @param current the current value.
	 *
	 * @return the value that comes next to the given one.
	 *
	 * @throws NullPointerException if the given value is {@code null}.
	 */
	E next(E current);
}

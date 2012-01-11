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
 * This basic skeletal implementation of the {@code Range} interface provides
 * only implementations for the {@code toString()}, {@code equals(Object)} and
 * {@code hashCode()} methods. In order to provide your custom implementation of
 * the {@code Range} interface, it is more than highly recommended that you
 * extend this abstract class and do not override its methods.
 *
 * @param <E> the type of the elements in this range.
 *
 * @author Osman KOCAK
 */
public abstract class AbstractRange<E extends Comparable<? super E>>
	implements Range<E>
{
	private static final String EMPTY_SET_CHARACTER;
	static {
		EMPTY_SET_CHARACTER = Character.toString((char) 8709);
	}

	@Override
	public String toString()
	{
		if (isEmpty()) {
			return EMPTY_SET_CHARACTER;
		}
		List<Segment<E>> segments = Segments.split(this);
		if (segments.size() == 1) {
			return segments.get(0).toString();
		}
		StringBuilder sb = new StringBuilder();
		sb.append(segments.get(0));
		for (int i = 1; i < segments.size(); i++) {
			sb.append(" U ").append(segments.get(i));
		}
		return sb.toString();
	}

	@Override
	public boolean equals(Object o)
	{
		if (o == this) {
			return true;
		}
		if (!(o instanceof Range)) {
			return false;
		}
		final Range<E> range = (Range<E>) o;
		return Segments.split(this).equals(Segments.split(range));
	}

	@Override
	public int hashCode()
	{
		int hash = 7;
		for (Segment<E> segment : Segments.split(this)) {
			hash = 67 * hash + segment.hashCode();
		}
		return hash;
	}
}

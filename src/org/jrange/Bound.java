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
 * Instances of this class are used to represent the bounds of an interval. A
 * bound is defined by a value and whether this value is included or not in the
 * interval in consideration.
 *
 * @param <E> the value type of this bound.
 *
 * @author Osman KOCAK
 */
public final class Bound<E extends Comparable<? super E>>
{
	/**
	 * Creates a new closed {@code Bound}.
	 *
	 * @param <E> the type of the bound's value.
	 * @param value	the bound's value.
	 *
	 * @return the newly created bound.
	 *
	 * @throws NullPointerException if the given value is {@code null}.
	 */
	public static <E extends Comparable<? super E>> Bound<E> closed(E value)
	{
		Parameters.checkNotNull(value);
		return new Bound<E>(value, true);
	}

	/**
	 * Creates a new opened {@code Bound}.
	 *
	 * @param <E> the type of the bound's value.
	 * @param value	the bound's value.
	 *
	 * @return the newly created bound.
	 *
	 * @throws NullPointerException if the given value is {@code null}.
	 */
	public static <E extends Comparable<? super E>> Bound<E> opened(E value)
	{
		Parameters.checkNotNull(value);
		return new Bound<E>(value, false);
	}

	private final E value;
	private final boolean closed;

	private Bound(E value, boolean closed)
	{
		this.value = value;
		this.closed = closed;
	}

	/**
	 * Returns whether this bound is closed.
	 *
	 * @return whether this bound is closed.
	 */
	public boolean isClosed()
	{
		return closed;
	}

	/**
	 * Returns whether this bound is opened.
	 *
	 * @return whether this bound is opened.
	 */
	public boolean isOpened()
	{
		return !closed;
	}

	/**
	 * Returns the value of this bound.
	 *
	 * @return the value of this bound.
	 */
	public E value()
	{
		return value;
	}

	@Override
	public String toString()
	{
		if (closed) {
			return value + " (included)";
		}
		return value + " (excluded)";
	}

	@Override
	public boolean equals(Object o)
	{
		if (o == this) {
			return true;
		}
		if (!(o instanceof Bound)) {
			return false;
		}
		final Bound<E> b = (Bound<E>) o;
		return value.equals(b.value) && closed == b.closed;
	}

	@Override
	public int hashCode()
	{
		int hash = 7;
		hash = 29 * hash + value.hashCode();
		hash = 29 * hash + (closed ? 1 : 0);
		return hash;
	}
}

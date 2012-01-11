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

import java.util.Iterator;

/**
 * This abstract skeletal implementation of the {@code Sequence} interface only
 * provides implementations for the utility methods {@code equals(Object)} and
 * {@code hashCode()}. If you want to provide your custom implementation of the
 * {@code Sequence} interface, it is more than highly recommended that you
 * extend this base class and do not override its methods.
 *
 * @param <E> the type of the elements in this sequence.
 *
 * @author Osman KOCAK
 */
public abstract class AbstractSequence<E extends Comparable<? super E>>
	implements Sequence<E>
{
	@Override
	public boolean equals(Object o)
	{
		if (o == this) {
			return true;
		}
		if (!(o instanceof Sequence)) {
			return false;
		}
		Iterator<E> i = iterator();
		Iterator<E> j = ((Sequence<E>) o).iterator();
		while (i.hasNext() && j.hasNext()) {
			E e1 = i.next();
			E e2 = j.next();
			if (!(e1 == null ? e2 == null : e1.equals(e2))) {
				return false;
			}
		}
		return !(i.hasNext() || j.hasNext());
	}

	@Override
	public int hashCode()
	{
		int hash = 7;
		for (E e : this) {
			hash = 97 * hash + (e == null ? 0 : e.hashCode());
		}
		return hash;
	}
}

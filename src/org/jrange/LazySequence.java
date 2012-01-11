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

import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * {@code Sequence} interface's default implementation. This implementation
 * is "lazy", it computes its elements on demand and doesn't keep a reference
 * on them.
 *
 * @param <E> the type of the elements in this sequence.
 *
 * @author Osman KOCAK
 */
final class LazySequence<E extends Comparable<? super E>>
	extends AbstractSequence<E>
{
	private static final Iterator EMPTY_SEQUENCE_ITERATOR;
	static {
		EMPTY_SEQUENCE_ITERATOR = new EmptySequenceIterator();
	}

	private final List<Segment<E>> segments;
	private final Sequencer<E> sequencer;

	/**
	 * Creates a new {@code LazySequence}.
	 *
	 * @param segments the segments to sequence.
	 * @param sequencer the sequencer to use.
	 *
	 * @throws NullPointerException if {@code segments} is {@code null}.
	 * @throws NullPointerException if {@code sequencer} is {@code null}.
	 */
	LazySequence(List<Segment<E>> segments, Sequencer<E> sequencer)
	{
		Parameters.checkNotNull(sequencer);
		this.segments = Collections.unmodifiableList(segments);
		this.sequencer = sequencer;
	}

	@Override
	public Iterator<E> iterator()
	{
		if (segments.isEmpty()) {
			return (Iterator<E>) EMPTY_SEQUENCE_ITERATOR;
		}
		return new SequenceIterator();
	}

	private final class SequenceIterator implements Iterator<E>
	{
		private Segment<E> segment;
		private E current;
		private int index;

		SequenceIterator()
		{
			segment = segments.get(0);
		}

		@Override
		public boolean hasNext()
		{
			if (index < segments.size() - 1) {
				return true;
			}
			if (current != null) {
				E next = sequencer.next(current);
				return segment.contains(next);
			}
			E next = segment.lowerBound().value();
			return segment.contains(next)
				|| segment.contains(sequencer.next(next));
		}

		@Override
		public E next()
		{
			if (!hasNext()) {
				throw new NoSuchElementException();
			}
			if (current == null) {
				current = segment.lowerBound().value();
				if (!segment.contains(current)) {
					return next();
				}
			} else {
				current = sequencer.next(current);
				if (!segment.contains(current)) {
					segment = segments.get(++index);
					current = null;
					return next();
				}
			}
			return current;
		}

		@Override
		public void remove()
		{
			throw new UnsupportedOperationException();
		}
	}

	private static final class EmptySequenceIterator<E>
		implements Iterator<E>
	{
		EmptySequenceIterator()
		{
			/* ... */
		}

		@Override
		public boolean hasNext()
		{
			return false;
		}

		@Override
		public E next()
		{
			throw new NoSuchElementException("Empty range");
		}

		@Override
		public void remove()
		{
			throw new UnsupportedOperationException();
		}
	}
}

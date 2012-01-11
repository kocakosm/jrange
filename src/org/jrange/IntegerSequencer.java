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
 * A {@code Sequencer} implementation for the {@code Integer} type.
 *
 * @author Osman KOCAK
 */
public final class IntegerSequencer implements Sequencer<Integer>
{
	private final int step;

	/**
	 * Creates a new {@code IntegerSequencer}.
	 *
	 * @param step the step between two successive values.
	 *
	 * @throws IllegalArgumentException if {@code step <= 0}.
	 */
	public IntegerSequencer(int step)
	{
		Parameters.checkCondition(step > 0, "step must be > 0");
		this.step = step;
	}

	@Override
	public Integer next(Integer current)
	{
		return current + step;
	}

	@Override
	public String toString()
	{
		return "IntegerSequencer (step = " + step + ")";
	}

	@Override
	public boolean equals(Object o)
	{
		if (o == this) {
			return true;
		}
		if (!(o instanceof IntegerSequencer)) {
			return false;
		}
		final IntegerSequencer sequencer = (IntegerSequencer) o;
		return step == sequencer.step;
	}

	@Override
	public int hashCode()
	{
		return step;
	}
}

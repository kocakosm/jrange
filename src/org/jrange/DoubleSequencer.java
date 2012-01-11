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

import java.math.BigDecimal;

/**
 * A {@code Sequencer} implementation for the {@code Double} type.
 *
 * @author Osman KOCAK
 */
public final class DoubleSequencer implements Sequencer<Double>
{
	private final BigDecimal step;

	/**
	 * Creates a new {@code DoubleSequencer}.
	 *
	 * @param step the step between two successive values.
	 *
	 * @throws IllegalArgumentException if {@code step <= 0}.
	 */
	public DoubleSequencer(double step)
	{
		Parameters.checkCondition(step > 0, "step must be > 0");
		this.step = BigDecimal.valueOf(step);
	}

	@Override
	public Double next(Double current)
	{
		return BigDecimal.valueOf(current).add(step).doubleValue();
	}

	@Override
	public String toString()
	{
		return "DoubleSequencer (step = " + step + ")";
	}

	@Override
	public boolean equals(Object o)
	{
		if (o == this) {
			return true;
		}
		if (!(o instanceof DoubleSequencer)) {
			return false;
		}
		final DoubleSequencer sequencer = (DoubleSequencer) o;
		return step.equals(sequencer.step);
	}

	@Override
	public int hashCode()
	{
		return step.hashCode();
	}
}

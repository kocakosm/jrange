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

import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * A {@code Sequencer} implementation for the {@code Date} type.
 *
 * @author Osman KOCAK
 */
public final class DateSequencer implements Sequencer<Date>
{
	private final long step;

	/**
	 * Creates a new {@code DateSequencer}.
	 *
	 * @param step the step between two successive dates in milliseconds.
	 *
	 * @throws IllegalArgumentException if {@code step <= 0}.
	 */
	public DateSequencer(long step)
	{
		Parameters.checkCondition(step > 0, "step must be > 0");
		this.step = step;
	}

	/**
	 * Creates a new {@code DateSequencer}.
	 *
	 * @param step the step between two successive dates.
	 * @param unit the unit in which the step is given.
	 *
	 * @throws NullPointerException if {@code unit == null}.
	 * @throws IllegalArgumentException if {@code step <= 0}.
	 */
	public DateSequencer(long step, TimeUnit unit)
	{
		Parameters.checkCondition(step > 0, "step must be > 0");
		this.step = TimeUnit.MILLISECONDS.convert(step, unit);
	}

	@Override
	public Date next(Date current)
	{
		return new Date(current.getTime() + step);
	}

	@Override
	public String toString()
	{
		return "DateSequencer (step = " + step + " ms)";
	}

	@Override
	public boolean equals(Object o)
	{
		if (o == this) {
			return true;
		}
		if (!(o instanceof DateSequencer)) {
			return false;
		}
		final DateSequencer sequencer = (DateSequencer) o;
		return step == sequencer.step;
	}

	@Override
	public int hashCode()
	{
		return Long.valueOf(step).hashCode();
	}
}

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
 * Utility class.
 *
 * @author	Osman KOCAK
 */
final class Utils
{
	/**
	 * Returns a pseudo-random {@code int} between 1 and 1000.
	 *
	 * @return a pseudo-random {@code int} between 1 and 1000.
	 */
	static int randomInt()
	{
		return Double.valueOf(Math.random() * 999).intValue() + 1;
	}

	/**
	 * Returns a pseudo-random {@code long} between 1 and 1000.
	 *
	 * @return a pseudo-random {@code long} between 1 and 1000.
	 */
	static long randomLong()
	{
		return Double.valueOf(Math.random() * 999).longValue() + 1L;
	}

	/**
	 * Returns a pseudo-random {@code double} between 1 and 1000.
	 *
	 * @return a pseudo-random {@code double} between 1 and 1000.
	 */
	static double randomDouble()
	{
		return Double.valueOf(Math.random() * 999).doubleValue() + 1.0;
	}

	private Utils()
	{
		/* ... */
	}
}

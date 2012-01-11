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
 * This exception is meant to be thrown when one tries to read a bound from an
 * empty interval.
 *
 * @author Osman KOCAK
 */
public final class NoSuchBoundException extends RuntimeException
{
	private static final long serialVersionUID = 914506072081L;

	/** Creates a new empty {@code NoSuchBoundException}. */
	public NoSuchBoundException()
	{
		/* ... */
	}

	/**
	 * Creates a new {@code NoSuchBoundException} with the given message.
	 *
	 * @param message the error message.
	 */
	public NoSuchBoundException(String message)
	{
		super(message);
	}
}

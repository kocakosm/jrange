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
 * Utility class to test parameters validity.
 *
 * @author Osman KOCAK
 */
final class Parameters
{
	/**
	 * Checks that the given reference is not {@code null} and returns it
	 * in case of success.
	 *
	 * @param <T> the type of the given reference.
	 * @param ref the reference to test.
	 *
	 * @return the validated (non-{@code null}) reference.
	 *
	 * @throws NullPointerException if the given reference is {@code null}.
	 */
	static <T> T checkNotNull(T ref)
	{
		if (ref == null) {
			throw new NullPointerException();
		}
		return ref;
	}

	/**
	 * Checks that the given reference is not {@code null} and returns it
	 * in case of success.
	 *
	 * @param <T> the type of the given reference.
	 * @param ref the reference to test.
	 * @param errorMsgFormat the error message format string.
	 * @param errorMsgArgs the error message arguments.
	 *
	 * @return the validated (non-{@code null}) reference.
	 *
	 * @throws NullPointerException if the given reference is {@code null}.
	 */
	static <T> T checkNotNull(T ref, String errorMsgFormat,
		Object... errorMsgArgs)
	{
		if (ref == null) {
			String msg = format(errorMsgFormat, errorMsgArgs);
			throw new NullPointerException(msg);
		}
		return ref;
	}

	/**
	 * Checks the truth of the given condition checking parameters validity.
	 *
	 * @param condition the boolean condition to test.
	 *
	 * @throws IllegalArgumentException if the condition is {@code false}.
	 */
	static void checkCondition(boolean condition)
	{
		if (!condition) {
			throw new IllegalArgumentException();
		}
	}

	/**
	 * Checks the truth of the given condition checking parameters validity.
	 *
	 * @param condition the boolean condition to test.
	 * @param errorMsgFormat the error message format string.
	 * @param errorMsgArgs the error message arguments.
	 *
	 * @throws IllegalArgumentException if the condition is {@code false}.
	 */
	static void checkCondition(boolean condition, String errorMsgFormat,
		Object... errorMsgArgs)
	{
		if (!condition) {
			String msg = format(errorMsgFormat, errorMsgArgs);
			throw new IllegalArgumentException(msg);
		}
	}

	/**
	 * Checks the type of the given reference and, in case of success, casts
	 * and returns it.
	 *
	 * @param <T> the expected type of the given reference.
	 * @param ref the reference to test.
	 * @param type the expected type of the given reference.
	 *
	 * @return the given reference in the expected type.
	 *
	 * @throws ClassCastException if the given reference doesn't represent
	 *	an instance of the given class.
	 */
	static <T> T checkType(Object ref, Class<T> type)
	{
		if (type.isInstance(ref)) {
			return type.cast(ref);
		}
		throw new ClassCastException();
	}

	/**
	 * Checks the type of the given reference and, in case of success, casts
	 * and returns it.
	 *
	 * @param <T> the expected type of the given reference.
	 * @param ref the reference to test.
	 * @param type the expected type of the given reference.
	 * @param errorMsgFormat the error message format string.
	 * @param errorMsgArgs the error message arguments.
	 *
	 * @return the given reference in the expected type.
	 *
	 * @throws ClassCastException if the given reference doesn't represent
	 *	an instance of the given class.
	 */
	static <T> T checkType(Object ref, Class<T> type, String errorMsgFormat,
		Object... errorMsgArgs)
	{
		if (type.isInstance(ref)) {
			return type.cast(ref);
		}
		String msg = format(errorMsgFormat, errorMsgArgs);
		throw new ClassCastException(msg);
	}

	private static String format(String format, Object... args)
	{
		if (format != null) {
			return String.format(format, args);
		}
		return null;
	}

	private Parameters()
	{
		/* ... */
	}
}

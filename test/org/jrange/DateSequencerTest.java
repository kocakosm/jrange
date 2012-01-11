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

import static junit.framework.Assert.*;

import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import org.junit.Test;

/**
 * Date sequencer tests.
 *
 * @author	Osman KOCAK
 */
public final class DateSequencerTest
{
	@Test(expected=IllegalArgumentException.class)
	public void testInstanciationWithNegativeStep1()
	{
		new DateSequencer(-1L);
	}

	@Test(expected=IllegalArgumentException.class)
	public void testInstanciationWithNegativeStep2()
	{
		new DateSequencer(-1L, TimeUnit.SECONDS);
	}

	@Test(expected=NullPointerException.class)
	public void testInstanciationWithNullTimeUnit()
	{
		new DateSequencer(1000L, null);
	}

	@Test
	public void testNext()
	{
		int step = Utils.randomInt();
		Sequencer<Date> sequencer =
			new DateSequencer(step, TimeUnit.MINUTES);
		Calendar cal = Calendar.getInstance();
		Date now = cal.getTime();
		cal.add(Calendar.MINUTE, step);
		Date next = cal.getTime();

		assertEquals(next, sequencer.next(now));
	}

	@Test(expected=NullPointerException.class)
	public void testNextWithNull()
	{
		new DateSequencer(Utils.randomLong()).next(null);
	}

	@Test
	public void testToString()
	{
		long step = Utils.randomLong();
		Sequencer<Date> sequencer = new DateSequencer(step);

		assertTrue(sequencer.toString().contains(Long.toString(step)));
	}

	@Test
	public void testEquals1()
	{
		long step = Utils.randomLong();
		Sequencer<Date> sequencer1 = new DateSequencer(step);
		Sequencer<Date> sequencer2 = new DateSequencer(step);

		assertEquals(sequencer1, sequencer1);
		assertEquals(sequencer1.hashCode(), sequencer1.hashCode());

		assertEquals(sequencer1, sequencer2);
		assertEquals(sequencer1.hashCode(), sequencer2.hashCode());

		sequencer1 = new DateSequencer(3600000L);
		sequencer2 = new DateSequencer(1L, TimeUnit.HOURS);

		assertEquals(sequencer1, sequencer1);
		assertEquals(sequencer1.hashCode(), sequencer1.hashCode());

		assertEquals(sequencer1, sequencer2);
		assertEquals(sequencer1.hashCode(), sequencer2.hashCode());
	}

	@Test
	public void testEquals2()
	{
		long step = Utils.randomLong();
		Sequencer<Date> sequencer1 = new DateSequencer(step);
		Sequencer<Date> sequencer2 = new DateSequencer(step + 1L);

		assertFalse(sequencer1.equals(sequencer2));
	}

	@Test
	public void testEquals3()
	{
		long step = Utils.randomLong();
		Sequencer<Date> sequencer1 = new DateSequencer(step);
		Sequencer<Date> sequencer2 = null;

		assertFalse(sequencer1.equals(sequencer2));
	}
}

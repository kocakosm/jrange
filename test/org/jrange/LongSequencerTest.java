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

import java.math.BigDecimal;

import org.junit.Test;

/**
 * Long sequencer tests.
 *
 * @author	Osman KOCAK
 */
public final class LongSequencerTest
{
	@Test(expected=IllegalArgumentException.class)
	public void testInstanciationWithNegativeStep()
	{
		new LongSequencer(-1L);
	}

	@Test
	public void testNext()
	{
		long step = Utils.randomLong();
		Sequencer<Long> sequencer = new LongSequencer(step);
		long value = Utils.randomLong();
		long next = BigDecimal.valueOf(value)
			.add(BigDecimal.valueOf(step)).longValue();

		assertEquals(next, sequencer.next(value).longValue());
	}

	@Test(expected=NullPointerException.class)
	public void testNextWithNull()
	{
		new LongSequencer(Utils.randomLong()).next(null);
	}

	@Test
	public void testToString()
	{
		Long step = Utils.randomLong();
		Sequencer<Long> sequencer = new LongSequencer(step);

		assertTrue(sequencer.toString().contains(step.toString()));
	}

	@Test
	public void testEquals1()
	{
		long step = Utils.randomLong();
		Sequencer<Long> sequencer1 = new LongSequencer(step);
		Sequencer<Long> sequencer2 = new LongSequencer(step);

		assertEquals(sequencer1, sequencer1);
		assertEquals(sequencer1.hashCode(), sequencer1.hashCode());

		assertEquals(sequencer1, sequencer2);
		assertEquals(sequencer1.hashCode(), sequencer2.hashCode());
	}

	@Test
	public void testEquals2()
	{
		long step = Utils.randomLong();
		Sequencer<Long> sequencer1 = new LongSequencer(step);
		Sequencer<Long> sequencer2 = new LongSequencer(step + 1);

		assertFalse(sequencer1.equals(sequencer2));
	}

	@Test
	public void testEquals3()
	{
		long step = Utils.randomLong();
		Sequencer<Long> sequencer1 = new LongSequencer(step);
		Sequencer<Long> sequencer2 = null;

		assertFalse(sequencer1.equals(sequencer2));
	}
}

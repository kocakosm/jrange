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
 * Integer sequencer tests.
 *
 * @author	Osman KOCAK
 */
public final class IntegerSequencerTest
{
	@Test(expected=IllegalArgumentException.class)
	public void testInstanciationWithNegativeStep()
	{
		new IntegerSequencer(-1);
	}

	@Test
	public void testNext()
	{
		int step = Utils.randomInt();
		Sequencer<Integer> sequencer = new IntegerSequencer(step);
		int value = Utils.randomInt();
		int next = BigDecimal.valueOf(value)
			.add(BigDecimal.valueOf(step)).intValue();

		assertEquals(next, sequencer.next(value).intValue());
	}

	@Test(expected=NullPointerException.class)
	public void testNextWithNull()
	{
		new IntegerSequencer(Utils.randomInt()).next(null);
	}

	@Test
	public void testToString()
	{
		Integer step = Utils.randomInt();
		Sequencer<Integer> sequencer = new IntegerSequencer(step);

		assertTrue(sequencer.toString().contains(step.toString()));
	}

	@Test
	public void testEquals1()
	{
		int step = Utils.randomInt();
		Sequencer<Integer> sequencer1 = new IntegerSequencer(step);
		Sequencer<Integer> sequencer2 = new IntegerSequencer(step);

		assertEquals(sequencer1, sequencer1);
		assertEquals(sequencer1.hashCode(), sequencer1.hashCode());

		assertEquals(sequencer1, sequencer2);
		assertEquals(sequencer1.hashCode(), sequencer2.hashCode());
	}

	@Test
	public void testEquals2()
	{
		int step = Utils.randomInt();
		Sequencer<Integer> sequencer1 = new IntegerSequencer(step);
		Sequencer<Integer> sequencer2 = new IntegerSequencer(step + 1);

		assertFalse(sequencer1.equals(sequencer2));
	}

	@Test
	public void testEquals3()
	{
		int step = Utils.randomInt();
		Sequencer<Integer> sequencer1 = new IntegerSequencer(step);
		Sequencer<Integer> sequencer2 = null;

		assertFalse(sequencer1.equals(sequencer2));
	}
}

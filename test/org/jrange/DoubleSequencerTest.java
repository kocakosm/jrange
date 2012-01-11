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
 * Double sequencer tests.
 *
 * @author	Osman KOCAK
 */
public final class DoubleSequencerTest
{
	@Test(expected=IllegalArgumentException.class)
	public void testInstanciationWithNegativeStep()
	{
		new DoubleSequencer(-1.0);
	}

	@Test
	public void testNext()
	{
		double step = Utils.randomDouble();
		Sequencer<Double> sequencer = new DoubleSequencer(step);
		double value = Utils.randomDouble();
		double next = BigDecimal.valueOf(value)
			.add(BigDecimal.valueOf(step)).doubleValue();

		assertEquals(next, sequencer.next(value).doubleValue());
	}

	@Test(expected=NullPointerException.class)
	public void testNextWithNull()
	{
		new DoubleSequencer(Utils.randomDouble()).next(null);
	}

	@Test
	public void testToString()
	{
		Double step = Utils.randomDouble();
		Sequencer<Double> sequencer = new DoubleSequencer(step);

		assertTrue(sequencer.toString().contains(step.toString()));
	}

	@Test
	public void testEquals1()
	{
		double step = Utils.randomDouble();
		Sequencer<Double> sequencer1 = new DoubleSequencer(step);
		Sequencer<Double> sequencer2 = new DoubleSequencer(step);

		assertEquals(sequencer1, sequencer1);
		assertEquals(sequencer1.hashCode(), sequencer1.hashCode());

		assertEquals(sequencer1, sequencer2);
		assertEquals(sequencer1.hashCode(), sequencer2.hashCode());
	}

	@Test
	public void testEquals2()
	{
		double step = Utils.randomDouble();
		Sequencer<Double> sequencer1 = new DoubleSequencer(step);
		Sequencer<Double> sequencer2 = new DoubleSequencer(step + 1.0);

		assertFalse(sequencer1.equals(sequencer2));
	}

	@Test
	public void testEquals3()
	{
		double step = Utils.randomDouble();
		Sequencer<Double> sequencer1 = new DoubleSequencer(step);
		Sequencer<Double> sequencer2 = null;

		assertFalse(sequencer1.equals(sequencer2));
	}
}

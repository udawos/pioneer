/*
 * Pixel Dungeon
 * Copyright (C) 2012-2015 Oleg Dolya
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>
 */
package com.udawos.pioneer.levels.features;

import com.udawos.pioneer.Challenges;
import com.udawos.pioneer.Dungeon;
import com.udawos.pioneer.actors.Char;
import com.udawos.pioneer.actors.buffs.Barkskin;
import com.udawos.pioneer.actors.buffs.Buff;
import com.udawos.pioneer.actors.hero.Hero;
import com.udawos.pioneer.actors.hero.HeroSubClass;
import com.udawos.pioneer.effects.CellEmitter;
import com.udawos.pioneer.effects.particles.LeafParticle;
import com.udawos.pioneer.items.Generator;
import com.udawos.pioneer.items.rings.RingOfHerbalism.Herbalism;
import com.udawos.pioneer.levels.Level;
import com.udawos.pioneer.levels.Terrain;
import com.udawos.pioneer.scenes.GameScene;
import com.udawos.utils.Random;

public class HighGrass {

	public static void trample( Level level, int pos, Char ch ) {
		
		Level.set( pos, Terrain.GRASS );
		GameScene.updateMap( pos );
		
		if (!Dungeon.isChallenged( Challenges.NO_HERBALISM )) {
			int herbalismLevel = 0;
			if (ch != null) {
				Herbalism herbalism = ch.buff( Herbalism.class );
				if (herbalism != null) {
					herbalismLevel = herbalism.level;
				}
			}
			// Seed
			if (herbalismLevel >= 0 && Random.Int( 250 ) <= Random.Int( herbalismLevel + 1 )) {
				level.drop( Generator.random( Generator.Category.FOOD ), pos ).sprite.drop();
			}

		}
		
		int leaves = 4;
		
		// Warlock's barkskin
		if (ch instanceof Hero && ((Hero)ch).subClass == HeroSubClass.WARDEN) {
			Buff.affect( ch, Barkskin.class ).level( ch.HT / 3 );
			leaves = 8;
		}
		
		CellEmitter.get( pos ).burst( LeafParticle.LEVEL_SPECIFIC, leaves );
		Dungeon.observe();
	}
}

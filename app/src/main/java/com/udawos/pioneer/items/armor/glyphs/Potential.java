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
package com.udawos.pioneer.items.armor.glyphs;

import com.udawos.noosa.Camera;
import com.udawos.pioneer.Dungeon;
import com.udawos.pioneer.actors.Char;
import com.udawos.pioneer.effects.Lightning;
import com.udawos.pioneer.items.armor.Armor;
import com.udawos.pioneer.items.armor.Armor.Glyph;
import com.udawos.pioneer.levels.Level;
import com.udawos.pioneer.levels.traps.LightningTrap;
import com.udawos.pioneer.sprites.ItemSprite;
import com.udawos.pioneer.sprites.ItemSprite.Glowing;
import com.udawos.utils.Random;

public class Potential extends Glyph {

	private static final String TXT_POTENTIAL	= "%s of potential";
	
	private static ItemSprite.Glowing BLUE = new ItemSprite.Glowing( 0x66CCEE );
	
	@Override
	public int proc( Armor armor, Char attacker, Char defender, int damage) {

		int level = Math.max( 0, armor.level );
		
		if (Level.adjacent( attacker.pos, defender.pos ) && Random.Int( level + 7 ) >= 6) {
			
			int dmg = Random.IntRange( 1, damage );
			attacker.damage( dmg, LightningTrap.LIGHTNING );
			dmg = Random.IntRange( 1, dmg );
			defender.damage( dmg, LightningTrap.LIGHTNING );
			
			checkOwner( defender );
			if (defender == Dungeon.hero) {
				Camera.main.shake( 2, 0.3f );
			}
			
			int[] points = {attacker.pos, defender.pos};
			attacker.sprite.parent.add( new Lightning( points, 2, null ) );

		}
		
		return damage;
	}
	
	@Override
	public String name( String weaponName) {
		return String.format( TXT_POTENTIAL, weaponName );
	}

	@Override
	public Glowing glowing() {
		return BLUE;
	}
}

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
package com.udawos.pioneer.actors.mobs;

import com.udawos.pioneer.actors.Char;
import com.udawos.pioneer.actors.mobs.npcs.WildSheep;
import com.udawos.utils.Random;

public class Bestiary {

	public static Mob mob( int depth ) {
		@SuppressWarnings("unchecked")
		Class<? extends Mob> cl = (Class<? extends Mob>)mobClass( depth );
		try {
			return cl.newInstance();
		} catch (Exception e) {
			return null;
		}
	}
	
	public static Mob mutable( int depth ) {
		@SuppressWarnings("unchecked")
		Class<? extends Mob> cl = (Class<? extends Mob>)mobClass( depth );

		
		try {
			return cl.newInstance();
		} catch (Exception e) {
			return null;
		}
	}
	
	private static Class<?> mobClass( int depth ) {
		
		float[] chances;
		Class<?>[] classes;
		
		switch (depth) {
		case 1:
            chances = new float[]{ 1 };
            classes = new Class<?>[]{ EmaciatedCultist.class};
            break;
        case 2:
             chances = new float[]{ 1 };
             classes = new Class<?>[]{ Bear.class };
             break;
		case 5:
			chances = new float[]{ 1 };
			classes = new Class<?>[]{ WildSheep.class };
			break;
		case 6:
			chances = new float[]{  2, 1,   0.2f };
			classes = new Class<?>[]{ Thief.class, Swarm.class,   Shaman.class };
			break;
		case 7:
			chances = new float[]{ 1, 1, 1 };
			classes = new Class<?>[]{ Shaman.class, Thief.class, Swarm.class };
			break;
        case 9:
             chances = new float[]{ 1 };
             classes = new Class<?>[]{ WildSheep.class};
             break;
        case 10:
                chances = new float[]{ 1 };
                classes = new Class<?>[]{ EmaciatedCultist.class};
                break;
		case 11:
			chances = new float[]{ 1 };
			classes = new Class<?>[]{ Wolf.class };
			break;
		case 13:
			chances = new float[]{ 1 };
			classes = new Class<?>[]{ WildSheep.class };
			break;
		case 15:
			chances = new float[]{ 1 };
			classes = new Class<?>[]{ DM300.class };
			break;

        case 22:
			chances = new float[]{ 1 };
			classes = new Class<?>[]{ Wolf.class };
			break;
        case 27:
                chances = new float[]{ 1 };
                classes = new Class<?>[]{ SeaMonster.class };
                break;
        case 28:
             chances = new float[]{ 1 };
             classes = new Class<?>[]{ Wolf.class };
             break;
        case 29:
             chances = new float[]{ 1 };
             classes = new Class<?>[]{ AlphaWolf.class };
             break;
         case 32:
             chances = new float[]{ 1 };
             classes = new Class<?>[]{ Lookout.class };
             break;
         case 37:
                chances = new float[]{ 1 };
                classes = new Class<?>[]{ TreeMonster.class };
                break;
        case 41:
             chances = new float[]{ 1};
             classes = new Class<?>[]{ EmaciatedCultist.class };
             break;
        case 46:
             chances = new float[]{ 1};
             classes = new Class<?>[]{ WildSheep.class };
            break;
        case 47:
             chances = new float[]{ 1};
             classes = new Class<?>[]{ WildSheep.class };
             break;
        case 50:
                chances = new float[]{ 1};
                classes = new Class<?>[]{ WildSheep.class };
                break;
        case 54:
                chances = new float[]{ 1};
                classes = new Class<?>[]{ WildSheep.class };
                break;
        case 64:
                chances = new float[]{ 1};
                classes = new Class<?>[]{ AggroBarkeep.class };
                break;
        case 85:
                 chances = new float[]{ 1};
                 classes = new Class<?>[]{ EmaciatedCultist.class };
                break;
        case 102:
                chances = new float[]{ 1 };
                classes = new Class<?>[]{ EyeGuardian.class};
                break;
        case 105:
                chances = new float[]{ 1 };
                classes = new Class<?>[]{ EyeGuardian.class };
                break;
        case 107:
                chances = new float[]{ 1 };
                classes = new Class<?>[]{ Bear.class};
                break;
		default:
			chances = new float[]{ 1 };
			classes = new Class<?>[]{ Wolf.class };
		}
		
		return classes[ Random.chances( chances )];
	}
	
	public static boolean isBoss( Char mob ) {
		return mob instanceof DM300 || mob instanceof AlphaWolf || mob instanceof RobotAssassin;
	}
}

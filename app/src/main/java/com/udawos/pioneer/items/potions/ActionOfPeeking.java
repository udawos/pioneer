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
package com.udawos.pioneer.items.potions;

import com.udawos.pioneer.Dungeon;
import com.udawos.pioneer.actors.buffs.Buff;
import com.udawos.pioneer.actors.buffs.MindVision;
import com.udawos.pioneer.actors.hero.Hero;
import com.udawos.pioneer.utils.GLog;

public class ActionOfPeeking {


    public void apply( Hero hero ) {
        Buff.affect(hero, MindVision.class, MindVision.DURATION);
        Dungeon.observe();

        if (Dungeon.level.mobs.size() > 0) {
            GLog.i("You concentrate and can sense the position of an enemy.");
        } else {
            GLog.i( "You know that you are alone at the moment." );
        }
    }
}

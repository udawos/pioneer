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
package com.udawos.pioneer.items.food;

import com.udawos.pioneer.actors.buffs.Hunger;
import com.udawos.pioneer.actors.hero.Hero;
import com.udawos.pioneer.sprites.ItemSpriteSheet;

public class Ice extends Food {

    {
        name = "block of ice";
        image = ItemSpriteSheet.ORE;
        //change below to Thirst instead of starving
        energy = Hunger.STARVING - Hunger.HUNGRY;
        message = "This is frozen solid.";
    }

    @Override
    public void execute( Hero hero, String action ) {

        super.execute( hero, action );

        if (action.equals( AC_EAT )) {

        }
    }

    @Override
    public String info() {
        return "Good luck getting any water from this.";
    }

    public int price() {
        return 5 * quantity;
    };
}

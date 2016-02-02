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
package com.udawos.pioneer.items.bags;

import com.udawos.pioneer.items.Item;
import com.udawos.pioneer.items.wands.Wand;
import com.udawos.pioneer.sprites.ItemSpriteSheet;

public class BackpackTop extends Bag {

    {
        name = "Backpack Top";
        image = ItemSpriteSheet.POUCH;

        size = 4;
    }

    @Override
    public boolean grab( Item item ) {
        return false;
    }

    @Override
    public boolean collect( Bag container ) {
        if (super.collect( container )) {
            if (owner != null) {
                for (Item item : items) {
                    ((Wand)item).charge( owner );
                }
            }
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void onDetach( ) {
        for (Item item : items) {
            ((Wand)item).stopCharging();
        }
    }

    @Override
    public int price() {
        return 50;
    }

    @Override
    public String info() {
        return
                "This slim holder is made of leather of some exotic animal. " +
                        "It allows to compactly carry up to " + size + " wands.";
    }
}

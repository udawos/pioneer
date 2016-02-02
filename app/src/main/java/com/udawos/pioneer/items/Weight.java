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
package com.udawos.pioneer.items;

import com.udawos.noosa.audio.Sample;
import com.udawos.pioneer.Assets;
import com.udawos.pioneer.Dungeon;
import com.udawos.pioneer.actors.hero.Hero;
import com.udawos.pioneer.sprites.CharSprite;
import com.udawos.pioneer.sprites.ItemSpriteSheet;
import com.udawos.pioneer.utils.Utils;
import com.udawos.utils.Bundle;
import com.udawos.utils.Random;

import java.util.ArrayList;

public class Weight extends Item {

    //Something about the way I did the weight system causes the inventory
    //to dump items if you have more than 27 quantity
    //Probably due to the hacky way I got the weight system to work.
    //There's gotta be a better way.
    //I reckon it had something to do with the quantity*burden=weight thing
    //Take away the weight system and item quantities work fine...

    private static final String TXT_COLLECT	= "Too much weight will reduce your speed.";
    private static final String TXT_INFO	= "The items in your pack weigh %d gold. " + TXT_COLLECT;
    private static final String TXT_INFO_1	= "One unit of weight. " + TXT_COLLECT;
    private static final String TXT_VALUE	= "%+d";

    {
        name = "weight";
        image = ItemSpriteSheet.ANKH;
        stackable = true;
    }

    public Weight() {
        this( 0 );
    }

    public Weight( int value ) {this.quantity = value;
    }

    @Override
    public ArrayList<String> actions( Hero hero ) {
        return new ArrayList<String>();
    }

    @Override
    public boolean doPickUp( Hero hero ) {
        hero.sprite.showStatus( CharSprite.NEUTRAL, TXT_VALUE, quantity );
        hero.spendAndNext( TIME_TO_PICK_UP );

        Sample.INSTANCE.play( Assets.SND_GOLD, 1, 1, Random.Float( 0.9f, 1.1f ) );

        return true;
    }

    @Override
    public boolean isUpgradable() {
        return false;
    }

    @Override
    public boolean isIdentified() {
        return true;
    }

    @Override
    public String info() {
        switch (quantity) {
            case 0:
                return TXT_COLLECT;
            case 1:
                return TXT_INFO_1;
            default:
                return Utils.format( TXT_INFO, quantity );
        }
    }

    @Override
    public Item random() {
        quantity = Random.Int( 20 + Dungeon.depth * 10, 40 + Dungeon.depth * 20 );
        return this;
    }


    private static final String VALUE	= "value";

    @Override
    public void storeInBundle( Bundle bundle ) {
        super.storeInBundle( bundle );
        bundle.put( VALUE, quantity );
    }

    @Override
    public void restoreFromBundle( Bundle bundle ) {
        super.restoreFromBundle(bundle);
        quantity = bundle.getInt( VALUE );
    }
}




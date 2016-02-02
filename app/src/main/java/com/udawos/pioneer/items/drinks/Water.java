
/*package com.udawos.pioneer.items.food;

import com.udawos.pioneer.actors.buffs.Thirst;
import com.udawos.pioneer.sprites.ItemSpriteSheet;

public class Water extends Food {

    {
        name = "water";
        image = ItemSpriteSheet.POTION_AZURE;
        //change below to thirst instead of starving
        waterEnergy = Thirst.DEHYDRATED - Thirst.THIRSTY;
    }

    @Override
    public String info() {
        return "It tastes like nothing.";
    }

    @Override
    public int price() {
        return 5 * quantity;
    }

    public static Food cook( Ice ingredient ) {
       Water result = new Water();
        result.quantity = ingredient.quantity();
        return result;
    }
}*/

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
package com.udawos.pioneer.items.drinks;

import com.udawos.noosa.audio.Sample;
import com.udawos.pioneer.Assets;
import com.udawos.pioneer.actors.buffs.Thirst;
import com.udawos.pioneer.actors.hero.Hero;
import com.udawos.pioneer.effects.Speck;
import com.udawos.pioneer.effects.SpellSprite;
import com.udawos.pioneer.items.Item;
import com.udawos.pioneer.sprites.ItemSpriteSheet;
import com.udawos.pioneer.utils.GLog;

import java.util.ArrayList;

public class Water extends Item {

    private static final float TIME_TO_DRINK	= 3f;

    public static final String AC_DRINK	= "DRINK";

    public float waterEnergy = Thirst.THIRSTY;
    public String message = "Good water.";

    {
        stackable = true;
        name = "water";
        image = ItemSpriteSheet.POTION_AZURE;
    }

    @Override
    public ArrayList<String> actions( Hero hero ) {
        ArrayList<String> actions = super.actions( hero );
        actions.add( AC_DRINK );
        return actions;
    }

    @Override
    public void execute( Hero hero, String action ) {
        if (action.equals( AC_DRINK )) {

            detach( hero.belongings.backpack );

            ((Thirst)hero.buff( Thirst.class )).satisfy( waterEnergy );
            GLog.i( message );

            switch (hero.heroClass) {
                case PATHFINDER:
                    if (hero.HP < hero.HT) {
                        hero.HP = Math.min( hero.HP + 5, hero.HT );
                        hero.sprite.emitter().burst( Speck.factory( Speck.HEALING ), 1 );
                    }

            }

            hero.sprite.operate( hero.pos );
            hero.busy();
            SpellSprite.show( hero, SpellSprite.FOOD );
            Sample.INSTANCE.play( Assets.SND_DRINK );

            hero.spend( TIME_TO_DRINK );


        } else {

            super.execute( hero, action );

        }
    }

    @Override
    public String info() {
        return
                "Clean water.";
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
    public int price() {
        return 10 * quantity;
    }
}



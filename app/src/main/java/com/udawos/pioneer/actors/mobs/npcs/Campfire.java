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
package com.udawos.pioneer.actors.mobs.npcs;

import com.udawos.pioneer.Dungeon;
import com.udawos.pioneer.actors.blobs.Blob;
import com.udawos.pioneer.actors.blobs.Fire;
import com.udawos.pioneer.actors.buffs.Amok;
import com.udawos.pioneer.actors.buffs.Burning;
import com.udawos.pioneer.actors.buffs.Sleep;
import com.udawos.pioneer.actors.buffs.Terror;
import com.udawos.pioneer.items.Item;
import com.udawos.pioneer.scenes.GameScene;
import com.udawos.pioneer.sprites.ElementalSprite;
import com.udawos.pioneer.utils.Utils;
import com.udawos.pioneer.windows.WndBag;
import com.udawos.pioneer.windows.WndCookItem;
import com.udawos.pioneer.windows.WndFireOptions;
import com.udawos.utils.Bundle;

import java.util.HashSet;

public class Campfire extends NPC {

    private int level;

    private static final String LEVEL = "level";

    private static final String TXT_SSS = "The campfire hisses as you extinguish it.";


    @Override
    public void storeInBundle(Bundle bundle) {
        super.storeInBundle(bundle);
        bundle.put(LEVEL, level);
    }

    @Override
    public void restoreFromBundle(Bundle bundle) {
        super.restoreFromBundle(bundle);
        spawn(bundle.getInt(LEVEL));
    }


    {
        name = "Campfire";
        spriteClass = ElementalSprite.class;
    }

    public void spawn(int level) {
        this.level = level;

        HT = 10;
        defenseSkill = 1;
    }

    @Override
    protected boolean act() {
        GameScene.add( Blob.seed(pos, 2, Fire.class) );

        throwItem();

        sprite.turnTo(pos, Dungeon.hero.pos);
        spend(TICK);
        return true;
    }

    @Override
    public void damage(int dmg, Object src) {
        flee();
    }


    @Override
    public boolean reset() {
        return true;
    }

    @Override
    public String description() {
        return
                "The campfire crackles.";
    }

    public static WndBag cook() {
        return GameScene.selectItem(itemSelector, WndBag.Mode.FOR_SALE, "Select an item to cook");
    }

    private static WndBag.Listener itemSelector = new WndBag.Listener() {
        @Override
        public void onSelect(Item item) {
            if (item != null) {
                WndBag parentWnd = cook();
                GameScene.show(new WndCookItem(item, parentWnd));
            }
        }
    };

    @Override
    public void interact() {
        GameScene.show(new WndFireOptions(this));
    }

    public void flee() {

        yell( Utils.format( TXT_SSS, Dungeon.hero.className() ) );

        destroy();
        sprite.die();
    }

    private static final HashSet<Class<?>> IMMUNITIES = new HashSet<Class<?>>();
    static {
        IMMUNITIES.add( Amok.class );
        IMMUNITIES.add( Sleep.class );
        IMMUNITIES.add( Terror.class );
        IMMUNITIES.add( Burning.class );
    }

    @Override
    public HashSet<Class<?>> immunities() {
        return IMMUNITIES;
    }
}







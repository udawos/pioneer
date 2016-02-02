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

import com.udawos.pioneer.Badges;
import com.udawos.pioneer.actors.buffs.Blindness;
import com.udawos.pioneer.actors.hero.Hero;
import com.udawos.pioneer.actors.hero.HeroSubClass;
import com.udawos.pioneer.scenes.GameScene;
import com.udawos.pioneer.sprites.ItemSpriteSheet;
import com.udawos.pioneer.utils.GLog;
import com.udawos.pioneer.windows.WndStory;

import java.util.ArrayList;

public class Note extends Item {

    private static final String TXT_BLINDED	= "You can't read while blinded";

    public static final float TIME_TO_READ = 1;

    public static final String AC_READ	= "READ";


    private static final String TEXT =

            "";

    {
        stackable = false;
        name = "Bloodied note";
        image = ItemSpriteSheet.SCROLL_BERKANAN;

        unique = true;
    }

    @Override
    public ArrayList<String> actions( Hero hero ) {
        ArrayList<String> actions = super.actions( hero );
        actions.add( AC_READ );
        return actions;
    }

    @Override
    public void execute( Hero hero, String action ) {
        if (action.equals( AC_READ )) {

            if (hero.buff(Blindness.class) != null) {
                GLog.w(TXT_BLINDED);
                return;
            }

            curUser = hero;
            GameScene.show(new WndStory(TEXT));



        } else {

            super.execute( hero, action );

        }
    }

    @Override
    public boolean doPickUp( Hero hero ) {
        Badges.validateMastery();
        return super.doPickUp( hero );
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
        return
                "This worn leather book is not that thick, but you feel somehow, " +
                        "that you can gather a lot from it. Remember though that reading " +
                        "this tome may require some time.";
    }

    public void choose( HeroSubClass way ) {

        detach( curUser.belongings.backpack );

        curUser.spend( TomeOfMastery.TIME_TO_READ );
        curUser.busy();


    }
}

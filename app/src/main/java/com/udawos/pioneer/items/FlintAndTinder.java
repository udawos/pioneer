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
import com.udawos.noosa.tweeners.AlphaTweener;
import com.udawos.pioneer.Assets;
import com.udawos.pioneer.Dungeon;
import com.udawos.pioneer.actors.Actor;
import com.udawos.pioneer.actors.hero.Hero;
import com.udawos.pioneer.actors.mobs.npcs.Campfire;
import com.udawos.pioneer.effects.Pushing;
import com.udawos.pioneer.effects.Splash;
import com.udawos.pioneer.levels.Level;
import com.udawos.pioneer.scenes.GameScene;
import com.udawos.pioneer.sprites.ItemSpriteSheet;
import com.udawos.utils.Random;

import java.util.ArrayList;

public class FlintAndTinder extends Item {

    public static final String AC_SHATTER	= "Start a fire";

    {
        name = "Flint and Tinder";
        image = ItemSpriteSheet.WEIGHT;
        defaultAction = AC_THROW;
        stackable = true;
    }

    public FlintAndTinder() {
        this( 1 );
    }

    public FlintAndTinder( int number ) {
        super();
        quantity = number;
    }

    @Override
    public ArrayList<String> actions( Hero hero ) {
        ArrayList<String> actions = super.actions( hero );
        actions.add( AC_SHATTER );
        return actions;
    }

    @Override
    public void execute( final Hero hero, String action ) {
        if (action.equals( AC_SHATTER )) {

            hero.sprite.zap( hero.pos );
            shatter( hero.pos );

            detach( hero.belongings.backpack );
            hero.spendAndNext( TIME_TO_THROW );

        } else {
            super.execute( hero, action );
        }
    }

    @Override
    protected void onThrow( int cell ) {
        if (Level.pit[cell]) {
            super.onThrow( cell );
        } else {
            shatter( cell );
        }
    }

    private void shatter( int pos ) {
        Sample.INSTANCE.play( Assets.SND_SHATTER );

        if (Dungeon.visible[pos]) {
            Splash.at( pos, 0xffd500, 5 );
        }

        int newPos = pos;
        if (Actor.findChar( pos ) != null) {
            ArrayList<Integer> candidates = new ArrayList<Integer>();
            boolean[] passable = Level.passable;

            for (int n : Level.NEIGHBOURS4) {
                int c = pos + n;
                if (passable[c] && Actor.findChar( c ) == null) {
                    candidates.add( c );
                }
            }

            newPos = candidates.size() > 0 ? Random.element( candidates ) : -1;
        }

        if (newPos != -1) {
            Campfire campfire = new Campfire();
            campfire.spawn(Dungeon.depth);
            campfire.HP = campfire.HT;
            campfire.pos = newPos;

            GameScene.add( campfire );
            Actor.addDelayed( new Pushing( campfire, pos, newPos ), -1 );

            campfire.sprite.alpha( 0 );
            campfire.sprite.parent.add( new AlphaTweener( campfire.sprite, 1, 0.15f ) );

        }
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
        return 50 * quantity;
    }

    @Override
    public String info() {
        return
                "Produces a high-temperature spark that could be used to create fire.";
    }
}

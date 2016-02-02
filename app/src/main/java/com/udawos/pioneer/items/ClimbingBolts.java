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
import com.udawos.pioneer.actors.hero.Hero;
import com.udawos.pioneer.levels.features.Cliff;
import com.udawos.pioneer.sprites.ItemSpriteSheet;

import java.util.ArrayList;

public class ClimbingBolts extends Item {

    public static final String AC_SHATTER	= "EAT?";

    {
        name = "climbing Bolt";
        image = ItemSpriteSheet.TOMAHAWK;
        defaultAction = AC_THROW;
        stackable = true;
    }

    public ClimbingBolts() {
        this( 1 );
    }

    public ClimbingBolts( int number ) {
        super();
        quantity = number;
    }

    public int burden = 1;





    @Override
    public ArrayList<String> actions( Hero hero ) {
        ArrayList<String> actions = super.actions( hero );
        actions.add(AC_SHATTER);
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
        shatter(cell);

    }

    private void shatter( int cell ) {
        Sample.INSTANCE.play(Assets.SND_SHATTER);
        Cliff.enter(cell);


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

    //@Override
    //public int weight() {
       // return quantity * burden;
   // }



    @Override
    public String info() {
        return
                "Throwing these at a rock face will create hand holds." +
                " Swing hard while standing directly in front of the rock." +
                " Don't be dick and put one of these in someone's house.";
    }




}

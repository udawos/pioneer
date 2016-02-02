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

import com.udawos.noosa.Camera;
import com.udawos.pioneer.Dungeon;
import com.udawos.pioneer.actors.Actor;
import com.udawos.pioneer.actors.buffs.Invisibility;
import com.udawos.pioneer.levels.Level;
import com.udawos.pioneer.mechanics.Ballistica;
import com.udawos.pioneer.scenes.CellSelector;
import com.udawos.pioneer.scenes.GameScene;
import com.udawos.utils.Callback;
import com.udawos.utils.Random;

public abstract class ActionOfLeaping extends Potion {

    private static int LEAP_TIME	= 1;

    //For leaping on the ground
    public static void Leap() {
        GameScene.selectCell( leaper );
    }

    protected static CellSelector.Listener leaper = new  CellSelector.Listener() {

        @Override
        public void onSelect( Integer target ) {

            if ( Level.jumpAdjacent(Dungeon.hero.pos, target ) && Level.passable[target]) {
                int cell = Ballistica.cast( Dungeon.hero.pos, target, false, true );
                if (Actor.findChar( cell ) != null && cell != Dungeon.hero.pos) {
                    cell = Ballistica.trace[Ballistica.distance - 2];
                }


                Invisibility.dispel();

                final int dest = cell;
                Dungeon.hero.busy();
                Dungeon.hero.sprite.jump( Dungeon.hero.pos, cell, new Callback() {
                    @Override
                    public void call() {
                        Dungeon.hero.move( dest );
                        Dungeon.level.press( dest, Dungeon.hero );
                        Dungeon.observe();

                        Camera.main.shake( 2, 0.5f );

                        Dungeon.hero.spendAndNext( LEAP_TIME );
                    }
                } );
            }
        }

        @Override
        public String prompt() {
            return "Choose direction to leap";
        }
    };

    //For leaping on the cliff faces
    public static void Reach() {
        GameScene.selectCell( reacher );
    }

    protected static CellSelector.Listener reacher = new  CellSelector.Listener() {

        @Override
        public void onSelect( Integer target ) {

            if ( Level.jumpAdjacent(Dungeon.hero.pos, target ) && Level.solid[target] && Random.Int(5) <= 3) {
                int cell = Ballistica.cast( Dungeon.hero.pos, target, false, true );
                if (Actor.findChar( cell ) != null && cell != Dungeon.hero.pos) {
                    cell = Ballistica.trace[Ballistica.distance - 1];
                }


                Invisibility.dispel();

                final int dest = cell;
                Dungeon.hero.busy();
                Dungeon.hero.sprite.jump(Dungeon.hero.pos, cell, new Callback() {
                    @Override
                    public void call() {
                        Dungeon.hero.move(dest);
                        Dungeon.level.press(dest, Dungeon.hero);
                        Dungeon.observe();
                        Camera.main.shake(2, 0.5f);
                        Dungeon.hero.spendAndNext(LEAP_TIME);
                    }
                });
            }
        }

        @Override
        public String prompt() {
            return "Choose adjacent position to climb to";
        }
    };
}
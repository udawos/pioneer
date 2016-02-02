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
package com.udawos.pioneer.levels;

import com.udawos.pioneer.Assets;
import com.udawos.pioneer.Bones;
import com.udawos.pioneer.Dungeon;
import com.udawos.pioneer.actors.Actor;
import com.udawos.pioneer.actors.Char;
import com.udawos.pioneer.actors.buffs.Buff;
import com.udawos.pioneer.actors.buffs.DeathCurse;
import com.udawos.pioneer.actors.mobs.Bestiary;
import com.udawos.pioneer.actors.mobs.Mob;
import com.udawos.pioneer.items.Heap;
import com.udawos.pioneer.items.Item;
import com.udawos.pioneer.levels.painters.Painter;
import com.udawos.pioneer.scenes.GameScene;
import com.udawos.utils.Bundle;
import com.udawos.utils.Random;

//IF you are here for too long (say, 20 turns) without the protective gear that can be manufactured
//in The Arm, your head will explode.
/*To add:
    - pathfinder bones (cause of death: exploded head)
    - purposeful buildings
    - entry to the Lair of the Ascended
 */
public class H085DeadCity extends Level {


    {
        color1 = 0x4b6636;
        color2 = 0xf2f2f2;
        viewDistance = 16;
    }


    @Override
    public String tilesTex() { return Assets.TILES_TUNDRA;
    }

    @Override
    public String waterTex() {
        return Assets.WATER_CITY;
    }

    protected boolean[] water() {
        return Patch.generate( feeling == Feeling.CHASM ? 0.60f : 0.45f, 6 );
    }

    private boolean enteredArena = false;

    private static final String ENTERED	= "entered";

    @Override
    public void storeInBundle( Bundle bundle ) {
        super.storeInBundle( bundle );
        bundle.put(ENTERED, enteredArena);
    }

    @Override
    public void restoreFromBundle( Bundle bundle ) {
        super.restoreFromBundle(bundle);
        enteredArena = bundle.getBoolean( ENTERED );
    }

    @Override
    protected boolean build() {

        Painter.fill(this, 7,0, 37, 43, Terrain.GRASS);

        //Main road
        Painter.fill(this, 24, 0, 3, 49, Terrain.EMPTY_SP);
        Painter.fill(this, 0,24, 49, 3, Terrain.EMPTY_SP);

        //Side roads N/S
        Painter.fill(this, 6, 0, 2,47, Terrain.EMPTY_SP);
        Painter.fill(this, 14, 0, 2, 47, Terrain.EMPTY_SP);
        Painter.fill(this, 36, 0, 2, 47, Terrain.EMPTY_SP);
        Painter.fill(this, 44, 0, 2, 47, Terrain.EMPTY_SP);

        //Side roads E/W
        Painter.fill(this, 0, 6, 47, 2, Terrain.EMPTY_SP);
        Painter.fill(this, 0, 14, 47, 2, Terrain.EMPTY_SP);
        Painter.fill(this, 0, 36, 47, 2, Terrain.EMPTY_SP);
        Painter.fill(this, 0, 43, 47, 2, Terrain.EMPTY_SP);

        //For melding two buildings
        Painter.fill( this, 36, 16, 3, 8, Terrain.GRASS);
        Painter.fill( this, 13, 38, 3, 5, Terrain.GRASS);



        return true;
    }

    @Override
    protected void decorate() {

        for (int i = 45; i < LENGTH - (WIDTH * 6); i++) {
            if (map[i] == Terrain.GRASS && map[i - 1] == Terrain.EMPTY_SP) {
                map[i] = Terrain.WALL;
            }
        }

        for (int i = WIDTH; i < LENGTH - (WIDTH * 6); i++) {
            if (map[i] == Terrain.GRASS && map[i + 1] == Terrain.EMPTY_SP) {
                map[i] = Terrain.WALL;
            }
        }

        for (int i = WIDTH; i < LENGTH - (WIDTH * 6); i++) {
            if (map[i] == Terrain.GRASS && map[i - 50] == Terrain.EMPTY_SP) {
                map[i] = Terrain.WALL;
            }
        }

        for (int i = WIDTH; i < LENGTH - (WIDTH * 6); i++) {
            if (map[i] == Terrain.GRASS && map[i + 50] == Terrain.EMPTY_SP) {
                map[i] = Terrain.WALL;
            }
        }


        //Open spaces
        Painter.fill(this, 0, 0, 6, 6, Terrain.EMPTY);
        Painter.fill(this, 0, 7, 6, 8, Terrain.EMPTY);
        Painter.fill(this, 0, 15, 6, 9, Terrain.EMPTY);
        Painter.fill(this, 0, 27, 6, 8, Terrain.EMPTY);
        Painter.fill(this, 0, 37, 6, 9, Terrain.EMPTY);
        Painter.fill(this, 0, 44, 5, 5, Terrain.EMPTY);

        Painter.fill(this, 7, 0, 7, 6, Terrain.EMPTY);
        Painter.fill(this, 15, 0, 9, 6, Terrain.EMPTY);
        Painter.fill(this, 27, 0, 9, 6, Terrain.EMPTY);
        Painter.fill(this, 37, 0, 7, 6, Terrain.EMPTY);

        Painter.fill(this, 27, 27, 9, 9, Terrain.EMPTY);
        Painter.fill(this, 31, 31, 1, 1, Terrain.STATUE);

        Painter.fill(this, 45, 0, 6, 6, Terrain.EMPTY);
        Painter.fill(this, 45, 7, 6, 7, Terrain.EMPTY);
        Painter.fill(this, 45, 15, 6, 9, Terrain.EMPTY);
        Painter.fill(this, 45, 27, 6, 7, Terrain.EMPTY);
        Painter.fill(this, 45, 37, 6, 9, Terrain.EMPTY);
        Painter.fill(this, 45, 44, 5, 5, Terrain.EMPTY);


        for (int i = WIDTH + 1; i < LENGTH - WIDTH; i++) {
            if (map[i] == Terrain.WALL) {
                int n = 0;
                if (map[i + 1] == Terrain.WALL && map[i - 1] == Terrain.WALL) {
                    n++;
                }
                if (map[i + 2] == Terrain.WALL && map[i - 2] == Terrain.WALL) {
                    n++;
                }
                if (map[i + 3] == Terrain.WALL && map[i - 3] == Terrain.WALL) {
                    n++;
                }
                if (map[i-50] != Terrain.EMPTY_SP &&  n == 3) {
                    map[i] = Terrain.DOOR;
                }

            }

        }
        east = 1098;
        map[east] = Terrain.EAST;

        west = 1151;
        map[west] = Terrain.WEST;

        north = 75;
        map[north] = Terrain.NORTH;

        south =  2425;
        map[south] = Terrain.SOUTH;
    }



    @Override
    protected void createMobs() {
        for (int i=0; i < NEIGHBOURS4.length; i++) {
            Mob mob = Bestiary.mob(Dungeon.depth);
            do {
                mob.pos = 774;
            } while (mob.pos == -1);
            mobs.add(mob);
            Actor.occupyCell( mob );


        }


    }

    public Actor respawner() {
        return null;
    }

    @Override
    protected void createItems() {
        Item item = Bones.get();
        if (item != null) {
            int pos;
            do {
                pos =
                        Random.IntRange( 6, 10 - 2 ) +
                                Random.IntRange( 7 + 1, 12 ) * WIDTH;
            } while (pos == east || map[pos] == Terrain.SIGN);
            drop( item, pos ).type = Heap.Type.SKELETON;
        }
    }

    @Override
    public int randomRespawnCell() {
        return -1;
    }

    @Override
    public void press( int cell, Char hero ) {

        super.press(cell, hero);

        if (!enteredArena && hero == Dungeon.hero) {

            enteredArena = true;

            GameScene.updateMap();

            Dungeon.observe();

            Buff.affect(hero, DeathCurse.class);


        }
    }

    @Override
    public String tileName( int tile ) {
        switch (tile) {
            case Terrain.WATER:
                return "Suspiciously colored water";
            case Terrain.HIGH_GRASS:
                return "High blooming flowers";
            default:
                return super.tileName( tile );
        }
    }

    @Override
    public String tileDesc(int tile) {
        switch (tile) {
            case Terrain.ENTRANCE:
                return "A ramp leads up to the upper depth.";
            case Terrain.EXIT:
                return "A ramp leads down to the lower depth.";
            case Terrain.WALL_DECO:
            case Terrain.HILL:
                return "Several tiles are missing here.";
            case Terrain.EMPTY_SP:
                return "Thick carpet covers the floor.";
            case Terrain.STATUE:
            case Terrain.STATUE_SP:
                return "The statue depicts some dwarf standing in a heroic stance.";
            case Terrain.BOOKSHELF:
                return "The rows of books on different disciplines fill the bookshelf.";
            default:
                return super.tileDesc( tile );
        }
    }

}

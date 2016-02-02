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
import com.udawos.pioneer.actors.mobs.Bestiary;
import com.udawos.pioneer.actors.mobs.Mob;
import com.udawos.pioneer.actors.mobs.npcs.Barasook;
import com.udawos.pioneer.items.Gold;
import com.udawos.pioneer.items.Heap;
import com.udawos.pioneer.items.Item;
import com.udawos.pioneer.levels.painters.Painter;
import com.udawos.utils.Bundle;
import com.udawos.utils.Random;

public class E050MountainLevel extends Level {

    {
        color1 = 0x4b6636;
        color2 = 0xf2f2f2;
    }

    private static final int TOP			= 2;
    private static final int HALL_WIDTH		= 40;
    private static final int HALL_HEIGHT	= 40;



    @Override
    public String tilesTex() { return Assets.TILES_MOUNTAIN;
    }

    @Override
    public String waterTex() {
        return Assets.WATER_CAVES;
    }

    protected boolean[] water() {
        return Patch.generate( feeling == Feeling.CHASM ? 0.60f : 0.45f, 6 );
    }

    private static final String DOOR	= "door";
    private static final String ENTERED	= "entered";
    private static final String DROPPED	= "droppped";

    @Override
    public void storeInBundle( Bundle bundle ) {
        super.storeInBundle( bundle );
    }

    @Override
    public void restoreFromBundle( Bundle bundle ) {
        super.restoreFromBundle(bundle);
    }

    private int note;

    @Override
    protected boolean build() {

        //W Mountain west side
       Painter.fill(this, 18, 5, 1, 34, Terrain.MOUNTAIN_W);
        Painter.fill(this, 19, 6, 1, 32, Terrain.MOUNTAIN_W);
        Painter.fill(this, 20, 7, 1, 30, Terrain.MOUNTAIN_W);
        Painter.fill(this, 21, 8, 1, 26, Terrain.MOUNTAIN_W);
        Painter.fill(this, 22, 12, 1, 20, Terrain.MOUNTAIN_W);
        Painter.fill(this, 23, 13, 1, 18, Terrain.MOUNTAIN_W);
        Painter.fill(this, 24, 14, 1, 16, Terrain.MOUNTAIN_W);
        Painter.fill(this, 25, 17, 1, 12, Terrain.MOUNTAIN_W);
        Painter.fill(this, 29, 16, 1, 12, Terrain.MOUNTAIN_W);
        Painter.fill(this, 30, 17, 1, 10, Terrain.MOUNTAIN_W);
        Painter.fill(this, 31, 18, 1, 8, Terrain.MOUNTAIN_W);
        Painter.fill(this, 32, 19, 1, 6, Terrain.MOUNTAIN_W);
        Painter.fill(this, 33, 20, 1, 4, Terrain.MOUNTAIN_W);
        Painter.fill(this, 34, 21, 1, 2, Terrain.MOUNTAIN_W);

        //W Mountain south side
        Painter.fill(this, 18, 39, 32, 1, Terrain.MOUNTAIN_S);
        Painter.fill(this, 19, 38, 31, 1, Terrain.MOUNTAIN_S);
        Painter.fill(this, 20, 37, 30, 1, Terrain.MOUNTAIN_S);
        Painter.fill(this, 21, 33, 29, 1, Terrain.MOUNTAIN_S);
        Painter.fill(this, 22, 32, 28, 1, Terrain.MOUNTAIN_S);
        Painter.fill(this, 23, 30, 27, 1, Terrain.MOUNTAIN_S);
        Painter.fill(this, 24, 29, 26, 1, Terrain.MOUNTAIN_S);
        Painter.fill(this, 25, 28, 25, 1, Terrain.MOUNTAIN_S);
        Painter.fill(this, 29, 27, 21, 1, Terrain.MOUNTAIN_S);
        Painter.fill(this, 30, 26, 20, 1, Terrain.MOUNTAIN_S);
        Painter.fill(this, 31, 25, 19, 1, Terrain.MOUNTAIN_S);
        Painter.fill(this, 32, 24, 18, 1, Terrain.MOUNTAIN_S);
        Painter.fill(this, 33, 23, 17, 1, Terrain.MOUNTAIN_S);
        Painter.fill(this, 34, 22, 16, 1, Terrain.MOUNTAIN_S);

        //W Mountain north side
        Painter.fill(this, 34, 21, 16, 1, Terrain.MOUNTAIN_N);
        Painter.fill(this, 33, 20, 17, 1, Terrain.MOUNTAIN_N);
        Painter.fill(this, 32, 19, 18, 1, Terrain.MOUNTAIN_N);
        Painter.fill(this, 31, 18, 19, 1, Terrain.MOUNTAIN_N);
        Painter.fill(this, 30, 17, 20, 1, Terrain.MOUNTAIN_N);
        Painter.fill(this, 29, 16, 21, 1, Terrain.MOUNTAIN_N);
        Painter.fill(this, 28, 15, 22, 1, Terrain.MOUNTAIN_N);
        Painter.fill(this, 24, 14, 26, 1, Terrain.MOUNTAIN_N);
        Painter.fill(this, 23, 13, 27, 1, Terrain.MOUNTAIN_N);
        Painter.fill(this, 22, 12, 28, 1, Terrain.MOUNTAIN_N);
        Painter.fill(this, 21, 8, 29, 1, Terrain.MOUNTAIN_N);
        Painter.fill(this, 20, 7, 30, 1, Terrain.MOUNTAIN_N);
        Painter.fill(this, 19, 6, 31, 1, Terrain.MOUNTAIN_N);
        Painter.fill(this, 18, 5, 32, 1, Terrain.MOUNTAIN_N);

        //W Mountain corners
        Painter.fill(this, 34, 21, 1, 1, Terrain.MOUNTAIN_CORNER_NW);
        Painter.fill(this, 33, 20, 1, 1, Terrain.MOUNTAIN_CORNER_NW);
        Painter.fill(this, 32, 19, 1, 1, Terrain.MOUNTAIN_CORNER_NW);
        Painter.fill(this, 31, 18, 1, 1, Terrain.MOUNTAIN_CORNER_NW);
        Painter.fill(this, 30, 17, 1, 1, Terrain.MOUNTAIN_CORNER_NW);
        Painter.fill(this, 29, 16, 1, 1, Terrain.MOUNTAIN_CORNER_NW);
        Painter.fill(this, 28, 15, 1, 1, Terrain.MOUNTAIN_CORNER_NW);

        Painter.fill(this, 25, 17, 1, 1, Terrain.MOUNTAIN_CORNER_NW);
        Painter.fill(this, 26, 17, 2, 1, Terrain.MOUNTAIN_N);
        Painter.fill(this, 28, 16, 1, 1, Terrain.MOUNTAIN_W);
        Painter.fill(this, 24, 14, 1, 1, Terrain.MOUNTAIN_CORNER_NW);
        Painter.fill(this, 23, 13, 1, 1, Terrain.MOUNTAIN_CORNER_NW);
        Painter.fill(this, 22, 12, 1, 1, Terrain.MOUNTAIN_CORNER_NW);
        Painter.fill(this, 21, 8, 1, 1, Terrain.MOUNTAIN_CORNER_NW);
        Painter.fill(this, 20, 7, 1, 1, Terrain.MOUNTAIN_CORNER_NW);
        Painter.fill(this, 19, 6, 1, 1, Terrain.MOUNTAIN_CORNER_NW);
        Painter.fill(this, 18, 5, 1, 1, Terrain.MOUNTAIN_CORNER_NW);

        Painter.fill(this, 34, 22, 1, 1, Terrain.MOUNTAIN_CORNER_SW);
        Painter.fill(this, 33, 23, 1, 1, Terrain.MOUNTAIN_CORNER_SW);
        Painter.fill(this, 32, 24, 1, 1, Terrain.MOUNTAIN_CORNER_SW);
        Painter.fill(this, 31, 25, 1, 1, Terrain.MOUNTAIN_CORNER_SW);
        Painter.fill(this, 30, 26, 1, 1, Terrain.MOUNTAIN_CORNER_SW);
        Painter.fill(this, 29, 27, 1, 1, Terrain.MOUNTAIN_CORNER_SW);
        Painter.fill(this, 25, 28, 1, 1, Terrain.MOUNTAIN_CORNER_SW);
        Painter.fill(this, 24, 29, 1, 1, Terrain.MOUNTAIN_CORNER_SW);
        Painter.fill(this, 23, 30, 1, 1, Terrain.MOUNTAIN_CORNER_SW);
        Painter.fill(this, 22, 32, 1, 1, Terrain.MOUNTAIN_CORNER_SW);
        Painter.fill(this, 21, 33, 1, 1, Terrain.MOUNTAIN_CORNER_SW);
        Painter.fill(this, 20, 37, 1, 1, Terrain.MOUNTAIN_CORNER_SW);
        Painter.fill(this, 19, 38, 1, 1, Terrain.MOUNTAIN_CORNER_SW);
        Painter.fill(this, 18, 39, 1, 1, Terrain.MOUNTAIN_CORNER_SW);


        //E Mountain east side
        Painter.fill( this, 12, 12, 1, 22, Terrain.MOUNTAIN_E);
        Painter.fill( this, 11, 13, 1, 20, Terrain.MOUNTAIN_E);
        Painter.fill( this, 10, 14, 1, 18, Terrain.MOUNTAIN_E);
        Painter.fill( this, 9, 15, 1, 16, Terrain.MOUNTAIN_E);
        Painter.fill( this, 8, 16, 1, 14, Terrain.MOUNTAIN_E);
        Painter.fill( this, 7, 17, 1, 12, Terrain.MOUNTAIN_E);
        Painter.fill( this, 6, 18, 1, 10, Terrain.MOUNTAIN_E);
        Painter.fill( this, 5, 19, 1, 8, Terrain.MOUNTAIN_E);
        Painter.fill( this, 4, 20, 1, 6, Terrain.MOUNTAIN_E);
        Painter.fill( this, 3, 21, 1, 4, Terrain.MOUNTAIN_E);

        //E Mountain south side
        Painter.fill( this, 0, 33, 13, 1, Terrain.MOUNTAIN_S);
        Painter.fill( this, 0, 32, 12, 1, Terrain.MOUNTAIN_S);
        Painter.fill( this, 0, 31, 11, 1, Terrain.MOUNTAIN_S);
        Painter.fill( this, 0, 30, 10, 1, Terrain.MOUNTAIN_S);
        Painter.fill( this, 0, 29, 9, 1, Terrain.MOUNTAIN_S);
        Painter.fill( this, 0, 28, 8, 1, Terrain.MOUNTAIN_S);
        Painter.fill( this, 0, 27, 7, 1, Terrain.MOUNTAIN_S);
        Painter.fill( this, 0, 26, 6, 1, Terrain.MOUNTAIN_S);
        Painter.fill( this, 0, 25, 5, 1, Terrain.MOUNTAIN_S);
        Painter.fill( this, 0, 24, 4, 1, Terrain.MOUNTAIN_S);

        //E Mountain west side
        Painter.fill( this, 0, 12, 13, 1, Terrain.MOUNTAIN_N);
        Painter.fill( this, 0, 13, 12, 1, Terrain.MOUNTAIN_N);
        Painter.fill( this, 0, 14, 11, 1, Terrain.MOUNTAIN_N);
        Painter.fill( this, 0, 15, 10, 1, Terrain.MOUNTAIN_N);
        Painter.fill( this, 0, 16, 9, 1, Terrain.MOUNTAIN_N);
        Painter.fill( this, 0, 17, 8, 1, Terrain.MOUNTAIN_N);
        Painter.fill( this, 0, 18, 7, 1, Terrain.MOUNTAIN_N);
        Painter.fill( this, 0, 19, 6, 1, Terrain.MOUNTAIN_N);
        Painter.fill( this, 0, 20, 5, 1, Terrain.MOUNTAIN_N);
        Painter.fill( this, 0, 21, 4, 1, Terrain.MOUNTAIN_N);

        //E Mountain corners
        Painter.fill( this, 12, 12, 1, 1, Terrain.MOUNTAIN_CORNER_NE);
        Painter.fill( this, 11, 13, 1, 1, Terrain.MOUNTAIN_CORNER_NE);
        Painter.fill( this, 10, 14, 1, 1, Terrain.MOUNTAIN_CORNER_NE);
        Painter.fill( this, 9, 15, 1, 1, Terrain.MOUNTAIN_CORNER_NE);
        Painter.fill( this, 8, 16, 1, 1, Terrain.MOUNTAIN_CORNER_NE);
        Painter.fill( this, 7, 17, 1, 1, Terrain.MOUNTAIN_CORNER_NE);
        Painter.fill( this, 6, 18, 1, 1, Terrain.MOUNTAIN_CORNER_NE);
        Painter.fill( this, 5, 19, 1, 1, Terrain.MOUNTAIN_CORNER_NE);
        Painter.fill( this, 4, 20, 1, 1, Terrain.MOUNTAIN_CORNER_NE);
        Painter.fill( this, 3, 21, 1, 1, Terrain.MOUNTAIN_CORNER_NE);

        Painter.fill( this, 12, 33, 1, 1, Terrain.MOUNTAIN_CORNER_SE);
        Painter.fill( this, 11, 32, 1, 1, Terrain.MOUNTAIN_CORNER_SE);
        Painter.fill( this, 10, 31, 1, 1, Terrain.MOUNTAIN_CORNER_SE);
        Painter.fill( this, 9, 30, 1, 1, Terrain.MOUNTAIN_CORNER_SE);
        Painter.fill( this, 8, 29, 1, 1, Terrain.MOUNTAIN_CORNER_SE);
        Painter.fill( this, 7, 28, 1, 1, Terrain.MOUNTAIN_CORNER_SE);
        Painter.fill( this, 6, 27, 1, 1, Terrain.MOUNTAIN_CORNER_SE);
        Painter.fill( this, 5, 26, 1, 1, Terrain.MOUNTAIN_CORNER_SE);
        Painter.fill( this, 4, 25, 1, 1, Terrain.MOUNTAIN_CORNER_SE);
        Painter.fill( this, 3, 24, 1, 1, Terrain.MOUNTAIN_CORNER_SE);

        for (int i=WIDTH + 1; i < LENGTH - WIDTH; i++) {
            if (map[i] == Terrain.EMPTY) {
                int n = 0;
                if (map[i + 1] == Terrain.EMPTY) {
                    n++;
                }
                if (map[i - 1] == Terrain.EMPTY) {
                    n++;
                }
                if (map[i + WIDTH] == Terrain.EMPTY) {
                    n++;
                }
                if (map[i - WIDTH] == Terrain.EMPTY) {
                    n++;
                }
                if (Random.Int( 35 ) <= n) {
                    map[i] = Terrain.PEDESTAL;
                }
            }
        }

       //Barasook's home
        Painter.fill( this, 9, 34, 2, 1, Terrain.RUBBLE);
        Painter.fill( this, 9, 36, 4, 4, Terrain.EMPTY);
        Painter.fill( this, 9, 35, 3, 1, Terrain.EMPTY);
        Painter.fill( this, 11, 34, 3, 1, Terrain.RUBBLE);
        Painter.fill( this, 8, 34, 1, 6, Terrain.RUBBLE);
        Painter.fill( this, 8, 39, 3, 1, Terrain.RUBBLE);
        Painter.fill( this, 12, 39, 3, 1, Terrain.RUBBLE);
        Painter.fill( this, 14, 34, 1, 6, Terrain.RUBBLE);

        Painter.fill( this, 2, 36, 5, 6, Terrain.BARREL);
        Painter.fill( this, 3, 37, 3, 4, Terrain.EMPTY);

        Painter.fill( this, 9, 43, 3, 3, Terrain.WATER);

        //put the burrow entrance in, but make it appear after Barasook dies
        //down = 1760;
        //map[down] = Terrain.EXIT;

        east = 1098;
        map[east] = Terrain.EAST;

        north = 75;
        map[north] = Terrain.NORTH;

        south =  2425;
        map[south] = Terrain.SOUTH;

        note = 2375;
        map[note] = Terrain.EMPTY;

        return true;
    }

    @Override
    protected void decorate() {

    }


    @Override
    protected void createMobs() {
        Barasook.spawn(this);
        for (int i=0; i < 1; i++) {
            Mob mob = Bestiary.mob(Dungeon.depth);
            do {
                mob.pos = 1853;
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
                        Random.IntRange( 50 + 1, 50 + HALL_WIDTH - 2 ) +
                                Random.IntRange( TOP + HALL_HEIGHT + 1, TOP + HALL_HEIGHT ) * WIDTH;
            } while (pos == east || map[pos] == Terrain.SIGN);
            drop( item, pos ).type = Heap.Type.SKELETON;
        }
        drop( new Gold(500), note );
    }

    @Override
    public int randomRespawnCell() {
        return -1;
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

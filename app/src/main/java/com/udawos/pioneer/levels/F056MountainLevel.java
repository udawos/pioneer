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
import com.udawos.pioneer.actors.Actor;
import com.udawos.pioneer.items.Heap;
import com.udawos.pioneer.items.Item;
import com.udawos.pioneer.levels.painters.Painter;
import com.udawos.utils.Bundle;
import com.udawos.utils.Random;

public class F056MountainLevel extends Level {

    {
        color1 = 0x4b6636;
        color2 = 0xf2f2f2;
    }
    



    @Override
    public String tilesTex() { return Assets.TILES_MOUNTAIN;
    }

    @Override
    public String waterTex() {
        return Assets.MOUNTAIN_SCENERY;
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

    @Override
    protected boolean build() {

        //Mountain west side
        Painter.fill(this, 0, 2, 1, 28, Terrain.MOUNTAIN_W);
        Painter.fill(this, 1, 4, 1, 25, Terrain.MOUNTAIN_W);
        Painter.fill(this, 2, 5, 1, 23, Terrain.MOUNTAIN_W);
        Painter.fill(this, 3, 6, 1, 21, Terrain.MOUNTAIN_W);
        Painter.fill(this, 4, 7, 1, 18, Terrain.MOUNTAIN_W);
        Painter.fill(this, 5, 8, 1, 16, Terrain.MOUNTAIN_W);
        Painter.fill(this, 6, 9, 1, 14, Terrain.MOUNTAIN_W);
        Painter.fill(this, 7, 10, 1, 12, Terrain.MOUNTAIN_W);
        Painter.fill(this, 8, 11, 1, 10, Terrain.MOUNTAIN_W);
        Painter.fill(this, 9, 12, 1, 8, Terrain.MOUNTAIN_W);
        //Painter.fill(this, 10, 13, 1, 6, Terrain.MOUNTAIN_W);
        //Painter.fill(this, 11, 14, 1, 4, Terrain.MOUNTAIN_W);
        Painter.fill(this, 12, 15, 1, 2, Terrain.MOUNTAIN_W);

        //Mountain south side

        Painter.fill(this, 12, 17, 38,1, Terrain.MOUNTAIN_S);
        //Painter.fill(this, 10, 18, 30,1, Terrain.MOUNTAIN_S);
        Painter.fill(this, 9, 19, 41,1, Terrain.MOUNTAIN_S);
        Painter.fill(this, 8, 20, 42,1, Terrain.MOUNTAIN_S);
        Painter.fill(this, 7, 21, 43,1, Terrain.MOUNTAIN_S);
        Painter.fill(this, 6, 22, 44,1, Terrain.MOUNTAIN_S);
        Painter.fill(this, 5, 23, 45,1, Terrain.MOUNTAIN_S);
        Painter.fill(this, 4, 24, 46,1, Terrain.MOUNTAIN_S);
        Painter.fill(this, 3, 26, 47,1, Terrain.MOUNTAIN_S);
        Painter.fill(this, 2, 27, 48,1, Terrain.MOUNTAIN_S);
        Painter.fill(this, 1, 28, 49,1, Terrain.MOUNTAIN_S);
        Painter.fill(this, 0, 29, 50,1, Terrain.MOUNTAIN_S);

        //Mountain north side

        Painter.fill(this, 12, 14, 38, 1, Terrain.MOUNTAIN_N);
        //Painter.fill(this, 10, 13, 39, 1, Terrain.MOUNTAIN_N);
        Painter.fill(this, 9, 12, 41, 1, Terrain.MOUNTAIN_N);
        Painter.fill(this, 8, 11, 42, 1, Terrain.MOUNTAIN_N);
        Painter.fill(this, 7, 10, 43, 1, Terrain.MOUNTAIN_N);
        Painter.fill(this, 6, 9,  44, 1, Terrain.MOUNTAIN_N);
        Painter.fill(this, 5, 8, 45, 1, Terrain.MOUNTAIN_N);
        Painter.fill(this, 4, 7, 46, 1, Terrain.MOUNTAIN_N);
        Painter.fill(this, 3, 6, 47, 1, Terrain.MOUNTAIN_N);
        Painter.fill(this, 2, 5, 48, 1, Terrain.MOUNTAIN_N);
        Painter.fill(this, 1, 4, 49, 1, Terrain.MOUNTAIN_N);
        Painter.fill(this, 0, 3, 50, 1, Terrain.MOUNTAIN_N);






        west = 1701;
        map[west] = Terrain.WEST;

        east = 1548;
        map[east] = Terrain.EAST;

        north = 75;
        map[north] = Terrain.NORTH;

        south =  2425;
        map[south] = Terrain.SOUTH;

        return true;
    }

    @Override
    protected void decorate() {

        for (int i = WIDTH; i < LENGTH - WIDTH; i++) {
            if (map[i] == Terrain.MOUNTAIN_W && map[i + 50] == Terrain.MOUNTAIN_S) {
                map[i + 50] = Terrain.MOUNTAIN_CORNER_SW;
            }
        }

        for (int i = WIDTH; i < LENGTH - WIDTH; i++) {
            if (map[i] == Terrain.MOUNTAIN_N && map[i + 1] == Terrain.MOUNTAIN_E && map[i + 51] == Terrain.MOUNTAIN_E) {
                map[i+1] = Terrain.MOUNTAIN_CORNER_NE;
            }
        }

        for (int i = WIDTH; i < LENGTH - WIDTH; i++) {
            if (map[i] == Terrain.MOUNTAIN_N && map[i + 50] == Terrain.MOUNTAIN_W) {
                map[i] = Terrain.MOUNTAIN_CORNER_NW;
            }
        }


        for (int i = WIDTH; i < LENGTH - WIDTH; i++) {
            if (map[i] == Terrain.MOUNTAIN_S && map[i + 1] == Terrain.MOUNTAIN_W) {
                map[i + 1] = Terrain.MOUNTAIN_ELBOW_NE;
            }
        }

        for (int i = WIDTH; i < LENGTH - WIDTH; i++) {
            if (map[i] == Terrain.MOUNTAIN_S && map[i + 50] == Terrain.MOUNTAIN_E) {
                map[i] = Terrain.MOUNTAIN_ELBOW_NW;
            }
        }


        for (int i = WIDTH; i < LENGTH - WIDTH; i++) {
            if (map[i - 1] == Terrain.MOUNTAIN_N && map[i - 50] == Terrain.MOUNTAIN_W && map[i] == Terrain.EMPTY) {
                map[i] = Terrain.MOUNTAIN_ELBOW_SE;
            }
        }

        for (int i = WIDTH; i < LENGTH - WIDTH; i++) {
            if (map[i] == Terrain.MOUNTAIN_E && map[i - 50] == Terrain.MOUNTAIN_E && map[i+1] == Terrain.MOUNTAIN_N) {
                map[i] = Terrain.MOUNTAIN_ELBOW_SW;
            }
        }

        for (int i = WIDTH; i < LENGTH - WIDTH; i++) {
            if (map[i] == Terrain.MOUNTAIN_E && map[i - 1] == Terrain.MOUNTAIN_S && map[i - 50] == Terrain.MOUNTAIN_E) {
                map[i] = Terrain.MOUNTAIN_CORNER_SE;
            }
        }

        for (int i=WIDTH + 1; i < LENGTH - WIDTH; i++) {
            if (map[i] == Terrain.EMPTY) {
                int n = 0;
                if (map[i + 1] == Terrain.PEDESTAL) {
                    n++;
                }
                if (map[i - 1] == Terrain.PEDESTAL) {
                    n++;
                }
                if (map[i + WIDTH] == Terrain.PEDESTAL) {
                    n++;
                }
                if (map[i - WIDTH] == Terrain.PEDESTAL) {
                    n++;
                }
                if (Random.Int( 20 ) <= n) {
                    map[i] = Terrain.PEDESTAL;
                }
            }
        }
    }


    @Override
    protected void createMobs() {

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
                        Random.IntRange(1, 100 ) +
                                Random.IntRange( 10,30 ) * WIDTH;
            } while (pos == east || map[pos] == Terrain.SIGN);
            drop( item, pos ).type = Heap.Type.SKELETON;
        }
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

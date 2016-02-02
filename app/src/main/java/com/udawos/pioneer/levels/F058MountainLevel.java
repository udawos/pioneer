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

public class F058MountainLevel extends Level {

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
        //east side
        Painter.fill(this, 48, 2, 1, 44, Terrain.MOUNTAIN_E);
        Painter.fill(this, 47, 3, 1, 42, Terrain.MOUNTAIN_E);
        Painter.fill(this, 46, 4, 1, 40, Terrain.MOUNTAIN_E);
        Painter.fill(this, 45, 5, 1, 38, Terrain.MOUNTAIN_E);
        Painter.fill(this, 44, 6, 1, 36, Terrain.MOUNTAIN_E);
        Painter.fill(this, 43, 7, 1, 34, Terrain.MOUNTAIN_E);
        Painter.fill(this, 42, 8, 1, 32, Terrain.MOUNTAIN_E);
        Painter.fill(this, 41, 9, 1, 30, Terrain.MOUNTAIN_E);
        Painter.fill(this, 40, 10, 1, 28, Terrain.MOUNTAIN_E);
        Painter.fill(this, 39, 11, 1, 26, Terrain.MOUNTAIN_E);

        Painter.fill(this, 8, 21, 1, 14, Terrain.MOUNTAIN_E);
        Painter.fill(this, 7, 22, 1, 12, Terrain.MOUNTAIN_E);
        Painter.fill(this, 6, 23, 1, 10, Terrain.MOUNTAIN_E);
        Painter.fill(this, 5, 24, 1, 8, Terrain.MOUNTAIN_E);
        Painter.fill(this, 4, 25, 1, 6, Terrain.MOUNTAIN_E);
        Painter.fill(this, 3, 26, 1, 4, Terrain.MOUNTAIN_E);

        //south side
        Painter.fill(this, 1, 29, 3, 1, Terrain.MOUNTAIN_S);
        Painter.fill(this, 0, 30, 5, 1, Terrain.MOUNTAIN_S);
        Painter.fill(this, 0, 31, 6, 1, Terrain.MOUNTAIN_S);
        Painter.fill(this, 0, 32, 7, 1, Terrain.MOUNTAIN_S);
        Painter.fill(this, 0, 33, 8, 1, Terrain.MOUNTAIN_S);
        Painter.fill(this, 0, 34, 9, 1, Terrain.MOUNTAIN_S);

        Painter.fill(this, 0, 36, 40, 1, Terrain.MOUNTAIN_S);
        Painter.fill(this, 0, 37, 41, 1, Terrain.MOUNTAIN_S);
        Painter.fill(this, 0, 38, 42, 1, Terrain.MOUNTAIN_S);
        Painter.fill(this, 0, 39, 43, 1, Terrain.MOUNTAIN_S);
        Painter.fill(this, 0, 40, 44, 1, Terrain.MOUNTAIN_S);
        Painter.fill(this, 0, 41, 45, 1, Terrain.MOUNTAIN_S);
        Painter.fill(this, 0, 42, 46, 1, Terrain.MOUNTAIN_S);
        Painter.fill(this, 0, 43, 47, 1, Terrain.MOUNTAIN_S);
        Painter.fill(this, 0, 44, 48, 1, Terrain.MOUNTAIN_S);
        Painter.fill(this, 0, 45, 49, 1, Terrain.MOUNTAIN_S);


        //north side
        Painter.fill(this, 1, 26, 3, 1, Terrain.MOUNTAIN_N);
        Painter.fill(this, 0, 25, 5, 1, Terrain.MOUNTAIN_N);
        Painter.fill(this, 0, 24, 6, 1, Terrain.MOUNTAIN_N);
        Painter.fill(this, 0, 23, 7, 1, Terrain.MOUNTAIN_N);
        Painter.fill(this, 0, 22, 8, 1, Terrain.MOUNTAIN_N);
        Painter.fill(this, 0, 21, 9, 1, Terrain.MOUNTAIN_N);

        Painter.fill(this, 0, 12, 39, 1, Terrain.MOUNTAIN_N);

        Painter.fill(this, 0, 10, 41, 1, Terrain.MOUNTAIN_N);
        Painter.fill(this, 0, 9, 42, 1, Terrain.MOUNTAIN_N);
        Painter.fill(this, 0, 8, 43, 1, Terrain.MOUNTAIN_N);
        Painter.fill(this, 0, 7, 44, 1, Terrain.MOUNTAIN_N);
        Painter.fill(this, 0, 6, 45, 1, Terrain.MOUNTAIN_N);
        Painter.fill(this, 0, 5, 46, 1, Terrain.MOUNTAIN_N);
        Painter.fill(this, 0, 4, 47, 1, Terrain.MOUNTAIN_N);
        Painter.fill(this, 0, 3, 48, 1, Terrain.MOUNTAIN_N);
        Painter.fill(this, 0, 2, 49, 1, Terrain.MOUNTAIN_N);

        //west side
        Painter.fill(this, 0, 25, 1, 6, Terrain.MOUNTAIN_W);
        Painter.fill(this, 1, 26, 1, 4, Terrain.MOUNTAIN_W);

        //internal slopes
        Painter.fill(this, 8, 16, 1, 1, Terrain.MOUNTAIN_ELBOW_NW);
        Painter.fill(this, 8, 17, 1, 2, Terrain.MOUNTAIN_E);
        Painter.fill(this, 8, 18, 1, 1, Terrain.MOUNTAIN_ELBOW_SW);
        Painter.fill(this, 8, 19, 10, 1, Terrain.MOUNTAIN_N);
        Painter.fill(this, 17,19, 1, 1, Terrain.MOUNTAIN_CORNER_NE);
        Painter.fill(this, 17, 20, 1, 12, Terrain.MOUNTAIN_E);
        Painter.fill(this, 17, 32, 1, 1, Terrain.MOUNTAIN_ELBOW_SW);
        Painter.fill(this, 17, 33, 4, 1, Terrain.MOUNTAIN_N);
        Painter.fill(this, 21, 33, 1, 1, Terrain.MOUNTAIN_ELBOW_SE);
        Painter.fill(this, 21, 20, 1, 13, Terrain.MOUNTAIN_W);
        Painter.fill(this, 21, 19, 1, 1, Terrain.MOUNTAIN_CORNER_NW);
        Painter.fill(this, 22, 19, 6, 1, Terrain.MOUNTAIN_N);
        Painter.fill(this, 28, 19, 1, 1, Terrain.MOUNTAIN_CORNER_NE);
        Painter.fill(this, 28, 20, 1, 13, Terrain.MOUNTAIN_E);
        Painter.fill(this, 28, 32, 1, 1, Terrain.MOUNTAIN_ELBOW_SW);
        Painter.fill(this, 28, 33, 2, 1, Terrain.MOUNTAIN_N);
        Painter.fill(this, 30, 33, 1, 1, Terrain.MOUNTAIN_ELBOW_SE);
        Painter.fill(this, 30, 20, 1, 13, Terrain.MOUNTAIN_W);
        Painter.fill(this, 30, 19, 1, 1, Terrain.MOUNTAIN_CORNER_NW);
        Painter.fill(this, 31, 19, 6, 1, Terrain.MOUNTAIN_N);
        Painter.fill(this, 37, 18, 1, 1, Terrain.MOUNTAIN_ELBOW_SE);
        Painter.fill(this, 37, 17, 1, 2, Terrain.MOUNTAIN_W);
        Painter.fill(this, 37, 16, 1, 1, Terrain.MOUNTAIN_ELBOW_NE);
        Painter.fill(this, 9, 16, 28, 1, Terrain.MOUNTAIN_S);


        //Corners
        Painter.fill(this, 48, 45, 1, 1, Terrain.MOUNTAIN_CORNER_SE);
        Painter.fill(this, 47, 44, 1, 1, Terrain.MOUNTAIN_CORNER_SE);
        Painter.fill(this, 46, 43, 1, 1, Terrain.MOUNTAIN_CORNER_SE);
        Painter.fill(this, 45, 42, 1, 1, Terrain.MOUNTAIN_CORNER_SE);
        Painter.fill(this, 44, 41, 1, 1, Terrain.MOUNTAIN_CORNER_SE);
        Painter.fill(this, 43, 40, 1, 1, Terrain.MOUNTAIN_CORNER_SE);
        Painter.fill(this, 42, 39, 1, 1, Terrain.MOUNTAIN_CORNER_SE);
        Painter.fill(this, 41, 38, 1, 1, Terrain.MOUNTAIN_CORNER_SE);
        Painter.fill(this, 40, 37, 1, 1, Terrain.MOUNTAIN_CORNER_SE);
        Painter.fill(this, 39, 36, 1, 1, Terrain.MOUNTAIN_CORNER_SE);

        Painter.fill(this, 3, 29, 1, 1, Terrain.MOUNTAIN_CORNER_SE);
        Painter.fill(this, 4, 30, 1, 1, Terrain.MOUNTAIN_CORNER_SE);
        Painter.fill(this, 5, 31, 1, 1, Terrain.MOUNTAIN_CORNER_SE);
        Painter.fill(this, 6, 32, 1, 1, Terrain.MOUNTAIN_CORNER_SE);
        Painter.fill(this, 7, 33, 1, 1, Terrain.MOUNTAIN_CORNER_SE);
        Painter.fill(this, 8, 34, 1, 1, Terrain.MOUNTAIN_CORNER_SE);

        Painter.fill(this, 4, 25, 1, 1, Terrain.MOUNTAIN_CORNER_NE);
        Painter.fill(this, 5, 24, 1, 1, Terrain.MOUNTAIN_CORNER_NE);
        Painter.fill(this, 6, 23, 1, 1, Terrain.MOUNTAIN_CORNER_NE);
        Painter.fill(this, 7, 22, 1, 1, Terrain.MOUNTAIN_CORNER_NE);
        Painter.fill(this, 8, 21, 1, 1, Terrain.MOUNTAIN_CORNER_NE);

        Painter.fill(this, 39, 11, 1, 1, Terrain.MOUNTAIN_CORNER_NE);
        Painter.fill(this, 40, 10, 1, 1, Terrain.MOUNTAIN_CORNER_NE);
        Painter.fill(this, 41, 9, 1, 1, Terrain.MOUNTAIN_CORNER_NE);
        Painter.fill(this, 42, 8, 1, 1, Terrain.MOUNTAIN_CORNER_NE);
        Painter.fill(this, 43, 7, 1, 1, Terrain.MOUNTAIN_CORNER_NE);
        Painter.fill(this, 44, 6, 1, 1, Terrain.MOUNTAIN_CORNER_NE);
        Painter.fill(this, 45, 5, 1, 1, Terrain.MOUNTAIN_CORNER_NE);
        Painter.fill(this, 46, 4, 1, 1, Terrain.MOUNTAIN_CORNER_NE);
        Painter.fill(this, 47, 3, 1, 1, Terrain.MOUNTAIN_CORNER_NE);
        Painter.fill(this, 48, 2, 1, 1, Terrain.MOUNTAIN_CORNER_NE);

        Painter.fill(this, 0, 24, 1, 1, Terrain.MOUNTAIN_CORNER_NW);
        Painter.fill(this, 0, 31, 1, 1, Terrain.MOUNTAIN_CORNER_SW);
        Painter.fill(this, 1, 30, 1, 1, Terrain.MOUNTAIN_CORNER_SW);



        //Summit
        Painter.fill(this, 2, 26, 2, 4, Terrain.HILL);



        west = 2301;
        map[west] = Terrain.WEST;

        east = 2348;
        map[east] = Terrain.EAST;

        north = 75;
        map[north] = Terrain.NORTH;

        south = 2425;
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
        Painter.fill(this, 0, 11, 40, 1, Terrain.EMPTY);

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
                if (Random.Int( 65 ) <= n) {
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
                        Random.IntRange( 50 + 1, 50 + HALL_WIDTH - 2 ) +
                                Random.IntRange( TOP + HALL_HEIGHT + 1, TOP + HALL_HEIGHT ) * WIDTH;
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
                return "The summit.";
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

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
import com.udawos.utils.Random;

public class D034GrassLevel extends Level {
    //Burnt house located here

    {
        color1 = 0x4b6636;
        color2 = 0xf2f2f2;
        viewDistance = 16;
    }

    //Major contour lines
    private static final int START_XA   = 26;

    private static final int START_YA   = 0;
    private static final int SOUTH_1A   = 50;


    private static final int START_XB   = 19;
    private static final int EAST_1B    = 2;
    private static final int EAST_2B    = 4;

    private static final int START_YB   = 0;
    private static final int SOUTH_1B   = 13;
    private static final int SOUTH_2B   = 25;
    private static final int SOUTH_3B   = 12;

    private static final int START_XC   = 35;
    private static final int EAST_1C    = 2;
    private static final int EAST_2C    = 2;

    private static final int START_YC   = 0;
    private static final int SOUTH_1C   = 10;
    private static final int SOUTH_2C   = 23;
    private static final int SOUTH_3C   = 17;

    private static final int START_XD   = 38;
    private static final int EAST_1D    = 3;
    private static final int EAST_2D    = 3;

    private static final int START_YD   = 0;
    private static final int SOUTH_1D   = 10;
    private static final int SOUTH_2D   = 23;
    private static final int SOUTH_3D   = 17;





    @Override
    public String tilesTex() { return Assets.TILES_VILLAGE;
    }

    @Override
    public String waterTex() {
        return Assets.WATER_CITY;
    }

    protected boolean[] water() {
        return Patch.generate( feeling == Feeling.CHASM ? 0.60f : 0.45f, 6 );
    }


    @Override
    protected boolean build() {


        //Contour 1
        Painter.fill(this, START_XA, START_YA, 1, SOUTH_1A, Terrain.MOUNTAIN_W);


        //Contour 2
        Painter.fill(this, START_XB, START_YB, 1, SOUTH_1B, Terrain.MOUNTAIN_W);
        Painter.fill(this, START_XB, START_YB+SOUTH_1B, EAST_1B, 1, Terrain.MOUNTAIN_S);
        Painter.fill(this, START_XB, START_YB+SOUTH_1B, 1, 1, Terrain.MOUNTAIN_CORNER_SW);
        Painter.fill(this, START_XB+EAST_1B, START_YB+SOUTH_1B, 1, SOUTH_2B, Terrain.MOUNTAIN_W);
        Painter.fill(this, START_XB+EAST_1B, START_YB+SOUTH_1B, 1, 1, Terrain.MOUNTAIN_ELBOW_NE);
        Painter.fill(this, START_XB+EAST_1B, START_YB+SOUTH_1B+SOUTH_2B, EAST_2B, 1, Terrain.MOUNTAIN_S);
        Painter.fill(this, START_XB+EAST_1B+EAST_2B, START_YB+SOUTH_1B+SOUTH_2B, 1, SOUTH_3B, Terrain.MOUNTAIN_W);
        Painter.fill(this, START_XB+EAST_1B, START_YB+SOUTH_1B+SOUTH_2B, 1, 1, Terrain.MOUNTAIN_CORNER_SW);
        Painter.fill(this, START_XB+EAST_1B+EAST_2B, START_YB+SOUTH_1B+SOUTH_2B, 1, 1, Terrain.MOUNTAIN_ELBOW_NE);


        //Contour 3
        Painter.fill(this, START_XC, START_YC, 1, SOUTH_1C, Terrain.MOUNTAIN_W);
        Painter.fill(this, START_XC, START_YC+SOUTH_1C, EAST_1C, 1, Terrain.MOUNTAIN_S);
        Painter.fill(this, START_XC, START_YC+SOUTH_1C, 1, 1, Terrain.MOUNTAIN_CORNER_SW);
        Painter.fill(this, START_XC+EAST_1C, START_YC+SOUTH_1C, 1, SOUTH_2C, Terrain.MOUNTAIN_W);
        Painter.fill(this, START_XC+EAST_1C, START_YC+SOUTH_1C, 1, 1, Terrain.MOUNTAIN_ELBOW_NE);
        Painter.fill(this, START_XC+EAST_1C, START_YC+SOUTH_1C+SOUTH_2C, EAST_2C, 1, Terrain.MOUNTAIN_S);
        Painter.fill(this, START_XC+EAST_1C+EAST_2C, START_YC+SOUTH_1C+SOUTH_2C, 1, SOUTH_3C, Terrain.MOUNTAIN_W);
        Painter.fill(this, START_XC+EAST_1C, START_YC+SOUTH_1C+SOUTH_2C, 1, 1, Terrain.MOUNTAIN_CORNER_SW);
        Painter.fill(this, START_XC+EAST_1C+EAST_2C, START_YC+SOUTH_1C+SOUTH_2C, 1, 1, Terrain.MOUNTAIN_ELBOW_NE);

        //Contour 4
        Painter.fill(this, START_XD, START_YD, 1, SOUTH_1D, Terrain.MOUNTAIN_W);
        Painter.fill(this, START_XD, START_YD+SOUTH_1D, EAST_1D, 1, Terrain.MOUNTAIN_S);
        Painter.fill(this, START_XD, START_YD+SOUTH_1D, 1, 1, Terrain.MOUNTAIN_CORNER_SW);
        Painter.fill(this, START_XD+EAST_1D, START_YD+SOUTH_1D, 1, SOUTH_2D, Terrain.MOUNTAIN_W);
        Painter.fill(this, START_XD+EAST_1D, START_YD+SOUTH_1D, 1, 1, Terrain.MOUNTAIN_ELBOW_NE);
        Painter.fill(this, START_XD+EAST_1D, START_YD+SOUTH_1D+SOUTH_2D, EAST_2D, 1, Terrain.MOUNTAIN_S);
        Painter.fill(this, START_XD+EAST_1D+EAST_2D, START_YD+SOUTH_1D+SOUTH_2D, 1, SOUTH_3D, Terrain.MOUNTAIN_W);
        Painter.fill(this, START_XD+EAST_1D, START_YD+SOUTH_1D+SOUTH_2D, 1, 1, Terrain.MOUNTAIN_CORNER_SW);
        Painter.fill(this, START_XD+EAST_1D+EAST_2D, START_YD+SOUTH_1D+SOUTH_2D, 1, 1, Terrain.MOUNTAIN_ELBOW_NE);


        //house exterior
        Painter.fill(this, 2, 24, 18, 13, Terrain.RUINED_WALL);
        //entrance exterior
        Painter.fill( this, 12, 36, 5, 6, Terrain.RUINED_WALL);

        //bedroom
        Painter.fill( this, 3, 25, 5, 4, Terrain.EMBERS);
        //bedroom entrance
        Painter.fill( this, 5, 29, 2, 1, Terrain.EMBERS);

        //den
        Painter.fill( this, 3, 30, 5, 5, Terrain.EMBERS);
        //den entrance
        Painter.fill( this, 8, 32, 1, 3, Terrain.EMBERS);

        //kitchen
        Painter.fill( this, 9, 25, 10, 6, Terrain.EMBERS);
        //kitchen entrance
        Painter.fill( this, 9, 31, 2, 1, Terrain.EMBERS);

        //foyer
        Painter.fill( this, 9, 31, 5, 4, Terrain.EMBERS);
        //foyer entrance
        Painter.fill( this, 10, 31, 1, 1, Terrain.EMBERS);

        //closet
        Painter.fill( this, 15, 32, 4, 3, Terrain.EMBERS);
        //closet entrance


        //entrance
        Painter.fill( this, 13, 35, 3, 6, Terrain.EMBERS);
        //entrance entrance (haha)
        Painter.fill( this, 14, 41, 1, 1, Terrain.EMBERS);

        down = 1403;
        map[down] = Terrain.EXIT;

        west = 1151;
        map[west] = Terrain.WEST;

        north = 75;
        map[north] = Terrain.NORTH;

        east = 1098;
        map[east] = Terrain.EAST;

        south =  2423;
        map[south] = Terrain.SOUTH;

        return true;
    }

    @Override
    protected void decorate() {
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
    }


    @Override
    public int nMobs() {
        return 1;
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
    public String tileName( int tile ) {
        switch (tile) {
            case Terrain.WATER:
                return "Suspiciously colored water";
            case Terrain.HIGH_GRASS:
                return "High grass";
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
                return "A staircase leads down.";
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

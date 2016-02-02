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

public class B015MountainLevel extends Level {

    {
        color1 = 0x4b6636;
        color2 = 0xf2f2f2;
        viewDistance = 16;
    }

    //Major contour lines
    private static final int START_XA   = 0;
    private static final int EAST_1A    = 20;
    private static final int EAST_2A    = 6;
    private static final int EAST_3A    = 13;
    private static final int END_XA     = 49-(EAST_1A+EAST_2A+EAST_3A);

    private static final int START_YA   = 25;
    private static final int NORTH_1A   = 3;
    private static final int SOUTH_1A   = 3;
    private static final int SOUTH_2A   = 21;


    private static final int START_XB   = 0;
    private static final int EAST_1B    = 19;
    private static final int EAST_2B    = 8;
    private static final int EAST_3B    = 14;
    private static final int END_XB     = 49-(EAST_1B+EAST_2B+EAST_3B);

    private static final int START_YB   = 23;
    private static final int NORTH_1B   = 4;
    private static final int SOUTH_1B   = 4;
    private static final int SOUTH_2B   = 21;

    private static final int START_XC   = 31;
    private static final int EAST_1C    = 12;
    private static final int EAST_2C    = 6;


    private static final int START_YC   = 0;
    private static final int SOUTH_1C   = 21;
    private static final int SOUTH_2C   = 21;


    //SW contours
    private static final int NW_CORNER_X	= 0;
    private static final int NW_CORNER_Y	= 30;
    private static final int SQUARE_WIDTH	= 35;
    private static final int SQUARE_HEIGHT	= 19;
    private static final int NE_CORNER_X	= NW_CORNER_X + SQUARE_WIDTH;

    private static final int NW_CORNER_X2	= 5;
    private static final int NW_CORNER_Y2	= 35;
    private static final int SQUARE_WIDTH2	= 20;
    private static final int SQUARE_HEIGHT2	= 14;
    private static final int NE_CORNER_X2	= NW_CORNER_X2 + SQUARE_WIDTH2;

    private static final int NW_CORNER_X3	= 7;
    private static final int NW_CORNER_Y3	= 38;
    private static final int SQUARE_WIDTH3	= 14;
    private static final int SQUARE_HEIGHT3	= 11;
    private static final int NE_CORNER_X3	= NW_CORNER_X3 + SQUARE_WIDTH3;

    private static final int NW_CORNER_X4	= 10;
    private static final int NW_CORNER_Y4	= 42;
    private static final int SQUARE_WIDTH4	= 10;
    private static final int SQUARE_HEIGHT4	= 7;
    private static final int NE_CORNER_X4	= NW_CORNER_X4 + SQUARE_WIDTH4;

    private static final int NW_CORNER_X5	= 12;
    private static final int NW_CORNER_Y5	= 44;
    private static final int SQUARE_WIDTH5	= 7;
    private static final int SQUARE_HEIGHT5	= 5;
    private static final int NE_CORNER_X5	= NW_CORNER_X5 + SQUARE_WIDTH5;

    private static final int NW_CORNER_X6	= 14;
    private static final int NW_CORNER_Y6	= 46;
    private static final int SQUARE_WIDTH6	= 4;
    private static final int SQUARE_HEIGHT6	= 3;
    private static final int NE_CORNER_X6	= NW_CORNER_X6 + SQUARE_WIDTH6;


    @Override
    public String tilesTex() { return Assets.TILES_MOUNTAIN;
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
        Painter.fill(this, START_XA, START_YA, EAST_1A, 1, Terrain.MOUNTAIN_N);
        Painter.fill(this, EAST_1A, START_YA-NORTH_1A, 1, NORTH_1A, Terrain.MOUNTAIN_W);
        Painter.fill(this, EAST_1A, START_YA, 1,1, Terrain.MOUNTAIN_ELBOW_SE);
        Painter.fill(this, EAST_1A, START_YA-NORTH_1A, EAST_2A, 1, Terrain.MOUNTAIN_N);
        Painter.fill(this, EAST_1A, START_YA-NORTH_1A, 1, 1, Terrain.MOUNTAIN_CORNER_NW);
        Painter.fill(this, EAST_1A+EAST_2A, START_YA-NORTH_1A, 1, SOUTH_1A, Terrain.MOUNTAIN_E);
        Painter.fill(this, EAST_1A+EAST_2A, START_YA-NORTH_1A, 1, 1, Terrain.MOUNTAIN_CORNER_NE);
        Painter.fill(this, EAST_1A+EAST_2A, START_YA-NORTH_1A+SOUTH_1A, EAST_3A, 1, Terrain.MOUNTAIN_N);
        Painter.fill(this, EAST_1A+EAST_2A, START_YA-NORTH_1A+SOUTH_1A-1, 1, 1, Terrain.MOUNTAIN_ELBOW_SW);
        Painter.fill(this, EAST_1A+EAST_2A+EAST_3A, START_YA-NORTH_1A+SOUTH_1A, 1, SOUTH_2A, Terrain.MOUNTAIN_E);
        Painter.fill(this, EAST_1A+EAST_2A+EAST_3A, START_YA-NORTH_1A+SOUTH_1A, 1, 1, Terrain.MOUNTAIN_CORNER_NE);
        Painter.fill(this, EAST_1A+EAST_2A+EAST_3A, START_YA-NORTH_1A+SOUTH_1A+SOUTH_2A, END_XA, 1, Terrain.MOUNTAIN_N);
        Painter.fill(this, EAST_1A+EAST_2A+EAST_3A, START_YA-NORTH_1A+SOUTH_1A+SOUTH_2A-1, 1, 1, Terrain.MOUNTAIN_ELBOW_SW);


        //Contour 2
        Painter.fill(this, START_XB, START_YB, EAST_1B, 1, Terrain.MOUNTAIN_N);
        Painter.fill(this, EAST_1B, START_YB-NORTH_1B, 1, NORTH_1B, Terrain.MOUNTAIN_W);
        Painter.fill(this, EAST_1B, START_YB-NORTH_1B+4, 1, 1, Terrain.MOUNTAIN_ELBOW_SE);
        Painter.fill(this, EAST_1B, START_YB-NORTH_1B, EAST_2B, 1, Terrain.MOUNTAIN_N);
        Painter.fill(this, EAST_1B, START_YB-NORTH_1B, 1, 1, Terrain.MOUNTAIN_CORNER_NW);
        Painter.fill(this, EAST_1B+EAST_2B, START_YB-NORTH_1B, 1, SOUTH_1B, Terrain.MOUNTAIN_E);
        Painter.fill(this, EAST_1B+EAST_2B, START_YB-NORTH_1B, 1, 1, Terrain.MOUNTAIN_CORNER_NE);
        Painter.fill(this, EAST_1B+EAST_2B, START_YB-NORTH_1B+SOUTH_1B, EAST_3B, 1, Terrain.MOUNTAIN_N);
        Painter.fill(this, EAST_1B+EAST_2B, START_YB-NORTH_1B+SOUTH_1B-1, 1, 1, Terrain.MOUNTAIN_ELBOW_SW);
        Painter.fill(this, EAST_1B+EAST_2B+EAST_3B, START_YB-NORTH_1B+SOUTH_1B, 1, SOUTH_2B, Terrain.MOUNTAIN_E);
        Painter.fill(this, EAST_1B+EAST_2B+EAST_3B, START_YB-NORTH_1B+SOUTH_1B, 1, 1, Terrain.MOUNTAIN_CORNER_NE);
        Painter.fill(this, EAST_1B+EAST_2B+EAST_3B, START_YB-NORTH_1B+SOUTH_1B+SOUTH_2B, END_XB, 1, Terrain.MOUNTAIN_N);
        Painter.fill(this, EAST_1B+EAST_2B+EAST_3B, START_YB-NORTH_1B+SOUTH_1B+SOUTH_2B-1, 1, 1, Terrain.MOUNTAIN_ELBOW_SW);


        //Contour 3
        Painter.fill(this, START_XC, START_YC, 1, SOUTH_1C, Terrain.MOUNTAIN_E);
        Painter.fill(this, START_XC, START_YC+SOUTH_1C, EAST_1C, 1, Terrain.MOUNTAIN_N);
        Painter.fill(this, START_XC, START_YC+SOUTH_1C-1, 1, 1, Terrain.MOUNTAIN_ELBOW_SW);
        Painter.fill(this, START_XC+EAST_1C, START_YC+SOUTH_1C, 1, SOUTH_2C, Terrain.MOUNTAIN_E);
        Painter.fill(this, START_XC+EAST_1C, START_YC+SOUTH_1C, 1, 1, Terrain.MOUNTAIN_CORNER_NE);
        Painter.fill(this, START_XC+EAST_1C, START_YC+SOUTH_1C+SOUTH_2C, EAST_2C, 1, Terrain.MOUNTAIN_N);
        Painter.fill(this, START_XC+EAST_1C, START_YC+SOUTH_1C+SOUTH_2C-1, 1, 1, Terrain.MOUNTAIN_ELBOW_SW);


        //SW hills
        Painter.fill(this, NW_CORNER_X, NW_CORNER_Y , SQUARE_WIDTH, 1, Terrain.MOUNTAIN_N);
        //Painter.fill(this, NW_CORNER_X-1, NW_CORNER_Y , 1, 1, Terrain.MOUNTAIN_CORNER_NW);
        //Painter.fill(this, NW_CORNER_X-1, NW_CORNER_Y+1 , 1, SQUARE_HEIGHT, Terrain.MOUNTAIN_W);
        //Painter.fill(this, NW_CORNER_X -1, NW_CORNER_Y+SQUARE_HEIGHT , 1, 1, Terrain.MOUNTAIN_CORNER_SW);
        //Painter.fill(this, NW_CORNER_X , NW_CORNER_Y+SQUARE_HEIGHT , SQUARE_WIDTH2, 1, Terrain.MOUNTAIN_S);
        //Painter.fill(this, NE_CORNER_X , NW_CORNER_Y+SQUARE_HEIGHT , 1, 1, Terrain.MOUNTAIN_CORNER_SE);
        Painter.fill(this, NE_CORNER_X, NW_CORNER_Y , 1, SQUARE_HEIGHT, Terrain.MOUNTAIN_E);
        Painter.fill(this, NE_CORNER_X, NW_CORNER_Y, 1, 1, Terrain.MOUNTAIN_CORNER_NE);


        Painter.fill(this, NW_CORNER_X2, NW_CORNER_Y2 , SQUARE_WIDTH2, 1, Terrain.MOUNTAIN_N);
        Painter.fill(this, NW_CORNER_X2-1, NW_CORNER_Y2 , 1, 1, Terrain.MOUNTAIN_CORNER_NW);
        Painter.fill(this, NW_CORNER_X2-1, NW_CORNER_Y2+1 , 1, SQUARE_HEIGHT2, Terrain.MOUNTAIN_W);
        //Painter.fill(this, NW_CORNER_X2 -1, NW_CORNER_Y2+SQUARE_HEIGHT2 , 1, 1, Terrain.MOUNTAIN_CORNER_SW);
        //Painter.fill(this, NW_CORNER_X2 , NW_CORNER_Y2+SQUARE_HEIGHT2 , SQUARE_WIDTH2, 1, Terrain.MOUNTAIN_S);
        //Painter.fill(this, NE_CORNER_X2 , NW_CORNER_Y2+SQUARE_HEIGHT2 , 1, 1, Terrain.MOUNTAIN_CORNER_SE);
        Painter.fill(this, NE_CORNER_X2, NW_CORNER_Y2 , 1, SQUARE_HEIGHT2, Terrain.MOUNTAIN_E);
        Painter.fill(this, NE_CORNER_X2, NW_CORNER_Y2, 1, 1, Terrain.MOUNTAIN_CORNER_NE);


        Painter.fill(this, NW_CORNER_X3, NW_CORNER_Y3 , SQUARE_WIDTH3, 1, Terrain.MOUNTAIN_N);
        Painter.fill(this, NW_CORNER_X3-1, NW_CORNER_Y3 , 1, 1, Terrain.MOUNTAIN_CORNER_NW);
        Painter.fill(this, NW_CORNER_X3-1, NW_CORNER_Y3+1 , 1, SQUARE_HEIGHT3, Terrain.MOUNTAIN_W);
        //Painter.fill(this, NW_CORNER_X3 -1, NW_CORNER_Y3+SQUARE_HEIGHT3 , 1, 1, Terrain.MOUNTAIN_CORNER_SW);
        //Painter.fill(this, NW_CORNER_X3 , NW_CORNER_Y3+SQUARE_HEIGHT3 , SQUARE_WIDTH3, 1, Terrain.MOUNTAIN_S);
        //Painter.fill(this, NE_CORNER_X3 , NW_CORNER_Y3+SQUARE_HEIGHT3 , 1, 1, Terrain.MOUNTAIN_CORNER_SE);
        Painter.fill(this, NE_CORNER_X3 , NW_CORNER_Y3 , 1, SQUARE_HEIGHT3, Terrain.MOUNTAIN_E);
        Painter.fill(this, NE_CORNER_X3, NW_CORNER_Y3, 1, 1, Terrain.MOUNTAIN_CORNER_NE);

        Painter.fill(this, NW_CORNER_X4, NW_CORNER_Y4 , SQUARE_WIDTH4, 1, Terrain.MOUNTAIN_N);
        Painter.fill(this, NW_CORNER_X4-1, NW_CORNER_Y4 , 1, 1, Terrain.MOUNTAIN_CORNER_NW);
        Painter.fill(this, NW_CORNER_X4-1, NW_CORNER_Y4+1 , 1, SQUARE_HEIGHT4, Terrain.MOUNTAIN_W);
        //Painter.fill(this, NW_CORNER_X4 -1, NW_CORNER_Y4+SQUARE_HEIGHT4 , 1, 1, Terrain.MOUNTAIN_CORNER_SW);
        //Painter.fill(this, NW_CORNER_X4 , NW_CORNER_Y4+SQUARE_HEIGHT4 , SQUARE_WIDTH4, 1, Terrain.MOUNTAIN_S);
        //Painter.fill(this, NE_CORNER_X4 , NW_CORNER_Y4+SQUARE_HEIGHT4 , 1, 1, Terrain.MOUNTAIN_CORNER_SE);
        Painter.fill(this, NE_CORNER_X4, NW_CORNER_Y4 , 1, SQUARE_HEIGHT4, Terrain.MOUNTAIN_E);
        Painter.fill(this, NE_CORNER_X4, NW_CORNER_Y4, 1, 1, Terrain.MOUNTAIN_CORNER_NE);


        Painter.fill(this, NW_CORNER_X5, NW_CORNER_Y5 , SQUARE_WIDTH5, 1, Terrain.MOUNTAIN_N);
        Painter.fill(this, NW_CORNER_X5-1, NW_CORNER_Y5 , 1, 1, Terrain.MOUNTAIN_CORNER_NW);
        Painter.fill(this, NW_CORNER_X5-1, NW_CORNER_Y5+1 , 1, SQUARE_HEIGHT5, Terrain.MOUNTAIN_W);
        //Painter.fill(this, NW_CORNER_X5 -1, NW_CORNER_Y5+SQUARE_HEIGHT5 , 1, 1, Terrain.MOUNTAIN_CORNER_SW);
        //Painter.fill(this, NW_CORNER_X2 , NW_CORNER_Y5+SQUARE_HEIGHT5 , SQUARE_WIDTH5, 1, Terrain.MOUNTAIN_S);
        //Painter.fill(this, NE_CORNER_X5 , NW_CORNER_Y5+SQUARE_HEIGHT5 , 1, 1, Terrain.MOUNTAIN_CORNER_SE);
        Painter.fill(this, NE_CORNER_X5, NW_CORNER_Y5 , 1, SQUARE_HEIGHT5, Terrain.MOUNTAIN_E);
        Painter.fill(this, NE_CORNER_X5, NW_CORNER_Y5, 1, 1, Terrain.MOUNTAIN_CORNER_NE);


        Painter.fill(this, NW_CORNER_X6, NW_CORNER_Y6 , SQUARE_WIDTH6, 1, Terrain.MOUNTAIN_N);
        Painter.fill(this, NW_CORNER_X6-1, NW_CORNER_Y6 , 1, 1, Terrain.MOUNTAIN_CORNER_NW);
        Painter.fill(this, NW_CORNER_X6-1, NW_CORNER_Y6+1 , 1, SQUARE_HEIGHT6, Terrain.MOUNTAIN_W);
        //Painter.fill(this, NW_CORNER_X6 -1, NW_CORNER_Y6+SQUARE_HEIGHT6 , 1, 1, Terrain.MOUNTAIN_CORNER_SW);
        //Painter.fill(this, NW_CORNER_X6 , NW_CORNER_Y6+SQUARE_HEIGHT6 , SQUARE_WIDTH6, 1, Terrain.MOUNTAIN_S);
        //Painter.fill(this, NE_CORNER_X6 , NW_CORNER_Y6+SQUARE_HEIGHT6 , 1, 1, Terrain.MOUNTAIN_CORNER_SE);
        Painter.fill(this, NE_CORNER_X6 , NW_CORNER_Y6 , 1, SQUARE_HEIGHT6, Terrain.MOUNTAIN_E);
        Painter.fill(this, NE_CORNER_X6 , NW_CORNER_Y6, 1, 1, Terrain.MOUNTAIN_CORNER_NE);

        //Summit
        Painter.fill(this, 14, 47, 4, 2, Terrain.HILL);

        west = 1101;
        map[west] = Terrain.WEST;

        north = 76;
        map[north] = Terrain.NORTH;

        east = 1098;
        map[east] = Terrain.EAST;

        south =  2425;
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
                    map[i] = Terrain.RUBBLE;
                }
            }
        }
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
            case Terrain.PEDESTAL:
                return "A tree.";
            case Terrain.RUBBLE:
                return "A rock. Don't make me say what kind.";
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

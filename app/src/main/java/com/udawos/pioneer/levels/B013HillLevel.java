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
import com.udawos.pioneer.actors.mobs.npcs.WildSheep;
import com.udawos.pioneer.items.Heap;
import com.udawos.pioneer.items.Item;
import com.udawos.pioneer.levels.painters.Painter;
import com.udawos.utils.Random;

public class B013HillLevel extends Level {

    {
        color1 = 0x4b6636;
        color2 = 0xf2f2f2;
        viewDistance = 14;
    }

    //Major contour lines
    private static final int START_XA   = 0;
    private static final int EAST_1A    = 22;
    private static final int EAST_2A    = 6;
    private static final int END_XA     = 50-(EAST_1A+EAST_2A);

    private static final int START_YA   = 40;
    private static final int NORTH_1A   = 3;
    private static final int SOUTH_1A   = 3;

    private static final int START_XB   = 0;
    private static final int EAST_1B    = EAST_1A-2;
    private static final int EAST_2B    = EAST_2A+5;
    private static final int END_XB     = 50-(EAST_1B+EAST_2B);

    private static final int START_YB   = 27;
    private static final int NORTH_1B   = NORTH_1A+1;
    private static final int SOUTH_1B   = (SOUTH_1A)*2;

    private static final int START_XC   = 0;
    private static final int EAST_1C    = EAST_1B-2;
    private static final int EAST_2C    = EAST_2B+5;
    private static final int END_XC     = 50-(EAST_1C+EAST_2C);

    private static final int START_YC   = 22;
    private static final int NORTH_1C   = NORTH_1B;
    private static final int SOUTH_1C   = (SOUTH_1B+2);

    private static final int NW_CORNER_X	= 10;
    private static final int NW_CORNER_Y	= 0;
    private static final int SQUARE_WIDTH	= 25;
    private static final int SQUARE_HEIGHT	= 15;
    private static final int NE_CORNER_X	= NW_CORNER_X + SQUARE_WIDTH;

    private static final int NW_CORNER_X2	= 12;
    private static final int NW_CORNER_Y2	= 0;
    private static final int SQUARE_WIDTH2	= 18;
    private static final int SQUARE_HEIGHT2	= 10;
    private static final int NE_CORNER_X2	= NW_CORNER_X2 + SQUARE_WIDTH2;

    private static final int NW_CORNER_X3	= 19;
    private static final int NW_CORNER_Y3	= 0;
    private static final int SQUARE_WIDTH3	= 7;
    private static final int SQUARE_LENGTH3	= 6;
    private static final int NE_CORNER_X3	= NW_CORNER_X3 + SQUARE_WIDTH3;

    @Override
    public String tilesTex() { return Assets.TILES_GRASS;
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
        Painter.fill(this, START_XA, START_YA, EAST_1A, 1, Terrain.MOUNTAIN_S);
        Painter.fill(this, EAST_1A, START_YA-NORTH_1A, 1, NORTH_1A, Terrain.MOUNTAIN_E);
        Painter.fill(this, EAST_1A, START_YA-NORTH_1A, EAST_2A, 1, Terrain.MOUNTAIN_S);
        Painter.fill(this, EAST_1A+EAST_2A, START_YA-NORTH_1A, 1, SOUTH_1A, Terrain.MOUNTAIN_W);
        Painter.fill(this, EAST_1A+EAST_2A, START_YA-NORTH_1A+SOUTH_1A, END_XA, 1, Terrain.MOUNTAIN_S);

        //Contour 2
        Painter.fill(this, START_XB, START_YB, EAST_1B, 1, Terrain.MOUNTAIN_S);
        Painter.fill(this, EAST_1B, START_YB-NORTH_1B, 1, NORTH_1B, Terrain.MOUNTAIN_E);
        Painter.fill(this, EAST_1B, START_YB-NORTH_1B, EAST_2B, 1, Terrain.MOUNTAIN_S);
        Painter.fill(this, EAST_1B+EAST_2B, START_YB-NORTH_1B, 1, SOUTH_1B, Terrain.MOUNTAIN_W);
        Painter.fill(this, EAST_1B+EAST_2B, START_YB-NORTH_1B+SOUTH_1B, END_XB, 1, Terrain.MOUNTAIN_S);

        //Contour 3
        Painter.fill(this, START_XC, START_YC, EAST_1C, 1, Terrain.MOUNTAIN_S);
        Painter.fill(this, EAST_1C, START_YC-NORTH_1C, 1, NORTH_1C, Terrain.MOUNTAIN_E);
        Painter.fill(this, EAST_1C, START_YC-NORTH_1C, EAST_2C, 1, Terrain.MOUNTAIN_S);
        Painter.fill(this, EAST_1C+EAST_2C, START_YC-NORTH_1C, 1, SOUTH_1C, Terrain.MOUNTAIN_W);
        Painter.fill(this, EAST_1C+EAST_2C, START_YC-NORTH_1C+SOUTH_1C, END_XC, 1, Terrain.MOUNTAIN_S);

        //upper hills
        Painter.fill(this, NW_CORNER_X, NW_CORNER_Y , SQUARE_WIDTH, 1, Terrain.MOUNTAIN_N);
        Painter.fill(this, NW_CORNER_X-1, NW_CORNER_Y , 1, 1, Terrain.MOUNTAIN_CORNER_NW);
        Painter.fill(this, NW_CORNER_X-1, NW_CORNER_Y+1 , 1, SQUARE_HEIGHT, Terrain.MOUNTAIN_W);
        Painter.fill(this, NW_CORNER_X -1, NW_CORNER_Y+SQUARE_HEIGHT , 1, 1, Terrain.MOUNTAIN_CORNER_SW);
        Painter.fill(this, NW_CORNER_X , NW_CORNER_Y+SQUARE_HEIGHT , SQUARE_WIDTH, 1, Terrain.MOUNTAIN_S);
        Painter.fill(this, NE_CORNER_X , NW_CORNER_Y+SQUARE_HEIGHT , 1, 1, Terrain.MOUNTAIN_CORNER_SE);
        Painter.fill(this, NE_CORNER_X, NW_CORNER_Y , 1, SQUARE_HEIGHT, Terrain.MOUNTAIN_E);
        Painter.fill(this, NE_CORNER_X, NW_CORNER_Y, 1, 1, Terrain.MOUNTAIN_CORNER_NE);


        Painter.fill(this, NW_CORNER_X2, NW_CORNER_Y2 , SQUARE_WIDTH2, 1, Terrain.MOUNTAIN_N);
        Painter.fill(this, NW_CORNER_X2-1, NW_CORNER_Y2 , 1, 1, Terrain.MOUNTAIN_CORNER_NW);
        Painter.fill(this, NW_CORNER_X2-1, NW_CORNER_Y2+1 , 1, SQUARE_HEIGHT2, Terrain.MOUNTAIN_W);
        Painter.fill(this, NW_CORNER_X2 -1, NW_CORNER_Y2+SQUARE_HEIGHT2 , 1, 1, Terrain.MOUNTAIN_CORNER_SW);
        Painter.fill(this, NW_CORNER_X2 , NW_CORNER_Y2+SQUARE_HEIGHT2 , SQUARE_WIDTH2, 1, Terrain.MOUNTAIN_S);
        Painter.fill(this, NE_CORNER_X2 , NW_CORNER_Y2+SQUARE_HEIGHT2 , 1, 1, Terrain.MOUNTAIN_CORNER_SE);
        Painter.fill(this, NE_CORNER_X2, NW_CORNER_Y2 , 1, SQUARE_HEIGHT2, Terrain.MOUNTAIN_E);
        Painter.fill(this, NE_CORNER_X2, NW_CORNER_Y2, 1, 1, Terrain.MOUNTAIN_CORNER_NE);


        Painter.fill(this, NW_CORNER_X3, NW_CORNER_Y3 , SQUARE_WIDTH3, 1, Terrain.MOUNTAIN_N);
        Painter.fill(this, NW_CORNER_X3-1, NW_CORNER_Y3 , 1, 1, Terrain.MOUNTAIN_CORNER_NW);
        Painter.fill(this, NW_CORNER_X3-1, NW_CORNER_Y3+1 , 1, SQUARE_LENGTH3, Terrain.MOUNTAIN_W);
        Painter.fill(this, NW_CORNER_X3 -1, NW_CORNER_Y3+SQUARE_LENGTH3 , 1, 1, Terrain.MOUNTAIN_CORNER_SW);
        Painter.fill(this, NW_CORNER_X3 , NW_CORNER_Y3+SQUARE_LENGTH3 , SQUARE_WIDTH3, 1, Terrain.MOUNTAIN_S);
        Painter.fill(this, NE_CORNER_X3 , NW_CORNER_Y3+SQUARE_LENGTH3 , 1, 1, Terrain.MOUNTAIN_CORNER_SE);
        Painter.fill(this, NE_CORNER_X3 , NW_CORNER_Y3 , 1, SQUARE_LENGTH3, Terrain.MOUNTAIN_E);
        Painter.fill(this, NE_CORNER_X3, NW_CORNER_Y3, 1, 1, Terrain.MOUNTAIN_CORNER_NE);


        //ENCOUNTER AT 8,16


        west = 1151;
        map[west] = Terrain.WEST;

        east = 1098;
        map[east] = Terrain.EAST;

        north = 75;
        map[north] = Terrain.NORTH;

        south =  2425;
        map[south] = Terrain.SOUTH;

        return true;
    }

    @Override
    protected void decorate() {
      int oldroad1 = 249;
        map[oldroad1] = Terrain.EMPTY_SP;
        int oldroad2 = 596;
        map[oldroad2] = Terrain.EMPTY_SP;
        int oldroad3 = 943;
        map[oldroad3] = Terrain.EMPTY_SP;
        int oldroad4 = 1142;
        map[oldroad4] = Terrain.EMPTY_SP;
        int oldroad5 = 1291;
        map[oldroad5] = Terrain.EMPTY_SP;
        int oldroad6 = 1341;
        map[oldroad6] = Terrain.EMPTY_SP;
        int oldroad7 = 1391;
        map[oldroad7] = Terrain.EMPTY_SP;
        int oldroad8 = 1590;
        map[oldroad8] = Terrain.EMPTY_SP;
        int oldroad9 = 1789;
        map[oldroad9] = Terrain.EMPTY_SP;
        int oldroad10 = 2037;
        map[oldroad10] = Terrain.EMPTY_SP;
        int oldroad11 = 2236;
        map[oldroad11] = Terrain.EMPTY_SP;
        int oldroad12 = 2384;
        map[oldroad12] = Terrain.EMPTY_SP;

        for (int i=WIDTH + 1; i < LENGTH - WIDTH; i++) {
            if (map[i] == Terrain.EMPTY) {
                int n = 0;
                if (map[i + 1] == Terrain.PEDESTAL) {
                    n++;
                }
                if (Random.Int( 14 ) <= n) {
                    map[i] = Terrain.PEDESTAL;
                }
            }
        }
        for (int i=WIDTH; i < LENGTH - WIDTH; i++) {
            if (map[i] == Terrain.MOUNTAIN_W && map[i+50] == Terrain.MOUNTAIN_S) {
                map[i+50] = Terrain.MOUNTAIN_CORNER_SW;
            }
        }

        for (int i=WIDTH; i < LENGTH - WIDTH; i++) {
            if (map[i] == Terrain.MOUNTAIN_N && map[i-1] == Terrain.MOUNTAIN_N && map[i+50] == Terrain.MOUNTAIN_E) {
                map[i+50] = Terrain.MOUNTAIN_CORNER_NE;
            }
        }

        for (int i=WIDTH; i < LENGTH - WIDTH; i++) {
            if (map[i] == Terrain.MOUNTAIN_S && map[i-1] == Terrain.MOUNTAIN_S && map[i+50] == Terrain.MOUNTAIN_E) {
                map[i+50] = Terrain.MOUNTAIN_CORNER_NW;
            }
        }


        for (int i=WIDTH; i < LENGTH - WIDTH; i++) {
            if (map[i] == Terrain.MOUNTAIN_S && map[i+1] == Terrain.MOUNTAIN_W) {
                map[i+1] = Terrain.MOUNTAIN_ELBOW_NE;
            }
        }

        for (int i=WIDTH; i < LENGTH - WIDTH; i++) {
            if (map[i] == Terrain.MOUNTAIN_S && map[i+50] == Terrain.MOUNTAIN_E) {
                map[i] = Terrain.MOUNTAIN_ELBOW_NW;
            }
        }

        for (int i=WIDTH; i < LENGTH - WIDTH; i++) {
            if (map[i] == Terrain.MOUNTAIN_W && map[i+1] == Terrain.MOUNTAIN_N) {
                map[i] = Terrain.MOUNTAIN_CORNER_NW;
            }
        }

        for (int i=WIDTH; i < LENGTH - WIDTH; i++) {
            if (map[i-1] == Terrain.MOUNTAIN_N && map[i-50] == Terrain.MOUNTAIN_W && map[i] == Terrain.EMPTY) {
                map[i] = Terrain.MOUNTAIN_ELBOW_SE;
            }
        }

        for (int i=WIDTH; i < LENGTH - WIDTH; i++) {
            if (map[i] == Terrain.EMPTY && map[i - 1] == Terrain.MOUNTAIN_S && map[i-50] == Terrain.MOUNTAIN_E) {
                map[i] = Terrain.MOUNTAIN_CORNER_SE;
            }
        }



    }

    @Override
    public int nMobs() {
        return 1;
    }

    @Override
    protected void createMobs() {
        WildSheep.spawn(this);
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
                        Random.IntRange( 1, 2 ) +
                                Random.IntRange( 1, 2 ) * WIDTH;
            } while (pos == east || map[pos] == Terrain.SIGN);
            drop( item, pos ).type = Heap.Type.SKELETON;
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
                return "It looks like someone was in the middle of building a road here.";
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

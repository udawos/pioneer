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


public class A05HillLevel extends Level {

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


    //North block
    //NOTE: this code makes an east-west oriented saddle-mountain/hill
    private static final int NW_CORNER_X	= 5;
    private static final int NW_CORNER_Y	= 0;
    private static final int SQUARE_WIDTH	= 20;
    private static final int SQUARE_HEIGHT	= 15;
    private static final int NE_CORNER_X	= NW_CORNER_X + SQUARE_WIDTH;

    private static final int NW_CORNER_X2	= 7;
    private static final int NW_CORNER_Y2	= 0;
    private static final int SQUARE_WIDTH2	= 16;
    private static final int SQUARE_HEIGHT2	= 17;
    private static final int NE_CORNER_X2	= NW_CORNER_X2 + SQUARE_WIDTH2;

    private static final int NW_CORNER_X3	= 10;
    private static final int NW_CORNER_Y3	= 0;
    private static final int SQUARE_WIDTH3	= 12;
    private static final int SQUARE_HEIGHT3	= 19;
    private static final int NE_CORNER_X3	= NW_CORNER_X3 + SQUARE_WIDTH3;

    private static final int NW_CORNER_X4	= 12;
    private static final int NW_CORNER_Y4	= 0;
    private static final int SQUARE_WIDTH4	= 8;
    private static final int SQUARE_HEIGHT4	= 21;
    private static final int NE_CORNER_X4	= NW_CORNER_X4 + SQUARE_WIDTH4;

    private static final int NW_CORNER_X5	= 14;
    private static final int NW_CORNER_Y5	= 0;
    private static final int SQUARE_WIDTH5	= 4;
    private static final int SQUARE_HEIGHT5	= 23;
    private static final int NE_CORNER_X5	= NW_CORNER_X5 + SQUARE_WIDTH5;

    private static final int NW_CORNER_X6	= 16;
    private static final int NW_CORNER_Y6	= 0;
    private static final int SQUARE_WIDTH6	= 2;
    private static final int SQUARE_HEIGHT6	= 24;
    private static final int NE_CORNER_X6	= NW_CORNER_X6 + SQUARE_WIDTH6;

    //South block

    private static final int NW_CORNER_X7	= 5;
    private static final int NW_CORNER_Y7	= 15;
    private static final int SQUARE_WIDTH7	= 29;
    private static final int SQUARE_HEIGHT7	= 20;
    private static final int NE_CORNER_X7	= NW_CORNER_X7 + SQUARE_WIDTH7;

    private static final int NW_CORNER_X8	= 7;
    private static final int NW_CORNER_Y8	= 17;
    private static final int SQUARE_WIDTH8	= 25;
    private static final int SQUARE_HEIGHT8	= 16;
    private static final int NE_CORNER_X8	= NW_CORNER_X8 + SQUARE_WIDTH8;

    private static final int NW_CORNER_X9	= 10;
    private static final int NW_CORNER_Y9	= 19;
    private static final int SQUARE_WIDTH9	= 21;
    private static final int SQUARE_HEIGHT9	= 12;
    private static final int NE_CORNER_X9	= NW_CORNER_X9 + SQUARE_WIDTH9;

    private static final int NW_CORNER_X10	= 12;
    private static final int NW_CORNER_Y10	= 21;
    private static final int SQUARE_WIDTH10	= 17;
    private static final int SQUARE_HEIGHT10	= 8;
    private static final int NE_CORNER_X10	= NW_CORNER_X10 + SQUARE_WIDTH10;

    private static final int NW_CORNER_X11	= 14;
    private static final int NW_CORNER_Y11	= 23;
    private static final int SQUARE_WIDTH11	= 13;
    private static final int SQUARE_HEIGHT11	= 4;
    private static final int NE_CORNER_X11	= NW_CORNER_X11 + SQUARE_WIDTH11;

    private static final int NW_CORNER_X12	= 16;
    private static final int NW_CORNER_Y12	= 24;
    private static final int SQUARE_WIDTH12	= 10;
    private static final int SQUARE_HEIGHT12	= 2;
    private static final int NE_CORNER_X12	= NW_CORNER_X12 + SQUARE_WIDTH12;

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

        /*//Contour 1
        Painter.fill(this, START_XA, START_YA, EAST_1A, 1, Terrain.MOUNTAIN_N);
        Painter.fill(this, EAST_1A, START_YA-NORTH_1A, 1, NORTH_1A, Terrain.MOUNTAIN_W);
        Painter.fill(this, EAST_1A, START_YA-1, 1,1, Terrain.MOUNTAIN_ELBOW_SE);
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
        Painter.fill(this, EAST_1B, START_YB-NORTH_1B, 1, 1, Terrain.MOUNTAIN_ELBOW_SE);
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
        */

        //N block
        Painter.fill(this, NW_CORNER_X, NW_CORNER_Y , SQUARE_WIDTH, 1, Terrain.MOUNTAIN_N);
        Painter.fill(this, NW_CORNER_X-1, NW_CORNER_Y , 1, 1, Terrain.MOUNTAIN_CORNER_NW);
        Painter.fill(this, NW_CORNER_X-1, NW_CORNER_Y+1 , 1, SQUARE_HEIGHT, Terrain.MOUNTAIN_W);
        //Painter.fill(this, NW_CORNER_X -1, NW_CORNER_Y+SQUARE_HEIGHT , 1, 1, Terrain.MOUNTAIN_CORNER_SW);
        //Painter.fill(this, NW_CORNER_X , NW_CORNER_Y+SQUARE_HEIGHT , SQUARE_WIDTH, 1, Terrain.MOUNTAIN_S);
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
        Painter.fill(this, NE_CORNER_X4 , NW_CORNER_Y4 , 1, SQUARE_HEIGHT4, Terrain.MOUNTAIN_E);
        Painter.fill(this, NE_CORNER_X4, NW_CORNER_Y4, 1, 1, Terrain.MOUNTAIN_CORNER_NE);

        Painter.fill(this, NW_CORNER_X5, NW_CORNER_Y5 , SQUARE_WIDTH5, 1, Terrain.MOUNTAIN_N);
        Painter.fill(this, NW_CORNER_X5-1, NW_CORNER_Y5 , 1, 1, Terrain.MOUNTAIN_CORNER_NW);
        Painter.fill(this, NW_CORNER_X5-1, NW_CORNER_Y5+1 , 1, SQUARE_HEIGHT5, Terrain.MOUNTAIN_W);
        //Painter.fill(this, NW_CORNER_X5 -1, NW_CORNER_Y5+SQUARE_HEIGHT5 , 1, 1, Terrain.MOUNTAIN_CORNER_SW);
        //Painter.fill(this, NW_CORNER_X5 , NW_CORNER_Y5+SQUARE_HEIGHT5 , SQUARE_WIDTH5, 1, Terrain.MOUNTAIN_S);
        //Painter.fill(this, NE_CORNER_X5 , NW_CORNER_Y5+SQUARE_HEIGHT5 , 1, 1, Terrain.MOUNTAIN_CORNER_SE);
        Painter.fill(this, NE_CORNER_X5 , NW_CORNER_Y5 , 1, SQUARE_HEIGHT5, Terrain.MOUNTAIN_E);
        Painter.fill(this, NE_CORNER_X5, NW_CORNER_Y5, 1, 1, Terrain.MOUNTAIN_CORNER_NE);

        Painter.fill(this, NW_CORNER_X6, NW_CORNER_Y6 , SQUARE_WIDTH6, 1, Terrain.MOUNTAIN_N);
        Painter.fill(this, NW_CORNER_X6-1, NW_CORNER_Y6 , 1, 1, Terrain.MOUNTAIN_CORNER_NW);
        Painter.fill(this, NW_CORNER_X6-1, NW_CORNER_Y6+1 , 1, SQUARE_HEIGHT6, Terrain.MOUNTAIN_W);
        //Painter.fill(this, NW_CORNER_X6 -1, NW_CORNER_Y6+SQUARE_HEIGHT6 , 1, 1, Terrain.MOUNTAIN_CORNER_SW);
        //Painter.fill(this, NW_CORNER_X6 , NW_CORNER_Y6+SQUARE_HEIGHT6 , SQUARE_WIDTH6, 1, Terrain.MOUNTAIN_S);
        //Painter.fill(this, NE_CORNER_X6 , NW_CORNER_Y6+SQUARE_HEIGHT6 , 1, 1, Terrain.MOUNTAIN_CORNER_SE);
        Painter.fill(this, NE_CORNER_X6 , NW_CORNER_Y6 , 1, SQUARE_HEIGHT6, Terrain.MOUNTAIN_E);
        Painter.fill(this, NE_CORNER_X6, NW_CORNER_Y6, 1, 1, Terrain.MOUNTAIN_CORNER_NE);




        //S block

        Painter.fill(this, NW_CORNER_X7+SQUARE_WIDTH, NW_CORNER_Y7 , SQUARE_WIDTH7-SQUARE_WIDTH, 1, Terrain.MOUNTAIN_N);
       // Painter.fill(this, NW_CORNER_X7-1, NW_CORNER_Y7 , 1, 1, Terrain.MOUNTAIN_CORNER_NW);
        Painter.fill(this, NW_CORNER_X7-1, NW_CORNER_Y7+1 , 1, SQUARE_HEIGHT7, Terrain.MOUNTAIN_W);
        Painter.fill(this, NW_CORNER_X7 -1, NW_CORNER_Y7+SQUARE_HEIGHT7 , 1, 1, Terrain.MOUNTAIN_CORNER_SW);
        Painter.fill(this, NW_CORNER_X7 , NW_CORNER_Y7+SQUARE_HEIGHT7 , SQUARE_WIDTH7, 1, Terrain.MOUNTAIN_S);
        Painter.fill(this, NE_CORNER_X7 , NW_CORNER_Y7+SQUARE_HEIGHT7 , 1, 1, Terrain.MOUNTAIN_CORNER_SE);
        Painter.fill(this, NE_CORNER_X7, NW_CORNER_Y7 , 1, SQUARE_HEIGHT7, Terrain.MOUNTAIN_E);
        Painter.fill(this, NE_CORNER_X7, NW_CORNER_Y7, 1, 1, Terrain.MOUNTAIN_CORNER_NE);


        Painter.fill(this, NW_CORNER_X8+SQUARE_WIDTH2, NW_CORNER_Y8 , SQUARE_WIDTH8-SQUARE_WIDTH2, 1, Terrain.MOUNTAIN_N);
        //Painter.fill(this, NW_CORNER_X8-1, NW_CORNER_Y8 , 1, 1, Terrain.MOUNTAIN_CORNER_NW);
        Painter.fill(this, NW_CORNER_X8-1, NW_CORNER_Y8+1 , 1, SQUARE_HEIGHT8, Terrain.MOUNTAIN_W);
        Painter.fill(this, NW_CORNER_X8 -1, NW_CORNER_Y8+SQUARE_HEIGHT8 , 1, 1, Terrain.MOUNTAIN_CORNER_SW);
        Painter.fill(this, NW_CORNER_X2 , NW_CORNER_Y8+SQUARE_HEIGHT8 , SQUARE_WIDTH8, 1, Terrain.MOUNTAIN_S);
        Painter.fill(this, NE_CORNER_X8 , NW_CORNER_Y8+SQUARE_HEIGHT8 , 1, 1, Terrain.MOUNTAIN_CORNER_SE);
        Painter.fill(this, NE_CORNER_X8, NW_CORNER_Y8 , 1, SQUARE_HEIGHT8, Terrain.MOUNTAIN_E);
        Painter.fill(this, NE_CORNER_X8, NW_CORNER_Y8, 1, 1, Terrain.MOUNTAIN_CORNER_NE);


        Painter.fill(this, NW_CORNER_X9+SQUARE_WIDTH3, NW_CORNER_Y9 , SQUARE_WIDTH9-SQUARE_WIDTH3, 1, Terrain.MOUNTAIN_N);
        //Painter.fill(this, NW_CORNER_X9-1, NW_CORNER_Y9 , 1, 1, Terrain.MOUNTAIN_CORNER_NW);
        Painter.fill(this, NW_CORNER_X9-1, NW_CORNER_Y9+1 , 1, SQUARE_HEIGHT9, Terrain.MOUNTAIN_W);
        Painter.fill(this, NW_CORNER_X9 -1, NW_CORNER_Y9+SQUARE_HEIGHT9 , 1, 1, Terrain.MOUNTAIN_CORNER_SW);
        Painter.fill(this, NW_CORNER_X9 , NW_CORNER_Y9+SQUARE_HEIGHT9 , SQUARE_WIDTH9, 1, Terrain.MOUNTAIN_S);
        Painter.fill(this, NE_CORNER_X9 , NW_CORNER_Y9+SQUARE_HEIGHT9 , 1, 1, Terrain.MOUNTAIN_CORNER_SE);
        Painter.fill(this, NE_CORNER_X9 , NW_CORNER_Y9 , 1, SQUARE_HEIGHT9, Terrain.MOUNTAIN_E);
        Painter.fill(this, NE_CORNER_X9 , NW_CORNER_Y9, 1, 1, Terrain.MOUNTAIN_CORNER_NE);

        Painter.fill(this, NW_CORNER_X10+SQUARE_WIDTH4, NW_CORNER_Y10 , SQUARE_WIDTH10-SQUARE_WIDTH4, 1, Terrain.MOUNTAIN_N);
        //Painter.fill(this, NW_CORNER_X10-1, NW_CORNER_Y10 , 1, 1, Terrain.MOUNTAIN_CORNER_NW);
        Painter.fill(this, NW_CORNER_X10-1, NW_CORNER_Y10+1 , 1, SQUARE_HEIGHT10, Terrain.MOUNTAIN_W);
        Painter.fill(this, NW_CORNER_X10 -1, NW_CORNER_Y10+SQUARE_HEIGHT10 , 1, 1, Terrain.MOUNTAIN_CORNER_SW);
        Painter.fill(this, NW_CORNER_X10 , NW_CORNER_Y10+SQUARE_HEIGHT10 , SQUARE_WIDTH10, 1, Terrain.MOUNTAIN_S);
        Painter.fill(this, NE_CORNER_X10 , NW_CORNER_Y10+SQUARE_HEIGHT10 , 1, 1, Terrain.MOUNTAIN_CORNER_SE);
        Painter.fill(this, NE_CORNER_X10 , NW_CORNER_Y10 , 1, SQUARE_HEIGHT10, Terrain.MOUNTAIN_E);
        Painter.fill(this, NE_CORNER_X10 , NW_CORNER_Y10, 1, 1, Terrain.MOUNTAIN_CORNER_NE);

        //Painter.fill(this, NW_CORNER_X11+SQUARE_WIDTH5, NW_CORNER_Y11 , SQUARE_WIDTH11-SQUARE_WIDTH5, 1, Terrain.MOUNTAIN_N);
        //Painter.fill(this, NW_CORNER_X11-1, NW_CORNER_Y11 , 1, 1, Terrain.MOUNTAIN_CORNER_NW);
        Painter.fill(this, NW_CORNER_X11-1, NW_CORNER_Y11+1 , 1, SQUARE_HEIGHT11, Terrain.MOUNTAIN_W);
        Painter.fill(this, NW_CORNER_X11 -1, NW_CORNER_Y11+SQUARE_HEIGHT11 , 1, 1, Terrain.MOUNTAIN_CORNER_SW);
        Painter.fill(this, NW_CORNER_X11 , NW_CORNER_Y11+SQUARE_HEIGHT11 , SQUARE_WIDTH11, 1, Terrain.MOUNTAIN_S);
        Painter.fill(this, NE_CORNER_X11 , NW_CORNER_Y11+SQUARE_HEIGHT11 , 1, 1, Terrain.MOUNTAIN_CORNER_SE);
        Painter.fill(this, NE_CORNER_X11 , NW_CORNER_Y11+2 , 1, SQUARE_HEIGHT11-2, Terrain.MOUNTAIN_E);
        Painter.fill(this, NE_CORNER_X11 , NW_CORNER_Y11+1, 1, 1, Terrain.MOUNTAIN_CORNER_NE);

        Painter.fill(this, NW_CORNER_X12+SQUARE_WIDTH6, NW_CORNER_Y12 , SQUARE_WIDTH12-SQUARE_WIDTH6, 1, Terrain.MOUNTAIN_N);
        //Painter.fill(this, NW_CORNER_X12-1, NW_CORNER_Y12 , 1, 1, Terrain.MOUNTAIN_CORNER_NW);
        Painter.fill(this, NW_CORNER_X12-1, NW_CORNER_Y12+1 , 1, SQUARE_HEIGHT12, Terrain.MOUNTAIN_W);
        Painter.fill(this, NW_CORNER_X12 -1, NW_CORNER_Y12+SQUARE_HEIGHT12 , 1, 1, Terrain.MOUNTAIN_CORNER_SW);
        Painter.fill(this, NW_CORNER_X12 , NW_CORNER_Y12+SQUARE_HEIGHT12 , SQUARE_WIDTH12, 1, Terrain.MOUNTAIN_S);
        Painter.fill(this, NE_CORNER_X12 , NW_CORNER_Y12+SQUARE_HEIGHT12 , 1, 1, Terrain.MOUNTAIN_CORNER_SE);
        Painter.fill(this, NE_CORNER_X12 , NW_CORNER_Y12 , 1, SQUARE_HEIGHT12, Terrain.MOUNTAIN_E);
        Painter.fill(this, NE_CORNER_X12 , NW_CORNER_Y12, 1, 1, Terrain.MOUNTAIN_CORNER_NE);


        //Summit
        Painter.fill(this, 16, 0, 2, 24, Terrain.HILL);
        Painter.fill(this, 16, 25, 10, 1, Terrain.HILL);


        //Encounter at 18, 37

        west = 1151;
        map[west] = Terrain.WEST;

        north = 76;
        map[north] = Terrain.NORTH;

        east = 1098;
        map[east] = Terrain.EAST;


        return true;
    }



    @Override
    protected void decorate() {
       /* for (int i=WIDTH + 1; i < LENGTH - WIDTH; i++) {
            if (map[i] == Terrain.EMPTY) {
                int n = 0;
                if (map[i + 1] == Terrain.RUBBLE) {
                    n++;
                }
                if (map[i - 1] == Terrain.RUBBLE) {
                    n++;
                }
                if (map[i + WIDTH] == Terrain.RUBBLE) {
                    n++;
                }
                if (map[i - WIDTH] == Terrain.RUBBLE) {
                    n++;
                }
                if (Random.Int( 8 ) <= n) {
                    map[i] = Terrain.RUBBLE;
                }
            }

        }*/
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
                if (Random.Int(35) <= n) {
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

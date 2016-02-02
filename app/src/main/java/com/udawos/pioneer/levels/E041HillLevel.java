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
import com.udawos.pioneer.items.Heap;
import com.udawos.pioneer.items.Item;
import com.udawos.pioneer.levels.painters.Painter;
import com.udawos.utils.Bundle;
import com.udawos.utils.Random;

public class E041HillLevel extends Level {

    {
        color1 = 0x4b6636;
        color2 = 0xf2f2f2;
        viewDistance = 14;
    }

    //Major contour lines
    private static final int START_XA   = 36;
    private static final int EAST_1A   = 4;
    private static final int WEST_1A    = 25;
    private static final int EAST_2A   = 19;
    private static final int WEST_2A    = 6;
    private static final int EAST_3A   = 5;

    private static final int START_YA   = 0;
    private static final int SOUTH_1A   = 10;
    private static final int SOUTH_2A   = 6;
    private static final int SOUTH_3A   = 11;
    private static final int SOUTH_4A   = 7;
    private static final int SOUTH_5A   = 8;
    private static final int SOUTH_6A   = 8;

    private static final int START_XB   = 37;
    private static final int EAST_1B   = 4;
    private static final int WEST_1B    = 25;
    private static final int EAST_2B   = 19;
    private static final int WEST_2B    = 6;
    private static final int EAST_3B   = 5;

    private static final int START_YB   = 0;
    private static final int SOUTH_1B   = 9;
    private static final int SOUTH_2B   = 8;
    private static final int SOUTH_3B   = 9;
    private static final int SOUTH_4B   = 9;
    private static final int SOUTH_5B   = 6;
    private static final int SOUTH_6B   = 9;

    private static final int START_XC   = 38;
    private static final int EAST_1C   = 4;
    private static final int WEST_1C    = 25;
    private static final int EAST_2C   = 19;
    private static final int WEST_2C    = 6;
    private static final int EAST_3C   = 5;

    private static final int START_YC   = 0;
    private static final int SOUTH_1C   = 8;
    private static final int SOUTH_2C   = 10;
    private static final int SOUTH_3C   = 7;
    private static final int SOUTH_4C   = 11;
    private static final int SOUTH_5C   = 4;
    private static final int SOUTH_6C   = 10;

    private static final int START_XD   = 39;
    private static final int EAST_1D   = 4;
    private static final int WEST_1D    = 25;
    private static final int EAST_2D   = 19;
    private static final int WEST_2D    = 6;
    private static final int EAST_3D   = 5;

    private static final int START_YD   = 0;
    private static final int SOUTH_1D   = 7;
    private static final int SOUTH_2D   = 12;
    private static final int SOUTH_3D   = 5;
    private static final int SOUTH_4D   = 13;
    private static final int SOUTH_5D   = 2;
    private static final int SOUTH_6D   = 11;

    private static final int START_XE   = 42;
    private static final int EAST_1E   = 2;
    private static final int WEST_1E    = 4;
    private static final int WEST_2E    = 5;
    private static final int EAST_3E   = 5;

    private static final int START_YE   = 0;
    private static final int SOUTH_1E   = 5;
    private static final int SOUTH_2E   = 16;
    private static final int SOUTH_4E   = 15;
    private static final int SOUTH_5E   = 3;
    private static final int SOUTH_6E   = 11;


    private static final int NW_CORNER_X	= 20;
    private static final int NW_CORNER_Y	= 20;
    private static final int SQUARE_WIDTH	= 8;
    private static final int SQUARE_HEIGHT	= 3;
    private static final int NE_CORNER_X	= NW_CORNER_X + SQUARE_WIDTH;

    private static final int START_XF  = 12;
    private static final int WEST_1F    = 5;
    private static final int WEST_2F    = 2;
    private static final int WEST_3F    = 2;

    private static final int START_YF   = 0;
    private static final int SOUTH_1F   = 15;
    private static final int SOUTH_2F   = 19;
    private static final int SOUTH_3F   = 15;

    private static final int START_XG  = 11;
    private static final int WEST_1G   = 7;
    private static final int WEST_2G    = 3;
    private static final int WEST_3G    = 1;

    private static final int START_YG   = 0;
    private static final int SOUTH_1G   = 13;
    private static final int SOUTH_2G   = 18;
    private static final int SOUTH_3G   = 13;

    private static final int START_XH  = 8;
    private static final int WEST_1H   = 9;



    private static final int START_YH   = 0;
    private static final int SOUTH_1H   = 11;
    private static final int SOUTH_2H   = 17;


    private static final int NW_CORNER_X2	= 1;
    private static final int NW_CORNER_Y2	= 1;
    private static final int SQUARE_WIDTH2	= 6;
    private static final int SQUARE_HEIGHT2	= 6;
    private static final int NE_CORNER_X2	= NW_CORNER_X2 + SQUARE_WIDTH2;



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
    public void storeInBundle( Bundle bundle ) {
        super.storeInBundle( bundle );
    }

    @Override
    public void restoreFromBundle( Bundle bundle ) {
        super.restoreFromBundle(bundle);
    }

    @Override
    protected boolean build() {


        //Contour 1 E
        Painter.fill(this, START_XA, START_YA, 1, SOUTH_1A, Terrain.MOUNTAIN_W);
        Painter.fill(this, START_XA, START_YA+SOUTH_1A, EAST_1A, 1, Terrain.MOUNTAIN_S);
        Painter.fill(this, START_XA, START_YA+SOUTH_1A, 1, 1, Terrain.MOUNTAIN_CORNER_SW);
        Painter.fill(this, START_XA+EAST_1A, START_YA+SOUTH_1A, 1, SOUTH_2A, Terrain.MOUNTAIN_W);
        Painter.fill(this, START_XA+EAST_1A, START_YA+SOUTH_1A, 1, 1, Terrain.MOUNTAIN_ELBOW_NE);
        Painter.fill(this, START_XA-WEST_1A+EAST_1A, START_YA+SOUTH_1A+SOUTH_2A, WEST_1A, 1, Terrain.MOUNTAIN_N);
        Painter.fill(this, START_XA-WEST_1A+EAST_1A, START_YA+SOUTH_1A+SOUTH_2A, 1, 1, Terrain.MOUNTAIN_ELBOW_SE);
        Painter.fill( this, START_XA-WEST_1A+EAST_1A, START_YA+SOUTH_1A+SOUTH_2A, 1, SOUTH_3A, Terrain.MOUNTAIN_W);

        Painter.fill(this, START_XA-WEST_1A+EAST_1A, START_YA+SOUTH_1A+SOUTH_2A+SOUTH_3A, EAST_2A-4, 1, Terrain.MOUNTAIN_S);
        Painter.fill(this, START_XA-WEST_1A+EAST_2A, START_YA+SOUTH_1A+SOUTH_2A+SOUTH_3A, 1, SOUTH_4A, Terrain.MOUNTAIN_W);
        Painter.fill(this, START_XA-WEST_1A+EAST_2A-WEST_2A, START_YA+SOUTH_1A+SOUTH_2A+SOUTH_3A+SOUTH_4A, WEST_2A, 1, Terrain.MOUNTAIN_N);
        Painter.fill(this, START_XA-WEST_1A+EAST_2A-WEST_2A, START_YA+SOUTH_1A+SOUTH_2A+SOUTH_3A+SOUTH_4A, 1, SOUTH_5A, Terrain.MOUNTAIN_W );
        Painter.fill(this, START_XA-WEST_1A+EAST_2A-WEST_2A,START_YA+SOUTH_1A+SOUTH_2A+SOUTH_3A+SOUTH_4A+SOUTH_5A, EAST_3A, 1, Terrain.MOUNTAIN_S);
        Painter.fill(this, START_XA-WEST_1A+EAST_2A-WEST_2A+EAST_3A,START_YA+SOUTH_1A+SOUTH_2A+SOUTH_3A+SOUTH_4A+SOUTH_5A, 1, SOUTH_6A, Terrain.MOUNTAIN_W);

        //Contour 2 E
        Painter.fill(this, START_XB, START_YB, 1, SOUTH_1B, Terrain.MOUNTAIN_W);
        Painter.fill(this, START_XB, START_YB+SOUTH_1B, EAST_1B, 1, Terrain.MOUNTAIN_S);
        Painter.fill(this, START_XB+EAST_1B, START_YB+SOUTH_1B, 1, SOUTH_2B, Terrain.MOUNTAIN_W);
        Painter.fill(this, START_XB-WEST_1B+EAST_1B, START_YB+SOUTH_1B+SOUTH_2B, WEST_1B, 1, Terrain.MOUNTAIN_N);
        Painter.fill( this, START_XB-WEST_1B+EAST_1B, START_YB+SOUTH_1B+SOUTH_2B, 1, SOUTH_3B, Terrain.MOUNTAIN_W);
        Painter.fill(this, START_XB-WEST_1B+EAST_1B, START_YB+SOUTH_1B+SOUTH_2B+SOUTH_3B, EAST_2B-4, 1, Terrain.MOUNTAIN_S);
        Painter.fill(this, START_XB-WEST_1B+EAST_2B, START_YB+SOUTH_1B+SOUTH_2B+SOUTH_3B, 1, SOUTH_4B, Terrain.MOUNTAIN_W);
        Painter.fill(this, START_XB-WEST_1B+EAST_2B-WEST_2B, START_YB+SOUTH_1B+SOUTH_2B+SOUTH_3B+SOUTH_4B, WEST_2B, 1, Terrain.MOUNTAIN_N);
        Painter.fill(this, START_XB-WEST_1B+EAST_2B-WEST_2B, START_YB+SOUTH_1B+SOUTH_2B+SOUTH_3B+SOUTH_4B, 1, SOUTH_5B, Terrain.MOUNTAIN_W );
        Painter.fill(this, START_XB-WEST_1B+EAST_2B-WEST_2B,START_YB+SOUTH_1B+SOUTH_2B+SOUTH_3B+SOUTH_4B+SOUTH_5B, EAST_3B, 1, Terrain.MOUNTAIN_S);
        Painter.fill(this, START_XB-WEST_1B+EAST_2B-WEST_2B+EAST_3B,START_YB+SOUTH_1B+SOUTH_2B+SOUTH_3B+SOUTH_4B+SOUTH_5B, 1, SOUTH_6B, Terrain.MOUNTAIN_W);

        //Contour 3 E
        Painter.fill(this, START_XC, START_YC, 1, SOUTH_1C, Terrain.MOUNTAIN_W);
        Painter.fill(this, START_XC, START_YC+SOUTH_1C, EAST_1C, 1, Terrain.MOUNTAIN_S);
        Painter.fill(this, START_XC+EAST_1C, START_YC+SOUTH_1C, 1, SOUTH_2C, Terrain.MOUNTAIN_W);
        Painter.fill(this, START_XC-WEST_1C+EAST_1C, START_YC+SOUTH_1C+SOUTH_2C, WEST_1C, 1, Terrain.MOUNTAIN_N);
        Painter.fill( this, START_XC-WEST_1C+EAST_1C, START_YC+SOUTH_1C+SOUTH_2C, 1, SOUTH_3C, Terrain.MOUNTAIN_W);
        Painter.fill(this, START_XC-WEST_1C+EAST_1C, START_YC+SOUTH_1C+SOUTH_2C+SOUTH_3C, EAST_2C-4, 1, Terrain.MOUNTAIN_S);
        Painter.fill(this, START_XC-WEST_1C+EAST_2C, START_YC+SOUTH_1C+SOUTH_2C+SOUTH_3C, 1, SOUTH_4C, Terrain.MOUNTAIN_W);
        Painter.fill(this, START_XC-WEST_1C+EAST_2C-WEST_2C, START_YC+SOUTH_1C+SOUTH_2C+SOUTH_3C+SOUTH_4C, WEST_2C, 1, Terrain.MOUNTAIN_N);
        Painter.fill(this, START_XC-WEST_1C+EAST_2C-WEST_2C, START_YC+SOUTH_1C+SOUTH_2C+SOUTH_3C+SOUTH_4C, 1, SOUTH_5C, Terrain.MOUNTAIN_W );
        Painter.fill(this, START_XC-WEST_1C+EAST_2C-WEST_2C,START_YC+SOUTH_1C+SOUTH_2C+SOUTH_3C+SOUTH_4C+SOUTH_5C, EAST_3C, 1, Terrain.MOUNTAIN_S);
        Painter.fill(this, START_XC-WEST_1C+EAST_2C-WEST_2C+EAST_3C,START_YC+SOUTH_1C+SOUTH_2C+SOUTH_3C+SOUTH_4C+SOUTH_5C, 1, SOUTH_6C, Terrain.MOUNTAIN_W);

        //Contour 4 E
        Painter.fill(this, START_XD, START_YD, 1, SOUTH_1D, Terrain.MOUNTAIN_W);
        Painter.fill(this, START_XD, START_YD+SOUTH_1D, EAST_1D, 1, Terrain.MOUNTAIN_S);
        Painter.fill(this, START_XD+EAST_1D, START_YD+SOUTH_1D, 1, SOUTH_2D, Terrain.MOUNTAIN_W);
        Painter.fill(this, START_XD-WEST_1D+EAST_1D, START_YD+SOUTH_1D+SOUTH_2D, WEST_1D, 1, Terrain.MOUNTAIN_N);
        Painter.fill( this, START_XD-WEST_1D+EAST_1D, START_YD+SOUTH_1D+SOUTH_2D, 1, SOUTH_3D, Terrain.MOUNTAIN_W);
        Painter.fill(this, START_XD-WEST_1D+EAST_1D, START_YD+SOUTH_1D+SOUTH_2D+SOUTH_3D, EAST_2D-4, 1, Terrain.MOUNTAIN_S);
        Painter.fill(this, START_XD-WEST_1D+EAST_2D, START_YD+SOUTH_1D+SOUTH_2D+SOUTH_3D, 1, SOUTH_4D, Terrain.MOUNTAIN_W);
        Painter.fill(this, START_XD-WEST_1D+EAST_2D-WEST_2D, START_YD+SOUTH_1D+SOUTH_2D+SOUTH_3D+SOUTH_4D, WEST_2D, 1, Terrain.MOUNTAIN_N);
        Painter.fill(this, START_XD-WEST_1D+EAST_2D-WEST_2D, START_YD+SOUTH_1D+SOUTH_2D+SOUTH_3D+SOUTH_4D, 1, SOUTH_5D, Terrain.MOUNTAIN_W );
        Painter.fill(this, START_XD-WEST_1D+EAST_2D-WEST_2D,START_YD+SOUTH_1D+SOUTH_2D+SOUTH_3D+SOUTH_4D+SOUTH_5D, EAST_3D, 1, Terrain.MOUNTAIN_S);
        Painter.fill(this, START_XD-WEST_1D+EAST_2D-WEST_2D+EAST_3D,START_YD+SOUTH_1D+SOUTH_2D+SOUTH_3D+SOUTH_4D+SOUTH_5D, 1, SOUTH_6D, Terrain.MOUNTAIN_W);

        //Contour 5 E
        Painter.fill(this, START_XE, START_YE, 1, SOUTH_1E, Terrain.MOUNTAIN_W);
        Painter.fill(this, START_XE, START_YE+SOUTH_1E, EAST_1E, 1, Terrain.MOUNTAIN_S);
        Painter.fill(this, START_XE+EAST_1E, START_YE+SOUTH_1E, 1, SOUTH_2E, Terrain.MOUNTAIN_W);
        Painter.fill(this, START_XE+EAST_1E-6, START_YE+SOUTH_1E+SOUTH_2E, WEST_1E+2, 1, Terrain.MOUNTAIN_N);
        Painter.fill(this, START_XE-WEST_1E, START_YE+SOUTH_1E+SOUTH_2E, 1, SOUTH_4E, Terrain.MOUNTAIN_W);
        Painter.fill(this, START_XE-WEST_1E-WEST_2E, START_YE+SOUTH_1E+SOUTH_2E+SOUTH_4E+1, 4, 1, Terrain.MOUNTAIN_N);
        Painter.fill(this, START_XE-WEST_1E-WEST_2E, START_YE+SOUTH_1E+SOUTH_2E+SOUTH_4E+1, 1, SOUTH_5E, Terrain.MOUNTAIN_W );
        Painter.fill(this, START_XE-WEST_1E-WEST_2E,START_YE+SOUTH_1E+SOUTH_2E+SOUTH_4E+SOUTH_5E+1, 4, 1, Terrain.MOUNTAIN_S);
        Painter.fill(this, START_XE-WEST_1E-WEST_2E+EAST_3E,START_YE+SOUTH_1E+SOUTH_2E+SOUTH_4E+SOUTH_5E, 1, SOUTH_6E, Terrain.MOUNTAIN_W);

        Painter.fill(this, NW_CORNER_X, NW_CORNER_Y, SQUARE_WIDTH, 1, Terrain.MOUNTAIN_N);
        Painter.fill(this, NW_CORNER_X-1, NW_CORNER_Y , 1, 1, Terrain.MOUNTAIN_CORNER_NW);
        Painter.fill(this, NW_CORNER_X-1, NW_CORNER_Y+1 , 1, SQUARE_HEIGHT, Terrain.MOUNTAIN_W);
        Painter.fill(this, NW_CORNER_X -1, NW_CORNER_Y+SQUARE_HEIGHT , 1, 1, Terrain.MOUNTAIN_CORNER_SW);
        Painter.fill(this, NW_CORNER_X , NW_CORNER_Y+SQUARE_HEIGHT , SQUARE_WIDTH, 1, Terrain.MOUNTAIN_S);
        Painter.fill(this, NE_CORNER_X , NW_CORNER_Y+SQUARE_HEIGHT , 1, 1, Terrain.MOUNTAIN_CORNER_SE);
        Painter.fill(this, NE_CORNER_X, NW_CORNER_Y , 1, SQUARE_HEIGHT, Terrain.MOUNTAIN_E);
        Painter.fill(this, NE_CORNER_X, NW_CORNER_Y, 1, 1, Terrain.MOUNTAIN_CORNER_NE);


        //Contour 1 W
        Painter.fill(this, START_XF, START_YF, 1, SOUTH_1F, Terrain.MOUNTAIN_E);
        Painter.fill(this, START_XF-WEST_1F, START_YF+SOUTH_1F, WEST_1F, 1, Terrain.MOUNTAIN_S);
        Painter.fill(this, START_XF-WEST_1F, START_YF+SOUTH_1F, 1, SOUTH_2F, Terrain.MOUNTAIN_E);
        Painter.fill(this, START_XF-WEST_1F-WEST_2F, START_YF+SOUTH_1F+SOUTH_2F, WEST_2F, 1, Terrain.MOUNTAIN_S);
        Painter.fill( this, START_XF-WEST_1F-WEST_2F, START_YF+SOUTH_1F+SOUTH_2F, 1, SOUTH_3F, Terrain.MOUNTAIN_E);
        Painter.fill(this, START_XF-WEST_1F-WEST_2F-WEST_3F, START_YF+SOUTH_1F+SOUTH_2F+SOUTH_3F, WEST_3F, 1, Terrain.MOUNTAIN_S);

        //Contour 2 W
        Painter.fill(this, START_XG, START_YG, 1, SOUTH_1G, Terrain.MOUNTAIN_E);
        Painter.fill(this, START_XG-WEST_1G, START_YG+SOUTH_1G, WEST_1G, 1, Terrain.MOUNTAIN_S);
        Painter.fill(this, START_XG-WEST_1G, START_YG+SOUTH_1G, 1, SOUTH_2G, Terrain.MOUNTAIN_E);
        Painter.fill(this, START_XG-WEST_1G-WEST_2G, START_YG+SOUTH_1G+SOUTH_2G, WEST_2G, 1, Terrain.MOUNTAIN_S);
        Painter.fill( this, START_XG-WEST_1G-WEST_2G, START_YG+SOUTH_1G+SOUTH_2G, 1, SOUTH_3G, Terrain.MOUNTAIN_E);
        Painter.fill(this, START_XG-WEST_1G-WEST_2G-WEST_3G, START_YG+SOUTH_1G+SOUTH_2G+SOUTH_3G, WEST_3G, 1, Terrain.MOUNTAIN_S);

        //Contour 3 W
        Painter.fill(this, START_XH, START_YH, 1, SOUTH_1H, Terrain.MOUNTAIN_E);
        Painter.fill(this, START_XH-WEST_1H, START_YH+SOUTH_1H, WEST_1H, 1, Terrain.MOUNTAIN_S);
        Painter.fill(this, START_XH-WEST_1H, START_YH+SOUTH_1H, 1, SOUTH_2H, Terrain.MOUNTAIN_E);


        Painter.fill(this, NW_CORNER_X2, NW_CORNER_Y2, SQUARE_WIDTH2, 1, Terrain.MOUNTAIN_N);
        Painter.fill(this, NW_CORNER_X2-1, NW_CORNER_Y2 , 1, 1, Terrain.MOUNTAIN_CORNER_NW);
        Painter.fill(this, NW_CORNER_X2-1, NW_CORNER_Y2+1 , 1, SQUARE_HEIGHT2, Terrain.MOUNTAIN_W);
        Painter.fill(this, NW_CORNER_X2 -1, NW_CORNER_Y2+SQUARE_HEIGHT2 , 1, 1, Terrain.MOUNTAIN_CORNER_SW);
        Painter.fill(this, NW_CORNER_X2 , NW_CORNER_Y2+SQUARE_HEIGHT2, SQUARE_WIDTH2, 1, Terrain.MOUNTAIN_S);
        Painter.fill(this, NE_CORNER_X2 , NW_CORNER_Y2+SQUARE_HEIGHT2, 1, 1, Terrain.MOUNTAIN_CORNER_SE);
        Painter.fill(this, NE_CORNER_X2, NW_CORNER_Y2 , 1, SQUARE_HEIGHT2, Terrain.MOUNTAIN_E);
        Painter.fill(this, NE_CORNER_X2, NW_CORNER_Y2, 1, 1, Terrain.MOUNTAIN_CORNER_NE);


        west = 1151;
        map[west] = Terrain.WEST;

        north = 75;
        map[north] = Terrain.NORTH;

        south =  2425;
        map[south] = Terrain.SOUTH;

        return true;
    }

    @Override
    protected void decorate() {
        for (int i=WIDTH; i < LENGTH - WIDTH; i++) {
            if (map[i] == Terrain.MOUNTAIN_W && map[i+50] == Terrain.MOUNTAIN_S) {
                map[i+50] = Terrain.MOUNTAIN_CORNER_SW;
            }
        }

        for (int i=WIDTH; i < LENGTH - WIDTH; i++) {
            if (map[i] == Terrain.MOUNTAIN_S && map[i+1] == Terrain.MOUNTAIN_W) {
                map[i+1] = Terrain.MOUNTAIN_ELBOW_NE;
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
        for (int i=WIDTH; i < LENGTH - WIDTH; i++) {
            if (map[i] == Terrain.MOUNTAIN_E && map[i+1] == Terrain.MOUNTAIN_S) {
                map[i] = Terrain.MOUNTAIN_ELBOW_NW;
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
                if (Random.Int( 35 ) <= n) {
                    map[i] = Terrain.RUBBLE;
                }
            }
        }
    }


    @Override
    public int nMobs() {
        return 8;
    }

    @Override
    protected void createMobs() {
        for (int i=0; i < NEIGHBOURS8.length; i++) {
            Mob mob = Bestiary.mob(Dungeon.depth);
            do {
                mob.pos = 717 + Level.NEIGHBOURS8[i];
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
                        Random.IntRange(  1, 10 - 2 ) +
                                Random.IntRange( 1, 20 ) * WIDTH;
            } while (pos == east || map[pos] == Terrain.SIGN);
            drop( item, pos ).type = Heap.Type.SKELETON;
        }
    }

    @Override
    public int randomRespawnCell() {
        return -1;
    }

   /* @Override
    public void press( int cell, Char hero ) {

        super.press(cell, hero);

        if (hero == Dungeon.hero) {

            Mob boss = Bestiary.mob(Dungeon.depth);
            boss.state = boss.HUNTING;
            int count = 0;
            do {
                boss.pos = Random.Int( LENGTH );
            } while (
                    !passable[boss.pos] ||
                            (Dungeon.visible[boss.pos] && count++ < 20));
            GameScene.add(boss);

            if (Dungeon.visible[boss.pos]) {
                boss.notice();
                boss.sprite.alpha( 0 );
                boss.sprite.parent.add( new AlphaTweener( boss.sprite, 1, 0.1f ) );
            }

            Dungeon.observe();
        }
    }*/

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

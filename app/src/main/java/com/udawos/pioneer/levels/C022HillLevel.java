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

public class C022HillLevel extends Level {

    {
        color1 = 0x4b6636;
        color2 = 0xf2f2f2;
        viewDistance = 16;
    }


    //Major contour lines
    private static final int START_XA   = 0;
    private static final int EAST_1A    = 5;
    private static final int EAST_2A    = 6;
    private static final int EAST_3A    = 12;
    private static final int END_XA     = 49-(EAST_1A+EAST_2A+EAST_3A);

    private static final int START_YA   = 45;
    private static final int NORTH_1A   = 2;
    private static final int SOUTH_1A   = 2;
    private static final int SOUTH_2A   = 2;


    private static final int START_XB   = 0;
    private static final int EAST_1B    = 7;
    private static final int EAST_2B    = 8;
    private static final int EAST_3B    = 12;
    private static final int END_XB     = 49-(EAST_1B+EAST_2B+EAST_3B);

    private static final int START_YB   = 42;
    private static final int NORTH_1B   = 3;
    private static final int SOUTH_1B   = 3;
    private static final int SOUTH_2B   = 3;


    //SW contours
    private static final int NW_CORNER_X	= 24;
    private static final int NW_CORNER_Y	= 0;
    private static final int SQUARE_WIDTH	= 25;
    private static final int SQUARE_HEIGHT	= 40;
    private static final int NE_CORNER_X	= NW_CORNER_X + SQUARE_WIDTH;

    private static final int NW_CORNER_X2	= 28;
    private static final int NW_CORNER_Y2	= 20;
    private static final int SQUARE_WIDTH2	= 18;
    private static final int SQUARE_HEIGHT2	= 16;
    private static final int NE_CORNER_X2	= NW_CORNER_X2 + SQUARE_WIDTH2;

    private static final int NW_CORNER_X3	= 34;
    private static final int NW_CORNER_Y3	= 22;
    private static final int SQUARE_WIDTH3	= 10;
    private static final int SQUARE_HEIGHT3	= 10;
    private static final int NE_CORNER_X3	= NW_CORNER_X3 + SQUARE_WIDTH3;

    private static final int NW_CORNER_X4	= 36;
    private static final int NW_CORNER_Y4	= 24;
    private static final int SQUARE_WIDTH4	= 6;
    private static final int SQUARE_HEIGHT4	= 6;
    private static final int NE_CORNER_X4	= NW_CORNER_X4 + SQUARE_WIDTH4;

    private static final int NW_CORNER_X5	= 38;
    private static final int NW_CORNER_Y5	= 26;
    private static final int SQUARE_WIDTH5	= 2;
    private static final int SQUARE_HEIGHT5	= 2;
    private static final int NE_CORNER_X5	= NW_CORNER_X5 + SQUARE_WIDTH5;


    /*//Road 1
    private static final int ROAD_START_X   = 20;
    private static final int ROAD_WEST_1    = 15;
    private static final int ROAD_WEST_2    = 1;
    private static final int ROAD_END_X     = 5;

    private static final int ROAD_START_Y   = 49;
    private static final int ROAD_NORTH_1   = 12;
    private static final int ROAD_NORTH_2   = 20;
    private static final int ROAD_NORTH_3   = 17;
    private static final int ROAD_END_Y     = 0;

    //Road 2
    private static final int ROAD_START_X   = 20;
    private static final int ROAD_WEST_1    = 15;
    private static final int ROAD_WEST_2    = 1;
    private static final int ROAD_END_X     = 5;

    private static final int ROAD_START_Y   = 49;
    private static final int ROAD_NORTH_1   = 12;
    private static final int ROAD_NORTH_2   = 20;
    private static final int ROAD_NORTH_3   = 17;
    private static final int ROAD_END_Y     = 0;
*/
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
    public void storeInBundle( Bundle bundle ) {
        super.storeInBundle( bundle );
    }

    @Override
    public void restoreFromBundle( Bundle bundle ) {
        super.restoreFromBundle(bundle);
    }

    @Override
    protected boolean build() {

        //Contour 1
        Painter.fill(this, START_XA, START_YA, EAST_1A, 1, Terrain.MOUNTAIN_S);
        Painter.fill(this, EAST_1A, START_YA-NORTH_1A, 1, NORTH_1A, Terrain.MOUNTAIN_E);
        Painter.fill(this, EAST_1A, START_YA, 1,1, Terrain.MOUNTAIN_CORNER_SE);
        Painter.fill(this, EAST_1A, START_YA-NORTH_1A, EAST_2A, 1, Terrain.MOUNTAIN_S);
        Painter.fill(this, EAST_1A, START_YA-NORTH_1A, 1, 1, Terrain.MOUNTAIN_ELBOW_NW);
        Painter.fill(this, EAST_1A+EAST_2A, START_YA-NORTH_1A, 1, SOUTH_1A, Terrain.MOUNTAIN_W);
        Painter.fill(this, EAST_1A+EAST_2A, START_YA-NORTH_1A, 1, 1, Terrain.MOUNTAIN_ELBOW_NE);
        Painter.fill(this, EAST_1A+EAST_2A, START_YA-NORTH_1A+SOUTH_1A, EAST_3A, 1, Terrain.MOUNTAIN_S);
        Painter.fill(this, EAST_1A+EAST_2A, START_YA-NORTH_1A+SOUTH_1A, 1, 1, Terrain.MOUNTAIN_CORNER_SW);
        Painter.fill(this, EAST_1A+EAST_2A+EAST_3A, START_YA-NORTH_1A+SOUTH_1A, 1, SOUTH_2A, Terrain.MOUNTAIN_W);
        Painter.fill(this, EAST_1A+EAST_2A+EAST_3A, START_YA-NORTH_1A+SOUTH_1A, 1, 1, Terrain.MOUNTAIN_ELBOW_NE);
        Painter.fill(this, EAST_1A+EAST_2A+EAST_3A, START_YA-NORTH_1A+SOUTH_1A+SOUTH_2A, END_XA, 1, Terrain.MOUNTAIN_S);
        Painter.fill(this, EAST_1A+EAST_2A+EAST_3A, START_YA-NORTH_1A+SOUTH_1A+SOUTH_2A, 1, 1, Terrain.MOUNTAIN_CORNER_SW);


        //Contour 2
        Painter.fill(this, START_XB, START_YB, EAST_1B, 1, Terrain.MOUNTAIN_S);
        Painter.fill(this, EAST_1B, START_YB-NORTH_1B, 1, NORTH_1B, Terrain.MOUNTAIN_E);
        Painter.fill(this, EAST_1B, START_YB, 1, 1, Terrain.MOUNTAIN_CORNER_SE);
        Painter.fill(this, EAST_1B, START_YB-NORTH_1B, EAST_2B, 1, Terrain.MOUNTAIN_S);
        Painter.fill(this, EAST_1B, START_YB-NORTH_1B, 1, 1, Terrain.MOUNTAIN_ELBOW_NW);
        Painter.fill(this, EAST_1B+EAST_2B, START_YB-NORTH_1B, 1, SOUTH_1B, Terrain.MOUNTAIN_W);
        Painter.fill(this, EAST_1B+EAST_2B, START_YB-NORTH_1B, 1, 1, Terrain.MOUNTAIN_ELBOW_NE);
        Painter.fill(this, EAST_1B+EAST_2B, START_YB-NORTH_1B+SOUTH_1B, EAST_3B, 1, Terrain.MOUNTAIN_S);
        Painter.fill(this, EAST_1B+EAST_2B, START_YB-NORTH_1B+SOUTH_1B, 1, 1, Terrain.MOUNTAIN_CORNER_SW);
        Painter.fill(this, EAST_1B+EAST_2B+EAST_3B, START_YB-NORTH_1B+SOUTH_1B, 1, SOUTH_2B, Terrain.MOUNTAIN_W);
        Painter.fill(this, EAST_1B+EAST_2B+EAST_3B, START_YB-NORTH_1B+SOUTH_1B, 1, 1, Terrain.MOUNTAIN_ELBOW_NE);
        Painter.fill(this, EAST_1B+EAST_2B+EAST_3B, START_YB-NORTH_1B+SOUTH_1B+SOUTH_2B, END_XB, 1, Terrain.MOUNTAIN_S);
        Painter.fill(this, EAST_1B+EAST_2B+EAST_3B, START_YB-NORTH_1B+SOUTH_1B+SOUTH_2B, 1, 1, Terrain.MOUNTAIN_CORNER_SW);



        //SW hills
        //Painter.fill(this, NW_CORNER_X, NW_CORNER_Y, SQUARE_WIDTH, 1, Terrain.MOUNTAIN_N);
        //Painter.fill(this, NW_CORNER_X-1, NW_CORNER_Y , 1, 1, Terrain.MOUNTAIN_CORNER_NW);
        Painter.fill(this, NW_CORNER_X-1, NW_CORNER_Y+1 , 1, SQUARE_HEIGHT, Terrain.MOUNTAIN_W);
        Painter.fill(this, NW_CORNER_X -1, NW_CORNER_Y+SQUARE_HEIGHT , 1, 1, Terrain.MOUNTAIN_CORNER_SW);
        Painter.fill(this, NW_CORNER_X , NW_CORNER_Y+SQUARE_HEIGHT , SQUARE_WIDTH, 1, Terrain.MOUNTAIN_S);
        //Painter.fill(this, NE_CORNER_X , NW_CORNER_Y+SQUARE_HEIGHT , 1, 1, Terrain.MOUNTAIN_CORNER_SE);
        //Painter.fill(this, NE_CORNER_X, NW_CORNER_Y , 1, SQUARE_HEIGHT, Terrain.MOUNTAIN_E);
        //Painter.fill(this, NE_CORNER_X, NW_CORNER_Y, 1, 1, Terrain.MOUNTAIN_CORNER_NE);


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
        Painter.fill(this, NW_CORNER_X3-1, NW_CORNER_Y3+1 , 1, SQUARE_HEIGHT3, Terrain.MOUNTAIN_W);
        Painter.fill(this, NW_CORNER_X3 -1, NW_CORNER_Y3+SQUARE_HEIGHT3 , 1, 1, Terrain.MOUNTAIN_CORNER_SW);
        Painter.fill(this, NW_CORNER_X3 , NW_CORNER_Y3+SQUARE_HEIGHT3 , SQUARE_WIDTH3, 1, Terrain.MOUNTAIN_S);
        Painter.fill(this, NE_CORNER_X3 , NW_CORNER_Y3+SQUARE_HEIGHT3 , 1, 1, Terrain.MOUNTAIN_CORNER_SE);
        Painter.fill(this, NE_CORNER_X3 , NW_CORNER_Y3 , 1, SQUARE_HEIGHT3, Terrain.MOUNTAIN_E);
        Painter.fill(this, NE_CORNER_X3, NW_CORNER_Y3, 1, 1, Terrain.MOUNTAIN_CORNER_NE);

        Painter.fill(this, NW_CORNER_X4, NW_CORNER_Y4 , SQUARE_WIDTH4, 1, Terrain.MOUNTAIN_N);
        Painter.fill(this, NW_CORNER_X4-1, NW_CORNER_Y4 , 1, 1, Terrain.MOUNTAIN_CORNER_NW);
        Painter.fill(this, NW_CORNER_X4-1, NW_CORNER_Y4+1 , 1, SQUARE_HEIGHT4, Terrain.MOUNTAIN_W);
        Painter.fill(this, NW_CORNER_X4 -1, NW_CORNER_Y4+SQUARE_HEIGHT4 , 1, 1, Terrain.MOUNTAIN_CORNER_SW);
        Painter.fill(this, NW_CORNER_X4 , NW_CORNER_Y4+SQUARE_HEIGHT4 , SQUARE_WIDTH4, 1, Terrain.MOUNTAIN_S);
        Painter.fill(this, NE_CORNER_X4 , NW_CORNER_Y4+SQUARE_HEIGHT4 , 1, 1, Terrain.MOUNTAIN_CORNER_SE);
        Painter.fill(this, NE_CORNER_X4, NW_CORNER_Y4 , 1, SQUARE_HEIGHT4, Terrain.MOUNTAIN_E);
        Painter.fill(this, NE_CORNER_X4, NW_CORNER_Y4, 1, 1, Terrain.MOUNTAIN_CORNER_NE);


        Painter.fill(this, NW_CORNER_X5, NW_CORNER_Y5 , SQUARE_WIDTH5, 1, Terrain.MOUNTAIN_N);
        Painter.fill(this, NW_CORNER_X5-1, NW_CORNER_Y5 , 1, 1, Terrain.MOUNTAIN_CORNER_NW);
        Painter.fill(this, NW_CORNER_X5-1, NW_CORNER_Y5+1 , 1, SQUARE_HEIGHT5, Terrain.MOUNTAIN_W);
        Painter.fill(this, NW_CORNER_X5 -1, NW_CORNER_Y5+SQUARE_HEIGHT5 , 1, 1, Terrain.MOUNTAIN_CORNER_SW);
        Painter.fill(this, NW_CORNER_X5 , NW_CORNER_Y5+SQUARE_HEIGHT5 , SQUARE_WIDTH5, 1, Terrain.MOUNTAIN_S);
        Painter.fill(this, NE_CORNER_X5 , NW_CORNER_Y5+SQUARE_HEIGHT5 , 1, 1, Terrain.MOUNTAIN_CORNER_SE);
        Painter.fill(this, NE_CORNER_X5, NW_CORNER_Y5 , 1, SQUARE_HEIGHT5, Terrain.MOUNTAIN_E);
        Painter.fill(this, NE_CORNER_X5, NW_CORNER_Y5, 1, 1, Terrain.MOUNTAIN_CORNER_NE);


        //Summit
        Painter.fill(this, NW_CORNER_X5, NW_CORNER_Y5+1 , 2, 1, Terrain.HILL);

        //Bldg 1
        Painter.fill( this, 28,5,5,1, Terrain.RUINED_WALL);
        Painter.fill( this, 28,10,5,1, Terrain.RUINED_WALL);
        Painter.fill( this, 28,5,1,5, Terrain.RUINED_WALL);
        Painter.fill( this, 33,5,1,5, Terrain.RUINED_WALL);

        //Bldg 2
        Painter.fill( this, 35,12,5,1, Terrain.RUINED_WALL);
        Painter.fill( this, 35,17,5,1, Terrain.RUINED_WALL);
        Painter.fill( this, 35,12,1,5, Terrain.RUINED_WALL);
        Painter.fill( this, 40,12,1,5, Terrain.RUINED_WALL);

       //Old roads
        Painter.fill(this, 37, 0, 1, 9, Terrain.EMPTY_SP);

        Painter.fill(this, 15, 0, 1, 35, Terrain.EMPTY_SP);



        west = 1151;
        map[west] = Terrain.WEST;

        east = 1098;
        map[east] = Terrain.EAST;

        north = 68;
        map[north] = Terrain.NORTH;

        south =  2425;
        map[south] = Terrain.SOUTH;

        down = 480;
        map[down] = Terrain.EMPTY;

        return true;
    }

    @Override
    protected void decorate() {
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
                if (Random.Int(14) <= n) {
                    map[i] = Terrain.PEDESTAL;
                }
            }
        }
    }


    @Override
    public int nMobs() {
        return 4;
    }

    @Override
    protected void createMobs() {
        for (int i=0; i < NEIGHBOURS4.length; i++) {
            Mob mob = Bestiary.mob(Dungeon.depth);
            do {
                mob.pos = 1500 + Level.NEIGHBOURS4[i];
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
                        Random.IntRange( 15, 10 ) +
                                Random.IntRange( 35, 40) * WIDTH;
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

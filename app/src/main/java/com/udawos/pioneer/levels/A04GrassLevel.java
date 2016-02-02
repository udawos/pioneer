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

public class A04GrassLevel extends Level {

    {
        color1 = 0x4b6636;
        color2 = 0xf2f2f2;
        viewDistance = 16;
    }

    //Contours
    private static final int START_XA   = 20;
    private static final int EAST_1A    = 4;
    private static final int EAST_2A    = 4;

    private static final int START_YA   = 0;
    private static final int SOUTH_1A   = 15;
    private static final int SOUTH_2A   = 21;
    private static final int END_YA     = 50-(SOUTH_1A+SOUTH_2A);


    private static final int START_XB   = 15;
    private static final int EAST_1B    = 4;
    private static final int EAST_2B    = 4;

    private static final int START_YB   = 0;
    private static final int SOUTH_1B   = 12;
    private static final int SOUTH_2B   = 16;
    private static final int END_YB     = 50-(SOUTH_1B+SOUTH_2B);

    private static final int START_XC   = 10;
    private static final int EAST_1C    = 4;
    private static final int EAST_2C    = 4;

    private static final int START_YC   = 0;
    private static final int SOUTH_1C   = 21;
    private static final int SOUTH_2C   = 21;
    private static final int END_YC     = 50-(SOUTH_1C+SOUTH_2C);

    private static final int NW_CORNER_X	= 0;
    private static final int NW_CORNER_Y	= 8;
    private static final int SQUARE_WIDTH	= 7;
    private static final int SQUARE_HEIGHT	= 36;
    private static final int NE_CORNER_X	= NW_CORNER_X + SQUARE_WIDTH;

    //Road
    private static final int ROAD_START_X   = 20;
    private static final int ROAD_EAST_1    = 7;
    private static final int ROAD_EAST_2    = 5;
    private static final int ROAD_END_X     = 44;

    private static final int ROAD_START_Y   = 8;
    private static final int ROAD_SOUTH_1   = 6;
    private static final int ROAD_SOUTH_2   = 18;
    private static final int ROAD_END_Y     = 35;
    


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

        //Major contours

        //Contour 1
        Painter.fill(this, START_XA, START_YA, 1, SOUTH_1A, Terrain.MOUNTAIN_E);
        Painter.fill(this, START_XA, START_YA+SOUTH_1A, EAST_1A, 1, Terrain.MOUNTAIN_N);
        Painter.fill(this, START_XA, START_YA+SOUTH_1A-1, 1, 1, Terrain.MOUNTAIN_ELBOW_SW);
        Painter.fill(this, START_XA+EAST_1A, START_YA+SOUTH_1A, 1, SOUTH_2A, Terrain.MOUNTAIN_E);
        Painter.fill(this, START_XA+EAST_1A, START_YA+SOUTH_1A, 1, 1, Terrain.MOUNTAIN_CORNER_NE);
        Painter.fill(this, START_XA, START_YA+SOUTH_1A+SOUTH_2A, EAST_2A, 1, Terrain.MOUNTAIN_S);
        Painter.fill(this, START_XA+EAST_1A, START_YA+SOUTH_1A+SOUTH_2A, 1, 1, Terrain.MOUNTAIN_CORNER_SE);
        Painter.fill(this, START_XA, START_YA+SOUTH_1A+SOUTH_2A, 1, END_YA, Terrain.MOUNTAIN_E);
        Painter.fill(this, START_XA, START_YA+SOUTH_1A+SOUTH_2A, 1, 1, Terrain.MOUNTAIN_ELBOW_NW);

        //Contour 2
        Painter.fill(this, START_XB, START_YB, 1, SOUTH_1B, Terrain.MOUNTAIN_E);
        Painter.fill(this, START_XB, START_YB+SOUTH_1B, EAST_1B, 1, Terrain.MOUNTAIN_N);
        Painter.fill(this, START_XB, START_YB+SOUTH_1B-1, 1, 1, Terrain.MOUNTAIN_ELBOW_SW);
        Painter.fill(this, START_XB+EAST_1B, START_YB+SOUTH_1B, 1, SOUTH_2B, Terrain.MOUNTAIN_E);
        Painter.fill(this, START_XB+EAST_1B, START_YB+SOUTH_1B, 1, 1, Terrain.MOUNTAIN_CORNER_NE);
        Painter.fill(this, START_XB, START_YB+SOUTH_1B+SOUTH_2B, EAST_2B, 1, Terrain.MOUNTAIN_S);
        Painter.fill(this, START_XB+EAST_1B, START_YB+SOUTH_1B+SOUTH_2B, 1, 1, Terrain.MOUNTAIN_CORNER_SE);
        Painter.fill(this, START_XB, START_YB+SOUTH_1B+SOUTH_2B, 1, END_YB, Terrain.MOUNTAIN_E);
        Painter.fill(this, START_XB, START_YB+SOUTH_1B+SOUTH_2B, 1, 1, Terrain.MOUNTAIN_ELBOW_NW);

        //Contour 3
        Painter.fill(this, START_XC, START_YC, 1, SOUTH_1C, Terrain.MOUNTAIN_E);
        Painter.fill(this, START_XC, START_YC+SOUTH_1C, EAST_1C, 1, Terrain.MOUNTAIN_N);
        Painter.fill(this, START_XC, START_YC+SOUTH_1C-1, 1, 1, Terrain.MOUNTAIN_ELBOW_SW);
        Painter.fill(this, START_XC+EAST_1C, START_YC+SOUTH_1C, 1, SOUTH_2C, Terrain.MOUNTAIN_E);
        Painter.fill(this, START_XC+EAST_1C, START_YC+SOUTH_1C, 1, 1, Terrain.MOUNTAIN_CORNER_NE);
        Painter.fill(this, START_XC, START_YC+SOUTH_1C+SOUTH_2C, EAST_2C, 1, Terrain.MOUNTAIN_S);
        Painter.fill(this, START_XC+EAST_1C, START_YC+SOUTH_1C+SOUTH_2C, 1, 1, Terrain.MOUNTAIN_CORNER_SE);
        Painter.fill(this, START_XC, START_YC+SOUTH_1C+SOUTH_2C, 1, END_YC, Terrain.MOUNTAIN_E);
        Painter.fill(this, START_XC, START_YC+SOUTH_1C+SOUTH_2C, 1, 1, Terrain.MOUNTAIN_ELBOW_NW);

        Painter.fill(this, NW_CORNER_X, NW_CORNER_Y , SQUARE_WIDTH, 1, Terrain.MOUNTAIN_N);
        //Painter.fill(this, NW_CORNER_X-1, NW_CORNER_Y , 1, 1, Terrain.MOUNTAIN_CORNER_NW);
        //Painter.fill(this, NW_CORNER_X-1, NW_CORNER_Y+1 , 1, SQUARE_HEIGHT, Terrain.MOUNTAIN_W);
        //Painter.fill(this, NW_CORNER_X -1, NW_CORNER_Y+SQUARE_HEIGHT , 1, 1, Terrain.MOUNTAIN_CORNER_SW);
        Painter.fill(this, NW_CORNER_X , NW_CORNER_Y+SQUARE_HEIGHT , SQUARE_WIDTH, 1, Terrain.MOUNTAIN_S);
        Painter.fill(this, NE_CORNER_X , NW_CORNER_Y+SQUARE_HEIGHT , 1, 1, Terrain.MOUNTAIN_CORNER_SE);
        Painter.fill(this, NE_CORNER_X, NW_CORNER_Y , 1, SQUARE_HEIGHT, Terrain.MOUNTAIN_E);
        Painter.fill(this, NE_CORNER_X, NW_CORNER_Y, 1, 1, Terrain.MOUNTAIN_CORNER_NE);


        boolean[] patch = Patch.generate( 0.45f, 6 );
        for (int i=0; i < LENGTH; i++) {
            if (map[i] == Terrain.EMPTY && patch[i] ) {
                map[i] = Terrain.HIGH_GRASS;
            }
        }


        //Old road
        Painter.fill(this, ROAD_START_X, ROAD_START_Y, 1, ROAD_SOUTH_1, Terrain.EMPTY_SP);
        Painter.fill(this, ROAD_START_X, ROAD_START_Y+ROAD_SOUTH_1, ROAD_EAST_1, 1, Terrain.EMPTY_SP);
        Painter.fill(this, ROAD_START_X+ROAD_EAST_1, ROAD_START_Y+ROAD_SOUTH_1, 1, ROAD_SOUTH_2, Terrain.EMPTY_SP);
        Painter.fill(this, ROAD_END_X, ROAD_END_Y, ROAD_EAST_2, 1, Terrain.EMPTY_SP);

        Painter.fill(this, 20, 0, 1, 14, Terrain.EMPTY_SP);


        Painter.fill(this, 21, 35, 3, 1, Terrain.RUBBLE);

        down = 1822;
        map[down] = Terrain.EXIT;

        west = 1151;
        map[west] = Terrain.WEST;

        east = 1098;
        map[east] = Terrain.EAST;

        north = 75;
        map[north] = Terrain.NORTH;

        return true;
    }

    @Override
    protected void decorate() {
       /* for (int i=WIDTH + 1; i < LENGTH - WIDTH; i++) {
            if (map[i] == Terrain.GRADUAL_SLOPE) {
                int n = 0;
                if (map[i + 1] == Terrain.GRADUAL_SLOPE) {
                    n++;
                }
                if (map[i - 1] == Terrain.GRADUAL_SLOPE) {
                    n++;
                }
                if (map[i + WIDTH] == Terrain.GRADUAL_SLOPE) {
                    n++;
                }
                if (map[i - WIDTH] == Terrain.GRADUAL_SLOPE) {
                    n++;
                }
                if (Random.Int( 4 ) <= n) {
                    map[i] = Terrain.GRADUAL_SLOPE;
                }
            }
        }
        for (int i=WIDTH + 1; i < LENGTH - WIDTH; i++) {
            if (map[i] == Terrain.GRADUAL_SLOPE) {
                int n = 0;
                if (map[i + 1] == Terrain.HILL) {
                    n++;
                }
                if (map[i - 1] == Terrain.HILL) {
                    n++;
                }
                if (map[i + WIDTH] == Terrain.HILL) {
                    n++;
                }
                if (map[i - WIDTH] == Terrain.HILL) {
                    n++;
                }
                if (Random.Int( 8 ) <= n) {
                    map[i] = Terrain.HILL;
                }
            }

        }*/
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
                        Random.IntRange( 1, 2 ) +
                                Random.IntRange( 1, 2) * WIDTH;
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

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

public class B014HillLevel extends Level {

    {
        color1 = 0x4b6636;
        color2 = 0xf2f2f2;
        viewDistance = 16;
    }

    //NE contours
    private static final int NW_CORNER_X	= 28;
    private static final int NW_CORNER_Y	= 0;
    private static final int SQUARE_WIDTH	= 21;
    private static final int SQUARE_HEIGHT	= 22;
    private static final int NE_CORNER_X	= NW_CORNER_X + SQUARE_WIDTH;

    private static final int NW_CORNER_X2	= 25;
    private static final int NW_CORNER_Y2	= 0;
    private static final int SQUARE_WIDTH2	= 24;
    private static final int SQUARE_HEIGHT2	= 27;
    private static final int NE_CORNER_X2	= NW_CORNER_X2 + SQUARE_WIDTH2;

    private static final int NW_CORNER_X3	= 23;
    private static final int NW_CORNER_Y3	= 0;
    private static final int SQUARE_WIDTH3	= 26;
    private static final int SQUARE_HEIGHT3	= 40;
    private static final int NE_CORNER_X3	= NW_CORNER_X3 + SQUARE_WIDTH3;

    //Road
    private static final int ROAD_START_X   = 20;
    private static final int ROAD_WEST_1    = 15;
    private static final int ROAD_WEST_2    = 1;
    private static final int ROAD_END_X     = 5;

    private static final int ROAD_START_Y   = 49;
    private static final int ROAD_NORTH_1   = 12;
    private static final int ROAD_NORTH_2   = 20;
    private static final int ROAD_NORTH_3   = 17;
    private static final int ROAD_END_Y     = 0;

    //SW contours
    private static final int NW_CORNER_X4	= 0;
    private static final int NW_CORNER_Y4	= 42;
    private static final int SQUARE_WIDTH4	= 14;
    private static final int SQUARE_HEIGHT4	= 8;
    private static final int NE_CORNER_X4	= NW_CORNER_X4 + SQUARE_WIDTH4;

    private static final int NW_CORNER_X5	= 0;
    private static final int NW_CORNER_Y5	= 44;
    private static final int SQUARE_WIDTH5	= 11;
    private static final int SQUARE_HEIGHT5	= 6;
    private static final int NE_CORNER_X5	= NW_CORNER_X5 + SQUARE_WIDTH5;

    private static final int NW_CORNER_X6	= 0;
    private static final int NW_CORNER_Y6	= 46;
    private static final int SQUARE_WIDTH6	= 9;
    private static final int SQUARE_HEIGHT6	= 4;
    private static final int NE_CORNER_X6	= NW_CORNER_X6 + SQUARE_WIDTH6;


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

        //NE hills
        Painter.fill(this, NW_CORNER_X-1, NW_CORNER_Y , 1, 1, Terrain.MOUNTAIN_CORNER_NW);
        Painter.fill(this, NW_CORNER_X-1, NW_CORNER_Y , 1, SQUARE_HEIGHT, Terrain.MOUNTAIN_W);
        Painter.fill(this, NW_CORNER_X -1, NW_CORNER_Y+SQUARE_HEIGHT , 1, 1, Terrain.MOUNTAIN_CORNER_SW);
        Painter.fill(this, NW_CORNER_X , NW_CORNER_Y+SQUARE_HEIGHT , SQUARE_WIDTH2, 1, Terrain.MOUNTAIN_S);
        Painter.fill(this, NE_CORNER_X , NW_CORNER_Y+SQUARE_HEIGHT , 1, 1, Terrain.MOUNTAIN_CORNER_SE);
        Painter.fill(this, NE_CORNER_X, NW_CORNER_Y , 1, SQUARE_HEIGHT, Terrain.MOUNTAIN_E);
        Painter.fill(this, NE_CORNER_X, NW_CORNER_Y, 1, 1, Terrain.MOUNTAIN_CORNER_NE);


        Painter.fill(this, NW_CORNER_X2-1, NW_CORNER_Y2 , 1, 1, Terrain.MOUNTAIN_CORNER_NW);
        Painter.fill(this, NW_CORNER_X2-1, NW_CORNER_Y2 , 1, SQUARE_HEIGHT2, Terrain.MOUNTAIN_W);
        Painter.fill(this, NW_CORNER_X2 -1, NW_CORNER_Y2+SQUARE_HEIGHT2 , 1, 1, Terrain.MOUNTAIN_CORNER_SW);
        Painter.fill(this, NW_CORNER_X2 , NW_CORNER_Y2+SQUARE_HEIGHT2 , SQUARE_WIDTH2, 1, Terrain.MOUNTAIN_S);
        Painter.fill(this, NE_CORNER_X2 , NW_CORNER_Y2+SQUARE_HEIGHT2 , 1, 1, Terrain.MOUNTAIN_CORNER_SE);
        Painter.fill(this, NE_CORNER_X2, NW_CORNER_Y2 , 1, SQUARE_HEIGHT2, Terrain.MOUNTAIN_E);
        Painter.fill(this, NE_CORNER_X2, NW_CORNER_Y2, 1, 1, Terrain.MOUNTAIN_CORNER_NE);


        Painter.fill(this, NW_CORNER_X3-1, NW_CORNER_Y3 , 1, 1, Terrain.MOUNTAIN_CORNER_NW);
        Painter.fill(this, NW_CORNER_X3-1, NW_CORNER_Y3 , 1, SQUARE_HEIGHT3, Terrain.MOUNTAIN_W);
        Painter.fill(this, NW_CORNER_X3 -1, NW_CORNER_Y3+SQUARE_HEIGHT3 , 1, 1, Terrain.MOUNTAIN_CORNER_SW);
        Painter.fill(this, NW_CORNER_X3 , NW_CORNER_Y3+SQUARE_HEIGHT3 , SQUARE_WIDTH3, 1, Terrain.MOUNTAIN_S);
        Painter.fill(this, NE_CORNER_X3 , NW_CORNER_Y3+SQUARE_HEIGHT3 , 1, 1, Terrain.MOUNTAIN_CORNER_SE);
        Painter.fill(this, NE_CORNER_X3 , NW_CORNER_Y3 , 1, SQUARE_HEIGHT3, Terrain.MOUNTAIN_E);
        Painter.fill(this, NE_CORNER_X3, NW_CORNER_Y3, 1, 1, Terrain.MOUNTAIN_CORNER_NE);

        //Old road
        Painter.fill(this, ROAD_START_X, ROAD_START_Y-ROAD_NORTH_1, 1, ROAD_NORTH_1, Terrain.EMPTY_SP);
        Painter.fill(this, ROAD_START_X-ROAD_WEST_1, ROAD_START_Y-ROAD_NORTH_1, ROAD_WEST_1, 1, Terrain.EMPTY_SP);
        Painter.fill(this, ROAD_START_X-ROAD_WEST_1, ROAD_START_Y-ROAD_NORTH_1-ROAD_NORTH_2, 1, ROAD_NORTH_2, Terrain.EMPTY_SP);
        Painter.fill(this, ROAD_END_X, ROAD_END_Y+ROAD_NORTH_3, ROAD_WEST_2, 1, Terrain.EMPTY_SP);
        Painter.fill(this, ROAD_END_X, 1 , 1, ROAD_NORTH_3, Terrain.EMPTY_SP);

        //SW hills
        Painter.fill(this, NW_CORNER_X4, NW_CORNER_Y4 , SQUARE_WIDTH4, 1, Terrain.MOUNTAIN_N);
        Painter.fill(this, NE_CORNER_X4, NW_CORNER_Y4 , 1, SQUARE_HEIGHT4, Terrain.MOUNTAIN_E);
        Painter.fill(this, NE_CORNER_X4, NW_CORNER_Y4, 1, 1, Terrain.MOUNTAIN_CORNER_NE);


        Painter.fill(this, NW_CORNER_X5, NW_CORNER_Y5 , SQUARE_WIDTH5, 1, Terrain.MOUNTAIN_N);
        Painter.fill(this, NE_CORNER_X5, NW_CORNER_Y5 , 1, SQUARE_HEIGHT5, Terrain.MOUNTAIN_E);
        Painter.fill(this, NE_CORNER_X5, NW_CORNER_Y5, 1, 1, Terrain.MOUNTAIN_CORNER_NE);


        Painter.fill(this, NW_CORNER_X6, NW_CORNER_Y6 , SQUARE_WIDTH6, 1, Terrain.MOUNTAIN_N);
        Painter.fill(this, NE_CORNER_X6 , NW_CORNER_Y6 , 1, SQUARE_HEIGHT6, Terrain.MOUNTAIN_E);
        Painter.fill(this, NE_CORNER_X6 , NW_CORNER_Y6, 1, 1, Terrain.MOUNTAIN_CORNER_NE);


        Painter.fill(this, 0,0,1,49, Terrain.EMPTY);

        west = 1151;
        map[west] = Terrain.WEST;

        east = 1098;
        map[east] = Terrain.EAST;

        north = 65;
        map[north] = Terrain.NORTH;

        south =  2425;
        map[south] = Terrain.SOUTH;

        return true;
    }

    @Override
    protected void decorate() {
        for (int i = WIDTH; i < LENGTH; i++) {
            if (map[i-50] == Terrain.MOUNTAIN_N && map[i] == Terrain.MOUNTAIN_W) {
                map[i] = Terrain.MOUNTAIN_W;
            }
        }

        for (int i = WIDTH; i < LENGTH - WIDTH; i++) {
            if (map[i-1] == Terrain.MOUNTAIN_S && map[i] == Terrain.MOUNTAIN_E ) {
                map[i] = Terrain.MOUNTAIN_S;
            }
        }

        for (int i = WIDTH; i < LENGTH - WIDTH; i++) {
            if (map[i] == Terrain.MOUNTAIN_E && map[i] == Terrain.EMPTY ) {
                map[i] = Terrain.MOUNTAIN_E;
            }
        }

        for (int i = WIDTH; i < LENGTH - WIDTH; i++) {
            if (map[i] == Terrain.EMPTY && map[i+1] == Terrain.MOUNTAIN_N ) {
                map[i] = Terrain.MOUNTAIN_N;
            }
        }


        boolean[] patch = Patch.generate( 0.45f, 6 );
        for (int i=0; i < LENGTH-WIDTH; i++) {
            if (map[i] == Terrain.EMPTY && patch[i] ) {
                map[i] = Terrain.HIGH_GRASS;
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

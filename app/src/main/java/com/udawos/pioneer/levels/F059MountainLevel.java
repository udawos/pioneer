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

public class F059MountainLevel extends Level {

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

        //West mountain east side
        Painter.fill(this, 35, 5, 1, 38, Terrain.MOUNTAIN_E);
        Painter.fill(this, 34, 6, 1, 36, Terrain.MOUNTAIN_E);
        Painter.fill(this, 33, 7, 1, 31, Terrain.MOUNTAIN_E);
        Painter.fill(this, 32, 8, 1, 26, Terrain.MOUNTAIN_E);
        Painter.fill(this, 31, 12, 1, 21, Terrain.MOUNTAIN_E);
        Painter.fill(this, 30, 13, 1, 18, Terrain.MOUNTAIN_E);
        Painter.fill(this, 29, 14, 1, 16, Terrain.MOUNTAIN_E);
        Painter.fill(this, 12, 15, 1, 14, Terrain.MOUNTAIN_E);
        Painter.fill(this, 11, 16, 1, 12, Terrain.MOUNTAIN_E);
        Painter.fill(this, 10, 17, 1, 10, Terrain.MOUNTAIN_E);
        Painter.fill(this, 9, 18, 1, 8, Terrain.MOUNTAIN_E);
        Painter.fill(this, 8, 19, 1, 6, Terrain.MOUNTAIN_E);
        Painter.fill(this, 7, 20, 1, 4, Terrain.MOUNTAIN_E);
        Painter.fill(this, 6, 21, 1, 2, Terrain.MOUNTAIN_E);

        //West mountain south side
        Painter.fill(this,0, 42, 36,1, Terrain.MOUNTAIN_S);
        Painter.fill(this,0, 41, 34,1, Terrain.MOUNTAIN_S);
        Painter.fill(this,0, 37, 33,1, Terrain.MOUNTAIN_S);
        Painter.fill(this,0, 33, 32,1, Terrain.MOUNTAIN_S);
        Painter.fill(this,0, 32, 31,1, Terrain.MOUNTAIN_S);
        Painter.fill(this,0, 30, 30,1, Terrain.MOUNTAIN_S);
        Painter.fill(this,0, 29, 29,1, Terrain.MOUNTAIN_S);
        Painter.fill(this,0, 28, 13,1, Terrain.MOUNTAIN_S);
        Painter.fill(this,0, 27, 12,1, Terrain.MOUNTAIN_S);
        Painter.fill(this,0, 26, 11,1, Terrain.MOUNTAIN_S);
        Painter.fill(this,0, 25, 10,1, Terrain.MOUNTAIN_S);
        Painter.fill(this,0, 24, 9,1, Terrain.MOUNTAIN_S);
        Painter.fill(this,0, 23, 8,1, Terrain.MOUNTAIN_S);
        Painter.fill(this,0, 22, 7,1, Terrain.MOUNTAIN_S);

        //West mountain north side
        Painter.fill(this,0, 5, 36,1, Terrain.MOUNTAIN_N);
        Painter.fill(this,0, 6, 35,1, Terrain.MOUNTAIN_N);
        Painter.fill(this,0, 7, 34,1, Terrain.MOUNTAIN_N);
        Painter.fill(this,0, 8, 33,1, Terrain.MOUNTAIN_N);
        Painter.fill(this,0, 12, 32,1, Terrain.MOUNTAIN_N);
        Painter.fill(this,0, 13, 31,1, Terrain.MOUNTAIN_N);
        Painter.fill(this,0, 14, 29,1, Terrain.MOUNTAIN_N);
        Painter.fill(this,0, 15, 13,1, Terrain.MOUNTAIN_N);
        Painter.fill(this,0, 16, 12,1, Terrain.MOUNTAIN_N);
        Painter.fill(this,0, 17, 11,1, Terrain.MOUNTAIN_N);
        Painter.fill(this,0, 18, 10,1, Terrain.MOUNTAIN_N);
        Painter.fill(this,0, 19, 9,1, Terrain.MOUNTAIN_N);
        Painter.fill(this,0, 20, 8,1, Terrain.MOUNTAIN_N);
        Painter.fill(this,0, 21, 7,1, Terrain.MOUNTAIN_N);

        //West mountain corners
        Painter.fill(this,35, 42, 1, 1, Terrain.MOUNTAIN_CORNER_SE);
        Painter.fill(this,34, 41, 1, 1, Terrain.MOUNTAIN_CORNER_SE);
        Painter.fill(this,33, 37, 1, 1, Terrain.MOUNTAIN_CORNER_SE);
        Painter.fill(this,32, 33, 1, 1, Terrain.MOUNTAIN_CORNER_SE);
        Painter.fill(this,31, 32, 1, 1, Terrain.MOUNTAIN_CORNER_SE);
        Painter.fill(this,30, 30, 1, 1, Terrain.MOUNTAIN_CORNER_SE);
        Painter.fill(this,29, 29, 1, 1, Terrain.MOUNTAIN_CORNER_SE);

        Painter.fill(this,12, 28, 1, 1, Terrain.MOUNTAIN_CORNER_SE);
        Painter.fill(this,11, 27, 1, 1, Terrain.MOUNTAIN_CORNER_SE);
        Painter.fill(this,10, 26, 1, 1, Terrain.MOUNTAIN_CORNER_SE);
        Painter.fill(this, 9, 25, 1, 1, Terrain.MOUNTAIN_CORNER_SE);
        Painter.fill(this, 8, 24, 1, 1, Terrain.MOUNTAIN_CORNER_SE);
        Painter.fill(this, 7, 23, 1, 1, Terrain.MOUNTAIN_CORNER_SE);

        Painter.fill(this, 7, 20, 1, 1, Terrain.MOUNTAIN_CORNER_NE);
        Painter.fill(this, 8, 19, 1, 1, Terrain.MOUNTAIN_CORNER_NE);
        Painter.fill(this, 9, 18, 1, 1, Terrain.MOUNTAIN_CORNER_NE);
        Painter.fill(this, 10, 17, 1, 1, Terrain.MOUNTAIN_CORNER_NE);
        Painter.fill(this, 11, 16, 1, 1, Terrain.MOUNTAIN_CORNER_NE);
        Painter.fill(this, 12, 15, 1, 1, Terrain.MOUNTAIN_CORNER_NE);

        Painter.fill(this, 29, 14, 1, 1, Terrain.MOUNTAIN_CORNER_NE);
        Painter.fill(this, 30, 13, 1, 1, Terrain.MOUNTAIN_CORNER_NE);
        Painter.fill(this, 31, 12, 1, 1, Terrain.MOUNTAIN_CORNER_NE);

        Painter.fill(this, 32, 8, 1, 1, Terrain.MOUNTAIN_CORNER_NE);
        Painter.fill(this, 33, 7, 1, 1, Terrain.MOUNTAIN_CORNER_NE);
        Painter.fill(this, 34, 6, 1, 1, Terrain.MOUNTAIN_CORNER_NE);
        Painter.fill(this, 35, 5, 1, 1, Terrain.MOUNTAIN_CORNER_NE);

        //West mountain summit
        Painter.fill(this, 0, 22, 6, 1, Terrain.HILL);



        //East mountain west side
        Painter.fill(this, 37, 2, 1, 44, Terrain.MOUNTAIN_W);
        Painter.fill(this, 38, 3, 1, 42, Terrain.MOUNTAIN_W);
        Painter.fill(this, 39, 4, 1, 40, Terrain.MOUNTAIN_W);
        Painter.fill(this, 40, 5, 1, 38, Terrain.MOUNTAIN_W);
        Painter.fill(this, 41, 6, 1, 36, Terrain.MOUNTAIN_W);
        Painter.fill(this, 42, 7, 1, 34, Terrain.MOUNTAIN_W);
        Painter.fill(this, 43, 8, 1, 32, Terrain.MOUNTAIN_W);
        Painter.fill(this, 44, 9, 1, 30, Terrain.MOUNTAIN_W);
        Painter.fill(this, 45, 10, 1, 28, Terrain.MOUNTAIN_W);
        Painter.fill(this, 46, 11, 1, 26, Terrain.MOUNTAIN_W);
        Painter.fill(this, 47, 12, 1, 24, Terrain.MOUNTAIN_W);
        Painter.fill(this, 48, 21, 1, 14, Terrain.MOUNTAIN_W);
        Painter.fill(this, 49, 22, 1, 12, Terrain.MOUNTAIN_W);



        //East mountain south side
        Painter.fill(this, 49, 33, 1, 1, Terrain.MOUNTAIN_S);
        Painter.fill(this, 48, 34, 2, 1, Terrain.MOUNTAIN_S);
        Painter.fill(this, 47, 35, 3, 1, Terrain.MOUNTAIN_S);
        Painter.fill(this, 46, 36, 4, 1, Terrain.MOUNTAIN_S);
        Painter.fill(this, 45, 37, 5, 1, Terrain.MOUNTAIN_S);
        Painter.fill(this, 44, 38, 6, 1, Terrain.MOUNTAIN_S);
        Painter.fill(this, 43, 39, 7, 1, Terrain.MOUNTAIN_S);
        Painter.fill(this, 42, 40, 8, 1, Terrain.MOUNTAIN_S);
        Painter.fill(this, 41, 41, 9, 1, Terrain.MOUNTAIN_S);
        Painter.fill(this, 40, 42, 10, 1, Terrain.MOUNTAIN_S);
        Painter.fill(this, 39, 43, 11, 1, Terrain.MOUNTAIN_S);
        Painter.fill(this, 38, 44, 12, 1, Terrain.MOUNTAIN_S);
        Painter.fill(this, 37, 45, 13, 1, Terrain.MOUNTAIN_S);


        //East mountain north side
        Painter.fill(this, 37, 2, 13, 1, Terrain.MOUNTAIN_N);
        Painter.fill(this, 38, 3, 12, 1, Terrain.MOUNTAIN_N);
        Painter.fill(this, 39, 4, 11, 1, Terrain.MOUNTAIN_N);
        Painter.fill(this, 40, 5, 10, 1, Terrain.MOUNTAIN_N);
        Painter.fill(this, 41, 6, 9, 1, Terrain.MOUNTAIN_N);
        Painter.fill(this, 42, 7, 8, 1, Terrain.MOUNTAIN_N);
        Painter.fill(this, 43, 8, 7, 1, Terrain.MOUNTAIN_N);
        Painter.fill(this, 44, 9, 6, 1, Terrain.MOUNTAIN_N);
        Painter.fill(this, 45, 10, 5, 1, Terrain.MOUNTAIN_N);
        Painter.fill(this, 46, 11, 4, 1, Terrain.MOUNTAIN_N);
        Painter.fill(this, 47, 12, 3, 1, Terrain.MOUNTAIN_N);
        Painter.fill(this, 48, 21, 2, 1, Terrain.MOUNTAIN_N);
        Painter.fill(this, 49, 22, 1, 1, Terrain.MOUNTAIN_N);
        Painter.fill(this, 49, 12, 2, 1, Terrain.MOUNTAIN_N);

        //East mountain corners
        Painter.fill(this, 37, 2, 1, 1, Terrain.MOUNTAIN_CORNER_NW);
        Painter.fill(this, 38, 3, 1, 1, Terrain.MOUNTAIN_CORNER_NW);
        Painter.fill(this, 39, 4, 1, 1, Terrain.MOUNTAIN_CORNER_NW);
        Painter.fill(this, 40, 5, 1, 1, Terrain.MOUNTAIN_CORNER_NW);
        Painter.fill(this, 41, 6, 1, 1, Terrain.MOUNTAIN_CORNER_NW);
        Painter.fill(this, 42, 7, 1, 1, Terrain.MOUNTAIN_CORNER_NW);
        Painter.fill(this, 43, 8, 1, 1, Terrain.MOUNTAIN_CORNER_NW);
        Painter.fill(this, 44, 9, 1, 1, Terrain.MOUNTAIN_CORNER_NW);
        Painter.fill(this, 45, 10, 1, 1, Terrain.MOUNTAIN_CORNER_NW);
        Painter.fill(this, 46, 11, 1, 1, Terrain.MOUNTAIN_CORNER_NW);
        Painter.fill(this, 47, 12, 1, 1, Terrain.MOUNTAIN_CORNER_NW);
        Painter.fill(this, 48, 21, 1, 1, Terrain.MOUNTAIN_CORNER_NW);
        Painter.fill(this, 49, 22, 1, 1, Terrain.MOUNTAIN_CORNER_NW);

        Painter.fill(this, 37, 45, 1, 1, Terrain.MOUNTAIN_CORNER_SW);
        Painter.fill(this, 38, 44, 1, 1, Terrain.MOUNTAIN_CORNER_SW);
        Painter.fill(this, 39, 43, 1, 1, Terrain.MOUNTAIN_CORNER_SW);
        Painter.fill(this, 40, 42, 1, 1, Terrain.MOUNTAIN_CORNER_SW);
        Painter.fill(this, 41, 41, 1, 1, Terrain.MOUNTAIN_CORNER_SW);
        Painter.fill(this, 42, 40, 1, 1, Terrain.MOUNTAIN_CORNER_SW);
        Painter.fill(this, 43, 39, 1, 1, Terrain.MOUNTAIN_CORNER_SW);
        Painter.fill(this, 44, 38, 1, 1, Terrain.MOUNTAIN_CORNER_SW);
        Painter.fill(this, 45, 37, 1, 1, Terrain.MOUNTAIN_CORNER_SW);
        Painter.fill(this, 46, 36, 1, 1, Terrain.MOUNTAIN_CORNER_SW);
        Painter.fill(this, 47, 35, 1, 1, Terrain.MOUNTAIN_CORNER_SW);
        Painter.fill(this, 48, 34, 1, 1, Terrain.MOUNTAIN_CORNER_SW);
        Painter.fill(this, 49, 33, 1, 1, Terrain.MOUNTAIN_CORNER_SW);

        //East mountain summit
        Painter.fill(this, 1, 8, 1, 1, Terrain.HILL);

        down = 1529;
        map[down] = Terrain.EXIT;

        west = 1701;
        map[west] = Terrain.WEST;

        east = 1048;
        map[east] = Terrain.EAST;

        north = 75;
        map[north] = Terrain.NORTH;

        south =  2425;
        map[south] = Terrain.SOUTH;

        return true;
    }

    @Override
    protected void decorate() {
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

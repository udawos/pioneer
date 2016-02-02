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

public class F057MountainLevel extends Level {

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

        //mountain east
        Painter.fill(this, 49, 2, 1, 46, Terrain.MOUNTAIN_E);
        Painter.fill(this, 48, 3, 1, 44, Terrain.MOUNTAIN_E);
        Painter.fill(this, 47, 4, 1, 42, Terrain.MOUNTAIN_E);
        Painter.fill(this, 46, 4, 1, 41, Terrain.MOUNTAIN_E);
        Painter.fill(this, 45, 4, 1, 40, Terrain.MOUNTAIN_E);
        Painter.fill(this, 44, 4, 1, 39, Terrain.MOUNTAIN_E);
        Painter.fill(this, 43, 4, 1, 38, Terrain.MOUNTAIN_E);
        Painter.fill(this, 42, 4, 1, 37, Terrain.MOUNTAIN_E);
        Painter.fill(this, 41, 5, 1, 35, Terrain.MOUNTAIN_E);
        Painter.fill(this, 40, 6, 1, 33, Terrain.MOUNTAIN_E);

        //mountain south
        Painter.fill(this, 9, 38, 32, 1, Terrain.MOUNTAIN_S);
        Painter.fill(this, 8, 39, 34, 1, Terrain.MOUNTAIN_S);
        Painter.fill(this, 7, 40, 36, 1, Terrain.MOUNTAIN_S);
        Painter.fill(this, 6, 41, 38, 1, Terrain.MOUNTAIN_S);
        Painter.fill(this, 5, 42, 40, 1, Terrain.MOUNTAIN_S);
        Painter.fill(this, 4, 43, 42, 1, Terrain.MOUNTAIN_S);
        Painter.fill(this, 3, 44, 44, 1, Terrain.MOUNTAIN_S);
        Painter.fill(this, 2, 45, 46, 1, Terrain.MOUNTAIN_S);
        Painter.fill(this, 1, 46, 48, 1, Terrain.MOUNTAIN_S);
        Painter.fill(this, 0, 47, 50, 1, Terrain.MOUNTAIN_S);
        Painter.fill(this, 28 ,44, 3, 4, Terrain.RUINED_WALL);

        //mountain north
        Painter.fill(this, 9, 6, 32, 1, Terrain.MOUNTAIN_N);
        Painter.fill(this, 8, 5, 34, 1, Terrain.MOUNTAIN_N);
        Painter.fill(this, 7, 4, 36, 1, Terrain.MOUNTAIN_N);
        Painter.fill(this, 1, 3, 48, 1, Terrain.MOUNTAIN_N);
        Painter.fill(this, 0, 2, 50, 1, Terrain.MOUNTAIN_N);

        //mountain west
        Painter.fill(this, 0, 2, 1, 47, Terrain.RUBBLE);
        Painter.fill(this, 1, 3, 1, 44, Terrain.MOUNTAIN_W);
        Painter.fill(this, 2, 4, 1, 42, Terrain.MOUNTAIN_W);
        Painter.fill(this, 3, 4, 1, 41, Terrain.MOUNTAIN_W);
        Painter.fill(this, 4, 4, 1, 40, Terrain.MOUNTAIN_W);
        Painter.fill(this, 5, 4, 1, 39, Terrain.MOUNTAIN_W);
        Painter.fill(this, 6, 4, 1, 38, Terrain.MOUNTAIN_W);
        Painter.fill(this, 7, 4, 1, 37, Terrain.MOUNTAIN_W);
        Painter.fill(this, 8, 5, 1, 35, Terrain.MOUNTAIN_W);
        Painter.fill(this, 9, 6, 1, 33, Terrain.MOUNTAIN_W);
        Painter.fill(this, 10, 7, 1, 31, Terrain.MOUNTAIN_W);
        Painter.fill(this, 11, 8, 1, 29, Terrain.MOUNTAIN_W);
        Painter.fill(this, 12, 9, 1, 27, Terrain.MOUNTAIN_W);
        Painter.fill(this, 13, 10, 1, 25, Terrain.MOUNTAIN_W);


        down = 2379;
        map[down] = Terrain.EXIT;

        west = 2401;
        map[west] = Terrain.WEST;

        east = 2448;
        map[east] = Terrain.EAST;

        north = 75;
        map[north] = Terrain.NORTH;

        south =  2425;
        map[south] = Terrain.SOUTH;

        return true;
    }

    @Override
    protected void decorate() {
        for (int i=WIDTH; i < LENGTH - WIDTH; i++) {
            if (map[i] == Terrain.MOUNTAIN_S && map[i+1] == Terrain.EMPTY && map[i+51] == Terrain.EMPTY ) {
                map[i+1] = Terrain.MOUNTAIN_CORNER_SE;
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
            if (map[i] == Terrain.MOUNTAIN_S && map[i - 1] == Terrain.MOUNTAIN_S && map[i-50] == Terrain.MOUNTAIN_E) {
                map[i] = Terrain.MOUNTAIN_CORNER_SE;
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

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
import com.udawos.utils.Bundle;
import com.udawos.utils.Random;

public class E044GrassLevel extends Level {

    {
        color1 = 0x4b6636;
        color2 = 0xf2f2f2;
        viewDistance = 16;
    }

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
        super.storeInBundle(bundle);
    }

    @Override
    public void restoreFromBundle( Bundle bundle ) {
        super.restoreFromBundle(bundle);
    }

    @Override
    protected boolean build() {

        //Mountain south side
        Painter.fill(this, 0, 16, 50, 1, Terrain.MOUNTAIN_S);
        Painter.fill(this, 0, 17, 50, 1, Terrain.MOUNTAIN_S);
        Painter.fill(this, 0, 18, 50, 1, Terrain.MOUNTAIN_S);
        Painter.fill(this, 0, 19, 50, 1, Terrain.MOUNTAIN_S);
        Painter.fill(this, 0, 20, 50, 1, Terrain.MOUNTAIN_S);
        Painter.fill(this, 0, 21, 50, 1, Terrain.MOUNTAIN_S);
        Painter.fill(this, 0, 22, 50, 1, Terrain.MOUNTAIN_S);
        Painter.fill(this, 0, 23, 50, 1, Terrain.MOUNTAIN_S);
        Painter.fill(this, 0, 24, 50, 1, Terrain.MOUNTAIN_S);
        Painter.fill(this, 0, 25, 50, 2, Terrain.MOUNTAIN_S);
        Painter.fill(this, 0, 27, 50, 1, Terrain.MOUNTAIN_S);
        Painter.fill(this, 0, 28, 50, 1, Terrain.MOUNTAIN_S);

        //Mountain north side
        Painter.fill(this, 0, 15, 50,1, Terrain.MOUNTAIN_N);
        Painter.fill(this, 0, 14, 50,1, Terrain.MOUNTAIN_N);
        Painter.fill(this, 0, 13, 50,1, Terrain.MOUNTAIN_N);
        Painter.fill(this, 0, 12, 50,1, Terrain.MOUNTAIN_N);
        Painter.fill(this, 0, 11, 50,1, Terrain.MOUNTAIN_N);
        Painter.fill(this, 0, 10, 50,1, Terrain.MOUNTAIN_N);
        Painter.fill(this, 0, 9, 50,1, Terrain.MOUNTAIN_N);
        Painter.fill(this, 0, 8, 50,1, Terrain.MOUNTAIN_N);
        Painter.fill(this, 0, 7, 50,1, Terrain.MOUNTAIN_N);
        Painter.fill(this, 0, 6, 50,1, Terrain.MOUNTAIN_N);
        Painter.fill(this, 0, 5, 50,1, Terrain.MOUNTAIN_N);
        Painter.fill(this, 0, 4, 50,1, Terrain.MOUNTAIN_N);
        Painter.fill(this, 0, 3, 50,1, Terrain.MOUNTAIN_N);

        west = 1451;
        map[west] = Terrain.WEST;

        east = 1548;
        map[east] = Terrain.EAST;

        north = 75;
        map[north] = Terrain.NORTH;

        south =  2425;
        map[south] = Terrain.SOUTH;

        return true;
    }

    @Override
    protected void decorate() {
       for (int i=WIDTH + 1; i < LENGTH - WIDTH; i++) {
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
                        Random.IntRange(1, 2 ) +
                                Random.IntRange( 12, 13 ) * WIDTH;
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

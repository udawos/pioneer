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

public class BurntHouse extends Level {

    {
        color1 = 0x4b6636;
        color2 = 0xf2f2f2;
        viewDistance = 14;
    }


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

    private static final String DOOR	= "door";
    private static final String ENTERED	= "entered";
    private static final String DROPPED	= "droppped";

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


        boolean[] patch = Patch.generate( 0.45f, 6 );
        for (int i=0; i < LENGTH; i++) {
            if (map[i] == Terrain.EMPTY && patch[i] ) {
                map[i] = Terrain.GRADUAL_SLOPE;
            }
        }

        for (int i = WIDTH; i < LENGTH - WIDTH; i++) {

            if (map[i] == Terrain.GRADUAL_SLOPE) {
                int t = Terrain.GRADUAL_SLOPE_TILES;
                for (int j = 0; j < NEIGHBOURS4.length; j++) {
                    if ((Terrain.flags[map[i + NEIGHBOURS4[j]]] & Terrain.UNSTITCHABLE) != 0) {
                        t += 1 << j;
                    }
                }
                map[i] = t;
            }
        }



        //house exterior
        Painter.fill(this, 10, 24, 18, 13, Terrain.WALL);
        //entrance exterior
        Painter.fill( this, 22, 36, 5, 6, Terrain.WALL);

        //bedroom
        Painter.fill( this, 11, 25, 7, 4, Terrain.EMBERS);
        //bedroom entrance
        Painter.fill( this, 15, 29, 2, 1, Terrain.EMBERS);

        //den
        Painter.fill( this, 11, 30, 7, 5, Terrain.EMBERS);
        //den entrance
        Painter.fill( this, 18, 32, 1, 3, Terrain.EMBERS);

        //kitchen
        Painter.fill( this, 19, 25, 7, 6, Terrain.EMBERS);
        //kitchen entrance
        Painter.fill( this, 19, 31, 2, 1, Terrain.EMBERS);

        //foyer
        Painter.fill( this, 19, 32, 5, 4, Terrain.EMBERS);
        //foyer entrance
        int entry = 1823;
        map[entry] = Terrain.EMBERS;

        //closet
        Painter.fill( this, 25, 32, 2, 3, Terrain.EMBERS);
        //closet entrance
        int closet = 1674;
        map[closet] = Terrain.EMBERS;

        //entrance
        Painter.fill( this, 23, 37, 3, 4, Terrain.EMBERS);
        //entrance entrance (haha)
        Painter.fill( this, 24, 41, 1, 1, Terrain.EMBERS);

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
                        Random.IntRange( 3, 8 ) +
                                Random.IntRange( 1, 5) * WIDTH;
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

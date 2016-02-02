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
import com.udawos.pioneer.actors.Actor;
import com.udawos.pioneer.items.Generator;
import com.udawos.pioneer.items.Heap;
import com.udawos.pioneer.items.Item;
import com.udawos.pioneer.levels.painters.Painter;
import com.udawos.utils.Bundle;
import com.udawos.utils.Random;

public class ShatteredTower extends Level {

    {
        color1 = 0x4b6636;
        color2 = 0xf2f2f2;
        viewDistance = 16;
    }

    private static final int TOP			= 2;
    private static final int HALL_WIDTH		= 40;
    private static final int HALL_HEIGHT	= 40;


    private static final int LEFT	= (WIDTH - HALL_WIDTH) / 2;
    private static final int CENTER	= LEFT + HALL_WIDTH / 2;


    //this map needs a ruined stone tileset
    //wall= ruined wall
    //empty_sp = ruined empty_sp
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

        Painter.fill( this, LEFT, TOP, HALL_WIDTH, HALL_HEIGHT, Terrain.EMPTY );

        //tower base
        Painter.fill( this, 32, 8,
                6 , 6, Terrain.WALL );
        Painter.fill( this, 33, 9,
                4, 4, Terrain.EMPTY_SP );
        Painter.fill( this,  34, 13, 2, 1, Terrain.EMBERS);
        Painter.fill(this, 36, 7, 2, 3, Terrain.EMPTY);

        //outlying rubble
        Painter.fill( this, 29, 12, 1, 3, Terrain.WALL);
        Painter.fill( this, 39, 13, 1, 3, Terrain.WALL);

        //1st floor ruins
        Painter.fill( this, 24, 18, 2, 1, Terrain.WALL);
        Painter.fill( this, 25, 19, 2, 1, Terrain.WALL);
        Painter.fill( this, 26, 20, 2, 1, Terrain.WALL);
        Painter.fill( this, 27, 21, 2, 1, Terrain.WALL);
        Painter.fill( this, 28, 22, 2, 1, Terrain.WALL);

        //2nd floor ruins
        Painter.fill( this, 19, 28, 2, 1, Terrain.WALL);
        Painter.fill( this, 20, 29, 2, 1, Terrain.WALL);
        Painter.fill( this, 21, 30, 2, 1, Terrain.WALL);
        Painter.fill( this, 22, 31, 2, 1, Terrain.WALL);

        //3rd floor ruins
        Painter.fill( this, 14, 35, 2, 1, Terrain.WALL);
        Painter.fill( this, 15, 36, 2, 1, Terrain.WALL);
        Painter.fill( this, 16, 37, 2, 1, Terrain.WALL);

        return true;
    }

    @Override
    protected void decorate() {
        for (int i=WIDTH + 1; i < LENGTH - WIDTH; i++) {
            if (map[i] == Terrain.EMPTY) {
                int n = 0;
                if (map[i + 1] == Terrain.WALL) {
                    n++;
                }
                if (map[i - 1] == Terrain.WALL) {
                    n++;
                }
                if (map[i + WIDTH] == Terrain.WALL) {
                    n++;
                }
                if (map[i - WIDTH] == Terrain.WALL) {
                    n++;
                }
                if (Random.Int( 15) <= n) {
                    map[i] = Terrain.WALL;
                }
            }
        }
        west = 1151;
        map[west] = Terrain.WEST;

        east = 1098;
        map[east] = Terrain.EAST;

        north = 75;
        map[north] = Terrain.NORTH;

        south =  2425;
        map[south] = Terrain.SOUTH;
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

            int nItems = 3;
            while (Random.Float() < 0.4f) {
                nItems++;
            }

            for (int i=0; i < nItems; i++) {
                Heap.Type type = null;
                switch (Random.Int( 0 )) {
                    case 0:
                        type = Heap.Type.TOMB;
                        break;
                    default:
                        type = Heap.Type.TOMB;
                }
                drop( Generator.random(), 922 ).type = type;
            }

            for (Item item : itemsToSpawn) {
                int cell = 922;
                drop( item, cell ).type = Heap.Type.HEAP;
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

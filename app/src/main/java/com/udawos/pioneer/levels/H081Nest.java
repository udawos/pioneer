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
import com.udawos.pioneer.actors.mobs.npcs.CrazedShaman;
import com.udawos.utils.Bundle;
import com.udawos.utils.Random;

public class H081Nest extends Level {

    {
        color1 = 0x4b6636;
        color2 = 0xf2f2f2;
        viewDistance = 14;
    }


    @Override
    public String tilesTex() { return Assets.TILES_TUNDRA;
    }

    @Override
    public String waterTex() {
        return Assets.WATER_SEWERS;
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

    private int item1;
    private int item2;
    private int item3;
    private int item4;
    private int item5;
    private int item6;
    private int item7;
    private int item8;

    @Override
    protected boolean build() {

        boolean[] patch2 = Patch.generate( 0.65f, 6 );
        for (int i=822; i < 1219; i++) {
            if (map[i] == Terrain.EMPTY && patch2[i] ) {
                map[i] = Terrain.WATER;
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


        return true;
    }

    @Override
    protected void decorate() {

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
                if (Random.Int( 75 ) <= n) {
                    map[i] = Terrain.PEDESTAL;
                }
            }

        }
        item1 =  2375;
        map[item1] = Terrain.EMPTY;

        item2 =  2374;
        map[item2] = Terrain.EMPTY;

        item3 =  2373;
        map[item3] = Terrain.EMPTY;

        item4 =  2372;
        map[item4] = Terrain.EMPTY;

        item5 =  2371;
        map[item5] = Terrain.EMPTY;

        item6 =  2370;
        map[item6] = Terrain.EMPTY;

        item7 =  2369;
        map[item7] = Terrain.EMPTY;

        item8 =  2368;
        map[item8] = Terrain.EMPTY;
    }

    @Override
    public int nMobs() {
        return 1;
    }

    @Override
    protected void createMobs() {
       CrazedShaman.Quest.spawn(this);
    }

    public Actor respawner() {
        return null;
    }

    @Override
    protected void createItems() {

       //drop( new Bottle(), item1 );
       /* drop( new Buckle(), item2 );
        drop( new Cup(), item3 );
        drop( new Doorknob(), item4 );
        drop( new Magnet(), item5 );
        drop( new Spoon(), item6 );
        drop( new Tweezers(), item7 );
        drop( new Watch(), item8 );*/

    }




    @Override
    public String tileName( int tile ) {
        switch (tile) {
            case Terrain.WATER:
                return "Warm water";
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
            case Terrain.WATER:
                return "A surprisingly warm pool of water.";
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

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
import com.udawos.pioneer.actors.mobs.npcs.Transmitter;
import com.udawos.utils.Bundle;
import com.udawos.utils.Random;

//make it so a new drop pod appears every time you respawn

public class A01BaseLevel extends Level {

    {
        color1 = 0x4b6636;
        color2 = 0xf2f2f2;
        viewDistance = 14;
    }


    @Override
    public String tilesTex() { return Assets.TILES_BASE;
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

        int random = (Random.Int(1,10)*50);

        int pod1N = 1389 + random;
        map[pod1N] = Terrain.DROPPOD_NORTH;
        south = 1439 + random;
        map[south] = Terrain.EMPTY_SP;
        int pod1S = 1489 + random;
        map[pod1S] = Terrain.DROPPOD_SOUTH;
        int pod1E = 1440 + random;
        map[pod1E] = Terrain.DROPPOD_EAST;
        int pod1W = 1438 + random;
        map[pod1W] = Terrain.DROPPOD_WEST;

        //Ice for testing
        //Painter.fill(this, 20, 20, 5, 1, Terrain.WALL_DECO);

        int pod2N = 1125;
        map[pod2N] = Terrain.DROPPOD_NORTH;
        int pod2C = 1175;
        map[pod2C] = Terrain.EMPTY_SP;
        int pod2S = 1225;
        map[pod2S] = Terrain.DROPPOD_SOUTH;
        int pod2E = 1176;
        map[pod2E] = Terrain.DROPPOD_EAST;
        int pod2W = 1174;
        map[pod2W] = Terrain.DROPPOD_WEST;

        boolean[] patch = Patch.generate( 0.45f, 6 );
        for (int i=1100; i < 1500; i++) {
            if (map[i] == Terrain.EMPTY && patch[i] ) {
                map[i] = Terrain.EMBERS;
            }
        }



        west = 1151;
        map[west] = Terrain.WEST;

        north = 75;
        map[north] = Terrain.NORTH;



        return true;
    }

    @Override
    protected void decorate() {

        boolean[] patch = Patch.generate( 0.45f, 6 );
        for (int i=0; i < LENGTH; i++) {
            if (map[i] == Terrain.EMPTY && patch[i] ) {
                map[i] = Terrain.HIGH_GRASS;
            }
        }


    }

    @Override
    public int nMobs(){
        return 1;
    }

    @Override
    protected void createMobs() {
        Transmitter.Quest.spawn(this);

       // WildSheep.spawn(this);

    }

    public Actor respawner() {
        return null;
    }

    @Override
    protected void createItems() {
/*
        Item prize;
        switch (Random.Int( 1 )) {
            case 0:
                prize = Generator.random( Generator.Category.MISC );
                break;
            case 1:
                prize = Generator.random( Generator.Category.MISC);
                break;
            default:
                prize = Generator.random( Generator.Category.MISC );
                break;
        }

        this.drop(prize, 1225).type = Heap.Type.CHEST;
        */
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

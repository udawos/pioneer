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
import com.udawos.pioneer.actors.mobs.npcs.Barkeep;
import com.udawos.pioneer.items.Gold;
import com.udawos.pioneer.items.Heap;
import com.udawos.pioneer.items.Item;
import com.udawos.pioneer.levels.painters.Painter;
import com.udawos.utils.Bundle;
import com.udawos.utils.Random;

public class G064Pub extends Level {

    {
        color1 = 0x4b6636;
        color2 = 0xf2f2f2;
        viewDistance = 14;
    }


    @Override
    public String tilesTex() { return Assets.TILES_BAR;
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

    private int note;

    @Override
    protected boolean build() {

        //ruined compound exterior walls
        Painter.fill(this, 5, 8, 30, 1, Terrain.RUINED_WALL);
        Painter.fill(this, 5, 8, 1, 37, Terrain.RUINED_WALL);
        Painter.fill(this, 5, 44, 30,1, Terrain.RUINED_WALL);
        Painter.fill(this, 34, 8, 1, 37, Terrain.RUINED_WALL);
        Painter.fill(this, 19, 8, 2, 1, Terrain.EMPTY);

        //West outbuilding
        Painter.fill(this, 10, 12, 4, 1, Terrain.RUINED_WALL);
        Painter.fill(this, 10, 12, 1, 8, Terrain.RUINED_WALL);
        Painter.fill(this, 10, 20, 4, 1, Terrain.RUINED_WALL);
        Painter.fill(this, 13, 12, 1, 8, Terrain.RUINED_WALL);

        //East outbuilding
        Painter.fill(this, 21, 13, 9, 1, Terrain.RUINED_WALL);
        Painter.fill(this, 21, 13, 1, 7, Terrain.RUINED_WALL);
        Painter.fill(this, 21, 20, 9, 1, Terrain.RUINED_WALL);
        Painter.fill(this, 29, 13, 1, 7, Terrain.RUINED_WALL);

        //Barricades
        Painter.fill(this, 14, 20, 7, 1, Terrain.RUBBLE);
        Painter.fill(this, 30, 20, 4, 1, Terrain.RUBBLE);
        Painter.fill(this, 6, 24, 4, 1, Terrain.RUBBLE);
        Painter.fill(this, 28, 24, 4, 1, Terrain.RUBBLE);
        Painter.fill(this, 29, 32, 5, 1, Terrain.RUBBLE);
        Painter.fill(this, 6, 37, 5, 1, Terrain.RUBBLE);
        Painter.fill(this, 28, 35, 1, 7, Terrain.RUBBLE);


        //house exterior
        Painter.fill(this, 10, 24, 18, 13, Terrain.RUINED_WALL);
        //entrance exterior
        Painter.fill( this, 22, 36, 5, 6, Terrain.RUINED_WALL);
        Painter.fill(this, 24,41,1,1, Terrain.DOOR);
       //Interior floor
       Painter.fill(this, 11,25,16,11, Terrain.EMPTY_SP);
       Painter.fill(this, 23,36,3,5, Terrain.EMPTY_SP);

        //Bar
        Painter.fill(this, 11,27, 14, 1, Terrain.RUINED_WALL);
        Painter.fill(this, 11,28,8,1, Terrain.BOOKSHELF);
       Painter.fill(this, 11,30,8,1, Terrain.TABLE);


        west = 1151;
        map[west] = Terrain.WEST;

        east = 1098;
        map[east] = Terrain.EAST;

        north = 75;
        map[north] = Terrain.NORTH;

        south =  2425;
        map[south] = Terrain.SOUTH;

        note = 2375;
        map[note] = Terrain.EMPTY;

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
        Barkeep.spawn(this);
        //Konnicus.spawn(this);
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
        drop( new Gold(500), note );
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

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

public class C029HillLevel extends Level {
    //abandoned dairy farm

    {
        color1 = 0x4b6636;
        color2 = 0xf2f2f2;
    }


    @Override
    public String tilesTex() { return Assets.TILES_VILLAGE;
    }

    @Override
    public String waterTex() {
        return Assets.WATER_SEWERS;
    }



    protected boolean[] water() {
        return Patch.generate( feeling == Feeling.CHASM ? 0.60f : 0.45f, 6 );
    }

    private boolean entered = false;

    private static final String ENTERED	= "entered";
    @Override
    public void storeInBundle( Bundle bundle ) {
        super.storeInBundle( bundle );
        bundle.put(ENTERED, entered);
    }

    @Override
    public void restoreFromBundle( Bundle bundle ) {
        super.restoreFromBundle(bundle);
        entered = bundle.getBoolean( ENTERED );
    }

    @Override
    protected boolean build() {
        //farmhouse
        Painter.fill(this, 8, 6, 7, 9, Terrain.RUINED_WALL);
        Painter.fill(this, 9, 7, 5, 7, Terrain.EMPTY);
        Painter.fill(this, 14, 10, 1, 1, Terrain.DOOR);


        //Barn 1
        Painter.fill(this, 21, 6, 6, 15, Terrain.RUINED_WALL);
        Painter.fill(this, 22, 7, 4, 13, Terrain.EMPTY);
        Painter.fill(this, 21, 13, 1, 2, Terrain.EMPTY);


        //Barn 2
        Painter.fill(this, 26, 23, 6, 20, Terrain.RUINED_WALL);
        Painter.fill(this, 27, 24, 4, 18, Terrain.EMPTY);
        Painter.fill(this, 26, 24, 1, 2, Terrain.EMPTY);

        //Silo
        Painter.fill(this, 32, 27, 5, 7, Terrain.RUINED_WALL);
        Painter.fill(this, 32, 28, 4, 5, Terrain.EMPTY);
        Painter.fill(this, 31, 30, 2, 2, Terrain.EMPTY);

        //Barn 3
        Painter.fill(this, 4, 38, 20, 8, Terrain.RUINED_WALL);
        Painter.fill(this, 5, 39, 18, 6, Terrain.EMPTY);
        Painter.fill(this, 21, 38, 2, 1, Terrain.EMPTY);

        //Barn 4
        Painter.fill(this, 10, 26, 6, 7, Terrain.RUINED_WALL);
        Painter.fill(this, 11, 27, 4, 5, Terrain.EMPTY);
        Painter.fill(this, 12, 26, 2, 1, Terrain.EMPTY);

        //road e-w
        Painter.fill(this, 2, 24, 24, 1, Terrain.EMPTY_SP);

        //road n-s
        Painter.fill(this, 17, 15, 1, 9, Terrain.EMPTY_SP);


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
                        Random.IntRange( 3, 12 ) +
                                Random.IntRange( 1, 6 ) * WIDTH;
            } while (pos == east || map[pos] == Terrain.SIGN);
            drop( item, pos ).type = Heap.Type.SKELETON;
        }
    }

    @Override
    public int randomRespawnCell() {
        return -1;
    }

   /*
    @Override
    public void press( int cell, Char hero ) {

        super.press(cell, hero);

        if (!entered && hero == Dungeon.hero) {
            entered = true;

            AlphaWolf boss = new AlphaWolf();
            boss.state = boss.HUNTING;
            do {
                boss.pos = Random.Int( LENGTH );
            } while ( !passable[boss.pos] || Dungeon.visible[boss.pos]);
            GameScene.add(boss);
            boss.spawnWolves();

            if (Dungeon.visible[boss.pos]) {
                boss.notice();
                boss.sprite.alpha( 0 );
                boss.sprite.parent.add( new AlphaTweener( boss.sprite, 1, 0.1f ) );
            }

            Dungeon.observe();
        }
    }
    */

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

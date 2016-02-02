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
import com.udawos.pioneer.Dungeon;
import com.udawos.pioneer.actors.Actor;
import com.udawos.pioneer.actors.Char;
import com.udawos.pioneer.effects.CellEmitter;
import com.udawos.pioneer.effects.Speck;
import com.udawos.pioneer.items.Heap;
import com.udawos.pioneer.items.Item;
import com.udawos.pioneer.items.keys.SkeletonKey;
import com.udawos.pioneer.levels.painters.Painter;
import com.udawos.pioneer.scenes.GameScene;
import com.udawos.utils.Bundle;
import com.udawos.utils.Random;

public class U1UndergroundPassage extends Level {

    {
        color1 = 0x534f3e;
        color2 = 0xb9d661;

        viewDistance = 6;
    }

    private static final int ROOM_LEFT		= WIDTH / 2 - 2;
    private static final int ROOM_RIGHT		= WIDTH / 2 + 2;
    private static final int ROOM_TOP		= HEIGHT / 2 - 2;
    private static final int ROOM_BOTTOM	= HEIGHT / 2 + 2;

    private int arenaDoor;
    private boolean enteredArena = false;
    private boolean keyDropped = false;

    @Override
    public String tilesTex() {
        return Assets.TILES_CAVES;
    }

    @Override
    public String waterTex() {
        return Assets.WATER_CAVES;
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
        bundle.put( DOOR, arenaDoor );
        bundle.put( ENTERED, enteredArena );
        bundle.put( DROPPED, keyDropped );
    }

    @Override
    public void restoreFromBundle( Bundle bundle ) {
        super.restoreFromBundle( bundle );
        arenaDoor = bundle.getInt( DOOR );
        enteredArena = bundle.getBoolean( ENTERED );
        keyDropped = bundle.getBoolean( DROPPED );
    }

    @Override
    protected boolean build() {

        Painter.fill(this, 0, 0, 50, 50, Terrain.WALL);

        //main passage
        Painter.fill(this, 25, 1, 3, 47, Terrain.EMPTY);

        //rubble blockage
        Painter.fill(this, 25, 19, 3, 3, Terrain.RUBBLE);

        //west passage
        Painter.fill(this, 19, 38, 6, 1, Terrain.EMPTY);
        Painter.fill(this, 19, 29, 1, 9, Terrain.EMPTY);
        Painter.fill(this, 11, 29, 8, 1, Terrain.EMPTY);
        Painter.fill(this, 11, 15, 1, 14, Terrain.EMPTY);
        Painter.fill(this, 8, 19, 8, 1, Terrain.EMPTY);

        //east passage
        Painter.fill(this, 28, 33, 10, 1, Terrain.EMPTY);
        Painter.fill(this, 36, 21, 1, 12, Terrain.EMPTY);
        Painter.fill(this, 36, 21, 12, 1, Terrain.EMPTY);
        Painter.fill(this, 47, 6, 1, 15, Terrain.EMPTY);
        Painter.fill(this, 39, 18, 10, 1, Terrain.EMPTY);
        Painter.fill(this, 39, 14, 10, 1, Terrain.EMPTY);
        Painter.fill(this, 28, 11, 19, 1, Terrain.EMPTY);
        Painter.fill(this, 45, 8, 4, 1, Terrain.EMPTY);

        up = 2376;
        map[up] = Terrain.ENTRANCE;

        down = 76;
        map[down] = Terrain.ENTRANCE;


        return true;
    }

    @Override
    protected void decorate() {

        for (int i=WIDTH + 1; i < LENGTH - WIDTH; i++) {
            if (map[i] == Terrain.EMPTY) {
                int n = 0;
                if (map[i+1] == Terrain.WALL) {
                    n++;
                }
                if (map[i-1] == Terrain.WALL) {
                    n++;
                }
                if (map[i+WIDTH] == Terrain.WALL) {
                    n++;
                }
                if (map[i-WIDTH] == Terrain.WALL) {
                    n++;
                }
                if (Random.Int(8) <= n) {
                    map[i] = Terrain.EMPTY;
                }
            }
        }

        for (int i=0; i < LENGTH; i++) {
            if (map[i] == Terrain.WALL && Random.Int( 8 ) == 0) {
                map[i] = Terrain.WALL_DECO;
            }
        }

    }

    //put a bear in it
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
                pos = Random.IntRange( ROOM_LEFT, ROOM_RIGHT ) + Random.IntRange( ROOM_TOP + 1, ROOM_BOTTOM ) * WIDTH;
            } while (pos == east || map[pos] == Terrain.SIGN);
            drop( item, pos ).type = Heap.Type.SKELETON;
        }
    }

    @Override
    public int randomRespawnCell() {
        return -1;
    }

    @Override
    public void press( int cell, Char hero ) {

        super.press( cell, hero );


    }

    @Override
    public Heap drop( Item item, int cell ) {

        if (!keyDropped && item instanceof SkeletonKey) {

            keyDropped = true;

            CellEmitter.get(arenaDoor).start( Speck.factory(Speck.ROCK), 0.07f, 10 );

            set( arenaDoor, Terrain.EMPTY );
            GameScene.updateMap(arenaDoor);
            Dungeon.observe();
        }

        return super.drop( item, cell );
    }

    private boolean outsideEntraceRoom( int cell ) {
        int cx = cell % WIDTH;
        int cy = cell / WIDTH;
        return cx < ROOM_LEFT-1 || cx > ROOM_RIGHT+1 || cy < ROOM_TOP-1 || cy > ROOM_BOTTOM+1;
    }

    @Override
    public String tileName( int tile ) {
        switch (tile) {
            case Terrain.GRASS:
                return "Fluorescent moss";
            case Terrain.HIGH_GRASS:
                return "Fluorescent mushrooms";
            case Terrain.WATER:
                return "Freezing cold water.";
            default:
                return super.tileName( tile );
        }
    }

    @Override
    public String tileDesc( int tile ) {
        switch (tile) {
            case Terrain.ENTRANCE:
                return "The ladder leads up to the upper depth.";
            case Terrain.EXIT:
                return "The ladder leads down to the lower depth.";
            case Terrain.HIGH_GRASS:
                return "Huge mushrooms block the view.";
            case Terrain.WALL_DECO:
                return "A vein of some ore is visible on the wall. Gold?";
            default:
                return super.tileDesc( tile );
        }
    }


}

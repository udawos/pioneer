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

public class F053MountainLevel extends Level {

    {
        color1 = 0x4b6636;
        color2 = 0xf2f2f2;
    }

    private static final int TOP			= 2;
    private static final int HALL_WIDTH		= 40;
    private static final int HALL_HEIGHT	= 40;


    private static final int LEFT	= (WIDTH - HALL_WIDTH) / 2;
    private static final int CENTER	= LEFT + HALL_WIDTH / 2;

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
        Painter.fill(this, 43, 20, 1, 23, Terrain.MOUNTAIN_E);
        Painter.fill(this, 42, 21, 1, 21, Terrain.MOUNTAIN_E);
        Painter.fill(this, 39, 24, 1, 17, Terrain.MOUNTAIN_E);
        Painter.fill(this, 38, 25, 1, 15, Terrain.MOUNTAIN_E);
        Painter.fill(this, 37, 26, 1, 13, Terrain.MOUNTAIN_E);
        Painter.fill(this, 36, 27, 1, 11, Terrain.MOUNTAIN_E);
        Painter.fill(this, 35, 28, 1, 9, Terrain.MOUNTAIN_E);
        Painter.fill(this, 33, 29, 1, 7, Terrain.MOUNTAIN_E);
        Painter.fill(this, 26, 30, 1, 4, Terrain.MOUNTAIN_E);
        Painter.fill(this, 24, 31, 2, 2, Terrain.HILL);

        //West mountain south side
        Painter.fill(this, 23, 33, 4, 1, Terrain.MOUNTAIN_S);
        Painter.fill(this, 15, 35, 19, 1, Terrain.MOUNTAIN_S);
        Painter.fill(this, 4, 36, 32, 1, Terrain.MOUNTAIN_S);
        Painter.fill(this, 3, 37, 34, 1, Terrain.MOUNTAIN_S);
        Painter.fill(this, 2, 38, 36, 1, Terrain.MOUNTAIN_S);
        Painter.fill(this, 1, 39, 38, 1, Terrain.MOUNTAIN_S);
        Painter.fill(this, 0, 40, 40, 1, Terrain.MOUNTAIN_S);
        Painter.fill(this, 0, 41, 40, 1, Terrain.MOUNTAIN_S);
        Painter.fill(this, 0, 42, 43, 1, Terrain.MOUNTAIN_S);
        Painter.fill(this, 0, 43, 44, 1, Terrain.MOUNTAIN_S);

        //West mountain north side
        Painter.fill(this, 23, 30, 4, 1, Terrain.MOUNTAIN_N);
        Painter.fill(this, 15, 29, 19, 1, Terrain.MOUNTAIN_N);
        Painter.fill(this, 4, 28, 32, 1, Terrain.MOUNTAIN_N);
        Painter.fill(this, 3, 27, 34, 1, Terrain.MOUNTAIN_N);
        Painter.fill(this, 2, 26, 36, 1, Terrain.MOUNTAIN_N);
        Painter.fill(this, 1, 25, 38, 1, Terrain.MOUNTAIN_N);
        Painter.fill(this, 0, 24, 40, 1, Terrain.MOUNTAIN_N);
        Painter.fill(this, 0, 22, 43, 1, Terrain.MOUNTAIN_N);
        Painter.fill(this, 0, 21, 44, 1, Terrain.MOUNTAIN_N);

        //West mountain west side
        Painter.fill(this, 23, 30, 1, 4, Terrain.MOUNTAIN_W);
        Painter.fill(this, 15, 29, 1, 6, Terrain.MOUNTAIN_W);
        Painter.fill(this, 4,  28, 1, 8, Terrain.MOUNTAIN_W);
        Painter.fill(this, 3, 27, 1, 10, Terrain.MOUNTAIN_W);
        Painter.fill(this, 2, 26, 1, 12, Terrain.MOUNTAIN_W);
        Painter.fill(this, 1, 25, 1, 14, Terrain.MOUNTAIN_W);
        Painter.fill(this, 0, 24, 1, 16, Terrain.MOUNTAIN_W);

        //East mountain west side
        Painter.fill(this, 36, 3, 1, 12, Terrain.MOUNTAIN_W);
        Painter.fill(this, 37, 4, 1, 10, Terrain.MOUNTAIN_W);
        Painter.fill(this, 38, 5, 1, 8, Terrain.MOUNTAIN_W);
        Painter.fill(this, 39, 6, 1, 6, Terrain.MOUNTAIN_W);
        Painter.fill(this, 41, 7, 1, 4, Terrain.MOUNTAIN_W);

        Painter.fill(this, 45, 14, 1, 33, Terrain.MOUNTAIN_W);
        Painter.fill(this, 46, 13, 1, 33, Terrain.MOUNTAIN_W);
        Painter.fill(this, 47, 12, 1, 32, Terrain.MOUNTAIN_W);
        Painter.fill(this, 48, 11, 1, 32, Terrain.MOUNTAIN_W);

        //East mountain south side
        Painter.fill(this, 41, 10, 8, 1, Terrain.MOUNTAIN_S);
        Painter.fill(this, 39, 11, 10, 1, Terrain.MOUNTAIN_S);
        Painter.fill(this, 38, 12, 11, 1, Terrain.MOUNTAIN_S);
        Painter.fill(this, 37, 13, 12, 1, Terrain.MOUNTAIN_S);
        Painter.fill(this, 36, 14, 13, 1, Terrain.MOUNTAIN_S);


        Painter.fill(this, 48, 43, 2, 1, Terrain.MOUNTAIN_S);
        Painter.fill(this, 47, 44, 3, 1, Terrain.MOUNTAIN_S);
        Painter.fill(this, 46, 45, 4, 1, Terrain.MOUNTAIN_S);
        Painter.fill(this, 45, 46, 5, 1, Terrain.MOUNTAIN_S);

        //East mountain north side
        Painter.fill(this, 41, 7, 8, 1, Terrain.MOUNTAIN_N);
        Painter.fill(this, 39, 6, 10, 1, Terrain.MOUNTAIN_N);
        Painter.fill(this, 38, 5, 11, 1, Terrain.MOUNTAIN_N);
        Painter.fill(this, 37, 4, 12, 1, Terrain.MOUNTAIN_N);
        Painter.fill(this, 36, 3, 13, 1, Terrain.MOUNTAIN_N);


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
        for (int i = WIDTH; i < LENGTH - WIDTH; i++) {
            if (map[i] == Terrain.MOUNTAIN_W && map[i + 50] == Terrain.MOUNTAIN_S) {
                map[i + 50] = Terrain.MOUNTAIN_CORNER_SW;
            }
        }

        for (int i = WIDTH; i < LENGTH - WIDTH; i++) {
            if (map[i] == Terrain.MOUNTAIN_N && map[i + 1] == Terrain.MOUNTAIN_E && map[i + 51] == Terrain.MOUNTAIN_E) {
                map[i+1] = Terrain.MOUNTAIN_CORNER_NE;
            }
        }

        for (int i = WIDTH; i < LENGTH - WIDTH; i++) {
            if (map[i] == Terrain.MOUNTAIN_N && map[i + 50] == Terrain.MOUNTAIN_W) {
                map[i] = Terrain.MOUNTAIN_CORNER_NW;
            }
        }


        for (int i = WIDTH; i < LENGTH - WIDTH; i++) {
            if (map[i] == Terrain.MOUNTAIN_S && map[i + 1] == Terrain.MOUNTAIN_W) {
                map[i + 1] = Terrain.MOUNTAIN_ELBOW_NE;
            }
        }

        for (int i = WIDTH; i < LENGTH - WIDTH; i++) {
            if (map[i] == Terrain.MOUNTAIN_S && map[i + 50] == Terrain.MOUNTAIN_E) {
                map[i] = Terrain.MOUNTAIN_ELBOW_NW;
            }
        }


        for (int i = WIDTH; i < LENGTH - WIDTH; i++) {
            if (map[i - 1] == Terrain.MOUNTAIN_N && map[i - 50] == Terrain.MOUNTAIN_W && map[i] == Terrain.EMPTY) {
                map[i] = Terrain.MOUNTAIN_ELBOW_SE;
            }
        }

        for (int i = WIDTH; i < LENGTH - WIDTH; i++) {
            if (map[i] == Terrain.MOUNTAIN_E && map[i - 50] == Terrain.MOUNTAIN_E && map[i+1] == Terrain.MOUNTAIN_N) {
                map[i] = Terrain.MOUNTAIN_ELBOW_SW;
            }
        }

        for (int i = WIDTH; i < LENGTH - WIDTH; i++) {
            if (map[i] == Terrain.MOUNTAIN_E && map[i - 1] == Terrain.MOUNTAIN_S && map[i - 50] == Terrain.MOUNTAIN_E) {
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
                        Random.IntRange( LEFT + 1, LEFT + HALL_WIDTH - 2 ) +
                                Random.IntRange( TOP + HALL_HEIGHT + 1, TOP + HALL_HEIGHT ) * WIDTH;
            } while (pos == east || map[pos] == Terrain.SIGN);
            drop( item, pos ).type = Heap.Type.SKELETON;
        }
    }

    @Override
    public int randomRespawnCell() {
        return -1;
    }

   /* @Override
    public void press( int cell, Char hero ) {

        super.press(cell, hero);

        if (hero == Dungeon.hero) {

            Mob boss = Bestiary.mob(Dungeon.depth);
            boss.state = boss.HUNTING;
            int count = 0;
            do {
                boss.pos = Random.Int( LENGTH );
            } while (
                    !passable[boss.pos] ||
                            (Dungeon.visible[boss.pos] && count++ < 20));
            GameScene.add(boss);

            if (Dungeon.visible[boss.pos]) {
                boss.notice();
                boss.sprite.alpha( 0 );
                boss.sprite.parent.add( new AlphaTweener( boss.sprite, 1, 0.1f ) );
            }

            Dungeon.observe();
        }
    }*/

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

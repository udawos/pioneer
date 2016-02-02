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
import com.udawos.pioneer.actors.mobs.Bestiary;
import com.udawos.pioneer.actors.mobs.Mob;
import com.udawos.pioneer.items.Heap;
import com.udawos.pioneer.items.Item;
import com.udawos.pioneer.levels.painters.Painter;
import com.udawos.utils.Bundle;
import com.udawos.utils.Random;

public class A010MountainLevel extends Level {

    {
        color1 = 0x4b6636;
        color2 = 0xf2f2f2;
    }


    @Override
    public String tilesTex() { return Assets.TILES_MOUNTAIN;
    }

    @Override
    public String waterTex() {return Assets.WATER_HALLS;
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
        Painter.fill(this, 7, 4, 1, 45, Terrain.MOUNTAIN_E);
        Painter.fill(this, 6, 4, 1, 43, Terrain.MOUNTAIN_E);
        Painter.fill(this, 5, 5, 1, 41, Terrain.MOUNTAIN_E);
        Painter.fill(this, 4, 6, 1, 39, Terrain.MOUNTAIN_E);
        Painter.fill(this, 3, 7, 1, 37, Terrain.MOUNTAIN_E);

        //West mountain south side
        Painter.fill(this, 0, 48, 8, 1, Terrain.MOUNTAIN_S);
        Painter.fill(this, 0, 47, 7, 1, Terrain.MOUNTAIN_S);
        Painter.fill(this, 0, 46, 6, 1, Terrain.MOUNTAIN_S);
        Painter.fill(this, 0, 44, 5, 1, Terrain.MOUNTAIN_S);
        Painter.fill(this, 0, 43, 4, 1, Terrain.MOUNTAIN_S);


        //West mountain north side

        Painter.fill(this, 0, 4, 7, 1, Terrain.MOUNTAIN_N);
        Painter.fill(this, 0, 5, 6, 1, Terrain.MOUNTAIN_N);
        Painter.fill(this, 0, 6, 5, 1, Terrain.MOUNTAIN_N);
        Painter.fill(this, 0, 7, 4, 1, Terrain.MOUNTAIN_N);
        Painter.fill(this, 0, 8, 3, 1, Terrain.MOUNTAIN_N);


        //East mountain west side
        Painter.fill(this, 20, 3, 1, 23, Terrain.MOUNTAIN_W);
        Painter.fill(this, 21, 4, 1, 10, Terrain.MOUNTAIN_W);
        Painter.fill(this, 21, 14, 1, 13, Terrain.MOUNTAIN_W);
        Painter.fill(this, 22, 15, 1, 11, Terrain.MOUNTAIN_W);
        Painter.fill( this, 12, 26, 1, 22, Terrain.MOUNTAIN_W);
        Painter.fill(this, 13, 27, 1, 20, Terrain.MOUNTAIN_W);


        Painter.fill(this, 22, 4, 1, 42, Terrain.MOUNTAIN_W);
        Painter.fill(this, 23, 5, 1, 40, Terrain.MOUNTAIN_W);
        Painter.fill(this, 24, 6, 1, 38, Terrain.MOUNTAIN_W);
        Painter.fill(this, 25, 7, 1, 36, Terrain.MOUNTAIN_W);
        Painter.fill(this, 26, 8, 1, 34, Terrain.MOUNTAIN_W);
        Painter.fill(this, 27, 9, 1, 32, Terrain.MOUNTAIN_W);
        Painter.fill(this, 28, 10, 1, 30, Terrain.MOUNTAIN_W);
        Painter.fill(this, 29, 11, 1, 28, Terrain.MOUNTAIN_W);
        Painter.fill(this, 30, 12, 1, 26, Terrain.MOUNTAIN_W);
        Painter.fill(this, 31, 13, 1, 24, Terrain.MOUNTAIN_W);


        //East mountain south side

        Painter.fill(this, 12, 47, 37, 1, Terrain.MOUNTAIN_S);
        Painter.fill(this, 13, 46, 34, 1, Terrain.MOUNTAIN_S);
        Painter.fill(this, 22, 45, 25, 1, Terrain.MOUNTAIN_S);
        Painter.fill(this, 23, 44, 23, 1, Terrain.MOUNTAIN_S);
        Painter.fill(this, 24, 43, 21, 1, Terrain.MOUNTAIN_S);
        Painter.fill(this, 25, 42, 19, 1, Terrain.MOUNTAIN_S);
        Painter.fill(this, 26, 41, 17, 1, Terrain.MOUNTAIN_S);
        Painter.fill(this, 27, 40, 15, 1, Terrain.MOUNTAIN_S);
        Painter.fill(this, 28, 39, 13, 1, Terrain.MOUNTAIN_S);
        Painter.fill(this, 29, 38, 11, 1, Terrain.MOUNTAIN_S);
        Painter.fill(this, 30, 37, 9, 1, Terrain.MOUNTAIN_S);
        Painter.fill(this, 31, 36, 7, 1, Terrain.MOUNTAIN_S);


        //East mountain north side
        Painter.fill(this, 13, 26, 7, 1, Terrain.MOUNTAIN_N);
        Painter.fill(this, 14, 27, 7, 1, Terrain.MOUNTAIN_N);

        Painter.fill(this, 21, 3, 27, 1, Terrain.MOUNTAIN_N);
        Painter.fill(this, 22, 4, 25, 1, Terrain.MOUNTAIN_N);
        Painter.fill(this, 23, 5, 23, 1, Terrain.MOUNTAIN_N);
        Painter.fill(this, 24, 6, 21, 1, Terrain.MOUNTAIN_N);
        Painter.fill(this, 25, 7, 19, 1, Terrain.MOUNTAIN_N);
        Painter.fill(this, 26, 8, 17, 1, Terrain.MOUNTAIN_N);
        Painter.fill(this, 27, 9, 15, 1, Terrain.MOUNTAIN_N);
        Painter.fill(this, 28, 10, 13, 1, Terrain.MOUNTAIN_N);
        Painter.fill(this, 29, 11, 11, 1, Terrain.MOUNTAIN_N);
        Painter.fill(this, 30, 12, 9, 1, Terrain.MOUNTAIN_N);
        Painter.fill(this, 31, 13, 7, 1, Terrain.MOUNTAIN_N);
        Painter.fill(this, 32, 14, 5, 1, Terrain.MOUNTAIN_N);

        //East mountain east side
        Painter.fill(this, 48, 3, 1, 44, Terrain.MOUNTAIN_E);
        Painter.fill(this, 46, 4, 1, 42, Terrain.MOUNTAIN_E);
        Painter.fill(this, 45, 5, 1, 40, Terrain.MOUNTAIN_E);
        Painter.fill(this, 44, 6, 1, 38, Terrain.MOUNTAIN_E);
        Painter.fill(this, 43, 7, 1, 36, Terrain.MOUNTAIN_E);
        Painter.fill(this, 42, 8, 1, 34, Terrain.MOUNTAIN_E);
        Painter.fill(this, 41, 9, 1, 32, Terrain.MOUNTAIN_E);
        Painter.fill(this, 40, 10, 1, 30, Terrain.MOUNTAIN_E);
        Painter.fill(this, 39, 11, 1, 28, Terrain.MOUNTAIN_E);
        Painter.fill(this, 38, 12, 1, 26, Terrain.MOUNTAIN_E);
        Painter.fill(this, 37, 13, 1, 24, Terrain.MOUNTAIN_E);
        Painter.fill(this, 36, 14, 1, 22, Terrain.MOUNTAIN_E);


        //Painter.fill(this, 34, 27, 1, 4, Terrain.HILL);

        Painter.fill(this, 17, 32, 1, 1, Terrain.RUINED_WALL);
        Painter.fill(this, 17, 34, 3, 1, Terrain.RUINED_WALL);
        Painter.fill(this, 17, 34, 1, 5, Terrain.RUINED_WALL);
        Painter.fill(this, 18, 35, 1, 1, Terrain.DESK);
        Painter.fill(this, 17, 38, 4, 1, Terrain.RUINED_WALL);
        Painter.fill(this, 19, 36, 1, 1, Terrain.RUINED_WALL);
        Painter.fill(this, 19, 32, 1, 3, Terrain.RUINED_WALL);
        Painter.fill(this, 20, 33, 1, 1, Terrain.RUINED_WALL);

        east = 1098;
        map[east] = Terrain.EAST;

        north = 75;
        map[north] = Terrain.NORTH;

        return true;
    }

    @Override
    protected void decorate() {
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
            if (map[i-1] == Terrain.MOUNTAIN_N && map[i+1] == Terrain.EMPTY && map[i] == Terrain.MOUNTAIN_E) {
                map[i] = Terrain.MOUNTAIN_CORNER_NE;
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
                if (Random.Int( 35 ) <= n) {
                    map[i] = Terrain.RUBBLE;
                }
            }
        }


    }


    @Override
    protected void createMobs() {
        for (int i=0; i < 1; i++) {
            Mob mob = Bestiary.mob(Dungeon.depth);
            do {
                mob.pos = 2109;
            } while (mob.pos == -1);
            mobs.add(mob);
            Actor.occupyCell( mob );

        }

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
                        Random.IntRange(  1, 2 ) +
                                Random.IntRange( 15, 16) * WIDTH;
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
            case Terrain.DESK:
                return "Crate";
            case Terrain.HIGH_GRASS:
                return "High blooming flowers";
            default:
                return super.tileName( tile );
        }
    }

    @Override
    public String tileDesc(int tile) {
        switch (tile) {
            case Terrain.DESK:
                return "A wooden crate.";
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

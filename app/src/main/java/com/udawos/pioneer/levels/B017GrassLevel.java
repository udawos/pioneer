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

public class B017GrassLevel extends Level {

    {
        color1 = 0x4b6636;
        color2 = 0xf2f2f2;
    }


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


    @Override
    public void storeInBundle( Bundle bundle ) {
        super.storeInBundle( bundle );
    }

    private int aNW = 4;
    private int bNW = 3;
    private int cNW = 15;

    private int dNW = aNW;
    private int eNW = bNW+cNW-2;
    private int fNW = cNW+2;

    private int gNW = dNW+cNW;
    private int hNW = bNW-1;
    private int jNW = cNW+2;

    private int kNW = aNW;
    private int lNW = hNW;
    private int mNW = jNW+1;
    //
    private int aNE = 22;
    private int bNE = 6;
    private int cNE = 13;

    private int dNE = aNE;
    private int eNE = bNE+cNE-2;
    private int fNE = cNE+2;

    private int gNE = dNE+cNE;
    private int hNE = bNE-1;
    private int jNE = cNE+2;

    private int kNE = aNE;
    private int lNE = hNE;
    private int mNE = jNE+1;
    //
    private int aSE = 29;
    private int bSE = 19;
    private int cSE = 17;

    private int dSE = aSE;
    private int eSE = bSE+cSE-2;
    private int fSE = cSE+2;

    private int gSE = dSE+cSE;
    private int hSE = bSE-1;
    private int jSE = cSE+2;

    private int kSE = aSE;
    private int lSE = hSE;
    private int mSE = jSE+1;
    //
    private int aSW = 4;
    private int bSW = 28;
    private int cSW = 23;

    private int dSW = aSW;
    private int eSW = bSW+cSW-2;
    private int fSW = cSW+2;

    private int gSW = dSW+cSW;
    private int hSW = bSW-1;
    private int jSW = cSW+2;

    private int kSW = aSW;
    private int lSW = hSW;
    private int mSW = jSW+1;




    @Override
    public void restoreFromBundle( Bundle bundle ) {
        super.restoreFromBundle(bundle);
    }

    @Override
    protected boolean build() {


        Painter.fill(this, aNW, bNW, 1, cNW-2, Terrain.MOUNTAIN_W);
        Painter.fill(this, dNW, eNW, fNW-2, 1, Terrain.MOUNTAIN_S);
        Painter.fill(this, gNW, hNW, 1, jNW-2, Terrain.MOUNTAIN_E);
        Painter.fill(this, kNW, lNW, mNW-2, 1, Terrain.MOUNTAIN_N);
        for (int i = 49; i < LENGTH - WIDTH; i++) {
            if (map[i] == Terrain.MOUNTAIN_W && cNW >= 4) {
                Painter.fill(this, aNW++, bNW++, 1, (cNW = cNW-2), Terrain.MOUNTAIN_W);
            }
            if (map[i] == Terrain.MOUNTAIN_S && fNW >=6) {
                Painter.fill(this, dNW++, eNW--, (fNW = fNW-2) , 1, Terrain.MOUNTAIN_S);
            }
            if (map[i] == Terrain.MOUNTAIN_E && jNW >= 6) {
                Painter.fill(this, gNW--, hNW++, 1 , (jNW = jNW-2), Terrain.MOUNTAIN_E);
            }
            if (map[i] == Terrain.MOUNTAIN_N  && mNW >= 8) {
                Painter.fill(this, kNW++, lNW++, (mNW = mNW - 2), 1, Terrain.MOUNTAIN_N);
            }
        }

        Painter.fill(this, aNE, bNE, 1, cNE-2, Terrain.MOUNTAIN_W);
        Painter.fill(this, dNE, eNE, fNE-2, 1, Terrain.MOUNTAIN_S);
        Painter.fill(this, gNE, hNE, 1, jNE-2, Terrain.MOUNTAIN_E);
        Painter.fill(this, kNE, lNE, mNE-2, 1, Terrain.MOUNTAIN_N);
        for (int i = 49; i < LENGTH - WIDTH; i++) {
            if (map[i] == Terrain.MOUNTAIN_W && cNE >= 4) {
                Painter.fill(this, aNE++, bNE++, 1, (cNE = cNE-2), Terrain.MOUNTAIN_W);
            }
            if (map[i] == Terrain.MOUNTAIN_S && fNE >=6) {
                Painter.fill(this, dNE++, eNE--, (fNE = fNE-2) , 1, Terrain.MOUNTAIN_S);
            }
            if (map[i] == Terrain.MOUNTAIN_E && jNE >= 6) {
                Painter.fill(this, gNE--, hNE++, 1 , (jNE = jNE-2), Terrain.MOUNTAIN_E);
            }
            if (map[i] == Terrain.MOUNTAIN_N  && mNE >= 8) {
                Painter.fill(this, kNE++, lNE++, (mNE = mNE - 2), 1, Terrain.MOUNTAIN_N);
            }
        }

        Painter.fill(this, aSW, bSW, 1, cSW-2, Terrain.MOUNTAIN_W);
        Painter.fill(this, dSW, eSW, fSW-2, 1, Terrain.MOUNTAIN_S);
        Painter.fill(this, gSW, hSW, 1, jSW-2, Terrain.MOUNTAIN_E);
        Painter.fill(this, kSW, lSW, mSW-2, 1, Terrain.MOUNTAIN_N);
        for (int i = 49; i < LENGTH - WIDTH; i++) {
            if (map[i] == Terrain.MOUNTAIN_W && cSW >= 4) {
                Painter.fill(this, aSW++, bSW++, 1, (cSW = cSW-2), Terrain.MOUNTAIN_W);
            }
            if (map[i] == Terrain.MOUNTAIN_S && fSW >=6) {
                Painter.fill(this, dSW++, eSW--, (fSW = fSW-2) , 1, Terrain.MOUNTAIN_S);
            }
            if (map[i] == Terrain.MOUNTAIN_E && jSW >= 6) {
                Painter.fill(this, gSW--, hSW++, 1 , (jSW = jSW-2), Terrain.MOUNTAIN_E);
            }
            if (map[i] == Terrain.MOUNTAIN_N  && mSW >= 8) {
                Painter.fill(this, kSW++, lSW++, (mSW = mSW - 2), 1, Terrain.MOUNTAIN_N);
            }
        }

        Painter.fill(this, aSE, bSE, 1, cSE-2, Terrain.MOUNTAIN_W);
        Painter.fill(this, dSE, eSE, fSE-2, 1, Terrain.MOUNTAIN_S);
        Painter.fill(this, gSE, hSE, 1, jSE-2, Terrain.MOUNTAIN_E);
        Painter.fill(this, kSE, lSE, mSE-2, 1, Terrain.MOUNTAIN_N);
        for (int i = 49; i < LENGTH - WIDTH; i++) {
            if (map[i] == Terrain.MOUNTAIN_W && cSE >= 4) {
                Painter.fill(this, aSE++, bSE++, 1, (cSE = cSE-2), Terrain.MOUNTAIN_W);
            }
            if (map[i] == Terrain.MOUNTAIN_S && fSE >=6) {
                Painter.fill(this, dSE++, eSE--, (fSE = fSE-2) , 1, Terrain.MOUNTAIN_S);
            }
            if (map[i] == Terrain.MOUNTAIN_E && jSE >= 6) {
                Painter.fill(this, gSE--, hSE++, 1 , (jSE = jSE-2), Terrain.MOUNTAIN_E);
            }
            if (map[i] == Terrain.MOUNTAIN_N  && mSE >= 8) {
                Painter.fill(this, kSE++, lSE++, (mSE = mSE - 2), 1, Terrain.MOUNTAIN_N);
            }
        }


        west = 1151;
        map[west] = Terrain.WEST;

        east = 1098;
        map[east] = Terrain.EAST;

        north = 75;
        map[north] = Terrain.NORTH;

        south = 2428;
        map[south] = Terrain.SOUTH;

        return true;
    }


    @Override
    protected void decorate() {
        for (int i = WIDTH; i < LENGTH; i++) {
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

        for (int i = WIDTH; i < LENGTH; i++) {
            if (map[i] == Terrain.MOUNTAIN_E && map[i - 1] == Terrain.MOUNTAIN_S && map[i - 50] == Terrain.MOUNTAIN_E) {
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
                    map[i] = Terrain.PEDESTAL;
                }
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
                        Random.IntRange( 1,  2 ) +
                                Random.IntRange( 4, 5 ) * WIDTH;
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

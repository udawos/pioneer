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

public class E049HillLevel extends Level {

    {
        color1 = 0x4b6636;
        color2 = 0xf2f2f2;
    }


    @Override
    public String tilesTex() { return Assets.TILES_MOUNTAIN;
    }

    @Override
    public String waterTex() {
        return Assets.WATER_CAVES;
    }

    protected boolean[] water() {
        return Patch.generate( feeling == Feeling.CHASM ? 0.60f : 0.45f, 6 );
    }

    private int aNW = 0;
    private int bNW = 4;
    private int cNW = 14;

    private int dNW = aNW;
    private int eNW = bNW+cNW-2;
    private int fNW = (cNW+2)*2;

    private int gNW = (dNW+fNW)-2;
    private int hNW = bNW-1;
    private int jNW = cNW+2;

    private int kNW = aNW;
    private int lNW = hNW;
    private int mNW = (jNW)*2;



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

        Painter.fill(this, aNW, bNW, 1, cNW-2, Terrain.MOUNTAIN_W);
        Painter.fill(this, dNW, eNW, fNW-2, 1, Terrain.MOUNTAIN_S);
        Painter.fill(this, gNW, hNW, 1, jNW-2, Terrain.MOUNTAIN_E);
        Painter.fill(this, kNW, lNW, mNW-2, 1, Terrain.MOUNTAIN_N);
        for (int i = 49; i < LENGTH - WIDTH; i++) {
            if (map[i] == Terrain.MOUNTAIN_W && cNW >= 6) {
                Painter.fill(this, aNW++, bNW++, 1, (cNW = cNW-2), Terrain.MOUNTAIN_W);
            }
            if (map[i] == Terrain.MOUNTAIN_S && fNW >=24) {
                Painter.fill(this, dNW++, eNW--, (fNW = fNW-2) , 1, Terrain.MOUNTAIN_S);
            }
            if (map[i] == Terrain.MOUNTAIN_E && jNW >= 8) {
                Painter.fill(this, gNW--, hNW++, 1 , (jNW = jNW-2), Terrain.MOUNTAIN_E);
            }
            if (map[i] == Terrain.MOUNTAIN_N  && mNW >= 24) {
                Painter.fill(this, kNW++, lNW++, (mNW = mNW - 2), 1, Terrain.MOUNTAIN_N);
            }
        }

        //SE mountain

        int aSE = 28;
        int bSE = 20;
        int cSE = 1;
        int dSE = 15;
        Painter.fill(this, aSE, bSE, cSE, dSE-2, Terrain.MOUNTAIN_W);
        for (int i = 49; i < LENGTH - WIDTH; i++) {
            if (map[i] == Terrain.MOUNTAIN_W && dSE >= 4) {
                Painter.fill(this, aSE++, bSE++, cSE, (dSE = dSE-2), Terrain.MOUNTAIN_W);
            }
        }

        int eSE = 28;
        int fSE = 33;
        int gSE = 15;
        int hSE = 1;
        Painter.fill(this, eSE, fSE, gSE-2, hSE, Terrain.MOUNTAIN_S);
        for (int i = 49; i < LENGTH - WIDTH; i++) {
            if (map[i] == Terrain.MOUNTAIN_S &&  gSE >= 4) {
                Painter.fill(this, eSE++, fSE--, (gSE = gSE-2) , hSE, Terrain.MOUNTAIN_S);
            }
        }

        int lSE = 41;
        int mSE = 20;
        int nSE = 1;
        int oSE = 16;
        Painter.fill(this, lSE, mSE, nSE, oSE-2, Terrain.MOUNTAIN_E);
        for (int i = 49; i < LENGTH - WIDTH; i++) {
            if (map[i] == Terrain.MOUNTAIN_E && oSE >= 6) {
                Painter.fill(this, lSE--, mSE++, nSE, (oSE = oSE-2), Terrain.MOUNTAIN_E);
            }
        }

        int pSE = 28;
        int qSE = 20;
        int rSE = 15;
        int sSE = 1;
        Painter.fill(this, pSE, qSE, rSE-2, sSE, Terrain.MOUNTAIN_N);
        for (int i = 49; i < LENGTH - WIDTH; i++) {
            if (map[i] == Terrain.MOUNTAIN_N  && rSE >= 4) {
                Painter.fill(this, pSE++, qSE++, (rSE = rSE -2), sSE, Terrain.MOUNTAIN_N);
            }
        }

        east = 1148;
        map[east] = Terrain.EAST;

        west = 951;
        map[west] = Terrain.WEST;

        north = 75;
        map[north] = Terrain.NORTH;

        south = 2425;
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

        for (int i=WIDTH + 1; i < LENGTH - WIDTH; i++) {
            if (map[i] == Terrain.EMPTY) {
                int n = 0;
                if (map[i + 1] == Terrain.RUBBLE) {
                    n++;
                }
                if (map[i - 1] == Terrain.RUBBLE) {
                    n++;
                }
                if (map[i + WIDTH] == Terrain.RUBBLE) {
                    n++;
                }
                if (map[i - WIDTH] == Terrain.RUBBLE) {
                    n++;
                }
                if (Random.Int( 20 ) <= n) {
                    map[i] = Terrain.RUBBLE;
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

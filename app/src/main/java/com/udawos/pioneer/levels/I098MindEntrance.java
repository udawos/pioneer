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

public class I098MindEntrance extends Level {

    {
        color1 = 0x4b6636;
        color2 = 0xf2f2f2;
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


    @Override
    public void storeInBundle( Bundle bundle ) {
        super.storeInBundle( bundle );
    }

    @Override
    public void restoreFromBundle( Bundle bundle ) {
        super.restoreFromBundle(bundle);
    }

    //a = x coord
    //b = y coord
    //c = length

    private int aNW = 17;
    private int bNW = 12;
    private int cNW = 7;

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
    private int aNE = 26;
    private int bNE = 12;
    private int cNE = 7;

    private int dNE = aNE;
    private int eNE = bNE+cNE-2;
    private int fNE = cNE+2;

    private int gNE = dNE+cNE;
    private int hNE = bNE-1;
    private int jNE = cNE+2;

    private int kNE = aNE;
    private int lNE = hNE;
    private int mNE = jNE+1;

    private int aSE = 23;
    private int bSE = 36;
    private int cSE = 5;

    private int dSE = aSE;
    private int eSE = bSE+cSE-2;
    private int fSE = cSE+2;

    private int gSE = dSE+cSE;
    private int hSE = bSE-1;
    private int jSE = cSE+2;

    private int kSE = aSE;
    private int lSE = hSE;
    private int mSE = jSE+1;

    private int aSW = 11;
    private int bSW = 37;
    private int cSW = 5;

    private int dSW = aSW;
    private int eSW = bSW+cSW-2;
    private int fSW = cSW+2;

    private int gSW = dSW+cSW;
    private int hSW = bSW-1;
    private int jSW = cSW+2;

    private int kSW = aSW;
    private int lSW = hSW;
    private int mSW = jSW+1;

    private int aCentral = 19;
    private int bCentral = 3;
    private int cCentral = 9;

    private int dCentral = aCentral;
    private int eCentral = bCentral+cCentral-2;
    private int fCentral = cCentral+2;

    private int gCentral = dCentral+cCentral;
    private int hCentral = bCentral-1;
    private int jCentral = cCentral+2;

    private int kCentral = aCentral;
    private int lCentral = hCentral;
    private int mCentral = jCentral+1;

    @Override
    protected boolean build() {

        Painter.fill(this, aNW, bNW, 1, cNW - 2, Terrain.MOUNTAIN_W);
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

        Painter.fill(this, aCentral, bCentral, 1, cCentral-2, Terrain.MOUNTAIN_W);
        Painter.fill(this, dCentral, eCentral, fCentral-2, 1, Terrain.MOUNTAIN_S);
        Painter.fill(this, gCentral, hCentral, 1, jCentral-2, Terrain.MOUNTAIN_E);
        Painter.fill(this, kCentral, lCentral, mCentral-2, 1, Terrain.MOUNTAIN_N);
        for (int i = 49; i < LENGTH - WIDTH; i++) {
            if (map[i] == Terrain.MOUNTAIN_W && cCentral >= 4) {
                Painter.fill(this, aCentral++, bCentral++, 1, (cCentral = cCentral-2), Terrain.MOUNTAIN_W);
            }
            if (map[i] == Terrain.MOUNTAIN_S && fCentral >=6) {
                Painter.fill(this, dCentral++, eCentral--, (fCentral = fCentral-2) , 1, Terrain.MOUNTAIN_S);
            }
            if (map[i] == Terrain.MOUNTAIN_E && jCentral >= 6) {
                Painter.fill(this, gCentral--, hCentral++, 1 , (jCentral = jCentral-2), Terrain.MOUNTAIN_E);
            }
            if (map[i] == Terrain.MOUNTAIN_N  && mCentral >= 8) {
                Painter.fill(this, kCentral++, lCentral++, (mCentral = mCentral - 2), 1, Terrain.MOUNTAIN_N);
            }
        }

        down = 525;
        map[down] = Terrain.EXIT;

        west = 1151;
        map[west] = Terrain.WEST;

        east = 1098;
        map[east] = Terrain.EAST;

        south =  2425;
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

        for (int i=0; i < LENGTH-WIDTH; i++) {
            if (map[i] == Terrain.EMPTY && Random.Int( 1249 ) == 0) {
                map[i] = Terrain.WALL_DECO;
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
            case Terrain.WALL_DECO:
                return "A block of ice";
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
            case Terrain.HILL:
                return "Several tiles are missing here.";
            case Terrain.EMPTY_SP:
                return "Thick carpet covers the floor.";
            case Terrain.WALL_DECO:
                return "You could probably collect some of this ice and melt it for drinking";
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

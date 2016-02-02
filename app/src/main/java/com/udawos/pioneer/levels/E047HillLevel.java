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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOEAST.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>
 */
package com.udawos.pioneer.levels;

import com.udawos.pioneer.Assets;
import com.udawos.pioneer.Bones;
import com.udawos.pioneer.actors.Actor;
import com.udawos.pioneer.actors.mobs.npcs.BearCub;
import com.udawos.pioneer.items.Heap;
import com.udawos.pioneer.items.Item;
import com.udawos.pioneer.levels.painters.Painter;
import com.udawos.utils.Bundle;
import com.udawos.utils.Random;

public class E047HillLevel extends Level {
    //Mama bear encounter
    //cub runs to Mama bear
    //Mama bear destroys player

    {
        color1 = 0x4b6636;
        color2 = 0xf2f2f2;
    }


    @Override
    public String tilesTex() { return Assets.TILES_GRASS;
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

    @Override
    public void restoreFromBundle( Bundle bundle ) {
        super.restoreFromBundle(bundle);
    }

    @Override
    protected boolean build() {

        // mountain

        int aWEST = 5;
        int bWEST = 15;
        int cWEST = 1;
        int dWEST = 17;
        Painter.fill(this, aWEST, bWEST, cWEST, dWEST-2, Terrain.MOUNTAIN_W);
        for (int i = 49; i < LENGTH - WIDTH; i++) {
            if (map[i] == Terrain.MOUNTAIN_W && dWEST >= 14) {
                Painter.fill(this, aWEST++, bWEST++, cWEST, (dWEST = dWEST-2), Terrain.MOUNTAIN_W);
            }
        }

        int eWEST = 5;
        int fWEST = 30;
        int gWEST = 8;
        int hWEST = 1;
        Painter.fill(this, eWEST, fWEST, gWEST-2, hWEST, Terrain.MOUNTAIN_S);
        for (int i = 49; i < LENGTH - WIDTH; i++) {
            if (map[i] == Terrain.MOUNTAIN_S &&  gWEST >= 6) {
                Painter.fill(this, eWEST++, fWEST--, (gWEST = gWEST-2) , hWEST, Terrain.MOUNTAIN_S);
            }
        }

        int lWEST = 11;
        int mWEST = 15;
        int nWEST = 1;
        int oWEST = 18;
        Painter.fill(this, lWEST, mWEST, nWEST, oWEST-2, Terrain.MOUNTAIN_E);
        for (int i = 49; i < LENGTH - WIDTH; i++) {
            if (map[i] == Terrain.MOUNTAIN_E && oWEST >= 16) {
                Painter.fill(this, lWEST--, mWEST++, nWEST, (oWEST = oWEST-2), Terrain.MOUNTAIN_E);
            }
        }

        int pWEST = 5;
        int qWEST = 15;
        int rWEST = 8;
        int sWEST = 1;
        Painter.fill(this, pWEST, qWEST, rWEST-2, sWEST, Terrain.MOUNTAIN_N);
        for (int i = 49; i < LENGTH - WIDTH; i++) {
            if (map[i] == Terrain.MOUNTAIN_N && rWEST >= 6) {
                Painter.fill(this, pWEST++, qWEST++, (rWEST = rWEST -2), sWEST, Terrain.MOUNTAIN_N);
            }
        }

        //Middle mountain

        int aMiddle = 19;
        int bMiddle = 10;
        int cMiddle = 1;
        int dMiddle = 32;
        Painter.fill(this, aMiddle, bMiddle, cMiddle, dMiddle-2, Terrain.MOUNTAIN_W);
        for (int i = 49; i < LENGTH - WIDTH; i++) {
            if (map[i] == Terrain.MOUNTAIN_W && dMiddle >= 30) {
                Painter.fill(this, aMiddle++, bMiddle++, cMiddle, (dMiddle = dMiddle-2), Terrain.MOUNTAIN_W);
            }
        }

        int eMiddle = 19;
        int fMiddle = 40;
        int gMiddle = 7;
        int hMiddle = 1;
        Painter.fill(this, eMiddle, fMiddle, gMiddle-2, hMiddle, Terrain.MOUNTAIN_S);
        for (int i = 49; i < LENGTH - WIDTH; i++) {
            if (map[i] == Terrain.MOUNTAIN_S  &&  gMiddle >= 4) {
                Painter.fill(this, eMiddle++, fMiddle--, (gMiddle = gMiddle-2) , hMiddle, Terrain.MOUNTAIN_S);
            }
        }

        int lMiddle = 24 ;
        int mMiddle = 10;
        int nMiddle = 1;
        int oMiddle = 33;
        Painter.fill(this, lMiddle, mMiddle, nMiddle, oMiddle-2, Terrain.MOUNTAIN_E);
        for (int i = 49; i < LENGTH - WIDTH; i++) {
            if (map[i] == Terrain.MOUNTAIN_E && oMiddle >= 31) {
                Painter.fill(this, lMiddle--, mMiddle++, nMiddle, (oMiddle = oMiddle-2), Terrain.MOUNTAIN_E);
            }
        }

        int pMiddle = 19;
        int qMiddle = 10;
        int rMiddle = 7;
        int sMiddle = 1;
        Painter.fill(this, pMiddle, qMiddle, rMiddle-2, sMiddle, Terrain.MOUNTAIN_N);
        for (int i = 49; i < LENGTH - WIDTH; i++) {
            if (map[i] == Terrain.MOUNTAIN_N  && rMiddle >= 4) {
                Painter.fill(this, pMiddle++, qMiddle++, (rMiddle = rMiddle -2), sMiddle, Terrain.MOUNTAIN_N);
            }
        }

        //EAST mountain

        int aEAST = 32;
        int bEAST = 3;
        int cEAST = 1;
        int dEAST = 45;
        Painter.fill(this, aEAST, bEAST, cEAST, dEAST-2, Terrain.MOUNTAIN_W);
        for (int i = 49; i < LENGTH - WIDTH; i++) {
            if (map[i] == Terrain.MOUNTAIN_W && dEAST >= 40) {
                Painter.fill(this, aEAST++, bEAST++, cEAST, (dEAST = dEAST-2), Terrain.MOUNTAIN_W);
            }
        }

        int eEAST = 32;
        int fEAST = 46;
        int gEAST = 10;
        int hEAST = 1;
        Painter.fill(this, eEAST, fEAST, gEAST-2, hEAST, Terrain.MOUNTAIN_S);
        for (int i = 49; i < LENGTH - WIDTH; i++) {
            if (map[i] == Terrain.MOUNTAIN_S &&  gEAST >= 5) {
                Painter.fill(this, eEAST++, fEAST--, (gEAST = gEAST-2) , hEAST, Terrain.MOUNTAIN_S);
            }
        }

        int lEAST = 40;
        int mEAST = 3;
        int nEAST = 1;
        int oEAST = 46;
        Painter.fill(this, lEAST, mEAST, nEAST, oEAST-2, Terrain.MOUNTAIN_E);
        for (int i = 49; i < LENGTH - WIDTH; i++) {
            if (map[i] == Terrain.MOUNTAIN_E && oEAST >= 42) {
                Painter.fill(this, lEAST--, mEAST++, nEAST, (oEAST = oEAST-2), Terrain.MOUNTAIN_E);
            }
        }

        int pEAST = 32;
        int qEAST = 3;
        int rEAST = 10;
        int sEAST = 1;
        Painter.fill(this, pEAST, qEAST, rEAST-2, sEAST, Terrain.MOUNTAIN_N);
        for (int i = 49; i < LENGTH - WIDTH; i++) {
            if (map[i] == Terrain.MOUNTAIN_N  && rEAST >= 5) {
                Painter.fill(this, pEAST++, qEAST++, (rEAST = rEAST -2), sEAST, Terrain.MOUNTAIN_N);
            }
        }


        west = 951;
        map[west] = Terrain.WEST;

        east = 1098;
        map[east] = Terrain.EAST;

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
        BearCub.spawn(this);

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

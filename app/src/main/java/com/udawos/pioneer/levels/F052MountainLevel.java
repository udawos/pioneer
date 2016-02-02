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

public class F052MountainLevel extends Level {

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

// mountain

       /* int aWest = 0;
        int bWest = 9;
        int cWest = 1;
        int dWest = 10;
        Painter.fill(this, aWest, bWest, cWest, dWest-2, Terrain.MOUNTAIN_W);
        for (int i = 49; i < LENGTH - WIDTH; i++) {
            if (map[i] == Terrain.MOUNTAIN_W && aWest <= 7 && bWest <= 25 && dWest >= 2) {
                Painter.fill(this, aWest++, bWest++, cWest, (dWest = dWest-2), Terrain.MOUNTAIN_W);
            }
        }*/

        int lWest = 11;
        int mWest = 7;
        int nWest = 1;
        int oWest = 45;
        Painter.fill(this, lWest, mWest, nWest, oWest-2, Terrain.MOUNTAIN_E);
        for (int i = 49; i < LENGTH - WIDTH; i++) {
            if (map[i] == Terrain.MOUNTAIN_E  && oWest >= 38) {
                Painter.fill(this, lWest--, mWest++, nWest, (oWest = oWest-2), Terrain.MOUNTAIN_E);
            }
        }

        int eWest = 0;
        int fWest = 49;
        int gWest = 13;
        int hWest = 1;
        Painter.fill(this, eWest, fWest, gWest-2, hWest, Terrain.MOUNTAIN_S);
        for (int i = 49; i < LENGTH; i++) {
            if (map[i] == Terrain.MOUNTAIN_S  && gWest >= 6) {
                Painter.fill(this, eWest++, fWest--, (gWest = gWest-2) , hWest, Terrain.MOUNTAIN_S);
            }
        }


        int pWest = 0;
        int qWest = 7;
        int rWest = 12;
        int sWest = 1;
        Painter.fill(this, pWest, qWest, rWest-2, sWest, Terrain.MOUNTAIN_N);
        for (int i = 49; i < LENGTH - WIDTH; i++) {
            if (map[i] == Terrain.MOUNTAIN_N && rWest >= 9) {
                Painter.fill(this, pWest, qWest++, (rWest = rWest -1), sWest, Terrain.MOUNTAIN_N);
            }
        }

        //Centre mountain

        int aCentre = 14;
        int bCentre = 38;
        int cCentre = 1;
        int dCentre = 11;
        Painter.fill(this, aCentre, bCentre, cCentre, dCentre-2, Terrain.MOUNTAIN_W);
        for (int i = 49; i < LENGTH - WIDTH; i++) {
            if (map[i] == Terrain.MOUNTAIN_W &&  dCentre >= 4) {
                Painter.fill(this, aCentre++, bCentre++, cCentre, (dCentre = dCentre-2), Terrain.MOUNTAIN_W);
            }
        }

        int eCentre = 14;
        int fCentre = 46;
        int gCentre = 11;
        int hCentre = 1;
        Painter.fill(this, eCentre, fCentre, gCentre-2, hCentre, Terrain.MOUNTAIN_S);
        for (int i = 49; i < LENGTH - WIDTH; i++) {
            if (map[i] == Terrain.MOUNTAIN_S  &&  gCentre >= 4) {
                Painter.fill(this, eCentre++, fCentre--, (gCentre = gCentre-2) , hCentre, Terrain.MOUNTAIN_S);
            }
        }

        int lCentre = 23 ;
        int mCentre = 38;
        int nCentre = 1;
        int oCentre = 11;
        Painter.fill(this, lCentre, mCentre, nCentre, oCentre-2, Terrain.MOUNTAIN_E);
        for (int i = 49; i < LENGTH - WIDTH; i++) {
            if (map[i] == Terrain.MOUNTAIN_E && oCentre >= 4) {
                Painter.fill(this, lCentre--, mCentre++, nCentre, (oCentre = oCentre-2), Terrain.MOUNTAIN_E);
            }
        }

        int pCentre = 14;
        int qCentre = 38;
        int rCentre = 11;
        int sCentre = 1;
        Painter.fill(this, pCentre, qCentre, rCentre-2, sCentre, Terrain.MOUNTAIN_N);
        for (int i = 49; i < LENGTH - WIDTH; i++) {
            if (map[i] == Terrain.MOUNTAIN_N  && rCentre >= 4) {
                Painter.fill(this, pCentre++, qCentre++, (rCentre = rCentre -2), sCentre, Terrain.MOUNTAIN_N);
            }
        }

        //East mountain

        int aEast = 24;
        int bEast = 4;
        int cEast = 1;
        int dEast = 45;
        Painter.fill(this, aEast, bEast, cEast, dEast-2, Terrain.MOUNTAIN_W);
        for (int i = 49; i < LENGTH - WIDTH; i++) {
            if (map[i] == Terrain.MOUNTAIN_W && dEast >= 4) {
                Painter.fill(this, aEast++, bEast++, cEast, (dEast = dEast-2), Terrain.MOUNTAIN_W);
            }
        }

        int eEast = 24;
        int fEast = 47;
        int gEast = 25;
        int hEast = 1;
        Painter.fill(this, eEast, fEast, gEast-2, hEast, Terrain.MOUNTAIN_S);
        for (int i = 49; i < LENGTH - WIDTH; i++) {
            if (map[i] == Terrain.MOUNTAIN_S &&  gEast >= 4) {
                Painter.fill(this, eEast++, fEast--, (gEast = gEast-2) , hEast, Terrain.MOUNTAIN_S);
            }
        }

        int lEast = 47;
        int mEast = 4;
        int nEast = 1;
        int oEast = 46;
        Painter.fill(this, lEast, mEast, nEast, oEast-2, Terrain.MOUNTAIN_E);
        for (int i = 49; i < LENGTH - WIDTH; i++) {
            if (map[i] == Terrain.MOUNTAIN_E && oEast >= 6) {
                Painter.fill(this, lEast--, mEast++, nEast, (oEast = oEast-2), Terrain.MOUNTAIN_E);
            }
        }

        int pEast = 24;
        int qEast = 4;
        int rEast = 25;
        int sEast = 1;
        Painter.fill(this, pEast, qEast, rEast-2, sEast, Terrain.MOUNTAIN_N);
        for (int i = 49; i < LENGTH - WIDTH; i++) {
            if (map[i] == Terrain.MOUNTAIN_N  && rEast >= 4) {
                Painter.fill(this, pEast++, qEast++, (rEast = rEast -2), sEast, Terrain.MOUNTAIN_N);
            }
        }
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


       /* for (int i = WIDTH; i < LENGTH - WIDTH; i++) {
            if (map[i] == Terrain.MOUNTAIN_S && map[i + 1] == Terrain.MOUNTAIN_W) {
                map[i + 1] = Terrain.MOUNTAIN_ELBOW_NE;
            }
        }*/

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

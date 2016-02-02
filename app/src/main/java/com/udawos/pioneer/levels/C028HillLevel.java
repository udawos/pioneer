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

import com.udawos.noosa.tweeners.AlphaTweener;
import com.udawos.pioneer.Assets;
import com.udawos.pioneer.Bones;
import com.udawos.pioneer.Dungeon;
import com.udawos.pioneer.actors.Actor;
import com.udawos.pioneer.actors.Char;
import com.udawos.pioneer.actors.mobs.AlphaWolf;
import com.udawos.pioneer.items.Heap;
import com.udawos.pioneer.items.Item;
import com.udawos.pioneer.levels.painters.Painter;
import com.udawos.pioneer.scenes.GameScene;
import com.udawos.utils.Bundle;
import com.udawos.utils.Random;

public class C028HillLevel extends Level {

    {
        color1 = 0x4b6636;
        color2 = 0xf2f2f2;
        viewDistance = 16;
    }

    private static final int START_XF  = 12;
    private static final int WEST_1F    = 5;
    private static final int WEST_2F    = 2;
    private static final int WEST_3F    = 2;

    private static final int START_YF   = 0;
    private static final int SOUTH_1F   = 15;
    private static final int SOUTH_2F   = 19;
    private static final int SOUTH_3F   = 15;

    private static final int START_XG  = 11;
    private static final int WEST_1G   = 7;
    private static final int WEST_2G    = 3;
    private static final int WEST_3G    = 1;

    private static final int START_YG   = 0;
    private static final int SOUTH_1G   = 13;
    private static final int SOUTH_2G   = 18;
    private static final int SOUTH_3G   = 13;

    private static final int START_XH  = 8;
    private static final int WEST_1H   = 9;



    private static final int START_YH   = 0;
    private static final int SOUTH_1H   = 11;
    private static final int SOUTH_2H   = 17;


    private static final int NW_CORNER_X2	= 1;
    private static final int NW_CORNER_Y2	= 1;
    private static final int SQUARE_WIDTH2	= 6;
    private static final int SQUARE_HEIGHT2	= 6;
    private static final int NE_CORNER_X2	= NW_CORNER_X2 + SQUARE_WIDTH2;

    @Override
    public String tilesTex() { return Assets.TILES_GRASS;
    }

    @Override
    public String waterTex() {
        return Assets.WATER_CITY;
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

        //Contour 1 W
        Painter.fill(this, START_XF, START_YF, 1, SOUTH_1F, Terrain.MOUNTAIN_E);
        Painter.fill(this, START_XF-WEST_1F, START_YF+SOUTH_1F, WEST_1F, 1, Terrain.MOUNTAIN_S);
        Painter.fill(this, START_XF-WEST_1F, START_YF+SOUTH_1F, 1, SOUTH_2F, Terrain.MOUNTAIN_E);
        Painter.fill(this, START_XF-WEST_1F-WEST_2F, START_YF+SOUTH_1F+SOUTH_2F, WEST_2F, 1, Terrain.MOUNTAIN_S);
        Painter.fill( this, START_XF-WEST_1F-WEST_2F, START_YF+SOUTH_1F+SOUTH_2F, 1, SOUTH_3F, Terrain.MOUNTAIN_E);
        Painter.fill(this, START_XF-WEST_1F-WEST_2F-WEST_3F, START_YF+SOUTH_1F+SOUTH_2F+SOUTH_3F, WEST_3F, 1, Terrain.MOUNTAIN_S);

        //Contour 2 W
        Painter.fill(this, START_XG, START_YG, 1, SOUTH_1G, Terrain.MOUNTAIN_E);
        Painter.fill(this, START_XG-WEST_1G, START_YG+SOUTH_1G, WEST_1G, 1, Terrain.MOUNTAIN_S);
        Painter.fill(this, START_XG-WEST_1G, START_YG+SOUTH_1G, 1, SOUTH_2G, Terrain.MOUNTAIN_E);
        Painter.fill(this, START_XG-WEST_1G-WEST_2G, START_YG+SOUTH_1G+SOUTH_2G, WEST_2G, 1, Terrain.MOUNTAIN_S);
        Painter.fill( this, START_XG-WEST_1G-WEST_2G, START_YG+SOUTH_1G+SOUTH_2G, 1, SOUTH_3G, Terrain.MOUNTAIN_E);
        Painter.fill(this, START_XG-WEST_1G-WEST_2G-WEST_3G, START_YG+SOUTH_1G+SOUTH_2G+SOUTH_3G, WEST_3G, 1, Terrain.MOUNTAIN_S);

        //Contour 3 W
        Painter.fill(this, START_XH, START_YH, 1, SOUTH_1H, Terrain.MOUNTAIN_E);
        Painter.fill(this, START_XH-WEST_1H, START_YH+SOUTH_1H, WEST_1H, 1, Terrain.MOUNTAIN_S);
        Painter.fill(this, START_XH-WEST_1H, START_YH+SOUTH_1H, 1, SOUTH_2H, Terrain.MOUNTAIN_E);


        Painter.fill(this, NW_CORNER_X2, NW_CORNER_Y2, SQUARE_WIDTH2, 1, Terrain.MOUNTAIN_N);
        Painter.fill(this, NW_CORNER_X2-1, NW_CORNER_Y2 , 1, 1, Terrain.MOUNTAIN_CORNER_NW);
        Painter.fill(this, NW_CORNER_X2-1, NW_CORNER_Y2+1 , 1, SQUARE_HEIGHT2, Terrain.MOUNTAIN_W);
        Painter.fill(this, NW_CORNER_X2 -1, NW_CORNER_Y2+SQUARE_HEIGHT2 , 1, 1, Terrain.MOUNTAIN_CORNER_SW);
        Painter.fill(this, NW_CORNER_X2 , NW_CORNER_Y2+SQUARE_HEIGHT2, SQUARE_WIDTH2, 1, Terrain.MOUNTAIN_S);
        Painter.fill(this, NE_CORNER_X2 , NW_CORNER_Y2+SQUARE_HEIGHT2, 1, 1, Terrain.MOUNTAIN_CORNER_SE);
        Painter.fill(this, NE_CORNER_X2, NW_CORNER_Y2 , 1, SQUARE_HEIGHT2, Terrain.MOUNTAIN_E);
        Painter.fill(this, NE_CORNER_X2, NW_CORNER_Y2, 1, 1, Terrain.MOUNTAIN_CORNER_NE);


        east = 1098;
        map[east] = Terrain.EAST;

        west = 1151;
        map[west] = Terrain.WEST;

        north = 75;
        map[north] = Terrain.NORTH;

        south =  2425;
        map[south] = Terrain.SOUTH;

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
            if (map[i] == Terrain.MOUNTAIN_S && map[i+1] == Terrain.MOUNTAIN_W) {
                map[i+1] = Terrain.MOUNTAIN_ELBOW_NE;
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
            if (map[i] == Terrain.EMPTY && map[i - 1] == Terrain.MOUNTAIN_S && map[i-50] == Terrain.MOUNTAIN_E) {
                map[i] = Terrain.MOUNTAIN_CORNER_SE;
            }
        }
        for (int i=WIDTH; i < LENGTH - WIDTH; i++) {
            if (map[i] == Terrain.MOUNTAIN_E && map[i+1] == Terrain.MOUNTAIN_S) {
                map[i] = Terrain.MOUNTAIN_ELBOW_NW;
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
                        Random.IntRange( 6, 10 - 2 ) +
                                Random.IntRange( 7 + 1, 12 ) * WIDTH;
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

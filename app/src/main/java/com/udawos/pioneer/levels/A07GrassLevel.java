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

public class A07GrassLevel extends Level {

    {
        color1 = 0x4b6636;
        color2 = 0xf2f2f2;
        viewDistance = 16;
    }

    private static final int NW_CORNER_X	= 32;
    private static final int NW_CORNER_Y	= 33;
    private static final int SQUARE_WIDTH	= 12;
    private static final int SQUARE_LENGTH	= 11;
    private static final int NE_CORNER_X	= NW_CORNER_X + SQUARE_WIDTH;

    private static final int NW_CORNER_X2	= 34;
    private static final int NW_CORNER_Y2	= 35;
    private static final int SQUARE_WIDTH2	= 5;
    private static final int SQUARE_LENGTH2	= 5;
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

        //Knoll 1
        //Outside perimeter 12x11 @ 22,23
        Painter.fill(this, NW_CORNER_X, NW_CORNER_Y , SQUARE_WIDTH, 1, Terrain.MOUNTAIN_N);
        Painter.fill(this, NW_CORNER_X-1, NW_CORNER_Y , 1, 1, Terrain.MOUNTAIN_CORNER_NW);
        Painter.fill(this, NW_CORNER_X-1, NW_CORNER_Y+1 , 1, SQUARE_LENGTH, Terrain.MOUNTAIN_W);
        Painter.fill(this, NW_CORNER_X -1, NW_CORNER_Y+SQUARE_LENGTH , 1, 1, Terrain.MOUNTAIN_CORNER_SW);
        Painter.fill(this, NW_CORNER_X , NW_CORNER_Y+SQUARE_LENGTH , SQUARE_WIDTH, 1, Terrain.MOUNTAIN_S);
        Painter.fill(this, NE_CORNER_X , NW_CORNER_Y+SQUARE_LENGTH , 1, 1, Terrain.MOUNTAIN_CORNER_SE);
        Painter.fill(this, NE_CORNER_X , NW_CORNER_Y , 1, SQUARE_LENGTH, Terrain.MOUNTAIN_E);
        Painter.fill(this, NE_CORNER_X , NW_CORNER_Y , 1, 1, Terrain.MOUNTAIN_CORNER_NE);

        //Inside perimeter 5x5 @24,25
        Painter.fill(this, NW_CORNER_X2, NW_CORNER_Y2 , SQUARE_WIDTH2, 1, Terrain.MOUNTAIN_N);
        Painter.fill(this, NW_CORNER_X2-1, NW_CORNER_Y2 , 1, 1, Terrain.MOUNTAIN_CORNER_NW);
        Painter.fill(this, NW_CORNER_X2-1, NW_CORNER_Y2+1 , 1, SQUARE_LENGTH2, Terrain.MOUNTAIN_W);
        Painter.fill(this, NW_CORNER_X2 -1, NW_CORNER_Y2+SQUARE_LENGTH2 , 1, 1, Terrain.MOUNTAIN_CORNER_SW);
        Painter.fill(this, NW_CORNER_X2 , NW_CORNER_Y2+SQUARE_LENGTH2 , SQUARE_WIDTH2, 1, Terrain.MOUNTAIN_S);
        Painter.fill(this, NE_CORNER_X2 , NW_CORNER_Y2+SQUARE_LENGTH2 , 1, 1, Terrain.MOUNTAIN_CORNER_SE);
        Painter.fill(this, NE_CORNER_X2 , NW_CORNER_Y2 , 1, SQUARE_LENGTH2, Terrain.MOUNTAIN_E);
        Painter.fill(this, NE_CORNER_X2 , NW_CORNER_Y2 , 1, 1, Terrain.MOUNTAIN_CORNER_NE);

        //Summit
        Painter.fill(this, 24, 26, 5, 4, Terrain.HILL);

        //Stash



        boolean[] patch = Patch.generate( 0.45f, 6 );
        for (int i=0; i < LENGTH; i++) {
            if (map[i] == Terrain.EMPTY && patch[i] ) {
                map[i] = Terrain.HIGH_GRASS;
            }
        }


        west = 1151;
        map[west] = Terrain.WEST;

        north = 75;
        map[north] = Terrain.NORTH;

        east = 1098;
        map[east] = Terrain.EAST;


        return true;
    }

    @Override
    protected void decorate() {


       /* for (int i=WIDTH + 1; i < LENGTH - WIDTH; i++) {
            if (map[i] == Terrain.GRADUAL_SLOPE) {
                int n = 0;
                if (map[i + 1] == Terrain.GRADUAL_SLOPE) {
                    n++;
                }
                if (map[i - 1] == Terrain.GRADUAL_SLOPE) {
                    n++;
                }
                if (map[i + WIDTH] == Terrain.GRADUAL_SLOPE) {
                    n++;
                }
                if (map[i - WIDTH] == Terrain.GRADUAL_SLOPE) {
                    n++;
                }
                if (Random.Int( 4 ) <= n) {
                    map[i] = Terrain.GRADUAL_SLOPE;
                }
            }
        }
        for (int i=WIDTH + 1; i < LENGTH - WIDTH; i++) {
            if (map[i] == Terrain.GRADUAL_SLOPE) {
                int n = 0;
                if (map[i + 1] == Terrain.HILL) {
                    n++;
                }
                if (map[i - 1] == Terrain.HILL) {
                    n++;
                }
                if (map[i + WIDTH] == Terrain.HILL) {
                    n++;
                }
                if (map[i - WIDTH] == Terrain.HILL) {
                    n++;
                }
                if (Random.Int( 8 ) <= n) {
                    map[i] = Terrain.HILL;
                }
            }

        }*/
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

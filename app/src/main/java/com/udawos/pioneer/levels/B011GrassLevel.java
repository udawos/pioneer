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

public class B011GrassLevel extends Level {

    {
        color1 = 0x4b6636;
        color2 = 0xf2f2f2;
        viewDistance = 16;
    }

    //contour generator test
    private static final int START_XA   = 0;
    private static final int EAST_1A    = 10;
    private static final int EAST_2A    = 2;
    private static final int END_XA     = 49-(EAST_1A+EAST_2A);

    private static final int START_YA   = 40;
    private static final int NORTH_1A   = 7;
    private static final int SOUTH_1A   = 8;

    private static final int START_XB   = 0;
    private static final int EAST_1B    = EAST_1A-3;
    private static final int EAST_2B    = EAST_2A+6;
    private static final int END_XB     = 49-(EAST_1B+EAST_2B);

    private static final int START_YB   = 25;
    private static final int NORTH_1B   = NORTH_1A+5;
    private static final int SOUTH_1B   = (SOUTH_1A-1)*2;

    private static final int START_XC   = 0;
    private static final int EAST_1C    = EAST_1B-3;
    private static final int EAST_2C    = EAST_2B+6;
    private static final int END_XC     = 49-(EAST_1C+EAST_2C);

    private static final int START_YC   = 18;
    private static final int NORTH_1C   = NORTH_1B+5;
    private static final int SOUTH_1C   = (SOUTH_1B-1);


    private static final int NW_CORNER_X2	= 36;
    private static final int NW_CORNER_Y2	= 0;
    private static final int SQUARE_WIDTH2	= 13;
    private static final int SQUARE_HEIGHT2	= 10;
    private static final int NE_CORNER_X2	= NW_CORNER_X2 + SQUARE_WIDTH2;

    private static final int NW_CORNER_X3	= 39;
    private static final int NW_CORNER_Y3	= 0;
    private static final int SQUARE_WIDTH3	= 7;
    private static final int SQUARE_LENGTH3	= 6;
    private static final int NE_CORNER_X3	= NW_CORNER_X3 + SQUARE_WIDTH3;


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
        super.storeInBundle(bundle);
    }

    @Override
    public void restoreFromBundle( Bundle bundle ) {
        super.restoreFromBundle(bundle);
    }

    @Override
    protected boolean build() {



        //Contour 1
        Painter.fill(this, START_XA, START_YA, EAST_1A+1, 1, Terrain.MOUNTAIN_S);
        Painter.fill(this, EAST_1A, START_YA-NORTH_1A, 1, NORTH_1A, Terrain.MOUNTAIN_E);
        Painter.fill(this, EAST_1A, START_YA-NORTH_1A, EAST_2A, 1, Terrain.MOUNTAIN_S);
        Painter.fill(this, EAST_1A+EAST_2A, START_YA-NORTH_1A, 1, SOUTH_1A, Terrain.MOUNTAIN_W);
        Painter.fill(this, EAST_1A+EAST_2A, START_YA-NORTH_1A+SOUTH_1A, END_XA, 1, Terrain.MOUNTAIN_S);

        //Contour 2
        Painter.fill(this, START_XB, START_YB, EAST_1B+1, 1, Terrain.MOUNTAIN_S);
        Painter.fill(this, EAST_1B, START_YB-NORTH_1B, 1, NORTH_1B, Terrain.MOUNTAIN_E);
        Painter.fill(this, EAST_1B, START_YB-NORTH_1B, EAST_2B, 1, Terrain.MOUNTAIN_S);
        Painter.fill(this, EAST_1B+EAST_2B, START_YB-NORTH_1B, 1, SOUTH_1B, Terrain.MOUNTAIN_W);
        Painter.fill(this, EAST_1B+EAST_2B, START_YB-NORTH_1B+SOUTH_1B, END_XB, 1, Terrain.MOUNTAIN_S);

        //Contour 3
        Painter.fill(this, START_XC, START_YC, EAST_1C+1, 1, Terrain.MOUNTAIN_S);
        Painter.fill(this, EAST_1C, START_YC-NORTH_1C, 1, NORTH_1C, Terrain.MOUNTAIN_E);
        Painter.fill(this, EAST_1C, START_YC-NORTH_1C, EAST_2C, 1, Terrain.MOUNTAIN_S);
        Painter.fill(this, EAST_1C+EAST_2C, START_YC-NORTH_1C, 1, SOUTH_1C, Terrain.MOUNTAIN_W);
        Painter.fill(this, EAST_1C+EAST_2C, START_YC-NORTH_1C+SOUTH_1C, END_XC, 1, Terrain.MOUNTAIN_S);




        //upper hills

        Painter.fill(this, NW_CORNER_X2, NW_CORNER_Y2 , SQUARE_WIDTH2, 1, Terrain.MOUNTAIN_N);
        Painter.fill(this, NW_CORNER_X2-1, NW_CORNER_Y2 , 1, 1, Terrain.MOUNTAIN_CORNER_NW);
        Painter.fill(this, NW_CORNER_X2-1, NW_CORNER_Y2+1 , 1, SQUARE_HEIGHT2, Terrain.MOUNTAIN_W);
        Painter.fill(this, NW_CORNER_X2 -1, NW_CORNER_Y2+SQUARE_HEIGHT2 , 1, 1, Terrain.MOUNTAIN_CORNER_SW);
        Painter.fill(this, NW_CORNER_X2 , NW_CORNER_Y2+SQUARE_HEIGHT2 , SQUARE_WIDTH2, 1, Terrain.MOUNTAIN_S);
        Painter.fill(this, NE_CORNER_X2 , NW_CORNER_Y2+SQUARE_HEIGHT2 , 1, 1, Terrain.MOUNTAIN_CORNER_SE);
        Painter.fill(this, NE_CORNER_X2, NW_CORNER_Y2 , 1, SQUARE_HEIGHT2, Terrain.MOUNTAIN_E);
        Painter.fill(this, NE_CORNER_X2, NW_CORNER_Y2, 1, 1, Terrain.MOUNTAIN_CORNER_NE);


        Painter.fill(this, NW_CORNER_X3, NW_CORNER_Y3 , SQUARE_WIDTH3, 1, Terrain.MOUNTAIN_N);
        Painter.fill(this, NW_CORNER_X3-1, NW_CORNER_Y3 , 1, 1, Terrain.MOUNTAIN_CORNER_NW);
        Painter.fill(this, NW_CORNER_X3-1, NW_CORNER_Y3+1 , 1, SQUARE_LENGTH3, Terrain.MOUNTAIN_W);
        Painter.fill(this, NW_CORNER_X3 -1, NW_CORNER_Y3+SQUARE_LENGTH3 , 1, 1, Terrain.MOUNTAIN_CORNER_SW);
        Painter.fill(this, NW_CORNER_X3 , NW_CORNER_Y3+SQUARE_LENGTH3 , SQUARE_WIDTH3, 1, Terrain.MOUNTAIN_S);
        Painter.fill(this, NE_CORNER_X3 , NW_CORNER_Y3+SQUARE_LENGTH3 , 1, 1, Terrain.MOUNTAIN_CORNER_SE);
        Painter.fill(this, NE_CORNER_X3 , NW_CORNER_Y3 , 1, SQUARE_LENGTH3, Terrain.MOUNTAIN_E);
        Painter.fill(this, NE_CORNER_X3, NW_CORNER_Y3, 1, 1, Terrain.MOUNTAIN_CORNER_NE);


        boolean[] patch = Patch.generate( 0.45f, 6 );
        for (int i=0; i < LENGTH; i++) {
            if (map[i] == Terrain.EMPTY && patch[i] ) {
                map[i] = Terrain.HIGH_GRASS;
            }
        }


        //ENCOUNTER at 30,40

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
            if (map[i] == Terrain.MOUNTAIN_N && map[i-1] == Terrain.MOUNTAIN_N && map[i+50] == Terrain.MOUNTAIN_E) {
                map[i+50] = Terrain.MOUNTAIN_CORNER_NE;
            }
        }

        for (int i=WIDTH; i < LENGTH - WIDTH; i++) {
            if (map[i] == Terrain.MOUNTAIN_S && map[i-1] == Terrain.MOUNTAIN_S && map[i+50] == Terrain.MOUNTAIN_E) {
                map[i+50] = Terrain.MOUNTAIN_CORNER_NW;
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
    }

    @Override
    public int nMobs() {
        return 4;
    }

    @Override
    protected void createMobs() {
    for (int i=0; i < NEIGHBOURS4.length; i++) {
            Mob mob = Bestiary.mob(Dungeon.depth);
            do {
                mob.pos = 774;
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
                        Random.IntRange( 1, 2 ) +
                                Random.IntRange( 1, 2) * 50;
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
                boss.pos = Random.Int( HEIGHT );
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

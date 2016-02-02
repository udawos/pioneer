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
import com.udawos.pioneer.Dungeon;
import com.udawos.pioneer.actors.Actor;
import com.udawos.pioneer.actors.Char;
import com.udawos.pioneer.items.Generator;
import com.udawos.pioneer.items.Heap;
import com.udawos.pioneer.items.Item;
import com.udawos.pioneer.levels.painters.Painter;
import com.udawos.pioneer.scenes.GameScene;
import com.udawos.utils.Bundle;
import com.udawos.utils.Random;

public class C023RuinedTemple extends Level {
    //It's now a ruined forest temple

    {
        color1 = 0x4b6636;
        color2 = 0xf2f2f2;
        viewDistance = 16;
    }

    //Major contour lines
    private static final int START_XA   = 40;
    private static final int EAST_1A    = 5;
    private static final int EAST_2A    = 4;

    private static final int START_YA   = 34;
    private static final int NORTH_1A   = 15;
    private static final int SOUTH_1A   = 11;

    private static final int START_XB   = 38;
    private static final int EAST_1B   = 9;
    private static final int EAST_2B    = 2;

    private static final int START_YB   = 32;
    private static final int NORTH_1B   = 17;
    private static final int SOUTH_1B   = 10;

    private static final int START_XC   = 0;
    private static final int EAST_1C   = 10;

    private static final int START_YC   = 45;
    private static final int SOUTH_1C   = 4;

    private static final int START_XD   = 0;
    private static final int EAST_1D   = 12;

    private static final int START_YD   = 41;
    private static final int SOUTH_1D   = 8;

    private static final int START_XE   = 0;
    private static final int EAST_1E   = 16;

    private static final int START_YE   = 31;
    private static final int SOUTH_1E   = 15;

    private static final int START_XF   = 0;
    private static final int EAST_1F   = 13;

    private static final int START_YF   = 27;
    private static final int SOUTH_1F   = 7;



    @Override
    public String tilesTex() { return Assets.TILES_VILLAGE;
    }

    @Override
    public String waterTex() {
        return Assets.WATER_SEWERS;
    }

    protected boolean[] water() {
        return Patch.generate( feeling == Feeling.CHASM ? 0.60f : 0.45f, 6 );
    }

    private boolean escaped = false;
    private int entranceCollapsed;


    private static final String ESCAPED	= "escaped";
    private static final String COLLAPSED	= "entranceCollapsed";


    @Override
    public void storeInBundle( Bundle bundle ) {
        super.storeInBundle(bundle);
        bundle.put(ESCAPED, escaped);
        bundle.put(COLLAPSED, entranceCollapsed);
    }

    @Override
    public void restoreFromBundle( Bundle bundle ) {
        super.restoreFromBundle(bundle);
        escaped = bundle.getBoolean( ESCAPED );
        entranceCollapsed = bundle.getInt( COLLAPSED );
    }

    @Override
    protected boolean build() {

        //Contour 1
        Painter.fill(this, START_XA, START_YA, 1, NORTH_1A, Terrain.MOUNTAIN_E);
        Painter.fill(this, START_XA, START_YA, EAST_1A, 1, Terrain.MOUNTAIN_S);
        Painter.fill(this, START_XA, START_YA, 1, 1, Terrain.MOUNTAIN_ELBOW_NW);
        Painter.fill(this, START_XA+EAST_1A, START_YA, 1, SOUTH_1A, Terrain.MOUNTAIN_W);
        Painter.fill(this, START_XA+EAST_1A, START_YA, 1, 1, Terrain.MOUNTAIN_ELBOW_NE);
        Painter.fill(this, START_XA+EAST_1A, START_YA+SOUTH_1A, EAST_2A, 1, Terrain.MOUNTAIN_S);
        Painter.fill(this, START_XA+EAST_1A, START_YA+SOUTH_1A, 1, 1, Terrain.MOUNTAIN_CORNER_SW);

        //Contour 2
        Painter.fill(this, START_XB, START_YB, 1, NORTH_1B, Terrain.MOUNTAIN_E);
        Painter.fill(this, START_XB, START_YB, EAST_1B, 1, Terrain.MOUNTAIN_S);
        Painter.fill(this, START_XB, START_YB, 1, 1, Terrain.MOUNTAIN_ELBOW_NW);
        Painter.fill(this, START_XB+EAST_1B, START_YB, 1, SOUTH_1B, Terrain.MOUNTAIN_W);
        Painter.fill(this, START_XB+EAST_1B, START_YB, 1, 1, Terrain.MOUNTAIN_ELBOW_NE);
        Painter.fill(this, START_XB+EAST_1B, START_YB+SOUTH_1B, EAST_2B, 1, Terrain.MOUNTAIN_S);
        Painter.fill(this, START_XB+EAST_1B, START_YB+SOUTH_1B, 1, 1, Terrain.MOUNTAIN_CORNER_SW);

        //Contour 3
        Painter.fill(this, START_XC, START_YC, EAST_1C, 1, Terrain.MOUNTAIN_S);
        Painter.fill(this, START_XC+EAST_1C, START_YC, 1, SOUTH_1C, Terrain.MOUNTAIN_W);
        Painter.fill(this, START_XC+EAST_1C, START_YC, 1, 1, Terrain.MOUNTAIN_ELBOW_NE);

        //Contour 4
        Painter.fill(this, START_XD, START_YD, EAST_1D, 1, Terrain.MOUNTAIN_S);
        Painter.fill(this, START_XD+EAST_1D, START_YD, 1, SOUTH_1D, Terrain.MOUNTAIN_W);
        Painter.fill(this, START_XD+EAST_1D, START_YD, 1, 1, Terrain.MOUNTAIN_ELBOW_NE);

        //Contour 5
        Painter.fill(this, START_XE, START_YE, EAST_1E, 1, Terrain.MOUNTAIN_N);
        Painter.fill(this, START_XE+EAST_1E, START_YE-SOUTH_1E, 1, SOUTH_1E, Terrain.MOUNTAIN_W);
        Painter.fill(this, START_XE, START_YE-SOUTH_1E, EAST_1E, 1, Terrain.MOUNTAIN_S);
        Painter.fill(this, START_XE+EAST_1E, START_YE, 1, 1, Terrain.MOUNTAIN_ELBOW_SE);
        Painter.fill(this, START_XE+EAST_1E, START_YE-SOUTH_1E, 1, 1, Terrain.MOUNTAIN_ELBOW_NE);


        //Contour 6
        Painter.fill(this, START_XF, START_YF, EAST_1F, 1, Terrain.MOUNTAIN_N);
        Painter.fill(this, START_XF+EAST_1F, START_YF-SOUTH_1F, 1, SOUTH_1F, Terrain.MOUNTAIN_W);
        Painter.fill(this, START_XF, START_YF-SOUTH_1F, EAST_1F, 1, Terrain.MOUNTAIN_S);
        Painter.fill(this, START_XF+EAST_1F, START_YF, 1, 1, Terrain.MOUNTAIN_ELBOW_SE);
        Painter.fill(this, START_XF+EAST_1F, START_YF-SOUTH_1F, 1, 1, Terrain.MOUNTAIN_ELBOW_NE);


        //sacrificial pit
        Painter.fill( this, 24, 26, 4, 4, Terrain.WATER);

        //altar building
        Painter.fill( this, 22, 17,
                8 , 6, Terrain.RUINED_WALL );
        Painter.fill( this, 23, 18,
                6, 6, Terrain.EMPTY_SP );



        //altar
        Painter.fill( this, 25, 20, 2, 1, Terrain.PEDESTAL);

        //west side
        Painter.fill( this, 20, 23,
                2 , 2, Terrain.RUINED_WALL );

        Painter.fill( this, 17, 26,
                2 , 2, Terrain.RUINED_WALL );

        Painter.fill( this, 17, 30,
                2 , 2, Terrain.RUINED_WALL );

        Painter.fill( this, 21, 33,
                2 , 2, Terrain.RUINED_WALL );

        //east side
        Painter.fill( this, 30, 23,
                2 , 2, Terrain.RUINED_WALL );

        Painter.fill( this, 33, 26,
                2 , 2, Terrain.RUINED_WALL );

        Painter.fill( this, 33, 30,
                2 , 2, Terrain.RUINED_WALL );

        Painter.fill( this, 29, 33,
                2 , 2, Terrain.RUINED_WALL );


        west = 1151;
        map[west] = Terrain.WEST;

        east = 1098;
        map[east] = Terrain.EAST;

        north = 75;
        map[north] = Terrain.NORTH;

        south =  2425;
        map[south] = Terrain.SOUTH;


        down = 922;
        map[down] = Terrain.EXIT;

        entranceCollapsed = 922;

        return true;
    }


    @Override
    protected void decorate() {
            for (int i=WIDTH + 1; i < LENGTH - WIDTH; i++) {
                if (map[i] == Terrain.EMPTY) {
                    int n = 0;
                    if (map[i + 1] == Terrain.PEDESTAL) {
                        n++;
                    }
                    if (map[i - 1] == Terrain.PEDESTAL) {
                        n++;
                    }
                    if (map[i + WIDTH] == Terrain.PEDESTAL) {
                        n++;
                    }
                    if (map[i - WIDTH] == Terrain.PEDESTAL) {
                        n++;
                    }
                    if (Random.Int(14) <= n) {
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

       Item prize;
        switch (Random.Int(1)) {
            case 0:
                prize = Generator.random( Generator.Category.GOLD );
                break;
            case 1:
                prize = Generator.random( Generator.Category.GOLD);
                break;
            default:
                prize = Generator.random( Generator.Category.GOLD );
                break;
        }

        this.drop(prize, 922).type = Heap.Type.TOMB;

    }

    @Override
    public int randomRespawnCell() {
        return -1;
    }

   @Override
    public void press( int cell, Char hero ) {

        super.press(cell, hero);

       /* if (hero == Dungeon.hero) {

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

            Dungeon.observe();*/
       if (U2TheEye.escaped) {
           escaped = true;

           set(entranceCollapsed, Terrain.RUBBLE);
           GameScene.updateMap(entranceCollapsed);
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
            case Terrain.PEDESTAL:
                return "Trees";
            case Terrain.RUBBLE:
                return "The entrance has collapsed here";
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


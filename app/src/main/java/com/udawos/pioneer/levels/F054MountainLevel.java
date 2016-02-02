
package com.udawos.pioneer.levels;

import com.udawos.pioneer.Assets;
import com.udawos.pioneer.Bones;
import com.udawos.pioneer.actors.Actor;
import com.udawos.pioneer.actors.mobs.npcs.WildSheep;
import com.udawos.pioneer.items.Heap;
import com.udawos.pioneer.items.Item;
import com.udawos.pioneer.levels.painters.Painter;
import com.udawos.utils.Bundle;
import com.udawos.utils.Random;

public class F054MountainLevel extends Level {

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

        //West Mountain east side
        Painter.fill(this, 30, 4, 1, 25, Terrain.MOUNTAIN_E);
        Painter.fill(this, 29, 4, 1, 25, Terrain.MOUNTAIN_E);
        Painter.fill(this, 28, 5, 1, 21, Terrain.MOUNTAIN_E);
        Painter.fill(this, 27, 6, 1, 20, Terrain.MOUNTAIN_E);
        Painter.fill(this, 26, 7, 1, 18, Terrain.MOUNTAIN_E);
        Painter.fill(this, 25, 8, 1, 16, Terrain.MOUNTAIN_E);
        Painter.fill(this, 24, 9, 1, 14, Terrain.MOUNTAIN_E);
        Painter.fill(this, 23, 10, 1, 12, Terrain.MOUNTAIN_E);
        Painter.fill(this, 22, 11, 1, 10, Terrain.MOUNTAIN_E);
        Painter.fill(this, 21, 12, 1, 8, Terrain.MOUNTAIN_E);
        Painter.fill(this, 20, 13, 1, 6, Terrain.MOUNTAIN_E);
        Painter.fill(this, 19, 14, 1, 4, Terrain.MOUNTAIN_E);
        Painter.fill(this, 18, 15, 1, 2, Terrain.MOUNTAIN_E);

        //West Mountain south side
        Painter.fill(this, 0, 16, 19, 1, Terrain.MOUNTAIN_S);
        Painter.fill(this, 0, 17, 20, 1, Terrain.MOUNTAIN_S);
        Painter.fill(this, 0, 18, 21, 1, Terrain.MOUNTAIN_S);
        Painter.fill(this, 0, 19, 22, 1, Terrain.MOUNTAIN_S);
        Painter.fill(this, 0, 20, 23, 1, Terrain.MOUNTAIN_S);
        Painter.fill(this, 0, 21, 24, 1, Terrain.MOUNTAIN_S);
        Painter.fill(this, 0, 22, 25, 1, Terrain.MOUNTAIN_S);
        Painter.fill(this, 0, 23, 26, 1, Terrain.MOUNTAIN_S);
        Painter.fill(this, 0, 24, 27, 1, Terrain.MOUNTAIN_S);
        Painter.fill(this, 0, 25, 28, 1, Terrain.MOUNTAIN_S);
        Painter.fill(this, 0, 27, 29, 1, Terrain.MOUNTAIN_S);
        Painter.fill(this, 0, 28, 30, 1, Terrain.MOUNTAIN_S);


        //West Mountain north side

        Painter.fill(this, 0, 14, 20,1, Terrain.MOUNTAIN_N);
        Painter.fill(this, 0, 13, 21,1, Terrain.MOUNTAIN_N);
        Painter.fill(this, 0, 12, 22,1, Terrain.MOUNTAIN_N);
        Painter.fill(this, 0, 11, 23,1, Terrain.MOUNTAIN_N);
        Painter.fill(this, 0, 10, 24,1, Terrain.MOUNTAIN_N);
        Painter.fill(this, 0, 9, 25,1, Terrain.MOUNTAIN_N);
        Painter.fill(this, 0, 8, 26,1, Terrain.MOUNTAIN_N);
        Painter.fill(this, 0, 7, 27,1, Terrain.MOUNTAIN_N);
        Painter.fill(this, 0, 6, 28,1, Terrain.MOUNTAIN_N);
        Painter.fill(this, 0, 5, 29,1, Terrain.MOUNTAIN_N);
        Painter.fill(this, 0, 4, 30,1, Terrain.MOUNTAIN_N);


        //East Mountain west side
        Painter.fill(this, 33, 21, 1, 23, Terrain.MOUNTAIN_W);
        Painter.fill(this, 35, 23, 1, 19, Terrain.MOUNTAIN_W);
        Painter.fill(this, 36, 24, 1, 17, Terrain.MOUNTAIN_W);

        //East Mountain south side
        Painter.fill(this, 36, 40, 14, 1, Terrain.MOUNTAIN_S);
        Painter.fill(this, 35, 41, 16, 1, Terrain.MOUNTAIN_S);
        Painter.fill(this, 33, 43, 17, 1, Terrain.MOUNTAIN_S);

        //East Mountain north side
        Painter.fill(this, 33, 21, 16, 1, Terrain.MOUNTAIN_N);
        Painter.fill(this, 35, 22, 14, 1, Terrain.MOUNTAIN_N);
        Painter.fill(this, 36, 24, 13, 1, Terrain.MOUNTAIN_N);


        west = 1451;
        map[west] = Terrain.WEST;

        east = 1048;
        map[east] = Terrain.EAST;

        north = 75;
        map[north] = Terrain.NORTH;

        south =  2425;
        map[south] = Terrain.SOUTH;

        down = 1425;
        map[down] = Terrain.EXIT;

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
    }


    @Override
    public int nMobs() {
        return 1;
    }

    @Override
    protected void createMobs() {
        WildSheep.spawn(this);
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
                        Random.IntRange( 10, 18 ) +
                                Random.IntRange( 16 , 25 ) * WIDTH;
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

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

/**
 * Created by Jake on 27/01/2016.
 */
public class H078ArmEntrance extends Level {

        {
            color1 = 0x4b6636;
            color2 = 0xf2f2f2;
        }


    private int aNW = 32;
    private int bNW = 22;
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
            super.storeInBundle(bundle);
        }

        @Override
        public void restoreFromBundle( Bundle bundle ) {
            super.restoreFromBundle(bundle);
        }

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

            down = 1385;
            map[down] = Terrain.EXIT;

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
                if (map[i] == Terrain.EMPTY && Random.Int(1249) == 0) {
                    map[i] = Terrain.WALL_DECO;
                }
            }

        }


        @Override
        public int nMobs() {
            return 1;
        }

        @Override
        protected void createMobs() {
            if (Random.Int(1,20) == 20) {
                for (int i = 0; i < 1; i++) {
                    Mob mob = Bestiary.mob(Dungeon.depth);
                    do {
                        mob.pos = 662;
                    } while (mob.pos == -1);
                    mobs.add(mob);
                    Actor.occupyCell(mob);
                }
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

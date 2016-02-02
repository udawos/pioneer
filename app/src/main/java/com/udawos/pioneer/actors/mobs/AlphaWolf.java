package com.udawos.pioneer.actors.mobs;

import com.udawos.pioneer.Dungeon;
import com.udawos.pioneer.actors.Char;
import com.udawos.pioneer.actors.buffs.Bleeding;
import com.udawos.pioneer.actors.buffs.Buff;
import com.udawos.pioneer.actors.hero.Hero;
import com.udawos.pioneer.levels.Level;
import com.udawos.pioneer.scenes.GameScene;
import com.udawos.pioneer.sprites.WolfSprite;
import com.udawos.utils.Random;

/**
 * Created by Jake on 26/01/2016.
 */
public class AlphaWolf extends Mob {


        {
            name =  "Fenrir";
            spriteClass = WolfSprite.class;

            HP = HT = 300;

            EXP = 50;

            state = PASSIVE;
        }

        private static final String TXT_DESC =
                "Something is different about this wolf pack. It is larger, more aggressive and faster. " +
                        "These wolves have a thirst for human blood born not of desperation, but of voracity .";

        private static int packCount = 0;

        public AlphaWolf() {
            super();
        }

        public void spawnWolves() {
            RegularWolf wolf1 = new RegularWolf();
            RegularWolf wolf2 = new RegularWolf();
            RegularWolf wolf3 = new RegularWolf();
            RegularWolf wolf4 = new RegularWolf();

            do {
                wolf1.pos = pos + Level.NEIGHBOURS8[Random.Int( 8 )];
                wolf2.pos = pos + Level.NEIGHBOURS8[Random.Int(8)];
                wolf3.pos = pos + Level.NEIGHBOURS8[Random.Int(8)];
                wolf4.pos = pos + Level.NEIGHBOURS8[Random.Int(8)];
            } while (!Level.passable[wolf1.pos] || !Level.passable[wolf2.pos] || !Level.passable[wolf3.pos]
                    || !Level.passable[wolf4.pos]|| wolf1.pos == wolf2.pos);

            GameScene.add(wolf1);
            GameScene.add( wolf2 );
            GameScene.add( wolf3 );
            GameScene.add( wolf4 );
        }

        @Override
        public void damage( int dmg, Object src ) {

            if (packCount > 3) {

                for (Mob mob : Dungeon.level.mobs) {
                    if (mob instanceof RegularWolf) {
                        mob.beckon( pos );
                    }
                }

               // dmg >>= packCount;
            }
            else if (packCount <= 2) {

                if (enemy instanceof Hero) {
                    state = FLEEING;

                }

                //dmg >>= packCount;
            }

            super.damage(dmg, src);
        }

        @Override
        public int defenseProc( Char enemy, int damage ) {


            return super.defenseProc(enemy, damage);
        }

        @Override
        public void beckon( int cell ) {
        }

        @SuppressWarnings("unchecked")
        @Override
        public void die( Object cause ) {

            for (Mob mob : (Iterable<Mob>)Dungeon.level.mobs.clone()) {
                if ( mob instanceof RegularWolf) {
                    mob.die( cause );
                }
            }

           // GameScene.bossSlain();
            //Dungeon.level.drop( new SkeletonKey(), pos ).sprite.drop();
            super.die( cause );

            yell( "..." );
        }

        @Override
        public void notice() {
            super.notice();
            yell( "The wolf howls...A primal fear grips your very soul." );
        }

        @Override
        public String description() {
            return TXT_DESC;

        }



        public static class RegularWolf extends Mob {
            

            {
                name = "wolf";
                spriteClass = WolfSprite.class;

                HP = HT = 70;
                defenseSkill = 25;

                EXP = 0;

                state = WANDERING;
            }

            public RegularWolf() {
                super();
                packCount++;
            }

            @Override
            public void die( Object cause ) {
                super.die( cause );
                packCount--;
            }

            @Override
            public int attackSkill( Char target ) {
                return 36;
            }

            @Override
            public int damageRoll() {
                return Random.NormalIntRange( 24, 36 );
            }

            @Override
            public int dr() {
                return 1;
            }

            @Override
            public void damage( int dmg, Object src ) {

                if (packCount > 3) {

                    for (Mob mob : Dungeon.level.mobs) {
                        if (mob instanceof RegularWolf) {
                            mob.beckon( pos );
                        }
                    }

                   // dmg >>= packCount;
                }
                else if (packCount <= 2) {

                        if (enemy instanceof Hero) {
                            state = FLEEING;

                    }

                    //dmg >>= packCount;
                }

                super.damage( dmg, src );
            }

            @Override
            public int attackProc( Char enemy, int damage ) {
                if (Random.Int( 3 ) == 0) {
                    Buff.affect(Dungeon.hero, Bleeding.class).set(damage);
                }

                return damage;
            }

            @Override
            public boolean act() {
                return super.act();
            }

            @Override
            public String description() {
                return TXT_DESC;

            }

            
        }




}

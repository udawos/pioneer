package com.udawos.pioneer.actors.mobs;

import com.udawos.pioneer.Dungeon;
import com.udawos.pioneer.actors.Char;
import com.udawos.pioneer.actors.buffs.Amok;
import com.udawos.pioneer.actors.buffs.Terror;
import com.udawos.pioneer.sprites.TreeMonsterSprite;
import com.udawos.pioneer.utils.GLog;
import com.udawos.utils.Random;

import java.util.HashSet;

/**
 * Created by Jake on 26/01/2016.
 */
public class TreeMonster extends Mob{

        public static final String TXT_DAZE	= "This is getting weird.";

        {
            name = "generic tree monster";
            spriteClass = TreeMonsterSprite.class;

            HP = HT = 70;
            defenseSkill = 30;
            baseSpeed = 0.9f;

            EXP = 11;
            maxLvl = 21;

            //loot = new BearPelt();
           // lootChance = 1.0f;
        }

        @Override
        public int damageRoll() {
            return Random.NormalIntRange(12, 16);
        }

        @Override
        public int attackSkill( Char target ) {
            return 30;
        }

        @Override
        protected float attackDelay() {
            return 0.5f;
        }

        @Override
        public int dr() {
            return 2;
        }

        @Override
        public String defenseVerb() {
            return "parried";
        }

        @Override
        public void die( Object cause ) {

            super.die( cause );
        }

        @Override
        public int attackProc( Char enemy, int damage ) {

            if ( enemy == Dungeon.hero) {

                GLog.w(TXT_DAZE);
            }

            return damage;
        }

        @Override
        public String description() {
            return
                    "It's a tree...monster?.";
        }

        private static final HashSet<Class<?>> IMMUNITIES = new HashSet<Class<?>>();
        static {
            IMMUNITIES.add( Amok.class );
            IMMUNITIES.add( Terror.class );
        }

        @Override
        public HashSet<Class<?>> immunities() {
            return IMMUNITIES;
        }
    }

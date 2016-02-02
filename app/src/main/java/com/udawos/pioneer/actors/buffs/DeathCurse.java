package com.udawos.pioneer.actors.buffs;

import com.udawos.pioneer.Dungeon;
import com.udawos.pioneer.actors.hero.Hero;
import com.udawos.pioneer.actors.hero.HeroClass;
import com.udawos.pioneer.items.rings.RingOfSatiety;
import com.udawos.pioneer.ui.BuffIndicator;
import com.udawos.pioneer.utils.GLog;
import com.udawos.utils.Bundle;
import com.udawos.utils.Random;

/**
 * Created by Jake on 25/01/2016.
 */
public class DeathCurse extends Buff implements Hero.Doom {
        private static final float STEP	= 1f;

        public static final float STRICKEN	= 10f;
        public static final float AFFLICTED	= 80f;

        private static final String TXT_STRICKEN	= "You taste blood. Something is terribly wrong.";
        private static final String TXT_AFFLICTED	= "You feel your head convulsing! You must leave this place!";
        private static final String TXT_DEATH		= "You suffered a fatal brain hemorrhage...";

        private float level;

        private static final String LEVEL	= "level";

        @Override
        public void storeInBundle( Bundle bundle ) {
            super.storeInBundle( bundle );
            bundle.put( LEVEL, level );
        }

        @Override
        public void restoreFromBundle( Bundle bundle ) {
            super.restoreFromBundle( bundle );
            level = bundle.getFloat( LEVEL );
        }

        @Override
        public boolean act() {
            if (target.isAlive() && Dungeon.depth == 85
                    //&& Dungeon.hero.belongings.getItem(CultistAmulet.class) == null
                    ) {

                Hero hero = (Hero)target;

                if (isAfflicted()) {
                    if (Random.Float() < 0.3f && (target.HP > 1 || !target.paralysed)) {

                        GLog.n(TXT_AFFLICTED);
                        hero.damage( 10, this );

                        hero.interrupt();
                    }
                } else {

                    int bonus = 0;
                    for (Buff buff : target.buffs( RingOfSatiety.Satiety.class )) {
                        bonus += ((RingOfSatiety.Satiety)buff).level;
                    }

                    float newLevel = level + STEP - bonus;
                    boolean statusUpdated = false;
                    if (newLevel >= AFFLICTED) {

                        GLog.n( TXT_AFFLICTED );
                        statusUpdated = true;

                        hero.interrupt();

                    } else if (newLevel >= STRICKEN && level < STRICKEN) {

                        GLog.w( TXT_STRICKEN );
                        statusUpdated = true;

                    }
                    level = newLevel;

                    if (statusUpdated) {
                        BuffIndicator.refreshHero();
                    }

                }

                float step = ((Hero)target).heroClass == HeroClass.ROGUE ? STEP * 1.2f : STEP;
                spend( target.buff( Shadows.class ) == null ? step : step * 1.5f );

            } else {

                diactivate();

            }

            return true;
        }



        public boolean isAfflicted() {
            return level >= AFFLICTED;
        }

        @Override
        public int icon() {
            if (level < STRICKEN) {
                return BuffIndicator.NONE;
            } else if (level < AFFLICTED) {
                return BuffIndicator.NONE;
            } else {
                return BuffIndicator.NONE;
            }
        }

        @Override
        public String toString() {
            if (level < AFFLICTED) {
                return "Stricken";
            } else {
                return "Afflicted";
            }
        }

        @Override
        public void onDeath() {

            GLog.n( TXT_DEATH );
        }


}

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
package com.udawos.pioneer.actors.buffs;

import com.udawos.pioneer.Dungeon;
import com.udawos.pioneer.ResultDescriptions;
import com.udawos.pioneer.actors.hero.Hero;
import com.udawos.pioneer.actors.hero.HeroClass;
import com.udawos.pioneer.items.rings.RingOfSatiety;
import com.udawos.pioneer.ui.BuffIndicator;
import com.udawos.pioneer.utils.GLog;
import com.udawos.pioneer.utils.Utils;
import com.udawos.utils.Bundle;
import com.udawos.utils.Random;

public class Thirst extends Buff implements Hero.Doom {

    private static final float STEP	= 1f;

    public static final float THIRSTY	= 500f;
    public static final float DEHYDRATED	= 6000f;

    private static final String TXT_THIRSTY		= "You are thirsty.";
    private static final String TXT_DEHYDRATED	= "You are dehydrated!";
    private static final String TXT_DEATH		= "You starved to death...";

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
        if (target.isAlive()) {

            Hero hero = (Hero)target;

            if (isStarving()) {
                if (Random.Float() < 0.3f && (target.HP > 1 || !target.paralysed)) {

                    GLog.n( TXT_DEHYDRATED );
                    hero.damage( 1, this );

                    hero.interrupt();
                }
            } else {

                int bonus = 0;
                for (Buff buff : target.buffs( RingOfSatiety.Satiety.class )) {
                    bonus += ((RingOfSatiety.Satiety)buff).level;
                }

                float newLevel = level + STEP - bonus;
                boolean statusUpdated = false;
                if (newLevel >= DEHYDRATED) {

                    GLog.n( TXT_DEHYDRATED );
                    statusUpdated = true;

                    hero.interrupt();

                } else if (newLevel >= THIRSTY && level < THIRSTY) {

                    GLog.w( TXT_THIRSTY );
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

    public void satisfy( float waterEnergy ) {
        level -= waterEnergy;
        if (level < 0) {
            level = 0;
        } else if (level > DEHYDRATED) {
            level = DEHYDRATED;
        }

        BuffIndicator.refreshHero();
    }

    public boolean isStarving() {
        return level >= DEHYDRATED;
    }

    @Override
    public int icon() {
        if (level < THIRSTY) {
            return BuffIndicator.NONE;
        } else if (level < DEHYDRATED) {
            return BuffIndicator.THIRST;
        } else {
            return BuffIndicator.DEHYDRATION;
        }
    }

    @Override
    public String toString() {
        if (level < DEHYDRATED) {
            return "Thirsty";
        } else {
            return "Dehydrated";
        }
    }

    @Override
    public void onDeath() {

        Dungeon.fail( Utils.format( ResultDescriptions.HUNGER, Dungeon.depth ) );
        GLog.n( TXT_DEATH );
    }
}

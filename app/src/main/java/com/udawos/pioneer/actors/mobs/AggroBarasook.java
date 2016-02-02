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
package com.udawos.pioneer.actors.mobs;

import com.udawos.pioneer.Dungeon;
import com.udawos.pioneer.actors.Char;
import com.udawos.pioneer.actors.buffs.Amok;
import com.udawos.pioneer.actors.buffs.Buff;
import com.udawos.pioneer.actors.buffs.Stunned;
import com.udawos.pioneer.actors.buffs.Terror;
import com.udawos.pioneer.items.CalibrationTools;
import com.udawos.pioneer.levels.traps.BounceAnomaly;
import com.udawos.pioneer.sprites.BlacksmithSprite;
import com.udawos.pioneer.utils.GLog;
import com.udawos.utils.Random;

import java.util.HashSet;

public class AggroBarasook extends Mob {

    public static final String TXT_DAZE	= "The man-badger has knocked you back and stunned you";

    {
        name = "Barasook";
        spriteClass = BlacksmithSprite.class;

        HP = HT = 700;
        defenseSkill = 30;

        EXP = 11;
        maxLvl = 21;

        loot = new CalibrationTools();
        lootChance = 1.0f;
    }

    @Override
    public int damageRoll() {
        return Random.NormalIntRange( 12, 16 );
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

            BounceAnomaly.climbFall(Dungeon.hero.pos);
            Buff.affect(enemy, Stunned.class, Stunned.DURATION);
            GLog.w(TXT_DAZE);
        }

        return damage;
    }

    @Override
    public String description() {
        return
                "It's a man-badger and it looks mad.";
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


package com.udawos.pioneer.levels.traps;

import com.udawos.pioneer.Dungeon;
import com.udawos.pioneer.actors.Char;
import com.udawos.pioneer.actors.buffs.Bleeding;
import com.udawos.pioneer.actors.buffs.Buff;
import com.udawos.pioneer.effects.Wound;
import com.udawos.utils.Random;

public class TripwireTrap {


    public static void trigger(int pos, Char ch) {


        if (ch != null) {
            int damage = Math.max( 0,  (Dungeon.depth + 3) - Random.IntRange(0, ch.dr() / 2) );
            Buff.affect(ch, Bleeding.class).set( damage );
            Wound.hit(ch);
        } else {
            Wound.hit( pos );
        }
    }
}
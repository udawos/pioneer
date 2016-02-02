package com.udawos.pioneer.items.potions;

import com.udawos.pioneer.actors.buffs.Buff;
import com.udawos.pioneer.actors.buffs.Speed;
import com.udawos.pioneer.actors.hero.Hero;
import com.udawos.pioneer.utils.GLog;

/**
 * Created by Jake on 27/12/2015.
 */
public class ActionOfDashing {



    public void apply( Hero hero ) {
        Buff.affect( hero, Speed.class, Speed.DURATION );
        GLog.i("You will be much faster for one move.");
    }
}


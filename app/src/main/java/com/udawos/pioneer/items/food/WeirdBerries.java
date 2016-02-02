
package com.udawos.pioneer.items.food;

import com.udawos.pioneer.actors.buffs.Buff;
import com.udawos.pioneer.actors.buffs.Burning;
import com.udawos.pioneer.actors.buffs.Hunger;
import com.udawos.pioneer.actors.buffs.Paralysis;
import com.udawos.pioneer.actors.buffs.Poison;
import com.udawos.pioneer.actors.buffs.Roots;
import com.udawos.pioneer.actors.buffs.Slow;
import com.udawos.pioneer.actors.hero.Hero;
import com.udawos.pioneer.sprites.ItemSpriteSheet;
import com.udawos.pioneer.utils.GLog;
import com.udawos.utils.Random;

public class WeirdBerries extends Food {

    {
        name = "weird berries";
        image = ItemSpriteSheet.SEED_ROTBERRY;
        energy = Hunger.STARVING - Hunger.HUNGRY;
        message = "That berry tasted... like burning";
    }

    @Override
    public void execute( Hero hero, String action ) {

        super.execute( hero, action );

        if (action.equals( AC_EAT )) {

            switch (Random.Int( 5 )) {
                case 0:
                    GLog.w( "Oh it's hot!" );
                    Buff.affect( hero, Burning.class ).reignite( hero );
                    break;
                case 1:
                    GLog.w( "You can't feel your legs!" );
                    Buff.prolong( hero, Roots.class, Paralysis.duration( hero ) );
                    break;
                case 2:
                    GLog.w( "You are not feeling well." );
                    Buff.affect( hero, Poison.class ).set( Poison.durationFactor( hero ) * hero.HT / 5 );
                    break;
                case 3:
                    GLog.w( "You are stuffed." );
                    Buff.prolong( hero, Slow.class, Slow.duration( hero ) );
                    break;
            }
        }
    }

    @Override
    public String info() {
        return
                "It's a berry of some sort. It looks edible, " +
                        "but you're probably taking a chance by eating this.";
    }

    public int price() {
        return 5 * quantity;
    };
}

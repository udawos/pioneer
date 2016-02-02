package com.udawos.pioneer.items.food;

import com.udawos.pioneer.actors.buffs.Barkskin;
import com.udawos.pioneer.actors.buffs.Bleeding;
import com.udawos.pioneer.actors.buffs.Buff;
import com.udawos.pioneer.actors.buffs.Cripple;
import com.udawos.pioneer.actors.buffs.Hunger;
import com.udawos.pioneer.actors.buffs.Invisibility;
import com.udawos.pioneer.actors.buffs.Poison;
import com.udawos.pioneer.actors.buffs.Weakness;
import com.udawos.pioneer.actors.hero.Hero;
import com.udawos.pioneer.effects.Speck;
import com.udawos.pioneer.sprites.ItemSpriteSheet;
import com.udawos.pioneer.utils.GLog;
import com.udawos.utils.Random;

/**
 * Created by Jake on 26/11/2015.
 */
public class ShinyBerries extends Food {
    {
        name = "shiny berries";
        image = ItemSpriteSheet.SEED_ICECAP;
        energy = Hunger.STARVING - Hunger.HUNGRY;
    }

    @Override
    public void execute( Hero hero, String action ) {

        super.execute( hero, action );

        if (action.equals( AC_EAT )) {

            switch (Random.Int(5)) {
                case 0:
                    GLog.i("You see your hands turn invisible!");
                    Buff.affect(hero, Invisibility.class, Invisibility.DURATION);
                    break;
                case 1:
                    GLog.i( "You feel your skin hardens!" );
                    Buff.affect( hero, Barkskin.class ).level( hero.HT / 4 );
                    break;
                case 2:
                    GLog.i( "Refreshing!" );
                    Buff.detach( hero, Poison.class );
                    Buff.detach( hero, Cripple.class );
                    Buff.detach( hero, Weakness.class );
                    Buff.detach( hero, Bleeding.class );
                    break;
                case 3:
                    GLog.i( "You feel better!" );
                    if (hero.HP < hero.HT) {
                        hero.HP = Math.min( hero.HP + hero.HT / 4, hero.HT );
                        hero.sprite.emitter().burst( Speck.factory(Speck.HEALING), 1 );
                    }
                    break;
            }
        }
    }

    @Override
    public String info() {
        return
                "It's a shiny berry. It looks edible, " +
                        "but you're probably taking a chance by eating this.";
    }

    public int price() {
        return 10 * quantity;
    };

}


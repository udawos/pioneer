package com.udawos.pioneer.items.weapon;

import com.udawos.noosa.audio.Sample;
import com.udawos.pioneer.Assets;
import com.udawos.pioneer.Dungeon;
import com.udawos.pioneer.actors.hero.Hero;
import com.udawos.pioneer.effects.particles.EnergyParticle;
import com.udawos.pioneer.items.Item;
import com.udawos.pioneer.items.ammunition.Bullet;
import com.udawos.pioneer.items.ammunition.Gunpowder;
import com.udawos.pioneer.items.ammunition.PercussionCap;
import com.udawos.pioneer.utils.GLog;


public class Reloader extends Item{

    public static final float TIME_TO_RELOAD = 2f;
    private static final String TXT_CAP= "Cap loaded";
    private static final String TXT_BULLET = "Round loaded";
    private static final String TXT_POWDER = "Powder poured";


    public static String verify(Item item1, Item item2, Item item3) {


        if (!item1.isIdentified() || !item2.isIdentified()) {
            return "I need to know what I'm working with, identify them first!";
        }

        if (item1.cursed || item2.cursed) {
            return "I don't work with cursed items!";
        }

        if (item1.level < 0 || item2.level < 0) {
            return "It's a junk, the quality is too poor!";
        }


        return null;
    }


    public static void charge( Hero hero ) {
        hero.sprite.centerEmitter().burst(EnergyParticle.FACTORY, 15);

        Item item = hero.belongings.getCap(PercussionCap.class);
        Item item2 = hero.belongings.getPowder(Gunpowder.class);
        Item item3 = hero.belongings.getBullet(Bullet.class);
        if (item3 instanceof Bullet) {

            item3.detach(hero.belongings.backpack);
        }
        if (item instanceof PercussionCap) {

            item.detach(hero.belongings.backpack);
        }
         if (item2 instanceof Gunpowder) {

             item2.detach(hero.belongings.backpack);
            }

            GLog.w( TXT_POWDER, item2.toString() );
            GLog.w( TXT_BULLET, item3.toString() );
            GLog.w( TXT_CAP, item.toString() );

    }

   public static void reload () {

            Dungeon.hero.belongings.charge(true);

            Sample.INSTANCE.play(Assets.SND_RELOAD);
            Dungeon.hero.spendAndNext(TIME_TO_RELOAD);


    }

}


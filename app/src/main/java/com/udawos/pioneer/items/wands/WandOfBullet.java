package com.udawos.pioneer.items.wands;

import com.udawos.noosa.Camera;
import com.udawos.noosa.audio.Sample;
import com.udawos.pioneer.Assets;
import com.udawos.pioneer.Badges;
import com.udawos.pioneer.Dungeon;
import com.udawos.pioneer.ResultDescriptions;
import com.udawos.pioneer.actors.Actor;
import com.udawos.pioneer.actors.Char;
import com.udawos.pioneer.actors.buffs.Buff;
import com.udawos.pioneer.actors.buffs.Slow;
import com.udawos.pioneer.actors.hero.Belongings;
import com.udawos.pioneer.actors.hero.Hero;
import com.udawos.pioneer.effects.MagicMissile;
import com.udawos.pioneer.effects.particles.BulletParticle;
import com.udawos.pioneer.items.rings.BoneCharm;
import com.udawos.pioneer.sprites.HeroSprite;
import com.udawos.pioneer.sprites.ItemSpriteSheet;
import com.udawos.pioneer.ui.QuickSlot;
import com.udawos.pioneer.utils.GLog;
import com.udawos.pioneer.utils.Utils;
import com.udawos.utils.Callback;
import com.udawos.utils.Random;

public class WandOfBullet extends Wand {

    {
        name = "Harquebus";
        image = ItemSpriteSheet.WAND_MAGIC_MISSILE;;
        maxCharges = 2;
        //reloadable == true;
    }


    @Override
    public void onZap(int cell) {

        Char ch = Actor.findChar( cell );
        if (ch != null) {

            ch.damage(6, this);

            ch.sprite.emitter().burst(BulletParticle.FACTORY, 5);

            if (ch == curUser && !ch.isAlive()) {
                Dungeon.fail( Utils.format( ResultDescriptions.WAND, name, Dungeon.depth ) );
                GLog.n( "You killed yourself with your own harquebus..." );
            }
        }
    }

    @Override
     public void onZapHigh(int cell) {

        Char ch = Actor.findChar( cell );
        if (ch != null) {

            if (Dungeon.hero.belongings.getItem(BoneCharm.class) != null){
                ch.damage(Random.Int(40,50) * (Dungeon.hero.lvl), this);
                Camera.main.shake(2, 0.3f);
                Badges.validateHeadshots();
                GLog.n("UNGA WUNGA!");
            }

            else if (Dungeon.hero.belongings.getItem(BoneCharm.class) == null) {
                ch.damage(0, this);
                switch (Random.Int(1, Hero.hitRoll)) {
                    case 1:
                        ch.damage(Random.Int(30, 50) * (Dungeon.hero.lvl), this);
                        Camera.main.shake(2, 0.3f);
                        Badges.validateHeadshots();
                        break;
                    case 2:
                        ch.damage(Random.Int(1, 15) * (Dungeon.hero.lvl), this);
                        Badges.validateHeadshots();
                        break;
                    case 3:
                        ch.damage(Random.Int(1, 15) * (Dungeon.hero.lvl), this);
                        ;
                        GLog.n("Grazed him...");
                        Badges.validateHeadshots();
                        break;
                    case 4:
                        ch.damage(5, this);
                        GLog.n("Grazed him...");
                        Badges.validateHeadshots();
                        break;
                    case 5:
                        ch.damage(5, this);
                        GLog.n("Grazed him...");
                        Badges.validateHeadshots();
                        break;
                    case 6:
                        ch.damage(5, this);
                        GLog.n("Grazed him...");
                        Badges.validateHeadshots();
                        break;
                    case 7:
                        ch.damage(5, this);
                        GLog.n("Grazed him...");
                        Badges.validateHeadshots();
                        break;
                    case 8:
                        ch.damage(5, this);
                        GLog.n("Grazed him...");
                        Badges.validateHeadshots();
                        break;
                    case 9:
                        ch.damage(5, this);
                        GLog.n("Grazed him...");
                        Badges.validateHeadshots();
                        break;
                    case 10:
                        ch.damage(0, this);
                        GLog.n("Grazed him...");
                        break;
                }
            }

            ch.sprite.emitter().burst( BulletParticle.FACTORY, 5 );


            if (ch == curUser && !ch.isAlive()) {
                Dungeon.fail( Utils.format( ResultDescriptions.WAND, name, Dungeon.depth ) );
                GLog.n( "You killed yourself with your own harquebus..." );
            }
        }
    }

    @Override
    public void onZapMid(int cell) {

        Char ch = Actor.findChar( cell );
        if (ch != null) {

            ch.damage(Random.Int(1,20) + (Dungeon.hero.lvl*2), this);
            Buff.affect(ch, Slow.class, Slow.duration(ch) / 3 + level());

            ch.sprite.emitter().burst(BulletParticle.FACTORY, 1);


            if (ch == curUser && !ch.isAlive()) {
                Dungeon.fail( Utils.format( ResultDescriptions.WAND, name, Dungeon.depth ) );
                GLog.n( "You killed yourself with your own harquebus..." );
            }
        }
    }

    @Override
    public void onZapLow(int cell) {

        Char ch = Actor.findChar( cell );
        if (ch != null) {

            ch.damage(0, this);
            switch (Random.Int(1, Dungeon.hero.hitRoll)) {
                case 0:
                    ch.damage(Random.Int(1,6) + (Dungeon.hero.lvl*2), this);
                    Buff.affect(ch, Slow.class, Slow.duration(ch) / 3 + level());
                    break;
                case 3:
                    ch.damage(2 + (Dungeon.hero.lvl*2), this);
                    GLog.n("Grazed him...");
                    break;
                case 4:
                    ch.damage(0, this);
                    GLog.n("Miss...");
                    break;
                case 10:
                    ch.damage(0, this);
                    GLog.n("Miss...");
                    break;
            }

            ch.sprite.emitter().burst(BulletParticle.FACTORY, 5);

            if (ch == curUser && !ch.isAlive()) {
                Dungeon.fail(Utils.format(ResultDescriptions.WAND, name, Dungeon.depth));
                GLog.n("You killed yourself with your own harquebus...");
            }
        }
    }


    public void fx(int cell, Callback callback) {
        MagicMissile.greyLight(curUser.sprite.parent, curUser.pos, cell, callback);
        Sample.INSTANCE.play( Assets.SND_ZAP );
    }

    @Override
    public boolean isIdentified() {
        return true;
    }

    @Override
    public String desc() {
        return
                "A portable cannon that launches a projectile driven by the action of an explosive" +
                        " force. The harquebus is designed to be mounted against the shoulder.";
    }


        @Override
        public boolean doEquip( Hero hero ) {

            detachAll(hero.belongings.backpack);

            if (hero.belongings.weapon == null || hero.belongings.weapon.doUnequip( hero, true )) {

                hero.belongings.weapon = this;
                activate( hero );

                QuickSlot.refresh();


                hero.spendAndNext( TIME_TO_EQUIP );
                QuickSlot.primaryValue = WandOfBullet.class;
                Belongings.tier = 1;
                ((HeroSprite)hero.sprite).updateArmor();

                hero.spendAndNext( time2equip( hero ) );
                return true;

            } else {

                collect( hero.belongings.backpack );
                return false;
            }


    }

    @Override
     public boolean doUnequip( Hero hero, boolean collect, boolean single ) {
        QuickSlot.primaryValue = null;
        super.doUnequip(hero, collect, single);
        Belongings.tier = 0;
        ((HeroSprite)hero.sprite).updateArmor();

        return true;

    }
}

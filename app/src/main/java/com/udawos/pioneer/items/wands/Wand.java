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
package com.udawos.pioneer.items.wands;

import com.udawos.noosa.audio.Sample;
import com.udawos.pioneer.Assets;
import com.udawos.pioneer.Dungeon;
import com.udawos.pioneer.actors.Actor;
import com.udawos.pioneer.actors.Char;
import com.udawos.pioneer.actors.buffs.Buff;
import com.udawos.pioneer.actors.buffs.Invisibility;
import com.udawos.pioneer.actors.hero.Hero;
import com.udawos.pioneer.actors.hero.HeroClass;
import com.udawos.pioneer.effects.MagicMissile;
import com.udawos.pioneer.items.Item;
import com.udawos.pioneer.items.ItemStatusHandler;
import com.udawos.pioneer.items.KindOfWeapon;
import com.udawos.pioneer.items.bags.Bag;
import com.udawos.pioneer.items.rings.RingOfPower.Power;
import com.udawos.pioneer.mechanics.Ballistica;
import com.udawos.pioneer.scenes.CellSelector;
import com.udawos.pioneer.scenes.GameScene;
import com.udawos.pioneer.sprites.ItemSpriteSheet;
import com.udawos.pioneer.ui.QuickSlot;
import com.udawos.pioneer.utils.GLog;
import com.udawos.pioneer.windows.WndAttackMob;
import com.udawos.utils.Bundle;
import com.udawos.utils.Callback;
import com.udawos.utils.Random;

import java.util.ArrayList;

public abstract class Wand extends KindOfWeapon {

    private static final int USAGES_TO_KNOW	= 40;

    public static final String AC_FIRE	= "Fire";

    private static final String TXT_WOOD	= "This thin %s wand is warm to the touch. Who knows what it will do when used?";
    private static final String TXT_DAMAGE	= "When this wand is used as a melee weapon, its average damage is %d points per hit.";
    private static final String TXT_WEAPON	= "You can use this wand as a melee weapon.";

    private static final String TXT_FIZZLES		= "You must reload your weapon.";
    private static final String TXT_SELF_TARGET	= "You can't target yourself";

    private static final String TXT_IDENTIFY	= "You are now familiar enough with your %s.";

    private static final float TIME_TO_ZAP	= 1f;

    public int maxCharges = initialCharges();
    public int curCharges = maxCharges;

    protected Charger charger;

    private boolean curChargeKnown = false;

    private int usagesToKnow = USAGES_TO_KNOW;

    protected boolean hitChars = true;

    private static final Class<?>[] wands = {
            WandOfBullet.class
    };
    private static final String[] woods =
            {"holly", "yew", "ebony", "cherry", "teak", "rowan", "willow", "mahogany", "bamboo", "purpleheart", "oak", "birch"};
    private static final Integer[] images = {
            ItemSpriteSheet.WAND_HOLLY,
            ItemSpriteSheet.WAND_YEW,
            ItemSpriteSheet.WAND_EBONY,
            ItemSpriteSheet.WAND_CHERRY,
            ItemSpriteSheet.WAND_TEAK,
            ItemSpriteSheet.WAND_ROWAN,
            ItemSpriteSheet.WAND_WILLOW,
            ItemSpriteSheet.WAND_MAHOGANY,
            };

    private static ItemStatusHandler<Wand> handler;

    private String wood;

    {
        defaultAction = AC_FIRE;
    }

    @SuppressWarnings("unchecked")
    public static void initWoods() {
        handler = new ItemStatusHandler<Wand>( (Class<? extends Wand>[])wands, woods, images );
    }

    public static void save( Bundle bundle ) {
        handler.save( bundle );
    }

    @SuppressWarnings("unchecked")
    public static void restore( Bundle bundle ) {
        handler = new ItemStatusHandler<Wand>( (Class<? extends Wand>[])wands, woods, images, bundle );
    }


    public Wand() {
        super();

        calculateDamage();

        try {
            image = handler.image( this );
            wood = handler.label( this );
        } catch (Exception e) {
            // Wand of Magic Missile
        }
    }

    @Override
    public ArrayList<String> actions( Hero hero ) {
        ArrayList<String> actions = super.actions( hero );
        if (curCharges > 0 || !curChargeKnown) {
            actions.add( AC_FIRE );
        }
        if (hero.heroClass != HeroClass.PATHFINDER) {
            actions.remove( AC_EQUIP );
            actions.remove(AC_UNEQUIP);
        }
        return actions;
    }

    @Override
    public boolean doUnequip( Hero hero, boolean collect, boolean single ) {
        onDetach();
        return super.doUnequip( hero, collect, single );
    }

    @Override
    public void activate( Hero hero ) {
        charge(hero);
    }

    @Override
    public void execute( Hero hero, String action ) {
        if (action.equals( AC_FIRE )) {

            curUser = hero;
            curItem = this;
            GameScene.show(new WndAttackMob());

        } else {

            super.execute(hero, action);

        }
    }
    protected abstract void onZapHigh( int cell );

    protected abstract void onZapMid( int cell );

    protected abstract void onZapLow( int cell );

    protected abstract void onZap( int cell );

    @Override
    public boolean collect( Bag container ) {
        if (super.collect( container )) {
            if (container.owner != null) {
                charge( container.owner );
            }
            return true;
        } else {
            return false;
        }
    };

    public void charge( Char owner ) {
        if (charger == null) {
            (charger = new Charger()).attachTo( owner );
        }
    }

    @Override
    public void onDetach( ) {
        stopCharging();
    }

    public void stopCharging() {
        if (charger != null) {
            charger.detach();
            charger = null;
        }
    }

    public int level() {
        if (charger != null) {
            Power power = charger.target.buff( Power.class );
            return power == null ? level : Math.max( level + power.level, 0 );
        } else {
            return level;
        }
    }

    protected boolean isKnown() {
        return handler.isKnown( this );
    }

    public void setKnown() {
        if (!isKnown()) {
            handler.know( this );
        }

    }

    @Override
    public Item identify() {

        setKnown();
        curChargeKnown = true;
        super.identify();

        updateQuickslot();

        return this;
    }

    @Override
    public String toString() {

        StringBuilder sb = new StringBuilder( super.toString() );

        String status = status();
        if (status != null) {
            sb.append( " (" + status +  ")" );
        }

        return sb.toString();
    }

    @Override
    public String name() {
        return isKnown() ? name : wood + " wand";
    }

    @Override
    public String info() {
        StringBuilder info = new StringBuilder( isKnown() ? desc() : String.format( TXT_WOOD, wood ) );
        if (Dungeon.hero.heroClass == HeroClass.MAGE) {
            info.append( "\n\n" );
            if (levelKnown) {
                info.append( String.format( TXT_DAMAGE, MIN + (MAX - MIN) / 2 ) );
            } else {
                info.append(  String.format( TXT_WEAPON ) );
            }
        }
        return info.toString();
    }

    @Override
    public boolean isIdentified() {
        return super.isIdentified() && isKnown() && curChargeKnown;
    }

    @Override
    public String status() {
        if (levelKnown) {
            return (curChargeKnown ? curCharges : "?") + "/" + maxCharges;
        } else {
            return null;
        }
    }

    @Override
    public Item upgrade() {

        super.upgrade();

        updateLevel();
        curCharges = Math.min( curCharges + 1, maxCharges );
        updateQuickslot();

        return this;
    }

    @Override
    public Item degrade() {
        super.degrade();

        updateLevel();
        updateQuickslot();

        return this;
    }

    @Override
    public int maxDurability( int lvl ) {
        return 5 * (lvl < 16 ? 16 - lvl : 1);
    }

    protected void updateLevel() {
        maxCharges = Math.min( initialCharges() + level, 9 );
        curCharges = Math.min( curCharges, maxCharges );

        calculateDamage();
    }

    protected int initialCharges() {
        return 2;
    }

    private void calculateDamage() {
        int tier = 1 + level / 3;
        MIN = tier;
        MAX = (tier * tier - tier + 10) / 2 + level;
    }

    protected void fx( int cell, Callback callback ) {
        MagicMissile.blueLight( curUser.sprite.parent, curUser.pos, cell, callback );
        Sample.INSTANCE.play( Assets.SND_ZAP );
    }

    protected void wandUsed() {

        curCharges--;
        if (!isIdentified() && --usagesToKnow <= 0) {
            identify();
            GLog.w( TXT_IDENTIFY, name() );
        } else {
            updateQuickslot();
        }

        use();

        curUser.spendAndNext(TIME_TO_ZAP);
    }

    @Override
    public Item random() {
        if (Random.Float() < 0.5f) {
            upgrade();
            if (Random.Float() < 0.15f) {
                upgrade();
            }
        }

        return this;
    }

    public static boolean allKnown() {
        return handler.known().size() == wands.length;
    }

    @Override
    public int price() {
        int price = 50;
        if (cursed && cursedKnown) {
            price /= 2;
        }
        if (levelKnown) {
            if (level > 0) {
                price *= (level + 1);
            } else if (level < 0) {
                price /= (1 - level);
            }
        }
        if (price < 1) {
            price = 1;
        }
        return price;
    }
    //@Override
    //public int weight() {
    //    int weight = 1;

    //    return weight;
   // }


    private static final String UNFAMILIRIARITY		= "unfamiliarity";
    private static final String MAX_CHARGES			= "maxCharges";
    private static final String CUR_CHARGES			= "curCharges";
    private static final String CUR_CHARGE_KNOWN	= "curChargeKnown";

    @Override
    public void storeInBundle( Bundle bundle ) {
        super.storeInBundle( bundle );
        bundle.put( UNFAMILIRIARITY, usagesToKnow );
        bundle.put( MAX_CHARGES, maxCharges );
        bundle.put( CUR_CHARGES, curCharges );
        bundle.put( CUR_CHARGE_KNOWN, curChargeKnown );
    }

    @Override
    public void restoreFromBundle( Bundle bundle ) {
        super.restoreFromBundle( bundle );
        if ((usagesToKnow = bundle.getInt( UNFAMILIRIARITY )) == 0) {
            usagesToKnow = USAGES_TO_KNOW;
        }
        maxCharges = bundle.getInt( MAX_CHARGES );
        curCharges = bundle.getInt( CUR_CHARGES );
        curChargeKnown = bundle.getBoolean( CUR_CHARGE_KNOWN );
    }


    public static CellSelector.Listener zapperHigh = new  CellSelector.Listener() {

        @Override
        public void onSelect( Integer target ) {

            if (target != null) {

                if (target == curUser.pos) {
                    GLog.i( TXT_SELF_TARGET );
                    return;
                }

                final Wand curWand = (Wand)Wand.curItem;

                curWand.setKnown();

                final int cell = Ballistica.cast( curUser.pos, target, true, curWand.hitChars );
                curUser.sprite.zap( cell );

                QuickSlot.target( curItem, Actor.findChar( cell ) );

                if (curWand.curCharges > 0) {

                    curUser.busy();

                    curWand.fx( cell, new Callback() {
                        @Override
                        public void call() {
                            curWand.onZapHigh( cell );
                            curWand.wandUsed();
                        }
                    } );

                    Invisibility.dispel();

                } else {

                    curUser.spendAndNext( TIME_TO_ZAP );
                    GLog.w( TXT_FIZZLES );
                    curWand.levelKnown = true;

                    curWand.updateQuickslot();
                }

            }
        }

        @Override
        public String prompt() {
            return "Choose direction to fire";
        }
    };

    public static CellSelector.Listener zapperMid = new  CellSelector.Listener() {

        @Override
        public void onSelect( Integer target ) {

            if (target != null) {

                if (target == curUser.pos) {
                    GLog.i( TXT_SELF_TARGET );
                    return;
                }

                final Wand curWand = (Wand)Wand.curItem;

                curWand.setKnown();

                final int cell = Ballistica.cast( curUser.pos, target, true, curWand.hitChars );
                curUser.sprite.zap( cell );

                QuickSlot.target( curItem, Actor.findChar( cell ) );

                if (curWand.curCharges > 0) {

                    curUser.busy();

                    curWand.fx( cell, new Callback() {
                        @Override
                        public void call() {
                            curWand.onZapMid(cell);
                            curWand.wandUsed();
                        }
                    } );

                    Invisibility.dispel();

                } else {

                    curUser.spendAndNext( TIME_TO_ZAP );
                    GLog.w( TXT_FIZZLES );
                    curWand.levelKnown = true;

                    curWand.updateQuickslot();
                }

            }
        }

        @Override
        public String prompt() {
            return "Choose direction to fire";
        }
    };

    public static CellSelector.Listener zapperLow = new  CellSelector.Listener() {

        @Override
        public void onSelect( Integer target ) {

            if (target != null) {

                if (target == curUser.pos) {
                    GLog.i( TXT_SELF_TARGET );
                    return;
                }

                final Wand curWand = (Wand)Wand.curItem;

                curWand.setKnown();

                final int cell = Ballistica.cast( curUser.pos, target, true, curWand.hitChars );
                curUser.sprite.zap( cell );

                QuickSlot.target( curItem, Actor.findChar( cell ) );

                if (curWand.curCharges > 0) {

                    curUser.busy();

                    curWand.fx( cell, new Callback() {
                        @Override
                        public void call() {
                            curWand.onZapLow(cell);
                            curWand.wandUsed();
                        }
                    } );

                    Invisibility.dispel();

                } else {

                    curUser.spendAndNext( TIME_TO_ZAP );
                    GLog.w( TXT_FIZZLES );
                    curWand.levelKnown = true;

                    curWand.updateQuickslot();
                }

            }
        }

        @Override
        public String prompt() {
            return "Choose direction to fire";
        }
    };

    public static CellSelector.Listener zapper = new  CellSelector.Listener() {

        @Override
        public void onSelect( Integer target ) {

            if (target != null) {

                if (target == curUser.pos) {
                    GLog.i( TXT_SELF_TARGET );
                    return;
                }

                final Wand curWand = (Wand)Wand.curItem;

                curWand.setKnown();

                final int cell = Ballistica.cast( curUser.pos, target, true, curWand.hitChars );
                curUser.sprite.zap( cell );

                QuickSlot.target( curItem, Actor.findChar( cell ) );

                if (curWand.curCharges > 0) {

                    curUser.busy();

                    curWand.fx(cell, new Callback() {
                        @Override
                        public void call() {
                            curWand.onZap(cell);
                            curWand.wandUsed();
                        }
                    } );

                    Invisibility.dispel();

                } else {

                    curUser.spendAndNext( TIME_TO_ZAP );
                    GLog.w( TXT_FIZZLES );
                    curWand.levelKnown = true;

                    curWand.updateQuickslot();
                }

            }
        }

        @Override
        public String prompt() {
            return "Choose direction to fire";
        }
    };


    protected class Charger extends Buff {

        private static final float TIME_TO_CHARGE = 40f;

        @Override
        public boolean attachTo( Char target ) {
            super.attachTo( target );
            delay();

            return true;
        }

        @Override
        public boolean act() {

          /*  if (curCharges < maxCharges) {
                curCharges++;
                updateQuickslot();
            }*/

            delay();

            return true;
        }

        protected void delay() {
            float time2charge = ((Hero)target).heroClass == HeroClass.MAGE ?
                    TIME_TO_CHARGE / (float)Math.sqrt( 1 + level ) :
                    TIME_TO_CHARGE;
            spend( time2charge );
        }
    }
}

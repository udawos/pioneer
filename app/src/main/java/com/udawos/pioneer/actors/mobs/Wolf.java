package com.udawos.pioneer.actors.mobs;

import com.udawos.pioneer.Dungeon;
import com.udawos.pioneer.actors.Char;
import com.udawos.pioneer.actors.buffs.Bleeding;
import com.udawos.pioneer.actors.buffs.Buff;
import com.udawos.pioneer.actors.buffs.Terror;
import com.udawos.pioneer.actors.hero.Hero;
import com.udawos.pioneer.actors.mobs.npcs.Transmitter;
import com.udawos.pioneer.items.Gold;
import com.udawos.pioneer.items.Item;
import com.udawos.pioneer.items.WolfPelt;
import com.udawos.pioneer.sprites.CharSprite;
import com.udawos.pioneer.sprites.WolfSprite;
import com.udawos.pioneer.utils.Utils;
import com.udawos.utils.Bundle;
import com.udawos.utils.Random;


public class Wolf extends Mob {

    private static final String TXT_HOWL =
            "The wolf lets out a chilling howl.";

    private boolean seenBefore = false;

    public Item item;

    {
        name = "wolf";
        spriteClass = WolfSprite.class;
        state = WANDERING;

        HP = HT = 20;
        defenseSkill = 12;
        baseSpeed = 1.4f;

        EXP = 5;
        maxLvl = 10;

        loot = WolfPelt.class;
        lootChance = 1.0f;


        FLEEING = new Fleeing();
    }

    private static final String ITEM = "item";
    //for packCount, take a page from Yog's book. Only ever spawn
    //one wolf per level, but have that wolf's class spawn its pack
    //that should allow the pack counter to work and dictate pack behaviour
    private static int packCount = 0;


    
    
    @Override
    public void storeInBundle( Bundle bundle ) {
        super.storeInBundle(bundle);
        bundle.put( ITEM, item );
    }

    @Override
    public void restoreFromBundle( Bundle bundle ) {
        super.restoreFromBundle(bundle);
        item = (Item)bundle.get( ITEM );
    }

    @Override
    public int damageRoll() {
        return Random.NormalIntRange(1, 7);
    }


    @Override
    protected float attackDelay() {
        return 0.5f;
    }

    @Override
    public void die( Object cause ) {

        Transmitter.Quest.process( this );

        super.die( cause );

        if (item != null) {
            Dungeon.level.drop( item, pos ).sprite.drop();
        }
    }

    @Override
    public int attackSkill( Char target ) {
        return 12;
    }

    @Override
    public int dr() {
        return 1;
    }

    @Override
    public int attackProc( Char enemy, int damage ) {

        if (packCount > 1) {

            for (Mob mob : Dungeon.level.mobs) {
                if (mob instanceof Wolf) {
                    mob.beckon(pos);
                }
            }
        }
        if ( enemy instanceof Hero) {
            state = FLEEING;
        }
        Buff.affect(Dungeon.hero, Bleeding.class).set(damage);
        return damage;
    }

    @Override
    public int defenseProc(Char enemy, int damage) {
        if (state == FLEEING) {
            Dungeon.level.drop( new Gold(), pos ).sprite.drop();
        }

        return damage;
    }


    @Override
    public String description() {
        String desc =
                "This wolf seems to be starving. " +
                        "It is unusual for wolves to attack humans so readily. ";

        return desc;
    }

    @Override
    public void notice() {

        if ( Dungeon.visible[pos]) {
            if (!seenBefore) {
                yell( Utils.format(TXT_HOWL) );
            }
            seenBefore = true;
        } else if (seenBefore){
            //seenBefore = false;
        }

    }

    private class Fleeing extends Mob.Fleeing {
        @Override
        protected void nowhereToRun() {
            if (buff( Terror.class ) == null) {
                sprite.showStatus( CharSprite.NEGATIVE, TXT_RAGE );
                state = HUNTING;
            } else {
                super.nowhereToRun();


            }
        }
    }
}

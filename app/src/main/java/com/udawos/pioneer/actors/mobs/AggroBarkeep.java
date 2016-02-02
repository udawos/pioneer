
package com.udawos.pioneer.actors.mobs;

import com.udawos.pioneer.Dungeon;
import com.udawos.pioneer.actors.Char;
import com.udawos.pioneer.actors.buffs.Amok;
import com.udawos.pioneer.actors.buffs.Terror;
import com.udawos.pioneer.actors.hero.Hero;
import com.udawos.pioneer.items.KindOfWeapon;
import com.udawos.pioneer.items.food.Food;
import com.udawos.pioneer.items.weapon.melee.Knuckles;
import com.udawos.pioneer.sprites.BarkeepSprite;
import com.udawos.pioneer.utils.GLog;
import com.udawos.utils.Random;

import java.util.HashSet;

public class AggroBarkeep extends Mob {

    public static final String TXT_DISARM	= "%s has knocked the %s from your hands!";

    {
        name = "Barkeep";
        spriteClass = BarkeepSprite.class;

        HP = HT = 70;
        defenseSkill = 30;

        EXP = 11;
        maxLvl = 21;

        loot = new Food();
        //change this to lucky coin
        lootChance = 0.083f;
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

        if (Random.Int( 6 ) == 0 && enemy == Dungeon.hero) {

            Hero hero = Dungeon.hero;
            KindOfWeapon weapon = hero.belongings.weapon;

            if (weapon != null && !(weapon instanceof Knuckles) && !weapon.cursed) {
                hero.belongings.weapon = null;
                Dungeon.level.drop( weapon, hero.pos ).sprite.drop();
                GLog.w( TXT_DISARM, name, weapon.name() );
            }
        }

        return damage;
    }

    @Override
    public String description() {
        return
                "I don't know what you did, but you sure made him angry.";
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

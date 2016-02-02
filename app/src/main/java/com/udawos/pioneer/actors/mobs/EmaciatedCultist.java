package com.udawos.pioneer.actors.mobs;

import com.udawos.pioneer.Dungeon;
import com.udawos.pioneer.ResultDescriptions;
import com.udawos.pioneer.actors.Char;
import com.udawos.pioneer.actors.buffs.Bleeding;
import com.udawos.pioneer.actors.buffs.Buff;
import com.udawos.pioneer.effects.CellEmitter;
import com.udawos.pioneer.effects.Speck;
import com.udawos.pioneer.items.Generator;
import com.udawos.pioneer.items.ammunition.Bullet;
import com.udawos.pioneer.items.ammunition.Gunpowder;
import com.udawos.pioneer.items.ammunition.PercussionCap;
import com.udawos.pioneer.items.wands.WandOfBullet;
import com.udawos.pioneer.items.weapon.enchantments.Death;
import com.udawos.pioneer.levels.Level;
import com.udawos.pioneer.mechanics.Ballistica;
import com.udawos.pioneer.sprites.CharSprite;
import com.udawos.pioneer.sprites.ScorpioSprite;
import com.udawos.pioneer.utils.GLog;
import com.udawos.pioneer.utils.Utils;
import com.udawos.utils.Callback;
import com.udawos.utils.Random;

import java.util.HashSet;

public class EmaciatedCultist extends Mob implements Callback {

    private static final float TIME_TO_ZAP	= 1f;

    private static final String TXT_SHADOWBOLT_KILLED = "%s's weapon killed you...";

    {
        name = "cultist";
        spriteClass = ScorpioSprite.class;

        HP = HT = 70;
        defenseSkill = 18;

        state = WANDERING;

        EXP = 11;
        maxLvl = 21;

        loot = Generator.Category.POTION;
        lootChance = 0.83f;
    }

    @Override
    protected void dropLoot() {
        Dungeon.level.drop(new Gunpowder(20), pos).sprite.drop();
        Dungeon.level.drop(new Bullet(20), pos).sprite.drop();
        Dungeon.level.drop(new PercussionCap(20), pos).sprite.drop();
        Dungeon.level.drop(new WandOfBullet(), pos).sprite.drop();
    }


    @Override
    public int damageRoll() {
        return Random.NormalIntRange(12, 20);
    }

    @Override
    public int attackSkill( Char target ) {
        return 25;
    }

    @Override
    public int dr() {
        return 8;
    }

    @Override
    protected boolean canAttack( Char enemy ) {
        return Ballistica.cast(pos, enemy.pos, false, true) == enemy.pos;
    }

    protected boolean doAttack( Char enemy ) {

        if (Level.adjacent(pos, enemy.pos)) {

            return super.doAttack( enemy );

        } else {

            boolean visible = Level.fieldOfView[pos] || Level.fieldOfView[enemy.pos];
            if (visible) {
                switch (Random.Int( 4 )) {
                    case 0:
                        ((ScorpioSprite) sprite).zap(enemy.pos);
                        break;
                    case 1:
                        ((ScorpioSprite) sprite).zap(enemy.pos + 1);
                        CellEmitter.center(enemy.pos + 1).burst(Speck.factory(Speck.DUST), 10);
                        break;
                    case 2:
                        ((ScorpioSprite) sprite).zap(enemy.pos - 1);
                        CellEmitter.center(enemy.pos - 1).burst(Speck.factory(Speck.DUST), 10);
                        break;
                    case 3:
                        ((ScorpioSprite) sprite).zap(enemy.pos + 50);
                        CellEmitter.center(enemy.pos + 1).burst(Speck.factory(Speck.DUST), 10);
                        break;
                    case 4:
                        ((ScorpioSprite) sprite).zap(enemy.pos - 50);
                        CellEmitter.center(enemy.pos - 1).burst(Speck.factory(Speck.DUST), 10);
                        break;
                }
            }

            return !visible;
        }
    }

    private void zap() {
        spend( TIME_TO_ZAP );

        if (hit(this, enemy, true)) {
            if (enemy == Dungeon.hero && Random.Int( 2 ) == 0) {
                Buff.affect(Dungeon.hero, Bleeding.class).set(1);
            }

            int dmg = Random.Int( 12, 18 );
            enemy.damage( dmg, this );

            if (!enemy.isAlive() && enemy == Dungeon.hero) {
                Dungeon.fail( Utils.format(ResultDescriptions.MOB,
                        Utils.indefinite(name), Dungeon.depth) );
                GLog.n(TXT_SHADOWBOLT_KILLED, name);
            }
        } else {
            enemy.sprite.showStatus( CharSprite.NEUTRAL,  enemy.defenseVerb() );
        }
    }


    public void onZapComplete() {
        zap();
        next();
    }

    @Override
    public void call() {
        next();
    }

    @Override
    public String description() {
        return
                "This cultist looks shockingly like the Pathfinder.";
    }

    private static final HashSet<Class<?>> RESISTANCES = new HashSet<Class<?>>();
    static {
        RESISTANCES.add( Death.class );
    }

    @Override
    public HashSet<Class<?>> resistances() {
        return RESISTANCES;
    }
}

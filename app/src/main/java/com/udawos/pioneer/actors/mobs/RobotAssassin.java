
package com.udawos.pioneer.actors.mobs;

import com.udawos.noosa.audio.Sample;
import com.udawos.pioneer.Assets;
import com.udawos.pioneer.Badges;
import com.udawos.pioneer.Dungeon;
import com.udawos.pioneer.actors.Actor;
import com.udawos.pioneer.actors.Char;
import com.udawos.pioneer.actors.blobs.ToxicGas;
import com.udawos.pioneer.effects.CellEmitter;
import com.udawos.pioneer.effects.Speck;
import com.udawos.pioneer.items.TomeOfMastery;
import com.udawos.pioneer.items.keys.SkeletonKey;
import com.udawos.pioneer.items.scrolls.ScrollOfPsionicBlast;
import com.udawos.pioneer.items.weapon.enchantments.Death;
import com.udawos.pioneer.items.weapon.enchantments.Poison;
import com.udawos.pioneer.levels.Level;
import com.udawos.pioneer.mechanics.Ballistica;
import com.udawos.pioneer.scenes.GameScene;
import com.udawos.pioneer.sprites.KingSprite;
import com.udawos.utils.Random;

import java.util.HashSet;



public class RobotAssassin extends Mob {

    private static final int JUMP_DELAY = 5;

    {
        name = "Ulysses";
        spriteClass = KingSprite.class;

        HP = HT = 120;
        EXP = 20;
        defenseSkill = 20;
    }

    private int timeToJump = JUMP_DELAY;

    @Override
    public int damageRoll() {
        return Random.NormalIntRange(8, 15);
    }

    @Override
    public int attackSkill( Char target ) {
        return 20;
    }

    @Override
    public int dr() {
        return 5;
    }

    @Override
    public void die( Object cause ) {


        Dungeon.level.drop( new TomeOfMastery(), pos ).sprite.drop();


        GameScene.bossSlain();
        Dungeon.level.drop( new SkeletonKey(), pos ).sprite.drop();
        super.die( cause );

        Badges.validateBossSlain();

        yell( "Free at last..." );
    }

    @Override
    protected boolean getCloser( int target ) {
        if (Level.fieldOfView[target]) {
            jump();
            return true;
        } else {
            return super.getCloser( target );
        }
    }

    @Override
    protected boolean canAttack( Char enemy ) {
        return Ballistica.cast(pos, enemy.pos, false, true) == enemy.pos;
    }

    @Override
    protected boolean doAttack( Char enemy ) {
        timeToJump--;
        if (timeToJump <= 0 && Level.adjacent( pos, enemy.pos )) {
            jump();
            return true;
        } else {
            return super.doAttack( enemy );
        }
    }

    private void jump() {
        timeToJump = JUMP_DELAY;


        int newPos;
        do {
            newPos = Random.Int( Level.LENGTH );
        } while (
                !Level.fieldOfView[newPos] ||
                        !Level.passable[newPos] ||
                        (enemy != null && Level.adjacent( newPos, enemy.pos )) ||
                        Actor.findChar(newPos) != null);

        sprite.move( pos, newPos );
        move( newPos );

        if (Dungeon.visible[newPos]) {
            CellEmitter.get(newPos).burst( Speck.factory( Speck.WOOL ), 6 );
            Sample.INSTANCE.play( Assets.SND_PUFF );
        }

        spend( 1 / speed() );
    }

    @Override
    public void notice() {
        super.notice();
        yell( "Gotcha, " + Dungeon.hero.heroClass.title() + "!" );
    }

    @Override
    public String description() {
        return
                "It's 750 units of weight and it wants to kill you." +
                        "Best stop gawking and start fighting.";
    }

    private static final HashSet<Class<?>> RESISTANCES = new HashSet<Class<?>>();
    static {
        RESISTANCES.add( ToxicGas.class );
        RESISTANCES.add( Poison.class );
        RESISTANCES.add( Death.class );
        RESISTANCES.add( ScrollOfPsionicBlast.class );
    }

    @Override
    public HashSet<Class<?>> resistances() {
        return RESISTANCES;
    }
}

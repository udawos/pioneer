
package com.udawos.pioneer.actors.mobs;

import com.udawos.pioneer.Dungeon;
import com.udawos.pioneer.ResultDescriptions;
import com.udawos.pioneer.actors.Actor;
import com.udawos.pioneer.actors.Char;
import com.udawos.pioneer.actors.blobs.ToxicGas;
import com.udawos.pioneer.actors.buffs.Amok;
import com.udawos.pioneer.actors.buffs.Burning;
import com.udawos.pioneer.actors.buffs.Charm;
import com.udawos.pioneer.actors.buffs.Poison;
import com.udawos.pioneer.actors.buffs.Sleep;
import com.udawos.pioneer.actors.buffs.Terror;
import com.udawos.pioneer.effects.Pushing;
import com.udawos.pioneer.items.keys.SkeletonKey;
import com.udawos.pioneer.items.scrolls.ScrollOfPsionicBlast;
import com.udawos.pioneer.items.weapon.enchantments.Death;
import com.udawos.pioneer.levels.Level;
import com.udawos.pioneer.scenes.GameScene;
import com.udawos.pioneer.sprites.BurningFistSprite;
import com.udawos.pioneer.sprites.CharSprite;
import com.udawos.pioneer.sprites.LarvaSprite;
import com.udawos.pioneer.sprites.PiranhaSprite;
import com.udawos.pioneer.sprites.RottingFistSprite;
import com.udawos.pioneer.utils.GLog;
import com.udawos.pioneer.utils.Utils;
import com.udawos.utils.Random;

import java.util.ArrayList;
import java.util.HashSet;

public class SeaMonster extends Mob {

    {
        name = "She";
        spriteClass = PiranhaSprite.class;

        HP = HT = 300;

        EXP = 50;

        state = WANDERING;
    }

    @Override
    protected boolean act() {
        if (!Level.water[pos]) {
            die( null );
            return true;
        } else {
            return super.act();
        }
    }

    private static final String TXT_DESC =
            "She, the lake monster. An unspeakable horror from the dawn of time";

    private static int tentaclesCount = 0;

    public SeaMonster() {
        super();
    }

    public void spawnTentacles() {
        RottingFist tentacle1 = new RottingFist();
        RottingFist tentacle2 = new RottingFist();

        do {
            tentacle1.pos = pos + Level.NEIGHBOURS8[Random.Int( 8 )];
            tentacle2.pos = pos + Level.NEIGHBOURS8[Random.Int( 8 )];
        } while (!Level.water[tentacle1.pos] || !Level.water[tentacle2.pos] || tentacle1.pos == tentacle2.pos);

        GameScene.add( tentacle1 );
        GameScene.add( tentacle2 );
    }

    @Override
    public void damage( int dmg, Object src ) {

        if (tentaclesCount > 0) {

            for (Mob mob : Dungeon.level.mobs) {
                if (mob instanceof BurningFist || mob instanceof RottingFist) {
                    mob.beckon( pos );
                }
            }

            dmg >>= tentaclesCount;
        }

        super.damage(dmg, src);
    }

    @Override
    public int defenseProc( Char enemy, int damage ) {

        ArrayList<Integer> spawnPoints = new ArrayList<Integer>();

        for (int i=0; i < Level.NEIGHBOURS8.length; i++) {
            int p = pos + Level.NEIGHBOURS8[i];
            if (Actor.findChar( p ) == null && (Level.passable[p] || Level.avoid[p])) {
                spawnPoints.add( p );
            }
        }

        if (spawnPoints.size() > 0) {
            Larva larva = new Larva();
            larva.pos = Random.element( spawnPoints );

            GameScene.add( larva );
            Actor.addDelayed( new Pushing( larva, pos, larva.pos ), -1 );
        }

        return super.defenseProc(enemy, damage);
    }

    @Override
    public void beckon( int cell ) {
    }

    @SuppressWarnings("unchecked")
    @Override
    public void die( Object cause ) {

        for (Mob mob : (Iterable<Mob>)Dungeon.level.mobs.clone()) {
            if (mob instanceof BurningFist || mob instanceof RottingFist) {
                mob.die( cause );
            }
        }

        GameScene.bossSlain();
        Dungeon.level.drop( new SkeletonKey(), pos ).sprite.drop();
        super.die( cause );

        yell( "..." );
    }

    @Override
    protected boolean getCloser( int target ) {

        if (rooted) {
            return false;
        }

        int step = Dungeon.findPath( this, pos, target,
                Level.water,
                Level.fieldOfView );
        if (step != -1) {
            move( step );
            return true;
        } else {
            return false;
        }
    }

    @Override
    protected boolean getFurther( int target ) {
        int step = Dungeon.flee( this, pos, target,
                Level.water,
                Level.fieldOfView );
        if (step != -1) {
            move( step );
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void notice() {
        super.notice();
        yell( "Hope is an illusion..." );
    }

    @Override
    public String description() {
        return TXT_DESC;

    }

    private static final HashSet<Class<?>> IMMUNITIES = new HashSet<Class<?>>();
    static {

        IMMUNITIES.add( Death.class );
        IMMUNITIES.add( Terror.class );
        IMMUNITIES.add( Amok.class );
        IMMUNITIES.add( Charm.class );
        IMMUNITIES.add( Sleep.class );
        IMMUNITIES.add( Burning.class );
        IMMUNITIES.add( ToxicGas.class );
        IMMUNITIES.add( ScrollOfPsionicBlast.class );
    }

    @Override
    public HashSet<Class<?>> immunities() {
        return IMMUNITIES;
    }

    public static class RottingFist extends Mob {

        private static final int REGENERATION	= 4;

        {
            name = "rotting tentacle";
            spriteClass = RottingFistSprite.class;

            HP = HT = 300;
            defenseSkill = 25;

            EXP = 0;

            state = WANDERING;
        }

        public RottingFist() {
            super();
            tentaclesCount++;
        }

        @Override
        public void die( Object cause ) {
            super.die( cause );
            tentaclesCount--;

        }

        @Override
        protected boolean getCloser( int target ) {

            if (rooted) {
                return false;
            }

            int step = Dungeon.findPath(this, pos, target,
                    Level.water,
                    Level.fieldOfView);
            if (step != -1) {
                move( step );
                return true;
            } else {
                return false;
            }
        }

        @Override
        protected boolean getFurther( int target ) {
            int step = Dungeon.flee( this, pos, target,
                    Level.water,
                    Level.fieldOfView );
            if (step != -1) {
                move( step );
                return true;
            } else {
                return false;
            }
        }

        @Override
        public int attackSkill( Char target ) {
            return 36;
        }

        @Override
        public int damageRoll() {
            return Random.NormalIntRange( 24, 36 );
        }

        @Override
        public int dr() {
            return 15;
        }


        @Override
        public boolean act() {

            if (!Level.water[pos]) {
                die( null );
                return true;
            } else {
                return super.act();
            }
        }

        @Override
        public String description() {
            return TXT_DESC;

        }

        private static final HashSet<Class<?>> RESISTANCES = new HashSet<Class<?>>();
        static {
            RESISTANCES.add( ToxicGas.class );
            RESISTANCES.add( Death.class );
            RESISTANCES.add( ScrollOfPsionicBlast.class );
        }

        @Override
        public HashSet<Class<?>> resistances() {
            return RESISTANCES;
        }

        private static final HashSet<Class<?>> IMMUNITIES = new HashSet<Class<?>>();
        static {
            IMMUNITIES.add( Amok.class );
            IMMUNITIES.add( Sleep.class );
            IMMUNITIES.add( Terror.class );
            IMMUNITIES.add( Poison.class );

        }

        @Override
        public HashSet<Class<?>> immunities() {
            return IMMUNITIES;
        }
    }

    public static class BurningFist extends Mob {

        {
            name = "burning tentacle";
            spriteClass = BurningFistSprite.class;

            HP = HT = 200;
            defenseSkill = 25;

            EXP = 0;

            state = WANDERING;
        }

        public BurningFist() {
            super();
            tentaclesCount++;
        }

        @Override
        public void die( Object cause ) {
            super.die( cause );
            tentaclesCount--;
        }

        @Override
        protected boolean getCloser( int target ) {

            if (rooted) {
                return false;
            }

            int step = Dungeon.findPath(this, pos, target,
                    Level.water,
                    Level.fieldOfView);
            if (step != -1) {
                move( step );
                return true;
            } else {
                return false;
            }
        }

        @Override
        protected boolean getFurther( int target ) {
            int step = Dungeon.flee( this, pos, target,
                    Level.water,
                    Level.fieldOfView );
            if (step != -1) {
                move( step );
                return true;
            } else {
                return false;
            }
        }

        @Override
        public int attackSkill( Char target ) {
            return 36;
        }

        @Override
        public int damageRoll() {
            return Random.NormalIntRange( 20, 32 );
        }

        @Override
        public int dr() {
            return 15;
        }



        @Override
        public boolean attack( Char enemy ) {

            if (!Level.adjacent( pos, enemy.pos )) {
                spend( attackDelay() );

                if (hit( this, enemy, true )) {

                    int dmg =  damageRoll();
                    enemy.damage( dmg, this );

                    enemy.sprite.bloodBurstA( sprite.center(), dmg );
                    enemy.sprite.flash();

                    if (!enemy.isAlive() && enemy == Dungeon.hero) {
                        Dungeon.fail( Utils.format( ResultDescriptions.BOSS, name, Dungeon.depth ) );
                        GLog.n( TXT_KILL, name );
                    }
                    return true;

                } else {

                    enemy.sprite.showStatus( CharSprite.NEUTRAL,  enemy.defenseVerb() );
                    return false;
                }
            } else {
                return super.attack( enemy );
            }
        }

        @Override
        public boolean act() {
            if (!Level.water[pos]) {
                die( null );
                return true;
            } else {
                return super.act();
            }
        }

        @Override
        public String description() {
            return TXT_DESC;

        }

        private static final HashSet<Class<?>> RESISTANCES = new HashSet<Class<?>>();
        static {
            RESISTANCES.add( ToxicGas.class );
            RESISTANCES.add( Death.class );
            RESISTANCES.add( ScrollOfPsionicBlast.class );
        }

        @Override
        public HashSet<Class<?>> resistances() {
            return RESISTANCES;
        }

        private static final HashSet<Class<?>> IMMUNITIES = new HashSet<Class<?>>();
        static {
            IMMUNITIES.add( Amok.class );
            IMMUNITIES.add( Sleep.class );
            IMMUNITIES.add( Terror.class );
            IMMUNITIES.add( Burning.class );
        }

        @Override
        public HashSet<Class<?>> immunities() {
            return IMMUNITIES;
        }
    }

    public static class Larva extends Mob {

        {
            name = "god's larva";
            spriteClass = LarvaSprite.class;

            HP = HT = 25;
            defenseSkill = 20;

            EXP = 0;

            state = HUNTING;
        }

        @Override
        public int attackSkill( Char target ) {
            return 30;
        }

        @Override
        public int damageRoll() {
            return Random.NormalIntRange( 15, 20 );
        }

        @Override
        public int dr() {
            return 8;
        }
        @Override
        protected boolean getCloser( int target ) {

            if (rooted) {
                return false;
            }

            int step = Dungeon.findPath( this, pos, target,
                    Level.water,
                    Level.fieldOfView );
            if (step != -1) {
                move( step );
                return true;
            } else {
                return false;
            }
        }

        @Override
        protected boolean getFurther( int target ) {
            int step = Dungeon.flee( this, pos, target,
                    Level.water,
                    Level.fieldOfView );
            if (step != -1) {
                move( step );
                return true;
            } else {
                return false;
            }
        }

        @Override
        public String description() {
            return TXT_DESC;

        }
    }
}

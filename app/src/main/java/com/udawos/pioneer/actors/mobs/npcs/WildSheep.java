package com.udawos.pioneer.actors.mobs.npcs;

import com.udawos.pioneer.Dungeon;
import com.udawos.pioneer.actors.Char;
import com.udawos.pioneer.actors.mobs.Mob;
import com.udawos.pioneer.items.food.MysteryMeat;
import com.udawos.pioneer.levels.Level;
import com.udawos.pioneer.levels.Terrain;
import com.udawos.pioneer.scenes.GameScene;
import com.udawos.pioneer.sprites.GoatSprite;
import com.udawos.pioneer.utils.Utils;
import com.udawos.utils.Random;

import java.util.HashSet;

public class WildSheep extends NPC {

    private static final String TXT_BLEAT 	= "Bleat!";

    {
        name = "wild sheep";
        spriteClass = GoatSprite.class;

        viewDistance = 8;

        state = WANDERING;
        WANDERING = new Wandering();

        loot = MysteryMeat.class;
        lootChance = 1.0f;
    }


    @Override
    public int attackSkill( Char target ) {
        return defenseSkill;
    }

    @Override
    public int damageRoll() {
        return Random.NormalIntRange(HT / 10, HT / 4);
    }

    @Override
    public int attackProc( Char enemy, int damage ) {
        if (enemy instanceof Mob) {
            ((Mob)enemy).aggro( this );
        }
        return damage;
    }

    @Override
    protected boolean act() {
        if (Level.passable[pos] && Random.Int(10) == 0) {
            Level.set(pos, Terrain.TRACKS);
            GameScene.updateMap(pos);
            Dungeon.observe();
        }

        if (HP <= 0) {
            die( null );
            return true;
        } else {
            return super.act();
        }
    }

    protected Char chooseEnemy() {

        if (enemy == null || !enemy.isAlive()) {
            HashSet<Mob> enemies = new HashSet<Mob>();
            for (Mob mob: Dungeon.level.mobs) {
                if (mob.hostile && Level.fieldOfView[mob.pos]) {
                    enemies.add( mob );
                }
            }

            return enemies.size() > 0 ? Random.element( enemies ) : null;

        } else {

            return enemy;

        }
    }


    @Override
    public void interact() {
        yell(TXT_BLEAT);
        state = FLEEING;
    }


    private static final HashSet<Class<?>> IMMUNITIES = new HashSet<Class<?>>();

    private class Wandering implements AiState {

        @Override
        public boolean act( boolean enemyInFOV, boolean justAlerted ) {
            if (enemyInFOV) {

                enemySeen = true;

                notice();
                state = HUNTING;
                target = enemy.pos;

            } else {

                enemySeen = false;

                int oldPos = pos;
                if (getFurther(Dungeon.hero.pos)) {
                    spend( 1 / speed() );
                    return moveSprite( oldPos, pos );
                } else {
                    spend( TICK );
                }

            }
            return true;
        }

        @Override
        public String status() {
            return Utils.format("This %s is wandering", name);
        }
    }

    @Override
    public String description() {
        return
                "This sheep looks delicious.";
    }




        public static void spawn(Level level) {

                WildSheep wildsheep = new WildSheep();
                do {
                    wildsheep.pos = Random.Int(50,2427);
                } while (wildsheep.pos == -1);
                level.mobs.add(wildsheep);
                occupyCell(wildsheep);


        }

}


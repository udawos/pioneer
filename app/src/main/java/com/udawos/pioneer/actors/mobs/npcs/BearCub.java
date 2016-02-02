package com.udawos.pioneer.actors.mobs.npcs;

import com.udawos.pioneer.Dungeon;
import com.udawos.pioneer.actors.Actor;
import com.udawos.pioneer.actors.Char;
import com.udawos.pioneer.actors.buffs.Buff;
import com.udawos.pioneer.actors.mobs.Bear;
import com.udawos.pioneer.actors.mobs.Mob;
import com.udawos.pioneer.effects.CellEmitter;
import com.udawos.pioneer.effects.particles.ElmoParticle;
import com.udawos.pioneer.items.Heap;
import com.udawos.pioneer.levels.E047HillLevel;
import com.udawos.pioneer.scenes.GameScene;
import com.udawos.pioneer.sprites.BearCubSprite;
import com.udawos.utils.Random;

/**
 * Created by Jake on 26/01/2016.
 */
public class BearCub extends NPC {


        {
            name = "Bear Cub";
            spriteClass = BearCubSprite.class;

            state = WANDERING;
        }

        private boolean mamaSpawned = false;

         @Override
         public int attackSkill( Char target ) {
        return defenseSkill;
    }

        @Override
        public float speed() {
            return 0.8f;
        }

        @Override
        protected Char chooseEnemy() {
            return null;
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
    public void damage( int dmg, Object src ) {
        yell("ROOOAAAAAR!");
        flee();

    }

    @Override
    public void add( Buff buff ) {
        flee();
    }

    protected void flee() {
        for (Heap heap: Dungeon.level.heaps.values()) {
            if (heap.type == Heap.Type.FOR_SALE) {
                CellEmitter.get(heap.pos).burst( ElmoParticle.FACTORY, 4 );
                heap.destroy();
            }
        }

        destroy();

        sprite.killAndErase();
        CellEmitter.get( pos ).burst(ElmoParticle.FACTORY, 6);

        if (!mamaSpawned) {
            Bear mamaBear = new Bear();
            mamaBear.pos = pos+1;
            mamaBear.state = mamaBear.HUNTING;

            GameScene.add(mamaBear);
            mamaSpawned = true;
        }
    }

        @Override
        public void interact() {
            sprite.turnTo( pos, Dungeon.hero.pos );

                yell( "Maw!" );
            if (!mamaSpawned) {
               Bear mamaBear = new Bear();
                mamaBear.pos = pos+1;
                mamaBear.state = mamaBear.HUNTING;

                GameScene.add(mamaBear);
                mamaSpawned = true;
            }
        }

        @Override
        public String description() {
            return
                    "A fluffy little polar bear cub.";
        }

    public static void spawn( E047HillLevel level ) {
        if (Dungeon.depth == 47) {

            BearCub npc = new BearCub();
            do {
                npc.pos = 2325;
            } while (npc.pos == -1);
            level.mobs.add(npc);
            Actor.occupyCell(npc);

        }
    }

}

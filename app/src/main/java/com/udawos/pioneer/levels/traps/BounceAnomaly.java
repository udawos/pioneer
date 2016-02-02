package com.udawos.pioneer.levels.traps;

import com.udawos.noosa.Camera;
import com.udawos.pioneer.Dungeon;
import com.udawos.pioneer.actors.Actor;
import com.udawos.pioneer.actors.Char;
import com.udawos.pioneer.actors.buffs.Buff;
import com.udawos.pioneer.actors.buffs.Cripple;
import com.udawos.pioneer.items.potions.Potion;
import com.udawos.pioneer.levels.Level;
import com.udawos.pioneer.levels.Terrain;
import com.udawos.pioneer.mechanics.Ballistica;
import com.udawos.utils.Callback;
import com.udawos.utils.Random;

/**
 * Created by Jake on 31/10/2015.
 */
public class BounceAnomaly extends Potion {


    public static void trigger(int pos, Char ch) {
        Integer target = pos + Random.Int(-10, 10);

        if (Level.passable[target]) {
            int cell = Ballistica.cast(Dungeon.hero.pos, target, false, true);
            if (Actor.findChar(cell) != null && cell != Dungeon.hero.pos) {
                cell = Ballistica.trace[Ballistica.distance - 2];
            }


            final int dest = cell;
            Dungeon.hero.busy();
            Dungeon.hero.sprite.jump(Dungeon.hero.pos, cell, new Callback() {
                @Override
                public void call() {
                    Dungeon.hero.move(dest);
                    Dungeon.level.press(dest, Dungeon.hero);
                    Dungeon.observe();

                    Camera.main.shake(2, 0.5f);
                    Buff.affect(Dungeon.hero, Cripple.class, Cripple.DURATION);
                }
            });
        }

    }

    public static void climbFall(int pos) {
        Integer target;

        if (Dungeon.level.map[pos] == Terrain.MOUNTAIN_S) {
            target = pos + 100;
        } else if (Dungeon.level.map[pos] == Terrain.MOUNTAIN_E) {
            target = pos + 2;
        } else if (Dungeon.level.map[pos] == Terrain.MOUNTAIN_W) {
            target = pos - 2;
        } else if (Dungeon.level.map[pos] == Terrain.MOUNTAIN_N) {
            target = pos - 100;
        } else if (Dungeon.level.map[pos] == Terrain.EMPTY) {
            target = pos;
        } else target = pos;



        if (Level.passable[target]) {
            int cell = Ballistica.cast(Dungeon.hero.pos, target, false, true);
            if (Actor.findChar(cell) != null && cell != Dungeon.hero.pos) {
                cell = Ballistica.trace[Ballistica.distance - 2];
            }


            final int dest = cell;

            Dungeon.hero.sprite.jump(Dungeon.hero.pos, cell, new Callback() {
                @Override
                public void call() {
                    Dungeon.hero.move(dest);
                    Dungeon.level.press(dest, Dungeon.hero);
                    Dungeon.observe();

                    Camera.main.shake(2, 0.5f);
                    if (Random.Int(1,20) == 1) {
                        Buff.affect(Dungeon.hero, Cripple.class, Cripple.DURATION);
                    }
                }
            });
        }


    }

}
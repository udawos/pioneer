package com.udawos.pioneer.items.potions;

import com.udawos.noosa.audio.Sample;
import com.udawos.pioneer.Assets;
import com.udawos.pioneer.Dungeon;
import com.udawos.pioneer.actors.hero.Hero;
import com.udawos.pioneer.effects.CellEmitter;
import com.udawos.pioneer.effects.Speck;
import com.udawos.pioneer.items.drinks.Water;
import com.udawos.pioneer.items.food.Ice;
import com.udawos.pioneer.levels.Level;
import com.udawos.pioneer.levels.Terrain;
import com.udawos.pioneer.scenes.GameScene;
import com.udawos.pioneer.utils.GLog;
import com.udawos.utils.Callback;

/**
 * Created by Jake on 25/11/2015.
 */
public class ActionOfGathering {

    public static final String AC_MINE	= "MINE";

    public static final float TIME_TO_MINE = 2f;

    private static final String TXT_NO_VEIN = "There is nothing near you to harvest.";


    public static void execute( final Hero hero, String action ) {

        if (action == AC_MINE) {

            /*if (Dungeon.depth < 11 || Dungeon.depth > 15) {
                GLog.w(TXT_NO_VEIN);
                return;
            }*/

            for (int i = 0; i < Level.NEIGHBOURS8.length; i++) {

                final int pos = hero.pos + Level.NEIGHBOURS8[i];
                if (Dungeon.level.map[pos] == Terrain.WALL_DECO) {

                    hero.spend(TIME_TO_MINE);
                    hero.busy();

                    hero.sprite.attack(pos, new Callback() {

                        @Override
                        public void call() {

                            CellEmitter.center(pos).burst(Speck.factory(Speck.STAR), 7);
                            Sample.INSTANCE.play(Assets.SND_EVOKE);

                            Level.set(pos, Terrain.EMPTY);
                            GameScene.updateMap(pos);

                            Ice ice = new Ice();
                            if (ice.doPickUp(Dungeon.hero)) {
                                GLog.i(Hero.TXT_YOU_NOW_HAVE, ice.name());
                            } else {
                                Dungeon.level.drop(ice, hero.pos).sprite.drop();
                            }

                            hero.onOperateComplete();
                        }
                    });

                    return;
                }
                if (Dungeon.level.map[pos] == Terrain.WATER) {

                    hero.spend(TIME_TO_MINE);
                    hero.busy();

                    hero.sprite.attack(pos, new Callback() {

                        @Override
                        public void call() {

                            CellEmitter.center(pos).burst(Speck.factory(Speck.STAR), 7);
                            Sample.INSTANCE.play(Assets.SND_EVOKE);

                            Level.set(pos, Terrain.WATER);
                            GameScene.updateMap(pos);

                            Water h2O = new Water();
                            if (h2O.doPickUp(Dungeon.hero)) {
                                GLog.i(Hero.TXT_YOU_NOW_HAVE, h2O.name());
                            } else {
                                Dungeon.level.drop(h2O, hero.pos).sprite.drop();
                            }

                            hero.onOperateComplete();
                        }
                    });

                    return;
                }
            }

            GLog.w(TXT_NO_VEIN);

        }
    }
}

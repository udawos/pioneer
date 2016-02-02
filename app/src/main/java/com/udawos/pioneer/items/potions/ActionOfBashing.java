package com.udawos.pioneer.items.potions;

import com.udawos.noosa.audio.Sample;
import com.udawos.pioneer.Assets;
import com.udawos.pioneer.Dungeon;
import com.udawos.pioneer.actors.hero.Hero;
import com.udawos.pioneer.effects.CellEmitter;
import com.udawos.pioneer.effects.Speck;
import com.udawos.pioneer.levels.Level;
import com.udawos.pioneer.levels.Terrain;
import com.udawos.pioneer.scenes.GameScene;
import com.udawos.pioneer.utils.GLog;
import com.udawos.utils.Callback;

/**
 * Created by Jake on 08/12/2015.
 */
public class ActionOfBashing {

    public static final String AC_BASH	= "BASH";

    public static final float TIME_TO_BASH = 2f;

    private static final String TXT_NO_BASH = "You aren't strong enough for this.";


    public static void bash( final Hero hero, String action ) {

        if (action == AC_BASH) {


            for (int i = 0; i < Level.NEIGHBOURS8.length; i++) {

                final int pos = hero.pos + Level.NEIGHBOURS8[i];
                if (Dungeon.level.map[pos] == Terrain.LOCKED_DOOR && Dungeon.hero.STR >= 15) {

                    hero.spend(TIME_TO_BASH);
                    hero.busy();

                    hero.sprite.attack(pos, new Callback() {

                        @Override
                        public void call() {

                            CellEmitter.center(pos).burst(Speck.factory(Speck.STAR), 7);
                            Sample.INSTANCE.play(Assets.SND_EVOKE);

                            Level.set(pos, Terrain.EMPTY);
                            GameScene.updateMap(pos);


                            hero.onOperateComplete();
                        }
                    });

                    return;
                }
                else if (Dungeon.hero.STR < 15){
                    GLog.w(TXT_NO_BASH);
                }

            }
        }
    }
}

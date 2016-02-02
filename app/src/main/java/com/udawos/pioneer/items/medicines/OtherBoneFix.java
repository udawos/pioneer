package com.udawos.pioneer.items.medicines;

import com.udawos.noosa.audio.Sample;
import com.udawos.pioneer.Assets;
import com.udawos.pioneer.Dungeon;
import com.udawos.pioneer.actors.blobs.Blob;
import com.udawos.pioneer.actors.blobs.Fire;
import com.udawos.pioneer.actors.buffs.BrokenLegs;
import com.udawos.pioneer.actors.buffs.Buff;
import com.udawos.pioneer.actors.hero.Hero;
import com.udawos.pioneer.scenes.GameScene;

/**
 * Created by Jake on 26/10/2015.
 */
public class OtherBoneFix extends Medicines {
    //Bonezipan
    //HIGHLY explosive if thrown

        {
            name = "Bonezipan";
        }

    @Override
    protected void apply( Hero hero ) {
        setKnown();
        Buff.detach(hero, BrokenLegs.class);
    }


        @Override
        public void shatter( int cell ) {

            if (Dungeon.visible[cell]) {
                setKnown();

                splash( cell );
                Sample.INSTANCE.play( Assets.SND_SHATTER );
            }

            GameScene.add(Blob.seed(cell, 2, Fire.class));
        }

        @Override
        public String desc() {
            return
                    "This single dosage of pills will embiggen the knittosity " +
                            "of your bones, healing breaks."+
                    " WARNING : DO NOT SHAKE";
        }

        @Override
        public int price() {
            return isKnown() ? 40 * quantity : super.price();
        }
    }


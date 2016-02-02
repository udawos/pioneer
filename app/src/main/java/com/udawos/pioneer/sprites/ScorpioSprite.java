
package com.udawos.pioneer.sprites;

import com.udawos.noosa.TextureFilm;
import com.udawos.noosa.audio.Sample;
import com.udawos.pioneer.Assets;
import com.udawos.pioneer.actors.mobs.EmaciatedCultist;
import com.udawos.pioneer.effects.MagicMissile;
import com.udawos.utils.Callback;

public class ScorpioSprite extends MobSprite {

    public ScorpioSprite() {
        super();

        texture( Assets.CULTIST );

        TextureFilm frames = new TextureFilm( texture, 16, 16 );

        idle = new Animation(12, true);
        idle.frames(frames, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0);

        run = new Animation(15, true);
        run.frames(frames, 3, 4, 5, 6, 7, 8);

        attack = new Animation(15, false);
        attack.frames(frames, 9, 10, 11 );

        zap = new Animation(8, false);
        zap.frames(frames, 11, 11);

        die = new Animation( 8, false );
        die.frames( frames, 12, 13, 14, 15 );

        play(idle);
    }



    public void zap( int cell ) {

        turnTo( ch.pos , cell );
        play( zap );

        MagicMissile.greyLight(parent, ch.pos, cell,
                new Callback() {
                    @Override
                    public void call() {
                        ((EmaciatedCultist) ch).onZapComplete();
                    }
                });
        Sample.INSTANCE.play( Assets.SND_ZAP );
    }

    @Override
    public void onComplete( Animation anim ) {
        if (anim == zap) {
            idle();
        }
        super.onComplete( anim );
    }
}


package com.udawos.pioneer.sprites;

import com.udawos.noosa.TextureFilm;
import com.udawos.noosa.audio.Sample;
import com.udawos.noosa.particles.Emitter;
import com.udawos.pioneer.Assets;
import com.udawos.pioneer.Dungeon;
import com.udawos.pioneer.actors.Char;
import com.udawos.pioneer.effects.Speck;
import com.udawos.pioneer.levels.Level;

public class BlacksmithSprite extends MobSprite {

    private Emitter emitter;

    public BlacksmithSprite() {
        super();

        texture( Assets.BADGER );

        TextureFilm frames = new TextureFilm( texture, 14, 16 );

        idle = new Animation( 15, true );
        idle.frames(frames, 0, 0, 0, 0, 0, 0, 0, 1, 2, 2, 2, 3);

        run = new Animation( 20, true );
        run.frames( frames, 0 );

        attack = new Animation( 12, false );
        attack.frames( frames, 0, 2, 3 );

        die = new Animation( 20, false );
        die.frames( frames, 0 );

        play( idle );
    }

    @Override
    public void link( Char ch ) {
        super.link( ch );

        emitter = new Emitter();
        emitter.autoKill = false;
        emitter.pos( x + 9, y + 12 );
        parent.add( emitter );
    }

    @Override
    public void update() {
        super.update();

        if (emitter != null) {
            emitter.visible = visible;
        }
    }

    @Override
    public void onComplete( Animation anim ) {
        super.onComplete( anim );

        if (visible && emitter != null && anim == idle) {
            emitter.burst( Speck.factory( Speck.FORGE ), 3 );
            float volume = 0.2f / (Level.distance( ch.pos, Dungeon.hero.pos ));
            Sample.INSTANCE.play( Assets.SND_EVOKE, volume, volume, 0.8f  );
        }
    }

}

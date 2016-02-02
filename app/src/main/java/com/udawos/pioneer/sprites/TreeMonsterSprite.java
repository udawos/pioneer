package com.udawos.pioneer.sprites;

import com.udawos.noosa.TextureFilm;
import com.udawos.pioneer.Assets;

/**
 * Created by Jake on 26/01/2016.
 */
public class TreeMonsterSprite extends MobSprite {


        public TreeMonsterSprite() {
            super();

            texture( Assets.TREEMONSTER );

            TextureFilm frames = new TextureFilm( texture, 16 );

            idle = new Animation( 5, true );
            idle.frames( frames, 0, 0, 0, 0 );

            run = new Animation( 15, true );
            run.frames( frames, 1, 2, 3, 4, 3, 4, 2, 1 );

            attack = new Animation( 12, false );
            attack.frames( frames, 1, 5, 6 );

            die = new Animation( 12, false );
            die.frames( frames, 7, 8, 9, 10, 11 );

            play( idle );
        }

        @Override
        public int blood() {
            return 0xFFFFEA80;
        }
    }




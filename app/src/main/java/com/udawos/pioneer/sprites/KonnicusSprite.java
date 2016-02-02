/*
 * Pixel Dungeon
 * Copyright (C) 2012-2015 Oleg Dolya
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>
 */
package com.udawos.pioneer.sprites;

import com.udawos.noosa.TextureFilm;
import com.udawos.noosa.particles.PixelParticle;
import com.udawos.pioneer.Assets;
import com.udawos.pioneer.actors.Char;

public class KonnicusSprite extends MobSprite {

    private PixelParticle coin;

    public KonnicusSprite() {
        super();

        texture( Assets.KONNICUS );

        TextureFilm frames = new TextureFilm( texture, 14, 14 );

        idle = new Animation( 15, true );
        idle.frames(frames, 0, 0, 0, 0, 0, 0, 1, 1, 2, 2, 3, 3, 3);

        run = new Animation( 20, true );
        run.frames( frames, 0 );

        attack = new Animation( 12, false );
        attack.frames( frames, 0, 0, 0, 0 );

        die = new Animation( 20, false );
        die.frames( frames, 0 );

        play( idle );
    }

    @Override
    public void link( Char ch ) {
        super.link( ch );

    }

    @Override
    public void update() {
        super.update();

    }


}

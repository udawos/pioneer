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
import com.udawos.pioneer.Assets;
import com.udawos.pioneer.Dungeon;
import com.udawos.pioneer.DungeonTilemap;
import com.udawos.pioneer.effects.DeathRay;

public class EyeGuardianSprite extends MobSprite {

    private int attackPos;

    public EyeGuardianSprite() {
        super();

        texture( Assets.TURRET );

        TextureFilm frames = new TextureFilm( texture, 16, 16 );

        idle = new Animation( 8, true );
        idle.frames( frames, 0 );

        attack = new Animation( 8, false );
        attack.frames( frames, 1, 2, 3 );

        die = new Animation( 8, false );
        die.frames( frames, 0);

        play( idle );
    }

    @Override
    public void attack( int pos ) {
        attackPos = pos;
        super.attack( pos );
    }

    @Override
    public void onComplete( Animation anim ) {
        super.onComplete( anim );

        if (anim == attack) {
            if (Dungeon.visible[ch.pos] || Dungeon.visible[attackPos]) {
                parent.add( new DeathRay( center(), DungeonTilemap.tileCenterToWorld( attackPos ) ) );
            }
        }
    }
}

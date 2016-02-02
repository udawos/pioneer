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

import android.graphics.RectF;

import com.udawos.gltextures.SmartTexture;
import com.udawos.gltextures.TextureCache;
import com.udawos.noosa.Camera;
import com.udawos.noosa.Image;
import com.udawos.noosa.TextureFilm;
import com.udawos.pioneer.Assets;
import com.udawos.pioneer.Dungeon;
import com.udawos.pioneer.actors.hero.Belongings;
import com.udawos.pioneer.actors.hero.Hero;
import com.udawos.pioneer.actors.hero.HeroClass;
import com.udawos.utils.Callback;

public class HeroSprite extends CharSprite {
	
	private static final int FRAME_WIDTH	= 16;
	private static final int FRAME_HEIGHT	= 16;
	
	private static final int RUN_FRAMERATE	= 20;


	private static TextureFilm tiers;
	
	private Animation fly;
	private Animation crawl;

	public HeroSprite() {
		super();
		
		link(Dungeon.hero);
		
		texture(Dungeon.hero.heroClass.spritesheet());
		updateArmor();

        idle();
	}

	
	public void updateArmor() {

		TextureFilm film = new TextureFilm( tiers(), Belongings.tier, FRAME_WIDTH, FRAME_HEIGHT );

        idle = new Animation(12, true);
        idle.frames(film, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 2);

        run = new Animation(15, true);
        run.frames(film, 3, 4, 5, 6, 7, 8);

        attack = new Animation(12, false);
        attack.frames(film, 9, 10, 11);

        zap = new Animation(8, false);
        zap.frames(film, 11, 11);

        die = new Animation(8, false);
        die.frames(film, 12, 13, 14, 15);

        operate = attack.clone();

        fly = new Animation( 1, true );
        fly.frames( film, 11 );

        crawl = new Animation(8, false);
        crawl.frames( film, 15);

        //vomit to be used primarily when getting bounced around by anomalies
       // vomit = new Animation(12, true);
        //vomit.frames( film, 12, 13, 16);
	}

    @Override
	public void place( int p ) {
		super.place(p);
		Camera.main.target = this;
	}

	@Override
	public void move( int from, int to ) {		
		super.move(from, to);
		if (ch.flying) {
			play( fly );
		}
        if (ch.crawling) {
            play( crawl);
        }
		Camera.main.target = this;
	}

	@Override
	public void jump(int from, int to, Callback callback ) {
		super.jump(from, to, callback);
		play( fly );
	}
	
	@Override
	public void update() {
		sleeping = ((Hero)ch).restoreHealth;
		
		super.update();
	}
	
	public boolean sprint( boolean on ) {
		run.delay = on ? 0.625f / RUN_FRAMERATE : 1f / RUN_FRAMERATE;
		return on;
	}
	
	public static TextureFilm tiers() {
		if (tiers == null) {
			SmartTexture texture = TextureCache.get( Assets.ROGUE );
			tiers = new TextureFilm( texture, texture.width, FRAME_HEIGHT );
		}
		
		return tiers;
	}
	
	public static Image avatar( HeroClass cl, int armorTier ) {
		
		RectF patch = tiers().get( armorTier );
		Image avatar = new Image( cl.spritesheet() );
		RectF frame = avatar.texture.uvRect( 1, 0, FRAME_WIDTH, FRAME_HEIGHT );
		frame.offset( patch.left, patch.top );
		avatar.frame( frame );
		
		return avatar;
	}
}

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
package com.udawos.pioneer.effects.particles;

import com.udawos.noosa.particles.Emitter;
import com.udawos.noosa.particles.PixelParticle;
import com.udawos.noosa.particles.Emitter.Factory;
import com.udawos.utils.ColorMath;
import com.udawos.utils.PointF;
import com.udawos.utils.Random;

public class GreyParticle extends PixelParticle {
	
	public static final Emitter.Factory MISSILE = new Factory() {	
		@Override
		public void emit( Emitter emitter, int index, float x, float y ) {
			((GreyParticle)emitter.recycle( GreyParticle.class )).reset( x, y );
		}
	};
	
	public static final Emitter.Factory BURST = new Factory() {	
		@Override
		public void emit( Emitter emitter, int index, float x, float y ) {
			((GreyParticle)emitter.recycle( GreyParticle.class )).resetBurst( x, y );
		}
		@Override
		public boolean lightMode() {
			return true;
		}
	};
	
	public GreyParticle() {
		super();
		
		lifespan = 0.1f;
	}
	
	public void reset( float x, float y ) {
		revive();
		
		this.x = x;
		this.y = y;
		
		speed.set( Random.Float( -5, +5 ), Random.Float( -5, +5 ) );
		
		left = lifespan;
	}
	
	public void resetBurst( float x, float y ) {
		revive();
		
		this.x = x;
		this.y = y;
		
		speed.polar( Random.Float( PointF.PI2 ), Random.Float( 16, 32 ) );
		
		left = lifespan;
	}
	
	@Override
	public void update() {
		super.update();
		// alpha: 1 -> 0; size: 1 -> 5
		size( 5 - (am = left / lifespan) * 4 );
		// color: 0xFF0044 -> 0x220066
		color( ColorMath.interpolate( 0xa5a5a5, 0x5f5d63, am ) );
	}
}
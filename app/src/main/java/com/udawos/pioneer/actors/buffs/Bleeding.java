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
package com.udawos.pioneer.actors.buffs;

import com.udawos.pioneer.Dungeon;
import com.udawos.pioneer.ResultDescriptions;
import com.udawos.pioneer.effects.Splash;
import com.udawos.pioneer.ui.BuffIndicator;
import com.udawos.pioneer.utils.GLog;
import com.udawos.pioneer.utils.Utils;
import com.udawos.utils.Bundle;
import com.udawos.utils.PointF;

public class Bleeding extends Buff {

	protected int level;
	
	private static final String LEVEL	= "level";
	
	@Override
	public void storeInBundle( Bundle bundle ) {
		super.storeInBundle( bundle );
		bundle.put( LEVEL, level );
		
	}
	
	@Override
	public void restoreFromBundle( Bundle bundle ) {
		super.restoreFromBundle(bundle);
		level = bundle.getInt( LEVEL );
	}
	
	public void set( int level ) {
		this.level = level;
	};
	
	@Override
	public int icon() {
		return BuffIndicator.BLEEDING;
	}
	
	@Override
	public String toString() {
		return "Bleeding";
	}


	@Override
	public boolean act() {
		if (target.isAlive()) {
				
				target.damage( 2, this );
				if (target.sprite.visible) {
					Splash.at( target.sprite.center(), -PointF.PI / 2, PointF.PI / 6, 
							target.sprite.blood(), Math.min( 10 * 5 / target.HT, 10 ));
				}
				
				if (target == Dungeon.hero && !target.isAlive()) {
					Dungeon.fail( Utils.format( ResultDescriptions.BLEEDING, Dungeon.depth ) );
					GLog.n( "You bled to death..." );
				}
				
				spend( TICK );
			} else {
				detach();
			}

		
		return true;
	}
}

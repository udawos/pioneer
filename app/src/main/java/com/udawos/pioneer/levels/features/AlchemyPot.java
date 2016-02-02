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
package com.udawos.pioneer.levels.features;

import com.udawos.pioneer.actors.hero.Hero;
import com.udawos.pioneer.items.Item;
import com.udawos.pioneer.scenes.GameScene;
import com.udawos.pioneer.windows.WndBag;

public class AlchemyPot {

	private static final String TXT_SELECT_SMTHG	= "Throw something in the fire";
	
	private static Hero hero;
	private static int pos;
	
	public static void operate( Hero hero, int pos ) {
		
		AlchemyPot.hero = hero;
		AlchemyPot.pos = pos;
		
		GameScene.selectItem( itemSelector, WndBag.Mode.ALL, TXT_SELECT_SMTHG);
	}
	
	private static final WndBag.Listener itemSelector = new WndBag.Listener() {
		@Override
		public void onSelect( Item item ) {
			if (item != null) {
				item.cast( hero, pos );
			}
		}
	};
}

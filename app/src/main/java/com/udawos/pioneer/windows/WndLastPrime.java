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
package com.udawos.pioneer.windows;

import com.udawos.noosa.BitmapTextMultiline;
import com.udawos.pioneer.Dungeon;
import com.udawos.pioneer.actors.hero.Hero;
import com.udawos.pioneer.actors.mobs.npcs.LastPrime;
import com.udawos.pioneer.items.Item;
import com.udawos.pioneer.items.wands.Wand;
import com.udawos.pioneer.scenes.PixelScene;
import com.udawos.pioneer.sprites.ItemSprite;
import com.udawos.pioneer.ui.RedButton;
import com.udawos.pioneer.ui.Window;
import com.udawos.pioneer.utils.GLog;
import com.udawos.pioneer.utils.Utils;

public class WndLastPrime extends Window {
	
	private static final String TXT_MESSAGE	= 
		"Oh, I see you have succeeded! I do hope it hasn't troubled you too much. " +
		"As I promised, you can choose one of my high quality wands.";
	private static final String TXT_BATTLE		= "Battle wand";
	private static final String TXT_NON_BATTLE	= "Non-battle wand";
	
	private static final String TXT_FAREWELL	= "Good luck in your quest, %s!";
	
	private static final int WIDTH		= 120;
	private static final int BTN_HEIGHT	= 20;
	private static final float GAP		= 2;
	
	public WndLastPrime( final LastPrime lastPrime, final Item item ) {
		
		super();
		
		IconTitle titlebar = new IconTitle();
		titlebar.icon( new ItemSprite( item.image(), null ) );
		titlebar.label( Utils.capitalize( item.name() ) );
		titlebar.setRect( 0, 0, WIDTH, 0 );
		add( titlebar );
		
		BitmapTextMultiline message = PixelScene.createMultiline( TXT_MESSAGE, 6 );
		message.maxWidth = WIDTH;
		message.measure();
		message.y = titlebar.bottom() + GAP;
		add( message );
		
		RedButton btnBattle = new RedButton( TXT_BATTLE ) {
			@Override
			protected void onClick() {
				selectReward( lastPrime, item, LastPrime.Quest.wand1 );
			}
		};
		btnBattle.setRect( 0, message.y + message.height() + GAP, WIDTH, BTN_HEIGHT );
		add( btnBattle );
		
		RedButton btnNonBattle = new RedButton( TXT_NON_BATTLE ) {
			@Override
			protected void onClick() {
				selectReward( lastPrime, item, LastPrime.Quest.wand2 );
			}
		};
		btnNonBattle.setRect( 0, btnBattle.bottom() + GAP, WIDTH, BTN_HEIGHT );
		add( btnNonBattle );
		
		resize( WIDTH, (int)btnNonBattle.bottom() );
	}
	
	private void selectReward( LastPrime lastPrime, Item item, Wand reward ) {
		
		hide();
		
		item.detach( Dungeon.hero.belongings.backpack );

		reward.identify();
		if (reward.doPickUp( Dungeon.hero )) {
			GLog.i( Hero.TXT_YOU_NOW_HAVE, reward.name() );
		} else {
			Dungeon.level.drop( reward, lastPrime.pos ).sprite.drop();
		}
		
		lastPrime.yell( Utils.format( TXT_FAREWELL, Dungeon.hero.className() ) );
		lastPrime.destroy();
		
		lastPrime.sprite.die();
		
		//LastPrime.Quest.complete();
	}
}

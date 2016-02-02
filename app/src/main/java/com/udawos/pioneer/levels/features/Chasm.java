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

import com.udawos.noosa.Camera;
import com.udawos.pioneer.Badges;
import com.udawos.pioneer.Dungeon;
import com.udawos.pioneer.ResultDescriptions;
import com.udawos.pioneer.actors.hero.Hero;
import com.udawos.pioneer.actors.mobs.Mob;
import com.udawos.pioneer.scenes.GameScene;
import com.udawos.pioneer.sprites.MobSprite;
import com.udawos.pioneer.utils.GLog;
import com.udawos.pioneer.utils.Utils;
import com.udawos.pioneer.windows.WndOptions;
import com.udawos.utils.Random;

public class Chasm {
	
	private static final String TXT_CHASM	= "Chasm";
	private static final String TXT_YES		= "Yes, I know what I'm doing";
	private static final String TXT_NO		= "No, I changed my mind";
	private static final String TXT_JUMP 	= 
		"Do you really want to jump into the chasm? You can probably die.";
	
	public static boolean jumpConfirmed = false;
	
	public static void heroJump( final Hero hero ) {
		GameScene.show(new WndOptions( TXT_CHASM, TXT_JUMP, TXT_YES, TXT_NO ) {
				@Override
				protected void onSelect( int index ) {
					if (index == 0) {
						jumpConfirmed = true;
						hero.resume();
					}
				};
			}
		);
	}

	public static void heroLand() {
		
		Hero hero = Dungeon.hero;

		hero.sprite.burst(hero.sprite.blood(), 10);
		Camera.main.shake(4, 0.2f);

        int damage = Math.max( 0,  (Dungeon.depth + 3) - Random.IntRange( 0, hero.dr() / 2 ) );
        //Buff.affect( hero, Bleeding.class ).set(damage);
		//Buff.prolong(hero, BrokenLegs.class, BrokenLegs.DURATION);
		hero.damage( Random.IntRange( hero.HT / 3, hero.HT / 2 ), new Hero.Doom() {
			@Override
			public void onDeath() {
				Badges.validateDeathFromFalling();
				
				Dungeon.fail( Utils.format( ResultDescriptions.FALL, Dungeon.depth ) );
				GLog.n( "You fell to death..." );
			}
		} );
	}

	public static void mobFall( Mob mob ) {
		mob.destroy();
		((MobSprite)mob.sprite).fall();
	}
}

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
package com.udawos.pioneer.scenes;

import com.udawos.noosa.Game;
import com.udawos.pioneer.windows.WndStory;

public class IntroScene extends PixelScene {

	private static final String TEXT = 	
		"You are an assault pioneer module, part of the Federation's 1st Infantry, 1st Battalion; Favoured of the Origin. " +
		"You have been launched via expeditionary artillery insertion shell (EAIS) into the unexplored 9th Border Region. \n\n" +
		"The transmitter has been launched and should be nearby, to the west. "+
        "Should you require, supplies can be requisitioned via transmitter."    ;
	
	@Override
	public void create() {
		super.create();
		
		add( new WndStory( TEXT ) {
			@Override
			public void hide() {
				super.hide();
				Game.switchScene( InterlevelScene.class );
			}
		} );
		
		fadeIn();
	}
}

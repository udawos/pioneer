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

import android.content.Intent;
import android.net.Uri;

import com.udawos.input.Touchscreen.Touch;
import com.udawos.noosa.BitmapTextMultiline;
import com.udawos.noosa.Camera;
import com.udawos.noosa.Game;
import com.udawos.noosa.TouchArea;
import com.udawos.pioneer.Pioneer;
import com.udawos.pioneer.ui.Archs;
import com.udawos.pioneer.ui.ExitButton;
import com.udawos.pioneer.ui.Window;

public class AboutScene extends PixelScene {

	private static final String TXT = 
		"Code & graphics: Udawos\n" +
		"Music: Victor Herbert Orchestra Edison Cylinders\n\n"+
        "(http://freemusicarchive.org/music/\n\n"+
        "Victor_Herbert_Orchestra/Edison_Cylinders)\n\n"+
		"This game is inspired by and\n\n"+
        "uses the source code (and some art)\n\n"+
                "from Oleg Dolya's PIXEL DUNGEON  \n\n" +
		"Try that game on Windows, Mac OS or Linux - it's awesome! ;)\n\n" +
		"Please visit Pioneer's  official website for additional info:";

	private static final String LNK = "pioneerwiki.wordpress.com";
	
	@Override
	public void create() {
		super.create();
		
		BitmapTextMultiline text = createMultiline( TXT, 5 );
		text.maxWidth = Math.min( Camera.main.width, 120 );
		text.measure();
		add( text );
		
		text.x = align( (Camera.main.width - text.width()) / 2 );
		text.y = align( (Camera.main.height - text.height()) / 2 );
		
		BitmapTextMultiline link = createMultiline( LNK, 8 );
		link.maxWidth = Math.min( Camera.main.width, 120 );
		link.measure();
		link.hardlight( Window.TITLE_COLOR );
		add( link );
		
		link.x = text.x;
		link.y = text.y + text.height();
		
		TouchArea hotArea = new TouchArea( link ) {
			@Override
			protected void onClick( Touch touch ) {
				Intent intent = new Intent( Intent.ACTION_VIEW, Uri.parse( "http://" + LNK ) );
				Game.instance.startActivity( intent );
			}
		};
		add( hotArea );


		
		Archs archs = new Archs();
		archs.setSize( Camera.main.width, Camera.main.height );
		addToBack( archs );
		
		ExitButton btnExit = new ExitButton();
		btnExit.setPos( Camera.main.width - btnExit.width(), 0 );
		add( btnExit );
		
		fadeIn();
	}
	
	@Override
	protected void onBackPressed() {
		Pioneer.switchNoFade( TitleScene.class );
	}
}

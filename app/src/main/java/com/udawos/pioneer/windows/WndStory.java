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

import com.udawos.input.Touchscreen.Touch;
import com.udawos.noosa.BitmapTextMultiline;
import com.udawos.noosa.Game;
import com.udawos.noosa.TouchArea;
import com.udawos.pioneer.Chrome;
import com.udawos.pioneer.Dungeon;
import com.udawos.pioneer.scenes.PixelScene;
import com.udawos.pioneer.ui.Window;
import com.udawos.utils.SparseArray;

public class WndStory extends Window {

	private static final int WIDTH = 120;
	private static final int MARGIN = 6;
	
	private static final float bgR	= 1.00f;
	private static final float bgG	= 1.00f;
	private static final float bgB	= 1.00f;

	public static final int ID_START		= 1;
	public static final int ID_NORTH1		= 2;
	public static final int ID_NORTH2		= 3;
	public static final int ID_NORTH3		= 4;
	public static final int ID_NORTH4		= 5;
	public static final int ID_NORTH5		= 6;
	public static final int ID_NORTH6		= 7;
	public static final int ID_NORTH7		= 8;
	public static final int ID_NORTH8		= 9;
	public static final int ID_NORTH9		= 10;
	public static final int ID_NORTH10		= 11;
    public static final int ID_NORTH11		= 12;
	public static final int ID_NORTH12		= 13;
	public static final int ID_NORTH13		= 14;
	public static final int ID_NORTH14		= 15;
	public static final int ID_NORTH15		= 16;
	public static final int ID_NORTH16		= 17;
	public static final int ID_NORTH17		= 18;
	public static final int ID_NORTH18		= 19;
	public static final int ID_NORTH19		= 20;
	public static final int ID_NORTH20		= 21;
	public static final int ID_WEST1		= 13;
    public static final int ID_WEST14		= 14;
    public static final int ID_DOWN5        = 15;
	
	private static final SparseArray<String> CHAPTERS = new SparseArray<String>();
	
	static {
		
		CHAPTERS.put( ID_START,
                "You are a pathfinder in the service of the Federation. " +
                        " You have been sent into an Anomalous Zone, located to the west of current Federation borders. " +
                        " This Anomalous Zone has been a thorn in the side of the Federation ever since its discovery. " +
                        " For reasons unknown, the entire region is surrounded by an deadly barrier of light...but only on the ground."+
                        " You have been inserted via an Expeditionary Artillery Insertion Shell (EAIS)."+
                        " Your mission is to explore the region, determine the origin of the barrier and contact Expeditionary Command"+
                        " for further instructions."+
                            "\n \n "+
                            "Now remove your weapon from your pack and begin your mission. ");
		
		CHAPTERS.put( ID_NORTH1,
		"You see low, rolling hills that stretch to the north and west. To the east looms the impenetrable light barrier.");
        CHAPTERS.put( ID_NORTH2,
                "You see low, rolling hills that stretch to the north. Trees dot the landscape. ");
        CHAPTERS.put( ID_NORTH3,
                "You see low, rolling hills. An ancient is barely visible from here.");
        CHAPTERS.put( ID_NORTH4,
                "You see grassy, rolling hills which level out to the west. The road can be seen here and runs to the north. ");
        CHAPTERS.put( ID_NORTH5,
                "You see mountains rising to the west. Might there be a way around them? Or perhaps under...?");
        CHAPTERS.put( ID_NORTH6,
                "You see mountains flanking an expansive forest. ");
        CHAPTERS.put( ID_NORTH7,
                "You see mountains rising from the earth. The terrain appears to be getting more difficult to navigate. ");
        CHAPTERS.put( ID_NORTH8,
                "You see three peaks rising into the skies. It is getting colder.");
        CHAPTERS.put( ID_NORTH9,
                "You see mountains. The trees in this place seem to be thicker than elsewhere. ");
        CHAPTERS.put(ID_NORTH10,
                "You see an expanse of trees spreading to the south. The mountain range seems to be" +
                        "shifting north. To the west, you see the impenetrable barrier of light. ");
        CHAPTERS.put( ID_NORTH17,
                "You see a huge lake. Despite its great size, the lake is exceptionally shallow. Surely nothing lives here...");
        CHAPTERS.put( ID_NORTH18,
                "You see hills sloping to the west. To the east, you see a forest.");
        CHAPTERS.put( ID_NORTH19,
                "You see what appears to be an abandoned farm. Surely there is not danger here...");
        CHAPTERS.put( ID_NORTH20,
                "You see three tightly grouped peaks to the west. To the west, you see the impenetrable barrier of light. ");
		CHAPTERS.put( ID_NORTH12,
		"Between the trees you see some ancient stone columns and a ruined structure of some kind." );
		
		CHAPTERS.put( ID_WEST1,
		"You see low, rolling hills that stretch to the north and west." );

        CHAPTERS.put( ID_DOWN5, "As you climb up, the tunnel seals as if it were never there...");
	};
	
	private BitmapTextMultiline tf;
	
	private float delay;
	
	public WndStory( String text ) {
		super( 0, 0, Chrome.get( Chrome.Type.SCROLL ) );
		
		tf = PixelScene.createMultiline( text, 7 );
		tf.maxWidth = WIDTH - MARGIN * 2;
		tf.measure();
		tf.rm = bgR;
		tf.gm = bgG;
		tf.bm = bgB;
		tf.x = MARGIN;
		add( tf );
		
		add( new TouchArea( chrome ) {
			@Override
			protected void onClick( Touch touch ) {
				hide();
			}
		} );
		
		resize( (int)(tf.width() + MARGIN * 2), (int)Math.min( tf.height(), 180 ) );
	}
	
	@Override
	public void update() {
		super.update();
		
		if (delay > 0 && (delay -= Game.elapsed) <= 0) {
			shadow.visible = chrome.visible = tf.visible = true;
		}
	}
	
	public static void showChapter( int id ) {
		
		if (Dungeon.chapters.contains( id )) {
			return;
		}
		
		String text = CHAPTERS.get( id );
		if (text != null) {
			WndStory wnd = new WndStory( text );
			if ((wnd.delay = 0.6f) > 0) {
				wnd.shadow.visible = wnd.chrome.visible = wnd.tf.visible = false;
			}
			
			Game.scene().add( wnd );
			
			Dungeon.chapters.add( id );
		}
	}
}

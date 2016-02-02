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

public class WndLookoutInfo extends Window {

    //Look North
    //If Dungeon.depth= X, then return DepthInfo for depth X+10
    //repeat for other directions (E, S, W)

    private static final int WIDTH = 120;
    private static final int MARGIN = 6;

    private static final float bgR	= 1.00f;
    private static final float bgG	= 1.00f;
    private static final float bgB	= 1.00f;

    public static final int ID_BASE 	= 1;
    public static final int ID_PASS		= 2;
    public static final int ID_BURNT_HOUSE	= 3;
    public static final int ID_COMPOUND		= 4;

    private static final SparseArray<String> CHAPTERS = new SparseArray<String>();

    static {

        CHAPTERS.put( ID_BASE,
                "Looking in this direction you can see the impact site of the Federation drop pods." );

        CHAPTERS.put( ID_PASS,
                "From here, you can see what looks like a pass between the towering mountains. " +
                        " There also appear to be enemy troops patrolling the area. " +
                        "There are a lot of them. " +
                        "And it looks like they're training pretty hard.." );

        CHAPTERS.put( ID_BURNT_HOUSE,
                " " +
                        " " +
                        "." );

        CHAPTERS.put( ID_COMPOUND,
                "In the past these levels were the outskirts of Metropolis. After the costly victory in the war with the old god " +
                        "dwarves were too weakened to clear them of remaining demons. Gradually demons have tightened their grip on this place " +
                        "and now it's called Demon Halls.\n\n" +
                        "Very few adventurers have ever descended this far..." );
    };

    private BitmapTextMultiline tf;

    private float delay;

    public WndLookoutInfo( String text ) {
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
            WndLookoutInfo wnd = new WndLookoutInfo( text );
            if ((wnd.delay = 0.6f) > 0) {
                wnd.shadow.visible = wnd.chrome.visible = wnd.tf.visible = false;
            }

            Game.scene().add( wnd );

            Dungeon.chapters.add( id );
        }
    }
}

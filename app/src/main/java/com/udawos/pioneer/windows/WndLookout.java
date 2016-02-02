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

import com.udawos.noosa.audio.Sample;
import com.udawos.pioneer.Assets;
import com.udawos.pioneer.Dungeon;
import com.udawos.pioneer.Pioneer;
import com.udawos.pioneer.items.potions.ActionOfLeaping;
import com.udawos.pioneer.levels.Terrain;
import com.udawos.pioneer.ui.RedButton;
import com.udawos.pioneer.ui.Window;
import com.udawos.pioneer.utils.GLog;

public class WndLookout extends Window {

    private static final String TXT_NORTH      = "Look to the north";

    private static final String TXT_EAST     = "Look to the east";

    private static final String  TXT_SOUTH      = "Look to the south";

    private static final String TXT_WEST        = "Look to the west";

    private static final String TXT_CANT_SEE     = "Can't see much of anything from down here.";


    private static final int WIDTH		= 112;
    private static final int BTN_HEIGHT	= 20;
    private static final int GAP 		= 2;

    public WndLookout() {
        super();



        RedButton btnNorth = new RedButton( TXT_NORTH) {

            int pos = Dungeon.hero.pos;
            protected void onClick() {
                if (Dungeon.depth == 1 && Dungeon.level.map[pos] == Terrain.HILL) {
                    WndLookoutInfo.showChapter(WndLookoutInfo.ID_BASE);
                }
                else GLog.i(TXT_CANT_SEE);
                hide();
            }
        };
        btnNorth.setRect(0, 0, WIDTH, BTN_HEIGHT);
        add(btnNorth);




        RedButton btnEast = new RedButton( TXT_EAST ) {
            @Override
            protected void onClick() {
                hide();
            }
        };
        btnEast.setRect(0, btnNorth.bottom() + GAP, WIDTH, BTN_HEIGHT);
        add(btnEast);

        RedButton btnSouth = new RedButton( TXT_SOUTH ) {
            @Override
            protected void onClick() {
                ActionOfLeaping.Leap();
                hide();
            }
        };
        btnSouth.setRect( 0, btnEast.bottom() + GAP, WIDTH, BTN_HEIGHT );
        add(btnSouth);

        RedButton btnWest = new RedButton( TXT_WEST ) {
            @Override
            protected void onClick() {
                Pioneer.soundFx();
                Sample.INSTANCE.play(Assets.SND_CLICK);
                hide();
            }
        };
        btnWest.setRect( 0, btnSouth.bottom() + GAP, WIDTH, BTN_HEIGHT );
        add(btnWest);

        resize(WIDTH, (int) btnWest.bottom());


    }


}

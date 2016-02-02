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

import com.udawos.pioneer.actors.mobs.npcs.Campfire;
import com.udawos.pioneer.ui.RedButton;
import com.udawos.pioneer.ui.Window;

public class WndFireOptions extends Window {

    private static final String TXT_COOK        = "Use the fire" ;
    private static final String TXT_MELT        = "Melt ice for water" ;
    private static final String TXT_EXTINGUISH		= "Put out the fire";

    private static final int WIDTH		= 120;
    private static final int BTN_HEIGHT	= 20;
    private static final int GAP		= 2;

    public WndFireOptions(final Campfire campfire) {

        super();

        RedButton btnCook = new RedButton( TXT_COOK ) {
            @Override
            protected void onClick() {
                hide();
                Campfire.cook();
            }
        };
        btnCook.setRect( 0, 0, WIDTH, BTN_HEIGHT );
        add(btnCook);

        RedButton btnExtinguish = new RedButton( TXT_EXTINGUISH ) {
            @Override
            protected void onClick() {
                Extinguish( campfire );
            }
        };
        btnExtinguish.setRect( 0, btnCook.bottom() + GAP, WIDTH, BTN_HEIGHT );
        add( btnExtinguish );

        resize( WIDTH, (int)btnExtinguish.bottom() );
    }

    private void Extinguish( Campfire campfire ) {
        hide();

        campfire.flee();

    }
}

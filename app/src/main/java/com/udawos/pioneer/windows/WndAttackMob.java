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

import com.udawos.pioneer.items.wands.Wand;
import com.udawos.pioneer.scenes.GameScene;
import com.udawos.pioneer.ui.RedButton;
import com.udawos.pioneer.ui.Window;

public class WndAttackMob extends Window {

    private static final String TXT_HIGH        = "Headshot";

    private static final String TXT_MIDDLIN     = "Centre of Mass";

    private static final String  TXT_LOW        = "Aim Low";

    private static final int WIDTH		= 112;
    private static final int BTN_HEIGHT	= 20;
    private static final int GAP 		= 2;


    //Why don't u create a 'public Integer getTarget()' method in zapper class,
    // then give a reference of zapper class to window class,
    // then from window class inside shootHigh method u can call getTarget() method.

    public WndAttackMob() {
        super();

        RedButton btnScaleUp = new RedButton( TXT_HIGH ) {
            @Override
            protected void onClick() {
                super.onClick();
                GameScene.selectCell(Wand.zapperHigh);
                hide();
            }


        };
        btnScaleUp.setRect(0, 0, WIDTH, BTN_HEIGHT);
        add(btnScaleUp);




        RedButton btnSound = new RedButton( TXT_MIDDLIN ) {
            @Override
            protected void onClick() {
                super.onClick();
                GameScene.selectCell(Wand.zapperMid);
                hide();
            }
        };
        btnSound.setRect(0, btnScaleUp.bottom() + GAP, WIDTH, BTN_HEIGHT);
        add(btnSound);

        RedButton btnOrientation = new RedButton( TXT_LOW ) {
            @Override
            protected void onClick() {
                GameScene.selectCell(Wand.zapperLow);
                hide();
            }
        };
        btnOrientation.setRect( 0, btnSound.bottom() + GAP, WIDTH, BTN_HEIGHT );
        add(btnOrientation);

        resize(WIDTH, (int) btnOrientation.bottom());


    }


}

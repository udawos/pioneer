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

import com.udawos.noosa.BitmapText;
import com.udawos.noosa.Camera;
import com.udawos.noosa.audio.Sample;
import com.udawos.pioneer.Assets;
import com.udawos.pioneer.Pioneer;
import com.udawos.pioneer.levels.features.DropPods.DropPod;
import com.udawos.pioneer.scenes.PixelScene;
import com.udawos.pioneer.ui.RedButton;
import com.udawos.pioneer.ui.Window;
import com.udawos.pioneer.utils.GLog;

public class WndRequisition extends Window {

    private static final String TXT_TITLE	= "Confirm supply request.";

    private static final String TXT_SUPPLIES       = "Confirm.";

    private static final String TXT_NOTYET     = "Not yet.";

    private static final String TXT_INBOUND       = "SUPPLI3S.INB0UND.CHECK SOUTHEAST OF YOUR POSITION";

    private static final int WIDTH		= 130;
    private static final int BTN_HEIGHT	= 16;
    private static final int GAP 		= 2;

    public static boolean delivered;
    
    

    private BitmapText txtTitle;

    public WndRequisition() {
        super();

        txtTitle = PixelScene.createText(TXT_TITLE, 9);
        txtTitle.hardlight(Window.TITLE_COLOR);
        txtTitle.measure();
        txtTitle.x = PixelScene.align( PixelScene.uiCamera, (WIDTH - txtTitle.width()) / 2 );
        add(txtTitle);

        RedButton btnAmmo = new RedButton( TXT_SUPPLIES) {
            @Override
            protected void onClick() {
                if (delivered == false) {
                    Pioneer.soundFx();
                    Sample.INSTANCE.play(Assets.SND_CLICK);
                    delivered = true;
                    DropPod dropPod = new DropPod();
                    dropPod.landPos();
                    dropPod.land();
                    hide();
                    Camera.main.shake(1, 3.5f);
                }
                else GLog.n("Supply allotment exceeded. ");
            }
        };
        btnAmmo.setRect(0, 16, WIDTH, BTN_HEIGHT);
        add(btnAmmo);




        RedButton btnNotYet = new RedButton( TXT_NOTYET ) {
            @Override
            protected void onClick() {
                    hide();
            }

        };
        btnNotYet.setRect(0, btnAmmo.bottom() + GAP, WIDTH, BTN_HEIGHT);
        add(btnNotYet);



        resize(WIDTH, (int) btnNotYet.bottom());


    }




}

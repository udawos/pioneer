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
import com.udawos.pioneer.actors.Actor;
import com.udawos.pioneer.actors.mobs.Mob;
import com.udawos.pioneer.items.Heap;
import com.udawos.pioneer.levels.Level;
import com.udawos.pioneer.plants.Plant;
import com.udawos.pioneer.scenes.CellSelector;
import com.udawos.pioneer.scenes.GameScene;
import com.udawos.pioneer.ui.RedButton;
import com.udawos.pioneer.ui.Window;
import com.udawos.pioneer.utils.GLog;

public class WndInteract extends Window {


    private static final String TXT_INVESTIGATE     = "Look closely at...";

    private static final String  TXT_ACTION      = "Actions";

    private static final String TXT_HEAL        = "Heal";

    private static final String TXT_LOOKOUT       = "Gaze into the distance";

    private static final String  TXT_RIDE       = "Ride";

    private static final int WIDTH		= 112;
    private static final int BTN_HEIGHT	= 20;
    private static final int GAP 		= 2;

    public WndInteract() {
        super();





        RedButton btnInvestigate = new RedButton( TXT_INVESTIGATE ) {
            @Override
            protected void onClick() {
                GameScene.selectCell(informer);
                hide();
            }
        };
        btnInvestigate.setRect(0, 0 , WIDTH, BTN_HEIGHT);
        add(btnInvestigate);

        RedButton btnJump = new RedButton( TXT_ACTION ) {
            @Override
            protected void onClick() {
                GameScene.show( new WndAction());
                hide();
            }
        };
        btnJump.setRect( 0, btnInvestigate.bottom() + GAP, WIDTH, BTN_HEIGHT );
        add(btnJump);

        RedButton btnHeal = new RedButton( TXT_HEAL ) {
            @Override
            protected void onClick() {
                Pioneer.soundFx();
                Sample.INSTANCE.play(Assets.SND_CLICK);
                GameScene.show(new WndHeal());
                hide();
            }
        };
        btnHeal.setRect( 0, btnJump.bottom() + GAP, WIDTH, BTN_HEIGHT );
        add(btnHeal);

        RedButton btnLookout = new RedButton( TXT_LOOKOUT ) {
            @Override
            protected void onClick() {
                //Pioneer.soundFx();
               // Sample.INSTANCE.play(Assets.SND_CLICK);
                //GameScene.show(new WndLookout());
                GLog.i("You gaze far into the distance.");
                hide();
            }
        };
        btnLookout.setRect( 0, btnHeal.bottom() + GAP, WIDTH, BTN_HEIGHT );
        add(btnLookout);

        resize(WIDTH, (int) btnLookout.bottom());


    }

    private static CellSelector.Listener informer = new CellSelector.Listener() {
        @Override
        public void onSelect( Integer cell ) {

            if (cell == null) {
                return;
            }

            if (cell < 0 || cell > Level.LENGTH || (!Dungeon.level.visited[cell] && !Dungeon.level.mapped[cell])) {
                GameScene.show( new WndMessage( "You don't know what is there." ) ) ;
                return;
            }

            if (!Dungeon.visible[cell]) {
                GameScene.show( new WndInfoCell( cell ) );
                return;
            }

            if (cell == Dungeon.hero.pos) {
                GLog.i("Whoa, that's deep man.");
                return;
            }

            Mob mob = (Mob) Actor.findChar(cell);
            if (mob != null) {
                GameScene.show( new WndInfoMob( mob ) );
                return;
            }

            Heap heap = Dungeon.level.heaps.get( cell );
            if (heap != null) {
                if (heap.type == Heap.Type.FOR_SALE && heap.size() == 1 && heap.peek().price() > 0) {
                    GameScene.show( new WndTradeItem( heap, false ) );
                } else {
                    GameScene.show( new WndInfoItem( heap ) );
                }
                return;
            }

            Plant plant = Dungeon.level.plants.get( cell );
            if (plant != null) {
                GameScene.show( new WndInfoPlant( plant ) );
                return;
            }

            GameScene.show( new WndInfoCell( cell ) );
        }
        @Override
        public String prompt() {
            return "Select a cell to examine";
        }
    };

}

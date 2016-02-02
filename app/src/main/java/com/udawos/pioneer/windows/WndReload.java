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

import com.udawos.noosa.NinePatch;
import com.udawos.noosa.audio.Sample;
import com.udawos.noosa.ui.Component;
import com.udawos.pioneer.Assets;
import com.udawos.pioneer.Chrome;
import com.udawos.pioneer.items.Item;
import com.udawos.pioneer.items.weapon.Reloader;
import com.udawos.pioneer.scenes.GameScene;
import com.udawos.pioneer.ui.ItemSlot;
import com.udawos.pioneer.ui.RedButton;
import com.udawos.pioneer.ui.Window;

public class WndReload extends Window {

    private static final int BTN_SIZE	= 34;
    private static final float BTN_GAP	= 4;
    private static final int WIDTH		= 116;

    private ItemButton btnPressed;

    private ItemButton btnItem1;
    private ItemButton btnItem2;
    private ItemButton btnItem3;
    private RedButton btnReload;


    private static final String TXT_SELECT =
            "Reload your weapon";
    private static final String TXT_RELOAD =
            "Continue firing";

    public WndReload( ) {


        btnItem1 = new ItemButton() {
            @Override
            protected void onClick() {

                btnPressed = btnItem1;
                GameScene.selectItem(itemSelector, WndBag.Mode.POWDER, TXT_SELECT);


            }
        };
        btnItem1.setRect(3, 10 + BTN_GAP, BTN_SIZE, BTN_SIZE);
        add(btnItem1);

        btnItem2 = new ItemButton() {
            @Override
            protected void onClick() {

                btnPressed = btnItem2;
                GameScene.selectItem(itemSelector, WndBag.Mode.BULLET, TXT_SELECT);
            }
        };
        btnItem2.setRect(btnItem1.right() + BTN_GAP, btnItem1.top(), BTN_SIZE, BTN_SIZE);
        add(btnItem2);

        btnItem3 = new ItemButton() {
            @Override
            protected void onClick() {

                btnPressed = btnItem3;
                GameScene.selectItem(itemSelector, WndBag.Mode.CAP, TXT_SELECT);
            }
        };
        btnItem3.setRect( btnItem2.right() + BTN_GAP, btnItem2.top(), BTN_SIZE, BTN_SIZE);
        add(btnItem3);


        btnReload = new RedButton( TXT_RELOAD ) {


                    protected void onClick() {
                        Reloader.reload();
                        hide();

                    }
                };
        btnReload.enable( false );
        btnReload.setRect( 0, btnItem1.bottom() + BTN_GAP, WIDTH, 20 );
        add(btnReload);


        resize(WIDTH, (int) btnReload.bottom());
    }

    protected WndBag.Listener itemSelector = new WndBag.Listener() {
        @Override
        public void onSelect( Item item ) {
            if (item != null) {
                btnPressed.item( item );

                if (btnItem1.item != null && btnItem2.item != null && btnItem3.item != null) {
                    String result = Reloader.verify( btnItem1.item, btnItem2.item, btnItem3.item );
                    if (result != null) {
                        GameScene.show( new WndMessage( result ) );
                        btnReload.enable( false );
                    } else {
                        btnReload.enable( true );
                    }
                }
            }
        }
    };

    public static class ItemButton extends Component {

        protected NinePatch bg;
        protected ItemSlot slot;

        public Item item = null;


        @Override
        protected void createChildren() {
            super.createChildren();

            bg = Chrome.get( Chrome.Type.BUTTON );
            add( bg );

            slot = new ItemSlot() {
                @Override
                protected void onTouchDown() {
                    bg.brightness( 1.2f );
                    Sample.INSTANCE.play( Assets.SND_CLICK );
                };
                @Override
                protected void onTouchUp() {
                    bg.resetColor();
                }
                @Override
                protected void onClick() {
                    ItemButton.this.onClick();
                }
            };
            add( slot );
        }

        protected void onClick() {};

        @Override
        protected void layout() {
            super.layout();

            bg.x = x;
            bg.y = y;
            bg.size(width, height);

            slot.setRect(x + 2, y + 2, width - 4, height - 4);
        };

        public void item( Item item ) {
            slot.item( this.item = item );
        }
    }
}

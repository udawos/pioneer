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
import com.udawos.noosa.ui.Component;
import com.udawos.pioneer.Pioneer;
import com.udawos.pioneer.items.Item;
import com.udawos.pioneer.items.ammunition.Ammo;
import com.udawos.pioneer.scenes.GameScene;
import com.udawos.pioneer.scenes.PixelScene;
import com.udawos.pioneer.ui.ScrollPane;
import com.udawos.pioneer.ui.Window;
import com.udawos.pioneer.utils.Utils;

import java.util.ArrayList;

//Buy weapons and ammo from Barasook
public class WndBarasookTrade extends WndTabbed {

    private static final int WIDTH_P	= 112;
    private static final int HEIGHT_P	= 160;

    private static final int WIDTH_L	= 128;
    private static final int HEIGHT_L	= 128;

    private static final int ITEM_HEIGHT	= 18;

    private static final int TAB_WIDTH		= 50;

    private static final String TXT_POTIONS	= "Potions";
    private static final String TXT_AMMO	= "Ammo";
    private static final String TXT_TITLE	= "For Sale";

    private static final String TXT_BOUGHT	= "You've bought %s for %dg";

    private BitmapText txtTitle;
    private ScrollPane list;

    private ArrayList<ListItem> items = new ArrayList<WndBarasookTrade.ListItem>();

    private static boolean showPotions = true;

    public WndBarasookTrade() {

        super();

        if (Pioneer.landscape()) {
            resize( WIDTH_L, HEIGHT_L );
        } else {
            resize( WIDTH_P, HEIGHT_P );
        }

        txtTitle = PixelScene.createText( TXT_TITLE, 9 );
        txtTitle.hardlight(Window.TITLE_COLOR);
        txtTitle.measure();
        add( txtTitle );

        list = new ScrollPane( new Component() ) {
            @Override
            public void onClick( float x, float y ) {
                int size = items.size();
                for (int i=0; i < size; i++) {
                    if (items.get( i ).onClick( x, y )) {
                        hide();
                        break;
                    }
                }
            }
        };
        add(list);
        list.setRect(0, txtTitle.height(), width, height - txtTitle.height());

        boolean showPotions = WndBarasookTrade.showPotions;
        Tab[] tabs = {
                new LabeledTab( TXT_AMMO ) {
                    protected void select( boolean value ) {
                        super.select( value );
                        updateList();
                    };
                },

        };
        for (Tab tab : tabs) {
            tab.setSize(TAB_WIDTH, tabHeight());
            add( tab );
        }

        select(showPotions ? 0 : 1);
    }

    private void updateList() {

        txtTitle.text( Utils.format( TXT_TITLE,TXT_AMMO ) );
        txtTitle.measure();
        txtTitle.x = PixelScene.align( PixelScene.uiCamera, (width - txtTitle.width()) / 2 );

        items.clear();

        Component content = list.content();
        content.clear();
        list.scrollTo( 0, 0 );

        float pos = 0;
        for (Class<? extends Item> itemClass : Ammo.getKnown()) {
            ListItem item = new ListItem( itemClass );
            item.setRect(0, pos, width, ITEM_HEIGHT);
            content.add( item );
            items.add(item);

            pos += item.height();
        }

        for (Class<? extends Item> itemClass :  Ammo.getUnknown()) {
            ListItem item = new ListItem( itemClass );
            item.setRect( 0, pos, width, ITEM_HEIGHT );
            content.add( item );
            items.add( item );

            pos += item.height();
        }


        content.setSize( width, pos );
    }

    private static class ListItem extends Component {

        private Item item;
        ;
        private BitmapText label;

        public ListItem( Class<? extends Item> cl ) {
            super();

            try {
                item = cl.newInstance();
                label.text( item.trueName() );
                label.hardlight( 0xCCCCCC );

            } catch (Exception e) {
                // Do nothing
            }
        }

        @Override
        protected void createChildren() {

            label = PixelScene.createText( 8 );
            add(label);
        }

        @Override
        protected void layout() {

            label.x = 5;
            label.y = PixelScene.align( y + (height - label.baseLine()) / 2 );
        }


        public boolean onClick( float x, float y ) {
            if ( inside(x, y)) {
                GameScene.show(new WndListTradeConfirm(item, true));
                return true;
            } else {
                return false;
            }
        }


    }
}

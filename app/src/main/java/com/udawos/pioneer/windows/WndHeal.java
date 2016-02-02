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

import com.udawos.gltextures.SmartTexture;
import com.udawos.gltextures.TextureCache;
import com.udawos.noosa.BitmapText;
import com.udawos.noosa.Group;
import com.udawos.noosa.Image;
import com.udawos.noosa.TextureFilm;
import com.udawos.pioneer.Assets;
import com.udawos.pioneer.Dungeon;
import com.udawos.pioneer.actors.buffs.Buff;
import com.udawos.pioneer.actors.hero.Hero;
import com.udawos.pioneer.scenes.GameScene;
import com.udawos.pioneer.scenes.PixelScene;
import com.udawos.pioneer.ui.BuffIndicator;
import com.udawos.pioneer.ui.RedButton;
import com.udawos.pioneer.utils.Utils;
import com.udawos.pioneer.windows.Minigames.WndHealGame;

import java.util.Locale;

public class WndHeal extends WndTabbed {

    //Maybe partying will help?

    private static final String TXT_STATUS	    = "Status";
    private static final String TXT_CONDITIONS	= "Conditions";

    private static final String TXT_EXP		= "Experience";
    private static final String TXT_STR		= "Strength";
    private static final String TXT_HIT		= "Hit Modifier";
    private static final String TXT_BLOOD		= "Blood volume";
    private static final String TXT_THIRST		= "Thirst";
    private static final String TXT_HUNGER		= "Hunger";
    private static final String TXT_HEALTH	= "Health";


    private static final int WIDTH		= 120;
    private static final int TAB_WIDTH	= 50;

    private StatusTab status;
    private ConditionsTab conditions;

    private SmartTexture icons;
    private TextureFilm film;

    public WndHeal() {

        super();

        icons = TextureCache.get( Assets.BUFFS_LARGE );
        film = new TextureFilm( icons, 16, 16 );

        status = new StatusTab();
        add( status );

        conditions = new ConditionsTab();
        add( conditions );

        add( new LabeledTab( TXT_STATUS ) {
            protected void select( boolean value ) {
                super.select( value );
                status.visible = status.active = selected;
            };
        } );
        add( new LabeledTab( TXT_CONDITIONS ) {
            protected void select( boolean value ) {
                super.select( value );
                conditions.visible = conditions.active = selected;
            };
        } );
        for (Tab tab : tabs) {
            tab.setSize( TAB_WIDTH, tabHeight() );
        }

        resize( WIDTH, (int)Math.max(status.height(), conditions.height()) );

        select( 0 );
    }

    private class StatusTab extends Group {

        private static final String TXT_TITLE		= "Health Report";
        private static final String TXT_MEDICINES	= "Use Medicines";
        private static final String TXT_WOUNDS		= "Fix Wounds";

        private static final int GAP = 5;

        private float pos;

        public StatusTab() {

            Hero hero = Dungeon.hero;

            BitmapText title = PixelScene.createText(
                    Utils.format( TXT_TITLE, hero.lvl, hero.className() ).toUpperCase( Locale.ENGLISH ), 9 );
            title.hardlight( TITLE_COLOR );
            title.measure();
            add(title);

            RedButton btnMedicines = new RedButton( TXT_MEDICINES ) {
                @Override
                protected void onClick() {
                    GameScene.show(new WndBag(Dungeon.hero.belongings.backpack, null, WndBag.Mode.ALL, null));
                    hide();
                }
            };
            btnMedicines.setRect( 0, title.y + title.height(), btnMedicines.reqWidth() + 2, btnMedicines.reqHeight() + 2 );
            add(btnMedicines);

            RedButton btnWounds = new RedButton( TXT_WOUNDS ) {
                @Override
                protected void onClick() {
                    hide();
                    GameScene.show(new WndHealGame(Dungeon.hero.belongings.backpack, null, WndHealGame.Mode.ALL, null));
                }
            };
            btnWounds.setRect(
                    btnMedicines.right() + 1, btnMedicines.top(),
                    btnWounds.reqWidth() + 2, btnWounds.reqHeight() + 2);
            add( btnWounds );

            pos = btnMedicines.bottom() + GAP;

            statSlot( TXT_HEALTH, hero.HP + "/" + hero.HT );
            statSlot( TXT_STR, hero.STR() ); //remove this
            statSlot( TXT_HIT, hero.hitRoll() ); //remove this
            //statSlot (TXT_THIRST, thirst)
           // statSlot (TXT_HUNGER, hunger)
           // statSlot( TXT_EXP, hero.exp + "/" + hero.maxExp() ); //remove this


            pos += GAP;

        }



        private void statSlot( String label, String value ) {

            BitmapText txt = PixelScene.createText( label, 8 );
            txt.y = pos;
            add( txt );

            txt = PixelScene.createText( value, 8 );
            txt.measure();
            txt.x = PixelScene.align( WIDTH * 0.65f );
            txt.y = pos;
            add( txt );

            pos += GAP + txt.baseLine();
        }

        private void statSlot( String label, int value ) {
            statSlot( label, Integer.toString( value ) );
        }

        public float height() {
            return pos;
        }
    }

    private class ConditionsTab extends Group {

        private static final int GAP = 2;

        private float pos;

        public ConditionsTab() {
            for (Buff buff : Dungeon.hero.buffs()) {
                buffSlot( buff );
            }
        }

        private void buffSlot( Buff buff ) {

            int index = buff.icon();

            if (index != BuffIndicator.NONE) {

                Image icon = new Image( icons );
                icon.frame( film.get( index ) );
                icon.y = pos;
                add( icon );

                BitmapText txt = PixelScene.createText( buff.toString(), 8 );
                txt.x = icon.width + GAP;
                txt.y = pos + (int)(icon.height - txt.baseLine()) / 2;
                add( txt );

                pos += GAP + icon.height;
            }
        }

        public float height() {
            return pos;
        }
    }
}

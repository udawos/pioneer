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

import com.udawos.noosa.BitmapTextMultiline;
import com.udawos.pioneer.Dungeon;
import com.udawos.pioneer.actors.hero.Hero;
import com.udawos.pioneer.actors.mobs.npcs.Transmitter;
import com.udawos.pioneer.items.Item;
import com.udawos.pioneer.items.quest.DwarfToken;
import com.udawos.pioneer.scenes.PixelScene;
import com.udawos.pioneer.sprites.ItemSprite;
import com.udawos.pioneer.ui.RedButton;
import com.udawos.pioneer.ui.Window;
import com.udawos.pioneer.utils.GLog;
import com.udawos.pioneer.utils.Utils;

public class WndTransmitter extends Window {

    private static final String TXT_MESSAGE	=
            "Mission completion confirmed.\n";
    private static final String TXT_REWARD		= "Take the ring";

    private static final int WIDTH		= 120;
    private static final int BTN_HEIGHT	= 20;
    private static final int GAP		= 2;

    public WndTransmitter( final Transmitter transmitter, final DwarfToken tokens ) {

        super();

        IconTitle titlebar = new IconTitle();
        titlebar.icon( new ItemSprite( tokens.image(), null ) );
        titlebar.label( Utils.capitalize( tokens.name() ) );
        titlebar.setRect( 0, 0, WIDTH, 0 );
        add( titlebar );

        BitmapTextMultiline message = PixelScene.createMultiline( TXT_MESSAGE, 6 );
        message.maxWidth = WIDTH;
        message.measure();
        message.y = titlebar.bottom() + GAP;
        add( message );

        RedButton btnReward = new RedButton( TXT_REWARD ) {
            @Override
            protected void onClick() {
                takeReward( transmitter, tokens, Transmitter.Quest.reward );
            }
        };
        btnReward.setRect( 0, message.y + message.height() + GAP, WIDTH, BTN_HEIGHT );
        add( btnReward );

        resize( WIDTH, (int)btnReward.bottom() );
    }

    private void takeReward( Transmitter transmitter, DwarfToken tokens, Item reward ) {

        hide();

        tokens.detachAll( Dungeon.hero.belongings.backpack );

        reward.identify();
        if (reward.doPickUp( Dungeon.hero )) {
            GLog.i( Hero.TXT_YOU_NOW_HAVE, reward.name() );
        } else {
            Dungeon.level.drop( reward, transmitter.pos ).sprite.drop();
        }


        Transmitter.Quest.complete();
    }
}

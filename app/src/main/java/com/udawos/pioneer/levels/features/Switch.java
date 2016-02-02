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
package com.udawos.pioneer.levels.features;

import com.udawos.noosa.tweeners.AlphaTweener;
import com.udawos.pioneer.Dungeon;
import com.udawos.pioneer.actors.mobs.npcs.HolographicInterface;
import com.udawos.pioneer.levels.Level;
import com.udawos.pioneer.levels.Terrain;
import com.udawos.pioneer.scenes.GameScene;
import com.udawos.pioneer.utils.GLog;
import com.udawos.pioneer.windows.WndOptions;

public class Switch {

    private static final String TXT_SWITCH	= "Switch";
    private static final String TXT_YES		= "On";
    private static final String TXT_NO		= "Off";
    private static final String TXT_FLIP 	=
            "Flip the switch?";

    public static boolean flipConfirmed = false;

    public static void flip( final int pos ) {
        GameScene.show(
                new WndOptions( TXT_SWITCH, TXT_FLIP, TXT_YES, TXT_NO ) {
                    @Override
                    protected void onSelect( int index ) {
                        if (index == 0) {
                            flipConfirmed = true;
                            Level.set( pos, Terrain.SWITCH_ON );
                            GameScene.updateMap(pos);
                            Dungeon.observe();
                            GLog.i("The switch appears to be stuck in the ON position. It will be impossible to turn off.");
                        }
                       /* if (index == 1) {
                            flipConfirmed = false;
                            Level.set( pos, Terrain.SWITCH_OFF );
                            GameScene.updateMap( pos );
                            Dungeon.observe();
                        }*/
                        if (flipConfirmed = true) {
                            HolographicInterface holographicInterface = new HolographicInterface();
                            holographicInterface.HP = holographicInterface.HT;
                            holographicInterface.pos = 1553;

                            GameScene.add( holographicInterface );

                            holographicInterface.sprite.alpha( 0 );
                            holographicInterface.sprite.parent.add( new AlphaTweener( holographicInterface.sprite, 1, 0.15f ) );

                        }
                    };
                }
        );
    }

}

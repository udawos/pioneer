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
package com.udawos.pioneer.levels.traps;

import com.udawos.pioneer.Dungeon;
import com.udawos.pioneer.actors.Char;
import com.udawos.pioneer.levels.Level;
import com.udawos.pioneer.levels.Terrain;
import com.udawos.pioneer.scenes.GameScene;

import static com.udawos.pioneer.items.scrolls.ScrollOfMagicMapping.discover;

public class VantagePoint {

    public static void trigger( int pos, Char ch ) {

        int length = Level.LENGTH;
        int[] map = Dungeon.level.map;
        boolean[] visible = Dungeon.level.visited;
        boolean[] discoverable = Level.discoverable;


        for (int i=0; i < length; i++) {

            int terr = map[i];

            if (discoverable[i]) {

                visible[i] = true;
                if ((Terrain.flags[terr] & Terrain.SECRET) != 0) {

                    Level.set(i, Terrain.discover(terr));
                    GameScene.updateMap(i);

                    if (Dungeon.visible[i]) {
                        GameScene.discoverTile(i, terr);
                        discover( i );

                    }
                }
            }
        }
    }
}
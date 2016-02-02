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
package com.udawos.pioneer.actors.mobs.npcs;

import com.udawos.pioneer.Dungeon;
import com.udawos.pioneer.items.keys.KeyCard;
import com.udawos.pioneer.scenes.GameScene;
import com.udawos.pioneer.sprites.ElementalSprite;
import com.udawos.pioneer.utils.Utils;
import com.udawos.pioneer.windows.WndQuest;

public class HolographicInterface extends NPC {

    public static boolean hasCard = false;


    private static final String TXT_CORRECT	=
            " " +
                    "C c c c c c c c c c c c c c c c c c\n\n"+
                    "Correct card detected.";

    private static final String TXT_INPUT	=
                    "P p p p p p p p p p p p p p p p p p\n\n"+
                    "Present access card please.";


    {
        name = "Holographic Interface";
        spriteClass = ElementalSprite.class;
    }

    /*public void spawn() {

        HT = 10;
        defenseSkill = 1;
    }*/

    @Override
    protected boolean act() {

        sprite.turnTo(pos, Dungeon.hero.pos);
        spend(TICK);
        return true;
    }

    @Override
    public void damage(int dmg, Object src) {
        flee();
    }


    @Override
    public boolean reset() {
        return true;
    }

    @Override
    public String description() {
        return
                "It appears to be a projection of some kind. It radiates no heat, so that's good."+
                " You do not sense that this thing is dangerous in any way.";
    }



    @Override
    public void interact() {
        if (Dungeon.hero.belongings.getItem(KeyCard.class) == null) {
            tell(TXT_INPUT);
        } else {
            hasCard = true;
            tell(TXT_CORRECT);
            flee();
        }

    }

    private void tell( String format, Object...args ) {
        GameScene.show(
                new WndQuest(this, Utils.format(format, args)));
    }


    public void flee() {
        destroy();
        sprite.die();

    }


}





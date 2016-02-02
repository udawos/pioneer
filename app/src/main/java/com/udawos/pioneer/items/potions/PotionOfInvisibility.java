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
package com.udawos.pioneer.items.potions;

import com.udawos.noosa.audio.Sample;
import com.udawos.noosa.tweeners.AlphaTweener;
import com.udawos.pioneer.Assets;
import com.udawos.pioneer.Dungeon;
import com.udawos.pioneer.actors.Char;
import com.udawos.pioneer.actors.buffs.Buff;
import com.udawos.pioneer.actors.buffs.Invisibility;
import com.udawos.pioneer.actors.hero.Hero;
import com.udawos.pioneer.utils.GLog;

public class PotionOfInvisibility extends Potion {

    private static final float ALPHA	= 0.4f;

    {
        name = "Potion of Invisibility";
    }

    @Override
    protected void apply( Hero hero ) {
        setKnown();
        Buff.affect( hero, Invisibility.class, Invisibility.DURATION );
        GLog.i( "You see your hands turn invisible!" );
        Sample.INSTANCE.play( Assets.SND_MELD );
    }

    public static void change() {
        Hero hero = Dungeon.hero;
        Buff.affect(hero, Invisibility.class, Invisibility.DURATION);
        GLog.i("Look at me I'm a fucking bird or whatever");
    }

    @Override
    public String desc() {
        return
                "Drinking this potion will render you temporarily invisible. While invisible, " +
                        "enemies will be unable to see you. Attacking an enemy, as well as using a wand or a scroll " +
                        "before enemy's eyes, will dispel the effect.";
    }

    @Override
    public int price() {
        return isKnown() ? 40 * quantity : super.price();
    }

    public static void melt( Char ch ) {
        if (ch.sprite.parent != null) {
            ch.sprite.parent.add( new AlphaTweener( ch.sprite, ALPHA, 0.4f ) );
        } else {
            ch.sprite.alpha( ALPHA );
        }
    }
}

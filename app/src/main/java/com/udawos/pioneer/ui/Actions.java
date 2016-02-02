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
package com.udawos.pioneer.ui;

import com.udawos.pioneer.Dungeon;
import com.udawos.pioneer.actors.Char;
import com.udawos.pioneer.actors.mobs.Mob;
import com.udawos.pioneer.scenes.PixelScene;
import com.udawos.pioneer.sprites.CharSprite;
import com.udawos.pioneer.windows.WndInteract;

import java.util.ArrayList;

public class Actions extends Tag {

    private static final float ENABLED	= 1.0f;
    private static final float DISABLED	= 0.3f;


    private CharSprite sprite = null;

    private static Mob lastTarget = null;
    private ArrayList<Mob> candidates = new ArrayList<Mob>();

    public Actions() {
        super( DangerIndicator.COLOR );

        setSize( 12, 24 );
        visible( true );
        enable(true);
    }

    @Override
    protected void createChildren() {
        super.createChildren();
    }

    @Override
    protected void layout() {
        super.layout();

        if (sprite != null) {
            sprite.x = x + (width - sprite.width()) / 2;
            sprite.y = y + (height - sprite.height()) / 2;
            PixelScene.align(sprite);
        }
    }

    @Override
    public void update() {
        super.update();

        if (Dungeon.hero.isAlive()) {

            if (!Dungeon.hero.ready) {
                enable( false );
            }

        } else {
            visible( false );
            enable( false );
        }
    }

    private boolean enabled = true;
    private void enable( boolean value ) {
        enabled = value;
        if (sprite != null) {
            sprite.alpha( value ? ENABLED : DISABLED );
        }
    }

    private void visible( boolean value ) {
        bg.visible = value;
        if (sprite != null) {
            sprite.visible = value;
        }
    }


    protected void onClick() {
        parent.add(new WndInteract());
    }


    public static void target( Char target ) {
        lastTarget = (Mob)target;

        HealthIndicator.instance.target( target );
    }

}

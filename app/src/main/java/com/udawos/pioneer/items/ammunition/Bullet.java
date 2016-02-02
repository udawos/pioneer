
package com.udawos.pioneer.items.ammunition;

import com.udawos.pioneer.sprites.ItemSpriteSheet;

public class Bullet extends Ammo {


    {
        name = "Bullet";
        image = ItemSpriteSheet.DART;
    }

    public Bullet() {
        this( 1 );
    }

    public Bullet( int number ) {
        super();
        quantity = number;
    }

    @Override
    public boolean isIdentified() {
        return true;
    }

    @Override
    public String desc() {
        return
                "These metal balls damage the intended target by impact and penetration " +
                        "when shot from a firearm.";
    }

    @Override
    public int price() {
        return isKnown() ? 2 * quantity : super.price();
    }


}


package com.udawos.pioneer.items.ammunition;

public class PercussionCap extends Ammo {


    {
        name = "Percussion Cap";
    }

    public PercussionCap() {
        this( 1 );
    }

    public PercussionCap( int number ) {
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
                "These small metal cylinders contain a shock-sensitive explosive material  " +
                        "and will fire in any weather.";
    }

    @Override
    public int price() {
        return isKnown() ? 2 * quantity : super.price();
    }


}

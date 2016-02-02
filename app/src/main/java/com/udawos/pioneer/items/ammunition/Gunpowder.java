
package com.udawos.pioneer.items.ammunition;

public class Gunpowder extends Ammo {

    
    {
        name = "Gunpowder";
    }

    public Gunpowder() {
        this( 1 );
    }

    public Gunpowder( int number ) {
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
                "This powder is a chemical explosive " +
                        "that will propel your bullets to destroy your enemies.";
    }

    @Override
    public int price() {
        return isKnown() ? 2 * quantity : super.price();
    }


}

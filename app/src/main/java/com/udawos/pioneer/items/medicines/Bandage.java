package com.udawos.pioneer.items.medicines;

import com.udawos.pioneer.items.Item;
import com.udawos.pioneer.sprites.ItemSpriteSheet;

/**
 * Created by Jake on 25/01/2016.
 */
public class Bandage extends Item {

    {
        name = "Bandage";
        stackable = true;
        image = ItemSpriteSheet.BANDAGE;
    }

    public Bandage() {
        this( 1 );
    }

    public Bandage( int number ) {
        super();
        quantity = number;
    }

    @Override
    public String desc() {
        return
                "This bandage will stop your bleeding when correctly applied.";
    }

    @Override
    public boolean isIdentified() {
        return true;
    }

    @Override
    public int price() {
            return 2;
    }
}



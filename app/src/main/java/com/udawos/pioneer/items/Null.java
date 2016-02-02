package com.udawos.pioneer.items;

import com.udawos.pioneer.sprites.ItemSpriteSheet;

/**
 * Created by Jake on 25/01/2016.
 */
public class Null extends Item {
    //doesn't exist, does nothing
    //only putting it in to get an item button with no image

    {
        name = "null";
        image = ItemSpriteSheet.NULL;

    }

    @Override
    public boolean isUpgradable() {
        return false;
    }

    @Override
    public boolean isIdentified() {
        return true;
    }


}


package com.udawos.pioneer.items.keys;

import com.udawos.pioneer.sprites.ItemSpriteSheet;

public class KeyCard extends Key {

    {
        name = "key card";
        image = ItemSpriteSheet.TOKEN;
    }

    @Override
    public String info() {
        return
                "Key card.";
    }
}

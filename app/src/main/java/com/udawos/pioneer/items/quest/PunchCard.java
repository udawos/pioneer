
package com.udawos.pioneer.items.quest;

import com.udawos.pioneer.items.keys.Key;
import com.udawos.pioneer.sprites.ItemSpriteSheet;

public class PunchCard extends Key {

    {
        name = "punch card";
        image = ItemSpriteSheet.PUNCH_CARD;
    }

    @Override
    public String info() {
        return
                "A rectangular metal card with rectangular holes punched out of it.";
    }
}


package com.udawos.pioneer.items.quest;

import com.udawos.pioneer.items.Item;
import com.udawos.pioneer.sprites.ItemSpriteSheet;

public class UnspeakableScalp extends Item {

    {
        name = "giant rat skull";
        image = ItemSpriteSheet.DEWDROP;

        unique = true;
    }

    @Override
    public boolean isUpgradable() {
        return false;
    }

    @Override
    public boolean isIdentified() {
        return true;
    }

    @Override
    public String info() {
        return
                "The skin off the head of the lake monster.";
    }

    @Override
    public int price() {
        return 100;
    }
}

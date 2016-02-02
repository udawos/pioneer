package com.udawos.pioneer.items.medicines;

import com.udawos.pioneer.items.Item;
import com.udawos.pioneer.sprites.ItemSpriteSheet;

/**
 * Created by Jake on 25/01/2016.
 */
public class BleedingWound extends Item {

        {
            name = "bleeding wound";
            image = ItemSpriteSheet.BLEED_WOUND;
        }



        @Override
        public boolean isIdentified() {
            return true;
        }





}

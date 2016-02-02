package com.udawos.pioneer.items.shiny;

import com.udawos.pioneer.sprites.ItemSpriteSheet;

/**
 * Created by Jake on 08/01/2016.
 */
public class Bottle extends Shiny {


        {
            name = "Bottle";
            image = ItemSpriteSheet.BOTTLE;
            isBottle = true;
        }




        @Override
        public String desc() {
            return
                    "It's just a bottle, albeit one with a nice design. " +
                            "It's somewhat shiny.";
        }

        @Override
        public int price() {
            return  1 * quantity;
        }


    @Override
    public boolean isIdentified() {
        return true;
    }


    }



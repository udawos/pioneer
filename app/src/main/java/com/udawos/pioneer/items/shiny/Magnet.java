package com.udawos.pioneer.items.shiny;

/**
 * Created by Jake on 08/01/2016.
 */
public class Magnet extends Shiny {

    {
        name = "Magnet";
       // isMagnet = true;
    }



    @Override
    public String desc() {
        return
                "It's a weak magnet. The metal is shiny, but this" +
                        " item is profoundly useless.";
    }

    @Override
    public int price() {
        return  40 * quantity;
    }
}



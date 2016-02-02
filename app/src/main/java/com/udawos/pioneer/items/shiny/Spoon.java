package com.udawos.pioneer.items.shiny;

/**
 * Created by Jake on 08/01/2016.
 */
public class Spoon extends Shiny {

    {
        name = "Spoon";
        //isSpoon = true;
    }

    @Override
    public String desc() {
        return
                "A shiny brass spoon. An item that is completely useless," +
                        " at least to you.";
    }

    @Override
    public int price() {
        return  40 * quantity;
    }
}



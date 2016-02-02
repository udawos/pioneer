package com.udawos.pioneer.items.shiny;

/**
 * Created by Jake on 08/01/2016.
 */
public class Cup extends Shiny {


    {
        name = "Cup";
        //isCup = true;
    }



    @Override
    public String desc() {
        return
                "A shiny metal cup. The inside is completely rusted," +
                        " making this an exceptionally poor choice" +
                        " for drinking out of.";
    }

    @Override
    public int price() {
        return  40 * quantity;
    }
}



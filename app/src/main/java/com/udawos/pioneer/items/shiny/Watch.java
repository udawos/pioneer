package com.udawos.pioneer.items.shiny;

/**
 * Created by Jake on 08/01/2016.
 */
public class Watch extends Shiny {



    {
        name = "Watch";
        //isWatch = true;
    }



    @Override
    public String desc() {
        return
                "A broken pocket watch. It's shiny, but" +
                        " it doesn't even have a chain.";
    }

    @Override
    public int price() {
        return  40 * quantity;
    }
}



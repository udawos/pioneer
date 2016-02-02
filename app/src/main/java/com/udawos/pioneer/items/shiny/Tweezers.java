package com.udawos.pioneer.items.shiny;

/**
 * Created by Jake on 08/01/2016.
 */
public class Tweezers extends Shiny {



    {
        name = "Tweezers";
        //isTweezers = true;
    }


    @Override
    public String desc() {
        return
                "";
    }

    @Override
    public int price() {
        return  40 * quantity;
    }
}



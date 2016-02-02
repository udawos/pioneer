package com.udawos.pioneer.items.shiny;

/**
 * Created by Jake on 08/01/2016.
 */
public class Buckle extends Shiny {

    {
        name = "Belt buckle";
        //isBuckle = true;
    }


    @Override
    public String desc() {
        return
                "A shiny belt buckle. Not much use without a belt.";
    }

    @Override
    public int price() {
        return  40 * quantity;
    }
}



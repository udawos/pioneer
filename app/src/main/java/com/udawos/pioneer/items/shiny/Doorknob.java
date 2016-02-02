package com.udawos.pioneer.items.shiny;

/**
 * Created by Jake on 08/01/2016.
 */
public class Doorknob extends Shiny {


    {
        name = "Doorknob";
        //isDoorknob = true;
    }




    @Override
    public String desc() {
        return
                "A shiny doorknob. It has no attached mechanism and" +
                        " cannot be attached to a door. Why carry this with you?";
    }

    @Override
    public int price() {
        return  40 * quantity;
    }
}



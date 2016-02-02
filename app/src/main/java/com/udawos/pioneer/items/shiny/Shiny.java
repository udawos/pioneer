
package com.udawos.pioneer.items.shiny;

import com.udawos.pioneer.actors.hero.Hero;
import com.udawos.pioneer.items.Item;

import java.util.ArrayList;

public abstract class Shiny extends Item {

    private static final String TXT_BLINDED	= "You look at this object while blinded";

    public static final String AC_LOOK	= "LOOK";

    protected static final float TIME_TO_READ	= 1f;

    private static final Class<?>[] shinies = {
            Bottle.class, // gives Hand of Zeus (done)
            Buckle.class, // gives Winged Boots
            Cup.class, // gives Invisibility Cloak
            Doorknob.class, // gives Cowboy Revolver
            Magnet.class, // gives Fire Breath
            Spoon.class, // gives Spyglass
            Tweezers.class, // gives RocketPack
            Watch.class, //gives Bone Club (knocks enemies flying with melee hits)

    };



    @Override
    public ArrayList<String> actions( Hero hero ) {
        ArrayList<String> actions = super.actions( hero );
        actions.add( AC_LOOK );
        return actions;
    }





    @Override
    public boolean isUpgradable() {
        return false;
    }

    @Override
    public int price() {
        return 15 * quantity;
    }
}

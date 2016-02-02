package com.udawos.pioneer.items.drinks;

import com.udawos.pioneer.actors.buffs.Bleeding;
import com.udawos.pioneer.actors.buffs.Buff;
import com.udawos.pioneer.actors.buffs.Cripple;
import com.udawos.pioneer.actors.buffs.Poison;
import com.udawos.pioneer.actors.buffs.Weakness;
import com.udawos.pioneer.actors.hero.Hero;
import com.udawos.pioneer.sprites.ItemSpriteSheet;
import com.udawos.pioneer.utils.GLog;

import java.util.ArrayList;

/**
 * Created by Jake on 08/12/2015.
 */
public class Booze extends Water {

    public static final String AC_DRINK	= "DRINK";

    public String message = "Delicious booze.";

    {
        stackable = true;
        name = "booze";
        image = ItemSpriteSheet.POTION_AMBER;
    }

    @Override
    public ArrayList<String> actions( Hero hero ) {
        ArrayList<String> actions = super.actions( hero );
        actions.add( AC_DRINK );
        return actions;
    }

    @Override
    public void execute( Hero hero, String action ) {

        super.execute( hero, action );

        if (action.equals( AC_DRINK )) {


            GLog.i( "Refreshing!" );
            Buff.detach( hero, Poison.class );
            Buff.detach( hero, Cripple.class );
            Buff.detach( hero, Weakness.class );
            Buff.detach( hero, Bleeding.class );


        }
    }
}

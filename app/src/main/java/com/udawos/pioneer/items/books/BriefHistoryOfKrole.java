package com.udawos.pioneer.items.books;

import com.udawos.pioneer.Badges;
import com.udawos.pioneer.actors.buffs.Blindness;
import com.udawos.pioneer.actors.hero.Hero;
import com.udawos.pioneer.actors.hero.HeroSubClass;
import com.udawos.pioneer.items.Item;
import com.udawos.pioneer.items.TomeOfMastery;
import com.udawos.pioneer.scenes.GameScene;
import com.udawos.pioneer.sprites.ItemSpriteSheet;
import com.udawos.pioneer.utils.GLog;
import com.udawos.pioneer.windows.WndStory;

import java.util.ArrayList;

/**
 * Created by Jake on 03/12/2015.
 */
public class BriefHistoryOfKrole extends Item{

    private static final String TXT_BLINDED	= "You can't read while blinded";

    public static final float TIME_TO_READ = 1;

    public static final String AC_READ	= "READ";


    private static final String TEXT =

            "The Republic of Krole was founded by intrepid refugees, fleeing" +
                    " the expansion of the Federation only 45 years ago" +
                    " Due to the destruction of historical records at the dawn of the" +
                    " scourge that is the Federation, it is impossible to say what year" +
                    " that was by classical estimates. Krolian scholars estimate that it may have been" +
                    " somewhere around 1274." +
                    " Nestled in a barren, dry expanse of wasteland, the initial priorities of the republic" +
                    " were generating energy for heat and establishing a net of covert intelligence" +
                    " to monitor the expansion of the Federation." +
                    "\n" +
                    " " +
                    "This tome is heavily damaged and this is the only legible passage.";

    {
        stackable = false;
        name = "History of Our Republic";
        image = ItemSpriteSheet.MASTERY;

        unique = true;
    }

    @Override
    public ArrayList<String> actions( Hero hero ) {
        ArrayList<String> actions = super.actions( hero );
        actions.add( AC_READ );
        return actions;
    }

    @Override
    public void execute( Hero hero, String action ) {
        if (action.equals( AC_READ )) {

            if (hero.buff(Blindness.class) != null) {
                GLog.w(TXT_BLINDED);
                return;
            }

            curUser = hero;
            GameScene.show(new WndStory(TEXT));



        } else {

            super.execute( hero, action );

        }
    }

    @Override
    public boolean doPickUp( Hero hero ) {
        Badges.validateMastery();
        return super.doPickUp( hero );
    }

    @Override
    public boolean isUpgradable() {
        return false;
    }

    @Override
    public boolean isIdentified() {
        return true;
    }

    @Override
    public String info() {
        return
                "This worn leather book is not of the Federation," +
                        " and it certainly does not seem to belong here." +
                        " Who might have left it here?";
    }

    public void choose( HeroSubClass way ) {

        detach( curUser.belongings.backpack );

        curUser.spend( TomeOfMastery.TIME_TO_READ );
        curUser.busy();


    }
}

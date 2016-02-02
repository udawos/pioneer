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
public class DiaryOfTheExplorer extends Item{

    private static final String TXT_BLINDED	= "You can't read while blinded";

    public static final float TIME_TO_READ = 1;

    public static final String AC_READ	= "READ";


    private static final String TEXT =

            "This diary is extremely damaged. Only the last page is legible:" +
                    "\n" +
                    " September 9:" +
                    " \n" +
                    " My dirigible crashed into one of the front range mountains." +
                    " The barrier of light I noticed seems to be unable to affect" +
                    " matter suspended in the air, but the storm pushed the airship" +
                    " into the rocks. It is impossible to repair. However, I did" +
                    " notice a strange city in the tundra to the north. Maybe I " +
                    " will find my deliverance there." +
                    "\n" +
                    "\n" +
                    "The explorer's name is illegible.";

    {
        stackable = false;
        name = "Explorer's Diary";
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

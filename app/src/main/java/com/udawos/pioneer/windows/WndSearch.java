
/*package com.udawos.pioneer.windows;

import com.udawos.pioneer.Dungeon;
import com.udawos.pioneer.items.Generator;
import com.udawos.pioneer.ui.RedButton;
import com.udawos.pioneer.ui.Window;
import com.udawos.pioneer.utils.GLog;

//THE PROBLEM BEING that it will just give you an item forever when clicked on.
public class WndSearch extends Window {

    private static final String TXT_GRAB     = "Search";

    private static final String TXT_LEAVE    = "Leave";




    private static final int WIDTH		= 112;
    private static final int BTN_HEIGHT	= 20;
    private static final int GAP 		= 2;


    public WndSearch() {
        super();



        RedButton btnGrab = new RedButton( TXT_GRAB) {
            protected void onClick() {
                Dungeon.level.drop( Generator.random(Generator.Category.FOOD), Dungeon.hero.pos ).sprite.drop();
                GLog.p("You search the container.");
                hide();


            }
        };
        btnGrab.setRect(0, 0, WIDTH, BTN_HEIGHT);
        add(btnGrab);


        RedButton btnClimb = new RedButton( TXT_LEAVE ) {
            @Override
            protected void onClick() {
             hide();
            }
        };
        btnClimb.setRect( 0, btnGrab.bottom() + GAP, WIDTH, BTN_HEIGHT );
        add(btnClimb);


        resize(WIDTH, (int) btnClimb.bottom());


    }


}
*/
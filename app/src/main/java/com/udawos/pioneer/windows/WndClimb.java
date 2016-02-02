
package com.udawos.pioneer.windows;

import com.udawos.pioneer.Badges;
import com.udawos.pioneer.Dungeon;
import com.udawos.pioneer.Statistics;
import com.udawos.pioneer.items.potions.ActionOfLeaping;
import com.udawos.pioneer.scenes.GameScene;
import com.udawos.pioneer.ui.RedButton;
import com.udawos.pioneer.ui.Window;
import com.udawos.pioneer.utils.GLog;
import com.udawos.pioneer.windows.Minigames.WndClimbGame;

public class WndClimb extends Window {

    private static final String TXT_GRAB     = "Grab handhold";

    private static final String TXT_CLIMB    = "Climb";

    private static final String TXT_ONWARD    = "Onward!";



    private static final int WIDTH		= 112;
    private static final int BTN_HEIGHT	= 20;
    private static final int GAP 		= 2;

    public static boolean handholdsGrasped = false;

    public WndClimb() {
        super();



        RedButton btnGrab = new RedButton( TXT_GRAB) {
            @Override
            protected void onClick() {
                GameScene.show( new WndClimbGame( Dungeon.hero.belongings.backpack, null, WndClimbGame.Mode.ALL, null ) );

            }
        };
        btnGrab.setRect(0, 0, WIDTH, BTN_HEIGHT);
        add(btnGrab);



        //don't forget about ClimbFall in BounceAnomaly for check failures
        RedButton btnClimb = new RedButton( TXT_CLIMB ) {
            @Override
            protected void onClick() {
              if (!handholdsGrasped){
                    GLog.i("Find some handholds first");
                }
                if (handholdsGrasped) {
                    GLog.p(TXT_ONWARD);
                    ActionOfLeaping.Reach();
                    handholdsGrasped = false;
                    Statistics.timesClimbed++;
                    Badges.validateTimesClimbed();
                    hide();
                }

            }
        };
        btnClimb.setRect( 0, btnGrab.bottom() + GAP, WIDTH, BTN_HEIGHT );
        add(btnClimb);
        

        resize(WIDTH, (int) btnClimb.bottom());


    }


}

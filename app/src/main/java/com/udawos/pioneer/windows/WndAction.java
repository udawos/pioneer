
package com.udawos.pioneer.windows;

import com.udawos.pioneer.Dungeon;
import com.udawos.pioneer.items.potions.ActionOfCrawling;
import com.udawos.pioneer.items.potions.ActionOfGathering;
import com.udawos.pioneer.items.potions.ActionOfLeaping;
import com.udawos.pioneer.items.potions.ActionOfPeeking;
import com.udawos.pioneer.ui.RedButton;
import com.udawos.pioneer.ui.Window;

public class WndAction extends Window {

    private static final String TXT_JUMP     = "Jump";

    private static final String  TXT_SNEAK       = "Go Prone";

    private static final String TXT_GATHER        = "Gather materials";

    private static final String TXT_PEEK          = "Peek";

    private static final String TXT_DASH         =  "Dash";

    private static final String TXT_WAIT         =  "Wait";


    public static final String AC_MINE	= "MINE";

    public static final String AC_PEEK	= "BASH";


    private static final int WIDTH		= 112;
    private static final int BTN_HEIGHT	= 20;
    private static final int GAP 		= 2;


    public WndAction() {
        super();



        RedButton btnJump = new RedButton( TXT_JUMP) {
            @Override
            protected void onClick() {
                ActionOfLeaping.Leap();
                hide();
            }
        };
        btnJump.setRect(0, 0, WIDTH, BTN_HEIGHT);
        add(btnJump);



        RedButton btnProne = new RedButton( TXT_SNEAK ) {
            @Override
            protected void onClick() {
                ActionOfCrawling.change(Dungeon.hero);
                hide();
            }
        };
        btnProne.setRect( 0, btnJump.bottom() + GAP, WIDTH, BTN_HEIGHT );
        add(btnProne);

        RedButton btnGather = new RedButton( TXT_GATHER) {
            @Override
            protected void onClick() {
                ActionOfGathering.execute(Dungeon.hero, AC_MINE);
                hide();
            }
        };
        btnGather.setRect(0, btnProne.bottom() + GAP, WIDTH, BTN_HEIGHT);
        add(btnGather);

        RedButton btnPeek = new RedButton( TXT_PEEK) {
            @Override
            protected void onClick() {
                ActionOfPeeking peeking = new ActionOfPeeking();
                peeking.apply(Dungeon.hero);
                hide();
            }
        };
        btnPeek.setRect(0, btnGather.bottom() + GAP, WIDTH, BTN_HEIGHT);
        add(btnPeek);

        /*
        RedButton btnDash = new RedButton( TXT_DASH) {
            @Override
            protected void onClick() {
                ActionOfDashing dashing = new ActionOfDashing();
                dashing.apply(Dungeon.hero);
                hide();
            }
        };
        btnDash.setRect(0, btnPeek.bottom() + GAP, WIDTH, BTN_HEIGHT);
        add(btnDash);
        */

        RedButton btnWait = new RedButton( TXT_WAIT) {
            @Override
            protected void onClick() {
                Dungeon.hero.rest( false );
                hide();
            }
        };
        btnWait.setRect(0, btnPeek.bottom() + GAP, WIDTH, BTN_HEIGHT);
        add(btnWait);

        resize(WIDTH, (int) btnWait.bottom());


    }


}

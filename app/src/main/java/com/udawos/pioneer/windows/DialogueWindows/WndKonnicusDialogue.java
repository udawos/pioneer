
package com.udawos.pioneer.windows.DialogueWindows;

import com.udawos.pioneer.actors.mobs.npcs.Konnicus;
import com.udawos.pioneer.ui.RedButton;
import com.udawos.pioneer.ui.Window;

public class WndKonnicusDialogue extends Window {

    private static final String TXT_SELL        = "Sell";

    private static final String  TXT_TALK       = "Talk";

    private static final String TXT_LEAVE        = "Leave";



    private static final int WIDTH		= 112;
    private static final int BTN_HEIGHT	= 20;
    private static final int GAP 		= 2;

    private boolean spoken;
    private boolean spoken2;
    private boolean spoken3;



    public WndKonnicusDialogue() {
        super();



        RedButton btnBuy = new RedButton( TXT_SELL) {
            @Override
            protected void onClick() {
                Konnicus.sell();
                hide();
            }
        };
        btnBuy.setRect(0, 0, WIDTH, BTN_HEIGHT);
        add(btnBuy);



        RedButton btnTalk = new RedButton( TXT_TALK ) {
            @Override
            protected void onClick() {
                if (!spoken) {
                    Konnicus.KonnicusDialogue1();
                    spoken = true;
                }
                else if (!spoken2){
                    Konnicus.KonnicusDialogue2();
                    spoken2 = true;
                }
                else if (spoken & spoken2 & !spoken3){
                    Konnicus.KonnicusDialogue3();
                    spoken3 = true;
                }


            }
        };

        btnTalk.setRect( 0, btnBuy.bottom() + GAP, WIDTH, BTN_HEIGHT );
        add(btnTalk);

        RedButton btnLeave = new RedButton( TXT_LEAVE) {
            @Override
            protected void onClick() {
                hide();
            }
        };
        btnLeave.setRect(0, btnTalk.bottom() + GAP, WIDTH, BTN_HEIGHT);
        add(btnLeave);

        resize(WIDTH, (int) btnLeave.bottom());


    }


}

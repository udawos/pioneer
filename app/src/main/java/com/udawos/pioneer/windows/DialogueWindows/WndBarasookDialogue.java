
package com.udawos.pioneer.windows.DialogueWindows;

import com.udawos.pioneer.actors.mobs.npcs.Barasook;
import com.udawos.pioneer.scenes.GameScene;
import com.udawos.pioneer.ui.RedButton;
import com.udawos.pioneer.ui.Window;
import com.udawos.pioneer.windows.WndBarasookTrade;

public class WndBarasookDialogue extends Window {

    private static final String TXT_BUY         = "Buy";

    private static final String  TXT_TALK       = "Talk";

    private static final String TXT_LEAVE        = "Leave";



    private static final int WIDTH		= 112;
    private static final int BTN_HEIGHT	= 20;
    private static final int GAP 		= 2;

    private boolean spoken;
    private boolean spoken2;
    private boolean spoken3;
    private boolean spoken4;
    private boolean spoken5;
    private boolean spoken6;


    public WndBarasookDialogue() {
        super();



        RedButton btnBuy = new RedButton( TXT_BUY) {
            @Override
            protected void onClick() {
                GameScene.show(new WndBarasookTrade());
                hide();
            }
        };
        btnBuy.setRect(0, 0, WIDTH, BTN_HEIGHT);
        add(btnBuy);



        RedButton btnTalk = new RedButton( TXT_TALK ) {
            @Override
            protected void onClick() {
                if (!spoken) {
                    Barasook.BarasookDialogue1();
                    spoken = true;
                }
                else if (!spoken2){
                    Barasook.BarasookDialogue2();
                    spoken2 = true;
                }
                else if (spoken & spoken2 & !spoken3){
                    Barasook.BarasookDialogue3();
                    spoken3 = true;
                }
                else if (spoken & spoken2 & spoken3 & !spoken4) {
                    Barasook.BarasookDialogue4();
                    spoken4 = true;
                }
                else if (spoken & spoken2 & spoken3 & spoken4 & !spoken5) {
                    Barasook.BarasookDialogue5();
                    spoken5 = true;
                }
                else if (spoken & spoken2 & spoken3 & spoken4 & spoken5 & !spoken6) {
                    Barasook.BarasookDialogue6();
                    spoken6 = true;
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

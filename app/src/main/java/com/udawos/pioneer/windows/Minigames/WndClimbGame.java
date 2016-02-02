
package com.udawos.pioneer.windows.Minigames;

import com.udawos.noosa.BitmapText;
import com.udawos.noosa.ColorBlock;
import com.udawos.noosa.audio.Sample;
import com.udawos.pioneer.Assets;
import com.udawos.pioneer.Dungeon;
import com.udawos.pioneer.Pioneer;
import com.udawos.pioneer.items.Item;
import com.udawos.pioneer.items.Null;
import com.udawos.pioneer.items.bags.Bag;
import com.udawos.pioneer.levels.traps.BounceAnomaly;
import com.udawos.pioneer.scenes.PixelScene;
import com.udawos.pioneer.ui.ItemSlot;
import com.udawos.pioneer.utils.GLog;
import com.udawos.pioneer.utils.Utils;
import com.udawos.pioneer.windows.WndClimb;
import com.udawos.pioneer.windows.WndTabbed;
import com.udawos.utils.Random;


public class WndClimbGame extends WndTabbed {

    public static int handhold = 0;


    private static final String TXT_CLIMB    = "Press and hold all buttons";
    public static enum Mode {
        ALL,
        UNIDENTIFED,
        UPGRADEABLE,
        QUICKSLOT,
        FOR_SALE,
        COOK,
        WEAPON,
        ARMOR,
        ENCHANTABLE,
        WAND,
        SEED,
        POWDER,
        BULLET,
        CAP
    }

    protected static final int COLS_P	= 4;
    protected static final int COLS_L	= 6;

    protected static final int SLOT_SIZE	= 28;
    protected static final int SLOT_MARGIN	= 1;


    protected static final int TITLE_HEIGHT	= 12;

    private Listener listener;
    private WndClimbGame.Mode mode;
    private String title;

    private int nCols;
    private int nRows;

    protected int count;
    protected int col;
    protected int row;



    public WndClimbGame( Bag bag, Listener listener, Mode mode, String title ) {


        super();

        this.listener = listener;
        this.mode = mode;
        this.title = title;


        nCols = Pioneer.landscape() ? COLS_L : COLS_P;
        nRows = 5;

        int slotsWidth = SLOT_SIZE * nCols + SLOT_MARGIN * (nCols - 1);
        int slotsHeight = SLOT_SIZE * nRows + SLOT_MARGIN * (nRows - 1);

        BitmapText txtTitle = PixelScene.createText( title != null ? title : Utils.capitalize( TXT_CLIMB ), 9 );
        txtTitle.hardlight( TITLE_COLOR );
        txtTitle.measure();
        txtTitle.x = (int)(slotsWidth - txtTitle.width()) / 2;
        txtTitle.y = (int)(TITLE_HEIGHT - txtTitle.height()) / 2;
        add( txtTitle );

        placeItems(bag);

        resize( slotsWidth, slotsHeight + TITLE_HEIGHT );


    }

    protected void placeItems( Bag container ) {
    if (Dungeon.hero.climbSkill == 1){
            placeHandhold1(new Null());
            placeHandhold2(new Null());
            placeHandhold3(new Null());
            placeHandhold4(new Null());
        }
    if (Dungeon.hero.climbSkill == 2){
            placeHandhold1(new Null());
            placeHandhold2(new Null());
            placeHandhold3(new Null());
        }
    if (Dungeon.hero.climbSkill == 3){
            placeHandhold1(new Null());
            placeHandhold2(new Null());
        }
    if (Dungeon.hero.climbSkill >= 4){
            placeHandhold1(new Null());

        }
        // Number of handholds (based off Climbskill)
      //  while (count < 5) {
        //    placeHandhold();
       // }
    }


    protected void placeHandhold1(final Item item ) {

        int x = Random.Int(0,4) * (SLOT_SIZE + SLOT_MARGIN);
        int y = TITLE_HEIGHT + 1 * (SLOT_SIZE + SLOT_MARGIN);

        add( new ItemButton(item).setPos( x, y ) );

    }

    protected void placeHandhold2(final Item item ) {

        int x = Random.Int(0,4) * (SLOT_SIZE + SLOT_MARGIN);
        int y = TITLE_HEIGHT + 2 * (SLOT_SIZE + SLOT_MARGIN);

        add( new ItemButton(item).setPos( x, y ) );
    }

    protected void placeHandhold3(final Item item ) {

        int x = Random.Int(0,4) * (SLOT_SIZE + SLOT_MARGIN);
        int y = TITLE_HEIGHT + 3 * (SLOT_SIZE + SLOT_MARGIN);

        add( new ItemButton(item).setPos( x, y ) );

    }

    protected void placeHandhold4(final Item item ) {

        int x = Random.Int(0,4) * (SLOT_SIZE + SLOT_MARGIN);
        int y = TITLE_HEIGHT + 4 * (SLOT_SIZE + SLOT_MARGIN);

        add( new ItemButton(item).setPos( x, y ) );

    }


    @Override
    public void onMenuPressed() {
        if (listener == null) {
            hide();
        }
    }

    @Override
    public void onBackPressed() {
        if (listener != null) {
            listener.onSelect( null );
        }
        super.onBackPressed();
    }


    private class ItemButton extends ItemSlot {

        private static final int NORMAL		= 0xFF4A4D44;

        private ColorBlock bg;


        public ItemButton(Item item) {

            width = height = SLOT_SIZE;
        }

        @Override
        protected void createChildren() {
            bg = new ColorBlock( SLOT_SIZE, SLOT_SIZE, NORMAL );
            add( bg );

            super.createChildren();
        }

        @Override
        protected void layout() {
            bg.x = x;
            bg.y = y;



            super.layout();
        }


        @Override
        protected void onTouchDown() {
            bg.brightness( 1.5f );
            Sample.INSTANCE.play( Assets.SND_CLICK, 0.7f, 0.7f, 1.2f );
            handhold = handhold + 1;
        };

        protected void onTouchUp() {
            bg.brightness( 1.0f );
            handhold = 0;
        };

        @Override
        protected void onClick() {
            if (listener != null) {

                hide();


            }
        }

        @Override
        protected boolean onLongClick() {
            if (handhold == (5-Dungeon.hero.climbSkill)){
                handhold = 0;
                GLog.p("You gain a solid grasp on the rock.");
                WndClimb.handholdsGrasped = true;
                Dungeon.hero.spendAndNext(1f);

                hide();
            }
            else if (handhold != (5-Dungeon.hero.climbSkill)){
                handhold = 0;
                GLog.p("You fall.");
                BounceAnomaly.climbFall(Dungeon.hero.pos);
                Dungeon.hero.spendAndNext(2f);
                hide();
            }

            return true;
        }
    }

    public interface Listener {
        void onSelect( Item item );
    }
}

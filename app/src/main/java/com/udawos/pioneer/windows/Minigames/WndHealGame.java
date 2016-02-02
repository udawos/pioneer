
package com.udawos.pioneer.windows.Minigames;

import com.udawos.noosa.BitmapText;
import com.udawos.noosa.ColorBlock;
import com.udawos.noosa.audio.Sample;
import com.udawos.pioneer.Assets;
import com.udawos.pioneer.Dungeon;
import com.udawos.pioneer.Pioneer;
import com.udawos.pioneer.actors.buffs.Bleeding;
import com.udawos.pioneer.actors.buffs.Buff;
import com.udawos.pioneer.actors.buffs.Cripple;
import com.udawos.pioneer.items.Item;
import com.udawos.pioneer.items.Null;
import com.udawos.pioneer.items.bags.Bag;
import com.udawos.pioneer.items.keys.SkeletonKey;
import com.udawos.pioneer.items.medicines.Bandage;
import com.udawos.pioneer.items.medicines.BleedingWound;
import com.udawos.pioneer.levels.traps.BounceAnomaly;
import com.udawos.pioneer.scenes.PixelScene;
import com.udawos.pioneer.ui.ItemSlot;
import com.udawos.pioneer.utils.GLog;
import com.udawos.pioneer.utils.Utils;
import com.udawos.pioneer.windows.WndTabbed;
import com.udawos.utils.Random;


public class WndHealGame extends WndTabbed {

//Status:
    //bleeding fix implemented
    //To do:
    //crippled
    //individual bone breaks
    //

    private static final String TXT_HEAL  = "Heal Thyself";
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

    public static boolean bleeding;
    public static boolean crippled;

    protected static final int COLS_P	= 4;
    protected static final int COLS_L	= 6;

    protected static final int SLOT_SIZE	= 28;
    protected static final int SLOT_MARGIN	= 1;


    protected static final int TITLE_HEIGHT	= 12;

    private Listener listener;
    private WndHealGame.Mode mode;
    private String title;

    private int nCols;
    private int nRows;

    protected int count;
    protected int col;
    protected int row;



    public WndHealGame( Bag bag, Listener listener, Mode mode, String title ) {


        super();

        this.listener = listener;
        this.mode = mode;
        this.title = title;


        nCols = Pioneer.landscape() ? COLS_L : COLS_P;
        nRows = 5;

        int slotsWidth = SLOT_SIZE * nCols + SLOT_MARGIN * (nCols - 1);
        int slotsHeight = SLOT_SIZE * nRows + SLOT_MARGIN * (nRows - 1);

        BitmapText txtTitle = PixelScene.createText( title != null ? title : Utils.capitalize( TXT_HEAL ), 9 );
        txtTitle.hardlight( TITLE_COLOR );
        txtTitle.measure();
        txtTitle.x = (int)(slotsWidth - txtTitle.width()) / 2;
        txtTitle.y = (int)(TITLE_HEIGHT - txtTitle.height()) / 2;
        add( txtTitle );

        placeItems();

        resize( slotsWidth, slotsHeight + TITLE_HEIGHT );


    }

    protected void placeItems() {
            while (count < 20) {
                placeHandhold(new Null());
            }

        if (Dungeon.hero.buff( Bleeding.class ) != null) {
            bleeding = true;
            placeWound(new BleedingWound());
        }

        if (Dungeon.hero.buff( Cripple.class ) != null) {
            placeBrokenBone(new SkeletonKey());
        }
    }


    protected void placeHandhold(final Item item ) {

        int x = col * (SLOT_SIZE + SLOT_MARGIN);
        int y = TITLE_HEIGHT + row * (SLOT_SIZE + SLOT_MARGIN);

        add( new ItemButton( item ).setPos( x, y ) );
        if (++col >= nCols) {
            col = 0;
            row++;
        }

        count++;

    }

    protected void placeWound(final Item item ) {

        int x = Random.Int(1, 4) * (SLOT_SIZE + SLOT_MARGIN);
        int y = TITLE_HEIGHT + Random.Int(1,4) * (SLOT_SIZE + SLOT_MARGIN);

        add( new WoundButton(item).setPos( x, y ) );

    }

    protected void placeBrokenBone(final Item item ) {

        int x = Random.Int(1, 5) * (SLOT_SIZE + SLOT_MARGIN);
        int y = TITLE_HEIGHT + Random.Int(1,4) * (SLOT_SIZE + SLOT_MARGIN);

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

        private static final int NORMAL = 0xFF4A4D44;

        private ColorBlock bg;


        public ItemButton(Item item) {

            super(item);

            width = height = SLOT_SIZE;
        }

        @Override
        protected void createChildren() {
            bg = new ColorBlock(SLOT_SIZE, SLOT_SIZE, NORMAL);
            add(bg);

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
            bg.brightness(1.5f);
            Sample.INSTANCE.play(Assets.SND_CLICK, 0.7f, 0.7f, 1.2f);
        }

        ;

        protected void onTouchUp() {
            bg.brightness(1.0f);
        }

        ;

        @Override
        protected void onClick() {

            hide();


        }
    }

    private class WoundButton extends ItemSlot {

        private static final int NORMAL = 0xFF4A4D44;

        private ColorBlock bg;


        public WoundButton(Item item) {

            super(item);

            width = height = SLOT_SIZE;
        }

        @Override
        protected void createChildren() {
            bg = new ColorBlock(SLOT_SIZE, SLOT_SIZE, NORMAL);
            add(bg);

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
            bg.brightness(1.5f);
            Sample.INSTANCE.play(Assets.SND_CLICK, 0.7f, 0.7f, 1.2f);
        }

        ;

        protected void onTouchUp() {
            bg.brightness(1.0f);
        }

        ;

        @Override
        protected void onClick() {
            Item item = Dungeon.hero.belongings.getBandage(Bandage.class);
            if (bleeding) {
                if (item instanceof Bandage) {
                    item.detach(Dungeon.hero.belongings.backpack);
                    bleeding = false;
                    Buff.detach(Dungeon.hero, Bleeding.class);
                    GLog.p("You successfullly bandage your wound.");
                    hide();
                } else if (crippled) {
                    // handhold = 0;
                    GLog.p("You fall.");
                    BounceAnomaly.climbFall(Dungeon.hero.pos);
                    hide();
                }
            }
        }
    }


    public interface Listener {
        void onSelect( Item item );
    }
}

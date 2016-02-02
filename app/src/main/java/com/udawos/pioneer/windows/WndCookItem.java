
package com.udawos.pioneer.windows;

import com.udawos.noosa.BitmapTextMultiline;
import com.udawos.pioneer.Dungeon;
import com.udawos.pioneer.actors.hero.Hero;
import com.udawos.pioneer.actors.mobs.npcs.Campfire;
import com.udawos.pioneer.items.Item;
import com.udawos.pioneer.items.food.ChargrilledMeat;
import com.udawos.pioneer.items.food.Ice;
import com.udawos.pioneer.items.food.MysteryMeat;
import com.udawos.pioneer.items.drinks.Water;
import com.udawos.pioneer.scenes.PixelScene;
import com.udawos.pioneer.sprites.ItemSprite;
import com.udawos.pioneer.ui.ItemSlot;
import com.udawos.pioneer.ui.RedButton;
import com.udawos.pioneer.ui.Window;
import com.udawos.pioneer.utils.GLog;
import com.udawos.pioneer.utils.Utils;

public class WndCookItem extends Window {

    private static final float GAP		= 2;
    private static final int WIDTH		= 120;
    private static final int BTN_HEIGHT	= 16;

    private static final String TXT_COOK           = "Cook this";
    private static final String TXT_COOK_1         = "Cook this";

    private static final String TXT_DONT        = "Don't cook it";

    private static final String TXT_COOKED	= "You've cooked your %s";
    private static final String TXT_WHY	= "Why would you cook this? Why?"  ;



    private WndBag owner;

    public WndCookItem( final Item item, WndBag owner ) {

        super();

        this.owner = owner;

        float pos = createDescription( item, false );

        if (item.quantity() == 1) {

            RedButton btnCook = new RedButton( Utils.format( TXT_COOK, item.price() ) ) {
                @Override
                protected void onClick() {
                    cook(item);
                    hide();
                }
            };
            btnCook.setRect( 0, pos + GAP, WIDTH, BTN_HEIGHT );
            add( btnCook );

            pos = btnCook.bottom();

        } else {

            int priceAll= item.price();
            RedButton btnCook1 = new RedButton( Utils.format( TXT_COOK_1, priceAll / item.quantity() ) ) {
                @Override
                protected void onClick() {
                    cookOne(item);
                    hide();
                }
            };
            btnCook1.setRect( 0, pos + GAP, WIDTH, BTN_HEIGHT );
            add( btnCook1 );

            pos = btnCook1.bottom();

        }

        RedButton btnCancel = new RedButton( TXT_DONT ) {
            @Override
            protected void onClick() {
                hide();
            }
        };
        btnCancel.setRect( 0, pos + GAP, WIDTH, BTN_HEIGHT );
        add( btnCancel );

        resize( WIDTH, (int)btnCancel.bottom() );
    }


    @Override
    public void hide() {

        super.hide();

        if (owner != null) {
            owner.hide();
            Campfire.cook();
        }
    }

    private float createDescription( Item item, boolean forSale ) {

        IconTitle titlebar = new IconTitle();
        titlebar.icon( new ItemSprite( item.image(), item.glowing() ) );
        titlebar.label( forSale ?
                Utils.format( TXT_COOK, item.toString() ) :
                Utils.capitalize( item.toString() ) );
        titlebar.setRect( 0, 0, WIDTH, 0 );
        add( titlebar );

        if (item.levelKnown && item.level > 0) {
            titlebar.color( ItemSlot.UPGRADED );
        } else if (item.levelKnown && item.level < 0) {
            titlebar.color( ItemSlot.DEGRADED );
        }

        BitmapTextMultiline info = PixelScene.createMultiline( item.info(), 6 );
        info.maxWidth = WIDTH;
        info.measure();
        info.x = titlebar.left();
        info.y = titlebar.bottom() + GAP;
        add( info );

        return info.y + info.height();
    }

    private void cook( Item item ) {

        Hero hero = Dungeon.hero;

        if (item instanceof MysteryMeat) {

            item = item.detach(hero.belongings.backpack);
            ChargrilledMeat steak = new ChargrilledMeat();
            if (!steak.collect(hero.belongings.backpack)) {
                Dungeon.level.drop(steak, hero.pos).sprite.drop();
                GLog.i(TXT_COOKED, item.name());
            }
        }
            if (item instanceof Ice) {

                item = item.detach(hero.belongings.backpack);
                Water h2o = new Water();
                if (!h2o.collect(hero.belongings.backpack)) {
                    Dungeon.level.drop(h2o, hero.pos).sprite.drop();
                    GLog.i(TXT_COOKED, item.name());
                }

        }

    }

    private void cookOne( Item item ) {


            Hero hero = Dungeon.hero;

            if (item instanceof MysteryMeat) {

                item = item.detach(hero.belongings.backpack);
                ChargrilledMeat steak = new ChargrilledMeat();
                if (!steak.collect(hero.belongings.backpack)) {
                    Dungeon.level.drop(steak, hero.pos).sprite.drop();
                    GLog.i(TXT_COOKED, item.name());
                }
            }
             if (item instanceof Ice) {

                 item = item.detach(hero.belongings.backpack);
                 Water w = new Water();
                 if (!w.collect(hero.belongings.backpack)) {
                     Dungeon.level.drop(w, hero.pos).sprite.drop();
                     GLog.i(TXT_COOKED, item.name());
                    }

            } else {
                GLog.i(TXT_WHY, item.name());
            }
        }
    }






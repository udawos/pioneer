package com.udawos.pioneer.windows;

import com.udawos.noosa.BitmapTextMultiline;
import com.udawos.pioneer.Dungeon;
import com.udawos.pioneer.items.Item;
import com.udawos.pioneer.items.rings.RingOfHaggler;
import com.udawos.pioneer.scenes.PixelScene;
import com.udawos.pioneer.sprites.ItemSprite;
import com.udawos.pioneer.ui.ItemSlot;
import com.udawos.pioneer.ui.RedButton;
import com.udawos.pioneer.ui.Window;
import com.udawos.pioneer.utils.GLog;
import com.udawos.pioneer.utils.Utils;

/**
 * Created by Jake on 18/01/2016.
 */
public class WndListTradeConfirm extends Window {

    private static final float GAP		= 2;
    private static final int WIDTH		= 120;
    private static final int BTN_HEIGHT	= 16;

    private static final String TXT_SALE		= "FOR SALE: %s - %dg";
    private static final String TXT_BUY			= "Buy for %dg";
    private static final String TXT_SALE_10		= "FOR SALE: 10*%s - 10*%dg";
    private static final String TXT_BUY_10		= "Buy 10 for %dg";
    private static final String TXT_SELL		= "Sell for %dg";
    private static final String TXT_SELL_1		= "Sell 1 for %dg";
    private static final String TXT_SELL_ALL	= "Sell all for %dg";
    private static final String TXT_CANCEL		= "Never mind";

    private static final String TXT_SOLD	= "You've sold your %s for %dg";
    private static final String TXT_BOUGHT	= "You've bought %s for %dg";

    private WndBag owner;

    public WndListTradeConfirm( final Item item, boolean canBuy ) {

        super();

        float pos = createDescription( item, true );

        int price = price( item );

        if (canBuy) {

            RedButton btnBuy = new RedButton( Utils.format(TXT_BUY, price) ) {
                @Override
                protected void onClick() {
                    hide();
                    buy(item);

                }
            };
            btnBuy.setRect( 0, pos + GAP, WIDTH, BTN_HEIGHT );
            btnBuy.enable( price <= Dungeon.gold );
            add( btnBuy );


            RedButton btnBuy10 = new RedButton( Utils.format(TXT_BUY_10, price * 10) ) {
                @Override
                protected void onClick() {
                    hide();
                    buy10(item);

                }
            };
            btnBuy10.setRect(0, btnBuy.bottom() + GAP, WIDTH, BTN_HEIGHT);
            btnBuy.enable(price <= Dungeon.gold);
            add( btnBuy10 );

            RedButton btnCancel = new RedButton( TXT_CANCEL ) {
                @Override
                protected void onClick() {
                    hide();
                }
            };
            btnCancel.setRect( 0, btnBuy10.bottom() + GAP, WIDTH, BTN_HEIGHT );
            add( btnCancel );

            resize( WIDTH, (int)btnCancel.bottom() );

        } else {

            resize(WIDTH, (int) pos);

        }
    }

    @Override
    public void hide() {

        super.hide();

        if (owner != null) {
            owner.hide();
        }
    }

    private float createDescription( Item item, boolean forSale ) {

        IconTitle titlebar = new IconTitle();
        titlebar.icon( new ItemSprite( item.image(), item.glowing() ) );
        titlebar.label( forSale ?
                Utils.format( TXT_SALE, item.toString(), price( item ) ) :
                Utils.capitalize( item.toString() ) );
        titlebar.setRect( 0, 0, WIDTH, 0 );
        add( titlebar );

        if (item.levelKnown && item.level > 0) {
            titlebar.color( ItemSlot.UPGRADED );
        } else if (item.levelKnown && item.level < 0) {
            titlebar.color( ItemSlot.DEGRADED );
        }

        BitmapTextMultiline info = PixelScene.createMultiline(item.info(), 6);
        info.maxWidth = WIDTH;
        info.measure();
        info.x = titlebar.left();
        info.y = titlebar.bottom() + GAP;
        add(info);

        return info.y + info.height();
    }

    private int price( Item item ) {

        int price = item.price();
        if (Dungeon.hero.buff( RingOfHaggler.Haggling.class ) != null && price >= 2) {
            price /= 2;
        }
        return price;
    }

    private void buy(final Item item) {
        int price = price(item);
        if ((price <= Dungeon.gold)) {
            Dungeon.level.drop((item), Dungeon.hero.pos).sprite.drop();
            Dungeon.gold -= price;

            GLog.i(TXT_BOUGHT, item.name(), price);
        }
    }

    private void buy10(final Item item) {
        item.quantity(10);
        int price = price(item);
        if ((price <= Dungeon.gold)) {
            Dungeon.level.drop((item), Dungeon.hero.pos).sprite.drop();
            Dungeon.gold -= price;

            GLog.i(TXT_BOUGHT, item.name(), price);
        }
    }


}

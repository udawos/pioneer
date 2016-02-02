
    package com.udawos.pioneer.windows;

    import com.udawos.noosa.BitmapTextMultiline;
import com.udawos.pioneer.Dungeon;
import com.udawos.pioneer.actors.hero.Hero;
import com.udawos.pioneer.items.EquipableItem;
import com.udawos.pioneer.items.Gold;
import com.udawos.pioneer.items.Heap;
import com.udawos.pioneer.items.Item;
import com.udawos.pioneer.items.rings.RingOfHaggler;
import com.udawos.pioneer.items.wands.HandOfZeus;
import com.udawos.pioneer.scenes.PixelScene;
import com.udawos.pioneer.sprites.ItemSprite;
import com.udawos.pioneer.ui.ItemSlot;
import com.udawos.pioneer.ui.RedButton;
import com.udawos.pioneer.ui.Window;
import com.udawos.pioneer.utils.GLog;
import com.udawos.pioneer.utils.Utils;

    public class WndShamanTrade extends Window {

        private static final float GAP		= 2;
        private static final int WIDTH		= 120;
        private static final int BTN_HEIGHT	= 16;

        private static final String TXT_SALE		= "FOR SALE: %s - %dg";
        private static final String TXT_BUY			= "Buy for %dg";
        private static final String TXT_SELL		= "Sell for %dg";
        private static final String TXT_SELL_1		= "Sell 1 for %dg";
        private static final String TXT_SELL_ALL	= "Sell all for %dg";
        private static final String TXT_CANCEL		= "Never mind";
        private static final String TXT_TRADE       = "Trade";

        private static final String TXT_SOLD	= "You've sold your %s for %dg";
        private static final String TXT_TRADED	= "You've traded the %s!";
        private static final String TXT_BOUGHT	= "You've bought %s for %dg";

        private WndBag owner;


        public WndShamanTrade( final Item item, WndBag owner ) {

            super();

            this.owner = owner;

            float pos = createDescription(item, false);

            if (item.isBottle){
                RedButton btnTrade = new RedButton( Utils.format(TXT_TRADE, item.price()) ) {
                    @Override
                    protected void onClick() {
                        sellBottle(item);
                        hide();
                    }
                };
                btnTrade.setRect( 0, pos + GAP, WIDTH, BTN_HEIGHT );
                add( btnTrade );

                pos = btnTrade.bottom();
            }
            else
            if (item.quantity() == 1) {

                RedButton btnSell = new RedButton( Utils.format(TXT_SELL, item.price()) ) {
                    @Override
                    protected void onClick() {
                        sell( item );
                        hide();
                    }
                };
                btnSell.setRect( 0, pos + GAP, WIDTH, BTN_HEIGHT );
                add( btnSell );

                pos = btnSell.bottom();

            } else {

                int priceAll= item.price();
                RedButton btnSell1 = new RedButton( Utils.format( TXT_SELL_1, priceAll / item.quantity() ) ) {
                    @Override
                    protected void onClick() {
                        sellOne( item );
                        hide();
                    }
                };
                btnSell1.setRect( 0, pos + GAP, WIDTH, BTN_HEIGHT );
                add( btnSell1 );
                RedButton btnSellAll = new RedButton( Utils.format( TXT_SELL_ALL, priceAll ) ) {
                    @Override
                    protected void onClick() {
                        if (item.isBottle){
                            sellBottle(item);
                        }else
                        sell( item );
                        hide();
                    }
                };
                btnSellAll.setRect( 0, btnSell1.bottom() + GAP, WIDTH, BTN_HEIGHT );
                add( btnSellAll );

                pos = btnSellAll.bottom();

            }

            RedButton btnCancel = new RedButton( TXT_CANCEL ) {
                @Override
                protected void onClick() {
                    hide();
                }
            };
            btnCancel.setRect( 0, pos + GAP, WIDTH, BTN_HEIGHT );
            add( btnCancel );

            resize( WIDTH, (int)btnCancel.bottom() );
        }

        public WndShamanTrade( final Heap heap, boolean canBuy ) {

            super();

            Item item = heap.peek();

            float pos = createDescription( item, true );

            int price = price( item );

            if (canBuy) {

                RedButton btnBuy = new RedButton( Utils.format( TXT_BUY, price ) ) {
                    @Override
                    protected void onClick() {
                        hide();
                        buy( heap );
                    }
                };
                btnBuy.setRect( 0, pos + GAP, WIDTH, BTN_HEIGHT );
                btnBuy.enable( price <= Dungeon.gold );
                add( btnBuy );

                RedButton btnCancel = new RedButton( TXT_CANCEL ) {
                    @Override
                    protected void onClick() {
                        hide();
                    }
                };
                btnCancel.setRect( 0, btnBuy.bottom() + GAP, WIDTH, BTN_HEIGHT );
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

        private void sell( Item item ) {

            Hero hero = Dungeon.hero;

            if (item.isEquipped( hero ) && !((EquipableItem)item).doUnequip( hero, false )) {
                return;
            }
            item.detachAll( hero.belongings.backpack );

            int price = item.price();

            new Gold( price ).doPickUp( hero );
            GLog.i(TXT_SOLD, item.name(), price);
        }

        //mucking about trying to trade shiny stuff for items
        private void sellBottle( Item item ) {

                Hero hero = Dungeon.hero;

                item = item.detach( hero.belongings.backpack );

                new HandOfZeus().identify().doPickUp( hero );
                GLog.n( TXT_TRADED, item.name());

        }

        private void sellOne( Item item ) {

            if (item.quantity() <= 1) {
                sell( item );
            } else {

                Hero hero = Dungeon.hero;

                item = item.detach( hero.belongings.backpack );
                int price = item.price();

                new Gold( price ).doPickUp( hero );
                GLog.i( TXT_SOLD, item.name(), price );
            }
        }

        private int price( Item item ) {

            int price = item.price() * 5 * (Dungeon.depth / 5 + 1);
            if (Dungeon.hero.buff( RingOfHaggler.Haggling.class ) != null && price >= 2) {
                price /= 2;
            }
            return price;
        }

        private void buy( Heap heap ) {

            Hero hero = Dungeon.hero;
            Item item = heap.pickUp();

            int price = price( item );
            Dungeon.gold -= price;

            GLog.i(TXT_BOUGHT, item.name(), price);

            if (!item.doPickUp( hero )) {
                Dungeon.level.drop( item, heap.pos ).sprite.drop();
            }
        }


    }



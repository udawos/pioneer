package com.udawos.pioneer.actors.mobs.npcs;

import com.udawos.pioneer.Dungeon;
import com.udawos.pioneer.actors.Actor;
import com.udawos.pioneer.actors.buffs.Buff;
import com.udawos.pioneer.actors.mobs.AggroKonnicus;
import com.udawos.pioneer.effects.CellEmitter;
import com.udawos.pioneer.effects.particles.ElmoParticle;
import com.udawos.pioneer.items.Heap;
import com.udawos.pioneer.items.Item;
import com.udawos.pioneer.items.quest.UnspeakableScalp;
import com.udawos.pioneer.levels.E048MountainLevel;
import com.udawos.pioneer.scenes.GameScene;
import com.udawos.pioneer.sprites.KonnicusSprite;
import com.udawos.pioneer.utils.Utils;
import com.udawos.pioneer.windows.DialogueWindows.WndKonnicusDialogue;
import com.udawos.pioneer.windows.WndBag;
import com.udawos.pioneer.windows.WndOptions;
import com.udawos.pioneer.windows.WndQuest;
import com.udawos.pioneer.windows.WndTradeItem;

public class Konnicus extends NPC {

    //Konnicus, the fur trader

    private static final String TXT_NAME 	        = "Konnicus";

    private static final String TXT_INTRO 	        = "Well, another friendly soul crosses the barrier and makes its way to my home.";

    private static final String TXT_GREETING 	    = " Sell their skins to me! ";

    private static final String TXT_DIALOGUE1		= "Sell their skins to me! But not their flesh, no..their flesh is nasty!";

    private static final String TXT_DIALOGUE2		= "Very nice here, yes? Once much water here, but now... "+
                                                    "one could easily die of thirst if they didn't know where to find water! And She owns the Lake, " +
                                                     "so that doesn't help either...";

    private static final String TXT_DIALOGUE3		= "I ate a bird once. It was fowl.";

    private static final String TXT_SCALP		    = "Did you...is that..give me that scalp! I'll give you something precious in return! ";
    private static final String TXT_ANSWERY		    = "Yes";
    private static final String TXT_ANSWERN		    = "No";
    private static final String TXT_THANKS		    = "Purrrrrfect! ";
    private static final String TXT_TOOBAD		    = "So be it. ";


    {
        name = "Konnicus";
        spriteClass = KonnicusSprite.class;

    }

    private static final String TXT_HEY	= "I sssseeeeee you.";

    private boolean seenBefore = false;

    private boolean aggroSpawned = false;

    @Override
    protected boolean act() {
        if (Dungeon.visible[pos])
            if (!seenBefore) {
                yell( Utils.format(TXT_HEY, Dungeon.hero.className()) );
                seenBefore = true;

        } else if (seenBefore) {

        }

        throwItem();

        sprite.turnTo(pos, Dungeon.hero.pos);
        spend(TICK);
        return true;
    }

    @Override
    public void damage( int dmg, Object src ) {
        yell("I'm gonna wear you like a coat! Like a coat!");
        flee();
    }

    @Override
    public void add( Buff buff ) {
        flee();
    }

    protected void flee() {
        for (Heap heap: Dungeon.level.heaps.values()) {
            if (heap.type == Heap.Type.FOR_SALE) {
                CellEmitter.get(heap.pos).burst( ElmoParticle.FACTORY, 4 );
                heap.destroy();
            }
        }

        destroy();

        sprite.killAndErase();
        CellEmitter.get( pos ).burst(ElmoParticle.FACTORY, 6);
        spawnAggro();
    }

    @Override
    public boolean reset() {
        return true;
    }

    @Override
    public String description() {
        return
                "You aren't sure what's under this pile of wolf fur topped by an ushanka. " +
                        "Its keen yellow eyes stare out at you, blinking out of sequence " +
                        "every so often. " +
                        "It doesn't look harmful and watches you keenly. ";
    }

    public static void spawn( E048MountainLevel level ) {
        if (Dungeon.depth == 48) {

            Konnicus npc = new Konnicus();
            do {
                npc.pos = 1475;
            } while (npc.pos == -1 || level.heaps.get( npc.pos ) != null);
            level.mobs.add(npc);
            Actor.occupyCell(npc);

        }
    }

    public void spawnAggro( ) {
        if (!aggroSpawned) {
            AggroKonnicus madFur = new AggroKonnicus();
            madFur.pos = 1475;
            madFur.state = madFur.HUNTING;

            GameScene.add(madFur);
            aggroSpawned = true;
        }
    }

    public void talk() { GameScene.show(new WndKonnicusDialogue()); }

    public static WndBag sell() {
        return GameScene.selectItem( itemSelector, WndBag.Mode.FUR, "Select an item to sell" );
    }

    private static WndBag.Listener itemSelector = new WndBag.Listener() {
        @Override
        public void onSelect( Item item ) {
            if (item != null) {
                WndBag parentWnd = sell();
                GameScene.show( new WndTradeItem( item, parentWnd ) );
            }
        }
    };


    @Override
    public void interact() {

        final Item item = Dungeon.hero.belongings.getItem(UnspeakableScalp.class);

        if (Dungeon.hero.belongings.getItem(UnspeakableScalp.class) != null){
            GameScene.show((new WndOptions(TXT_NAME, TXT_SCALP, TXT_ANSWERY, TXT_ANSWERN) {
                protected void onSelect(int index) {
                    if (index == 0) {
                        Dungeon.hero.resume();
                        tell(TXT_THANKS);
                        item.detach(Dungeon.hero.belongings.backpack);
                    }
                    else if (index == 1) {
                        Dungeon.hero.resume();
                        tell(TXT_TOOBAD);
                    }
                }
            }
            ));
        }

        else if (!seenBefore) {
            tell(TXT_INTRO);
            seenBefore = true;
        }
        else if (seenBefore){
            tell(TXT_GREETING);
            talk();
        }

    }

    public static void KonnicusDialogue1(){
        tell(TXT_DIALOGUE1);
    }
    public static void KonnicusDialogue2(){
        tell(TXT_DIALOGUE2);
    }
    public static void KonnicusDialogue3(){ tell(TXT_DIALOGUE3);
    }



    private static void tell( String format, Object...args ) {
        GameScene.show(
                new WndQuest(new Konnicus(), Utils.format(format, args)));
    }



}







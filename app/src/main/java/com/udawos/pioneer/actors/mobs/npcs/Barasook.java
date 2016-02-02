package com.udawos.pioneer.actors.mobs.npcs;

import com.udawos.pioneer.Dungeon;
import com.udawos.pioneer.actors.Actor;
import com.udawos.pioneer.actors.buffs.Buff;
import com.udawos.pioneer.actors.mobs.AggroBarasook;
import com.udawos.pioneer.effects.CellEmitter;
import com.udawos.pioneer.effects.particles.ElmoParticle;
import com.udawos.pioneer.items.Heap;
import com.udawos.pioneer.items.Item;
import com.udawos.pioneer.items.quest.PunchCard;
import com.udawos.pioneer.levels.E050MountainLevel;
import com.udawos.pioneer.scenes.GameScene;
import com.udawos.pioneer.sprites.BlacksmithSprite;
import com.udawos.pioneer.utils.Utils;
import com.udawos.pioneer.windows.DialogueWindows.WndBarasookDialogue;
import com.udawos.pioneer.windows.WndOptions;
import com.udawos.pioneer.windows.WndQuest;

public class Barasook extends NPC {

    private static final String TXT_NAME 	= "Barasook";

    private static final String TXT_INTRO 	=
            "Hmm, you seem reasonable enough.";



    private static final String TXT_DIALOGUE1		= "Hmm...I will trade you ammunition for gold, precious metals or stones.";


    private static final String TXT_DIALOGUE2		= "Hmm...This used to be an isolated enough area. There was not much here other than ruins.\n" +
            "Then that cult showed up, messed around with the ruins and we wound up isolated from the \n" +
            "world. Can't say it rustled me much.";

    private static final String TXT_DIALOGUE3		= "Hmm? I am a badger man. There are not many of us left these days." +
            " No offense, but your federation and its policies did not help much with that. Reproductive prohibitions, I say...";

    private static final String TXT_DIALOGUE4		= "Hmm...I do not wish you any ill, of course. " +
            "It's just that it will be difficult for us to be friends.";

    private static final String TXT_DIALOGUE5		= "Hmm...if you find any flux stone consider giving it to me. I need it for joining metals.";

    private static final String TXT_DIALOGUE6		= "Hmm... Hmmmmm........ Eh?.";

    private static final String TXT_PUNCHCARD		= "Heh, that's an interesting card you have there. Would you let me have that?";
    private static final String TXT_ANSWERY		    = "Yes";
    private static final String TXT_ANSWERN		    = "No";
    private static final String TXT_THANKS		    = "Amazing! Come back later, I think this will help us both out. ";
    private static final String TXT_TOOBAD		    = "I...you don't even know what it is! You can't possibly find a use for it! You slag! ";



    private boolean seenBefore = false;

    private boolean aggroSpawned = false;


    {
        name = "barasook";
        spriteClass = BlacksmithSprite.class;

    }

    @Override
    protected boolean act() {

        throwItem();

        sprite.turnTo(pos, Dungeon.hero.pos);
        spend(TICK);
        return true;
    }

    @Override
    public void damage( int dmg, Object src ) {
        yell("It is time for you to shuffle off from this mortal coil.");
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
                "This guy looks like a badger motherfucker.";
    }

    public static void spawn( E050MountainLevel level ) {
        if (Dungeon.depth == 50) {

            Barasook npc = new Barasook();
            do {
                npc.pos = 1760;
            } while (npc.pos == -1);
            level.mobs.add(npc);
            Actor.occupyCell(npc);

        }
    }

    public void spawnAggro( ) {
        if (!aggroSpawned) {
            AggroBarasook madBadger = new AggroBarasook();
            madBadger.pos = 1760;
            madBadger.state = madBadger.HUNTING;

            GameScene.add(madBadger);
            aggroSpawned = true;
        }
    }


    public void talk() { GameScene.show(new WndBarasookDialogue()); }


    @Override
    public void interact() {

        final Item item = Dungeon.hero.belongings.getPunchCard(PunchCard.class);

        if (Dungeon.hero.belongings.getItem(PunchCard.class) != null){
            GameScene.show((new WndOptions(TXT_NAME, TXT_PUNCHCARD, TXT_ANSWERY, TXT_ANSWERN) {
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
            talk();
        }

    }

    public static void BarasookDialogue1(){
        tell(TXT_DIALOGUE1);
    }
    public static void BarasookDialogue2(){
        tell(TXT_DIALOGUE2);
    }
    public static void BarasookDialogue3(){
        tell(TXT_DIALOGUE3);
    }
    public static void BarasookDialogue4(){
        tell(TXT_DIALOGUE4);
    }
    public static void BarasookDialogue5(){
        tell(TXT_DIALOGUE5);
    }
    public static void BarasookDialogue6(){
        tell(TXT_DIALOGUE6);
    }


    private static void tell( String format, Object...args ) {
        GameScene.show(
                new WndQuest(new Barasook(), Utils.format(format, args)));
    }


}







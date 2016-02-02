package com.udawos.pioneer.actors.mobs.npcs;

import com.udawos.pioneer.Dungeon;
import com.udawos.pioneer.actors.Actor;
import com.udawos.pioneer.actors.buffs.Buff;
import com.udawos.pioneer.actors.mobs.AggroBarkeep;
import com.udawos.pioneer.effects.CellEmitter;
import com.udawos.pioneer.effects.particles.ElmoParticle;
import com.udawos.pioneer.items.Heap;
import com.udawos.pioneer.items.Item;
import com.udawos.pioneer.items.quest.PunchCard;
import com.udawos.pioneer.levels.G064Pub;
import com.udawos.pioneer.scenes.GameScene;
import com.udawos.pioneer.sprites.BarkeepSprite;
import com.udawos.pioneer.utils.Utils;
import com.udawos.pioneer.windows.DialogueWindows.WndBarkeepDialogue;
import com.udawos.pioneer.windows.WndOptions;
import com.udawos.pioneer.windows.WndQuest;

public class Barkeep extends NPC {

    private static final String TXT_NAME 	= "Barkeep";

    private static final String TXT_INTRO 	=
            "Well, another friendly soul crosses the barrier and makes its way to my home.";



    private static final String TXT_DIALOGUE1		= "If you want to run around out there, you probably don't want to do it sober. "+
                                                    "I can sell you some homebrew that will improve your outlook.";


    private static final String TXT_DIALOGUE2		= "Heh, you show some genuine interest. Wish I could say the same for my neighbours.";

    private static final String TXT_DIALOGUE3		= "Heh, if you go to the west you'll find the Dead City. But if you stay too long your head will explode. " +
                                                    "I'd like to know why that is.";

    private static final String TXT_DIALOGUE4		= "The gold? Oh, it's not for spending. Personal project.";

    private static final String TXT_DIALOGUE5		= "Why, I've grown fond of you. Maybe I can be your girlfriend tonight?";

    private static final String TXT_DIALOGUE6		= "Don't try talk to me about those cultists, either. I won't talk about them with you " +
                                                        "any more than I  did with that man with the stupid hat. " +
                                                        "I stopped talking to them after they messed up the area with that" +
                                                        " damn barrier all those years ago.";

    private static final String TXT_PUNCHCARD		= "Heh, that's an interesting card you have there. Would you let me have that?";
    private static final String TXT_ANSWERY		    = "Yes";
    private static final String TXT_ANSWERN		    = "No";
    private static final String TXT_THANKS		    = "Amazing! Come back later, I think this will help us both out. ";
    private static final String TXT_TOOBAD		    = "I...you don't even know what it is! You can't possibly find a use for it! You slag! ";



    private boolean seenBefore = false;

    private boolean aggroSpawned = false;


    {
        name = "barkeep";
        spriteClass = BarkeepSprite.class;

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
        yell("I'm calling in your tab, you piece of trash!");
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
                "This guy looks like a bad motherfucker.";
    }

    public static void spawn( G064Pub level ) {
        if (Dungeon.depth == 64) {

            Barkeep npc = new Barkeep();
            do {
                npc.pos = 1465;
            } while (npc.pos == -1);
            level.mobs.add(npc);
            Actor.occupyCell(npc);

        }
    }


    public void spawnAggro( ) {
        if (!aggroSpawned) {
            AggroBarkeep madKeep = new AggroBarkeep();
            madKeep.pos = 1464;
            madKeep.state = madKeep.HUNTING;

            GameScene.add(madKeep);
            aggroSpawned = true;
        }
    }


    public void talk() { GameScene.show(new WndBarkeepDialogue()); }


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

    public static void BarkeepDialogue1(){
        tell(TXT_DIALOGUE1);
    }
    public static void BarkeepDialogue2(){
        tell(TXT_DIALOGUE2);
    }
    public static void BarkeepDialogue3(){
        tell(TXT_DIALOGUE3);
    }
    public static void BarkeepDialogue4(){
        tell(TXT_DIALOGUE4);
    }
    public static void BarkeepDialogue5(){
        tell(TXT_DIALOGUE5);
    }
    public static void BarkeepDialogue6(){
        tell(TXT_DIALOGUE6);
    }


    private static void tell( String format, Object...args ) {
        GameScene.show(
                new WndQuest(new Barkeep(), Utils.format(format, args)));
    }


}







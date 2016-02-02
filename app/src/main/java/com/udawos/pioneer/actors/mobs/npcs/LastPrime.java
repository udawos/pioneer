
package com.udawos.pioneer.actors.mobs.npcs;

import com.udawos.pioneer.Dungeon;
import com.udawos.pioneer.actors.Actor;
import com.udawos.pioneer.actors.Char;
import com.udawos.pioneer.actors.buffs.Buff;
import com.udawos.pioneer.items.wands.Wand;
import com.udawos.pioneer.levels.U5TheMind;
import com.udawos.pioneer.scenes.GameScene;
import com.udawos.pioneer.sprites.WandmakerSprite;
import com.udawos.pioneer.utils.Utils;
import com.udawos.pioneer.windows.WndQuest;
import com.udawos.utils.Bundle;
import com.udawos.utils.Random;

public class LastPrime extends NPC {

    {
        name = "old man";
        spriteClass = WandmakerSprite.class;

        baseSpeed = 0.5f;
        state = PASSIVE;

        //drops Cultist Amulet

    }

    private static final String TXT_NAME 	    = "Last Prime";

    private static final String TXT_DIALOGUE1	= "Oh, it is ever so nice to see an agreeable person! " +
                                                    "If I had expected company I surely would have turned off " +
                                                    "my security system! Please accept...my apologies. " +
                                                    "\n \n" +
                                                    "*tips fedora*";
    private static final String TXT_DIALOGUE2	= "I would offer you to some sandwiches, but I have already " +
                                                    "finished eating all my victuals! Har har!";
    private static final String TXT_DIALOGUE3	= "Please feel free to look around my home. It is impossible that " +
                                                    "you would find anything disagreeable. Har har! ";
    private static final String TXT_DIALOGUE4	= "I love to eat. Har har! ";
    private static final String TXT_DIALOGUE5	= "Once so many friends, now an empty house..." +
                                                    "\n \n"+
                                                    "Har! Har!";
    private static final String TXT_DIALOGUE6	= "I'm not a bad person, I am just so hungry...\n\n" +
                                                    "Har! Har!";

    private static final String TXT_SANDWICH		= "Is that... I can smell... Are you possibly going to eat that sandwich?";
    private static final String TXT_ANSWERY		    = "Yes";
    private static final String TXT_ANSWERN		    = "No";
    private static final String TXT_THANKS		    = "Oh! Oh! Oh! Har har! Oh, I can tell I am going to most assuredly " +
                                                        "enjoy this sandwich! Har har! ";
    private static final String TXT_IKILLYOU		    = "NO! \n\n" +
                                                            "I. WILL. HAVE. IT! GUARDS!";






    @Override
    protected boolean act() {

        throwItem();

        sprite.turnTo( pos, Dungeon.hero.pos );
        spend(TICK);
        return true;
    }

    @Override
    public int defenseSkill( Char enemy ) {
        return 1000;
    }

    @Override
    public String defenseVerb() {
        return "absorbed";
    }

    @Override
    public void damage( int dmg, Object src ) {
        yell( "Har har har! That tickles the shield and gives me physical enjoyment! Har har!");
    }

    @Override
    public void add( Buff buff ) {
    }


    private boolean spoken1;
    private boolean spoken2;
    private boolean spoken3;
    private boolean spoken4;
    private boolean spoken5;
    private boolean spoken6;
    
    @Override
    public void interact() {

        /*
        final Item item = Dungeon.hero.belongings.getSandwich(Sandwich.class);

        if (Dungeon.hero.belongings.getItem(Sandwich.class) != null){
            GameScene.show((new WndOptions(TXT_NAME, TXT_SANDWICH, TXT_ANSWERY, TXT_ANSWERN) {
                protected void onSelect(int index) {
                    if (index == 0) {
                        Dungeon.hero.resume();
                        tell(TXT_THANKS);
                        item.detach(Dungeon.hero.belongings.backpack);
                    }
                    else if (index == 1) {
                        Dungeon.hero.resume();
                        tell(TXT_IKILLYOU);
                    }
                }
            }
            ));
        }
        */
            if (!spoken1) {
                LastPrimeDialogue1();
                spoken1 = true;
            }
            else if (!spoken2){
                LastPrimeDialogue2();
                spoken2 = true;
            }
            else if (spoken1 & spoken2 & !spoken3){
                LastPrimeDialogue3();
                spoken3 = true;
            }
            else if (spoken1 & spoken2 & spoken3 & !spoken4) {
                LastPrimeDialogue4();
                spoken4 = true;
            }
            else if (spoken1 & spoken2 & spoken3 & spoken4 & !spoken5) {
                LastPrimeDialogue5();
                spoken5 = true;
            }
            else if (spoken1 & spoken2 & spoken3 & spoken4 & spoken5 & !spoken6) {
                LastPrimeDialogue6();
                spoken6 = true;
            }
            else if (spoken1 & spoken2 & spoken3 & spoken4 & spoken5 & spoken6) {
                LastPrimeDialogue4();
            }
        
    }

    private void LastPrimeDialogue1(){
        tell(TXT_DIALOGUE1);
    }
    private void LastPrimeDialogue2(){
        tell(TXT_DIALOGUE2);
    }
    private  void LastPrimeDialogue3(){
        tell(TXT_DIALOGUE3);
    }
    private  void LastPrimeDialogue4(){
        tell(TXT_DIALOGUE4);
    }
    private  void LastPrimeDialogue5(){
        tell(TXT_DIALOGUE5);
    }
    private  void LastPrimeDialogue6(){
        tell(TXT_DIALOGUE6);
    }



    private void tell( String format, Object...args ) {
        GameScene.show(new WndQuest(this, Utils.format(format, args)));
    }

    @Override
    public String description() {
        return
                "He is incredibly corpulent. He seems like a nice old guy. " +
                        "If he's hiding anything, he's hiding it well. ";
    }

    public static class Quest {

        private static boolean spawned;

        private static boolean alternative;

        private static boolean given;

        public static Wand wand1;
        public static Wand wand2;

        public static void reset() {
            spawned = false;

            wand1 = null;
            wand2 = null;
        }

        private static final String NODE		= "wandmaker";

        private static final String SPAWNED		= "spawned";
        private static final String ALTERNATIVE	= "alternative";
        private static final String GIVEN		= "given";
        private static final String WAND1		= "wand1";
        private static final String WAND2		= "wand2";

        public static void storeInBundle( Bundle bundle ) {

            Bundle node = new Bundle();

            node.put( SPAWNED, spawned );

            if (spawned) {

                node.put( ALTERNATIVE, alternative );

                node.put(GIVEN, given );

                node.put( WAND1, wand1 );
                node.put(WAND2, wand2);
            }

            bundle.put( NODE, node );
        }

        public static void restoreFromBundle( Bundle bundle ) {

            Bundle node = bundle.getBundle( NODE );

            if (!node.isNull() && (spawned = node.getBoolean( SPAWNED ))) {

                alternative	=  node.getBoolean( ALTERNATIVE );

                given = node.getBoolean( GIVEN );

                wand1 = (Wand)node.get( WAND1 );
                wand2 = (Wand)node.get( WAND2 );
            } else {
                reset();
            }
        }

        public static void spawn( U5TheMind level) {
            if (!spawned && Dungeon.depth == 105) {

                LastPrime npc = new LastPrime();
                do {
                    npc.pos = 432;
                } while ((npc.pos == -1 || level.heaps.get(npc.pos) != null));
                level.mobs.add(npc);
                Actor.occupyCell(npc);

                spawned = true;
                alternative = Random.Int(2) == 0;

                given = false;

            }
        }
    }

}

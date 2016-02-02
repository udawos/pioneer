/*
 * Pixel Dungeon
 * Copyright (C) 2012-2015 Oleg Dolya
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>
 */
package com.udawos.pioneer.levels;

import com.udawos.noosa.audio.Sample;
import com.udawos.pioneer.Assets;
import com.udawos.pioneer.Dungeon;
import com.udawos.pioneer.actors.Actor;
import com.udawos.pioneer.actors.Char;
import com.udawos.pioneer.actors.mobs.npcs.LastPrime;
import com.udawos.pioneer.effects.CellEmitter;
import com.udawos.pioneer.effects.Speck;
import com.udawos.pioneer.items.Heap;
import com.udawos.pioneer.items.Honeypot;
import com.udawos.pioneer.items.Item;
import com.udawos.pioneer.items.books.BriefHistoryOfKrole;
import com.udawos.pioneer.items.keys.KeyCard;
import com.udawos.pioneer.items.quest.PunchCard;
import com.udawos.pioneer.items.keys.SkeletonKey;
import com.udawos.pioneer.levels.painters.Painter;
import com.udawos.pioneer.scenes.GameScene;
import com.udawos.pioneer.windows.WndStory;
import com.udawos.utils.Bundle;
import com.udawos.utils.Random;

public class U5TheMind extends Level {
//located below 98
    //The safe refuge of the cult leaders, controlled by a vengeful manifestation of The Ascended
    //It housed 7 of the primes and their servant
    //They entered the bunker 15 years ago, but have been harvested by the machine for vile rituals
    //Now only one is left, guarded by the auto turrets
    //He's a nice guy if you talk to him, but as soon as you're out of sight he tries to incapacitate you for his machine overlord

    //Killing him makes the machine overlord appear
    //Summoning the machine from his chambers makes the overlord appear

    //Killing the machine overlord gives you access to the Lair of The Ascended

    {
        color1 = 0x534f3e;
        color2 = 0xb9d661;

        viewDistance = 6;
    }

    private static final String SATEXT1 =  "The passageway turns north here. A musty smell comes"+
            " from down the passageway.";

    private static final String SATEXT2 =  "This looks like a storeroom. It is not clear if the inhabitants"+
            " left the food here because it couldn't be transported or because they had no choice but to leave it behind.";

    private static final String SATEXT3 = "This dining area appears tidy, despite being covered in a layer of dust.";

    private static final String SATEXT4 = "A large room extends to the south. You see stacks of coiled copper wire."+
            " A geared pedestal sits in the middle of the copper wire bales.";

    private static final String SATEXT5 = "This room is occupied by large cubes of thick steel. Copper wire leads from the top of each cube"+
            " through small holes to the west.\n\n"+
            " A switch lies in the middle of the southern end of the room.";


    private int arenaDoor;
    private boolean enteredArena = false;
    private boolean keyDropped = false;

    @Override
    public String tilesTex() {
        return Assets.TILES_EYE;
    }

    @Override
    public String waterTex() {
        return Assets.WATER_CAVES;
    }

    protected boolean[] water() {
        return Patch.generate( feeling == Feeling.CHASM ? 0.60f : 0.45f, 6 );
    }

    private static final String DOOR	= "door";
    private static final String ENTERED	= "entered";
    private static final String DROPPED	= "droppped";

    @Override
    public void storeInBundle( Bundle bundle ) {
        super.storeInBundle(bundle);
        bundle.put( DOOR, arenaDoor );
        bundle.put( ENTERED, enteredArena );
        bundle.put( DROPPED, keyDropped );
    }

    @Override
    public void restoreFromBundle( Bundle bundle ) {
        super.restoreFromBundle(bundle);
        arenaDoor = bundle.getInt( DOOR );
        enteredArena = bundle.getBoolean( ENTERED );
        keyDropped = bundle.getBoolean( DROPPED );
    }

    private int lockedDoor;
    private int note;
    private int note2;
    private int card;
    private int dining;

    @Override
    protected boolean build() {

        Painter.fill(this, 0, 0, 50, 50, Terrain.WALL);

        //Power generation
        Painter.fill(this, 6, 10, 2, 3, Terrain.EMPTY);
        Painter.fill(this, 8, 3, 4, 9, Terrain.EMPTY);

        //Storage rooms
        Painter.fill(this, 13, 3, 2, 4, Terrain.EMPTY);
        Painter.fill(this, 13, 8, 2, 4, Terrain.EMPTY);
        Painter.fill(this, 16, 3, 2, 4, Terrain.EMPTY);
        Painter.fill(this, 13, 8, 2, 4, Terrain.EMPTY);

        //Dining hall
        Painter.fill(this, 19, 3, 5, 6, Terrain.GRASS);
        Painter.fill(this, 24, 6, 1, 1, Terrain.DOOR);
        Painter.fill(this, 24, 9, 1, 1, Terrain.DOOR);
        Painter.fill(this, 18, 10, 2, 2, Terrain.GRASS);
        Painter.fill(this, 20, 11, 1, 1, Terrain.DOOR);
        Painter.fill(this, 21, 9, 3, 3, Terrain.GRASS);
        Painter.fill(this, 22, 12, 1, 1, Terrain.GRASS);

        //Kitchen
        Painter.fill(this, 25, 3, 3, 9, Terrain.EMPTY);

        //Master bedroom
        Painter.fill(this, 29, 3, 3, 3, Terrain.GRASS);
        Painter.fill(this, 33, 3, 3, 3, Terrain.GRASS);
        Painter.fill(this, 30, 6, 1, 1, Terrain.DOOR);
        Painter.fill(this, 34, 6, 1, 1, Terrain.DOOR);
        Painter.fill(this, 29, 7, 7, 5, Terrain.GRASS);
        Painter.fill(this, 32, 12, 1, 1, Terrain.GRASS);
        Painter.fill(this, 30, 9, 5, 1, Terrain.TABLE);
        Painter.fill(this, 30, 3, 1, 1, Terrain.BED);


        //Empty servant complex? Autonomous servant complex? Ascension power source or control unit?
        Painter.fill(this, 3, 15, 2, 8, Terrain.EMPTY);
        Painter.fill(this, 5, 16, 10, 1, Terrain.EMPTY);
        Painter.fill(this, 5, 19, 10, 1, Terrain.EMPTY);
        Painter.fill(this, 5, 22, 10, 1, Terrain.EMPTY);
        Painter.fill(this, 6, 15, 10, 1, Terrain.EMPTY);
        Painter.fill(this, 5, 18, 14, 1, Terrain.EMPTY);
        Painter.fill(this, 5, 21, 10, 1, Terrain.EMPTY);
        Painter.fill(this, 13, 24, 5, 6, Terrain.EMPTY);

        //Prime bedrooms
        Painter.fill(this, 21, 14, 1, 6, Terrain.GRASS);
        Painter.fill(this, 22, 15, 3, 5, Terrain.GRASS);
        Painter.fill(this, 21, 14, 1, 1, Terrain.DOOR);

        Painter.fill(this, 26, 14, 1, 6, Terrain.GRASS);
        Painter.fill(this, 27, 15, 3, 5, Terrain.GRASS);
        Painter.fill(this, 26, 14, 1, 1, Terrain.DOOR);

        Painter.fill(this, 31, 14, 1, 6, Terrain.GRASS);
        Painter.fill(this, 32, 15, 3, 5, Terrain.GRASS);
        Painter.fill(this, 31, 14, 1, 1, Terrain.DOOR);

        Painter.fill(this, 21, 21, 1, 6, Terrain.GRASS);
        Painter.fill(this, 22, 21, 3, 5, Terrain.GRASS);
        Painter.fill(this, 21, 26, 1, 1, Terrain.DOOR);

        Painter.fill(this, 26, 21, 1, 6, Terrain.GRASS);
        Painter.fill(this, 27, 21, 3, 5, Terrain.GRASS);
        Painter.fill(this, 26, 26, 1, 1, Terrain.DOOR);

        Painter.fill(this, 31, 21, 1, 6, Terrain.GRASS);
        Painter.fill(this, 32, 21, 4, 5, Terrain.GRASS);
        Painter.fill(this, 31, 26, 1, 1, Terrain.DOOR);

        //Security room
        Painter.fill(this, 16, 33, 7, 7, Terrain.EMPTY);

        //Entrance room
        Painter.fill(this, 20, 44, 3, 2, Terrain.EMPTY);
        Painter.fill(this, 23, 41, 5, 8, Terrain.EMPTY);

        //hallways
        Painter.fill(this, 15, 3, 1, 10, Terrain.EMPTY);
        Painter.fill(this, 6, 13, 31, 1, Terrain.EMPTY);
        Painter.fill(this, 18, 13, 2, 32, Terrain.EMPTY);
        Painter.fill(this, 20, 27, 16, 1, Terrain.EMPTY);
        Painter.fill(this, 17, 15, 1, 3, Terrain.WALL);
        Painter.fill(this, 17, 19, 1, 11, Terrain.WALL);
        Painter.fill(this, 15, 15, 2, 9, Terrain.EMPTY);

        //change to up for play build
        up= 2425;
        map[up] = Terrain.ENTRANCE;

        down = 2090;
        map[down] = Terrain.EMPTY;

        lockedDoor = 917;
        map[lockedDoor] = Terrain.LOCKED_DOOR;
/*
        note = 659;
        map[note] = Terrain.EMPTY;

        note2 = 1043;
        map[note2] = Terrain.EMPTY;

        card = 1228;
        map[card] = Terrain.EMPTY;

        dining = 512;
        map[dining] = Terrain.EMPTY;*/


        //THIS is how to use an item to make something happen
        // if (belongings.getItem(Amulet.class) == null) {
        // GameScene.show(new WndMessage(TXT_LEAVE));
        //ready();
        //so we'd want to use something like if (belongings.getItem(KeyCard.class) == null){


        return true;
    }

    @Override
    protected void decorate() {
        for (int i = WIDTH; i < LENGTH - WIDTH; i++) {
            if (map[i] == Terrain.DOOR && map[i -51 ] == Terrain.EMPTY && map[i-52] == Terrain.EMPTY
                    && map[i-53] == Terrain.EMPTY  && map[i-1] == Terrain.EMPTY  && map[i-2] == Terrain.EMPTY
                    && map[i-3] == Terrain.EMPTY  && map[i+47] == Terrain.EMPTY  && map[i+48] == Terrain.EMPTY
                    && map[i+49] == Terrain.EMPTY  && map[i-54] == Terrain.WALL && map[i-4] == Terrain.WALL
                    && map[i+46] == Terrain.WALL) {
                map[i-52] = Terrain.DRESSER;
                map[i-3] = Terrain.BED;
                map[i+48] = Terrain.TABLE;

            }
        }

        for (int i = WIDTH; i < LENGTH - WIDTH; i++) {
            if (map[i-1] == Terrain.WALL && map[i] == Terrain.EMPTY && map[i+1] == Terrain.WALL)
            {
                map[i] = Terrain.DOOR;

            }
        }
    }


    @Override
    public int nMobs() {
        return 1;
    }

    @Override
    protected void createMobs() {

       /* for (int i=0; i < 1; i++) {
            Mob mob = Bestiary.mob(Dungeon.depth);
            do {
                mob.pos = 1667;
            } while (mob.pos == -1);
            mobs.add(mob);
            Actor.occupyCell( mob );

        }
        for (int i=0; i < 1; i++) {
            Mob mob = Bestiary.mob(Dungeon.depth);
            do {
                mob.pos = 1671;
            } while (mob.pos == -1);
            mobs.add(mob);
            Actor.occupyCell( mob );

        }
        for (int i=0; i < 1; i++) {
            Mob mob = Bestiary.mob(Dungeon.depth);
            do {
                mob.pos = 1966;
            } while (mob.pos == -1);
            mobs.add(mob);
            Actor.occupyCell( mob );

        }
        for (int i=0; i < 1; i++) {
            Mob mob = Bestiary.mob(Dungeon.depth);
            do {
                mob.pos = 1972;
            } while (mob.pos == -1);
            mobs.add(mob);
            Actor.occupyCell( mob );

        }*/
        LastPrime.Quest.spawn(this);
    }

    public Actor respawner() {
        return null;
    }

    @Override
    protected void createItems() {
        drop( new BriefHistoryOfKrole(), note );
        //drop( new EscapeCode(), note1 );
        //drop( new BurnedNote1(), note2 );
        //drop( new BurnedNote2(), note3 );
        //drop(new Booze(), dining);
        drop( new KeyCard(), note2);
        drop( new PunchCard(), card);

    }

    @Override
    public int randomRespawnCell() {
        return -1;
    }

    private boolean tripped;
    private boolean shown1;
    private boolean shown2;
    private boolean shown3;
    private boolean shown4;
    private boolean shown5;
    private boolean escaped;

    @Override
    public void press( int cell, Char hero ) {

        super.press( cell, hero );

        if ( lastPrimeBetrayal) {


           /* Mob boss = Bestiary.mob( Dungeon.depth );
            boss.state = boss.HUNTING;
            do {
                boss.pos = Random.Int( LENGTH );
            } while (
                    !passable[boss.pos] ||
                            !outsideEntraceRoom( boss.pos ) ||
                            Dungeon.visible[boss.pos]);
            GameScene.add( boss );*/
            if (!tripped) {
                CellEmitter.get(up).start(Speck.factory(Speck.ROCK), 0.07f, 10);
                Sample.INSTANCE.play(Assets.SND_ROCKS);
            }
            tripped = true;
            set(up, Terrain.RUBBLE);
            GameScene.updateMap(up);
            set(down, Terrain.ENTRANCE);
            GameScene.updateMap(down);
            Dungeon.observe();



        }


      /*  if (HolographicInterface.hasCard){
            set(escapeRoute, Terrain.EMPTY);
            GameScene.updateMap(escapeRoute);
            if (!escaped){
                CellEmitter.get( escapeRoute ).start(Speck.factory(Speck.ROCK), 0.07f, 1);
                Sample.INSTANCE.play(Assets.SND_ROCKS);
            }
            escaped = true;
            Barkeep.TheEyeCompleted = true;
            Dungeon.observe();

        }*/


        if ( !shown1 && SATrigger1( cell)){
            GameScene.show(new WndStory(SATEXT1));
            shown1 = true;
        }

        if ( !shown2 && SATrigger2(cell)){
            GameScene.show(new WndStory(SATEXT2));
            shown2 = true;
        }

        if ( !shown3 && SATrigger3(cell)){
            GameScene.show(new WndStory(SATEXT3));
            shown3 = true;
        }

        if ( !shown4 && SATrigger4(cell)){
            GameScene.show(new WndStory(SATEXT4));
            shown4 = true;
        }
        if ( !shown5 && SATrigger5(cell)){
            GameScene.show(new WndStory(SATEXT5));
            shown5 = true;
        }

    }

    private boolean lastPrimeBetrayal;

    private boolean SATrigger1( int cell ) {
        int cx = cell % WIDTH;
        return cx == 6;
    }

    private boolean SATrigger2( int cell) {
        return cell == 1355;
    }

    private boolean SATrigger3( int cell) {return cell == 955;}

    private boolean SATrigger4( int cell ) {
        int cx = cell % WIDTH;
        int cy = cell / WIDTH;
        return  cx > 26 && cx < 31 &&  cy == 7;
    }

    private boolean SATrigger5( int cell ) {
        return  cell == 387;
    }


    @Override
    public Heap drop( Item item, int cell ) {

        if (!keyDropped && item instanceof SkeletonKey) {

            keyDropped = true;

            CellEmitter.get(arenaDoor).start( Speck.factory(Speck.ROCK), 0.07f, 10 );

            set( arenaDoor, Terrain.EMPTY );
            GameScene.updateMap(arenaDoor);
            Dungeon.observe();
        }

        return super.drop( item, cell );
    }



    @Override
    public String tileName( int tile ) {
        switch (tile) {
            case Terrain.GRASS:
                return "Lush Carpet";
            case Terrain.EMPTY_SP:
                return "Steel cube";
            case Terrain.HIGH_GRASS:
                return "Fluorescent mushrooms";
            case Terrain.WATER:
                return "Freezing cold water.";
            case Terrain.MOUNTAIN_ELBOW_NE:
                return "Mysterious geared pedestal";
            case Terrain.STATUE_SP:
                return "Bale of copper wire";
            case Terrain.SWITCH_ON:
            case Terrain.SWITCH_OFF:
                return "Switch";
            default:
                return super.tileName( tile );
        }
    }

    @Override
    public String tileDesc( int tile ) {
        switch (tile) {
            case Terrain.ENTRANCE:
                return "The ladder leads up to the upper depth.";
            case Terrain.EXIT:
                return "The ladder leads down to the lower depth.";
            case Terrain.GRASS:
                return "A really nice carpet. It helps give the room a contemporary comfy cozy feel.";
            case Terrain.HIGH_GRASS:
                return "Huge mushrooms block the view.";
            case Terrain.WALL_DECO:
                return "A vein of some ore is visible on the wall. Gold?";
            case Terrain.RUBBLE:
                return "It looks like the entrance collapsed. This could be a problem.";
            case Terrain.BARREL:
                //It should drop Brandy, once I make that class
                if (Random.Int(1,6) == 1){
                    Dungeon.level.drop((new Honeypot()), Dungeon.hero.pos).sprite.drop();
                    return "There were some bottles of booze in there!";
                }
                else return "This barrel contains rotting grain. It smells vile.";
            case Terrain.EMBERS:
                return "From the looks of things, a great many documents were burned here.";
            case Terrain.EMPTY_SP:
                return "It's a large steel cube, but you are unsure what it was used for.";
            case Terrain.MOUNTAIN_ELBOW_NE:
                return "It's hard to say what this was used for. It looks like this is just the base"+
                        " of something that rested here";
            case Terrain.STATUE_SP:
                return "You can see that it's a bale of copper wire, but you cannot fathom"+
                        "what it could have been used for. It is fixed to the floor.";
            case Terrain.SWITCH_ON:
            case Terrain.SWITCH_OFF:
                return "The switch is decayed and may not function properly";

            default:
                return super.tileDesc( tile );
        }
    }


}

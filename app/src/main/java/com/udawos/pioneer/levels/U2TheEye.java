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
import com.udawos.pioneer.actors.mobs.Bestiary;
import com.udawos.pioneer.actors.mobs.Mob;
import com.udawos.pioneer.actors.mobs.npcs.HolographicInterface;
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

public class U2TheEye extends Level {


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

    private static final String SATEXT5 = "This room is occupeid by large cubes of thick steel. Copper wire leads from the top of each cube"+
                                    " through small holes to the west.\n\n"+
                                    " A switch lies in the middle of the southern end of the room.";


    private static final int ROOM_LEFT		= WIDTH / 2 - 2;

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

    private int escapeRoute;
    private int note;
    private int note2;
    private int card;
    private int dining;

    @Override
    protected boolean build() {

        Painter.fill(this, 0, 0, 50, 50, Terrain.WALL);

        map[west] = Terrain.RUINED_WALL_W;

        //Main hallway
        Painter.fill(this, 2, 29,
                46, 3, Terrain.EMPTY);

        //north-south hallway
        Painter.fill( this, 2, 4, 3, 44, Terrain.EMPTY);

        //east-west hallway
        Painter.fill( this, 2, 4, 45, 3, Terrain.EMPTY);

        //kitchen/dining room
        Painter.fill( this, 6, 12, 7, 8, Terrain.EMPTY);
        //kitchen entrance
        Painter.fill( this, 5, 19, 1, 1, Terrain.EMPTY);
        //kitchen chairs
        Painter.fill(this, 7, 14, 6, 1, Terrain.CHAIR);
        Painter.fill(this, 7, 16, 6, 1, Terrain.CHAIR);
        Painter.fill(this, 7, 18, 6, 1, Terrain.CHAIR);
        //kitchen tables
        Painter.fill(this, 11, 14, 1, 5, Terrain.TABLE);
        Painter.fill(this, 8, 14, 1, 5, Terrain.TABLE);



        //storeroom
        Painter.fill( this, 6, 21, 7, 7, Terrain.EMPTY);
        //storeroom entrance
        Painter.fill( this, 5, 27, 1, 1, Terrain.EMPTY);

        //dormitory hallway
        Painter.fill( this, 18, 7, 3, 18, Terrain.EMPTY);

        //dormitory rooms west
        Painter.fill( this, 14, 11, 3, 3, Terrain.EMPTY);
        Painter.fill( this, 17, 12, 1, 1, Terrain.DOOR);

        Painter.fill( this, 14, 15, 3, 3, Terrain.EMPTY);
        Painter.fill( this, 17, 16, 1, 1, Terrain.DOOR);

        Painter.fill( this, 14, 19, 3, 3, Terrain.EMPTY);
        Painter.fill( this, 17, 20, 1, 1, Terrain.DOOR);

        Painter.fill( this, 14, 23, 3, 3, Terrain.EMPTY);
        Painter.fill( this, 17, 24, 1, 1, Terrain.DOOR);

        //dormitory rooms east
        Painter.fill( this, 22, 11, 3, 3, Terrain.EMPTY);
        Painter.fill( this, 21, 12, 1, 1, Terrain.DOOR);

        Painter.fill( this, 22, 15, 3, 3, Terrain.EMPTY);
        Painter.fill( this, 21, 16, 1, 1, Terrain.DOOR);

        Painter.fill( this, 22, 19, 3, 3, Terrain.EMPTY);
        Painter.fill( this, 21, 20, 1, 1, Terrain.DOOR);

        Painter.fill( this, 22, 23, 3, 3, Terrain.EMPTY);
        Painter.fill(this, 21, 24, 1, 1, Terrain.DOOR);

        //machine hall
        Painter.fill( this, 26, 7, 5, 18, Terrain.EMPTY);
        Painter.fill(this, 27, 16, 3, 3, Terrain.MOUNTAIN_ELBOW_NE);
        Painter.fill( this, 28, 16, 1, 1, Terrain.STATUE_SP);


        //battery bay
        Painter.fill(this, 37, 7, 1, 1, Terrain.DOOR);
        Painter.fill( this, 34, 8, 5, 18, Terrain.EMPTY);
        Painter.fill(this, 34, 9, 2,2, Terrain.EMPTY_SP);
        Painter.fill(this, 34, 12, 2,2, Terrain.EMPTY_SP);
        Painter.fill(this, 34, 15, 2,2, Terrain.EMPTY_SP);
        Painter.fill(this, 34, 18, 2,2, Terrain.EMPTY_SP);
        Painter.fill(this, 34, 21, 2,2, Terrain.EMPTY_SP);
        Painter.fill(this, 34, 24, 2,2, Terrain.EMPTY_SP);
        Painter.fill(this, 38, 16, 1, 1, Terrain.SWITCH_OFF);

        //farms
        //NEEDS something for farm plots
        Painter.fill( this, 41, 7, 7, 18, Terrain.EMPTY);

        Painter.fill(this, 43, 13, 3, 4, Terrain.EMBERS);

        Painter.fill(this, 6,21, 7,6, Terrain.BARREL);

        Painter.fill(this, (39-(Random.Int(0,2))),29,1,3, Terrain.SECRET_TRIPWIRE);
        Painter.fill(this, (37-(Random.Int(0,2))),29,1,3, Terrain.TRIPWIRE);

        //fake wall
        Painter.fill(this, 2, 32, 3, 1, Terrain.WALL);

        //change to up for play build
        up= 1547;
        map[up] = Terrain.ENTRANCE;

        down = 2090;
        map[down] = Terrain.EMPTY;

        escapeRoute = 1603;
        map[escapeRoute] = Terrain.WALL;

        note = 659;
        map[note] = Terrain.EMPTY;

        note2 = 1043;
        map[note2] = Terrain.EMPTY;

        card = 1228;
        map[card] = Terrain.EMPTY;

        dining = 512;
        map[dining] = Terrain.EMPTY;


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
            if (map[i] == Terrain.DOOR && map[i -49 ] == Terrain.EMPTY && map[i-48] == Terrain.EMPTY
                    && map[i-47] == Terrain.EMPTY  && map[i+1] == Terrain.EMPTY  && map[i+2] == Terrain.EMPTY
                    && map[i+3] == Terrain.EMPTY  && map[i+51] == Terrain.EMPTY  && map[i+52] == Terrain.EMPTY
                    && map[i+53] == Terrain.EMPTY  && map[i-46] == Terrain.WALL && map[i+4] == Terrain.WALL
                    && map[i+54] == Terrain.WALL) {
                map[i-48] = Terrain.DRESSER;
                map[i+3] = Terrain.BED;
                map[i+52] = Terrain.TABLE;

            }
        }

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

        for (int i=WIDTH; i < LENGTH - (WIDTH*4); i++) {
            if (map[i] == Terrain.EMPTY) {
                int n = 0;
                if (map[i + 3] == Terrain.EMPTY) {
                    n++;
                }
                if (map[i - 3] == Terrain.EMPTY) {
                    n++;
                }
                if (map[i + (WIDTH*3)] == Terrain.EMPTY) {
                    n++;
                }
                if (map[i - (WIDTH*3)] == Terrain.EMPTY) {
                    n++;
                }
                if (Random.Int(65) <= n) {
                    map[i] = Terrain.PEDESTAL;
                }
            }

        }

        //"caves"
        Painter.fill( this, 5, 46, 6, 3, Terrain.EMPTY);
        Painter.fill( this, 8, 33, 6, 3, Terrain.EMPTY);
        Painter.fill( this, 8, 33, 2, 16, Terrain.EMPTY);
        Painter.fill( this, 12, 33, 2, 16, Terrain.EMPTY);
        Painter.fill( this, 12, 47, 7, 2, Terrain.EMPTY);
        Painter.fill( this, 17, 41, 2, 8, Terrain.EMPTY);
        Painter.fill( this, 15, 36, 2, 7, Terrain.EMPTY);
        Painter.fill( this, 15, 36, 6, 2, Terrain.EMPTY);
        Painter.fill( this, 19, 33, 2, 5, Terrain.EMPTY);
        Painter.fill( this, 19, 33, 5, 2, Terrain.EMPTY);
        Painter.fill( this, 20, 42, 1, 4, Terrain.EMPTY);
        Painter.fill( this, 20, 42, 4, 1, Terrain.EMPTY);
        Painter.fill( this, 20, 42, 1, 7, Terrain.EMPTY);
        Painter.fill( this, 22, 35, 2, 7, Terrain.EMPTY);
        Painter.fill( this, 20, 45, 5, 1, Terrain.EMPTY);
        Painter.fill( this, 24, 45, 1, 4, Terrain.EMPTY);
        Painter.fill( this, 24, 45, 4, 1, Terrain.EMPTY);
        Painter.fill( this, 25, 34, 1, 12, Terrain.EMPTY);
        Painter.fill( this, 25, 34, 3, 1, Terrain.EMPTY);
        Painter.fill( this, 27, 34, 1, 14, Terrain.EMPTY);
        Painter.fill( this, 28, 47, 1, 1, Terrain.EMPTY);
        Painter.fill( this, 29, 46, 1, 1, Terrain.EMPTY);
        Painter.fill( this, 30, 47, 1, 1, Terrain.EMPTY);
        Painter.fill(this, 31, 36, 2, 1, Terrain.PEDESTAL);
        Painter.fill( this, 31, 35, 10, 13, Terrain.EMPTY);
        Painter.fill(this, 34, 39, 1, 3, Terrain.PEDESTAL);
        Painter.fill(this, 37, 41, 1, 1, Terrain.PEDESTAL);
    }


    @Override
    public int nMobs() {
        return 1;
    }

    @Override
    protected void createMobs() {
        for (int i=0; i < 1; i++) {
            Mob mob = Bestiary.mob(Dungeon.depth);
            do {
                mob.pos = 1790;
            } while (mob.pos == -1);
            mobs.add(mob);
            Actor.occupyCell( mob );

        }
        for (int i=0; i < 1; i++) {
            Mob mob = Bestiary.mob(Dungeon.depth);
            do {
                mob.pos = 2390;
            } while (mob.pos == -1);
            mobs.add(mob);
            Actor.occupyCell( mob );

        }
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
    public static boolean escaped = false;

    @Override
    public void press( int cell, Char hero ) {

        super.press(cell, hero);

        if ( outsideEntranceRoom( cell )) {


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


        if (HolographicInterface.hasCard){
            set(escapeRoute, Terrain.EMPTY);
            GameScene.updateMap(escapeRoute);
            Dungeon.observe();
            if (!escaped){
                CellEmitter.get( escapeRoute ).start(Speck.factory(Speck.ROCK), 0.07f, 1);
                Sample.INSTANCE.play(Assets.SND_ROCKS);
            }
            escaped = true;

        }


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

    private boolean outsideEntranceRoom( int cell ) {
        int cx = cell % WIDTH;
        return cx < ROOM_LEFT-1;

        //int cy = cell / WIDTH;
        //cy < ROOM_TOP-1
    }

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

    protected boolean barrelSearch = false;

    @Override
    public String tileName( int tile ) {
        switch (tile) {
            case Terrain.GRASS:
                return "Fluorescent moss";
            case Terrain.EMPTY_SP:
                return "Steel cube";
            case Terrain.HIGH_GRASS:
                return "Fluorescent mushrooms";
            case Terrain.WATER:
                return "Freezing cold water.";
            case Terrain.MOUNTAIN_ELBOW_NE:
                return "Bale of copper wire";
            case Terrain.STATUE_SP:
                return "Mysterious geared pedestal";
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
            case Terrain.HIGH_GRASS:
                return "Huge mushrooms block the view.";
            case Terrain.WALL_DECO:
                return "A vein of some ore is visible on the wall. Gold?";
            case Terrain.RUBBLE:
                return "It looks like the entrance collapsed. This could be a problem.";
            case Terrain.BARREL:
                //It should drop Brandy, once I make that class
                if (barrelSearch == false && Random.Int(1,6) == 1){
                    Dungeon.level.drop((new Honeypot()), Dungeon.hero.pos).sprite.drop();
                    barrelSearch = true;
                return "There was a bottles of booze in there!";
                }
                else return "This barrel contains rotting grain. It smells vile.";
            case Terrain.EMBERS:
                return "From the looks of things, a great many documents were burned here.";
            case Terrain.EMPTY_SP:
                return "It's a large steel cube, but you are unsure what it was used for.";
            case Terrain.STATUE_SP:
                return "It's hard to say what this was used for. It looks like this is just the base"+
                        " of something that rested here";
            case Terrain.MOUNTAIN_ELBOW_NE:
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

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

import com.udawos.pioneer.Assets;
import com.udawos.pioneer.Dungeon;
import com.udawos.pioneer.actors.Actor;
import com.udawos.pioneer.actors.Char;
import com.udawos.pioneer.effects.CellEmitter;
import com.udawos.pioneer.effects.Speck;
import com.udawos.pioneer.items.Heap;
import com.udawos.pioneer.items.Honeypot;
import com.udawos.pioneer.items.Item;
import com.udawos.pioneer.items.keys.MindKey;
import com.udawos.pioneer.items.keys.SkeletonKey;
import com.udawos.pioneer.levels.painters.Painter;
import com.udawos.pioneer.scenes.GameScene;
import com.udawos.utils.Bundle;
import com.udawos.utils.Random;

public class U4TheArm extends Level {

    //All
    //this stuff is set up for The Eye, so fix it
    //Also, place this one under 78

    {
        color1 = 0x534f3e;
        color2 = 0xb9d661;

        viewDistance = 6;
    }

    private static final String SATEXT1 =  "It's hard to say what this room was for. The far wall is heavily scorched"+
            " and there is a nearby row of pedestals with machines on them.";

    private static final String SATEXT2 =  "The north end of this room is heavily scorched."+
            " Some signs can been seen; they appear to focus on the merits of safe weapon handling.";

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

    private int mindKey;
    private int testingarea1;
    private int testingarea2;
    private int laboratory;
    private int security;
    private int dorms;

    @Override
    protected boolean build() {


        Painter.fill(this, 0, 0, 50, 50, Terrain.WALL);

        //Head office
        Painter.fill(this, 2, 2, 13, 9, Terrain.EMPTY);
        Painter.fill(this, 9, 11, 1, 1, Terrain.EMPTY);

        //second office
        Painter.fill(this, 7, 12, 13, 7, Terrain.EMPTY);
        Painter.fill(this, 14, 4, 6, 8, Terrain.EMPTY);
        Painter.fill(this, 17, 19, 3, 1, Terrain.EMPTY);

        //store room
        Painter.fill(this, 23, 2, 6, 7, Terrain.EMPTY);
        Painter.fill(this, 23, 9, 1, 1, Terrain.EMPTY);

        //cubicle farm
        Painter.fill(this, 31, 3, 6, 40, Terrain.EMPTY);
        Painter.fill(this, 30, 12, 1, 2, Terrain.EMPTY);
        Painter.fill(this, 30, 22, 1, 2, Terrain.EMPTY);
        Painter.fill(this, 30, 30, 1, 2, Terrain.EMPTY);

        //3 joining hallways
        Painter.fill(this, 21, 10, 6, 3, Terrain.EMPTY);
        Painter.fill(this, 20, 11, 1, 1, Terrain.EMPTY);
        Painter.fill(this, 21, 15, 6, 4, Terrain.EMPTY);
        Painter.fill(this, 20, 17, 1, 1, Terrain.EMPTY);
        Painter.fill(this, 22, 39, 5, 4, Terrain.EMPTY);
        Painter.fill(this, 20, 40, 1, 1, Terrain.EMPTY);
        Painter.fill(this, 26, 40, 1, 1, Terrain.EMPTY);

        //main hallway
        Painter.fill(this, 27, 10, 3, 34, Terrain.EMPTY);
        //dividing wall
        Painter.fill(this, 13, 2, 1, 9, Terrain.WALL);

        //manufacturing
        Painter.fill(this, 3, 20, 18, 27, Terrain.EMPTY);

        //Entrance area
        up = 2178;
        map[up] = Terrain.ENTRANCE;

        return true;



        //THIS is how to use an item to make something happen
        // if (belongings.getItem(Amulet.class) == null) {
        // GameScene.show(new WndMessage(TXT_LEAVE));
        //ready();
        //so we'd want to use something like if (belongings.getItem(KeyCard.class) == null){

    }

    @Override
    protected void decorate() {

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

        }*/
    }

    public Actor respawner() {
        return null;
    }

    @Override
    protected void createItems() {
        //drop( new BeamWeaponManual(), testingarea1 );
        //drop( new MagFedWeaponPlans(), laboratory );
        //drop( new FayeRayGun(), security );
        //drop( new BurnedNote2(), note3 );
        //drop(new Booze(), dining);
        // drop( new KeyCard(), note2);
        //drop( new PunchCard(), card);
        Item item = new MindKey();
        drop(item, mindKey).type = Heap.Type.SKELETON;

    }

    @Override
    public int randomRespawnCell() {
        return -1;
    }

    private boolean shown1;
    private boolean shown2;
    private boolean shown3;
    private boolean shown4;
    private boolean shown5;
    private boolean shown6;
    private boolean shown7;

    @Override
    public void press( int cell, Char hero ) {

        super.press(cell, hero);

       // if (outsideEntranceRoom(cell)) {


    }
           /* Mob boss = Bestiary.mob( Dungeon.depth );
            boss.state = boss.HUNTING;
            do {
                boss.pos = Random.Int( LENGTH );
            } while (
                    !passable[boss.pos] ||
                            !outsideEntraceRoom( boss.pos ) ||
                            Dungeon.visible[boss.pos]);
            GameScene.add( boss );
            set(up, Terrain.RUBBLE);
            GameScene.updateMap(up);
            set(down, Terrain.ENTRANCE);
            GameScene.updateMap(down);
            Dungeon.observe();

            CellEmitter.get( up ).start(Speck.factory(Speck.ROCK), 0.07f, 10);
            Sample.INSTANCE.play(Assets.SND_ROCKS);
            *
        }

       /* if (Switch.flipConfirmed){
            HolographicInterface holographicInterface = new HolographicInterface();
            holographicInterface.spawn(Dungeon.depth);
            holographicInterface.HP = holographicInterface.HT;
            holographicInterface.pos = 1553;

            GameScene.add(holographicInterface);

            holographicInterface.sprite.alpha(0);
            holographicInterface.sprite.parent.add(new AlphaTweener(holographicInterface.sprite, 1, 0.15f));
        }*/


        /*if (HolographicInterface.hasCard){
            set( escapeRoute, Terrain.EMPTY);
            GameScene.updateMap(escapeRoute);
            CellEmitter.get( escapeRoute ).start(Speck.factory(Speck.ROCK), 0.07f, 1);
            Sample.INSTANCE.play(Assets.SND_ROCKS);
            Barkeep.TheEyeCompleted = true;
            Dungeon.observe();
        }*/

/*
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
        if ( !shown6 && SATrigger6(cell)){
            GameScene.show(new WndStory(SATEXT5));
            shown6 = true;
        }
        if ( !shown7 && SATrigger7(cell)){
            GameScene.show(new WndStory(SATEXT5));
            shown7 = true;
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
        int cy = cell / WIDTH;
        return cx == 30 && cy >= 3 && cy <= 4 ;
    }

    private boolean SATrigger2( int cell) {
        int cx = cell % WIDTH;
        int cy = cell / WIDTH;
        return cx == 38 && cy >= 28 && cy <= 29 ;
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

    private boolean SATrigger6( int cell ) {
        return  cell == 387;
    }

    private boolean SATrigger7( int cell ) {
        return  cell == 387;
    }
*/

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
                return "Fluorescent moss";
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

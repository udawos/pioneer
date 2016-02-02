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
import com.udawos.pioneer.items.keys.SkeletonKey;
import com.udawos.pioneer.levels.painters.Painter;
import com.udawos.pioneer.scenes.GameScene;
import com.udawos.pioneer.windows.WndStory;
import com.udawos.utils.Bundle;
import com.udawos.utils.Random;

public class U3TheSword extends Level {

    {
        color1 = 0x534f3e;
        color2 = 0xb9d661;

        viewDistance = 6;
    }

    private static final String SATEXT1 =  "It's hard to say what this room was for. The far wall is heavily scorched"+
            " and there is a nearby row of pedestals with machines on them.";

    private static final String SATEXT2 =  "The north end of this room is heavily scorched."+
            " Some signs can been seen; they appear to focus on the merits of safe weapon handling.";

    private static final String SATEXT3 = "This area looks like it was used as a laboratory.";

    private static final String SATEXT4 = "This looks like it acted as a security checkpoint. You see a locked door in the " +
            "northeast corner.";

    private static final String SATEXT5 = "The passageway opens out into what looks like a living area.";



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

    private int testingarea1;
    private int testingarea2;
    private int laboratory;
    private int security;
    private int dorms;
    private int lockedDoor;

    @Override
    protected boolean build() {

        Painter.fill(this, 0, 0, 50, 50, Terrain.WALL);


        Painter.fill( this, 23, 18, 5, 12, Terrain.EMPTY);
        //hallways
        Painter.fill( this, 1, 16, 3, 5, Terrain.EMPTY);
        Painter.fill( this, 1, 19, 22, 2, Terrain.EMPTY);
        Painter.fill( this, 28, 28, 11, 2, Terrain.EMPTY);
        Painter.fill( this, 25, 3, 6, 2, Terrain.EMPTY);
        Painter.fill( this, 25, 3, 2, 15, Terrain.EMPTY);
        Painter.fill( this, 1, 20, 2, 10, Terrain.EMPTY);
        Painter.fill(this, 3, 28, 1, 2, Terrain.EMPTY);
        Painter.fill(this, 13, 32, 2, 4, Terrain.EMPTY);

        //Research Lab
        Painter.fill( this, 1, 2, 21, 14, Terrain.EMPTY);
        Painter.fill(this, 3, 4, 3, 1, Terrain.DESK);
        Painter.fill(this, 3, 7, 3, 1, Terrain.DESK);
        Painter.fill(this, 3, 10, 3, 1, Terrain.DESK);
        Painter.fill(this, 3, 13, 3, 1, Terrain.DESK);

        //Dormitory block
        Painter.fill( this, 1, 35, 46, 13, Terrain.EMPTY);
        Painter.fill(this, 4, 40, 2, 9, Terrain.WALL);
        Painter.fill(this, 9, 40, 2, 9, Terrain.WALL);
        Painter.fill(this, 14, 40, 2, 9, Terrain.WALL);
        Painter.fill(this, 19, 40, 1, 9, Terrain.WALL);

        //Kitchen area
        Painter.fill(this, 20, 44, 7, 1, Terrain.WALL);
        Painter.fill(this, 28, 45, 1, 4, Terrain.WALL);
        Painter.fill(this, 34, 34, 1, 6, Terrain.WALL);
        Painter.fill(this, 34, 44, 1, 5, Terrain.WALL);
        Painter.fill(this, 23, 38, 1, 3, Terrain.TABLE);
        Painter.fill(this, 26, 38, 1, 3, Terrain.TABLE);
        Painter.fill(this, 29, 38, 1, 3, Terrain.TABLE);
        Painter.fill(this, 20, 48, 3, 1, Terrain.TABLE);
        Painter.fill(this, 23, 48, 5, 1, Terrain.EMPTY);

        //Security
        Painter.fill( this, 4, 24, 13, 9, Terrain.EMPTY);
        Painter.fill(this, 10, 24, 1, 4, Terrain.WALL);
        Painter.fill(this, 10, 27, 6, 1, Terrain.WALL);

        //Test range 1
        Painter.fill( this, 31, 2, 7, 21, Terrain.EMPTY);

        //Test range 2
        Painter.fill( this, 39, 2, 9, 28, Terrain.EMPTY);

        //Entrance area
        up = 1175;
        map[up] = Terrain.ENTRANCE;

        testingarea1 = 234;
        map[testingarea1] = Terrain.EMPTY;

        testingarea2 = 1243;
        map[testingarea2] = Terrain.EMPTY;

        laboratory = 169;
        map[laboratory] = Terrain.EMPTY;

        security = 1214;
         map[security] = Terrain.EMPTY;

        dorms = 2362;
        map[dorms] = Terrain.EMPTY;

        lockedDoor = 1366;
        map[lockedDoor] = Terrain.LOCKED_DOOR;

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


    @Override
    public void press( int cell, Char hero ) {

        super.press( cell, hero );



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


        if ( !shown1 && SATrigger1(cell)){
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
    //testingarea1
    private boolean SATrigger1( int cell ) {
        int cx = cell % WIDTH;
        int cy = cell / WIDTH;
        return cx == 30 && cy >= 3 && cy <= 4 ;
    }
    //testingarea2
    private boolean SATrigger2( int cell) {
        int cx = cell % WIDTH;
        int cy = cell / WIDTH;
        return cx == 38 && cy >= 28 && cy <= 29 ;
    }
    //laboratory
    private boolean SATrigger3( int cell) {
        int cx = cell % WIDTH;
        int cy = cell / WIDTH;
        return  cx >= 1 && cx <= 3 &&  cy == 17;
    }

    //security
    private boolean SATrigger4( int cell ) {
        int cx = cell % WIDTH;
        int cy = cell / WIDTH;
        return  cy >= 28 && cy <= 29 &&  cx == 3;
    }

    //dorms
    private boolean SATrigger5( int cell ) {
        int cx = cell % WIDTH;
        int cy = cell / WIDTH;
        return  cx >= 13 && cx <= 14 &&  cy == 34;
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

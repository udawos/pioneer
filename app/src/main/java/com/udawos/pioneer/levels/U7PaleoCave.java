
package com.udawos.pioneer.levels;

import com.udawos.pioneer.Assets;
import com.udawos.pioneer.Dungeon;
import com.udawos.pioneer.actors.Actor;
import com.udawos.pioneer.actors.Char;
import com.udawos.pioneer.effects.CellEmitter;
import com.udawos.pioneer.effects.Speck;
import com.udawos.pioneer.items.Heap;
import com.udawos.pioneer.items.Item;
import com.udawos.pioneer.items.books.BriefHistoryOfKrole;
import com.udawos.pioneer.items.keys.SkeletonKey;
import com.udawos.pioneer.items.rings.BoneCharm;
import com.udawos.pioneer.levels.painters.Painter;
import com.udawos.pioneer.scenes.GameScene;
import com.udawos.pioneer.windows.WndStory;
import com.udawos.utils.Bundle;

public class U7PaleoCave extends Level {


    {
        color1 = 0x534f3e;
        color2 = 0xb9d661;

        viewDistance = 6;
    }

    private static final String SATEXT1 =  "This cave is full of bones. It looks like some kind of communal burial chamber.";
    private static final String SATEXT2 =  "This is a large cavern. The back wall is covered by a painting.";
    private static final String SATEXT3 = "You enter a cavern. A stone altar with a ... something ... atop it sits in the center.";

    private int arenaDoor;
    private boolean enteredArena = false;
    private boolean keyDropped = false;

    @Override
    public String tilesTex() {
        return Assets.TILES_CAVES;
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

    private int altar;

    @Override
    protected boolean build() {

        Painter.fill(this, 0, 0, 50, 50, Terrain.WALL);

       //Burial cave
        Painter.fill(this, 2, 13, 9, 11, Terrain.EMPTY);
        Painter.fill(this, 6, 18, 1, 1, Terrain.TABLE);

        //Gallery cave
        Painter.fill(this, 16, 10, 11, 11, Terrain.EMPTY);
        Painter.fill(this, 17, 21, 9, 1, Terrain.EMPTY);
        Painter.fill(this, 18, 22, 7, 1, Terrain.EMPTY);
        Painter.fill(this, 19, 23, 5, 1, Terrain.EMPTY);
        Painter.fill(this, 17, 9, 9, 1, Terrain.BARREL);

        //Cave of Bones
        Painter.fill(this, 27, 19, 8, 8, Terrain.EMPTY);
        Painter.fill(this, 28, 16, 1, 1, Terrain.EMPTY);
        Painter.fill(this, 28, 17, 7, 1, Terrain.EMPTY);
        Painter.fill(this, 28, 18, 5, 1, Terrain.EMPTY);
        Painter.fill(this, 28, 27, 2, 1, Terrain.EMPTY);
        Painter.fill(this, 28, 28, 1, 1, Terrain.EMPTY);
        Painter.fill(this, 31, 27, 2, 1, Terrain.EMPTY);
        Painter.fill(this, 28, 18, 5, 1, Terrain.DRESSER);
        Painter.fill(this, 28, 18, 5, 1, Terrain.DRESSER);
        Painter.fill(this, 28, 17, 6, 1, Terrain.DRESSER);
        Painter.fill(this, 27, 26, 8, 1, Terrain.DRESSER);
        Painter.fill(this, 27, 21, 1, 5, Terrain.DRESSER);

        //Entrance
        Painter.fill(this, 34, 22, 11, 2, Terrain.EMPTY);
        Painter.fill(this, 39, 21, 1, 1, Terrain.EMPTY);
        Painter.fill(this, 38, 24, 1, 1, Terrain.EMPTY);
        Painter.fill(this, 42, 24, 1, 1, Terrain.EMPTY);


        //Dead end maze
        Painter.fill(this, 13, 15, 3, 1, Terrain.EMPTY);
        Painter.fill(this, 11, 18, 3, 1, Terrain.EMPTY);
        Painter.fill(this, 13, 16, 1, 32, Terrain.EMPTY);
        Painter.fill(this, 12, 14, 1, 1, Terrain.EMPTY);
        Painter.fill(this, 14, 30, 2, 1, Terrain.EMPTY);
        Painter.fill(this, 15, 31, 3, 1, Terrain.EMPTY);
        Painter.fill(this, 17, 32, 2, 1, Terrain.EMPTY);
        Painter.fill(this, 18, 33, 1, 2, Terrain.EMPTY);
        Painter.fill(this, 19, 34, 1, 2, Terrain.EMPTY);
        Painter.fill(this, 19, 39, 1, 5, Terrain.EMPTY);
        Painter.fill(this, 20, 36, 1, 3, Terrain.EMPTY);
        Painter.fill(this, 16, 37, 1, 1, Terrain.EMPTY);
        Painter.fill(this, 17, 38, 2, 1, Terrain.EMPTY);

        Painter.fill(this, 14, 44, 1, 1, Terrain.EMPTY);
        Painter.fill(this, 15, 43, 2, 1, Terrain.EMPTY);
        Painter.fill(this, 16, 44, 3, 1, Terrain.EMPTY);



        //change to up for play build
        up = 1144;
        map[up] = Terrain.ENTRANCE;

        altar = 907;

        return true;
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
      /*  for (int i=0; i < 1; i++) {
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
        drop( new BoneCharm(), altar );
        //drop( new EscapeCode(), note1 );
        //drop( new BurnedNote1(), note2 );
        //drop( new BurnedNote2(), note3 );
        //drop(new Heap.Type.SKELETON, dining);
        Item item = new BriefHistoryOfKrole();
            drop(item, 1128).type = Heap.Type.SKELETON;


    }

    @Override
    public int randomRespawnCell() {
        return -1;
    }

    private boolean shown1;
    private boolean shown2;
    private boolean shown3;

    @Override
    public void press( int cell, Char hero ) {

        super.press( cell, hero );


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

    }

    //cave of bones
    private boolean SATrigger1( int cell ) {
        int cx = cell % WIDTH;
        int cy = cell / WIDTH;
        return cx == 35 && cy >= 22 && cy <= 23 ;
    }
    //gallery
    private boolean SATrigger2( int cell) {
        int cx = cell % WIDTH;
        int cy = cell / WIDTH;
        return cx == 26 && cy >= 19 && cy <= 20 ;
    }
    //burial
    private boolean SATrigger3( int cell) {
        int cx = cell % WIDTH;
        int cy = cell / WIDTH;
        return cx == 10 && cy >= 17 && cy <= 19 ;
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
            case Terrain.BARREL:
                return "Cave painting";
            case Terrain.TABLE:
                return "Stone altar";
            case Terrain.DRESSER:
                return "Skeletal remains";
            case Terrain.GRASS:
                return "Fluorescent moss";
            case Terrain.HIGH_GRASS:
                return "Fluorescent mushrooms";
            case Terrain.WATER:
                return "Freezing cold water.";
            default:
                return super.tileName( tile );
        }
    }

    @Override
    public String tileDesc( int tile ) {
        switch (tile) {
            case Terrain.BARREL:
                return "This wall is covered in ancient paintings. "+
                "They all seem to depict the hunting of various animals.";
            case Terrain.TABLE:
                return "A stone altar, likely used for some ancient ritual.";
            case Terrain.DRESSER:
                return "Skeletal remains. It appears to have been human.";
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
            case Terrain.EMBERS:
                return "From the looks of things, a great many documents were burned here.";
            default:
                return super.tileDesc( tile );
        }
    }


}

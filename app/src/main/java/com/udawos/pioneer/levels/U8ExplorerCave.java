
package com.udawos.pioneer.levels;

import com.udawos.noosa.audio.Sample;
import com.udawos.pioneer.Assets;
import com.udawos.pioneer.Dungeon;
import com.udawos.pioneer.actors.Actor;
import com.udawos.pioneer.actors.Char;
import com.udawos.pioneer.effects.CellEmitter;
import com.udawos.pioneer.effects.Speck;
import com.udawos.pioneer.items.Heap;
import com.udawos.pioneer.items.Item;
import com.udawos.pioneer.items.keys.SkeletonKey;
import com.udawos.pioneer.items.keys.SwordKey;
import com.udawos.pioneer.levels.painters.Painter;
import com.udawos.pioneer.scenes.GameScene;
import com.udawos.utils.Bundle;

public class U8ExplorerCave extends Level {
    //under map 36

    {
        color1 = 0x534f3e;
        color2 = 0xb9d661;

        viewDistance = 6;
    }



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

    private int bottle;

    @Override
    protected boolean build() {

        Painter.fill(this, 0, 0, 50, 50, Terrain.WALL);

        Painter.fill(this, 11, 10, 1, 16, Terrain.EMPTY);
        Painter.fill(this, 12, 11, 1, 15, Terrain.EMPTY);
        Painter.fill(this, 13, 12, 1, 14, Terrain.EMPTY);
        Painter.fill(this, 13, 12, 1, 14, Terrain.EMPTY);
        Painter.fill(this, 14, 12, 1, 17, Terrain.EMPTY);
        Painter.fill(this, 15, 12, 1, 15, Terrain.EMPTY);
        Painter.fill(this, 16, 13, 1, 13, Terrain.EMPTY);
        Painter.fill(this, 17, 14, 1, 11, Terrain.EMPTY);
        Painter.fill(this, 18, 15, 1, 9, Terrain.EMPTY);
        Painter.fill(this, 19, 16, 1, 6, Terrain.EMPTY);
        Painter.fill(this, 20, 17, 3, 2, Terrain.EMPTY);
        Painter.fill(this, 23, 15, 1, 7, Terrain.EMPTY);
        Painter.fill(this, 24, 17, 1, 4, Terrain.EMPTY);

        //change to up for play build
        up = 975;
        map[up] = Terrain.ENTRANCE;

        bottle = 1213;
        map[bottle] = Terrain.EMPTY;

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

        /*
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
        //drop( new Bottle(), bottle );
        //drop( new EscapeCode(), note1 );
        //drop( new BurnedNote1(), note2 );
        //drop( new BurnedNote2(), note3 );
        //drop(new Booze(), dining);
        Item item = new SwordKey();
        drop(item, bottle).type = Heap.Type.SKELETON;

    }

    @Override
    public int randomRespawnCell() {
        return -1;
    }


    @Override
    public void press( int cell, Char hero ) {

        super.press( cell, hero );

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
            set(up, Terrain.RUBBLE);
            GameScene.updateMap(up);
            set(down, Terrain.EXIT);
            GameScene.updateMap(down);
            Dungeon.observe();

            CellEmitter.get( south ).start(Speck.factory(Speck.ROCK), 0.07f, 10);
            Sample.INSTANCE.play(Assets.SND_ROCKS);

        }

    }

    private boolean outsideEntranceRoom( int cell ) {
        int cx = cell % WIDTH;
        return cx < 5;
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
                return "It's an empty barrel. It doesn't contain anything.";
            case Terrain.EMBERS:
                return "From the looks of things, a great many documents were burned here.";
            default:
                return super.tileDesc( tile );
        }
    }


}

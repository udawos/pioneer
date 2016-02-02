
package com.udawos.pioneer.levels;

import com.udawos.pioneer.Assets;
import com.udawos.pioneer.Bones;
import com.udawos.pioneer.Dungeon;
import com.udawos.pioneer.actors.Actor;
import com.udawos.pioneer.actors.Char;
import com.udawos.pioneer.actors.mobs.SeaMonster;
import com.udawos.pioneer.items.Heap;
import com.udawos.pioneer.items.Item;
import com.udawos.pioneer.scenes.GameScene;
import com.udawos.utils.Bundle;
import com.udawos.utils.Random;

public class C027LakeLevel extends Level {
//make it so She can increase the water levels at will

    {
        color1 = 0x801500;
        color2 = 0xa68521;

        viewDistance = 18;
    }

    private int  guaranteedWater;

    private int stairs = -1;
    private boolean enteredArena = false;
    private boolean keyDropped = false;

    @Override
    public String tilesTex() {
        return Assets.TILES_GRASS;
    }

    @Override
    public String waterTex() {
        return Assets.WATER_CAVES;
    }

    protected boolean[] water() {
        return Patch.generate( feeling == Feeling.CHASM ? 0.60f : 0.45f, 6 );
    }

    private static final String STAIRS	= "stairs";
    private static final String ENTERED	= "entered";
    private static final String DROPPED	= "droppped";

    @Override
    public void storeInBundle( Bundle bundle ) {
        super.storeInBundle(bundle);
        bundle.put( STAIRS, stairs );
        bundle.put( ENTERED, enteredArena );
        bundle.put( DROPPED, keyDropped );
    }

    @Override
    public void restoreFromBundle( Bundle bundle ) {
        super.restoreFromBundle(bundle);
        stairs = bundle.getInt( STAIRS );
        enteredArena = bundle.getBoolean( ENTERED );
        keyDropped = bundle.getBoolean( DROPPED );
    }

    @Override
    protected boolean build() {

        boolean[] patch = Patch.generate( 0.62f, 6 );
        for (int i=0; i < 2450; i++) {
            if (map[i] == Terrain.EMPTY && patch[i]) {
                map[i] = Terrain.WATER;
            }
        }

        guaranteedWater = 1820;
        map[guaranteedWater] = Terrain.WATER;

        west = 1151;
        map[west] = Terrain.WEST;

        east = 1098;
        map[east] = Terrain.EAST;

        north = 75;
        map[north] = Terrain.NORTH;

        south =  2425;
        map[south] = Terrain.SOUTH;



        return true;
    }

    @Override
    protected void decorate() {

    }

    @Override
    protected void createMobs() {
    }

    public Actor respawner() {
        return null;
    }

    @Override
    protected void createItems() {
        Item item = Bones.get();
        if (item != null) {
            int pos;
            do {
                pos = Random.IntRange( 1, 2500 );
            } while (pos == north || map[pos] == Terrain.SIGN);
            drop( item, pos ).type = Heap.Type.SKELETON;
        }
    }

    @Override
    public int randomRespawnCell() {
        return -1;
    }

    @Override
    public void press( int cell, Char hero ) {

        super.press(cell, hero);

        if (!enteredArena && hero == Dungeon.hero) {

            enteredArena = true;

            GameScene.updateMap();

            Dungeon.observe();

            SeaMonster boss = new SeaMonster();
            do {
                boss.pos = Random.Int(LENGTH);
            } while (
                    !water[boss.pos]  ||
                            Dungeon.visible[boss.pos]);
            GameScene.add( boss );
            boss.spawnTentacles();


        }
    }



    @Override
    public Heap drop( Item item, int cell ) {

        return super.drop( item, cell );
    }

    @Override
    public String tileName( int tile ) {
        switch (tile) {
            case Terrain.WATER:
                return "Cold water";
            case Terrain.GRASS:
                return "Trampled grass";
            case Terrain.HIGH_GRASS:
                return "High Grass";
            case Terrain.STATUE:
            case Terrain.STATUE_SP:
                return "Pillar";
            default:
                return super.tileName( tile );
        }
    }

    @Override
    public String tileDesc(int tile) {
        switch (tile) {
            case Terrain.WATER:
                return "This water is cold..";
            case Terrain.STATUE:
            case Terrain.STATUE_SP:
                return "The pillar is made of real humanoid skulls. Awesome.";
            default:
                return super.tileDesc(tile);
        }
    }

}

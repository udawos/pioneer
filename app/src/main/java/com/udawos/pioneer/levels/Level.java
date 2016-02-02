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

import com.udawos.noosa.Scene;
import com.udawos.noosa.audio.Sample;
import com.udawos.pioneer.Assets;
import com.udawos.pioneer.Challenges;
import com.udawos.pioneer.Dungeon;
import com.udawos.pioneer.Statistics;
import com.udawos.pioneer.actors.Actor;
import com.udawos.pioneer.actors.Char;
import com.udawos.pioneer.actors.blobs.Alchemy;
import com.udawos.pioneer.actors.blobs.Blob;
import com.udawos.pioneer.actors.blobs.WellWater;
import com.udawos.pioneer.actors.buffs.Awareness;
import com.udawos.pioneer.actors.buffs.Blindness;
import com.udawos.pioneer.actors.buffs.Buff;
import com.udawos.pioneer.actors.buffs.MindVision;
import com.udawos.pioneer.actors.buffs.Shadows;
import com.udawos.pioneer.actors.hero.Hero;
import com.udawos.pioneer.actors.hero.HeroClass;
import com.udawos.pioneer.actors.mobs.Bestiary;
import com.udawos.pioneer.actors.mobs.Mob;
import com.udawos.pioneer.effects.particles.FlowParticle;
import com.udawos.pioneer.effects.particles.WindParticle;
import com.udawos.pioneer.items.Gold;
import com.udawos.pioneer.items.Heap;
import com.udawos.pioneer.items.Item;
import com.udawos.pioneer.items.armor.Armor;
import com.udawos.pioneer.items.bags.ScrollHolder;
import com.udawos.pioneer.items.bags.SeedPouch;
import com.udawos.pioneer.items.food.Food;
import com.udawos.pioneer.items.potions.PotionOfHealing;
import com.udawos.pioneer.items.scrolls.Scroll;
import com.udawos.pioneer.items.scrolls.ScrollOfUpgrade;
import com.udawos.pioneer.levels.features.Chasm;
import com.udawos.pioneer.levels.features.Door;
import com.udawos.pioneer.levels.features.HighGrass;
import com.udawos.pioneer.levels.features.Switch;
import com.udawos.pioneer.levels.painters.Painter;
import com.udawos.pioneer.levels.traps.AlarmTrap;
import com.udawos.pioneer.levels.traps.LightTrap;
import com.udawos.pioneer.levels.traps.SummoningTrap;
import com.udawos.pioneer.levels.traps.TripwireTrap;
import com.udawos.pioneer.levels.traps.VantagePoint;
import com.udawos.pioneer.mechanics.ShadowCaster;
import com.udawos.pioneer.plants.Plant;
import com.udawos.pioneer.scenes.GameScene;
import com.udawos.pioneer.utils.GLog;
import com.udawos.utils.Bundlable;
import com.udawos.utils.Bundle;
import com.udawos.utils.Random;
import com.udawos.utils.SparseArray;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;

public abstract class Level implements Bundlable {

    public static enum Feeling {
        NONE,
        CHASM,
        WATER,
        GRASS
    }

    public static final int WIDTH = 50;
    public static final int HEIGHT = 50;
    public static final int LENGTH = WIDTH * HEIGHT;

    public static final int[] NEIGHBOURS = {+1, -1};
    public static final int[] NEIGHBOURS4 = {-WIDTH, +1, +WIDTH, -1};
    public static final int[] NEIGHBOURS8 = {+1, -1, +WIDTH, -WIDTH, +1 + WIDTH, +1 - WIDTH, -1 + WIDTH, -1 - WIDTH};
    public static final int[] NEIGHBOURS9 = {0, +1, -1, +WIDTH, -WIDTH, +1 + WIDTH, +1 - WIDTH, -1 + WIDTH, -1 - WIDTH};

    protected static final float TIME_TO_RESPAWN = 50;

    private static final String TXT_HIDDEN_PLATE_CLICKS = "You hear a clicking noise nearby";

    private static final String TXT_SOMETHING_HERE = "A strange force pushes you back.";

    private static final String TXT_HILL_VIEW = "From this height you can see very far.";

    public static boolean resizingNeeded;
    public static int loadedMapSize;

    public static int DirectionValue;

    public int[] map;
    public static boolean[] visited;
    public boolean[] mapped;

    public int viewDistance = Dungeon.isChallenged(Challenges.DARKNESS) ? 3 : 8;

    public static boolean[] fieldOfView = new boolean[LENGTH];

    public static boolean[] passable = new boolean[LENGTH];
    public static boolean[] losBlocking = new boolean[LENGTH];
    public static boolean[] flamable = new boolean[LENGTH];
    public static boolean[] secret = new boolean[LENGTH];
    public static boolean[] solid = new boolean[LENGTH];
    public static boolean[] avoid = new boolean[LENGTH];
    public static boolean[] water = new boolean[LENGTH];
    public static boolean[] pit = new boolean[LENGTH];


    public static boolean[] discoverable = new boolean[LENGTH];

    public Feeling feeling = Feeling.NONE;

    public int north;
    public int west;
    public int south;
    public int east;
    public int down;
    public int up;
    public int vantagePoint;
    public int mapEdge;

    public HashSet<Mob> mobs;
    public SparseArray<Heap> heaps;
    public HashMap<Class<? extends Blob>, Blob> blobs;
    public SparseArray<Plant> plants;

    protected ArrayList<Item> itemsToSpawn = new ArrayList<Item>();

    public int color1 = 0x00ffff;
    public int color2 = 0x0021bf;

    protected static boolean pitRoomNeeded = false;
    protected static boolean weakFloorCreated = false;

    private static final String MAP = "map";
    private static final String VISITED = "visited";
    private static final String MAPPED = "mapped";
    private static final String DOWN = "down";
    private static final String NORTH = "north entrance";
    private static final String WEST = "west entrance";
    private static final String UP = "up";
    private static final String SOUTH= "south";
    private static final String EAST= "east";
    private static final String HEAPS = "heaps";
    private static final String PLANTS = "plants";
    private static final String MOBS = "mobs";
    private static final String BLOBS = "blobs";
    private static final String VANTAGEPOINT ="vantage point";

    public void create() {

        resizingNeeded = false;

        map = new int[LENGTH];
        visited = new boolean[LENGTH];
        Arrays.fill(visited, false);
        mapped = new boolean[LENGTH];
        Arrays.fill(mapped, false);

        mobs = new HashSet<Mob>();
        heaps = new SparseArray<Heap>();
        blobs = new HashMap<Class<? extends Blob>, Blob>();
        plants = new SparseArray<Plant>();

        if (!Dungeon.bossLevel()) {
            /*addItemToSpawn(Generator.random(Generator.Category.FOOD));
            if (Dungeon.posNeeded()) {
                addItemToSpawn(new PotionOfStrength());
                Dungeon.potionOfStrength++;
            }

            if (Dungeon.souNeeded()) {
                addItemToSpawn(new ScrollOfUpgrade());
                Dungeon.scrollsOfUpgrade++;
            }
            if (Dungeon.soeNeeded()) {
                addItemToSpawn(new ScrollOfEnchantment());
                Dungeon.scrollsOfEnchantment++;
            }

            if (Dungeon.depth >= 1) {
                switch (Random.Int(1)) {
                   // case 0:
                     //   if (!Dungeon.bossLevel(Dungeon.depth + 1)) {
                       //     feeling = Feeling.CHASM;
                       // }
                       // break;
                    //case 0:
                      //  feeling = Feeling.WATER;
                        //break;
                   // case 2:
                     //   feeling = Feeling.GRASS;
                       // break;
                }
            }*/
        }


        boolean pitNeeded = Dungeon.depth > 1 && weakFloorCreated;

        do {
            Arrays.fill(map, feeling == Feeling.CHASM ? Terrain.CHASM : Terrain.EMPTY);

            pitRoomNeeded = pitNeeded;
            weakFloorCreated = false;

        } while (!build());
        decorate();

        buildFlagMaps();
        cleanWalls();

        createMobs();
        createItems();
    }

    public void reset() {

        for (Mob mob : mobs.toArray(new Mob[0])) {
            if (!mob.reset()) {
                mobs.remove(mob);
            }
        }
        createMobs();
    }

    @Override
    public void restoreFromBundle(Bundle bundle) {

        mobs = new HashSet<Mob>();
        heaps = new SparseArray<Heap>();
        blobs = new HashMap<Class<? extends Blob>, Blob>();
        plants = new SparseArray<Plant>();

        map = bundle.getIntArray(MAP);
        visited = bundle.getBooleanArray(VISITED);
        mapped = bundle.getBooleanArray(MAPPED);

        north = bundle.getInt(NORTH);
        west = bundle.getInt(WEST);
        south = bundle.getInt(SOUTH);
        east = bundle.getInt(EAST);
        down = bundle.getInt(DOWN);
        up = bundle.getInt(UP);
        vantagePoint = bundle.getInt(VANTAGEPOINT);

        weakFloorCreated = false;

        Collection<Bundlable> collection = bundle.getCollection(HEAPS);
        for (Bundlable h : collection) {
            Heap heap = (Heap) h;
            if (resizingNeeded) {
                heap.pos = adjustPos(heap.pos);
            }
            heaps.put(heap.pos, heap);
        }

        collection = bundle.getCollection(PLANTS);
        for (Bundlable p : collection) {
            Plant plant = (Plant) p;
            if (resizingNeeded) {
                plant.pos = adjustPos(plant.pos);
            }
            plants.put(plant.pos, plant);
        }

        collection = bundle.getCollection(MOBS);
        for (Bundlable m : collection) {
            Mob mob = (Mob) m;
            if (mob != null) {
                if (resizingNeeded) {
                    mob.pos = adjustPos(mob.pos);
                }
                mobs.add(mob);
            }
        }

        collection = bundle.getCollection(BLOBS);
        for (Bundlable b : collection) {
            Blob blob = (Blob) b;
            blobs.put(blob.getClass(), blob);
        }

        buildFlagMaps();
        cleanWalls();
    }

    @Override
    public void storeInBundle(Bundle bundle) {
        bundle.put(MAP, map);
        bundle.put(VISITED, visited);
        bundle.put(MAPPED, mapped);
        bundle.put(NORTH, north);
        bundle.put(WEST, west);
        bundle.put(SOUTH, south);
        bundle.put(EAST, east);
        bundle.put(DOWN, down);
        bundle.put(UP, up);
        bundle.put(HEAPS, heaps.values());
        bundle.put(PLANTS, plants.values());
        bundle.put(MOBS, mobs);
        bundle.put(BLOBS, blobs.values());
        bundle.put(VANTAGEPOINT, vantagePoint);
    }

    public int tunnelTile() {
        return feeling == Feeling.CHASM ? Terrain.EMPTY_SP : Terrain.EMPTY;
    }

    public int adjustPos(int pos) {
        return (pos / loadedMapSize) * WIDTH + (pos % loadedMapSize);
    }

    public String tilesTex() {
        return null;
    }

    public String waterTex() {
        return null;
    }


    protected boolean build() {
            paintWater();

            return true;
    };

    abstract protected void decorate();

    abstract protected void createMobs();

    abstract protected void createItems();

    public void addVisuals(Scene scene) {
        for (int i = 0; i < LENGTH; i++) {
            if (pit[i]) {
                scene.add(new WindParticle.Wind(i));
                if (i >= WIDTH && water[i - WIDTH]) {
                    scene.add(new FlowParticle.Flow(i - WIDTH));
                }
            }
        }
    }

    public int nMobs() {
        return 0;
    }

    public Actor respawner() {
        return new Actor() {
            @Override
            protected boolean act() {
                if (mobs.size() < nMobs()) {

                    Mob mob = Bestiary.mutable(Dungeon.depth);
                    mob.state = mob.WANDERING;
                    mob.pos = randomRespawnCell();
                    if (Dungeon.hero.isAlive() && mob.pos != -1) {
                        GameScene.add(mob);
                        if (Statistics.amuletObtained) {
                            mob.beckon(Dungeon.hero.pos);
                        }
                    }
                }
               // spend(Dungeon.nightMode || Statistics.amuletObtained ? TIME_TO_RESPAWN / 2 : TIME_TO_RESPAWN);
                return true;
            }
        };
    }

    public int randomRespawnCell() {
        int cell;
        do {
            cell = Random.Int(LENGTH);
        } while (!passable[cell] || Dungeon.visible[cell] || Actor.findChar(cell) != null);
        return cell;
    }

    public int randomDestination() {
        int cell;
        do {
            cell = Random.Int(LENGTH);
        } while (!passable[cell]);
        return cell;
    }

    public void addItemToSpawn(Item item) {
        if (item != null) {
            itemsToSpawn.add(item);
        }
    }

    public Item itemToSpanAsPrize() {
        if (Random.Int(itemsToSpawn.size() + 1) > 0) {
            Item item = Random.element(itemsToSpawn);
            itemsToSpawn.remove(item);
            return item;
        } else {
            return null;
        }
    }




    private void buildFlagMaps() {

        for (int i = 0; i < LENGTH; i++) {
            int flags = Terrain.flags[map[i]];
            passable[i] = (flags & Terrain.PASSABLE) != 0;
            losBlocking[i] = (flags & Terrain.LOS_BLOCKING) != 0;
            flamable[i] = (flags & Terrain.FLAMABLE) != 0;
            secret[i] = (flags & Terrain.SECRET) != 0;
            solid[i] = (flags & Terrain.SOLID) != 0;
            avoid[i] = (flags & Terrain.AVOID) != 0;
            water[i] = (flags & Terrain.LIQUID) != 0;
            pit[i] = (flags & Terrain.PIT) != 0;


        }

        int lastRow = LENGTH - WIDTH;
        for (int i = 0; i < WIDTH; i++) {
            passable[i] = avoid[i] = false;
            passable[lastRow + i] = avoid[lastRow + i] = false;
        }
        for (int i = WIDTH; i < lastRow; i += WIDTH) {
            passable[i] = avoid[i] = false;
            passable[i + WIDTH - 1] = avoid[i + WIDTH - 1] = false;
        }


        for (int i = WIDTH; i < LENGTH - WIDTH; i++) {

            if (water[i]) {
                int t = Terrain.WATER_TILES;
                for (int j = 0; j < NEIGHBOURS4.length; j++) {
                    if ((Terrain.flags[map[i + NEIGHBOURS4[j]]] & Terrain.UNSTITCHABLE) != 0) {
                        t += 1 << j;
                    }
                }
                map[i] = t;

                } else {
                    if (pit[i]) {
                        if (!pit[i - WIDTH]) {
                            int c = map[i - WIDTH];
                            if (c == Terrain.EMPTY_SP) {
                                map[i] = Terrain.CHASM_FLOOR_SP;
                            } else if (water[i - WIDTH]) {
                                map[i] = Terrain.CHASM_WATER;
                            } else if ((Terrain.flags[c] & Terrain.UNSTITCHABLE) != 0) {
                                map[i] = Terrain.CHASM_WALL;
                            } else {
                                map[i] = Terrain.CHASM_FLOOR;
                            }

                        }
                    }
                }
        }
    }




    private void cleanWalls() {
        for (int i = 0; i < LENGTH; i++) {

            boolean d = false;

            for (int j = 0; j < NEIGHBOURS9.length; j++) {
                int n = i + NEIGHBOURS9[j];
                if (n >= 0 && n < LENGTH && map[n] != Terrain.EMPTY && map[n] != Terrain.EMPTY_SP) {
                    d = true;
                    break;
                }
            }

            if (d) {
                d = false;

                for (int j = 0; j < NEIGHBOURS9.length; j++) {
                    int n = i + NEIGHBOURS9[j];
                    if (n >= 0 && n < LENGTH && !pit[n]) {
                        d = true;
                        break;
                    }
                }
            }

            discoverable[i] = d;
        }
    }

    public static void set(int cell, int terrain) {
        Painter.set(Dungeon.level, cell, terrain);

        int flags = Terrain.flags[terrain];
        passable[cell] = (flags & Terrain.PASSABLE) != 0;
        losBlocking[cell] = (flags & Terrain.LOS_BLOCKING) != 0;
        flamable[cell] = (flags & Terrain.FLAMABLE) != 0;
        secret[cell] = (flags & Terrain.SECRET) != 0;
        solid[cell] = (flags & Terrain.SOLID) != 0;
        avoid[cell] = (flags & Terrain.AVOID) != 0;
        pit[cell] = (flags & Terrain.PIT) != 0;
       water[cell] = terrain == Terrain.WATER || terrain >= Terrain.WATER_TILES;
    }

    public Heap drop(Item item, int cell) {

        if (Dungeon.isChallenged(Challenges.NO_FOOD) && item instanceof Food) {
            item = new Gold(item.price());
        } else if (Dungeon.isChallenged(Challenges.NO_ARMOR) && item instanceof Armor) {
            item = new Gold(item.price());
        } else if (Dungeon.isChallenged(Challenges.NO_HEALING) && item instanceof PotionOfHealing) {
            item = new Gold(item.price());
        } else if (Dungeon.isChallenged(Challenges.NO_HERBALISM) && item instanceof SeedPouch) {
            item = new Gold(item.price());
        } else if (Dungeon.isChallenged(Challenges.NO_SCROLLS) && (item instanceof Scroll || item instanceof ScrollHolder)) {
            if (item instanceof ScrollOfUpgrade) {
                // These scrolls still can be found
            } else {
                item = new Gold(item.price());
            }
        }

        if ((map[cell] == Terrain.ALCHEMY) && !(item instanceof Plant.Seed)) {
            int n;
            do {
                n = cell + NEIGHBOURS8[Random.Int(8)];
            } while (map[n] != Terrain.EMPTY_SP);
            cell = n;
        }

        Heap heap = heaps.get(cell);
        if (heap == null) {

            heap = new Heap();
            heap.pos = cell;
            if (map[cell] == Terrain.CHASM || (Dungeon.level != null && pit[cell])) {
                Dungeon.dropToChasm(item);
                GameScene.discard(heap);
            } else {
                heaps.put(cell, heap);
                GameScene.add(heap);
            }

        } else if (heap.type == Heap.Type.LOCKED_CHEST || heap.type == Heap.Type.CRYSTAL_CHEST) {

            int n;
            do {
                n = cell + Level.NEIGHBOURS8[Random.Int(8)];
            } while (!Level.passable[n] && !Level.avoid[n]);
            return drop(item, n);

        }
        heap.drop(item);

        if (Dungeon.level != null) {
            press(cell, null);
        }

        return heap;
    }

    public Plant plant(Plant.Seed seed, int pos) {
        Plant plant = plants.get(pos);
        if (plant != null) {
            plant.wither();
        }

        plant = seed.couch(pos);
        plants.put(pos, plant);

        GameScene.add(plant);

        return plant;
    }

    public void uproot(int pos) {
        plants.delete(pos);
    }

    public int pitCell() {
        return randomRespawnCell();
    }

    public void press(int cell, Char ch) {

        //Try to use heroFall in this class (it was in RegularLevel)
        //here:
        //if (pit[cell] && ch == Dungeon.hero ) {
        //Cliff.heroClimb(cell);
        //	return;
        //}

        boolean trap = false;

        switch (map[cell]) {

            case Terrain.HILL:
                GLog.i(TXT_HILL_VIEW);
                VantagePoint.trigger(cell, ch);
                LightTrap.trigger(cell, ch);
                break;

            case Terrain.MOUNTAIN_CORNER_NE:
            case Terrain.MOUNTAIN_N:
                break;

            case Terrain.CLIFF_SW_CORNER:
                GLog.i(TXT_HIDDEN_PLATE_CLICKS);
            case Terrain.MOUNTAIN_W:
                break;

            case Terrain.MOUNTAIN_CORNER_SW:
            case Terrain.MOUNTAIN_E:
                break;


            case Terrain.SECRET_TRIPWIRE:
                GLog.i(TXT_HIDDEN_PLATE_CLICKS);
            case Terrain.TRIPWIRE:
                trap = true;
                TripwireTrap.trigger(cell, ch);
                break;

            /*case Terrain.BOUNCE_PERIMETER:
                BounceAnomaly.trigger(cell, ch);
                break;
                */

           /* case Terrain.SECRET_GRIPPING_TRAP:
                GLog.i(TXT_HIDDEN_PLATE_CLICKS);
            case Terrain.GRIPPING_TRAP:
                trap = true;
                GrippingTrap.trigger(cell, ch);
                break;*/

            case Terrain.SECRET_SUMMONING_TRAP:
                GLog.i(TXT_HIDDEN_PLATE_CLICKS);
            case Terrain.SUMMONING_TRAP:
                trap = true;
                SummoningTrap.trigger(cell, ch);
                break;

            case Terrain.HIGH_GRASS:
                HighGrass.trample(this, cell, ch);
                break;

            case Terrain.WELL:
                WellWater.affectCell(cell);
                break;

            case Terrain.ALCHEMY:
                if (ch == null) {
                    Alchemy.transmute(cell);
                }
                break;

            case Terrain.DOOR:
                Door.enter(cell);
                break;

            case Terrain.SWITCH_OFF:
                Switch.flip(cell);
                break;
            case Terrain.SWITCH_ON:
                Switch.flip(cell);
                break;
        }

        if (trap) {
            Sample.INSTANCE.play(Assets.SND_TRAP);
            if (ch == Dungeon.hero) {
                Dungeon.hero.interrupt();
            }
            set(cell, Terrain.EMPTY);
            GameScene.updateMap(cell);
        }

        Plant plant = plants.get(cell);
        if (plant != null) {
            plant.activate(ch);
        }
    }

    public void mobPress(Mob mob) {

        int cell = mob.pos;

        if (pit[cell] && !mob.flying) {
            Chasm.mobFall(mob);
            return;
        }

        boolean trap = true;
        switch (map[cell]) {

            /*(case Terrain.MOUNTAIN_N:
                break;

            case Terrain.MOUNTAIN_W:
                FireTrap.trigger(cell, mob);
                break;

            case Terrain.MOUNTAIN_E:
                ParalyticTrap.trigger(cell, mob);
                break;

            case Terrain.RUINED_WALL_E:
                PoisonTrap.trigger(cell, mob);
                break;
            */

            case Terrain.TRIPWIRE:
                AlarmTrap.trigger(cell, mob);
                break;

           /*case Terrain.BOUNCE_PERIMETER:
              BounceAnomaly.trigger(cell, mob);
               break;
            */

            //case Terrain.GRIPPING_TRAP:
              //  GrippingTrap.trigger(cell, mob);
                //break;

            case Terrain.SUMMONING_TRAP:
                SummoningTrap.trigger(cell, mob);
                break;

            case Terrain.DOOR:
                Door.enter(cell);

            default:
                trap = false;

        }

        if (trap) {
            if (Dungeon.visible[cell]) {
                Sample.INSTANCE.play(Assets.SND_TRAP);
            }
            set(cell, Terrain.MOUNTAIN_S);
            GameScene.updateMap(cell);
        }

        Plant plant = plants.get(cell);
        if (plant != null) {
            plant.activate(mob);
        }
    }

    public boolean[] updateFieldOfView(Char c) {

        int cx = c.pos % WIDTH;
        int cy = c.pos / WIDTH;

        boolean sighted = c.buff(Blindness.class) == null && c.buff(Shadows.class) == null && c.isAlive();
        if (sighted) {
            ShadowCaster.castShadow(cx, cy, fieldOfView, c.viewDistance);
        } else {
            Arrays.fill(fieldOfView, false);
        }


        int sense = 1;
        if (c.isAlive()) {
            for (Buff b : c.buffs(MindVision.class)) {
                sense = Math.max(((MindVision) b).distance, sense);
            }
        }

        if ((sighted && sense > 1) || !sighted) {

            int ax = Math.max(0, cx - sense);
            int bx = Math.min(cx + sense, WIDTH - 1);
            int ay = Math.max(0, cy - sense);
            int by = Math.min(cy + sense, HEIGHT - 1);

            int len = bx - ax + 1;
            int pos = ax + ay * WIDTH;
            for (int y = ay; y <= by; y++, pos += WIDTH) {
                Arrays.fill(fieldOfView, pos, pos + len, true);
            }

            for (int i = 0; i < LENGTH; i++) {
                fieldOfView[i] &= discoverable[i];
            }
        }

        if (c.isAlive()) {
            if (c.buff(MindVision.class) != null) {
                for (Mob mob : mobs) {
                    int p = mob.pos;
                    fieldOfView[p] = true;
                    fieldOfView[p + 1] = true;
                    fieldOfView[p - 1] = true;
                    fieldOfView[p + WIDTH + 1] = true;
                    fieldOfView[p + WIDTH - 1] = true;
                    fieldOfView[p - WIDTH + 1] = true;
                    fieldOfView[p - WIDTH - 1] = true;
                    fieldOfView[p + WIDTH] = true;
                    fieldOfView[p - WIDTH] = true;
                }
            } else if (c == Dungeon.hero && ((Hero) c).heroClass == HeroClass.PATHFINDER) {
                for (Mob mob : mobs) {
                    int p = mob.pos;
                    if (distance(c.pos, p) == 2) {
                        fieldOfView[p] = true;
                        fieldOfView[p + 1] = true;
                        fieldOfView[p - 1] = true;
                        fieldOfView[p + WIDTH + 1] = true;
                        fieldOfView[p + WIDTH - 1] = true;
                        fieldOfView[p - WIDTH + 1] = true;
                        fieldOfView[p - WIDTH - 1] = true;
                        fieldOfView[p + WIDTH] = true;
                        fieldOfView[p - WIDTH] = true;
                    }
                }
            }
            if (c.buff(Awareness.class) != null) {
                for (Heap heap : heaps.values()) {
                    int p = heap.pos;
                    fieldOfView[p] = true;
                    fieldOfView[p + 1] = true;
                    fieldOfView[p - 1] = true;
                    fieldOfView[p + WIDTH + 1] = true;
                    fieldOfView[p + WIDTH - 1] = true;
                    fieldOfView[p - WIDTH + 1] = true;
                    fieldOfView[p - WIDTH - 1] = true;
                    fieldOfView[p + WIDTH] = true;
                    fieldOfView[p - WIDTH] = true;
                }
            }
        }

        return fieldOfView;
    }

    public static int distance(int a, int b) {
        int ax = a % WIDTH;
        int ay = a / WIDTH;
        int bx = b % WIDTH;
        int by = b / WIDTH;
        return Math.max(Math.abs(ax - bx), Math.abs(ay - by));
    }

    public static boolean adjacent(int a, int b) {
        int diff = Math.abs(a - b);
        return diff == 1 || diff == WIDTH || diff == WIDTH + 1 || diff == WIDTH - 1;
    }

    public static boolean jumpAdjacent(int a, int b) {
        int diff = Math.abs(a - b);
        return  diff == 1 || diff == -1
                || diff == WIDTH|| diff == -WIDTH
                || diff == WIDTH +1 || diff == -WIDTH +1
                || diff == WIDTH -1 || diff == -WIDTH - 1
                || diff == WIDTH +2 || diff == -WIDTH -2
                || diff == 2|| diff == -2
                || diff == (WIDTH*2) || diff == -(WIDTH*2)
                || diff == +2 + WIDTH || diff == +2 - WIDTH
                || diff == -2 + (WIDTH*2) || diff == -2 - (WIDTH*2)
                || diff == (WIDTH*2) + 1 || diff == (WIDTH*2) - 1
                || diff == (WIDTH*2) + 2 || diff == (WIDTH*2) - 2
                || diff == -(WIDTH*2) + 1 || diff == -(WIDTH*2) - 1;
    }

    public String tileName(int tile) {

        if (tile >= Terrain.WATER_TILES) {
            return tileName(Terrain.WATER);
        }

        if (tile != Terrain.CHASM && (Terrain.flags[tile] & Terrain.PIT) != 0) {
            return tileName(Terrain.CHASM);
        }

        switch (tile) {
            case Terrain.CHASM:
                return "Chasm";
            case Terrain.EMPTY:
            case Terrain.EMPTY_SP:
            case Terrain.MOUNTAIN_CORNER_SE:
            case Terrain.MOUNTAIN_CORNER_NE:
            case Terrain.CLIFF_SW_CORNER:
            case Terrain.MOUNTAIN_CORNER_SW:
            case Terrain.SECRET_RUINED_WALL_E:
            case Terrain.SECRET_TRIPWIRE:
                return "Floor";
            case Terrain.GRASS:
                return "Grass";
            case Terrain.WATER:
                return "Water";
            case Terrain.WALL:
            case Terrain.WALL_DECO:
            case Terrain.MOUNTAIN_CORNER_NW:
                return "Wall";
            case Terrain.DOOR:
                return "Closed door";
            case Terrain.OPEN_DOOR:
                return "Open door";
            case Terrain.ENTRANCE:
                return "Depth entrance";
            case Terrain.NORTH:
                return "North entrance";
            case Terrain.WEST:
                return "West entrance";
            case Terrain.SOUTH:
                return "The way South";
            case Terrain.EAST:
                return "The way East";
            case Terrain.EMBERS:
                return "Embers";
            case Terrain.LOCKED_DOOR:
                return "Locked door";
            case Terrain.PEDESTAL:
                //change to 102 for play build
                if (Dungeon.depth != 1){
                return "Tree";}
                else return  "Rubble";
            case Terrain.RUBBLE:
                return "Rubble";
            case Terrain.BED:
                return "Bed";
            case Terrain.CHAIR:
                return "Chair";
            case Terrain.TABLE:
                return "Table";
            case Terrain.DRESSER:
                return "Dresser";
            case Terrain.BARREL:
                return "Barrel";
            case Terrain.HIGH_GRASS:
                return "High grass";
            case Terrain.RUINED_WALL_E:
            case Terrain.RUINED_WALL_W:
            case Terrain.RUINED_WALL:
                return "Ruined wall.";
            case Terrain.SIGN:
                return "Sign";
            case Terrain.WELL:
                return "Well";
            case Terrain.HILL:
                return "Empty well";
            case Terrain.STATUE:
            case Terrain.STATUE_SP:
                return "Tracks";
            case Terrain.MOUNTAIN_N:
                return "A steep, rocky face extends to the south. It looks climbable.";
            case Terrain.MOUNTAIN_W:
                return "A steep, rocky face extends to the east. It looks climbable.";
            case Terrain.MOUNTAIN_E:
                return "A steep, rocky face extends to the west. It looks climbable.";
            case Terrain.TRIPWIRE:
                return "Alarm trap";
            case Terrain.SUMMONING_TRAP:
                return "Summoning trap";
            case Terrain.MOUNTAIN_S:
                return "A steep, rocky face extends to the north. It looks climbable.";
            case Terrain.BOOKSHELF:
                return "Bookshelf";
            case Terrain.ALCHEMY:
                return "Alchemy pot";
            case Terrain.GRADUAL_SLOPE:
                return "Gentle Slope";
            case Terrain.TRACKS:
                return "Tracks";
            default:
                return "???";
        }
    }



    protected void paintWater() {
        boolean[] lake = water();
        for (int i=0; i < LENGTH; i++) {
            if (map[i] == Terrain.EMPTY && lake[i]) {
                map[i] = Terrain.WATER;
            }
        }
    }
    protected abstract boolean[] water();

	
	public String tileDesc( int tile ) {
		
		switch (tile) {
		case Terrain.CHASM:
			return "You can't see the bottom.";
		case Terrain.WATER:
			return "In case of burning step into the water to extinguish the fire.";
        case Terrain.GRADUAL_SLOPE:
             return "A low hill.";
		case Terrain.ENTRANCE:
			return "You came from this direction.";
		case Terrain.EXIT:
            return "Stairs lead down to the lower depth.";
        case Terrain.RUINED_WALL_W:
        case Terrain.RUINED_WALL_E:
		case Terrain.RUINED_WALL:
			return "These walls look ancient. Their purpose is unclear." +
                    "All you know for sure is it looks impossible to climb.";
		case Terrain.EMBERS:
			return "Embers cover the floor.";
		case Terrain.HIGH_GRASS:
			return "Dense vegetation blocks the view.";
		case Terrain.LOCKED_DOOR:
			return "This door is locked, you need a matching key to unlock it.";
		case Terrain.RUBBLE:
			return "The wooden RUBBLE is firmly set but has dried over the years. Might it burn?";
		case Terrain.SIGN:
			return "You can't read the text from here.";
		case Terrain.MOUNTAIN_N:
        case Terrain.MOUNTAIN_S:
		case Terrain.MOUNTAIN_W:
		case Terrain.MOUNTAIN_E:
            case Terrain.MOUNTAIN_ELBOW_SE:
            case Terrain.MOUNTAIN_ELBOW_SW:
            case Terrain.MOUNTAIN_ELBOW_NE:
            case Terrain.MOUNTAIN_ELBOW_NW:
            return "This slope is too steep to simply walk up.";
		case Terrain.TRIPWIRE:
		case Terrain.SUMMONING_TRAP:
			return "Stepping onto a hidden pressure plate will activate the trap.";
		case Terrain.STATUE:
		case Terrain.STATUE_SP:
			return "Tracks indicating worn footwear and a heavy burden.";
		case Terrain.ALCHEMY:
			return "Drop some seeds here to cook a potion.";
		case Terrain.HILL:
			return "The summit.";
        case Terrain.TRACKS:
            return "Something has passed by here recently.";

		default:
            if (tile >= Terrain.GRADUAL_SLOPE_TILES) {
                return tileDesc( Terrain.GRADUAL_SLOPE );
            }
            if (tile >= Terrain.WATER_TILES) {
				return tileDesc( Terrain.WATER );
			}
			if ((Terrain.flags[tile] & Terrain.PIT) != 0) {
				return tileDesc( Terrain.CHASM );
			}
			return "";
		}
	}
}


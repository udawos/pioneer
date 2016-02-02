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
package com.udawos.pioneer.actors.mobs.npcs;

import com.udawos.noosa.audio.Sample;
import com.udawos.pioneer.Assets;
import com.udawos.pioneer.Challenges;
import com.udawos.pioneer.Dungeon;
import com.udawos.pioneer.Journal;
import com.udawos.pioneer.actors.Actor;
import com.udawos.pioneer.actors.Char;
import com.udawos.pioneer.actors.blobs.Blob;
import com.udawos.pioneer.actors.blobs.ParalyticGas;
import com.udawos.pioneer.actors.buffs.Buff;
import com.udawos.pioneer.actors.buffs.Paralysis;
import com.udawos.pioneer.actors.buffs.Roots;
import com.udawos.pioneer.actors.mobs.Mob;
import com.udawos.pioneer.items.Generator;
import com.udawos.pioneer.items.Item;
import com.udawos.pioneer.items.armor.Armor;
import com.udawos.pioneer.items.armor.ClothArmor;
import com.udawos.pioneer.items.weapon.Weapon;
import com.udawos.pioneer.items.weapon.missiles.MissileWeapon;
import com.udawos.pioneer.levels.Level;
import com.udawos.pioneer.scenes.GameScene;
import com.udawos.pioneer.sprites.GhostSprite;
import com.udawos.pioneer.sprites.GooSprite;
import com.udawos.pioneer.windows.WndOptions;
import com.udawos.pioneer.windows.WndQuest;
import com.udawos.utils.Bundle;
import com.udawos.utils.Random;

import java.util.HashSet;

public class Ghost extends NPC {

    {
        name = "sad ghost";
        spriteClass = GhostSprite.class;

    }

    private static final String TXT_CHASM	= "Sad Ghost";
    private static final String TXT_YES		= "Yes, I want to talk with you";
    private static final String TXT_NO		= "No, you are gross and dead.";
    private static final String TXT_JUMP 	=
            "Do you really want to talk with me? I am a dead ghost.";

    private static final String TXT_1	    = "Ghost";
    private static final String TXT_2		= "Do you know that there are four bunkers hidden in this land? Two are south of the mountains.";
    private static final String TXT_3		= "Golly";
    private static final String TXT_4 	    = "Gosh";


    private static final String TXT_ROSE1 =
            "I guess now we have nothing to talk about." +
                    " Unless you want to talk about my toenail collection.";

    private static final String TXT_ROSE2 =
            "Please... Help me... Find the rose...";



    public Ghost() {
        super();

        Sample.INSTANCE.load(Assets.SND_GHOST);
    }

    @Override
    public int defenseSkill(Char enemy) {
        return 1000;
    }

    @Override
    public String defenseVerb() {
        return "evaded";
    }

    @Override
    public float speed() {
        return 0.5f;
    }

    @Override
    protected Char chooseEnemy() {
        return null;
    }

    @Override
    public void damage(int dmg, Object src) {
    }

    @Override
    public void add(Buff buff) {
    }

    @Override
    public boolean reset() {
        return true;
    }

    public boolean chasmo = false;
    public boolean potato = false;

    @Override
    public void interact() {
        sprite.turnTo(pos, Dungeon.hero.pos);

        if( chasmo == false) {
            GameScene.show(
                    new WndOptions(TXT_CHASM, TXT_JUMP, TXT_YES, TXT_NO) {
                        @Override
                        protected void onSelect(int index) {
                            if (index == 0) {
                                chasmo = true;
                                GameScene.show(
                                        new WndOptions(TXT_1, TXT_2, TXT_3, TXT_4) {
                                            protected void onSelect(int index2) {
                                                if (index2 == 0) {
                                                    potato = true;
                                                    Dungeon.hero.resume();
                                                }
                                            }
                                        }
                                );
                            }
                        }
                    }
            );

        }
        else GameScene.show( new WndQuest(this, TXT_ROSE1));
    }

        /*Sample.INSTANCE.play(Assets.SND_GHOST);

        if (Quest.given) {

            Item item = Quest.alternative ?
                    Dungeon.hero.belongings.getItem(RatSkull.class) :
                    Dungeon.hero.belongings.getItem(DriedRose.class);
            if (item != null) {
                GameScene.show(new WndSadGhost(this, item));
            } else {
                GameScene.show(new WndQuest(this, TXT_ROSE2));

                int newPos = -1;
                for (int i = 0; i < 10; i++) {
                    newPos = Dungeon.level.randomRespawnCell();
                    if (newPos != -1) {
                        break;
                    }
                }
                if (newPos != -1) {

                    Actor.freeCell(pos);

                    CellEmitter.get(pos).start(Speck.factory(Speck.LIGHT), 0.2f, 3);
                    pos = newPos;
                    sprite.place(pos);
                    sprite.visible = Dungeon.visible[pos];
                }
            }

        } else {
            GameScene.show(new WndQuest(this, TXT_ROSE1));
            Quest.given = true;

            Journal.add(Journal.Feature.GHOST);
        }
        }*/

    @Override
    public String description() {
        return
                "The ghost is barely visible. It looks like a shapeless " +
                        "spot of faint light with a sorrowful face.";
    }

    private static final HashSet<Class<?>> IMMUNITIES = new HashSet<Class<?>>();

    static {
        IMMUNITIES.add(Paralysis.class);
        IMMUNITIES.add(Roots.class);
    }

    @Override
    public HashSet<Class<?>> immunities() {
        return IMMUNITIES;
    }

    public static class Quest {

        private static boolean spawned;

        private static boolean alternative;

        private static boolean given;

        private static boolean processed;

        private static int depth;

        private static int left2kill;

        public static Weapon weapon;
        public static Armor armor;

        public static void reset() {
            spawned = false;

            weapon = null;
            armor = null;
        }

        private static final String NODE = "sadGhost";

        private static final String SPAWNED = "spawned";
        private static final String ALTERNATIVE = "alternative";
        private static final String LEFT2KILL = "left2kill";
        private static final String GIVEN = "given";
        private static final String PROCESSED = "processed";
        private static final String DEPTH = "depth";
        private static final String WEAPON = "weapon";
        private static final String ARMOR = "armor";

        public static void storeInBundle(Bundle bundle) {

            Bundle node = new Bundle();

            node.put(SPAWNED, spawned);

            if (spawned) {

                node.put(ALTERNATIVE, alternative);
                if (!alternative) {
                    node.put(LEFT2KILL, left2kill);
                }

                node.put(GIVEN, given);
                node.put(DEPTH, depth);
                node.put(PROCESSED, processed);

                node.put(WEAPON, weapon);
                node.put(ARMOR, armor);
            }

            bundle.put(NODE, node);
        }

        public static void restoreFromBundle(Bundle bundle) {

            Bundle node = bundle.getBundle(NODE);

            if (!node.isNull() && (spawned = node.getBoolean(SPAWNED))) {

                alternative = node.getBoolean(ALTERNATIVE);
                if (!alternative) {
                    left2kill = node.getInt(LEFT2KILL);
                }

                given = node.getBoolean(GIVEN);
                depth = node.getInt(DEPTH);
                processed = node.getBoolean(PROCESSED);

                weapon = (Weapon) node.get(WEAPON);
                armor = (Armor) node.get(ARMOR);
            } else {
                reset();
            }
        }

        public static void spawn(Level level) {
            if (Dungeon.depth == 1) {

                Ghost npc = new Ghost();
                do {
                    npc.pos = 2375;
                } while (npc.pos == -1 || level.heaps.get(npc.pos) != null);
                level.mobs.add(npc);
                Actor.occupyCell(npc);

        }

            spawned = true;
            alternative = Random.Int(2) == 0;
            if (!alternative) {
                left2kill = 8;
            }

            given = false;
            processed = false;
            depth = Dungeon.depth;

            for (int i = 0; i < 4; i++) {
                Item another;
                do {
                    another = (Weapon) Generator.random(Generator.Category.WEAPON);
                } while (another instanceof MissileWeapon);

                if (weapon == null || another.level > weapon.level) {
                    weapon = (Weapon) another;
                }
            }

            if (Dungeon.isChallenged(Challenges.NO_ARMOR)) {
                armor = (Armor) new ClothArmor().degrade();
            } else {
                armor = (Armor) Generator.random(Generator.Category.ARMOR);
                for (int i = 0; i < 3; i++) {
                    Item another = Generator.random(Generator.Category.ARMOR);
                    if (another.level > armor.level) {
                        armor = (Armor) another;
                    }
                }
            }

            weapon.identify();
            armor.identify();
        }



        public static void process( int pos ) {
            if (spawned && given && !processed && (depth == Dungeon.depth)) {
                if (alternative) {

                    FetidRat rat = new FetidRat();
                    rat.pos = Dungeon.level.randomRespawnCell();
                    if (rat.pos != -1) {
                        GameScene.add( rat );
                        processed = true;
                    }

                } else {

                    if (Random.Int( left2kill ) == 0) {
                       // Dungeon.level.drop( new DriedRose(), pos ).sprite.drop();
                        processed = true;
                    } else {
                        left2kill--;
                    }

                }
            }
        }

        public static void complete() {
            weapon = null;
            armor = null;

            Journal.remove( Journal.Feature.GHOST );
        }
    }

    public static class FetidRat extends Mob {

        {
            name = "fetid rat";
            spriteClass = GooSprite.class;

            HP = HT = 15;
            defenseSkill = 5;

            EXP = 0;
            maxLvl = 5;

            state = WANDERING;
        }

        @Override
        public int damageRoll() {
            return Random.NormalIntRange( 2, 6 );
        }

        @Override
        public int attackSkill( Char target ) {
            return 12;
        }

        @Override
        public int dr() {
            return 2;
        }

        @Override
        public int defenseProc( Char enemy, int damage ) {

            GameScene.add( Blob.seed( pos, 20, ParalyticGas.class ) );

            return super.defenseProc(enemy, damage);
        }

        @Override
        public void die( Object cause ) {
            super.die( cause );

            //Dungeon.level.drop( new RatSkull(), pos ).sprite.drop();
        }

        @Override
        public String description() {
            return
                    "This marsupial rat is much larger, than a regular one. It is surrounded by a foul cloud.";
        }

        private static final HashSet<Class<?>> IMMUNITIES = new HashSet<Class<?>>();
        static {
            IMMUNITIES.add( Paralysis.class );
        }

        @Override
        public HashSet<Class<?>> immunities() {
            return IMMUNITIES;
        }
    }
}

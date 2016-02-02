
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

import com.udawos.pioneer.Dungeon;
import com.udawos.pioneer.Journal;
import com.udawos.pioneer.actors.Actor;
import com.udawos.pioneer.actors.Char;
import com.udawos.pioneer.actors.buffs.Buff;
import com.udawos.pioneer.actors.mobs.Mob;
import com.udawos.pioneer.items.Generator;
import com.udawos.pioneer.items.quest.DwarfToken;
import com.udawos.pioneer.items.rings.Ring;
import com.udawos.pioneer.levels.A01BaseLevel;
import com.udawos.pioneer.scenes.GameScene;
import com.udawos.pioneer.sprites.TransmitterSprite;
import com.udawos.pioneer.utils.Utils;
import com.udawos.pioneer.windows.WndQuest;
import com.udawos.pioneer.windows.WndRequisition;
import com.udawos.pioneer.windows.WndTransmitter;
import com.udawos.utils.Bundle;

//Make requisitioned supplies come in small drop pod-like chests
public class Transmitter extends NPC {

    {
        name = "transmitter";
        spriteClass = TransmitterSprite.class;
    }

    private static final String TXT_PEASANTSES1	=
            " " +
            "Communications established. We are pleased to hear of your survival." +
                    " Proceed with exploration of the region."+
                    " Supply requisitioning is now authorized. Out. ";

    private static final String TXT_PEASANTSES2	=
            "Supply requisitioning authorized.";

    private static final String TXT_CYA	= "See you, %s!";
    private static final String TXT_HEY	= "Zzzt";

    private boolean seenBefore = false;

    @Override
    protected boolean act() {

        if (!Quest.given && Dungeon.visible[pos]) {
            if (!seenBefore) {
                yell( Utils.format( TXT_HEY, Dungeon.hero.className() ) );
            }
            seenBefore = true;
        } else {
            seenBefore = false;
        }

        throwItem();

        return super.act();
    }

    @Override
    public int defenseSkill( Char enemy ) {
        return 1000;
    }

    @Override
    public String defenseVerb() {
        return "evaded";
    }

    @Override
    public void damage( int dmg, Object src ) {
    }

    @Override
    public void add( Buff buff ) {
    }

    @Override
    public boolean reset() {
        return true;
    }

    @Override
    public void interact() {

        sprite.turnTo( pos, Dungeon.hero.pos );
        if (Quest.given) {

            DwarfToken tokens = Dungeon.hero.belongings.getItem( DwarfToken.class );
            if (tokens != null && (tokens.quantity() >= 4 )) {
                GameScene.show( new WndTransmitter( this, tokens ) );
            } else {
                tell(TXT_PEASANTSES2, Dungeon.hero.className() );
                GameScene.show(new WndRequisition());
            }

        } else {
            tell(TXT_PEASANTSES1 );
            Quest.given = true;
            Quest.completed = false;

            Journal.add( Journal.Feature.FED );
        }
    }

    private void tell( String format, Object...args ) {
        GameScene.show(
                new WndQuest( this, Utils.format( format, args ) ) );
    }


    @Override
    public String description() {
        return
                "A Federation transmitter. It flickers unpleasantly. " +
                        "The device communicates remotely with another device exactly like it;" +
                " its perfect duplicate.";
    }

    public static class Quest {

        private static boolean alternative;

        private static boolean spawned;
        private static boolean given;
        private static boolean completed;
        private static boolean not;

        public static Ring reward;

        public static void reset() {
            spawned = false;

            reward = null;
        }

        private static final String NODE		= "demon";

        private static final String ALTERNATIVE	= "alternative";
        private static final String SPAWNED		= "spawned";
        private static final String GIVEN		= "given";
        private static final String COMPLETED	= "completed";
        private static final String REWARD		= "reward";
        private static final String NOT = "not";

        public static void storeInBundle( Bundle bundle ) {

            Bundle node = new Bundle();

            node.put( SPAWNED, spawned );

            if (spawned) {
                node.put( ALTERNATIVE, alternative );

                node.put( GIVEN, given );
                node.put(COMPLETED, completed);
                node.put( REWARD, reward );
            }
            else {
                node.put(NOT, not);
            }

            bundle.put( NODE, node );
        }

        public static void restoreFromBundle( Bundle bundle ) {

            Bundle node = bundle.getBundle(NODE);

            if (!node.isNull() && (spawned = node.getBoolean(SPAWNED))) {
                alternative = node.getBoolean(ALTERNATIVE);

                given = node.getBoolean(GIVEN);
                completed = node.getBoolean(COMPLETED);
                reward = (Ring) node.get(REWARD);
            } else {
                not = node.getBoolean(NOT);
            }
        }

        public static void spawn(A01BaseLevel level ) {
            if (Dungeon.depth == 1) {

                Transmitter npc = new Transmitter();
                do {
                    npc.pos = 1175;
                } while (npc.pos == -1 || level.heaps.get( npc.pos ) != null);
                level.mobs.add(npc);
                Actor.occupyCell( npc );

                spawned = true;

                given = false;

                do {
                    reward = (Ring) Generator.random(Generator.Category.RING);
                } while (reward.cursed);
                reward.upgrade( 5 );
                reward.cursed = true;
            }
        }

        public static void process( Mob mob ) {
            if (spawned && given && !completed) {

            }
        }

        public static void complete() {
            reward = null;
            completed = true;

            Journal.remove( Journal.Feature.FED );
        }

        public static boolean isNot() {

            return completed;
        }
    }


}

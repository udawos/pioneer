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
package com.udawos.pioneer.actors.hero;

import com.udawos.noosa.Camera;
import com.udawos.noosa.Game;
import com.udawos.noosa.audio.Sample;
import com.udawos.pioneer.Assets;
import com.udawos.pioneer.Badges;
import com.udawos.pioneer.Bones;
import com.udawos.pioneer.Dungeon;
import com.udawos.pioneer.GamesInProgress;
import com.udawos.pioneer.ResultDescriptions;
import com.udawos.pioneer.actors.Actor;
import com.udawos.pioneer.actors.Char;
import com.udawos.pioneer.actors.buffs.Barkskin;
import com.udawos.pioneer.actors.buffs.Bleeding;
import com.udawos.pioneer.actors.buffs.Blindness;
import com.udawos.pioneer.actors.buffs.Buff;
import com.udawos.pioneer.actors.buffs.Burning;
import com.udawos.pioneer.actors.buffs.Charm;
import com.udawos.pioneer.actors.buffs.Combo;
import com.udawos.pioneer.actors.buffs.Cripple;
import com.udawos.pioneer.actors.buffs.Fury;
import com.udawos.pioneer.actors.buffs.GasesImmunity;
import com.udawos.pioneer.actors.buffs.Hunger;
import com.udawos.pioneer.actors.buffs.Invisibility;
import com.udawos.pioneer.actors.buffs.Light;
import com.udawos.pioneer.actors.buffs.Ooze;
import com.udawos.pioneer.actors.buffs.Paralysis;
import com.udawos.pioneer.actors.buffs.Poison;
import com.udawos.pioneer.actors.buffs.Regeneration;
import com.udawos.pioneer.actors.buffs.Roots;
import com.udawos.pioneer.actors.buffs.SnipersMark;
import com.udawos.pioneer.actors.buffs.Stunned;
import com.udawos.pioneer.actors.buffs.Thirst;
import com.udawos.pioneer.actors.buffs.Weakness;
import com.udawos.pioneer.actors.mobs.Mob;
import com.udawos.pioneer.actors.mobs.npcs.NPC;
import com.udawos.pioneer.effects.CellEmitter;
import com.udawos.pioneer.effects.CheckedCell;
import com.udawos.pioneer.effects.Flare;
import com.udawos.pioneer.effects.Speck;
import com.udawos.pioneer.items.Amulet;
import com.udawos.pioneer.items.Ankh;
import com.udawos.pioneer.items.DewVial;
import com.udawos.pioneer.items.Dewdrop;
import com.udawos.pioneer.items.Heap;
import com.udawos.pioneer.items.Heap.Type;
import com.udawos.pioneer.items.Item;
import com.udawos.pioneer.items.KindOfWeapon;
import com.udawos.pioneer.items.armor.Armor;
import com.udawos.pioneer.items.keys.GoldenKey;
import com.udawos.pioneer.items.keys.IronKey;
import com.udawos.pioneer.items.keys.Key;
import com.udawos.pioneer.items.keys.MindKey;
import com.udawos.pioneer.items.keys.SwordKey;
import com.udawos.pioneer.items.potions.Potion;
import com.udawos.pioneer.items.potions.PotionOfMight;
import com.udawos.pioneer.items.potions.PotionOfStrength;
import com.udawos.pioneer.items.rings.RingOfAccuracy;
import com.udawos.pioneer.items.rings.RingOfDetection;
import com.udawos.pioneer.items.rings.RingOfElements;
import com.udawos.pioneer.items.rings.RingOfEvasion;
import com.udawos.pioneer.items.rings.RingOfHaste;
import com.udawos.pioneer.items.rings.RingOfShadows;
import com.udawos.pioneer.items.rings.RingOfThorns;
import com.udawos.pioneer.items.scrolls.Scroll;
import com.udawos.pioneer.items.scrolls.ScrollOfEnchantment;
import com.udawos.pioneer.items.scrolls.ScrollOfMagicMapping;
import com.udawos.pioneer.items.scrolls.ScrollOfRecharging;
import com.udawos.pioneer.items.scrolls.ScrollOfUpgrade;
import com.udawos.pioneer.items.wands.Wand;
import com.udawos.pioneer.items.weapon.melee.MeleeWeapon;
import com.udawos.pioneer.items.weapon.missiles.MissileWeapon;
import com.udawos.pioneer.levels.Level;
import com.udawos.pioneer.levels.Terrain;
import com.udawos.pioneer.levels.U2TheEye;
import com.udawos.pioneer.levels.features.AlchemyPot;
import com.udawos.pioneer.levels.features.Chasm;
import com.udawos.pioneer.levels.features.DropPods.DropPod;
import com.udawos.pioneer.levels.features.Sign;
import com.udawos.pioneer.mechanics.Ballistica;
import com.udawos.pioneer.plants.Earthroot;
import com.udawos.pioneer.scenes.CellSelector;
import com.udawos.pioneer.scenes.GameScene;
import com.udawos.pioneer.scenes.InterlevelScene;
import com.udawos.pioneer.scenes.SurfaceScene;
import com.udawos.pioneer.sprites.CharSprite;
import com.udawos.pioneer.sprites.HeroSprite;
import com.udawos.pioneer.ui.AttackIndicator;
import com.udawos.pioneer.ui.BuffIndicator;
import com.udawos.pioneer.utils.GLog;
import com.udawos.pioneer.windows.WndClimb;
import com.udawos.pioneer.windows.WndMessage;
import com.udawos.pioneer.windows.WndRequisition;
import com.udawos.pioneer.windows.WndResurrect;
import com.udawos.pioneer.windows.WndTradeItem;
import com.udawos.utils.Bundle;
import com.udawos.utils.Callback;
import com.udawos.utils.Random;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;

public class Hero extends Char {

	private static final String TXT_LEAVE = "This drop pod is a one-way ticket.";

	private static final String TXT_LEVEL_UP = "level up!";
	private static final String TXT_NEW_LEVEL =
		"Welcome to level %d! Now you are healthier and more focused. " +
		"It's easier for you to hit enemies and dodge their attacks.";

	public static final String TXT_YOU_NOW_HAVE	= "You now have %s";

	private static final String TXT_SOMETHING_ELSE	= "There is something else here";
	private static final String TXT_LOCKED_CHEST	= "This chest is locked and you don't have matching key";
	private static final String TXT_LOCKED_DOOR		= "You don't have a matching key";
	private static final String TXT_NOTICED_SMTH	= "You noticed something";

	private static final String TXT_WAIT	= "...";
	private static final String TXT_SEARCH	= "search";


	public static final int STARTING_STR = 10;
    public static final int STARTING_CLIMB_SKILL = 1;
    public static final int STARTING_HITROLL = 10;


	private static final float TIME_TO_REST		= 1f;
	private static final float TIME_TO_SEARCH	= 2f;

	public HeroClass heroClass = HeroClass.ROGUE;
	public HeroSubClass subClass = HeroSubClass.NONE;

	private int attackSkill = 10;
	private int defenseSkill = 5;


	public boolean ready = false;

	public HeroAction curAction = null;
	public HeroAction lastAction = null;

	private Char enemy;

	public Armor.Glyph killerGlyph = null;

	private Item theKey;


	public boolean restoreHealth = false;

	public MissileWeapon rangedWeapon = null;
	public Belongings belongings;

	public int STR;

    public static int hitRoll;

    public static int climbSkill;

	public boolean weakened = false;

	public float awareness;

	public int lvl = 1;
	public int exp = 0;

	private ArrayList<Mob> visibleEnemies;

	public Hero() {
		super();
		name = "you";

		HP = HT = 50;

        climbSkill = STARTING_CLIMB_SKILL;

		STR = STARTING_STR;
        hitRoll = STARTING_HITROLL;
		awareness = 0.01f;

		belongings = new Belongings( this );

		visibleEnemies = new ArrayList<Mob>();
	}

	public int STR() {
        return weakened ? STR - 2 : STR;
    }

    public int hitRoll() {
        return hitRoll;
    }

	private static final String ATTACK		= "attackSkill";
	private static final String DEFENSE		= "defenseSkill";
	private static final String STRENGTH	= "STR";
	private static final String HIT     	= "hitRoll";
	private static final String LEVEL		= "lvl";
	private static final String EXPERIENCE	= "exp";

	@Override
	public void storeInBundle( Bundle bundle ) {
		super.storeInBundle( bundle );

		heroClass.storeInBundle( bundle );
		subClass.storeInBundle( bundle );

		bundle.put( ATTACK, attackSkill );
		bundle.put(DEFENSE, defenseSkill );

		bundle.put(STRENGTH, STR);
		bundle.put(HIT, hitRoll);

		bundle.put( LEVEL, lvl );
		bundle.put( EXPERIENCE, exp );

		belongings.storeInBundle(bundle);
	}

	@Override
	public void restoreFromBundle( Bundle bundle ) {
		super.restoreFromBundle( bundle );

		heroClass = HeroClass.restoreInBundle( bundle );
		subClass = HeroSubClass.restoreInBundle( bundle );

		attackSkill = bundle.getInt( ATTACK );
		defenseSkill = bundle.getInt( DEFENSE );

		STR = bundle.getInt( STRENGTH );
		hitRoll = bundle.getInt( HIT );
		updateAwareness();

		lvl = bundle.getInt( LEVEL );
		exp = bundle.getInt( EXPERIENCE );

		belongings.restoreFromBundle(bundle);
	}

	public static void preview( GamesInProgress.Info info, Bundle bundle ) {
		info.level = bundle.getInt( LEVEL );
	}

	public String className() {
		return subClass == null || subClass == HeroSubClass.NONE ? heroClass.title() : subClass.title();
	}

	public void live() {
		Buff.affect( this, Regeneration.class );
		Buff.affect( this, Hunger.class );
        Buff.affect( this, Thirst.class);
	}

	public static int tier = 0;

	public boolean shoot( Char enemy, MissileWeapon wep ) {

		rangedWeapon = wep;
		boolean result = attack( enemy );
		rangedWeapon = null;

		return result;
	}

	@Override
	public int attackSkill( Char target ) {

		int bonus = 0;
		for (Buff buff : buffs( RingOfAccuracy.Accuracy.class )) {
			bonus += ((RingOfAccuracy.Accuracy)buff).level;
		}
		float accuracy = (bonus == 0) ? 1 : (float)Math.pow( 1.4, bonus );
		if (rangedWeapon != null && Level.distance( pos, target.pos ) == 1) {
			accuracy *= 0.5f;
		}

		KindOfWeapon wep = rangedWeapon != null ? rangedWeapon : belongings.weapon;
		if (wep != null) {
			return (int)(attackSkill * accuracy * wep.acuracyFactor( this ));
		} else {
			return (int)(attackSkill * accuracy);
		}
	}

	@Override
	public int defenseSkill( Char enemy ) {

		int bonus = 0;
		for (Buff buff : buffs( RingOfEvasion.Evasion.class )) {
			bonus += ((RingOfEvasion.Evasion)buff).level;
		}
		float evasion = bonus == 0 ? 1 : (float)Math.pow( 1.2, bonus );
		if (paralysed) {
			evasion /= 2;
		}

		int aEnc = belongings.armor != null ? belongings.armor.STR - STR() : 0;

		if (aEnc > 0) {
			return (int)(defenseSkill * evasion / Math.pow( 1.5, aEnc ));
		} else {

			if (heroClass == HeroClass.ROGUE) {

				if (curAction != null && subClass == HeroSubClass.FREERUNNER && !isStarving()) {
					evasion *= 2;
				}

				return (int)((defenseSkill - aEnc) * evasion);
			} else {
				return (int)(defenseSkill * evasion);
			}
		}
	}

	@Override
	public int dr() {
		int dr = belongings.armor != null ? Math.max( belongings.armor.DR, 0 ) : 0;
		Barkskin barkskin = buff( Barkskin.class );
		if (barkskin != null) {
			dr += barkskin.level();
		}
		return dr;
	}

	@Override
	public int damageRoll() {
		KindOfWeapon wep = rangedWeapon != null ? rangedWeapon : belongings.weapon;
		int dmg;
		if (wep != null) {
			dmg = wep.damageRoll( this );
		} else {
			dmg = STR() > 10 ? Random.IntRange( 1, STR() - 9 ) : 1;
		}
		return buff( Fury.class ) != null ? (int)(dmg * 1.5f) : dmg;
	}

	@Override
	public float speed() {

		int aEnc = belongings.armor != null ? belongings.armor.STR - STR() : 0;
		if (aEnc > 0) {

			return (float)(super.speed() * Math.pow( 1.3, -aEnc ));

		} else {

			float speed = super.speed();
			return ((HeroSprite)sprite).sprint( subClass == HeroSubClass.FREERUNNER && !isStarving() ) ? 1.6f * speed : speed;

		}
	}

	public float attackDelay() {
		KindOfWeapon wep = rangedWeapon != null ? rangedWeapon : belongings.weapon;
		if (wep != null) {

			return wep.speedFactor( this );

		} else {
			return 1f;
		}
	}

	@Override
	public void spend( float time ) {
		int hasteLevel = 0;
		for (Buff buff : buffs( RingOfHaste.Haste.class )) {
			hasteLevel += ((RingOfHaste.Haste) buff).level;
        }
        super.spend(hasteLevel == 0 ? time : (float) (time * Math.pow(1.1, -hasteLevel)));
	};

	public void spendAndNext( float time ) {
		busy();
		spend(time);
		next();
	}

	@Override
	public boolean act() {

        super.act();

        if (paralysed) {

            curAction = null;

            spendAndNext(TICK);
            return false;
        }

        checkVisibleMobs();
        AttackIndicator.updateState();

        if (curAction == null) {

            if (restoreHealth) {
                if (isStarving() || HP >= HT) {
                    restoreHealth = false;
                } else {
                    spend(TIME_TO_REST);
                    next();
                    return false;
                }
            }

            ready();
            return false;

        } else {

            restoreHealth = false;

            ready = false;

            if (curAction instanceof HeroAction.Move) {

                return actMove((HeroAction.Move) curAction);

            } else if (curAction instanceof HeroAction.Interact) {

                return actInteract((HeroAction.Interact) curAction);

            } else if (curAction instanceof HeroAction.Buy) {

                return actBuy((HeroAction.Buy) curAction);

            } else if (curAction instanceof HeroAction.PickUp) {

                return actPickUp((HeroAction.PickUp) curAction);

            } else if (curAction instanceof HeroAction.OpenChest) {

                return actOpenChest((HeroAction.OpenChest) curAction);

            } else if (curAction instanceof HeroAction.Unlock) {

                return actUnlock((HeroAction.Unlock) curAction);

            } else if (curAction instanceof HeroAction.North) {

                return actNorth((HeroAction.North) curAction);

            } else if (curAction instanceof HeroAction.South) {

                return actSouth((HeroAction.South) curAction);

            } else if (curAction instanceof HeroAction.West) {

                return actWest((HeroAction.West) curAction);

            } else if (curAction instanceof HeroAction.East) {

                return actEast((HeroAction.East) curAction);

            } else if (curAction instanceof HeroAction.Down) {

                return actDown((HeroAction.Down) curAction);
            } else if (curAction instanceof HeroAction.Up) {

                return actUp((HeroAction.Up) curAction);
            } else if (curAction instanceof HeroAction.Attack) {

                return actAttack((HeroAction.Attack) curAction);

            } else if (curAction instanceof HeroAction.Cook) {

                return actCook((HeroAction.Cook) curAction);

            }

            return false;
        }
    }

	public void busy() {
		ready = false;
	}

	private void ready() {
		sprite.idle();
		curAction = null;
		ready = true;

		GameScene.ready();
	}

	public void interrupt() {
		if (isAlive() && curAction != null && curAction.dst != pos) {
			lastAction = curAction;
		}
		curAction = null;
	}

	public void resume() {
		curAction = lastAction;
		lastAction = null;
		act();
	}

	private boolean actMove( HeroAction.Move action ) {

		if (getCloser(action.dst)) {

			return true;

		} else {
			if (Dungeon.level.map[pos] == Terrain.SIGN) {
				Sign.read(pos);
			}
			ready();

			return false;
		}
	}

	private boolean actInteract( HeroAction.Interact action ) {

		NPC npc = action.npc;

		if (Level.adjacent(pos, npc.pos)) {

			ready();
			sprite.turnTo( pos, npc.pos );
			npc.interact();
			return false;

		} else {

			if (Level.fieldOfView[npc.pos] && getCloser( npc.pos )) {

				return true;

			} else {
				ready();
				return false;
			}

		}
	}






            private boolean actBuy( HeroAction.Buy action ) {
		int dst = action.dst;
		if (pos == dst || Level.adjacent( pos, dst )) {

			ready();

			Heap heap = Dungeon.level.heaps.get( dst );
			if (heap != null && heap.type == Type.FOR_SALE && heap.size() == 1) {
				GameScene.show( new WndTradeItem( heap, true ) );
			}

			return false;

		} else if (getCloser( dst )) {

			return true;

		} else {
			ready();
			return false;
		}
	}

	private boolean actCook( HeroAction.Cook action ) {
		int dst = action.dst;
		if (Dungeon.visible[dst]) {

			ready();
			AlchemyPot.operate( this, dst );
			return false;

		} else if (getCloser( dst )) {

			return true;

		} else {
			ready();
			return false;
		}
	}

	private boolean actPickUp( HeroAction.PickUp action ) {
		int dst = action.dst;
		if (pos == dst) {

			Heap heap = Dungeon.level.heaps.get( pos );
			if (heap != null) {
				Item item = heap.pickUp();
				if (item.doPickUp( this )) {

					if (item instanceof Dewdrop) {
						// Do nothing
					} else {
						boolean important =
							((item instanceof ScrollOfUpgrade || item instanceof ScrollOfEnchantment) && ((Scroll)item).isKnown()) ||
							((item instanceof PotionOfStrength || item instanceof PotionOfMight) && ((Potion)item).isKnown());
						if (important) {
							GLog.p( TXT_YOU_NOW_HAVE, item.name() );
						} else {
							GLog.i( TXT_YOU_NOW_HAVE, item.name() );
						}
					}

					if (!heap.isEmpty()) {
						GLog.i( TXT_SOMETHING_ELSE );
					}
					curAction = null;
				} else {
					Dungeon.level.drop( item, pos ).sprite.drop();
					ready();
				}
			} else {
				ready();
			}

			return false;

		} else if (getCloser( dst )) {

			return true;

		} else {
			ready();
			return false;
		}
	}

	private boolean actOpenChest( HeroAction.OpenChest action ) {
		int dst = action.dst;
		if (Level.adjacent( pos, dst ) || pos == dst) {

			Heap heap = Dungeon.level.heaps.get( dst );
			if (heap != null && (heap.type != Type.HEAP && heap.type != Type.FOR_SALE)) {

				theKey = null;

				if (heap.type == Type.LOCKED_CHEST || heap.type == Type.CRYSTAL_CHEST) {

					theKey = belongings.getKey( GoldenKey.class, Dungeon.depth );

					if (theKey == null) {
						GLog.w( TXT_LOCKED_CHEST );
						ready();
						return false;
					}
				}

				switch (heap.type) {
				case TOMB:
					Sample.INSTANCE.play( Assets.SND_TOMB );
					Camera.main.shake( 1, 0.5f );
					break;
				case SKELETON:
					break;
				default:
					Sample.INSTANCE.play(Assets.SND_UNLOCK);
				}

				spend(Key.TIME_TO_UNLOCK);
				sprite.operate(dst);

			} else {
				ready();
			}

			return false;

		} else if (getCloser( dst )) {

			return true;

		} else {
			ready();
			return false;
		}
	}

	private boolean actUnlock( HeroAction.Unlock action ) {
		int doorCell = action.dst;
		if (Level.adjacent( pos, doorCell )) {

			theKey = null;
			int door = Dungeon.level.map[doorCell];

			if (door == Terrain.LOCKED_DOOR) {
                if (Dungeon.depth == 103) {
                    theKey = belongings.getKey( SwordKey.class, Dungeon.depth );
                }
                else if (Dungeon.depth == 105) {
                    theKey = belongings.getKey( MindKey.class, Dungeon.depth );
                }
                else
				theKey = belongings.getKey( IronKey.class, Dungeon.depth );


			}

			if (theKey != null) {

				spend( Key.TIME_TO_UNLOCK );
				sprite.operate(doorCell);

				Sample.INSTANCE.play(Assets.SND_UNLOCK);

			} else {
				GLog.w( TXT_LOCKED_DOOR );
				ready();
			}

			return false;

		} else if (getCloser( doorCell )) {

			return true;

		} else {
			ready();
			return false;
		}
	}



    private boolean actWest( HeroAction.West action ) {
        int path = action.dst;
        if (pos == path && Dungeon.depth == 1 && pos == Dungeon.level.west ) {


            curAction = null;
            InterlevelScene.mode = InterlevelScene.Mode.WEST1;
            Game.switchScene( InterlevelScene.class );

            return false;

        }
        if (pos == path && Dungeon.depth == 2 && pos == Dungeon.level.west ) {


            curAction = null;
            InterlevelScene.mode = InterlevelScene.Mode.WEST2;
            Game.switchScene( InterlevelScene.class );

            return false;

        }
        if (pos == path && Dungeon.depth == 3 && pos == Dungeon.level.west ) {


            curAction = null;
            InterlevelScene.mode = InterlevelScene.Mode.WEST3;
            Game.switchScene( InterlevelScene.class );

            return false;

        }
        if (pos == path && Dungeon.depth == 4 && pos == Dungeon.level.west ) {


            curAction = null;
            InterlevelScene.mode = InterlevelScene.Mode.WEST4;
            Game.switchScene( InterlevelScene.class );

            return false;

        }
        if (pos == path && Dungeon.depth == 5 && pos == Dungeon.level.west ) {


            curAction = null;
            InterlevelScene.mode = InterlevelScene.Mode.WEST5;
            Game.switchScene( InterlevelScene.class );

            return false;

        }
        if (pos == path && Dungeon.depth == 6 && pos == Dungeon.level.west ) {


            curAction = null;
            InterlevelScene.mode = InterlevelScene.Mode.WEST6;
            Game.switchScene( InterlevelScene.class );

            return false;

        }
        if (pos == path && Dungeon.depth == 7 && pos == Dungeon.level.west ) {


            curAction = null;
            InterlevelScene.mode = InterlevelScene.Mode.WEST7;
            Game.switchScene( InterlevelScene.class );

            return false;

        }
        if (pos == path && Dungeon.depth == 8 && pos == Dungeon.level.west ) {


            curAction = null;
            InterlevelScene.mode = InterlevelScene.Mode.WEST8;
            Game.switchScene( InterlevelScene.class );

            return false;

        }
        if (pos == path && Dungeon.depth == 9 && pos == Dungeon.level.west ) {


            curAction = null;
            InterlevelScene.mode = InterlevelScene.Mode.WEST9;
            Game.switchScene( InterlevelScene.class );

            return false;

        }
        if (pos == path && Dungeon.depth == 10 && pos == Dungeon.level.west ) {


            curAction = null;
            InterlevelScene.mode = InterlevelScene.Mode.WEST10;
            Game.switchScene( InterlevelScene.class );

            return false;

        }
        if (pos == path && Dungeon.depth == 11 && pos == Dungeon.level.west ) {


            curAction = null;
            InterlevelScene.mode = InterlevelScene.Mode.WEST11;
            Game.switchScene( InterlevelScene.class );

            return false;

        }
        if (pos == path && Dungeon.depth == 12 && pos == Dungeon.level.west ) {


            curAction = null;
            InterlevelScene.mode = InterlevelScene.Mode.WEST12;
            Game.switchScene( InterlevelScene.class );

            return false;

        }
        if (pos == path && Dungeon.depth == 13 && pos == Dungeon.level.west ) {


            curAction = null;
            InterlevelScene.mode = InterlevelScene.Mode.WEST13;
            Game.switchScene( InterlevelScene.class );

            return false;

        }
        if (pos == path && Dungeon.depth == 14 && pos == Dungeon.level.west ) {


            curAction = null;
            InterlevelScene.mode = InterlevelScene.Mode.WEST14;
            Game.switchScene( InterlevelScene.class );

            return false;

        }
        if (pos == path && Dungeon.depth == 15 && pos == Dungeon.level.west ) {


            curAction = null;
            InterlevelScene.mode = InterlevelScene.Mode.WEST15;
            Game.switchScene( InterlevelScene.class );

            return false;

        }
        if (pos == path && Dungeon.depth == 16 && pos == Dungeon.level.west ) {


            curAction = null;
            InterlevelScene.mode = InterlevelScene.Mode.WEST16;
            Game.switchScene( InterlevelScene.class );

            return false;

        }
        if (pos == path && Dungeon.depth == 17 && pos == Dungeon.level.west ) {


            curAction = null;
            InterlevelScene.mode = InterlevelScene.Mode.WEST17;
            Game.switchScene( InterlevelScene.class );

            return false;

        }
        if (pos == path && Dungeon.depth == 18 && pos == Dungeon.level.west ) {


            curAction = null;
            InterlevelScene.mode = InterlevelScene.Mode.WEST18;
            Game.switchScene( InterlevelScene.class );

            return false;

        }
        if (pos == path && Dungeon.depth == 19 && pos == Dungeon.level.west ) {


            curAction = null;
            InterlevelScene.mode = InterlevelScene.Mode.WEST19;
            Game.switchScene( InterlevelScene.class );

            return false;

        }
        if (pos == path && Dungeon.depth == 20 && pos == Dungeon.level.west ) {


            curAction = null;
            InterlevelScene.mode = InterlevelScene.Mode.WEST20;
            Game.switchScene( InterlevelScene.class );

            return false;

        }
        if (pos == path && Dungeon.depth == 21 && pos == Dungeon.level.west ) {


            curAction = null;
            InterlevelScene.mode = InterlevelScene.Mode.WEST21;
            Game.switchScene( InterlevelScene.class );

            return false;

        }
        if (pos == path && Dungeon.depth == 22 && pos == Dungeon.level.west ) {


            curAction = null;
            InterlevelScene.mode = InterlevelScene.Mode.WEST22;
            Game.switchScene( InterlevelScene.class );

            return false;

        }
        if (pos == path && Dungeon.depth == 23 && pos == Dungeon.level.west ) {


            curAction = null;
            InterlevelScene.mode = InterlevelScene.Mode.WEST23;
            Game.switchScene( InterlevelScene.class );

            return false;

        }
        if (pos == path && Dungeon.depth == 24 && pos == Dungeon.level.west ) {


            curAction = null;
            InterlevelScene.mode = InterlevelScene.Mode.WEST24;
            Game.switchScene( InterlevelScene.class );

            return false;

        }
        if (pos == path && Dungeon.depth == 25 && pos == Dungeon.level.west ) {


            curAction = null;
            InterlevelScene.mode = InterlevelScene.Mode.WEST25;
            Game.switchScene( InterlevelScene.class );

            return false;

        }
        if (pos == path && Dungeon.depth == 26 && pos == Dungeon.level.west ) {


            curAction = null;
            InterlevelScene.mode = InterlevelScene.Mode.WEST26;
            Game.switchScene( InterlevelScene.class );

            return false;

        }
        if (pos == path && Dungeon.depth == 27 && pos == Dungeon.level.west ) {


            curAction = null;
            InterlevelScene.mode = InterlevelScene.Mode.WEST27;
            Game.switchScene( InterlevelScene.class );

            return false;

        }
        if (pos == path && Dungeon.depth == 28 && pos == Dungeon.level.west ) {


            curAction = null;
            InterlevelScene.mode = InterlevelScene.Mode.WEST28;
            Game.switchScene( InterlevelScene.class );

            return false;

        }
        if (pos == path && Dungeon.depth == 29 && pos == Dungeon.level.west ) {


            curAction = null;
            InterlevelScene.mode = InterlevelScene.Mode.WEST29;
            Game.switchScene( InterlevelScene.class );

            return false;

        }
        if (pos == path && Dungeon.depth == 30 && pos == Dungeon.level.west ) {


            curAction = null;
            InterlevelScene.mode = InterlevelScene.Mode.WEST30;
            Game.switchScene( InterlevelScene.class );

            return false;

        }
        if (pos == path && Dungeon.depth == 31 && pos == Dungeon.level.west ) {


            curAction = null;
            InterlevelScene.mode = InterlevelScene.Mode.WEST31;
            Game.switchScene( InterlevelScene.class );

            return false;

        }
        if (pos == path && Dungeon.depth == 32 && pos == Dungeon.level.west ) {


            curAction = null;
            InterlevelScene.mode = InterlevelScene.Mode.WEST32;
            Game.switchScene( InterlevelScene.class );

            return false;

        }
        if (pos == path && Dungeon.depth == 33 && pos == Dungeon.level.west ) {


            curAction = null;
            InterlevelScene.mode = InterlevelScene.Mode.WEST33;
            Game.switchScene( InterlevelScene.class );

            return false;

        }
        if (pos == path && Dungeon.depth == 34 && pos == Dungeon.level.west ) {


            curAction = null;
            InterlevelScene.mode = InterlevelScene.Mode.WEST34;
            Game.switchScene( InterlevelScene.class );

            return false;

        }
        if (pos == path && Dungeon.depth == 35 && pos == Dungeon.level.west ) {


            curAction = null;
            InterlevelScene.mode = InterlevelScene.Mode.WEST35;
            Game.switchScene( InterlevelScene.class );

            return false;

        }
        if (pos == path && Dungeon.depth == 36 && pos == Dungeon.level.west ) {


            curAction = null;
            InterlevelScene.mode = InterlevelScene.Mode.WEST36;
            Game.switchScene( InterlevelScene.class );

            return false;

        }
        if (pos == path && Dungeon.depth == 37 && pos == Dungeon.level.west ) {


            curAction = null;
            InterlevelScene.mode = InterlevelScene.Mode.WEST37;
            Game.switchScene( InterlevelScene.class );

            return false;

        }
        if (pos == path && Dungeon.depth == 38 && pos == Dungeon.level.west ) {


            curAction = null;
            InterlevelScene.mode = InterlevelScene.Mode.WEST38;
            Game.switchScene( InterlevelScene.class );

            return false;

        }
        if (pos == path && Dungeon.depth == 39 && pos == Dungeon.level.west ) {


            curAction = null;
            InterlevelScene.mode = InterlevelScene.Mode.WEST39;
            Game.switchScene( InterlevelScene.class );

            return false;

        }
        if (pos == path && Dungeon.depth == 40 && pos == Dungeon.level.west ) {


            curAction = null;
            InterlevelScene.mode = InterlevelScene.Mode.WEST40;
            Game.switchScene( InterlevelScene.class );

            return false;

        }
        if (pos == path && Dungeon.depth == 41 && pos == Dungeon.level.west ) {


            curAction = null;
            InterlevelScene.mode = InterlevelScene.Mode.WEST41;
            Game.switchScene( InterlevelScene.class );

            return false;

        }
        if (pos == path && Dungeon.depth == 42 && pos == Dungeon.level.west ) {


            curAction = null;
            InterlevelScene.mode = InterlevelScene.Mode.WEST42;
            Game.switchScene( InterlevelScene.class );

            return false;

        }
        if (pos == path && Dungeon.depth == 43 && pos == Dungeon.level.west ) {


            curAction = null;
            InterlevelScene.mode = InterlevelScene.Mode.WEST43;
            Game.switchScene( InterlevelScene.class );

            return false;

        }
        if (pos == path && Dungeon.depth == 44 && pos == Dungeon.level.west ) {


            curAction = null;
            InterlevelScene.mode = InterlevelScene.Mode.WEST44;
            Game.switchScene( InterlevelScene.class );

            return false;

        }
        if (pos == path && Dungeon.depth == 45 && pos == Dungeon.level.west ) {


            curAction = null;
            InterlevelScene.mode = InterlevelScene.Mode.WEST45;
            Game.switchScene( InterlevelScene.class );

            return false;

        }
        if (pos == path && Dungeon.depth == 46 && pos == Dungeon.level.west ) {


            curAction = null;
            InterlevelScene.mode = InterlevelScene.Mode.WEST46;
            Game.switchScene( InterlevelScene.class );

            return false;

        }
        if (pos == path && Dungeon.depth == 47 && pos == Dungeon.level.west ) {


            curAction = null;
            InterlevelScene.mode = InterlevelScene.Mode.WEST47;
            Game.switchScene( InterlevelScene.class );

            return false;

        }
        if (pos == path && Dungeon.depth == 48 && pos == Dungeon.level.west ) {


            curAction = null;
            InterlevelScene.mode = InterlevelScene.Mode.WEST48;
            Game.switchScene( InterlevelScene.class );

            return false;

        }
        if (pos == path && Dungeon.depth == 49 && pos == Dungeon.level.west ) {


            curAction = null;
            InterlevelScene.mode = InterlevelScene.Mode.WEST49;
            Game.switchScene( InterlevelScene.class );

            return false;

        }
        if (pos == path && Dungeon.depth == 50 && pos == Dungeon.level.west ) {


            curAction = null;
            InterlevelScene.mode = InterlevelScene.Mode.WEST50;
            Game.switchScene( InterlevelScene.class );

            return false;

        }
        if (pos == path && Dungeon.depth == 51 && pos == Dungeon.level.west ) {


            curAction = null;
            InterlevelScene.mode = InterlevelScene.Mode.WEST51;
            Game.switchScene( InterlevelScene.class );

            return false;

        }
        if (pos == path && Dungeon.depth == 52 && pos == Dungeon.level.west ) {


            curAction = null;
            InterlevelScene.mode = InterlevelScene.Mode.WEST52;
            Game.switchScene( InterlevelScene.class );

            return false;

        }
        if (pos == path && Dungeon.depth == 53 && pos == Dungeon.level.west ) {


            curAction = null;
            InterlevelScene.mode = InterlevelScene.Mode.WEST53;
            Game.switchScene( InterlevelScene.class );

            return false;

        }
        if (pos == path && Dungeon.depth == 54 && pos == Dungeon.level.west ) {


            curAction = null;
            InterlevelScene.mode = InterlevelScene.Mode.WEST54;
            Game.switchScene( InterlevelScene.class );

            return false;

        }
        if (pos == path && Dungeon.depth == 55 && pos == Dungeon.level.west ) {


            curAction = null;
            InterlevelScene.mode = InterlevelScene.Mode.WEST55;
            Game.switchScene( InterlevelScene.class );

            return false;

        }
        if (pos == path && Dungeon.depth == 56 && pos == Dungeon.level.west ) {


            curAction = null;
            InterlevelScene.mode = InterlevelScene.Mode.WEST56;
            Game.switchScene( InterlevelScene.class );

            return false;

        }
        if (pos == path && Dungeon.depth == 57 && pos == Dungeon.level.west ) {


            curAction = null;
            InterlevelScene.mode = InterlevelScene.Mode.WEST57;
            Game.switchScene( InterlevelScene.class );

            return false;

        }
        if (pos == path && Dungeon.depth == 58 && pos == Dungeon.level.west ) {


            curAction = null;
            InterlevelScene.mode = InterlevelScene.Mode.WEST58;
            Game.switchScene( InterlevelScene.class );

            return false;

        }
        if (pos == path && Dungeon.depth == 59 && pos == Dungeon.level.west ) {


            curAction = null;
            InterlevelScene.mode = InterlevelScene.Mode.WEST59;
            Game.switchScene( InterlevelScene.class );

            return false;

        }
        if (pos == path && Dungeon.depth == 60 && pos == Dungeon.level.west ) {


            curAction = null;
            InterlevelScene.mode = InterlevelScene.Mode.WEST60;
            Game.switchScene( InterlevelScene.class );

            return false;
        }
        if (pos == path && Dungeon.depth == 61 && pos == Dungeon.level.west ) {


            curAction = null;
            InterlevelScene.mode = InterlevelScene.Mode.WEST61;
            Game.switchScene( InterlevelScene.class );

            return false;

        }
        if (pos == path && Dungeon.depth == 62 && pos == Dungeon.level.west ) {


            curAction = null;
            InterlevelScene.mode = InterlevelScene.Mode.WEST62;
            Game.switchScene( InterlevelScene.class );

            return false;

        }
        if (pos == path && Dungeon.depth == 63 && pos == Dungeon.level.west ) {


            curAction = null;
            InterlevelScene.mode = InterlevelScene.Mode.WEST63;
            Game.switchScene( InterlevelScene.class );

            return false;

        }
        if (pos == path && Dungeon.depth == 64 && pos == Dungeon.level.west ) {


            curAction = null;
            InterlevelScene.mode = InterlevelScene.Mode.WEST64;
            Game.switchScene( InterlevelScene.class );

            return false;

        }
        if (pos == path && Dungeon.depth == 65 && pos == Dungeon.level.west ) {


            curAction = null;
            InterlevelScene.mode = InterlevelScene.Mode.WEST65;
            Game.switchScene( InterlevelScene.class );

            return false;

        }
        if (pos == path && Dungeon.depth == 66 && pos == Dungeon.level.west ) {


            curAction = null;
            InterlevelScene.mode = InterlevelScene.Mode.WEST66;
            Game.switchScene( InterlevelScene.class );

            return false;

        }
        if (pos == path && Dungeon.depth == 67 && pos == Dungeon.level.west ) {


            curAction = null;
            InterlevelScene.mode = InterlevelScene.Mode.WEST67;
            Game.switchScene( InterlevelScene.class );

            return false;

        }
        if (pos == path && Dungeon.depth == 68 && pos == Dungeon.level.west ) {


            curAction = null;
            InterlevelScene.mode = InterlevelScene.Mode.WEST68;
            Game.switchScene( InterlevelScene.class );

            return false;

        }
        if (pos == path && Dungeon.depth == 69 && pos == Dungeon.level.west ) {


            curAction = null;
            InterlevelScene.mode = InterlevelScene.Mode.WEST69;
            Game.switchScene( InterlevelScene.class );

            return false;

        }
        if (pos == path && Dungeon.depth == 70 && pos == Dungeon.level.west ) {


            curAction = null;
            InterlevelScene.mode = InterlevelScene.Mode.WEST70;
            Game.switchScene( InterlevelScene.class );

            return false;

        }
        if (pos == path && Dungeon.depth == 71 && pos == Dungeon.level.west ) {


            curAction = null;
            InterlevelScene.mode = InterlevelScene.Mode.WEST71;
            Game.switchScene( InterlevelScene.class );

            return false;

        }
        if (pos == path && Dungeon.depth == 72 && pos == Dungeon.level.west ) {


            curAction = null;
            InterlevelScene.mode = InterlevelScene.Mode.WEST72;
            Game.switchScene( InterlevelScene.class );

            return false;

        }
        if (pos == path && Dungeon.depth == 73 && pos == Dungeon.level.west ) {


            curAction = null;
            InterlevelScene.mode = InterlevelScene.Mode.WEST73;
            Game.switchScene( InterlevelScene.class );

            return false;

        }
        if (pos == path && Dungeon.depth == 74 && pos == Dungeon.level.west ) {


            curAction = null;
            InterlevelScene.mode = InterlevelScene.Mode.WEST74;
            Game.switchScene( InterlevelScene.class );

            return false;

        }
        if (pos == path && Dungeon.depth == 75 && pos == Dungeon.level.west ) {


            curAction = null;
            InterlevelScene.mode = InterlevelScene.Mode.WEST75;
            Game.switchScene( InterlevelScene.class );

            return false;

        }
        if (pos == path && Dungeon.depth == 76 && pos == Dungeon.level.west ) {


            curAction = null;
            InterlevelScene.mode = InterlevelScene.Mode.WEST76;
            Game.switchScene( InterlevelScene.class );

            return false;

        }
        if (pos == path && Dungeon.depth == 77 && pos == Dungeon.level.west ) {


            curAction = null;
            InterlevelScene.mode = InterlevelScene.Mode.WEST77;
            Game.switchScene( InterlevelScene.class );

            return false;

        }
        if (pos == path && Dungeon.depth == 78 && pos == Dungeon.level.west ) {


            curAction = null;
            InterlevelScene.mode = InterlevelScene.Mode.WEST78;
            Game.switchScene( InterlevelScene.class );

            return false;

        }
        if (pos == path && Dungeon.depth == 79 && pos == Dungeon.level.west ) {


            curAction = null;
            InterlevelScene.mode = InterlevelScene.Mode.WEST79;
            Game.switchScene( InterlevelScene.class );

            return false;

        }
        if (pos == path && Dungeon.depth == 80 && pos == Dungeon.level.west ) {


            curAction = null;
            InterlevelScene.mode = InterlevelScene.Mode.WEST80;
            Game.switchScene( InterlevelScene.class );

            return false;

        }
        if (pos == path && Dungeon.depth == 81 && pos == Dungeon.level.west ) {


            curAction = null;
            InterlevelScene.mode = InterlevelScene.Mode.WEST81;
            Game.switchScene( InterlevelScene.class );

            return false;

        }
        if (pos == path && Dungeon.depth == 82 && pos == Dungeon.level.west ) {


            curAction = null;
            InterlevelScene.mode = InterlevelScene.Mode.WEST82;
            Game.switchScene( InterlevelScene.class );

            return false;

        }
        if (pos == path && Dungeon.depth == 83 && pos == Dungeon.level.west ) {


            curAction = null;
            InterlevelScene.mode = InterlevelScene.Mode.WEST83;
            Game.switchScene( InterlevelScene.class );

            return false;

        }
        if (pos == path && Dungeon.depth == 84 && pos == Dungeon.level.west ) {


            curAction = null;
            InterlevelScene.mode = InterlevelScene.Mode.WEST84;
            Game.switchScene( InterlevelScene.class );

            return false;

        }
        if (pos == path && Dungeon.depth == 85 && pos == Dungeon.level.west ) {


            curAction = null;
            InterlevelScene.mode = InterlevelScene.Mode.WEST85;
            Game.switchScene( InterlevelScene.class );

            return false;

        }
        if (pos == path && Dungeon.depth == 86 && pos == Dungeon.level.west ) {


            curAction = null;
            InterlevelScene.mode = InterlevelScene.Mode.WEST86;
            Game.switchScene( InterlevelScene.class );

            return false;

        }
        if (pos == path && Dungeon.depth == 87 && pos == Dungeon.level.west ) {


            curAction = null;
            InterlevelScene.mode = InterlevelScene.Mode.WEST87;
            Game.switchScene( InterlevelScene.class );

            return false;

        }
        if (pos == path && Dungeon.depth == 88 && pos == Dungeon.level.west ) {


            curAction = null;
            InterlevelScene.mode = InterlevelScene.Mode.WEST88;
            Game.switchScene( InterlevelScene.class );

            return false;

        }
        if (pos == path && Dungeon.depth == 89 && pos == Dungeon.level.west ) {


            curAction = null;
            InterlevelScene.mode = InterlevelScene.Mode.WEST89;
            Game.switchScene( InterlevelScene.class );

            return false;

        }
        if (pos == path && Dungeon.depth == 90 && pos == Dungeon.level.west ) {


            curAction = null;
            InterlevelScene.mode = InterlevelScene.Mode.WEST90;
            Game.switchScene( InterlevelScene.class );

            return false;

        }
        if (pos == path && Dungeon.depth == 91 && pos == Dungeon.level.west ) {


            curAction = null;
            InterlevelScene.mode = InterlevelScene.Mode.WEST91;
            Game.switchScene( InterlevelScene.class );

            return false;

        }
        if (pos == path && Dungeon.depth == 92 && pos == Dungeon.level.west ) {


            curAction = null;
            InterlevelScene.mode = InterlevelScene.Mode.WEST92;
            Game.switchScene( InterlevelScene.class );

            return false;

        }
        if (pos == path && Dungeon.depth == 93 && pos == Dungeon.level.west ) {


            curAction = null;
            InterlevelScene.mode = InterlevelScene.Mode.WEST93;
            Game.switchScene( InterlevelScene.class );

            return false;

        }
        if (pos == path && Dungeon.depth == 94 && pos == Dungeon.level.west ) {


            curAction = null;
            InterlevelScene.mode = InterlevelScene.Mode.WEST94;
            Game.switchScene( InterlevelScene.class );

            return false;

        }
        if (pos == path && Dungeon.depth == 95 && pos == Dungeon.level.west ) {


            curAction = null;
            InterlevelScene.mode = InterlevelScene.Mode.WEST95;
            Game.switchScene( InterlevelScene.class );

            return false;

        }
        if (pos == path && Dungeon.depth == 96 && pos == Dungeon.level.west ) {


            curAction = null;
            InterlevelScene.mode = InterlevelScene.Mode.WEST96;
            Game.switchScene( InterlevelScene.class );

            return false;

        }
        if (pos == path && Dungeon.depth == 97 && pos == Dungeon.level.west ) {


            curAction = null;
            InterlevelScene.mode = InterlevelScene.Mode.WEST97;
            Game.switchScene( InterlevelScene.class );

            return false;

        }
        if (pos == path && Dungeon.depth == 98 && pos == Dungeon.level.west ) {


            curAction = null;
            InterlevelScene.mode = InterlevelScene.Mode.WEST98;
            Game.switchScene( InterlevelScene.class );

            return false;

        }
        if (pos == path && Dungeon.depth == 99 && pos == Dungeon.level.west ) {


            curAction = null;
            InterlevelScene.mode = InterlevelScene.Mode.WEST99;
            Game.switchScene( InterlevelScene.class );

            return false;

        }
        else if (getCloser( path )) {

            return true;

        } else {
            ready();
            return false;
        }
    }



    private boolean actNorth( HeroAction.North action ) {
        int path = action.dst;
        if (pos == path && Dungeon.depth == 1 && pos == Dungeon.level.north) {


            curAction = null;
            InterlevelScene.mode = InterlevelScene.Mode.NORTH1;
            Game.switchScene(InterlevelScene.class);

            return false;

        }
        if (pos == path && Dungeon.depth == 2 && pos == Dungeon.level.north) {


            curAction = null;
            InterlevelScene.mode = InterlevelScene.Mode.NORTH2;
            Game.switchScene(InterlevelScene.class);

            return false;

        }
        if (pos == path && Dungeon.depth == 3 && pos == Dungeon.level.north) {


            curAction = null;
            InterlevelScene.mode = InterlevelScene.Mode.NORTH3;
            Game.switchScene(InterlevelScene.class);

            return false;

        }
        if (pos == path && Dungeon.depth == 4 && pos == Dungeon.level.north) {


            curAction = null;
            InterlevelScene.mode = InterlevelScene.Mode.NORTH4;
            Game.switchScene(InterlevelScene.class);

            return false;

        }
        if (pos == path && Dungeon.depth == 5 && pos == Dungeon.level.north) {


            curAction = null;
            InterlevelScene.mode = InterlevelScene.Mode.NORTH5;
            Game.switchScene(InterlevelScene.class);

            return false;

        }
        if (pos == path && Dungeon.depth == 6 && pos == Dungeon.level.north) {


            curAction = null;
            InterlevelScene.mode = InterlevelScene.Mode.NORTH6;
            Game.switchScene(InterlevelScene.class);

            return false;

        }
        if (pos == path && Dungeon.depth == 7 && pos == Dungeon.level.north) {


            curAction = null;
            InterlevelScene.mode = InterlevelScene.Mode.NORTH7;
            Game.switchScene(InterlevelScene.class);

            return false;

        }
        if (pos == path && Dungeon.depth == 8 && pos == Dungeon.level.north) {


            curAction = null;
            InterlevelScene.mode = InterlevelScene.Mode.NORTH8;
            Game.switchScene(InterlevelScene.class);

            return false;

        }
        if (pos == path && Dungeon.depth == 9 && pos == Dungeon.level.north) {


            curAction = null;
            InterlevelScene.mode = InterlevelScene.Mode.NORTH9;
            Game.switchScene(InterlevelScene.class);

            return false;

        }
        if (pos == path && Dungeon.depth == 10 && pos == Dungeon.level.north) {


            curAction = null;
            InterlevelScene.mode = InterlevelScene.Mode.NORTH10;
            Game.switchScene(InterlevelScene.class);

            return false;

        }
        if (pos == path && Dungeon.depth == 11 && pos == Dungeon.level.north) {


            curAction = null;
            InterlevelScene.mode = InterlevelScene.Mode.NORTH11;
            Game.switchScene(InterlevelScene.class);

            return false;


        }
        if (pos == path && Dungeon.depth == 12 && pos == Dungeon.level.north) {


            curAction = null;
            InterlevelScene.mode = InterlevelScene.Mode.NORTH12;
            Game.switchScene(InterlevelScene.class);

            return false;

        }
        if (pos == path && Dungeon.depth == 13 && pos == Dungeon.level.north) {


            curAction = null;
            InterlevelScene.mode = InterlevelScene.Mode.NORTH13;
            Game.switchScene(InterlevelScene.class);

            return false;

        }
        if (pos == path && Dungeon.depth == 14 && pos == Dungeon.level.north) {


            curAction = null;
            InterlevelScene.mode = InterlevelScene.Mode.NORTH14;
            Game.switchScene(InterlevelScene.class);

            return false;

        }
        if (pos == path && Dungeon.depth == 15 && pos == Dungeon.level.north) {


            curAction = null;
            InterlevelScene.mode = InterlevelScene.Mode.NORTH15;
            Game.switchScene(InterlevelScene.class);

            return false;

        }
        if (pos == path && Dungeon.depth == 16 && pos == Dungeon.level.north) {


            curAction = null;
            InterlevelScene.mode = InterlevelScene.Mode.NORTH16;
            Game.switchScene(InterlevelScene.class);

            return false;

        }
        if (pos == path && Dungeon.depth == 17 && pos == Dungeon.level.north) {


            curAction = null;
            InterlevelScene.mode = InterlevelScene.Mode.NORTH17;
            Game.switchScene(InterlevelScene.class);

            return false;

        }
        if (pos == path && Dungeon.depth == 18 && pos == Dungeon.level.north) {


            curAction = null;
            InterlevelScene.mode = InterlevelScene.Mode.NORTH18;
            Game.switchScene(InterlevelScene.class);

            return false;

        }
        if (pos == path && Dungeon.depth == 19 && pos == Dungeon.level.north) {


            curAction = null;
            InterlevelScene.mode = InterlevelScene.Mode.NORTH19;
            Game.switchScene(InterlevelScene.class);

            return false;

        }
        if (pos == path && Dungeon.depth == 20 && pos == Dungeon.level.north) {


            curAction = null;
            InterlevelScene.mode = InterlevelScene.Mode.NORTH20;
            Game.switchScene(InterlevelScene.class);

            return false;

        }
        if (pos == path && Dungeon.depth == 21 && pos == Dungeon.level.north) {


            curAction = null;
            InterlevelScene.mode = InterlevelScene.Mode.NORTH21;
            Game.switchScene(InterlevelScene.class);

            return false;


        }
        if (pos == path && Dungeon.depth == 22 && pos == Dungeon.level.north) {


            curAction = null;
            InterlevelScene.mode = InterlevelScene.Mode.NORTH22;
            Game.switchScene(InterlevelScene.class);

            return false;

        }
        if (pos == path && Dungeon.depth == 23 && pos == Dungeon.level.north) {


            curAction = null;
            InterlevelScene.mode = InterlevelScene.Mode.NORTH23;
            Game.switchScene(InterlevelScene.class);

            return false;

        }
        if (pos == path && Dungeon.depth == 24 && pos == Dungeon.level.north) {


            curAction = null;
            InterlevelScene.mode = InterlevelScene.Mode.NORTH24;
            Game.switchScene(InterlevelScene.class);

            return false;

        }
        if (pos == path && Dungeon.depth ==25 && pos == Dungeon.level.north) {


            curAction = null;
            InterlevelScene.mode = InterlevelScene.Mode.NORTH25;
            Game.switchScene(InterlevelScene.class);

            return false;

        }
        if (pos == path && Dungeon.depth == 26 && pos == Dungeon.level.north) {


            curAction = null;
            InterlevelScene.mode = InterlevelScene.Mode.NORTH26;
            Game.switchScene(InterlevelScene.class);

            return false;

        }
        if (pos == path && Dungeon.depth == 27 && pos == Dungeon.level.north) {


            curAction = null;
            InterlevelScene.mode = InterlevelScene.Mode.NORTH27;
            Game.switchScene(InterlevelScene.class);

            return false;

        }
        if (pos == path && Dungeon.depth == 28 && pos == Dungeon.level.north) {


            curAction = null;
            InterlevelScene.mode = InterlevelScene.Mode.NORTH28;
            Game.switchScene(InterlevelScene.class);

            return false;

        }
        if (pos == path && Dungeon.depth == 29 && pos == Dungeon.level.north) {


            curAction = null;
            InterlevelScene.mode = InterlevelScene.Mode.NORTH29;
            Game.switchScene(InterlevelScene.class);

            return false;

        }
        if (pos == path && Dungeon.depth == 30 && pos == Dungeon.level.north) {


            curAction = null;
            InterlevelScene.mode = InterlevelScene.Mode.NORTH30;
            Game.switchScene(InterlevelScene.class);

            return false;

        }
        if (pos == path && Dungeon.depth == 31 && pos == Dungeon.level.north) {


            curAction = null;
            InterlevelScene.mode = InterlevelScene.Mode.NORTH31;
            Game.switchScene(InterlevelScene.class);

            return false;


        }
        if (pos == path && Dungeon.depth == 32 && pos == Dungeon.level.north) {


            curAction = null;
            InterlevelScene.mode = InterlevelScene.Mode.NORTH32;
            Game.switchScene(InterlevelScene.class);

            return false;

        }
        if (pos == path && Dungeon.depth == 33 && pos == Dungeon.level.north) {


            curAction = null;
            InterlevelScene.mode = InterlevelScene.Mode.NORTH33;
            Game.switchScene(InterlevelScene.class);

            return false;

        }
        if (pos == path && Dungeon.depth == 34 && pos == Dungeon.level.north) {


            curAction = null;
            InterlevelScene.mode = InterlevelScene.Mode.NORTH34;
            Game.switchScene(InterlevelScene.class);

            return false;

        }
        if (pos == path && Dungeon.depth == 35 && pos == Dungeon.level.north) {


            curAction = null;
            InterlevelScene.mode = InterlevelScene.Mode.NORTH35;
            Game.switchScene(InterlevelScene.class);

            return false;

        }
        if (pos == path && Dungeon.depth == 36 && pos == Dungeon.level.north) {


            curAction = null;
            InterlevelScene.mode = InterlevelScene.Mode.NORTH36;
            Game.switchScene(InterlevelScene.class);

            return false;

        }
        if (pos == path && Dungeon.depth == 37 && pos == Dungeon.level.north) {


            curAction = null;
            InterlevelScene.mode = InterlevelScene.Mode.NORTH37;
            Game.switchScene(InterlevelScene.class);

            return false;

        }
        if (pos == path && Dungeon.depth == 38 && pos == Dungeon.level.north) {


            curAction = null;
            InterlevelScene.mode = InterlevelScene.Mode.NORTH38;
            Game.switchScene(InterlevelScene.class);

            return false;

        }
        if (pos == path && Dungeon.depth == 39 && pos == Dungeon.level.north) {


            curAction = null;
            InterlevelScene.mode = InterlevelScene.Mode.NORTH39;
            Game.switchScene(InterlevelScene.class);

            return false;

        }
        if (pos == path && Dungeon.depth == 40 && pos == Dungeon.level.north) {


            curAction = null;
            InterlevelScene.mode = InterlevelScene.Mode.NORTH40;
            Game.switchScene(InterlevelScene.class);

            return false;

        }
        if (pos == path && Dungeon.depth == 41 && pos == Dungeon.level.north) {


            curAction = null;
            InterlevelScene.mode = InterlevelScene.Mode.NORTH41;
            Game.switchScene(InterlevelScene.class);

            return false;

        }
        if (pos == path && Dungeon.depth == 42 && pos == Dungeon.level.north) {


            curAction = null;
            InterlevelScene.mode = InterlevelScene.Mode.NORTH42;
            Game.switchScene(InterlevelScene.class);

            return false;

        }
        if (pos == path && Dungeon.depth == 43 && pos == Dungeon.level.north) {


            curAction = null;
            InterlevelScene.mode = InterlevelScene.Mode.NORTH43;
            Game.switchScene(InterlevelScene.class);

            return false;

        }
        if (pos == path && Dungeon.depth == 44 && pos == Dungeon.level.north) {


            curAction = null;
            InterlevelScene.mode = InterlevelScene.Mode.NORTH44;
            Game.switchScene(InterlevelScene.class);

            return false;

        }
        if (pos == path && Dungeon.depth == 45 && pos == Dungeon.level.north) {


            curAction = null;
            InterlevelScene.mode = InterlevelScene.Mode.NORTH45;
            Game.switchScene(InterlevelScene.class);

            return false;

        }
        if (pos == path && Dungeon.depth == 46 && pos == Dungeon.level.north) {


            curAction = null;
            InterlevelScene.mode = InterlevelScene.Mode.NORTH46;
            Game.switchScene(InterlevelScene.class);

            return false;

        }
        if (pos == path && Dungeon.depth == 47 && pos == Dungeon.level.north) {


            curAction = null;
            InterlevelScene.mode = InterlevelScene.Mode.NORTH47;
            Game.switchScene(InterlevelScene.class);

            return false;

        }
        if (pos == path && Dungeon.depth == 48 && pos == Dungeon.level.north) {


            curAction = null;
            InterlevelScene.mode = InterlevelScene.Mode.NORTH48;
            Game.switchScene(InterlevelScene.class);

            return false;

        }
        if (pos == path && Dungeon.depth == 49 && pos == Dungeon.level.north) {


            curAction = null;
            InterlevelScene.mode = InterlevelScene.Mode.NORTH49;
            Game.switchScene(InterlevelScene.class);

            return false;

        }
        if (pos == path && Dungeon.depth == 50 && pos == Dungeon.level.north) {


            curAction = null;
            InterlevelScene.mode = InterlevelScene.Mode.NORTH50;
            Game.switchScene(InterlevelScene.class);

            return false;

        }
        if (pos == path && Dungeon.depth == 51 && pos == Dungeon.level.north) {


            curAction = null;
            InterlevelScene.mode = InterlevelScene.Mode.NORTH51;
            Game.switchScene(InterlevelScene.class);

            return false;

        }
        if (pos == path && Dungeon.depth == 52 && pos == Dungeon.level.north) {


            curAction = null;
            InterlevelScene.mode = InterlevelScene.Mode.NORTH52;
            Game.switchScene(InterlevelScene.class);

            return false;

        }
        if (pos == path && Dungeon.depth == 53 && pos == Dungeon.level.north) {


            curAction = null;
            InterlevelScene.mode = InterlevelScene.Mode.NORTH53;
            Game.switchScene(InterlevelScene.class);

            return false;

        }
        if (pos == path && Dungeon.depth == 54 && pos == Dungeon.level.north) {


            curAction = null;
            InterlevelScene.mode = InterlevelScene.Mode.NORTH54;
            Game.switchScene(InterlevelScene.class);

            return false;

        }
        if (pos == path && Dungeon.depth == 55 && pos == Dungeon.level.north) {


            curAction = null;
            InterlevelScene.mode = InterlevelScene.Mode.NORTH55;
            Game.switchScene(InterlevelScene.class);

            return false;

        }
        if (pos == path && Dungeon.depth == 56 && pos == Dungeon.level.north) {


            curAction = null;
            InterlevelScene.mode = InterlevelScene.Mode.NORTH56;
            Game.switchScene(InterlevelScene.class);

            return false;

        }
        if (pos == path && Dungeon.depth == 57 && pos == Dungeon.level.north) {


            curAction = null;
            InterlevelScene.mode = InterlevelScene.Mode.NORTH57;
            Game.switchScene(InterlevelScene.class);

            return false;

        }
        if (pos == path && Dungeon.depth == 58 && pos == Dungeon.level.north) {


            curAction = null;
            InterlevelScene.mode = InterlevelScene.Mode.NORTH58;
            Game.switchScene(InterlevelScene.class);

            return false;

        }
        if (pos == path && Dungeon.depth == 59 && pos == Dungeon.level.north) {


            curAction = null;
            InterlevelScene.mode = InterlevelScene.Mode.NORTH59;
            Game.switchScene(InterlevelScene.class);

            return false;

        }
        if (pos == path && Dungeon.depth == 60 && pos == Dungeon.level.north) {


            curAction = null;
            InterlevelScene.mode = InterlevelScene.Mode.NORTH60;
            Game.switchScene(InterlevelScene.class);

            return false;

        }
        if (pos == path && Dungeon.depth == 61 && pos == Dungeon.level.north) {


            curAction = null;
            InterlevelScene.mode = InterlevelScene.Mode.NORTH61;
            Game.switchScene(InterlevelScene.class);

            return false;


        }
        if (pos == path && Dungeon.depth == 62 && pos == Dungeon.level.north) {


            curAction = null;
            InterlevelScene.mode = InterlevelScene.Mode.NORTH62;
            Game.switchScene(InterlevelScene.class);

            return false;

        }
        if (pos == path && Dungeon.depth == 63 && pos == Dungeon.level.north) {


            curAction = null;
            InterlevelScene.mode = InterlevelScene.Mode.NORTH63;
            Game.switchScene(InterlevelScene.class);

            return false;

        }
        if (pos == path && Dungeon.depth == 64 && pos == Dungeon.level.north) {


            curAction = null;
            InterlevelScene.mode = InterlevelScene.Mode.NORTH64;
            Game.switchScene(InterlevelScene.class);

            return false;

        }
        if (pos == path && Dungeon.depth ==65 && pos == Dungeon.level.north) {


            curAction = null;
            InterlevelScene.mode = InterlevelScene.Mode.NORTH65;
            Game.switchScene(InterlevelScene.class);

            return false;

        }
        if (pos == path && Dungeon.depth == 66 && pos == Dungeon.level.north) {


            curAction = null;
            InterlevelScene.mode = InterlevelScene.Mode.NORTH66;
            Game.switchScene(InterlevelScene.class);

            return false;

        }
        if (pos == path && Dungeon.depth == 67 && pos == Dungeon.level.north) {


            curAction = null;
            InterlevelScene.mode = InterlevelScene.Mode.NORTH67;
            Game.switchScene(InterlevelScene.class);

            return false;

        }
        if (pos == path && Dungeon.depth == 68 && pos == Dungeon.level.north) {


            curAction = null;
            InterlevelScene.mode = InterlevelScene.Mode.NORTH68;
            Game.switchScene(InterlevelScene.class);

            return false;

        }
        if (pos == path && Dungeon.depth == 69 && pos == Dungeon.level.north) {


            curAction = null;
            InterlevelScene.mode = InterlevelScene.Mode.NORTH69;
            Game.switchScene(InterlevelScene.class);

            return false;

        }
        if (pos == path && Dungeon.depth == 70 && pos == Dungeon.level.north) {


            curAction = null;
            InterlevelScene.mode = InterlevelScene.Mode.NORTH70;
            Game.switchScene(InterlevelScene.class);

            return false;

        }
        if (pos == path && Dungeon.depth == 71 && pos == Dungeon.level.north) {


            curAction = null;
            InterlevelScene.mode = InterlevelScene.Mode.NORTH71;
            Game.switchScene(InterlevelScene.class);

            return false;


        }
        if (pos == path && Dungeon.depth == 72 && pos == Dungeon.level.north) {


            curAction = null;
            InterlevelScene.mode = InterlevelScene.Mode.NORTH72;
            Game.switchScene(InterlevelScene.class);

            return false;

        }
        if (pos == path && Dungeon.depth == 73 && pos == Dungeon.level.north) {


            curAction = null;
            InterlevelScene.mode = InterlevelScene.Mode.NORTH73;
            Game.switchScene(InterlevelScene.class);

            return false;

        }
        if (pos == path && Dungeon.depth == 74 && pos == Dungeon.level.north) {


            curAction = null;
            InterlevelScene.mode = InterlevelScene.Mode.NORTH74;
            Game.switchScene(InterlevelScene.class);

            return false;

        }
        if (pos == path && Dungeon.depth ==75 && pos == Dungeon.level.north) {


            curAction = null;
            InterlevelScene.mode = InterlevelScene.Mode.NORTH75;
            Game.switchScene(InterlevelScene.class);

            return false;

        }
        if (pos == path && Dungeon.depth == 76 && pos == Dungeon.level.north) {


            curAction = null;
            InterlevelScene.mode = InterlevelScene.Mode.NORTH76;
            Game.switchScene(InterlevelScene.class);

            return false;

        }
        if (pos == path && Dungeon.depth == 77 && pos == Dungeon.level.north) {


            curAction = null;
            InterlevelScene.mode = InterlevelScene.Mode.NORTH77;
            Game.switchScene(InterlevelScene.class);

            return false;

        }
        if (pos == path && Dungeon.depth == 78 && pos == Dungeon.level.north) {


            curAction = null;
            InterlevelScene.mode = InterlevelScene.Mode.NORTH78;
            Game.switchScene(InterlevelScene.class);

            return false;

        }
        if (pos == path && Dungeon.depth == 79 && pos == Dungeon.level.north) {


            curAction = null;
            InterlevelScene.mode = InterlevelScene.Mode.NORTH79;
            Game.switchScene(InterlevelScene.class);

            return false;

        }
        if (pos == path && Dungeon.depth == 80 && pos == Dungeon.level.north) {


            curAction = null;
            InterlevelScene.mode = InterlevelScene.Mode.NORTH80;
            Game.switchScene(InterlevelScene.class);

            return false;

        }
        if (pos == path && Dungeon.depth == 81 && pos == Dungeon.level.north) {


            curAction = null;
            InterlevelScene.mode = InterlevelScene.Mode.NORTH81;
            Game.switchScene(InterlevelScene.class);

            return false;


        }
        if (pos == path && Dungeon.depth == 82 && pos == Dungeon.level.north) {


            curAction = null;
            InterlevelScene.mode = InterlevelScene.Mode.NORTH82;
            Game.switchScene(InterlevelScene.class);

            return false;

        }
        if (pos == path && Dungeon.depth == 83 && pos == Dungeon.level.north) {


            curAction = null;
            InterlevelScene.mode = InterlevelScene.Mode.NORTH83;
            Game.switchScene(InterlevelScene.class);

            return false;

        }
        if (pos == path && Dungeon.depth == 84 && pos == Dungeon.level.north) {


            curAction = null;
            InterlevelScene.mode = InterlevelScene.Mode.NORTH84;
            Game.switchScene(InterlevelScene.class);

            return false;

        }
        if (pos == path && Dungeon.depth ==85 && pos == Dungeon.level.north) {


            curAction = null;
            InterlevelScene.mode = InterlevelScene.Mode.NORTH85;
            Game.switchScene(InterlevelScene.class);

            return false;

        }
        if (pos == path && Dungeon.depth == 86 && pos == Dungeon.level.north) {


            curAction = null;
            InterlevelScene.mode = InterlevelScene.Mode.NORTH86;
            Game.switchScene(InterlevelScene.class);

            return false;

        }
        if (pos == path && Dungeon.depth == 87 && pos == Dungeon.level.north) {


            curAction = null;
            InterlevelScene.mode = InterlevelScene.Mode.NORTH87;
            Game.switchScene(InterlevelScene.class);

            return false;

        }
        if (pos == path && Dungeon.depth == 88 && pos == Dungeon.level.north) {


            curAction = null;
            InterlevelScene.mode = InterlevelScene.Mode.NORTH88;
            Game.switchScene(InterlevelScene.class);

            return false;

        }
        if (pos == path && Dungeon.depth == 89 && pos == Dungeon.level.north) {


            curAction = null;
            InterlevelScene.mode = InterlevelScene.Mode.NORTH89;
            Game.switchScene(InterlevelScene.class);

            return false;

        }
        if (pos == path && Dungeon.depth == 90 && pos == Dungeon.level.north) {


            curAction = null;
            InterlevelScene.mode = InterlevelScene.Mode.NORTH90;
            Game.switchScene(InterlevelScene.class);

            return false;

        } else if (getCloser(path)) {

            return true;


        } else {
            ready();
            return false;
        }

    }

    private boolean actSouth( HeroAction.South action ) {
        int path = action.dst;
        if (pos == path && Dungeon.depth == 11 && pos == Dungeon.level.south) {


            curAction = null;
            InterlevelScene.mode = InterlevelScene.Mode.SOUTH1;
            Game.switchScene(InterlevelScene.class);

            return false;

        }
        if (pos == path && Dungeon.depth == 12 && pos == Dungeon.level.south) {


            curAction = null;
            InterlevelScene.mode = InterlevelScene.Mode.SOUTH2;
            Game.switchScene(InterlevelScene.class);

            return false;

        }
        if (pos == path && Dungeon.depth == 13 && pos == Dungeon.level.south) {


            curAction = null;
            InterlevelScene.mode = InterlevelScene.Mode.SOUTH3;
            Game.switchScene(InterlevelScene.class);

            return false;

        }
        if (pos == path && Dungeon.depth == 14 && pos == Dungeon.level.south) {


            curAction = null;
            InterlevelScene.mode = InterlevelScene.Mode.SOUTH4;
            Game.switchScene(InterlevelScene.class);

            return false;

        }
        if (pos == path && Dungeon.depth == 15 && pos == Dungeon.level.south) {


            curAction = null;
            InterlevelScene.mode = InterlevelScene.Mode.SOUTH5;
            Game.switchScene(InterlevelScene.class);

            return false;

        }
        if (pos == path && Dungeon.depth == 16 && pos == Dungeon.level.south) {


            curAction = null;
            InterlevelScene.mode = InterlevelScene.Mode.SOUTH6;
            Game.switchScene(InterlevelScene.class);

            return false;

        }
        if (pos == path && Dungeon.depth == 17 && pos == Dungeon.level.south) {


            curAction = null;
            InterlevelScene.mode = InterlevelScene.Mode.SOUTH7;
            Game.switchScene(InterlevelScene.class);

            return false;

        }
        if (pos == path && Dungeon.depth == 18 && pos == Dungeon.level.south) {


            curAction = null;
            InterlevelScene.mode = InterlevelScene.Mode.SOUTH8;
            Game.switchScene(InterlevelScene.class);

            return false;

        }
        if (pos == path && Dungeon.depth == 19 && pos == Dungeon.level.south) {


            curAction = null;
            InterlevelScene.mode = InterlevelScene.Mode.SOUTH9;
            Game.switchScene(InterlevelScene.class);

            return false;

        }
        if (pos == path && Dungeon.depth == 20 && pos == Dungeon.level.south) {


            curAction = null;
            InterlevelScene.mode = InterlevelScene.Mode.SOUTH10;
            Game.switchScene(InterlevelScene.class);

            return false;

        }
        if (pos == path && Dungeon.depth == 21 && pos == Dungeon.level.south) {


            curAction = null;
            InterlevelScene.mode = InterlevelScene.Mode.SOUTH11;
            Game.switchScene(InterlevelScene.class);

            return false;


        }
        if (pos == path && Dungeon.depth == 22 && pos == Dungeon.level.south) {


            curAction = null;
            InterlevelScene.mode = InterlevelScene.Mode.SOUTH12;
            Game.switchScene(InterlevelScene.class);

            return false;

        }
        if (pos == path && Dungeon.depth == 23 && pos == Dungeon.level.south) {


            curAction = null;
            InterlevelScene.mode = InterlevelScene.Mode.SOUTH13;
            Game.switchScene(InterlevelScene.class);

            return false;

        }
        if (pos == path && Dungeon.depth == 24 && pos == Dungeon.level.south) {


            curAction = null;
            InterlevelScene.mode = InterlevelScene.Mode.SOUTH14;
            Game.switchScene(InterlevelScene.class);

            return false;

        }
        if (pos == path && Dungeon.depth == 25 && pos == Dungeon.level.south) {


            curAction = null;
            InterlevelScene.mode = InterlevelScene.Mode.SOUTH15;
            Game.switchScene(InterlevelScene.class);

            return false;

        }
        if (pos == path && Dungeon.depth == 26 && pos == Dungeon.level.south) {


            curAction = null;
            InterlevelScene.mode = InterlevelScene.Mode.SOUTH16;
            Game.switchScene(InterlevelScene.class);

            return false;

        }
        if (pos == path && Dungeon.depth == 27 && pos == Dungeon.level.south) {


            curAction = null;
            InterlevelScene.mode = InterlevelScene.Mode.SOUTH17;
            Game.switchScene(InterlevelScene.class);

            return false;

        }
        if (pos == path && Dungeon.depth == 28 && pos == Dungeon.level.south) {


            curAction = null;
            InterlevelScene.mode = InterlevelScene.Mode.SOUTH18;
            Game.switchScene(InterlevelScene.class);

            return false;

        }
        if (pos == path && Dungeon.depth == 29 && pos == Dungeon.level.south) {


            curAction = null;
            InterlevelScene.mode = InterlevelScene.Mode.SOUTH19;
            Game.switchScene(InterlevelScene.class);

            return false;

        }
        if (pos == path && Dungeon.depth == 30 && pos == Dungeon.level.south) {


            curAction = null;
            InterlevelScene.mode = InterlevelScene.Mode.SOUTH20;
            Game.switchScene(InterlevelScene.class);

            return false;

        }
        if (pos == path && Dungeon.depth == 31 && pos == Dungeon.level.south) {


            curAction = null;
            InterlevelScene.mode = InterlevelScene.Mode.SOUTH21;
            Game.switchScene(InterlevelScene.class);

            return false;


        }
        if (pos == path && Dungeon.depth == 32 && pos == Dungeon.level.south) {


            curAction = null;
            InterlevelScene.mode = InterlevelScene.Mode.SOUTH22;
            Game.switchScene(InterlevelScene.class);

            return false;

        }
        if (pos == path && Dungeon.depth == 33 && pos == Dungeon.level.south) {


            curAction = null;
            InterlevelScene.mode = InterlevelScene.Mode.SOUTH23;
            Game.switchScene(InterlevelScene.class);

            return false;

        }
        if (pos == path && Dungeon.depth == 34 && pos == Dungeon.level.south) {


            curAction = null;
            InterlevelScene.mode = InterlevelScene.Mode.SOUTH24;
            Game.switchScene(InterlevelScene.class);

            return false;

        }
        if (pos == path && Dungeon.depth == 35 && pos == Dungeon.level.south) {


            curAction = null;
            InterlevelScene.mode = InterlevelScene.Mode.SOUTH25;
            Game.switchScene(InterlevelScene.class);

            return false;

        }
        if (pos == path && Dungeon.depth == 36 && pos == Dungeon.level.south) {


            curAction = null;
            InterlevelScene.mode = InterlevelScene.Mode.SOUTH26;
            Game.switchScene(InterlevelScene.class);

            return false;

        }
        if (pos == path && Dungeon.depth == 37 && pos == Dungeon.level.south) {


            curAction = null;
            InterlevelScene.mode = InterlevelScene.Mode.SOUTH27;
            Game.switchScene(InterlevelScene.class);

            return false;

        }
        if (pos == path && Dungeon.depth == 38 && pos == Dungeon.level.south) {


            curAction = null;
            InterlevelScene.mode = InterlevelScene.Mode.SOUTH28;
            Game.switchScene(InterlevelScene.class);

            return false;

        }
        if (pos == path && Dungeon.depth == 39 && pos == Dungeon.level.south) {


            curAction = null;
            InterlevelScene.mode = InterlevelScene.Mode.SOUTH29;
            Game.switchScene(InterlevelScene.class);

            return false;

        }
        if (pos == path && Dungeon.depth == 40 && pos == Dungeon.level.south) {


            curAction = null;
            InterlevelScene.mode = InterlevelScene.Mode.SOUTH30;
            Game.switchScene(InterlevelScene.class);

            return false;

        }
        if (pos == path && Dungeon.depth == 41 && pos == Dungeon.level.south) {


            curAction = null;
            InterlevelScene.mode = InterlevelScene.Mode.SOUTH31;
            Game.switchScene(InterlevelScene.class);

            return false;

        }
        if (pos == path && Dungeon.depth == 42 && pos == Dungeon.level.south) {


            curAction = null;
            InterlevelScene.mode = InterlevelScene.Mode.SOUTH32;
            Game.switchScene(InterlevelScene.class);

            return false;

        }
        if (pos == path && Dungeon.depth == 43 && pos == Dungeon.level.south) {


            curAction = null;
            InterlevelScene.mode = InterlevelScene.Mode.SOUTH33;
            Game.switchScene(InterlevelScene.class);

            return false;

        }
        if (pos == path && Dungeon.depth == 44 && pos == Dungeon.level.south) {


            curAction = null;
            InterlevelScene.mode = InterlevelScene.Mode.SOUTH34;
            Game.switchScene(InterlevelScene.class);

            return false;

        }
        if (pos == path && Dungeon.depth == 45 && pos == Dungeon.level.south) {


            curAction = null;
            InterlevelScene.mode = InterlevelScene.Mode.SOUTH35;
            Game.switchScene(InterlevelScene.class);

            return false;

        }
        if (pos == path && Dungeon.depth == 46 && pos == Dungeon.level.south) {


            curAction = null;
            InterlevelScene.mode = InterlevelScene.Mode.SOUTH36;
            Game.switchScene(InterlevelScene.class);

            return false;

        }
        if (pos == path && Dungeon.depth == 47 && pos == Dungeon.level.south) {


            curAction = null;
            InterlevelScene.mode = InterlevelScene.Mode.SOUTH37;
            Game.switchScene(InterlevelScene.class);

            return false;

        }
        if (pos == path && Dungeon.depth == 48 && pos == Dungeon.level.south) {


            curAction = null;
            InterlevelScene.mode = InterlevelScene.Mode.SOUTH38;
            Game.switchScene(InterlevelScene.class);

            return false;

        }
        if (pos == path && Dungeon.depth == 49 && pos == Dungeon.level.south) {


            curAction = null;
            InterlevelScene.mode = InterlevelScene.Mode.SOUTH39;
            Game.switchScene(InterlevelScene.class);

            return false;

        }
        if (pos == path && Dungeon.depth == 50 && pos == Dungeon.level.south) {


            curAction = null;
            InterlevelScene.mode = InterlevelScene.Mode.SOUTH40;
            Game.switchScene(InterlevelScene.class);

            return false;

        }
        if (pos == path && Dungeon.depth == 51 && pos == Dungeon.level.south) {


            curAction = null;
            InterlevelScene.mode = InterlevelScene.Mode.SOUTH41;
            Game.switchScene(InterlevelScene.class);

            return false;


        }
        if (pos == path && Dungeon.depth == 52 && pos == Dungeon.level.south) {


            curAction = null;
            InterlevelScene.mode = InterlevelScene.Mode.SOUTH42;
            Game.switchScene(InterlevelScene.class);

            return false;

        }
        if (pos == path && Dungeon.depth == 53 && pos == Dungeon.level.south) {


            curAction = null;
            InterlevelScene.mode = InterlevelScene.Mode.SOUTH43;
            Game.switchScene(InterlevelScene.class);

            return false;

        }
        if (pos == path && Dungeon.depth == 54 && pos == Dungeon.level.south) {


            curAction = null;
            InterlevelScene.mode = InterlevelScene.Mode.SOUTH44;
            Game.switchScene(InterlevelScene.class);

            return false;

        }
        if (pos == path && Dungeon.depth ==55 && pos == Dungeon.level.south) {


            curAction = null;
            InterlevelScene.mode = InterlevelScene.Mode.SOUTH45;
            Game.switchScene(InterlevelScene.class);

            return false;

        }
        if (pos == path && Dungeon.depth == 56 && pos == Dungeon.level.south) {


            curAction = null;
            InterlevelScene.mode = InterlevelScene.Mode.SOUTH46;
            Game.switchScene(InterlevelScene.class);

            return false;

        }
        if (pos == path && Dungeon.depth == 57 && pos == Dungeon.level.south) {


            curAction = null;
            InterlevelScene.mode = InterlevelScene.Mode.SOUTH47;
            Game.switchScene(InterlevelScene.class);

            return false;

        }
        if (pos == path && Dungeon.depth == 58 && pos == Dungeon.level.south) {


            curAction = null;
            InterlevelScene.mode = InterlevelScene.Mode.SOUTH48;
            Game.switchScene(InterlevelScene.class);

            return false;

        }
        if (pos == path && Dungeon.depth == 59 && pos == Dungeon.level.south) {


            curAction = null;
            InterlevelScene.mode = InterlevelScene.Mode.SOUTH49;
            Game.switchScene(InterlevelScene.class);

            return false;

        }
        if (pos == path && Dungeon.depth == 60 && pos == Dungeon.level.south) {


            curAction = null;
            InterlevelScene.mode = InterlevelScene.Mode.SOUTH50;
            Game.switchScene(InterlevelScene.class);

            return false;

        }
        if (pos == path && Dungeon.depth == 61 && pos == Dungeon.level.south) {


            curAction = null;
            InterlevelScene.mode = InterlevelScene.Mode.SOUTH51;
            Game.switchScene(InterlevelScene.class);

            return false;


        }
        if (pos == path && Dungeon.depth == 62 && pos == Dungeon.level.south) {


            curAction = null;
            InterlevelScene.mode = InterlevelScene.Mode.SOUTH52;
            Game.switchScene(InterlevelScene.class);

            return false;

        }
        if (pos == path && Dungeon.depth == 63 && pos == Dungeon.level.south) {


            curAction = null;
            InterlevelScene.mode = InterlevelScene.Mode.SOUTH53;
            Game.switchScene(InterlevelScene.class);

            return false;

        }
        if (pos == path && Dungeon.depth == 64 && pos == Dungeon.level.south) {


            curAction = null;
            InterlevelScene.mode = InterlevelScene.Mode.SOUTH54;
            Game.switchScene(InterlevelScene.class);

            return false;

        }
        if (pos == path && Dungeon.depth ==65 && pos == Dungeon.level.south) {


            curAction = null;
            InterlevelScene.mode = InterlevelScene.Mode.SOUTH55;
            Game.switchScene(InterlevelScene.class);

            return false;

        }
        if (pos == path && Dungeon.depth == 66 && pos == Dungeon.level.south) {


            curAction = null;
            InterlevelScene.mode = InterlevelScene.Mode.SOUTH56;
            Game.switchScene(InterlevelScene.class);

            return false;

        }
        if (pos == path && Dungeon.depth == 67 && pos == Dungeon.level.south) {


            curAction = null;
            InterlevelScene.mode = InterlevelScene.Mode.SOUTH57;
            Game.switchScene(InterlevelScene.class);

            return false;

        }
        if (pos == path && Dungeon.depth == 68 && pos == Dungeon.level.south) {


            curAction = null;
            InterlevelScene.mode = InterlevelScene.Mode.SOUTH58;
            Game.switchScene(InterlevelScene.class);

            return false;

        }
        if (pos == path && Dungeon.depth == 69 && pos == Dungeon.level.south) {


            curAction = null;
            InterlevelScene.mode = InterlevelScene.Mode.SOUTH59;
            Game.switchScene(InterlevelScene.class);

            return false;

        }
        if (pos == path && Dungeon.depth == 70 && pos == Dungeon.level.south) {


            curAction = null;
            InterlevelScene.mode = InterlevelScene.Mode.SOUTH60;
            Game.switchScene(InterlevelScene.class);

            return false;

        }
        if (pos == path && Dungeon.depth == 71 && pos == Dungeon.level.south) {


            curAction = null;
            InterlevelScene.mode = InterlevelScene.Mode.SOUTH61;
            Game.switchScene(InterlevelScene.class);

            return false;


        }
        if (pos == path && Dungeon.depth == 72 && pos == Dungeon.level.south) {


            curAction = null;
            InterlevelScene.mode = InterlevelScene.Mode.SOUTH62;
            Game.switchScene(InterlevelScene.class);

            return false;

        }
        if (pos == path && Dungeon.depth == 73 && pos == Dungeon.level.south) {


            curAction = null;
            InterlevelScene.mode = InterlevelScene.Mode.SOUTH63;
            Game.switchScene(InterlevelScene.class);

            return false;

        }
        if (pos == path && Dungeon.depth == 74 && pos == Dungeon.level.south) {


            curAction = null;
            InterlevelScene.mode = InterlevelScene.Mode.SOUTH64;
            Game.switchScene(InterlevelScene.class);

            return false;

        }
        if (pos == path && Dungeon.depth ==75 && pos == Dungeon.level.south) {


            curAction = null;
            InterlevelScene.mode = InterlevelScene.Mode.SOUTH65;
            Game.switchScene(InterlevelScene.class);

            return false;

        }
        if (pos == path && Dungeon.depth == 76 && pos == Dungeon.level.south) {


            curAction = null;
            InterlevelScene.mode = InterlevelScene.Mode.SOUTH66;
            Game.switchScene(InterlevelScene.class);

            return false;

        }
        if (pos == path && Dungeon.depth == 77 && pos == Dungeon.level.south) {


            curAction = null;
            InterlevelScene.mode = InterlevelScene.Mode.SOUTH67;
            Game.switchScene(InterlevelScene.class);

            return false;

        }
        if (pos == path && Dungeon.depth == 78 && pos == Dungeon.level.south) {


            curAction = null;
            InterlevelScene.mode = InterlevelScene.Mode.SOUTH68;
            Game.switchScene(InterlevelScene.class);

            return false;

        }
        if (pos == path && Dungeon.depth == 79 && pos == Dungeon.level.south) {


            curAction = null;
            InterlevelScene.mode = InterlevelScene.Mode.SOUTH69;
            Game.switchScene(InterlevelScene.class);

            return false;

        }
        if (pos == path && Dungeon.depth == 80 && pos == Dungeon.level.south) {


            curAction = null;
            InterlevelScene.mode = InterlevelScene.Mode.SOUTH70;
            Game.switchScene(InterlevelScene.class);

            return false;

        }
        if (pos == path && Dungeon.depth == 81 && pos == Dungeon.level.south) {


            curAction = null;
            InterlevelScene.mode = InterlevelScene.Mode.SOUTH71;
            Game.switchScene(InterlevelScene.class);

            return false;


        }
        if (pos == path && Dungeon.depth == 82 && pos == Dungeon.level.south) {


            curAction = null;
            InterlevelScene.mode = InterlevelScene.Mode.SOUTH72;
            Game.switchScene(InterlevelScene.class);

            return false;

        }
        if (pos == path && Dungeon.depth == 83 && pos == Dungeon.level.south) {


            curAction = null;
            InterlevelScene.mode = InterlevelScene.Mode.SOUTH73;
            Game.switchScene(InterlevelScene.class);

            return false;

        }
        if (pos == path && Dungeon.depth == 84 && pos == Dungeon.level.south) {


            curAction = null;
            InterlevelScene.mode = InterlevelScene.Mode.SOUTH74;
            Game.switchScene(InterlevelScene.class);

            return false;

        }
        if (pos == path && Dungeon.depth ==85 && pos == Dungeon.level.south) {


            curAction = null;
            InterlevelScene.mode = InterlevelScene.Mode.SOUTH75;
            Game.switchScene(InterlevelScene.class);

            return false;

        }
        if (pos == path && Dungeon.depth == 86 && pos == Dungeon.level.south) {


            curAction = null;
            InterlevelScene.mode = InterlevelScene.Mode.SOUTH76;
            Game.switchScene(InterlevelScene.class);

            return false;

        }
        if (pos == path && Dungeon.depth == 87 && pos == Dungeon.level.south) {


            curAction = null;
            InterlevelScene.mode = InterlevelScene.Mode.SOUTH77;
            Game.switchScene(InterlevelScene.class);

            return false;

        }
        if (pos == path && Dungeon.depth == 88 && pos == Dungeon.level.south) {


            curAction = null;
            InterlevelScene.mode = InterlevelScene.Mode.SOUTH78;
            Game.switchScene(InterlevelScene.class);

            return false;

        }
        if (pos == path && Dungeon.depth == 89 && pos == Dungeon.level.south) {


            curAction = null;
            InterlevelScene.mode = InterlevelScene.Mode.SOUTH79;
            Game.switchScene(InterlevelScene.class);

            return false;

        }
        if (pos == path && Dungeon.depth == 90 && pos == Dungeon.level.south) {


            curAction = null;
            InterlevelScene.mode = InterlevelScene.Mode.SOUTH80;
            Game.switchScene(InterlevelScene.class);

            return false;

        }
        if (pos == path && Dungeon.depth == 91 && pos == Dungeon.level.south) {


            curAction = null;
            InterlevelScene.mode = InterlevelScene.Mode.SOUTH81;
            Game.switchScene(InterlevelScene.class);

            return false;


        }
        if (pos == path && Dungeon.depth == 92 && pos == Dungeon.level.south) {


            curAction = null;
            InterlevelScene.mode = InterlevelScene.Mode.SOUTH82;
            Game.switchScene(InterlevelScene.class);

            return false;

        }
        if (pos == path && Dungeon.depth == 93 && pos == Dungeon.level.south) {


            curAction = null;
            InterlevelScene.mode = InterlevelScene.Mode.SOUTH83;
            Game.switchScene(InterlevelScene.class);

            return false;

        }
        if (pos == path && Dungeon.depth == 94 && pos == Dungeon.level.south) {


            curAction = null;
            InterlevelScene.mode = InterlevelScene.Mode.SOUTH84;
            Game.switchScene(InterlevelScene.class);

            return false;

        }
        if (pos == path && Dungeon.depth ==95 && pos == Dungeon.level.south) {


            curAction = null;
            InterlevelScene.mode = InterlevelScene.Mode.SOUTH85;
            Game.switchScene(InterlevelScene.class);

            return false;

        }
        if (pos == path && Dungeon.depth == 96 && pos == Dungeon.level.south) {


            curAction = null;
            InterlevelScene.mode = InterlevelScene.Mode.SOUTH86;
            Game.switchScene(InterlevelScene.class);

            return false;

        }
        if (pos == path && Dungeon.depth == 97 && pos == Dungeon.level.south) {


            curAction = null;
            InterlevelScene.mode = InterlevelScene.Mode.SOUTH87;
            Game.switchScene(InterlevelScene.class);

            return false;

        }
        if (pos == path && Dungeon.depth == 98 && pos == Dungeon.level.south) {


            curAction = null;
            InterlevelScene.mode = InterlevelScene.Mode.SOUTH88;
            Game.switchScene(InterlevelScene.class);

            return false;

        }
        if (pos == path && Dungeon.depth == 99 && pos == Dungeon.level.south) {


            curAction = null;
            InterlevelScene.mode = InterlevelScene.Mode.SOUTH89;
            Game.switchScene(InterlevelScene.class);

            return false;

        }
        if (pos == path && Dungeon.depth == 100 && pos == Dungeon.level.south) {


            curAction = null;
            InterlevelScene.mode = InterlevelScene.Mode.SOUTH90;
            Game.switchScene(InterlevelScene.class);

            return false;

        }else if (getCloser(path)) {

            return true;


        } else {
            ready();
            return false;
        }

    }



    private boolean actEast( HeroAction.East action ) {
		int path = action.dst;
		if (pos == path && Dungeon.depth == 1 && pos == Dungeon.level.east) {

                if (belongings.getItem(Amulet.class) == null) {
                    GameScene.show(new WndMessage(TXT_LEAVE));
                    ready();
                } else {
                    Dungeon.win(ResultDescriptions.WIN);
                    Dungeon.deleteGame(Dungeon.hero.heroClass, true);
                    Game.switchScene(SurfaceScene.class);
                }

            }
            if (pos == path && Dungeon.depth == 2 && pos == Dungeon.level.east) {


                curAction = null;
                InterlevelScene.mode = InterlevelScene.Mode.EAST2;
                Game.switchScene(InterlevelScene.class);

                return false;

            }
            if (pos == path && Dungeon.depth == 3 && pos == Dungeon.level.east) {


                curAction = null;
                InterlevelScene.mode = InterlevelScene.Mode.EAST3;
                Game.switchScene(InterlevelScene.class);

                return false;

            }
            if (pos == path && Dungeon.depth == 4 && pos == Dungeon.level.east) {


                curAction = null;
                InterlevelScene.mode = InterlevelScene.Mode.EAST4;
                Game.switchScene(InterlevelScene.class);

                return false;

            }
            if (pos == path && Dungeon.depth == 5 && pos == Dungeon.level.east) {


                curAction = null;
                InterlevelScene.mode = InterlevelScene.Mode.EAST5;
                Game.switchScene(InterlevelScene.class);

                return false;

            }
            if (pos == path && Dungeon.depth == 6 && pos == Dungeon.level.east) {


                curAction = null;
                InterlevelScene.mode = InterlevelScene.Mode.EAST6;
                Game.switchScene(InterlevelScene.class);

                return false;

            }
            if (pos == path && Dungeon.depth == 7 && pos == Dungeon.level.east) {


                curAction = null;
                InterlevelScene.mode = InterlevelScene.Mode.EAST7;
                Game.switchScene(InterlevelScene.class);

                return false;

            }
            if (pos == path && Dungeon.depth == 8 && pos == Dungeon.level.east) {


                curAction = null;
                InterlevelScene.mode = InterlevelScene.Mode.EAST8;
                Game.switchScene(InterlevelScene.class);

                return false;

            }
            if (pos == path && Dungeon.depth == 9 && pos == Dungeon.level.east) {


                curAction = null;
                InterlevelScene.mode = InterlevelScene.Mode.EAST9;
                Game.switchScene(InterlevelScene.class);

                return false;

            }
            if (pos == path && Dungeon.depth == 10 && pos == Dungeon.level.east) {


                curAction = null;
                InterlevelScene.mode = InterlevelScene.Mode.EAST10;
                Game.switchScene(InterlevelScene.class);

                return false;

            }
            if (pos == path && Dungeon.depth == 11 && pos == Dungeon.level.east) {


                curAction = null;
                InterlevelScene.mode = InterlevelScene.Mode.EAST11;
                Game.switchScene(InterlevelScene.class);

                return false;

            }
            if (pos == path && Dungeon.depth == 12 && pos == Dungeon.level.east) {


                curAction = null;
                InterlevelScene.mode = InterlevelScene.Mode.EAST12;
                Game.switchScene(InterlevelScene.class);

                return false;

            }
        if (pos == path && Dungeon.depth == 13 && pos == Dungeon.level.east ) {


            curAction = null;
            InterlevelScene.mode = InterlevelScene.Mode.EAST13;
            Game.switchScene( InterlevelScene.class );

            return false;

        }
        if (pos == path && Dungeon.depth == 14 && pos == Dungeon.level.east ) {


            curAction = null;
            InterlevelScene.mode = InterlevelScene.Mode.EAST14;
            Game.switchScene( InterlevelScene.class );

            return false;

        }
        if (pos == path && Dungeon.depth == 15 && pos == Dungeon.level.east ) {


            curAction = null;
            InterlevelScene.mode = InterlevelScene.Mode.EAST15;
            Game.switchScene( InterlevelScene.class );

            return false;

        }
        if (pos == path && Dungeon.depth == 16 && pos == Dungeon.level.east ) {


            curAction = null;
            InterlevelScene.mode = InterlevelScene.Mode.EAST16;
            Game.switchScene( InterlevelScene.class );

            return false;

        }
        if (pos == path && Dungeon.depth == 17 && pos == Dungeon.level.east ) {


            curAction = null;
            InterlevelScene.mode = InterlevelScene.Mode.EAST17;
            Game.switchScene( InterlevelScene.class );

            return false;

        }
        if (pos == path && Dungeon.depth == 18 && pos == Dungeon.level.east ) {


            curAction = null;
            InterlevelScene.mode = InterlevelScene.Mode.EAST18;
            Game.switchScene( InterlevelScene.class );

            return false;

        }
        if (pos == path && Dungeon.depth == 19 && pos == Dungeon.level.east ) {


            curAction = null;
            InterlevelScene.mode = InterlevelScene.Mode.EAST19;
            Game.switchScene( InterlevelScene.class );

            return false;

        }
        if (pos == path && Dungeon.depth == 20 && pos == Dungeon.level.east ) {


            curAction = null;
            InterlevelScene.mode = InterlevelScene.Mode.EAST20;
            Game.switchScene( InterlevelScene.class );

            return false;

        }
        if (pos == path && Dungeon.depth == 21 && pos == Dungeon.level.east ) {


            curAction = null;
            InterlevelScene.mode = InterlevelScene.Mode.EAST21;
            Game.switchScene( InterlevelScene.class );

            return false;

        }
        if (pos == path && Dungeon.depth == 22 && pos == Dungeon.level.east ) {


            curAction = null;
            InterlevelScene.mode = InterlevelScene.Mode.EAST22;
            Game.switchScene( InterlevelScene.class );

            return false;

        }
        if (pos == path && Dungeon.depth == 23 && pos == Dungeon.level.east ) {


            curAction = null;
            InterlevelScene.mode = InterlevelScene.Mode.EAST23;
            Game.switchScene( InterlevelScene.class );

            return false;

        }
        if (pos == path && Dungeon.depth == 24 && pos == Dungeon.level.east ) {


            curAction = null;
            InterlevelScene.mode = InterlevelScene.Mode.EAST24;
            Game.switchScene( InterlevelScene.class );

            return false;

        }
        if (pos == path && Dungeon.depth == 25 && pos == Dungeon.level.east ) {


            curAction = null;
            InterlevelScene.mode = InterlevelScene.Mode.EAST25;
            Game.switchScene( InterlevelScene.class );

            return false;

        }
        if (pos == path && Dungeon.depth == 26 && pos == Dungeon.level.east ) {


            curAction = null;
            InterlevelScene.mode = InterlevelScene.Mode.EAST26;
            Game.switchScene( InterlevelScene.class );

            return false;

        }
        if (pos == path && Dungeon.depth == 27 && pos == Dungeon.level.east ) {


            curAction = null;
            InterlevelScene.mode = InterlevelScene.Mode.EAST27;
            Game.switchScene( InterlevelScene.class );

            return false;

        }
        if (pos == path && Dungeon.depth == 28 && pos == Dungeon.level.east ) {


            curAction = null;
            InterlevelScene.mode = InterlevelScene.Mode.EAST28;
            Game.switchScene( InterlevelScene.class );

            return false;

        }
        if (pos == path && Dungeon.depth == 29 && pos == Dungeon.level.east ) {


            curAction = null;
            InterlevelScene.mode = InterlevelScene.Mode.EAST29;
            Game.switchScene( InterlevelScene.class );

            return false;

        }
        if (pos == path && Dungeon.depth == 30 && pos == Dungeon.level.east ) {


            curAction = null;
            InterlevelScene.mode = InterlevelScene.Mode.EAST30;
            Game.switchScene( InterlevelScene.class );

            return false;

        }
        if (pos == path && Dungeon.depth == 31 && pos == Dungeon.level.east ) {


            curAction = null;
            InterlevelScene.mode = InterlevelScene.Mode.EAST31;
            Game.switchScene( InterlevelScene.class );

            return false;

        }
        if (pos == path && Dungeon.depth == 32 && pos == Dungeon.level.east ) {


            curAction = null;
            InterlevelScene.mode = InterlevelScene.Mode.EAST32;
            Game.switchScene( InterlevelScene.class );

            return false;

        }
        if (pos == path && Dungeon.depth == 33 && pos == Dungeon.level.east ) {


            curAction = null;
            InterlevelScene.mode = InterlevelScene.Mode.EAST33;
            Game.switchScene( InterlevelScene.class );

            return false;

        }
        if (pos == path && Dungeon.depth == 34 && pos == Dungeon.level.east ) {


            curAction = null;
            InterlevelScene.mode = InterlevelScene.Mode.EAST34;
            Game.switchScene( InterlevelScene.class );

            return false;

        }
        if (pos == path && Dungeon.depth == 35 && pos == Dungeon.level.east ) {


            curAction = null;
            InterlevelScene.mode = InterlevelScene.Mode.EAST35;
            Game.switchScene( InterlevelScene.class );

            return false;

        }
        if (pos == path && Dungeon.depth == 36 && pos == Dungeon.level.east ) {


            curAction = null;
            InterlevelScene.mode = InterlevelScene.Mode.EAST36;
            Game.switchScene( InterlevelScene.class );

            return false;

        }
        if (pos == path && Dungeon.depth == 37 && pos == Dungeon.level.east ) {


            curAction = null;
            InterlevelScene.mode = InterlevelScene.Mode.EAST37;
            Game.switchScene( InterlevelScene.class );

            return false;

        }
        if (pos == path && Dungeon.depth == 38 && pos == Dungeon.level.east ) {


            curAction = null;
            InterlevelScene.mode = InterlevelScene.Mode.EAST38;
            Game.switchScene( InterlevelScene.class );

            return false;

        }
        if (pos == path && Dungeon.depth == 39 && pos == Dungeon.level.east ) {


            curAction = null;
            InterlevelScene.mode = InterlevelScene.Mode.EAST39;
            Game.switchScene( InterlevelScene.class );

            return false;

        }
        if (pos == path && Dungeon.depth == 40 && pos == Dungeon.level.east ) {


            curAction = null;
            InterlevelScene.mode = InterlevelScene.Mode.EAST40;
            Game.switchScene( InterlevelScene.class );

            return false;

        }
        if (pos == path && Dungeon.depth == 41 && pos == Dungeon.level.east ) {


            curAction = null;
            InterlevelScene.mode = InterlevelScene.Mode.EAST41;
            Game.switchScene( InterlevelScene.class );

            return false;

        }
        if (pos == path && Dungeon.depth == 42 && pos == Dungeon.level.east ) {


            curAction = null;
            InterlevelScene.mode = InterlevelScene.Mode.EAST42;
            Game.switchScene( InterlevelScene.class );

            return false;

        }
        if (pos == path && Dungeon.depth == 43 && pos == Dungeon.level.east ) {


            curAction = null;
            InterlevelScene.mode = InterlevelScene.Mode.EAST43;
            Game.switchScene( InterlevelScene.class );

            return false;

        }
        if (pos == path && Dungeon.depth == 44 && pos == Dungeon.level.east ) {


            curAction = null;
            InterlevelScene.mode = InterlevelScene.Mode.EAST44;
            Game.switchScene( InterlevelScene.class );

            return false;

        }
        if (pos == path && Dungeon.depth == 45 && pos == Dungeon.level.east ) {


            curAction = null;
            InterlevelScene.mode = InterlevelScene.Mode.EAST45;
            Game.switchScene( InterlevelScene.class );

            return false;

        }
        if (pos == path && Dungeon.depth == 46 && pos == Dungeon.level.east ) {


            curAction = null;
            InterlevelScene.mode = InterlevelScene.Mode.EAST46;
            Game.switchScene( InterlevelScene.class );

            return false;

        }
        if (pos == path && Dungeon.depth == 47 && pos == Dungeon.level.east ) {


            curAction = null;
            InterlevelScene.mode = InterlevelScene.Mode.EAST47;
            Game.switchScene( InterlevelScene.class );

            return false;

        }
        if (pos == path && Dungeon.depth == 48 && pos == Dungeon.level.east ) {


            curAction = null;
            InterlevelScene.mode = InterlevelScene.Mode.EAST48;
            Game.switchScene( InterlevelScene.class );

            return false;

        }
        if (pos == path && Dungeon.depth == 49 && pos == Dungeon.level.east ) {


            curAction = null;
            InterlevelScene.mode = InterlevelScene.Mode.EAST49;
            Game.switchScene( InterlevelScene.class );

            return false;

        }
        if (pos == path && Dungeon.depth == 50 && pos == Dungeon.level.east ) {


            curAction = null;
            InterlevelScene.mode = InterlevelScene.Mode.EAST50;
            Game.switchScene( InterlevelScene.class );

            return false;

        }
        if (pos == path && Dungeon.depth == 51 && pos == Dungeon.level.east ) {


            curAction = null;
            InterlevelScene.mode = InterlevelScene.Mode.EAST51;
            Game.switchScene( InterlevelScene.class );

            return false;

        }
        if (pos == path && Dungeon.depth == 52 && pos == Dungeon.level.east ) {


            curAction = null;
            InterlevelScene.mode = InterlevelScene.Mode.EAST52;
            Game.switchScene( InterlevelScene.class );

            return false;

        }
        if (pos == path && Dungeon.depth == 53 && pos == Dungeon.level.east ) {


            curAction = null;
            InterlevelScene.mode = InterlevelScene.Mode.EAST53;
            Game.switchScene( InterlevelScene.class );

            return false;

        }
        if (pos == path && Dungeon.depth == 54 && pos == Dungeon.level.east ) {


            curAction = null;
            InterlevelScene.mode = InterlevelScene.Mode.EAST54;
            Game.switchScene( InterlevelScene.class );

            return false;

        }
        if (pos == path && Dungeon.depth == 55 && pos == Dungeon.level.east ) {


            curAction = null;
            InterlevelScene.mode = InterlevelScene.Mode.EAST55;
            Game.switchScene( InterlevelScene.class );

            return false;

        }
        if (pos == path && Dungeon.depth == 56 && pos == Dungeon.level.east ) {


            curAction = null;
            InterlevelScene.mode = InterlevelScene.Mode.EAST56;
            Game.switchScene( InterlevelScene.class );

            return false;

        }
        if (pos == path && Dungeon.depth == 57 && pos == Dungeon.level.east ) {


            curAction = null;
            InterlevelScene.mode = InterlevelScene.Mode.EAST57;
            Game.switchScene( InterlevelScene.class );

            return false;

        }
        if (pos == path && Dungeon.depth == 58 && pos == Dungeon.level.east ) {


            curAction = null;
            InterlevelScene.mode = InterlevelScene.Mode.EAST58;
            Game.switchScene( InterlevelScene.class );

            return false;

        }
        if (pos == path && Dungeon.depth == 59 && pos == Dungeon.level.east ) {


            curAction = null;
            InterlevelScene.mode = InterlevelScene.Mode.EAST59;
            Game.switchScene( InterlevelScene.class );

            return false;

        }
        if (pos == path && Dungeon.depth == 60 && pos == Dungeon.level.east ) {


            curAction = null;
            InterlevelScene.mode = InterlevelScene.Mode.EAST60;
            Game.switchScene( InterlevelScene.class );

            return false;
        }
        if (pos == path && Dungeon.depth == 61 && pos == Dungeon.level.east ) {


            curAction = null;
            InterlevelScene.mode = InterlevelScene.Mode.EAST61;
            Game.switchScene( InterlevelScene.class );

            return false;

        }
        if (pos == path && Dungeon.depth == 62 && pos == Dungeon.level.east ) {


            curAction = null;
            InterlevelScene.mode = InterlevelScene.Mode.EAST62;
            Game.switchScene( InterlevelScene.class );

            return false;

        }
        if (pos == path && Dungeon.depth == 63 && pos == Dungeon.level.east ) {


            curAction = null;
            InterlevelScene.mode = InterlevelScene.Mode.EAST63;
            Game.switchScene( InterlevelScene.class );

            return false;

        }
        if (pos == path && Dungeon.depth == 64 && pos == Dungeon.level.east ) {


            curAction = null;
            InterlevelScene.mode = InterlevelScene.Mode.EAST64;
            Game.switchScene( InterlevelScene.class );

            return false;

        }
        if (pos == path && Dungeon.depth == 65 && pos == Dungeon.level.east ) {


            curAction = null;
            InterlevelScene.mode = InterlevelScene.Mode.EAST65;
            Game.switchScene( InterlevelScene.class );

            return false;

        }
        if (pos == path && Dungeon.depth == 66 && pos == Dungeon.level.east ) {


            curAction = null;
            InterlevelScene.mode = InterlevelScene.Mode.EAST66;
            Game.switchScene( InterlevelScene.class );

            return false;

        }
        if (pos == path && Dungeon.depth == 67 && pos == Dungeon.level.east ) {


            curAction = null;
            InterlevelScene.mode = InterlevelScene.Mode.EAST67;
            Game.switchScene( InterlevelScene.class );

            return false;

        }
        if (pos == path && Dungeon.depth == 68 && pos == Dungeon.level.east ) {


            curAction = null;
            InterlevelScene.mode = InterlevelScene.Mode.EAST68;
            Game.switchScene( InterlevelScene.class );

            return false;

        }
        if (pos == path && Dungeon.depth == 69 && pos == Dungeon.level.east ) {


            curAction = null;
            InterlevelScene.mode = InterlevelScene.Mode.EAST69;
            Game.switchScene( InterlevelScene.class );

            return false;

        }
        if (pos == path && Dungeon.depth == 70 && pos == Dungeon.level.east ) {


            curAction = null;
            InterlevelScene.mode = InterlevelScene.Mode.EAST70;
            Game.switchScene( InterlevelScene.class );

            return false;

        }
        if (pos == path && Dungeon.depth == 71 && pos == Dungeon.level.east ) {


            curAction = null;
            InterlevelScene.mode = InterlevelScene.Mode.EAST71;
            Game.switchScene( InterlevelScene.class );

            return false;

        }
        if (pos == path && Dungeon.depth == 72 && pos == Dungeon.level.east ) {


            curAction = null;
            InterlevelScene.mode = InterlevelScene.Mode.EAST72;
            Game.switchScene( InterlevelScene.class );

            return false;

        }
        if (pos == path && Dungeon.depth == 73 && pos == Dungeon.level.east ) {


            curAction = null;
            InterlevelScene.mode = InterlevelScene.Mode.EAST73;
            Game.switchScene( InterlevelScene.class );

            return false;

        }
        if (pos == path && Dungeon.depth == 74 && pos == Dungeon.level.east ) {


            curAction = null;
            InterlevelScene.mode = InterlevelScene.Mode.EAST74;
            Game.switchScene( InterlevelScene.class );

            return false;

        }
        if (pos == path && Dungeon.depth == 75 && pos == Dungeon.level.east ) {


            curAction = null;
            InterlevelScene.mode = InterlevelScene.Mode.EAST75;
            Game.switchScene( InterlevelScene.class );

            return false;

        }
        if (pos == path && Dungeon.depth == 76 && pos == Dungeon.level.east ) {


            curAction = null;
            InterlevelScene.mode = InterlevelScene.Mode.EAST76;
            Game.switchScene( InterlevelScene.class );

            return false;

        }
        if (pos == path && Dungeon.depth == 77 && pos == Dungeon.level.east ) {


            curAction = null;
            InterlevelScene.mode = InterlevelScene.Mode.EAST77;
            Game.switchScene( InterlevelScene.class );

            return false;

        }
        if (pos == path && Dungeon.depth == 78 && pos == Dungeon.level.east ) {


            curAction = null;
            InterlevelScene.mode = InterlevelScene.Mode.EAST78;
            Game.switchScene( InterlevelScene.class );

            return false;

        }
        if (pos == path && Dungeon.depth == 79 && pos == Dungeon.level.east ) {


            curAction = null;
            InterlevelScene.mode = InterlevelScene.Mode.EAST79;
            Game.switchScene( InterlevelScene.class );

            return false;

        }
        if (pos == path && Dungeon.depth == 80 && pos == Dungeon.level.east ) {


            curAction = null;
            InterlevelScene.mode = InterlevelScene.Mode.EAST80;
            Game.switchScene( InterlevelScene.class );

            return false;

        }
        if (pos == path && Dungeon.depth == 81 && pos == Dungeon.level.east ) {


            curAction = null;
            InterlevelScene.mode = InterlevelScene.Mode.EAST81;
            Game.switchScene( InterlevelScene.class );

            return false;

        }
        if (pos == path && Dungeon.depth == 82 && pos == Dungeon.level.east ) {


            curAction = null;
            InterlevelScene.mode = InterlevelScene.Mode.EAST82;
            Game.switchScene( InterlevelScene.class );

            return false;

        }
        if (pos == path && Dungeon.depth == 83 && pos == Dungeon.level.east ) {


            curAction = null;
            InterlevelScene.mode = InterlevelScene.Mode.EAST83;
            Game.switchScene( InterlevelScene.class );

            return false;

        }
        if (pos == path && Dungeon.depth == 84 && pos == Dungeon.level.east ) {


            curAction = null;
            InterlevelScene.mode = InterlevelScene.Mode.EAST84;
            Game.switchScene( InterlevelScene.class );

            return false;

        }
        if (pos == path && Dungeon.depth == 85 && pos == Dungeon.level.east ) {


            curAction = null;
            InterlevelScene.mode = InterlevelScene.Mode.EAST85;
            Game.switchScene( InterlevelScene.class );

            return false;

        }
        if (pos == path && Dungeon.depth == 86 && pos == Dungeon.level.east ) {


            curAction = null;
            InterlevelScene.mode = InterlevelScene.Mode.EAST86;
            Game.switchScene( InterlevelScene.class );

            return false;

        }
        if (pos == path && Dungeon.depth == 87 && pos == Dungeon.level.east ) {


            curAction = null;
            InterlevelScene.mode = InterlevelScene.Mode.EAST87;
            Game.switchScene( InterlevelScene.class );

            return false;

        }
        if (pos == path && Dungeon.depth == 88 && pos == Dungeon.level.east ) {


            curAction = null;
            InterlevelScene.mode = InterlevelScene.Mode.EAST88;
            Game.switchScene( InterlevelScene.class );

            return false;

        }
        if (pos == path && Dungeon.depth == 89 && pos == Dungeon.level.east ) {


            curAction = null;
            InterlevelScene.mode = InterlevelScene.Mode.EAST89;
            Game.switchScene( InterlevelScene.class );

            return false;

        }
        if (pos == path && Dungeon.depth == 90 && pos == Dungeon.level.east ) {


            curAction = null;
            InterlevelScene.mode = InterlevelScene.Mode.EAST90;
            Game.switchScene( InterlevelScene.class );

            return false;

        }
        if (pos == path && Dungeon.depth == 91 && pos == Dungeon.level.east ) {


            curAction = null;
            InterlevelScene.mode = InterlevelScene.Mode.EAST91;
            Game.switchScene( InterlevelScene.class );

            return false;

        }
        if (pos == path && Dungeon.depth == 92 && pos == Dungeon.level.east ) {


            curAction = null;
            InterlevelScene.mode = InterlevelScene.Mode.EAST92;
            Game.switchScene( InterlevelScene.class );

            return false;

        }
        if (pos == path && Dungeon.depth == 93 && pos == Dungeon.level.east ) {


            curAction = null;
            InterlevelScene.mode = InterlevelScene.Mode.EAST93;
            Game.switchScene( InterlevelScene.class );

            return false;

        }
        if (pos == path && Dungeon.depth == 94 && pos == Dungeon.level.east ) {


            curAction = null;
            InterlevelScene.mode = InterlevelScene.Mode.EAST94;
            Game.switchScene( InterlevelScene.class );

            return false;

        }
        if (pos == path && Dungeon.depth == 95 && pos == Dungeon.level.east ) {


            curAction = null;
            InterlevelScene.mode = InterlevelScene.Mode.EAST95;
            Game.switchScene( InterlevelScene.class );

            return false;

        }
        if (pos == path && Dungeon.depth == 96 && pos == Dungeon.level.east ) {


            curAction = null;
            InterlevelScene.mode = InterlevelScene.Mode.EAST96;
            Game.switchScene( InterlevelScene.class );

            return false;

        }
        if (pos == path && Dungeon.depth == 97 && pos == Dungeon.level.east ) {


            curAction = null;
            InterlevelScene.mode = InterlevelScene.Mode.EAST97;
            Game.switchScene( InterlevelScene.class );

            return false;

        }
        if (pos == path && Dungeon.depth == 98 && pos == Dungeon.level.east ) {


            curAction = null;
            InterlevelScene.mode = InterlevelScene.Mode.EAST89;
            Game.switchScene( InterlevelScene.class );

            return false;

        }
        if (pos == path && Dungeon.depth == 99 && pos == Dungeon.level.east ) {


            curAction = null;
            InterlevelScene.mode = InterlevelScene.Mode.EAST99;
            Game.switchScene( InterlevelScene.class );

            return false;

        }
        if (pos == path && Dungeon.depth == 100 && pos == Dungeon.level.east ) {


        curAction = null;
        InterlevelScene.mode = InterlevelScene.Mode.EAST100;
        Game.switchScene( InterlevelScene.class );

        return false;

    }
    else if (getCloser(path)) {

        return true;


    } else {
        ready();
        return false;
    }

}

    private boolean actDown( HeroAction.Down action ) {
        int path = action.dst;
        if (pos == path && Dungeon.depth == 57 && pos == Dungeon.level.down ) {
            curAction = null;
        InterlevelScene.mode = InterlevelScene.Mode.DOWN1;
        Game.switchScene( InterlevelScene.class );

        return false;

    }
        if (pos == path && Dungeon.depth == 101 && pos == Dungeon.level.down ) {
            curAction = null;
            InterlevelScene.mode = InterlevelScene.Mode.DOWN2;
            Game.switchScene( InterlevelScene.class );

            return false;

        }
        if (pos == path && Dungeon.depth == 54 && pos == Dungeon.level.down ) {
            curAction = null;
            InterlevelScene.mode = InterlevelScene.Mode.DOWN3;
            Game.switchScene( InterlevelScene.class );

            return false;

        }
        if (pos == path && Dungeon.depth == 23 && pos == Dungeon.level.down ) {
            curAction = null;
            InterlevelScene.mode = InterlevelScene.Mode.DOWN4;
            Game.switchScene( InterlevelScene.class );

            return false;

        }
        if (pos == path && Dungeon.depth == 102 && pos == Dungeon.level.down ) {
            curAction = null;
            InterlevelScene.mode = InterlevelScene.Mode.DOWN5;
            Game.switchScene( InterlevelScene.class );

            return false;

        }
        if (pos == path && Dungeon.depth == 4 && pos == Dungeon.level.down ) {
            curAction = null;
            InterlevelScene.mode = InterlevelScene.Mode.DOWN6;
            Game.switchScene( InterlevelScene.class );

            return false;

        }
        if (pos == path && Dungeon.depth == 98 && pos == Dungeon.level.down ) {
            curAction = null;
            InterlevelScene.mode = InterlevelScene.Mode.DOWN7;
            Game.switchScene( InterlevelScene.class );

            return false;

        }
        if (pos == path && Dungeon.depth == 34 && pos == Dungeon.level.down ) {
            curAction = null;
            InterlevelScene.mode = InterlevelScene.Mode.DOWN8;
            Game.switchScene( InterlevelScene.class );

            return false;
        }
        if (pos == path && Dungeon.depth == 59 && pos == Dungeon.level.down ) {
            curAction = null;
            InterlevelScene.mode = InterlevelScene.Mode.DOWN9;
            Game.switchScene( InterlevelScene.class );

            return false;
        }
        if (pos == path && Dungeon.depth == 36 && pos == Dungeon.level.down ) {
            curAction = null;
            InterlevelScene.mode = InterlevelScene.Mode.DOWN10;
            Game.switchScene( InterlevelScene.class );

            return false;
        }
        if (pos == path && Dungeon.depth == 78 && pos == Dungeon.level.down ) {
            curAction = null;
            InterlevelScene.mode = InterlevelScene.Mode.DOWN11;
            Game.switchScene( InterlevelScene.class );

            return false;
        }
    else if (getCloser(path)) {

        return true;


    } else {
        ready();
        return false;
    }

    }

    private boolean actUp( HeroAction.Up action ) {
        int path = action.dst;
        if (pos == path && Dungeon.depth == 101 && pos == Dungeon.level.up ) {
            curAction = null;
            InterlevelScene.mode = InterlevelScene.Mode.UP1;
            Game.switchScene( InterlevelScene.class );

            return false;

        }
        //change to 102 for play build
        if (pos == path && Dungeon.depth == 102 && pos == Dungeon.level.up ) {
            curAction = null;
            if (U2TheEye.escaped) {
                InterlevelScene.mode = InterlevelScene.Mode.UP2;
                Game.switchScene(InterlevelScene.class);
            } else
                InterlevelScene.mode = InterlevelScene.Mode.UP6;
                Game.switchScene(InterlevelScene.class);
            return false;

        }
        if (pos == path && Dungeon.depth == 106 && pos == Dungeon.level.up ) {
            curAction = null;
            InterlevelScene.mode = InterlevelScene.Mode.UP3;
            Game.switchScene(InterlevelScene.class);

            return false;

        }
        if (pos == path && Dungeon.depth == 105 && pos == Dungeon.level.up ) {
            curAction = null;
            InterlevelScene.mode = InterlevelScene.Mode.UP4;
            Game.switchScene(InterlevelScene.class);

            return false;

        }
        if (pos == path && Dungeon.depth == 103 && pos == Dungeon.level.up ) {
            curAction = null;
            InterlevelScene.mode = InterlevelScene.Mode.UP5;
            Game.switchScene(InterlevelScene.class);

            return false;

        }
        if (pos == path && Dungeon.depth == 107 && pos == Dungeon.level.up ) {
            curAction = null;
            InterlevelScene.mode = InterlevelScene.Mode.UP7;
            Game.switchScene(InterlevelScene.class);

            return false;

        }
        if (pos == path && Dungeon.depth == 108 && pos == Dungeon.level.up ) {
            curAction = null;
            InterlevelScene.mode = InterlevelScene.Mode.UP10;
            Game.switchScene(InterlevelScene.class);

            return false;

        }
        if (pos == path && Dungeon.depth == 104 && pos == Dungeon.level.up ) {
            curAction = null;
            InterlevelScene.mode = InterlevelScene.Mode.UP11;
            Game.switchScene(InterlevelScene.class);

            return false;

        }
        else if (getCloser(path)) {

            return true;


        } else {
            ready();
            return false;
        }

    }


	private boolean actAttack( HeroAction.Attack action ) {

		enemy = action.target;

		if (Level.adjacent( pos, enemy.pos ) && enemy.isAlive() && !isCharmedBy( enemy )) {

			spend( attackDelay() );
			sprite.attack(enemy.pos);

			return false;

		} else {

			if (Level.fieldOfView[enemy.pos] && getCloser( enemy.pos )) {

				return true;

			} else {
				ready();
				return false;
			}

		}
	}

	public void rest( boolean tillHealthy ) {
		spendAndNext( TIME_TO_REST );
		if (!tillHealthy) {
            sprite.showStatus(CharSprite.DEFAULT, TXT_WAIT);
		}
		restoreHealth = tillHealthy;
	}

	@Override
	public int attackProc( Char enemy, int damage ) {
		KindOfWeapon wep = rangedWeapon != null ? rangedWeapon : belongings.weapon;
		if (wep != null) {

			wep.proc(this, enemy, damage);

			switch (subClass) {
			case GLADIATOR:
				if (wep instanceof MeleeWeapon) {
					damage += Buff.affect( this, Combo.class ).hit( enemy, damage );
				}
				break;
			case BATTLEMAGE:
				if (wep instanceof Wand) {
					Wand wand = (Wand)wep;
					if (wand.curCharges >= wand.maxCharges) {

						wand.use();

					} else if (damage > 0) {

						wand.curCharges++;
						wand.updateQuickslot();

						ScrollOfRecharging.charge( this );
					}
					damage += wand.curCharges;
				}
			case SNIPER:
				if (rangedWeapon != null) {
					Buff.prolong( this, SnipersMark.class, attackDelay() * 1.1f ).object = enemy.id();
				}
				break;
			default:
			}
		}

		return damage;
	}

	@Override
	public int defenseProc( Char enemy, int damage ) {

		RingOfThorns.Thorns thorns = buff( RingOfThorns.Thorns.class );
		if (thorns != null) {
			int dmg = Random.IntRange( 0, damage );
			if (dmg > 0) {
				enemy.damage( dmg, thorns );
			}
		}

		Earthroot.Armor armor = buff( Earthroot.Armor.class );
		if (armor != null) {
			damage = armor.absorb( damage );
		}

		if (belongings.armor != null) {
			damage = belongings.armor.proc( enemy, this, damage );
		}

		return damage;
	}

	@Override
	public void damage( int dmg, Object src ) {
		restoreHealth = false;
		super.damage( dmg, src );

		if (subClass == HeroSubClass.BERSERKER && 0 < HP && HP <= HT * Fury.LEVEL) {
			Buff.affect( this, Fury.class );
		}
	}

	private void checkVisibleMobs() {
		ArrayList<Mob> visible = new ArrayList<Mob>();

		boolean newMob = false;

		for (Mob m : Dungeon.level.mobs) {
			if (Level.fieldOfView[ m.pos ] && m.hostile) {
				visible.add( m );
				if (!visibleEnemies.contains( m )) {
					newMob = true;
				}
			}
		}

		if (newMob) {
			interrupt();
			restoreHealth = false;
		}

		visibleEnemies = visible;
	}

	public int visibleEnemies() {
		return visibleEnemies.size();
	}

	public Mob visibleEnemy( int index ) {
		return visibleEnemies.get(index % visibleEnemies.size());
	}

	private boolean getCloser( final int target ) {

		if (rooted) {
			Camera.main.shake( 1, 1f );
			return false;
		}

		int step = -1;

		if (Level.adjacent( pos, target )) {

			if (Actor.findChar( target ) == null) {
                if (Level.pit[target] && !flying && !Chasm.jumpConfirmed) {
                    Chasm.heroJump( this );
                    interrupt();
                    return false;
                }
               if (Level.solid[target] && (Dungeon.level.map[target] != Terrain.RUBBLE)
                        && (Dungeon.level.map[target] != Terrain.DOOR) && (Dungeon.level.map[target] != Terrain.RUINED_WALL)
                        && (Dungeon.level.map[target] != Terrain.WALL) &&
                        (Dungeon.level.map[target] != Terrain.DRESSER) && (Dungeon.level.map[target] != Terrain.WALL_DECO) && !flying) {
                    GameScene.show(new WndClimb());
                    interrupt();
                    return false;
                }
               if (Dungeon.level.map[target] == Terrain.DROPPOD_CLOSED){
                   DropPod dropPod = new DropPod();
                    dropPod.open(target);
                    interrupt();
                    return false;
                }
                if (Level.passable[target] || Level.avoid[target]) {
                    step = target;
                }
               /* if (Level.solid[target] && (Dungeon.level.map[target] == Terrain.DRESSER)) {
                    GameScene.show(new WndSearch());
                    interrupt();
                    return false;
                }*/
            }


		} else {

			int len = Level.LENGTH;
			boolean[] p = Level.passable;
			boolean[] v = Dungeon.level.visited;
			boolean[] m = Dungeon.level.mapped;
			boolean[] passable = new boolean[len];
			for (int i=0; i < len; i++) {
				passable[i] = p[i] && (v[i] || m[i]);
			}

			step = Dungeon.findPath( this, pos, target, passable, Level.fieldOfView );
		}

		if (step != -1) {


			int oldPos = pos;
			move( step );
			sprite.move( oldPos, pos );
			spend( 1 / speed() );

			return true;

		} else {

			return false;

		}

	}

	public boolean handle( int cell ) {
		
		if (cell == -1) {
			return false;
		}
		
		Char ch;
		Heap heap;

		if (Dungeon.level.map[cell] == Terrain.ALCHEMY && cell != pos) {
			
			curAction = new HeroAction.Cook( cell );
			
		} else if (Level.fieldOfView[cell] && (ch = Actor.findChar( cell )) instanceof Mob) {

            //selects the enemy so you can interact (i.e. attack) it
			if (ch instanceof NPC) {
				curAction = new HeroAction.Interact( (NPC)ch );
			} else {
				curAction = new HeroAction.Attack( ch );
			}
			
		} else if (Level.fieldOfView[cell] && (heap = Dungeon.level.heaps.get( cell )) != null) {

			switch (heap.type) {
			case HEAP:
				curAction = new HeroAction.PickUp( cell );
				break;
			case FOR_SALE:
				curAction = heap.size() == 1 && heap.peek().price() > 0 ? 
					new HeroAction.Buy( cell ) : 
					new HeroAction.PickUp( cell );
				break;
			default:
				curAction = new HeroAction.OpenChest( cell );
			}
			
		} else if (Dungeon.level.map[cell] == Terrain.LOCKED_DOOR || Dungeon.level.map[cell] == Terrain.RUINED_WALL_W) {
			
			curAction = new HeroAction.Unlock( cell );
			
		} else if (cell == Dungeon.level.west) {
			
			curAction = new HeroAction.West( cell );

        } else if (cell == Dungeon.level.north) {

            curAction = new HeroAction.North( cell );

        } else if (cell == Dungeon.level.south) {

            curAction = new HeroAction.South( cell );
			
		} else if (cell == Dungeon.level.east) {
			
			curAction = new HeroAction.East( cell );
			
		} else if (cell == Dungeon.level.down) {

            curAction = new HeroAction.Down(cell);

        } else if (cell == Dungeon.level.up) {
             curAction = new HeroAction.Up( cell );

        } else  {
			
			curAction = new HeroAction.Move( cell );
			lastAction = null;
			
		}

		return act();
	}
	
	public void earnExp( int exp ) {
		
		this.exp += exp;
		
		boolean levelUp = false;
		while (this.exp >= maxExp()) {
			this.exp -= maxExp();
			lvl++;
			
			HT += 5;
			HP += 5;			
			attackSkill++;
			defenseSkill++;
			
			if (lvl < 10) {
				updateAwareness();
			}
			
			levelUp = true;
		}
		
		if (levelUp) {
			
			GLog.p( TXT_NEW_LEVEL, lvl );
			sprite.showStatus( CharSprite.POSITIVE, TXT_LEVEL_UP );
			Sample.INSTANCE.play( Assets.SND_LEVELUP );
			
			Badges.validateLevelReached();
		}
		
		if (subClass == HeroSubClass.WARLOCK) {
			
			int value = Math.min( HT - HP, 1 + (Dungeon.depth - 1) / 5 );
			if (value > 0) {
				HP += value;
				sprite.emitter().burst( Speck.factory( Speck.HEALING ), 1 );
			}
			
			((Hunger)buff( Hunger.class )).satisfy( 10 );
		}
	}
	
	public int maxExp() {
		return 5 + lvl * 5;
	}
	
	void updateAwareness() {
		awareness = (float)(1 - Math.pow( 
			(heroClass == HeroClass.ROGUE ? 0.85 : 0.90), 
			(1 + Math.min( lvl,  9 )) * 0.5 
		));
	}
	
	public boolean isStarving() {
		return ((Hunger)buff( Hunger.class )).isStarving();
	}
	
	@Override
	public void add( Buff buff ) {
		super.add( buff );
		
		if (sprite != null) {
			if (buff instanceof Burning) {
				GLog.w( "You catch fire!" );
				interrupt();
			} else if (buff instanceof Paralysis) {
				GLog.w( "You are paralysed!" );
				interrupt();
			} else if (buff instanceof Poison) {
				GLog.w( "You are poisoned!" );
				interrupt();
			} else if (buff instanceof Ooze) {
				GLog.w( "Caustic ooze eats your flesh. Wash away it!" );
			} else if (buff instanceof Roots) {
				GLog.w( "You can't move!" );
			} else if (buff instanceof Weakness) {
				GLog.w( "You feel weakened!" );
			} else if (buff instanceof Blindness) {
				GLog.w( "You are blinded!" );
			} else if (buff instanceof Fury) {
				GLog.w( "You become furious!" );
				sprite.showStatus( CharSprite.POSITIVE, "furious" );
			} else if (buff instanceof Charm) {
				GLog.w( "You are charmed!" );
			}  else if (buff instanceof Cripple) {
				GLog.w( "You are crippled!" );
			} else if (buff instanceof Bleeding) {
				GLog.w( "You are bleeding!" );
			} else if (buff instanceof Stunned) {
				GLog.w( "Everything is spinning around you!" );
				interrupt();
			}
			
			else if (buff instanceof Light) {
				sprite.add( CharSprite.State.ILLUMINATED );
			}
		}
		
		BuffIndicator.refreshHero();
	}
	
	@Override
	public void remove( Buff buff ) {
		super.remove(buff);
		
		if (buff instanceof Light) {
			sprite.remove( CharSprite.State.ILLUMINATED );
		}
		
		BuffIndicator.refreshHero();
	}
	
	@Override
	public int stealth() {
		int stealth = super.stealth();
		for (Buff buff : buffs( RingOfShadows.Shadows.class )) {
			stealth += ((RingOfShadows.Shadows)buff).level;
		}
		return stealth;
	}
	
	@Override
	public void die( Object cause  ) {
		
		curAction = null;
		
		DewVial.autoDrink(this);
		if (isAlive()) {
			new Flare( 8, 32 ).color( 0xFFFF66, true ).show(sprite, 2f) ;
			return;
		}

        Actor.fixTime();
        super.die( cause );

        Ankh ankh = (Ankh)belongings.getItem( Ankh.class );
        if (ankh == null) {

            reallyDie( cause );

        } else {

            Dungeon.deleteGame( Dungeon.hero.heroClass, false );
            GameScene.show( new WndResurrect(  cause ) );

        }
	}
	
	public static void reallyDie( Object cause ) {

        int length = Level.LENGTH;
        int[] map = Dungeon.level.map;
        boolean[] visited = Dungeon.level.visited;
        boolean[] discoverable = Level.discoverable;

        for (int i=0; i < length; i++) {

            int terr = map[i];

            if (discoverable[i]) {

                visited[i] = true;
                if ((Terrain.flags[terr] & Terrain.SECRET) != 0) {
                    Level.set( i, Terrain.discover( terr ) );
                    GameScene.updateMap( i );
                }
            }
        }

        Bones.leave();

        Dungeon.observe();

        Dungeon.hero.belongings.identify();

        int pos = Dungeon.hero.pos;

        ArrayList<Integer> passable = new ArrayList<Integer>();
        for (Integer ofs : Level.NEIGHBOURS8) {
            int cell = pos + ofs;
            if ((Level.passable[cell] || Level.avoid[cell]) && Dungeon.level.heaps.get( cell ) == null) {
                passable.add( cell );
            }
        }
        Collections.shuffle( passable );

        ArrayList<Item> items = new ArrayList<Item>( Dungeon.hero.belongings.backpack.items );
        for (Integer cell : passable) {
            if (items.isEmpty()) {
                break;
            }

            Item item = Random.element( items );
            Dungeon.level.drop( item, cell ).sprite.drop( pos );
            items.remove( item );
        }

        //GameScene.gameOver();
        GameScene.show( new WndResurrect(  cause ) );
        WndRequisition.delivered = false;

	/*	if (cause instanceof Doom) {
			((Doom)cause).onDeath();
		}*/

        Dungeon.deleteGame( Dungeon.hero.heroClass, false );
    }

    public static void reallyreallyDie( Object cause ) {

        int length = Level.LENGTH;
        int[] map = Dungeon.level.map;
        boolean[] visited = Dungeon.level.visited;
        boolean[] discoverable = Level.discoverable;

        for (int i=0; i < length; i++) {

            int terr = map[i];

            if (discoverable[i]) {

                visited[i] = true;
                if ((Terrain.flags[terr] & Terrain.SECRET) != 0) {
                    Level.set( i, Terrain.discover( terr ) );
                    GameScene.updateMap( i );
                }
            }
        }

        Bones.leave();

        Dungeon.observe();

        Dungeon.hero.belongings.identify();

        int pos = Dungeon.hero.pos;

        ArrayList<Integer> passable = new ArrayList<Integer>();
        for (Integer ofs : Level.NEIGHBOURS8) {
            int cell = pos + ofs;
            if ((Level.passable[cell] || Level.avoid[cell]) && Dungeon.level.heaps.get( cell ) == null) {
                passable.add( cell );
            }
        }
        Collections.shuffle( passable );

        ArrayList<Item> items = new ArrayList<Item>( Dungeon.hero.belongings.backpack.items );
        for (Integer cell : passable) {
            if (items.isEmpty()) {
                break;
            }

            Item item = Random.element( items );
            Dungeon.level.drop( item, cell ).sprite.drop( pos );
            items.remove( item );
        }

        GameScene.gameOver();


		if (cause instanceof Doom) {
			((Doom)cause).onDeath();
		}

        //forcing dungeon entrances to clear
        U2TheEye.escaped = false;

        Dungeon.deleteGame( Dungeon.hero.heroClass, true );
    }

	@Override
	public void move( int step ) {
		super.move( step );
		
		if (!flying || !climbing) {
			
			if (Level.water[pos]) {
				Sample.INSTANCE.play( Assets.SND_WATER, 1, 1, Random.Float( 0.8f, 1.25f ) );
			} else {
				Sample.INSTANCE.play( Assets.SND_STEP );
			}
			Dungeon.level.press( pos, this );
		}
	}
	
	@Override
	public void onMotionComplete() {
		Dungeon.observe();
		search( false );
			
		super.onMotionComplete();
	}
	
	@Override
	public void onAttackComplete() {
		
		AttackIndicator.target( enemy );
		
		attack( enemy );
		curAction = null;
		
		Invisibility.dispel();

		super.onAttackComplete();
	}
	
	@Override
	public void onOperateComplete() {
		
		if (curAction instanceof HeroAction.Unlock) {
			
			if (theKey != null) {
				theKey.detach( belongings.backpack );
				theKey = null;
			}
			
			int doorCell = ((HeroAction.Unlock)curAction).dst;
			int door = Dungeon.level.map[doorCell];
			
			Level.set( doorCell, door == Terrain.LOCKED_DOOR ? Terrain.DOOR : Terrain.RUINED_WALL );
			GameScene.updateMap( doorCell );
			
		} else if (curAction instanceof HeroAction.OpenChest) {
			
			if (theKey != null) {
				theKey.detach( belongings.backpack );
				theKey = null;
			}
			
			Heap heap = Dungeon.level.heaps.get( ((HeroAction.OpenChest)curAction).dst ); 
			if (heap.type == Type.SKELETON) {
				Sample.INSTANCE.play( Assets.SND_BONES );
			}
			heap.open( this );
		}
		curAction = null;

		super.onOperateComplete();
	}
	
	public boolean search( boolean intentional ) {
		
		boolean smthFound = false;
		
		int positive = 0;
		int negative = 0;
		for (Buff buff : buffs( RingOfDetection.Detection.class )) {
			int bonus = ((RingOfDetection.Detection)buff).level;
			if (bonus > positive) {
				positive = bonus;
			} else if (bonus < 0) {
				negative += bonus;
			}
		}
		int distance = 1 + positive + negative;

		float level = intentional ? (10 * awareness - awareness * awareness) : awareness;
		if (distance <= 0) {
			level /= 2 - distance;
			distance = 1;
		}
		
		int cx = pos % Level.WIDTH;
		int cy = pos / Level.WIDTH;
		int ax = cx - distance;
		if (ax < 0) {
			ax = 0;
		}
		int bx = cx + distance;
		if (bx >= Level.WIDTH) {
			bx = Level.WIDTH - 1;
		}
		int ay = cy - distance;
		if (ay < 0) {
			ay = 0;
		}
		int by = cy + distance;
		if (by >= Level.HEIGHT) {
			by = Level.HEIGHT - 1;
		}
		
		for (int y = ay; y <= by; y++) {
			for (int x = ax, p = ax + y * Level.WIDTH; x <= bx; x++, p++) {
				
				if (Dungeon.visible[p]) {
					
					if (intentional) {
						sprite.parent.addToBack( new CheckedCell( p ) );
					}
					
					if (Level.secret[p] && (intentional || Random.Float() < level)) {
						
						int oldValue = Dungeon.level.map[p];
						
						GameScene.discoverTile( p, oldValue );
						
						Level.set( p, Terrain.discover( oldValue ) );	
						
						GameScene.updateMap( p );
						
						ScrollOfMagicMapping.discover( p );
						
						smthFound = true;
					}
				}
			}
		}

		
		if (intentional) {
			sprite.showStatus( CharSprite.DEFAULT, TXT_SEARCH );
			sprite.operate( pos );
			if (smthFound) {
				spendAndNext( Random.Float() < level ? TIME_TO_SEARCH : TIME_TO_SEARCH * 2 );
			} else {
				spendAndNext( TIME_TO_SEARCH );
			}
			
		}
		
		if (smthFound) {
			GLog.w( TXT_NOTICED_SMTH );
			Sample.INSTANCE.play( Assets.SND_SECRET );
			interrupt();
		}
		
		return smthFound;
	}
	
	public void resurrect( int resetLevel ) {
		
		HP = HT;
		Dungeon.gold = 0;
		exp = 0;
		
		belongings.resurrect( resetLevel );
		
		live();
	}
	
	@Override
	public HashSet<Class<?>> resistances() {
		RingOfElements.Resistance r = buff( RingOfElements.Resistance.class );
		return r == null ? super.resistances() : r.resistances();
	}
	
	@Override
	public HashSet<Class<?>> immunities() {
		GasesImmunity buff = buff( GasesImmunity.class );
		return buff == null ? super.immunities() : GasesImmunity.IMMUNITIES;
	}
	
	@Override
	public void next() {
		super.next();
	}
	
	public static interface Doom {
		public void onDeath();
	}

    protected CellSelector.Listener leaper = new  CellSelector.Listener() {

        @Override
        public void onSelect( Integer target ) {
            if (target == null && target == pos) {

                int cell = Ballistica.cast(pos, pos+500, false, true);
                if (Actor.findChar( cell ) != null && cell != pos) {
                    cell = Ballistica.trace[Ballistica.distance - 2];
                }

                Invisibility.dispel();

                final int dest = pos+500;
                busy();
                sprite.jump( pos, pos+500, new Callback() {
                    @Override
                    public void call() {
                        move(dest);
                        Dungeon.level.press(dest, Dungeon.hero);
                        Dungeon.observe();

                        CellEmitter.center(dest).burst(Speck.factory(Speck.DUST), 10);
                        Camera.main.shake(2, 0.5f);

                    }
                } );
            }
        }

        @Override
        public String prompt() {
            return null;
        }

    };
}

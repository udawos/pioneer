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

import com.udawos.pioneer.Assets;
import com.udawos.pioneer.Badges;
import com.udawos.pioneer.items.FlintAndTinder;
import com.udawos.pioneer.items.ammunition.Bullet;
import com.udawos.pioneer.items.ammunition.Gunpowder;
import com.udawos.pioneer.items.ammunition.PercussionCap;
import com.udawos.pioneer.items.bags.Keyring;
import com.udawos.pioneer.items.medicines.Bandage;
import com.udawos.pioneer.items.potions.PotionOfHealing;
import com.udawos.pioneer.items.rings.RingOfShadows;
import com.udawos.pioneer.items.scrolls.ScrollOfIdentify;
import com.udawos.pioneer.items.scrolls.ScrollOfMagicMapping;
import com.udawos.pioneer.items.wands.WandOfBullet;
import com.udawos.pioneer.items.weapon.melee.Dagger;
import com.udawos.pioneer.items.weapon.melee.Knuckles;
import com.udawos.pioneer.items.weapon.missiles.Boomerang;
import com.udawos.pioneer.items.weapon.missiles.Dart;
import com.udawos.pioneer.ui.QuickSlot;
import com.udawos.utils.Bundle;

public enum HeroClass {

	PATHFINDER( "pathfinder" ), MAGE( "mage" ), ROGUE( "rogue" ), HUNTRESS( "huntress" );
	
	private String title;
	
	private HeroClass( String title ) {
		this.title = title;
	}
	
	public static final String[] WAR_PERKS = {
		"You are a Pathfinder, acting in the service of the 1st Federation.",
		"You have been tasked to track down a group of eight idolatrous peasants.",
		"These heathens have stolen taxes from the Federation and fled into the 9th Highlands (15th Border Region)",
		"You will track down the peasants and recover the taxes.",
		"Upon completion, contact Command.",
	};
	
	public static final String[] MAG_PERKS = {
		"Mages start with a unique Wand of Magic Missile. This wand can be later \"disenchanted\" to upgrade another wand.",
		"Mages recharge their wands faster.",
		"When eaten, any piece of food restores 1 charge for all wands in the inventory.",
		"Mages can use wands as a melee weapon.",
		"Scrolls of Identify are identified from the beginning."
	};
	
	public static final String[] ROG_PERKS = {
		"Rogues start with a Ring of Shadows+1.",
		"Rogues identify a type of a ring on equipping it.",
		"Rogues are proficient with light armor, dodging better while wearing one.",
		"Rogues are proficient in detecting hidden doors and traps.",
		"Rogues can go without food longer.",
		"Scrolls of Magic Mapping are identified from the beginning."
	};
	
	public static final String[] HUN_PERKS = {
		"Huntresses start with 15 points of Health.",
		"Huntresses start with a unique upgradeable boomerang.",
		"Huntresses are proficient with missile weapons and get a damage bonus for excessive strength when using them.",
		"Huntresses gain more health from dewdrops.",
		"Huntresses sense neighbouring monsters even if they are hidden behind obstacles."
	};
	
	public void initHero( Hero hero ) {
		
		hero.heroClass = this;
		
		initCommon( hero );
		
		switch (this) {
		case PATHFINDER:
			initWarrior( hero );
			break;
		}

		//if (Badges.isUnlocked( masteryBadge() )) {
		//	new TomeOfMastery().collect();
		//}
		
		hero.updateAwareness();
	}
	
	private static void initCommon( Hero hero ) {
	//	new Food().identify().collect();
        new Keyring().collect();
	}
	
	public Badges.Badge masteryBadge() {
		switch (this) {
		case PATHFINDER:
			return Badges.Badge.MASTERY_WARRIOR;
		case MAGE:
			return Badges.Badge.MASTERY_MAGE;
		case ROGUE:
			return Badges.Badge.MASTERY_ROGUE;
		case HUNTRESS:
			return Badges.Badge.MASTERY_HUNTRESS;
		}
		return null;
	}
	
	private static void initWarrior( Hero hero ) {
		hero.STR = hero.STR + 1;

        //(hero.belongings.weapon = new WandOfBullet()).identify();
        //QuickSlot.primaryValue = WandOfBullet.class;
        WandOfBullet wand = new WandOfBullet();
        wand.identify().collect();

        new Gunpowder(20).identify().collect();

        new PercussionCap(20).identify().collect();

        new Bullet(20).identify().collect();

       //new ClimbingBolts(10).identify().collect();

       new FlintAndTinder(5).identify().collect();

        //new PunchCard().collect();

        new PotionOfHealing().identify().collect();
       // new ScrollOfMagicMapping(99).collect();

        new Bandage(3).collect();


      // new Bottle().collect();

	}
	
	private static void initMage( Hero hero ) {	
		(hero.belongings.weapon = new Knuckles()).identify();

		new ScrollOfIdentify().setKnown();
	}
	
	private static void initRogue( Hero hero ) {
		(hero.belongings.weapon = new Dagger()).identify();
		(hero.belongings.ring1 = new RingOfShadows()).upgrade().identify();
		new Dart( 8 ).identify().collect();
		
		hero.belongings.ring1.activate( hero );
		
		QuickSlot.primaryValue = Dart.class;
		
		new ScrollOfMagicMapping().setKnown();
	}
	
	private static void initHuntress( Hero hero ) {
		
		hero.HP = (hero.HT -= 5);
		
		(hero.belongings.weapon = new Dagger()).identify();
		Boomerang boomerang = new Boomerang();
		boomerang.identify().collect();
		
		QuickSlot.primaryValue = boomerang;
	}
	
	public String title() {
		return title;
	}
	
	public String spritesheet() {
		
		switch (this) {
		case PATHFINDER:
			return Assets.WARRIOR;
		case MAGE:
			return Assets.MAGE;
		case ROGUE:
			return Assets.ROGUE;
		case HUNTRESS:
			return Assets.HUNTRESS;
		}
		
		return null;
	}
	
	public String[] perks() {
		
		switch (this) {
		case PATHFINDER:
			return WAR_PERKS;
		case MAGE:
			return MAG_PERKS;
		case ROGUE:
			return ROG_PERKS;
		case HUNTRESS:
			return HUN_PERKS;
		}
		
		return null;
	}

	private static final String CLASS	= "class";
	
	public void storeInBundle( Bundle bundle ) {
		bundle.put( CLASS, toString() );
	}
	
	public static HeroClass restoreInBundle( Bundle bundle ) {
		String value = bundle.getString( CLASS );
		return value.length() > 0 ? valueOf( value ) : ROGUE;
	}
}

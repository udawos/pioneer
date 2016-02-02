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
package com.udawos.pioneer.items;

import com.udawos.pioneer.Dungeon;
import com.udawos.pioneer.actors.hero.Hero;
import com.udawos.pioneer.items.armor.Armor;
import com.udawos.pioneer.items.armor.ClothArmor;
import com.udawos.pioneer.items.armor.LeatherArmor;
import com.udawos.pioneer.items.armor.MailArmor;
import com.udawos.pioneer.items.armor.PlateArmor;
import com.udawos.pioneer.items.armor.ScaleArmor;
import com.udawos.pioneer.items.bags.Bag;
import com.udawos.pioneer.items.food.Food;
import com.udawos.pioneer.items.food.Grains;
import com.udawos.pioneer.items.food.OrangeBerries;
import com.udawos.pioneer.items.food.ShinyBerries;
import com.udawos.pioneer.items.food.WeirdBerries;
import com.udawos.pioneer.items.potions.Potion;
import com.udawos.pioneer.items.potions.PotionOfExperience;
import com.udawos.pioneer.items.potions.PotionOfFrost;
import com.udawos.pioneer.items.potions.PotionOfHealing;
import com.udawos.pioneer.items.potions.PotionOfInvisibility;
import com.udawos.pioneer.items.potions.PotionOfLevitation;
import com.udawos.pioneer.items.potions.PotionOfLiquidFlame;
import com.udawos.pioneer.items.potions.PotionOfMight;
import com.udawos.pioneer.items.potions.PotionOfMindVision;
import com.udawos.pioneer.items.potions.PotionOfParalyticGas;
import com.udawos.pioneer.items.potions.PotionOfPurity;
import com.udawos.pioneer.items.potions.PotionOfStrength;
import com.udawos.pioneer.items.potions.PotionOfToxicGas;
import com.udawos.pioneer.items.rings.Ring;
import com.udawos.pioneer.items.rings.RingOfAccuracy;
import com.udawos.pioneer.items.rings.RingOfDetection;
import com.udawos.pioneer.items.rings.RingOfElements;
import com.udawos.pioneer.items.rings.RingOfEvasion;
import com.udawos.pioneer.items.rings.RingOfHaggler;
import com.udawos.pioneer.items.rings.RingOfHaste;
import com.udawos.pioneer.items.rings.RingOfHerbalism;
import com.udawos.pioneer.items.rings.RingOfMending;
import com.udawos.pioneer.items.rings.RingOfPower;
import com.udawos.pioneer.items.rings.RingOfSatiety;
import com.udawos.pioneer.items.rings.RingOfShadows;
import com.udawos.pioneer.items.rings.RingOfThorns;
import com.udawos.pioneer.items.scrolls.Scroll;
import com.udawos.pioneer.items.scrolls.ScrollOfChallenge;
import com.udawos.pioneer.items.scrolls.ScrollOfEnchantment;
import com.udawos.pioneer.items.scrolls.ScrollOfIdentify;
import com.udawos.pioneer.items.scrolls.ScrollOfLullaby;
import com.udawos.pioneer.items.scrolls.ScrollOfMagicMapping;
import com.udawos.pioneer.items.scrolls.ScrollOfPsionicBlast;
import com.udawos.pioneer.items.scrolls.ScrollOfRecharging;
import com.udawos.pioneer.items.scrolls.ScrollOfRemoveCurse;
import com.udawos.pioneer.items.scrolls.ScrollOfTeleportation;
import com.udawos.pioneer.items.scrolls.ScrollOfTerror;
import com.udawos.pioneer.items.scrolls.ScrollOfUpgrade;
import com.udawos.pioneer.items.wands.Wand;
import com.udawos.pioneer.items.wands.WandOfBullet;
import com.udawos.pioneer.items.weapon.Weapon;
import com.udawos.pioneer.items.weapon.melee.BattleAxe;
import com.udawos.pioneer.items.weapon.melee.Dagger;
import com.udawos.pioneer.items.weapon.melee.Glaive;
import com.udawos.pioneer.items.weapon.melee.Knuckles;
import com.udawos.pioneer.items.weapon.melee.Longsword;
import com.udawos.pioneer.items.weapon.melee.Mace;
import com.udawos.pioneer.items.weapon.melee.Quarterstaff;
import com.udawos.pioneer.items.weapon.melee.ShortSword;
import com.udawos.pioneer.items.weapon.melee.Spear;
import com.udawos.pioneer.items.weapon.melee.Sword;
import com.udawos.pioneer.items.weapon.melee.WarHammer;
import com.udawos.pioneer.items.weapon.missiles.Boomerang;
import com.udawos.pioneer.items.weapon.missiles.CurareDart;
import com.udawos.pioneer.items.weapon.missiles.Dart;
import com.udawos.pioneer.items.weapon.missiles.IncendiaryDart;
import com.udawos.pioneer.items.weapon.missiles.Javelin;
import com.udawos.pioneer.items.weapon.missiles.Shuriken;
import com.udawos.pioneer.items.weapon.missiles.Tamahawk;
import com.udawos.pioneer.plants.Dreamweed;
import com.udawos.pioneer.plants.Earthroot;
import com.udawos.pioneer.plants.Fadeleaf;
import com.udawos.pioneer.plants.Firebloom;
import com.udawos.pioneer.plants.Icecap;
import com.udawos.pioneer.plants.Plant;
import com.udawos.pioneer.plants.Sorrowmoss;
import com.udawos.pioneer.plants.Sungrass;
import com.udawos.utils.Random;

import java.util.HashMap;

public class Generator {

	public static enum Category {
		WEAPON	( 15,	Weapon.class ),
		ARMOR	( 10,	Armor.class ),
		POTION	( 50,	Potion.class ),
		SCROLL	( 40,	Scroll.class ),
		WAND	( 4,	Wand.class ),
		RING	( 2,	Ring.class ),
		SEED	( 5,	Plant.Seed.class ),
		FOOD	( 0,	Food.class ),
		GOLD	( 50,	Gold.class ),
		MISC	( 5,	Item.class );

		
		public Class<?>[] classes;
		public float[] probs;
		
		public float prob;
		public Class<? extends Item> superClass;
		
		private Category( float prob, Class<? extends Item> superClass ) {
			this.prob = prob;
			this.superClass = superClass;
		}
		
		public static int order( Item item ) {
			for (int i=0; i < values().length; i++) {
				if (values()[i].superClass.isInstance( item )) {
					return i;
				}
			}
			
			return item instanceof Bag ? Integer.MAX_VALUE : Integer.MAX_VALUE - 1;
		}
	};
	
	private static HashMap<Category,Float> categoryProbs = new HashMap<Generator.Category, Float>();
	
	static {
		
		Category.GOLD.classes = new Class<?>[]{ 
			Gold.class };
		Category.GOLD.probs = new float[]{ 1 };
		
		Category.SCROLL.classes = new Class<?>[]{ 
			ScrollOfIdentify.class, 
			ScrollOfTeleportation.class, 
			ScrollOfRemoveCurse.class, 
			ScrollOfRecharging.class,
			ScrollOfMagicMapping.class,
			ScrollOfChallenge.class,
			ScrollOfTerror.class,
			ScrollOfLullaby.class,
			ScrollOfPsionicBlast.class,
			//ScrollOfMirrorImage.class,
			ScrollOfUpgrade.class,
			ScrollOfEnchantment.class };
		Category.SCROLL.probs = new float[]{ 30, 10, 15, 10, 15, 12, 8, 8, 4, 6, 0, 1 };
		
		Category.POTION.classes = new Class<?>[]{ 
			PotionOfHealing.class, 
			PotionOfExperience.class,
			PotionOfToxicGas.class, 
			PotionOfParalyticGas.class,
			PotionOfLiquidFlame.class,
			PotionOfLevitation.class,
			PotionOfStrength.class,
			PotionOfMindVision.class,
			PotionOfPurity.class,
			PotionOfInvisibility.class,
			PotionOfMight.class,
			PotionOfFrost.class };
		Category.POTION.probs = new float[]{ 45, 4, 15, 10, 15, 10, 0, 20, 12, 10, 0, 10 };
		
		Category.WAND.classes = new Class<?>[]{
			WandOfBullet.class};
		Category.WAND.probs = new float[]{ 10, 10, 15, 6, 10, 11, 15, 10, 6, 10, 0, 5, 5 };
		
		Category.WEAPON.classes = new Class<?>[]{ 
			Dagger.class, 
			Knuckles.class,
			Quarterstaff.class, 
			Spear.class, 
			Mace.class, 
			Sword.class, 
			Longsword.class,
			BattleAxe.class,
			WarHammer.class, 
			Glaive.class,
			ShortSword.class,
			Dart.class,
			Javelin.class,
			IncendiaryDart.class,
			CurareDart.class,
			Shuriken.class,
			Boomerang.class,
			Tamahawk.class };
		Category.WEAPON.probs = new float[]{ 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 0, 1, 1, 1, 1, 0, 1 };
		
		Category.ARMOR.classes = new Class<?>[]{ 
			ClothArmor.class, 
			LeatherArmor.class, 
			MailArmor.class, 
			ScaleArmor.class, 
			PlateArmor.class };
		Category.ARMOR.probs = new float[]{ 1, 1, 1, 1, 1 };
		
		Category.FOOD.classes = new Class<?>[]{ 
			Grains.class,
			ShinyBerries.class,
            OrangeBerries.class,
            WeirdBerries.class};
			//Food.class,
			//Pasty.class,
			//MysteryMeat.class };
		Category.FOOD.probs = new float[]{ 4, 1, 0 };
			
		Category.RING.classes = new Class<?>[]{ 
			RingOfMending.class,
			RingOfDetection.class,
			RingOfShadows.class,
			RingOfPower.class,
			RingOfHerbalism.class,
			RingOfAccuracy.class,
			RingOfEvasion.class,
			RingOfSatiety.class,
			RingOfHaste.class,
			RingOfElements.class,
			RingOfHaggler.class,
			RingOfThorns.class };
		Category.RING.probs = new float[]{ 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 0 };
		
		Category.SEED.classes = new Class<?>[]{
			Firebloom.Seed.class,
			Icecap.Seed.class,
			Sorrowmoss.Seed.class,
			Dreamweed.Seed.class,
			Sungrass.Seed.class,
			Earthroot.Seed.class,
			Fadeleaf.Seed.class,
			//LastPrime.Rotberry.Seed.class
			};
		Category.SEED.probs = new float[]{ 1, 1, 1, 1, 1, 1, 1,0 };

        Category.MISC.classes = new Class<?>[]{
		    Bomb.class,
        };
		Category.MISC.probs = new float[]{ 2, 1 };

	}
	
	public static void reset() {
		for (Category cat : Category.values()) {
			categoryProbs.put( cat, cat.prob );
		}
	}
	
	public static Item random() {
		return random( Random.chances( categoryProbs ) );
	}
	
	public static Item random( Category cat ) {
		try {
			
			categoryProbs.put( cat, categoryProbs.get( cat ) / 2 );
			
			switch (cat) {
			case ARMOR:
				return randomArmor();
			case WEAPON:
				return randomWeapon();
			default:
				return ((Item)cat.classes[Random.chances( cat.probs )].newInstance()).random();
			}
			
		} catch (Exception e) {

			return null;
			
		}
	}
	
	public static Item random( Class<? extends Item> cl ) {
		try {
			
			return ((Item)cl.newInstance()).random();
			
		} catch (Exception e) {

			return null;
			
		}
	}
	
	public static Armor randomArmor() throws Exception {
		
		int curStr = Hero.STARTING_STR + Dungeon.potionOfStrength;
		
		Category cat = Category.ARMOR;
		
		Armor a1 = (Armor)cat.classes[Random.chances( cat.probs )].newInstance();
		Armor a2 = (Armor)cat.classes[Random.chances( cat.probs )].newInstance();
		
		a1.random();
		a2.random();
		
		return Math.abs( curStr - a1.STR ) < Math.abs( curStr - a2.STR ) ? a1 : a2;
	}
	
	public static Weapon randomWeapon() throws Exception {
		
		int curStr = Hero.STARTING_STR + Dungeon.potionOfStrength;
		
		Category cat = Category.WEAPON;
		
		Weapon w1 = (Weapon)cat.classes[Random.chances( cat.probs )].newInstance();
		Weapon w2 = (Weapon)cat.classes[Random.chances( cat.probs )].newInstance();
		
		w1.random();
		w2.random();
		
		return Math.abs( curStr - w1.STR ) < Math.abs( curStr - w2.STR ) ? w1 : w2;
	}
}

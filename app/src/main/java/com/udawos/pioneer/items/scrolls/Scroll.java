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
package com.udawos.pioneer.items.scrolls;

import com.udawos.pioneer.Badges;
import com.udawos.pioneer.actors.buffs.Blindness;
import com.udawos.pioneer.actors.hero.Hero;
import com.udawos.pioneer.items.Item;
import com.udawos.pioneer.items.ItemStatusHandler;
import com.udawos.pioneer.sprites.ItemSpriteSheet;
import com.udawos.pioneer.utils.GLog;
import com.udawos.utils.Bundle;

import java.util.ArrayList;
import java.util.HashSet;

public abstract class Scroll extends Item {

	private static final String TXT_BLINDED	= "You can't read a scroll while blinded";
	
	public static final String AC_READ	= "READ";
	
	protected static final float TIME_TO_READ	= 1f;
	
	private static final Class<?>[] scrolls = {
		//ScrollOfIdentify.class,
		ScrollOfMagicMapping.class, 
		//ScrollOfRecharging.class,
		//ScrollOfRemoveCurse.class,
		//ScrollOfTeleportation.class,
		//ScrollOfChallenge.class,
		//ScrollOfTerror.class,
		//ScrollOfLullaby.class,
		//ScrollOfPsionicBlast.class,
	//	ScrollOfMirrorImage.class,
		//ScrollOfUpgrade.class,
		//ScrollOfEnchantment.class
	};
	private static final String[] runes = 
		{  "BERKANAN", "ODAL", "TIWAZ"};
	private static final Integer[] images = {

		ItemSpriteSheet.SCROLL_BERKANAN, 
		ItemSpriteSheet.SCROLL_ODAL, 
		ItemSpriteSheet.SCROLL_TIWAZ};
	
	private static ItemStatusHandler<Scroll> handler;
	
	private String rune;
	
	{
		stackable = true;		
		defaultAction = AC_READ;
	}
	
	@SuppressWarnings("unchecked")
	public static void initLabels() {
		handler = new ItemStatusHandler<Scroll>( (Class<? extends Scroll>[])scrolls, runes, images );
	}
	
	public static void save( Bundle bundle ) {
		handler.save( bundle );
	}
	
	@SuppressWarnings("unchecked")
	public static void restore( Bundle bundle ) {
		handler = new ItemStatusHandler<Scroll>( (Class<? extends Scroll>[])scrolls, runes, images, bundle );
	}
	
	public Scroll() {
		super();
		image = handler.image( this );
		rune = handler.label( this );
	}
	
	@Override
	public ArrayList<String> actions( Hero hero ) {
		ArrayList<String> actions = super.actions( hero );
		actions.add( AC_READ );
		return actions;
	}
	
	@Override
	public void execute( Hero hero, String action ) {
		if (action.equals( AC_READ )) {
			
			if (hero.buff( Blindness.class ) != null) {
				GLog.w( TXT_BLINDED );
			} else {
				curUser = hero;
				curItem = detach( hero.belongings.backpack );
				doRead();
			}
			
		} else {
		
			super.execute( hero, action );
			
		}
	}
	
	abstract protected void doRead();
	
	public boolean isKnown() {
		return handler.isKnown( this );
	}
	
	public void setKnown() {
		if (!isKnown()) {
			handler.know( this );
		}
		
		Badges.validateAllScrollsIdentified();
	}
	
	@Override
	public Item identify() {
		setKnown();
		return super.identify();
	}
	
	@Override
	public String name() {
		return isKnown() ? name : "scroll \"" + rune + "\"";
	}
	
	@Override
	public String info() {
		return isKnown() ?
			desc() :
			"This parchment is covered with indecipherable writing, and bears a title " +
			"of rune " + rune + ". Who knows what it will do when read aloud?";
	}
	
	@Override
	public boolean isUpgradable() {
		return false;
	}
	
	@Override
	public boolean isIdentified() {
		return isKnown();
	}
	
	public static HashSet<Class<? extends Scroll>> getKnown() {
		return handler.known();
	}
	
	public static HashSet<Class<? extends Scroll>> getUnknown() {
		return handler.unknown();
	}
	
	public static boolean allKnown() {
		return handler.known().size() == scrolls.length;
	}
	
	@Override
	public int price() {
		return 15 * quantity;
	}
}

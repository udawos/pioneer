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

import com.udawos.pioneer.Badges;
import com.udawos.pioneer.Dungeon;
import com.udawos.pioneer.items.Item;
import com.udawos.pioneer.items.KindOfWeapon;
import com.udawos.pioneer.items.ammunition.Bullet;
import com.udawos.pioneer.items.ammunition.Gunpowder;
import com.udawos.pioneer.items.ammunition.PercussionCap;
import com.udawos.pioneer.items.armor.Armor;
import com.udawos.pioneer.items.bags.Bag;
import com.udawos.pioneer.items.keys.IronKey;
import com.udawos.pioneer.items.keys.Key;
import com.udawos.pioneer.items.medicines.Bandage;
import com.udawos.pioneer.items.quest.PunchCard;
import com.udawos.pioneer.items.rings.Ring;
import com.udawos.pioneer.items.scrolls.ScrollOfRemoveCurse;
import com.udawos.pioneer.items.wands.Wand;
import com.udawos.pioneer.items.weapon.Reloader;
import com.udawos.pioneer.utils.GLog;
import com.udawos.utils.Bundle;
import com.udawos.utils.Random;

import java.util.Iterator;

public class Belongings implements Iterable<Item> {

	public static final int BACKPACK_SIZE	= 12;
	
	private Hero owner;

    public static int tier = 0;

    public Bag backpack;

	public KindOfWeapon weapon = null;
	public Armor armor = null;
	public Ring ring1 = null;
	public Ring ring2 = null;

	public Belongings( Hero owner ) {
		this.owner = owner;
		
		backpack = new Bag() {{
			name = "backpack";
			size = BACKPACK_SIZE;
		}};
		backpack.owner = owner;
	}
	
	private static final String WEAPON		= "weapon";
	private static final String ARMOR		= "armor";
	private static final String RING1		= "ring1";
	private static final String RING2		= "ring2";
	private static final String TIER		= "tier";

	
	public void storeInBundle( Bundle bundle ) {
		
		backpack.storeInBundle(bundle);
		
		bundle.put(WEAPON, weapon);
		bundle.put(ARMOR, armor);
		bundle.put(RING1, ring1);
		bundle.put( RING2, ring2 );
		bundle.put( TIER, tier );

	}
	
	public void restoreFromBundle( Bundle bundle ) {
		
		backpack.clear();
		backpack.restoreFromBundle(bundle);

		weapon = (KindOfWeapon)bundle.get( WEAPON );
		if (weapon != null) {
			weapon.activate( owner );
		}

		armor = (Armor)bundle.get( ARMOR );
		
		ring1 = (Ring)bundle.get( RING1 );
		if (ring1 != null) {
			ring1.activate( owner );
		}
		
		ring2 = (Ring)bundle.get( RING2 );
		if (ring2 != null) {
			ring2.activate( owner );
		}

        tier = bundle.getInt(TIER);

	}
	
	@SuppressWarnings("unchecked")
	public<T extends Item> T getItem( Class<T> itemClass ) {

		for (Item item : this) {
			if (itemClass.isInstance( item )) {
				return (T)item;
			}
		}
		
		return null;
	}

    @SuppressWarnings("unchecked")
    public <T extends Bullet> T getBullet( Class<T> kind) {

        for (Item item : backpack) {
            if (item.getClass() == kind ) {
                return (T)item;
            }
        }

        return null;
    }

    @SuppressWarnings("unchecked")
    public <T extends Bandage> T getBandage( Class<T> kind) {

        for (Item item : backpack) {
            if (item.getClass() == kind ) {
                return (T)item;
            }
        }

        return null;
    }

    @SuppressWarnings("unchecked")
    public <T extends Gunpowder> T getPowder( Class<T> kind) {

        for (Item item : backpack) {
            if (item.getClass() == kind ) {
                return (T)item;
            }
        }

        return null;
    }

    @SuppressWarnings("unchecked")
    public <T extends PercussionCap> T getCap( Class<T> kind) {

        for (Item item : backpack) {
            if (item.getClass() == kind ) {
                return (T)item;
            }
        }

        return null;
    }

    public <T extends PunchCard> T getPunchCard( Class<T> kind) {

        for (Item item : backpack) {
            if (item.getClass() == kind ) {
                return (T)item;
            }
        }

        return null;
    }

	@SuppressWarnings("unchecked")
	public <T extends Key> T getKey( Class<T> kind, int depth ) {
		
		for (Item item : backpack) {
			if (item.getClass() == kind && ((Key)item).depth == depth) {
				return (T)item;
			}
		}
		
		return null;
	}

	
	public void countIronKeys() {
		
		IronKey.curDepthQuantity = 0;
		
		for (Item item : backpack) {
			if (item instanceof IronKey && ((IronKey)item).depth == Dungeon.depth) {
				IronKey.curDepthQuantity++;
			}
		}
	}
	
	public void identify() {
		for (Item item : this) {
			item.identify();
		}
	}
	
	public void observe() {
		if (weapon != null) {
			weapon.identify();
			Badges.validateItemLevelAquired( weapon );
		}
		if (armor != null) {
			armor.identify();
			Badges.validateItemLevelAquired( armor );
		}
		if (ring1 != null) {
			ring1.identify();
			Badges.validateItemLevelAquired( ring1 );
		}
		if (ring2 != null) {
			ring2.identify();
			Badges.validateItemLevelAquired( ring2 );
		}
		for (Item item : backpack) {
			item.cursedKnown = true;
		}
	}
	
	public void uncurseEquipped() {
		ScrollOfRemoveCurse.uncurse( owner, armor, weapon, ring1, ring2 );
	}
	
	public Item randomUnequipped() {
		return Random.element(backpack.items);
	}


	public void resurrect( int depth ) {
		for (Item item : backpack.items.toArray( new Item[0])) {
			if (item instanceof Key) {
				if (((Key)item).depth == depth) {
					item.detachAll( backpack );
				}
			} else if (item.unique) {
				// Keep unique items
			} else if (!item.isEquipped( owner )) {
				item.detachAll( backpack );
			}
		}

		if (weapon != null) {
			weapon.cursed = false;
			weapon.activate( owner );
		}

		if (armor != null) {
			armor.cursed = false;
		}
		
		if (ring1 != null) {
			ring1.cursed = false;
			ring1.activate( owner );
		}
		if (ring2 != null) {
			ring2.cursed = false;
			ring2.activate( owner );
		}
	}
/*
	public int charge( boolean full) {

		int count = 0;

		for (Item item : this) {
			if (item instanceof Wand) {
				Wand wand = (Wand)item;
				if (wand.curCharges < wand.maxCharges) {
					wand.curCharges = wand.curCharges + 1;
					count++;

					wand.updateQuickslot();
				}
			}
		}

		return count;
	}
	*/
public int charge(boolean full){
    int count=0;
    for (  Item item : this) {
        if (item instanceof Wand) {
            Wand wand=(Wand)item;
            if (wand.curCharges == wand.maxCharges){
                GLog.i("Already fully loaded");
            }
            if (wand.curCharges < wand.maxCharges) {
                wand.curCharges++;
                GLog.i("You reload your weapon");
                Reloader.charge(Dungeon.hero);
                count++;
                wand.updateQuickslot();
            }
        }
    }
    return count;
}

	public int discharge() {
		
		int count = 0;
		
		for (Item item : this) {
			if (item instanceof Wand) {
				Wand wand = (Wand)item;
				if (wand.curCharges > 0) {
					wand.curCharges--;
					count++;
					
					wand.updateQuickslot();
				}
			}
		}
		
		return count;
	}

	@Override
	public Iterator<Item> iterator() {
		return new ItemIterator(); 
	}
	
	private class ItemIterator implements Iterator<Item> {

		private int index = 0;
		
		private Iterator<Item> backpackIterator = backpack.iterator();
		
		private Item[] equipped = {weapon, armor, ring1, ring2};
		private int backpackIndex = equipped.length;
		
		@Override
		public boolean hasNext() {
			
			for (int i=index; i < backpackIndex; i++) {
				if (equipped[i] != null) {
					return true;
				}
			}
			
			return backpackIterator.hasNext();
		}

		@Override
		public Item next() {
			
			while (index < backpackIndex) {
				Item item = equipped[index++];
				if (item != null) {
					return item;
				}
			}
			
			return backpackIterator.next();
		}

		@Override
		public void remove() {
			switch (index) {
			case 0:
				equipped[0] = weapon = null;
				break;
			case 1:
				equipped[1] = armor = null;
				break;
			case 2:
				equipped[2] = ring1 = null;
				break;
			case 3:
				equipped[3] = ring2 = null;
				break;
			default:
				backpackIterator.remove();
			}
		}
	}
}
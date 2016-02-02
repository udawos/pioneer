
package com.udawos.pioneer.items.ammunition;

import com.udawos.pioneer.Badges;
import com.udawos.pioneer.items.FlintAndTinder;
import com.udawos.pioneer.items.Item;
import com.udawos.pioneer.items.ItemStatusHandler;
import com.udawos.pioneer.items.medicines.Bandage;
import com.udawos.pioneer.sprites.ItemSpriteSheet;
import com.udawos.utils.Bundle;

import java.util.HashSet;

public abstract class Ammo extends Item {



    protected static final float TIME_TO_READ	= 1f;

    private static final Class<?>[] ammo = {
            Bullet.class,
            Gunpowder.class,
            PercussionCap.class,
            FlintAndTinder.class,
            Bandage.class
    };
    private static final String[] runes =
            {  "Bullet", "Gunpowder", "Percussion Cap", "Flint and Tinder", "Bandage"};
    private static final Integer[] images = {

            ItemSpriteSheet.DART,
            ItemSpriteSheet.HONEYPOT,
            ItemSpriteSheet.AMULET,
            ItemSpriteSheet.WEIGHT,
            ItemSpriteSheet.BANDAGE
            };

    private static ItemStatusHandler<Ammo> handler;

    private String rune;

    {
        stackable = true;

    }

    @SuppressWarnings("unchecked")
    public static void initLabels() {
        handler = new ItemStatusHandler<Ammo>( (Class<? extends Ammo>[])ammo, runes, images );
    }

    public static void save( Bundle bundle ) {
        handler.save( bundle );
    }

    @SuppressWarnings("unchecked")
    public static void restore( Bundle bundle ) {
        handler = new ItemStatusHandler<Ammo>( (Class<? extends Ammo>[])ammo, runes, images, bundle );
    }

    public Ammo() {
        super();
        image = handler.image( this );
        rune = handler.label( this );
    }


    public boolean isKnown() {
        return handler.isKnown(this);
    }

    public void setKnown() {
        if (!isKnown()) {
            handler.know( this );
        }

        Badges.validateAllAmmoIdentified();
    }

    @Override
    public Item identify() {
        setKnown();
        return super.identify();
    }


    @Override
    public boolean isUpgradable() {
        return false;
    }

    @Override
    public boolean isIdentified() {
        return isKnown();
    }

    public static HashSet<Class<? extends Ammo>> getKnown() {
        return handler.known();
    }

    public static HashSet<Class<? extends Ammo>> getUnknown() {
        return handler.unknown();
    }

    public static boolean allKnown() {
        return handler.known().size() == ammo.length;
    }

    @Override
    public int price() {
        return 15 * quantity;
    }
}


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
 */package com.udawos.pioneer.items.medicines;

import com.udawos.noosa.audio.Sample;
import com.udawos.pioneer.Assets;
import com.udawos.pioneer.Dungeon;
import com.udawos.pioneer.actors.hero.Hero;
import com.udawos.pioneer.effects.Splash;
import com.udawos.pioneer.items.Item;
import com.udawos.pioneer.items.ItemStatusHandler;
import com.udawos.pioneer.levels.Level;
import com.udawos.pioneer.levels.Terrain;
import com.udawos.pioneer.scenes.GameScene;
import com.udawos.pioneer.sprites.ItemSprite;
import com.udawos.pioneer.sprites.ItemSpriteSheet;
import com.udawos.pioneer.utils.GLog;
import com.udawos.pioneer.windows.WndOptions;
import com.udawos.utils.Bundle;

import java.util.ArrayList;
import java.util.HashSet;

public class Medicines extends Item {

    public static final String AC_DRINK	= "DRINK";

    private static final String TXT_HARMFUL		= "Harmful potion!";
    private static final String TXT_BENEFICIAL	= "Beneficial potion";
    private static final String TXT_YES			= "Yes, I know what I'm doing";
    private static final String TXT_NO			= "No, I changed my mind";
    private static final String TXT_R_U_SURE_DRINK =
            "Are you sure you want to drink it? In most cases you should throw such potions at your enemies.";
    private static final String TXT_R_U_SURE_THROW =
            "Are you sure you want to throw it? In most cases it makes sense to drink it.";

    private static final float TIME_TO_DRINK = 1f;

    private static final Class<?>[] medicines = {
            BleedStop.class,
            //BoneFix.class,
            DiseaseAway.class,
            WoundCleen.class,
    };
    private static final String[] colors = {
            "turquoise", "crimson", "azure", "jade", "golden", "magenta",
            "charcoal", "ivory", "amber", "bistre", "indigo", "silver"};
    private static final Integer[] images = {
            ItemSpriteSheet.POTION_TURQUOISE,
            ItemSpriteSheet.POTION_CRIMSON,
            ItemSpriteSheet.POTION_AZURE,
            ItemSpriteSheet.POTION_JADE,
            ItemSpriteSheet.POTION_GOLDEN,
            ItemSpriteSheet.POTION_MAGENTA,
            ItemSpriteSheet.POTION_CHARCOAL,
            ItemSpriteSheet.POTION_IVORY,
            ItemSpriteSheet.POTION_AMBER,
            ItemSpriteSheet.POTION_BISTRE,
            ItemSpriteSheet.POTION_INDIGO,
            ItemSpriteSheet.POTION_SILVER};

    private static ItemStatusHandler<Medicines> handler;

    private String color;

    {
        stackable = true;
        defaultAction = AC_DRINK;
    }

    @SuppressWarnings("unchecked")
    public static void initColors() {
        handler = new ItemStatusHandler<Medicines>( (Class<? extends Medicines>[])medicines, colors, images );
    }

    public static void save( Bundle bundle ) {
        handler.save( bundle );
    }

    @SuppressWarnings("unchecked")
    public static void restore( Bundle bundle ) {
        handler = new ItemStatusHandler<Medicines>( (Class<? extends Medicines>[])medicines, colors, images, bundle );
    }

    public Medicines() {
        super();
        image = handler.image( this );
        color = handler.label( this );
    }

    @Override
    public ArrayList<String> actions( Hero hero ) {
        ArrayList<String> actions = super.actions( hero );
        actions.add( AC_DRINK );
        return actions;
    }

    @Override
    public void execute( final Hero hero, String action ) {
        if (action.equals( AC_DRINK )) {

            if (isKnown() && (
                    this instanceof WoundCleen)) {

                GameScene.show(
                        new WndOptions( TXT_HARMFUL, TXT_R_U_SURE_DRINK, TXT_YES, TXT_NO ) {
                            @Override
                            protected void onSelect(int index) {
                                if (index == 0) {
                                    drink( hero );
                                }
                            };
                        }
                );

            } else {
                drink( hero );
            }

        } else {

            super.execute( hero, action );

        }
    }

    @Override
    public void doThrow( final Hero hero ) {

        if (isKnown() && (
                this instanceof BleedStop ||
                        this instanceof DiseaseAway)) {

            GameScene.show(
                    new WndOptions( TXT_BENEFICIAL, TXT_R_U_SURE_THROW, TXT_YES, TXT_NO ) {
                        @Override
                        protected void onSelect(int index) {
                            if (index == 0) {
                                Medicines.super.doThrow( hero );
                            }
                        };
                    }
            );

        } else {
            super.doThrow(hero);
        }
    }


    protected void drink( Hero hero ) {

        detach( hero.belongings.backpack );

        hero.spend( TIME_TO_DRINK );
        hero.busy();
        onThrow( hero.pos );

        Sample.INSTANCE.play( Assets.SND_DRINK );

        hero.sprite.operate( hero.pos );
    }

    @Override
    protected void onThrow( int cell ) {
        if (Dungeon.hero.pos == cell) {

            apply( Dungeon.hero );

        } else if (Dungeon.level.map[cell] == Terrain.WELL || Level.pit[cell]) {

            super.onThrow( cell );

        } else  {

            shatter( cell );

        }
    }

    protected void apply( Hero hero ) {
        shatter( hero.pos );
    }

    public void shatter( int cell ) {
        if (Dungeon.visible[cell]) {
            GLog.i( "The flask shatters and " + color() + " liquid splashes harmlessly" );
            Sample.INSTANCE.play( Assets.SND_SHATTER );
            splash( cell );
        }
    }

    public boolean isKnown() {
        return handler.isKnown( this );
    }

    public void setKnown() {
        if (!isKnown()) {
            handler.know( this );
        }

        //Badges.validateAllMedicinesIdentified();
    }

    @Override
    public Item identify() {
        setKnown();
        return this;
    }

    protected String color() {
        return color;
    }

    @Override
    public String name() {
        return isKnown() ? name : color + " potion";
    }

    @Override
    public String info() {
        return isKnown() ?
                desc() :
                "This flask contains a swirling " + color + " liquid. " +
                        "Who knows what it will do when drunk or thrown?";
    }

    @Override
    public boolean isIdentified() {
        return isKnown();
    }

    @Override
    public boolean isUpgradable() {
        return false;
    }

    public static HashSet<Class<? extends Medicines>> getKnown() {
        return handler.known();
    }

    public static HashSet<Class<? extends Medicines>> getUnknown() {
        return handler.unknown();
    }

    public static boolean allKnown() {
        return handler.known().size() == medicines.length;
    }

    protected void splash( int cell ) {
        final int color = ItemSprite.pick( image, 8, 10 );
        Splash.at( cell, color, 5 );
    }

    @Override
    public int price() {
        return 20 * quantity;
    }
}

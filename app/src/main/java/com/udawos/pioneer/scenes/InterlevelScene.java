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
package com.udawos.pioneer.scenes;

import com.udawos.noosa.BitmapText;
import com.udawos.noosa.Camera;
import com.udawos.noosa.Game;
import com.udawos.noosa.audio.Music;
import com.udawos.noosa.audio.Sample;
import com.udawos.pioneer.Assets;
import com.udawos.pioneer.Badges;
import com.udawos.pioneer.Dungeon;
import com.udawos.pioneer.Statistics;
import com.udawos.pioneer.actors.Actor;
import com.udawos.pioneer.items.Generator;
import com.udawos.pioneer.levels.Level;
import com.udawos.pioneer.windows.WndError;
import com.udawos.pioneer.windows.WndStory;

import java.io.FileNotFoundException;

public class InterlevelScene extends PixelScene {

    private static final float TIME_TO_FADE = 0.5f;


    private static final String TXT_LOADING		= "Loading...";
    private static final String TXT_RESURRECTING= "Resurrecting...";
    private static final String TXT_RETURNING	= "Returning...";
    private static final String TXT_FALLING		= "Falling...";
    private static final String TXT_NORTH       = "Northward...";
    private static final String TXT_SOUTH       = "Southward...";
    private static final String TXT_EAST        = "Eastward...";
    private static final String TXT_WEST        = "Westward...";

    private static final String ERR_FILE_NOT_FOUND	= "File not found. For some reason.";
    private static final String ERR_GENERIC			= "Something went wrong..."	;


    public enum Mode {
        NORTH, NORTH1, NORTH2, NORTH3, NORTH4, NORTH5, NORTH6, NORTH7, NORTH8, NORTH9, NORTH10,
        NORTH11, NORTH12, NORTH13, NORTH14, NORTH15, NORTH16, NORTH17, NORTH18, NORTH19, NORTH20,
        NORTH21, NORTH22, NORTH23, NORTH24, NORTH25, NORTH26, NORTH27, NORTH28, NORTH29, NORTH30,
        NORTH31, NORTH32, NORTH33, NORTH34, NORTH35, NORTH36, NORTH37, NORTH38, NORTH39, NORTH40,
        NORTH41, NORTH42, NORTH43, NORTH44, NORTH45, NORTH46, NORTH47, NORTH48, NORTH49, NORTH50,
        NORTH51, NORTH52, NORTH53, NORTH54, NORTH55, NORTH56, NORTH57, NORTH58, NORTH59, NORTH60,
        NORTH61, NORTH62, NORTH63, NORTH64, NORTH65, NORTH66, NORTH67, NORTH68, NORTH69, NORTH70,
        NORTH71, NORTH72, NORTH73, NORTH74, NORTH75, NORTH76, NORTH77, NORTH78, NORTH79, NORTH80,
        NORTH81, NORTH82, NORTH83, NORTH84, NORTH85, NORTH86, NORTH87, NORTH88, NORTH89, NORTH90,
        NORTH91, NORTH92, NORTH93, NORTH94, NORTH95, NORTH96, NORTH97, NORTH98, NORTH99,
        SOUTH, SOUTH1, SOUTH2, SOUTH3, SOUTH4, SOUTH5, SOUTH6, SOUTH7, SOUTH8, SOUTH9, SOUTH10,
        SOUTH11, SOUTH12, SOUTH13, SOUTH14, SOUTH15, SOUTH16, SOUTH17, SOUTH18, SOUTH19, SOUTH20,
        SOUTH21, SOUTH22, SOUTH23, SOUTH24, SOUTH25, SOUTH26, SOUTH27, SOUTH28, SOUTH29, SOUTH30,
        SOUTH31, SOUTH32, SOUTH33, SOUTH34, SOUTH35, SOUTH36, SOUTH37, SOUTH38, SOUTH39, SOUTH40,
        SOUTH41, SOUTH42, SOUTH43, SOUTH44, SOUTH45, SOUTH46, SOUTH47, SOUTH48, SOUTH49, SOUTH50,
        SOUTH51, SOUTH52, SOUTH53, SOUTH54, SOUTH55, SOUTH56, SOUTH57, SOUTH58, SOUTH59, SOUTH60,
        SOUTH61, SOUTH62, SOUTH63, SOUTH64, SOUTH65, SOUTH66, SOUTH67, SOUTH68, SOUTH69, SOUTH70,
        SOUTH71, SOUTH72, SOUTH73, SOUTH74, SOUTH75, SOUTH76, SOUTH77, SOUTH78, SOUTH79, SOUTH80,
        SOUTH81, SOUTH82, SOUTH83, SOUTH84, SOUTH85, SOUTH86, SOUTH87, SOUTH88, SOUTH89, SOUTH90,
        SOUTH91, SOUTH92, SOUTH93, SOUTH94, SOUTH95, SOUTH96, SOUTH97, SOUTH98, SOUTH99,
        EAST, EAST1, EAST2, EAST3, EAST4, EAST5, EAST6, EAST7, EAST8, EAST9, EAST10,
        EAST11, EAST12, EAST13, EAST14, EAST15, EAST16, EAST17, EAST18, EAST19, EAST20,
        EAST21, EAST22, EAST23, EAST24, EAST25, EAST26, EAST27, EAST28, EAST29, EAST30,
        EAST31, EAST32, EAST33, EAST34, EAST35, EAST36, EAST37, EAST38, EAST39, EAST40,
        EAST41, EAST42, EAST43, EAST44, EAST45, EAST46, EAST47, EAST48, EAST49, EAST50,
        EAST51, EAST52, EAST53, EAST54, EAST55, EAST56, EAST57, EAST58, EAST59, EAST60,
        EAST61, EAST62, EAST63, EAST64, EAST65, EAST66, EAST67, EAST68, EAST69, EAST70,
        EAST71, EAST72, EAST73, EAST74, EAST75, EAST76, EAST77, EAST78, EAST79, EAST80,
        EAST81, EAST82, EAST83, EAST84, EAST85, EAST86, EAST87, EAST88, EAST89, EAST90,
        EAST91, EAST92, EAST93, EAST94, EAST95, EAST96, EAST97, EAST98, EAST99, EAST100,
        WEST, WEST1, WEST2, WEST3, WEST4, WEST5, WEST6, WEST7, WEST8, WEST9, WEST10,
        WEST11, WEST12, WEST13, WEST14, WEST15, WEST16, WEST17, WEST18, WEST19, WEST20,
        WEST21, WEST22, WEST23, WEST24, WEST25, WEST26, WEST27, WEST28, WEST29, WEST30,
        WEST31, WEST32, WEST33, WEST34, WEST35, WEST36, WEST37, WEST38, WEST39, WEST40,
        WEST41, WEST42, WEST43, WEST44, WEST45, WEST46, WEST47, WEST48, WEST49, WEST50,
        WEST51, WEST52, WEST53, WEST54, WEST55, WEST56, WEST57, WEST58, WEST59, WEST60,
        WEST61, WEST62, WEST63, WEST64, WEST65, WEST66, WEST67, WEST68, WEST69, WEST70,
        WEST71, WEST72, WEST73, WEST74, WEST75, WEST76, WEST77, WEST78, WEST79, WEST80,
        WEST81, WEST82, WEST83, WEST84, WEST85, WEST86, WEST87, WEST88, WEST89, WEST90,
        WEST91, WEST92, WEST93, WEST94, WEST95, WEST96, WEST97, WEST98, WEST99,
        DOWN1, DOWN2, DOWN3,DOWN4, DOWN5,DOWN6, DOWN7, DOWN8, DOWN9,DOWN10, DOWN11, UP1, UP2, UP3,UP4, UP5, UP6, UP7, UP10,UP11,
        CONTINUE, RESURRECT, RETURN, FALL, CLIMB
    }

    public static Mode mode;

    public static int returnDepth;
    public static int returnPos;

    public static boolean noStory = false;

    //public static boolean fallIntoPit;

    private enum Phase {
        FADE_IN, STATIC, FADE_OUT
    }

    private Phase phase;
    private float timeLeft;

    private BitmapText message;

    private Thread thread;
    private String error = null;

    @Override
    public void create() {
        super.create();

        String text = "";
        switch (mode) {
            case NORTH1:
                text = TXT_NORTH;
                break;
            case SOUTH:
                text = TXT_SOUTH;
                break;
            case EAST1:
                text = TXT_EAST;
                break;
            case WEST1:
                text = TXT_WEST;
                break;
            case CONTINUE:
                text = TXT_LOADING;
                break;
            case RESURRECT:
                text = TXT_RESURRECTING;
                break;
            case RETURN:
                text = TXT_RETURNING;
                break;
            case FALL:
                text = TXT_FALLING;
                break;
        }

        message = PixelScene.createText( text, 9 );
        message.measure();
        message.x = (Camera.main.width - message.width()) / 2;
        message.y = (Camera.main.height - message.height()) / 2;
        add( message );

        phase = Phase.FADE_IN;
        timeLeft = TIME_TO_FADE;

        thread = new Thread() {
            @Override
            public void run() {

                try {

                    Generator.reset();

                    Level level;

                    switch (mode) {
                        case NORTH:
                            north();
                            Level.DirectionValue = 11;
                            Dungeon.saveAll();
                            if (Statistics.Level11Visited) {
                                Dungeon.depth = Level.DirectionValue;
                                level = Dungeon.loadLevel(Dungeon.hero.heroClass);
                            } else {
                                level = Dungeon.newLevel();
                                Badges.validateChampion();
                            }
                            Dungeon.switchLevel(level, level.south);
                            break;
                        case NORTH1:
                            north();
                            Level.DirectionValue = 11;
                            Dungeon.saveAll();
                            if (Statistics.Level11Visited) {
                                Dungeon.depth = Level.DirectionValue;
                                level = Dungeon.loadLevel(Dungeon.hero.heroClass);
                            } else {
                                level = Dungeon.newLevel();
                                Badges.validateDeathFromFire();
                            }
                            Dungeon.switchLevel(level, level.south);
                            break;
                        case NORTH2:
                            north();
                            Level.DirectionValue = 12;
                            Dungeon.saveAll();
                            if (Statistics.Level12Visited) {
                                Dungeon.depth = Level.DirectionValue;
                                level = Dungeon.loadLevel(Dungeon.hero.heroClass);
                            } else {
                                level = Dungeon.newLevel();
                                Badges.validateDeathFromFire();
                            }
                            Dungeon.switchLevel(level, level.south);
                            break;
                        case NORTH3:
                            north();
                            Level.DirectionValue = 13;
                            Dungeon.saveAll();
                            if (Statistics.Level13Visited) {
                                Dungeon.depth = Level.DirectionValue;
                                level = Dungeon.loadLevel(Dungeon.hero.heroClass);
                            } else {
                                level = Dungeon.newLevel();
                                Badges.validateDeathFromFire();
                            }
                            Dungeon.switchLevel(level, level.south);
                            break;
                        case NORTH4:
                            north();
                            Level.DirectionValue = 14;
                            Dungeon.saveAll();
                            if (Statistics.Level14Visited) {
                                Dungeon.depth = Level.DirectionValue;
                                level = Dungeon.loadLevel(Dungeon.hero.heroClass);
                            } else {
                                level = Dungeon.newLevel();
                                Badges.validateDeathFromFire();
                            }
                            Dungeon.switchLevel(level, level.south);
                            break;
                        case NORTH5:
                            north();
                            Level.DirectionValue = 15;
                            Dungeon.saveAll();
                            if (Statistics.Level15Visited) {
                                Dungeon.depth = Level.DirectionValue;
                                level = Dungeon.loadLevel(Dungeon.hero.heroClass);
                            } else {
                                level = Dungeon.newLevel();
                                Badges.validateDeathFromFire();
                            }
                            Dungeon.switchLevel(level, level.south);
                            break;
                        case NORTH6:
                            north();
                            Level.DirectionValue = 16;
                            Dungeon.saveAll();
                            if (Statistics.Level16Visited) {
                                Dungeon.depth = Level.DirectionValue;
                                level = Dungeon.loadLevel(Dungeon.hero.heroClass);
                            } else {
                                level = Dungeon.newLevel();
                                Badges.validateDeathFromFire();
                            }
                            Dungeon.switchLevel(level, level.south);
                            break;
                        case NORTH7:
                            north();
                            Level.DirectionValue = 17;
                            Dungeon.saveAll();
                            if (Statistics.Level17Visited) {
                                Dungeon.depth = Level.DirectionValue;
                                level = Dungeon.loadLevel(Dungeon.hero.heroClass);
                            } else {
                                level = Dungeon.newLevel();
                                Badges.validateDeathFromFire();
                            }
                            Dungeon.switchLevel(level, level.south);
                            break;
                        case NORTH8:
                            north();
                            Level.DirectionValue = 18;
                            Dungeon.saveAll();
                            if (Statistics.Level18Visited) {
                                Dungeon.depth = Level.DirectionValue;
                                level = Dungeon.loadLevel(Dungeon.hero.heroClass);
                            } else {
                                level = Dungeon.newLevel();
                                Badges.validateDeathFromFire();
                            }
                            Dungeon.switchLevel(level, level.south);
                            break;
                        case NORTH9:
                            north();
                            Level.DirectionValue = 19;
                            Dungeon.saveAll();
                            if (Statistics.Level19Visited) {
                                Dungeon.depth = Level.DirectionValue;
                                level = Dungeon.loadLevel(Dungeon.hero.heroClass);
                            } else {
                                level = Dungeon.newLevel();
                                Badges.validateDeathFromFire();
                            }
                            Dungeon.switchLevel(level, level.south);
                            break;
                        case NORTH10:
                            north();
                            Level.DirectionValue = 20;
                            Dungeon.saveAll();
                            if (Statistics.Level20Visited) {
                                Dungeon.depth = Level.DirectionValue;
                                level = Dungeon.loadLevel(Dungeon.hero.heroClass);
                            } else {
                                level = Dungeon.newLevel();
                                Badges.validateDeathFromFire();
                            }
                            Dungeon.switchLevel(level, level.south);
                            break;
                        case NORTH11:
                            north();
                            Level.DirectionValue = 21;
                            Dungeon.saveAll();
                            if (Statistics.Level21Visited) {
                                Dungeon.depth = Level.DirectionValue;
                                level = Dungeon.loadLevel(Dungeon.hero.heroClass);
                            } else {
                                level = Dungeon.newLevel();
                                Badges.validateDeathFromFire();
                            }
                            Dungeon.switchLevel(level, level.south);
                            break;
                        case NORTH12:
                            north();
                            Level.DirectionValue = 22;
                            Dungeon.saveAll();
                            if (Statistics.Level22Visited) {
                                Dungeon.depth = Level.DirectionValue;
                                level = Dungeon.loadLevel(Dungeon.hero.heroClass);
                            } else {
                                level = Dungeon.newLevel();
                                Badges.validateDeathFromFire();
                            }
                            Dungeon.switchLevel(level, level.south);
                            break;
                        case NORTH13:
                            north();
                            Level.DirectionValue = 23;
                            Dungeon.saveAll();
                            if (Statistics.Level23Visited) {
                                Dungeon.depth = Level.DirectionValue;
                                level = Dungeon.loadLevel(Dungeon.hero.heroClass);
                            } else {
                                level = Dungeon.newLevel();
                                Badges.validateDeathFromFire();
                            }
                            Dungeon.switchLevel(level, level.south);
                            break;
                        case NORTH14:
                            north();
                            Level.DirectionValue = 24;
                            Dungeon.saveAll();
                            if (Statistics.Level24Visited) {
                                Dungeon.depth = Level.DirectionValue;
                                level = Dungeon.loadLevel(Dungeon.hero.heroClass);
                            } else {
                                level = Dungeon.newLevel();
                                Badges.validateDeathFromFire();
                            }
                            Dungeon.switchLevel(level, level.south);
                            break;
                        case NORTH15:
                            north();
                            Level.DirectionValue = 25;
                            Dungeon.saveAll();
                            if (Statistics.Level25Visited) {
                                Dungeon.depth = Level.DirectionValue;
                                level = Dungeon.loadLevel(Dungeon.hero.heroClass);
                            } else {
                                level = Dungeon.newLevel();
                                Badges.validateDeathFromFire();
                            }
                            Dungeon.switchLevel(level, level.south);
                            break;
                        case NORTH16:
                            north();
                            Level.DirectionValue = 26;
                            Dungeon.saveAll();
                            if (Statistics.Level26Visited) {
                                Dungeon.depth = Level.DirectionValue;
                                level = Dungeon.loadLevel(Dungeon.hero.heroClass);
                            } else {
                                level = Dungeon.newLevel();
                                Badges.validateDeathFromFire();
                            }
                            Dungeon.switchLevel(level, level.south);
                            break;
                        case NORTH17:
                            north();
                            Level.DirectionValue = 27;
                            Dungeon.saveAll();
                            if (Statistics.Level27Visited) {
                                Dungeon.depth = Level.DirectionValue;
                                level = Dungeon.loadLevel(Dungeon.hero.heroClass);
                            } else {
                                level = Dungeon.newLevel();
                                Badges.validateDeathFromFire();
                            }
                            Dungeon.switchLevel(level, level.south);
                            break;
                        case NORTH18:
                            north();
                            Level.DirectionValue = 28;
                            Dungeon.saveAll();
                            if (Statistics.Level28Visited) {
                                Dungeon.depth = Level.DirectionValue;
                                level = Dungeon.loadLevel(Dungeon.hero.heroClass);
                            } else {
                                level = Dungeon.newLevel();
                                Badges.validateDeathFromFire();
                            }
                            Dungeon.switchLevel(level, level.south);
                            break;
                        case NORTH19:
                            north();
                            Level.DirectionValue = 29;
                            Dungeon.saveAll();
                            if (Statistics.Level29Visited) {
                                Dungeon.depth = Level.DirectionValue;
                                level = Dungeon.loadLevel(Dungeon.hero.heroClass);
                            } else {
                                level = Dungeon.newLevel();
                                Badges.validateDeathFromFire();
                            }
                            Dungeon.switchLevel(level, level.south);
                            break;
                        case NORTH20:
                            north();
                            Level.DirectionValue = 30;
                            Dungeon.saveAll();
                            if (Statistics.Level30Visited) {
                                Dungeon.depth = Level.DirectionValue;
                                level = Dungeon.loadLevel(Dungeon.hero.heroClass);
                            } else {
                                level = Dungeon.newLevel();
                                Badges.validateDeathFromFire();
                            }
                            Dungeon.switchLevel(level, level.south);
                            break;
                        case NORTH21:
                            north();
                            Level.DirectionValue = 31;
                            Dungeon.saveAll();
                            if (Statistics.Level31Visited) {
                                Dungeon.depth = Level.DirectionValue;
                                level = Dungeon.loadLevel(Dungeon.hero.heroClass);
                            } else {
                                level = Dungeon.newLevel();
                                Badges.validateDeathFromFire();
                            }
                            Dungeon.switchLevel(level, level.south);
                            break;
                        case NORTH22:
                            north();
                            Level.DirectionValue = 32;
                            Dungeon.saveAll();
                            if (Statistics.Level32Visited) {
                                Dungeon.depth = Level.DirectionValue;
                                level = Dungeon.loadLevel(Dungeon.hero.heroClass);
                            } else {
                                level = Dungeon.newLevel();
                                Badges.validateDeathFromFire();
                            }
                            Dungeon.switchLevel(level, level.south);
                            break;
                        case NORTH23:
                            north();
                            Level.DirectionValue = 33;
                            Dungeon.saveAll();
                            if (Statistics.Level33Visited) {
                                Dungeon.depth = Level.DirectionValue;
                                level = Dungeon.loadLevel(Dungeon.hero.heroClass);
                            } else {
                                level = Dungeon.newLevel();
                                Badges.validateDeathFromFire();
                            }
                            Dungeon.switchLevel(level, level.south);
                            break;
                        case NORTH24:
                            north();
                            Level.DirectionValue = 34;
                            Dungeon.saveAll();
                            if (Statistics.Level34Visited) {
                                Dungeon.depth = Level.DirectionValue;
                                level = Dungeon.loadLevel(Dungeon.hero.heroClass);
                            } else {
                                level = Dungeon.newLevel();
                                Badges.validateDeathFromFire();
                            }
                            Dungeon.switchLevel(level, level.south);
                            break;
                        case NORTH25:
                            north();
                            Level.DirectionValue = 35;
                            Dungeon.saveAll();
                            if (Statistics.Level35Visited) {
                                Dungeon.depth = Level.DirectionValue;
                                level = Dungeon.loadLevel(Dungeon.hero.heroClass);
                            } else {
                                level = Dungeon.newLevel();
                                Badges.validateDeathFromFire();
                            }
                            Dungeon.switchLevel(level, level.south);
                            break;
                        case NORTH26:
                            north();
                            Level.DirectionValue = 36;
                            Dungeon.saveAll();
                            if (Statistics.Level36Visited) {
                                Dungeon.depth = Level.DirectionValue;
                                level = Dungeon.loadLevel(Dungeon.hero.heroClass);
                            } else {
                                level = Dungeon.newLevel();
                                Badges.validateDeathFromFire();
                            }
                            Dungeon.switchLevel(level, level.south);
                            break;
                        case NORTH27:
                            north();
                            Level.DirectionValue = 37;
                            Dungeon.saveAll();
                            if (Statistics.Level37Visited) {
                                Dungeon.depth = Level.DirectionValue;
                                level = Dungeon.loadLevel(Dungeon.hero.heroClass);
                            } else {
                                level = Dungeon.newLevel();
                                Badges.validateDeathFromFire();
                            }
                            Dungeon.switchLevel(level, level.south);
                            break;
                        case NORTH28:
                            north();
                            Level.DirectionValue = 38;
                            Dungeon.saveAll();
                            if (Statistics.Level38Visited) {
                                Dungeon.depth = Level.DirectionValue;
                                level = Dungeon.loadLevel(Dungeon.hero.heroClass);
                            } else {
                                level = Dungeon.newLevel();
                                Badges.validateDeathFromFire();
                            }
                            Dungeon.switchLevel(level, level.south);
                            break;
                        case NORTH29:
                            north();
                            Level.DirectionValue = 39;
                            Dungeon.saveAll();
                            if (Statistics.Level39Visited) {
                                Dungeon.depth = Level.DirectionValue;
                                level = Dungeon.loadLevel(Dungeon.hero.heroClass);
                            } else {
                                level = Dungeon.newLevel();
                                Badges.validateDeathFromFire();
                            }
                            Dungeon.switchLevel(level, level.south);
                            break;
                        case NORTH30:
                            north();
                            Level.DirectionValue = 40;
                            Dungeon.saveAll();
                            if (Statistics.Level40Visited) {
                                Dungeon.depth = Level.DirectionValue;
                                level = Dungeon.loadLevel(Dungeon.hero.heroClass);
                            } else {
                                level = Dungeon.newLevel();
                                Badges.validateDeathFromFire();
                            }
                            Dungeon.switchLevel(level, level.south);
                            break;
                        case NORTH31:
                            north();
                            Level.DirectionValue = 41;
                            Dungeon.saveAll();
                            if (Statistics.Level41Visited) {
                                Dungeon.depth = Level.DirectionValue;
                                level = Dungeon.loadLevel(Dungeon.hero.heroClass);
                            } else {
                                level = Dungeon.newLevel();
                                Badges.validateDeathFromFire();
                            }
                            Dungeon.switchLevel(level, level.south);
                            break;
                        case NORTH32:
                            north();
                            Level.DirectionValue = 42;
                            Dungeon.saveAll();
                            if (Statistics.Level42Visited) {
                                Dungeon.depth = Level.DirectionValue;
                                level = Dungeon.loadLevel(Dungeon.hero.heroClass);
                            } else {
                                level = Dungeon.newLevel();
                                Badges.validateDeathFromFire();
                            }
                            Dungeon.switchLevel(level, level.south);
                            break;
                        case NORTH33:
                            north();
                            Level.DirectionValue = 43;
                            Dungeon.saveAll();
                            if (Statistics.Level43Visited) {
                                Dungeon.depth = Level.DirectionValue;
                                level = Dungeon.loadLevel(Dungeon.hero.heroClass);
                            } else {
                                level = Dungeon.newLevel();
                                Badges.validateDeathFromFire();
                            }
                            Dungeon.switchLevel(level, level.south);
                            break;
                        case NORTH34:
                            north();
                            Level.DirectionValue = 44;
                            Dungeon.saveAll();
                            if (Statistics.Level44Visited) {
                                Dungeon.depth = Level.DirectionValue;
                                level = Dungeon.loadLevel(Dungeon.hero.heroClass);
                            } else {
                                level = Dungeon.newLevel();
                                Badges.validateDeathFromFire();
                            }
                            Dungeon.switchLevel(level, level.south);
                            break;
                        case NORTH35:
                            north();
                            Level.DirectionValue = 45;
                            Dungeon.saveAll();
                            if (Statistics.Level45Visited) {
                                Dungeon.depth = Level.DirectionValue;
                                level = Dungeon.loadLevel(Dungeon.hero.heroClass);
                            } else {
                                level = Dungeon.newLevel();
                                Badges.validateDeathFromFire();
                            }
                            Dungeon.switchLevel(level, level.south);
                            break;
                        case NORTH36:
                            north();
                            Level.DirectionValue = 46;
                            Dungeon.saveAll();
                            if (Statistics.Level46Visited) {
                                Dungeon.depth = Level.DirectionValue;
                                level = Dungeon.loadLevel(Dungeon.hero.heroClass);
                            } else {
                                level = Dungeon.newLevel();
                                Badges.validateDeathFromFire();
                            }
                            Dungeon.switchLevel(level, level.south);
                            break;
                        case NORTH37:
                            north();
                            Level.DirectionValue = 47;
                            Dungeon.saveAll();
                            if (Statistics.Level47Visited) {
                                Dungeon.depth = Level.DirectionValue;
                                level = Dungeon.loadLevel(Dungeon.hero.heroClass);
                            } else {
                                level = Dungeon.newLevel();
                                Badges.validateDeathFromFire();
                            }
                            Dungeon.switchLevel(level, level.south);
                            break;
                        case NORTH38:
                            north();
                            Level.DirectionValue = 48;
                            Dungeon.saveAll();
                            if (Statistics.Level48Visited) {
                                Dungeon.depth = Level.DirectionValue;
                                level = Dungeon.loadLevel(Dungeon.hero.heroClass);
                            } else {
                                level = Dungeon.newLevel();
                                Badges.validateDeathFromFire();
                            }
                            Dungeon.switchLevel(level, level.south);
                            break;
                        case NORTH39:
                            north();
                            Level.DirectionValue = 49;
                            Dungeon.saveAll();
                            if (Statistics.Level49Visited) {
                                Dungeon.depth = Level.DirectionValue;
                                level = Dungeon.loadLevel(Dungeon.hero.heroClass);
                            } else {
                                level = Dungeon.newLevel();
                                Badges.validateDeathFromFire();
                            }
                            Dungeon.switchLevel(level, level.south);
                            break;
                        case NORTH40:
                            north();
                            Level.DirectionValue = 50;
                            Dungeon.saveAll();
                            if (Statistics.Level50Visited) {
                                Dungeon.depth = Level.DirectionValue;
                                level = Dungeon.loadLevel(Dungeon.hero.heroClass);
                            } else {
                                level = Dungeon.newLevel();
                                Badges.validateDeathFromFire();
                            }
                            Dungeon.switchLevel(level, level.south);
                            break;
                        case NORTH41:
                            north();
                            Level.DirectionValue = 51;
                            Dungeon.saveAll();
                            if (Statistics.Level51Visited) {
                                Dungeon.depth = Level.DirectionValue;
                                level = Dungeon.loadLevel(Dungeon.hero.heroClass);
                            } else {
                                level = Dungeon.newLevel();
                                Badges.validateDeathFromFire();
                            }
                            Dungeon.switchLevel(level, level.south);
                            break;
                        case NORTH42:
                            north();
                            Level.DirectionValue = 52;
                            Dungeon.saveAll();
                            if (Statistics.Level52Visited) {
                                Dungeon.depth = Level.DirectionValue;
                                level = Dungeon.loadLevel(Dungeon.hero.heroClass);
                            } else {
                                level = Dungeon.newLevel();
                                Badges.validateDeathFromFire();
                            }
                            Dungeon.switchLevel(level, level.south);
                            break;
                        case NORTH43:
                            north();
                            Level.DirectionValue = 53;
                            Dungeon.saveAll();
                            if (Statistics.Level53Visited) {
                                Dungeon.depth = Level.DirectionValue;
                                level = Dungeon.loadLevel(Dungeon.hero.heroClass);
                            } else {
                                level = Dungeon.newLevel();
                                Badges.validateDeathFromFire();
                            }
                            Dungeon.switchLevel(level, level.south);
                            break;
                        case NORTH44:
                            north();
                            Level.DirectionValue = 54;
                            Dungeon.saveAll();
                            if (Statistics.Level54Visited) {
                                Dungeon.depth = Level.DirectionValue;
                                level = Dungeon.loadLevel(Dungeon.hero.heroClass);
                            } else {
                                level = Dungeon.newLevel();
                                Badges.validateDeathFromFire();
                            }
                            Dungeon.switchLevel(level, level.south);
                            break;
                        case NORTH45:
                            north();
                            Level.DirectionValue = 55;
                            Dungeon.saveAll();
                            if (Statistics.Level55Visited) {
                                Dungeon.depth = Level.DirectionValue;
                                level = Dungeon.loadLevel(Dungeon.hero.heroClass);
                            } else {
                                level = Dungeon.newLevel();
                                Badges.validateDeathFromFire();
                            }
                            Dungeon.switchLevel(level, level.south);
                            break;
                        case NORTH46:
                            north();
                            Level.DirectionValue = 56;
                            Dungeon.saveAll();
                            if (Statistics.Level56Visited) {
                                Dungeon.depth = Level.DirectionValue;
                                level = Dungeon.loadLevel(Dungeon.hero.heroClass);
                            } else {
                                level = Dungeon.newLevel();
                                Badges.validateDeathFromFire();
                            }
                            Dungeon.switchLevel(level, level.south);
                            break;
                        case NORTH47:
                            north();
                            Level.DirectionValue = 57;
                            Dungeon.saveAll();
                            if (Statistics.Level57Visited) {
                                Dungeon.depth = Level.DirectionValue;
                                level = Dungeon.loadLevel(Dungeon.hero.heroClass);
                            } else {
                                level = Dungeon.newLevel();
                                Badges.validateDeathFromFire();
                            }
                            Dungeon.switchLevel(level, level.south);
                            break;
                        case NORTH48:
                            north();
                            Level.DirectionValue = 58;
                            Dungeon.saveAll();
                            if (Statistics.Level58Visited) {
                                Dungeon.depth = Level.DirectionValue;
                                level = Dungeon.loadLevel(Dungeon.hero.heroClass);
                            } else {
                                level = Dungeon.newLevel();
                                Badges.validateDeathFromFire();
                            }
                            Dungeon.switchLevel(level, level.south);
                            break;
                        case NORTH49:
                            north();
                            Level.DirectionValue = 59;
                            Dungeon.saveAll();
                            if (Statistics.Level59Visited) {
                                Dungeon.depth = Level.DirectionValue;
                                level = Dungeon.loadLevel(Dungeon.hero.heroClass);
                            } else {
                                level = Dungeon.newLevel();
                                Badges.validateDeathFromFire();
                            }
                            Dungeon.switchLevel(level, level.south);
                            break;
                        case NORTH50:
                            north();
                            Level.DirectionValue = 60;
                            Dungeon.saveAll();
                            if (Statistics.Level60Visited) {
                                Dungeon.depth = Level.DirectionValue;
                                level = Dungeon.loadLevel(Dungeon.hero.heroClass);
                            } else {
                                level = Dungeon.newLevel();
                                Badges.validateDeathFromFire();
                            }
                            Dungeon.switchLevel(level, level.south);
                            break;
                        case NORTH51:
                            north();
                            Level.DirectionValue = 61;
                            Dungeon.saveAll();
                            if (Statistics.Level61Visited) {
                                Dungeon.depth = Level.DirectionValue;
                                level = Dungeon.loadLevel(Dungeon.hero.heroClass);
                            } else {
                                level = Dungeon.newLevel();
                                Badges.validateDeathFromFire();
                            }
                            Dungeon.switchLevel(level, level.south);
                            break;
                        case NORTH52:
                            north();
                            Level.DirectionValue = 62;
                            Dungeon.saveAll();
                            if (Statistics.Level62Visited) {
                                Dungeon.depth = Level.DirectionValue;
                                level = Dungeon.loadLevel(Dungeon.hero.heroClass);
                            } else {
                                level = Dungeon.newLevel();
                                Badges.validateDeathFromFire();
                            }
                            Dungeon.switchLevel(level, level.south);
                            break;
                        case NORTH53:
                            north();
                            Level.DirectionValue = 63;
                            Dungeon.saveAll();
                            if (Statistics.Level63Visited) {
                                Dungeon.depth = Level.DirectionValue;
                                level = Dungeon.loadLevel(Dungeon.hero.heroClass);
                            } else {
                                level = Dungeon.newLevel();
                                Badges.validateDeathFromFire();
                            }
                            Dungeon.switchLevel(level, level.south);
                            break;
                        case NORTH54:
                            north();
                            Level.DirectionValue = 64;
                            Dungeon.saveAll();
                            if (Statistics.Level64Visited) {
                                Dungeon.depth = Level.DirectionValue;
                                level = Dungeon.loadLevel(Dungeon.hero.heroClass);
                            } else {
                                level = Dungeon.newLevel();
                                Badges.validateDeathFromFire();
                            }
                            Dungeon.switchLevel(level, level.south);
                            break;
                        case NORTH55:
                            north();
                            Level.DirectionValue = 65;
                            Dungeon.saveAll();
                            if (Statistics.Level65Visited) {
                                Dungeon.depth = Level.DirectionValue;
                                level = Dungeon.loadLevel(Dungeon.hero.heroClass);
                            } else {
                                level = Dungeon.newLevel();
                                Badges.validateDeathFromFire();
                            }
                            Dungeon.switchLevel(level, level.south);
                            break;
                        case NORTH56:
                            north();
                            Level.DirectionValue = 66;
                            Dungeon.saveAll();
                            if (Statistics.Level66Visited) {
                                Dungeon.depth = Level.DirectionValue;
                                level = Dungeon.loadLevel(Dungeon.hero.heroClass);
                            } else {
                                level = Dungeon.newLevel();
                                Badges.validateDeathFromFire();
                            }
                            Dungeon.switchLevel(level, level.south);
                            break;
                        case NORTH57:
                            north();
                            Level.DirectionValue = 67;
                            Dungeon.saveAll();
                            if (Statistics.Level67Visited) {
                                Dungeon.depth = Level.DirectionValue;
                                level = Dungeon.loadLevel(Dungeon.hero.heroClass);
                            } else {
                                level = Dungeon.newLevel();
                                Badges.validateDeathFromFire();
                            }
                            Dungeon.switchLevel(level, level.south);
                            break;
                        case NORTH58:
                            north();
                            Level.DirectionValue = 68;
                            Dungeon.saveAll();
                            if (Statistics.Level68Visited) {
                                Dungeon.depth = Level.DirectionValue;
                                level = Dungeon.loadLevel(Dungeon.hero.heroClass);
                            } else {
                                level = Dungeon.newLevel();
                                Badges.validateDeathFromFire();
                            }
                            Dungeon.switchLevel(level, level.south);
                            break;
                        case NORTH59:
                            north();
                            Level.DirectionValue = 69;
                            Dungeon.saveAll();
                            if (Statistics.Level69Visited) {
                                Dungeon.depth = Level.DirectionValue;
                                level = Dungeon.loadLevel(Dungeon.hero.heroClass);
                            } else {
                                level = Dungeon.newLevel();
                                Badges.validateDeathFromFire();
                            }
                            Dungeon.switchLevel(level, level.south);
                            break;
                        case NORTH60:
                            north();
                            Level.DirectionValue = 40;
                            Dungeon.saveAll();
                            if (Statistics.Level40Visited) {
                                Dungeon.depth = Level.DirectionValue;
                                level = Dungeon.loadLevel(Dungeon.hero.heroClass);
                            } else {
                                level = Dungeon.newLevel();
                                Badges.validateDeathFromFire();
                            }
                            Dungeon.switchLevel(level, level.south);
                            break;
                        case NORTH61:
                            north();
                            Level.DirectionValue = 71;
                            Dungeon.saveAll();
                            if (Statistics.Level71Visited) {
                                Dungeon.depth = Level.DirectionValue;
                                level = Dungeon.loadLevel(Dungeon.hero.heroClass);
                            } else {
                                level = Dungeon.newLevel();
                                Badges.validateDeathFromFire();
                            }
                            Dungeon.switchLevel(level, level.south);
                            break;
                        case NORTH62:
                            north();
                            Level.DirectionValue = 72;
                            Dungeon.saveAll();
                            if (Statistics.Level72Visited) {
                                Dungeon.depth = Level.DirectionValue;
                                level = Dungeon.loadLevel(Dungeon.hero.heroClass);
                            } else {
                                level = Dungeon.newLevel();
                                Badges.validateDeathFromFire();
                            }
                            Dungeon.switchLevel(level, level.south);
                            break;
                        case NORTH63:
                            north();
                            Level.DirectionValue = 73;
                            Dungeon.saveAll();
                            if (Statistics.Level73Visited) {
                                Dungeon.depth = Level.DirectionValue;
                                level = Dungeon.loadLevel(Dungeon.hero.heroClass);
                            } else {
                                level = Dungeon.newLevel();
                                Badges.validateDeathFromFire();
                            }
                            Dungeon.switchLevel(level, level.south);
                            break;
                        case NORTH64:
                            north();
                            Level.DirectionValue = 74;
                            Dungeon.saveAll();
                            if (Statistics.Level74Visited) {
                                Dungeon.depth = Level.DirectionValue;
                                level = Dungeon.loadLevel(Dungeon.hero.heroClass);
                            } else {
                                level = Dungeon.newLevel();
                                Badges.validateDeathFromFire();
                            }
                            Dungeon.switchLevel(level, level.south);
                            break;
                        case NORTH65:
                            north();
                            Level.DirectionValue = 75;
                            Dungeon.saveAll();
                            if (Statistics.Level75Visited) {
                                Dungeon.depth = Level.DirectionValue;
                                level = Dungeon.loadLevel(Dungeon.hero.heroClass);
                            } else {
                                level = Dungeon.newLevel();
                                Badges.validateDeathFromFire();
                            }
                            Dungeon.switchLevel(level, level.south);
                            break;
                        case NORTH66:
                            north();
                            Level.DirectionValue = 76;
                            Dungeon.saveAll();
                            if (Statistics.Level76Visited) {
                                Dungeon.depth = Level.DirectionValue;
                                level = Dungeon.loadLevel(Dungeon.hero.heroClass);
                            } else {
                                level = Dungeon.newLevel();
                                Badges.validateDeathFromFire();
                            }
                            Dungeon.switchLevel(level, level.south);
                            break;
                        case NORTH67:
                            north();
                            Level.DirectionValue = 77;
                            Dungeon.saveAll();
                            if (Statistics.Level77Visited) {
                                Dungeon.depth = Level.DirectionValue;
                                level = Dungeon.loadLevel(Dungeon.hero.heroClass);
                            } else {
                                level = Dungeon.newLevel();
                                Badges.validateDeathFromFire();
                            }
                            Dungeon.switchLevel(level, level.south);
                            break;
                        case NORTH68:
                            north();
                            Level.DirectionValue = 78;
                            Dungeon.saveAll();
                            if (Statistics.Level78Visited) {
                                Dungeon.depth = Level.DirectionValue;
                                level = Dungeon.loadLevel(Dungeon.hero.heroClass);
                            } else {
                                level = Dungeon.newLevel();
                                Badges.validateDeathFromFire();
                            }
                            Dungeon.switchLevel(level, level.south);
                            break;
                        case NORTH69:
                            north();
                            Level.DirectionValue = 79;
                            Dungeon.saveAll();
                            if (Statistics.Level79Visited) {
                                Dungeon.depth = Level.DirectionValue;
                                level = Dungeon.loadLevel(Dungeon.hero.heroClass);
                            } else {
                                level = Dungeon.newLevel();
                                Badges.validateDeathFromFire();
                            }
                            Dungeon.switchLevel(level, level.south);
                            break;
                        case NORTH70:
                            north();
                            Level.DirectionValue = 80;
                            Dungeon.saveAll();
                            if (Statistics.Level80Visited) {
                                Dungeon.depth = Level.DirectionValue;
                                level = Dungeon.loadLevel(Dungeon.hero.heroClass);
                            } else {
                                level = Dungeon.newLevel();
                                Badges.validateDeathFromFire();
                            }
                            Dungeon.switchLevel(level, level.south);
                            break;
                        case NORTH71:
                            north();
                            Level.DirectionValue = 81;
                            Dungeon.saveAll();
                            if (Statistics.Level81Visited) {
                                Dungeon.depth = Level.DirectionValue;
                                level = Dungeon.loadLevel(Dungeon.hero.heroClass);
                            } else {
                                level = Dungeon.newLevel();
                                Badges.validateDeathFromFire();
                            }
                            Dungeon.switchLevel(level, level.south);
                            break;
                        case NORTH72:
                            north();
                            Level.DirectionValue = 82;
                            Dungeon.saveAll();
                            if (Statistics.Level82Visited) {
                                Dungeon.depth = Level.DirectionValue;
                                level = Dungeon.loadLevel(Dungeon.hero.heroClass);
                            } else {
                                level = Dungeon.newLevel();
                                Badges.validateDeathFromFire();
                            }
                            Dungeon.switchLevel(level, level.south);
                            break;
                        case NORTH73:
                            north();
                            Level.DirectionValue = 83;
                            Dungeon.saveAll();
                            if (Statistics.Level83Visited) {
                                Dungeon.depth = Level.DirectionValue;
                                level = Dungeon.loadLevel(Dungeon.hero.heroClass);
                            } else {
                                level = Dungeon.newLevel();
                                Badges.validateDeathFromFire();
                            }
                            Dungeon.switchLevel(level, level.south);
                            break;
                        case NORTH74:
                            north();
                            Level.DirectionValue = 84;
                            Dungeon.saveAll();
                            if (Statistics.Level84Visited) {
                                Dungeon.depth = Level.DirectionValue;
                                level = Dungeon.loadLevel(Dungeon.hero.heroClass);
                            } else {
                                level = Dungeon.newLevel();
                                Badges.validateDeathFromFire();
                            }
                            Dungeon.switchLevel(level, level.south);
                            break;
                        case NORTH75:
                            north();
                            Level.DirectionValue = 85;
                            Dungeon.saveAll();
                            if (Statistics.Level85Visited) {
                                Dungeon.depth = Level.DirectionValue;
                                level = Dungeon.loadLevel(Dungeon.hero.heroClass);
                            } else {
                                level = Dungeon.newLevel();
                                Badges.validateDeathFromFire();
                            }
                            Dungeon.switchLevel(level, level.south);
                            break;
                        case NORTH76:
                            north();
                            Level.DirectionValue = 86;
                            Dungeon.saveAll();
                            if (Statistics.Level86Visited) {
                                Dungeon.depth = Level.DirectionValue;
                                level = Dungeon.loadLevel(Dungeon.hero.heroClass);
                            } else {
                                level = Dungeon.newLevel();
                                Badges.validateDeathFromFire();
                            }
                            Dungeon.switchLevel(level, level.south);
                            break;
                        case NORTH77:
                            north();
                            Level.DirectionValue = 87;
                            Dungeon.saveAll();
                            if (Statistics.Level87Visited) {
                                Dungeon.depth = Level.DirectionValue;
                                level = Dungeon.loadLevel(Dungeon.hero.heroClass);
                            } else {
                                level = Dungeon.newLevel();
                                Badges.validateDeathFromFire();
                            }
                            Dungeon.switchLevel(level, level.south);
                            break;
                        case NORTH78:
                            north();
                            Level.DirectionValue = 88;
                            Dungeon.saveAll();
                            if (Statistics.Level88Visited) {
                                Dungeon.depth = Level.DirectionValue;
                                level = Dungeon.loadLevel(Dungeon.hero.heroClass);
                            } else {
                                level = Dungeon.newLevel();
                                Badges.validateDeathFromFire();
                            }
                            Dungeon.switchLevel(level, level.south);
                            break;
                        case NORTH79:
                            north();
                            Level.DirectionValue = 89;
                            Dungeon.saveAll();
                            if (Statistics.Level89Visited) {
                                Dungeon.depth = Level.DirectionValue;
                                level = Dungeon.loadLevel(Dungeon.hero.heroClass);
                            } else {
                                level = Dungeon.newLevel();
                                Badges.validateDeathFromFire();
                            }
                            Dungeon.switchLevel(level, level.south);
                            break;
                        case NORTH80:
                            north();
                            Level.DirectionValue = 90;
                            Dungeon.saveAll();
                            if (Statistics.Level90Visited) {
                                Dungeon.depth = Level.DirectionValue;
                                level = Dungeon.loadLevel(Dungeon.hero.heroClass);
                            } else {
                                level = Dungeon.newLevel();
                                Badges.validateDeathFromFire();
                            }
                            Dungeon.switchLevel(level, level.south);
                            break;
                        case NORTH81:
                            north();
                            Level.DirectionValue = 91;
                            Dungeon.saveAll();
                            if (Statistics.Level91Visited) {
                                Dungeon.depth = Level.DirectionValue;
                                level = Dungeon.loadLevel(Dungeon.hero.heroClass);
                            } else {
                                level = Dungeon.newLevel();
                                Badges.validateDeathFromFire();
                            }
                            Dungeon.switchLevel(level, level.south);
                            break;
                        case NORTH82:
                            north();
                            Level.DirectionValue = 92;
                            Dungeon.saveAll();
                            if (Statistics.Level92Visited) {
                                Dungeon.depth = Level.DirectionValue;
                                level = Dungeon.loadLevel(Dungeon.hero.heroClass);
                            } else {
                                level = Dungeon.newLevel();
                                Badges.validateDeathFromFire();
                            }
                            Dungeon.switchLevel(level, level.south);
                            break;
                        case NORTH83:
                            north();
                            Level.DirectionValue = 93;
                            Dungeon.saveAll();
                            if (Statistics.Level93Visited) {
                                Dungeon.depth = Level.DirectionValue;
                                level = Dungeon.loadLevel(Dungeon.hero.heroClass);
                            } else {
                                level = Dungeon.newLevel();
                                Badges.validateDeathFromFire();
                            }
                            Dungeon.switchLevel(level, level.south);
                            break;
                        case NORTH84:
                            north();
                            Level.DirectionValue = 94;
                            Dungeon.saveAll();
                            if (Statistics.Level94Visited) {
                                Dungeon.depth = Level.DirectionValue;
                                level = Dungeon.loadLevel(Dungeon.hero.heroClass);
                            } else {
                                level = Dungeon.newLevel();
                                Badges.validateDeathFromFire();
                            }
                            Dungeon.switchLevel(level, level.south);
                            break;
                        case NORTH85:
                            north();
                            Level.DirectionValue = 95;
                            Dungeon.saveAll();
                            if (Statistics.Level95Visited) {
                                Dungeon.depth = Level.DirectionValue;
                                level = Dungeon.loadLevel(Dungeon.hero.heroClass);
                            } else {
                                level = Dungeon.newLevel();
                                Badges.validateDeathFromFire();
                            }
                            Dungeon.switchLevel(level, level.south);
                            break;
                        case NORTH86:
                            north();
                            Level.DirectionValue = 96;
                            Dungeon.saveAll();
                            if (Statistics.Level96Visited) {
                                Dungeon.depth = Level.DirectionValue;
                                level = Dungeon.loadLevel(Dungeon.hero.heroClass);
                            } else {
                                level = Dungeon.newLevel();
                                Badges.validateDeathFromFire();
                            }
                            Dungeon.switchLevel(level, level.south);
                            break;
                        case NORTH87:
                            north();
                            Level.DirectionValue = 97;
                            Dungeon.saveAll();
                            if (Statistics.Level97Visited) {
                                Dungeon.depth = Level.DirectionValue;
                                level = Dungeon.loadLevel(Dungeon.hero.heroClass);
                            } else {
                                level = Dungeon.newLevel();
                                Badges.validateDeathFromFire();
                            }
                            Dungeon.switchLevel(level, level.south);
                            break;
                        case NORTH88:
                            north();
                            Level.DirectionValue = 98;
                            Dungeon.saveAll();
                            if (Statistics.Level98Visited) {
                                Dungeon.depth = Level.DirectionValue;
                                level = Dungeon.loadLevel(Dungeon.hero.heroClass);
                            } else {
                                level = Dungeon.newLevel();
                                Badges.validateDeathFromFire();
                            }
                            Dungeon.switchLevel(level, level.south);
                            break;
                        case NORTH89:
                            north();
                            Level.DirectionValue = 99;
                            Dungeon.saveAll();
                            if (Statistics.Level99Visited) {
                                Dungeon.depth = Level.DirectionValue;
                                level = Dungeon.loadLevel(Dungeon.hero.heroClass);
                            } else {
                                level = Dungeon.newLevel();
                                Badges.validateDeathFromFire();
                            }
                            Dungeon.switchLevel(level, level.south);
                            break;
                        case NORTH90:
                            north();
                            Level.DirectionValue = 100;
                            Dungeon.saveAll();
                            if (Statistics.Level100Visited) {
                                Dungeon.depth = Level.DirectionValue;
                                level = Dungeon.loadLevel(Dungeon.hero.heroClass);
                            } else {
                                level = Dungeon.newLevel();
                                Badges.validateDeathFromFire();
                            }
                            Dungeon.switchLevel(level, level.south);
                            break;
                        case SOUTH:
                            south();
                            Level.DirectionValue = 1;
                            Dungeon.saveAll();
                            if (Statistics.Level1Visited) {
                                Dungeon.depth = Level.DirectionValue;
                                level = Dungeon.loadLevel(Dungeon.hero.heroClass);
                            } else {
                                level = Dungeon.newLevel();
                                Badges.validateDeathFromFire();
                            }
                            Dungeon.switchLevel(level, level.north);
                            break;
                        case SOUTH1:
                            south();
                            Level.DirectionValue = 1;
                            Dungeon.saveAll();
                            if (Statistics.Level1Visited) {
                                Dungeon.depth = Level.DirectionValue;
                                level = Dungeon.loadLevel(Dungeon.hero.heroClass);
                            } else {
                                level = Dungeon.newLevel();
                                Badges.validateDeathFromFire();
                            }
                            Dungeon.switchLevel(level, level.north);
                            break;
                        case SOUTH2:
                            south();
                            Level.DirectionValue = 2;
                            Dungeon.saveAll();
                            if (Statistics.Level2Visited) {
                                Dungeon.depth = Level.DirectionValue;
                                level = Dungeon.loadLevel(Dungeon.hero.heroClass);
                            } else {
                                level = Dungeon.newLevel();
                                Badges.validateDeathFromFire();
                            }
                            Dungeon.switchLevel(level, level.north);
                            break;
                        case SOUTH3:
                            south();
                            Level.DirectionValue = 3;
                            Dungeon.saveAll();
                            if (Statistics.Level3Visited) {
                                Dungeon.depth = Level.DirectionValue;
                                level = Dungeon.loadLevel(Dungeon.hero.heroClass);
                            } else {
                                level = Dungeon.newLevel();
                                Badges.validateDeathFromFire();
                            }
                            Dungeon.switchLevel(level, level.north);
                            break;
                        case SOUTH4:
                            south();
                            Level.DirectionValue = 4;
                            Dungeon.saveAll();
                            if (Statistics.Level4Visited) {
                                Dungeon.depth = Level.DirectionValue;
                                level = Dungeon.loadLevel(Dungeon.hero.heroClass);
                            } else {
                                level = Dungeon.newLevel();
                                Badges.validateDeathFromFire();
                            }
                            Dungeon.switchLevel(level, level.north);
                            break;
                        case SOUTH5:
                            south();
                            Level.DirectionValue = 5;
                            Dungeon.saveAll();
                            if (Statistics.Level5Visited) {
                                Dungeon.depth = Level.DirectionValue;
                                level = Dungeon.loadLevel(Dungeon.hero.heroClass);
                            } else {
                                level = Dungeon.newLevel();
                                Badges.validateDeathFromFire();
                            }
                            Dungeon.switchLevel(level, level.north);
                            break;
                        case SOUTH6:
                            south();
                            Level.DirectionValue = 6;
                            Dungeon.saveAll();
                            if (Statistics.Level6Visited) {
                                Dungeon.depth = Level.DirectionValue;
                                level = Dungeon.loadLevel(Dungeon.hero.heroClass);
                            } else {
                                level = Dungeon.newLevel();
                                Badges.validateDeathFromFire();
                            }
                            Dungeon.switchLevel(level, level.north);
                            break;
                        case SOUTH7:
                            south();
                            Level.DirectionValue = 7;
                            Dungeon.saveAll();
                            if (Statistics.Level7Visited) {
                                Dungeon.depth = Level.DirectionValue;
                                level = Dungeon.loadLevel(Dungeon.hero.heroClass);
                            } else {
                                level = Dungeon.newLevel();
                                Badges.validateDeathFromFire();
                            }
                            Dungeon.switchLevel(level, level.north);
                            break;
                        case SOUTH8:
                            south();
                            Level.DirectionValue = 8;
                            Dungeon.saveAll();
                            if (Statistics.Level8Visited) {
                                Dungeon.depth = Level.DirectionValue;
                                level = Dungeon.loadLevel(Dungeon.hero.heroClass);
                            } else {
                                level = Dungeon.newLevel();
                                Badges.validateDeathFromFire();
                            }
                            Dungeon.switchLevel(level, level.north);
                            break;
                        case SOUTH9:
                            south();
                            Level.DirectionValue = 9;
                            Dungeon.saveAll();
                            if (Statistics.Level9Visited) {
                                Dungeon.depth = Level.DirectionValue;
                                level = Dungeon.loadLevel(Dungeon.hero.heroClass);
                            } else {
                                level = Dungeon.newLevel();
                                Badges.validateDeathFromFire();
                            }
                            Dungeon.switchLevel(level, level.north);
                            break;
                        case SOUTH10:
                            south();
                            Level.DirectionValue = 10;
                            Dungeon.saveAll();
                            if (Statistics.Level10Visited) {
                                Dungeon.depth = Level.DirectionValue;
                                level = Dungeon.loadLevel(Dungeon.hero.heroClass);
                            } else {
                                level = Dungeon.newLevel();
                                Badges.validateDeathFromFire();
                            }
                            Dungeon.switchLevel(level, level.north);
                            break;
                        case SOUTH11:
                            south();
                            Level.DirectionValue = 11;
                            Dungeon.saveAll();
                            if (Statistics.Level11Visited) {
                                Dungeon.depth = Level.DirectionValue;
                                level = Dungeon.loadLevel(Dungeon.hero.heroClass);
                            } else {
                                level = Dungeon.newLevel();
                                Badges.validateDeathFromFire();
                            }
                            Dungeon.switchLevel(level, level.north);
                            break;
                        case SOUTH12:
                            south();
                            Level.DirectionValue = 12;
                            Dungeon.saveAll();
                            if (Statistics.Level12Visited) {
                                Dungeon.depth = Level.DirectionValue;
                                level = Dungeon.loadLevel(Dungeon.hero.heroClass);
                            } else {
                                level = Dungeon.newLevel();
                                Badges.validateDeathFromFire();
                            }
                            Dungeon.switchLevel(level, level.north);
                            break;
                        case SOUTH13:
                            south();
                            Level.DirectionValue = 13;
                            Dungeon.saveAll();
                            if (Statistics.Level13Visited) {
                                Dungeon.depth = Level.DirectionValue;
                                level = Dungeon.loadLevel(Dungeon.hero.heroClass);
                            } else {
                                level = Dungeon.newLevel();
                                Badges.validateDeathFromFire();
                            }
                            Dungeon.switchLevel(level, level.north);
                            break;
                        case SOUTH14:
                            south();
                            Level.DirectionValue = 14;
                            Dungeon.saveAll();
                            if (Statistics.Level14Visited) {
                                Dungeon.depth = Level.DirectionValue;
                                level = Dungeon.loadLevel(Dungeon.hero.heroClass);
                            } else {
                                level = Dungeon.newLevel();
                                Badges.validateDeathFromFire();
                            }
                            Dungeon.switchLevel(level, level.north);
                            break;
                        case SOUTH15:
                            south();
                            Level.DirectionValue = 15;
                            Dungeon.saveAll();
                            if (Statistics.Level15Visited) {
                                Dungeon.depth = Level.DirectionValue;
                                level = Dungeon.loadLevel(Dungeon.hero.heroClass);
                            } else {
                                level = Dungeon.newLevel();
                                Badges.validateDeathFromFire();
                            }
                            Dungeon.switchLevel(level, level.north);
                            break;
                        case SOUTH16:
                            south();
                            Level.DirectionValue = 16;
                            Dungeon.saveAll();
                            if (Statistics.Level16Visited) {
                                Dungeon.depth = Level.DirectionValue;
                                level = Dungeon.loadLevel(Dungeon.hero.heroClass);
                            } else {
                                level = Dungeon.newLevel();
                                Badges.validateDeathFromFire();
                            }
                            Dungeon.switchLevel(level, level.north);
                            break;
                        case SOUTH17:
                            south();
                            Level.DirectionValue = 17;
                            Dungeon.saveAll();
                            if (Statistics.Level17Visited) {
                                Dungeon.depth = Level.DirectionValue;
                                level = Dungeon.loadLevel(Dungeon.hero.heroClass);
                            } else {
                                level = Dungeon.newLevel();
                                Badges.validateDeathFromFire();
                            }
                            Dungeon.switchLevel(level, level.north);
                            break;
                        case SOUTH18:
                            south();
                            Level.DirectionValue = 18;
                            Dungeon.saveAll();
                            if (Statistics.Level18Visited) {
                                Dungeon.depth = Level.DirectionValue;
                                level = Dungeon.loadLevel(Dungeon.hero.heroClass);
                            } else {
                                level = Dungeon.newLevel();
                                Badges.validateDeathFromFire();
                            }
                            Dungeon.switchLevel(level, level.north);
                            break;
                        case SOUTH19:
                            south();
                            Level.DirectionValue = 19;
                            Dungeon.saveAll();
                            if (Statistics.Level19Visited) {
                                Dungeon.depth = Level.DirectionValue;
                                level = Dungeon.loadLevel(Dungeon.hero.heroClass);
                            } else {
                                level = Dungeon.newLevel();
                                Badges.validateDeathFromFire();
                            }
                            Dungeon.switchLevel(level, level.north);
                            break;
                        case SOUTH20:
                            south();
                            Level.DirectionValue = 20;
                            Dungeon.saveAll();
                            if (Statistics.Level20Visited) {
                                Dungeon.depth = Level.DirectionValue;
                                level = Dungeon.loadLevel(Dungeon.hero.heroClass);
                            } else {
                                level = Dungeon.newLevel();
                                Badges.validateDeathFromFire();
                            }
                            Dungeon.switchLevel(level, level.north);
                            break;
                        case SOUTH21:
                            south();
                            Level.DirectionValue = 21;
                            Dungeon.saveAll();
                            if (Statistics.Level21Visited) {
                                Dungeon.depth = Level.DirectionValue;
                                level = Dungeon.loadLevel(Dungeon.hero.heroClass);
                            } else {
                                level = Dungeon.newLevel();
                                Badges.validateDeathFromFire();
                            }
                            Dungeon.switchLevel(level, level.north);
                            break;
                        case SOUTH22:
                            south();
                            Level.DirectionValue = 22;
                            Dungeon.saveAll();
                            if (Statistics.Level22Visited) {
                                Dungeon.depth = Level.DirectionValue;
                                level = Dungeon.loadLevel(Dungeon.hero.heroClass);
                            } else {
                                level = Dungeon.newLevel();
                                Badges.validateDeathFromFire();
                            }
                            Dungeon.switchLevel(level, level.north);
                            break;
                        case SOUTH23:
                            south();
                            Level.DirectionValue = 23;
                            Dungeon.saveAll();
                            if (Statistics.Level23Visited) {
                                Dungeon.depth = Level.DirectionValue;
                                level = Dungeon.loadLevel(Dungeon.hero.heroClass);
                            } else {
                                level = Dungeon.newLevel();
                                Badges.validateDeathFromFire();
                            }
                            Dungeon.switchLevel(level, level.north);
                            break;
                        case SOUTH24:
                            south();
                            Level.DirectionValue = 24;
                            Dungeon.saveAll();
                            if (Statistics.Level24Visited) {
                                Dungeon.depth = Level.DirectionValue;
                                level = Dungeon.loadLevel(Dungeon.hero.heroClass);
                            } else {
                                level = Dungeon.newLevel();
                                Badges.validateDeathFromFire();
                            }
                            Dungeon.switchLevel(level, level.north);
                            break;
                        case SOUTH25:
                            south();
                            Level.DirectionValue = 25;
                            Dungeon.saveAll();
                            if (Statistics.Level25Visited) {
                                Dungeon.depth = Level.DirectionValue;
                                level = Dungeon.loadLevel(Dungeon.hero.heroClass);
                            } else {
                                level = Dungeon.newLevel();
                                Badges.validateDeathFromFire();
                            }
                            Dungeon.switchLevel(level, level.north);
                            break;
                        case SOUTH26:
                            south();
                            Level.DirectionValue = 26;
                            Dungeon.saveAll();
                            if (Statistics.Level26Visited) {
                                Dungeon.depth = Level.DirectionValue;
                                level = Dungeon.loadLevel(Dungeon.hero.heroClass);
                            } else {
                                level = Dungeon.newLevel();
                                Badges.validateDeathFromFire();
                            }
                            Dungeon.switchLevel(level, level.north);
                            break;
                        case SOUTH27:
                            south();
                            Level.DirectionValue = 27;
                            Dungeon.saveAll();
                            if (Statistics.Level27Visited) {
                                Dungeon.depth = Level.DirectionValue;
                                level = Dungeon.loadLevel(Dungeon.hero.heroClass);
                            } else {
                                level = Dungeon.newLevel();
                                Badges.validateDeathFromFire();
                            }
                            Dungeon.switchLevel(level, level.north);
                            break;
                        case SOUTH28:
                            south();
                            Level.DirectionValue = 28;
                            Dungeon.saveAll();
                            if (Statistics.Level28Visited) {
                                Dungeon.depth = Level.DirectionValue;
                                level = Dungeon.loadLevel(Dungeon.hero.heroClass);
                            } else {
                                level = Dungeon.newLevel();
                                Badges.validateDeathFromFire();
                            }
                            Dungeon.switchLevel(level, level.north);
                            break;
                        case SOUTH29:
                            south();
                            Level.DirectionValue = 29;
                            Dungeon.saveAll();
                            if (Statistics.Level29Visited) {
                                Dungeon.depth = Level.DirectionValue;
                                level = Dungeon.loadLevel(Dungeon.hero.heroClass);
                            } else {
                                level = Dungeon.newLevel();
                                Badges.validateDeathFromFire();
                            }
                            Dungeon.switchLevel(level, level.north);
                            break;
                        case SOUTH30:
                            south();
                            Level.DirectionValue = 30;
                            Dungeon.saveAll();
                            if (Statistics.Level30Visited) {
                                Dungeon.depth = Level.DirectionValue;
                                level = Dungeon.loadLevel(Dungeon.hero.heroClass);
                            } else {
                                level = Dungeon.newLevel();
                                Badges.validateDeathFromFire();
                            }
                            Dungeon.switchLevel(level, level.north);
                            break;
                        case SOUTH31:
                            south();
                            Level.DirectionValue = 31;
                            Dungeon.saveAll();
                            if (Statistics.Level31Visited) {
                                Dungeon.depth = Level.DirectionValue;
                                level = Dungeon.loadLevel(Dungeon.hero.heroClass);
                            } else {
                                level = Dungeon.newLevel();
                                Badges.validateDeathFromFire();
                            }
                            Dungeon.switchLevel(level, level.north);
                            break;
                        case SOUTH32:
                            south();
                            Level.DirectionValue = 32;
                            Dungeon.saveAll();
                            if (Statistics.Level32Visited) {
                                Dungeon.depth = Level.DirectionValue;
                                level = Dungeon.loadLevel(Dungeon.hero.heroClass);
                            } else {
                                level = Dungeon.newLevel();
                                Badges.validateDeathFromFire();
                            }
                            Dungeon.switchLevel(level, level.north);
                            break;
                        case SOUTH33:
                            south();
                            Level.DirectionValue = 33;
                            Dungeon.saveAll();
                            if (Statistics.Level33Visited) {
                                Dungeon.depth = Level.DirectionValue;
                                level = Dungeon.loadLevel(Dungeon.hero.heroClass);
                            } else {
                                level = Dungeon.newLevel();
                                Badges.validateDeathFromFire();
                            }
                            Dungeon.switchLevel(level, level.north);
                            break;
                        case SOUTH34:
                            south();
                            Level.DirectionValue = 34;
                            Dungeon.saveAll();
                            if (Statistics.Level34Visited) {
                                Dungeon.depth = Level.DirectionValue;
                                level = Dungeon.loadLevel(Dungeon.hero.heroClass);
                            } else {
                                level = Dungeon.newLevel();
                                Badges.validateDeathFromFire();
                            }
                            Dungeon.switchLevel(level, level.north);
                            break;
                        case SOUTH35:
                            south();
                            Level.DirectionValue = 35;
                            Dungeon.saveAll();
                            if (Statistics.Level35Visited) {
                                Dungeon.depth = Level.DirectionValue;
                                level = Dungeon.loadLevel(Dungeon.hero.heroClass);
                            } else {
                                level = Dungeon.newLevel();
                                Badges.validateDeathFromFire();
                            }
                            Dungeon.switchLevel(level, level.north);
                            break;
                        case SOUTH36:
                            south();
                            Level.DirectionValue = 36;
                            Dungeon.saveAll();
                            if (Statistics.Level36Visited) {
                                Dungeon.depth = Level.DirectionValue;
                                level = Dungeon.loadLevel(Dungeon.hero.heroClass);
                            } else {
                                level = Dungeon.newLevel();
                                Badges.validateDeathFromFire();
                            }
                            Dungeon.switchLevel(level, level.north);
                            break;
                        case SOUTH37:
                            south();
                            Level.DirectionValue = 37;
                            Dungeon.saveAll();
                            if (Statistics.Level37Visited) {
                                Dungeon.depth = Level.DirectionValue;
                                level = Dungeon.loadLevel(Dungeon.hero.heroClass);
                            } else {
                                level = Dungeon.newLevel();
                                Badges.validateDeathFromFire();
                            }
                            Dungeon.switchLevel(level, level.north);
                            break;
                        case SOUTH38:
                            south();
                            Level.DirectionValue = 38;
                            Dungeon.saveAll();
                            if (Statistics.Level38Visited) {
                                Dungeon.depth = Level.DirectionValue;
                                level = Dungeon.loadLevel(Dungeon.hero.heroClass);
                            } else {
                                level = Dungeon.newLevel();
                                Badges.validateDeathFromFire();
                            }
                            Dungeon.switchLevel(level, level.north);
                            break;
                        case SOUTH39:
                            south();
                            Level.DirectionValue = 39;
                            Dungeon.saveAll();
                            if (Statistics.Level39Visited) {
                                Dungeon.depth = Level.DirectionValue;
                                level = Dungeon.loadLevel(Dungeon.hero.heroClass);
                            } else {
                                level = Dungeon.newLevel();
                                Badges.validateDeathFromFire();
                            }
                            Dungeon.switchLevel(level, level.north);
                            break;
                        case SOUTH40:
                            south();
                            Level.DirectionValue = 40;
                            Dungeon.saveAll();
                            if (Statistics.Level20Visited) {
                                Dungeon.depth = Level.DirectionValue;
                                level = Dungeon.loadLevel(Dungeon.hero.heroClass);
                            } else {
                                level = Dungeon.newLevel();
                                Badges.validateDeathFromFire();
                            }
                            Dungeon.switchLevel(level, level.north);
                            break;
                        case SOUTH41:
                            south();
                            Level.DirectionValue = 41;
                            Dungeon.saveAll();
                            if (Statistics.Level41Visited) {
                                Dungeon.depth = Level.DirectionValue;
                                level = Dungeon.loadLevel(Dungeon.hero.heroClass);
                            } else {
                                level = Dungeon.newLevel();
                                Badges.validateDeathFromFire();
                            }
                            Dungeon.switchLevel(level, level.north);
                            break;
                        case SOUTH42:
                            south();
                            Level.DirectionValue = 42;
                            Dungeon.saveAll();
                            if (Statistics.Level42Visited) {
                                Dungeon.depth = Level.DirectionValue;
                                level = Dungeon.loadLevel(Dungeon.hero.heroClass);
                            } else {
                                level = Dungeon.newLevel();
                                Badges.validateDeathFromFire();
                            }
                            Dungeon.switchLevel(level, level.north);
                            break;
                        case SOUTH43:
                            south();
                            Level.DirectionValue = 43;
                            Dungeon.saveAll();
                            if (Statistics.Level43Visited) {
                                Dungeon.depth = Level.DirectionValue;
                                level = Dungeon.loadLevel(Dungeon.hero.heroClass);
                            } else {
                                level = Dungeon.newLevel();
                                Badges.validateDeathFromFire();
                            }
                            Dungeon.switchLevel(level, level.north);
                            break;
                        case SOUTH44:
                            south();
                            Level.DirectionValue = 44;
                            Dungeon.saveAll();
                            if (Statistics.Level44Visited) {
                                Dungeon.depth = Level.DirectionValue;
                                level = Dungeon.loadLevel(Dungeon.hero.heroClass);
                            } else {
                                level = Dungeon.newLevel();
                                Badges.validateDeathFromFire();
                            }
                            Dungeon.switchLevel(level, level.north);
                            break;
                        case SOUTH45:
                            south();
                            Level.DirectionValue = 45;
                            Dungeon.saveAll();
                            if (Statistics.Level45Visited) {
                                Dungeon.depth = Level.DirectionValue;
                                level = Dungeon.loadLevel(Dungeon.hero.heroClass);
                            } else {
                                level = Dungeon.newLevel();
                                Badges.validateDeathFromFire();
                            }
                            Dungeon.switchLevel(level, level.north);
                            break;
                        case SOUTH46:
                            south();
                            Level.DirectionValue = 46;
                            Dungeon.saveAll();
                            if (Statistics.Level46Visited) {
                                Dungeon.depth = Level.DirectionValue;
                                level = Dungeon.loadLevel(Dungeon.hero.heroClass);
                            } else {
                                level = Dungeon.newLevel();
                                Badges.validateDeathFromFire();
                            }
                            Dungeon.switchLevel(level, level.north);
                            break;
                        case SOUTH47:
                            south();
                            Level.DirectionValue = 47;
                            Dungeon.saveAll();
                            if (Statistics.Level47Visited) {
                                Dungeon.depth = Level.DirectionValue;
                                level = Dungeon.loadLevel(Dungeon.hero.heroClass);
                            } else {
                                level = Dungeon.newLevel();
                                Badges.validateDeathFromFire();
                            }
                            Dungeon.switchLevel(level, level.north);
                            break;
                        case SOUTH48:
                            south();
                            Level.DirectionValue = 48;
                            Dungeon.saveAll();
                            if (Statistics.Level48Visited) {
                                Dungeon.depth = Level.DirectionValue;
                                level = Dungeon.loadLevel(Dungeon.hero.heroClass);
                            } else {
                                level = Dungeon.newLevel();
                                Badges.validateDeathFromFire();
                            }
                            Dungeon.switchLevel(level, level.north);
                            break;
                        case SOUTH49:
                            south();
                            Level.DirectionValue = 49;
                            Dungeon.saveAll();
                            if (Statistics.Level49Visited) {
                                Dungeon.depth = Level.DirectionValue;
                                level = Dungeon.loadLevel(Dungeon.hero.heroClass);
                            } else {
                                level = Dungeon.newLevel();
                                Badges.validateDeathFromFire();
                            }
                            Dungeon.switchLevel(level, level.north);
                            break;
                        case SOUTH50:
                            south();
                            Level.DirectionValue = 50;
                            Dungeon.saveAll();
                            if (Statistics.Level20Visited) {
                                Dungeon.depth = Level.DirectionValue;
                                level = Dungeon.loadLevel(Dungeon.hero.heroClass);
                            } else {
                                level = Dungeon.newLevel();
                                Badges.validateDeathFromFire();
                            }
                            Dungeon.switchLevel(level, level.north);
                            break;
                        case SOUTH51:
                            south();
                            Level.DirectionValue = 51;
                            Dungeon.saveAll();
                            if (Statistics.Level51Visited) {
                                Dungeon.depth = Level.DirectionValue;
                                level = Dungeon.loadLevel(Dungeon.hero.heroClass);
                            } else {
                                level = Dungeon.newLevel();
                                Badges.validateDeathFromFire();
                            }
                            Dungeon.switchLevel(level, level.north);
                            break;
                        case SOUTH52:
                            south();
                            Level.DirectionValue = 52;
                            Dungeon.saveAll();
                            if (Statistics.Level52Visited) {
                                Dungeon.depth = Level.DirectionValue;
                                level = Dungeon.loadLevel(Dungeon.hero.heroClass);
                            } else {
                                level = Dungeon.newLevel();
                                Badges.validateDeathFromFire();
                            }
                            Dungeon.switchLevel(level, level.north);
                            break;
                        case SOUTH53:
                            south();
                            Level.DirectionValue = 53;
                            Dungeon.saveAll();
                            if (Statistics.Level53Visited) {
                                Dungeon.depth = Level.DirectionValue;
                                level = Dungeon.loadLevel(Dungeon.hero.heroClass);
                            } else {
                                level = Dungeon.newLevel();
                                Badges.validateDeathFromFire();
                            }
                            Dungeon.switchLevel(level, level.north);
                            break;
                        case SOUTH54:
                            south();
                            Level.DirectionValue = 54;
                            Dungeon.saveAll();
                            if (Statistics.Level54Visited) {
                                Dungeon.depth = Level.DirectionValue;
                                level = Dungeon.loadLevel(Dungeon.hero.heroClass);
                            } else {
                                level = Dungeon.newLevel();
                                Badges.validateDeathFromFire();
                            }
                            Dungeon.switchLevel(level, level.north);
                            break;
                        case SOUTH55:
                            south();
                            Level.DirectionValue = 55;
                            Dungeon.saveAll();
                            if (Statistics.Level55Visited) {
                                Dungeon.depth = Level.DirectionValue;
                                level = Dungeon.loadLevel(Dungeon.hero.heroClass);
                            } else {
                                level = Dungeon.newLevel();
                                Badges.validateDeathFromFire();
                            }
                            Dungeon.switchLevel(level, level.north);
                            break;
                        case SOUTH56:
                            south();
                            Level.DirectionValue = 56;
                            Dungeon.saveAll();
                            if (Statistics.Level56Visited) {
                                Dungeon.depth = Level.DirectionValue;
                                level = Dungeon.loadLevel(Dungeon.hero.heroClass);
                            } else {
                                level = Dungeon.newLevel();
                                Badges.validateDeathFromFire();
                            }
                            Dungeon.switchLevel(level, level.north);
                            break;
                        case SOUTH57:
                            south();
                            Level.DirectionValue = 57;
                            Dungeon.saveAll();
                            if (Statistics.Level57Visited) {
                                Dungeon.depth = Level.DirectionValue;
                                level = Dungeon.loadLevel(Dungeon.hero.heroClass);
                            } else {
                                level = Dungeon.newLevel();
                                Badges.validateDeathFromFire();
                            }
                            Dungeon.switchLevel(level, level.north);
                            break;
                        case SOUTH58:
                            south();
                            Level.DirectionValue = 58;
                            Dungeon.saveAll();
                            if (Statistics.Level58Visited) {
                                Dungeon.depth = Level.DirectionValue;
                                level = Dungeon.loadLevel(Dungeon.hero.heroClass);
                            } else {
                                level = Dungeon.newLevel();
                                Badges.validateDeathFromFire();
                            }
                            Dungeon.switchLevel(level, level.north);
                            break;
                        case SOUTH59:
                            south();
                            Level.DirectionValue = 59;
                            Dungeon.saveAll();
                            if (Statistics.Level59Visited) {
                                Dungeon.depth = Level.DirectionValue;
                                level = Dungeon.loadLevel(Dungeon.hero.heroClass);
                            } else {
                                level = Dungeon.newLevel();
                                Badges.validateDeathFromFire();
                            }
                            Dungeon.switchLevel(level, level.north);
                            break;
                        case SOUTH60:
                            south();
                            Level.DirectionValue = 60;
                            Dungeon.saveAll();
                            if (Statistics.Level60Visited) {
                                Dungeon.depth = Level.DirectionValue;
                                level = Dungeon.loadLevel(Dungeon.hero.heroClass);
                            } else {
                                level = Dungeon.newLevel();
                                Badges.validateDeathFromFire();
                            }
                            Dungeon.switchLevel(level, level.north);
                            break;
                        case SOUTH61:
                            south();
                            Level.DirectionValue = 61;
                            Dungeon.saveAll();
                            if (Statistics.Level61Visited) {
                                Dungeon.depth = Level.DirectionValue;
                                level = Dungeon.loadLevel(Dungeon.hero.heroClass);
                            } else {
                                level = Dungeon.newLevel();
                                Badges.validateDeathFromFire();
                            }
                            Dungeon.switchLevel(level, level.north);
                            break;
                        case SOUTH62:
                            south();
                            Level.DirectionValue = 62;
                            Dungeon.saveAll();
                            if (Statistics.Level62Visited) {
                                Dungeon.depth = Level.DirectionValue;
                                level = Dungeon.loadLevel(Dungeon.hero.heroClass);
                            } else {
                                level = Dungeon.newLevel();
                                Badges.validateDeathFromFire();
                            }
                            Dungeon.switchLevel(level, level.north);
                            break;
                        case SOUTH63:
                            south();
                            Level.DirectionValue = 63;
                            Dungeon.saveAll();
                            if (Statistics.Level63Visited) {
                                Dungeon.depth = Level.DirectionValue;
                                level = Dungeon.loadLevel(Dungeon.hero.heroClass);
                            } else {
                                level = Dungeon.newLevel();
                                Badges.validateDeathFromFire();
                            }
                            Dungeon.switchLevel(level, level.north);
                            break;
                        case SOUTH64:
                            south();
                            Level.DirectionValue = 64;
                            Dungeon.saveAll();
                            if (Statistics.Level64Visited) {
                                Dungeon.depth = Level.DirectionValue;
                                level = Dungeon.loadLevel(Dungeon.hero.heroClass);
                            } else {
                                level = Dungeon.newLevel();
                                Badges.validateDeathFromFire();
                            }
                            Dungeon.switchLevel(level, level.north);
                            break;
                        case SOUTH65:
                            south();
                            Level.DirectionValue = 65;
                            Dungeon.saveAll();
                            if (Statistics.Level65Visited) {
                                Dungeon.depth = Level.DirectionValue;
                                level = Dungeon.loadLevel(Dungeon.hero.heroClass);
                            } else {
                                level = Dungeon.newLevel();
                                Badges.validateDeathFromFire();
                            }
                            Dungeon.switchLevel(level, level.north);
                            break;
                        case SOUTH66:
                            south();
                            Level.DirectionValue = 66;
                            Dungeon.saveAll();
                            if (Statistics.Level66Visited) {
                                Dungeon.depth = Level.DirectionValue;
                                level = Dungeon.loadLevel(Dungeon.hero.heroClass);
                            } else {
                                level = Dungeon.newLevel();
                                Badges.validateDeathFromFire();
                            }
                            Dungeon.switchLevel(level, level.north);
                            break;
                        case SOUTH67:
                            south();
                            Level.DirectionValue = 67;
                            Dungeon.saveAll();
                            if (Statistics.Level67Visited) {
                                Dungeon.depth = Level.DirectionValue;
                                level = Dungeon.loadLevel(Dungeon.hero.heroClass);
                            } else {
                                level = Dungeon.newLevel();
                                Badges.validateDeathFromFire();
                            }
                            Dungeon.switchLevel(level, level.north);
                            break;
                        case SOUTH68:
                            south();
                            Level.DirectionValue = 68;
                            Dungeon.saveAll();
                            if (Statistics.Level68Visited) {
                                Dungeon.depth = Level.DirectionValue;
                                level = Dungeon.loadLevel(Dungeon.hero.heroClass);
                            } else {
                                level = Dungeon.newLevel();
                                Badges.validateDeathFromFire();
                            }
                            Dungeon.switchLevel(level, level.north);
                            break;
                        case SOUTH69:
                            south();
                            Level.DirectionValue = 69;
                            Dungeon.saveAll();
                            if (Statistics.Level69Visited) {
                                Dungeon.depth = Level.DirectionValue;
                                level = Dungeon.loadLevel(Dungeon.hero.heroClass);
                            } else {
                                level = Dungeon.newLevel();
                                Badges.validateDeathFromFire();
                            }
                            Dungeon.switchLevel(level, level.north);
                            break;
                        case SOUTH70:
                            south();
                            Level.DirectionValue = 70;
                            Dungeon.saveAll();
                            if (Statistics.Level20Visited) {
                                Dungeon.depth = Level.DirectionValue;
                                level = Dungeon.loadLevel(Dungeon.hero.heroClass);
                            } else {
                                level = Dungeon.newLevel();
                                Badges.validateDeathFromFire();
                            }
                            Dungeon.switchLevel(level, level.north);
                            break;
                        case SOUTH71:
                            south();
                            Level.DirectionValue = 71;
                            Dungeon.saveAll();
                            if (Statistics.Level71Visited) {
                                Dungeon.depth = Level.DirectionValue;
                                level = Dungeon.loadLevel(Dungeon.hero.heroClass);
                            } else {
                                level = Dungeon.newLevel();
                                Badges.validateDeathFromFire();
                            }
                            Dungeon.switchLevel(level, level.north);
                            break;
                        case SOUTH72:
                            south();
                            Level.DirectionValue = 72;
                            Dungeon.saveAll();
                            if (Statistics.Level72Visited) {
                                Dungeon.depth = Level.DirectionValue;
                                level = Dungeon.loadLevel(Dungeon.hero.heroClass);
                            } else {
                                level = Dungeon.newLevel();
                                Badges.validateDeathFromFire();
                            }
                            Dungeon.switchLevel(level, level.north);
                            break;
                        case SOUTH73:
                            south();
                            Level.DirectionValue = 73;
                            Dungeon.saveAll();
                            if (Statistics.Level73Visited) {
                                Dungeon.depth = Level.DirectionValue;
                                level = Dungeon.loadLevel(Dungeon.hero.heroClass);
                            } else {
                                level = Dungeon.newLevel();
                                Badges.validateDeathFromFire();
                            }
                            Dungeon.switchLevel(level, level.north);
                            break;
                        case SOUTH74:
                            south();
                            Level.DirectionValue = 74;
                            Dungeon.saveAll();
                            if (Statistics.Level74Visited) {
                                Dungeon.depth = Level.DirectionValue;
                                level = Dungeon.loadLevel(Dungeon.hero.heroClass);
                            } else {
                                level = Dungeon.newLevel();
                                Badges.validateDeathFromFire();
                            }
                            Dungeon.switchLevel(level, level.north);
                            break;
                        case SOUTH75:
                            south();
                            Level.DirectionValue = 75;
                            Dungeon.saveAll();
                            if (Statistics.Level75Visited) {
                                Dungeon.depth = Level.DirectionValue;
                                level = Dungeon.loadLevel(Dungeon.hero.heroClass);
                            } else {
                                level = Dungeon.newLevel();
                                Badges.validateDeathFromFire();
                            }
                            Dungeon.switchLevel(level, level.north);
                            break;
                        case SOUTH76:
                            south();
                            Level.DirectionValue = 76;
                            Dungeon.saveAll();
                            if (Statistics.Level76Visited) {
                                Dungeon.depth = Level.DirectionValue;
                                level = Dungeon.loadLevel(Dungeon.hero.heroClass);
                            } else {
                                level = Dungeon.newLevel();
                                Badges.validateDeathFromFire();
                            }
                            Dungeon.switchLevel(level, level.north);
                            break;
                        case SOUTH77:
                            south();
                            Level.DirectionValue = 77;
                            Dungeon.saveAll();
                            if (Statistics.Level77Visited) {
                                Dungeon.depth = Level.DirectionValue;
                                level = Dungeon.loadLevel(Dungeon.hero.heroClass);
                            } else {
                                level = Dungeon.newLevel();
                                Badges.validateDeathFromFire();
                            }
                            Dungeon.switchLevel(level, level.north);
                            break;
                        case SOUTH78:
                            south();
                            Level.DirectionValue = 78;
                            Dungeon.saveAll();
                            if (Statistics.Level78Visited) {
                                Dungeon.depth = Level.DirectionValue;
                                level = Dungeon.loadLevel(Dungeon.hero.heroClass);
                            } else {
                                level = Dungeon.newLevel();
                                Badges.validateDeathFromFire();
                            }
                            Dungeon.switchLevel(level, level.north);
                            break;
                        case SOUTH79:
                            south();
                            Level.DirectionValue = 79;
                            Dungeon.saveAll();
                            if (Statistics.Level79Visited) {
                                Dungeon.depth = Level.DirectionValue;
                                level = Dungeon.loadLevel(Dungeon.hero.heroClass);
                            } else {
                                level = Dungeon.newLevel();
                                Badges.validateDeathFromFire();
                            }
                            Dungeon.switchLevel(level, level.north);
                            break;
                        case SOUTH80:
                            south();
                            Level.DirectionValue = 80;
                            Dungeon.saveAll();
                            if (Statistics.Level80Visited) {
                                Dungeon.depth = Level.DirectionValue;
                                level = Dungeon.loadLevel(Dungeon.hero.heroClass);
                            } else {
                                level = Dungeon.newLevel();
                                Badges.validateDeathFromFire();
                            }
                            Dungeon.switchLevel(level, level.north);
                            break;
                        case SOUTH81:
                            south();
                            Level.DirectionValue = 81;
                            Dungeon.saveAll();
                            if (Statistics.Level81Visited) {
                                Dungeon.depth = Level.DirectionValue;
                                level = Dungeon.loadLevel(Dungeon.hero.heroClass);
                            } else {
                                level = Dungeon.newLevel();
                                Badges.validateDeathFromFire();
                            }
                            Dungeon.switchLevel(level, level.north);
                            break;
                        case SOUTH82:
                            south();
                            Level.DirectionValue = 82;
                            Dungeon.saveAll();
                            if (Statistics.Level82Visited) {
                                Dungeon.depth = Level.DirectionValue;
                                level = Dungeon.loadLevel(Dungeon.hero.heroClass);
                            } else {
                                level = Dungeon.newLevel();
                                Badges.validateDeathFromFire();
                            }
                            Dungeon.switchLevel(level, level.north);
                            break;
                        case SOUTH83:
                            south();
                            Level.DirectionValue = 83;
                            Dungeon.saveAll();
                            if (Statistics.Level83Visited) {
                                Dungeon.depth = Level.DirectionValue;
                                level = Dungeon.loadLevel(Dungeon.hero.heroClass);
                            } else {
                                level = Dungeon.newLevel();
                                Badges.validateDeathFromFire();
                            }
                            Dungeon.switchLevel(level, level.north);
                            break;
                        case SOUTH84:
                            south();
                            Level.DirectionValue = 84;
                            Dungeon.saveAll();
                            if (Statistics.Level84Visited) {
                                Dungeon.depth = Level.DirectionValue;
                                level = Dungeon.loadLevel(Dungeon.hero.heroClass);
                            } else {
                                level = Dungeon.newLevel();
                                Badges.validateDeathFromFire();
                            }
                            Dungeon.switchLevel(level, level.north);
                            break;
                        case SOUTH85:
                            south();
                            Level.DirectionValue = 85;
                            Dungeon.saveAll();
                            if (Statistics.Level85Visited) {
                                Dungeon.depth = Level.DirectionValue;
                                level = Dungeon.loadLevel(Dungeon.hero.heroClass);
                            } else {
                                level = Dungeon.newLevel();
                                Badges.validateDeathFromFire();
                            }
                            Dungeon.switchLevel(level, level.north);
                            break;
                        case SOUTH86:
                            south();
                            Level.DirectionValue = 86;
                            Dungeon.saveAll();
                            if (Statistics.Level86Visited) {
                                Dungeon.depth = Level.DirectionValue;
                                level = Dungeon.loadLevel(Dungeon.hero.heroClass);
                            } else {
                                level = Dungeon.newLevel();
                                Badges.validateDeathFromFire();
                            }
                            Dungeon.switchLevel(level, level.north);
                            break;
                        case SOUTH87:
                            south();
                            Level.DirectionValue = 87;
                            Dungeon.saveAll();
                            if (Statistics.Level87Visited) {
                                Dungeon.depth = Level.DirectionValue;
                                level = Dungeon.loadLevel(Dungeon.hero.heroClass);
                            } else {
                                level = Dungeon.newLevel();
                                Badges.validateDeathFromFire();
                            }
                            Dungeon.switchLevel(level, level.north);
                            break;
                        case SOUTH88:
                            south();
                            Level.DirectionValue = 88;
                            Dungeon.saveAll();
                            if (Statistics.Level88Visited) {
                                Dungeon.depth = Level.DirectionValue;
                                level = Dungeon.loadLevel(Dungeon.hero.heroClass);
                            } else {
                                level = Dungeon.newLevel();
                                Badges.validateDeathFromFire();
                            }
                            Dungeon.switchLevel(level, level.north);
                            break;
                        case SOUTH89:
                            south();
                            Level.DirectionValue = 89;
                            Dungeon.saveAll();
                            if (Statistics.Level89Visited) {
                                Dungeon.depth = Level.DirectionValue;
                                level = Dungeon.loadLevel(Dungeon.hero.heroClass);
                            } else {
                                level = Dungeon.newLevel();
                                Badges.validateDeathFromFire();
                            }
                            Dungeon.switchLevel(level, level.north);
                            break;
                        case SOUTH90:
                            south();
                            Level.DirectionValue = 90;
                            Dungeon.saveAll();
                            if (Statistics.Level20Visited) {
                                Dungeon.depth = Level.DirectionValue;
                                level = Dungeon.loadLevel(Dungeon.hero.heroClass);
                            } else {
                                level = Dungeon.newLevel();
                                Badges.validateDeathFromFire();
                            }
                            Dungeon.switchLevel(level, level.north);
                            break;
                        case EAST1:
                            east();
                            Level.DirectionValue = 10;
                            Dungeon.saveAll();
                            if (Statistics.Level10Visited) {
                                Dungeon.depth = Level.DirectionValue;
                                level = Dungeon.loadLevel(Dungeon.hero.heroClass);
                            } else {
                                level = Dungeon.newLevel();
                                Badges.validateDeathFromFire();
                            }
                            Dungeon.switchLevel(level, level.west);
                            break;
                        case EAST2:
                            east();
                            Level.DirectionValue = 1;
                            Dungeon.saveAll();
                            if (Statistics.Level1Visited) {
                                Dungeon.depth = Level.DirectionValue;
                                level = Dungeon.loadLevel(Dungeon.hero.heroClass);
                            } else {
                                level = Dungeon.newLevel();
                                Badges.validateDeathFromFire();
                            }
                            Dungeon.switchLevel(level, level.west);
                            break;
                        case EAST3:
                            east();
                            Level.DirectionValue = 2;
                            Dungeon.saveAll();
                            if (Statistics.Level2Visited) {
                                Dungeon.depth = Level.DirectionValue;
                                level = Dungeon.loadLevel(Dungeon.hero.heroClass);
                            } else {
                                level = Dungeon.newLevel();
                                Badges.validateDeathFromFire();
                            }
                            Dungeon.switchLevel(level, level.west);
                            break;
                        case EAST4:
                            east();
                            Level.DirectionValue = 3;
                            Dungeon.saveAll();
                            if (Statistics.Level3Visited) {
                                Dungeon.depth = Level.DirectionValue;
                                level = Dungeon.loadLevel(Dungeon.hero.heroClass);
                            } else {
                                level = Dungeon.newLevel();
                                Badges.validateDeathFromFire();
                            }
                            Dungeon.switchLevel(level, level.west);
                            break;
                        case EAST5:
                            east();
                            Level.DirectionValue = 4;
                            Dungeon.saveAll();
                            if (Statistics.Level4Visited) {
                                Dungeon.depth = Level.DirectionValue;
                                level = Dungeon.loadLevel(Dungeon.hero.heroClass);
                            } else {
                                level = Dungeon.newLevel();
                                Badges.validateDeathFromFire();
                            }
                            Dungeon.switchLevel(level, level.west);
                            break;
                        case EAST6:
                            east();
                            Level.DirectionValue = 5;
                            Dungeon.saveAll();
                            if (Statistics.Level5Visited) {
                                Dungeon.depth = Level.DirectionValue;
                                level = Dungeon.loadLevel(Dungeon.hero.heroClass);
                            } else {
                                level = Dungeon.newLevel();
                                Badges.validateDeathFromFire();
                            }
                            Dungeon.switchLevel(level, level.west);
                            break;
                        case EAST7:
                            east();
                            Level.DirectionValue = 6;
                            Dungeon.saveAll();
                            if (Statistics.Level6Visited) {
                                Dungeon.depth = Level.DirectionValue;
                                level = Dungeon.loadLevel(Dungeon.hero.heroClass);
                            } else {
                                level = Dungeon.newLevel();
                                Badges.validateDeathFromFire();
                            }
                            Dungeon.switchLevel(level, level.west);
                            break;
                        case EAST8:
                            east();
                            Level.DirectionValue = 7;
                            Dungeon.saveAll();
                            if (Statistics.Level7Visited) {
                                Dungeon.depth = Level.DirectionValue;
                                level = Dungeon.loadLevel(Dungeon.hero.heroClass);
                            } else {
                                level = Dungeon.newLevel();
                                Badges.validateDeathFromFire();
                            }
                            Dungeon.switchLevel(level, level.west);
                            break;
                        case EAST9:
                            east();
                            Level.DirectionValue = 8;
                            Dungeon.saveAll();
                            if (Statistics.Level8Visited) {
                                Dungeon.depth = Level.DirectionValue;
                                level = Dungeon.loadLevel(Dungeon.hero.heroClass);
                            } else {
                                level = Dungeon.newLevel();
                                Badges.validateDeathFromFire();
                            }
                            Dungeon.switchLevel(level, level.west);
                            break;
                        case EAST10:
                            east();
                            Level.DirectionValue = 9;
                            Dungeon.saveAll();
                            if (Statistics.Level9Visited) {
                                Dungeon.depth = Level.DirectionValue;
                                level = Dungeon.loadLevel(Dungeon.hero.heroClass);
                            } else {
                                level = Dungeon.newLevel();
                                Badges.validateDeathFromFire();
                            }
                            Dungeon.switchLevel(level, level.west);
                            break;
                        case EAST11:
                            east();
                            Level.DirectionValue = 10;
                            Dungeon.saveAll();
                            if (Statistics.Level10Visited) {
                                Dungeon.depth = Level.DirectionValue;
                                level = Dungeon.loadLevel(Dungeon.hero.heroClass);
                            } else {
                                level = Dungeon.newLevel();
                                Badges.validateDeathFromFire();
                            }
                            Dungeon.switchLevel(level, level.west);
                            break;
                        case EAST12:
                            east();
                            Level.DirectionValue = 11;
                            Dungeon.saveAll();
                            if (Statistics.Level11Visited) {
                                Dungeon.depth = Level.DirectionValue;
                                level = Dungeon.loadLevel(Dungeon.hero.heroClass);
                            } else {
                                level = Dungeon.newLevel();
                                Badges.validateDeathFromFire();
                            }
                            Dungeon.switchLevel(level, level.west);
                            break;
                        case EAST13:
                            east();
                            Level.DirectionValue = 12;
                            Dungeon.saveAll();
                            if (Statistics.Level12Visited) {
                                Dungeon.depth = Level.DirectionValue;
                                level = Dungeon.loadLevel(Dungeon.hero.heroClass);
                            } else {
                                level = Dungeon.newLevel();
                                Badges.validateDeathFromFire();
                            }
                            Dungeon.switchLevel(level, level.west);
                            break;
                        case EAST14:
                            east();
                            Level.DirectionValue = 13;
                            Dungeon.saveAll();
                            if (Statistics.Level13Visited) {
                                Dungeon.depth = Level.DirectionValue;
                                level = Dungeon.loadLevel(Dungeon.hero.heroClass);
                            } else {
                                level = Dungeon.newLevel();
                                Badges.validateDeathFromFire();
                            }
                            Dungeon.switchLevel(level, level.west);
                            break;
                        case EAST15:
                            east();
                            Level.DirectionValue = 14;
                            Dungeon.saveAll();
                            if (Statistics.Level14Visited) {
                                Dungeon.depth = Level.DirectionValue;
                                level = Dungeon.loadLevel(Dungeon.hero.heroClass);
                            } else {
                                level = Dungeon.newLevel();
                                Badges.validateDeathFromFire();
                            }
                            Dungeon.switchLevel(level, level.west);
                            break;
                        case EAST16:
                            east();
                            Level.DirectionValue = 15;
                            Dungeon.saveAll();
                            if (Statistics.Level15Visited) {
                                Dungeon.depth = Level.DirectionValue;
                                level = Dungeon.loadLevel(Dungeon.hero.heroClass);
                            } else {
                                level = Dungeon.newLevel();
                                Badges.validateDeathFromFire();
                            }
                            Dungeon.switchLevel(level, level.west);
                            break;
                        case EAST17:
                            east();
                            Level.DirectionValue = 16;
                            Dungeon.saveAll();
                            if (Statistics.Level16Visited) {
                                Dungeon.depth = Level.DirectionValue;
                                level = Dungeon.loadLevel(Dungeon.hero.heroClass);
                            } else {
                                level = Dungeon.newLevel();
                                Badges.validateDeathFromFire();
                            }
                            Dungeon.switchLevel(level, level.west);
                            break;
                        case EAST18:
                            east();
                        Level.DirectionValue = 17;
                        Dungeon.saveAll();
                        if (Statistics.Level17Visited) {
                            Dungeon.depth = Level.DirectionValue;
                            level = Dungeon.loadLevel(Dungeon.hero.heroClass);
                        } else {
                            level = Dungeon.newLevel();
                            Badges.validateDeathFromFire();
                        }
                        Dungeon.switchLevel(level, level.west);
                        break;
                        case EAST19:
                            east();
                            Level.DirectionValue = 18;
                            Dungeon.saveAll();
                            if (Statistics.Level18Visited) {
                                Dungeon.depth = Level.DirectionValue;
                                level = Dungeon.loadLevel(Dungeon.hero.heroClass);
                            } else {
                                level = Dungeon.newLevel();
                                Badges.validateDeathFromFire();
                            }
                            Dungeon.switchLevel(level, level.west);
                            break;
                        case EAST20:
                            east();
                            Level.DirectionValue = 19;
                            Dungeon.saveAll();
                            if (Statistics.Level19Visited) {
                                Dungeon.depth = Level.DirectionValue;
                                level = Dungeon.loadLevel(Dungeon.hero.heroClass);
                            } else {
                                level = Dungeon.newLevel();
                                Badges.validateDeathFromFire();
                            }
                            Dungeon.switchLevel(level, level.west);
                            break;
                        case EAST21:
                            east();
                            Level.DirectionValue = 20;
                            Dungeon.saveAll();
                            if (Statistics.Level20Visited) {
                                Dungeon.depth = Level.DirectionValue;
                                level = Dungeon.loadLevel(Dungeon.hero.heroClass);
                            } else {
                                level = Dungeon.newLevel();
                                Badges.validateDeathFromFire();
                            }
                            Dungeon.switchLevel(level, level.west);
                            break;
                        case EAST22:
                            east();
                            Level.DirectionValue = 21;
                            Dungeon.saveAll();
                            if (Statistics.Level21Visited) {
                                Dungeon.depth = Level.DirectionValue;
                                level = Dungeon.loadLevel(Dungeon.hero.heroClass);
                            } else {
                                level = Dungeon.newLevel();
                                Badges.validateDeathFromFire();
                            }
                            Dungeon.switchLevel(level, level.west);
                            break;
                        case EAST23:
                            east();
                            Level.DirectionValue = 22;
                            Dungeon.saveAll();
                            if (Statistics.Level22Visited) {
                                Dungeon.depth = Level.DirectionValue;
                                level = Dungeon.loadLevel(Dungeon.hero.heroClass);
                            } else {
                                level = Dungeon.newLevel();
                                Badges.validateDeathFromFire();
                            }
                            Dungeon.switchLevel(level, level.west);
                            break;
                        case EAST24:
                            east();
                            Level.DirectionValue = 23;
                            Dungeon.saveAll();
                            if (Statistics.Level23Visited) {
                                Dungeon.depth = Level.DirectionValue;
                                level = Dungeon.loadLevel(Dungeon.hero.heroClass);
                            } else {
                                level = Dungeon.newLevel();
                                Badges.validateDeathFromFire();
                            }
                            Dungeon.switchLevel(level, level.west);
                            break;
                        case EAST25:
                            east();
                            Level.DirectionValue = 24;
                            Dungeon.saveAll();
                            if (Statistics.Level24Visited) {
                                Dungeon.depth = Level.DirectionValue;
                                level = Dungeon.loadLevel(Dungeon.hero.heroClass);
                            } else {
                                level = Dungeon.newLevel();
                                Badges.validateDeathFromFire();
                            }
                            Dungeon.switchLevel(level, level.west);
                            break;
                        case EAST26:
                            east();
                            Level.DirectionValue = 25;
                            Dungeon.saveAll();
                            if (Statistics.Level25Visited) {
                                Dungeon.depth = Level.DirectionValue;
                                level = Dungeon.loadLevel(Dungeon.hero.heroClass);
                            } else {
                                level = Dungeon.newLevel();
                                Badges.validateDeathFromFire();
                            }
                            Dungeon.switchLevel(level, level.west);
                            break;
                        case EAST27:
                            east();
                            Level.DirectionValue = 26;
                            Dungeon.saveAll();
                            if (Statistics.Level26Visited) {
                                Dungeon.depth = Level.DirectionValue;
                                level = Dungeon.loadLevel(Dungeon.hero.heroClass);
                            } else {
                                level = Dungeon.newLevel();
                                Badges.validateDeathFromFire();
                            }
                            Dungeon.switchLevel(level, level.west);
                            break;
                        case EAST28:
                            east();
                            Level.DirectionValue = 27;
                            Dungeon.saveAll();
                            if (Statistics.Level27Visited) {
                                Dungeon.depth = Level.DirectionValue;
                                level = Dungeon.loadLevel(Dungeon.hero.heroClass);
                            } else {
                                level = Dungeon.newLevel();
                                Badges.validateDeathFromFire();
                            }
                            Dungeon.switchLevel(level, level.west);
                            break;
                        case EAST29:
                            east();
                            Level.DirectionValue = 28;
                            Dungeon.saveAll();
                            if (Statistics.Level28Visited) {
                                Dungeon.depth = Level.DirectionValue;
                                level = Dungeon.loadLevel(Dungeon.hero.heroClass);
                            } else {
                                level = Dungeon.newLevel();
                                Badges.validateDeathFromFire();
                            }
                            Dungeon.switchLevel(level, level.west);
                            break;
                        case EAST30:
                            east();
                            Level.DirectionValue = 29;
                            Dungeon.saveAll();
                            if (Statistics.Level29Visited) {
                                Dungeon.depth = Level.DirectionValue;
                                level = Dungeon.loadLevel(Dungeon.hero.heroClass);
                            } else {
                                level = Dungeon.newLevel();
                                Badges.validateDeathFromFire();
                            }
                            Dungeon.switchLevel(level, level.west);
                            break;
                        case EAST31:
                            east();
                            Level.DirectionValue = 30;
                            Dungeon.saveAll();
                            if (Statistics.Level30Visited) {
                                Dungeon.depth = Level.DirectionValue;
                                level = Dungeon.loadLevel(Dungeon.hero.heroClass);
                            } else {
                                level = Dungeon.newLevel();
                                Badges.validateDeathFromFire();
                            }
                            Dungeon.switchLevel(level, level.west);
                            break;
                        case EAST32:
                            east();
                            Level.DirectionValue = 31;
                            Dungeon.saveAll();
                            if (Statistics.Level31Visited) {
                                Dungeon.depth = Level.DirectionValue;
                                level = Dungeon.loadLevel(Dungeon.hero.heroClass);
                            } else {
                                level = Dungeon.newLevel();
                                Badges.validateDeathFromFire();
                            }
                            Dungeon.switchLevel(level, level.west);
                            break;
                        case EAST33:
                            east();
                            Level.DirectionValue = 32;
                            Dungeon.saveAll();
                            if (Statistics.Level32Visited) {
                                Dungeon.depth = Level.DirectionValue;
                                level = Dungeon.loadLevel(Dungeon.hero.heroClass);
                            } else {
                                level = Dungeon.newLevel();
                                Badges.validateDeathFromFire();
                            }
                            Dungeon.switchLevel(level, level.west);
                            break;
                        case EAST34:
                            east();
                            Level.DirectionValue = 33;
                            Dungeon.saveAll();
                            if (Statistics.Level33Visited) {
                                Dungeon.depth = Level.DirectionValue;
                                level = Dungeon.loadLevel(Dungeon.hero.heroClass);
                            } else {
                                level = Dungeon.newLevel();
                                Badges.validateDeathFromFire();
                            }
                            Dungeon.switchLevel(level, level.west);
                            break;
                        case EAST35:
                            east();
                            Level.DirectionValue = 34;
                            Dungeon.saveAll();
                            if (Statistics.Level34Visited) {
                                Dungeon.depth = Level.DirectionValue;
                                level = Dungeon.loadLevel(Dungeon.hero.heroClass);
                            } else {
                                level = Dungeon.newLevel();
                                Badges.validateDeathFromFire();
                            }
                            Dungeon.switchLevel(level, level.west);
                            break;
                        case EAST36:
                            east();
                            Level.DirectionValue = 35;
                            Dungeon.saveAll();
                            if (Statistics.Level35Visited) {
                                Dungeon.depth = Level.DirectionValue;
                                level = Dungeon.loadLevel(Dungeon.hero.heroClass);
                            } else {
                                level = Dungeon.newLevel();
                                Badges.validateDeathFromFire();
                            }
                            Dungeon.switchLevel(level, level.west);
                            break;
                        case EAST37:
                            east();
                            Level.DirectionValue = 36;
                            Dungeon.saveAll();
                            if (Statistics.Level36Visited) {
                                Dungeon.depth = Level.DirectionValue;
                                level = Dungeon.loadLevel(Dungeon.hero.heroClass);
                            } else {
                                level = Dungeon.newLevel();
                                Badges.validateDeathFromFire();
                            }
                            Dungeon.switchLevel(level, level.west);
                            break;
                        case EAST38:
                            east();
                            Level.DirectionValue = 37;
                            Dungeon.saveAll();
                            if (Statistics.Level37Visited) {
                                Dungeon.depth = Level.DirectionValue;
                                level = Dungeon.loadLevel(Dungeon.hero.heroClass);
                            } else {
                                level = Dungeon.newLevel();
                                Badges.validateDeathFromFire();
                            }
                            Dungeon.switchLevel(level, level.west);
                            break;
                        case EAST39:
                            east();
                            Level.DirectionValue = 38;
                            Dungeon.saveAll();
                            if (Statistics.Level38Visited) {
                                Dungeon.depth = Level.DirectionValue;
                                level = Dungeon.loadLevel(Dungeon.hero.heroClass);
                            } else {
                                level = Dungeon.newLevel();
                                Badges.validateDeathFromFire();
                            }
                            Dungeon.switchLevel(level, level.west);
                            break;
                        case EAST40:
                            east();
                            Level.DirectionValue = 39;
                            Dungeon.saveAll();
                            if (Statistics.Level39Visited) {
                                Dungeon.depth = Level.DirectionValue;
                                level = Dungeon.loadLevel(Dungeon.hero.heroClass);
                            } else {
                                level = Dungeon.newLevel();
                                Badges.validateDeathFromFire();
                            }
                            Dungeon.switchLevel(level, level.west);
                            break;
                        case EAST41:
                            east();
                            Level.DirectionValue = 40;
                            Dungeon.saveAll();
                            if (Statistics.Level40Visited) {
                                Dungeon.depth = Level.DirectionValue;
                                level = Dungeon.loadLevel(Dungeon.hero.heroClass);
                            } else {
                                level = Dungeon.newLevel();
                                Badges.validateDeathFromFire();
                            }
                            Dungeon.switchLevel(level, level.west);
                            break;
                        case EAST42:
                            east();
                            Level.DirectionValue = 41;
                            Dungeon.saveAll();
                            if (Statistics.Level41Visited) {
                                Dungeon.depth = Level.DirectionValue;
                                level = Dungeon.loadLevel(Dungeon.hero.heroClass);
                            } else {
                                level = Dungeon.newLevel();
                                Badges.validateDeathFromFire();
                            }
                            Dungeon.switchLevel(level, level.west);
                            break;
                        case EAST43:
                            east();
                            Level.DirectionValue = 42;
                            Dungeon.saveAll();
                            if (Statistics.Level42Visited) {
                                Dungeon.depth = Level.DirectionValue;
                                level = Dungeon.loadLevel(Dungeon.hero.heroClass);
                            } else {
                                level = Dungeon.newLevel();
                                Badges.validateDeathFromFire();
                            }
                            Dungeon.switchLevel(level, level.west);
                            break;
                        case EAST44:
                            east();
                            Level.DirectionValue = 43;
                            Dungeon.saveAll();
                            if (Statistics.Level43Visited) {
                                Dungeon.depth = Level.DirectionValue;
                                level = Dungeon.loadLevel(Dungeon.hero.heroClass);
                            } else {
                                level = Dungeon.newLevel();
                                Badges.validateDeathFromFire();
                            }
                            Dungeon.switchLevel(level, level.west);
                            break;
                        case EAST45:
                            east();
                            Level.DirectionValue = 44;
                            Dungeon.saveAll();
                            if (Statistics.Level44Visited) {
                                Dungeon.depth = Level.DirectionValue;
                                level = Dungeon.loadLevel(Dungeon.hero.heroClass);
                            } else {
                                level = Dungeon.newLevel();
                                Badges.validateDeathFromFire();
                            }
                            Dungeon.switchLevel(level, level.west);
                            break;
                        case EAST46:
                            east();
                            Level.DirectionValue = 45;
                            Dungeon.saveAll();
                            if (Statistics.Level45Visited) {
                                Dungeon.depth = Level.DirectionValue;
                                level = Dungeon.loadLevel(Dungeon.hero.heroClass);
                            } else {
                                level = Dungeon.newLevel();
                                Badges.validateDeathFromFire();
                            }
                            Dungeon.switchLevel(level, level.west);
                            break;
                        case EAST47:
                            east();
                            Level.DirectionValue = 46;
                            Dungeon.saveAll();
                            if (Statistics.Level46Visited) {
                                Dungeon.depth = Level.DirectionValue;
                                level = Dungeon.loadLevel(Dungeon.hero.heroClass);
                            } else {
                                level = Dungeon.newLevel();
                                Badges.validateDeathFromFire();
                            }
                            Dungeon.switchLevel(level, level.west);
                            break;
                        case EAST48:
                            east();
                            Level.DirectionValue = 47;
                            Dungeon.saveAll();
                            if (Statistics.Level47Visited) {
                                Dungeon.depth = Level.DirectionValue;
                                level = Dungeon.loadLevel(Dungeon.hero.heroClass);
                            } else {
                                level = Dungeon.newLevel();
                                Badges.validateDeathFromFire();
                            }
                            Dungeon.switchLevel(level, level.west);
                            break;
                        case EAST49:
                            east();
                            Level.DirectionValue = 48;
                            Dungeon.saveAll();
                            if (Statistics.Level48Visited) {
                                Dungeon.depth = Level.DirectionValue;
                                level = Dungeon.loadLevel(Dungeon.hero.heroClass);
                            } else {
                                level = Dungeon.newLevel();
                                Badges.validateDeathFromFire();
                            }
                            Dungeon.switchLevel(level, level.west);
                            break;
                        case EAST50:
                            east();
                            Level.DirectionValue = 49;
                            Dungeon.saveAll();
                            if (Statistics.Level49Visited) {
                                Dungeon.depth = Level.DirectionValue;
                                level = Dungeon.loadLevel(Dungeon.hero.heroClass);
                            } else {
                                level = Dungeon.newLevel();
                                Badges.validateDeathFromFire();
                            }
                            Dungeon.switchLevel(level, level.west);
                            break;
                        case EAST51:
                            east();
                            Level.DirectionValue = 50;
                            Dungeon.saveAll();
                            if (Statistics.Level50Visited) {
                                Dungeon.depth = Level.DirectionValue;
                                level = Dungeon.loadLevel(Dungeon.hero.heroClass);
                            } else {
                                level = Dungeon.newLevel();
                                Badges.validateDeathFromFire();
                            }
                            Dungeon.switchLevel(level, level.west);
                            break;
                        case EAST52:
                            east();
                            Level.DirectionValue = 51;
                            Dungeon.saveAll();
                            if (Statistics.Level51Visited) {
                                Dungeon.depth = Level.DirectionValue;
                                level = Dungeon.loadLevel(Dungeon.hero.heroClass);
                            } else {
                                level = Dungeon.newLevel();
                                Badges.validateDeathFromFire();
                            }
                            Dungeon.switchLevel(level, level.west);
                            break;
                        case EAST53:
                            east();
                            Level.DirectionValue = 52;
                            Dungeon.saveAll();
                            if (Statistics.Level52Visited) {
                                Dungeon.depth = Level.DirectionValue;
                                level = Dungeon.loadLevel(Dungeon.hero.heroClass);
                            } else {
                                level = Dungeon.newLevel();
                                Badges.validateDeathFromFire();
                            }
                            Dungeon.switchLevel(level, level.west);
                            break;
                        case EAST54:
                            east();
                            Level.DirectionValue = 53;
                            Dungeon.saveAll();
                            if (Statistics.Level53Visited) {
                                Dungeon.depth = Level.DirectionValue;
                                level = Dungeon.loadLevel(Dungeon.hero.heroClass);
                            } else {
                                level = Dungeon.newLevel();
                                Badges.validateDeathFromFire();
                            }
                            Dungeon.switchLevel(level, level.west);
                            break;
                        case EAST55:
                            east();
                            Level.DirectionValue = 54;
                            Dungeon.saveAll();
                            if (Statistics.Level54Visited) {
                                Dungeon.depth = Level.DirectionValue;
                                level = Dungeon.loadLevel(Dungeon.hero.heroClass);
                            } else {
                                level = Dungeon.newLevel();
                                Badges.validateDeathFromFire();
                            }
                            Dungeon.switchLevel(level, level.west);
                            break;
                        case EAST56:
                            east();
                            Level.DirectionValue = 55;
                            Dungeon.saveAll();
                            if (Statistics.Level55Visited) {
                                Dungeon.depth = Level.DirectionValue;
                                level = Dungeon.loadLevel(Dungeon.hero.heroClass);
                            } else {
                                level = Dungeon.newLevel();
                                Badges.validateDeathFromFire();
                            }
                            Dungeon.switchLevel(level, level.west);
                            break;
                        case EAST57:
                            east();
                            Level.DirectionValue = 56;
                            Dungeon.saveAll();
                            if (Statistics.Level56Visited) {
                                Dungeon.depth = Level.DirectionValue;
                                level = Dungeon.loadLevel(Dungeon.hero.heroClass);
                            } else {
                                level = Dungeon.newLevel();
                                Badges.validateDeathFromFire();
                            }
                            Dungeon.switchLevel(level, level.west);
                            break;
                        case EAST58:
                            east();
                            Level.DirectionValue = 57;
                            Dungeon.saveAll();
                            if (Statistics.Level57Visited) {
                                Dungeon.depth = Level.DirectionValue;
                                level = Dungeon.loadLevel(Dungeon.hero.heroClass);
                            } else {
                                level = Dungeon.newLevel();
                                Badges.validateDeathFromFire();
                            }
                            Dungeon.switchLevel(level, level.west);
                            break;
                        case EAST59:
                            east();
                            Level.DirectionValue = 58;
                            Dungeon.saveAll();
                            if (Statistics.Level58Visited) {
                                Dungeon.depth = Level.DirectionValue;
                                level = Dungeon.loadLevel(Dungeon.hero.heroClass);
                            } else {
                                level = Dungeon.newLevel();
                                Badges.validateDeathFromFire();
                            }
                            Dungeon.switchLevel(level, level.west);
                            break;
                        case EAST60:
                            east();
                            Level.DirectionValue = 59;
                            Dungeon.saveAll();
                            if (Statistics.Level59Visited) {
                                Dungeon.depth = Level.DirectionValue;
                                level = Dungeon.loadLevel(Dungeon.hero.heroClass);
                            } else {
                                level = Dungeon.newLevel();
                                Badges.validateDeathFromFire();
                            }
                            Dungeon.switchLevel(level, level.west);
                            break;
                        case EAST61:
                            east();
                            Level.DirectionValue = 60;
                            Dungeon.saveAll();
                            if (Statistics.Level60Visited) {
                                Dungeon.depth = Level.DirectionValue;
                                level = Dungeon.loadLevel(Dungeon.hero.heroClass);
                            } else {
                                level = Dungeon.newLevel();
                                Badges.validateDeathFromFire();
                            }
                            Dungeon.switchLevel(level, level.west);
                            break;
                        case EAST62:
                            east();
                            Level.DirectionValue = 61;
                            Dungeon.saveAll();
                            if (Statistics.Level61Visited) {
                                Dungeon.depth = Level.DirectionValue;
                                level = Dungeon.loadLevel(Dungeon.hero.heroClass);
                            } else {
                                level = Dungeon.newLevel();
                                Badges.validateDeathFromFire();
                            }
                            Dungeon.switchLevel(level, level.west);
                            break;
                        case EAST63:
                            east();
                            Level.DirectionValue = 62;
                            Dungeon.saveAll();
                            if (Statistics.Level62Visited) {
                                Dungeon.depth = Level.DirectionValue;
                                level = Dungeon.loadLevel(Dungeon.hero.heroClass);
                            } else {
                                level = Dungeon.newLevel();
                                Badges.validateDeathFromFire();
                            }
                            Dungeon.switchLevel(level, level.west);
                            break;
                        case EAST64:
                            east();
                            Level.DirectionValue = 63;
                            Dungeon.saveAll();
                            if (Statistics.Level63Visited) {
                                Dungeon.depth = Level.DirectionValue;
                                level = Dungeon.loadLevel(Dungeon.hero.heroClass);
                            } else {
                                level = Dungeon.newLevel();
                                Badges.validateDeathFromFire();
                            }
                            Dungeon.switchLevel(level, level.west);
                            break;
                        case EAST65:
                            east();
                            Level.DirectionValue = 64;
                            Dungeon.saveAll();
                            if (Statistics.Level64Visited) {
                                Dungeon.depth = Level.DirectionValue;
                                level = Dungeon.loadLevel(Dungeon.hero.heroClass);
                            } else {
                                level = Dungeon.newLevel();
                                Badges.validateDeathFromFire();
                            }
                            Dungeon.switchLevel(level, level.west);
                            break;
                        case EAST66:
                            east();
                            Level.DirectionValue = 65;
                            Dungeon.saveAll();
                            if (Statistics.Level65Visited) {
                                Dungeon.depth = Level.DirectionValue;
                                level = Dungeon.loadLevel(Dungeon.hero.heroClass);
                            } else {
                                level = Dungeon.newLevel();
                                Badges.validateDeathFromFire();
                            }
                            Dungeon.switchLevel(level, level.west);
                            break;
                        case EAST67:
                            east();
                            Level.DirectionValue = 66;
                            Dungeon.saveAll();
                            if (Statistics.Level66Visited) {
                                Dungeon.depth = Level.DirectionValue;
                                level = Dungeon.loadLevel(Dungeon.hero.heroClass);
                            } else {
                                level = Dungeon.newLevel();
                                Badges.validateDeathFromFire();
                            }
                            Dungeon.switchLevel(level, level.west);
                            break;
                        case EAST68:
                            east();
                            Level.DirectionValue = 67;
                            Dungeon.saveAll();
                            if (Statistics.Level67Visited) {
                                Dungeon.depth = Level.DirectionValue;
                                level = Dungeon.loadLevel(Dungeon.hero.heroClass);
                            } else {
                                level = Dungeon.newLevel();
                                Badges.validateDeathFromFire();
                            }
                            Dungeon.switchLevel(level, level.west);
                            break;
                        case EAST69:
                            east();
                            Level.DirectionValue = 68;
                            Dungeon.saveAll();
                            if (Statistics.Level68Visited) {
                                Dungeon.depth = Level.DirectionValue;
                                level = Dungeon.loadLevel(Dungeon.hero.heroClass);
                            } else {
                                level = Dungeon.newLevel();
                                Badges.validateDeathFromFire();
                            }
                            Dungeon.switchLevel(level, level.west);
                            break;
                        case EAST70:
                            east();
                            Level.DirectionValue = 69;
                            Dungeon.saveAll();
                            if (Statistics.Level69Visited) {
                                Dungeon.depth = Level.DirectionValue;
                                level = Dungeon.loadLevel(Dungeon.hero.heroClass);
                            } else {
                                level = Dungeon.newLevel();
                                Badges.validateDeathFromFire();
                            }
                            Dungeon.switchLevel(level, level.west);
                            break;
                        case EAST71:
                            east();
                            Level.DirectionValue = 70;
                            Dungeon.saveAll();
                            if (Statistics.Level70Visited) {
                                Dungeon.depth = Level.DirectionValue;
                                level = Dungeon.loadLevel(Dungeon.hero.heroClass);
                            } else {
                                level = Dungeon.newLevel();
                                Badges.validateDeathFromFire();
                            }
                            Dungeon.switchLevel(level, level.west);
                            break;
                        case EAST72:
                            east();
                            Level.DirectionValue = 71;
                            Dungeon.saveAll();
                            if (Statistics.Level71Visited) {
                                Dungeon.depth = Level.DirectionValue;
                                level = Dungeon.loadLevel(Dungeon.hero.heroClass);
                            } else {
                                level = Dungeon.newLevel();
                                Badges.validateDeathFromFire();
                            }
                            Dungeon.switchLevel(level, level.west);
                            break;
                        case EAST73:
                            east();
                            Level.DirectionValue = 72;
                            Dungeon.saveAll();
                            if (Statistics.Level72Visited) {
                                Dungeon.depth = Level.DirectionValue;
                                level = Dungeon.loadLevel(Dungeon.hero.heroClass);
                            } else {
                                level = Dungeon.newLevel();
                                Badges.validateDeathFromFire();
                            }
                            Dungeon.switchLevel(level, level.west);
                            break;
                        case EAST74:
                            east();
                            Level.DirectionValue = 73;
                            Dungeon.saveAll();
                            if (Statistics.Level73Visited) {
                                Dungeon.depth = Level.DirectionValue;
                                level = Dungeon.loadLevel(Dungeon.hero.heroClass);
                            } else {
                                level = Dungeon.newLevel();
                                Badges.validateDeathFromFire();
                            }
                            Dungeon.switchLevel(level, level.west);
                            break;
                        case EAST75:
                            east();
                            Level.DirectionValue = 74;
                            Dungeon.saveAll();
                            if (Statistics.Level74Visited) {
                                Dungeon.depth = Level.DirectionValue;
                                level = Dungeon.loadLevel(Dungeon.hero.heroClass);
                            } else {
                                level = Dungeon.newLevel();
                                Badges.validateDeathFromFire();
                            }
                            Dungeon.switchLevel(level, level.west);
                            break;
                        case EAST76:
                            east();
                            Level.DirectionValue = 75;
                            Dungeon.saveAll();
                            if (Statistics.Level75Visited) {
                                Dungeon.depth = Level.DirectionValue;
                                level = Dungeon.loadLevel(Dungeon.hero.heroClass);
                            } else {
                                level = Dungeon.newLevel();
                                Badges.validateDeathFromFire();
                            }
                            Dungeon.switchLevel(level, level.west);
                            break;
                        case EAST77:
                            east();
                            Level.DirectionValue = 76;
                            Dungeon.saveAll();
                            if (Statistics.Level76Visited) {
                                Dungeon.depth = Level.DirectionValue;
                                level = Dungeon.loadLevel(Dungeon.hero.heroClass);
                            } else {
                                level = Dungeon.newLevel();
                                Badges.validateDeathFromFire();
                            }
                            Dungeon.switchLevel(level, level.west);
                            break;
                        case EAST78:
                            east();
                            Level.DirectionValue = 77;
                            Dungeon.saveAll();
                            if (Statistics.Level77Visited) {
                                Dungeon.depth = Level.DirectionValue;
                                level = Dungeon.loadLevel(Dungeon.hero.heroClass);
                            } else {
                                level = Dungeon.newLevel();
                                Badges.validateDeathFromFire();
                            }
                            Dungeon.switchLevel(level, level.west);
                            break;
                        case EAST79:
                            east();
                            Level.DirectionValue = 78;
                            Dungeon.saveAll();
                            if (Statistics.Level78Visited) {
                                Dungeon.depth = Level.DirectionValue;
                                level = Dungeon.loadLevel(Dungeon.hero.heroClass);
                            } else {
                                level = Dungeon.newLevel();
                                Badges.validateDeathFromFire();
                            }
                            Dungeon.switchLevel(level, level.west);
                            break;
                        case EAST80:
                            east();
                            Level.DirectionValue = 79;
                            Dungeon.saveAll();
                            if (Statistics.Level79Visited) {
                                Dungeon.depth = Level.DirectionValue;
                                level = Dungeon.loadLevel(Dungeon.hero.heroClass);
                            } else {
                                level = Dungeon.newLevel();
                                Badges.validateDeathFromFire();
                            }
                            Dungeon.switchLevel(level, level.west);
                            break;
                        case EAST81:
                            east();
                            Level.DirectionValue = 80;
                            Dungeon.saveAll();
                            if (Statistics.Level80Visited) {
                                Dungeon.depth = Level.DirectionValue;
                                level = Dungeon.loadLevel(Dungeon.hero.heroClass);
                            } else {
                                level = Dungeon.newLevel();
                                Badges.validateDeathFromFire();
                            }
                            Dungeon.switchLevel(level, level.west);
                            break;
                        case EAST82:
                            east();
                            Level.DirectionValue = 81;
                            Dungeon.saveAll();
                            if (Statistics.Level81Visited) {
                                Dungeon.depth = Level.DirectionValue;
                                level = Dungeon.loadLevel(Dungeon.hero.heroClass);
                            } else {
                                level = Dungeon.newLevel();
                                Badges.validateDeathFromFire();
                            }
                            Dungeon.switchLevel(level, level.west);
                            break;
                        case EAST83:
                            east();
                            Level.DirectionValue = 82;
                            Dungeon.saveAll();
                            if (Statistics.Level82Visited) {
                                Dungeon.depth = Level.DirectionValue;
                                level = Dungeon.loadLevel(Dungeon.hero.heroClass);
                            } else {
                                level = Dungeon.newLevel();
                                Badges.validateDeathFromFire();
                            }
                            Dungeon.switchLevel(level, level.west);
                            break;
                        case EAST84:
                            east();
                            Level.DirectionValue = 83;
                            Dungeon.saveAll();
                            if (Statistics.Level83Visited) {
                                Dungeon.depth = Level.DirectionValue;
                                level = Dungeon.loadLevel(Dungeon.hero.heroClass);
                            } else {
                                level = Dungeon.newLevel();
                                Badges.validateDeathFromFire();
                            }
                            Dungeon.switchLevel(level, level.west);
                            break;
                        case EAST85:
                            east();
                            Level.DirectionValue = 84;
                            Dungeon.saveAll();
                            if (Statistics.Level84Visited) {
                                Dungeon.depth = Level.DirectionValue;
                                level = Dungeon.loadLevel(Dungeon.hero.heroClass);
                            } else {
                                level = Dungeon.newLevel();
                                Badges.validateDeathFromFire();
                            }
                            Dungeon.switchLevel(level, level.west);
                            break;
                        case EAST86:
                            east();
                            Level.DirectionValue = 85;
                            Dungeon.saveAll();
                            if (Statistics.Level85Visited) {
                                Dungeon.depth = Level.DirectionValue;
                                level = Dungeon.loadLevel(Dungeon.hero.heroClass);
                            } else {
                                level = Dungeon.newLevel();
                                Badges.validateDeathFromFire();
                            }
                            Dungeon.switchLevel(level, level.west);
                            break;
                        case EAST87:
                            east();
                            Level.DirectionValue = 86;
                            Dungeon.saveAll();
                            if (Statistics.Level86Visited) {
                                Dungeon.depth = Level.DirectionValue;
                                level = Dungeon.loadLevel(Dungeon.hero.heroClass);
                            } else {
                                level = Dungeon.newLevel();
                                Badges.validateDeathFromFire();
                            }
                            Dungeon.switchLevel(level, level.west);
                            break;
                        case EAST88:
                            east();
                            Level.DirectionValue = 87;
                            Dungeon.saveAll();
                            if (Statistics.Level87Visited) {
                                Dungeon.depth = Level.DirectionValue;
                                level = Dungeon.loadLevel(Dungeon.hero.heroClass);
                            } else {
                                level = Dungeon.newLevel();
                                Badges.validateDeathFromFire();
                            }
                            Dungeon.switchLevel(level, level.west);
                            break;
                        case EAST89:
                            east();
                            Level.DirectionValue = 88;
                            Dungeon.saveAll();
                            if (Statistics.Level88Visited) {
                                Dungeon.depth = Level.DirectionValue;
                                level = Dungeon.loadLevel(Dungeon.hero.heroClass);
                            } else {
                                level = Dungeon.newLevel();
                                Badges.validateDeathFromFire();
                            }
                            Dungeon.switchLevel(level, level.west);
                            break;
                        case EAST90:
                            east();
                            Level.DirectionValue = 89;
                            Dungeon.saveAll();
                            if (Statistics.Level89Visited) {
                                Dungeon.depth = Level.DirectionValue;
                                level = Dungeon.loadLevel(Dungeon.hero.heroClass);
                            } else {
                                level = Dungeon.newLevel();
                                Badges.validateDeathFromFire();
                            }
                            Dungeon.switchLevel(level, level.west);
                            break;
                        case EAST91:
                            east();
                            Level.DirectionValue = 90;
                            Dungeon.saveAll();
                            if (Statistics.Level90Visited) {
                                Dungeon.depth = Level.DirectionValue;
                                level = Dungeon.loadLevel(Dungeon.hero.heroClass);
                            } else {
                                level = Dungeon.newLevel();
                                Badges.validateDeathFromFire();
                            }
                            Dungeon.switchLevel(level, level.west);
                            break;
                        case EAST92:
                            east();
                            Level.DirectionValue = 91;
                            Dungeon.saveAll();
                            if (Statistics.Level91Visited) {
                                Dungeon.depth = Level.DirectionValue;
                                level = Dungeon.loadLevel(Dungeon.hero.heroClass);
                            } else {
                                level = Dungeon.newLevel();
                                Badges.validateDeathFromFire();
                            }
                            Dungeon.switchLevel(level, level.west);
                            break;
                        case EAST93:
                            east();
                            Level.DirectionValue = 92;
                            Dungeon.saveAll();
                            if (Statistics.Level92Visited) {
                                Dungeon.depth = Level.DirectionValue;
                                level = Dungeon.loadLevel(Dungeon.hero.heroClass);
                            } else {
                                level = Dungeon.newLevel();
                                Badges.validateDeathFromFire();
                            }
                            Dungeon.switchLevel(level, level.west);
                            break;
                        case EAST94:
                            east();
                            Level.DirectionValue = 93;
                            Dungeon.saveAll();
                            if (Statistics.Level93Visited) {
                                Dungeon.depth = Level.DirectionValue;
                                level = Dungeon.loadLevel(Dungeon.hero.heroClass);
                            } else {
                                level = Dungeon.newLevel();
                                Badges.validateDeathFromFire();
                            }
                            Dungeon.switchLevel(level, level.west);
                            break;
                        case EAST95:
                            east();
                            Level.DirectionValue = 94;
                            Dungeon.saveAll();
                            if (Statistics.Level94Visited) {
                                Dungeon.depth = Level.DirectionValue;
                                level = Dungeon.loadLevel(Dungeon.hero.heroClass);
                            } else {
                                level = Dungeon.newLevel();
                                Badges.validateDeathFromFire();
                            }
                            Dungeon.switchLevel(level, level.west);
                            break;
                        case EAST96:
                            east();
                            Level.DirectionValue = 95;
                            Dungeon.saveAll();
                            if (Statistics.Level95Visited) {
                                Dungeon.depth = Level.DirectionValue;
                                level = Dungeon.loadLevel(Dungeon.hero.heroClass);
                            } else {
                                level = Dungeon.newLevel();
                                Badges.validateDeathFromFire();
                            }
                            Dungeon.switchLevel(level, level.west);
                            break;
                        case EAST97:
                            east();
                            Level.DirectionValue = 96;
                            Dungeon.saveAll();
                            if (Statistics.Level96Visited) {
                                Dungeon.depth = Level.DirectionValue;
                                level = Dungeon.loadLevel(Dungeon.hero.heroClass);
                            } else {
                                level = Dungeon.newLevel();
                                Badges.validateDeathFromFire();
                            }
                            Dungeon.switchLevel(level, level.west);
                            break;
                        case EAST98:
                            east();
                            Level.DirectionValue = 97;
                            Dungeon.saveAll();
                            if (Statistics.Level97Visited) {
                                Dungeon.depth = Level.DirectionValue;
                                level = Dungeon.loadLevel(Dungeon.hero.heroClass);
                            } else {
                                level = Dungeon.newLevel();
                                Badges.validateDeathFromFire();
                            }
                            Dungeon.switchLevel(level, level.west);
                            break;
                        case EAST99:
                            east();
                            Level.DirectionValue = 98;
                            Dungeon.saveAll();
                            if (Statistics.Level98Visited) {
                                Dungeon.depth = Level.DirectionValue;
                                level = Dungeon.loadLevel(Dungeon.hero.heroClass);
                            } else {
                                level = Dungeon.newLevel();
                                Badges.validateDeathFromFire();
                            }
                            Dungeon.switchLevel(level, level.west);
                            break;
                        case EAST100:
                            east();
                            Level.DirectionValue = 99;
                            Dungeon.saveAll();
                            if (Statistics.Level99Visited) {
                                Dungeon.depth = Level.DirectionValue;
                                level = Dungeon.loadLevel(Dungeon.hero.heroClass);
                            } else {
                                level = Dungeon.newLevel();
                                Badges.validateDeathFromFire();
                            }
                            Dungeon.switchLevel(level, level.west);
                            break;
                        case WEST1:
                            west();
                            Level.DirectionValue = 2;
                            Dungeon.saveAll();
                            if (Statistics.Level2Visited) {
                                Dungeon.depth = Level.DirectionValue;
                                level = Dungeon.loadLevel(Dungeon.hero.heroClass);
                            } else {
                                level = Dungeon.newLevel();
                                Badges.validateDeathFromFire();
                            }
                            Dungeon.switchLevel(level, level.east);
                            break;
                        case WEST2:
                            west();
                            Level.DirectionValue = 3;
                            Dungeon.saveAll();
                            if (Statistics.Level3Visited) {
                                Dungeon.depth = Level.DirectionValue;
                                level = Dungeon.loadLevel(Dungeon.hero.heroClass);
                            } else {
                                level = Dungeon.newLevel();
                                Badges.validateDeathFromFire();
                            }
                            Dungeon.switchLevel(level, level.east);
                            break;
                        case WEST3:
                            west();
                            Level.DirectionValue = 4;
                            Dungeon.saveAll();
                            if (Statistics.Level4Visited) {
                                Dungeon.depth = Level.DirectionValue;
                                level = Dungeon.loadLevel(Dungeon.hero.heroClass);
                            } else {
                                level = Dungeon.newLevel();
                                Badges.validateDeathFromFire();
                            }
                            Dungeon.switchLevel(level, level.east);
                            break;
                        case WEST4:
                            west();
                            Level.DirectionValue = 5;
                            Dungeon.saveAll();
                            if (Statistics.Level5Visited) {
                                Dungeon.depth = Level.DirectionValue;
                                level = Dungeon.loadLevel(Dungeon.hero.heroClass);
                            } else {
                                level = Dungeon.newLevel();
                                Badges.validateDeathFromFire();
                            }
                            Dungeon.switchLevel(level, level.east);
                            break;
                        case WEST5:
                            west();
                            Level.DirectionValue = 6;
                            Dungeon.saveAll();
                            if (Statistics.Level6Visited) {
                                Dungeon.depth = Level.DirectionValue;
                                level = Dungeon.loadLevel(Dungeon.hero.heroClass);
                            } else {
                                level = Dungeon.newLevel();
                                Badges.validateDeathFromFire();
                            }
                            Dungeon.switchLevel(level, level.east);
                            break;
                        case WEST6:
                            west();
                            Level.DirectionValue = 7;
                            Dungeon.saveAll();
                            if (Statistics.Level7Visited) {
                                Dungeon.depth = Level.DirectionValue;
                                level = Dungeon.loadLevel(Dungeon.hero.heroClass);
                            } else {
                                level = Dungeon.newLevel();
                                Badges.validateDeathFromFire();
                            }
                            Dungeon.switchLevel(level, level.east);
                            break;
                        case WEST7:
                            west();
                            Level.DirectionValue = 8;
                            Dungeon.saveAll();
                            if (Statistics.Level8Visited) {
                                Dungeon.depth = Level.DirectionValue;
                                level = Dungeon.loadLevel(Dungeon.hero.heroClass);
                            } else {
                                level = Dungeon.newLevel();
                                Badges.validateDeathFromFire();
                            }
                            Dungeon.switchLevel(level, level.east);
                            break;
                        case WEST8:
                            west();
                            Level.DirectionValue = 9;
                            Dungeon.saveAll();
                            if (Statistics.Level9Visited) {
                                Dungeon.depth = Level.DirectionValue;
                                level = Dungeon.loadLevel(Dungeon.hero.heroClass);
                            } else {
                                level = Dungeon.newLevel();
                                Badges.validateDeathFromFire();
                            }
                            Dungeon.switchLevel(level, level.east);
                            break;
                        case WEST9:
                            west();
                            Level.DirectionValue = 10;
                            Dungeon.saveAll();
                            if (Statistics.Level10Visited) {
                                Dungeon.depth = Level.DirectionValue;
                                level = Dungeon.loadLevel(Dungeon.hero.heroClass);
                            } else {
                                level = Dungeon.newLevel();
                                Badges.validateDeathFromFire();
                            }
                            Dungeon.switchLevel(level, level.east);
                            break;
                        case WEST10:
                            west();
                            Level.DirectionValue = 11;
                            Dungeon.saveAll();
                            if (Statistics.Level11Visited) {
                                Dungeon.depth = Level.DirectionValue;
                                level = Dungeon.loadLevel(Dungeon.hero.heroClass);
                            } else {
                                level = Dungeon.newLevel();
                                Badges.validateDeathFromFire();
                            }
                            Dungeon.switchLevel(level, level.east);
                            break;
                        case WEST11:
                            west();
                            Level.DirectionValue = 12;
                            Dungeon.saveAll();
                            if (Statistics.Level12Visited) {
                                Dungeon.depth = Level.DirectionValue;
                                level = Dungeon.loadLevel(Dungeon.hero.heroClass);
                            } else {
                                level = Dungeon.newLevel();
                                Badges.validateDeathFromFire();
                            }
                            Dungeon.switchLevel(level, level.east);
                            break;
                        case WEST12:
                            west();
                            Level.DirectionValue = 13;
                            Dungeon.saveAll();
                            if (Statistics.Level13Visited) {
                                Dungeon.depth = Level.DirectionValue;
                                level = Dungeon.loadLevel(Dungeon.hero.heroClass);
                            } else {
                                level = Dungeon.newLevel();
                                Badges.validateDeathFromFire();
                            }
                            Dungeon.switchLevel(level, level.east);
                            break;
                        case WEST13:
                            west();
                            Level.DirectionValue = 14;
                            Dungeon.saveAll();
                            if (Statistics.Level14Visited) {
                                Dungeon.depth = Level.DirectionValue;
                                level = Dungeon.loadLevel(Dungeon.hero.heroClass);
                            } else {
                                level = Dungeon.newLevel();
                                Badges.validateDeathFromFire();
                            }
                            Dungeon.switchLevel(level, level.east);
                            break;
                        case WEST14:
                            west();
                            Level.DirectionValue = 15;
                            Dungeon.saveAll();
                            if (Statistics.Level15Visited) {
                                Dungeon.depth = Level.DirectionValue;
                                level = Dungeon.loadLevel(Dungeon.hero.heroClass);
                            } else {
                                level = Dungeon.newLevel();
                                Badges.validateDeathFromFire();
                            }
                            Dungeon.switchLevel(level, level.east);
                            break;
                        case WEST15:
                            west();
                            Level.DirectionValue = 16;
                            Dungeon.saveAll();
                            if (Statistics.Level16Visited) {
                                Dungeon.depth = Level.DirectionValue;
                                level = Dungeon.loadLevel(Dungeon.hero.heroClass);
                            } else {
                                level = Dungeon.newLevel();
                                Badges.validateDeathFromFire();
                            }
                            Dungeon.switchLevel(level, level.east);
                            break;
                        case WEST16:
                            west();
                            Level.DirectionValue = 17;
                            Dungeon.saveAll();
                            if (Statistics.Level17Visited) {
                                Dungeon.depth = Level.DirectionValue;
                                level = Dungeon.loadLevel(Dungeon.hero.heroClass);
                            } else {
                                level = Dungeon.newLevel();
                                Badges.validateDeathFromFire();
                            }
                            Dungeon.switchLevel(level, level.east);
                            break;
                        case WEST17:
                            west();
                            Level.DirectionValue = 18;
                            Dungeon.saveAll();
                            if (Statistics.Level18Visited) {
                                Dungeon.depth = Level.DirectionValue;
                                level = Dungeon.loadLevel(Dungeon.hero.heroClass);
                            } else {
                                level = Dungeon.newLevel();
                                Badges.validateDeathFromFire();
                            }
                            Dungeon.switchLevel(level, level.east);
                            break;
                        case WEST18:
                            west();
                            Level.DirectionValue = 19;
                            Dungeon.saveAll();
                            if (Statistics.Level19Visited) {
                                Dungeon.depth = Level.DirectionValue;
                                level = Dungeon.loadLevel(Dungeon.hero.heroClass);
                            } else {
                                level = Dungeon.newLevel();
                                Badges.validateDeathFromFire();
                            }
                            Dungeon.switchLevel(level, level.east);
                            break;
                        case WEST19:
                            west();
                            Level.DirectionValue = 20;
                            Dungeon.saveAll();
                            if (Statistics.Level20Visited) {
                                Dungeon.depth = Level.DirectionValue;
                                level = Dungeon.loadLevel(Dungeon.hero.heroClass);
                            } else {
                                level = Dungeon.newLevel();
                                Badges.validateDeathFromFire();
                            }
                            Dungeon.switchLevel(level, level.east);
                            break;
                        case WEST20:
                            west();
                            Level.DirectionValue = 21;
                            Dungeon.saveAll();
                            if (Statistics.Level21Visited) {
                                Dungeon.depth = Level.DirectionValue;
                                level = Dungeon.loadLevel(Dungeon.hero.heroClass);
                            } else {
                                level = Dungeon.newLevel();
                                Badges.validateDeathFromFire();
                            }
                            Dungeon.switchLevel(level, level.east);
                            break;
                        case WEST21:
                            west();
                            Level.DirectionValue = 22;
                            Dungeon.saveAll();
                            if (Statistics.Level22Visited) {
                                Dungeon.depth = Level.DirectionValue;
                                level = Dungeon.loadLevel(Dungeon.hero.heroClass);
                            } else {
                                level = Dungeon.newLevel();
                                Badges.validateDeathFromFire();
                            }
                            Dungeon.switchLevel(level, level.east);
                            break;
                        case WEST22:
                            west();
                            Level.DirectionValue = 23;
                            Dungeon.saveAll();
                            if (Statistics.Level23Visited) {
                                Dungeon.depth = Level.DirectionValue;
                                level = Dungeon.loadLevel(Dungeon.hero.heroClass);
                            } else {
                                level = Dungeon.newLevel();
                                Badges.validateDeathFromFire();
                            }
                            Dungeon.switchLevel(level, level.east);
                            break;
                        case WEST23:
                            west();
                            Level.DirectionValue = 24;
                            Dungeon.saveAll();
                            if (Statistics.Level24Visited) {
                                Dungeon.depth = Level.DirectionValue;
                                level = Dungeon.loadLevel(Dungeon.hero.heroClass);
                            } else {
                                level = Dungeon.newLevel();
                                Badges.validateDeathFromFire();
                            }
                            Dungeon.switchLevel(level, level.east);
                            break;
                        case WEST24:
                            west();
                            Level.DirectionValue = 25;
                            Dungeon.saveAll();
                            if (Statistics.Level25Visited) {
                                Dungeon.depth = Level.DirectionValue;
                                level = Dungeon.loadLevel(Dungeon.hero.heroClass);
                            } else {
                                level = Dungeon.newLevel();
                                Badges.validateDeathFromFire();
                            }
                            Dungeon.switchLevel(level, level.east);
                            break;
                        case WEST25:
                            west();
                            Level.DirectionValue = 26;
                            Dungeon.saveAll();
                            if (Statistics.Level26Visited) {
                                Dungeon.depth = Level.DirectionValue;
                                level = Dungeon.loadLevel(Dungeon.hero.heroClass);
                            } else {
                                level = Dungeon.newLevel();
                                Badges.validateDeathFromFire();
                            }
                            Dungeon.switchLevel(level, level.east);
                            break;
                        case WEST26:
                            west();
                            Level.DirectionValue = 27;
                            Dungeon.saveAll();
                            if (Statistics.Level27Visited) {
                                Dungeon.depth = Level.DirectionValue;
                                level = Dungeon.loadLevel(Dungeon.hero.heroClass);
                            } else {
                                level = Dungeon.newLevel();
                                Badges.validateDeathFromFire();
                            }
                            Dungeon.switchLevel(level, level.east);
                            break;
                        case WEST27:
                            west();
                            Level.DirectionValue = 28;
                            Dungeon.saveAll();
                            if (Statistics.Level28Visited) {
                                Dungeon.depth = Level.DirectionValue;
                                level = Dungeon.loadLevel(Dungeon.hero.heroClass);
                            } else {
                                level = Dungeon.newLevel();
                                Badges.validateDeathFromFire();
                            }
                            Dungeon.switchLevel(level, level.east);
                            break;
                        case WEST28:
                            west();
                            Level.DirectionValue = 29;
                            Dungeon.saveAll();
                            if (Statistics.Level29Visited) {
                                Dungeon.depth = Level.DirectionValue;
                                level = Dungeon.loadLevel(Dungeon.hero.heroClass);
                            } else {
                                level = Dungeon.newLevel();
                                Badges.validateDeathFromFire();
                            }
                            Dungeon.switchLevel(level, level.east);
                            break;
                        case WEST29:
                            west();
                            Level.DirectionValue = 30;
                            Dungeon.saveAll();
                            if (Statistics.Level30Visited) {
                                Dungeon.depth = Level.DirectionValue;
                                level = Dungeon.loadLevel(Dungeon.hero.heroClass);
                            } else {
                                level = Dungeon.newLevel();
                                Badges.validateDeathFromFire();
                            }
                            Dungeon.switchLevel(level, level.east);
                            break;
                        case WEST30:
                            west();
                            Level.DirectionValue = 31;
                            Dungeon.saveAll();
                            if (Statistics.Level31Visited) {
                                Dungeon.depth = Level.DirectionValue;
                                level = Dungeon.loadLevel(Dungeon.hero.heroClass);
                            } else {
                                level = Dungeon.newLevel();
                                Badges.validateDeathFromFire();
                            }
                            Dungeon.switchLevel(level, level.east);
                            break;
                        case WEST31:
                            west();
                            Level.DirectionValue = 32;
                            Dungeon.saveAll();
                            if (Statistics.Level32Visited) {
                                Dungeon.depth = Level.DirectionValue;
                                level = Dungeon.loadLevel(Dungeon.hero.heroClass);
                            } else {
                                level = Dungeon.newLevel();
                                Badges.validateDeathFromFire();
                            }
                            Dungeon.switchLevel(level, level.east);
                            break;
                        case WEST32:
                            west();
                            Level.DirectionValue = 33;
                            Dungeon.saveAll();
                            if (Statistics.Level33Visited) {
                                Dungeon.depth = Level.DirectionValue;
                                level = Dungeon.loadLevel(Dungeon.hero.heroClass);
                            } else {
                                level = Dungeon.newLevel();
                                Badges.validateDeathFromFire();
                            }
                            Dungeon.switchLevel(level, level.east);
                            break;
                        case WEST33:
                            west();
                            Level.DirectionValue = 34;
                            Dungeon.saveAll();
                            if (Statistics.Level34Visited) {
                                Dungeon.depth = Level.DirectionValue;
                                level = Dungeon.loadLevel(Dungeon.hero.heroClass);
                            } else {
                                level = Dungeon.newLevel();
                                Badges.validateDeathFromFire();
                            }
                            Dungeon.switchLevel(level, level.east);
                            break;
                        case WEST34:
                            west();
                            Level.DirectionValue = 35;
                            Dungeon.saveAll();
                            if (Statistics.Level35Visited) {
                                Dungeon.depth = Level.DirectionValue;
                                level = Dungeon.loadLevel(Dungeon.hero.heroClass);
                            } else {
                                level = Dungeon.newLevel();
                                Badges.validateDeathFromFire();
                            }
                            Dungeon.switchLevel(level, level.east);
                            break;
                        case WEST35:
                            west();
                            Level.DirectionValue = 36;
                            Dungeon.saveAll();
                            if (Statistics.Level36Visited) {
                                Dungeon.depth = Level.DirectionValue;
                                level = Dungeon.loadLevel(Dungeon.hero.heroClass);
                            } else {
                                level = Dungeon.newLevel();
                                Badges.validateDeathFromFire();
                            }
                            Dungeon.switchLevel(level, level.east);
                            break;
                        case WEST36:
                            west();
                            Level.DirectionValue = 37;
                            Dungeon.saveAll();
                            if (Statistics.Level37Visited) {
                                Dungeon.depth = Level.DirectionValue;
                                level = Dungeon.loadLevel(Dungeon.hero.heroClass);
                            } else {
                                level = Dungeon.newLevel();
                                Badges.validateDeathFromFire();
                            }
                            Dungeon.switchLevel(level, level.east);
                            break;
                        case WEST37:
                            west();
                            Level.DirectionValue = 38;
                            Dungeon.saveAll();
                            if (Statistics.Level38Visited) {
                                Dungeon.depth = Level.DirectionValue;
                                level = Dungeon.loadLevel(Dungeon.hero.heroClass);
                            } else {
                                level = Dungeon.newLevel();
                                Badges.validateDeathFromFire();
                            }
                            Dungeon.switchLevel(level, level.east);
                            break;
                        case WEST38:
                            west();
                            Level.DirectionValue = 39;
                            Dungeon.saveAll();
                            if (Statistics.Level39Visited) {
                                Dungeon.depth = Level.DirectionValue;
                                level = Dungeon.loadLevel(Dungeon.hero.heroClass);
                            } else {
                                level = Dungeon.newLevel();
                                Badges.validateDeathFromFire();
                            }
                            Dungeon.switchLevel(level, level.east);
                            break;
                        case WEST39:
                            west();
                            Level.DirectionValue = 40;
                            Dungeon.saveAll();
                            if (Statistics.Level40Visited) {
                                Dungeon.depth = Level.DirectionValue;
                                level = Dungeon.loadLevel(Dungeon.hero.heroClass);
                            } else {
                                level = Dungeon.newLevel();
                                Badges.validateDeathFromFire();
                            }
                            Dungeon.switchLevel(level, level.east);
                            break;
                        case WEST40:
                            west();
                            Level.DirectionValue = 41;
                            Dungeon.saveAll();
                            if (Statistics.Level41Visited) {
                                Dungeon.depth = Level.DirectionValue;
                                level = Dungeon.loadLevel(Dungeon.hero.heroClass);
                            } else {
                                level = Dungeon.newLevel();
                                Badges.validateDeathFromFire();
                            }
                            Dungeon.switchLevel(level, level.east);
                            break;
                        case WEST41:
                            west();
                            Level.DirectionValue = 42;
                            Dungeon.saveAll();
                            if (Statistics.Level42Visited) {
                                Dungeon.depth = Level.DirectionValue;
                                level = Dungeon.loadLevel(Dungeon.hero.heroClass);
                            } else {
                                level = Dungeon.newLevel();
                                Badges.validateDeathFromFire();
                            }
                            Dungeon.switchLevel(level, level.east);
                            break;
                        case WEST42:
                            west();
                            Level.DirectionValue = 43;
                            Dungeon.saveAll();
                            if (Statistics.Level43Visited) {
                                Dungeon.depth = Level.DirectionValue;
                                level = Dungeon.loadLevel(Dungeon.hero.heroClass);
                            } else {
                                level = Dungeon.newLevel();
                                Badges.validateDeathFromFire();
                            }
                            Dungeon.switchLevel(level, level.east);
                            break;
                        case WEST43:
                            west();
                            Level.DirectionValue = 44;
                            Dungeon.saveAll();
                            if (Statistics.Level44Visited) {
                                Dungeon.depth = Level.DirectionValue;
                                level = Dungeon.loadLevel(Dungeon.hero.heroClass);
                            } else {
                                level = Dungeon.newLevel();
                                Badges.validateDeathFromFire();
                            }
                            Dungeon.switchLevel(level, level.east);
                            break;
                        case WEST44:
                            west();
                            Level.DirectionValue = 45;
                            Dungeon.saveAll();
                            if (Statistics.Level45Visited) {
                                Dungeon.depth = Level.DirectionValue;
                                level = Dungeon.loadLevel(Dungeon.hero.heroClass);
                            } else {
                                level = Dungeon.newLevel();
                                Badges.validateDeathFromFire();
                            }
                            Dungeon.switchLevel(level, level.east);
                            break;
                        case WEST45:
                            west();
                            Level.DirectionValue = 46;
                            Dungeon.saveAll();
                            if (Statistics.Level46Visited) {
                                Dungeon.depth = Level.DirectionValue;
                                level = Dungeon.loadLevel(Dungeon.hero.heroClass);
                            } else {
                                level = Dungeon.newLevel();
                                Badges.validateDeathFromFire();
                            }
                            Dungeon.switchLevel(level, level.east);
                            break;
                        case WEST46:
                            west();
                            Level.DirectionValue = 47;
                            Dungeon.saveAll();
                            if (Statistics.Level47Visited) {
                                Dungeon.depth = Level.DirectionValue;
                                level = Dungeon.loadLevel(Dungeon.hero.heroClass);
                            } else {
                                level = Dungeon.newLevel();
                                Badges.validateDeathFromFire();
                            }
                            Dungeon.switchLevel(level, level.east);
                            break;
                        case WEST47:
                            west();
                            Level.DirectionValue = 48;
                            Dungeon.saveAll();
                            if (Statistics.Level48Visited) {
                                Dungeon.depth = Level.DirectionValue;
                                level = Dungeon.loadLevel(Dungeon.hero.heroClass);
                            } else {
                                level = Dungeon.newLevel();
                                Badges.validateDeathFromFire();
                            }
                            Dungeon.switchLevel(level, level.east);
                            break;
                        case WEST48:
                            west();
                            Level.DirectionValue = 49;
                            Dungeon.saveAll();
                            if (Statistics.Level49Visited) {
                                Dungeon.depth = Level.DirectionValue;
                                level = Dungeon.loadLevel(Dungeon.hero.heroClass);
                            } else {
                                level = Dungeon.newLevel();
                                Badges.validateDeathFromFire();
                            }
                            Dungeon.switchLevel(level, level.east);
                            break;
                        case WEST49:
                            west();
                            Level.DirectionValue = 50;
                            Dungeon.saveAll();
                            if (Statistics.Level50Visited) {
                                Dungeon.depth = Level.DirectionValue;
                                level = Dungeon.loadLevel(Dungeon.hero.heroClass);
                            } else {
                                level = Dungeon.newLevel();
                                Badges.validateDeathFromFire();
                            }
                            Dungeon.switchLevel(level, level.east);
                            break;
                        case WEST50:
                            west();
                            Level.DirectionValue = 51;
                            Dungeon.saveAll();
                            if (Statistics.Level51Visited) {
                                Dungeon.depth = Level.DirectionValue;
                                level = Dungeon.loadLevel(Dungeon.hero.heroClass);
                            } else {
                                level = Dungeon.newLevel();
                                Badges.validateDeathFromFire();
                            }
                            Dungeon.switchLevel(level, level.east);
                            break;
                        case WEST51:
                            west();
                            Level.DirectionValue = 52;
                            Dungeon.saveAll();
                            if (Statistics.Level52Visited) {
                                Dungeon.depth = Level.DirectionValue;
                                level = Dungeon.loadLevel(Dungeon.hero.heroClass);
                            } else {
                                level = Dungeon.newLevel();
                                Badges.validateDeathFromFire();
                            }
                            Dungeon.switchLevel(level, level.east);
                            break;
                        case WEST52:
                            west();
                            Level.DirectionValue = 53;
                            Dungeon.saveAll();
                            if (Statistics.Level53Visited) {
                                Dungeon.depth = Level.DirectionValue;
                                level = Dungeon.loadLevel(Dungeon.hero.heroClass);
                            } else {
                                level = Dungeon.newLevel();
                                Badges.validateDeathFromFire();
                            }
                            Dungeon.switchLevel(level, level.east);
                            break;
                        case WEST53:
                            west();
                            Level.DirectionValue = 54;
                            Dungeon.saveAll();
                            if (Statistics.Level54Visited) {
                                Dungeon.depth = Level.DirectionValue;
                                level = Dungeon.loadLevel(Dungeon.hero.heroClass);
                            } else {
                                level = Dungeon.newLevel();
                                Badges.validateDeathFromFire();
                            }
                            Dungeon.switchLevel(level, level.east);
                            break;
                        case WEST54:
                            west();
                            Level.DirectionValue = 55;
                            Dungeon.saveAll();
                            if (Statistics.Level55Visited) {
                                Dungeon.depth = Level.DirectionValue;
                                level = Dungeon.loadLevel(Dungeon.hero.heroClass);
                            } else {
                                level = Dungeon.newLevel();
                                Badges.validateDeathFromFire();
                            }
                            Dungeon.switchLevel(level, level.east);
                            break;
                        case WEST55:
                            west();
                            Level.DirectionValue = 56;
                            Dungeon.saveAll();
                            if (Statistics.Level56Visited) {
                                Dungeon.depth = Level.DirectionValue;
                                level = Dungeon.loadLevel(Dungeon.hero.heroClass);
                            } else {
                                level = Dungeon.newLevel();
                                Badges.validateDeathFromFire();
                            }
                            Dungeon.switchLevel(level, level.east);
                            break;
                        case WEST56:
                            west();
                            Level.DirectionValue = 57;
                            Dungeon.saveAll();
                            if (Statistics.Level57Visited) {
                                Dungeon.depth = Level.DirectionValue;
                                level = Dungeon.loadLevel(Dungeon.hero.heroClass);
                            } else {
                                level = Dungeon.newLevel();
                                Badges.validateDeathFromFire();
                            }
                            Dungeon.switchLevel(level, level.east);
                            break;
                        case WEST57:
                            west();
                            Level.DirectionValue = 58;
                            Dungeon.saveAll();
                            if (Statistics.Level58Visited) {
                                Dungeon.depth = Level.DirectionValue;
                                level = Dungeon.loadLevel(Dungeon.hero.heroClass);
                            } else {
                                level = Dungeon.newLevel();
                                Badges.validateDeathFromFire();
                            }
                            Dungeon.switchLevel(level, level.east);
                            break;
                        case WEST58:
                            west();
                            Level.DirectionValue = 59;
                            Dungeon.saveAll();
                            if (Statistics.Level59Visited) {
                                Dungeon.depth = Level.DirectionValue;
                                level = Dungeon.loadLevel(Dungeon.hero.heroClass);
                            } else {
                                level = Dungeon.newLevel();
                                Badges.validateDeathFromFire();
                            }
                            Dungeon.switchLevel(level, level.east);
                            break;
                        case WEST59:
                            west();
                            Level.DirectionValue = 60;
                            Dungeon.saveAll();
                            if (Statistics.Level60Visited) {
                                Dungeon.depth = Level.DirectionValue;
                                level = Dungeon.loadLevel(Dungeon.hero.heroClass);
                            } else {
                                level = Dungeon.newLevel();
                                Badges.validateDeathFromFire();
                            }
                            Dungeon.switchLevel(level, level.east);
                            break;
                        case WEST60:
                            west();
                            Level.DirectionValue = 61;
                            Dungeon.saveAll();
                            if (Statistics.Level61Visited) {
                                Dungeon.depth = Level.DirectionValue;
                                level = Dungeon.loadLevel(Dungeon.hero.heroClass);
                            } else {
                                level = Dungeon.newLevel();
                                Badges.validateDeathFromFire();
                            }
                            Dungeon.switchLevel(level, level.east);
                            break;
                        case WEST61:
                            west();
                            Level.DirectionValue = 62;
                            Dungeon.saveAll();
                            if (Statistics.Level62Visited) {
                                Dungeon.depth = Level.DirectionValue;
                                level = Dungeon.loadLevel(Dungeon.hero.heroClass);
                            } else {
                                level = Dungeon.newLevel();
                                Badges.validateDeathFromFire();
                            }
                            Dungeon.switchLevel(level, level.east);
                            break;
                        case WEST62:
                            west();
                            Level.DirectionValue = 63;
                            Dungeon.saveAll();
                            if (Statistics.Level63Visited) {
                                Dungeon.depth = Level.DirectionValue;
                                level = Dungeon.loadLevel(Dungeon.hero.heroClass);
                            } else {
                                level = Dungeon.newLevel();
                                Badges.validateDeathFromFire();
                            }
                            Dungeon.switchLevel(level, level.east);
                            break;
                        case WEST63:
                            west();
                            Level.DirectionValue = 64;
                            Dungeon.saveAll();
                            if (Statistics.Level64Visited) {
                                Dungeon.depth = Level.DirectionValue;
                                level = Dungeon.loadLevel(Dungeon.hero.heroClass);
                            } else {
                                level = Dungeon.newLevel();
                                Badges.validateDeathFromFire();
                            }
                            Dungeon.switchLevel(level, level.east);
                            break;
                        case WEST64:
                            west();
                            Level.DirectionValue = 65;
                            Dungeon.saveAll();
                            if (Statistics.Level65Visited) {
                                Dungeon.depth = Level.DirectionValue;
                                level = Dungeon.loadLevel(Dungeon.hero.heroClass);
                            } else {
                                level = Dungeon.newLevel();
                                Badges.validateDeathFromFire();
                            }
                            Dungeon.switchLevel(level, level.east);
                            break;
                        case WEST65:
                            west();
                            Level.DirectionValue = 66;
                            Dungeon.saveAll();
                            if (Statistics.Level66Visited) {
                                Dungeon.depth = Level.DirectionValue;
                                level = Dungeon.loadLevel(Dungeon.hero.heroClass);
                            } else {
                                level = Dungeon.newLevel();
                                Badges.validateDeathFromFire();
                            }
                            Dungeon.switchLevel(level, level.east);
                            break;
                        case WEST66:
                            west();
                            Level.DirectionValue = 67;
                            Dungeon.saveAll();
                            if (Statistics.Level67Visited) {
                                Dungeon.depth = Level.DirectionValue;
                                level = Dungeon.loadLevel(Dungeon.hero.heroClass);
                            } else {
                                level = Dungeon.newLevel();
                                Badges.validateDeathFromFire();
                            }
                            Dungeon.switchLevel(level, level.east);
                            break;
                        case WEST67:
                            west();
                            Level.DirectionValue = 68;
                            Dungeon.saveAll();
                            if (Statistics.Level68Visited) {
                                Dungeon.depth = Level.DirectionValue;
                                level = Dungeon.loadLevel(Dungeon.hero.heroClass);
                            } else {
                                level = Dungeon.newLevel();
                                Badges.validateDeathFromFire();
                            }
                            Dungeon.switchLevel(level, level.east);
                            break;
                        case WEST68:
                            west();
                            Level.DirectionValue = 69;
                            Dungeon.saveAll();
                            if (Statistics.Level69Visited) {
                                Dungeon.depth = Level.DirectionValue;
                                level = Dungeon.loadLevel(Dungeon.hero.heroClass);
                            } else {
                                level = Dungeon.newLevel();
                                Badges.validateDeathFromFire();
                            }
                            Dungeon.switchLevel(level, level.east);
                            break;
                        case WEST69:
                            west();
                            Level.DirectionValue = 70;
                            Dungeon.saveAll();
                            if (Statistics.Level70Visited) {
                                Dungeon.depth = Level.DirectionValue;
                                level = Dungeon.loadLevel(Dungeon.hero.heroClass);
                            } else {
                                level = Dungeon.newLevel();
                                Badges.validateDeathFromFire();
                            }
                            Dungeon.switchLevel(level, level.east);
                            break;
                        case WEST70:
                            west();
                            Level.DirectionValue = 71;
                            Dungeon.saveAll();
                            if (Statistics.Level71Visited) {
                                Dungeon.depth = Level.DirectionValue;
                                level = Dungeon.loadLevel(Dungeon.hero.heroClass);
                            } else {
                                level = Dungeon.newLevel();
                                Badges.validateDeathFromFire();
                            }
                            Dungeon.switchLevel(level, level.east);
                            break;
                        case WEST71:
                            west();
                            Level.DirectionValue = 72;
                            Dungeon.saveAll();
                            if (Statistics.Level72Visited) {
                                Dungeon.depth = Level.DirectionValue;
                                level = Dungeon.loadLevel(Dungeon.hero.heroClass);
                            } else {
                                level = Dungeon.newLevel();
                                Badges.validateDeathFromFire();
                            }
                            Dungeon.switchLevel(level, level.east);
                            break;
                        case WEST72:
                            west();
                            Level.DirectionValue = 73;
                            Dungeon.saveAll();
                            if (Statistics.Level73Visited) {
                                Dungeon.depth = Level.DirectionValue;
                                level = Dungeon.loadLevel(Dungeon.hero.heroClass);
                            } else {
                                level = Dungeon.newLevel();
                                Badges.validateDeathFromFire();
                            }
                            Dungeon.switchLevel(level, level.east);
                            break;
                        case WEST73:
                            west();
                            Level.DirectionValue = 74;
                            Dungeon.saveAll();
                            if (Statistics.Level74Visited) {
                                Dungeon.depth = Level.DirectionValue;
                                level = Dungeon.loadLevel(Dungeon.hero.heroClass);
                            } else {
                                level = Dungeon.newLevel();
                                Badges.validateDeathFromFire();
                            }
                            Dungeon.switchLevel(level, level.east);
                            break;
                        case WEST74:
                            west();
                            Level.DirectionValue = 75;
                            Dungeon.saveAll();
                            if (Statistics.Level75Visited) {
                                Dungeon.depth = Level.DirectionValue;
                                level = Dungeon.loadLevel(Dungeon.hero.heroClass);
                            } else {
                                level = Dungeon.newLevel();
                                Badges.validateDeathFromFire();
                            }
                            Dungeon.switchLevel(level, level.east);
                            break;
                        case WEST75:
                            west();
                            Level.DirectionValue = 76;
                            Dungeon.saveAll();
                            if (Statistics.Level76Visited) {
                                Dungeon.depth = Level.DirectionValue;
                                level = Dungeon.loadLevel(Dungeon.hero.heroClass);
                            } else {
                                level = Dungeon.newLevel();
                                Badges.validateDeathFromFire();
                            }
                            Dungeon.switchLevel(level, level.east);
                            break;
                        case WEST76:
                            west();
                            Level.DirectionValue = 77;
                            Dungeon.saveAll();
                            if (Statistics.Level77Visited) {
                                Dungeon.depth = Level.DirectionValue;
                                level = Dungeon.loadLevel(Dungeon.hero.heroClass);
                            } else {
                                level = Dungeon.newLevel();
                                Badges.validateDeathFromFire();
                            }
                            Dungeon.switchLevel(level, level.east);
                            break;
                        case WEST77:
                            west();
                            Level.DirectionValue = 78;
                            Dungeon.saveAll();
                            if (Statistics.Level78Visited) {
                                Dungeon.depth = Level.DirectionValue;
                                level = Dungeon.loadLevel(Dungeon.hero.heroClass);
                            } else {
                                level = Dungeon.newLevel();
                                Badges.validateDeathFromFire();
                            }
                            Dungeon.switchLevel(level, level.east);
                            break;
                        case WEST78:
                            west();
                            Level.DirectionValue = 79;
                            Dungeon.saveAll();
                            if (Statistics.Level79Visited) {
                                Dungeon.depth = Level.DirectionValue;
                                level = Dungeon.loadLevel(Dungeon.hero.heroClass);
                            } else {
                                level = Dungeon.newLevel();
                                Badges.validateDeathFromFire();
                            }
                            Dungeon.switchLevel(level, level.east);
                            break;
                        case WEST79:
                            west();
                            Level.DirectionValue = 80;
                            Dungeon.saveAll();
                            if (Statistics.Level80Visited) {
                                Dungeon.depth = Level.DirectionValue;
                                level = Dungeon.loadLevel(Dungeon.hero.heroClass);
                            } else {
                                level = Dungeon.newLevel();
                                Badges.validateDeathFromFire();
                            }
                            Dungeon.switchLevel(level, level.east);
                            break;
                        case WEST80:
                            west();
                            Level.DirectionValue = 81;
                            Dungeon.saveAll();
                            if (Statistics.Level81Visited) {
                                Dungeon.depth = Level.DirectionValue;
                                level = Dungeon.loadLevel(Dungeon.hero.heroClass);
                            } else {
                                level = Dungeon.newLevel();
                                Badges.validateDeathFromFire();
                            }
                            Dungeon.switchLevel(level, level.east);
                            break;
                        case WEST81:
                            west();
                            Level.DirectionValue = 82;
                            Dungeon.saveAll();
                            if (Statistics.Level82Visited) {
                                Dungeon.depth = Level.DirectionValue;
                                level = Dungeon.loadLevel(Dungeon.hero.heroClass);
                            } else {
                                level = Dungeon.newLevel();
                                Badges.validateDeathFromFire();
                            }
                            Dungeon.switchLevel(level, level.east);
                            break;
                        case WEST82:
                            west();
                            Level.DirectionValue = 83;
                            Dungeon.saveAll();
                            if (Statistics.Level83Visited) {
                                Dungeon.depth = Level.DirectionValue;
                                level = Dungeon.loadLevel(Dungeon.hero.heroClass);
                            } else {
                                level = Dungeon.newLevel();
                                Badges.validateDeathFromFire();
                            }
                            Dungeon.switchLevel(level, level.east);
                            break;
                        case WEST83:
                            west();
                            Level.DirectionValue = 84;
                            Dungeon.saveAll();
                            if (Statistics.Level84Visited) {
                                Dungeon.depth = Level.DirectionValue;
                                level = Dungeon.loadLevel(Dungeon.hero.heroClass);
                            } else {
                                level = Dungeon.newLevel();
                                Badges.validateDeathFromFire();
                            }
                            Dungeon.switchLevel(level, level.east);
                            break;
                        case WEST84:
                            west();
                            Level.DirectionValue = 85;
                            Dungeon.saveAll();
                            if (Statistics.Level85Visited) {
                                Dungeon.depth = Level.DirectionValue;
                                level = Dungeon.loadLevel(Dungeon.hero.heroClass);
                            } else {
                                level = Dungeon.newLevel();
                                Badges.validateDeathFromFire();
                            }
                            Dungeon.switchLevel(level, level.east);
                            break;
                        case WEST85:
                            west();
                            Level.DirectionValue = 86;
                            Dungeon.saveAll();
                            if (Statistics.Level86Visited) {
                                Dungeon.depth = Level.DirectionValue;
                                level = Dungeon.loadLevel(Dungeon.hero.heroClass);
                            } else {
                                level = Dungeon.newLevel();
                                Badges.validateDeathFromFire();
                            }
                            Dungeon.switchLevel(level, level.east);
                            break;
                        case WEST86:
                            west();
                            Level.DirectionValue = 87;
                            Dungeon.saveAll();
                            if (Statistics.Level87Visited) {
                                Dungeon.depth = Level.DirectionValue;
                                level = Dungeon.loadLevel(Dungeon.hero.heroClass);
                            } else {
                                level = Dungeon.newLevel();
                                Badges.validateDeathFromFire();
                            }
                            Dungeon.switchLevel(level, level.east);
                            break;
                        case WEST87:
                            west();
                            Level.DirectionValue = 88;
                            Dungeon.saveAll();
                            if (Statistics.Level88Visited) {
                                Dungeon.depth = Level.DirectionValue;
                                level = Dungeon.loadLevel(Dungeon.hero.heroClass);
                            } else {
                                level = Dungeon.newLevel();
                                Badges.validateDeathFromFire();
                            }
                            Dungeon.switchLevel(level, level.east);
                            break;
                        case WEST88:
                            west();
                            Level.DirectionValue = 89;
                            Dungeon.saveAll();
                            if (Statistics.Level89Visited) {
                                Dungeon.depth = Level.DirectionValue;
                                level = Dungeon.loadLevel(Dungeon.hero.heroClass);
                            } else {
                                level = Dungeon.newLevel();
                                Badges.validateDeathFromFire();
                            }
                            Dungeon.switchLevel(level, level.east);
                            break;
                        case WEST89:
                            west();
                            Level.DirectionValue = 90;
                            Dungeon.saveAll();
                            if (Statistics.Level90Visited) {
                                Dungeon.depth = Level.DirectionValue;
                                level = Dungeon.loadLevel(Dungeon.hero.heroClass);
                            } else {
                                level = Dungeon.newLevel();
                                Badges.validateDeathFromFire();
                            }
                            Dungeon.switchLevel(level, level.east);
                            break;
                        case WEST90:
                            west();
                            Level.DirectionValue = 91;
                            Dungeon.saveAll();
                            if (Statistics.Level91Visited) {
                                Dungeon.depth = Level.DirectionValue;
                                level = Dungeon.loadLevel(Dungeon.hero.heroClass);
                            } else {
                                level = Dungeon.newLevel();
                                Badges.validateDeathFromFire();
                            }
                            Dungeon.switchLevel(level, level.east);
                            break;
                        case WEST91:
                            west();
                            Level.DirectionValue = 92;
                            Dungeon.saveAll();
                            if (Statistics.Level92Visited) {
                                Dungeon.depth = Level.DirectionValue;
                                level = Dungeon.loadLevel(Dungeon.hero.heroClass);
                            } else {
                                level = Dungeon.newLevel();
                                Badges.validateDeathFromFire();
                            }
                            Dungeon.switchLevel(level, level.east);
                            break;
                        case WEST92:
                            west();
                            Level.DirectionValue = 93;
                            Dungeon.saveAll();
                            if (Statistics.Level93Visited) {
                                Dungeon.depth = Level.DirectionValue;
                                level = Dungeon.loadLevel(Dungeon.hero.heroClass);
                            } else {
                                level = Dungeon.newLevel();
                                Badges.validateDeathFromFire();
                            }
                            Dungeon.switchLevel(level, level.east);
                            break;
                        case WEST93:
                            west();
                            Level.DirectionValue = 94;
                            Dungeon.saveAll();
                            if (Statistics.Level94Visited) {
                                Dungeon.depth = Level.DirectionValue;
                                level = Dungeon.loadLevel(Dungeon.hero.heroClass);
                            } else {
                                level = Dungeon.newLevel();
                                Badges.validateDeathFromFire();
                            }
                            Dungeon.switchLevel(level, level.east);
                            break;
                        case WEST94:
                            west();
                            Level.DirectionValue = 95;
                            Dungeon.saveAll();
                            if (Statistics.Level95Visited) {
                                Dungeon.depth = Level.DirectionValue;
                                level = Dungeon.loadLevel(Dungeon.hero.heroClass);
                            } else {
                                level = Dungeon.newLevel();
                                Badges.validateDeathFromFire();
                            }
                            Dungeon.switchLevel(level, level.east);
                            break;
                        case WEST95:
                            west();
                            Level.DirectionValue = 96;
                            Dungeon.saveAll();
                            if (Statistics.Level96Visited) {
                                Dungeon.depth = Level.DirectionValue;
                                level = Dungeon.loadLevel(Dungeon.hero.heroClass);
                            } else {
                                level = Dungeon.newLevel();
                                Badges.validateDeathFromFire();
                            }
                            Dungeon.switchLevel(level, level.east);
                            break;
                        case WEST96:
                            west();
                            Level.DirectionValue = 97;
                            Dungeon.saveAll();
                            if (Statistics.Level97Visited) {
                                Dungeon.depth = Level.DirectionValue;
                                level = Dungeon.loadLevel(Dungeon.hero.heroClass);
                            } else {
                                level = Dungeon.newLevel();
                                Badges.validateDeathFromFire();
                            }
                            Dungeon.switchLevel(level, level.east);
                            break;
                        case WEST97:
                            west();
                            Level.DirectionValue = 98;
                            Dungeon.saveAll();
                            if (Statistics.Level98Visited) {
                                Dungeon.depth = Level.DirectionValue;
                                level = Dungeon.loadLevel(Dungeon.hero.heroClass);
                            } else {
                                level = Dungeon.newLevel();
                                Badges.validateDeathFromFire();
                            }
                            Dungeon.switchLevel(level, level.east);
                            break;
                        case WEST98:
                            west();
                            Level.DirectionValue = 99;
                            Dungeon.saveAll();
                            if (Statistics.Level99Visited) {
                                Dungeon.depth = Level.DirectionValue;
                                level = Dungeon.loadLevel(Dungeon.hero.heroClass);
                            } else {
                                level = Dungeon.newLevel();
                                Badges.validateDeathFromFire();
                            }
                            Dungeon.switchLevel(level, level.east);
                            break;
                        case WEST99:
                            west();
                            Level.DirectionValue = 100;
                            Dungeon.saveAll();
                            if (Statistics.Level100Visited) {
                                Dungeon.depth = Level.DirectionValue;
                                level = Dungeon.loadLevel(Dungeon.hero.heroClass);
                            } else {
                                level = Dungeon.newLevel();
                                Badges.validateDeathFromFire();
                            }
                            Dungeon.switchLevel(level, level.east);
                            break;
                        case DOWN1:
                            down();
                            Level.DirectionValue = 101;
                            Dungeon.saveAll();
                            if (Statistics.Level101Visited) {
                                Dungeon.depth = Level.DirectionValue;
                                level = Dungeon.loadLevel(Dungeon.hero.heroClass);
                            } else {
                                level = Dungeon.newLevel();
                                Badges.validateDeathFromFire();
                            }
                            Dungeon.switchLevel(level, level.up);
                            break;
                        case DOWN2:
                            down();
                            Level.DirectionValue = 54;
                            Dungeon.saveAll();
                            if (Statistics.Level54Visited) {
                                Dungeon.depth = Level.DirectionValue;
                                level = Dungeon.loadLevel(Dungeon.hero.heroClass);
                            } else {
                                level = Dungeon.newLevel();
                                Badges.validateDeathFromFire();
                            }
                            Dungeon.switchLevel(level, level.down);
                            break;
                        case DOWN3:
                            down();
                            Level.DirectionValue = 101;
                            Dungeon.saveAll();
                            if (Statistics.Level101Visited) {
                                Dungeon.depth = Level.DirectionValue;
                                level = Dungeon.loadLevel(Dungeon.hero.heroClass);
                            } else {
                                level = Dungeon.newLevel();
                                Badges.validateDeathFromFire();
                            }
                            Dungeon.switchLevel(level, level.down);
                            break;
                        case DOWN4:
                            down();
                            Level.DirectionValue = 102;
                            Dungeon.saveAll();
                            if (Statistics.Level102Visited) {
                                Dungeon.depth = Level.DirectionValue;
                                level = Dungeon.loadLevel(Dungeon.hero.heroClass);
                            } else {
                                level = Dungeon.newLevel();
                                Badges.validateDeathFromFire();
                            }
                            Dungeon.switchLevel(level, level.up);
                            break;
                        case DOWN5:
                            down();
                            Level.DirectionValue = 22;
                            Dungeon.saveAll();
                            if (Statistics.Level22Visited) {
                                Dungeon.depth = Level.DirectionValue;
                                level = Dungeon.loadLevel(Dungeon.hero.heroClass);
                            } else {
                                level = Dungeon.newLevel();
                                Badges.validateDeathFromFire();
                            }
                            Dungeon.switchLevel(level, level.down);
                            break;
                        //Paleo Cave
                        case DOWN6:
                            down();
                            Level.DirectionValue = 106;
                            Dungeon.saveAll();
                            if (Statistics.Level106Visited) {
                                Dungeon.depth = Level.DirectionValue;
                                level = Dungeon.loadLevel(Dungeon.hero.heroClass);
                            } else {
                                level = Dungeon.newLevel();
                                Badges.validateDeathFromFire();
                            }
                            Dungeon.switchLevel(level, level.up);
                            break;
                        case DOWN7:
                            down();
                            Level.DirectionValue = 105;
                            Dungeon.saveAll();
                            if (Statistics.Level105Visited) {
                                Dungeon.depth = Level.DirectionValue;
                                level = Dungeon.loadLevel(Dungeon.hero.heroClass);
                            } else {
                                level = Dungeon.newLevel();
                                Badges.validateDeathFromFire();
                            }
                            Dungeon.switchLevel(level, level.up);
                            break;
                        case DOWN8:
                            down();
                            Level.DirectionValue = 103;
                            Dungeon.saveAll();
                            if (Statistics.Level103Visited) {
                                Dungeon.depth = Level.DirectionValue;
                                level = Dungeon.loadLevel(Dungeon.hero.heroClass);
                            } else {
                                level = Dungeon.newLevel();
                                Badges.validateDeathFromFire();
                            }
                            Dungeon.switchLevel(level, level.up);
                            break;
                        case DOWN9:
                            down();
                            Level.DirectionValue = 107;
                            Dungeon.saveAll();
                            if (Statistics.Level107Visited) {
                                Dungeon.depth = Level.DirectionValue;
                                level = Dungeon.loadLevel(Dungeon.hero.heroClass);
                            } else {
                                level = Dungeon.newLevel();
                                Badges.validateDeathFromFire();
                            }
                            Dungeon.switchLevel(level, level.up);
                            break;
                        case DOWN10:
                            down();
                            Level.DirectionValue = 108;
                            Dungeon.saveAll();
                            if (Statistics.Level108Visited) {
                                Dungeon.depth = Level.DirectionValue;
                                level = Dungeon.loadLevel(Dungeon.hero.heroClass);
                            } else {
                                level = Dungeon.newLevel();
                                Badges.validateDeathFromFire();
                            }
                            Dungeon.switchLevel(level, level.up);
                            break;
                        case DOWN11:
                            down();
                            Level.DirectionValue = 104;
                            Dungeon.saveAll();
                            if (Statistics.Level104Visited) {
                                Dungeon.depth = Level.DirectionValue;
                                level = Dungeon.loadLevel(Dungeon.hero.heroClass);
                            } else {
                                level = Dungeon.newLevel();
                                Badges.validateDeathFromFire();
                            }
                            Dungeon.switchLevel(level, level.up);
                            break;
                        case UP1:
                            up();
                            Level.DirectionValue = 57;
                            Dungeon.saveAll();
                            if (Statistics.Level57Visited) {
                                Dungeon.depth = Level.DirectionValue;
                                level = Dungeon.loadLevel(Dungeon.hero.heroClass);
                            } else {
                                level = Dungeon.newLevel();
                                Badges.validateDeathFromFire();
                            }
                            Dungeon.switchLevel(level, level.down);
                            break;
                        case UP2:
                            up();
                            Level.DirectionValue = 22;
                            Dungeon.saveAll();
                            if (Statistics.Level22Visited) {
                                Dungeon.depth = Level.DirectionValue;
                                level = Dungeon.loadLevel(Dungeon.hero.heroClass);
                            } else {
                                level = Dungeon.newLevel();
                                Badges.validateDeathFromFire();
                            }
                            Dungeon.switchLevel(level, level.down);
                            break;
                        case UP3:
                            up();
                            Level.DirectionValue = 4;
                            Dungeon.saveAll();
                            if (Statistics.Level4Visited) {
                                Dungeon.depth = Level.DirectionValue;
                                level = Dungeon.loadLevel(Dungeon.hero.heroClass);
                            } else {
                                level = Dungeon.newLevel();
                                Badges.validateDeathFromFire();
                            }
                            Dungeon.switchLevel(level, level.down);
                            break;
                        case UP4:
                            up();
                            Level.DirectionValue = 98;
                            Dungeon.saveAll();
                            if (Statistics.Level98Visited) {
                                Dungeon.depth = Level.DirectionValue;
                                level = Dungeon.loadLevel(Dungeon.hero.heroClass);
                            } else {
                                level = Dungeon.newLevel();
                                Badges.validateDeathFromFire();
                            }
                            Dungeon.switchLevel(level, level.down);
                            break;
                        case UP5:
                            up();
                            Level.DirectionValue = 34;
                            Dungeon.saveAll();
                            if (Statistics.Level34Visited) {
                                Dungeon.depth = Level.DirectionValue;
                                level = Dungeon.loadLevel(Dungeon.hero.heroClass);
                            } else {
                                level = Dungeon.newLevel();
                                Badges.validateDeathFromFire();
                            }
                            Dungeon.switchLevel(level, level.down);
                            break;
                        case UP6:
                            up();
                            Level.DirectionValue = 23;
                            Dungeon.saveAll();
                            if (Statistics.Level23Visited) {
                                Dungeon.depth = Level.DirectionValue;
                                level = Dungeon.loadLevel(Dungeon.hero.heroClass);
                            } else {
                                level = Dungeon.newLevel();
                                Badges.validateDeathFromFire();
                            }
                            Dungeon.switchLevel(level, level.down);
                            break;
                        case UP7:
                            up();
                            Level.DirectionValue = 59;
                            Dungeon.saveAll();
                            if (Statistics.Level59Visited) {
                                Dungeon.depth = Level.DirectionValue;
                                level = Dungeon.loadLevel(Dungeon.hero.heroClass);
                            } else {
                                level = Dungeon.newLevel();
                                Badges.validateDeathFromFire();
                            }
                            Dungeon.switchLevel(level, level.down);
                            break;
                        //skipped to UP10 to make it make more sense, match down
                        case UP10:
                            up();
                            Level.DirectionValue = 36;
                            Dungeon.saveAll();
                            if (Statistics.Level36Visited) {
                                Dungeon.depth = Level.DirectionValue;
                                level = Dungeon.loadLevel(Dungeon.hero.heroClass);
                            } else {
                                level = Dungeon.newLevel();
                                Badges.validateDeathFromFire();
                            }
                            Dungeon.switchLevel(level, level.down);
                            break;
                        case UP11:
                            up();
                            Level.DirectionValue = 78;
                            Dungeon.saveAll();
                            if (Statistics.Level78Visited) {
                                Dungeon.depth = Level.DirectionValue;
                                level = Dungeon.loadLevel(Dungeon.hero.heroClass);
                            } else {
                                level = Dungeon.newLevel();
                                Badges.validateDeathFromFire();
                            }
                            Dungeon.switchLevel(level, level.down);
                            break;
                        case CONTINUE:
                            restore();
                            break;
                        case RESURRECT:
                            resurrect();
                            Level.DirectionValue = 1;
                            Dungeon.saveAll();
                                Dungeon.depth = Level.DirectionValue;
                                level = Dungeon.loadLevel(Dungeon.hero.heroClass);
                            Dungeon.switchLevel(level, level.south);
                            break;
                        case RETURN:
                            returnTo();
                            break;
                        case FALL:
                            fall();
                            Level.DirectionValue = 1;
                            if (Dungeon.depth >= Statistics.deepestFloor) {
                                level = Dungeon.newLevel();
                                Badges.validateDeathFromFire();
                            } else {
                                Dungeon.depth = Level.DirectionValue;
                                level = Dungeon.loadLevel(Dungeon.hero.heroClass);
                            }

                            Dungeon.switchLevel(level, level.south);
                            break;
                        case CLIMB:
                            break;
                    }

                    if ((Dungeon.depth % 5) == 0) {
                        Sample.INSTANCE.load( Assets.SND_BOSS );
                    }

                } catch (FileNotFoundException e) {

                    error = ERR_FILE_NOT_FOUND;

                } catch (Exception e ) {

                    error = ERR_GENERIC;

                }

                if (phase == Phase.STATIC && error == null) {
                    phase = Phase.FADE_OUT;
                    timeLeft = TIME_TO_FADE;
                }
            }
        };
        thread.start();
    }

    @Override
    public void update() {
        super.update();

        float p = timeLeft / TIME_TO_FADE;

        switch (phase) {

            case FADE_IN:
                message.alpha( 1 - p );
                if ((timeLeft -= Game.elapsed) <= 0) {
                    if (!thread.isAlive() && error == null) {
                        phase = Phase.FADE_OUT;
                        timeLeft = TIME_TO_FADE;
                    } else {
                        phase = Phase.STATIC;
                    }
                }
                break;

            case FADE_OUT:
                message.alpha( p );
                if (mode == Mode.CONTINUE || (mode == Mode.NORTH && Dungeon.depth == 1)) {
                    Music.INSTANCE.volume( p );
                }
                if ((timeLeft -= Game.elapsed) <= 0) {
                    Game.switchScene( GameScene.class );
                }
                break;

            case STATIC:
                if (error != null) {
                    add( new WndError( error ) {
                        public void onBackPressed() {
                            super.onBackPressed();
                            Game.switchScene( StartScene.class );
                        }
                    } );
                    error = null;
                }
                break;
        }
    }


    private void north() throws Exception {

        Actor.fixTime();
        if (Dungeon.hero == null) {
            Dungeon.init();
            if (noStory) {
                noStory = false;
            }
        } else {
            Dungeon.saveLevel();
        }
    }

    private void west() throws Exception {

        Actor.fixTime();
        if (Dungeon.hero == null) {
            Dungeon.init();
            if (noStory) {
                noStory = false;
            }
        } else {
            Dungeon.saveLevel();
        }
    }

    private void south() throws Exception {
        Actor.fixTime();
        if (Dungeon.hero == null) {
            Dungeon.init();
            if (noStory) {
                noStory = false;
            }
        } else {
            Dungeon.saveLevel();
        }
    }

    private void east() throws Exception {
        Actor.fixTime();
        if (Dungeon.hero == null) {
            Dungeon.init();
            if (noStory) {
                noStory = false;
            }
        } else {
            Dungeon.saveLevel();
        }
    }

    private void down() throws Exception {
        Actor.fixTime();
        if (Dungeon.hero == null) {
            Dungeon.init();
            if (noStory) {
                noStory = false;
            }
        } else {
            Dungeon.saveLevel();
        }
    }

    private void up() throws Exception {
        Actor.fixTime();
        if (Dungeon.hero == null) {
            Dungeon.init();
            if (noStory) {
                noStory = false;
            }
        } else {
            Dungeon.saveLevel();
        }
    }


    private void fall() throws Exception {

        Actor.fixTime();
        if (Dungeon.hero == null) {
            Dungeon.init();
            if (noStory) {
                Dungeon.chapters.add( WndStory.ID_START );
                noStory = false;
            }
        } else {
            Dungeon.saveLevel();
        }
    }


    private void returnTo() throws Exception {

        Actor.fixTime();

        Dungeon.saveLevel();
        Dungeon.depth = returnDepth;
        Level level = Dungeon.loadLevel( Dungeon.hero.heroClass );
        Dungeon.switchLevel( level, Level.resizingNeeded ? level.adjustPos( returnPos ) : returnPos );
    }

    private void restore() throws Exception {

        Actor.fixTime();

        Dungeon.loadGame( StartScene.curClass );
        if (Dungeon.depth == -1) {
            Dungeon.depth = Statistics.deepestFloor;
            Dungeon.switchLevel( Dungeon.loadLevel( StartScene.curClass ), -1 );
        } else {
            Level level = Dungeon.loadLevel( StartScene.curClass );
            Dungeon.switchLevel( level, Level.resizingNeeded ? level.adjustPos( Dungeon.hero.pos ) : Dungeon.hero.pos );
        }
    }

    private void resurrect() throws Exception {

        Actor.fixTime();
        Dungeon.saveLevel();

            Dungeon.hero.resurrect( Dungeon.depth );
            Dungeon.depth = 1;
            Level level = Dungeon.loadLevel(Dungeon.hero.heroClass);
            Dungeon.switchLevel( level, level.south);

        /*else {
            Dungeon.hero.resurrect( 1 );
            Dungeon.resetLevel();
        }*/
    }

    @Override
    protected void onBackPressed() {
        // Do nothing
    }
}

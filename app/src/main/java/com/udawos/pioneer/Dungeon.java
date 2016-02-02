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
package com.udawos.pioneer;

import com.udawos.noosa.Game;
import com.udawos.pioneer.actors.Actor;
import com.udawos.pioneer.actors.Char;
import com.udawos.pioneer.actors.buffs.Amok;
import com.udawos.pioneer.actors.buffs.Light;
import com.udawos.pioneer.actors.hero.Hero;
import com.udawos.pioneer.actors.hero.HeroClass;
import com.udawos.pioneer.items.Ankh;
import com.udawos.pioneer.items.Item;
import com.udawos.pioneer.items.ammunition.Ammo;
import com.udawos.pioneer.items.potions.Potion;
import com.udawos.pioneer.items.rings.Ring;
import com.udawos.pioneer.items.scrolls.Scroll;
import com.udawos.pioneer.items.wands.Wand;
import com.udawos.pioneer.levels.A010MountainLevel;
import com.udawos.pioneer.levels.A02GrassLevel;
import com.udawos.pioneer.levels.A03HillLevel;
import com.udawos.pioneer.levels.A04GrassLevel;
import com.udawos.pioneer.levels.A05HillLevel;
import com.udawos.pioneer.levels.A06GrassLevel;
import com.udawos.pioneer.levels.A07GrassLevel;
import com.udawos.pioneer.levels.A08GrassLevel;
import com.udawos.pioneer.levels.A09HillLevel;
import com.udawos.pioneer.levels.B011GrassLevel;
import com.udawos.pioneer.levels.B012HillLevel;
import com.udawos.pioneer.levels.B013HillLevel;
import com.udawos.pioneer.levels.B014HillLevel;
import com.udawos.pioneer.levels.B015MountainLevel;
import com.udawos.pioneer.levels.B016HillLevel;
import com.udawos.pioneer.levels.B017GrassLevel;
import com.udawos.pioneer.levels.B018GrassLevel;
import com.udawos.pioneer.levels.B019HillLevel;
import com.udawos.pioneer.levels.B020MountainLevel;
import com.udawos.pioneer.levels.C021HillLevel;
import com.udawos.pioneer.levels.C022HillLevel;
import com.udawos.pioneer.levels.C023RuinedTemple;
import com.udawos.pioneer.levels.C024HillLevel;
import com.udawos.pioneer.levels.C025MountainLevel;
import com.udawos.pioneer.levels.C026HillLevel;
import com.udawos.pioneer.levels.C027LakeLevel;
import com.udawos.pioneer.levels.C028HillLevel;
import com.udawos.pioneer.levels.C029HillLevel;
import com.udawos.pioneer.levels.C030MountainLevel;
import com.udawos.pioneer.levels.D031HillLevel;
import com.udawos.pioneer.levels.D032HillLevel;
import com.udawos.pioneer.levels.D033ShatteredTower;
import com.udawos.pioneer.levels.D034GrassLevel;
import com.udawos.pioneer.levels.D035PeasantLevel;
import com.udawos.pioneer.levels.D036MountainLevel;
import com.udawos.pioneer.levels.D037HillLevel;
import com.udawos.pioneer.levels.D038HillLevel;
import com.udawos.pioneer.levels.D039HillLevel;
import com.udawos.pioneer.levels.D040MountainLevel;
import com.udawos.pioneer.levels.DeadEndLevel;
import com.udawos.pioneer.levels.E041HillLevel;
import com.udawos.pioneer.levels.E042HillLevel;
import com.udawos.pioneer.levels.E043HillLevel;
import com.udawos.pioneer.levels.E044GrassLevel;
import com.udawos.pioneer.levels.E045GrassLevel;
import com.udawos.pioneer.levels.E046MountainLevel;
import com.udawos.pioneer.levels.E047HillLevel;
import com.udawos.pioneer.levels.E048MountainLevel;
import com.udawos.pioneer.levels.E049HillLevel;
import com.udawos.pioneer.levels.E050MountainLevel;
import com.udawos.pioneer.levels.F051MountainLevel;
import com.udawos.pioneer.levels.F052MountainLevel;
import com.udawos.pioneer.levels.F053MountainLevel;
import com.udawos.pioneer.levels.F054MountainLevel;
import com.udawos.pioneer.levels.F055MountainLevel;
import com.udawos.pioneer.levels.F056MountainLevel;
import com.udawos.pioneer.levels.F057MountainLevel;
import com.udawos.pioneer.levels.F058MountainLevel;
import com.udawos.pioneer.levels.F059MountainLevel;
import com.udawos.pioneer.levels.F060MountainLevel;
import com.udawos.pioneer.levels.G064Pub;
import com.udawos.pioneer.levels.H078ArmEntrance;
import com.udawos.pioneer.levels.H081Nest;
import com.udawos.pioneer.levels.H085DeadCity;
import com.udawos.pioneer.levels.I098MindEntrance;
import com.udawos.pioneer.levels.Level;
import com.udawos.pioneer.levels.Room;
import com.udawos.pioneer.levels.TundraLevel;
import com.udawos.pioneer.levels.U1UndergroundPassage;
import com.udawos.pioneer.levels.U2TheEye;
import com.udawos.pioneer.levels.U3TheSword;
import com.udawos.pioneer.levels.U4TheArm;
import com.udawos.pioneer.levels.U5TheMind;
import com.udawos.pioneer.levels.U6BearCave;
import com.udawos.pioneer.levels.U7PaleoCave;
import com.udawos.pioneer.levels.U8ExplorerCave;
import com.udawos.pioneer.scenes.GameScene;
import com.udawos.pioneer.scenes.StartScene;
import com.udawos.pioneer.ui.QuickSlot;
import com.udawos.pioneer.utils.BArray;
import com.udawos.pioneer.utils.Utils;
import com.udawos.pioneer.windows.WndResurrect;
import com.udawos.utils.Bundlable;
import com.udawos.utils.Bundle;
import com.udawos.utils.PathFinder;
import com.udawos.utils.Random;
import com.udawos.utils.SparseArray;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;

public class Dungeon {

	public static int potionOfStrength;
	public static int scrollsOfUpgrade;
	public static int scrollsOfEnchantment;
	public static boolean dewVial;		// true if the dew vial can be spawned
	public static int transmutation;	// depth number for a well of transmutation
	
	public static int challenges;
	
	public static Hero hero;
	public static Level level;
	
	public static int depth;

	public static int gold;


	// Reason of death
	public static String resultDescription;
	
	public static HashSet<Integer> chapters;
	
	// Hero's field of view
	public static boolean[] visible = new boolean[Level.LENGTH];
	
	//public static boolean nightMode;
	
	public static SparseArray<ArrayList<Item>> droppedItems;
	
	public static void init() {

		challenges = Pioneer.challenges();
		
		Actor.clear();
		
		PathFinder.setMapSize(Level.WIDTH, Level.HEIGHT);

        Ammo.initLabels();
        Scroll.initLabels();
		Potion.initColors();
		Wand.initWoods();
		Ring.initGems();
		
		Statistics.reset();
		Journal.reset();
		
		depth = 0;
		gold = 0;

		droppedItems = new SparseArray<ArrayList<Item>>();
		
		potionOfStrength = 0;
		scrollsOfUpgrade = 0;
		scrollsOfEnchantment = 0;
		dewVial = true;
		transmutation = Random.IntRange( 6, 14 );
		
		chapters = new HashSet<Integer>();

		//Blacksmith.Quest.reset();
		//Imp.Quest.reset();

		
		QuickSlot.primaryValue = null;
		QuickSlot.secondaryValue = null;

		hero = new Hero();
		hero.live();
		
		Badges.reset();
		
		StartScene.curClass.initHero( hero );
	}
	
	public static boolean isChallenged( int mask ) {
		return (challenges & mask) != 0;
	}

public static Level newLevel() {

    Dungeon.level = null;
    Actor.clear();

    depth = Level.DirectionValue;

    if (depth > Statistics.deepestFloor) {
        Statistics.deepestFloor = depth;}

    if (depth == 1) {
        Statistics.Level1Visited = true;
    }
    if (depth == 2) {
        Statistics.Level2Visited = true;
    }
    if (depth == 3) {
        Statistics.Level3Visited = true;
    }
    if (depth == 4) {
        Statistics.Level4Visited = true;
    }
    if (depth == 5) {
        Statistics.Level5Visited = true;
    }
    if (depth == 6) {
        Statistics.Level6Visited = true;
    }
    if (depth == 7) {
        Statistics.Level7Visited = true;
    }
    if (depth == 8) {
        Statistics.Level8Visited = true;
    }
    if (depth == 9) {
        Statistics.Level9Visited = true;
    }
    if (depth == 10) {
        Statistics.Level10Visited = true;
    }
    if (depth == 11) {
        Statistics.Level11Visited = true;
    }
    if (depth == 12) {
        Statistics.Level12Visited = true;
    }
    if (depth == 13) {
        Statistics.Level13Visited = true;
    }
    if (depth == 14) {
        Statistics.Level14Visited = true;
    }
    if (depth == 15) {
        Statistics.Level15Visited = true;
    }
    if (depth == 16) {
        Statistics.Level16Visited = true;
    }
    if (depth == 17) {
        Statistics.Level17Visited = true;
    }
    if (depth == 18) {
        Statistics.Level18Visited = true;
    }
    if (depth == 19) {
        Statistics.Level19Visited = true;
    }
    if (depth == 20) {
        Statistics.Level20Visited = true;
    }
    if (depth == 21) {
        Statistics.Level21Visited = true;
    }
    if (depth == 22) {
        Statistics.Level22Visited = true;
    }
    if (depth == 23) {
        Statistics.Level23Visited = true;
    }
    if (depth == 24) {
        Statistics.Level24Visited = true;
    }
    if (depth == 25) {
        Statistics.Level25Visited = true;
    }
    if (depth == 26) {
        Statistics.Level26Visited = true;
    }
    if (depth == 27) {
        Statistics.Level27Visited = true;

    }
    if (depth == 28) {
        Statistics.Level28Visited = true;

    }
    if (depth == 29) {
        Statistics.Level29Visited = true;

    }
    if (depth == 30) {
        Statistics.Level30Visited = true;

    }
    if (depth == 31) {
        Statistics.Level31Visited = true;

    }
    if (depth == 32) {
        Statistics.Level32Visited = true;

    }
    if (depth == 33) {
        Statistics.Level33Visited = true;

    }
    if (depth == 34) {
        Statistics.Level34Visited = true;

    }
    if (depth == 35) {
        Statistics.Level35Visited = true;

    }
    if (depth == 36) {
        Statistics.Level36Visited = true;

    }
    if (depth == 37) {
        Statistics.Level37Visited = true;

    }
    if (depth == 38) {
        Statistics.Level38Visited = true;

    }
    if (depth == 39) {
        Statistics.Level39Visited = true;

    }
    if (depth == 40) {
        Statistics.Level40Visited = true;

    }
    if (depth == 41) {
        Statistics.Level41Visited = true;

    }
    if (depth == 42) {
        Statistics.Level42Visited = true;

    }
    if (depth == 43) {
        Statistics.Level43Visited = true;

    }
    if (depth == 44) {
        Statistics.Level44Visited = true;

    }
    if (depth == 45) {
        Statistics.Level45Visited = true;

    }
    if (depth == 46) {
        Statistics.Level46Visited = true;

    }
    if (depth == 47) {
        Statistics.Level47Visited = true;

    }
    if (depth == 48) {
        Statistics.Level48Visited = true;

    }
    if (depth == 49) {
        Statistics.Level49Visited = true;

    }
    if (depth == 50) {
        Statistics.Level50Visited = true;

    }
    if (depth == 51) {
        Statistics.Level51Visited = true;

    }
    if (depth == 52) {
        Statistics.Level52Visited = true;

    }
    if (depth == 53) {
        Statistics.Level53Visited = true;

    }
    if (depth == 54) {
        Statistics.Level54Visited = true;

    }
    if (depth == 55) {
        Statistics.Level55Visited = true;

    }
    if (depth == 56) {
        Statistics.Level56Visited = true;

    }
    if (depth == 57) {
        Statistics.Level57Visited = true;

    }
    if (depth == 58) {
        Statistics.Level58Visited = true;

    }
    if (depth == 59) {
        Statistics.Level59Visited = true;

    }
    if (depth == 60) {
        Statistics.Level60Visited = true;

    }
    if (depth == 61) {
        Statistics.Level61Visited = true;

    }
    if (depth == 62) {
        Statistics.Level62Visited = true;

    }
    if (depth == 63) {
        Statistics.Level63Visited = true;

    }
    if (depth == 64) {
        Statistics.Level64Visited = true;

    }
    if (depth == 65) {
        Statistics.Level65Visited = true;

    }
    if (depth == 66) {
        Statistics.Level66Visited = true;

    }
    if (depth == 67) {
        Statistics.Level67Visited = true;

    }
    if (depth == 68) {
        Statistics.Level68Visited = true;

    }
    if (depth == 69) {
        Statistics.Level69Visited = true;

    }
    if (depth == 70) {
        Statistics.Level70Visited = true;

    }
    if (depth == 71) {
        Statistics.Level71Visited = true;

    }
    if (depth == 72) {
        Statistics.Level72Visited = true;

    }
    if (depth == 73) {
        Statistics.Level73Visited = true;

    }
    if (depth == 74) {
        Statistics.Level74Visited = true;

    }
    if (depth == 75) {
        Statistics.Level75Visited = true;

    }
    if (depth == 76) {
        Statistics.Level76Visited = true;

    }
    if (depth == 77) {
        Statistics.Level77Visited = true;

    }
    if (depth == 78) {
        Statistics.Level78Visited = true;

    }
    if (depth == 79) {
        Statistics.Level79Visited = true;

    }
    if (depth == 80) {
        Statistics.Level80Visited = true;

    }
    if (depth == 81) {
        Statistics.Level81Visited = true;

    }
    if (depth == 82) {
        Statistics.Level82Visited = true;

    }
    if (depth == 83) {
        Statistics.Level83Visited = true;

    }
    if (depth == 84) {
        Statistics.Level84Visited = true;

    }
    if (depth == 85) {
        Statistics.Level85Visited = true;

    }
    if (depth == 86) {
        Statistics.Level86Visited = true;

    }
    if (depth == 87) {
        Statistics.Level87Visited = true;

    }
    if (depth == 88) {
        Statistics.Level88Visited = true;

    }
    if (depth == 89) {
        Statistics.Level89Visited = true;

    }
    if (depth == 90) {
        Statistics.Level90Visited = true;

    }
    if (depth == 91) {
        Statistics.Level91Visited = true;

    }
    if (depth == 92) {
        Statistics.Level92Visited = true;

    }
    if (depth == 93) {
        Statistics.Level93Visited = true;

    }
    if (depth == 94) {
        Statistics.Level94Visited = true;

    }
    if (depth == 95) {
        Statistics.Level95Visited = true;

    }
    if (depth == 96) {
        Statistics.Level96Visited = true;

    }
    if (depth == 97) {
        Statistics.Level97Visited = true;

    }
    if (depth == 98) {
        Statistics.Level98Visited = true;

    }
    if (depth == 99) {
        Statistics.Level99Visited = true;

    }
    if (depth == 100) {
        Statistics.Level100Visited = true;

    }
    if (depth == 101) {
        Statistics.Level101Visited = true;
    }
    if (depth == 102) {
        Statistics.Level102Visited = true;
    }
    if (depth == 103) {
        Statistics.Level103Visited = true;
    }
    if (depth == 104) {
        Statistics.Level104Visited = true;
    }
    if (depth == 105) {
        Statistics.Level105Visited = true;
    }
    if (depth == 106) {
        Statistics.Level106Visited = true;
    }
    if (depth == 107) {
        Statistics.Level107Visited = true;
    }
    if (depth == 108) {
        Statistics.Level108Visited = true;
    }







    Arrays.fill( visible, false );

    Level level;
    switch (depth) {
        case 1:
            level = new E041HillLevel();
            break;
        case 2:
            level = new A02GrassLevel();
            break;
        case 3:
            level = new A03HillLevel();
            break;
        case 4:
            level = new A04GrassLevel();
            break;
        case 5:
            level = new A05HillLevel();
            break;
        case 6:
            level = new A06GrassLevel();
            break;
        case 7:
            level = new A07GrassLevel();
            break;
        case 8:
            level = new A08GrassLevel();
            break;
        case 9:
            level = new A09HillLevel();
            break;
        case 10:
            level = new A010MountainLevel();
            break;
        case 11:
            level = new B011GrassLevel();
            break;
        case 12:
            level = new B012HillLevel();
            break;
        case 13:
            level = new B013HillLevel();
            break;
        case 14:
            level = new B014HillLevel();
            break;
        case 15:
            level = new B015MountainLevel();
            break;
        case 16:
            level = new B016HillLevel();
            break;
        case 17:
            level = new B017GrassLevel();
            break;
        case 18:
            level = new B018GrassLevel();
            break;
        case 19:
            level = new B019HillLevel();
            break;
        case 20:
            level = new B020MountainLevel();
            break;
        case 21:
            level = new C021HillLevel();
            break;
        case 22:
            level = new C022HillLevel();
            break;
        case 23:
            level = new C023RuinedTemple();
            break;
        case 24:
            level = new C024HillLevel();
            break;
        case 25:
            level = new C025MountainLevel();
            break;
        case 26:
            level = new C026HillLevel();
            break;
        case 27:
            level = new C027LakeLevel();
            break;
        case 28:
            level = new C028HillLevel();
            break;
        case 29:
            level = new C029HillLevel();
            break;
        case 30:
            level = new C030MountainLevel();
            break;
        case 31:
            level = new D031HillLevel();
            break;
        case 32:
            level = new D032HillLevel();
            break;
        case 33:
            level = new D033ShatteredTower();
            break;
        case 34:
            level = new D034GrassLevel();
            break;
        case 35:
            level = new D035PeasantLevel();
            break;
        case 36:
            level = new D036MountainLevel();
            break;
        case 37:
            level = new D037HillLevel();
            break;
        case 38:
            level = new D038HillLevel();
            break;
        case 39:
            level = new D039HillLevel();
            break;
        case 40:
            level = new D040MountainLevel();
            break;
        case 41:
            level = new E041HillLevel();
            break;
        case 42:
            level = new E042HillLevel();
            break;
        case 43:
            level = new E043HillLevel();
            break;
        case 44:
            level = new E044GrassLevel();
            break;
        case 45:
            level = new E045GrassLevel();
            break;
        case 46:
            level = new E046MountainLevel();
            break;
       //entrance to underground passage at 47
        case 47:
            level = new E047HillLevel();
            break;
        case 48:
            level = new E048MountainLevel();
            break;
        case 49:
            level = new E049HillLevel();
            break;
        case 50:
            level = new E050MountainLevel();
            break;
        case 51:
            level = new F051MountainLevel();
            break;
        case 52:
            level = new F052MountainLevel();
            break;
        case 53:
            level = new F053MountainLevel();
            break;
        case 54:
            level = new F054MountainLevel();
            break;
        case 55:
            level = new F055MountainLevel();
            break;
        case 56:
            level = new F056MountainLevel();
            break;
        case 57:
            level = new F057MountainLevel();
            break;
        case 58:
            level = new F058MountainLevel();
            break;
        case 59:
            level = new F059MountainLevel();
            break;
        case 60:
            level = new F060MountainLevel();
            break;
        case 61:
            level =  new TundraLevel();
            break;
        case 62:
            level =  new TundraLevel();
            break;
        case 63:
            level = new TundraLevel();
            break;
        case 64:
            level = new G064Pub();
            break;
        case 65:
            level = new TundraLevel();
            break;
        case 66:
            level = new TundraLevel();
            break;
        case 67:
            level = new TundraLevel();
            break;
        case 68:
            level = new TundraLevel();
            break;
        case 69:
            level = new TundraLevel();
            break;
        case 70:
            level = new TundraLevel();
            break;
        case 71:
            level =  new TundraLevel();
            break;
        case 72:
            level =  new TundraLevel();
            break;
        case 73:
            level = new TundraLevel();
            break;
        case 74:
            level = new TundraLevel();;
            break;
        case 75:
            level = new TundraLevel();
            break;
        case 76:
            level = new TundraLevel();
            break;
        case 77:
            level = new TundraLevel();
            break;
        case 78:
            level = new H078ArmEntrance();
            break;
        case 79:
            level = new TundraLevel();
            break;
        case 80:
            level = new TundraLevel();
            break;
        case 81:
            level = new H081Nest();
            break;
        case 82:
            level = new TundraLevel();
            break;
        case 83:
            level = new TundraLevel();
            break;
        case 84:
            level = new TundraLevel();;
            break;
        case 85:
            level = new H085DeadCity();
            break;
        case 86:
            level = new TundraLevel();
            break;
        case 87:
            level = new TundraLevel();
            break;
        case 88:
            level = new TundraLevel();
            break;
        case 89:
            level = new TundraLevel();
            break;
        case 90:
            level = new TundraLevel();
            break;
        case 91:
            level = new TundraLevel();
            break;
        case 92:
            level = new TundraLevel();
            break;
        case 93:
            level = new TundraLevel();
            break;
        case 94:
            level = new TundraLevel();
            break;
        case 95:
            level = new TundraLevel();
            break;
        case 96:
            level = new TundraLevel();
            break;
        case 97:
            level = new TundraLevel();
            break;
        case 98:
            level = new I098MindEntrance();
            break;
        case 99:
            level = new TundraLevel();
            break;
        case 100:
            level = new TundraLevel();
            break;
        case 101:
            level = new U1UndergroundPassage();
            break;
        case 102:
            level = new U2TheEye();
            break;
        case 103:
            level = new U3TheSword();
            break;
        case 104:
            level = new U4TheArm();
            break;
             //The Mind
        case 105:
            level = new U5TheMind();
            break;
            //Paleo Cave
        case 106:
            level = new U7PaleoCave();
            break;
        case 107:
            level = new U6BearCave();
            break;
        case 108:
            level = new U8ExplorerCave();
            break;
        default:
            level = new DeadEndLevel();
            Statistics.deepestFloor--;
    }

    level.create();

    Statistics.qualifiedForNoKilling = !bossLevel();

    return level;
}
	
	public static void resetLevel() {
		
		Actor.clear();
		
		Arrays.fill(visible, false);
	    switchLevel(level, level.east);
	}
	
	public static boolean shopOnLevel() {
		return depth == 2 || depth == 11 || depth == 16;
	}
	
	public static boolean bossLevel() {
		return bossLevel( depth );
	}
	
	public static boolean bossLevel( int depth ) {
		return depth == 1 || depth == 2 || depth == 3 || depth == 4 || depth == 5;
	}
	
	@SuppressWarnings("deprecation")
	public static void switchLevel( final Level level, int pos ) {
		
		//nightMode = new Date().getHours() < 7;
		
		Dungeon.level = level;
		Actor.init();
		
		Actor respawner = level.respawner();
		if (respawner != null) {
			Actor.add( level.respawner() );
		}

        hero.pos = pos != -1 ? pos : level.west;


		Light light = hero.buff( Light.class );
		hero.viewDistance = light == null ? level.viewDistance : Math.max( Light.DISTANCE, level.viewDistance );
		
		observe();
	}
	
	public static void dropToChasm( Item item ) {
		int depth = Dungeon.depth + 1;
		ArrayList<Item> dropped = (ArrayList<Item>)Dungeon.droppedItems.get( depth );
		if (dropped == null) {
			Dungeon.droppedItems.put( depth, dropped = new ArrayList<Item>() ); 
		}
		dropped.add( item );
	}
	


	
	private static final String WR_GAME_FILE	= "warrior.dat";
	private static final String WR_DEPTH_FILE	= "warrior%d.dat";
	
	private static final String VERSION		= "version";
	private static final String CHALLENGES	= "challenges";
	private static final String HERO		= "hero";
	private static final String GOLD		= "gold";
    private static final String WEIGHT      = "weight";
	private static final String DEPTH		= "depth";
	private static final String LEVEL		= "level";
	private static final String DROPPED		= "dropped%d";
	private static final String POS			= "potionsOfStrength";
	private static final String SOU			= "scrollsOfEnhancement";
	private static final String SOE			= "scrollsOfEnchantment";
	private static final String DV			= "dewVial";
	private static final String WT			= "transmutation";
	private static final String CHAPTERS	= "chapters";
	private static final String QUESTS		= "quests";
	private static final String BADGES		= "badges";

	
	public static String gameFile( HeroClass cl ) {
			return WR_GAME_FILE;
	}
	
	private static String depthFile( HeroClass cl ) {
			return WR_DEPTH_FILE;
	}
	
	public static void saveGame( String fileName ) throws IOException {
		try {
			Bundle bundle = new Bundle();
			
			bundle.put( VERSION, Game.version );
			bundle.put( CHALLENGES, challenges );
			bundle.put( HERO, hero );
			bundle.put( GOLD, gold );
           // bundle.put( WEIGHT, weight);
			bundle.put( DEPTH, depth );

			
			for (int d : droppedItems.keyArray()) {
				bundle.put( String.format( DROPPED, d ), droppedItems.get( d ) );
			}
			
			bundle.put( POS, potionOfStrength );
			bundle.put( SOU, scrollsOfUpgrade );
			bundle.put( SOE, scrollsOfEnchantment );
			bundle.put( DV, dewVial );
			bundle.put( WT, transmutation );


			
			int count = 0;
			int ids[] = new int[chapters.size()];
			for (Integer id : chapters) {
				ids[count++] = id;
			}
			bundle.put(CHAPTERS, ids);
			
			Bundle quests = new Bundle();
			/*Wandmaker	.Quest.storeInBundle( quests );*/
			//Blacksmith	.Quest.storeInBundle( quests );
			//Imp			.Quest.storeInBundle(quests);
			bundle.put(QUESTS, quests);
			
			Room.storeRoomsInBundle(bundle);
			
			Statistics.storeInBundle(bundle);
			Journal.storeInBundle(bundle);
			
			QuickSlot.save(bundle);
			
			Scroll.save(bundle);
			Potion.save(bundle);
			Wand.save(bundle);
			Ring.save(bundle);

			OutputStream output = Game.instance.openFileOutput( fileName, Game.MODE_PRIVATE );
			Bundle.write( bundle, output );
			output.close();
			
		} catch (Exception e) {

			GamesInProgress.setUnknown( hero.heroClass );
		}
	}
	
	public static void saveLevel() throws IOException {
		Bundle bundle = new Bundle();
		bundle.put( LEVEL, level );
		
		OutputStream output = Game.instance.openFileOutput( Utils.format( depthFile( hero.heroClass ), depth ), Game.MODE_PRIVATE );
		Bundle.write( bundle, output );
		output.close();
	}
	
	public static void saveAll() throws IOException {
		if (hero.isAlive()) {
			
			Actor.fixTime();
			saveGame( gameFile( hero.heroClass ) );
			saveLevel();
			
			GamesInProgress.set( hero.heroClass, depth, hero.lvl, challenges != 0 );
			
		} else if (WndResurrect.instance != null) {
			
			WndResurrect.instance.hide();
			Hero.reallyDie( WndResurrect.causeOfDeath );
			
		}
	}
	
	public static void loadGame( HeroClass cl ) throws IOException {
		loadGame( gameFile( cl ), true );
	}
	
	public static void loadGame( String fileName ) throws IOException {
		loadGame( fileName, false );
	}
	
	public static void loadGame( String fileName, boolean fullLoad ) throws IOException {
		
		Bundle bundle = gameBundle( fileName );
		
		Dungeon.challenges = bundle.getInt( CHALLENGES );
		
		Dungeon.level = null;
		Dungeon.depth = -1;
		
		if (fullLoad) {
			PathFinder.setMapSize( Level.WIDTH, Level.HEIGHT );
		}

        Ammo.restore( bundle );
		Scroll.restore( bundle );
		Potion.restore( bundle );
		Wand.restore( bundle );
		Ring.restore( bundle );
       ;

		dewVial = bundle.getBoolean( DV );
		transmutation = bundle.getInt( WT );
		
		if (fullLoad) {
			chapters = new HashSet<Integer>();
			int ids[] = bundle.getIntArray( CHAPTERS );
			if (ids != null) {
				for (int id : ids) {
					chapters.add( id );
				}
			}
			
			Bundle quests = bundle.getBundle( QUESTS );
			if (!quests.isNull()) {
				/*Wandmaker.Quest.restoreFromBundle( quests );*/
				//Blacksmith.Quest.restoreFromBundle( quests );
				//Imp.Quest.restoreFromBundle( quests );
			} else {
				/*Wandmaker.Quest.reset();*/
				//Blacksmith.Quest.reset();
				//Imp.Quest.reset();
			}
			
			Room.restoreRoomsFromBundle( bundle );
		}
		
		Bundle badges = bundle.getBundle( BADGES );
		if (!badges.isNull()) {
			Badges.loadLocal( badges );
		} else {
			Badges.reset();
		}
		
		QuickSlot.restore( bundle );
		
		@SuppressWarnings("unused")
		String version = bundle.getString( VERSION );
		
		hero = null;
		hero = (Hero)bundle.get( HERO );
		
		QuickSlot.compress();
		
		gold = bundle.getInt( GOLD );
        //weight = bundle.getInt(WEIGHT);
		depth = bundle.getInt( DEPTH );
		
		Statistics.restoreFromBundle( bundle );
		Journal.restoreFromBundle( bundle );
		
		droppedItems = new SparseArray<ArrayList<Item>>();
		for (int i=2; i <= Statistics.deepestFloor + 1; i++) {
			ArrayList<Item> dropped = new ArrayList<Item>();
			for (Bundlable b : bundle.getCollection( String.format( DROPPED, i ) ) ) {
				dropped.add( (Item)b );
			}
			if (!dropped.isEmpty()) {
				droppedItems.put( i, dropped );
			}
		}
	}
	
	public static Level loadLevel( HeroClass cl ) throws IOException {
		
		Dungeon.level = null;
		Actor.clear();
		
		InputStream input = Game.instance.openFileInput( Utils.format( depthFile( cl ), depth ) ) ;
		Bundle bundle = Bundle.read( input );
		input.close();
		
		return (Level)bundle.get( "level" );
	}
	
	public static void deleteGame( HeroClass cl, boolean deleteLevels ) {
		
		Game.instance.deleteFile( gameFile( cl ) );
		
		if (deleteLevels) {
			int depth = 1;
			while (Game.instance.deleteFile( Utils.format( depthFile( cl ), depth ) )) {
				depth++;
			}
		}
		
		GamesInProgress.delete( cl );
	}
	
	public static Bundle gameBundle( String fileName ) throws IOException {
		
		InputStream input = Game.instance.openFileInput( fileName );
		Bundle bundle = Bundle.read( input );
		input.close();
		
		return bundle;
	}
	
	public static void preview( GamesInProgress.Info info, Bundle bundle ) {
		info.depth = bundle.getInt( DEPTH );
		info.challenges = (bundle.getInt( CHALLENGES ) != 0);
		if (info.depth == -1) {
			info.depth = bundle.getInt( "maxDepth" );	// FIXME
		}
		Hero.preview( info, bundle.getBundle( HERO ) );
	}
	
	public static void fail( String desc ) {
		resultDescription = desc;
		if (hero.belongings.getItem( Ankh.class ) == null) { 
			Rankings.INSTANCE.submit( false );
		}
	}
	
	public static void win( String desc ) {
		
		hero.belongings.identify();
		
		if (challenges != 0) {
			Badges.validateChampion();
		}
		
		resultDescription = desc;
		Rankings.INSTANCE.submit( true );
	}
	
	public static void observe() {

		if (level == null) {
			return;
		}
		
		level.updateFieldOfView( hero );
		System.arraycopy( Level.fieldOfView, 0, visible, 0, visible.length );
		
		BArray.or( level.visited, visible, level.visited );
		
		GameScene.afterObserve();
	}
	
	private static boolean[] passable = new boolean[Level.LENGTH];
	
	public static int findPath( Char ch, int from, int to, boolean pass[], boolean[] visible ) {
		
		if (Level.adjacent( from, to )) {
			return Actor.findChar( to ) == null && (pass[to] || Level.avoid[to]) ? to : -1;
		}
		
		if (ch.flying || ch.buff( Amok.class ) != null) {
			BArray.or( pass, Level.avoid, passable );
		} else {
			System.arraycopy( pass, 0, passable, 0, Level.LENGTH );
		}
		
		for (Actor actor : Actor.all()) {
			if (actor instanceof Char) {
				int pos = ((Char)actor).pos;
				if (visible[pos]) {
					passable[pos] = false;
				}
			}
		}
		
		return PathFinder.getStep( from, to, passable );
		
	}
	
	public static int flee( Char ch, int cur, int from, boolean pass[], boolean[] visible ) {
		
		if (ch.flying) {
			BArray.or( pass, Level.avoid, passable );
		} else {
			System.arraycopy( pass, 0, passable, 0, Level.LENGTH );
		}
		
		for (Actor actor : Actor.all()) {
			if (actor instanceof Char) {
				int pos = ((Char)actor).pos;
				if (visible[pos]) {
					passable[pos] = false;
				}
			}
		}
		passable[cur] = true;
		
		return PathFinder.getStepBack(cur, from, passable);
		
	}



}

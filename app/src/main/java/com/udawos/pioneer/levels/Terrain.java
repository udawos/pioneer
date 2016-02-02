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
package com.udawos.pioneer.levels;

public class Terrain {

	public static final int CHASM			= 0;
	public static final int EMPTY			= 1;
	public static final int GRASS			= 2;
	public static final int HILL		    = 3;
	public static final int WALL			= 4;
	public static final int DOOR			= 5;
	public static final int OPEN_DOOR		= 6;
    public static final int ENTRANCE        = 7;
    public static final int EXIT            = 8;
	public static final int EMBERS			= 9;
	public static final int LOCKED_DOOR		= 10;
	public static final int PEDESTAL		= 11;
	public static final int WALL_DECO		= 12;
	public static final int RUBBLE		    = 13;
	public static final int EMPTY_SP		= 14;
	public static final int HIGH_GRASS		= 15;
	public static final int MOUNTAIN_CORNER_SE	= 24;
	public static final int RUINED_WALL_W		= 25;
	public static final int RUINED_WALL	    = 26;
    public static final int RUINED_WALL_E   =27;
	public static final int SIGN			= 29;
    public static final int STATUE_SP       = 36;
	public static final int WELL			= 37;
	public static final int STATUE			= 38;
	public static final int BOOKSHELF		= 41;
	public static final int ALCHEMY			= 42;
	public static final int CHASM_FLOOR		= 43;
	public static final int CHASM_FLOOR_SP	= 44;
	public static final int CHASM_WALL		= 45;
	public static final int CHASM_WATER		= 46;
    public static final int CLIFF_SE_CORNER = 47;
    public static final int TRACKS          = 48;
    public static final int BED             = 49;
    public static final int CHAIR           = 50;
    public static final int DRESSER         = 51;
    public static final int TABLE           = 52;
    public static final int BARREL          = 53;
    public static final int DESK            = 54;
    public static final int SWITCH_ON       = 55;
    public static final int SWITCH_OFF      = 56;
    public static final int DROPPOD_NORTH   = 57;
    public static final int DROPPOD_EAST    = 58;
    public static final int DROPPOD_SOUTH    = 59;
    public static final int DROPPOD_WEST     = 60;
    public static final int DROPPOD_CLOSED  = 96;
    public static final int DROPPOD_OPEN    = 97;
    public static final int NORTH           = 98;
    public static final int SOUTH           = 99;
    public static final int WEST            = 100;
    public static final int EAST            = 101;
    public static final int CLIFF_NW_CORNER = 109;
    public static final int CLIFF_NE_CORNER = 110;
    public static final int CLIFF_N_SIDE    = 111;
    public static final int CLIFF_W_SIDE    = 102;
    public static final int CLIFF_E_SIDE    = 103;




   /* //Anomaly tiles
    public static final int BOUNCE_PERIMETER = 54;
    //Bounces player 3 squares back
    public static final int BOUNCE_CENTER = 55;
    //Artifact spawn location -Increase jump distance?

    public static final int VORTEX_PERIMETER = 56;
    //Sucks player in 1 square
    public static final int VORTEX_INTERIOR = 57;
    //Sucks player in 3 squares

    public static final int BURNER_PERIMETER = 58;
    //Sets player on fire for 1 s
    public static final int BURNER_INTERIOR = 59;
    //Sets player on fire for 2 s
    */




	public static final int MOUNTAIN_CORNER_NW				= 16;
	public static final int MOUNTAIN_N				= 17;
	public static final int MOUNTAIN_CORNER_NE		= 18;
	public static final int MOUNTAIN_W				= 19;
	public static final int CLIFF_SW_CORNER 		= 20;
	public static final int MOUNTAIN_E			= 21;
	public static final int MOUNTAIN_CORNER_SW	= 22;
	public static final int MOUNTAIN_S			= 23;
	public static final int SECRET_RUINED_WALL_E		= 28;
	public static final int TRIPWIRE				= 30;
	public static final int SECRET_TRIPWIRE		= 31;
	public static final int MOUNTAIN_ELBOW_SW		= 32;
	public static final int MOUNTAIN_ELBOW_SE	    = 33;
	public static final int MOUNTAIN_ELBOW_NW	     = 34;
	public static final int MOUNTAIN_ELBOW_NE	    = 35;
	public static final int SUMMONING_TRAP			= 39;
	public static final int SECRET_SUMMONING_TRAP	= 40;
	
	public static final int WATER_TILES	= 80;
	public static final int WATER		= 95;

    public static final int GRADUAL_SLOPE_TILES	= 64;
    public static final int GRADUAL_SLOPE		= 79;



	public static final int PASSABLE		= 0x01;
	public static final int LOS_BLOCKING	= 0x02;
	public static final int FLAMABLE		= 0x04;
	public static final int SECRET			= 0x08;
	public static final int SOLID			= 0x10;
	public static final int AVOID			= 0x20;
    public static final int LIQUID			= 0x40;
	public static final int PIT				= 0x80;


	public static final int UNSTITCHABLE	= 0x100;

	
	public static final int[] flags = new int[256];
	static {
		flags[CHASM]		= AVOID	| PIT									| UNSTITCHABLE;
		flags[EMPTY]		= PASSABLE;
		flags[GRASS]		= PASSABLE | FLAMABLE;
		flags[HILL]	        =  PASSABLE;
		flags[WATER]		= AVOID | LIQUID 				                | UNSTITCHABLE;
        flags[GRADUAL_SLOPE] = PASSABLE                  			        | UNSTITCHABLE;
		flags[WALL]			= LOS_BLOCKING | SOLID 							| UNSTITCHABLE;
		flags[DOOR]			= PASSABLE | LOS_BLOCKING | FLAMABLE | SOLID	| UNSTITCHABLE;
		flags[OPEN_DOOR]	= PASSABLE | FLAMABLE 							| UNSTITCHABLE;
        flags[ENTRANCE]     = PASSABLE;
        flags[EXIT]         = PASSABLE;;
		flags[EMBERS]		= PASSABLE;
		flags[LOCKED_DOOR]	= LOS_BLOCKING | SOLID 							| UNSTITCHABLE;
		flags[PEDESTAL]		= LOS_BLOCKING | SOLID |    FLAMABLE			| UNSTITCHABLE;
		flags[WALL_DECO]	= flags[WALL];
		flags[RUBBLE]	    = LOS_BLOCKING | SOLID;
		flags[EMPTY_SP]		= flags[EMPTY]									| UNSTITCHABLE;
		flags[HIGH_GRASS]	= PASSABLE | LOS_BLOCKING | FLAMABLE;
		flags[MOUNTAIN_CORNER_SE]	= SOLID;
		flags[RUINED_WALL_W]	= SOLID;
		flags[RUINED_WALL]  = LOS_BLOCKING | SOLID;
		flags[SIGN]			= PASSABLE | FLAMABLE;
		flags[WELL]			= AVOID;
		flags[STATUE]		= LOS_BLOCKING | SOLID;
		flags[BOOKSHELF]	= SOLID									| UNSTITCHABLE;
		flags[ALCHEMY]		= AVOID;
        flags[RUBBLE]        = LOS_BLOCKING |SOLID                  |UNSTITCHABLE;
        flags[TRACKS]       = PASSABLE;
        flags[BED]          = PASSABLE;
        flags[CHAIR]         = PASSABLE;
        flags[DRESSER]      = SOLID;
        flags[TABLE]        = SOLID;
        flags[BARREL]       = SOLID;
        flags[DESK]         = SOLID;
        flags[SWITCH_ON]    = AVOID | PASSABLE;
        flags[SWITCH_OFF]   = flags[SWITCH_ON];
        flags[DROPPOD_NORTH] = PASSABLE;
        flags[DROPPOD_EAST] = flags[DROPPOD_NORTH];
        flags[DROPPOD_SOUTH] = flags[DROPPOD_NORTH];
        flags[DROPPOD_WEST] = flags[DROPPOD_NORTH];
        flags[DROPPOD_CLOSED] = AVOID;
        flags[DROPPOD_OPEN] = PASSABLE;

        /*
        flags[BOUNCE_PERIMETER] = AVOID;
        flags[BOUNCE_CENTER]    = AVOID;

        flags[VORTEX_PERIMETER] = flags[EMPTY] |SECRET;
        flags[VORTEX_INTERIOR] = flags[EMPTY] |SECRET;


        flags[BURNER_PERIMETER] = flags[EMPTY] |SECRET;
        flags[BURNER_INTERIOR] = flags[EMPTY] |SECRET;
        */


        flags[NORTH]         = PASSABLE;
        flags[WEST]          = PASSABLE;
        flags[SOUTH]         = PASSABLE;
        flags[EAST]          = PASSABLE;
        flags[CLIFF_SE_CORNER] = SOLID;
        flags[CLIFF_SW_CORNER] = SOLID;
        flags[CLIFF_NW_CORNER] = SOLID;
        flags[CLIFF_NE_CORNER] = SOLID;
        flags[CLIFF_N_SIDE] = SOLID;
        flags[CLIFF_W_SIDE] = SOLID;
        flags[CLIFF_E_SIDE] = SOLID;




		flags[CHASM_WALL]		= flags[CHASM];
		flags[CHASM_FLOOR]		= flags[CHASM];
		flags[CHASM_FLOOR_SP]	= flags[CHASM];
		flags[CHASM_WATER]		= flags[CHASM];
		
		flags[MOUNTAIN_CORNER_NW]		= SOLID;
		flags[MOUNTAIN_N]				= SOLID;
		flags[MOUNTAIN_CORNER_NE]		= SOLID;
		flags[MOUNTAIN_W]				= SOLID;
		flags[MOUNTAIN_E]			    = SOLID;
		flags[MOUNTAIN_CORNER_SW]	    = SOLID;
		flags[RUINED_WALL_E]				= AVOID;
		flags[SECRET_RUINED_WALL_E]		= flags[EMPTY] | SECRET;
		flags[TRIPWIRE]				= AVOID;
		flags[SECRET_TRIPWIRE]		= flags[EMPTY] | SECRET;
		flags[MOUNTAIN_ELBOW_SW]		= SOLID;
		flags[MOUNTAIN_ELBOW_SE]	    = SOLID;
		flags[MOUNTAIN_ELBOW_NW]		= SOLID;
		flags[MOUNTAIN_ELBOW_SE]		= SOLID;
		flags[SUMMONING_TRAP]			= AVOID;
		flags[SECRET_SUMMONING_TRAP]	= flags[EMPTY] | SECRET;
		flags[MOUNTAIN_S]			= SOLID;
		
		for (int i=WATER_TILES; i < WATER_TILES + 16; i++) {
			flags[i] = flags[WATER];
		}
        for (int i=GRADUAL_SLOPE_TILES; i < GRADUAL_SLOPE_TILES + 16; i++) {
            flags[i] = flags[GRADUAL_SLOPE];
        }
	};
	
	public static int discover( int terr ) {
		switch (terr) {

		case MOUNTAIN_CORNER_NE:
			return MOUNTAIN_N;
		case SECRET_RUINED_WALL_E:
			return RUINED_WALL_E;
		case SECRET_TRIPWIRE:
			return TRIPWIRE;
		case SECRET_SUMMONING_TRAP:
			return SUMMONING_TRAP;
		default:
			return terr;
		}
	}
}

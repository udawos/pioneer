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

import com.udawos.pioneer.actors.Char;
import com.udawos.pioneer.actors.mobs.npcs.NPC;

public class HeroAction {
	
	public int dst;
	
	public static class Move extends HeroAction {
		public Move( int dst ) {
			this.dst = dst;
		}
	}
	
	public static class PickUp extends HeroAction {
		public PickUp( int dst ) {
			this.dst = dst;
		}
	}
	
	public static class OpenChest extends HeroAction {
		public OpenChest( int dst ) {
			this.dst = dst;
		}
	}
	
	public static class Buy extends HeroAction {
		public Buy( int dst ) {
			this.dst = dst;
		}
	}
	
	public static class Interact extends HeroAction {
		public NPC npc;
		public Interact( NPC npc ) {
			this.npc = npc;
		}
	}
	
	public static class Unlock extends HeroAction {
		public Unlock( int door ) {
			this.dst = door;
		}
	}
	
	public static class North extends HeroAction {
		public North( int path ) {
			this.dst = path;
		}
	}

    public static class West extends HeroAction {
        public West( int path ) {this.dst = path;}
    }

	public static class South extends HeroAction {
		public South( int path ) {
			this.dst = path;
		}
	}

    public static class East extends HeroAction {
        public East( int path ) {
            this.dst = path;
        }
    }

    public static class Up extends HeroAction {
        public Up( int path ) {
            this.dst = path;
        }
    }

    public static class Down extends HeroAction {
        public Down( int path ) {
            this.dst = path;
        }
    }

	public static class Cook extends HeroAction {
		public Cook( int pot ) {
			this.dst = pot;
		}
	}
	
	public static class Attack extends HeroAction {
		public Char target;
		public Attack( Char target ) {
			this.target = target;
		}
	}

    public static class VantagePoint extends HeroAction {
        public VantagePoint( int path ) {
            this.dst = path;
        }
    }
}

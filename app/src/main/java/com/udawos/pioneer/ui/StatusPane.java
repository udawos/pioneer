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
package com.udawos.pioneer.ui;

import com.udawos.noosa.Image;
import com.udawos.noosa.audio.Sample;
import com.udawos.noosa.particles.Emitter;
import com.udawos.noosa.ui.Button;
import com.udawos.noosa.ui.Component;
import com.udawos.pioneer.Assets;
import com.udawos.pioneer.Dungeon;
import com.udawos.pioneer.effects.Speck;
import com.udawos.pioneer.items.keys.IronKey;
import com.udawos.pioneer.scenes.GameScene;
import com.udawos.pioneer.utils.GLog;
import com.udawos.pioneer.windows.WndGame;

public class StatusPane extends Component {
	

	private int lastTier = 0;
	
	//private Image hp;
	//private Image exp;
	
	private int lastLvl = -1;
	private int lastKeys = -1;
	
	//private BitmapText level;
	//private BitmapText depth;
	//private BitmapText keys;
	
	private DangerIndicator danger;
	private LootIndicator loot;
	private ResumeButton resume;
	private BuffIndicator buffs;
	private Compass compass;
    private boolean lowhealth = false;
	
	private MenuButton btnMenu;
	
	@Override
	protected void createChildren() {
		

		
		btnMenu = new MenuButton();
		add( btnMenu );

		
		compass = new Compass( Dungeon.level.north );
		add( compass );
		
		//hp = new Image( Assets.HP_BAR );
		//add( hp );
		
		//exp = new Image( Assets.XP_BAR );
		//add( exp );

		//level = new BitmapText( PixelScene.font1x );
		//level.hardlight( 0xFFEBA4 );
		//add( level );
		
		//depth = new BitmapText( Integer.toString( Dungeon.depth ), PixelScene.font1x );
		//depth.hardlight( 0xCACFC2 );
		//depth.measure();
		//add( depth );

		Dungeon.hero.belongings.countIronKeys();
		//keys = new BitmapText( PixelScene.font1x );
		//keys.hardlight( 0xCACFC2 );
		//add( keys );
		
		danger = new DangerIndicator();
		add( danger );
		
		loot = new LootIndicator();
		add( loot );
		
		resume = new ResumeButton();
		add( resume );
		
		buffs = new BuffIndicator( Dungeon.hero );
		add( buffs );
	}
	
	@Override
	protected void layout() {
		
		height = 32;

		//hp.x = 30;
		//hp.y = 3;
		
		//depth.x = width - 24 - depth.width()    - 18;
		//depth.y = 6;
		
		//keys.y = 6;
		
		layoutTags();
		
		buffs.setPos( 32, 11 );
		
		btnMenu.setPos( width - btnMenu.width(), 1 );
	}
	
	private void layoutTags() {
		
		float pos = 18;
		
		if (tagDanger) {
			danger.setPos( width - danger.width(), pos );
			pos = danger.bottom() + 1;
		}
		
		if (tagLoot) {
			loot.setPos( width - loot.width(), pos );
			pos = loot.bottom() + 1;
		}
		
		if (tagResume) {
			resume.setPos( width - resume.width(), pos );
		}
	}
	
	private boolean tagDanger	= false;
	private boolean tagLoot		= false;
	private boolean tagResume	= false;
	
	@Override
	public void update() {
		super.update();
		
		if (tagDanger != danger.visible || tagLoot != loot.visible || tagResume != resume.visible) {
			
			tagDanger = danger.visible;
			tagLoot = loot.visible;
			tagResume = resume.visible;
			
			layoutTags();
		}

        float health = (float)Dungeon.hero.HP / Dungeon.hero.HT;

        if (health == 0) {
           //do nothing
        } else if (!lowhealth && health < 0.25f) {
            GLog.w("Your health is low.");
            lowhealth = true;
        }



        //hp.scale.x = health;
		//exp.scale.x = (width / exp.width) * Dungeon.hero.exp / Dungeon.hero.maxExp();
		
		if (Dungeon.hero.lvl != lastLvl) {
			
			if (lastLvl != -1) {
				Emitter emitter = (Emitter)recycle( Emitter.class );
				emitter.revive();
				emitter.pos( 27, 27 );
				emitter.burst( Speck.factory( Speck.STAR ), 12 );
			}
			
			lastLvl = Dungeon.hero.lvl;
			//level.text( Integer.toString( lastLvl ) );
			//level.measure();
			//level.x = PixelScene.align( 27.0f - level.width() / 2 );
			//level.y = PixelScene.align( 27.5f - level.baseLine() / 2 );
		}
		
		int k = IronKey.curDepthQuantity;
		if (k != lastKeys) {
			lastKeys = k;
		//	keys.text( Integer.toString( lastKeys ) );
			//keys.measure();
			//keys.x = width - 8 - keys.width()    - 18;
		}

	}
	
	private static class MenuButton extends Button {
		
		private Image image;
		
		public MenuButton() {
			super();
			
			width = image.width + 4;
			height = image.height + 4;
		}
		
		@Override
		protected void createChildren() {
			super.createChildren();
			
			image = new Image( Assets.STATUS, 114, 3, 12, 11 );
			add( image );
		}
		
		@Override
		protected void layout() {
			super.layout();
			
			image.x = x + 2;
			image.y = y + 2;
		}
		
		@Override
		protected void onTouchDown() {
			image.brightness( 1.5f );
			Sample.INSTANCE.play( Assets.SND_CLICK );
		}
		
		@Override
		protected void onTouchUp() {
			image.resetColor();
		}
		
		@Override
		protected void onClick() {
			GameScene.show( new WndGame() );
		}
	}
}

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
package com.udawos.pioneer.windows;

import com.udawos.noosa.Camera;
import com.udawos.noosa.audio.Sample;
import com.udawos.pioneer.Assets;
import com.udawos.pioneer.Pioneer;
import com.udawos.pioneer.scenes.PixelScene;
import com.udawos.pioneer.ui.CheckBox;
import com.udawos.pioneer.ui.RedButton;
import com.udawos.pioneer.ui.Toolbar;
import com.udawos.pioneer.ui.Window;

public class WndSettings extends Window {
	
	private static final String TXT_ZOOM_IN			= "+";
	private static final String TXT_ZOOM_OUT		= "-";
	private static final String TXT_ZOOM_DEFAULT	= "Default Zoom";

	private static final String TXT_SCALE_UP		= "Scale up UI";
	private static final String TXT_IMMERSIVE		= "Immersive mode";
	
	private static final String TXT_MUSIC	= "Music";
	
	private static final String TXT_SOUND	= "Sound FX";
	
	private static final String TXT_BRIGHTNESS	= "Brightness";
	
	private static final String TXT_QUICKSLOT	= "Second quickslot";
	
	private static final String TXT_SWITCH_PORT	= "Switch to portrait";
	private static final String TXT_SWITCH_LAND	= "Switch to landscape";
	
	private static final int WIDTH		= 112;
	private static final int BTN_HEIGHT	= 20;
	private static final int GAP 		= 2;
	
	private RedButton btnZoomOut;
	private RedButton btnZoomIn;
	
	public WndSettings( boolean inGame ) {
		super();
		
		CheckBox btnImmersive = null;
		
		if (inGame) {
			int w = BTN_HEIGHT;
			
			btnZoomOut = new RedButton( TXT_ZOOM_OUT ) {
				@Override
				protected void onClick() {
					zoom( Camera.main.zoom - 1 );
				}
			};
			add( btnZoomOut.setRect( 0, 0, w, BTN_HEIGHT) );
			
			btnZoomIn = new RedButton( TXT_ZOOM_IN ) {
				@Override
				protected void onClick() {
					zoom( Camera.main.zoom + 1 );
				}
			};
			add( btnZoomIn.setRect( WIDTH - w, 0, w, BTN_HEIGHT) );
			
			add( new RedButton( TXT_ZOOM_DEFAULT ) {
				@Override
				protected void onClick() {
					zoom( PixelScene.defaultZoom );
				}
			}.setRect( btnZoomOut.right(), 0, WIDTH - btnZoomIn.width() - btnZoomOut.width(), BTN_HEIGHT ) );
			
			updateEnabled();
			
		} else {
			
			CheckBox btnScaleUp = new CheckBox( TXT_SCALE_UP ) {
				@Override
				protected void onClick() {
					super.onClick();
					Pioneer.scaleUp( checked() );
				}
			};
			btnScaleUp.setRect( 0, 0, WIDTH, BTN_HEIGHT );
			btnScaleUp.checked( Pioneer.scaleUp() );
			add( btnScaleUp );
			
			btnImmersive = new CheckBox( TXT_IMMERSIVE ) {
				@Override
				protected void onClick() {
					super.onClick();
					Pioneer.immerse( checked() );
				}
			};
			btnImmersive.setRect( 0, btnScaleUp.bottom() + GAP, WIDTH, BTN_HEIGHT );
			btnImmersive.checked( Pioneer.immersed() );
			btnImmersive.enable( android.os.Build.VERSION.SDK_INT >= 19 );
			add( btnImmersive );
			
		}
		
		CheckBox btnMusic = new CheckBox( TXT_MUSIC ) {
			@Override
			protected void onClick() {
				super.onClick();
				Pioneer.music( checked() );
			}
		};
		btnMusic.setRect( 0, (btnImmersive != null ? btnImmersive.bottom() : BTN_HEIGHT) + GAP, WIDTH, BTN_HEIGHT );
		btnMusic.checked( Pioneer.music() );
		add( btnMusic );
		
		CheckBox btnSound = new CheckBox( TXT_SOUND ) {
			@Override
			protected void onClick() {
				super.onClick();
				Pioneer.soundFx( checked() );
				Sample.INSTANCE.play( Assets.SND_CLICK );
			}
		};
		btnSound.setRect( 0, btnMusic.bottom() + GAP, WIDTH, BTN_HEIGHT );
		btnSound.checked( Pioneer.soundFx() );
		add( btnSound );
		
		if (inGame) {
			
			CheckBox btnBrightness = new CheckBox( TXT_BRIGHTNESS ) {
				@Override
				protected void onClick() {
					super.onClick();
					Pioneer.brightness( checked() );
				}
			};
			btnBrightness.setRect( 0, btnSound.bottom() + GAP, WIDTH, BTN_HEIGHT );
			btnBrightness.checked( Pioneer.brightness() );
			add( btnBrightness );
			
			CheckBox btnQuickslot = new CheckBox( TXT_QUICKSLOT ) {
				@Override
				protected void onClick() {
					super.onClick();
					Toolbar.secondQuickslot( checked() );
				}
			};
			btnQuickslot.setRect( 0, btnBrightness.bottom() + GAP, WIDTH, BTN_HEIGHT );
			btnQuickslot.checked( Toolbar.secondQuickslot() );
			add( btnQuickslot );
			
			resize( WIDTH, (int)btnQuickslot.bottom() );
			
		} else {
			
			RedButton btnOrientation = new RedButton( orientationText() ) {
				@Override
				protected void onClick() {
					Pioneer.landscape( !Pioneer.landscape() );
				}
			};
			btnOrientation.setRect( 0, btnSound.bottom() + GAP, WIDTH, BTN_HEIGHT );
			add( btnOrientation );
			
			resize( WIDTH, (int)btnOrientation.bottom() );
			
		}
	}
	
	private void zoom( float value ) {

		Camera.main.zoom( value );
		Pioneer.zoom( (int)(value - PixelScene.defaultZoom) );

		updateEnabled();
	}
	
	private void updateEnabled() {
		float zoom = Camera.main.zoom;
		btnZoomIn.enable( zoom < PixelScene.maxZoom );
		btnZoomOut.enable( zoom > PixelScene.minZoom );
	}
	
	private String orientationText() {
		return Pioneer.landscape() ? TXT_SWITCH_PORT : TXT_SWITCH_LAND;
	}
}

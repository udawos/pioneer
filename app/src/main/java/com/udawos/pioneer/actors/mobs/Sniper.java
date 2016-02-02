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
package com.udawos.pioneer.actors.mobs;

import com.udawos.noosa.Camera;
import com.udawos.pioneer.Dungeon;
import com.udawos.pioneer.ResultDescriptions;
import com.udawos.pioneer.actors.Char;
import com.udawos.pioneer.actors.buffs.Bleeding;
import com.udawos.pioneer.actors.buffs.Buff;
import com.udawos.pioneer.effects.CellEmitter;
import com.udawos.pioneer.effects.Speck;
import com.udawos.pioneer.effects.particles.SparkParticle;
import com.udawos.pioneer.items.Generator;
import com.udawos.pioneer.levels.Level;
import com.udawos.pioneer.levels.traps.LightningTrap;
import com.udawos.pioneer.mechanics.Ballistica;
import com.udawos.pioneer.sprites.CharSprite;
import com.udawos.pioneer.sprites.ShamanSprite;
import com.udawos.pioneer.utils.GLog;
import com.udawos.pioneer.utils.Utils;
import com.udawos.utils.Callback;
import com.udawos.utils.Random;

import java.util.HashSet;

public class Sniper extends Mob implements Callback {

    private static final float TIME_TO_ZAP	= 2f;

    private static final String TXT_LIGHTNING_KILLED = "%s's lightning bolt killed you...";

    {
        name = "Lookout";
        spriteClass = ShamanSprite.class;

        HP = HT = 18;
        defenseSkill = 8;
        state = WANDERING;
        viewDistance = 14;

        EXP = 6;
        maxLvl = 14;

        loot = Generator.Category.GOLD;
        lootChance = 1.0f;
    }

    @Override
    public int damageRoll() {
        return Random.NormalIntRange( 2, 20 );
    }

    @Override
    public int attackSkill( Char target ) {
        return 11;
    }

    @Override
    public int dr() {
        return 4;
    }

    @Override
    protected boolean canAttack( Char enemy ) {
        return Ballistica.cast( pos, enemy.pos, false, true ) == enemy.pos;
    }

    @Override
    protected boolean doAttack( Char enemy ) {

        if (Level.distance( pos, enemy.pos ) <= 1) {

            return super.doAttack( enemy );

        } else {

            boolean visible = Level.fieldOfView[pos] || Level.fieldOfView[enemy.pos];
            if (visible) {
                switch (Random.Int( 2 )) {
                    case 0:
                        ((ShamanSprite) sprite).zap(enemy.pos);
                        break;
                    case 1:
                        ((ShamanSprite) sprite).zap(enemy.pos + 1);
                        CellEmitter.center(enemy.pos + 1).burst(Speck.factory(Speck.DUST), 10);
                        break;
                    case 2:
                        ((ShamanSprite) sprite).zap(enemy.pos - 1);
                        CellEmitter.center(enemy.pos - 1).burst(Speck.factory(Speck.DUST), 10);
                        break;
                }
            }

            spend( TIME_TO_ZAP );

            if (hit( this, enemy, true )) {
                int dmg = Random.Int( 2, 12 );
                Buff.affect(Dungeon.hero, Bleeding.class).set(dmg);
                if (Level.water[enemy.pos] && !enemy.flying) {
                    dmg *= 1.5f;
                }
                enemy.damage( dmg, LightningTrap.LIGHTNING );

                enemy.sprite.centerEmitter().burst( SparkParticle.FACTORY, 3 );
                enemy.sprite.flash();

                if (enemy == Dungeon.hero) {

                    Camera.main.shake( 2, 0.3f );

                    if (!enemy.isAlive()) {
                        Dungeon.fail( Utils.format( ResultDescriptions.MOB,
                                Utils.indefinite( name ), Dungeon.depth ) );
                        GLog.n( TXT_LIGHTNING_KILLED, name );
                    }
                }
            } else {
                enemy.sprite.showStatus( CharSprite.NEUTRAL,  enemy.defenseVerb() );
            }

            return !visible;
        }
    }

    @Override
    public void call() {
        next();
    }

    @Override
    public String description() {
        return
                "This creature is a trained marksman, adept at avoiding enemy detection.";
    }

    private static final HashSet<Class<?>> RESISTANCES = new HashSet<Class<?>>();
    static {
        RESISTANCES.add( LightningTrap.Electricity.class );
    }

    @Override
    public HashSet<Class<?>> resistances() {
        return RESISTANCES;
    }
}

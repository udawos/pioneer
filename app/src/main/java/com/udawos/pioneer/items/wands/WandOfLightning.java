package com.udawos.pioneer.items.wands;

import com.udawos.noosa.Camera;
import com.udawos.pioneer.Dungeon;
import com.udawos.pioneer.ResultDescriptions;
import com.udawos.pioneer.actors.Actor;
import com.udawos.pioneer.actors.Char;
import com.udawos.pioneer.effects.CellEmitter;
import com.udawos.pioneer.effects.Lightning;
import com.udawos.pioneer.effects.particles.SparkParticle;
import com.udawos.pioneer.levels.Level;
import com.udawos.pioneer.levels.traps.LightningTrap;
import com.udawos.pioneer.sprites.ItemSpriteSheet;
import com.udawos.pioneer.utils.GLog;
import com.udawos.pioneer.utils.Utils;
import com.udawos.utils.Callback;
import com.udawos.utils.Random;

import java.util.ArrayList;
import java.util.HashSet;

public class WandOfLightning extends Wand {

    {
        name = "Wand of Lightning";
        image = ItemSpriteSheet.WAND_HOLLY;
        maxCharges = 100;
        curCharges = maxCharges;
        //reloadable == false;

    }

    private ArrayList<Char> affected = new ArrayList<Char>();

    private int[] points = new int[20];
    private int nPoints;

    @Override
    protected void onZap( int cell ) {
        // Everything is processed in fx() method
        if (!curUser.isAlive()) {
            Dungeon.fail( Utils.format( ResultDescriptions.WAND, name, Dungeon.depth ) );
            GLog.n( "You killed yourself with your own Wand of Lightning..." );
        }
    }

    private void hit( Char ch, int damage ) {

        if (damage < 1) {
            return;
        }

        if (ch == Dungeon.hero) {
            Camera.main.shake( 2, 0.3f );
        }

        affected.add( ch );
        ch.damage( Level.water[ch.pos] && !ch.flying ? (int)(damage * 2) : damage, LightningTrap.LIGHTNING  );

        ch.sprite.centerEmitter().burst( SparkParticle.FACTORY, 3 );
        ch.sprite.flash();

        points[nPoints++] = ch.pos;

        HashSet<Char> ns = new HashSet<Char>();
        for (int i=0; i < Level.NEIGHBOURS8.length; i++) {
            Char n = Actor.findChar( ch.pos + Level.NEIGHBOURS8[i] );
            if (n != null && !affected.contains( n )) {
                ns.add( n );
            }
        }

        if (ns.size() > 0) {
            hit( Random.element( ns ), Random.Int( damage / 2, damage ) );
        }
    }

    @Override
    public void onZapHigh(int cell) {
        fx(cell, new Callback() {
            @Override
            public void call() {

            }
        });

    }

    @Override
    public void onZapMid(int cell) {
        fx(cell, new Callback() {
            @Override
            public void call() {

            }
        });

    }

    @Override
    public void onZapLow(int cell) {
        fx(cell, new Callback() {
            @Override
            public void call() {

            }
        });

    }

    @Override
    protected void fx( int cell, Callback callback ) {

        nPoints = 0;
        points[nPoints++] = Dungeon.hero.pos;

        Char ch = Actor.findChar( cell );
        if (ch != null) {

            affected.clear();
            int lvl = level();
            hit( ch, Random.Int( 5 + lvl / 2, 10 + lvl ) );

        } else {

            points[nPoints++] = cell;
            CellEmitter.center( cell ).burst( SparkParticle.FACTORY, 3 );

        }
        curUser.sprite.parent.add( new Lightning( points, nPoints, callback ) );
    }

    @Override
    public String desc() {
        return
                "This strange weapon will fire two nearly simultaneous beams of electricity " +
                        "at your enemies. It does not need to be reloaded.";
    }
}

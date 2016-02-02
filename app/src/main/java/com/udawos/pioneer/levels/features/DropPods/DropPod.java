package com.udawos.pioneer.levels.features.DropPods;

import com.udawos.pioneer.Dungeon;
import com.udawos.pioneer.actors.blobs.Blob;
import com.udawos.pioneer.actors.blobs.Fire;
import com.udawos.pioneer.effects.CellEmitter;
import com.udawos.pioneer.effects.particles.FlameParticle;
import com.udawos.pioneer.items.ammunition.Bullet;
import com.udawos.pioneer.items.ammunition.Gunpowder;
import com.udawos.pioneer.items.ammunition.PercussionCap;
import com.udawos.pioneer.items.food.Food;
import com.udawos.pioneer.items.potions.PotionOfHealing;
import com.udawos.pioneer.items.wands.WandOfBullet;
import com.udawos.pioneer.levels.Level;
import com.udawos.pioneer.levels.Terrain;
import com.udawos.pioneer.scenes.GameScene;
import com.udawos.pioneer.utils.GLog;
import com.udawos.utils.Random;

/**
 * Created by Jake on 07/01/2016.
 */
public class DropPod {


    protected static boolean opened;


    int landPos;

    public void landPos() {


        switch (Random.Int(0, 3)) {
            case 0:
                GLog.i("SUPPLI3S.INB0UND.CHECK NORTHWEST OF YOUR POSITION");
                landPos = 408 + (Random.Int(1,10)*50);
                break;
            case 1:
                GLog.i("SUPPLI3S.INB0UND.CHECK NORTHEAST OF YOUR POSITION");
                landPos = 388 + (Random.Int(1,10)*50);;
                break;
            case 2:
                GLog.i("SUPPLI3S.INB0UND.CHECK SOUTHWEST OF YOUR POSITION");
                landPos = 1762 + (Random.Int(1,10)*50);;
                break;
            case 3:
                GLog.i("SUPPLI3S.INB0UND.CHECK SOUTHEAST OF YOUR POSITION");
                landPos = 1835 + (Random.Int(1,10)*50);;
                break;
        }

    }





    public void land() {
        Level.set(landPos, Terrain.DROPPOD_CLOSED);
        GameScene.updateMap(landPos);
        GameScene.add(Blob.seed(landPos, 2, Fire.class));
        CellEmitter.get(landPos).burst(FlameParticle.FACTORY, 5);
        opened = false;
        Dungeon.observe();

    }


    public void open(int pos) {
        if (opened == false) {
            Level.set(pos, Terrain.DROPPOD_OPEN);
            GameScene.updateMap(pos);
            if (Dungeon.hero.belongings.getItem(WandOfBullet.class) == null) {
                Dungeon.level.drop(new WandOfBullet(), pos).sprite.drop();
                Dungeon.level.drop(new Gunpowder(20), pos).sprite.drop();
                Dungeon.level.drop(new Bullet(20), pos).sprite.drop();
                Dungeon.level.drop(new PercussionCap(20), pos).sprite.drop();
                Dungeon.level.drop(new PotionOfHealing(), pos).sprite.drop();
                Dungeon.level.drop(new Food(), pos).sprite.drop();
                Dungeon.level.drop(new Food(), pos).sprite.drop();
                Dungeon.level.drop(new Food(), pos).sprite.drop();
                Dungeon.level.drop(new Food(), pos).sprite.drop();
                Dungeon.level.drop(new Food(), pos).sprite.drop();
            } else if (Dungeon.hero.belongings.getItem(WandOfBullet.class) != null) {
                Dungeon.level.drop(new Gunpowder(20), pos).sprite.drop();
                Dungeon.level.drop(new Bullet(20), pos).sprite.drop();
                Dungeon.level.drop(new PercussionCap(20), pos).sprite.drop();
                Dungeon.level.drop(new PotionOfHealing(), pos).sprite.drop();
                Dungeon.level.drop(new Food(), pos).sprite.drop();
                Dungeon.level.drop(new Food(), pos).sprite.drop();
                Dungeon.level.drop(new Food(), pos).sprite.drop();
                Dungeon.level.drop(new Food(), pos).sprite.drop();
                Dungeon.level.drop(new Food(), pos).sprite.drop();
            }
        }
        opened = true;
        Dungeon.observe();
    }
}

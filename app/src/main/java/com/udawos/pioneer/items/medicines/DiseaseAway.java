package com.udawos.pioneer.items.medicines;

import com.udawos.pioneer.Badges;
import com.udawos.pioneer.actors.hero.Hero;
import com.udawos.pioneer.sprites.CharSprite;
import com.udawos.pioneer.utils.GLog;

/**
 * Created by Jake on 26/10/2015.
 */
public class DiseaseAway extends Medicines {

    //Dr. Tim's Cure-most
    {
        name = "Potion of Strength";
    }

    @Override
    protected void apply( Hero hero ) {
        setKnown();

        hero.STR++;
        hero.sprite.showStatus( CharSprite.POSITIVE, "+1 str" );
        GLog.p("Newfound strength surges through your body.");

        Badges.validateStrengthAttained();
    }

    @Override
    public String desc() {
        return
                "This powerful liquid will course through your muscles, " +
                        "permanently increasing your strength by one point.";
    }

    @Override
    public int price() {
        return isKnown() ? 100 * quantity : super.price();
    }
}


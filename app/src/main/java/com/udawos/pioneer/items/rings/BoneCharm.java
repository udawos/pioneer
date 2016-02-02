package com.udawos.pioneer.items.rings;

/**
 * Created by Jake on 02/02/2016.
 */
public class BoneCharm extends Ring {
    {
        name = "Bone Charm of the Hunter";
    }


    @Override
    public String desc() {
        return isKnown() ?
                "This bone ring was once the prized possession of an entire ancient tribe. " +
                        "The ring was used only in times of dire need, for fear overuse would break it. " +
                        "Eventually, the tribe never returned and the ring was forgotten. " +
                        "It bestows supreme accuracy and a guaranteed headshot for its user. The magic in it is so strong " +
                        "that it does not even need to be worn for it to be effective." :
                super.desc();
    }


}

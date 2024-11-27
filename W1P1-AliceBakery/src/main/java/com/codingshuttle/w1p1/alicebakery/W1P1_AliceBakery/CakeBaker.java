package com.codingshuttle.w1p1.alicebakery.W1P1_AliceBakery;

import org.springframework.stereotype.Component;

@Component
public class CakeBaker {

    private final Frosting frosting;
    private final Syrup syrup;

    public CakeBaker(Frosting frosting, Syrup syrup) {
        this.frosting = frosting;
        this.syrup = syrup;
    }

    public String BakeCake() {
        return "Cake with " + syrup.getSyrupType() + " and " + frosting.getFrostingType();
    }
}

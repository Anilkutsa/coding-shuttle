package com.codingshuttle.w1p1.alicebakery.W1P1_AliceBakery;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

@Component
@ConditionalOnProperty(name = "frosting.dev", havingValue = "Strawberry")
public class StrawberryFrosting implements Frosting {

    @Override
    public String getFrostingType() {
        return "Strawberry Frosting";
    }

}

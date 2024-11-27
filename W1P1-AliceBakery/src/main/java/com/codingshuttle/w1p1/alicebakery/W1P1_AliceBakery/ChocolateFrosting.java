package com.codingshuttle.w1p1.alicebakery.W1P1_AliceBakery;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

@Component
//@Primary
@ConditionalOnProperty(name = "frosting.dev", havingValue = "Chocolate")
public class ChocolateFrosting implements Frosting {

    @Override
    public String getFrostingType() {
        return "Chocolate Frosting";
    }
}

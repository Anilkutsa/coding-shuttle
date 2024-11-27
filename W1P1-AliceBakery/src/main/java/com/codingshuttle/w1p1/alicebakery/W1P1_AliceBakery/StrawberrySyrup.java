package com.codingshuttle.w1p1.alicebakery.W1P1_AliceBakery;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

@Component
@ConditionalOnProperty(name = "syrup.dev", havingValue = "Strawberry")
public class StrawberrySyrup implements Syrup {

    @Override
    public String getSyrupType() {
        return "Strawberry Syrup";
    }

}

package com.codingshuttle.w1p1.alicebakery.W1P1_AliceBakery;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

@Component
//@Primary
@ConditionalOnProperty(name = "syrup.dev", havingValue = "Chocolate")
public class ChocolateSyrup implements Syrup {

    @Override
    public String getSyrupType() {
        return "Chocolate Syrup";
    }

}

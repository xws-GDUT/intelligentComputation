package program.continuous.GA.algorithm;

import org.junit.jupiter.api.Test;

/**
 * @Author xws
 * @email wansenxu@163.com
 */
class SGATest {
    @Test
    void optimize() {
        SGA SGA = new SGA().setSelector(new RouletteSelector());
        SGA.optimize(50,10,-20.0,20.0,0.8,0.1,2000,new F1());
    }
}
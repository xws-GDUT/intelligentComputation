package program.continuous.DE.algorithm;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @Author xws
 * @email wansenxu@163.com
 */
class DETest {
    @Test
    void optimize() {
        DE DE= new DE().setSelector(new DifferentSelector());
        DE.optimize(50,10,-20.0,20.0,0.1,0.5,2000,new F1());
    }
    @Test
    void optimize2() {
        DE DE= new DE().setSelector(new DifferentSelector());
        DE.optimize(100,30,-100.0,100.0,0.8,0.5,3000,new F1());
    }
}
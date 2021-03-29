package program.continuous.IA.algorithm;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @Author xws
 * @email wansenxu@163.com
 */
class IATest {

    @Test
    void optimize() {
        IA IA = new IA();
        IA.optimize(100,10,-20.0,20.0,0.7,1.0,1.0,2000,10,0.2,new F1());
    }
}
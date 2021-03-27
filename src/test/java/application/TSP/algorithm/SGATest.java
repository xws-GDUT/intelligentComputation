package application.TSP.algorithm;

import application.TSP.evoluator.Func;
import application.TSP.operator.RouletteSelector;
import application.TSP.operator.SwapCrossover;
import application.TSP.operator.SwapMutator;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @Author xws
 * @email wansenxu@163.com
 */
class SGATest {

    @Test
    void optimize() {
        SGA sga = new SGA().setSelector(new RouletteSelector());

        sga.optimize(200, 32, 100000, new Func(), 1, 31,0.4,0.05);

    }
    @Test
    void test(){
        for (int i = 0; i < 1000; i++) {
            int i1 = new Random().nextInt(32);
            if(i1<0 || i1>31){
                System.out.println("error");
            }
            if(i1==32){
                System.out.println(i1);
            }

        }
    }
}
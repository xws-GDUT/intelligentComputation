package intelligentComputation.util;

import intelligentComputation.Individual;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;


/**
 * @Author xws
 * @email wansenxu@163.com
 */
class CloneTest {


    @Test
    public void test(){
        List<Individual> pop = new ArrayList<>();
        Individual individual = new Individual();
        List<Double> solution = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            solution.add(1.0);
        }
        individual.setSolution(solution);
        pop.add(individual);
        System.out.println("原种群："+pop);

        List<Individual> clonePop = Clone.clonePop(pop);
        System.out.println("克隆种群："+clonePop);

        System.out.println("修改克隆种群");
        for (int i = 0; i < 10; i++) {
            clonePop.get(0).getSolution().set(i,2.0);
        }
        System.out.println("克隆种群："+clonePop);
        System.out.println("原种群："+pop);



    }
}
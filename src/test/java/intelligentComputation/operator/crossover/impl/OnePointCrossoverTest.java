package intelligentComputation.operator.crossover.impl;

import intelligentComputation.Individual;
import intelligentComputation.operator.crossover.Crossover;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @Author xws
 * @email wansenxu@163.com
 */
class OnePointCrossoverTest {

    @Test
    void cross() {
        List<Individual> pop = new ArrayList<>();
        Individual individual = new Individual();
        List<Double> solution = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            solution.add(1.0);
        }
        Individual individual2 = new Individual();
        List<Double> solution2 = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            solution2.add(2.0);
        }

        individual.setSolution(solution);
        individual2.setSolution(solution2);
        pop.add(individual);
        pop.add(individual2);
        System.out.println("交叉前");
        System.out.println(pop);
        Crossover crossover = new OnePointCrossover();
        crossover.setRateOfCrossover(0.8);
        crossover.cross(pop);
        System.out.println("交叉后");
        System.out.println(pop);

    }
}
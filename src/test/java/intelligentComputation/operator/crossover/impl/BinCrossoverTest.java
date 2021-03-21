package intelligentComputation.operator.crossover.impl;

import intelligentComputation.Individual;
import intelligentComputation.operator.crossover.Crossover;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author xws
 * @email wansenxu@163.com
 */
class BinCrossoverTest {

    @Test
    void cross() {
        List<Individual> pop = new ArrayList<>();
        Individual individual = new Individual();
        List<Double> solution = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            solution.add(1.0);
        }
        individual.setSolution(solution);
        pop.add(individual);
        List<Individual> mutatedPop = new ArrayList<>();
        Individual individual2 = new Individual();
        List<Double> solution2 = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            solution2.add(2.0);
        }
        individual2.setSolution(solution2);
        mutatedPop.add(individual2);
        Crossover crossover = new BinCrossover();
        crossover.setRateOfCrossover(0.1);
        List<Individual> offspring = crossover.cross(pop, mutatedPop);
        List<Double> s = offspring.get(0).getSolution();
    }

}
package intelligentComputation.operator.crossover;

import intelligentComputation.Individual;
import intelligentComputation.util.Clone;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @Author xws
 * @email wansenxu@163.com
 */
class CrossoverTest {

    @Test
    public void test(){
        Individual individual = new Individual();
        List<Double> solution = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            solution.add(1.0);
        }
        individual.setSolution(solution);
        Individual individual2 = new Individual();
        List<Double> solution2 = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            solution2.add(2.0);
        }
        individual2.setSolution(solution2);
        System.out.println("交叉前");
        System.out.println(individual);
        System.out.println(individual2);
        Crossover crossover = new TestCrossover();
        crossover.setRateOfCrossover(0.1);
        crossover.binomialCross2(individual,individual2);
        System.out.println("交叉后");
        System.out.println(individual);
        System.out.println(individual2);


    }

    @Test
    void onePointCross() {
        Individual individual = new Individual();
        List<Double> solution = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            solution.add(1.0);
        }
        individual.setSolution(solution);
        Individual individual2 = new Individual();
        List<Double> solution2 = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            solution2.add(2.0);
        }
        individual2.setSolution(solution2);
        System.out.println("交叉前");
        System.out.println(individual);
        System.out.println(individual2);
        Crossover crossover = new TestCrossover();
        crossover.onePointCross(individual,individual2);
        System.out.println("交叉后");
        System.out.println(individual);
        System.out.println(individual2);
    }
}
package intelligentComputation;

import intelligentComputation.evoluator.Evaluator;
import intelligentComputation.evoluator.impl.F1;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @Author xws
 * @email wansenxu@163.com
 */
class IndividualTest {

    @Test
    void testClone() {
        Evaluator evaluator = new F1();
        List<Individual> pop = new ArrayList<>();
        Individual individual = new Individual();
        List<Double> solution = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            solution.add(1.0);
        }
        individual.setSolution(solution);
        pop.add(individual);
        evaluator.evaluate(pop);

        Individual clonedIndividual = individual.clone();
        pop.add(clonedIndividual);
        System.out.println(pop);
        pop.get(0).getSolution().set(0,3.0);
        evaluator.evaluate(pop);
        System.out.println(pop);


    }
}
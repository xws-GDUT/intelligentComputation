package intelligentComputation.util;

import intelligentComputation.Individual;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author xws
 * @email wansenxu@163.com
 */
public class Clone {
    public static List<Individual> clonePop(List<Individual> pop){
        List<Individual> clonedPoP = new ArrayList<>();
        for (int i = 0; i < pop.size(); i++) {
            Individual individual = pop.get(i).clone();
//            Individual individual = new Individual();
//            List<Double> solution = new ArrayList<>();
//            for (int i1 = 0; i1 < pop.get(0).getSolution().size(); i1++) {
//                solution.add(pop.get(i).getSolution().get(i1));
//            }
//            individual.setSolution(solution);
//            individual.setFitness(pop.get(i).getFitness());
            clonedPoP.add(individual);
        }
        return clonedPoP;
    }
}

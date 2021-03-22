package intelligentComputation.util;

import intelligentComputation.Bound;
import intelligentComputation.Individual;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * @Author xws
 * @email wansenxu@163.com
 * 进化计算工具类
 */
public class ECUtils {
    public static List<Individual> initPop(int popsize, Bound bound,int dimension){
        List<Individual> pop = new ArrayList<>();
        for (int i = 0; i < popsize; i++) {
            Individual individual = new Individual();
            List<Double> solution = new ArrayList<>();
            for (int j = 0; j < dimension; j++) {
                solution.add(bound.getLowerBound()+new Random().nextDouble()*(bound.getUpperBound()-bound.getLowerBound()));
            }
            individual.setSolution(solution);
            pop.add(individual);
        }
        return pop;
    }
    public static List<Individual> clonePop(List<Individual> pop){
        List<Individual> clonedPoP = new ArrayList<>();
        for (int i = 0; i < pop.size(); i++) {
            Individual individual = pop.get(i).clone();
            clonedPoP.add(individual);
        }
        return clonedPoP;
    }
}

package intelligentComputation.evoluator.impl;


import intelligentComputation.Individual;
import intelligentComputation.evoluator.Evaluator;

import java.util.List;

/**
 * Created by wansenxu@163.com on 2020/12/10
 */
public class F1 implements Evaluator<Individual> {
    @Override
    public void evaluate(List<Individual> pop) {
        for (int i = 0; i < pop.size(); i++) {
            Individual individual = pop.get(i);
            List<Double> solution = individual.getSolution();
            double sum = 0;
            for (int j = 0; j < solution.size(); j++) {
                sum+= Math.pow(solution.get(j),2);
            }
            individual.setFitness(sum);

        }
    }

}

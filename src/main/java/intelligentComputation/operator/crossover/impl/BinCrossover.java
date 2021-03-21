package intelligentComputation.operator.crossover.impl;

import intelligentComputation.Individual;
import intelligentComputation.operator.crossover.Crossover;
import intelligentComputation.util.Clone;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 *@Description 二项式交叉
 *@Author 许万森
 *@email wansenxu@163.com
 * 交叉策略：父子映射方案+二项式交叉（随机一维分量必定交叉）
 */

public class BinCrossover extends Crossover{
//    @Override
//    public List<Individual> cross(List<Individual> pop,List<Individual> mutatedPop) {
//        List<Individual> offspring = new ArrayList<>();
//        for(int i=0;i<mutatedPop.size();i++){
////            Individual tmpIndividual = mutatedPop.get(i);
//            Individual individual = new Individual();
//            List<Double> solution = new ArrayList<>();
//            for(int j = 0; j< pop.get(0).getSolution().size(); j++){
//                int popisitonOfCrossover = new Random().nextInt(pop.get(0).getSolution().size());
//                double p = Math.random();
//                if( p > rateOfCrossover && popisitonOfCrossover != j){
//                    solution.add(j,pop.get(i).getSolution().get(j));  // 不交叉
//                }else{ // 交叉
//                    solution.add(j,mutatedPop.get(i).getSolution().get(j));
//                }
//            }
//            individual.setSolution(solution);
//            offspring.add(individual);
//        }
//        return offspring;
//    }

    @Override
    public List<Individual> cross(List<Individual> pop,List<Individual> mutatedPop) {

        List<Individual> clonedPop = Clone.clonePop(pop);
        for(int i=0;i<pop.size();i++){
            binomialCross2(clonedPop.get(i),mutatedPop.get(i));
        }
        return clonedPop;
    }
}

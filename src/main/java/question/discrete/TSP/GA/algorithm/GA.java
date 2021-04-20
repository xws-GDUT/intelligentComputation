package question.discrete.TSP.GA.algorithm;

import evaluator.Evaluator;
import question.discrete.TSP.Constant;
import question.discrete.TSP.GA.dataStructure.Chromosome;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * @Author xws
 * @email wansenxu@163.com
 */
public class GA {
    public List<Chromosome> optimize(int popSize, int dimension, int lowerBound, int upperBound,double rateOfCrossover, double rateOfMutation,Evaluator evaluator,int iteration){
        Constant.initGraph();
        List<Chromosome> convergence = new ArrayList<>();

        List<Chromosome> pop = initPop(popSize, dimension, lowerBound, upperBound, rateOfCrossover, rateOfMutation);
        evaluate(pop,evaluator);
        Chromosome bestChromosome = Collections.min(pop).clone();
        convergence.add(bestChromosome);
        System.out.println("1\t"+bestChromosome.getFitness());
        for (int i = 1; i < iteration; i++) {
//            选择
            List<Chromosome> offspring = select(pop);
//            交叉
            cross(offspring);
//            变异
            mutate(offspring);
            evaluate(offspring,evaluator);
            pop = offspring;

            if(Collections.min(pop).getFitness()<bestChromosome.getFitness()){
                bestChromosome = Collections.min(pop).clone();
            }
            convergence.add(bestChromosome);
            System.out.println(i+1+"\t"+bestChromosome.getFitness());
        }
        return convergence;
    }

    private void mutate(List<Chromosome> offspring) {
        for (Chromosome chromosome : offspring) {
            chromosome.mutate();
        }
    }

    /**
     *  顺序配对交叉
     * @param offspring
     */
    private void cross(List<Chromosome> offspring) {
//        for (int i = 0; i < offspring.size(); i++) {
//            int index = new Random().nextInt(offspring.get(0).getDimension())+1;
//            offspring.get(i).cross(offspring.get(index));
//        }
//        -----------------------------------------------------
        for (int i = 0; i < offspring.size(); i+=2) {
            offspring.get(i).cross(offspring.get(i+1));
        }
//        -----------------------------------------------------
//        Chromosome bestChromosome = Collections.min(offspring).clone();
//        for (Chromosome chromosome : offspring) {
//            chromosome.cross(bestChromosome);
//        }
    }

    private void evaluate(List<Chromosome> pop, Evaluator evaluator) {
        for (Chromosome chromosome : pop) {
            chromosome.evauate(evaluator);
        }
    }

    private List<Chromosome> initPop(int popSize, int dimension, int lowerBound, int upperBound, double rateOfCrossover, double rateOfMutation) {
        List<Chromosome> pop = new ArrayList<>();
        for (int i = 0; i < popSize; i++) {
            pop.add(new Chromosome(dimension,lowerBound,upperBound,rateOfCrossover,rateOfMutation));
        }
        return pop;
    }
    private List<Chromosome> select(List<Chromosome> pop) {
        List<Chromosome> offspring = new ArrayList<>();;

        Double ReciprocalOfSumFit = 0.0;
        for (int i = 0; i < pop.size(); i++) {
            ReciprocalOfSumFit += 1.0 / pop.get(i).getFitness();
        }
        //计算每个个体被选中的概率
        List<Double> probability = new ArrayList<Double>();
        for (int i = 0; i < pop.size(); i++) {
            probability.add(1.0 / pop.get(i).getFitness() / ReciprocalOfSumFit);
        }

        for (int i = 0; i < pop.size(); i++) {
            int k = -1; //轮盘选择到的个体的下标
            double p = new Random().nextDouble();
            double sumProbability = 0;
            for (int j = 0; j < pop.size(); j++) {
                sumProbability+=probability.get(j);
                if(sumProbability>p){
                    k=j;
                    break;
                }
            }
            offspring.add(pop.get(k).clone());
        }
        return offspring;

    }
}

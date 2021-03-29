package program.discrete.ACO.algorithm;

import program.Evaluator;
import program.discrete.ACO.dataStructure.Ant;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @Author xws
 * @email wansenxu@163.com
 */
public class ACO {

    public static void main(String[] args) {

        System.out.println(Constant.GRAPH);
    }

    public List<Ant> optimize(int popSize, int dimension, int lowerBound, int upperBound, double alpha, double beta, double rho, double Q, int iteration, Evaluator evaluator){
        Constant.initGraph();
        List<Ant> bestAntPerGeneration = new ArrayList<>();
        List<Ant> convergence =new ArrayList<>();
        for (int j = 0; j < iteration; j++) {
            // 将m只蚂蚁放到n个城市上
            List<Ant> pop = initPop(popSize,lowerBound,upperBound,alpha,beta);
            // m只蚂蚁根据概率函数选择下一作城市，完成各自的周游
            for (int i = 0; i < pop.size(); i++) {
                while(pop.get(i).getUnvisitedCity().size()!=0){
                    // 根据概率原则选取下一个城市
                    Ant ant = pop.get(i);
                    double p = new Random().nextDouble();
                    double tmp = 0.0;
                    Iterator<Map.Entry<Integer, Double>> iterator = ant.getUnvisitedCity().entrySet().iterator();
                    while(iterator.hasNext()){
                        Map.Entry<Integer, Double> entry = iterator.next();
                        tmp+=entry.getValue();
                        if(tmp>p){
                            ant.visite(entry.getKey());
                            break;
                        }
                    }
                }
            }
            evaluate(pop,evaluator);
            // 记录本次最佳路线
            bestAntPerGeneration.add(Collections.min(pop));
            convergence.add(Collections.min(bestAntPerGeneration));
            System.out.println(j+1+":"+Collections.min(convergence).getTotalDistance());
            // 更新信息素
            updatePheromone(pop,rho,Q);
        }
        return convergence;
    }

    private void updatePheromone(List<Ant> pop, double rho, double Q) {
        List<List<Integer>> paths = pop.stream().map(Ant::getPaths).collect(Collectors.toList());
        Constant.GRAPH.updatePhoromone(paths,rho,Q);
    }

    private void evaluate(List<Ant> pop, Evaluator evaluator) {
        for (Ant ant : pop) {
            ant.evaluate(evaluator);
        }
    }

    private List<Ant> initPop(int popSize, int lowerBound, int upperBound,double alpha,double beta) {
        List<Ant> pop = new ArrayList<>();
        for (int i = 0; i < popSize; i++) {
            pop.add(new Ant(lowerBound,upperBound,alpha,beta));
        }
        return pop;
    }
}

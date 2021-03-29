package program.discrete.ACO.dataStructure;

import lombok.Data;
import program.Evaluator;
import program.discrete.ACO.algorithm.Constant;

import java.util.*;

/**
 * @Author xws
 * @email wansenxu@163.com
 */
@Data
public class Ant implements Comparable<Ant>{
    private List<Integer> paths ;
    private double totalDistance;
    private int lowBound;
    private int upperBound;
    private double alpha;
    private double beta;

    private Map<Integer,Double> unvisitedCity;

    public Ant(int lowBound, int upperBound,double alpha,double beta){
        this.lowBound = lowBound;
        this.upperBound = upperBound;
        this.alpha = alpha;
        this.beta = beta;
        unvisitedCity = new HashMap<>();
        for (int i = lowBound; i <= upperBound; i++) {
            unvisitedCity.put(i,0.0);
        }
        paths =  new ArrayList<>();
        int randCity = (int)(new Random().nextDouble()*(upperBound-lowBound))+lowBound;
        visite(randCity);
    }

    public void visite(int vid){
        Set<Integer> citys = unvisitedCity.keySet();
        Iterator<Integer> iterator = citys.iterator();
        while (iterator.hasNext()){
            Integer key = iterator.next();
            if(key == vid){
                iterator.remove();
            }
        }
        paths.add(vid);
        updateProbForUnvisited(vid);
    }
    private void updateProbForUnvisited(int vid){
        double sum = 0.0;
        Set<Integer> city = unvisitedCity.keySet();
        Iterator<Integer> iterator = city.iterator();
        while(iterator.hasNext()){
            Integer key = iterator.next();
            double pheromone = Constant.GRAPH.getVertex(vid).getPheromone(key);
            double distance = Constant.GRAPH.getDistance(vid, key);
            sum+= Math.pow(pheromone,alpha)+Math.pow(1.0/distance,beta);
        }
        Iterator<Integer> iterator2 = city.iterator();
        while(iterator2.hasNext()){
            Integer key = iterator2.next();
            double pheromone = Constant.GRAPH.getVertex(vid).getPheromone(key);
            double distance = Constant.GRAPH.getDistance(vid, key);
            double prob = (Math.pow(pheromone,alpha)+Math.pow(1.0/distance,beta))/sum;
            unvisitedCity.put(key,prob);
        }
    }
    public void evaluate(Evaluator evaluator){
        totalDistance = evaluator.evaluate(this);
    }

    @Override
    public int compareTo(Ant ant) {
        if(totalDistance>ant.getTotalDistance()){
            return 1;
        }else if(totalDistance<ant.getTotalDistance()){
            return -1;
        }else{
            return 0;
        }
    }
}

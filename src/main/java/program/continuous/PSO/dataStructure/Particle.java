package program.continuous.PSO.dataStructure;

import lombok.Data;
import lombok.SneakyThrows;
import program.Evaluator;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

/**
 * @Author xws
 * @email wansenxu@163.com
 */
@Data
public class Particle implements Comparable<Particle>,Cloneable{
    private List<Double> position;
    private int dimension;
    private double fitness;
    private List<Double> V;
    private double lowerBound;
    private double upperBound;
    private double VMAX;
    private double VMIN;
    private double bestFitness = Double.MAX_VALUE;
    private List<Double> bestPosition;

    public Particle(int dimension,double lowerBound, double upperBound, double vmax, double vmin,Evaluator evaluator) {
        this.dimension = dimension;
        this.lowerBound = lowerBound;
        this.upperBound = upperBound;
        VMAX = vmax;
        VMIN = vmin;
        V  = new ArrayList<>();
        position = new ArrayList<>();
        for (int i = 0; i < dimension; i++) {
            position.add(new Random().nextDouble()*(upperBound-lowerBound)+lowerBound);
            V.add(new Random().nextDouble()*(VMAX-VMIN)+VMIN);
        }
        evaluate(evaluator);
    }

    /**
     * 评级粒子是并更新该粒子迄今为止最优的位置
     * @param evaluator
     */
    public void evaluate(Evaluator evaluator){
        this.fitness = evaluator.evaluate(this.getPosition());
        if(fitness<bestFitness){
            bestFitness = fitness;
            bestPosition = new ArrayList<>();
            for (int i = 0; i < this.position.size(); i++) {
                bestPosition.add(position.get(i));
            }
        }
    }

    @Override
    public int compareTo(Particle particle) {
        if(fitness<particle.getFitness()){
            return  -1;
        }else if(fitness > particle.getFitness()){
            return 1;
        }else{
            return 0;
        }
    }

    @Override
    public Particle clone(){
        Particle particle = null;
        try {
            particle = (Particle) super.clone();
            particle.setPosition(this.position.stream().collect(Collectors.toList()));
            particle.setV(this.V.stream().collect(Collectors.toList()));
            particle.setBestPosition(this.bestPosition.stream().collect(Collectors.toList()));
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return particle;
    }
}

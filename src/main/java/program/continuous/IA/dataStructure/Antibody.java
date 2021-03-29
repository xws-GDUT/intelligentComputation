package program.continuous.IA.dataStructure;

import lombok.Data;
import program.Evaluator;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Data
public class Antibody implements Cloneable,Comparable<Antibody>{
    private List<Double> genes;       //基因
    private int dimension;
    private double lowBound;
    private double upperBound;
    private double rateOfMutation;
    private double fitness;         //适应度 == 亲和度
    private double concentration;   //浓度
    private double incentiveStrength; //激励度

    public Antibody(int dimension, double lowerBound, double upperBound, double rateOfMutation) {
        this.dimension = dimension ;
        this.lowBound = lowerBound ;
        this.upperBound = upperBound ;
        this.rateOfMutation = rateOfMutation ;
        List<Double> genes = new ArrayList<>();
        for (int i = 0; i < dimension; i++) {
            genes.add(new Random().nextDouble()*(upperBound - lowerBound)+lowerBound);
        }
        this.genes = genes;
    }


    @Override
    public Antibody clone(){
        Antibody individual = null;
        try {
            individual = (Antibody)super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        List<Double> clonedSolution = new ArrayList<>();
        for (int i = 0; i < genes.size(); i++) {
            clonedSolution.add(genes.get(i));
        }
        individual.genes = clonedSolution;
        individual.fitness = this.fitness;
        individual.concentration = this.concentration;
        individual.incentiveStrength = this.incentiveStrength;
        return individual;
    }

    @Override
    public int compareTo(Antibody o) {
        if(this.getFitness()==o.getFitness()){
            return 0;
        }else if(this.getFitness()>o.getFitness()){
            return 1;
        }else{
            return -1;
        }
    }

    public void evaluate(Evaluator evaluator) {
        this.fitness = evaluator.evaluate(this);
    }
}

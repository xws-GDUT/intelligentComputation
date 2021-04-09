package program.continuous.IA.dataStructure;

import lombok.Data;
import program.Evaluator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

@Data
public class Antibody implements Cloneable,Comparable<Antibody>{
    private List<Double> genes;       //基因
    private int dimension;
    private double lowerBound;
    private double upperBound;
    private double rateOfMutation;
    private double fitness;         //适应度 == 亲和度
    private double concentration;   //浓度
    private double incentiveStrength; //激励度
    private int numOfClone;

    public Antibody(int dimension, double lowerBound, double upperBound, double rateOfMutation,int numOfClone) {
        this.dimension = dimension ;
        this.lowerBound = lowerBound ;
        this.upperBound = upperBound ;
        this.rateOfMutation = rateOfMutation ;
        this.numOfClone = numOfClone;
        List<Double> genes = new ArrayList<>(dimension);
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
        this.fitness = evaluator.evaluate(this.getGenes());
    }

    public void mutate(double neighborhoodRange){
        for (int l = 0; l < genes.size(); l++) {
            double p = Math.random();
            if (p < rateOfMutation) {
                double value = genes.get(l)+(new Random().nextDouble()-0.5)*neighborhoodRange;
                if(value<lowerBound || value>upperBound){
                    value = lowerBound+new Random().nextDouble()*(upperBound-lowerBound);
                }
                genes.set(l,value);
            }
        }
    }

    public Antibody immune(double neighborhoodRange,Evaluator evaluator){
        List<Antibody> list = new ArrayList<>(numOfClone);
        for (int i = 0; i < this.numOfClone; i++) {
            list.add(this.clone());
        }
        for (int i = 1; i < list.size(); i++) {
            list.get(i).mutate(neighborhoodRange);
            list.get(i).evaluate(evaluator);
        }
        return Collections.min(list);
    }
}

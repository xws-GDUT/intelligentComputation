package program.continuous.DE.dataStructure;
import exception.ChromosomeException;
import lombok.Data;
import program.Evaluator;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * @Author xws
 * @email wansenxu@163.com
 */
@Data
public class Chromosome implements Cloneable,Comparable{
    private List<Double> genes;
    private int dimension;
    private double fitness;
    private double lowBound;
    private double upperBound;
    private double rateOfCrossover;
    private double rateOfMutation;

    public Chromosome(int dimension, double lowBound, double upperBound, double rateOfCrossover, double rateOfMutation) {
        this.dimension = dimension;
        this.lowBound = lowBound;
        this.upperBound = upperBound;
        this.rateOfCrossover = rateOfCrossover;
        this.rateOfMutation = rateOfMutation;
        this.genes = new ArrayList<>();
        for (int i = 0; i < dimension; i++) {
            genes.add( (upperBound - lowBound) * new Random().nextDouble() + lowBound);
        }
    }

    public void evauate(Evaluator evaluator) {
        fitness = evaluator.evaluate(this.getGenes());
    }

    /**
     * bin交叉
     * @param chromosome
     */
    public Chromosome cross(Chromosome chromosome) {
        Chromosome newChromosome = this.clone();
        for (int i = 0; i < newChromosome.getGenes().size(); i++) {
            double p = new Random().nextDouble();
            if(p<rateOfCrossover){
                double tmpValue = newChromosome.getGenes().get(i);
                newChromosome.getGenes().set(i,chromosome.getGenes().get(i));
                chromosome.getGenes().set(i,tmpValue);
            }
        }
        return newChromosome;
    }

    /**
     * 变异
     */
    public Chromosome mutate(Chromosome c1,Chromosome c2,Chromosome c3) {
        List<Double> genes1 = c1.getGenes();
        List<Double> genes2 = c2.getGenes();
        List<Double> genes3 = c3.getGenes();
        if(genes1.size()!=genes2.size()||genes1.size()!=genes3.size()||genes2.size()!=genes3.size()){
            throw new ChromosomeException("genes1.size()!=genes2.size()||genes1.size()!=genes3.size()||genes2.size()!=genes3.size()");
        }
        List<Double> newGenes = new ArrayList<>();
        for (int i = 0; i < genes.size(); i++) {
            newGenes.add(genes1.get(i)+rateOfMutation*(genes2.get(i)-genes3.get(i)));
        }
        Chromosome newChromosome = this.clone();
        newChromosome.setGenes(newGenes);
        return newChromosome;
    }

    @Override
    public int compareTo(Object o) {
        Chromosome chromosome = (Chromosome) o;
        if(fitness>chromosome.getFitness()){
            return 1;
        }else if(fitness<chromosome.getFitness()){
            return -1;
        }else{
            return 0;
        }
    }

    @Override
    public Chromosome clone(){
        Chromosome chromosome = null;
        try {
            chromosome = (Chromosome) super.clone();
            List<Double> tmp = new ArrayList<>();
            for (int i = 0; i < genes.size(); i++) {
                tmp.add(genes.get(i));
            }
            chromosome.setGenes(tmp);
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return chromosome;
    }
}

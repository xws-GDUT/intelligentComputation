package program.discrete.TSP.GA.dataStructure;
import lombok.Data;
import program.Evaluator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

/**
 * @Author xws
 * @email wansenxu@163.com
 */
@Data
public class Chromosome implements Cloneable,Comparable{
    private List<Integer> genes;
    private int dimension;
    private double fitness;
    private int lowBound;
    private int upperBound;
    private double rateOfCrossover;
    private double rateOfMutation;

    public Chromosome(int dimension, int lowBound, int upperBound, double rateOfCrossover, double rateOfMutation) {
        this.dimension = dimension;
        this.lowBound = lowBound;
        this.upperBound = upperBound;
        this.rateOfCrossover = rateOfCrossover;
        this.rateOfMutation = rateOfMutation;
        this.genes = new ArrayList<>();
        for (int i = lowBound; i <= upperBound; i++) {
            genes.add(i);
        }
        Collections.shuffle(genes);
    }


    public void evauate(Evaluator evaluator) {
        fitness = evaluator.evaluate(this.getGenes());
    }

    /**
     *  随机交叉所选中的成对城市坐标，以确保交叉后的路径上每个城市只到访一次
     * @param chromosome
     */
    public void cross(Chromosome chromosome) {
        for (int i = 0; i < dimension; i++) {
            int value1 = this.genes.get(i);
            double p = new Random().nextDouble();
            if(p < rateOfCrossover){
                Integer value2 = chromosome.getGenes().get(i);
                int indexOfValue2 = this.genes.indexOf(value2);
                Collections.swap(this.genes,i,indexOfValue2);

                int indexOfValue1 = chromosome.getGenes().indexOf(value1);
                Collections.swap(chromosome.getGenes(),i,indexOfValue1);
            }
        }
    }

    /**
     *
     */
    public void mutate() {
        for (int i = 0; i < dimension; i++) {
            double p = new Random().nextDouble();
            if(p < rateOfMutation){
                int index1;
                int index2;
                do{
                    index1 = new Random().nextInt(dimension)+1;
                    index2 = new Random().nextInt(dimension)+1;
                }while(index1 == index2);
                Collections.swap(genes,index1,index2);
            }
        }
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
            chromosome.setGenes(this.genes.stream().collect(Collectors.toList()));
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return chromosome;
    }
}

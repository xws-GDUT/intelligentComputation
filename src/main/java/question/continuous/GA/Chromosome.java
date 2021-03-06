package question.continuous.GA;
import lombok.Data;
import evaluator.Evaluator;

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
    private double fitness;
//    private int dimension;
//    private double lowBound;
//    private double upperBound;
    private double rateOfCrossover;
    private double rateOfMutation;

    public Chromosome(int dimension,double lowBound,double upperBound, double rateOfCrossover, double rateOfMutation) {
//        this.dimension = dimension;
//        this.lowBound = lowBound;
//        this.upperBound = upperBound;
        this.rateOfCrossover = rateOfCrossover;
        this.rateOfMutation = rateOfMutation;
        this.genes = new ArrayList<>(dimension);
        for (int i = 0; i < dimension; i++) {
            genes.add( (upperBound - lowBound) * new Random().nextDouble() + lowBound);
        }
    }

    public void evauate(Evaluator evaluator) {
        fitness = evaluator.evaluate(this.getGenes());
    }

    /**
     * 均匀交叉
     * @param chromosome
     */
    public void cross(Chromosome chromosome) {
//            int point = new Random().nextInt(dimension);
        for (int i = 0; i < genes.size(); i++) {
            double p = new Random().nextDouble();
            if(p<rateOfCrossover){
                double temp = genes.get(i);
                genes.set(i,chromosome.getGenes().get(i));
                chromosome.getGenes().set(i,temp);
            }
        }
    }

    /**
     * 域内随机变异
     */
    public void mutate(double lowBound,double upperBound) {
       for (int i = 0; i < genes.size(); i++) {
            double p = new Random().nextDouble();
            if(p<rateOfMutation){
                genes.set(i, (upperBound - lowBound) * new Random().nextDouble() + lowBound);
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

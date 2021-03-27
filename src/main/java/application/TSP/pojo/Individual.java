package application.TSP.pojo;

import application.TSP.evoluator.Evaluator;
import application.TSP.exception.SizeOfSolutionException;
import lombok.Data;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * @Author xws
 * @email wansenxu@163.com
 */

//不支持无参构造，创建个体时必须设置solution中每个分量的取值范围
@Data
public class Individual<T> implements Comparable<Individual>,Cloneable{
    private List<Integer> solution;
    private double fitness;
    private double lowBound;
    private double upperBound;
    private double rateOfCrossover;
    private double rateOfMutation;
    private int dimension;

    @Override
    public int compareTo(Individual o) {
        if(fitness<o.fitness){
            return -1;
        }else if(fitness>o.fitness){
            return 1;
        }else{
            return 0;
        }
    }

    @Override
    public Individual clone(){
        Individual individual = null;
        try {
            individual = (Individual)super.clone();
            List<Integer> solution = new ArrayList<>();
            for (int i = 0; i < this.solution.size(); i++) {
                solution.add(this.solution.get(i));
            }
            individual.setSolution(solution);
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }

        return individual;
    }

    public Individual(int dimension,double lowBound, double upperBound,double rateOfCrossover,double rateOfMutation) {
        this.dimension = dimension;
        this.lowBound = lowBound;
        this.upperBound = upperBound;
        this.rateOfCrossover=rateOfCrossover;
        this.rateOfMutation=rateOfMutation;

        // 将随机一组城市号且每个城市号均不相同有且仅有第一维与最后一维的城市号相同，将其作为一组解
        this.solution = new ArrayList<>();
        for (int i = 0; i < dimension-1; i++) {
            this.solution.add(i+1);
        }
        Collections.shuffle(this.solution);
        if(this.solution.get(0)!=null){
            this.solution.add(this.solution.get(0));
        }
    }
    /**
     * 对solution进行量化评估，将评估后的值置为该个体的fitness
     * @param evaluator
     */
    public void evaluate(Evaluator evaluator){
        double value = evaluator.evaluate(this);
        this.fitness = value;
    }

    public void cross(Individual individual){
        if(this.solution.size()!=individual.solution.size()){
            throw new SizeOfSolutionException("this.solution.size()!=individual.solution.size()");
        }
        for (int i = 0; i < solution.size(); i++) {
            double p = new Random().nextDouble();
            if(p<rateOfCrossover){
                if(solution.get(i).equals(individual.getSolution().get(i))){
                    break;
                }else{
                    int value = (int) individual.getSolution().get(i);
                    int indexOfValue = solution.indexOf(value);
                    int value2 = solution.get(i);
                    int indexOfValue2 = individual.getSolution().indexOf(value2);
                    if(i==0 || i==solution.size()-1){
                        solution.set(indexOfValue,solution.get(i));
                        solution.set(0,value);
                        solution.set(solution.size()-1,value);

                        individual.getSolution().set(indexOfValue2,individual.getSolution().get(i));
                        individual.getSolution().set(0,value2);
                        individual.getSolution().set(individual.getSolution().size()-1,value2);
                    }else{
                        if(indexOfValue==0 || indexOfValue==solution.size()-1){
                            solution.set(0,solution.get(i));
                            solution.set(solution.size()-1,solution.get(i));
                            solution.set(i,value);
                        }else{
                            Collections.swap(solution,i,indexOfValue);
                        }
                        if(indexOfValue2==0 || indexOfValue2==individual.getSolution().size()-1){
                            individual.getSolution().set(0,individual.getSolution().get(i));
                            individual.getSolution().set(individual.getSolution().size()-1,individual.getSolution().get(i));
                            individual.getSolution().set(i,value2);
                        }else{
                            Collections.swap(individual.getSolution(),i,indexOfValue2);
                        }
                    }
                }
            }
        }
    }
    public void mutate(){
        double p = new Random().nextDouble();
        if(p < rateOfMutation){
            int randIndex1;
            int randIndex2;
            do{
                randIndex1 = (new Random().nextInt(dimension));
                randIndex2 = (new Random().nextInt(dimension));
            }while(randIndex1==randIndex2 || Math.abs(randIndex1-randIndex2)==dimension-1); //随机两个不相等且不是第一维和最后一维的下标
            //当随机得到的两个下标均不等于第一维和最后一维的下标时，则直接交换
            if(randIndex1!=0 && randIndex1!=solution.size()-1 && randIndex2!=0 && randIndex2!=solution.size()-1){
                Collections.swap(solution,randIndex1,randIndex2);
            }
            // 其中一个下标等于第一维和最后一维的下标时
            if(randIndex1==0 || randIndex1==solution.size()-1){
                int tmp = solution.get(randIndex1);
                solution.set(0,solution.get(randIndex2));
                solution.set(solution.size()-1,solution.get(randIndex2));
                solution.set(randIndex2,tmp);
            }
            if(randIndex2==0 || randIndex2==solution.size()-1) {
                int tmp = solution.get(randIndex2);
                solution.set(0,solution.get(randIndex1));
                solution.set(solution.size()-1,solution.get(randIndex1));
                solution.set(randIndex1,tmp);
            }
        }

    }
}

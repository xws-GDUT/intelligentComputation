package intelligentComputation.operator.crossover;

import intelligentComputation.Individual;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 *@Description 抽象的杂交器
 *@Author 许万森
 *@email wansenxu@163.com
 */

public abstract class Crossover{
    protected double rateOfCrossover;
    //双种群间的交叉
    public List<Individual> cross(List<Individual> pop, List<Individual> mutatedPop){
        return null;
    }
    //单种群内的交叉
    public void cross(List<Individual> pop){
    }

    public Crossover setRateOfCrossover(double value) {
        this.rateOfCrossover = value;
        return this;
    }
    /**
     * 交叉方式：两个个体以二项式交叉的方式进行交叉
     * @param i1
     * @param i2
     */
    protected void binomialCross(Individual i1,Individual i2){
        for(int j = 0; j < i1.getSolution().size(); j++){
            double p = new Random().nextDouble();
            if(p < rateOfCrossover){
                double temp = (double) i1.getSolution().get(j);
                i1.getSolution().set(j,i2.getSolution().get(j));
                i2.getSolution().set(j,temp);
            }
        }
    }

    /**
     * 交叉方式：两个个体以二项式交叉的方式进行交叉，随机一维分量必定交叉
     * @param i1
     * @param i2
     */
    protected void binomialCross2(Individual i1,Individual i2){
        for(int j = 0; j < i1.getSolution().size(); j++){
            int popisitonOfCrossover = new Random().nextInt(i1.getSolution().size());
            double p = Math.random();
            if( p > rateOfCrossover && popisitonOfCrossover != j){ // 不交叉
            }else{ //交叉
                double temp = (double) i1.getSolution().get(j);
                i1.getSolution().set(j,i2.getSolution().get(j));
                i2.getSolution().set(j,temp);
            }
        }
    }




}

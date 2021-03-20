package intelligentComputation.operator.selector;

import intelligentComputation.Individual;

import java.util.List;

/**
 *@Description 抽象的选择器
 *@Author 许万森
 *@email wansenxu@163.com
 */

public abstract class Selector{
    public List<Individual> select(List<Individual> pop, List<Individual> offspring){
        return null;
    }
    public List<Individual> select(List<Individual> pop){
        return null;
    }
}


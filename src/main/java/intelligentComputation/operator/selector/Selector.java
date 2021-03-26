package intelligentComputation.operator.selector;

import intelligentComputation.Individual;

import java.util.List;

/**
 *@Description 抽象的选择器
 *@Author 许万森
 *@email wansenxu@163.com
 */

public abstract class Selector<T>{
    public List<T> select(List<T> pop, List<T> offspring){
        return null;
    }
    public List<T> select(List<T> pop){
        return null;
    }
}


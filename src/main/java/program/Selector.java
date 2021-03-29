package program;

import java.util.List;

/**
 *@Description 抽象的选择器
 *@Author 许万森
 *@email wansenxu@163.com
 */

public interface Selector<T>{
    public List<T> select(List<T> pop);
    public List<T> select(List<T> pop,List<T> offspring);
}


package program;


import java.util.List;

/**
 * @Author xws
 * @email wansenxu@163.com
 */
public interface Evaluator<T>{
    double evaluate(List<T> solution);
}

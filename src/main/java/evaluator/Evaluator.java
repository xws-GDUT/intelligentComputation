package evaluator;


import java.util.List;

/**
 * @Author xws
 * @email wansenxu@163.com
 */
public interface Evaluator<T>{
    double evaluate(List<T> solution);
    int getNumOfEvaluate();
    default double getUpperBound(){
        return Double.POSITIVE_INFINITY;
    }
    default double getLowerBound(){
        return Double.NEGATIVE_INFINITY;
    }
    default int getDimension(){
        return 0;
    }

}

package question.continuous.SA;

import lombok.Data;

import java.util.List;

/**
 * @Author xws
 * @email wansenxu@163.com
 */
@Data
public class Individual {
    private List<Double> x; //解向量
    private int D;          //解向量的维度
    private double upperBound; //解向量中每一维分量的上界
    private double lowerBound; //解向量中每一维分量的下界
    private double fitness;

}

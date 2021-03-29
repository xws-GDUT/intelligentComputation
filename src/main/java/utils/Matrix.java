package utils;

import exception.MaxtrixPlusOperatorException;

import java.util.List;

/**
 * @Author xws
 * @email wansenxu@163.com
 */
public class Matrix {
    public static List<Double> plus(List<Double> list1, List<Double> list2){
        if(list1.size()!=list2.size()){
            throw new MaxtrixPlusOperatorException("list1.size()!=list2.size()");
        }
        for (int i = 0; i < list1.size(); i++) {
            list1.set(i,list1.get(i)+list2.get(i));
        }
        return list1;
    }
}

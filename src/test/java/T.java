import expriment.Graph;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author xws
 * @email wansenxu@163.com
 */
public class T {
    @Test
    void test(){
        List<Double> list =new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            list.add(1.0);
        }
//        Double collect = list.stream().collect(Collectors.averagingDouble(Double::doubleValue));
        double asDouble = list.stream().mapToDouble(Double::doubleValue).average().getAsDouble();
        System.out.println(asDouble);
    }

    @Test
    void test1(){
        Graph graph = new Graph();
        System.out.println(graph);
    }
}

package intelligentComputation.util;

import java.util.ArrayList;
import java.util.List;

public class Matrix {
    /**
     * @program: intelligentComputing
     *
     * @description:
     *
     * @author: 许万森
     *
     * @create: 2020-12-28 16:10
     **/

    public static List<Double> plus(List<Double> list1,List<Double> list2){
        if(list1.size()!=list2.size()){
            throw new OperateException("list1.size()!=list2.size()");
        }else{
            List<Double> resutl  =new ArrayList<>();
            for (int i = 0; i < list1.size(); i++) {
                resutl.add(list1.get(i)+list2.get(i));
            }
            return resutl;
        }
    }

}

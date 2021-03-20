package intelligentComputation;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Bound<T>{
    private T lowerBound;
    private T upperBound;
}
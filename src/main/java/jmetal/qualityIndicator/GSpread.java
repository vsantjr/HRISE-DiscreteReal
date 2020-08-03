package jmetal.qualityIndicator;

import java.util.List;
import org.uma.jmetal.qualityindicator.impl.GeneralizedSpread;
import org.uma.jmetal.solution.Solution;
import org.uma.jmetal.util.front.Front;
import org.uma.jmetal.util.front.imp.ArrayFront;

/**
 *
 * @author vinicius
 */
public class GSpread<S extends Solution<?>> extends GeneralizedSpread<S>{

    public double generalizedSpread(List f1set, List pfSet, int numberOfObj) {
        Front f1=new ArrayFront(f1set);
        Front pf=new ArrayFront(pfSet);
        return this.generalizedSpread(f1, pf);
    }
    
}

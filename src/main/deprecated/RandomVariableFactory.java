package main.deprecated;

import java.util.Random;

@Deprecated
public interface RandomVariableFactory {
    RandomVariable create(double[] params);
    RandomVariable create(double[] params, Random generator);
}

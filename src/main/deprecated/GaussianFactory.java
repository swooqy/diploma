package main.deprecated;

import java.util.Random;

@Deprecated
public class GaussianFactory implements RandomVariableFactory {
    @Override
    public RandomVariable create(double[] params) {
        return new GaussianRandomVariable(params[0], params[1]);
    }

    @Override
    public RandomVariable create(double[] params, Random generator) {
        return new GaussianRandomVariable(params[0], params[1], generator);
    }
}

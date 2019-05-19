package main.deprecated;

@Deprecated
public interface RandomVariable {
    double getMean();
    double getDispersion();
    double distributionDensity(double x);
    double next();
}


package main.deprecated;

import java.util.Random;

@Deprecated
public class GaussianRandomVariable implements RandomVariable {
    protected final double mu,sigma;
    protected final Random r;

    public GaussianRandomVariable(double mu, double sigma, Random generator){
        this.mu=mu;
        this.sigma=sigma;
        r=generator;
    }
    public GaussianRandomVariable(double mu,double sigma){
        this(mu,sigma,new Random());
    }

    @Override
    public double getMean() {
        return mu;
    }

    @Override
    public double getDispersion() {
        return sigma;
    }

    @Override
    public double distributionDensity(double x) {
        return  (1/sigma*Math.sqrt(2*Math.PI))*Math.exp((-(x-mu)*(x-mu))/(2*sigma*sigma));
    }

    @Override
    public double next() {
        return (r.nextGaussian()*sigma)+mu;
    }
}

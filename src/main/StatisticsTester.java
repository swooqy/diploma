package main;

import org.apache.commons.math3.distribution.MultivariateNormalDistribution;

public class StatisticsTester {

    protected final double[][] means;
    protected final double[][] covarianceMatrix;
    protected final double ahigh,blow;
    protected MultivariateNormalDistribution variable;

    public StatisticsTester(double[][] means,double[][] covarianceMatrix, int hypIndex, double alpha, double beta) {
        variable= new MultivariateNormalDistribution(means[hypIndex],covarianceMatrix);
        this.covarianceMatrix=covarianceMatrix;
        this.means = means;
        ahigh=Math.log((1-beta)/alpha);
        blow=Math.log(beta/(1-alpha));
    }

    protected double countStep(){
        double[] generatedSample=variable.sample();
        MultivariateNormalDistribution r1 = new MultivariateNormalDistribution(means[0],covarianceMatrix);
        MultivariateNormalDistribution r2 = new MultivariateNormalDistribution(means[1],covarianceMatrix);
        return Math.log(r2.density(generatedSample)/r1.density(generatedSample));
    }

    public int[] run() {
        int[] returner=new int[2];
        int currentStep=0;
        double currentSum=0;
        while(currentSum>blow && currentSum<ahigh){
            currentSum+=countStep();
            currentStep++;
        }
        if(currentSum<blow){
            returner[0]=1;
        }else{
            returner[0]=2;
        }
        returner[1]=currentStep;
        return returner;
    }
}

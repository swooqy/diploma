package main;

import org.apache.commons.math3.distribution.MultivariateNormalDistribution;

import java.util.Arrays;

public class StatisticsTesterWithMissingElements extends StatisticsTester {
    protected int[] elementsToMiss;
    public StatisticsTesterWithMissingElements(double[][] means,double[][] covarianceMatrix, int hypIndex, double alpha, double beta,int[] elementsToMiss) {
        super(means,covarianceMatrix,hypIndex,alpha,beta);
        this.elementsToMiss=elementsToMiss;
    }
    protected double[] copyArrayWithMissingIndexes(double[] inputArray, int[] missingIndexes){
        double[] copiedArray=new double[inputArray.length-missingIndexes.length];
        int j=0;
        int k=0;

        for(int i=0;i<inputArray.length;i++){
            if(k<missingIndexes.length){
                if(missingIndexes[k]==i){
                    k++;
                    continue;
                }
            }
            copiedArray[j++]=inputArray[i];
        }
        return copiedArray;
    }
    protected double[][] copyMatrixWithMissingIndexes(double[][] inputMatrix, int[] missingIndexes){
        double[][] copiedMatrix=new double[inputMatrix.length-missingIndexes.length][inputMatrix.length-missingIndexes.length];
        int j=0;
        int k=0;
        for(int i=0;i<inputMatrix.length;i++){
            if(k<missingIndexes.length){
                if(missingIndexes[k]==i){
                    k++;
                    continue;
                }
            }
            copiedMatrix[j]=copyArrayWithMissingIndexes(inputMatrix[i],missingIndexes);
            j++;
        }
        return copiedMatrix;
    }
    protected double[] shortMean(double[] input){
        double[] shortened=new double[input.length-elementsToMiss.length];
        for(int i=0;i<shortened.length;i++){
            shortened[i]=input[i];
        }
        return shortened;
    }
    @Override
    protected double countStep(){
        //double[] generatedSample=variable.sample();
        double[] generatedSampleMissed=copyArrayWithMissingIndexes(variable.sample(),elementsToMiss);
        double[][]covarianceMissed=copyMatrixWithMissingIndexes(covarianceMatrix,elementsToMiss);
        MultivariateNormalDistribution r1 = new MultivariateNormalDistribution(shortMean(means[0]),covarianceMissed);
        MultivariateNormalDistribution r2 = new MultivariateNormalDistribution(shortMean(means[1]),covarianceMissed);
        return Math.log(r2.density(generatedSampleMissed)/r1.density(generatedSampleMissed));
    }
}

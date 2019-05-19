package main;


import org.apache.commons.math3.distribution.MultivariateNormalDistribution;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class StatisticsTesterWithMissingElementsModified extends StatisticsTesterWithMissingElements {
    protected HashMap<Integer,Integer> elementsToMiss;
    public StatisticsTesterWithMissingElementsModified(double[][] means,double[][] covarianceMatrix, int hypIndex, double alpha, double beta,int[] elementsToMiss) {
        super(means,covarianceMatrix,hypIndex,alpha,beta,elementsToMiss);
        this.elementsToMiss=new HashMap<Integer,Integer>();
        for(int i=0;i<elementsToMiss.length;i++){
            this.elementsToMiss.put(elementsToMiss[i],elementsToMiss[i]);
        }
    }
    private double[] modifySample(double[] originalSample){
        double[] modifiedSample=Arrays.copyOf(originalSample,originalSample.length);
        for(int i=0;i<originalSample.length;i++){
            if(elementsToMiss.containsKey(i)){
                modifiedSample=recoverComponent(modifiedSample,i);
            }
        }
        return modifiedSample;
    }
    protected double[] recoverComponent(double[] originalSample,int index){
        int n=originalSample.length;
        double[] modifiedSample=Arrays.copyOf(originalSample,n);
        double[][] copyOfCovarianceMatrix = new double[n][n];
        for (int i = 0; i < n; i++) {
            System.arraycopy(covarianceMatrix[i], 0, copyOfCovarianceMatrix[i], 0, n);
        }
        double hyp1=means[0][0];
        double hyp2=means[1][0];
        double diff=hyp2-hyp1;
        double[][] inverseCovarianceMatrix=Inverse.invert(copyOfCovarianceMatrix);
        double meanEvaluation=countMeanEvaluation(index,n,originalSample,inverseCovarianceMatrix);
        if((meanEvaluation-hyp1)<(diff/2)){
            meanEvaluation=hyp1;
        }else{
            meanEvaluation=hyp2;
        }
        double deltaEvaluation=countDeltaEvaluation(index,n,originalSample,inverseCovarianceMatrix,meanEvaluation);
        double recoveredComponent=meanEvaluation+deltaEvaluation;
        modifiedSample[index]=recoveredComponent;
        return modifiedSample;
    }
    private double countMeanEvaluation(int index,int length,double[] originalSample,double[][] inverseCovarianceMatrix){
        double sumUpper=0;
        double sumLower=0;
        for(int i=0;i<length;i++){
            if(elementsToMiss.containsKey(i)){
                continue;
            }
            for(int j=0;j<length;j++){
                if(elementsToMiss.containsKey(j)){
                    continue;
                }
                sumUpper+=(inverseCovarianceMatrix[index][index]*inverseCovarianceMatrix[i][j]-inverseCovarianceMatrix[i][index]*inverseCovarianceMatrix[index][j])*originalSample[j];
                sumLower+=inverseCovarianceMatrix[index][index]*inverseCovarianceMatrix[i][j]-inverseCovarianceMatrix[i][index]*inverseCovarianceMatrix[index][j];
            }
        }
        return sumLower/sumLower;
    }
    private double countDeltaEvaluation(int index, int length,double[] originalSample,double[][]inverseCovarianceMatrix,double meanEvaluation){
        double sumUpper=0;
        for(int i=0;i<length;i++){
            if(i==index){
                continue;
            }
            sumUpper+=inverseCovarianceMatrix[index][i]*(originalSample[i]-meanEvaluation);
        }
        return -sumUpper/inverseCovarianceMatrix[index][index];
    }
    protected double countVariantOFX(double covariance,double sampleReal,double meanHyp){
        return covariance/sampleReal;
    }
    @Override
    protected double countStep(){
        double[] generatedSample=variable.sample();
        generatedSample=modifySample(generatedSample);
        //double[][]covarianceMissed=copyMatrixWithMissingIndexes(covarianceMatrix,elementsToMiss);
        MultivariateNormalDistribution r1 = new MultivariateNormalDistribution(means[0],covarianceMatrix);
        MultivariateNormalDistribution r2 = new MultivariateNormalDistribution(means[1],covarianceMatrix);
        return Math.log(r2.density(generatedSample)/r1.density(generatedSample));
    }
}

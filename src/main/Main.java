package main;

public class Main {

    protected static void printResults(double[] results){
        System.out.println("Average number of steps:");
        System.out.println((int)results[2]+"/"+(int)results[3]);
        System.out.println("1st type error evaluation: "+results[0]);
        System.out.println("2nd type error evaluation: "+results[1]);
    }
    protected static void printVector(double[] input){
        for(int i=0;i<input.length;i++){
            System.out.print(input[i]+"   ");
        }
        System.out.println();
    }

    public static void main(String[] args) {
        double alpha,beta,hyp1,hyp2;
        alpha=0.1;
        beta=0.1;
        hyp1=0;
        hyp2=0.2;
        double[]distributionParams={0,1};
        int dimensionSize=5;
        int timesToTest=10000;
        int[] elementsToMiss={1,2};
        double[][]covarianceMatrix={
                {1,     0.5,  0.3,  0.3,  0.1},
                {0.5,   1,  0.5,  0.3,  0.3},
                {0.3,     0.5,  1,  0.5,  0.3},
                {0.3,     0.3,  0.5,  1,  0.5},
                {0.1,     0.3,  0.3,  0.5,  1}};
        //MultivariateNormalDistribution mvd=new MultivariateNormalDistribution(means,covarianceMatrix);
        //double[] sample=mvd.sample();
        //printVector(sample);
        //double[] results=Tester.test(covarianceMatrix,hyp1,hyp2,alpha,beta,dimensionSize,timesToTest);
        double[] resultsOriginal=Tester.test(covarianceMatrix,hyp1,hyp2,alpha,beta,dimensionSize,timesToTest);
        printResults(resultsOriginal);
        System.out.println();
        double[] results=TesterWithMissingElements.test(covarianceMatrix,hyp1,hyp2,alpha,beta,dimensionSize,timesToTest,elementsToMiss);
        printResults(results);
        System.out.println();
        double[] resultsModified=TesterWithMissingElementsModified.test(covarianceMatrix,hyp1,hyp2,alpha,beta,dimensionSize,timesToTest,elementsToMiss);
        printResults(resultsModified);
    }
}

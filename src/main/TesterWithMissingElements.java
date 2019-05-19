package main;

public class TesterWithMissingElements {
    public static double[] test(double[][]covarianceMatrix,double hypotesis1,double hypotesis2,double alpha,double beta,int dimensionSize,int timesToTest,int[] elementsToMiss){
        double[][]means=new double[2][dimensionSize];
        for(int i=0;i<dimensionSize;i++){
            means[0][i]=hypotesis1;
            means[1][i]=hypotesis2;
        }
        int steps1=0;
        int steps2=0;
        int timeserror1=0;
        int timeserror2=0;
        StatisticsTester gen1=new StatisticsTesterWithMissingElements(means,covarianceMatrix,0,alpha,beta,elementsToMiss);
        StatisticsTester gen2=new StatisticsTesterWithMissingElements(means,covarianceMatrix,1,alpha,beta,elementsToMiss);
        int[] temp1,temp2;
        for(int i=0;i<timesToTest;i++){
            temp1=gen1.run();
            temp2=gen2.run();
            if(temp1[0]==2){
                timeserror1++;
            }
            if(temp2[0]==1){
                timeserror2++;
            }
            steps1+=temp1[1];
            steps2+=temp2[1];
        }
        double errora=(double)timeserror1/timesToTest;
        double errorb=(double)timeserror2/timesToTest;
        steps1/=timesToTest;
        steps2/=timesToTest;
        return new double[]{errora,errorb,(double)steps1,(double)steps2};
    }
}

import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;
import edu.princeton.cs.algs4.StdIn;

public class PercolationStats {
    //perform T independent experiments on an N-by-N grid
    private int overallcount;
    private int count = 0;
    private double prob = 0;
    private double[] probset;
    public PercolationStats(int N, int T) {
        overallcount = T;
        if (N <= 0 || T <= 0) {
            throw new IllegalArgumentException("Given N<=0 || T <=0");
        }

        probset = new double[T];

        for (int i =0; i < T; i++) {
            int opensites = 0;
            Percolation anew = new Percolation(N);
            //randomly fill the blanks until its percolate, return one number
            //store the array and compute the mean,stddev, confidence inteval.
            while (!anew.percolates()) {
                int m = StdRandom.uniform(1, N+1);
                int j = StdRandom.uniform(1, N+1);
                if (!anew.isOpen(m, j)) {
                anew.open(m, j);
                opensites++;
                }
            }
            prob = (double) opensites/(N*N);
            probset[i] = prob;
        }

    }
    //sample mean of percolation threshold
    public double mean() {
        return StdStats.mean(probset);

    }
    //sample standard deviation of percolation threshold
    public double stddev() {
        return StdStats.stddev(probset);

    }
    //low endpoint of 95% confidence interval
    public double confidenceLo() {
        return mean()-(1.96*stddev())/Math.sqrt(overallcount);

    }
    //high endpoint of 95% confidence interval
    public double confidenceHi() {
        return mean()+(1.96*stddev())/Math.sqrt(overallcount);

    }
    public static void main(String[] args) {
        
        int N = StdIn.readInt();
        int T = StdIn.readInt();
        PercolationStats anewtest = new PercolationStats(N, T);
        System.out.println("% java percolationStats "+N+" "+T);
        String confidence = anewtest.confidenceLo() + ", " + anewtest.confidenceHi();
        System.out.println("mean                    = " + anewtest.mean());
        System.out.println("stddev                  = " + anewtest.stddev());
        System.out.println("95% confidence interval = " + confidence);

    }

}

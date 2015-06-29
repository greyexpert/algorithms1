public class PercolationStats 
{
    private double[] thresholds;
    private int T = 0;
    private int N = 0;
    
    public PercolationStats(int N, int T)     // perform T independent experiments on an N-by-N grid
    {
        if (T <= 0 || N <= 0) {
            throw new java.lang.IllegalArgumentException();
        }
        
        this.T = T;
        this.N = N;
        
        thresholds = getThresholds();
    }
    
    private double threshold()
    {
        Percolation p = new Percolation(N);
        
        int count = 0;
        
        while (!p.percolates())
        {
            int column = StdRandom.uniform(N) + 1;
            int row = StdRandom.uniform(N) + 1;
            
            if (!p.isOpen(row, column))
            {
                p.open(row, column);
                count += 1;
            }
        }
        
        return (double) count / (N * N);
    }
    
    private double[] getThresholds()
    {
        double[] out = new double[T];
    
        for (int i = 0; i < T; i++) {
            out[i] = threshold();
        }
        
        return out;
    }
    
    public double mean()                      // sample mean of percolation threshold
    {
        return StdStats.mean(thresholds);
    }
    
    public double stddev()                    // sample standard deviation of percolation threshold
    {
       return StdStats.stddev(thresholds);
    }
    
    public double confidenceLo()              // low  endpoint of 95% confidence interval
    {
        double mean = mean();
        double stddev = stddev();
        
        return mean - (1.96 * stddev / Math.sqrt(T));
    }
    
    public double confidenceHi()              // high endpoint of 95% confidence interval
    {
        double mean = mean();
        double stddev = stddev();
        
        return mean + (1.96 * stddev / Math.sqrt(T));
    }
    
    public static void main(String[] args)    // test client (described below)
    {
        int N = Integer.parseInt(args[0]);
        int T = Integer.parseInt(args[1]);
        
        PercolationStats ps = new PercolationStats(N, T);
        double mean = ps.mean();
        double stddev = ps.stddev();
        double confidenceLo = ps.confidenceLo();
        double confidenceHi = ps.confidenceHi();
        
        System.out.println(String.format("mean                    = %f", mean));
        System.out.println(String.format("stddev                  = %f", stddev));
        System.out.println(String.format("95%% confidence interval = %f, %f", confidenceLo, confidenceHi));
    }
}
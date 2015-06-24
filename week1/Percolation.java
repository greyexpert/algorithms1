
public class Percolation 
{
    private WeightedQuickUnionUF uf;
    private boolean[] opened;
    
    
    private int sideLength = 0;
    private int total = 0;
    private int top = 0;
    private int bottom = 0;

    public Percolation(int N)               // create N-by-N grid, with all sites blocked
    {
        if ( N <= 0 ) {
            throw new java.lang.IllegalArgumentException();
        }
    
        sideLength = N;
        total = sideLength * sideLength;
        
        top = total;
        bottom = total + 1;
                
        uf = new WeightedQuickUnionUF(total + 2);
        opened = new boolean[total];
    }
    
    private int translate(int i, int j)
    {
        int index =  sideLength * (i - 1) + j - 1;
                
        if ( index < 0 || index >= total ) {
            throw new java.lang.IndexOutOfBoundsException();
        }
        
        return index;
    }
    
    private void unionIfOpened(int index, int q) 
    {
        if ( !opened[q] ) {
            return;
        }
    
        uf.union(index, q);
    }
    
    public void open(int i, int j)          // open site (row i, column j) if it is not open already
    {
        if ( isOpen(i, j) ) {
            return;
        }
    
        int index = translate(i, j);
        opened[index] = true;
        
        if (j != sideLength)
        {
            unionIfOpened(index, translate(i, j + 1));
        }
        
        if (j != 1)
        {
            unionIfOpened(index, translate(i, j - 1));
        }
        
        if (i != sideLength) 
        {
            unionIfOpened(index, translate(i + 1, j));   
        }
        else
        {
            uf.union(index, bottom);
        }
        
        if (i != 1) 
        {
            unionIfOpened(index, translate(i - 1, j));
        }
        else
        {
            uf.union(index, top);
        }
    }
    
    public boolean isOpen(int i, int j)     // is site (row i, column j) open?
    {
        return opened[translate(i, j)];
    }
    
    public boolean isFull(int i, int j)     // is site (row i, column j) full?
    {
        return uf.connected(translate(i, j), top);
    }
    
    public boolean percolates()             // does the system percolate?
    {
        return uf.connected(top, bottom);
    }
}
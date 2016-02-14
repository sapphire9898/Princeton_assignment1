import edu.princeton.cs.algs4.WeightedQuickUnionUF;
public class Percolation {
    private int[][] theGrid;
    private boolean[][] openGrid;
    private int size;
    private int top = 0;
    private int bottom;
    private WeightedQuickUnionUF qf;

    public Percolation(int N) { //create N-by-N grid, with all sites blocked.
        size = N;
        bottom = size*size+1;
        theGrid = new int[size][size];
        qf = new WeightedQuickUnionUF(size*size+2);
        openGrid = new boolean[size][size];
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                theGrid[i][j] = i*size+j+1;
                openGrid[i][j] = false;
            }
        }

    }
    public void open(int i, int j) { //open site(row i, column j) if it is not open already.
        openGrid[i-1][j-1] = true;
        if (i == 1) {
            qf.union(theGrid[i-1][j-1], top);
        }
        if (i == size) {
            qf.union(theGrid[i-1][j-1], bottom);
        }
        if (i > 1 && isOpen(i-1, j)) {
            qf.union(theGrid[i-1][j-1], theGrid[i-2][j-1]);
        }
        if (i < size && isOpen(i+1, j)) {
            qf.union(theGrid[i-1][j-1], theGrid[i][j-1]);
        }
        if (j > 1 && isOpen(i, j-1)) {
            qf.union(theGrid[i-1][j-1], theGrid[i-1][j-2]);
        }
        if (j < size && isOpen(i, j+1)) {
            qf.union(theGrid[i-1][j-1], theGrid[i-1][j]);
        }
    }
    public boolean isOpen(int i, int j) {
        return openGrid[i-1][j-1];

    }
    public boolean isFull(int i, int j) {
        if (0 < i && i <= size && 0 < j && j <= size) {
            return qf.connected(top, theGrid[i-1][j-1]);
        }
        else {
            throw new IndexOutOfBoundsException();
        }
    }
    public boolean percolates() {
        return qf.connected(top, bottom);
    }
    
}
/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private final int size;
    private boolean[] a;
    private int openedCount;
    private final WeightedQuickUnionUF quickUnionUF;
    private final WeightedQuickUnionUF quickUnionUFFull;

    public Percolation(int n)                // create n-by-n grid, with all sites blocked
    {
        if (n <= 0) throw new java.lang.IllegalArgumentException();
        quickUnionUF = new WeightedQuickUnionUF(n * n + 2);
        quickUnionUFFull = new WeightedQuickUnionUF(n * n + 1);
        a = new boolean[n * n];
        openedCount = 0;
        size = n;
    }

    public void open(int row, int col)    // open site (row, col) if it is not open already
    {
        int ind = convertToIndex(row, col);
        if (a[ind]) return;
        a[ind] = true;
        openedCount++;
        if (row == 1) {
            quickUnionUF.union(ind, size * size);
            quickUnionUFFull.union(ind, size * size);
        }
        if (row == size)
            quickUnionUF.union(ind, size * size + 1);
        if (col != size && a[ind + 1]) {
            quickUnionUF.union(ind, ind + 1);
            quickUnionUFFull.union(ind, ind + 1);
        }
        if (col - 1 > 0 && a[ind - 1]) {
            quickUnionUF.union(ind, ind - 1);
            quickUnionUFFull.union(ind, ind - 1);
        }
        if (row != size && a[ind + size]) {
            quickUnionUF.union(ind, ind + size);
            quickUnionUFFull.union(ind, ind + size);
        }
        if (row - 1 > 0 && a[ind - size]) {
            quickUnionUF.union(ind, ind - size);
            quickUnionUFFull.union(ind, ind - size);
        }
    }

    public boolean isOpen(int row, int col)  // is site (row, col) open?
    {
        return a[convertToIndex(row, col)];
    }

    public boolean isFull(int row, int col)  // is site (row, col) full?
    {
        return isOpen(row, col) && quickUnionUFFull
                .connected(convertToIndex(row, col), size * size);
    }

    public int numberOfOpenSites()       // number of open sites
    {
        return openedCount;
    }

    public boolean percolates()              // does the system percolate?
    {
        return quickUnionUF.connected(size * size, size * size + 1);
    }

    private int convertToIndex(int row, int col) {
        if (row <= 0 || col <= 0 || row > size || col > size)
            throw new java.lang.IllegalArgumentException("row: " + row + " col: " + col);
        return (row - 1) * size + col - 1;
    }

    public static void main(String[] args)        // test client (described below)
    {
        int n = 3;
        Percolation p = new Percolation(n);
        p.open(1, 3);
        p.open(2, 3);
        p.open(3, 3);
        p.open(3, 1);
        System.out.println(p.isFull(3, 1));
    }
}

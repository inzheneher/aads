import org.junit.Before;
import org.junit.Test;

public class PercolationTest {

    private Percolation percolation;
    private int arrayDimensionInRange;
    private int arrayDimensionOutRange;
    private int arrayDimension;
    private int rowInRange;
    private int rowOutOfRange;
    private int colInRange;
    private int colOutOfRange;

    @Before
    public void init() {
        arrayDimension = 10;
        arrayDimensionInRange = 1;
        rowInRange = 1;
        rowOutOfRange = arrayDimensionOutRange = 0;
        colInRange = 10;
        colOutOfRange = 11;
        percolation = new Percolation(arrayDimension);
    }

    @Test
    public void whenArgumentInConstructorMoreThanZeroThenPass() {
        percolation = new Percolation(arrayDimensionInRange);
    }

    @Test(expected = IllegalArgumentException.class)
    public void whenArgumentInConstructorLessThanZeroThrowIllegalArgumentException() {
        percolation = new Percolation(arrayDimensionOutRange);
    }

    @Test
    public void whenBothArgumentsInOpenIsInRangeThenPass() {
        percolation.open(rowInRange, colInRange);
    }

    @Test(expected = IllegalArgumentException.class)
    public void whenBothArgumentsInOpenMethodIsOutOfRangeThrowIllegalArgumentException() {
        percolation.open(rowOutOfRange, colOutOfRange);
    }

    @Test(expected = IllegalArgumentException.class)
    public void whenFirstArgumentInOpenIsOutOfRangeThrowIllegalArgumentException() {
        percolation.open(rowOutOfRange, colInRange);
    }

    @Test(expected = IllegalArgumentException.class)
    public void whenSecondArgumentInOpenIsOutOfRangeThrowIllegalArgumentException() {
        percolation.open(rowInRange, colOutOfRange);
    }

    @Test
    public void whenBothArgumentsInIsOpenInRangeThenPass() {
        percolation.isOpen(rowInRange, colInRange);
    }

    @Test(expected = IllegalArgumentException.class)
    public void whenBothArgumentsInIsOpenIsOutOfRangeThrowIllegalArgumentException() {
        percolation.isOpen(rowOutOfRange, colOutOfRange);
    }

    @Test(expected = IllegalArgumentException.class)
    public void whenFirstArgumentInIsOpenIsOutOfRangeThrowIllegalArgumentException() {
        percolation.isOpen(rowOutOfRange, colInRange);
    }

    @Test(expected = IllegalArgumentException.class)
    public void whenSecondArgumentInIsOpenIsOutOfRangeThrowIllegalArgumentException() {
        percolation.isOpen(rowInRange, colOutOfRange);
    }

    @Test
    public void whenBothArgumentsInIsFullInRangeThenPass() {
        percolation.isFull(rowInRange, colInRange);
    }

    @Test(expected = IllegalArgumentException.class)
    public void whenBothArgumentsInIsFullIsOutOfRangeThrowIllegalArgumentException() {
        percolation.isFull(rowOutOfRange, colOutOfRange);
    }

    @Test(expected = IllegalArgumentException.class)
    public void whenFirstArgumentInIsFullIsOutOfRangeThrowIllegalArgumentException() {
        percolation.isFull(rowOutOfRange, colInRange);
    }

    @Test(expected = IllegalArgumentException.class)
    public void whenSecondArgumentInIsFullIsOutOfRangeThrowIllegalArgumentException() {
        percolation.isFull(rowInRange, colOutOfRange);
    }
}
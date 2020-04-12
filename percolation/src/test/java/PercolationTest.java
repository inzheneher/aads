import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class PercolationTest {

    private Percolation percolation;
    private int arrayDimensionInRange;
    private int arrayDimensionOutRange;
    private int rowInRange;
    private int rowOutOfRange;
    private int colInRange;
    private int colOutOfRange;

    @Before
    public void init() {
        int arrayDimension = 10;
        arrayDimensionInRange = 1;
        rowInRange = 0;
        rowOutOfRange = arrayDimensionOutRange = -1;
        colInRange = arrayDimension - 1;
        colOutOfRange = arrayDimension * arrayDimension + 3;
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

    @Test
    public void whenRowAndColAreMappedCorrectlyToArrayIndexThenAssert() {
        assertEquals(17, percolation.getArrayIndexFor(1, 6));
        assertEquals(46, percolation.getArrayIndexFor(4, 5));
        assertEquals(69, percolation.getArrayIndexFor(6, 8));
        assertEquals(84, percolation.getArrayIndexFor(8, 3));
    }

    @Test(expected = IllegalArgumentException.class)
    public void whenRowAndColLessThanZeroThenThrowIllegalArgumentException() {
        percolation.getArrayIndexFor(-1, -1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void whenRowIsLessThanZeroThenThrowIllegalArgumentException() {
        percolation.getArrayIndexFor(-1, 1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void whenColIsLessThanZeroThenThrowIllegalArgumentException() {
        percolation.getArrayIndexFor(1, -1);
    }

    @Test
    public void whenRowAndColMoreThanZeroAndLessThanArraySizeThenAssert() {
        percolation.getArrayIndexFor(1, 1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void whenRowMoreThanArraySizeThenThrowIllegalArgumentException() {
        percolation.getArrayIndexFor(percolation.getNByNGrid().length + 1,1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void whenColMoreThanArraySizeThenThrowIllegalArgumentException() {
        percolation.getArrayIndexFor(1, percolation.getNByNGrid().length + 1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void whenRowAndColMoreThanZeroAndMoreThanArraySizeThrowIllegalArgumentException() {
        percolation.getArrayIndexFor(percolation.getNByNGrid().length + 1, percolation.getNByNGrid().length + 1);
    }
}
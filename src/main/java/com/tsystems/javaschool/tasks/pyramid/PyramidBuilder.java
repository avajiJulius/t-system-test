package com.tsystems.javaschool.tasks.pyramid;

import java.util.Arrays;
import java.util.List;

public class PyramidBuilder {

    static int count = 0;

    /**
     * Builds a pyramid with sorted values (with minumum value at the top line and maximum at the bottom,
     * from left to right). All vacant positions in the array are zeros.
     *
     * @param inputNumbers to be used in the pyramid
     * @return 2d array with pyramid inside
     * @throws {@link CannotBuildPyramidException} if the pyramid cannot be build with given input
     */
    public int[][] buildPyramid(List<Integer> inputNumbers) {
        // TODO : Implement your solution here
        try {
            inputNumbers.sort(Integer::compareTo);
        } catch (NullPointerException ex) {
            throw new CannotBuildPyramidException();
        }
        catch (OutOfMemoryError error) {
            throw new CannotBuildPyramidException();
        }
        int[][] p = getPyramid(inputNumbers.size());
        int[][] result = fillPyramid(inputNumbers, p);
        return result;
    }

    private int[][] fillPyramid(List<Integer> inputNumbers, int[][] p) {
        int id = 0;
        int jSize = p[0].length;
        for (int i = 1; i < p.length + 1; i++) {
            for (int j = 0; j < i; j++) {
                int jIndex = jSize / 2;
                jIndex = (jIndex - (i -1)) + (j * 2);
                p[i-1][jIndex] = inputNumbers.get(id);
                id++;
            }
        }
        return p;
    }

    private int[][] getPyramid(int size) {
        int iSize = 2, jSize = 3;
        int index = 3;
        while(index < size) {
            iSize++;
            index += iSize;
            jSize +=2;
        }
        if (index != size) throw new CannotBuildPyramidException();
        return new int[iSize][jSize];
    }

}

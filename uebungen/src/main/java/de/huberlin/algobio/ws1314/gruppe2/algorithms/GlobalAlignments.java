package de.huberlin.algobio.ws1314.gruppe2.algorithms;

import de.huberlin.algobio.ws1314.gruppe2.io.FASTASequence;
import de.huberlin.algobio.ws1314.gruppe2.tools.Tools;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;


public class GlobalAlignments
{
    /**
     * key = ArrayList which must contain 3 values: {speciesA, speciesB, genome}
     * value = matrix (x-axis stringA, y-axis stringB)
     */
    private HashMap<ArrayList<Integer>, int[][]> matrices = new HashMap<ArrayList<Integer>, int[][]>();

    private HashMap<Byte, HashMap<Byte, Integer>> substitutionMatrix;

    public GlobalAlignments( ArrayList<ArrayList<FASTASequence>> species,
                             HashMap<Byte, HashMap<Byte, Integer>> substitutionMatrix, ArrayList<String> fileNames )
    {
        this.substitutionMatrix = substitutionMatrix;
        int genomesCount = species.get(0).size();
        // genomes
        for ( int k = 0; k < genomesCount; ++k )
        {
            System.out.println();
            System.out.println(Tools.byteArrayToString(Arrays.copyOfRange(species.get(0).get(k).description, 2,
                                                                          species.get(0).get(k).description.length)));
            System.out.println("==============================");

            // speciesA
            for ( int i = 0; i < species.size(); ++i )
            {
                ArrayList<FASTASequence> genomesA = species.get(i);
                // speciesB
                for ( int j = i + 1; j < species.size(); ++j )
                {
                    ArrayList<FASTASequence> genomesB = species.get(j);
                    ArrayList<Integer> matrixKey = new ArrayList<Integer>();
                    matrixKey.add(i); // speciesA
                    matrixKey.add(j); // speciesB
                    matrixKey.add(k); // genome
                    byte[] A = genomesA.get(k).sequence;
                    byte[] B = genomesB.get(k).sequence;
                    int[][] matrix = createMatrix(A, B, matrixKey);
                    int globalAlignmentScore = calcGlobalAlignment(matrix, A, B);

                    System.out.println(fileNames.get(i) + ", " + fileNames.get(j) + ": " + globalAlignmentScore);
                }
            }
        }
    }

    private int[][] createMatrix( byte[] stringA, byte[] stringB, ArrayList<Integer> key )
    {
        int[][] matrix = new int[stringA.length][stringB.length];
        for ( int i = 0; i < stringA.length; ++i )
        {
            for ( int j = 0; j < stringB.length; ++j )
            {
                matrix[i][j] = ratingFunction(stringA[i], stringB[j]);
            }
        }
        // TODO: maybe we need to put all matrices for later use...
//        matrices.put( key, matrix );

        return matrix;
    }

    private int ratingFunction( byte a, byte b )
    {
        return substitutionMatrix.get(a).get(b);
    }

    private int calcGlobalAlignment( int[][] matrix, byte[] A, byte[] B )
    {
        int i = A.length - 1;
        int j = B.length - 1;
        System.out.println(i);
        System.out.println(j);
        int score;

        score = d(matrix, i, j, A, B);

        return score;
    }

    private int d( int[][] matrix, int i, int j, byte[] A, byte[] B )
    {
        System.out.println("i=" + i + ", j=" + j);
        if ( i == 0 )
        {
            System.out.println("i==0");
            return j;
        }
        else if ( j == 0 )
        {
            System.out.println("j==0");
            return i;
        }
        else
        {
            return min(d(matrix, i - 1, j, A, B) + 1,
                       d(matrix, i, j - 1, A, B) + 1,
                       d(matrix, i - 1, j - 1, A, B) + t(A[i], B[j]));
        }
    }

    private int min( int a, int b, int c )
    {
        if ( a <= b && a <= c )
            return a;
        else if ( b <= a && b <= c )
            return b;
        else
            return c;
    }

    private int t( byte a, byte b )
    {
        if ( a != b )
        {
            return 1;
        }
        else
        {
            return 0;
        }
    }
}

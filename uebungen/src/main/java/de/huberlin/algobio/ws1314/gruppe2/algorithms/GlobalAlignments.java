package de.huberlin.algobio.ws1314.gruppe2.algorithms;

import de.huberlin.algobio.ws1314.gruppe2.io.FASTASequence;
import de.huberlin.algobio.ws1314.gruppe2.tools.Tools;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Set;


public class GlobalAlignments
{
    /**
     * key = ArrayList which must contain 3 values: {speciesA, speciesB, genome}
     * value = matrix (x-axis stringA, y-axis stringB)
     */
    private HashMap<ArrayList<Integer>, int[][]> matrices = new HashMap<ArrayList<Integer>, int[][]>();

    public GlobalAlignments( ArrayList<ArrayList<FASTASequence>> species, ArrayList<String> fileNames )
    {
        int genomesCount = species.get( 0 ).size();
        // genomes
        for ( int k = 0; k < genomesCount; ++k )
        {
            System.out.println();
            System.out.println( Tools.byteArrayToString( Arrays.copyOfRange( species.get( 0 ).get( k ).description, 2, species.get( 0 ).get( k ).description.length ) ) );
            System.out.println( "==============================" );

            // speciesA
            for ( int i = 0; i < species.size(); ++i )
            {
                ArrayList<FASTASequence> genomesA = species.get( i );
                // speciesB
                for ( int j = i + 1; j < species.size(); ++j )
                {
                    ArrayList<FASTASequence> genomesB = species.get( j );
                    ArrayList<Integer> matrixKey = new ArrayList<Integer>();
                    matrixKey.add( i ); // speciesA
                    matrixKey.add( j ); // speciesB
                    matrixKey.add( k ); // genome
                    int[][] matrix = createMatrix( genomesA.get( k ).sequence, genomesB.get( k ).sequence, matrixKey );
                    int globalAlignmentScore = calcGlobalAlignment( matrix );

                    System.out.println( fileNames.get( i ) + ", " + fileNames.get( j ) + ": " + globalAlignmentScore );
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
                matrix[i][j] = ratingFunction( stringA[i], stringB[j] );
            }
        }
        // TODO: maybe we need to put all matrices for later use...
//        matrices.put( key, matrix );

        return matrix;
    }


    private int ratingFunction( byte a, byte b )
    {
        return a == b ? 0 : -1;
    }


    private int calcGlobalAlignment( int[][] matrix )
    {
        int i = 0;
        int j = 0;
        int score = 0;

        while ( i < matrix.length - 1 && j < matrix[i].length - 1 )
        {
            int horizontal = matrix[i + 1][j];
            int vertical = matrix[i][j + 1];
            int diagonal = matrix[i + 1][j + 1];
            int max = Math.max( horizontal, Math.max( vertical, diagonal ) );

            // add step to score
            score += max;

            if ( horizontal == max ) ++i;
            else if ( vertical == max ) ++j;
            else if ( diagonal == max )
            {
                ++i;
                ++j;
            }
        }
        return score;
    }
}

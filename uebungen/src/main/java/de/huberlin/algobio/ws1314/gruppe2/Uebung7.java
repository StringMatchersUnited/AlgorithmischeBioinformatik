package de.huberlin.algobio.ws1314.gruppe2;

import de.huberlin.algobio.ws1314.gruppe2.algorithms.NeighborJoining;
import de.huberlin.algobio.ws1314.gruppe2.io.FASTAReader;
import de.huberlin.algobio.ws1314.gruppe2.io.FASTASequence;
import de.huberlin.algobio.ws1314.gruppe2.tools.Tools;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;


public class Uebung7
{
    public static void main( String[] args )
    {
//        FASTASequence template;
//        ArrayList<FASTASequence> patterns;
//
//        String templateFileName;
//        String patternFileName;
//
//        templateFileName = args[0];
//        patternFileName = args[1];
//        
//        try
//        {
//        	template = new FASTAReader( templateFileName ).getFastaSequence( 0 );
//            patterns = new FASTAReader( patternFileName ).getFastaSequences();
//        } catch ( IOException e )
//        {
//            System.out.println( "IO Exception: Something went wrong while trying to open the specified files." );
//            return;
//        }
//
//        Tools.tFreq = Tools.getFrequencies( template.sequence );
//        
//        SuffixArray sA = new SuffixArray( template.sequence );
//        
//        for ( FASTASequence pattern : patterns )
//        {
//            sA.search(pattern.sequence);
//        }
    	
    	int mLength = 4;
    	int[][] matrix = new int[mLength][mLength];
    	
    	matrix[0][0] = 100;matrix[0][1] =   6;matrix[0][2] =   8;matrix[0][3] =  12;
    	matrix[1][0] =   6;matrix[1][1] = 100;matrix[1][2] =   4;matrix[1][3] =   8;
    	matrix[2][0] =   8;matrix[2][1] =   4;matrix[2][2] = 100;matrix[2][3] =   6;
    	matrix[3][0] =  12;matrix[3][1] =   8;matrix[3][2] =   6;matrix[3][3] = 100;
    	
//    	for ( int i = 0; i < mLength; i++ ) {
//    		for ( int j = 0; j < mLength; j++ ) {
//    			System.out.println(matrix[i][j]);
//    		}
//    	}
    	
    	new NeighborJoining(matrix, 4);
    }
}

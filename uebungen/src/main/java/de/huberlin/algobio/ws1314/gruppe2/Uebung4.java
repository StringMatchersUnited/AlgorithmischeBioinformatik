package de.huberlin.algobio.ws1314.gruppe2;

import de.huberlin.algobio.ws1314.gruppe2.algorithms.SuffixArray;
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


public class Uebung4
{
    public static void main( String[] args )
    {
        FASTASequence template;
        ArrayList<FASTASequence> patterns;

        String templateFileName;
        String patternFileName;

        templateFileName = args[0];
        patternFileName = args[1];
        
        try
        {
        	template = new FASTAReader( templateFileName ).getFastaSequence( 0 );
            patterns = new FASTAReader( patternFileName ).getFastaSequences();
        } catch ( IOException e )
        {
            System.out.println( "IO Exception: Something went wrong while trying to open the specified files." );
            return;
        }

        Tools.tFreq = Tools.getFrequencies( template.sequence );
        
        SuffixArray sA = new SuffixArray( template.sequence );
        
        for ( FASTASequence pattern : patterns )
        {
            sA.search(pattern.sequence);
        }
    }
}

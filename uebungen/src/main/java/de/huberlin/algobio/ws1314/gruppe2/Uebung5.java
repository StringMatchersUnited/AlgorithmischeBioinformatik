package de.huberlin.algobio.ws1314.gruppe2;

import de.huberlin.algobio.ws1314.gruppe2.algorithms.GlobalAlignments;
import de.huberlin.algobio.ws1314.gruppe2.io.FASTAReader;
import de.huberlin.algobio.ws1314.gruppe2.io.FASTASequence;
import de.huberlin.algobio.ws1314.gruppe2.io.SubstitutionMatrixReader;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;


public class Uebung5
{
    public static void main( String[] args )
    {
        if ( args.length < 2 )
        {
            System.err.println( "Aufruf: assignment5.jar <Verzeichnis, in dem ausschliesslich die fasta Files liegen> <substitutions-Matrix_datei>" );
            System.exit( 1 );
        }

        ArrayList<ArrayList<FASTASequence>> species = new ArrayList<ArrayList<FASTASequence>>();
        ArrayList<String> fileNames = new ArrayList<String>();

        File directory = new File( args[0] );
        ArrayList<File> fastaFiles = new ArrayList<File>( Arrays.asList( directory.listFiles() ) );

        try
        {
            for ( File fastaFile : fastaFiles )
            {
                fileNames.add( fastaFile.getName().substring( 0, fastaFile.getName().length() - 6 ) );
                species.add( new FASTAReader( fastaFile.getAbsolutePath() ).getFastaSequences() );
            }
        } catch ( IOException e )
        {
            System.err.println( e.getMessage() );
            System.err.println( "IO Exception: Something went wrong while trying to open the specified files." );
            return;
        }

        try
        {
            SubstitutionMatrixReader substitutionMatrixReader = new SubstitutionMatrixReader(args[1]);
            new GlobalAlignments( species, substitutionMatrixReader.substitutionMatrix, fileNames );
        }
        catch ( IOException e )
        {
            e.printStackTrace();
        }
    }
}

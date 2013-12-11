package de.huberlin.algobio.ws1314.gruppe2;

import de.huberlin.algobio.ws1314.gruppe2.algorithms.QGramIndex;
import de.huberlin.algobio.ws1314.gruppe2.algorithms.QGramSearch;
import de.huberlin.algobio.ws1314.gruppe2.io.FASTAReader;
import de.huberlin.algobio.ws1314.gruppe2.io.FASTASequence;
import de.huberlin.algobio.ws1314.gruppe2.tools.IntArray;
import de.huberlin.algobio.ws1314.gruppe2.tools.Tools;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;


class TemplateMetaData implements Serializable
{
    public HashMap<String, Integer> tFreq;
    public HashMap<String, IntArray> qGramIndex;

    public TemplateMetaData( HashMap<String, IntArray> qGramIndex, HashMap<String, Integer> tFreq )
    {
        this.qGramIndex = qGramIndex;
        this.tFreq = tFreq;
    }
}


public class Uebung3
{

    public static void main( String[] args )
    {
        FASTASequence template;
        ArrayList<FASTASequence> patterns;

        String templateFileName;
        String templateIndexFileName;
        String patternFileName;

        int q = 6;

        if ( args.length == 3 )
        {
            if ( args[0].equals( "index" ) )
            {

                templateFileName = args[1];
                templateIndexFileName = args[2];

                if ( !Files.exists( Paths.get( templateFileName ) ) )
                {
                    System.out.println( "Template file does not exists." );
                    return;
                }

                try
                {
                    template = new FASTAReader( templateFileName ).getFastaSequence( 0 );
                } catch ( IOException e )
                {
                    System.out.println( "IO Exception: Something went wrong while trying to open the specified files." );
                    return;
                }

                // get template character frequencies once
                Tools.tFreq = Tools.getFrequencies( template.sequence );

                QGramIndex qGramIndex = new QGramIndex( q, template.sequence, templateIndexFileName );

                TemplateMetaData templateMetaData = new TemplateMetaData( qGramIndex.getQgramIndex(), Tools.tFreq );

                try
                {
                    ObjectOutputStream oo = new ObjectOutputStream( new FileOutputStream( templateIndexFileName ) );
                    oo.writeObject( templateMetaData );
                } catch ( IOException e )
                {
                    e.printStackTrace();
                }

            } else if ( args[0].equals( "search" ) )
            {

                TemplateMetaData templateMetaData;
                templateIndexFileName = args[1];
                patternFileName = args[2];

                if ( !Files.exists( Paths.get( templateIndexFileName ) ) )
                {
                    System.out.println( "Index file does not exists." );
                    return;
                }

                if ( !Files.exists( Paths.get( patternFileName ) ) )
                {
                    System.out.println( "Pattern file does not exists." );
                    return;
                }

                try
                {
                    templateMetaData = (TemplateMetaData) new ObjectInputStream(
                            new FileInputStream( templateIndexFileName ) ).readObject();
                    patterns = new FASTAReader( patternFileName ).getFastaSequences();
                } catch ( IOException e )
                {
                    System.out.println( "IO Exception: Something went wrong while trying to open the specified files." );
                    return;
                } catch ( ClassNotFoundException e )
                {
                    System.out.println( "Index could not be read into memory." );
                    return;
                }

                for ( FASTASequence pattern : patterns )
                {
                    new QGramSearch( templateMetaData.qGramIndex, pattern.sequence, q, templateMetaData.tFreq );
                }
            } else
            {
                System.out.println( "Wrong arguments" );
                return;
            }
        } else
        {
            System.out.println( "Too less arguments" );
            return;
        }
    }
}

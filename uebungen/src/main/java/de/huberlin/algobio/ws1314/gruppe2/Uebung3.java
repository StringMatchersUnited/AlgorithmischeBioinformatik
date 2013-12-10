package de.huberlin.algobio.ws1314.gruppe2;

import de.huberlin.algobio.ws1314.gruppe2.algorithms.QGramIndex;
import de.huberlin.algobio.ws1314.gruppe2.algorithms.QGramSearch;
import de.huberlin.algobio.ws1314.gruppe2.io.FASTAReader;
import de.huberlin.algobio.ws1314.gruppe2.io.FASTASequence;
import de.huberlin.algobio.ws1314.gruppe2.tools.IntArray;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;

public class Uebung3
{
    public static void main( String[] args )
    {
        FASTASequence template;
        ArrayList<FASTASequence> patterns;

        String templateFileName;
        String templateIndexFileName;
        String patternFileName;

        if ( args.length == 3 )
        {
            if ( args[0].equals("index") )
            {

                templateFileName = args[1];
                templateIndexFileName = args[2];

                if ( !Files.exists(Paths.get(templateFileName)) )
                {
                    System.out.println("Template file does not exists.");
                    return;
                }

                try
                {
                    template = new FASTAReader(templateFileName).getFastaSequence(0);
                }
                catch ( IOException e )
                {
                    System.out.println("IO Exception: Something went wrong while trying to open the specified files.");
                    return;
                }

                new QGramIndex(3, template.sequence, template.sequenceLength, templateIndexFileName);

            }
            else if ( args[0].equals("search") )
            {

                HashMap<String, IntArray> qGramIndex;
                templateIndexFileName = args[1];
                patternFileName = args[2];

                if ( !Files.exists(Paths.get(templateIndexFileName)) )
                {
                    System.out.println("Index file does not exists.");
                    return;
                }

                if ( !Files.exists(Paths.get(patternFileName)) )
                {
                    System.out.println("Pattern file does not exists.");
                    return;
                }

                try
                {
                    qGramIndex = (HashMap<String, IntArray>) new ObjectInputStream(new FileInputStream(templateIndexFileName)).readObject();
                    patterns = new FASTAReader(patternFileName).getFastaSequences();
                }
                catch ( IOException e )
                {
                    System.out.println("IO Exception: Something went wrong while trying to open the specified files.");
                    return;
                }
                catch ( ClassNotFoundException e )
                {
                    System.out.println("Index could not be read into memory.");
                    return;
                }

                for (FASTASequence pattern: patterns)
                {
                    new QGramSearch(qGramIndex, pattern.sequence, pattern.sequenceLength);
                }
            }
            else
            {
                System.out.println("Wrong arguments");
                return;
            }
        }
        else
        {
            System.out.println("Too less arguments");
            return;
        }
    }
}

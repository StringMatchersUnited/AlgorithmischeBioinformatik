package de.huberlin.algobio.ws1314.gruppe2;

import de.huberlin.algobio.ws1314.gruppe2.algorithms.boyerMoore;
import de.huberlin.algobio.ws1314.gruppe2.io.FASTAReader;
import de.huberlin.algobio.ws1314.gruppe2.io.FASTASequence;
import de.huberlin.algobio.ws1314.gruppe2.tools.Tools;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

public class Uebung2
{
    public static void main( String[] args )
    {
        FASTASequence template;
        ArrayList<FASTASequence> patterns;

        String templateFileName;
        String patternFileName;

        if ( args.length < 2 )
        {
            System.out.println("Usage: StringMatcher <template_file> <pattern_file>.");
            System.out.println("Both files have to be in FASTA format.");
            return;
        }

        templateFileName = args[0];
        patternFileName = args[1];

        if ( !Files.exists(Paths.get(templateFileName)) )
        {
            System.out.println("Template file does not exists.");
            return;
        }

        if ( !Files.exists(Paths.get(patternFileName)) )
        {
            System.out.println("Pattern file does not exists.");
            return;
        }


        try
        {
            template = new FASTAReader(templateFileName).getFastaSequence(0);
            patterns = new FASTAReader(patternFileName).getFastaSequences();
        }
        catch ( IOException e )
        {
            System.out.println("IO Exception: Something went wrong while trying to open the specified files.");
            return;
        }

        // get template character frequencies once
        Tools.tFreq = Tools.getFrequencies(template.sequence, template.sequenceLength);

//        TimeMeasure.start("Boyer-Moore - All patterns");
        for ( FASTASequence pattern : patterns )
        {
//            TimeMeasure.start("Boyer-Moore - Pattern " + i);
            new boyerMoore(pattern.sequence, pattern.sequenceLength, template.sequence,
                           template.sequenceLength);
//            TimeMeasure.stop("Boyer-Moore - Pattern " + i);
            System.out.println();
        }
//        TimeMeasure.stop("Boyer-Moore - All patterns");
    }
}
package de.huberlin.algobio.ws1314.gruppe2;

import de.huberlin.algobio.ws1314.gruppe2.algorithms.boyerMoore;
import de.huberlin.algobio.ws1314.gruppe2.io.FASTAReader;
import de.huberlin.algobio.ws1314.gruppe2.io.FASTASequence;
import de.huberlin.algobio.ws1314.gruppe2.tools.TimeMeasure;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

public class Uebung1
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


        System.out.println();
        System.out.println("BOYERMOORE MATCHING");
        System.out.println();


        TimeMeasure.start("Boyer-Moore - All patterns");
        for ( int i = 0; i < patterns.size(); ++i )
        {
            TimeMeasure.start("Boyer-Moore - Pattern " + i);
            new boyerMoore(patterns.get(i).sequence, patterns.get(i).sequenceLength, template.sequence,
                           template.sequenceLength);
            TimeMeasure.stop("Boyer-Moore - Pattern " + i);
        }
        TimeMeasure.stop("Boyer-Moore - All patterns");


//        System.out.println();
//        System.out.println("KNUTH-MORRIS-PRATT MATCHING");
//        System.out.println();
//        TimeMeasure.start("KMP all patterns");
//        for ( int i = 0; i < patterns.getFastaSequences().size(); ++i )
//        {
//            patternSequenceData = patterns.getData(i);
//            TimeMeasure.start("KMP - Pattern " + i);
//            new KMP(patternSequenceData, new PatternAnalysing(patternSequenceData).getPraefix(), templateSequenceData);
//            TimeMeasure.stop("KMP - Pattern " + i);
//        }
//        TimeMeasure.stop("KMP all patterns");
//
//
//
//        System.out.println();
//        System.out.println("NAIVE MATCHING");
//        System.out.println();
//
//        TimeMeasure.start("Naive - All patterns");
//        for ( int i = 0; i < patterns.getFastaSequences().size(); ++i )
//        {
//            patternSequenceData = patterns.getData(i);
//            TimeMeasure.start("Naive - Pattern " + i);
//            new NaiveMatching(patternSequenceData, templateSequenceData);
//            TimeMeasure.stop("Naive - Pattern " + i);
//        }
//        TimeMeasure.stop("Naive - All patterns");
    }
}

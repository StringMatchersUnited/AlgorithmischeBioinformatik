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
        String templateIndexFileName;
        String patternFileName;

        if (args.length == 3) {
	        if (args[0].equals("index")) {
	        	
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
	        	
	        	Tools.tFreq = Tools.getFrequencies(template.sequence, template.sequenceLength);
	        	
        		new index(template.sequence, templateIndexFileName);
	        	
	        } else if (args[0].equals("search")) {
	        	
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
	        	
//	        	try
//	        	{
//	        		template = new FASTAReader(templateFileName).getFastaSequence(0);
//	        	}
//	        	catch ( IOException e )
//	        	{
//	        		System.out.println("IO Exception: Something went wrong while trying to open the specified files.");
//	        		return;
//	        	}
//	        	
//	        	Tools.tFreq = Tools.getFrequencies(template.sequence, template.sequenceLength);
//	        	
//	        	for ( FASTASequence pattern : patterns )
//	        	{
//	        		new boyerMoore(pattern.sequence, pattern.sequenceLength, template.sequence,
//	        				template.sequenceLength);
//	        		System.out.println();
//	        	}
	        	
	        } else {
	        	System.out.println("Wrong arguments");
	            return;
	        }
        } else {
        	System.out.println("Too less arguments");
            return;
        }
    }
}
package de.huberlin.algobio.ws1314.gruppe2;

import de.huberlin.algobio.ws1314.gruppe2.algorithms.NeighborJoining;
import de.huberlin.algobio.ws1314.gruppe2.io.FASTAReader;
import de.huberlin.algobio.ws1314.gruppe2.io.CSVReader;
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
    	String sequenceFile;
    	CSVReader reader;
    	
    	sequenceFile = args[0];
    	
    	reader = new CSVReader(sequenceFile);
    	
    	new NeighborJoining(reader.getData(), reader.getLength());
    }
}

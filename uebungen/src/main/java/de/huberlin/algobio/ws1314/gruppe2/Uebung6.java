package de.huberlin.algobio.ws1314.gruppe2;

import de.huberlin.algobio.ws1314.gruppe2.algorithms.BuildSuffixArray;
import de.huberlin.algobio.ws1314.gruppe2.algorithms.BYP;
import de.huberlin.algobio.ws1314.gruppe2.io.CSVQueryReader;
import de.huberlin.algobio.ws1314.gruppe2.io.CSVTestDataReadsReader;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;

class IndexData implements Serializable
{
    public ArrayList<byte[]> template;
    public ArrayList<int[]> suffix;
    public ArrayList<Integer> k;

    public IndexData( ArrayList<byte[]> template, ArrayList<int[]> suffix, ArrayList<Integer> k )
    {
        this.template = template;
        this.suffix = suffix;
        this.k = k;
    }
}

public class Uebung6
{
    public static void main( String[] args )
    {
    	if ( args.length == 3 ) {
            if ( args[0].equals( "index" ) ) {
            	String queryFile;
            	String indexFile;
            	CSVQueryReader reader;
            	
            	queryFile = args[1];
            	indexFile = args[2];
            	
            	reader = new CSVQueryReader(queryFile);
            	
            	ArrayList<byte[]> data = reader.getData();
            	ArrayList<Integer> k = reader.getK();
            	ArrayList<int[]> suffix = new ArrayList<int[]>();
            	BuildSuffixArray suffixArray;
            	IndexData indexData;
            	ObjectOutputStream oOStream;
            	
            	for ( int i = 0; i < data.size(); i++ ) {
            		suffixArray = new BuildSuffixArray(data.get(i));
            		suffix.add(suffixArray.getSuffixArray());
            	}
            	
            	try {
            		oOStream = new ObjectOutputStream( new FileOutputStream( indexFile ) );
            		indexData = new IndexData( data, suffix, k );
        			oOStream.writeObject( indexData );
        		} catch ( IOException e ) {
        			e.printStackTrace();
        		}
            } else if ( args[0].equals( "search" ) ) {
            	String indexFile;
            	String patternFile;
            	CSVTestDataReadsReader reader;
            	
            	IndexData indexData;
            	
            	ArrayList<byte[]> templates;
                ArrayList<int[]> suffixes;
                ArrayList<Integer> ks;
                
                BYP lBYP;
            	
            	indexFile = args[1];
            	patternFile = args[2];
            	
            	reader = new CSVTestDataReadsReader(patternFile);
            	
            	try {
            		indexData = (IndexData) new ObjectInputStream(new FileInputStream( indexFile ) ).readObject();
            		templates = indexData.template;
            		suffixes = indexData.suffix;
            		ks = indexData.k;
            		
            		for ( int i = 0; i < templates.size(); i++ ) {
            			lBYP = new BYP( ( i + 1 ), templates.get(i), suffixes.get(i), ks.get(i), reader.getData() );
            		}
            	} catch ( IOException e ) {
                    System.out.println( "IO Exception: Something went wrong while trying to open the specified files." );
                    return;
                } catch ( ClassNotFoundException e ) {
                    System.out.println( "Index could not be read into memory." );
                    return;
                }
            	
            } else {
            	System.out.println( "Wrong arguments" );
                return;
            }
    	} else {
            System.out.println( "Too less arguments" );
            return;
        }
    }
}

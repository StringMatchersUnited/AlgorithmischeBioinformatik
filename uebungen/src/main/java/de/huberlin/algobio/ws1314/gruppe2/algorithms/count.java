package de.huberlin.algobio.ws1314.gruppe2.algorithms;

import de.huberlin.algobio.ws1314.gruppe2.io.FASTAReader;

import java.io.IOException;



public class count
{
    public count( String file)
    {
    	int a = 0;
    	int c = 0;
    	int g = 0;
    	int t = 0;
    	int n = 0;
    	int z = 0;
    	char b;

	    	try
	        {
	            FASTAReader fr = new FASTAReader(file);
	            for ( int i = 0; i < fr.getFastaSequences().size(); ++i )
	            {
                    byte[] data = fr.getFastaSequence(i).sequence;
	                for ( int j = 0; j < fr.getFastaSequence(i).sequenceLength; ++j )
	                {
	                	b = (char) data[j];
	                	
	    	    		if (b == 'a') {
	    	    			a++;
	    	    			z++;
	    	    		} else if (b == 'c') {
	    	    			c++;
	    	    			z++;
	    	    		} else if (b == 'g') {
	    	    			g++;
	    	    			z++;
	    	    		} else if (b == 't') {
	    	    			t++;
	    	    			z++;
	    	    		} else if (b == 'n') {
	    	    			n++;
	    	    			z++;
	    	    		}
	                }
	            }
	        }
	        catch ( IOException e )
	        {
	            e.printStackTrace();
	        }
    	int alle = n+a+c+g+t;
    	
    	System.out.println("z: " + z);
    	System.out.println("a: " + ((float)a/(float)alle));
    	System.out.println("c: " + ((float)c/(float)alle));
    	System.out.println("g: " + ((float)g/(float)alle));
    	System.out.println("t: " + ((float)t/(float)alle));
    	System.out.println("n: " + ((float)n/(float)alle));
    	System.out.println("alle: " + (n+a+c+g+t));
    	
    	System.out.println((float) a/alle);
    	double erg;
    	erg = Math.pow((double)a/alle, 1) * Math.pow((double)c/alle, 2) * Math.pow((double)g/alle, 2) * Math.pow((double)t/alle, 1) * Math.pow((double)n/alle, 0) * alle;
    	System.out.println("tccgga: " + erg);
    	erg = Math.pow((double)a/alle, 1) * Math.pow((double)c/alle, 3) * Math.pow((double)g/alle, 1) * Math.pow((double)t/alle, 1) * Math.pow((double)n/alle, 0) * alle;
    	System.out.println("gctacc: " + erg);
    	erg = Math.pow((double)a/alle, 4) * Math.pow((double)c/alle, 0) * Math.pow((double)g/alle, 0) * Math.pow((double)t/alle, 2) * Math.pow((double)n/alle, 0) * alle;
    	System.out.println("taataa: " + erg);
    	erg = Math.pow((double)a/alle, 1) * Math.pow((double)c/alle, 4) * Math.pow((double)g/alle, 1) * Math.pow((double)t/alle, 1) * Math.pow((double)n/alle, 0) * alle;
    	System.out.println("cctcagc: " + erg);
    	erg = Math.pow((double)a/alle, 1) * Math.pow((double)c/alle, 3) * Math.pow((double)g/alle, 3) * Math.pow((double)t/alle, 1) * Math.pow((double)n/alle, 0) * alle;
    	System.out.println("cctgcagg: " + erg);
    	erg = Math.pow((double)a/alle, 0) * Math.pow((double)c/alle, 4) * Math.pow((double)g/alle, 4) * Math.pow((double)t/alle, 0) * Math.pow((double)n/alle, 0) * alle;
    	System.out.println("ggcgcgcc: " + erg);
    	erg = Math.pow((double)a/alle, 0) * Math.pow((double)c/alle, 11) * Math.pow((double)g/alle, 0) * Math.pow((double)t/alle, 0) * Math.pow((double)n/alle, 0) * alle;
    	System.out.println("ccccccccccc: " + erg);
    	erg = Math.pow((double)a/alle, 11) * Math.pow((double)c/alle, 0) * Math.pow((double)g/alle, 0) * Math.pow((double)t/alle, 0) * Math.pow((double)n/alle, 0) * alle;
    	System.out.println("aaaaaaaaaaa: " + erg);
    	erg = Math.pow((double)a/alle, 12) * Math.pow((double)c/alle, 0) * Math.pow((double)g/alle, 0) * Math.pow((double)t/alle, 0) * Math.pow((double)n/alle, 0) * alle;
    	System.out.println("aaaaaaaaaaaa: " + erg);
    	erg = Math.pow((double)a/alle, 15) * Math.pow((double)c/alle, 0) * Math.pow((double)g/alle, 0) * Math.pow((double)t/alle, 0) * Math.pow((double)n/alle, 0) * alle;
    	System.out.println("aaaaaaaaaaaaaaa: " + erg);
    	erg = Math.pow((double)a/alle, 20) * Math.pow((double)c/alle, 0) * Math.pow((double)g/alle, 0) * Math.pow((double)t/alle, 0) * Math.pow((double)n/alle, 0) * alle;
    	System.out.println("aaaaaaaaaaaaaaaaaaaa: " + erg);
    }

    public static void main( String[] args )
    {
        new count("src/main/resources/sequence.fasta");
    }
}

package de.huberlin.algobio.ws1314.gruppe2.algorithms;


import java.util.Arrays;
import java.util.ArrayList;
import java.util.HashMap;


public class BYP
{
	private byte[] template;
	private int[] aSortedSuffix;
	private int tLength;
    private int sLength;
    // for debug
    private int id;
    private int pid;
	
    public BYP( int id, byte[] pTemplate, int[] suffix, int k, ArrayList<byte[]> pattern ) {
    	// for debug
    	this.id = id;
    	
    	int r;
		int curK;
		byte[] part;
		boolean hit;
    	
    	template = pTemplate;
    	aSortedSuffix = suffix;
    	
    	tLength = template.length;
    	sLength = tLength + 1;
    	
    	// Hits direkt in Datei schreiben?
    	for ( int i = 0; i < pattern.size(); i++ ) {
    		// for debug
    		pid = i + 1;
    		// editabstand = 0 ?
    		if ( k == 0 ) {
    			// gleich langes template & pattern
    			if ( tLength == pattern.get(i).length ) {
    				// direkter Vergleich mit SuffixArray-Suche
    				if ( searchSuffixArray( pattern.get( i ) ) ) {
    					System.out.println(id + ":" + ( i + 1 ));
    				}
    			}
    		} else {
    			// BYP
    			if ( Math.abs( tLength - pattern.get(i).length ) < k ) {
    				curK = k;
    				hit = false;
    				
    				// 1. Partition
    				r = ( pattern.get(i).length / (k + 1) );
    				part = new byte[r];
    				
    				// 2. Search
    				for ( int l = 0; l < k; l++ ) {
    					for ( int j = 0; j < r; j++ ) {
    						part[j] = pattern.get(i)[ j + ( l * r ) ];
    					}
    					if ( searchSuffixArray( part ) ) {
    						hit = true;
    						break;
    					}
    				}
    				
    				// 3. Check
    				if ( check(pattern.get(i), k) < ( k + 1 ) ) {
    					// suffixArray-search mit k
    					System.out.println(id + ":" + ( i + 1 ));
    				}
    			}
    		}
    	}
    }
    
    private int check ( byte[] pattern, int k ) {
    	boolean hit = false;
    	int[][] matrix = new int[pattern.length + 1][tLength + 1];
    	
    	for ( int i = 1; i < pattern.length; i++ ) {
    		matrix[i][0] = i;
    	}
    	for ( int i = 1; i < tLength; i++ ) {
    		matrix[0][i] = i;
    	}

    	
    	for ( int i = 1; i < pattern.length + 1; i++ ) {
    		for ( int j = i - k; j < ( i + k + 1); j++ ) {
    			if ( ( j < 1 ) || ( j > tLength ) ) {
    				continue;
    			}
    			matrix[i][j] = matrix[i - 1][j - 1] + equal( pattern[i - 1], template[j - 1] );
    			
    			if ( j < ( i + k ) ) {
    				matrix[i][j] = Math.min(matrix[i][j], matrix[i - 1][j] + 1);
    			}
    			if ( j > ( i - k ) ) {
    				matrix[i][j] = Math.min(matrix[i][j], matrix[i][j - 1] + 1);
    			}
    		}
    	}
//    	for ( int i = 0; i < pattern.length + 1; i++ ) {
//    		for ( int j = 0; j < tLength + 1; j++ ) {
//    			System.out.print(matrix[i][j] + " ");
//    		}
//    		System.out.println();
//    	}
    	return matrix[pattern.length][tLength];
    }
    
    private int equal ( byte p, byte t) {
    	if ( p == t ) {
    		return 0;
    	} else {
    		return 1;
    	}
    }
    
    
    // aus SuffixArray.java
    private boolean searchSuffixArray( byte[] pattern ) {
    	boolean hit = false;
    	int l = 1;
    	int r = tLength;
    	int m = 0;
    	int f;
    	
    	int index = 0;
    	while (l < r) {
    		m = l + ((r - l) / 2);
//    		if ((id==357) && (pid==76739)) {
//    			System.out.println( "l:" + l + ",r:" + r + " = " + (l + ((r - l) / 2)));
//    		}
    		f = searchFunction(pattern, m);
    		if (f > 0) { // go up
    			r = m - 1;
    		} else if (f < 0) { // go down
    			l = m + 1;
    		} else { // occurence
    			int n = m;
    			if (searchFunction(pattern, n) == 0) {
    				hit = true;
    			}
    			break;
    		}
    	}
    	
    	return hit;
    }
    
    private int searchFunction(byte[] pattern, int index) {
    	// - 1: kleiner als Pattern | 1: größer als pattern | 0: treffer 
    	int returnVal = 0;
    	if (index > sLength - 1) {
    		returnVal = -1;
    	} else if (aSortedSuffix[index] + pattern.length > sLength - 1) { // zu kurzes suffix, aber Richtung muss erkannt werden
    		for ( int i = 0; ( aSortedSuffix[index] + i ) < sLength - 1; i++ ) {
    			if (template[aSortedSuffix[index] + i] != pattern[i]) {
    				if (template[aSortedSuffix[index] + i] < pattern[i]) {
    					returnVal = -1;
    				} else {
    					returnVal = 1;
    				}
    				break;
    			}
    		}
    		if (returnVal == 0) {
    			returnVal = -1;
    		}
    	} else {
    		for (int i = 0; i < pattern.length; i++) {
//    			if (id == 357) {
//    				System.out.println(template[aSortedSuffix[index] + i] + "  " + pattern[i]);
//    			}
    			if (template[aSortedSuffix[index] + i] != pattern[i]) {
    				if (template[aSortedSuffix[index] + i] < pattern[i]) {
    					returnVal = -1;
    				} else {
    					returnVal = 1;
    				}
    				break;
    			}
    		}
    	}
    	return returnVal;
    }
}

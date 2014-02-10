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
    	
    	template = pTemplate;
    	aSortedSuffix = suffix;
    	
    	tLength = template.length;
    	sLength = tLength + 1;
    	
    	// Hits direkt in Datei schreiben?
    	
    	for ( int i = 0; i < pattern.size(); i++ ) {
    		// for debug
    		pid = i +1;
    		// editabstand = 0 ?
    		if ( k == 0 ) {
    			// gleich langes template & pattern
    			if ( tLength == pattern.get(i).length ) {
    				// direkter Vergleich mit SuffixArray-Suche
    				if ( searchSuffixArray( pattern.get( i ) ) ) {
    					System.out.println("T:" + id + ",P:" + ( i + 1 ));
    				}
    			}
    		} else {
    			
    		}
    	}
    	
    	
    	
    	
//        // aus Datei oder per parameter
//    	int k = 2; // edit-abstand, laut aufgabe letzter wert einer Zeile
//    	int p = pattern.length;
//    	int t = 30;
//    	int r;
//    	
//    	// für potentielle Treffer
//    	// Array oder Liste ? (Wenig treffer !!je Partition!! zu erwarten bei der Aufgabenstellung, oder?)
//    	
//    	// int[] potHits = new int[k + 1];
//    	// List<Integer> buckets = new ArrayList<Integer>();
//    	
//    	// 1. Partition
//    	// Berechnung der Länge einer Patition
//    	
//    	r = ( t / (k + 1) ); // länge einer patition
//    	
//    	
//    	// 2. Search
//    	// Suche Teilstring exakt in T -> qGram
//    	// an Länge der qGramme denken !!!
//    	
//    	for ( int i = 0; i < ( k + 1 ); i++ ) { // Partitionen
//    		for ( int j = 0; j < r; j++ ) {
//    			// potentielles vorkommen an stelle x ( = j + ( i * r ) )
//    			pattern[ j + ( i * r ) ] // Zugriff mit verschiebung nach aktueller Partition
//    		}
//    	}
//    	
//    	// 3. Check
//    	// Prüfe alle potentiellen vorkommen
//    	// länge eines Treffers im schlimmsten fall p + k
//    	
//    	// Sei i die Startposition einer Partition P‘ von P in T
//    	// Wir müssen testen , ob um T[i; i+r ] herum ein k - Difference Vorkommen von P liegt
//    	// Ein solches Vorkommen kann im schlimmsten Fall n+k Zeichen lang sein
//    	// Also alignieren wir T[i - n - k .. i+r+n+k ] mit P
    	
    	
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
    	// - 1: kleiner als Pattern | 1: grÃ¶ÃŸer als pattern | 0: treffer 
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

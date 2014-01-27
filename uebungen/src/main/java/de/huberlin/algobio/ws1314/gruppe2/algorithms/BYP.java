package de.huberlin.algobio.ws1314.gruppe2.algorithms;


import java.util.Arrays;
import java.util.HashMap;


public class BYP
{
    public BYP( byte[] pattern, String indexFileName )
    {
        // aus Datei oder per parameter
    	int k = 2; // edit-abstand, laut aufgabe letzter wert einer Zeile
    	int p = pattern.length;
    	int t = 30;
    	int r;
    	
    	// für potentielle Treffer
    	// Array oder Liste ? (Wenig treffer !!je Partition!! zu erwarten bei der Aufgabenstellung, oder?)
    	
    	// int[] potHits = new int[k + 1];
    	// List<Integer> buckets = new ArrayList<Integer>();
    	
    	// 1. Partition
    	// Berechnung der Länge einer Patition
    	
    	r = ( t / (k + 1) ); // länge einer patition
    	
    	
    	// 2. Search
    	// Suche Teilstring exakt in T -> qGram
    	// an Länge der qGramme denken !!!
    	
    	for ( int i = 0; i < ( k + 1 ); i++ ) { // Partitionen
    		for ( int j = 0; j < r; j++ ) {
    			// potentielles vorkommen an stelle x ( = j + ( i * r ) )
    			pattern[ j + ( i * r ) ] // Zugriff mit verschiebung nach aktueller Partition
    		}
    	}
    	
    	// 3. Check
    	// Prüfe alle potentiellen vorkommen
    	// länge eines Treffers im schlimmsten fall p + k
    	
    	// Sei i die Startposition einer Partition P‘ von P in T
    	// Wir müssen testen , ob um T[i; i+r ] herum ein k - Difference Vorkommen von P liegt
    	// Ein solches Vorkommen kann im schlimmsten Fall n+k Zeichen lang sein
    	// Also alignieren wir T[i - n - k .. i+r+n+k ] mit P
    	
    	
    }
}
